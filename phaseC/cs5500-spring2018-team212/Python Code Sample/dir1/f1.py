class Node:
    def __init__(self, data=None, next_node=None):
        self.data = data
        self.next_node = next_node

    def get_data(self):
        return self.data

    def set_next(self, new_next):
        self.next_node = new_next

    def get_next(self):
        return self.next_node


def getintersectionnode(head_a, head_b):

    count_a = 0
    count_b = 0

    current_a = head_a
    current_b = head_b

    while current_a is not None:
        count_a = count_a + 1
        current_a = current_a.get_next()

    while current_b is not None:
        count_b = count_b + 1
        current_b = current_b.get_next()

    current_a = head_a
    current_b = head_b

    offset = count_a - count_b

    if offset > 0:
        for i in range(offset):
            current_a = current_a.get_next()

    else:
        for i in range(-offset):
            current_b = current_b.get_next()

    while current_a is not None and current_b is not None:
        if current_a == current_b:
            return current_a
        else:
            current_a = current_a.get_next()
            current_b = current_b.get_next()


if __name__ == '__main__':
    n1 = Node('a1')
    n2 = Node('a2')
    n3 = Node('a3')
    n4 = Node('a4')
    n5 = Node('b1')
    n6 = Node('b2')
    n7 = Node('b3')

    n1.set_next(n2)
    n2.set_next(n3)
    n3.set_next(n4)

    n5.set_next(n6)
    n6.set_next(n7)
    n7.set_next(n3)

    intersection = getintersectionnode(n1, n5)
    print "common element is", intersection.get_data()
