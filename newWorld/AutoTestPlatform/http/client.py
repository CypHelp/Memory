import json
import logging
import os
from json import JSONDecodeError

import chardet
import requests
from requests import PreparedRequest

from AutoTestPlatform.tools.date import DateTime
from AutoTestPlatform.tools.file import FileUtil
from AutoTestPlatform.tools.http import HTTPTools


class Client:

    def __init__(self):
        self.session = requests.Session()
        self.proxies = None

    def set_proxies(self, proxies):
        self.proxies = proxies

    def do(self, request: PreparedRequest):
        res = self.session.send(request, proxies=self.proxies)
        logging.info("请求url：{0}，状态码：{1}".format(res.url, res.status_code))
        return Response(res)

    def do_many_request(self, many_request):
        return [self.do(req) for req in many_request]

    def close(self):
        logging.warning("链接已关闭")
        self.session.close()


class Response:

    def __init__(self, response):
        self.__response = response

    def get_header(self):
        return self.__response.headers

    def get_header_by(self, data):
        data = HTTPTools.http_header_convert(data)
        return self.__response.headers[data]

    def jsonify(self):
        if not self.__response.content.startswith("{") or not self.__response.content.startswith("["):
            raise TypeError("响应结果不是json格式")
        # 将结果转为json格式（如果是json格式）
        try:
            return json.loads(self.__response.content)
        except JSONDecodeError as e:
            logging.error("发生异常：原因：{}".format(e.msg))

    @property
    def text(self):
        # 如果没有返回任何文档则不转换编码
        if not self.__response.text or self.__response.text == "":
            return ""
        return self.__response.content.decode(chardet.detect(self.__response.content)["encoding"])

    @property
    def response(self):
        return self.__response

    def auto_save(self):
        # 创建temp目录
        FileUtil.mkdir("temp")
        # temp目录和文件路径
        file_path = os.path.join("temp", DateTime.current_date_time() + ".temp")
        self.save_as_file(file_path)

    def save_as_file(self, filename):
        with open(filename, "xb") as fp:
            fp.write(self.__response.content)
        logging.info("请求：{0}响应结果保存成功，文件路径为:{1}".format(self.__response.url, filename))

    def filter(self, f):
        # 鸭子类型
        return f(self.text)
