# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: run.py
@time: 2019/8/5 0005 14:13
"""
import unittest

from AutoTestPlatform.report.HTMLTestReportCN import HTMLTestRunner

with open("report.html", "wb") as f:
    dis = unittest.defaultTestLoader.discover("E:/pythonProject/newWorld/seleniumDemo/D2AdminTestDemo", pattern="*_test.py")
    r = HTMLTestRunner(stream=f, title="测试", description="登录测试", tester="yp")
    r.run(dis)