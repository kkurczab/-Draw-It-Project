import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.util.Arrays;
import java.util.Collections;


public class GraGUI extends Thread{

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
    private JButton czarnyButton;
    private JProgressBar progressBar;
    private JFormattedTextField punkty1;
    private JFormattedTextField punkty2;
    private JFormattedTextField punkty3;
    private JButton zielonyButton;
    private JButton szaryButton;
    private JButton czerwonyButton;
    private JButton niebieskiButton;
    private JButton gumkaButton;
    private JFormattedTextField haslo;
    private JTextArea chatPole;
    private JButton wyslijButton;

    private Timer timer;
    private boolean czyKoniecGry; //0-nie, 1-tak
    private short currentX, currentY;
    private BufferedImage canvas;
    private int value;//zmiana value zmiana geubosci1do1
    private Byte color;//kazdy przycik to cyferka - patrz getColor()
    private boolean kuleczkaWladzy;
    private int liczbGraczy = 2;


    private Gra gra;
    private Player klient;
    private Server serwer;

    public GraGUI() {
        canvas = new BufferedImage(960, 1080, BufferedImage.TYPE_INT_RGB);
        gra = new Gra(liczbGraczy);
        klient = new Player();
        MenuGUI menuGUI = new MenuGUI(GraGUI.this);
        menuGUI.setVisible(true);

        //oknoGry.setPreferredSize(new Dimension(400, 600));
        value = 7;
        color = 3;//bialy
        colorScreen();
        planszaRysunku.addMouseMotionListener(new MouseMotionAdapter() {
            @Override

            public void mouseDragged(MouseEvent e) {
                if (kuleczkaWladzy) {
                    currentX = (short)e.getX();
                    currentY = (short)e.getY();
                    klient.setCurrentX(currentX);
                    klient.wyslijCurrentX();
                    klient.setCurrentY(currentY);
                    klient.wyslijCurrentY();
                    updateCanvas();

                }
            }
        });


        planszaRysunku.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (kuleczkaWladzy) {
                    currentX = (short)e.getX();
                    currentY = (short)e.getY();;
                    klient.setCurrentX(currentX);
                    klient.wyslijCurrentX();
                    klient.setCurrentY(currentY);
                    klient.wyslijCurrentY();
                    updateCanvas();
                }
            }
        });


        czarnyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(kuleczkaWladzy) {
                    color = 1;
                    klient.setKolor(color);
                    klient.wyslijKolor();
                }
            }
        });
        szaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(kuleczkaWladzy) {
                    color = 2;
                    klient.setKolor(color);
                    klient.wyslijKolor();
                }
            }
        });
        czerwonyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(kuleczkaWladzy) {
                    color = 4;
                    klient.setKolor(color);
                    klient.wyslijKolor();
                }
            }
        });
        zielonyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(kuleczkaWladzy) {
                    color = 5;
                    klient.setKolor(color);
                    klient.wyslijKolor();
                }
            }
        });
        niebieskiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(kuleczkaWladzy) {
                    color = 6;
                    klient.setKolor(color);
                    klient.wyslijKolor();
                }
            }
        });
        gumkaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(kuleczkaWladzy) {
                    color = 3;
                    klient.setKolor(color);
                    klient.wyslijKolor();
                }
            }
        });

        wyslijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String wiadomosc = chatWpisz.getText();
                if(!wiadomosc.equals("")) {
                    klient.setSlowo(chatWpisz.getText());
                    chatPole.append(gra.getGracze()[klient.getPlayerID()].getNazwaGracza() + ": " + chatWpisz.getText() + "\n");
                    klient.wyslijSlowo();
                    chatWpisz.setText("");
                    klient.setSlowo("");
                }
            }
        });
        //okno startowe



        //Pasek czasu------------------------------------------------------------------------

        //JOptionPane.showMessageDialog(null, progressBar);
        //Pasek czasu------------------------------------------------------------------------

    }


    public void czyZgadles(int x, GraGUI graGUI){
        //if(x == )
    }


    public static int getMinValue(Integer[] numbers){
        int minValue = numbers[0];
        for(int i=1;i<numbers.length;i++){
            if(numbers[i] < minValue){
                minValue = numbers[i];
            }
        }
        return minValue;
    }

    public void refresh() {//metoda oswiezajaca GUI
        //ustawienie rankingu graczy
        Integer[] ranking = new Integer[liczbGraczy];
        for(int i = 0; i < liczbGraczy; i++){
            ranking[i] = gra.getGracze()[i].getLiczbaPunktow();
        }
        Arrays.sort(ranking, Collections.reverseOrder()); //od najw. do najm. wartosci {100, 40, 20, 8, ... itd.}
        punkty1.setValue(ranking[0]);
        punkty2.setValue(ranking[1]);
       // punkty3.setValue(ranking[2]);


        int k = 1;
        Gracz[] listaGraczy = gra.getGracze();
        Integer[] x = ranking; //zmienne pomocnicze

        for(int i = 0; i < liczbGraczy; i++){ //RANKING
             if(getMinValue(ranking) == listaGraczy[i].getLiczbaPunktow()){
                 if(k==2){
                     imie1.setText(listaGraczy[i].getNazwaGracza());
                     k++;
                 }if(k==1){
                     imie2.setText(listaGraczy[i].getNazwaGracza());
                     k++;
                 }/*if(k==1){
                     imie3.setText(listaGraczy[i].getNazwaGracza());
                     k++;
                 }*/
                x = Arrays.copyOf(x, x.length - 1); //usuwa ostatni element z "x"
            }
        }

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

        progressBar = new JProgressBar(JProgressBar.VERTICAL, 0, 10);
        progressBar.setValue(10);
        ActionListener listener = new ActionListener() {
            int counter = 10;

            public void actionPerformed(ActionEvent ae) {
                counter--;
                progressBar.setValue(counter);
                if (counter < 1) {
                    JOptionPane.showMessageDialog(null, "Koniec czasu!");
                    timer.stop();
                }
            }
        };
        timer = new Timer(1000, listener);

    }
    public void updateCanvas() {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setPaint(getColor());
        g2d.fillOval(currentX - (value / 2), currentY - (value / 2), value, value);
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


    public void stworzGre(){
        serwer = new Server();
        serwer.przyjeciePolaczenia();

    }
    public void dolaczDoGry(){
        klient.polaczZSerwerem();
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("GraGUI");
        frame.setContentPane(new GraGUI().oknoGry);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
    public void run(){
        dolaczDoGry();
        gra.setSeed(klient.getSeed());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }if(klient.getNrGraczaUWladzy()==klient.getPlayerID())
            haslo.setText(gra.getHaslo());



            timer.start();
        while (true){
            //imie1.setText(gra.getGracze()[0].getNazwaGracza());
            //imie2.setText(gra.getGracze()[1].getNazwaGracza());
            refresh();

            synchronizeValues();


        }
    }

    public Player getKlient() {
        return klient;
    }


        private void synchronizeValues(){
            if(gra.getGracze()[0].getNazwaGracza().equals("")){
                try {
                    gra.getGracze()[0].setNazwaGracza(klient.getImiona()[0]);
                }catch (IllegalArgumentException e){}
                imie1.setText(gra.getGracze()[0].getNazwaGracza());
            }
            if(gra.getGracze()[1].getNazwaGracza().isBlank()){
                try {
                    gra.getGracze()[1].setNazwaGracza(klient.getImiona()[1]);
                }catch (IllegalArgumentException e){}
                imie2.setText(gra.getGracze()[1].getNazwaGracza());
            }

            if(klient.getNrGraczaUWladzy()==klient.getPlayerID()){
                kuleczkaWladzy = true;
            }
            else{

                kuleczkaWladzy = false;
                color = klient.getKolor();
                currentX = klient.getCurrentX();
                currentY = klient.getCurrentY();
                updateCanvas();

            }
            if(!klient.getSlowo().isBlank() && klient.getPlayerID() != klient.getOtrzymanyNR()){
                chatPole.append(gra.getGracze()[klient.getOtrzymanyNR()].getNazwaGracza() + ": " + klient.getSlowo() + "\n");
                klient.setOtrzymanyNR((short) -1);
                klient.setSlowo("");
            }


        }

}
