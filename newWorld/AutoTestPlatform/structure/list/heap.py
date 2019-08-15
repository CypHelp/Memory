class MaxHeap:

    def __init__(self):
        self.data = []

    def swap(self, i, j):
        if i < 0 or i > len(self.data) or j < 0 or j > len(self.data):
            raise ValueError()
        self.data[i], self.data[j] = self.data[j], self.data[i]

    @staticmethod
    def get_parent(k: int) -> int:
        if k <= 0:
            raise ValueError("Index Error")
        return int(k / 2)

    @staticmethod
    def get_left_child(k: int) -> int:
        return k * 2 + 1

    @staticmethod
    def get_right_child(k: int) -> int:
        return k * 2 + 2

    def add(self, data):
        self.data.append(data)
        self.sift_up(len(self.data) - 1)

    def sift_up(self, index: int):
        while index > 0 and self.data[index] > self.data[self.get_parent(index)]:
            self.swap(index, self.get_parent(index))
            index = self.get_parent(index)

    def is_empty(self) -> bool:
        return len(self.data) == 0

    def find_max(self):
        if len(self.data) == 0:
            raise ValueError("Empty Heap")
        return self.data[0]

    def extract_max(self) -> any:
        ret = self.find_max()
        self.swap(0, len(self.data) - 1)
        self.data.remove(self.data[len(self.data) - 1])
        self.sift_down(0)
        return ret

    def sift_down(self, k: int):
        while self.get_left_child(k) < len(self.data):
            leftChild = self.get_left_child(k)
            rightChild = leftChild + 1
            if rightChild < len(self.data) and self.data[rightChild] > self.data[leftChild]:
                leftChild = rightChild
            if self.data[k] > self.data[leftChild]:
                break
            self.swap(k, leftChild)
            k = leftChild

    def size(self):
        return len(self.data)
