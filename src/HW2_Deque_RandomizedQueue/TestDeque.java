package HW2_Deque_RandomizedQueue;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.LinkedList;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class TestDeque {
    public void testCornerCase() {
        Deque<Integer> test = new Deque<Integer>();
        test.addFirst(1);
        if (test.removeFirst() == 1 && test.size() == 0) ;
        else System.out.println("testCornerCase 1 failed");

        test.addFirst(1);
        if (test.removeLast() == 1 && test.size() == 0) ;
        else System.out.println("testCornerCase 2 failed");

        test.addLast(1);
        if (test.removeLast() == 1 && test.size() == 0) ;
        else System.out.println("testCornerCase 3 failed");

        test.addLast(1);
        if (test.removeFirst() == 1 && test.size() == 0) ;
        else System.out.println("testCornerCase 4 failed");
        System.out.println("testCornerCase passed");         
    }
    public void testRandomAdd() {
        Deque<Integer> test = new Deque<Integer>();
        LinkedList<Integer> a = new LinkedList<Integer>();
        for (int i = 0; i < 1000; i++) {
            int r = StdRandom.uniform(100000);
            if (r % 2 == 0) {
                test.addFirst(r);
                a.addFirst(r);
            } else if (r % 2 == 1) {
                test.addLast(r);
                a.addLast(r);
            }            
        }        
        int i = 0;
        for (Integer item : test) {
            if (item - a.get(i) != 0) { //two integer objs are not unboxed
                System.out.println("testRandomAdd failed");
                return;
            }
            i++;
        }
        System.out.println("testRandomAdd passed");
    }
    public void testPerformance() {
        int[] count = {1000, 10000, 100000, 1000000,10000000};
        for (int c : count) {                 
            Stopwatch watch = new Stopwatch();
            Deque<String> test = new Deque<String>();
            for (int i = 0; i < c; i++) {
                test.addFirst("x");
                test.addLast("y");
                test.removeFirst();
                test.size();
            }
            for (int i = 0; i < c; i++) {                       
                test.removeLast();            
            }
            System.out.println(c + " operations: " + watch.elapsedTime() + " seconds");
        }        
    }
    public static void main(String[] args) {
        TestDeque testObj = new TestDeque();
        testObj.testCornerCase();
        testObj.testRandomAdd();
        testObj.testPerformance();
    }

}
