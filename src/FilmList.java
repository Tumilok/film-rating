import java.awt.*;

import javax.swing.*;

public class FilmList {

    private JFrame frame;
    private JTextField textField;

    public FilmList() {
        initialize();
        this.frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(screenSize.width/2-442, screenSize.height/2-322, 884, 644);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(41, 514, 787, -470);
        frame.getContentPane().add(scrollPane);

        JButton btnDodajFilm = new JButton("Dodaj film");
        btnDodajFilm.setBounds(201, 544, 117, 40);
        btnDodajFilm.setAlignmentX(Component.RIGHT_ALIGNMENT);
        frame.getContentPane().add(btnDodajFilm);

        textField = new JTextField();
        textField.setBounds(482, 544, 230, 42);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblSzukajFilmu = new JLabel("Szukaj filmu:");
        lblSzukajFilmu.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblSzukajFilmu.setBounds(359, 548, 113, 27);
        frame.getContentPane().add(lblSzukajFilmu);
    }
}
