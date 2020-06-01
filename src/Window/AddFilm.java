package Window;

import Container.Actor;
import Container.Director;
import Container.Movie;
import DataProvider.DataProvider;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddFilm extends JFrame {

    private JTextField movieTitle;
    private JTextField description;
    private JTextField year;
    private JTextField actors;
    private JTextField directors;
    private final JPanel contentPanel;

    public AddFilm() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width/2-250, screenSize.height/2-350, 500, 700);
        setResizable(false);
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);
        setTitle("Add Film");
        setVisible(true);
    }

    public void showAddFilmWindow() {


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

        JLabel lblFirstName = new JLabel("Opis");
        lblFirstName.setBackground(Color.BLACK);
        lblFirstName.setForeground(Color.BLACK);
        lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblFirstName.setBounds(10, 150, 193, 52);
        contentPanel.add(lblFirstName);

        JLabel lblLastName = new JLabel("Rok");
        lblLastName.setBackground(Color.BLACK);
        lblLastName.setForeground(Color.BLACK);
        lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblLastName.setBounds(10, 250, 193, 52);
        contentPanel.add(lblLastName);

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

        JButton registerButton = new JButton("Dodaj");
        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        registerButton.setBounds(250-81, 550, 162, 73);
        registerButton.addActionListener(e -> {
            String str = actors.getText();
            ArrayList<String> actorsList = new ArrayList<>(Arrays.asList(str.split(", ")));
            List<Actor> movieActors = new ArrayList<>();
            for (String actor: actorsList) {
                ArrayList<String> name = new ArrayList<>(Arrays.asList(actor.split(" ")));
                if (name.size() < 2) continue;
                List<Actor> allActors = DataProvider.getActors();
                boolean isNew = true;
                Actor newActor = new Actor(name.get(0), name.get(1));
                for (Actor a: allActors) {
                    if (a.equals(newActor)) {
                        movieActors.add(a);
                        isNew = false;
                        break;
                    }
                }
                if (isNew) {
                    movieActors.add(newActor);
                    if (!DataProvider.addActor(newActor)) {
                        System.out.println("Oops, Something wend wrong with adding new Actor");
                    }
                }
            }

            str = directors.getText();
            ArrayList<String> directorsList = new ArrayList<>(Arrays.asList(str.split(", ")));
            List<Director> movieDirectors = new ArrayList<>();
            for (String director: directorsList) {
                ArrayList<String> name = new ArrayList<>(Arrays.asList(director.split(" ")));
                if (name.size() < 2) continue;
                List<Director> allDirectors = DataProvider.getDirectors();
                boolean isNew = true;
                Director newDirector = new Director(name.get(0), name.get(1));
                for (Director d: allDirectors){
                    if (d.equals(newDirector)) {
                        movieDirectors.add(d);
                        isNew = false;
                        break;
                    }
                }
                if (isNew) {
                    movieDirectors.add(newDirector);
                    if (!DataProvider.addDirector(newDirector)) {
                        System.out.println("Oops, Something wend wrong with adding new Director");
                    }
                }
            }

            Movie movie = new Movie(movieTitle.getText(), description.getText(), year.getText());
            for (Actor actor: movieActors) {
                movie.addActor(actor);
            }
            for (Director director: movieDirectors) {
                movie.addDirector(director);
            }
            if (!DataProvider.addMovie(movie)) {
                System.out.println("Oops, Something wend wrong with adding new Movie");
            }
            dispose();
        });
        contentPanel.add(registerButton);

        JLabel label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        contentPanel.add(label);
    }
}