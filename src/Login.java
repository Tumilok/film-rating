import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

    private JTextField email;
    private JPasswordField password;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel label;
    private JPanel contentPanel;

    public static void main(String[] args) {
        Login frame = new Login();
        frame.setVisible(true);
    }

    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width/2-250, screenSize.height/2-200, 500, 400);
        setResizable(false);
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);

        email = new JTextField();
        email.setFont(new Font("Tahoma", Font.PLAIN, 32));
        email.setBounds(200, 50, 281, 68);
        contentPanel.add(email);
        email.setColumns(10);

        password = new JPasswordField();
        password.setFont(new Font("Tahoma", Font.PLAIN, 32));
        password.setBounds(200, 150, 281, 68);
        contentPanel.add(password);

        JLabel lblUsername = new JLabel("E-mail");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(10, 50, 193, 52);
        contentPanel.add(lblUsername);

        JLabel lblPassword = new JLabel("Hasło");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblPassword.setBounds(10, 150, 193, 52);
        contentPanel.add(lblPassword);

        loginButton = new JButton("Zaloguj się");
        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        loginButton.setBounds(10, 250, 162, 73);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Authentication.login(email.getText(), Login.this.password.getText())) {
                    dispose();
                    FilmList filmList = new FilmList();
                }
                else{
                    System.out.println("NULL");
                }
            }
        });
        contentPanel.add(loginButton);

        registerButton = new JButton("Zarejestruj się");
        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        registerButton.setBounds(318, 250, 162, 73);
        registerButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
                Register register = new Register();
                register.setTitle("Rejestracja");
                register.setVisible(true);
            }
        });
        contentPanel.add(registerButton);

        label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        contentPanel.add(label);
    }
}
