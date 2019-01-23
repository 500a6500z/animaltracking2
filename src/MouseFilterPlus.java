import processing.core.PApplet;

import java.util.ArrayList;

//do not use
public class MouseFilterPlus implements PixelFilter {

    private static final int TARGET = 30;
    private int rAvg;
    private int cAvg;

    private short[][] arrx = {{-1, 0, 1},
                            {-2, 0, 2},
                            {-1, 0, 1}};

    private final kernel Gx = new kernel(arrx);

    private short[][] arry = {{1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1}};
    private final kernel Gy = new kernel(arry);

    private short[][] arr1 = {{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};
    private kernel thin1 = new kernel(arr1);

    private short[][] arr2 = {{0, 0, 0}, {1, 1, 0}, {0, 1, 0}};
    private kernel thin2 = new kernel(arr2);

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
    class kernel {
        public short[][] getKernel() {
            return kernel;
        }

        private short[][] kernel;
        private short height, width, weight;


        public void rotate(){
            short[][] temp = new short[height][width];
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    temp[r][c] = kernel[c][width - r - 1];
                }
            }
            kernel = temp;
        }


        public short getHeight() {
            return height;
        }

        public short getWidth() {
            return width;
        }

        public short getWeight() {
            return weight;
        }

        public kernel(short[][] kernel) {
            this.kernel = kernel;
            height = (short)kernel.length;
            width = (short)kernel[0].length;
            weight = sum(kernel);
        }

        private short sum(short[][] kernel) {
            short sum = 0;
            for (short[] row : kernel) {
                for (int col = 0; col < width; col++) {
                    sum += row[col];
                }
            }
            if(sum == 0) {
                return 1;
            }

            return sum;
        }

        public String toString(){
            String str = "";
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    str += kernel[row][col] + ", ";
                }
                str += "\n";
            }
            return str;
        }
    }
}

//public class SobelEdgeDetection implements PixelFilter {
//    private short[][] arrx = {{-1, 0, 1},
//            {-2, 0, 2},
//            {-1, 0, 1}};
//
//    private final kernel Gx = new kernel(arrx);
//
//    private short[][] arry = {{1, 2, 1},
//            {0, 0, 0},
//            {-1, -2, -1}};
//    private final kernel Gy = new kernel(arry);
//
//    private short[][] arr1 = {{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};
//    private kernel thin1 = new kernel(arr1);
//
//    private short[][] arr2 = {{0, 0, 0}, {1, 1, 0}, {0, 1, 0}};
//    private kernel thin2 = new kernel(arr2);
//
//    @Override
//    public int[] filter(int[] pixels, int width, int height) {
//        short[][] im = PixelLib.convertTo2dArray(PixelLib.convertToShortGreyscale(pixels), width, height);
//        short[][] output = new short[height][width];
//        short sumX, sumY, valX, valY;
//
//        for (int r = 0; r < im.length - Gx.getHeight(); r++) {
//            for (int c = 0; c < im[0].length - Gx.getWidth(); c++) {
//                sumX = 0;
//                sumY = 0;
//                for (int i = 0; i < Gx.getHeight(); i++) {
//                    for (int j = 0; j < Gx.getWidth(); j++) {
//                        sumX += im[r + i][c + j] * Gx.getKernel()[i][j];
//                        sumY += im[r + i][c + j] * Gy.getKernel()[i][j];
//                    }
//                }
//
//                sumX /= Gx.getWeight();
//                sumY /= Gy.getWeight();
//
//                valX = threshold(sumX);
//                valY = threshold(sumY);
//
//                output[r + Gx.getHeight() / 2][c + Gx.getWidth() / 2] = (short) Math.sqrt(valX * valX + valY * valY);
//            }
//        }
//
//
//        int sum;
//        for (int t = 0; t < 4; t++) {
//            for (int r = 0; r < im.length - thin1.getHeight(); r++) {
//                for (int c = 0; c < im[0].length - thin1.getWidth(); c++) {
//                    sum = 0;
//                    for (int i = 0; i < thin1.getHeight(); i++) {
//                        for (int j = 0; j < thin1.getHeight(); j++) {
//                            sum += output[r + i][c + j] * thin1.getKernel()[i][j];
//                        }
//                    }
//                    im[r + thin1.getHeight() / 2][c + thin1.getWidth() / 2] = (short) (sum / thin1.getWeight());
//                }
//            }
//            for (int r = 0; r < im.length - thin2.getHeight(); r++) {
//                for (int c = 0; c < im[0].length - thin2.getWidth(); c++) {
//                    sum = 0;
//                    for (int i = 0; i < thin2.getHeight(); i++) {
//                        for (int j = 0; j < thin2.getHeight(); j++) {
//                            sum += im[r + i][c + j] * thin2.getKernel()[i][j];
//                        }
//                    }
//                    output[r + thin2.getHeight() / 2][c + thin2.getWidth() / 2] = (short) (sum / thin2.getWeight());
//                }
//            }
//            thin1.rotate();
//            thin2.rotate();
//        }
//        PixelLib.fill1dArray(output, pixels);
//        return pixels;
//    }
//
//    private short threshold(short sum) {
//        if (sum < 255 / 2) {
//            return 0;
//        } else {
//            return 255;
//        }
//    }
//
//}
