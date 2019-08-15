# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: bilibiliLogin.py
@time: 2019/8/2 0002 10:36
"""
from AutoTestPlatform.web.WebDriver import Driver

driver=Driver()
driver.get('https://passport.bilibili.com/login')
driver.close()
