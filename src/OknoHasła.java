import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OknoHasła extends JDialog {
    private JPanel panel1;
    private JButton STARTButton;
    private JPasswordField hasło;

    public OknoHasła(GraGUI graGUI){
        setContentPane(this.panel1);
        setModal(true);
        setUndecorated(true);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(STARTButton);
        pack();

        STARTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        STARTButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    dispose();
                }
            }
        });

    }
}
