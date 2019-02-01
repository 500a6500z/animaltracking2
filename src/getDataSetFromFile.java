public class getDataSetFromFile {

    private static final int TARGET_RADIUS = 200;
    private static final int CENTER_COLUMN = 308;
    private static final int CENTER_ROW = 232;
    private static final int FIELD_RADIUS = 240;
    private static final int INNER_RADIUS = 53;
    private static Point center = new Point(CENTER_ROW, CENTER_COLUMN);


    public static void main(String[] args) {

        DataSet d = new DataSet("MouseData.csv",25, 5.333, center, FIELD_RADIUS, INNER_RADIUS);
        System.out.println("total distance traveled: " + d.getTotalDistanceTraveled());
        System.out.println("time spent moving slower than 3 cm/s: " + d.getTimeSpentAtSpeed(1.5, 1.5));
        double max = d.getMaxSpeed();
        System.out.println("max speed: " + max);
        System.out.println("time spent at 80%: " + d.getTimeSpentAtSpeed(.9 * max, .1 * max));

        System.out.println("total time spent in inner 10cm: " + d.getTimeSpentInInnerRegion());
        System.out.println("percent of time spent in inner 10cm: " + (d.getTimeSpentInInnerRegion()/399)*100);


    }
}
