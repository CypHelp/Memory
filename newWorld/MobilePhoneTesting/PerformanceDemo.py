# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: PerformanceDemo.py
@time: 2019/8/8 0008 09:45
"""


from time import sleep
from appium import webdriver
from appium.webdriver.common.touch_action import TouchAction


def run():
    # 招商模块
    sleep(6)
    driver.find_elements_by_id('com.chinat2t32275yuneb.templte:id/header_title')[3].click()
    sleep(6)
    # 按时间顺序查看
    el1 = driver.find_element_by_xpath(
        "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView")
    el1.click()
    sleep(6)
    operation()

    # 最新招商->区域招商
    driver.find_element_by_id('com.chinat2t32275yuneb.templte:id/tv_shaixuan').click()
    driver.find_elements_by_id('com.chinat2t32275yuneb.templte:id/tv_item')[1].click()
    sleep(3)
    operation()

    # --》代理信息
    driver.find_element_by_id('com.chinat2t32275yuneb.templte:id/tv_shaixuan').click()
    driver.find_elements_by_id('com.chinat2t32275yuneb.templte:id/tv_item')[2].click()
    sleep(3)
    operation()

    # --》招商信息
    driver.find_element_by_id('com.chinat2t32275yuneb.templte:id/tv_shaixuan').click()
    driver.find_elements_by_id('com.chinat2t32275yuneb.templte:id/tv_item')[3].click()
    sleep(3)
    operation()

    # ->招商咨询
    driver.find_element_by_id('com.chinat2t32275yuneb.templte:id/tv_shaixuan').click()
    driver.find_elements_by_id('com.chinat2t32275yuneb.templte:id/tv_item')[4].click()
    sleep(3)
    operation()

    # ->合作信息
    driver.find_element_by_id('com.chinat2t32275yuneb.templte:id/tv_shaixuan').click()
    driver.find_elements_by_id('com.chinat2t32275yuneb.templte:id/tv_item')[5].click()
    sleep(3)
    operation()

    # ->加盟宝典
    driver.find_element_by_id('com.chinat2t32275yuneb.templte:id/tv_shaixuan').click()
    driver.find_elements_by_id('com.chinat2t32275yuneb.templte:id/tv_item')[6].click()
    sleep(3)
    operation()

    # ->品牌顾问
    driver.find_element_by_id('com.chinat2t32275yuneb.templte:id/tv_shaixuan').click()
    driver.find_elements_by_id('com.chinat2t32275yuneb.templte:id/tv_item')[7].click()
    sleep(3)
    operation()

def operation():

    TouchAction(driver).tap(x=290, y=1224).perform()
    #分享
    el1 = driver.find_element_by_xpath(
        "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout[2]/android.widget.LinearLayout[2]/android.widget.ImageView")
    el1.click()

    sleep(7)
    driver.find_element_by_class_name('android.widget.Button').click()

    sleep(2)
    driver.find_element_by_id("com.chinat2t32275yuneb.templte:id/back").click()

if __name__ == '__main__':
    server = "http://localhost:4723/wd/hub"

    setting = {
        "deviceName": "127.0.0.1:62001",
        "platformVersion": "5.1.1",
        "appPackage": "com.chinat2t32275yuneb.templte",
        "platformName": "Android",
        "noReset": "true",
        "appActivity": "com.chinat2t.tp005.activity.SplashActivity",
    }

    driver = webdriver.Remote(command_executor=server, desired_capabilities=setting)

    run()
    driver.quit()



