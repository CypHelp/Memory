# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: mailDemo.py
@time: 2019/7/31 0031 16:45
"""
from time import sleep

from selenium import webdriver

import unittest

path = "D:\Program Files (x86)\Google\Chrome\Application\chromedriver"
mailAccount = "1377982034@qq.com"
mailPwd = "cyp741741wan"
accessMailAccount = "839426340@qq.com"
mailItem = "测试"
mailContent = "hello,我在进行一项测试"

class MailTest(unittest.TestCase):
    driver = None

    @classmethod
    def setUpClass(cls) -> None:
        cls.driver = webdriver.Chrome(executable_path=path)

    def test_mail_send(self):
        MailTest.driver.get('https://www.baidu.com/')
        # -------------------查询---------------------------------

        MailTest.driver.find_element_by_id("kw").send_keys("qq邮箱")
        MailTest.driver.find_element_by_id("su").click()
        sleep(1)
        MailTest.driver.find_element_by_xpath('//*[@id="1"]/h3/a[1]').click()

        # 切换窗口
        MailTest.driver.switch_to.window(MailTest.driver.window_handles[1])
        sleep(3)
        frame = MailTest.driver.find_element_by_id('login_frame')
        MailTest.driver.switch_to.frame(frame)

        # --------------------输入信息-----------------------------

        MailTest.driver.find_element_by_id("u").send_keys(mailAccount)
        MailTest.driver.find_element_by_id("p").send_keys(mailPwd)
        MailTest.driver.find_element_by_id("login_button").click()

        # --------------------发送邮件--------------------------
        sleep(2)
        MailTest.driver.find_element_by_id("composebtn").click()

        frame = MailTest.driver.find_element_by_id('mainFrame')
        MailTest.driver.switch_to.frame(frame)

        sleep(2)
        MailTest.driver.find_element_by_xpath('//*[@id="toAreaCtrl"]/div[2]/input').send_keys(accessMailAccount)
        MailTest.driver.find_element_by_xpath('//*[@id="subject"]').send_keys(mailItem)

        frame = MailTest.driver.find_element_by_tag_name("iframe")
        MailTest.driver.switch_to.frame(frame)
        sleep(2)
        MailTest.driver.find_element_by_xpath('/html/body').send_keys(mailContent)

        MailTest.driver.switch_to.parent_frame()
        sleep(2)
        MailTest.driver.find_element_by_xpath('//*[@id="toolbar"]/div/a[1]').click()

        # -----------------退出登录-------------------------
        MailTest.driver.switch_to.parent_frame()
        sleep(2)
        MailTest.driver.find_element_by_xpath('//*[@id="SetInfo"]/div[1]/a[3]').click()

    def close(self):
        MailTest.driver.close()


if __name__ == '__main__':

    unittest.main()