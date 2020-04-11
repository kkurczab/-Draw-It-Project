import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NowaTura extends JDialog {

    private JPanel P;
    private JButton okButton;
    private JTextField IDGracza;
    private JPanel panel1;

    public NowaTura(GraGUI graGUI){

        setContentPane(P);
        setModal(true);
        setUndecorated(true);
        getRootPane().setDefaultButton(okButton);
        pack();
        okButton.setPreferredSize(new Dimension(300,20));
        IDGracza.setText(graGUI.getGra().getGracze()[graGUI.getGra().getNrGracza()].getNazwaGracza());


        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                graGUI.refresh();
                dispose();
            }
        });

        okButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    graGUI.refresh();
                    dispose();
                }
            }
        });

    }
}

