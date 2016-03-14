package HW3_collinearPoint;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lines;
    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        if (points == null) {
            throw new java.lang.NullPointerException();
        }
        for (Point i : points) {
            if (i == null) {
                throw new java.lang.NullPointerException();
            }
        }
        lines = new ArrayList<LineSegment>();
        int n = points.length;
        Point[] sortedPoints = copy(points);
        Arrays.sort(sortedPoints); //sort by natural order (compareTo())
        //check if repeated points exist
        for (int i = 1; i < n; i++) {
            if (sortedPoints[i - 1].compareTo(sortedPoints[i]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        for (int i = 0; i < n; i++) {
            Point p = sortedPoints[i];
            //copy the remaining points to a new array
            Point[] complementPoints = copy(sortedPoints, i);
            //sort them by slope            
            Arrays.sort(complementPoints, p.slopeOrder()); //Arrays.sort(Obj) is stable, this is important
            //output line segments if 4 or more points
            //are collinear
            if (complementPoints.length < 3) continue;
            int j = 1;
            int strike = 1; //number of collinear points
            while (j < complementPoints.length) {
                if (p.slopeTo(complementPoints[j - 1]) == p.slopeTo(complementPoints[j])) {
                    strike++;
                } else {
                    if (strike >= 3) {  //>= 4 collinear points counting p
                        if (inorder(p, complementPoints, j - strike, j - 1))
                            lines.add(new LineSegment(p, complementPoints[j - 1]));                        
                    }
                    strike = 1;
                }
                j++;
            }  
            if (strike >= 3) {
                if (inorder(p, complementPoints, complementPoints.length - strike, complementPoints.length - 1))
                    lines.add(new LineSegment(p, complementPoints[complementPoints.length - 1]));
            }
        }        
    }
    /*determine if p, complementPoints[j - strike ~ j - 1] are in 
     * ascending order
     * 
     */
    private boolean inorder(Point p, Point[] complementPoints, int lo, int hi) {
        int first = p.compareTo(complementPoints[lo]);
        if (first > 0)
            return false;
        for (int i = lo + 1; i <= hi; i++) {
            if (complementPoints[i - 1].compareTo(complementPoints[i]) > 0) {
                return false;
            }
        }
        return true;
    }
    /*
     * copy a's elements to b's elements
     */
    private Point[] copy(Point[] a) {
        Point[] b = new Point[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = a[i];
        }
        return b;
    }
    /*
     * copy a's elements to b, except i
     */
    private Point[] copy(Point[] a, int i) {
        Point[] b = new Point[a.length - 1];
        int count = 0;
        for (int j = 0; j < a.length; j++) {
            if (j == i) continue;
            b[count++] = a[j];
        }
        return b;
    }
    public int numberOfSegments() {
        // the number of line segments
        return lines.size();
    }
    public LineSegment[] segments() {
        // the line segments
        return lines.toArray(new LineSegment[0]);
    }
    public static void main(String[] args) {
        Point[] points = new Point[4];
        points[0] = new Point(0, 0);
        points[1] = new Point(1, 0);
        points[2] = new Point(2, 0);
        points[3] = new Point(3, 0);
        FastCollinearPoints test = new FastCollinearPoints(points);
        System.out.println(test.numberOfSegments() == 1);
    }

}
