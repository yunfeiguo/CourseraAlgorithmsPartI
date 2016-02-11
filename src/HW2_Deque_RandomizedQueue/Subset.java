package HW2_Deque_RandomizedQueue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
/*
 * Write a client program Subset.java that 
 * takes a command-line integer k; reads in
 *  a sequence of N strings from standard input 
 *  using StdIn.readString(); and prints out 
 *  exactly k of them, uniformly at random. 
 *  Each item from the sequence can be printed 
 *  out at most once. You may assume that 0 <= k <= N, 
 *  where N is the number of string on standard input.
 */
public class Subset {
    /*first implementation use ~N extra space
     * 
     */
    /*
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> out = new RandomizedQueue<String>();
        if (k <= 0) return;
        while (!StdIn.isEmpty()) {
            out.enqueue(StdIn.readString());
        }
        while (k > 0) {
            System.out.println(out.dequeue());
            k--;
        }
    }*/
    /*second implementation use ~k extra space
     * (for testing)
     */
    /*
    public static void main(String[] args) {
        int k = 2;

        int[] str = {0, 1, 2, 3,4,5};
        int[] freq = new int[6];
        for (int i = 0; i < 100000; i++) {
            RandomizedQueue<Integer> out = new RandomizedQueue<Integer>();
            int count = 0;
            for (int s : str) {
                count++;
                if (out.size() < k) out.enqueue(s);
                else if (StdRandom.uniform() < 1.0 * k / count) { //prob = 1- k/count                    
                    out.dequeue();
                    out.enqueue(s);                     
                }
            }
            for (Integer s : out) {
                freq[s]++;
                break; //only look at first position
            }
        }      
        for (int i : freq) {
            System.out.println(i);
        }
    }
     */
    /*second implementation use ~k extra space
     *
     */
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> out = new RandomizedQueue<String>();
        int count = 0;
        if (k <= 0) return;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            count++;
            if (out.size() < k) out.enqueue(s);
            else if (StdRandom.uniform() < 1.0 * k / count) { //we want to have 1/(n choose k) * k/(n+1) = 1/(n + 1 choose k)
                out.dequeue();
                out.enqueue(s);
            }
        }
        for (String s : out)
            System.out.println(s);
    }
}
