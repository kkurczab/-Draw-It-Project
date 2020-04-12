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
    private double value;//grubosc pedzla do zmiany, tak do 50 grubosc, jakis slider bylby spoko, zmiana value zmiana geubosci1do1
    private int color;//kazdy przycik to cyferka - patrz getColor()//mozna mniej kolorow jak cos, przycisk gumki zmiana koloru na bialo XD


    private Gra gra;

    public GraGUI(){

        color = 1;
        value = 15;
        gra = new Gra(5);
        //oknoGry.setPreferredSize(new Dimension(400, 600));

        oknoGry.setVisible(true);
        canvas = new BufferedImage(1920,1080,BufferedImage.TYPE_INT_RGB);
        //mozna uladnic potencjalnie to ale sa wazniejsze rzeczy
        int tmpCol = color;
        color = 3;//bialy
        colorScreen();
        color = tmpCol;
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



        zakończGręButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        //okno startowe
        MenuGUI menuGUI = new MenuGUI(GraGUI.this);
        menuGUI.setVisible(true);
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
       g2d.setPaint(getColor());

        if (clicked)
            g2d.fillOval(oldX - ((int) value / 2), oldY - ((int) value / 2), (int) value, (int) value);
        else {
            g2d.setStroke(new BasicStroke((float) value, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(oldX, oldY, currentX, currentY);
            g2d.setStroke(new BasicStroke(1.0f));
        }
        planszaRysunku.repaint();
    }
    private Color getColor() {
        Color c = null;
        switch (color) {
            case 1:
                c = Color.black;
                break;
            case 2:
                c = Color.gray;
                break;
            case 3:
                c = Color.white;
                break;
            case 4:
                c = Color.red;
                break;
            case 5:
                c = Color.green;
                break;
            case 6:
                c = Color.blue;
                break;
        }
        return c;
    }
    private void colorScreen() {//przycisk kolorujacy caly ekran(potencajlna funcja), ustawaijac bialy kolor sluzy do czyszczenia
        Graphics g = canvas.getGraphics();
        g.setColor( getColor());
        g.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        planszaRysunku.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GraGUI");
        frame.setContentPane(new GraGUI().oknoGry);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


/////


    public void refresh(){}
}
