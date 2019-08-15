from tools import Validator


class Node:

    def __init__(self, data, previous=None, nexts=None):
        self.previous = previous
        self.data = data
        self.next = nexts


class DoubleList:

    def __init__(self):
        self.__head = Node(-1)
        self.__size = 0
        self.__tail = self.__head

    def add(self, element):
        node = self.__head
        while node.next:
            node = node.next

        new_node = Node(element, previous=node)
        node.next = new_node
        self.__tail = new_node
        self.__size += 1
        return self

    def __str__(self):
        node = self.__head.next
        elements = []
        while node:
            elements.append("{}".format(node.data))
            node = node.next
        return "->".join(elements)

    def is_empty(self):
        return self.__size == 0

    def get_last(self):
        return self.__tail.data

    def insert(self, element, index):
        if not Validator.range_check(index, (self.__size, 0)):
            raise IndexError("index out of bounds")
        middle = int(self.__size / 2)
        current_index = 0
        if index > middle:
            current_index = self.__size
            previous_node = self.__tail
            while current_index > index - 1:
                previous_node = previous_node.previous
                current_index -= 1
        else:
            previous_node = self.__head
            while current_index < index - 1:
                previous_node = previous_node.next
                current_index += 1
        new_node = Node(element, previous=previous_node, nexts=previous_node.next)
        previous_node.next = new_node
        if index == self.__size:
            self.__tail = new_node
        self.__size += 1
        return self

    def add_first(self, element):
        return self.insert(element, 0)

    def index(self, element):
        index = 0
        node = self.__head.next
        while node:
            if element == node.data:
                return index
            node = node.next
            index += 1
        return -1

    @property
    def size(self):
        return self.__size

    def __contains__(self, item):
        return self.index(item) != -1

    def remove(self, index):
        if not Validator.range_check(index, (self.__size, 0)):
            raise IndexError("index out of bounds")
        middle = int(self.__size / 2)
        current_index = 0
        if index > middle:
            current_index = self.__size
            previous_node = self.__tail
            while current_index > index - 1:
                previous_node = previous_node.previous
                current_index -= 1
        else:
            previous_node = self.__head
            while current_index < index - 1:
                previous_node = previous_node.next
                current_index += 1

        delete_node = previous_node.next
        previous_node.next = delete_node.next
        delete_node.next.previous = previous_node
        delete_node.previous = None
        delete_node.next = None
        self.__size += 1
        return delete_node.data

    def delete(self, element):
        return self.remove(self.index(element))

    def __len__(self):
        return self.__size


if __name__ == '__main__':
    d = DoubleList()
    d.add(5).add(1).add_first(5).add_first(1)
    print(d)
    d.insert(100, 4)
    print(d)
    d.remove(1)
    print(d)
