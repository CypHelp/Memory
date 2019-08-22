# coding: utf-8

stack = []
# 向栈中“入栈”3个元素
stack.append("fkit")
stack.append("crazyit")
stack.append("Charlie")
print(stack) # ['fkit', 'crazyit', 'Charlie']
# 第一次出栈：最后入栈的元素被移出栈
print(stack.pop())
print(stack) # ['fkit', 'crazyit']
# 再次出栈
print(stack.pop())
print(stack) # ['fkit']
