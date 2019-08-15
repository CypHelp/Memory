# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: start_dead.py
@time: 2019/7/26 0026 09:28
"""

import threading


def action(max):
    for i in range(max):
        print(threading.current_thread().getName() + " " + str(i))


sd = threading.Thread(target=action, args=(100,))
for i in range(200):
    print(threading.current_thread().getName() + " " + str(i))
    if i==20:
        sd.start()
        print(sd.is_alive())
    if i>20 and not sd.is_alive():
        sd.start()