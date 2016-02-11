package HW2_Deque_RandomizedQueue;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
/* there are at least two ways for implementation using linked list, first is without a sentinel node,
 * we have an empty linked list at first, when we add or remove, we have to be careful
 * about whether there will be one or zero elements afterwards
 * the other way: use two sentinel nodes (head and tail), they are like anchors
 * so we always know there are one node at each end
 */
public class Deque<Item> implements Iterable<Item> {
    private int N; //total number of elements
    private Node head;
    private Node tail;

    public Deque() {
        // construct an empty deque
        N = 0;
        head = new Node();
        tail = new Node();
        head.next = tail;
        head.prev = null;
        tail.prev = head;
        tail.next = null;        
    }
    private class Node {
        private Item val;
        private Node prev;
        private Node next;
    }
    public boolean isEmpty() {
        // is the deque empty? 
        return N == 0;
    }
    public int size() {
        // return the number of items on the deque
        return N;
    }
    public void addFirst(Item item) {
        // add the item to the front
        if (item == null) throw new java.lang.NullPointerException();
        Node newNode = new Node();
        newNode.val = item;
        
        newNode.prev = head;
        newNode.next = head.next;
        head.next.prev = newNode;
        head.next = newNode;                              
        N++;
    }
    public void addLast(Item item) {
        // add the item to the end
        if (item == null) throw new java.lang.NullPointerException();
        Node newNode = new Node();
        newNode.val = item;
        
        newNode.next = tail;
        newNode.prev = tail.prev;
        tail.prev.next = newNode;
        tail.prev = newNode;        
        N++;
    }
    public Item removeFirst() {
        // remove and return the item from the front
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Node ret = head.next;
        head.next = ret.next;
        ret.next.prev = head;        
        N--;      
        return ret.val;
    }
    public Item removeLast() {
        // remove and return the item from the end
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Node ret = tail.prev;
        tail.prev = ret.prev;
        tail.prev.next = tail;               
        N--; 
        return ret.val;
    }
    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item> {
        private Node cur = head.next;

        public boolean hasNext() {
            return cur != tail; //not empty until seeing tail
        }

        public void remove() { throw new java.lang.UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item ret = cur.val;
            cur = cur.next;            
            return ret;
        }
    }

    public static void main(String[] args) {
        // unit testing
        Deque<Integer> test = new Deque<Integer>();
        test.addFirst(1);
        test.addLast(2);
        for (Integer i : test) {
            System.out.println(i);
        }
        StdOut.println(test.removeFirst());
        StdOut.println(test.removeLast());
    }
}

