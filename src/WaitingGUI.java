import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WaitingGUI extends JDialog{
    private JPanel P;
    private JFormattedTextField liczbaObecnychGraczy;

    public WaitingGUI(GraGUI graGUI){
        setContentPane(P);
        setModal(true);
        setUndecorated(true);
        pack();
        liczbaObecnychGraczy.addActionListener(new ActionListener() { //tutaj listener na liczbe graczy, ktorzy juz dolaczyli (zaczynam od 1)
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int k = 1;
                //...
                //moge to napisac jutro
                k=2;
                if(k == 2){
                    dispose();
                   // NowaTura nowaTura = new NowaTura(graGUI);
                   // nowaTura.setVisible(true);
                }
            }
        });
    }


}
