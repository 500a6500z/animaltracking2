import processing.core.PApplet;

import java.util.ArrayList;

public class MouseFilter implements PixelFilter {

    private static final int TARGET = 30;
    private int rAvg;
    private int cAvg;

    @Override
    public DImage processImage(DImage img) {
        //DImage copy = new DImage(img);
        ArrayList<Point> list = new ArrayList<>();
        short[][] pixels = img.getBWPixelGrid();

        //color mask
        for(int r = 0; r < pixels.length; r++) {
            for(int c = 0; c < pixels[r].length; c++) {
                if(Math.abs((int) pixels[r][c] - TARGET) <= 5) {
                    pixels[r][c] = 0;
                }
                else {
                    pixels[r][c] = 255;
                }
            }
        }

        //blur
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

        //color mask 2
        for(int r = 0; r < pixels.length; r++) {
            for(int c = 0; c < pixels[r].length; c++) {
                if(Math.abs((int) pixels[r][c] - TARGET) <= 5) {
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

        //get black points
        for(int i = 0; i < list.size(); i++) {
            rAvg += list.get(i).r;
            cAvg += list.get(i).c;
        }

        //calculate center
        rAvg /= list.size();
        cAvg /= list.size();

        img.setPixels(pixels);

        int[][] cPixels = img.getColorPixelGrid();

        //set red center
//        for(int i = -1; i < 2; i++) {
//            for(int j = -1; j < 2; j++) {
//                cPixels[rAvg + i][cAvg + j] = 0xFFFF0000;
//            }
//        }

        img.setPixels(cPixels);

        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        window.fill(255,0,0);
        window.ellipse(cAvg,rAvg, 10,10);
    }


    //change
    static class Point implements Comparable<Point> {
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