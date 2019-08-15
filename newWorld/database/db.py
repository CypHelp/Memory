# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: db.py
@time: 2019/7/25 0025 13:32
"""

import sqlite3

connect = sqlite3.connect("case")
cursor = connect.cursor()
cursor.execute("""
insert into teacher
    values (2,'mark');
""")

connect.commit()
cursor.close()
connect.close()

