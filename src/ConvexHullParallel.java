import parcs.AM;
import parcs.AMInfo;
import parcs.channel;
import parcs.point;

import java.awt.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings("unchecked")
public class ConvexHullParallel extends ConvexHull implements AM {

    public static int PARALLEL_SIZE_LIMIT = 1 << 16;

    public static channel callConvexHull(AMInfo info, List<MyPoint> points, MyPoint p1, MyPoint p2, Integer side) {
        point p = info.createPoint();
        channel c = p.createChannel();
        p.execute("ConvexHullParallel");
        c.write((Serializable) points);
        c.write((Serializable) p1);
        c.write((Serializable) p2);
        c.write((Serializable) side);
        return c;
    }

    @Override
    public void run(AMInfo info) {
        List<MyPoint> points = (List<MyPoint>) info.parent.readObject();
        MyPoint p1 = (MyPoint) info.parent.readObject();
        MyPoint p2 = (MyPoint) info.parent.readObject();
        Integer side = (Integer) info.parent.readObject();
        System.out.println("Convex hull iteration.\n" +
                "p1: " + p1.toString() + " p2: " + p2.toString());

        List<Double> result;

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
        HashSet<MyPoint> hull;
        if (ind == -1) {
            hull = new HashSet<>();
            hull.add((p1));
            hull.add((p2));
        }
        else {
            channel side1Channel = callConvexHull(info, points, points.get(ind), p1, -findSide(points.get(ind), p1, p2));
            channel side2Channel = callConvexHull(info, points, points.get(ind), p2, -findSide(points.get(ind), p2, p1));

            hull = (HashSet<MyPoint>) side1Channel.readObject();
            hull.addAll((HashSet<MyPoint>) side2Channel.readObject());
        }

        info.parent.write((Serializable) hull);
    }
}
