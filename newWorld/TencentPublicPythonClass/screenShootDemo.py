# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: screenShootDemo.py
@time: 2019/8/27 0027 14:57

orc
图片识别
"""

# 1.截取图片 ，保存到本地
#  snipaste


# 2.调用三方的sdk，来实现
import time

import keyboard

from PIL import ImageGrab
from aip import AipOcr

# 调用百度的sdk

""" 你的 APPID AK SK """
APP_ID = '17134093'
API_KEY = 'gDGaUOGMRX5cxqFOxgp5SGbm'
SECRET_KEY = '2YOAu5p6MEpq9iWKR3yKRERfxkduWFWN'

client = AipOcr(APP_ID, API_KEY, SECRET_KEY)

""" 读取图片 """


def get_file_content(filePath):
    with open(filePath, 'rb') as fp:
        return fp.read()


while 1:
    # 监听键盘按键
    keyboard.wait(hotkey='f1')
    keyboard.wait(hotkey='ctrl+c')

    time.sleep(0.1)

    # 保存件剪贴板的图片保存到本地
    image = ImageGrab.grabclipboard()
    image.save('./images/shoot.jpg')

    image = get_file_content('./images/shoot.jpg')

    """ 调用通用文字识别（高精度版） """
    text = client.basicAccurate(image)
    # print(text)
    result = text['words_result']

    for info in result:
        print(info['words'])
