class Node:

    def __init__(self, key, value, left=None, right=None):
        self.left = left
        self.key = key
        self.value = value
        self.right = right
        self.height = 1


class BalancedBinaryTree:
    __root: Node

    def __init__(self):
        self.__size = 0
        self.__root = None

    def put(self, key, value):
        self.__root = self.__add(self.__root, key, value)

    def __right_rotate(self, node: Node):
        x = node.left
        new = node.right

        x.right = node
        node.left = new

        x.height = self.height(x)
        new.height = self.height(new)
        return x

    def __left_rotate(self, node: Node):
        x = node.left
        new = node.right

        x.left = node
        node.right = new

        x.height = self.height(x)
        new.height = self.height(new)
        return x

    def __add(self, node: Node, key, value) -> Node:
        if node is None:
            self.__size += 1
            return Node(key, value)

        if key < node.key:
            node.left = self.__add(node.left, key, value)
        elif key > node.key:
            node.right = self.__add(node.right, key, value)
        else:
            node.value = value

        node.height = self.height(node)

        balance = self.balance_factor(node)
        # RR
        if balance > 1 and self.balance_factor(node.left) >= 0:
            return self.__right_rotate(node)
        # LL
        if balance < -1 and self.balance_factor(node.right) <= 0:
            return self.__left_rotate(node)
        # LR
        if balance > 1 and self.balance_factor(node.left) < 0:
            node.left = self.__left_rotate(node.left)
            return self.__right_rotate(node)
        # RL
        if balance < -1 and self.balance_factor(node.right) > 0:
            node.right = self.__right_rotate(node.right)
            return self.__left_rotate(node)

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

    def is_avl(self):
        return self.__is_avl(self.__root)

    def __is_avl(self, node: Node):
        if node is None:
            return True
        self.__is_avl(node.left)
        if self.balance_factor(node) > 1:
            return False
        self.__is_avl(node.right)
        return True

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

    @staticmethod
    def __max(x, y):
        return x if x > y else y

    def height(self, node: Node):
        if not node:
            return 1
        return self.__max(node.height, node.height) + 1

    @staticmethod
    def balance_factor(node: Node):
        if not node or not node.right or not node.left:
            return 0
        return node.left.height - node.right.height

