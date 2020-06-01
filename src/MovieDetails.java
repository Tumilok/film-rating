import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class MovieDetails {

    private final Movie movie;

    public MovieDetails(Movie movie) {
        this.movie = movie;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }

                JFrame frame = new JFrame(movie.getTitle());
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

            JPanel pnPanel0;
            JLabel lbDescription;
            JLabel lbTitle;
            JLabel lbRok;
            JTabbedPane tbpTabbedPane1;

            JPanel pnActors;
            JList lsList2;

            JPanel pnDirectors;
            JList lsList3;
            JLabel lbOcena;
            JSpinner spnSpinner1;
            JButton btRate;

            pnPanel0 = new JPanel();
            GridBagLayout gbPanel0 = new GridBagLayout();
            GridBagConstraints gbcPanel0 = new GridBagConstraints();
            pnPanel0.setLayout( gbPanel0 );

            lbDescription = new JLabel( "Description: " + movie.getDescription() );
            lbDescription.setFont(new Font("Tahoma", Font.PLAIN, 17));
            gbcPanel0.gridx = 1;
            gbcPanel0.gridy = 2;
            gbcPanel0.gridwidth = 18;
            gbcPanel0.gridheight = 2;
            gbcPanel0.fill = GridBagConstraints.BOTH;
            gbcPanel0.weightx = 1;
            gbcPanel0.weighty = 1;
            gbcPanel0.anchor = GridBagConstraints.NORTH;
            gbPanel0.setConstraints( lbDescription, gbcPanel0 );
            pnPanel0.add( lbDescription );

            lbTitle = new JLabel( movie.getTitle()  );
            lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 25));
            gbcPanel0.gridx = 1;
            gbcPanel0.gridy = 1;
            gbcPanel0.gridwidth = 18;
            gbcPanel0.gridheight = 2;
            gbcPanel0.fill = GridBagConstraints.BOTH;
            gbcPanel0.weightx = 1;
            gbcPanel0.weighty = 1;
            gbcPanel0.anchor = GridBagConstraints.NORTH;
            gbPanel0.setConstraints( lbTitle, gbcPanel0 );
            pnPanel0.add( lbTitle );

            lbRok = new JLabel( "Rok: " + movie.getYearOfRelease() );
            lbRok.setFont(new Font("Tahoma", Font.PLAIN, 17));
            gbcPanel0.gridx = 1;
            gbcPanel0.gridy = 3;
            gbcPanel0.gridwidth = 18;
            gbcPanel0.gridheight = 2;
            gbcPanel0.fill = GridBagConstraints.BOTH;
            gbcPanel0.weightx = 1;
            gbcPanel0.weighty = 1;
            gbcPanel0.anchor = GridBagConstraints.NORTH;
            gbPanel0.setConstraints( lbRok, gbcPanel0 );
            pnPanel0.add( lbRok );

            lbOcena = new JLabel( "Ocena: " + Authentication.getMovieRating(movie));
            lbOcena.setFont(new Font("Tahoma", Font.PLAIN, 17));
            gbcPanel0.gridx = 14;
            gbcPanel0.gridy = 4;
            gbcPanel0.gridwidth = 5;
            gbcPanel0.gridheight = 1;
            gbcPanel0.fill = GridBagConstraints.BOTH;
            gbcPanel0.weightx = 1;
            gbcPanel0.weighty = 1;
            gbcPanel0.anchor = GridBagConstraints.NORTH;
            gbPanel0.setConstraints( lbOcena, gbcPanel0 );
            pnPanel0.add( lbOcena );

            spnSpinner1 = new JSpinner( );
            gbcPanel0.gridx = 7;
            gbcPanel0.gridy = 6;
            gbcPanel0.gridwidth = 2;
            gbcPanel0.gridheight = 2;
            gbcPanel0.fill = GridBagConstraints.BOTH;
            gbcPanel0.weightx = 1;
            gbcPanel0.weighty = 0;
            gbcPanel0.anchor = GridBagConstraints.NORTH;
            gbPanel0.setConstraints( spnSpinner1, gbcPanel0 );
            pnPanel0.add( spnSpinner1 );

            btRate = new JButton( "Rate"  );
            btRate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double rate = new Double(spnSpinner1.getValue().toString());
                    Rating rating = new Rating(movie, Authentication.getUser(), rate, new Date().toString());
                    if (Authentication.addRating(rating)) {
                        lbOcena.setText( "Ocena: " + Authentication.getMovieRating(movie));
                    } else {
                        System.out.println("Something went wrong with rating");
                    }

                    validate();
                    repaint();
                }
            });
            gbcPanel0.gridx = 6;
            gbcPanel0.gridy = 8;
            gbcPanel0.gridwidth = 4;
            gbcPanel0.gridheight = 2;
            gbcPanel0.fill = GridBagConstraints.BOTH;
            gbcPanel0.weightx = 1;
            gbcPanel0.weighty = 0;
            gbcPanel0.anchor = GridBagConstraints.NORTH;
            gbPanel0.setConstraints( btRate, gbcPanel0 );
            pnPanel0.add( btRate );

            tbpTabbedPane1 = new JTabbedPane( );

            pnActors = new JPanel();
            GridBagLayout gbActors = new GridBagLayout();
            GridBagConstraints gbcActors = new GridBagConstraints();
            pnActors.setLayout( gbActors );

            Set<Actor> actorSet = movie.getActors();
            List<String> actorList = new ArrayList<>();
            for (Actor actor: actorSet) {
                actorList.add(actor.toString());
            }

            lsList2 = new JList( actorList.toArray() );
            JScrollPane scpList2 = new JScrollPane( lsList2 );
            gbcActors.gridx = 0;
            gbcActors.gridy = 0;
            gbcActors.gridwidth = 9;
            gbcActors.gridheight = 5;
            gbcActors.fill = GridBagConstraints.BOTH;
            gbcActors.weightx = 1;
            gbcActors.weighty = 1;
            gbcActors.anchor = GridBagConstraints.NORTH;
            gbActors.setConstraints( scpList2, gbcActors );
            pnActors.add( scpList2 );
            tbpTabbedPane1.addTab("Actors",pnActors);

            pnDirectors = new JPanel();
            GridBagLayout gbDirectors = new GridBagLayout();
            GridBagConstraints gbcDirectors = new GridBagConstraints();
            pnDirectors.setLayout( gbDirectors );

            Set<Director> directorSet = movie.getDirectors();
            List<String> directorList = new ArrayList<>();
            for (Director director: directorSet) {
                directorList.add(director.toString());
            }

            lsList3 = new JList( directorList.toArray() );
            gbcDirectors.gridx = 0;
            gbcDirectors.gridy = 0;
            gbcDirectors.gridwidth = 9;
            gbcDirectors.gridheight = 5;
            gbcDirectors.fill = GridBagConstraints.BOTH;
            gbcDirectors.weightx = 1;
            gbcDirectors.weighty = 1;
            gbcDirectors.anchor = GridBagConstraints.NORTH;
            gbDirectors.setConstraints( lsList3, gbcDirectors );
            pnDirectors.add( lsList3 );
            tbpTabbedPane1.addTab("Directors",pnDirectors);
            gbcPanel0.gridx = 3;
            gbcPanel0.gridy = 13;
            gbcPanel0.gridwidth = 14;
            gbcPanel0.gridheight = 5;
            gbcPanel0.fill = GridBagConstraints.BOTH;
            gbcPanel0.weightx = 1;
            gbcPanel0.weighty = 1;
            gbcPanel0.anchor = GridBagConstraints.NORTH;
            gbPanel0.setConstraints( tbpTabbedPane1, gbcPanel0 );
            pnPanel0.add( tbpTabbedPane1 );

            add(pnPanel0);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 600);
        }
    }
}
