import json
import logging
import os
import re
import time
from json import JSONDecodeError

import chardet
import requests
from requests import Response, PreparedRequest


class Client:

    def __init__(self):
        self.session = requests.Session()
        self.proxies = ""

    def set_proxies(self, proxies):
        self.proxies = proxies

    def do(self, request: PreparedRequest):
        res = self.session.send(request, proxies=self.proxies)
        logging.info("请求url：{0}，状态码：{1}".format(res.url, res.status_code))
        return Responses(res)

    def do_many_request(self, many_request):
        return [self.do(req) for req in many_request]

    def close(self):
        logging.warning("链接已关闭")
        self.session.close()


class Responses:

    def __init__(self, response: Response):
        self.__response = response

    def jsonify(self):
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
        ti = time.time()
        # 把时间戳转换为本地时间
        local = time.localtime(ti)
        # 把时间转为字符串格式
        filename = time.strftime("%Y-%m-%d_%H:%M:%S.temp", local)
        # 创建temp目录
        FileUtil.mkdir("temp")
        # temp目录和文件路径
        file_path = os.path.join("temp", filename)
        self.save_as_file(file_path)

    def save_as_file(self, filename):
        with open(filename, "xb") as fp:
            fp.write(self.__response.content)
        logging.info("请求：{0}响应结果保存成功，文件路径为:{1}".format(self.__response.url, filename))

    def filter(self, f):
        # 鸭子类型
        return f(self.text)


class FileUtil:

    @classmethod
    def delete(cls, path):
        # 如果文件不存在则不做处理
        if not os.path.exists(path):
            return
        # 循环当前所有的文件夹
        for file in os.listdir(path):
            # 根据当前路径拼接 文件路径
            file_path = os.path.join(path, file)
            # 如果是文件直接删除
            if os.path.isfile(file_path):
                os.remove(file_path)
            # 如果是文件夹则删除当前文件夹内容，然后删除该文件夹
            elif os.path.isdir(path):
                cls.delete(file_path)
                os.rmdir(file_path)
        if os.path.isfile(path):
            os.remove(path)
        elif os.path.isdir(path):
            os.rmdir(path)

    @classmethod
    def mkdir(cls, name):
        if os.path.exists(name):
            return
        os.mkdir(name)


class ImmutableDict:

    def __init__(self):
        self.__data = {}

    def __setitem__(self, key, value):
        for k in self.__data:
            if key == k:
                raise ValueError("已经有该Key:【{}】不能修改".format(key))
        self.__data[key] = value

    def __getitem__(self, item):
        return self.__data[item]


class Context:

    def __init__(self):
        self.__immutable = ImmutableDict()

    def set(self, key, value):
        self.__immutable[key] = value

    def get(self, key):
        return self.__immutable[key]


class ResponseTable:

    def __init__(self):
        self.__response_table = ImmutableDict()

    def set(self, key, value):
        self.__response_table[key] = value

    def get(self, item):
        return self.__response_table[item]


def images(data):
    return re.findall("<img.+?src=\"(.+?)\".+?>", data)


# response("userid","itemid","siteid")
class Analysis:

    def __init__(self, response):
        self.response = response

    @classmethod
    def analysis(cls, data):
        # 1:response("userid","itemid","siteid").header
        case_id = re.findall("(.+?):(.+)\.(.+)", data)
        if len(case_id) != 1:
            raise SyntaxError("语法错误: 【{}】".format(data))
        case_id, body, insert_place = case_id[0]
        # response -> 依赖响应结果 (json)
        # header   -> 依赖头部信息
        # url      -> 依赖请求URL
        # response("userid","itemid","siteid")
        res = re.findall("(\w+)\((.+)\)", body)
        if len(res) != 1:
            raise SyntaxError("语法错误: 【{}】".format(data))
        try:
            where, param = res[0]
        except ValueError:
            raise SyntaxError("语法错误: 【{}】".format(data))
        # 将依赖位置转为小写
        where = where.lower()
        # 获取括号中的参数信息
        param = cls.get_param(param)
        if len(param) < 1:
            raise SyntaxError("语法错误: 【{}】".format(data))
        return case_id, where, param, insert_place

    @classmethod
    def get_param(cls, param):
        return re.findall("\"(.+?)\"", param)

    @classmethod
    def get_response(cls, response_result, param):
        if isinstance(response_result, dict):
            result = {}
            for key in param:
                # 根据传递的字段获取到值
                result[key] = response_result[key]
            return result
        else:
            raise TypeError("响应结果必须是json格式")


if __name__ == '__main__':
    # FileUtil.delete("/home/amdins/no.625/zentao")
    strs = '1:response("userid","itemid","siteid").url'
    a = Analysis("")
    print(a.analysis(strs))
