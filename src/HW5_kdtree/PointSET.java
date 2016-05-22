package HW5_kdtree;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
public class PointSET {
    private Set<Point2D> rbtree;
    public PointSET() {
        // construct an empty set of points
        rbtree = new TreeSet<Point2D>();
    }
    public boolean isEmpty()  {
        // is the set empty? 
        return rbtree.isEmpty();
    }
    public int size() {
        // number of points in the set 
        return rbtree.size();
    }
    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        if (!rbtree.contains(p))
            rbtree.add(p);       
    }
    public boolean contains(Point2D p) {
        // does the set contain point p? 
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        return rbtree.contains(p);
    }
    public void draw() {
        // draw all points to standard draw
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        for (Point2D p : rbtree) {                        
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle 
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }
        List<Point2D> result = new ArrayList<Point2D>();
        for (Point2D p : rbtree) {
            if (rect.contains(p))
                result.add(p);
        }
        return result;
    }
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        if (isEmpty())
            return null;
        Point2D nearest = null;
        double minDistance = 0;
        for (Point2D t : rbtree) {
            if (nearest == null) {
                nearest = t;
                minDistance = p.distanceTo(t);
            } else if (p.distanceTo(t) < minDistance) {
                minDistance = p.distanceTo(t);
                nearest = t;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
        PointSET test = new PointSET();
        Point2D p1 = new Point2D(0, 0);
        Point2D p2 = new Point2D(1, 1);
        Point2D p3 = new Point2D(0, 0.5);
        test.insert(p1);
        test.insert(p2);
        test.draw();
        System.out.println(test.nearest(p3));
    }
}
