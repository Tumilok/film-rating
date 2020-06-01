import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MovieDetails {

    public static void main(String[] args) {
        new FilmList();
    }

    public MovieDetails() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }

                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new MovieDetails.TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private JPanel mainList;

        public TestPane() {
            setLayout(new BorderLayout());


            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.weighty = 1;
            mainList.add(new JPanel(), gbc);

            JLabel add = new JLabel("Title");
            add.setFont(new Font("Tahoma", Font.PLAIN, 17));
            add.setSize(100, 20);
            add.setLocation(100, 300);
            .add(add);






            JTextField textField = new JTextField();
            textField.setColumns(10);

            JLabel lblSzukajFilmu = new JLabel("Szukaj filmu:");
            lblSzukajFilmu.setFont(new Font("Tahoma", Font.PLAIN, 17));

            FlowLayout bottomlayout = new FlowLayout();
            JPanel bottom = new JPanel(bottomlayout);
            bottom.add(lblSzukajFilmu);
            bottom.add(textField);
            add(bottom, BorderLayout.SOUTH);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 600);
        }
    }
}
