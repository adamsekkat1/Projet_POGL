import javax.swing.*;
import java.awt.Color;

public class Vue {
    public JFrame Fenetre;
    public Modele modele;
    public int taille;
    public int TailleCase = 40;

    public Vue(){
        Modele modele = new Modele();
        taille = modele.Taille;
        JFrame Fenetre = new JFrame();
        for (int i=0;i<taille; i++){
            for (int j=0;j<taille;j++){
                JPanel ZonePanel = new JPanel();
                ZonePanel.setBounds(i*TailleCase,j*TailleCase,TailleCase,TailleCase);
                Case c = modele.p.grille[i][j];
                if (c.estNormale())
                    ZonePanel.setBackground(Color.WHITE);
                else if (c.estInondee())
                    ZonePanel.setBackground(Color.CYAN);
                else if (c.estSubmerge())
                    ZonePanel.setBackground(Color.BLACK);
                //
                Fenetre.add(ZonePanel);
            }
        }
        System.out.println(Fenetre);
        Fenetre.setSize(600, 600);
        Fenetre.setTitle("Ile Interdite");

        Fenetre.setLayout(null);
        Fenetre.setVisible(true);
    }

    public void afficheGrille(){
        for (int i=0;i<taille; i++){
            for (int j=0;j<taille;j++){
                JPanel ZonePanel = new JPanel();
                ZonePanel.setBounds(i*TailleCase,j*TailleCase,TailleCase,TailleCase);
                ZonePanel.setBackground(Color.WHITE);
                //modele.p.grille[i][j]
                System.out.println(Fenetre);
            }
        }

    }
}