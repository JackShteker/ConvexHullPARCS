
import parcs.AMInfo;
import parcs.channel;
import parcs.point;
import parcs.task;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;
import java.util.List;

@SuppressWarnings("unchecked")
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        task curtask = new task();
        curtask.addJarFile("ConvexHullParallel.jar");

        AMInfo info = new AMInfo(curtask, null);
        Scanner sc = new Scanner(new File(curtask.findFile("points_large.txt")));

        List<MyPoint> points = new ArrayList<>();
        while (sc.hasNextLine()) {
            String data = sc.nextLine();
            String[] nums = data.split(" ");
            points.add(new MyPoint(Integer.parseInt(nums[0]), Integer.parseInt(nums[1])));
        }
        sc.close();

        int[] minmax = ConvexHull.findLeftRightIndices(points);
        int min_ind = minmax[0];
        int max_ind = minmax[1];
        channel side1Channel = ConvexHullParallel.callConvexHull(info, points, points.get(min_ind), points.get(max_ind), 1, 0);
        channel side2Channel = ConvexHullParallel.callConvexHull(info, points, points.get(min_ind), points.get(max_ind), -1, 0);

        HashSet<MyPoint> hull;
        hull = (HashSet<MyPoint>) side1Channel.readObject();
        hull.addAll((HashSet<MyPoint>) side2Channel.readObject());
        System.out.println(hull);



    }

}

