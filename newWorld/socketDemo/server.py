# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: server.py
@time: 2019/7/26 0026 14:31
"""

import socket

sock = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)

sock.bind(("192.168.1.90", 6563))
sock.listen(2)
conn, addr = sock.accept()
print("新的请求来自{}".format(addr))
data = conn.recv(1024)
data = data.decode("utf-8")
print(data)

conn.send("Hello".encode("utf-8"))
conn.close()
