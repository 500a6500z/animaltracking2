import java.util.ArrayList;

public interface DS {


    public ArrayList<Interval> getIntervalsInInnerRegion();

    public ArrayList<Interval> getIntervalsInOuterRegion();

    public double getTimeSpentInInnerRegion();

    public double getTimeSpentInOuterRegion();

    public void add(Point f);

    public double getTotalDistanceTraveled(double start, double end);

    public ArrayList<Point> getData();

    public Point getMouseLocation(double time);

    public double getMouseSpeed(double time);

    public double getAverageSpeed(double start, double end);

    public double getDistanceFromWall(double time);

}
