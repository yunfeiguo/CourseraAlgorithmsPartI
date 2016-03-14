package HW3_collinearPoint;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lines;
   public BruteCollinearPoints(Point[] points)  {
       // finds all line segments containing 4 points exactly once
       if (points == null) {
           throw new java.lang.NullPointerException();
       } else {
           for (Point p : points) {
               if (p == null) 
                   throw new java.lang.NullPointerException();
           }
       }     
       Point[] sortedPoints = copy(points);
       Arrays.sort(sortedPoints);
       int n = sortedPoints.length;
       //check repeated points
       for (int i = 1; i < n; i++) {    
           if (sortedPoints[i].compareTo(sortedPoints[i - 1]) == 0) {
               //repeated sortedPoints are illegal
               throw new java.lang.IllegalArgumentException();
           }
       }       
       lines = new ArrayList<LineSegment>();
       for (int i = 0; i < n; i++) {    
           for (int j = i + 1; j < n; j++) {
               double slope1 = sortedPoints[i].slopeTo(sortedPoints[j]);
               for (int k = j + 1; k < n; k++) {
                   double slope2 = sortedPoints[i].slopeTo(sortedPoints[k]);
                   if (slope1 != slope2) 
                       continue;                  
                   for (int l = k + 1; l < n; l++) {                                           
                       double slope3 = sortedPoints[i].slopeTo(sortedPoints[l]);
                       if ((slope1 == slope2) && (slope2 == slope3)) {
                           if (sortedPoints[i].compareTo(sortedPoints[l]) < 0) {
                               //only output i->l, not l -> i
                           LineSegment line = new LineSegment(sortedPoints[i], sortedPoints[l]);
                           lines.add(line);
                           }
                       }
                   }
               }
           }
       }
   }
   private Point[] copy(Point[] a) {
       Point[] b = new Point[a.length];
       for (int i = 0; i < a.length; i++) {
           b[i] = a[i];
       }
       return b;
   }
   public int numberOfSegments() {
       // the number of line segments
       return lines.size();
   }
   public LineSegment[] segments() {
       // the line segments
       LineSegment[] ret = new LineSegment[lines.size()];
       for (int i = 0; i < lines.size(); i++) {
           ret[i] = lines.get(i);
       }
       return ret;
   }
   public static void main(String[] args) {
       Point[] points = new Point[4];
       points[0] = new Point(0, 0);
       points[1] = new Point(1, 0);
       points[2] = new Point(2, 0);
       points[3] = new Point(3, 0);
    BruteCollinearPoints test = new BruteCollinearPoints(points);
    System.out.println(test.numberOfSegments() == 1);
    /*
    points[0] = new Point(26688, 4703);
    points[1] = new Point(5800, 25231);
    points[2] = new Point(5800, 25231);
    try {
         test = new BruteCollinearPoints(points);
    } catch (IllegalArgumentException e) {
        System.out.println("caught illegalargumentexception");
    }
    */
    
}
}

