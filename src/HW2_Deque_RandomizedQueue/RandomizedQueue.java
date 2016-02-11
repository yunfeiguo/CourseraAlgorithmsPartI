package HW2_Deque_RandomizedQueue;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

/*A randomized queue is similar to a stack or queue, 
 * except that the item removed is chosen uniformly at 
 * random from items in the data structure. 
 *****/
public class RandomizedQueue<Item> implements Iterable<Item> {
    //16byte overhead
    private int N; //number of elements, 4byte    
    private Item[] a; //24 + 8*2*N bytes, because of resizing    

    public RandomizedQueue() {
        // construct an empty randomized queue
        a = (Item[]) new Object[2];        
        N = 0;
    }
    public boolean isEmpty() {
        // is the queue empty?
        return N == 0;
    }
    public int size() {
        // return the number of items on the queue
        return N;
    }
    public void enqueue(Item item) {
        // add the item
        if (item == null) throw new java.lang.NullPointerException();
        if (N == a.length) resize(a.length * 2);
        //try insert at random place instead of always appending
        int r = StdRandom.uniform(N + 1);
        /*System.out.println("enqueue: a length" + a.length);
        System.out.println("enqueue: N" + N);
        System.out.println("enqueue: r" + r);
        */
        a[N] = a[r];
        a[r] = item;
        N++;
    }
    
    private void resize(int size) {
        //System.out.println("changing to " + size);
        Item[] b = (Item[]) new Object[size];
        for (int i = 0; i < N; i++)
            b[i] = a[i];
        a = b;
    }
    public Item dequeue() {
        // remove and return a random item
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item ret = a[--N];  
        a[N] = null; //avoid loitering
        if (N > 0 && N <= a.length / 4) resize(a.length / 2); //must use < rather than <=, in case N = 0
        return ret;     
    }
    public Item sample() {
        // return (but do not remove) a random item
        if (isEmpty()) throw new java.util.NoSuchElementException();
        return a[StdRandom.uniform(N)];
    }
    /*make sure every iterator is independent of another iterator
     * and use linear memory space
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int itCount; //count of remaining items to be iterated
        private int[] randIdx;

        public RandomizedQueueIterator() {
            itCount = N;
            randIdx = new int[itCount]; //make sure it's independent of another iterator
            for (int i = 0; i < itCount; i++)
                randIdx[i] = i;
            //use Knuth shuffle (O(N)) to randomly shuffle index array
            StdRandom.shuffle(randIdx);        
        }
        public void remove() { throw new java.lang.UnsupportedOperationException(); }
        public boolean hasNext() {
            return itCount > 0;
        }
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return a[randIdx[--itCount]];
        }
    }
    public static void main(String[] args) {
        // unit testing
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        for (int i = 0; i < 3; i++) {
            test.enqueue(0);
            test.enqueue(1);       
            for (Integer r : test) {
                System.out.println(r);
            }
            System.out.println("size " + test.size());
            System.out.println(test.dequeue());
            System.out.println(test.dequeue());
        }        
    }
}
