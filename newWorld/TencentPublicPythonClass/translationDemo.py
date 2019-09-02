# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: translationDemo.py
@time: 2019/8/22 0022 20:19

有待改进。。。  --> 输出的信息无法与函数的结果进行绑定？？
"""

# 1.找接口
import json
import tkinter
import requests


def translation():
    url = 'http://fanyi.youdao.com/translate?smartresult=dict&smartresult=rule'
    info = input("您想翻译的单词：")
    # 时间戳？？ sign
    data = {
        'i': info,
        'doctype': 'json'
    }

    # 用户代理，反爬
    header_ua = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36'
    }

    # 2.请求
    res_string = requests.post(url, data=data).content.decode()
    # print(res_string)

    json_data = json.loads(res_string)

    result_string = json_data["translateResult"][0][0]["tgt"]
    print(result_string)
    # info.insert(result_string)


if __name__ == '__main__':
    translation()

"""
# 3.制作界面
# ① 创建对象
# ② 开启运行循环
# ③ 设置窗口的大小
# ④ 窗口内容
# ⑤ 布局
# ⑥ 设置文本框
"""
# window = tkinter.Tk()
#
# window.geometry('400x400+800+20')
# window.title('中 --》英')
#
# tkinter.Label(text='内容:    ', font=('GB2312', 22), background="#fc0").grid(row=0, column=0)
# data = tkinter.Entry(font=('GB2312', 22), background="red").grid(row=0, column=1, )
#
# tkinter.Label(text='输出:    ', font=('GB2312', 22), background="#fc0").grid(row=1, column=0)
# res = tkinter.StringVar()
# info = tkinter.Entry(font=('GB2312', 22), background="red", textvariable=res).grid(row=1, column=1)
#
# tkinter.Button(text='翻译', font=('GB2312', 19), command=translation).grid(row=2, column=0)
# tkinter.Button(text='退出', font=('GB2312', 19), command=window.quit).grid(row=2, column=1)
#
# window.mainloop()
