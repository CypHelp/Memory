import unittest

import ddt
import requests

from AutoTestPlatform.common.datastruce import ResponseTable
# 响应上下文
from AutoTestPlatform.http.client import Client
from AutoTestPlatform.tools.functools import FunctionTools
from dbshop.tools import too


@ddt.ddt
class LoginTest(unittest.TestCase):

    @classmethod
    def setUpClass(cls) -> None:
        cls.table = ResponseTable()
        cls.client = Client()
        cls.tools = FunctionTools()
        cls.tools.register_function(too.security)

    def test_get_cookie(self):
        p = requests.PreparedRequest()
        p.prepare(
            url="http://192.168.1.16/DBshop/user/login",
            method="get"
        )
        LoginTest.table.set("get_cookie", LoginTest.client.do(p))

    @ddt.data(["admin123", "123456"], ["adad", "123123"])
    @ddt.unpack
    def test_login(self, username, password):
        resp = LoginTest.table.get("get_cookie")
        send_data = {
            "user_name": username,
            "user_password": password,
            "login_security": resp.filter(LoginTest.tools.get("security")),
            "http_referer": "/DBshop"
        }
        p = requests.PreparedRequest()
        p.prepare(
            url="http://192.168.1.16/DBshop/user/login",
            method="post",
            headers={
                "Cookie": resp.get_header_by("set-cookie")
            },
            data=send_data
        )
        response = LoginTest.client.do(p)
        self.assertTrue(username in response.text)


if __name__ == '__main__':
    unittest.main()
