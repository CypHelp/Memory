import re


class HTTPTools:

    @classmethod
    def str_url_to_dict(cls, param):
        """
        将参数转为字典格式
        user=123&pass=456
        {
            "user":"123",
            "pass":"456"
        }
        :param param: user=123&pass=45
        :return:
        """
        all_param = re.findall("(.+?)=(.+?)", param)
        result = {}
        for param in all_param:
            key, value = param
            result[key] = value
        return result

    @classmethod
    def dict_url_to_str(cls, url):
        """
        将字典形式的url转为字符形式
        {
            "user":"123",
            "pass":"456"
        }
        ->
        user=123&pass=456
        :param url:
        :return:
        """
        if isinstance(url, dict):
            # ["user=123","pass=456"]
            param = ["{0}={1}".format(key, value) for key, value in url.items()]
            return "&".join(param)
        else:
            raise TypeError("[{}]不是字典类型".format(url.__clas__))

    @classmethod
    def make_header(cls, data):
        """
        将文本形式的header转为字典形式
        Cookie:xxxx
        Host:1233
        Set-Cookie
        ->
        {
            "Cookie":"xxx",
            "Host":"xxx"
        }
        :return:
        """
        header = data.replace(" ", "")
        # User-Agent:Android
        return {cls.http_header_convert(k): v for k, v in re.findall("(.+?):(.+)", header)}

    @classmethod
    def first_word_upper(cls, key):
        """
        将单词的首字母大写
        :param key: 单词
        :return:
        """
        first_char = key[:1]
        return first_char.upper() + key[1:]

    @classmethod
    def http_header_convert(cls, data):
        """
        将http请求头/响应头单词转为大写
        set-cookie -> Set-Cookie
        :param data:
        :return:
        """
        if "-" in data:
            # ["set","cookie"]
            words = data.split("-")
            header = [cls.first_word_upper(word) for word in words]
            return "-".join(header)
        else:
            cls.first_word_upper(data)


if __name__ == '__main__':
    print(HTTPTools.http_header_convert("x-request-header"))
