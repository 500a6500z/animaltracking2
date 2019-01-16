import processing.core.PApplet;

import java.util.ArrayList;

public class ConvexHullFilter implements PixelFilter {
    private static final int THRESHOLD = 80;
    private static final int RED = 150;
    private static final int GREEN = 102;
    private static final int BLUE = 81;
    private ArrayList<Point> list;

    public ConvexHullFilter() {
        list = new ArrayList<>();
    }

    @Override
    public DImage processImage(DImage img) {

        //color mask
        int[][] pixels = img.getColorPixelGrid();

        for(int r = 0; r < pixels.length; r++) {
            for(int c = 0; c < pixels[r].length; c++) {
                if(isClose(pixels[r][c])) {
                    pixels[r][c] = Integer.MAX_VALUE;
                    list.add(new Point(r, c));
                }
                else {
                    pixels[r][c] = 0;
                }
            }
        }
        img.setPixels(pixels);



        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }

    private boolean isClose(int pixel) {
        int red1 = getRed(pixel) - RED;
        int green1 = getGreen(pixel) - GREEN;
        int blue1 = getBlue(pixel) - BLUE;
        double distance1 = Math.sqrt( (red1 * red1) + (green1 * green1) + (blue1 * blue1));

        return distance1 <= THRESHOLD;
    }

    private int getRed(int color) {
        return (color >> 16) & 255;
    }

    private int getGreen(int color) {
        return (color >> 8) & 255;
    }

    private int getBlue(int color) {
        return (color & 255);
    }

    private ArrayList<Point> doConvex(ArrayList<Point> p) {

    }

    private static class Point implements Comparable<Point> {
        public int r;
        public int c;

        public Point(int row, int col) {
            r = row;
            c = col;
        }

        @Override
        public int compareTo(Point other) {
            if(this.r == other.r) {
                return this.c - other.c;
            }
            return this.r - other.r;
        }
    }
}
