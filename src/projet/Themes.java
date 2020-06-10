package projet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Themes {
    private ArrayList<String> themes;
    private int indicateur = -1;

    public Themes(ArrayList<String> themes) {
        if (themes == null) {
            throw new NullPointerException("Votre input est null !");
        }
        // hashset peut pas avoir de duplicates donc on enleve tout duplicates possible
        themes = new ArrayList<>(new HashSet<>(themes));
        // les reponses ne doivent pas etre vides
        ArrayList<String> list = new ArrayList<>();
        for (String rep : themes) {
            if (!rep.isBlank()) {
                list.add(rep);
            }
        }
        themes = list;
        // cas où la liste est vide
        if (themes.isEmpty()) {
            throw new IllegalArgumentException("Th\u00e8mes vides.");
        }
        this.themes = themes;
    }

    // modification d'un thème déjà créé
    public void modifierTheme() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Th\u00e8me \u00e0 modifier :");
        String theme = sc.nextLine();
        System.out.println("Nouveau th\u00e8me :");
        String newTheme = sc.nextLine();
        if (!themes.contains(theme)) {
            System.out.println("Ce th\u00e8me n'existe pas.");
        } else {
            themes.set(themes.indexOf(theme), newTheme);
            System.out.println("Th\u00e8me modifi\u00e9.");

            File fichier = new File("src/projet/themesQ/themes.txt");
        ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(fichier));
             a.writeObject(this.themes);
        }
    }

    // selection d'un theme, return l'index
    public int selectionnerTheme() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choisissez un th\u00e8me :");
        String theme = sc.nextLine();
        if (themes.contains(theme)) {
            int index = indicateur;
            indicateur = themes.indexOf(theme);
            return index;
        } else {
            return -1;
        }
    }

    // return array de 5 themes random
    public ArrayList<String> selectionnerCinqThemes() {
        ArrayList<String> themesCopie = new ArrayList<>(themes);
        ArrayList<String> themesChoisis = new ArrayList<>();
        IntStream.range(0, 5).map(i -> (int)(Math.random() * 100) % themesCopie.size()).forEachOrdered(randomNum -> {
            themesChoisis.add(themesCopie.get(randomNum)); // selection random
            themesCopie.remove(randomNum);
        });
        return themesChoisis;
    }

    public ArrayList<String> selectionner6Themes() {
        ArrayList<String> themesCopie = new ArrayList<>(themes);
        ArrayList<String> themesChoisis = new ArrayList<>();
        IntStream.range(0, 6).map(i -> (int)(Math.random() * 100) % themesCopie.size()).forEachOrdered(randomNum -> {
            themesChoisis.add(themesCopie.get(randomNum)); // selection random
            themesCopie.remove(randomNum);
        });
        return themesChoisis;
    }

    // affiche la liste et le theme choisi
    public void afficher() {
        System.out.println("Liste des th\u00e8mes :");
        for (String theme : themes) {
            System.out.println(theme);
        }
        System.out.print("Th\u00e8me choisi : ");
        System.out.println(((indicateur < 0) || (indicateur >= themes.size())) ? "aucun.\nIndicateur : " + indicateur : themes.get(indicateur) + ".\nIndicateur : " + indicateur);
    }

    public String get(int num){
        return themes.get(num);
    }

    public int size(){
        return themes.size();
    }

    public void clear(){
        themes.clear();
        indicateur = -1;
    }
}
