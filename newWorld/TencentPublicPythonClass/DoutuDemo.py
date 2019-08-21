# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: DoutuDemo.py 爬虫-》图片
@time: 2019/8/6 0006 22:46
"""


#1.确定url地址
import re

import requests

url='https://www.doutula.com/photo/list/?page=2'

#2.请求
rsponse=requests.get(url).text

#3.筛选数据（re）--》正则
image_urls=re.findall('data-original=".*?"',rsponse)

for image_url in image_urls:
    image_name=image_url.split('/')[-1]
    # print(image_name)
    image=requests.get(image_url)
    #4.保存
    with open('./images2/%s'%image_name,'wb') as fp:
        fp.write(image)