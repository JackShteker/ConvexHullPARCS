import java.awt.*;

public class MyPoint extends Point {
    @Override
    public String toString() {
        return "(" + this.x +", " + this.y + ")";
    }

    public MyPoint(int x, int y) {
        super(x, y);
    }
}
