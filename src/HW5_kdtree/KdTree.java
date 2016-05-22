package HW5_kdtree;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private int N; //count of points
    private KdTreeNode root;
    private KdTreeNodeComparator c;
    public KdTree() {
        N = 0;
        root = null;
        c = new KdTreeNodeComparator();
    }
    private static class KdTreeNode {
        private Point2D p;
        private KdTreeNode left, right;
        private boolean vertical;
        public KdTreeNode(Point2D p) {
            this.p = p;
            this.left = null;
            this.right = null;
            this.vertical = false;
        }
    }
    private class KdTreeNodeComparator implements Comparator<KdTreeNode> {
        /**
         * suppose a is a node in the tree
         * return comparison result based on whether
         * a is vertical split or horizontal split
         * 
         * @param a
         * @param b
         * @return
         */
        public int compare(KdTreeNode a, KdTreeNode b) {
            if (a == null || b == null) {
                throw new NullPointerException();
            }
            int cmp = 0;
            if (a.vertical) {
                cmp = Point2D.X_ORDER.compare(a.p, b.p);
            } else {
                cmp = Point2D.Y_ORDER.compare(a.p, b.p);
            }    
            //when there are ties, we break ties using the other coordinate
            if (cmp == 0) {
                return a.p.compareTo(b.p);
            } else {
                return cmp;
            }
        }
    }
    public Iterable<Point2D> range(RectHV box) {
        if (box == null) {
            throw new java.lang.NullPointerException();
        }    
        List<Point2D> result = new ArrayList<Point2D>();
        if (isEmpty()) {
            return result;
        }
        range(root, box, result);
        return result;
    }
    private void range(KdTreeNode currentRoot, RectHV box, List<Point2D> result) {
        if (currentRoot == null) 
            return;
        if (box.contains(currentRoot.p)) {
            result.add(currentRoot.p);
        }
        //choose which side(s) to look at based on intersections
        //assume box is bounded by a uniform squared area from 0 to 1
        if (currentRoot.vertical) {
            if (box.xmax() >= currentRoot.p.x()) {
                range(currentRoot.right, box, result);
            }
            if (box.xmin() <= currentRoot.p.x()) {
                range(currentRoot.left, box, result);
            }
        } else {
            if (box.ymax() >= currentRoot.p.y()) {
                range(currentRoot.right, box, result);
            }
            if (box.ymin() <= currentRoot.p.y()) {
                range(currentRoot.left, box, result);
            }
        }
    }
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        if (isEmpty()) {
            return false;
        }
        return contains(root, new KdTreeNode(p));
    }
    private boolean contains(KdTreeNode currentRoot, KdTreeNode n) {
        if (currentRoot == null) {
            return false;
        }
        int cmp = c.compare(currentRoot, n);
        if (cmp < 0) {
            return contains(currentRoot.right, n);
        } else if (cmp > 0) {
            return contains(currentRoot.left, n);
        } else {
            return true;
        }
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        if (isEmpty())
            return null;
        if (contains(p))
            return find(root, new KdTreeNode(p));
        return nearest(root, p, root.p);
    }
    private Point2D find(KdTreeNode currentRoot, KdTreeNode n) {
        if (currentRoot == null) {
            return null;
        }
        int cmp = c.compare(currentRoot, n);
        if (cmp < 0) {
            return find(currentRoot.right, n);
        } else if (cmp > 0) {
            return find(currentRoot.left, n);
        } else {
            return currentRoot.p;
        }
    }
    private Point2D nearest(KdTreeNode currentRoot, Point2D p, Point2D nearestSoFar) {
        //check the distance with the children of currentRoot,
        //if p is closer to left child, go left
        //if p is closer to right child, go right
        //if the distance between p and currentRoot partition axis is smaller
        //than the distance to closest child, we need to look at
        //both sides
        if (currentRoot == null) {
            return nearestSoFar;
        }
        if (p.distanceSquaredTo(currentRoot.p) < p.distanceSquaredTo(nearestSoFar))
            nearestSoFar = currentRoot.p;                                             
        //search the side where query point is at 
        if (c.compare(currentRoot, new KdTreeNode(p)) > 0)
            nearestSoFar = nearest(currentRoot.left, p, nearestSoFar);
        else
            nearestSoFar = nearest(currentRoot.right, p, nearestSoFar);
        //use squared distance to speed up
        double nearestDistanceSoFar = p.distanceSquaredTo(nearestSoFar);
        double distanceToBoundary = calcDistanceToBoundary(currentRoot, p);        
        //check if we need to search the other subtree
        if (nearestDistanceSoFar > distanceToBoundary)
            if (c.compare(currentRoot, new KdTreeNode(p)) > 0)
                nearestSoFar = nearest(currentRoot.right, p, nearestSoFar);
            else
                nearestSoFar = nearest(currentRoot.left, p, nearestSoFar);         
        return nearestSoFar;
    }
    private double calcDistanceToBoundary(KdTreeNode n, Point2D p) {        
        if (n.vertical) {
            return Math.pow(p.x() - n.p.x(), 2);
        } else {
            return Math.pow(p.y() - n.p.y(), 2);
        }
    }
    public int size() {
        return N;
    }
    public void draw() {
        draw(root, 0, 0, 1, 1);
    }
    private void draw(KdTreeNode currentRoot, double xmin, double ymin, double xmax, double ymax) {
        if (currentRoot == null)
            return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        currentRoot.p.draw();
        if (currentRoot.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(currentRoot.p.x(), ymin, currentRoot.p.x(), ymax);
        } else {        
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(xmin, currentRoot.p.y(), xmax, currentRoot.p.y());
        }
        if (currentRoot.vertical) {
            draw(currentRoot.left, xmin, ymin, currentRoot.p.x(), ymax);
            draw(currentRoot.right, currentRoot.p.x(), ymin, xmax, ymax);
        } else {
            draw(currentRoot.left, xmin, ymin, xmax, currentRoot.p.y());
            draw(currentRoot.right, xmin, currentRoot.p.y(), xmax, ymax);        
        }
    }
    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        //assume first partition is vertical
        if (contains(p)) {
            return;
        }
        root = insert(root, new KdTreeNode(p), true);
        N++;
    }
    private KdTreeNode insert(KdTreeNode currentRoot, KdTreeNode n, boolean verticalOrientation) {
        if (currentRoot == null) {
            n.vertical = verticalOrientation;
            return n;
        }
        int cmp = c.compare(currentRoot, n);        
        if (cmp < 0) {
            currentRoot.right = insert(currentRoot.right, n, !currentRoot.vertical);
        } else if (cmp > 0) {
            currentRoot.left = insert(currentRoot.left, n, !currentRoot.vertical);
        }
        return currentRoot;
    }
    public static void main(String[] args) {
        KdTree test = new KdTree();
        Point2D p = new Point2D(0, 0);
        Point2D q = new Point2D(0, 1);
        test.insert(p);
        System.out.println(test.contains(p));
        System.out.println(!test.contains(q));
        Point2D p1 = new Point2D(0, 0);
        Point2D p2 = new Point2D(0, 0.75);
        Point2D p3 = new Point2D(0, 1);
        test.insert(p1);
        test.insert(p2);
        test.insert(p3);
        System.out.println(test.nearest(new Point2D(0, 0.7)));
        System.out.println(test.range(new RectHV(0, 0, 0.25, 0.25)));
    }
}
