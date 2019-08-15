# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: baiduMusicPythonDemo.py
@time: 2019/7/31 0031 10:20
"""


from selenium import webdriver

path="D:\Program Files (x86)\Google\Chrome\Application\chromedriver"

driver = webdriver.Chrome(executable_path=path)
driver.get('https://www.baidu.com/')

driver.get("http://192.168.1.16/DBshop")
driver.find_element_by_link_text("注册").click()

driver.find_element_by_id("user_name").send_keys("haha")
driver.find_element_by_id("user_password").send_keys("123456")
driver.find_element_by_id("user_com_passwd").send_keys("123456")
driver.find_element_by_id("user_email").send_keys("asdfghj@qq.com")
driver.find_element_by_id("agreement").click()
driver.find_element_by_xpath("//button[contains(text(),'提交注册用户')]").click()