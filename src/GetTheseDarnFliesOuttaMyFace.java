import processing.core.PApplet;

import javax.swing.*;
import java.util.ArrayList;

public class GetTheseDarnFliesOuttaMyFace implements PixelFilter {
    private DImage previousImg = null;
    private DImage filtered = null;
    private static final int THRESHOLD = 30;
    private ArrayList<Point> list = null;
    private int score;

    public GetTheseDarnFliesOuttaMyFace() {
        boolean done = false;
        while(!done) {
            String str = JOptionPane.showInputDialog("you gonna get those darn flies outta your face? (Y)");
            if(str.equals("Y")) {
                done = true;
            }
        }
        score = 0;
    }

    @Override
    public DImage processImage(DImage img) {
        if(previousImg == null) {
            previousImg = new DImage(img);
            return img;
        }

        short[][] current = img.getBWPixelGrid();
        short[][] prev = previousImg.getBWPixelGrid();

        for(int r = 0; r < img.getHeight(); r++) {
            for(int c = 0; c < img.getWidth(); c++) {
                if(Math.abs(current[r][c] - prev[r][c]) >= THRESHOLD) {
                    current[r][c] = 255;
                }
                else {
                    current[r][c] = 0;
                }
            }
        }

        previousImg = new DImage(img);

        filtered = new DImage(img);
        filtered.setPixels(current);

        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage f) {
        if(list == null) {
            list = new ArrayList<>();
            for(int i = 0; i < 100; i++) {
                int row = (int) (Math.random() * original.getHeight());
                int col = (int) (Math.random() * original.getWidth());
                list.add(new Point(row, col));
            }
        }

        if(filtered == null) {
            return;
        }

        short[][] pixels = filtered.getBWPixelGrid();

        for(int i = list.size() - 1; i >= 0; i--) {
            int r = list.get(i).r;
            int c = list.get(i).c;

            if(r > original.getHeight() - 20 || r < 20 || c > original.getWidth() - 20 || c < 20) {
                list.remove(i);
                score++;
                continue;
            }

            int ct = 0;

            for(int k = r - 5; k <= r + 5; k++) {
                for(int j = c - 5;  j <= c + 5; j++) {
                    if(pixels[k][j] == 255) {
                        ct++;
                    }
                }
            }

            if(ct > 90) {
                score++;
                list.remove(i);
            }
        }

        for(int i = 0; i < list.size(); i++) {
            window.ellipse(list.get(i).c, list.get(i).r, 15, 15);
        }
        for(int i = 0; i < list.size(); i++) {
            list.get(i).takeRandomStep();
        }

        window.text("score: " + score, 200, 200);
    }

    private static class Point {
        public int r;
        public int c;
        private static int[] dR = {-1, 0, 1, 0};
        private static int[] dC = {0, 1, 0, -1};
        private static final int MULTIPLIER = 3;

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
