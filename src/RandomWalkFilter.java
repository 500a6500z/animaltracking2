import processing.core.PApplet;

import java.util.ArrayList;

public class RandomWalkFilter implements PixelFilter {

    private ArrayList<Point> list;

    public RandomWalkFilter() {
        list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            list.add(new Point(200, 200));
        }
    }

    @Override
    public DImage processImage(DImage img) {
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        for(int i = 0; i < list.size(); i++) {
            window.ellipse(list.get(i).c, list.get(i).r, 10, 10);
        }
        for(int i = 0; i < list.size(); i++) {
            list.get(i).takeRandomStep();
        }
    }

    private static class Point {
        public int r;
        public int c;
        private static int[] dR = {-1, 0, 1, 0};
        private static int[] dC = {0, 1, 0, -1};
        private static final int MULTIPLIER = 5;

        public Point(int row, int col) {
            r = row;
            c = col;
        }

        public void takeRandomStep() {
            int random = (int) (Math.random() * 4);
            r += dR[random] * MULTIPLIER;
            c += dC[random] * MULTIPLIER;
        }
    }
}
