import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Modele{
    public int Taille = 10;
    public int nb_joueurs = 1;
    public Plateau p;
    public Joueur[] Joueurs;
    public int CurrentJoueur;
    public boolean finDeTour;
    public int HeliX, HeliY;

    public Modele(){
        p = new Plateau(Taille);
        Random r  = new Random();
        HeliX = r.nextInt(Taille);
        HeliY = r.nextInt(Taille);
        Joueurs = new Joueur[nb_joueurs];
        for (int i=0;i<nb_joueurs;i++){
            Joueur j = new Joueur(i,HeliX,HeliY,this);
            Joueurs[i] = j;
        }
        CurrentJoueur = 0;
        finDeTour = false;
    }
    public void prochainJoueur() {
        CurrentJoueur = (CurrentJoueur +1)%(nb_joueurs);
        finDeTour = false;
    }
    public void setFinDeTour(){
        finDeTour = true;
    }
}


class Plateau{
    public Case[][] grille;
    public int TAILLE;

    public Plateau(int Taille){
        this.TAILLE = Taille;
        grille = new Case[Taille][Taille];
        for (int i=0;i<Taille; i++){
            for (int j=0;j<Taille;j++){
                grille[i][j] = new Case(i,j,this);
            }
        }
    }

}

class Case{
    private int x,y;
    private Plateau p;
    private boolean estHeliport;
    private boolean Normale;
    private boolean Inondee;
    public Joueur[] joueursSurCase;
    public JPanel panel;


    public Case(int X,int Y, Plateau P){
        x = X;
        y = Y;
        p = P;
        joueursSurCase = new Joueur[4];
        Normale = true;
        Inondee = false;
        estHeliport = false;
    }

    public void setHeliPort(){
        estHeliport = true;
    }

    public void colorie(){
        if (this.estNormale())
            this.panel.setBackground(Color.WHITE);
        else if (this.estInondee())
            this.panel.setBackground(Color.CYAN);
        else if (this.estSubmerge())
            this.panel.setBackground(Color.BLACK);
        if (this.estHeliport){
            this.panel.setBackground(Color.RED);
        }
    }

    public void putJoueur(Joueur j){
        joueursSurCase[j.id] = j;
    }
    public void removeJoueur(Joueur j){
        joueursSurCase[j.id] = null;
    }

    public void setHeliport(){
        estHeliport = true;
    }

    public boolean estInondee(){
        return this.Inondee;
    }
    public boolean estNormale(){
        return this.Normale;
    }
    public boolean estSubmerge(){
        return !(estNormale() && estInondee());
    }

    public boolean inondeZone(){
        if (estInondee()) {
            Inondee = false;
            return true;
        }
        else if (estNormale()){
            Inondee = true;
            Normale = false;
            return true;
        }
        return false;
    }
    public boolean asseche(){
        if (estNormale() || estSubmerge()){
            return false;
        }
        Normale = true;
        Inondee = false;
        return true;
    }
}

class Joueur{
    public int id;
    public int x,y;
    public Modele m;
    public int nombre_tours = 3;
    public int nb_tours_joues = 0;
    public  Joueur(int id_, int X, int Y, Modele M){
        id = id_;
        x = X;
        y = Y;
        m = M;
        m.p.grille[x][y].putJoueur(this);
    }
    private boolean checkIfMovingSubmerge(int x, int y){
        return m.p.grille[x][y].estSubmerge();
    }


    private boolean move_(String dir){

        switch (dir){
            case "haut":
                if (!checkIfMovingSubmerge(this.x,this.y-1))
                    return false;
                if(y>0){

                    y--;
                    return true;
                }
                return false;
            case "bas":
                if (!checkIfMovingSubmerge(this.x,this.y+1))
                    return false;
                if(y<m.Taille-1){
                    y++;
                    return true;
                }
                return false;
            case "gauche":
                if (!checkIfMovingSubmerge(this.x-1,this.y))
                    return false;
                if(x>0){
                    x--;
                    return true;
                }
                return false;
            case "droite":
                if (!checkIfMovingSubmerge(this.x+1,this.y))
                    return false;
                if(x<m.Taille-1){
                    x++;
                    return true;
                }
                return false;
        }
        return false;
    }
    public boolean move(String dir){
        int posx = x;
        int posy = y;
        if (move_(dir)){
            m.p.grille[posx][posy].removeJoueur(this);
            m.p.grille[x][y].putJoueur(this);
            nb_tours_joues++;
            if (nb_tours_joues>=nombre_tours){
                nb_tours_joues = 0;
                m.setFinDeTour();
            }
            return true;
        }
        return false;
    }


}
