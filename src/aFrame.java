public class aFrame {

    public Point getCenter() {
        return center;
    }

    Point center;

    public double getSpeed() {
        return speed;
    }

    double speed;




    public double getDistanceFromCenter(){
        double dispX = center.getX() - Main.getDisplayWidth()/2;
        double dispY = center.getY() - Main.getDisplayHeight()/2;
        return Math.sqrt(dispX*dispX - dispY*dispY);
    }

}
