# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: templateSeleniumDemo.py
@time: 2019/7/31 0031 16:36
"""

import unittest
from time import sleep

import ddt

from AutoTestPlatform.report.HTMLTestReportCN import HTMLTestRunner
from AutoTestPlatform.web.WebDriver import Driver

path='https://renren.d2admin.fairyever.com/#/login'


@ddt.ddt
class Test(unittest.TestCase):
    driver = Driver()


    @ddt.data(("",""),("%%%&&*","123"))
    @ddt.unpack
    def test_login(self,username,password) -> None:
        Test.driver.get('http://192.168.1.22:8080/#/login')
        Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[3]/div/div[2]/div/div/div/form/div[1]/div/div/input').send_keys(username)
        Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[3]/div/div[2]/div/div/div/form/div[2]/div/div/input').send_keys(password)
        sleep(6)
        Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[3]/div/div[2]/div/div/div/form/button').click()

    @ddt.unpack
    def test_user_manage(self) -> None:
        try:
            Test.driver.get('http://192.168.1.22:8080/#/login')
            sleep(6)
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[3]/div/div[2]/div/div/div/form/button').click()
            sleep(2)
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[1]/div/ul/li[1]/div').click()
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[1]/div/ul/li[1]/ul/li[1]/span').click()
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div[1]/form/div[3]/div/button/span').click()
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div[1]/div[2]/div/div[2]/form/div[1]/div/div[1]/input').send_keys('yp')
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div[1]/div[2]/div/div[2]/form/div[2]/div/div/input').click()
            sleep(2)
            Test.driver.find_element_by_xpath('//div//span [@class="el-tree-node__label"]').click()
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div[1]/div[2]/div/div[2]/form/div[3]/div/div[1]/input').send_keys('123456')
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div[1]/div[2]/div/div[2]/form/div[4]/div/div[1]/input').send_keys('123456')
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div[1]/div[2]/div/div[2]/form/div[5]/div/div[1]/input').send_keys('oo')
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div[1]/div[2]/div/div[2]/form/div[7]/div/div[1]/input').send_keys('1234@qq.com')
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div[1]/div[2]/div/div[2]/form/div[8]/div/div[1]/input').send_keys('17756323695')
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div[1]/div[2]/div/div[2]/form/div[9]/div/div/div[2]/input').click()
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div[1]/div[2]/div/div[3]/button[2]').click()
            # Test.driver.find_element_by_xpath('//div//i [@class="el-dialog__close el-icon el-icon-close"]').click()
        except:
            pass

    # @ddt.unpack
    def test_department_manage(self):
        try:
            Test.driver.get('http://192.168.1.22:8080/#/login')
            sleep(6)
            Test.drive.find_element_by_xpath('//*[@id="app"]/div/div[3]/div/div[2]/div/div/div/form/button').click()
            sleep(2)
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[1]/div/ul/li[1]/div').click()
            #部门管理
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[1]/div/ul/li[1]/ul/li[2]/span').click()
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div/form/div/div/button/span').click()
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div/div[2]/div/div[2]/form/div[1]/div/div/input').send_keys('haha')
            Test.driver.find_element_by_xpath('//div//i [@class="el-icon-arrow-up"]').click()
            Test.driver.find_element_by_xpath('//div//button [@class="el-button el-button--primary"]').click()
            # Test.driver.find_element_by_xpath('//div//i [@class="el-dialog__close el-icon el-icon-close"]').click()
        except:
            pass

    #
    def test_role_manage(self):
        try:
            # Test.driver.get('http://192.168.1.22:8080/#/login')
            Test.driver.get(path)
            sleep(7)
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[3]/div/div[2]/div/div/div/form/button').click()
            sleep(2)
            Test.driver.find_element_by_xpath('//div//div [@class="el-submenu__title"]').click()
            #角色管理
            Test.driver.find_element_by_xpath('//div//li[3] [@class="el-menu-item"]').click()
            Test.driver.find_element_by_xpath('//div//button [@class="el-button el-button--primary el-button--mini"]').click()
            Test.driver.find_element_by_xpath('//div//div[1] [@class="el-input"]/input').send_keys("dd")
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div[1]/div[2]/div/div[2]/form/div[2]/div/div/input').send_keys("无")
            Test.driver.find_element_by_xpath('//*[@id="app"]/div/div[2]/div[2]/div[2]/div/div[2]/div/div/div[1]/div[2]/div/div[2]/form/div[3]/div[1]/div/div/div/div[1]/div/label/span/span').click()
            Test.driver.find_element_by_xpath('//div//button [@class="el-button el-button--primary"]').click()
        except:
            pass




@classmethod
def tearDownClass(cls) -> None:
    Test.driver.close()


if __name__ == '__main__':
    unittest.main()
