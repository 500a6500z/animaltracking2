import javax.swing.*;

public class getDataSetFromFile {

    public static void main(String[] args) {
        DataSet d = new DataSet(JOptionPane.showInputDialog("filename "));
        d.print();
    }
}
