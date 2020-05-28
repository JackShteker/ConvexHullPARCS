import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class ConvexHull {

    public static int findSide(MyPoint p1, MyPoint p2, MyPoint p) {
        int val = (p.y - p1.y) * (p2.x - p1.x) - (p2.y - p1.y) * (p.x - p1.x);
        return Integer.compare(val, 0);
    }

    public static int line_dist(MyPoint p1, MyPoint p2, MyPoint p) {
//        int val = (p.y - p1.y) * (p2.x - p1.x) - (p2.y - p1.y) * (p.x - p1.x);
        int val = (p2.y - p1.y) * p.x - (p2.x - p1.x) * p.y + p2.x * p1.y - p2.y * p1.x;
        return abs(val);
    }

    public static HashSet<MyPoint> find_hull(List<MyPoint> points, MyPoint p1, MyPoint p2, int side) {
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
            HashSet<MyPoint> hull = new HashSet<>();
            hull.add((p1));
            hull.add((p2));
            return hull;
        }
        HashSet<MyPoint> hull = find_hull(points, points.get(ind), p1, -findSide(points.get(ind), p1, p2));
        hull.addAll(find_hull(points, points.get(ind), p2, -findSide(points.get(ind), p2, p1)));
        return hull;
    }

    public static int[] findLeftRightIndices(List<MyPoint> points)
    {
        int min_x = 0;
        int max_x = 1;
        for (int i = 0; i < points.size(); ++i) {
            if (points.get(i).x < points.get(min_x).x)
                min_x = i;
            if (points.get(i).x > points.get(max_x).x)
                max_x = i;
        }
        return new int[]{min_x, max_x};
    }
}

