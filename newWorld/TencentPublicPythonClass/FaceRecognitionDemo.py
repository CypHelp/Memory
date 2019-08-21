# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: FaceRecognitionDemo.py
@time: 2019/8/7 0007 20:19
"""

"""
1.识别图片上面的人脸
2.使用摄像头动态捕获识别人脸

python-》三方框架 opencv

思路：
1导入【opencv】
2.读取图片
3.图片灰度处理
4.检查人脸
5.标记人脸
6.显示图片
7.暂停窗口
8.销毁窗口
"""

# 打开摄像头并灰度化显示
import cv2

capture = cv2.VideoCapture(0)

while(True):
    # 获取一帧
    ret, frame = capture.read()
    # 将这帧转换为灰度图
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    cv2.imshow('frame', gray)
    if cv2.waitKey(1) == ord('q'):
        break