# coding: utf-8

# 同时对元组使用加法、乘法
order_endings = ('st', 'nd', 'rd') \
                + ('th',) * 17 + ('st', 'nd', 'rd') \
                + ('th',) * 7 + ('st',)
# 将会看到st、nd、rd、17个th、st、nd、rd、7个th、st
print(order_endings)
day = input("输入日期(1-31)：")
# 将字符串转成整数
day_int = int(day)
print(day + order_endings[day_int - 1])
