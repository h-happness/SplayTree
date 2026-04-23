public class SplayTree {
    private Node root;
    private static int COUNT_INSERT = 0;
    private static int COUNT_SEARCH = 0;
    private static int COUNT_DELETE = 0;


    public SplayTree() {
        root = null;
    }

    public void insert(int data) {
        if (root == null) {
            root = new Node(data);
            return;
        }

        Node current = root;
        Node parent = null;

        while (current != null) {
            COUNT_INSERT++;
            parent = current;
            if (data < current.data) {
                current = current.left;
            } else if (data > current.data) {
                current = current.right;
            } else {
                // Дубликаты не вставляем, выполняем splay для существующего узла
                splay(current, 1);
                return;
            }
        }

        Node newNode = new Node(data);
        newNode.parent = parent;

        if (data < parent.data) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        splay(newNode, 1);
    }

    public Node search(int data) {
        Node node = root;
        while (node != null && node.data != data) {
            COUNT_SEARCH++;
            if (data < node.data) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        if (node != null) {
            splay(node, 2);
        }
        return node;
    }

    public void delete(int data) {
        COUNT_DELETE++;
        Node node = search(data);
        if (node == null) return;

        splay(node, 3); // Перемещаем узел в корень

        // Если у узла нет левого поддерева
        if (node.left == null) {
            root = node.right;
        }
        // Если нет правого поддерева
        else if (node.right == null) {
            root = node.left;
        }
        // Узел имеет оба поддерева
        else {
            Node rightSubtree = node.right;
            root = node.left;
            root.parent = null;
            splay(findMax(root), 3); // Максимум левого поддерева становится корнем
            root.right = rightSubtree;
            rightSubtree.parent = root;
        }
    }

    // Операция splay — перемещает узел в корень с помощью вращений
    private void splay(Node node, int count) {
        while (node.parent != null) {
            if (count == 1) COUNT_INSERT++;
            if (count == 2) COUNT_SEARCH++;
            if (count == 3) COUNT_DELETE++;
            Node parent = node.parent;
            Node grandparent = parent.parent;

            if (grandparent == null) {
                // Zig: узел — прямой потомок корня
                if (node == parent.left) {
                    rotateRight(parent);
                } else {
                    rotateLeft(parent);
                }
            } else if (node == parent.left && parent == grandparent.left) {
                // Zig‑Zig: два левых поворота
                rotateRight(grandparent);
                rotateRight(parent);
            } else if (node == parent.right && parent == grandparent.right) {
                // Zag‑Zag: два правых поворота
                rotateLeft(grandparent);
                rotateLeft(parent);
            } else if (node == parent.right && parent == grandparent.left) {
                // Zig‑Zag: левый, затем правый поворот
                rotateLeft(parent);
                rotateRight(grandparent);
            } else {
                // Zag‑Zig: правый, затем левый поворот
                rotateRight(parent);
                rotateLeft(grandparent);
            }
        }
    }

    // Правый поворот
    private void rotateRight(Node node) {
        Node leftChild = node.left;

        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.parent = node.parent;
        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.left) {
            node.parent.left = leftChild;
        } else {
            node.parent.right = leftChild;
        }

        leftChild.right = node;
        node.parent = leftChild;
    }

    // Левый поворот
    private void rotateLeft(Node node) {
        Node rightChild = node.right;

        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.parent = node.parent;
        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }

        rightChild.left = node;
        node.parent = rightChild;
    }

    // Находит максимальный элемент в поддереве
    private Node findMax(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // Обход в порядке in‑order (возрастающий порядок)
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.data + " ");
            inOrder(node.right);
        }
    }

    // Обход в порядке pre‑order
    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public int getCountInsert() {
        return COUNT_INSERT;
    }

    public int getCountSearch() {
        return COUNT_SEARCH;
    }

    public int getCountDelete() {
        return COUNT_DELETE;
    }

    public void reset() {
        COUNT_DELETE = 0;
        COUNT_INSERT = 0;
        COUNT_SEARCH = 0;
    }
}
