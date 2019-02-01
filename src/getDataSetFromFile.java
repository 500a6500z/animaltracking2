public class getDataSetFromFile {

    public static void main(String[] args) {
        DataSet d = new DataSet("MouseData.csv");
        System.out.println("total distance traveled: " + d.getTotalDistanceTraveled(0, 399));
        System.out.println("time spent moving slower than 3 cm/s: " + d.getTimeSpentAtSpeed(1.5, 1.5));
        double max = 0;
        for(int i = 0; i < d.getData().size(); i++) {
            if(max < d.getMouseSpeed(i)) {
                max = d.getMouseSpeed(i);
            }
        }
        System.out.println("max speed: " + max);
        System.out.println("time spent at 80%: " + d.getTimeSpentAtSpeed(.9 * max, .1 * max));

    }
}
