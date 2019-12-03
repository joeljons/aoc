package se.timotej.advent.y2018;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static se.timotej.advent.y2018.Util.findAllInts;

public class Advent10_visualize {

    public static void main(String[] args) throws IOException {
        int svar = new Advent10_visualize().calc(Online.get());
        //System.out.println("svar = " + svar);
        // Online.submit(svar);
    }


    private int calc(List<String> strs) throws IOException {
        int svar = 0;
        List<Point> points = new ArrayList<>();
        for (String str : strs) {
            List<Integer> line = findAllInts(str);
            points.add(new Point(line.get(0),
                    line.get(1),
                    line.get(2),
                    line.get(3)));

        }
        long lastSize = Long.MAX_VALUE;
        for (int i = 0; ; i++) {
            int minx = points.stream().mapToInt(Point::getX).min().getAsInt();
            int maxx = points.stream().mapToInt(Point::getX).max().getAsInt();
            int miny = points.stream().mapToInt(Point::getY).min().getAsInt();
            int maxy = points.stream().mapToInt(Point::getY).max().getAsInt();
            long size = (maxy - miny) * (long) (maxx - minx);
            if (size > lastSize) {
                System.out.println("i = " + (i - 1));
                break;
            }
            lastSize = size;
            for (Point point : points) {
                point.x += point.dx;
                point.y += point.dy;
            }
        }
        for (Point point : points) {
            point.x -= point.dx;
            point.y -= point.dy;
        }
        int minx = points.stream().mapToInt(Point::getX).min().getAsInt();
        int maxx = points.stream().mapToInt(Point::getX).max().getAsInt();
        int miny = points.stream().mapToInt(Point::getY).min().getAsInt();
        int maxy = points.stream().mapToInt(Point::getY).max().getAsInt();
        System.out.println(maxy - miny + 1);
        System.out.println(maxx - minx + 1);
        minx -= 10;
        miny -= 10;
        maxx += 10;
        maxy += 10;
        //boolean[][] g = new boolean[maxy - miny + 1][maxx - minx + 1];
        int PIXEL_HEIGHT = 10;
        int PIXEL_WIDTH = 10;
        List<Color> colors = new ArrayList<>();
        Random r = new Random();
        for (Point point : points) {
            colors.add(new Color(r.nextInt(200) + 55, r.nextInt(200) + 55, r.nextInt(200) + 55));
        }
        for (int i = 0; i <= 400; i++) {
            double tid = (i - 200d) / 10;
            BufferedImage img = new BufferedImage((maxx - minx) * PIXEL_WIDTH, (maxy - miny) * PIXEL_HEIGHT,
                    BufferedImage.TYPE_INT_RGB);
            Graphics graphics = img.getGraphics();
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, (maxx - minx) * PIXEL_WIDTH, (maxy - miny) * PIXEL_HEIGHT);
            Iterator<Color> colorIterator = colors.iterator();
            for (Point point : points) {
                graphics.setColor(colorIterator.next());
                graphics.fillOval((int) ((point.x + point.dx*tid - minx) * PIXEL_HEIGHT), (int) ((point.y +point.dy*tid- miny) * PIXEL_HEIGHT), PIXEL_WIDTH,
                        PIXEL_WIDTH);
            }
            ImageIO.write(img, "png", new File(String.format("%03d.png", i)));
            if(i==200){
                for(int j=0;j<20;j++){
                    ImageIO.write(img, "png", new File(String.format("%03d_%02d.png", i,j)));

                }
            }
        }

        return svar;
    }

    class Point {
        int x, y;
        int dx, dy;

        public Point(int x, int y, int dx, int dy) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

}
