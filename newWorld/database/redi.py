# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: redi.py
@time: 2019/7/26 0026 11:45
"""

import redis

pool = redis.ConnectionPool(host="192.168.195.131", port=6379, password="123456")
redi = redis.Redis(connection_pool=pool)

redi.set("username", "yp")
print(redi.get("username").decode("utf-8"))

redi.lpush("user2", "value1", "value2")
res = redi.lindex("user2", 0)
print(res.decode("utf-8"))
