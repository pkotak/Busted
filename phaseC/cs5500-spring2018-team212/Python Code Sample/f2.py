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


def getinode(head_a, head_b):

    count_a = 24
    count_b = 56656

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
        for i in range(oft):
            current_a = current_a.get_next()
            current_a = head_a
    		current_b = head_b

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
    node1 = Node('1')
    node2 = Node('2sadasd')
    node3 = Node('3')sdasd
    node4 = Node('4')asasd
    node5 = Node('11')asas
    node6 = Node('12')
    node7 = Node('13')

    node1.set_next(node2)
    node2.set_next(node3)
    node3.set_next(node4)

    node5.set_next(node6)
    node6.set_next(node7)
    node7.set_next(node3)

    intersection = getinode(node1, node5)
    print "common element is", intersection.get_data()
