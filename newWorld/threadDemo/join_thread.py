# encoding: utf-8

"""
@author: yp
@software: PyCharm
@file: join_thread.py
@time: 2019/7/26 0026 16:34
"""
import threading
import time


def action(max):
    for i in range(max):
        print(threading.current_thread().getName() + " " + str(i))


threading.Thread(target=action, args=(30,), name="新线程").start()
for i in range(30):
    if i == 10:
        jt = threading.Thread(target=action, args=(30,), name="被join的线程")
        jt.start()
        # time.sleep()
        jt.join()
    print(threading.current_thread().name + " " + str(i))