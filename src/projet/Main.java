package projet;

import projet.graphic.ThemeVue;
import projet.graphic.front;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class Main implements Serializable {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
       //initialisationthemesQ();
       //testSerialization();
        // initialisationjoueurs();
        testGraphe();
        //testThemeVue();

    }

    private static void testThemeVue(){
        ThemeVue t = new ThemeVue();
    }

    private static void testGraphe() throws IOException, ClassNotFoundException {
        front f = new front();
    }

    private static void initialisationjoueurs()throws IOException{
         Vector<Joueur> ListeJ = new Vector<>(20);
         int numJoueurs=0;
         char nomJ = 65;//'@' ascii
         int i;

         for(i=0;i<=19; i++){
        Joueur J3 = new Joueur(Character.toString(nomJ),numJoueurs);
        ListeJ.add(J3);
        numJoueurs+=10;
         nomJ+=1;}
        File fichier = new File("src/projet/joueur/listeJoueurs.txt");
        ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(fichier));
        a.writeObject(ListeJ);
    }

    private static void initialisationthemesQ()  throws IOException{
        ArrayList<String> list = new ArrayList<>();

        /** création fichier theme **/
        list.add("Histoire");
        list.add("Geographie");
        list.add("Automobile");
        list.add("Mathématiques");
        list.add("Francais");
        list.add("Cuisine");
        list.add("Sport");
        list.add("Politique");
        list.add("Animaux");
        list.add("Astronomie");

        File fichier = new File("src/projet/themesQ/themes.txt");
        ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(fichier));
        a.writeObject(list);

        /** Initialisation des questions, adapter en fonction du thèmes et des questions**/
        ArrayList<String>  liste= new  ArrayList<String>();
        liste.add("Marignan");
        liste.add("Verdun");
        liste.add("Paris");
        Question<QCM> quest1 = new Question<>(new QCM("Ou a eut lieu la bataille de 1515 ?", liste, "Marignan"),1 , "Histoire");
        ArrayList<String>  liste2= new  ArrayList<String>();
        liste2.add("Hamid Karzai ");
        liste2.add("Hosni Moubarak");
        liste2.add("Pervez Musharraf");
        Question<QCM> quest4 = new Question<>(new QCM("Quel président égyptien a dû abandonner le pouvoir en 2011, suite au Printemps arabe ?", liste2, "Hosni Moubarak"),2 , "Histoire");
        ArrayList<String>  liste3= new  ArrayList<String>();
        liste3.add("Janvier");
        liste3.add("Mai");
        liste3.add("Août");
        Question<QCM> quest7 = new Question<>(new QCM("Lors de quel mois de l’année 1789, la Révolution française a-t-elle débuté avec l’ouverture des États généraux ?", liste3, "Mai"),3 , "Histoire");
        Question<VF> quest2  = new Question<>(new VF("Les girondins sont un mouvement des lumières ", false), 1, "Histoire");
        Question<VF> quest5  = new Question<>(new VF("La revolte des Boxers a eut lieu en Chine ", true), 2, "Histoire");
        Question<VF> quest8  = new Question<>(new VF("Fidel Castro a destitue Marcos Perez Jimenez en 1959", false), 3, "Histoire");
        Question<RC> quest3  = new Question<>(new RC("Comment appel t'on le fils héritier du roi de france", "ledauphin"), 1, "Histoire");
        Question<RC> quest6  = new Question<>(new RC("Qui fut execute apres la révolution francaise le 16 octobre 1793 ?", "Lareine"), 2, "Histoire");
        Question<RC> quest9  = new Question<>(new RC("Qui fur le premier roi de france ?", "clovis"), 3, "Histoire");
        LinkedList<Question<? extends QType>> qList = new LinkedList<>();

        qList.add(quest1);
        qList.add(quest2);
        qList.add(quest3);
        qList.add(quest4);
        qList.add(quest5);
        qList.add(quest6);
        qList.add(quest7);
        qList.add(quest8);
        qList.add(quest9);

        /** Sérialisation de la question**/
        fichier = new File("src/projet/themesQ/Histoire.txt");//Creation du fichier de question pour un thème
        a = new ObjectOutputStream(new FileOutputStream(fichier));
        a.writeObject(qList);
    }

    private static void testSerialization()  throws IOException, ClassNotFoundException {
        ArrayList<String> list;

        File fichier = new File("src/projet/themesQ/themes.txt");
        ObjectInputStream b = new ObjectInputStream(new FileInputStream(fichier)); /** lecture du fichier**/
        list = (ArrayList<String>) b.readObject();                                 /** lecture du fichier**/
        Themes themes = new Themes(list);
        themes.afficher();
//        themes.modifierTheme();
//        themes.afficher();
//        themes.selectionnerTheme();
//        themes.afficher();
        System.out.println("Selection de 5 th\u00e8mes random :");
        System.out.println(themes.selectionnerCinqThemes());

        LinkedList<Question<? extends QType>> qList;


        /** Sérialisation de la question**/
        fichier = new File("src/projet/themesQ/Histoire.txt");

        b = new ObjectInputStream(new FileInputStream(fichier));     /** lecture du fichier**/
        qList = (LinkedList<Question<? extends QType>>) b.readObject(); /** lecture du fichier**/
        ListeQuestions listeQuestions = new ListeQuestions(qList);
        listeQuestions.afficherListe();
       // qList.get(1).afficher();
        //qList.get(1).saisir();
    }


}
