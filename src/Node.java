public class Node {

    int data;

    Node left;
    Node right;
    Node parent;

    boolean color;

    public Node(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }
}