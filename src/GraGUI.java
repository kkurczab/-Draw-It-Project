import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

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
    int currentX, currentY, oldX, oldY;
    BufferedImage canvas;
    private boolean clicked;
    private int value =15;
    //double value = 5;

    private Gra gra;

    public GraGUI(){
        oknoGry.setPreferredSize(new Dimension(400, 600));

        oknoGry.setVisible(true);
        canvas = new BufferedImage(400,600,BufferedImage.TYPE_INT_RGB);

        planszaRysunku.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                currentX = e.getX();
                currentY = e.getY();
                updateCanvas();
                oldX = currentX;
                oldY = currentY;
            }
        });


        planszaRysunku.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clicked = true;
                oldX = e.getX();
                oldY  = e.getY();
                updateCanvas();
                clicked = false;

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GraGUI");
        frame.setContentPane(new GraGUI().oknoGry);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Gra getGra(){return gra;}
    public boolean getCzyKoniecGry(){ return czyKoniecGry;}

    private void createUIComponents() {

            planszaRysunku = new JPanel(){

                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.drawImage(canvas, 0, 0, null);
                }

            };



    }
    public void updateCanvas() {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Color.red);

        if (clicked==true)
            g2d.fillOval(oldX - ((int) value / 2), oldY - ((int) value / 2), (int) value, (int) value);
        else {
            g2d.setStroke(new BasicStroke((float) value, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(oldX, oldY, currentX, currentY);
            g2d.setStroke(new BasicStroke(1.0f));
        }
        planszaRysunku.repaint();
    }



/////


    public void refresh(){}
}
