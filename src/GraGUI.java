import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GraGUI {

    private int weight;
    private int height;
    private JPanel planszaRysunku;
    private JPanel górnaTablica;
    private JPanel chat;
    private JFormattedTextField chatWpisz;
    private JPanel lewaStrona;
    private JPanel oknoGry;
    private JPanel ranking;
    private JPanel oknoChatu;
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField formattedTextField2;
    private JFormattedTextField formattedTextField3;
    private JFormattedTextField formattedTextField4;
    private JFormattedTextField formattedTextField5;

    public boolean czyKoniecGry; //0-nie, 1-tak

    private Gra gra;

    public GraGUI(){
        oknoGry.setPreferredSize(new Dimension(400, 600));

        oknoGry.setVisible(true);
    }


    public Gra getGra(){return gra;}
    public boolean getCzyKoniecGry(){ return czyKoniecGry;}
}
