import javax.swing.JDialog;
import javax.swing.JOptionPane;
public class Main_CommonBreaks { 

    public static void main(String[] args) { 

        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);

        JOptionPane.showMessageDialog(dialog, "Welcome to the Common Breaks Finder Program!");


        WebScraping.logInPage();

        WebScraping.commonBreaks();
    }
}