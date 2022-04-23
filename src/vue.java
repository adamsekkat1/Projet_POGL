import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;



public class Vue  extends JFrame{
    public JFrame Fenetre;
    public Modele modele;
    public int taille;
    public int TailleCase = 45;

    JButton BoutonFinDeTour;
    JButton BoutonDroite;
    JButton BoutonBas;
    JButton BoutonHaut;
    JButton BoutonGauche;
    public ImageIcon ImageJoueur = new ImageIcon("/Users/mac/IdeaProjects/IleInterdite/src/joueur.jpg");


    public Vue() throws IOException {
        modele = new Modele();
        taille = modele.Taille;
        Fenetre = new JFrame();
        afficheGrille();
        //System.out.println(Fenetre);
        Fenetre.setSize(600, 600);
        Fenetre.setTitle("Ile Interdite");

        JPanel ZonePanel = new JPanel();
        BoutonFinDeTour = new JButton("Fin de Tour");
        BoutonFinDeTour.setBounds(50,500,125,75);
        BoutonFinDeTour.setBackground(Color.BLUE);

        BoutonFinDeTour.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (modele.finDeTour){
                    for ( int k=0 ; k<3 ; k++) {
                        Random random = new Random();
                        int i = random.nextInt(taille);

                        Random random1 = new Random();
                        int j = random1.nextInt(taille);
                        modele.p.grille[i][j].inondeZone();
                        modele.p.grille[i][j].colorie();
                    }
                    modele.prochainJoueur();
                    setMovementButtonClickable();
                }
            }
        });



        BoutonDroite = new JButton("droite");
        BoutonDroite.setBounds(525,300,75,55);
        BoutonDroite.setBackground(Color.BLUE);

        BoutonDroite.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!modele.finDeTour){
                    Joueur j =  modele.Joueurs[modele.CurrentJoueur];
                    modele.p.grille[j.x][j.y].colorie();
                    Component images[] = modele.p.grille[j.x][j.y].panel.getComponents();
                    for (int i=0;i<images.length;i++){
                        System.out.println(images[i]);
                        modele.p.grille[j.x][j.y].panel.remove(images[i]);
                    }
                    modele.p.grille[j.x][j.y].panel.updateUI();
                    modele.Joueurs[modele.CurrentJoueur].move("droite");
                    try {
                        afficheJoueurs();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if(modele.finDeTour){
                        setMovementButtonNotClickable();
                    }
                }
            }
        });

        BoutonGauche = new JButton("gauche");
        BoutonGauche.setBounds(450,300,75,55);
        BoutonGauche.setBackground(Color.BLUE);

        BoutonGauche.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!modele.finDeTour){
                    Joueur j =  modele.Joueurs[modele.CurrentJoueur];
                    modele.p.grille[j.x][j.y].colorie();
                    Component images[] = modele.p.grille[j.x][j.y].panel.getComponents();
                    for (int i=0;i<images.length;i++){
                        modele.p.grille[j.x][j.y].panel.remove(images[i]);
                    }
                    modele.p.grille[j.x][j.y].panel.updateUI();
                    modele.Joueurs[modele.CurrentJoueur].move("gauche");
                    try {
                        afficheJoueurs();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if(modele.finDeTour){
                        setMovementButtonNotClickable();
                    }
                }
            }
        });

        BoutonHaut = new JButton("haut");
        BoutonHaut.setBounds(490,250,75,55);
        BoutonHaut.setBackground(Color.BLUE);

        BoutonHaut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!modele.finDeTour) {
                    Joueur j =  modele.Joueurs[modele.CurrentJoueur];
                    modele.p.grille[j.x][j.y].colorie();
                    Component images[] = modele.p.grille[j.x][j.y].panel.getComponents();
                    for (int i=0;i<images.length;i++){
                        modele.p.grille[j.x][j.y].panel.remove(images[i]);
                    }
                    modele.p.grille[j.x][j.y].panel.updateUI();
                    modele.Joueurs[modele.CurrentJoueur].move("haut");
                    try {
                        afficheJoueurs();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if(modele.finDeTour){
                        setMovementButtonNotClickable();
                    }
                }
            }
        });
        BoutonBas = new JButton("bas");
        BoutonBas.setBounds(490,350,75,55);
        BoutonBas.setBackground(Color.BLUE);

        BoutonBas.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!modele.finDeTour){
                    Joueur j =  modele.Joueurs[modele.CurrentJoueur];
                    modele.p.grille[j.x][j.y].colorie();
                    Component images[] = modele.p.grille[j.x][j.y].panel.getComponents();
                    for (int i=0;i<images.length;i++){
                        modele.p.grille[j.x][j.y].panel.remove(images[i]);
                    }
                    modele.p.grille[j.x][j.y].panel.updateUI();
                    modele.Joueurs[modele.CurrentJoueur].move("bas");


                    try {
                        afficheJoueurs();//peut etre faudrait appliquer move à affiche joueurs ou bien creer in move special image(un peu useless)
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if(modele.finDeTour){
                        setMovementButtonNotClickable();
                    }
                }
            }
        });

        Fenetre.add(BoutonFinDeTour);
        Fenetre.add(BoutonDroite);
        Fenetre.add(BoutonGauche);
        Fenetre.add(BoutonHaut);
        Fenetre.add(BoutonBas);
        Fenetre.add(ZonePanel);
        afficheJoueurs();
        Fenetre.setLayout(null);
        Fenetre.setVisible(true);
        Fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setMovementButtonNotClickable(){
        BoutonGauche.setEnabled(false);
        BoutonDroite.setEnabled(false);
        BoutonHaut.setEnabled(false);
        BoutonBas.setEnabled(false);
    }

    public void setMovementButtonClickable(){
        BoutonGauche.setEnabled(true);
        BoutonDroite.setEnabled(true);
        BoutonHaut.setEnabled(true);
        BoutonBas.setEnabled(true);

    }
    public void afficheJoueurs() throws IOException {
        for (Joueur j: modele.Joueurs){
            //JLabel picLabel = new JLabel(new ImageIcon(ImageJoueur));
            modele.p.grille[j.x][j.y].panel.add(new JLabel(ImageJoueur));
            //modele.p.grille[j.x][j.y].panel.setBackground(Color.GREEN);
            modele.p.grille[j.x][j.y].panel.repaint();
            modele.p.grille[j.x][j.y].panel.updateUI();
        }
    }

    public void afficheGrille(){
        for (int i=0;i<taille; i++){
            for (int j=0;j<taille;j++){
                JPanel ZonePanel = new JPanel();
                ZonePanel.setBounds(i*TailleCase,j*TailleCase,TailleCase,TailleCase);
                Case c = modele.p.grille[i][j];
                if  (i== modele.HeliX && j == modele.HeliY)
                    c.setHeliPort();

                c.panel = ZonePanel;
                c.colorie();
                Fenetre.add(ZonePanel);
            }
        }
    }



}
