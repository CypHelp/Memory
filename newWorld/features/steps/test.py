# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: test.py
@time: 2019/8/1 0001 09:41
"""
from behave import given, when, then


@given("I have {pens}")
def pen(context, pens):
    context.pen = pens


@when("I have {apple}")
def appl(context, apple):
    context.appl = apple


@then("Huh {ap}")
def apple_pen(context, ap):
    context.ap = ap

    assert ap == context.appl + context.pen, "error"
