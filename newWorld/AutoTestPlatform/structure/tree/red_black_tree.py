class Node:
    RED = True
    BLACK = False

    def __init__(self, key, value, left=None, right=None):
        self.left = left
        self.key = key
        self.value = value
        self.right = right
        self.color = Node.RED
        self.p = None


class RedBlackTree:
    __root: Node

    def __init__(self):
        self.__size = 0
        self.__root = None

    @staticmethod
    def __is_red(node: Node) -> bool:
        if node is None:
            return Node.BLACK
        return node.color

    def put(self, key, value) -> None:
        self.__add(key, value)

    def get(self, key) -> None:
        res = self.__get(key, self.__root)
        if not res:
            raise ValueError("No Such Key")
        return res

    def __get(self, key, node: Node):
        if node is None:
            return
        self.__get(key, node.left)
        self.__get(key, node.right)
        if key == node.key:
            return node.value

    def left_rotate(self, node):
        if not node.right:
            return False
        node_right = node.right
        node_right.p = node.p
        if not node.p:
            self.__root = node_right
        elif node == node.p.left:
            node.p.left = node_right
        else:
            node.p.right = node_right
        if node_right.left:
            node_right.left.p = node
        node.right = node_right.left
        node.p = node_right
        node_right.left = node

    def right_rotate(self, node):
        if not node.left:
            return False
        node_left = node.left
        node_left.p = node.p
        if not node.p:
            self.__root = node_left
        elif node == node.p.left:
            node.p.left = node_left
        elif node == node.p.right:
            node.p.right = node_left
        if node_left.right:
            node_left.right.p = node
        node.left = node_left.right
        node.p = node_left
        node_left.right = node

    def transplant(self, node_u, node_v):
        if not node_u.p:
            self.__root = node_v
        elif node_u == node_u.p.left:
            node_u.p.left = node_v
        elif node_u == node_u.p.right:
            node_u.p.right = node_v
        if node_v:
            node_v.p = node_u.p

    @staticmethod
    def tree_maximum(node):
        temp_node = node
        while temp_node.right:
            temp_node = temp_node.right
        return temp_node

    @staticmethod
    def tree_minimum(node):
        temp_node = node
        while temp_node.left:
            temp_node = temp_node.left
        return temp_node

    def prev_order_tree_walk(self, node):
        if node:
            print(node.value, node.color)
            self.prev_order_tree_walk(node.left)
            self.prev_order_tree_walk(node.right)

    def __add(self, key, value):
        node = Node(key=key, value=value)
        temp_root = self.__root
        temp_node = None
        while temp_root:
            temp_node = temp_root
            if node.key == temp_node.key:
                return
            elif node.key > temp_node.key:
                temp_root = temp_root.right
            else:
                temp_root = temp_root.left
        if not temp_node:
            self.__root = node
            node.color = Node.BLACK
        elif node.key < temp_node.key:
            temp_node.left = node
            node.p = temp_node
        else:
            temp_node.right = node
            node.p = temp_node
        self.insert_fix_up(node)

    def insert_fix_up(self, node):
        if node.value == self.__root.value:
            return
        while node.p and node.p.color == Node.RED:
            if node.p == node.p.p.left:
                node_uncle = node.p.p.right
                if node_uncle and node_uncle.color == Node.RED:
                    node.p.color = Node.BLACK
                    node_uncle.color = Node.BLACK
                    node.p.p.color = Node.RED
                    node = node.p.p
                    continue
                elif node == node.p.right:
                    self.left_rotate(node.p)
                    node = node.left
                node.p.color = Node.BLACK
                node.p.p.color = Node.RED
                self.right_rotate(node.p.p)
                return
            elif node.p == node.p.p.right:
                node_uncle = node.p.p.left
                if node_uncle and node_uncle.color == Node.RED:
                    node.p.color = Node.BLACK
                    node_uncle.color = Node.BLACK
                    node.p.p.color = Node.RED
                    node = node.p.p
                    continue
                elif node == node.p.left:
                    self.right_rotate(node)
                    node = node.right
                node.p.color = Node.BLACK
                node.p.p.color = Node.RED
                self.left_rotate(node.p.p)
                return
        self.__root.color = Node.BLACK

    def delete(self, node):
        node_color = node.color
        if not node.left:
            temp_node = node.right
            self.transplant(node, node.right)
        elif not node.right:
            temp_node = node.left
            self.transplant(node, node.left)
        else:
            node_min = self.tree_minimum(node.right)
            node_color = node_min.color
            temp_node = node_min.right
            if node_min.p != node:
                self.transplant(node_min, node_min.right)
                node_min.right = node.right
                node_min.right.p = node_min
                self.transplant(node, node_min)
            node_min.left = node.left
            node_min.left.p = node_min
            node_min.color = node.color
        if node_color == Node.BLACK:
            self.delete_fixup(temp_node)

    def delete_fixup(self, node):
        while node != self.__root and node.color == Node.BLACK:
            if node == node.p.left:
                node_brother = node.p.right
                if node_brother.color == Node.RED:
                    node_brother.color = Node.BLACK
                    node.p.color = Node.RED
                    self.left_rotate(node.p)
                    node_brother = node.p.right
                if (not node_brother.left or node_brother.left.color == Node.BLACK) and \
                        (not node_brother.right or node_brother.right.color == Node.BLACK):
                    node_brother.color = Node.RED
                    # node = node.p
                else:
                    if not node_brother.right or node_brother.right.color == Node.BLACK:
                        node_brother.color = Node.RED
                        node_brother.left.color = Node.BLACK
                        self.right_rotate(node_brother)
                        node_brother = node.p.right
                    node_brother.color = node.p.color
                    node.p.color = Node.BLACK
                    node_brother.right.color = Node.BLACK
                    self.left_rotate(node.p)
                node = self.__root
                break
            else:
                node_brother = node.p.left
                if node_brother.color == Node.RED:
                    node_brother.color = Node.BLACK
                    node.p.color = Node.RED
                    self.left_rotate(node.p)
                    node_brother = node.p.right
                if (not node_brother.left or node_brother.left.color == Node.BLACK) and \
                        (not node_brother.right or node_brother.right.color == Node.BLACK):
                    node_brother.color = Node.RED
                    # node = node.p
                else:
                    if not node_brother.left or node_brother.left.color == Node.BLACK:
                        node_brother.color = Node.RED
                        node_brother.right.color = Node.BLACK
                        self.left_rotate(node_brother)
                        node_brother = node.p.left
                    node_brother.color = node.p.color
                    node.p.color = Node.BLACK
                    node_brother.left.color = Node.BLACK
                    self.right_rotate(node.p)
                node = self.__root
                break
        node.color = Node.BLACK

    def prev_order(self, f) -> None:
        self.__prev_order(f, self.__root)

    def keys(self):
        s = set()
        self.__keys(s, self.__root)
        return s

    def __keys(self, s: set, node) -> None:
        if node is None:
            return
        self.__keys(s, node.left)
        self.__keys(s, node.right)
        s.add(node.key)

    def __prev_order(self, f, node) -> None:
        if node is None:
            return
        f(node.key, node.value)
        self.__prev_order(f, node.left)
        self.__prev_order(f, node.right)

    def in_order(self, f) -> None:
        self.__in_order(f, self.__root)

    def __in_order(self, f, node) -> None:
        if node is None:
            return
        self.__in_order(f, node.left)
        f(node.key, node.value)
        self.__in_order(f, node.right)

    def last_order(self, f) -> None:
        self.__last_order(f, self.__root)

    def __last_order(self, f, node) -> None:
        if node is None:
            return
        self.__last_order(f, node.left)
        self.__last_order(f, node.right)
        f(node.key, node.value)

    def is_empty(self) -> bool:
        return self.__size == 0

    @property
    def size(self) -> int:
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
