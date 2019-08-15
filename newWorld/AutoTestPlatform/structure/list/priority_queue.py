from AutoTestPlatform.structure.list.heap import MaxHeap


class PriorityQueue:

    def __init__(self):
        self.__heap = MaxHeap()

    def is_empty(self):
        return self.__heap.is_empty()

    def enqueue(self, data):
        self.__heap.add(data)

    def dequeue(self):
        return self.__heap.extract_max()

    def size(self):
        return self.__heap.size()

    def peek(self):
        return self.__heap.find_max()
