# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: baiduMusicPythonDemo.py
@time: 2019/8/13 0013 09:18
"""
import os
from time import sleep

import psutil
import xlwt
from AutoTestPlatform.app.adb import AndroidDebugBridge



# 创建一个工作簿
workbook=xlwt.Workbook(encoding='utf-8')
worksheet=workbook.add_sheet('手机测试')

"""
    1.#连接本地设备
    #获取设备
"""
bridge = AndroidDebugBridge()
bridge.get_connect_devices("127.0.0.1:62001")
bridge.get_devices()
package='com.chinat2t32275yuneb.templte'
activity='com.chinat2t.tp005.activity.SplashActivity'
print("设备：{0},android版本：{1},屏幕大小：{2}".format(bridge.device_model(),bridge.device_android_version(),bridge.device_size()))
worksheet.write(1,0,label="设备：{0},android版本：{1},屏幕大小：{2}".format(bridge.device_model(),bridge.device_android_version(),bridge.device_size()))
workbook.save('test.xls')


"""
# 2.启动时间 ①冷启动 ②热启动 ③查看包名以及avtivaty ④测试方法 记录下每次启动用时 重复N次记住下每次的取值并计算出平均值

"""
count = 0
time = 0
while count < 3:
    # 启动
    bridge.start_app(package, activity)
    # print(androiddebugbridge.get_app_start_time('com.tencent.mm', '.app.WeChatSplashActivity', time='WaitTime'))
    waitime = bridge.get_app_start_time(package, activity, time='WaitTime')
    time += waitime
    count += 1
    sleep(3)
    bridge.stop_app(package)
time /= 3
print('冷启动时间{}'.format(time)) #冷启动时间1422.3333333333333
#3.CPU使用率
print('CPU使用率冷启动{}'.format(bridge.get_cpu_info(package))) #86

worksheet.write(2,0,label='冷启动时间{}'.format(time))
worksheet.write(2,1,label='CPU使用率冷启动{}'.format(bridge.get_cpu_info(package)))
workbook.save('test.xls')



count = 0
time = 0
while count < 3:
    bridge.start_app(package, activity)
    # print(androiddebugbridge.get_app_start_time('com.tencent.mm', '.app.WeChatSplashActivity', time='WaitTime'))
    waitime = bridge.get_app_start_time(package, activity, time='WaitTime')
    time += waitime
    count += 1
    bridge.return_desktop()

time /= 3
# bridge.start_app(package, activity)
# sleep(3)
print('热启动时间{}'.format(time)) #热启动时间503.6666666666667
#3.CPU使用率
print('CPU使用率热启动')
print(bridge.get_cpu_info(package)) #8.6

worksheet.write(3,0,label='热启动时间{}'.format(time))
worksheet.write(3,1,label='CPU使用率热启动{}'.format(bridge.get_cpu_info(package)))
workbook.save('test.xls')


"""
#3.网络流量消耗 ①获取进程id ②查看流量消耗 adb shell cat /proc/pid/net/dev ③收到流量Receive ④发送流量Transmit 

⑤测试方法记录测试前的总流量消耗量和收发消耗量 执行程序后在记录总消耗量和收发消耗量 计算出两次的差值

"""

# pid=bridge.get_pid_process_name(package)
# print("获取进程id:{}".format(pid)) #[('11257', ' com.chinat2t32275yuneb.templte:bdservice_v1'), ('12322', ' com.chinat2t32275yuneb.templte')]
pid=bridge.get_pid_process_name(package)[1][0]
pid=int(pid)
print("获取进程id:{}".format(pid))  #12322
fir_num=bridge.get_network_flow(package,1,'lo')[0]
print("查看流量消耗:{}".format(fir_num)) #15395723

worksheet.write(4,0,label="获取进程id:{}".format(pid))
worksheet.write(4,1,label="查看流量消耗:{}".format(fir_num))
workbook.save('test.xls')



"""
#4.电量测试  ①获取电量 ② 切换到非充电状态③ 测试前记录当前电量 功能执行完毕后记录当前电量 记录两次差值

"""
current_battery=bridge.get_device_battery()
print("首次电量:{}".format(current_battery)) #71
while count<3:
    bridge.start_app(package, activity)
    bridge.stop_app(package)
    count+=1
lastest_battery=bridge.get_device_battery()
print("结束电量:{}".format(lastest_battery)) #72
difer_battery=current_battery-lastest_battery
print("两次差值为{}".format(difer_battery)) #-1

worksheet.write(5,0,label="首次电量:{}".format(current_battery))
worksheet.write(5,1,label="结束电量:{}".format(lastest_battery))
worksheet.write(5,2,label="两次差值为{}".format(difer_battery))
workbook.save('test.xls')

"""
5.内存①获取内存 ②内存缩写含义 VSS RSS 
"""
#可以手动输入adb shell top
#代码读不出来？？
# print(bridge.execute_script('adb shell top'))
# with open('./text/texts','w+') as file:
#     file.write(bridge.execute_script('adb shell top'))
# file.close()

# mem = psutil.virtual_memory()
# print(mem)


"""
6.FPS&过度渲染
"""

content = os.popen("adb -s 127.0.0.1:62001 shell dumpsys gfxinfo {}".format(package))
#读取行数
data = content.readlines()
print(data)
start = 0
end = 0
i = 0
#为获得帧数据，先找具有代表性的开始行与结束行的字段“Draw"、"View hierarchy:"
for line in data:
    if "Draw" in line:
        start = i
        print("start:",start)

    if "View hierarchy:" in line:
        end = i
        print("end",end)
    i = i+1
# 精确定位帧数据的开始行与结束行
result = data[start+1:end-1]
print(result)

worksheet.write(6,0,label="帧数据:{}".format(result))
workbook.save('test.xls')

sleep(5)
bridge.stop_app(package)

