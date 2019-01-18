public class Point {

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getDistance(Point center) {
        return Math.sqrt((x-center.getX()) * (x-center.getX()) + (y-center.getY()) * (y-center.getY()));
    }
}
