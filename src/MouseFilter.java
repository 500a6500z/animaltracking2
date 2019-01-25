import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashSet;

public class MouseFilter implements PixelFilter {

    private static final int TARGET = 30;
    private int rAvg;
    private int cAvg;
    private HashSet<Integer> path;

    public MouseFilter(){
        path = new HashSet<>();
    }

    @Override
    public DImage processImage(DImage img) {
        ArrayList<Point> list = new ArrayList<>();
        short[][] pixels = img.getBWPixelGrid();

        for(int r = 0; r < pixels.length; r++) {
            for(int c = 0; c < pixels[r].length; c++) {
                if(Math.abs((int) pixels[r][c] - TARGET) <= 7) {
                    pixels[r][c] = 0;
                }
                else {
                    pixels[r][c] = 255;
                }
            }
        }

        for(int r = 0; r < pixels.length - 2; r++) {
            for(int c = 0; c < pixels[r].length - 2; c++) {
                short avg = 0;
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        avg += pixels[r + i][c + j];
                    }
                    pixels[r + 1][c + 1] = (short) (avg / 9);
                }
            }
        }

        for(int r = 0; r < pixels.length; r++) {
            for(int c = 0; c < pixels[r].length; c++) {
                if(Math.abs((int) pixels[r][c] - TARGET) <= 7) {
                    pixels[r][c] = 0;
                    list.add(new Point(r, c));
                }
                else {
                    pixels[r][c] = 255;
                }
            }
        }

        int rAvg = 0;
        int cAvg = 0;

        for(int i = 0; i < list.size(); i++) {
            rAvg += list.get(i).r;
            cAvg += list.get(i).c;
        }

        rAvg /= list.size();
        cAvg /= list.size();

        img.setPixels(pixels);

        int[][] cPixels = img.getColorPixelGrid();

        path.add((rAvg * img.getHeight()) + cAvg);
        for(Integer previousPoint : path) {
            int pathRow = previousPoint / img.getHeight();
            int pathColumn = previousPoint % img.getHeight();
            cPixels[pathRow][pathColumn] = 	16776960;
        }

        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                cPixels[rAvg + i][cAvg + j] = 0xFFFF0000;
            }
        }

        cPixels[100][100] = 16776960;
        cPixels[99][99] = 16776960;
        cPixels[98][98] = 16776960;

        img.setPixels(cPixels);

        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }

    static class Point {
        public int r;
        public int c;

        public Point(int row, int col) {
            r = row;
            c = col;
        }

        public int compareTo(Point other) {
            if(this.r == other.r) {
                return this.c - other.c;
            }
            return this.r - other.r;
        }
    }
}