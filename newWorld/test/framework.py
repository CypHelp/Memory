# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: framework.py
@time: 2019/7/18 0018 14:12
"""
import unittest

from pip._vendor.distlib.compat import raw_input


def col(a, b, x):
    if a > 1 and b == 0:
        x = x / a
    if a == 2 or x > 1:
        x = x + 1
    return x


def add(x, y):
    return x + y


def mult(a, b):
    return a * b


class BrowserTest(unittest.TestCase):
    # 测试环境初始化
    # 在每条测试用例测试之前都会执行
    def setUp(self) -> None:
        print("初始化测试环境")

    # 测试用例函数名必须以test_开头
    # @unittest.skip("暂不做测试")
    def test_col(self):
        col(a=2, b=0, x=3)

    def test_mult(self):
        res = mult(1, 5)
        self.assertEqual(5, res)

    def test_add(self):
        res = add(1, 2)
        # 断言
        # if res != 3:
        #     raise AssertionError("{0} != {1}".format(3, res))

        self.assertEqual(3, res)

    # 测试环境还原
    # 在每条测试用例测试之后都会执行
    def tearDown(self) -> None:
        print("还原测试环境")


if __name__ == '__main__':
    unittest.main()
