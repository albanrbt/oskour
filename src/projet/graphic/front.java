package projet.graphic;

import projet.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;


public class front extends JFrame implements Phase {


    private JPanel NomJoueur;
    private JPanel QCM;
    private JPanel frontGame;
    private JLabel J1Choix;

    /**
     * Variable pour la définition du nom des joueurs
     **/
    private JLabel J2Choix;
    private JLabel J3Choix;
    private JLabel ChoixJ4;
    private JButton ChoixJButton;
    private JTextField textFieldJ1;
    private JTextField textFieldJ3;
    private JTextField textFieldJ2;
    private JTextField textFieldJ4;

    /**
     * QCM
     **/
    private JLabel QCMQuest;
    private JButton QCMrep1;
    private JButton QCMrep2;
    private JButton QCMrep3;
    private JLabel NomJQCM;
    private JLabel InstructionQCM;

    /**
     * RC
     **/
    private JPanel RC;
    private JLabel InstructionRC;
    private JLabel NomJRC;
    private JLabel RCquest;
    private JTextArea RcAns;
    private JButton RcButton;

    /**
     * VF
     **/
    private JPanel VF;
    private JButton TrueB;
    private JButton FalseB;
    private JLabel VFinstruction;
    private JLabel VfQuest;
    private JLabel NomJVF;
    private JPanel result;
    private JLabel rinfo;
    private JLabel First;
    private JLabel Score1;
    private JLabel Second;
    private JLabel Score2;
    private JLabel Third;
    private JLabel Score3;
    private JLabel fourth;
    private JLabel Score4;
    private JLabel Time1;
    private JLabel Time2;
    private JLabel Time3;
    private JLabel Time4;
    private JButton Next;

    /**
     * Variables divers et variées
     **/
    private Vector<Joueur> ListeJ = new Vector<>(20);
    private int numJoueurs = 0;
    private EnsJoueurs PlayerManche = new EnsJoueurs();
    private EnsJoueurs Participants = new EnsJoueurs();
    private Themes themes;
    private int ChoixTheme;
    private Question question;

    public front() throws IOException, ClassNotFoundException {

        RcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (question.isRC().check(RcAns.getText())) {
                    PlayerManche.getJoueur(numJoueurs).setScore(2);
                    PlayerManche.getJoueur(numJoueurs).afficher();
                } else {
                    PlayerManche.getJoueur(numJoueurs).setScore(0);
                }
                finQuest();
            }
        });

        QCMrep1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (QCMrep1.getText().equals(question.isQCM().getBonneRep())) {
                    System.out.println("FFFFFF : " + numJoueurs);
                    PlayerManche.getJoueur(numJoueurs).setScore(2);
                    PlayerManche.getJoueur(numJoueurs).afficher();

                } else {
                    PlayerManche.getJoueur(numJoueurs).setScore(0);
                    System.out.println(numJoueurs);

                }
                finQuest();
            }
        });

        QCMrep2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (QCMrep2.getText().equals(question.isQCM().getBonneRep())) {
                    System.out.println(numJoueurs);
                    PlayerManche.getJoueur(numJoueurs).setScore(2);
                    PlayerManche.getJoueur(numJoueurs).afficher();

                } else {
                    PlayerManche.getJoueur(numJoueurs).setScore(0);

                }
                finQuest();
            }
        });

        QCMrep3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (QCMrep3.getText().equals(question.isQCM().getBonneRep())) {
                    PlayerManche.getJoueur(numJoueurs).setScore(2);
                    PlayerManche.getJoueur(numJoueurs).afficher();

                } else {
                    PlayerManche.getJoueur(numJoueurs).setScore(0);

                }
                finQuest();
            }
        });

        TrueB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (question.isVF().isBonneRep()) {
                    PlayerManche.getJoueur(numJoueurs).setScore(2);
                    PlayerManche.getJoueur(numJoueurs).afficher();
                } else {
                    PlayerManche.getJoueur(numJoueurs).setScore(0);
                }
                finQuest();
            }
        });

        FalseB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean verif = false;
                if (!question.isVF().isBonneRep()) {
                    PlayerManche.getJoueur(numJoueurs).setScore(2);
                    PlayerManche.getJoueur(numJoueurs).afficher();
                } else {
                    PlayerManche.getJoueur(numJoueurs).setScore(0);
                }
                finQuest();
            }
        });

        this.setContentPane(NomJoueur);
        this.setSize(500, 200);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        saisisNom();
    }


    /**
     * Permet de modifier les noms des joueurs séléctionnés
     **/
    private void saisisNom() throws IOException, ClassNotFoundException {
        File fichier = new File("src/projet/joueur/listeJoueurs.txt");
        /** lecture du fichier**/
        ObjectInputStream b = new ObjectInputStream(new FileInputStream(fichier));
        ListeJ = (Vector<Joueur>) b.readObject();//on récupère toutes la liste des participants sauvegardée
        /** lecture du fichier**/
        Participants.creer(ListeJ);             //on fait de cette liste un ensemble de joueurs
        // Participants.afficher();
        Vector<Joueur> ListeP = new Vector<>(4); //on séléctionne 4 joueurs dans la liste qui vont devoir s'affronter
        for (int i = 0; i < 4; i++) {
            int x = 0;
            Joueur selection = Participants.selectionnerJoueur();
            while (x == 0) {                                      // on vérifie qu'on ne peut pas sélectionner deux fois le meme joueur
                if (!ListeP.contains(selection)) {
                    x = 1;
                } else {
                    selection = Participants.selectionnerJoueur();
                }
            }
            ListeP.add(selection);
        }
        //on crée un autre ensemble de joueurs, qui nous permettra de ne traiter que
        PlayerManche.creer(ListeP);                            // les joueurs séléctionné. Toutes modifications faites sur un joueurs de cette liste se fera aussi sur le joueur
        ChoixJButton.addActionListener(new ActionListener() {   // de la liste principale
            @Override
            public void actionPerformed(ActionEvent actionEvent) {  // on récupère les noms
                if (!textFieldJ1.getText().equals("")) {
                    PlayerManche.getJoueur(0).setNom(textFieldJ1.getText());
                }
                if (!textFieldJ2.getText().equals("")) {
                    PlayerManche.getJoueur(1).setNom(textFieldJ2.getText());
                }
                if (!textFieldJ3.getText().equals("")) {
                    PlayerManche.getJoueur(2).setNom(textFieldJ3.getText());
                }
                if (!textFieldJ4.getText().equals("")) {
                    PlayerManche.getJoueur(3).setNom(textFieldJ4.getText());
                }

                PlayerManche.afficher();
                // Participants.afficher();
                try {
                    initPhase1();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initPhase1() throws IOException, ClassNotFoundException {
        getTheme();
        //themes.afficher();
        ChoixTheme = (int) (Math.random() * 100) % themes.size();
//        for (int i = 0; i < PlayerManche.size(); i++) {
//            String th = SelectThemes();
//            System.out.println(th);
//            System.out.println(i);
//            PlayerManche.getJoueur(i).setScore(QuestP1(th, i));
//        }
        Phase1();
    }

    private void Phase1() throws IOException, ClassNotFoundException {
        System.out.println("nuJ=" + numJoueurs);
        System.out.println(PlayerManche.size());
        String th = SelectThemes();
        System.out.println(th);
        QuestP1(th);

    }

    private void QuestP1(String Theme) throws IOException, ClassNotFoundException {

        LinkedList<Question<? extends QType>> qList;
        //File fichier = new File("src/projet/themesQ/" + Theme + ".txt");
        File fichier = new File("src/projet/themesQ/Histoire.txt");
        ObjectInputStream b = new ObjectInputStream(new FileInputStream(fichier));     /** lecture du fichier**/
        qList = (LinkedList<Question<? extends QType>>) b.readObject(); /** lecture du fichier**/
        ListeQuestions listeQuestions = new ListeQuestions(qList);

        question = listeQuestions.selectionnerQuestion(1);

        //Qchoix.afficher();
        System.out.println("Checking type of object in Java using  getClass() ==>");
        if (question.getTexte() instanceof QCM) {
            System.out.println("Question_que_tu_test is instance of QCM");
            QCM(question);
        }
        if (question.getTexte() instanceof VF) {
            System.out.println("Question_que_tu_test is instance of VF");
            VF(question);
        }
        if (question.getTexte() instanceof RC) {
            System.out.println("Question_que_tu_test is instance of RC");
            RC(question);
        }
    }

    private void result() throws IOException, ClassNotFoundException {

        resetClassement();

        ArrayList<Joueur> classement = new ArrayList<>(PlayerManche.getVector());

        Collections.sort(classement, new Comparator<Joueur>() {
            @Override
            public int compare(Joueur o1, Joueur o2) {
                return Integer.compare(o1.getScore(), o2.getScore());
            }
        });

        for(int i=0; i<classement.size(); i++){
            if (i==classement.size()-4){
                fourth.setText(classement.get(i).getNom());
                Score4.setText(String.valueOf(classement.get(i).getScore()));
                continue;
            }
            if (i==classement.size()-3){
                Third.setText(classement.get(i).getNom());
                Score3.setText(String.valueOf(classement.get(i).getScore()));
                continue;
            }
            if (i==classement.size()-2){
                Second.setText(classement.get(i).getNom());
                Score2.setText(String.valueOf(classement.get(i).getScore()));
                continue;
            }
            if (i==classement.size()-1){
                First.setText(classement.get(i).getNom());
                Score1.setText(String.valueOf(classement.get(i).getScore()));
            }
        }


        this.setContentPane(result);
        this.setSize(500, 200);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    private void resetClassement(){

        fourth.setText("");
        Score4.setText("");

        Third.setText("");
        Score3.setText("");

        Second.setText("");
        Score2.setText("");

        First.setText("");
        Score1.setText("");

    }

    private void QCM(Question<QCM> Question) throws IOException, ClassNotFoundException {
        this.setContentPane(QCM);
        this.setSize(500, 200);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        NomJQCM.setText(PlayerManche.getJoueur(numJoueurs).getNom());
        QCMQuest.setText(Question.getTexte().gettexte());
        QCMrep1.setText(Question.getTexte().getReponses().get(0));
        QCMrep2.setText(Question.getTexte().getReponses().get(1));
        QCMrep3.setText(Question.getTexte().getReponses().get(2));

    }

    private void VF(Question<VF> Question) throws IOException, ClassNotFoundException {
        this.setContentPane(VF);
        this.setSize(500, 200);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        NomJVF.setText(PlayerManche.getJoueur(numJoueurs).getNom());
        VfQuest.setText(Question.getTexte().getTexte());



    }

    private void RC(Question<RC> Question) throws IOException, ClassNotFoundException {
        this.setContentPane(RC);
        this.setSize(500, 200);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        NomJRC.setText(PlayerManche.getJoueur(numJoueurs).getNom());
        RCquest.setText(Question.getTexte().getTexte());
        RcAns.setText("");


    }

    private void finQuest() {
        System.out.println("Entrer finQuest" + numJoueurs);
        numJoueurs += 1;
        System.out.println("Sortie finQuest" + numJoueurs);
        if (numJoueurs < PlayerManche.size()) {
            try {
                Phase1();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                result();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void getTheme() throws IOException, ClassNotFoundException {
        ArrayList<String> list;

        File fichier = new File("src/projet/themesQ/themes.txt");
        ObjectInputStream b = new ObjectInputStream(new FileInputStream(fichier)); /** lecture du fichier**/
        list = (ArrayList<String>) b.readObject();                                 /** lecture du fichier**/
        themes = new Themes(list);
    }

    private String SelectThemes() {
        String th = themes.get(ChoixTheme);
        ChoixTheme++;
        ChoixTheme = ChoixTheme % themes.size();
        return th;
    }

    @Override
    public void SelectionnerJoueur() {

    }

    @Override
    public void PhaseDeJeu() {

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        frontGame = new JPanel();
        frontGame.setLayout(new GridBagLayout());
        NomJoueur = new JPanel();
        NomJoueur.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        frontGame.add(NomJoueur, gbc);
        NomJoueur.setBorder(BorderFactory.createTitledBorder(""));
        J1Choix = new JLabel();
        J1Choix.setText("Joueur1");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        NomJoueur.add(J1Choix, gbc);
        J2Choix = new JLabel();
        J2Choix.setText("Joueur2");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        NomJoueur.add(J2Choix, gbc);
        J3Choix = new JLabel();
        J3Choix.setText("Joueur3");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        NomJoueur.add(J3Choix, gbc);
        ChoixJ4 = new JLabel();
        ChoixJ4.setText("Joueur4");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        NomJoueur.add(ChoixJ4, gbc);
        ChoixJButton = new JButton();
        ChoixJButton.setText("OK");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        NomJoueur.add(ChoixJButton, gbc);
        textFieldJ1 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        NomJoueur.add(textFieldJ1, gbc);
        textFieldJ3 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        NomJoueur.add(textFieldJ3, gbc);
        textFieldJ2 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        NomJoueur.add(textFieldJ2, gbc);
        textFieldJ4 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        NomJoueur.add(textFieldJ4, gbc);
        QCM = new JPanel();
        QCM.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        frontGame.add(QCM, gbc);
        QCMQuest = new JLabel();
        QCMQuest.setText("question");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        QCM.add(QCMQuest, gbc);
        QCMrep1 = new JButton();
        QCMrep1.setText("rep1");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        QCM.add(QCMrep1, gbc);
        QCMrep2 = new JButton();
        QCMrep2.setText("rep2");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        QCM.add(QCMrep2, gbc);
        QCMrep3 = new JButton();
        QCMrep3.setText("rep3");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        QCM.add(QCMrep3, gbc);
        InstructionQCM = new JLabel();
        InstructionQCM.setText("A vous de jouer joueur:  ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        QCM.add(InstructionQCM, gbc);
        NomJQCM = new JLabel();
        NomJQCM.setText("Label");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        QCM.add(NomJQCM, gbc);
        RC = new JPanel();
        RC.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 6, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        frontGame.add(RC, gbc);
        InstructionRC = new JLabel();
        InstructionRC.setText("A vous de jouer :");
        RC.add(InstructionRC, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        NomJRC = new JLabel();
        NomJRC.setText("Label");
        RC.add(NomJRC, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        RCquest = new JLabel();
        RCquest.setText("Question :");
        RC.add(RCquest, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        RcAns = new JTextArea();
        RcAns.setText("");
        RC.add(RcAns, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        RC.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        RC.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(2, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        RC.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        RC.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        RcButton = new JButton();
        RcButton.setText("Valider");
        RC.add(RcButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 6, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        RC.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        RC.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        RC.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer8 = new com.intellij.uiDesigner.core.Spacer();
        RC.add(spacer8, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer9 = new com.intellij.uiDesigner.core.Spacer();
        RC.add(spacer9, new com.intellij.uiDesigner.core.GridConstraints(1, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer10 = new com.intellij.uiDesigner.core.Spacer();
        RC.add(spacer10, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer11 = new com.intellij.uiDesigner.core.Spacer();
        RC.add(spacer11, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        VF = new JPanel();
        VF.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        frontGame.add(VF, gbc);
        TrueB = new JButton();
        TrueB.setText("Vrai");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        VF.add(TrueB, gbc);
        FalseB = new JButton();
        FalseB.setText("Faux");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        VF.add(FalseB, gbc);
        VfQuest = new JLabel();
        VfQuest.setText("Question :");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        VF.add(VfQuest, gbc);
        VFinstruction = new JLabel();
        VFinstruction.setText("A vous de jouer  ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        VF.add(VFinstruction, gbc);
        NomJVF = new JLabel();
        NomJVF.setText("Label");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        VF.add(NomJVF, gbc);
        result = new JPanel();
        result.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        frontGame.add(result, gbc);
        rinfo = new JLabel();
        rinfo.setText("RESULT :");
        result.add(rinfo, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer12 = new com.intellij.uiDesigner.core.Spacer();
        result.add(spacer12, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer13 = new com.intellij.uiDesigner.core.Spacer();
        result.add(spacer13, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        First = new JLabel();
        First.setText("Label");
        result.add(First, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Score1 = new JLabel();
        Score1.setText("Label");
        result.add(Score1, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Second = new JLabel();
        Second.setText("Label");
        result.add(Second, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Score2 = new JLabel();
        Score2.setText("Label");
        result.add(Score2, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Third = new JLabel();
        Third.setText("Label");
        result.add(Third, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Score3 = new JLabel();
        Score3.setText("Label");
        result.add(Score3, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fourth = new JLabel();
        fourth.setText("Label");
        result.add(fourth, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Score4 = new JLabel();
        Score4.setText("Label");
        result.add(Score4, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Time1 = new JLabel();
        Time1.setText("Time1");
        result.add(Time1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Time2 = new JLabel();
        Time2.setText("Label");
        result.add(Time2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Time3 = new JLabel();
        Time3.setText("Label");
        result.add(Time3, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Time4 = new JLabel();
        Time4.setText("Label");
        result.add(Time4, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Next = new JButton();
        Next.setText("Suite");
        result.add(Next, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return frontGame;
    }

}

