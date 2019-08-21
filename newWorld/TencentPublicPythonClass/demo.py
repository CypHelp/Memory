# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: demo.py
@time: 2019/8/20 0020 14:41
"""
import requests
# 广寒宫.mp3
url1='https://m10.music.126.net/20190820150532/9df1aa5a3dabec8dcec3486271db1b37/ymusic/000b/555c/0058/7a5c31c00eb66499de0f72bb67097111.mp3'

res=requests.get(url1).content

with open('./music/广寒宫.mp3','wb') as file:
    file.write(res)

