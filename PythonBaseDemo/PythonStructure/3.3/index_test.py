# coding: utf-8

a_list = [2, 30, 'a', 'b', 'crazyit', 30]
# 定位元素30的出现位置
print(a_list.index(30))  # 1
# 从索引2处开始、定位元素30的出现位置
print(a_list.index(30, 2))  # 5
# 从索引2处到索引4处之间定位元素30的出现位置，找不到该元素
print(a_list.index(30, 2, 4))  # ValueError
