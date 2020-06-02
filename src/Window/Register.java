package Window;

import DataProvider.DataProvider;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import Container.User;


public class Register extends JFrame {

    private JTextField email;
    private JTextField firstName;
    private JTextField lastName;
    private JPasswordField password;
    private final JPanel contentPanel;

    public Register() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width/2-250, screenSize.height/2-310, 500, 620);
        setResizable(false);
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);
        setTitle("Registration");
        setVisible(true);
    }

    public void showRegisterWindow() {
        email = new JTextField();
        email.setFont(new Font("Tahoma", Font.PLAIN, 32));
        email.setBounds(200, 50, 281, 68);
        contentPanel.add(email);
        email.setColumns(10);

        firstName = new JTextField();
        firstName.setFont(new Font("Tahoma", Font.PLAIN, 32));
        firstName.setBounds(200, 150, 281, 68);
        contentPanel.add(firstName);
        firstName.setColumns(10);

        lastName = new JTextField();
        lastName.setFont(new Font("Tahoma", Font.PLAIN, 32));
        lastName.setBounds(200, 250, 281, 68);
        contentPanel.add(lastName);
        lastName.setColumns(10);

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

        JButton registerButton = new JButton("Zarejestruj się");
        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        registerButton.setBounds(10, 450, 162, 73);
        registerButton.addActionListener(e -> {
            boolean isUser = false;
            try {
                if(!this.password.getText().equals("") && !this.firstName.getText().equals("")
                        && !this.lastName.getText().equals("") && !this.email.getText().equals("")
                        && this.email.getText().contains("@") && this.email.getText().contains(".")
                        && this.password.getText().length()>6){
                    byte[] salt = "12345678".getBytes();
                    int iterationCount = 40000;
                    int keyLength = 128;
                    SecretKeySpec key = DataProvider.createSecretKey(this.password.getText().toCharArray(),
                            salt, iterationCount, keyLength);
                    User user = new User(email.getText(), DataProvider.encrypt(
                            this.password.getText(), key), firstName.getText(), lastName.getText());
                    isUser = DataProvider.addUser(user);
                }
            } catch (GeneralSecurityException generalSecurityException) {
                generalSecurityException.printStackTrace();
            }
            if(isUser) {
                dispose();
                new FilmList();
            }
            else {
                JOptionPane.showMessageDialog(contentPanel, "Niepoprawne dane");
            }
        });
        contentPanel.add(registerButton);

        JButton loginButton = new JButton("Zaloguj się");
        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        loginButton.setBounds(318, 450, 162, 73);
        loginButton.addActionListener(e -> {
            dispose();
            Login login = new Login();
            login.setTitle("Window.Login");
            login.setVisible(true);
        });
        contentPanel.add(loginButton);

        JLabel label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        contentPanel.add(label);
    }
}
