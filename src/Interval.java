public class Interval {

    public Interval(double ti) {
        Ti = ti;
    }

    public double getTi() {
        return Ti;
    }


    public double getTf() {
        return Tf;
    }

    public void setTf(double tf) {
        Tf = tf;
    }

    public double getDuration() {
        return Tf - Ti;
    }

    double Ti,Tf;

}
