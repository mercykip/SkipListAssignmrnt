package java;

public class Node <K extends Comparable<K>, V>{
    private K key;

    private V value;

    private int level;

    private Node<K, V> up, down, next, previous;

    public Node(K key, V value, int level) {
        this.key = key;
        this.value = value;
        this.level = level;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("java.Node[")
                .append("key:");
        if (this.key == null)
            sb.append("None");
        else
            sb.append(this.key.toString());

        sb.append(" value:");
        if (this.value == null)
            sb.append("None");
        else
            sb.append(this.value.toString());
        sb.append("]");
        return sb.toString();
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Node<K, V> getUp() {
        return up;
    }

    public void setUp(Node<K, V> up) {
        this.up = up;
    }

    public Node<K, V> getDown() {
        return down;
    }

    public void setDown(Node<K, V> down) {
        this.down = down;
    }

    public Node<K, V> getNext() {
        return next;
    }

    public void setNext(Node<K, V> next) {
        this.next = next;
    }

    public Node<K, V> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<K, V> previous) {
        this.previous = previous;
    }
}
