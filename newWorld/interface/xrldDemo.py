# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: xrldDemo.py
@time: 2019/7/30 0030 10:14
"""

import xlrd

book = xlrd.open_workbook("..\zixun\example\case.xlsx")
# 查看表格的张数
# print(book.sheet_names())

sheet = book.sheet_by_name(book.sheet_names()[0])
# 列数
for i in range(sheet.ncols):
    print(sheet.cell_value(1, i))
