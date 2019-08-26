# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: lolPhotoDemo.py
@time: 2019/8/20 0020 20:13

爬取lol 英雄的皮肤图片
"""
import json
import re

"""
https://game.gtimg.cn/images/lol/act/img/skin/big1000.jpg
https://game.gtimg.cn/images/lol/act/img/skin/big1001.jpg

分析url，寻找共同的

英雄id？ 遍历？
①//a[contains(@href,"id")]/@href

②ampion.js--》https://lol.qq.com/biz/hero/champion.js


"""

import requests

# 1.确定url地址
# 2.请求
# 3.筛选数据
# 4.保存


url = 'https://lol.qq.com/biz/hero/champion.js'
base_url = 'https://game.gtimg.cn/images/lol/act/img/skin/big'
response = requests.get(url).text
# print(response)

"""
 ①正则表达式中，group（）用来提出分组截获的字符串，（）用来分组
 ②group() 同group（0）就是匹配正则表达式整体结果
 ③group(1) 列出第一个括号匹配部分，group(2) 列出第二个括号匹配部分，group(3) 列出第三个括号匹配部分。
没有匹配成功的，re.search（）返回None
"""
str_id = re.search('"keys":(.+?),"data"', response).group(1)  # -->str
# re.findall()  #--》list
# print(str_id)
# print(type(str_id)) # --><class 'str'>

dict_id = json.loads(str_id)
# print(dict_id)
# print(type(dict_id)) #--><class 'dict'>

for hero_id, name in dict_id.items():
    for i in range(30):
        # print(hero_id,name)
        # %3d--可以指定宽度，不足的左边补空格
        # %-3d--左对齐
        # %03d---一种左边补0 的等宽格式,比如数字12,%03d出来就是: 012
        image_result = base_url + hero_id + "%03d" % i + '.jpg'
        # 过滤掉无用的url
        if requests.get(image_result).status_code == 200:
            print(image_result)

            image = requests.get(image_result).content
            # print(image)
            with open("./images/%s%d.jpg" % (name, i), 'wb') as file:
                file.write(image)
