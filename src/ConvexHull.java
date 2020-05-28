import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class ConvexHull {

    public static int findSide(Point p1, Point p2, Point p) {
        int val = (p.y - p1.y) * (p2.x - p1.x) - (p2.y - p1.y) * (p.x - p1.x);
        return Integer.compare(val, 0);
    }

    public static int line_dist(Point p1, Point p2, Point p) {
        int val = (p.y - p1.y) * (p2.x - p1.x) - (p2.y - p1.y) * (p.x - p1.x);
        return abs(val);
    }

    public static HashSet<Point> find_hull(List<Point> points, Point p1, Point p2, int side) {
        int ind = -1;
        int max_dist = 0;
        for (int i = 0; i < points.size(); ++i)
        {
            int d = line_dist(p1, p2, points.get(i));
            if (findSide(p1, p2, points.get(i)) == side && d > max_dist)
            {
                ind = i;
                max_dist = d;
            }
        }

        if (ind == -1) {
            HashSet<Point> hull = new HashSet<>();
            hull.add((p1));
            hull.add((p2));
            return hull;
        }
        HashSet<Point> hull = find_hull(points, points.get(ind), p1, -findSide(points.get(ind), p1, p2));
        hull.addAll(find_hull(points, points.get(ind), p2, -findSide(points.get(ind), p2, p1)));
        return hull;
    }

    public static int[] findLeftRightIndices(List<Point> points)
    {
        int min_x = 0;
        int max_x = 1;
        for (int i = 0; i < points.size(); ++i) {
            if (points.get(i).x < min_x)
                min_x = i;
            if (points.get(i).x > max_x)
                max_x = i;
        }
        return new int[]{min_x, max_x};
    }
}

