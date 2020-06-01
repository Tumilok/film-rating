import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddFilm extends JFrame {

    private JTextField movieTitle;
    private JTextField description;
    private JTextField year;
    private JTextField actors;
    private JTextField directors;
    private JButton registerButton;
    private JLabel label;
    private JPanel contentPanel;

    public AddFilm() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width/2-250, screenSize.height/2-350, 500, 700);
        setResizable(false);
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);

        movieTitle = new JTextField();
        movieTitle.setFont(new Font("Tahoma", Font.PLAIN, 32));
        movieTitle.setBounds(200, 50, 281, 68);
        contentPanel.add(movieTitle);
        movieTitle.setColumns(10);

        description = new JTextField();
        description.setFont(new Font("Tahoma", Font.PLAIN, 32));
        description.setBounds(200, 150, 281, 68);
        contentPanel.add(description);
        description.setColumns(10);

        year = new JTextField();
        year.setFont(new Font("Tahoma", Font.PLAIN, 32));
        year.setBounds(200, 250, 281, 68);
        contentPanel.add(year);
        year.setColumns(10);

        actors = new JTextField();
        actors.setFont(new Font("Tahoma", Font.PLAIN, 32));
        actors.setBounds(200, 350, 281, 68);
        contentPanel.add(actors);

        directors = new JTextField();
        directors.setFont(new Font("Tahoma", Font.PLAIN, 32));
        directors.setBounds(200, 450, 281, 68);
        contentPanel.add(directors);

        JLabel lblTitle = new JLabel("Tytuł");
        lblTitle.setBackground(Color.BLACK);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblTitle.setBounds(10, 50, 193, 52);
        contentPanel.add(lblTitle);

        JLabel lblFirstname = new JLabel("Opis");
        lblFirstname.setBackground(Color.BLACK);
        lblFirstname.setForeground(Color.BLACK);
        lblFirstname.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblFirstname.setBounds(10, 150, 193, 52);
        contentPanel.add(lblFirstname);

        JLabel lblLastname = new JLabel("Rok");
        lblLastname.setBackground(Color.BLACK);
        lblLastname.setForeground(Color.BLACK);
        lblLastname.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblLastname.setBounds(10, 250, 193, 52);
        contentPanel.add(lblLastname);

        JLabel lblActors = new JLabel("Aktorzy");
        lblActors.setForeground(Color.BLACK);
        lblActors.setBackground(Color.CYAN);
        lblActors.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblActors.setBounds(10, 350, 193, 52);
        contentPanel.add(lblActors);

        JLabel lblDirectors = new JLabel("Reżyserzy");
        lblDirectors.setForeground(Color.BLACK);
        lblDirectors.setBackground(Color.CYAN);
        lblDirectors.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblDirectors.setBounds(10, 450, 193, 52);
        contentPanel.add(lblDirectors);

        registerButton = new JButton("Dodaj");
        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        registerButton.setBounds(250-81, 550, 162, 73);
        registerButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String str = actors.getText();
                ArrayList<String> actorsList = new ArrayList<>(Arrays.asList(str.split(", ")));
                List<Actor> movieActors = new ArrayList<>();
                for (String actor: actorsList) {
                    ArrayList<String> name = new ArrayList<>(Arrays.asList(actor.split(" ")));
                    if (name.size() < 2) continue;
                    String firstName = name.get(0);
                    String lastName = name.get(1);
                    System.out.println(firstName + " " + lastName);
                    List<Actor> allActors = Authentication.getActors();
                    boolean isNew = true;
                    Actor newActor = new Actor(firstName, lastName);
                    for (Actor a: allActors) {
                        if (a.equals(newActor)) {
                            movieActors.add(a);
                            isNew = false;
                            break;
                        }
                    }
                    if (isNew) {
                        movieActors.add(newActor);
                        Authentication.addActor(newActor);
                    }
                }

                str = directors.getText();
                ArrayList<String> directorsList = new ArrayList<>(Arrays.asList(str.split(", ")));
                List<Director> movieDirectors = new ArrayList<>();
                for (String director: directorsList) {
                    ArrayList<String> name = new ArrayList<>(Arrays.asList(director.split(" ")));
                    if (name.size() < 2) continue;
                    String firstName = name.get(0);
                    String lastName = name.get(1);
                    List<Director> allDirectors = Authentication.getDirectors();
                    boolean isNew = true;
                    Director newDirector = new Director(firstName, lastName);
                    for (Director d: allDirectors){
                        if (d.equals(newDirector)) {
                            movieDirectors.add(d);
                            isNew = false;
                            break;
                        }
                    }
                    if (isNew) {
                        movieDirectors.add(newDirector);
                        Authentication.addDirector(newDirector);
                    }
                }
                Movie movie = new Movie(movieTitle.getText(), description.getText(), year.getText());
                for (Actor actor: movieActors) {
                    movie.addActor(actor);
                }
                for (Director director: movieDirectors) {
                    movie.addDirector(director);
                }
                Authentication.addMovie(movie);
                dispose();
            }
        });
        contentPanel.add(registerButton);


        label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        contentPanel.add(label);
    }
}