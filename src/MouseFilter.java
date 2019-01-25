import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashSet;

public class MouseFilter implements PixelFilter {

    private static final int TARGET = 30;
    private int rAvg;
    private int cAvg;
    private ArrayList<Point> path;
    private DataSet ds;

    public MouseFilter(){
        path = new ArrayList<>();
        ds = new DataSet(25, 0.1875);
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

        rAvg = 0;
        cAvg = 0;

        for(int i = 0; i < list.size(); i++) {
            rAvg += list.get(i).r;
            cAvg += list.get(i).c;
        }

        rAvg /= list.size();
        cAvg /= list.size();

        /*
        Add DataSet Centers Here
         */
        //ds.add(new aFrame(new Point(rAvg, cAvg), ));

        img.setPixels(pixels);

        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        window.stroke(0, 255, 255);
        System.out.println(path.size());
        for(int i = 1; i < path.size(); i++) {
            int pathRow1 = path.get(i).r;
            int pathCol1 = path.get(i).c;
            int pathRow2 = path.get(i - 1).r;
            int pathCol2 = path.get(i - 1).c;
            window.line(pathCol1, pathRow1, pathCol2, pathRow2);
        }

        window.fill(255, 0, 0);
        window.ellipse(cAvg, rAvg, 5, 5);

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