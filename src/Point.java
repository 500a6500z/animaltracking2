public class Point {

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    int r, c;

    /**
     * Constructs a point given the row and column coordinates
     * @param row row coordinate
     * @param col column coordinate
     */
    public Point(int row, int col) {
        r = row;
        c = col;
    }

    /**
     * Constructs a point given a string with comma separated row and column coordinates
     * @param str String in the format "row,col" where row and col are the coordinates
     */
    public Point(String str) {
        this.r = Integer.parseInt(str.substring(0, str.indexOf(',')).trim());
        this.c = Integer.parseInt(str.substring(str.indexOf(',') + 1).trim());
    }

    public double getDistance(Point center) {
        return Math.sqrt((r-center.getR()) * (r-center.getR()) + (c-center.getC()) * (c-center.getC()));
    }

    /**
     * Returns the Point data as a String
     * @return String in format "row,col"
     */
    public String toString() {
        return r + "," + c;
    }
}
