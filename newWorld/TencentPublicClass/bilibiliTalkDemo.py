# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: bilibiliTalkDemo.py
@time: 2019/8/16 0016 20:37
"""
import requests

url="https://api.live.bilibili.com/msg/send"

header={
"Cookie": "CURRENT_FNVAL=16; buvid3=A0C37E37-1E17-4463-940F-C0DAA8A9EA96110268infoc; stardustvideo=1; LIVE_BUVID=AUTO6815554081282065; sid=c1iwsta7; CURRENT_QUALITY=80; UM_distinctid=16a293b965033d-018a7d3797b44b-3d644601-144000-16a293b9651183; fts=1559897528; rpdid=|(u))|mkJlR~0J'ullm)m~lJm; _uuid=79D23048-FD17-C578-DFA8-FF0B35021BA982018infoc; GIFT_BLOCK_COOKIE=GIFT_BLOCK_COOKIE; _dfcaptcha=245ad107a3af2a27faf8c58fb0c4a85d; Hm_lvt_8a6e55dbd2870f0f5bc9194cddf32a02=1565959938,1565960127,1565960148,1565962598; Hm_lpvt_8a6e55dbd2870f0f5bc9194cddf32a02=1565962605; DedeUserID=106361563; DedeUserID__ckMd5=4bebff50244b7fce; SESSDATA=f17f9215%2C1568554633%2C8d9ed581; bili_jct=0a8f1655f1421b1ef64e69390726e906",
        }

data ={
    'color': '16777215',
    'fontsize': '25',
    'mode': '1',
    'msg': '123',
    'rnd': '1565962634',
    'roomid': '21525533',
    'bubble': '0',
    'csrf_token': '0a8f1655f1421b1ef64e69390726e906',
    'csrf': '0a8f1655f1421b1ef64e69390726e906',

}
response=requests.post(url,headers=header,data=data).text
print(response)
