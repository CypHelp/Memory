# coding: utf-8

a_tuple = ('crazyit', 20, -1.2)
# 将元组转换成列表
a_list = list(a_tuple)
print(a_list)
# 使用range()函数创建区间（range）对象
a_range = range(1, 5)
print(a_range)  # range(1, 5)
# 将区间转换成列表
b_list = list(a_range)
print(b_list)  # [1, 2, 3, 4]
# 创建区间时还指定步长
c_list = list(range(4, 20, 3))
print(c_list)  # [4, 7, 10, 13, 16, 19]
