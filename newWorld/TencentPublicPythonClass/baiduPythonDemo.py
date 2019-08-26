# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: baiduPythonDemo.py
@time: 2019/8/12 0012 20:42

百度 爬取图片
"""

import re
import requests

# 1.确定url地址
string_name=input('大佬，请讲：')

# url='http://image.baidu.com/search/flip?tn=baiduimage&word=%E6%B0%B4%E6%9E%9C'
# url='http://image.baidu.com/search/flip?tn=baiduimage&word=%s'%string_name
url=f'http://image.baidu.com/search/flip?tn=baiduimage&word={string_name}'


#2.请求
response=requests.get(url).text
# print(response)-->objURL

#3. 筛选数据  xpath re正则
image_urls=re.findall('"objURL":"(.*?)",',response)
# print(image_urls)

for image_url in image_urls:
    image_name=image_url.split('/')[-1]
    # print(image_name)

    #查找没有后缀的图片名字
    image_end=re.search('(.jpg|.jpeg|.png|.gif|.ico|.tif)$',image_name)
    print(image_end)

    if image_end == None:
        image_name=image_name+'.jpg'

    image=requests.get(image_url).content

    # 4.保存
    # http://l.b2b168.com/2017/04/07/09/201704070942573737854.jpg
    with open('./images/%s'%image_name,'wb') as file:
        file.write(image)