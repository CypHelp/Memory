import re

from database.back import Step
from interface.client import Client


class Function:
    @staticmethod
    def security(data, param):
        # <input type="hidden" name="login_security" value="158911e7321cdb70d91e4c91dcebba9a-609d143b876affd0e1e949f98257682d">
        # <input type="hidden" name="http_referer" value="http%3A%2F%2F192.168.1.16%2FDBshop%2Fuser%2Flogin%3Fhttp_referer%3Dhttp%25253A%25252F%25252F192.168.1.16%25252FDBshop%25252Fhome">
        return re.findall('<input.+hidden.+{}.+value="(.+)"'.format(param), data)[0]

    @staticmethod
    def get_cookie(data):
        if ";" in data:
            return data.split(";")[0]


class FunctionTools:

    def __init__(self, clazz):
        self.func_class = clazz

    def get(self, func_name):
        return getattr(self.func_class, func_name)


f = Function()
f1 = getattr(f, "get_cookie")
se = getattr(f, "security")

# 第一次请求
s = Step()
s.case_id = 1
s.url = "http://192.168.1.16/DBshop/user/login"
s.method = "get"
s.need_analysis = False
client = Client()
response = client.do(s.request())

s2 = Step()
s2.case_id = 2
s2.header = {
    "Cookie": f1(response.get_header_by("Set-Cookie"))
}
s2.url = "http://192.168.1.16/DBshop/user/login"
s2.method = "POST"
s2.body_param = {
    "user_name": "admin123",
    "user_password": "123456",
    "login_security": se(response.text, "login_security"),
    "http_referer": "/DBShop"
}
s2.need_analysis = False
response = client.do(s2.request())
print("admin123" in response.text)
