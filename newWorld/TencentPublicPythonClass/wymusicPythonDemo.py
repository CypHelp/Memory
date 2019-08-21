# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: wymusicPythonDemo.py
@time: 2019/8/14 0014 20:16
"""



import requests
from lxml import etree
# # 1.确定url地址
# url='https://m701.music.126.net/20190814205257/c596e7c4c0e98c82ece25fe6822d8f68/jdyyaac/015f/0158/0652/659801352362d8ffb66c18ec6fee31ef.m4a'
#
# # 2.请求
# music=requests.get(url).content
#
# # 3.筛选数据
#
# # 4. 保存
# with open('./music/1.m4a','wb') as file:
#     file.write(music)


"""
 1.确定url地址
"""
url='https://music.163.com/playlist?id=2341985523'

#外链地址？？ http://music.163.com/song/media/outer/url?id=ID数字.mp3
base_url='https://music.163.com/song/media/outer/url?id='


"""
    2.请求
"""
#代理
header={
    'User-agent':''
}
#拉取这个页面的所有内容
result=requests.get(url,headers=header).text
# print(result)


"""
# 3.筛选数据 
    etree:
    ①解析HTML：使用 etree.HTML(text) 将字符串格式的 html 片段解析成 html 文档
    ②读取xml文件
    ③etree和XPath 配合使用
"""
dom=etree.HTML(result)
ids=dom.xpath('//a[contains(@href,"/song")]/@href')
# print(ids)
for songid in ids:
    # print(songid)
    #strip() 方法用于移除字符串头尾指定的字符（默认为空格或换行符）或字符序列。
    count_id=songid.strip('/song?id=')
    print(count_id)
    if not "$" in count_id:
        # print("进来")
        # print(count_id)
        music_url = base_url + "%s"%count_id + ".mp3"
        # print(music_url)
        music_mp3=requests.get(music_url).content
    # 4. 保存
        with open('./music/%s.mp3'%count_id,'wb') as file:
            file.write(music_mp3)