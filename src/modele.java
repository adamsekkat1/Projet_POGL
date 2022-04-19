import java.util.Random;

public class Modele{
    public int Taille = 10;
    public int nb_joueurs = 1;
    public Plateau p;
    public Joueur[] Joueurs;

    public int HeliX, HeliY;

    public Modele(){
        p = new Plateau(Taille);
        Random r  = new Random();
        HeliX = r.nextInt(Taille);
        HeliY = r.nextInt(Taille);
        Joueur j = new Joueur(0,HeliX,HeliY,p);
        Joueurs = new Joueur[nb_joueurs];
        Joueurs[0] = j;
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



    public Case(int X,int Y, Plateau P){
        x = X;
        y = Y;
        p = P;
        joueursSurCase = new Joueur[4];
        Normale = true;
        Inondee = false;
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
        else{
            return false;
        }
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
    public Plateau p;
    public  Joueur(int id_, int X, int Y, Plateau P){
        id = id_;
        x = X;
        y = Y;
        p = P;
    }
}