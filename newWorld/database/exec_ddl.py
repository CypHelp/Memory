# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: exec_ddl.py
@time: 2019/7/25 0025 17:20
"""
# 导入访问SQLite的模块
import sqlite3

# ①、打开或创建数据库
# 也可以使用特殊名：:memory:代表创建内存中的数据库
conn = sqlite3.connect('first.db')

# ②、获取游标
c = conn.cursor()

# # ③、执行DDL语句创建数据表
# c.execute('''create table user_tb(
#    _id integer primary key autoincrement,
#    name text,
#    pass text,
#    gender text)
#    ''')
# # 执行DDL语句创建数据表
# c.execute('''create table order_tb(
#    _id integer primary key autoincrement,
#    item_name text,
#    item_price real,
#     item_number real,
#    user_id inteter,
#     foreign key(user_id) references user_tb(_id) )''')

# # 调用执行insert语句插入数据
# c.execute('insert into user_tb values(null, ?, ?, ?)',
#           ('孙悟空', '123456', 'male'))
# c.execute('insert into order_tb values(null, ?, ?, ?, ?)',
#           ('鼠标', '34.2', '3', 1))
# # 调用executemany()方法把同一条SQL语句执行多次 insert语句，update语句
# # delete语句
# c.executemany('insert into user_tb values(null, ?, ?, ?)',
#               (('sun', '123456', 'male'),
#                ('bai', '123456', 'female'),
#                ('zhu', '123456', 'male'),
#                ('niu', '123456', 'male'),
#                ('tang', '123456', 'male')))
# c.executemany('update user_tb set name=? where _id=?',
#               (('小孙', 2),
#                ('小白', 3),
#                ('小猪', 4),
#                ('小牛', 5),
#                ('小唐', 6)))
# # 通过rowcount获取被修改的记录条数
# print('修改的记录条数：', c.rowcount)
# conn.commit()
# ③、调用执行select语句查询数据
c.execute('select * from user_tb where _id > ?', (2,))
print('查询返回的记录数:', c.rowcount)
# 通过游标的description属性获取列信息
for col in c.description:
    print(col[0], end='\t')
print('\n--------------------------------')
while True:
    # 获取一行记录，每行数据都是一个元组
    # fetchone()，fetchmany(n)， fetchchall()
    row = c.fetchone()
    # 如果抓取的row为None，退出循环
    if not row:
        break
    print(row)
    print(row[1] + '-->' + row[2])
# ④、关闭游标
c.close()
# ⑤、关闭连接
conn.close()
