import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Main main = new Main();
        try {
            main.showGui();
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Creation failed, " + exc);
        }
    }

    public void showGui() {
        SwingUtilities.invokeLater(() -> {
            JFrame jf = new JFrame("NAI");
            jf.setPreferredSize(new Dimension(900, 600));
            List<Language> languages = Language.Dir("training");
            Result result = new Result(languages);
            JTextArea text = new JTextArea();
            text.setEditable(false);
                String inputtext = JOptionPane.showInputDialog("Podaj swój tekst do testu");
                if (inputtext != null && inputtext != "") {
                    text.append("Testowany tekst: " + inputtext + "\n");
                    text.append(result.examin(inputtext) + "\n");
                } else {
                    JOptionPane.showMessageDialog(null, "Nie podano tekstu!");
                }
            jf.pack();
            jf.setLayout(new BorderLayout());
            jf.add(text,BorderLayout.CENTER);
            jf.setLocationRelativeTo(null);
            jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jf.setVisible(true);
        });
    }
}
