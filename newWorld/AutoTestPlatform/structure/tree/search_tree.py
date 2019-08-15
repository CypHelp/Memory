class Node:

    def __init__(self, key, value, left=None, right=None):
        self.left = left
        self.key = key
        self.value = value
        self.right = right


class BrainySearchTree:
    __root: Node

    def __init__(self):
        self.__size = 0
        self.__root = None

    def put(self, key, value):
        self.__root = self.__add(self.__root, key, value)

    def __add(self, node: Node, key, value) -> Node:
        if node is None:
            self.__size += 1
            return Node(key, value)

        if key < node.key:
            node.left = self.__add(node.left, key, value)
        else:
            node.right = self.__add(node.right, key, value)
        return node

    def get(self, key):
        res = self.__get(key, self.__root)
        if not res:
            raise ValueError("No Such Key")
        return res

    def __get(self, key, node):
        if node is None:
            return
        self.__get(key, node.left)
        self.__get(key, node.right)
        if key == node.key:
            return node.value

    def prev_order(self, f):
        self.__prev_order(f, self.__root)

    def keys(self):
        s = set()
        self.__keys(s, self.__root)
        return s

    def __keys(self, s: set, node):
        if node is None:
            return
        self.__keys(s, node.left)
        self.__keys(s, node.right)
        s.add(node.key)

    def __prev_order(self, f, node):
        if node is None:
            return
        f(node.key, node.value)
        self.__prev_order(f, node.left)
        self.__prev_order(f, node.right)

    def in_order(self, f):
        self.__in_order(f, self.__root)

    def __in_order(self, f, node):
        if node is None:
            return
        self.__in_order(f, node.left)
        f(node.key, node.value)
        self.__in_order(f, node.right)

    def last_order(self, f):
        self.__last_order(f, self.__root)

    def __last_order(self, f, node):
        if node is None:
            return
        self.__last_order(f, node.left)
        self.__last_order(f, node.right)
        f(node.key, node.value)

    def is_empty(self):
        return self.__size == 0

    @property
    def size(self):
        return self.__size

    def __getitem__(self, item):
        return self.get(item)

    def __setitem__(self, key, value):
        self.put(key, value)

    def items(self):
        l = []
        self.__item(l, self.__root)
        return l

    def __item(self, l: list, node: Node):
        if node is None:
            return
        self.__item(l, node.left)
        self.__item(l, node.right)
        l.append((node.key, node.value))

    @staticmethod
    def find_max(root):
        while root.right:
            root = root.right
        return root

    @staticmethod
    def find_min(root):
        while root.left:
            root = root.left
        return root

    def remove_min(self):
        ret = self.find_min(self.__root)
        self.__root = self.__remove_min(self.__root)
        return ret

    def __remove_min(self, node: Node):
        if node.left is None:
            n = node.right
            node.right = None
            self.__size -= 1
            return n
        node.left = self.__remove_min(node.left)
        return node

    def remove_max(self):
        ret = self.find_max(self.__root)
        self.__root = self.__remove_max(self.__root)
        return ret

    def __remove_max(self, node: Node):
        if node.right is None:
            n = node.left
            node.left = None
            self.__size -= 1
            return n
        node.right = self.__remove_max(node.right)
        return node

    def remove(self, key):
        self.__root = self.__remove(key, self.__root)

    def __remove(self, key, node: Node):
        if node is None:
            return node

        if key > node.key:
            node.right = self.__remove(key, node.right)
        elif key < node.key:
            node.left = self.__remove(key, node.left)
        else:
            if node.left is None:
                right = node.right
                node.right = None
                self.__size -= 1
                return right
            if node.right is None:
                left = node.left
                node.left = None
                self.__size -= 1
                return left

            successor = self.find_min(node.right)
            successor.right = self.remove_min()
            successor.left = node.left
            node.left = node.right = None
            return successor
