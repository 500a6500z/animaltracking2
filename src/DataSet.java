import java.util.ArrayList;

public class DataSet {

    private ArrayList<aFrame> data;


    private double distanceTraveled;
    private double timeSpentInMiddle;
    private double timeSpentInOuterRing;


    public DataSet() {
        this.data = new ArrayList<>();
        this.distanceTraveled = 0;
    }

    public void add(aFrame f){
        Point p = data.get(data.size()).getCenter();
        distanceTraveled += Math.sqrt((p.getX() - f.getCenter().getX())*(p.getX() - f.getCenter().getX()) +
                (p.getY() - f.getCenter().getY())*(p.getY() - f.getCenter().getY()));
        data.add(f);
    }

    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    public ArrayList<aFrame> getData() {
        return data;
    }

    public aFrame getFrame(int index){//need to change index to time
        return data.get(index);
    }

    public double getAverageSpeed(){
        double tot = 0;
        for (int i = 0; i < data.size(); i++) {
            tot+=data.get(i).getSpeed();
        }
        return tot/data.size();
    }

}
