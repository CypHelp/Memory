# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: templateSeleniumDemo.py
@time: 2019/7/31 0031 16:36
"""


import unittest

class Test(unittest.TestCase):

    @classmethod
    def setUpClass(cls) -> None:
        pass

    def setUp(self) -> None:
        pass


    def test_xxx(self)->None:
        pass

    def tearDown(self) -> None:
        pass

    @classmethod
    def tearDownClass(cls) -> None:
        pass

if __name__ == '__main__':
    unittest.main()