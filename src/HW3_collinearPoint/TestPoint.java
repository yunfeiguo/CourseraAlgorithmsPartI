package HW3_collinearPoint;
import java.util.Comparator;

public class TestPoint {
    public static void main(String[] args) {
        Point x = new Point(0, 0);
        Point y = new Point(0, 1);
        Point z = new Point(1, 0);
        Point w = new Point(1, 1);
        Point xp = new Point(0, 0);
        
        System.out.println(x.slopeTo(y) == Double.POSITIVE_INFINITY);
        System.out.println(x.slopeTo(xp) == Double.NEGATIVE_INFINITY);
        System.out.println(x.slopeTo(z) == 0);
        System.out.println(x.slopeTo(w) == 1);
        System.out.println(x.compareTo(xp) == 0);
        System.out.println(x.compareTo(y) < 0);
        System.out.println(x.compareTo(z) < 0);
        
        Comparator<Point> c = x.slopeOrder();
        System.out.println(c.compare(y, z) > 0);
        System.out.println(c.compare(y, x) > 0);
        System.out.println(c.compare(w, x) > 0);
        System.out.println(0 == 1/Double.POSITIVE_INFINITY);
        System.out.println(0 == 1/Double.NEGATIVE_INFINITY);
    }

}
