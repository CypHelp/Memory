# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: templateSeleniumDemo.py
@time: 2019/7/31 0031 16:36
"""

import unittest

from AutoTestPlatform.web.WebDriver import Driver

videokey="陈情令"

class Test(unittest.TestCase):
    driver=Driver()


    def test_Login(self) -> None:
        Test.driver.get('https://v.qq.com/')
        Test.driver.find_element_by_id_data('keywords',videokey)
        Test.driver.find_element_by_class_name('btn_inner').click()
        Test.driver.switch_to_window_index(1)

if __name__ == '__main__':
    unittest.main()