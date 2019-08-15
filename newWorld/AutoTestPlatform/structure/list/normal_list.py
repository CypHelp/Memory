from tools import Validator


class Node:

    def __init__(self, data, next=None):
        self.data = data
        self.next = next


class NormalList:

    def __init__(self):
        self.__head = Node(-1)
        self.__size = 0

    def add(self, element):
        node = self.__head
        while node.next:
            node = node.next
        node.next = Node(element)
        self.__size += 1
        return self

    def insert(self, element, index: int):
        if not Validator.range_check(index, (self.__size, 0)):
            raise IndexError("index out of bounds")
        current_index = 0
        previous_node = self.__head
        while current_index < index - 1:
            previous_node = previous_node.next
            current_index += 1
        insert_node = Node(element, previous_node.next)
        previous_node.next = insert_node
        self.__size += 1
        return self

    def remove(self, index):
        if not Validator.range_check(index, (self.__size, 0)):
            raise IndexError("index out of bounds")
        current_index = 0
        previous_node = self.__head
        while current_index < index - 1:
            previous_node = previous_node.next
            current_index += 1
        delete_node = previous_node.next
        previous_node.next = previous_node.next.next
        delete_node.next = None
        self.__size -= 1
        return delete_node.data

    def index(self, element):
        node = self.__head.next
        index = 0
        while node:
            if node == element:
                return index
            node = node.next
            index += 1
        return -1

    def delete(self, element):
        return self.remove(self.index(element))

    def replace(self, index, element):
        node = self.__head.next
        current_index = 0
        while current_index < index:
            node = node.next
            current_index += 1
        node.data = element
        return self

    def add_first(self, element):
        return self.insert(element, 0)

    @property
    def size(self):
        return self.__size

    def is_empty(self):
        return self.__size == 0

    def __len__(self):
        return self.__size

    def __contains__(self, item):
        return self.index(item) != -1

    def __str__(self):
        elements = []
        node = self.__head.next
        while node:
            elements.append("{}".format(node.data))
            node = node.next
        return "->".join(elements)


if __name__ == '__main__':
    n = NormalList()
    n.add(5).add(1).add(6).add_first(100)

    print(n)
