# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: clent.py
@time: 2019/7/26 0026 14:35
"""

import socket

sock = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
sock.connect(("192.168.1.34", 1046))
# sock.send("你好".encode("utf-8"))
while 1:
    while input():
        da = input("i say :")
        sock.send(da.encode("utf-8"))

    while input("i say :"):
        data = sock.recv(1024)
        print("he say:", data.decode("utf-8"))

sock.close()
