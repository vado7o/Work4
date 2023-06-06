public class RedBlackTree extends BaseBinaryTree implements BinarySearchTree {

    static final boolean RED = false;
    static final boolean BLACK = true;


    @Override
    public Node searchNode(int key) {

        Node node = root;
        while (node != null) {
            if (key == node.data) {
                return node;
            } else if (key < node.data) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        return null;
    }

    @Override
    public void insertNode(int key) {
        Node node = root;
        Node parent = null;

        while (node != null) {
            parent = node;
            if (key < node.data) {
                node = node.left;
            } else  if (key > node.data) {
                node = node.right;
            } else {
                throw  new IllegalArgumentException("уже содержит ноду с ключом " + key);
            }
        }

        Node newNode = new Node(key);
        newNode.color = RED;
        if (parent == null) {
            root = newNode;
        } else if (key < parent.data) {
            parent.left = newNode;
        }
        newNode.parent = parent;

        fixRedBlackPropertiesAfterInsert(newNode);
    }

    private void fixRedBlackPropertiesAfterInsert(Node node) {
        Node parent = node.parent;

        if (parent == null) {
            return;
        }
        if (parent.color == BLACK) {
            return;
        }

        Node grandparent = parent.parent;

        if (grandparent == null) {
            parent.color = BLACK;
            return;
        }

        Node uncle = getUncle(parent);

        if (uncle != null && uncle.color == RED) {
            parent.color = BLACK;
            grandparent.color = RED;
            uncle.color = BLACK;

            fixRedBlackPropertiesAfterInsert(grandparent);
        }

        else if (parent == grandparent.left) {
            if (node == parent.right) {
                rotateLeft(parent);

                parent = node;
            }

            rotateRight(grandparent);

            parent.color = BLACK;
            grandparent.color = RED;
        }

        else {
            if (node == parent.left) {
                rotateRight(parent);

                parent = node;
            }

            rotateLeft(grandparent);

            parent.color = BLACK;
            grandparent.color = RED;
        }
    }

    private Node getUncle(Node parent) {
        Node grandparent = parent.parent;
        if (grandparent.left == parent) {
            return grandparent.right;
        } else if (grandparent.right == parent) {
            return grandparent.left;
        } else {
            throw new IllegalStateException("Родитель не является ребёнком своих прородителей");
        }
    }

    private void rotateRight(Node node) {
        Node parent = node.parent;
        Node leftChild = node.left;

        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.right = node;
        node.parent = leftChild;

        replaceParentsChild(parent, node, leftChild);
    }

    private void rotateLeft(Node node) {
        Node parent = node.parent;
        Node rightChild = node.right;

        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.left = node;
        node.parent = rightChild;

        replaceParentsChild(parent, node, rightChild);
    }

    private void replaceParentsChild(Node parent, Node oldChild, Node newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.left == oldChild) {
            parent.left = newChild;
        } else if (parent.right == oldChild) {
            parent.right = newChild;
        } else {
            throw new IllegalStateException("Нода не является ребёнком своих родителей");
        }

        if (newChild != null) {
            newChild.parent = parent;
        }
    }
}
