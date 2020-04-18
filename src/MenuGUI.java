import javax.swing.*;
import java.awt.event.*;
import javax.swing.JPanel;

public class MenuGUI extends JDialog{
    private JButton HOSTGAMEButton;
    private JPanel menuGry;
    private JFormattedTextField imie1;
    private JButton JOINButton;
    private JPanel P;

    public MenuGUI(GraGUI graGUI){
        setTitle("MENU");
        setContentPane(menuGry);
        setModal(true);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(HOSTGAMEButton);
        getRootPane().setDefaultButton(JOINButton);
        setLocation(430,200);
        pack();

        HOSTGAMEButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try{
                        graGUI.getGra().getGracze()[0].setNazwaGracza(imie1.getText());
                        dispose();
                        if(graGUI.czyKoniecGry != false){
                            KoniecGry koniecGry = new KoniecGry(graGUI);
                            koniecGry.setVisible(true);
                        }
                        else{
                            NowaTura nowaTura = new NowaTura(graGUI);
                            nowaTura.setVisible(true);
                        }
                    }catch (IllegalArgumentException ee){
                        JOptionPane.showMessageDialog(null, "Podaj imiona wszystkich 5 graczy!");
                    }
                }
            }
        });

        HOSTGAMEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{

                    if(graGUI.czyKoniecGry != false){
                        KoniecGry koniecGry = new KoniecGry(graGUI);
                        koniecGry.setVisible(true);
                    }
                    else {
                        if(graGUI.getKlient().getPlayerID() == -5) {//brak wczesniejszych udanych polaczen z serwerem
                            new Thread(() -> graGUI.stworzGre()).start();
                            graGUI.start();
                            //WaitingGUI waitingGUI = new WaitingGUI(graGUI);
                            //waitingGUI.setVisible(true);
                        }
                        Thread.sleep(1000);
                        short otrzymanyNR = graGUI.getKlient().getOtrzymanyNR();
                        if(graGUI.getKlient().getPlayerID() != otrzymanyNR){
                            graGUI.getGra().getGracze()[otrzymanyNR].setNazwaGracza(graGUI.getKlient().getSlowo());
                        }
                        graGUI.getGra().getGracze()[graGUI.getKlient().getPlayerID()].setNazwaGracza(imie1.getText());
                        graGUI.getKlient().setSlowo(imie1.getText());
                        graGUI.getKlient().wyslijSlowo();
                    }
                    dispose();
                }catch (IllegalArgumentException | InterruptedException ee){
                    JOptionPane.showMessageDialog(null, "Podaj imię!");
                }
            }
        });

        JOINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    OknoHasła oknoHasła = new OknoHasła(graGUI);
                    oknoHasła.setVisible(true);
                    if(graGUI.getKlient().getPlayerID() == -5){//brak wczesniejszych udanych polaczen z serwerem
                        graGUI.start();
                    }
                    Thread.sleep(1000);

                    graGUI.getGra().getGracze()[graGUI.getKlient().getPlayerID()].setNazwaGracza(imie1.getText());
                    graGUI.getKlient().setSlowo(imie1.getText());
                    graGUI.getKlient().wyslijSlowo();
                    dispose();


                }catch (IllegalArgumentException | InterruptedException ee){
                    JOptionPane.showMessageDialog(null, "Podaj imię!");
                }
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowAdapter e){System.exit(0);}
        });
    }


}
