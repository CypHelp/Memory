# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: movieDemo.py
@time: 2019/8/19 0019 20:24
"""
import ffmpeg
import requests

"""
流媒体：
    是指将一连串的媒体数据压缩后，经过网上分段发送数据，在网上即时传输影音以供观赏的一种技术与过程，
    此技术使得数据包得以像流水一样发送；如果不使用此技术，就必须在使用前下载整个媒体文件。
    
    流式传输的实现需要缓存。    .ts文件
    记录顺序 .m3u8
    
   1. 解码--》https://jx.618g.com/?url=
    http://www.iqiyi.com/v 19rr53f8m0.html
    https://y.qq.com/x/coner/1v1d6tnz4ugk7kg.html
    
    2.下载：：ffmpeg -i '网址' -vcodec copy -acodec copy xxx.mp4
    
    
"""



# 1.确定url地址

url='https://jx.618g.com/?url=https://v.qq.com/x/cover/vbb35hm6m6da1wc/i0031i4gwrb.html'

# 2.请求

res=requests.get(url).text
print(res)


# /m3u8.php?url=https://letv.com-t-letv.com/20190815/3978_e1fbd3b1/index.m3u8
# ffmpeg -i 'https://v.qq.com/x/cover/vbb35hm6m6da1wc/i0031i4gwrb.html' -vcodec copy -acodec copy xxx.mp4

# 3.筛选数据
# 4.保存


