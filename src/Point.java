public class Point {

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    int r, c;

    public Point(int row, int col) {
        r = row;
        c = col;
    }

    public double getDistance(Point center) {
        return Math.sqrt((r-center.getR()) * (r-center.getR()) + (c-center.getC()) * (c-center.getC()));
    }
}
