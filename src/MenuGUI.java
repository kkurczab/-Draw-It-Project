import javax.swing.*;
import java.awt.event.*;

public class MenuGUI extends JDialog{
    private JButton STARTTHEGAMEButton;
    private JPanel menuGry;
    private JFormattedTextField imie1;
    private JFormattedTextField imie2;
    private JFormattedTextField imie3;
    private JFormattedTextField imie4;
    private JFormattedTextField imie5;

    public MenuGUI(GraGUI graGUI){
        setTitle("MENU");
        setContentPane(menuGry);
        setModal(true);
        getRootPane().setDefaultButton(STARTTHEGAMEButton);
        pack();

        STARTTHEGAMEButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try{
                        graGUI.getGra().getGracze()[0].setNazwaGracza(imie1.getText());
                        graGUI.getGra().getGracze()[1].setNazwaGracza(imie2.getText());
                        graGUI.getGra().getGracze()[2].setNazwaGracza(imie3.getText());
                        graGUI.getGra().getGracze()[3].setNazwaGracza(imie4.getText());
                        graGUI.getGra().getGracze()[4].setNazwaGracza(imie5.getText());
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

        STARTTHEGAMEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    graGUI.getGra().getGracze()[0].setNazwaGracza(imie5.getText());
                    graGUI.getGra().getGracze()[1].setNazwaGracza(imie5.getText());
                    graGUI.getGra().getGracze()[2].setNazwaGracza(imie5.getText());
                    graGUI.getGra().getGracze()[3].setNazwaGracza(imie5.getText());
                    graGUI.getGra().getGracze()[4].setNazwaGracza(imie5.getText());
                    dispose();
                    if(graGUI.czyKoniecGry != false){
                        KoniecGry koniecGry = new KoniecGry(graGUI);
                        koniecGry.setVisible(true);
                    }
                    else {
                        NowaTura nowaTura = new NowaTura(graGUI);
                        nowaTura.setVisible(true);
                    }
                }catch (IllegalArgumentException ee){
                    JOptionPane.showMessageDialog(null, "Podaj imiona wszystkich 4 graczy!");
                }
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowAdapter e){System.exit(0);}
        });
    }
}
