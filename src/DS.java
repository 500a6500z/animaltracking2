import java.util.ArrayList;

public interface DS {

    public ArrayList<Interval> getIntervalsInInnerRegion();

    public ArrayList<Interval> getIntervalsInOuterRegion();

    public ArrayList<Interval> getIntervalsAtSpeed(double speed, double threshold);

    public double getTimeSpentInInnerRegion();

    public double getTimeSpentInOuterRegion();

    public double getTimeSpentAtSpeed(double speed, double threshold);

    public void add(Point f);

    public double getTotalDistanceTraveled(double start, double end);

    public ArrayList<Point> getData();

    public Point getMouseLocation(double time);

    public double getMouseSpeed(double time);

    public double getAverageSpeed(double start, double end);

    public double getDistanceFromWall(double time);

}
