# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: exercise.py
@time: 2019/8/23 0023 20:30
"""

"""
01  12 11  10
02  13 16  09           -->   列表 嵌套  列表 
03  14 15  08           
04  05 06  07

*** 找到 拐弯的点
"""

# 1.创建二维列表
SIZE = 4
array = [[0] * SIZE]
# print(array)
for i in range(SIZE - 1):
    array += [[0] * 4]
print(array)

# 2. 更改方向   0->下  1 ->左  2 ->上  3->右
orient = 0
col = 0  # -->列
row = 0  # -->行
for i in range(1, SIZE * SIZE+1):
    array[row][col] = i
    if row + col == SIZE-1:
        if j>K:
            orient = 1
        else:
