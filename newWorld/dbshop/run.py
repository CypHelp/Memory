import unittest

from AutoTestPlatform.report.HTMLTestReportCN import HTMLTestRunner

with open("reprt.html", "wb") as f:
    dis = unittest.defaultTestLoader.discover("..\dbshop", pattern="*_test.py")
    r = HTMLTestRunner(stream=f, title="接口测试", description="DBSHOP接口测试", tester="yp")
    r.run(dis)
