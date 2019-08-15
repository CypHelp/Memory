# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: string.py
@time: 2019/8/12 0012 11:29
"""


class String:

    @classmethod
    def first_word_upper(cls,key):
        first_char=key[:1]
        return first_char.upper()+key[1:]
