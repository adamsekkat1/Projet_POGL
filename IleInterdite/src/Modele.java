import javax.swing.*;
import java.awt.*;
import java.util.Random;

enum Elem {Terre,Air,Eau,Feu,Rien};
enum Cle {Feu,Air,Eau,Terre};


public class Modele{
    public int Taille = 10;
    public int nb_joueurs = 3;
    public Plateau p;
    public Joueur[] Joueurs;
    public int CurrentJoueur;
    public boolean finDeTour;
    public int HeliX, HeliY;
    public boolean finDepartie;
    public boolean SacDeSableActive;

    public boolean HelicoptereTeleportationActive;

    public Artefact[] Artefacts = new Artefact[4];


    public Modele(){
        p = new Plateau(Taille);
        Random r  = new Random();
        HeliX = r.nextInt(Taille);
        HeliY = r.nextInt(Taille);
        p.grille[HeliX][HeliY].setHeliport();
        Joueurs = new Joueur[nb_joueurs];
        for (int i=0;i<nb_joueurs;i++){
            Joueur j = new Joueur(i,HeliX,HeliY,this);
            Joueurs[i] = j;
        }
        CurrentJoueur = 0;
        finDeTour = false;
        Case c = TrouveCasePourArtefact();
        Artefacts[0] = new Artefact(Elem.Terre,c);

        c = TrouveCasePourArtefact();
        Artefacts[1] = new Artefact(Elem.Feu,c);

        c = TrouveCasePourArtefact();
        Artefacts[2] = new Artefact(Elem.Eau,c);

        c = TrouveCasePourArtefact();
        Artefacts[3] = new Artefact(Elem.Air,c);

        SacDeSableActive = false;
        HelicoptereTeleportationActive = false;
    }

    public Case TrouveCasePourArtefact(){
        Random r  = new Random();
        int x = r.nextInt(Taille);
        int y = r.nextInt(Taille);
        Case c = p.grille[x][y];
        while (c.contientArtefact || c.IsHeliport()){
            x = r.nextInt(Taille);
            y = r.nextInt(Taille);
            c = p.grille[x][y];
        }
        return c;
    }
    public void prochainJoueur() {
        Joueurs[CurrentJoueur].nb_tours_joues = 0;
        CurrentJoueur = (CurrentJoueur +1)%(nb_joueurs);
        finDeTour = false;
    }
    public void setFinDeTour(){
        finDeTour = true;
    }

    public boolean verifJoueurNoye(Joueur j){
        int[][] cases = {{j.x-1,j.y},{j.x+1,j.y},{j.x,j.y+1},{j.x,j.y-1}};
        for (int[] aCase : cases) {
            if (!(aCase[0] < 0 || aCase[1] < 0 || aCase[0] > Taille || aCase[1] > Taille)) {
                if (!(p.grille[aCase[0]][aCase[1]].estSubmerge())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean TousLesJoueursAuHeliport(){
        for (Joueur j: Joueurs){
            if (HeliX!=j.x || HeliY!=j.y)
                return false;
        }
        return true;
    }
    public boolean TousLesArtefactsBienObtenus(){
        for (int i=0;i<4;i++){
            boolean a = false;
            for (Joueur j: Joueurs){
                if (j.ArtefactsPossedes[i]){
                    a = true;
                    break;
                }
            }
            if (!a)
                return false;
        }
        return true;
    }

    public boolean verifPartieGagnee(){
        return TousLesJoueursAuHeliport() && TousLesArtefactsBienObtenus();
    }

    public boolean verifPartiePerdue(){
        if (p.grille[HeliX][HeliY].estSubmerge()){
            System.out.println("heli submerge");
            return true;

        }
        for (Artefact a: Artefacts){
            if (a.c.estSubmerge() && a.c.contientArtefact){
                System.out.println("artefact submerge");
                return true;

            }
        }
        for (Joueur j: Joueurs){
            if (verifJoueurNoye(j)){
                System.out.println("joueur noye");
                return true;

            }
        }
        return false;
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
    public boolean contientArtefact;
    public Artefact artefact;

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public Case(int X,int Y, Plateau P){
        x = X;
        y = Y;
        p = P;
        joueursSurCase = new Joueur[4];
        Normale = true;
        Inondee = false;
        estHeliport = false;
        contientArtefact = false;
    }

    public void setArtefact(Artefact a){
        artefact = a;
        if (a.type != Elem.Rien)
            contientArtefact = true;
    }

    public Artefact retireArtefact(){
        contientArtefact = false;
        Component images[] = panel.getComponents();

        for (int i=0;i<images.length;i++){
            if (images[i].getName() == "artefact") {
                panel.remove(images[i]);
            }
        }
        panel.repaint();
        this.colorie();
        panel.updateUI();


        return artefact;
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
            if (estInondee()){
                this.panel.setBackground(Color.MAGENTA);
            }



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

    public boolean IsHeliport(){
        return estHeliport;
    }

    public boolean estInondee(){
        return this.Inondee;
    }
    public boolean estNormale(){
        return this.Normale;
    }
    public boolean estSubmerge(){
        return !(estNormale() || estInondee());
    }

    public boolean inondeZone(){
        if (estInondee()) {
            Inondee = false;
            colorie();
            return true;
        }
        else if (estNormale()){
            Inondee = true;
            Normale = false;
            colorie();
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
        colorie();
        return true;
    }
}

class Joueur{
    public int id;
    public int x,y;
    public Modele m;
    public int nombre_tours = 3;
    public int nb_tours_joues = 0;

    public boolean ArtefactsPossedes[] = {false,false,false,false};
    public boolean ClePossedees[] = {false,false,false,false};

    public boolean possedeSacDeSable;
    public boolean possedeTeleportation;

    public  Joueur(int id_, int X, int Y, Modele M){
        id = id_;
        x = X;
        y = Y;
        m = M;
        m.p.grille[x][y].putJoueur(this);
        possedeSacDeSable = false;
        possedeTeleportation = false;
    }


    public void FinDeTour(){ //ou debut de tour au choix
        Random r = new Random();
        if (r.nextInt(10)>4){
            System.out.println("On ajoute une cle au joueur:"+id);
            ajouterCle();
        }
        else if (r.nextInt(10)>5){
            System.out.println("On ajoute un sac de sable au joueur");
            possedeSacDeSable = true;
        }
        else if (r.nextInt(10)>5){
            System.out.println("On ajoute une teleportation helico");
            possedeTeleportation = true;
        }
    }

    public void ajouterCle(){
        Random r = new Random();
        int i = r.nextInt(4);
        System.out.println("On ajoute la "+i+" ieme cle.");
        ClePossedees[i] = true;
    }

    public void prendArtefact(){
        Artefact a = m.p.grille[x][y].retireArtefact();

        ArtefactsPossedes[ArtefactIndex(a)] = true;
        m.p.grille[x][y].artefact = new Artefact(Elem.Rien,m.p.grille[x][y]);
        nb_tours_joues++;
    }

    public int ArtefactIndex(Artefact a){
        switch (a.type){
            case Terre:
                return 0;
            case Feu:
                return 1;
            case Eau:
                return 2;
            case Air:
                return 3;
            case Rien:
                return 4;
        }
        return -1;
    }


    private boolean assecheCase_(String dir){
        Case c;
        switch (dir){
            case "pos":
                c = m.p.grille[x][y];
                if (!c.estInondee())
                    return false;
                c.asseche();
                return true;
            case "haut":
                if(y>0){
                    c = m.p.grille[x][y-1];
                    if (!c.estInondee())
                        return false;
                    c.asseche();
                    return true;
                }
                return false;
            case "bas":

                if(y<m.Taille-1){
                    c = m.p.grille[x][y+1];
                    if (!c.estInondee())
                        return false;
                    c.asseche();
                    return true;
                }
                return false;
            case "gauche":
                if(x>0){
                    c = m.p.grille[x-1][y];
                    if (!c.estInondee())
                        return false;
                    c.asseche();
                    return true;
                }
                return false;
            case "droite":
                if(x<m.Taille-1){
                    c = m.p.grille[x+1][y];
                    if (!c.estInondee())
                        return false;
                    c.asseche();
                    return true;
                }
                return false;
        }
        return false;
    }


    public boolean assecheCase(String dir){
        if (assecheCase_(dir)){
            nb_tours_joues++;
            if (nb_tours_joues>=nombre_tours){
                m.setFinDeTour();
            }
            return true;
        }
        return false;
    }

    private boolean checkIfMovingSubmerge(int xi, int yi){
        return m.p.grille[xi][yi].estSubmerge();
    }

    private boolean move_(String dir){
        switch (dir){
            case "haut":
                if(y>0){
                    if (checkIfMovingSubmerge(this.x,this.y-1))
                        return false;
                    y--;
                    return true;
                }
                return false;
            case "bas":

                if(y<m.Taille-1){
                    if (checkIfMovingSubmerge(this.x,this.y+1))
                        return false;
                    y++;
                    return true;
                }
                return false;
            case "gauche":

                if(x>0){
                    if (checkIfMovingSubmerge(this.x-1,this.y))
                        return false;
                    x--;
                    return true;
                }
                return false;
            case "droite":

                if(x<m.Taille-1){
                    if (checkIfMovingSubmerge(this.x+1,this.y))
                        return false;
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
                m.setFinDeTour();
            }
            return true;
        }
        return false;
    }
}

class Artefact{
    public Elem type;
    public Case c;


    public Artefact(Elem e,Case C){
        type = e;
        c = C;
        c.setArtefact(this);
    }

}




