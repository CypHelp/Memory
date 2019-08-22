# coding: utf-8

class Item:
    def __init__(self, name, price):
        self.name = name
        self.price = price


# 创建一个Item对象，将之赋给im变量
im = Item('鼠标', 29.8)
# 打印im所引用的Item对象
print(im)  # <__main__.Item object at 0x017E1A10>

#   类名+object at +内存地址
# 不能真正实现‘自我描述’--》重写__repr__()方法 --》repr_test.py
im_str = im.__repr__() + ""
print(im_str)
