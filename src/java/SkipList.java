package java;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
public class SkipList <K extends Comparable<K>, V> implements Iterable<K>{
    protected static final Random randomGenerator = new Random();

    protected static final double DEFAULT_PROBABILITY = 0.5;

    private Node<K, V> head;

    private double probability;

    private int size;

    public SkipList(int i, String jjj) {
        this(DEFAULT_PROBABILITY);
    }

    public SkipList(double probability) {
        this.head = new Node<K, V>(null, null, 0);
        this.probability = probability;
        this.size = 0;
    }

    public V get(K key) {
        checkKeyValidity(key);
        Node<K, V> node = findNode(key);
        if (node.getKey().compareTo(key) == 0)
            return node.getValue();
        else
            return null;
    }

    public void add(K key, V value) {
        checkKeyValidity(key);
        Node<K, V> node = findNode(key);
        if (node.getKey() != null && node.getKey().compareTo(key) == 0) {
            node.setValue(value);
            return;
        }

        Node<K, V> newNode = new Node<K, V>(key, value, node.getLevel());
        horizontalInsert(node, newNode);
        // Decide level according to the probability function
        int currentLevel = node.getLevel();
        int headLevel = head.getLevel();
        while (isBuildLevel()) {
            // buiding a new level
            if (currentLevel >= headLevel) {
                Node<K, V> newHead = new Node<K, V>(null, null, headLevel + 1);
                verticalLink(newHead, head);
                head = newHead;
                headLevel = head.getLevel();
            }
            // copy node and newNode to the upper level
            while (node.getUp() == null) {
                node = node.getPrevious();
            }
            node = node.getUp();

            Node<K, V> tmp = new Node<K, V>(key, value, node.getLevel());
            horizontalInsert(node, tmp);
            verticalLink(tmp, newNode);
            newNode = tmp;
            currentLevel++;
        }
        size++;
    }

    public void remove(K key) {
        checkKeyValidity(key);
        Node<K, V> node = findNode(key);
        if (node == null || node.getKey().compareTo(key) != 0)
            throw new NoSuchElementException("The key is not exist!");

        // Move to the bottom
        while (node.getDown() != null)
            node = node.getDown();
        // Because node is on the lowest level so we need remove by down-top
        Node<K, V> prev = null;
        Node<K, V> next = null;
        for (; node != null; node = node.getUp()) {
            prev = node.getPrevious();
            next = node.getNext();
            if (prev != null)
                prev.setNext(next);
            if (next != null)
                next.setPrevious(prev);
        }

        // Adjust head
        while (head.getNext() == null && head.getDown() != null) {
            head = head.getDown();
            head.setUp(null);
        }
        size--;
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean empty() {
        return size == 0;
    }

    protected Node<K, V> findNode(K key) {
        Node<K, V> node = head;
        Node<K, V> next = null;
        Node<K, V> down = null;
        K nodeKey = null;

        while (true) {
            // Searching nearest (less than or equal) node with special key
            next = node.getNext();
            while (next != null && lessThanOrEqual(next.getKey(), key)) {
                node = next;
                next = node.getNext();
            }
            nodeKey = node.getKey();
            if (nodeKey != null && nodeKey.compareTo(key) == 0)
                break;
            // Descend to the bottom for continue search
            down = node.getDown();
            if (down != null) {
                node = down;
            } else {
                break;
            }
        }

        return node;
    }

    protected void checkKeyValidity(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key must be not null!");
    }

    protected boolean lessThanOrEqual(K a, K b) {
        return a.compareTo(b) <= 0;
    }

    protected boolean isBuildLevel() {
        return randomGenerator.nextDouble() < probability;
    }

    protected void horizontalInsert(Node<K, V> x, Node<K, V> y) {
        y.setPrevious(x);
        y.setNext(x.getNext());
        if (x.getNext() != null)
            x.getNext().setPrevious(y);
        x.setNext(y);
    }

    protected void verticalLink(Node<K, V> x, Node<K, V> y) {
        x.setDown(y);
        y.setUp(x);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<K, V> node = head;

        // Move into the lower left bottom
        while (node.getDown() != null)
            node = node.getDown();

        while (node.getPrevious() != null)
            node = node.getPrevious();

        // Head node with each level the key is null
        // so need to move into the next node
        if (node.getNext() != null)
            node = node.getNext();

        while (node != null) {
            sb.append(node.toString()).append("\n");
            node = node.getNext();
        }

        return sb.toString();
    }

    @Override
    public Iterator<K> iterator() {
        return new SkipListIterator<K, V>(head);
    }
}
