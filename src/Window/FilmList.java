package Window;

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

import Container.Movie;
import DataProvider.DataProvider;

public class FilmList {

    public FilmList() {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            JFrame frame = new JFrame("List of Films");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new TestPane());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
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
            List<Movie> movies = DataProvider.getMovies(filter);
            for(Movie movie : movies){
                JButton movieButton = new JButton(movie.getTitle());
                movieButton.addActionListener(e -> new MovieDetails(movie));
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
            add.addActionListener(e -> {
                try {
                    AddFilm addFilm = new AddFilm();
                    addFilm.showAddFilmWindow();
                    addFilm.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent windowEvent){
                            updateList("");
                        }
                    });
                }
                finally {

                    updateList("");
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
