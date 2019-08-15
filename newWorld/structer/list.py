# 链表


class Node:

    def __init__(self, data):
        # data节点存放的元素
        self.data = data
        # 指向下一个元素的位置
        self.next = None


class LinkedList:

    def __init__(self):
        self.head = Node(None)
        self.size = 0

    def add(self, data):
        node = Node(data)

        current = self.head.next
        if current is None:
            self.head.next = node
            return

        while current and current.next:
            current = current.next

        current.next = node

    # 递归
    def __add(self, node: Node, data) -> Node:
        if node is None:
            self.size += 1
            return Node(data)
        # 1-> 2-> 3-> 4-> 16
        node.next = self.__add(node.next, data)
        return node

    def append(self, data):
        self.head.next = self.__add(self.head.next, data)

    def print(self):
        current = self.head.next

        while current:
            print(current.data)
            current = current.next

    def insert(self, data, index):
        if index > self.size - 1 or index < 0:
            raise IndexError("超出范围")

        node = Node(data)
        current_index = 0
        previous_node = self.head.next

        while current_index < index - 1:
            previous_node = previous_node.next
            current_index += 1
        node.next = previous_node.next
        previous_node.next = node

        self.size += 1

    def remove(self, index):
        if index > self.size - 1 or index < 0:
            raise IndexError("超出范围")

        previous_node = self.head.next
        current_index = 0
        while current_index < index - 1:
            previous_node = previous_node.next
            current_index += 1

        del_node = previous_node.next
        previous_node.next = del_node.next
        del_node.next = None

    def find(self, data):
        index = 0
        current = self.head.next
        # 1->2->3
        while current:

            if current.data == data:
                return index
            current = current.next
            index += 1

        return -1

    def delete(self, data):
        if data is None:
            return

        index = self.find(data)
        if index == -1:
            return
        self.remove(index)


l = LinkedList()
l.append(5)
l.append(7)
l.append(8)

l.insert(66, 1)
l.insert(100, 2)
l.insert(101, 3)

l.print()
print("zhao{}".format(l.find(5)))
