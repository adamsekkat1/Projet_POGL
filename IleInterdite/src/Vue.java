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

    JButton AssecheDroite;
    JButton AssecheBas;
    JButton AssecheHaut;
    JButton AssecheGauche;
    JButton AssechePos;

    JButton RecupererArtefactB;

    JButton BouttonSacDeSable;
    JButton BoutonHeli;

    public ImageIcon[] ImageJoueurs = {new ImageIcon("/Users/victor/Projet_POGL/IleInterdite/src/joueur.jpg"),new ImageIcon("/Users/victor/Projet_POGL/IleInterdite/src/etre_supreme.jpeg"),new ImageIcon("/Users/victor/Projet_POGL/IleInterdite/src/poussin.jpeg")};

    public ImageIcon[] ImageArtefacts = {new ImageIcon("/Users/victor/Projet_POGL/IleInterdite/src/artefact_terre.jpeg"),new ImageIcon("/Users/victor/Projet_POGL/IleInterdite/src/artefact_feu.jpeg"),new ImageIcon("/Users/victor/Projet_POGL/IleInterdite/src/artefact_eau.png"),new ImageIcon("/Users/victor/Projet_POGL/IleInterdite/src/artefact_air.png")};



    public Vue() throws IOException {
        modele = new Modele();
        taille = modele.Taille;
        //ImageJoueurs = new ImageIcon[modele.nb_joueurs];

        Fenetre = new JFrame();
        afficheGrille();
        afficherArtefact();
        //System.out.println(Fenetre);
        Fenetre.setSize(600, 600);
        Fenetre.setTitle("Ile Interdite");




        //InfoZone.setBackground(Color.BLACK);
        JLabel InfoJoueur = new JLabel("Joueur:0");
        InfoJoueur.setBounds(200,500,200,50);

        JLabel InfoActionsRestantes = new JLabel("Actions Restantes:"+modele.Joueurs[modele.CurrentJoueur].nombre_tours);
        InfoActionsRestantes.setBounds(200,525,200,50);

        JLabel ArtefactsObtenus = new JLabel("Artefacts obtenus:");
        ArtefactsObtenus.setBounds(330,500,200,50);

        JLabel ClesObtenues = new JLabel("Clés obtenues:");
        ClesObtenues.setBounds(330,525,240,50);

        BoutonHeli = new JButton("Helicoptere");
        BoutonHeli.setBounds(500,530,100,50);
        BoutonHeli.setBackground(Color.BLUE);
        BoutonHeli.setEnabled(false);


        BoutonHeli.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                modele.HelicoptereTeleportationActive = true;
                BoutonFinDeTour.setEnabled(false);
                setMovementButtonNotClickable();
                AssechePos.setEnabled(false);
                AssecheBas.setEnabled(false);
                AssecheHaut.setEnabled(false);
                AssecheDroite.setEnabled(false);
                AssecheGauche.setEnabled(false);
                BoutonHeli.setEnabled(false);
            }
        });

        BouttonSacDeSable = new JButton("SacDeSable");
        BouttonSacDeSable.setBounds(500,470,100,50);
        BouttonSacDeSable.setBackground(Color.BLUE);
        BouttonSacDeSable.setEnabled(false);


        BouttonSacDeSable.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                modele.SacDeSableActive = true;
                BoutonFinDeTour.setEnabled(false);
                setMovementButtonNotClickable();
                AssechePos.setEnabled(false);
                AssecheBas.setEnabled(false);
                AssecheHaut.setEnabled(false);
                AssecheDroite.setEnabled(false);
                AssecheGauche.setEnabled(false);
                BouttonSacDeSable.setEnabled(false);

            }
        });

        RecupererArtefactB = new JButton("artefact");
        RecupererArtefactB.setBounds(450,0,100,50);
        RecupererArtefactB.setBackground(Color.BLUE);
        RecupererArtefactB.setEnabled(false);

        RecupererArtefactB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!modele.finDeTour){
                    Joueur j =  modele.Joueurs[modele.CurrentJoueur];

                    //modele.p.grille[j.x][j.y].panel.updateUI();
                    j.prendArtefact();
                    //modele.p.grille[j.x][j.y].panel.updateUI();
                    int actionsRestantes = modele.Joueurs[modele.CurrentJoueur].nombre_tours-modele.Joueurs[modele.CurrentJoueur].nb_tours_joues;
                    InfoActionsRestantes.setText("Actions Restantes:"+actionsRestantes);
                    afficherArtefact();
                    if(modele.finDeTour){
                        setMovementButtonNotClickable();
                    }
                    RecupererArtefactB.setEnabled(false);
                    String artefactsText = "";
                    for (int i=0; i < 4; i++){
                        if (j.ArtefactsPossedes[i]){
                            artefactsText +=ArtefactFromIndex(i);
                        }
                    }
                    ArtefactsObtenus.setText("Artefacts obtenus:"+artefactsText);
                }
            }
        });

        AssechePos = new JButton("ASP");
        AssechePos.setBounds(500,100,50,50);
        AssechePos.setBackground(Color.BLUE);

        AssechePos.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!modele.finDeTour){
                    Joueur j =  modele.Joueurs[modele.CurrentJoueur];
                    j.assecheCase("pos");
                    int actionsRestantes = modele.Joueurs[modele.CurrentJoueur].nombre_tours-modele.Joueurs[modele.CurrentJoueur].nb_tours_joues;
                    InfoActionsRestantes.setText("Actions Restantes:"+actionsRestantes);

                    if(modele.finDeTour){
                        setMovementButtonNotClickable();
                    }
                }
            }
        });

        AssecheDroite = new JButton("ASD");
        AssecheDroite.setBounds(550,100,50,50);
        AssecheDroite.setBackground(Color.BLUE);

        AssecheDroite.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!modele.finDeTour){
                    Joueur j =  modele.Joueurs[modele.CurrentJoueur];

                    j.assecheCase("droite");


                    //modele.p.grille[j.x][j.y].panel.updateUI();

                    int actionsRestantes = modele.Joueurs[modele.CurrentJoueur].nombre_tours-modele.Joueurs[modele.CurrentJoueur].nb_tours_joues;
                    InfoActionsRestantes.setText("Actions Restantes:"+actionsRestantes);

                    if(modele.finDeTour){
                        setMovementButtonNotClickable();
                    }
                }
            }
        });

        AssecheGauche = new JButton("ASG");
        AssecheGauche.setBounds(450,100,50,50);
        AssecheGauche.setBackground(Color.BLUE);

        AssecheGauche.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!modele.finDeTour){
                    Joueur j =  modele.Joueurs[modele.CurrentJoueur];

                    j.assecheCase("gauche");


                    //modele.p.grille[j.x][j.y].panel.updateUI();

                    int actionsRestantes = modele.Joueurs[modele.CurrentJoueur].nombre_tours-modele.Joueurs[modele.CurrentJoueur].nb_tours_joues;
                    InfoActionsRestantes.setText("Actions Restantes:"+actionsRestantes);

                    if(modele.finDeTour){
                        setMovementButtonNotClickable();
                    }
                }
            }
        });

        AssecheHaut = new JButton("ASH");
        AssecheHaut.setBounds(500,50,50,50);
        AssecheHaut.setBackground(Color.BLUE);

        AssecheHaut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!modele.finDeTour){
                    Joueur j =  modele.Joueurs[modele.CurrentJoueur];

                    j.assecheCase("haut");


                    //modele.p.grille[j.x][j.y].panel.updateUI();

                    int actionsRestantes = modele.Joueurs[modele.CurrentJoueur].nombre_tours-modele.Joueurs[modele.CurrentJoueur].nb_tours_joues;
                    InfoActionsRestantes.setText("Actions Restantes:"+actionsRestantes);

                    if(modele.finDeTour){
                        setMovementButtonNotClickable();
                    }
                }
            }
        });

        AssecheBas = new JButton("ASB");
        AssecheBas.setBounds(500,150,50,50);
        AssecheBas.setBackground(Color.BLUE);

        AssecheBas.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!modele.finDeTour){
                    Joueur j =  modele.Joueurs[modele.CurrentJoueur];

                    j.assecheCase("bas");


                    //modele.p.grille[j.x][j.y].panel.updateUI();

                    int actionsRestantes = modele.Joueurs[modele.CurrentJoueur].nombre_tours-modele.Joueurs[modele.CurrentJoueur].nb_tours_joues;
                    InfoActionsRestantes.setText("Actions Restantes:"+actionsRestantes);

                    if(modele.finDeTour){
                        setMovementButtonNotClickable();
                    }
                }
            }
        });


        BoutonFinDeTour = new JButton("Fin de Tour");
        BoutonFinDeTour.setBounds(50,500,125,75);
        BoutonFinDeTour.setBackground(Color.BLUE);

        BoutonFinDeTour.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //if (modele.finDeTour)
                for ( int k=0 ; k<3 ; k++) {
                    Random random = new Random();
                    int i = random.nextInt(taille);

                    Random random1 = new Random();
                    int j = random1.nextInt(taille);
                    modele.p.grille[i][j].inondeZone();
                    modele.p.grille[i][j].colorie();
                }
                //Joueur j1 = modele.Joueurs[modele.CurrentJoueur];

                //j1.FinDeTourCle();

                modele.prochainJoueur();
                InfoJoueur.setText("Joueur:"+modele.Joueurs[modele.CurrentJoueur].id);
                int actionsRestantes = modele.Joueurs[modele.CurrentJoueur].nombre_tours-modele.Joueurs[modele.CurrentJoueur].nb_tours_joues;
                InfoActionsRestantes.setText("Actions Restantes:"+actionsRestantes);
                setMovementButtonClickable();
                Joueur j = modele.Joueurs[modele.CurrentJoueur];
                j.FinDeTour();
                if(j.possedeSacDeSable){
                    //modele.SacDeSableActive = true;
                    BouttonSacDeSable.setEnabled(true);
                }
                else {
                    modele.SacDeSableActive = false;
                    BouttonSacDeSable.setEnabled(false);
                }
                if(j.possedeTeleportation){
                    //modele.SacDeSableActive = true;
                    BoutonHeli.setEnabled(true);
                }
                else {
                    modele.HelicoptereTeleportationActive = false;
                    BoutonHeli.setEnabled(false);
                }
                if (modele.p.grille[j.x][j.y].contientArtefact){
                    RecupererArtefactB.setEnabled(cleCorrespondArtefact(j,modele.p.grille[j.x][j.y].artefact));
                }
                else {RecupererArtefactB.setEnabled(false);}
                String artefactsText = "";
                for (int i=0; i < 4; i++){
                    if (j.ArtefactsPossedes[i]){
                        artefactsText += ArtefactFromIndex(i)+" ";
                    }
                }
                ArtefactsObtenus.setText("Artefacts obtenus:"+artefactsText);

                String cleText = "";
                for (int i=0; i < 4; i++){
                    if (j.ClePossedees[i]){
                        cleText += ArtefactFromIndex(i)+" ";
                    }
                }
                ClesObtenues.setText("Cles obtenues:"+cleText);
                if (modele.verifPartiePerdue()){
                    afficheGrilleRouge();
                    setMovementButtonNotClickable();
                    BoutonFinDeTour.setEnabled(false);
                }
                if (modele.verifPartieGagnee()){
                    afficheGrilleVert();
                    setMovementButtonNotClickable();
                    BoutonFinDeTour.setEnabled(false);
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
                        if (images[i].getName() != "artefact") {
                            modele.p.grille[j.x][j.y].panel.remove(images[i]);
                        }
                    }
                    modele.p.grille[j.x][j.y].panel.updateUI();
                    modele.Joueurs[modele.CurrentJoueur].move("droite");
                    int actionsRestantes = modele.Joueurs[modele.CurrentJoueur].nombre_tours-modele.Joueurs[modele.CurrentJoueur].nb_tours_joues;
                    InfoActionsRestantes.setText("Actions Restantes:"+actionsRestantes);

                    try {
                        afficheJoueurs();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if(modele.finDeTour){
                        setMovementButtonNotClickable();
                    }
                    else{
                        if (modele.p.grille[j.x][j.y].contientArtefact){
                            RecupererArtefactB.setEnabled(cleCorrespondArtefact(j,modele.p.grille[j.x][j.y].artefact));
                        }
                        else {RecupererArtefactB.setEnabled(false);}


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
                        if (images[i].getName() != "artefact") {
                            modele.p.grille[j.x][j.y].panel.remove(images[i]);
                        }
                    }
                    modele.p.grille[j.x][j.y].panel.updateUI();
                    modele.Joueurs[modele.CurrentJoueur].move("gauche");
                    int actionsRestantes = modele.Joueurs[modele.CurrentJoueur].nombre_tours-modele.Joueurs[modele.CurrentJoueur].nb_tours_joues;
                    InfoActionsRestantes.setText("Actions Restantes:"+actionsRestantes);
                    try {
                        afficheJoueurs();
                        afficherArtefact();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if(modele.finDeTour){
                        setMovementButtonNotClickable();
                    }
                    else{
                        if (modele.p.grille[j.x][j.y].contientArtefact){
                            RecupererArtefactB.setEnabled(cleCorrespondArtefact(j,modele.p.grille[j.x][j.y].artefact));
                        }
                        else {RecupererArtefactB.setEnabled(false);}

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
                        if (images[i].getName() != "artefact") {
                            modele.p.grille[j.x][j.y].panel.remove(images[i]);
                        }
                    }
                    modele.p.grille[j.x][j.y].panel.updateUI();
                    modele.Joueurs[modele.CurrentJoueur].move("haut");
                    int actionsRestantes = modele.Joueurs[modele.CurrentJoueur].nombre_tours-modele.Joueurs[modele.CurrentJoueur].nb_tours_joues;
                    InfoActionsRestantes.setText("Actions Restantes:"+actionsRestantes);
                    try {
                        afficheJoueurs();
                        afficherArtefact();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if(modele.finDeTour){
                        setMovementButtonNotClickable();
                    }
                    else{
                        if (modele.p.grille[j.x][j.y].contientArtefact){
                            RecupererArtefactB.setEnabled(cleCorrespondArtefact(j,modele.p.grille[j.x][j.y].artefact));
                        }
                        else {RecupererArtefactB.setEnabled(false);}

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
                        if (images[i].getName() != "artefact") {
                            modele.p.grille[j.x][j.y].panel.remove(images[i]);
                        }
                    }
                    modele.p.grille[j.x][j.y].panel.updateUI();
                    modele.Joueurs[modele.CurrentJoueur].move("bas");
                    int actionsRestantes = modele.Joueurs[modele.CurrentJoueur].nombre_tours-modele.Joueurs[modele.CurrentJoueur].nb_tours_joues;
                    InfoActionsRestantes.setText("Actions Restantes:"+actionsRestantes);

                    try {
                        afficheJoueurs();//peut etre faudrait appliquer move à affiche joueurs ou bien creer in move special image(un peu useless)
                        afficherArtefact();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if(modele.finDeTour){
                        setMovementButtonNotClickable();

                    }
                    else{
                        if (modele.p.grille[j.x][j.y].contientArtefact){
                            RecupererArtefactB.setEnabled(cleCorrespondArtefact(j,modele.p.grille[j.x][j.y].artefact));
                        }
                        else {RecupererArtefactB.setEnabled(false);}
                    }



                }
            }
        });

        Fenetre.add(BoutonFinDeTour);
        Fenetre.add(BoutonDroite);
        Fenetre.add(BoutonGauche);
        Fenetre.add(BoutonHaut);
        Fenetre.add(BoutonBas);
        Fenetre.add(AssecheDroite);
        Fenetre.add(AssecheBas);
        Fenetre.add(AssecheGauche);
        Fenetre.add(AssecheHaut);
        Fenetre.add(AssechePos);
        Fenetre.add(InfoJoueur);
        Fenetre.add(RecupererArtefactB);
        Fenetre.add(InfoActionsRestantes);
        Fenetre.add(ArtefactsObtenus);
        Fenetre.add(ClesObtenues);
        Fenetre.add(BouttonSacDeSable);
        Fenetre.add(BoutonHeli);
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

        AssecheDroite.setEnabled(false);
        AssecheGauche.setEnabled(false);
        AssecheBas.setEnabled(false);
        AssecheHaut.setEnabled(false);
        AssechePos.setEnabled(false);

        RecupererArtefactB.setEnabled(false);
    }

    public void setMovementButtonClickable(){
        BoutonGauche.setEnabled(true);
        BoutonDroite.setEnabled(true);
        BoutonHaut.setEnabled(true);
        BoutonBas.setEnabled(true);

        AssecheDroite.setEnabled(true);
        AssecheGauche.setEnabled(true);
        AssecheBas.setEnabled(true);
        AssecheHaut.setEnabled(true);
        AssechePos.setEnabled(true);

    }
    public void afficheJoueurs() throws IOException {
        for (Joueur j: modele.Joueurs){
            //JLabel picLabel = new JLabel(new ImageIcon(ImageJoueur));
            modele.p.grille[j.x][j.y].panel.add(new JLabel(ImageJoueurs[j.id]));
            //modele.p.grille[j.x][j.y].panel.setBackground(Color.GREEN);
            modele.p.grille[j.x][j.y].panel.repaint();
            modele.p.grille[j.x][j.y].panel.updateUI();


        }
    }


    public void SacDeSable(int x, int y){
        if (modele.SacDeSableActive){
            modele.Joueurs[modele.CurrentJoueur].possedeSacDeSable = false;
            modele.p.grille[x][y].asseche();
            modele.SacDeSableActive = false;
            BoutonFinDeTour.setEnabled(true);
            setMovementButtonClickable();
            AssechePos.setEnabled(true);
            AssecheBas.setEnabled(true);
            AssecheHaut.setEnabled(true);
            AssecheDroite.setEnabled(true);
            AssecheGauche.setEnabled(true);
        }

    }


    public void TeleportationHeli(int x, int y)  {
        if (modele.HelicoptereTeleportationActive){
            modele.Joueurs[modele.CurrentJoueur].possedeTeleportation = false;
            modele.HelicoptereTeleportationActive = false;
            BoutonFinDeTour.setEnabled(true);
            setMovementButtonClickable();
            AssechePos.setEnabled(true);
            AssecheBas.setEnabled(true);
            AssecheHaut.setEnabled(true);
            AssecheDroite.setEnabled(true);
            AssecheGauche.setEnabled(true);
            if (!(modele.p.grille[x][y].estSubmerge())){
                Joueur j = modele.Joueurs[modele.CurrentJoueur];

                modele.p.grille[j.x][j.y].removeJoueur(j);
                modele.p.grille[j.x][j.y].panel.repaint();
                modele.p.grille[j.x][j.y].panel.updateUI();
                modele.p.grille[x][y].putJoueur(j);
                modele.p.grille[x][y].panel.updateUI();

                Component images[] = modele.p.grille[j.x][j.y].panel.getComponents();
                for (int i=0;i<images.length;i++){
                    if (images[i].getName() != "artefact") {
                        modele.p.grille[j.x][j.y].panel.remove(images[i]);
                    }
                }
                j.x = x;
                j.y = y;
                if (modele.p.grille[j.x][j.y].contientArtefact){
                    RecupererArtefactB.setEnabled(cleCorrespondArtefact(j,modele.p.grille[j.x][j.y].artefact));
                }
                else {RecupererArtefactB.setEnabled(false);}
                try{
                    afficheJoueurs();

                }
                catch (IOException e){}


            }



        }

    }


    public void afficheGrille(){ //creer la grille
        for (int i=0;i<taille; i++){
            for (int j=0;j<taille;j++){
                JPanel ZonePanel = new JPanel();
                ZonePanel.setBounds(i*TailleCase,j*TailleCase,TailleCase,TailleCase);
                int finalI = i;
                int finalJ = j;
                ZonePanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        //System.out.println("x:"+ finalI +",y:"+ finalJ);
                        if (modele.SacDeSableActive)
                            SacDeSable(finalI,finalJ);
                        if (modele.HelicoptereTeleportationActive)
                            TeleportationHeli(finalI,finalJ);
                    }});
                Case c = modele.p.grille[i][j];


                c.panel = ZonePanel;
                c.colorie();
                Fenetre.add(ZonePanel);
            }
        }
    }


    public void afficheGrilleRouge(){
        for (int i=0;i<taille; i++) {
            for (int j = 0; j < taille; j++) {
                modele.p.grille[i][j].panel.setBackground(Color.RED);
            }
        }
    }

    public void afficheGrilleVert(){
        for (int i=0;i<taille; i++) {
            for (int j = 0; j < taille; j++) {
                modele.p.grille[i][j].panel.setBackground(Color.GREEN);
            }
        }
    }

    public void afficherArtefact(){
        for (int i=0;i<taille; i++) {
            for (int j = 0; j < taille; j++) {
                Case c = modele.p.grille[i][j];
                if (c.contientArtefact){
                    Artefact a = c.artefact;
                    //JLabel picLabel = new JLabel(new ImageIcon(ImageJoueur));
                    if (!(a.type == Elem.Rien)) {
                        JLabel ArtefactLabel = new JLabel(ImageArtefacts[ArtefactIndex(a)]);
                        ArtefactLabel.setName("artefact");
                        a.c.panel.add(ArtefactLabel);
                    }
                    a.c.panel.repaint();
                    a.c.panel.updateUI();
                }
                //modele.p.grille[j.x][j.y].panel.setBackground(Color.GREEN);
            }
        }
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
    public String ArtefactFromIndex(int i){
        switch (i){
            case 0:
                return "Terre";
            case 1:
                return "Feu";
            case 2:
                return "Eau";
            case 3:
                return "Air";
        }
        return "";
    }

    public boolean cleCorrespondArtefact(Joueur j, Artefact a){
        for (int i=0;i<4; i++){
            if(j.ClePossedees[i]){
                System.out.println("cle:"+ArtefactFromIndex(i)+"; nom artefact:"+a.type.name());
                if(ArtefactFromIndex(i) == a.type.name()){
                    return true;
                }
            }
        }
        return false;
    }
}
