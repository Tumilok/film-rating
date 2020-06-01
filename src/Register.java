import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Register extends JFrame {

    private JTextField email;
    private JTextField firstname;
    private JTextField lastname;
    private JPasswordField password;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel label;
    private JPanel contentPanel;

    public Register() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width/2-250, screenSize.height/2-310, 500, 620);
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

        firstname = new JTextField();
        firstname.setFont(new Font("Tahoma", Font.PLAIN, 32));
        firstname.setBounds(200, 150, 281, 68);
        contentPanel.add(firstname);
        firstname.setColumns(10);

        lastname = new JTextField();
        lastname.setFont(new Font("Tahoma", Font.PLAIN, 32));
        lastname.setBounds(200, 250, 281, 68);
        contentPanel.add(lastname);
        lastname.setColumns(10);

        password = new JPasswordField();
        password.setFont(new Font("Tahoma", Font.PLAIN, 32));
        password.setBounds(200, 350, 281, 68);
        contentPanel.add(password);

        JLabel lblUsername = new JLabel("E-mail");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(10, 50, 193, 52);
        contentPanel.add(lblUsername);

        JLabel lblFirstname = new JLabel("Imię");
        lblFirstname.setBackground(Color.BLACK);
        lblFirstname.setForeground(Color.BLACK);
        lblFirstname.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblFirstname.setBounds(10, 150, 193, 52);
        contentPanel.add(lblFirstname);

        JLabel lblLastname = new JLabel("Nazwisko");
        lblLastname.setBackground(Color.BLACK);
        lblLastname.setForeground(Color.BLACK);
        lblLastname.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblLastname.setBounds(10, 250, 193, 52);
        contentPanel.add(lblLastname);

        JLabel lblPassword = new JLabel("Hasło");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblPassword.setBounds(10, 350, 193, 52);
        contentPanel.add(lblPassword);

        registerButton = new JButton("Zarejestruj się");
        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        registerButton.setBounds(10, 450, 162, 73);
        registerButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
                boolean isUser = false;
                try {
                    isUser = Authentication.addUser(email.getText(), Register.this.password.getText(), firstname.getText(), lastname.getText());
                } catch (GeneralSecurityException generalSecurityException) {
                    generalSecurityException.printStackTrace();
                } catch (UnsupportedEncodingException unsupportedEncodingException) {
                    unsupportedEncodingException.printStackTrace();
                }
                if(isUser) {
                    dispose();
                    new FilmList();
                }
                else {
                    System.out.println("NULL");
                }
            }
        });
        contentPanel.add(registerButton);

        loginButton = new JButton("Zaloguj się");
        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        loginButton.setBounds(318, 450, 162, 73);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login login = new Login();
                login.setTitle("Login");
                login.setVisible(true);
            }
        });
        contentPanel.add(loginButton);

        label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        contentPanel.add(label);
    }
}
