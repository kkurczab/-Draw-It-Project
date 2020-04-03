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
    private JPanel chatPokaz;
    private JPanel oknoGry;

    public GraGUI(){
        oknoGry.setPreferredSize(new Dimension(400, 600));

        oknoGry.setVisible(true);
    }
}
