# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: baiduMusicPythonDemo.py
@time: 2019/8/13 0013 20:05
"""

# # 1.确定url
import json

from lxml import etree

import requests
#
# url='http://audio01.dmhmusic.com/71_53_T10041174955_128_4_1_0_sdk-cpm/0208/M00/31/9B/ChR461pkwKqAeWwiADVNtf568VY426.mp3?xcode=0c69fc1d0df4a2514eadd337fb3a2f63a8cfdc3'
#
# # 2.请求
#
# music=requests.get(url).content
#
#
#
#
# with open('./music/jj.mp3','wb') as file:
#     file.write(music)


url='http://music.taihe.com/top/dayhot'
base_url='http://musicapi.taihe.com/v1/restserver/ting?method=baidu.ting.song.playAAC&songid='

# url1='http://audio01.dmhmusic.com'
response = requests.get(url).content

dom=etree.HTML(response)

song_ids=dom.xpath('//a[contains(@href,"/song/")]/@href')[3:]
song_name=dom.xpath('//a[contains(@href,"/song")]/@title')
# print(son_ids)
print(song_name)
index=0
for song_id in song_ids:
    # print(song_id)
    song_id_url=song_id.split('/')[-1]
    # print(song_id_url)

    song_url=base_url+song_id_url
    print(song_url)
    song_url_string = requests.get(song_url).text
    print(song_url_string)
    dict_url=json.loads(song_url_string)
    print(dict_url)
    music_url = dict_url['bitrate']['show_link']
    print(music_url)
    music_mp3=requests.get(music_url).content
    # 4.保存
    with open('./music/%d.mp3'%song_name[index],'wb') as file:
        file.write(music_mp3)