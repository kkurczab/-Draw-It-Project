import javax.swing.*;

public class OknoHasła extends JDialog {
    private JPanel panel1;
    private JButton STARTButton;
    private JFormattedTextField hasło;

    public OknoHasła(GraGUI graGUI){
        setContentPane(panel1);
        setModal(true);
        setUndecorated(true);
        getRootPane().setDefaultButton(STARTButton);
        pack();

    }
}
