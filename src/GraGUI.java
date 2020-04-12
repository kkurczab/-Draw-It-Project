import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.util.Arrays;
import java.util.Collections;


public class GraGUI {

    private int weight;
    private int height;
    private JPanel planszaRysunku;
    private JPanel górnaTablica;
    private JPanel chat;
    private JFormattedTextField chatWpisz;
    private JPanel lewaStrona;
    private JPanel oknoGry;
    private JPanel rankingGUI;
    private JPanel oknoChatu;
    private JFormattedTextField imie1;
    private JFormattedTextField imie2;
    private JFormattedTextField imie3;
    private JFormattedTextField imie4;
    private JFormattedTextField imie5;
    private JButton zakończGręButton;
    private JProgressBar progressBar1;
    private JFormattedTextField punkty1;
    private JFormattedTextField punkty2;
    private JFormattedTextField punkty3;
    private JFormattedTextField punkty4;
    private JFormattedTextField punkty5;
    private JFormattedTextField formattedTextField1;

    public boolean czyKoniecGry; //0-nie, 1-tak
    private int currentX, currentY, oldX, oldY;
    private BufferedImage canvas;
    private boolean clicked;
    private int value =15;
    //double value = 5;

    private Gra gra;

    public GraGUI(){
        gra = new Gra(5);
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

    public void refresh() {//metoda oswiezajaca GUI
        //ustawienie rankingu graczy
        Integer[] ranking = new Integer[5];
        for(int i = 0; i < 5; i++){
            ranking[i] = gra.getGracze()[i].getLiczbaPunktow();
        }
        Arrays.sort(ranking, Collections.reverseOrder()); //od najw. do najm. wartosci {100, 40, 20, 8, ... itd.}
        punkty1.setValue(ranking[0]);
        punkty2.setValue(ranking[1]);
        punkty3.setValue(ranking[2]);
        punkty4.setValue(ranking[3]);
        punkty5.setValue(ranking[4]);
        Gracz[] listaGraczy = gra.getGracze();
        for(int i = 0; i < 5; i++){
            Integer[] x = ranking; //zmienne pomocnicze
            for(int j = 0; j < 5; j++){
                if(ranking[ranking.length - 1 - i] == gra.getGracze()[j].getLiczbaPunktow());
                    imie5.setText(gra.getGracze()[j].getNazwaGracza());
                    Arrays.copyOf(x, x.length-1); //usuwa ostatni element z "x"
            }
        }

        //imie1.setText(gra.getGracze()[gra.getNrGracza()].getNazwaGracza());

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

}
