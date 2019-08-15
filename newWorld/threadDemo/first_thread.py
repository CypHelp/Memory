# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: first_thread.py
@time: 2019/7/26 0026 09:04
"""
import threading


def action(max):
    for i in range(max):
        print(threading.current_thread().getName() + " " + str(i))


for i in range(100):
    print(threading.current_thread().getName() + " " + str(i))
    if i == 20:
        t1 = threading.Thread(target=action, args=(100,))
        t1.start()
        t2 = threading.Thread(target=action, args=(100,))
        t2.start()
print('主程序执行完成')
