package HW2_Deque_RandomizedQueue;
import java.lang.reflect.Array;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class TestRandomizedQueue {
    //test corner case
    public void testCornerCase() {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        test.enqueue(1);
        test.dequeue();
        if (!test.isEmpty()) System.out.println("testCornerCase 1 failed");
        test.enqueue(1);
        test.enqueue(2);
        test.enqueue(3);
        test.enqueue(4);
        if (test.size() != 4) System.out.println("testCornerCase 2 failed");
        test.dequeue();
        test.dequeue();
        test.dequeue();
        test.dequeue();
        if (!test.isEmpty()) System.out.println("testCornerCase 3 failed");
        System.out.println("testCornerCase passed");
    }
    //test independence of iterators
    //test randomness of iterators
    public void testIterator() {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        int N = 100;
        int[] freq1 = new int[N];
        int[] freq2 = new int[N];
        int[] freq3 = new int[N];
        int[][] freq4 = new int[N][N]; //count frequency of each number at each position
        int pos = 0;
        for (int i = 0; i < N; i++) {
            test.enqueue(i);
        }
        for (Integer i : test) {
            freq1[i]++;            
            for (Integer j : test) {
                freq2[j]++;
                for (Integer k : test) {
                    freq3[k]++;
                    freq4[pos++ % N][k]++;
                }
            }
        }
        for (int i : freq1) {
            if (i != 1) {
                System.out.println("testIterator 1 failed");
                return;
            }
        }
        for (int j : freq2) {
            if (j != N) {
                System.out.println("testIterator 2 failed");
                return;
            }
        }
        for (int k : freq3) {
            if (k != N * N) {
                System.out.println("testIterator 3 failed");
                return;
            }
        }
        for (int[] freqPerPos : freq4) { 
            System.out.println("first position: ");
            for (int freq : freqPerPos) {
                System.out.println(1.0 * freq / N / N + " ");
            }
            System.out.println();
            break;
        }
        System.out.println("testIteartor passed");
    }   
    //test randomness of dequeue
    public void testDequeue() {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        int N = 2;
        int[] freq = new int[N];

        for (int i = 0; i < N * 100; i++) {
            for (int j = 0; j < N; j++) {
                test.enqueue(j);
            }
            for (int j = 0; j < N; j++) {
                if (j == N / 2) {
                    freq[test.dequeue()]++;
                } else {
                    test.dequeue();
                }
            }
        }
        for (int i : freq) {
            System.out.println(i);
        }
        System.out.println("testDequeue: you should see similar counts for each number");
    }
    public void testEnqueueDequeue() {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        int N = 10000;
        for (int i = 0; i < N;i++) {
            int r = StdRandom.uniform(4);
            if (r == 1) {
                test.enqueue(r);
            } else {
                if (!test.isEmpty()) {
                    if (1 != test.dequeue()) {
                        System.out.println("testEnqueueDequeue failed");
                        return;
                    }
                }
            }
        }
        System.out.println("testEnqueueDequeue passed");
    }
    //test performance (constant amortized time)
    public void testPerformance() {
        int[] count = {1000, 10000, 100000, 1000000,10000000};
        for (int c : count) {                 
            Stopwatch watch = new Stopwatch();
            RandomizedQueue<String> test = new RandomizedQueue<String>();
            for (int i = 0; i < c; i++) {
                test.enqueue("x");                
                test.size();
            }
            for (int i = 0; i < c; i++) {                       
                test.dequeue();            
            }
            System.out.println(c + " operations: " + watch.elapsedTime() + " seconds");
        } 
    }
    public static void main(String[] args) {
        TestRandomizedQueue testObj = new TestRandomizedQueue();
        testObj.testCornerCase();
        testObj.testIterator();
        testObj.testDequeue();
        testObj.testPerformance();
        testObj.testEnqueueDequeue();
    }
}