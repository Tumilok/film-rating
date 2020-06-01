import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FilmList {

    public static void main(String[] args) {
        new FilmList();
    }

    public FilmList() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }

                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public static class TestPane extends JPanel {

        private static JPanel mainList;

        public void updateList(String filter){
            mainList.removeAll();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.weighty = 1;
            mainList.add(new JPanel(), gbc);
            List<Movie> movies = Authentication.getMovies(filter);
            for(Movie movie : movies){
                JButton movieButton = new JButton(movie.getTitle());
                movieButton.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
                GridBagConstraints gbcc = new GridBagConstraints();
                gbcc.gridwidth = GridBagConstraints.REMAINDER;
                gbcc.weightx = 1;
                gbcc.fill = GridBagConstraints.HORIZONTAL;
                mainList.add(movieButton, gbcc, 0);
            }
            validate();
            repaint();
        }

        public TestPane() {
            setLayout(new BorderLayout());

            mainList = new JPanel(new GridBagLayout());

            updateList("");

            add(new JScrollPane(mainList));

            JButton add = new JButton("Dodaj film");
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        AddFilm register = new AddFilm();
                        register.setTitle("Rejestracja");
                        register.setVisible(true);
                        register.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent windowEvent){
                                updateList("");
                            }
                        });
                    }
                    finally{

                        updateList("");
                    }
                }
            });
            JTextField textField = new JTextField();
            textField.setColumns(10);
            textField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateList(textField.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    insertUpdate(e);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    insertUpdate(e);
                }
            });

            JLabel lblSzukajFilmu = new JLabel("Szukaj filmu:");
            lblSzukajFilmu.setFont(new Font("Tahoma", Font.PLAIN, 17));

            FlowLayout bottomlayout = new FlowLayout();
            JPanel bottom = new JPanel(bottomlayout);
            bottom.add(add);
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
