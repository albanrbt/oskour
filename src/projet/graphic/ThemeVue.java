package projet.graphic;

import projet.QCM;
import projet.QType;
import projet.Question;
import projet.RC;
import projet.VF;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Theme vue.
 */
public class ThemeVue extends JFrame {
    private JList jList;
    private JLabel label;
    private JTextField textField;
    private JPanel panel;
    private JButton validerButton;
    private JPanel panel2 = new JPanel(new BorderLayout());
    private JPanel panel3 = new JPanel(new BorderLayout());
    private JPanel panel4 = new JPanel(new BorderLayout());
    private JButton modifier = new JButton("Modifier le th\u00e8me");
    private JButton afficher = new JButton("Afficher les questions");
    private JButton supprimer = new JButton("Supprimer une question");
    private JButton ajouter = new JButton("Ajouter une question");
    private String[] columnNames = {"Num\u00e9ro", "Type", "Question",
            "Bonne r\u00e9ponse", "R\u00e9ponse 2", "R\u00e9ponse 3",
            "Niveau"};
    private JTable table = new JTable() {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            // redimensione automatiquement la largeur des colonnes
            Component component = super.prepareRenderer(renderer, row, column);
            int rendererWidth = component.getPreferredSize().width;
            TableColumn tableColumn = getColumnModel().getColumn(column);
            tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
            return component;
        }
    };
    private DefaultTableModel model = new DefaultTableModel();
    private JScrollPane scrollPane = new JScrollPane(table);
    private DefaultTableCellRenderer cellRenderer;

    /**
     * Instantiates a new Theme vue.
     */
    public ThemeVue() {
        // config fenetre
        this.setContentPane(panel);
        this.setSize(500, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Menu th\u00e8mes");
        this.getRootPane().setDefaultButton(validerButton); // appuyer sur la touche entree == cliquer sur le bouton valider par défaut
        try {
            // deserialisation
            FileInputStream fi = new FileInputStream(new File("src/projet/themesQ/themes.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            ArrayList<String> list = (ArrayList<String>) oi.readObject(); // recupere la liste
            DefaultListModel listModel = new DefaultListModel();
            // add chaque theme dans la liste
            for (int i = 0; i < list.size(); i++) {
                listModel.addElement(list.get(i));
            }
            jList.setModel(listModel);
            validerButton.addActionListener(actionEvent -> {
                if (!textField.getText().isBlank() && !textField.getText().isEmpty()) {
                    List<String> liste = list.stream().map(String::toLowerCase).collect(Collectors.toList()); // convertie chaque theme en minuscule
                    String themeChoisi = textField.getText().toLowerCase(); // convertie l'input en minuscules
                    if (liste.contains(themeChoisi)) { // verif
                        menu(themeChoisi);
                    } else {
                        // erreur de saisie
                        JOptionPane.showMessageDialog(panel, "Ce th\u00e8me n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        textField.setText("");
                    }
                }
            });
            fi.close();
            oi.close();
        } catch (IOException |
                ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Menu.
     *
     * @param theme the theme
     */
    public void menu(String theme) {
        this.dispose(); // dispose au cas où y a eu un appel back()
        JButton back = new JButton("Pr\u00e9c\u00e9dent");
        // ajout au panel
        panel2.add(modifier, BorderLayout.CENTER);
        panel2.add(afficher, BorderLayout.PAGE_START);
        panel2.add(ajouter, BorderLayout.LINE_START);
        panel2.add(supprimer, BorderLayout.AFTER_LINE_ENDS);
        panel2.add(back, BorderLayout.AFTER_LAST_LINE);
        // config fenetre
        this.setContentPane(panel2);
        this.setSize(500, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Menu th\u00e8mes");
        // action listener pour chaque boutons
        ajouter.addActionListener(actionEvent -> addQuestion(theme));
        modifier.addActionListener(actionEvent -> modifierTheme(theme));
        supprimer.addActionListener(actionEvent -> supprimerQuestion(theme));
        afficher.addActionListener(actionEvent -> afficherQuestion(theme));
        back.addActionListener(actionEvent -> back()); // retour a la fenetre precedente
    }

    /**
     * Back.
     */
    public void back() {
        this.dispose();
        new ThemeVue();
    }

    /**
     * Modifier theme.
     *
     * @param theme the theme
     */
    public void modifierTheme(String theme) {
        this.dispose(); // dispose au cas où y a eu un appel back()
        // initialisation des composants
        JPanel panel6 = new JPanel(new BorderLayout());
        JLabel themeLabel = new JLabel("Nouveau th\u00e8me :");
        JTextField themeField = new JTextField();
        JButton save = new JButton("Valider");
        JButton back = new JButton("Pr\u00e9c\u00e9dent");
        // config la taille et position
        save.setBounds(100, 200, 100, 30);
        back.setBounds(230, 200, 150, 30);
        themeLabel.setBounds(20, 40, 100, 30);
        themeField.setBounds(150, 40, 200, 30);
        // ajout au panel
        panel6.add(themeLabel);
        panel6.add(themeField);
        panel6.add(save);
        panel6.add(back);
        // config fenetre
        this.setContentPane(panel6);
        this.getContentPane().setLayout(null);
        this.setSize(500, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Modifier un th\u00e8me");
        this.getRootPane().setDefaultButton(save);
        // clic sur le boutton "valider"
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // erreur de saisie
                if (themeField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(panel6, "Input invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        // deserialize
                        FileInputStream fi = new FileInputStream(new File("src/projet/themesQ/themes.txt"));
                        ObjectInputStream oi = new ObjectInputStream(fi);
                        ArrayList<String> list = (ArrayList<String>) oi.readObject(); // lecture
                        File fichier = new File("src/projet/themesQ/themes.txt"); // serialize
                        ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(fichier));
                        if (list.contains(themeField.getText())) {
                            JOptionPane.showMessageDialog(panel6, "Ce th\u00e8me existe d\u00e9j\u00e0 !", "Erreur", JOptionPane.ERROR_MESSAGE);
                            themeField.setText("");
                        } else {
                            String themeChoisi = theme.substring(0, 1).toUpperCase() + theme.substring(1).toLowerCase(); // en minuscules
                            String themeModifie = themeField.getText().substring(0, 1).toUpperCase() + themeField.getText().substring(1).toLowerCase();
                            list.set(list.indexOf(themeChoisi), themeModifie); // remplace le theme
                            a.writeObject(list); // ecrit dans le fichier txt en question
                            JOptionPane.showMessageDialog(panel6, "Th\u00e8me modifi\u00e9 !");
                            back();
                        }
                        fi.close();
                        oi.close();
                        a.close();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        back.addActionListener(actionEvent -> menu(theme)); // retour a la fenetre precedente
    }

    /**
     * Create window.
     */
    public void createWindow() {
        this.dispose();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // initialisation des composants
        JPanel panel6 = new JPanel(new BorderLayout());
        JLabel niveauLabel = new JLabel("Niveau :");
        niveauLabel.setBounds(20, 20, 100, 30);
        // ajout au panel
        panel6.add(niveauLabel);
        panel6.add(scrollPane);
        // config fenetre
        scrollPane.setBounds(20, 70, 1800, 300);
        this.setContentPane(panel6);
        this.getContentPane().setLayout(null);
        this.setSize(1900, 450);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Liste des questions");
        model.setColumnIdentifiers(columnNames); // nom des columns
        table.setModel(model);
        table.setFillsViewportHeight(true);
        cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer); // centre les donnees
        table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(cellRenderer);
        // ordre croissant decroissant pour la table
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int columnIndexToSort = 6;
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    /**
     * Afficher question.
     *
     * @param theme the theme
     */
    public void afficherQuestion(String theme) {
        createWindow();
        model.setRowCount(0);
        JTextField niveauField = new JTextField();
        niveauField.setBounds(150, 20, 200, 30);
        JButton save = new JButton("Valider");
        JButton back = new JButton("Pr\u00e9c\u00e9dent");
        // config la taille et position
        save.setBounds(370, 20, 100, 30);
        back.setBounds(500, 20, 150, 30);
        this.add(niveauField);
        this.add(save);
        this.add(back);
        this.getRootPane().setDefaultButton(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (niveauField.getText().isEmpty() || Integer.parseInt(niveauField.getText()) < 1 || Integer.parseInt(niveauField.getText()) > 3) { // erreur de saisie
                    JOptionPane.showMessageDialog(null, "Input invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    niveauField.setText("");
                } else {
                    try {
                        model.setRowCount(0);
                        String themeChoisi = theme.substring(0, 1).toUpperCase() + theme.substring(1).toLowerCase(); // en minuscules et 1ere lettre en maj
                        // deserialize
                        FileInputStream fi = new FileInputStream(new File("src/projet/themesQ/" + themeChoisi + ".txt"));
                        if (fi.available() <= 0) {
                            JOptionPane.showMessageDialog(panel4, "Il n'y a pas de question pour ce th\u00e8me !", "Erreur", JOptionPane.ERROR_MESSAGE);
                        } else {
                            ObjectInputStream oi = new ObjectInputStream(fi);
                            LinkedList<Question<? extends QType>> qList = (LinkedList<Question<? extends QType>>) oi.readObject(); // lecture
                            int lvl = Integer.parseInt(niveauField.getText());
                            for (int i = 0; i < qList.size(); i++) {
                                int niveau = qList.get(i).getNiveau();
                                if (lvl == niveau) {
                                    int num = i;
                                    String question = "";
                                    String bonneRep = "";
                                    String rep2 = "";
                                    String rep3 = "";
                                    Object texte = qList.get(i).getTexte();
                                    String test = String.valueOf(texte.getClass());
                                    String type = test.substring(test.lastIndexOf(".") + 1); // recupere le type de la question
                                    if (type.equals("RC")) {
                                        RC rc = qList.get(i).isRC();
                                        question = rc.getTexte();
                                        bonneRep = rc.getBonneRep();
                                    } else if (type.equals("QCM")) {
                                        QCM qcm = qList.get(i).isQCM();
                                        question = qcm.gettexte();
                                        bonneRep = qcm.getBonneRep();
                                        ArrayList<String> arrayList = qcm.getReponses();
                                        int j = 0;
                                        int count = 0;
                                        ArrayList<String> array2 = new ArrayList<>(arrayList.size() - count); // copie
                                        String remove = bonneRep;
                                        for (String s : arrayList) { // copie l'array sans la bonne rep
                                            if (!s.equals(remove)) {
                                                array2.add(j++, s);
                                            }
                                        }
                                        // init reps
                                        rep2 = array2.get(0);
                                        rep3 = array2.get(1);
                                    } else if (type.equals("VF")) {
                                        VF vf = qList.get(i).isVF();
                                        question = vf.getTexte();
                                        bonneRep = String.valueOf(vf.isBonneRep());
                                    }
                                    model.addRow(new Object[]{num, type, question, bonneRep, rep2, rep3, niveau}); // ajoute la ligne dans la table
                                }
                            }
                            fi.close();
                            oi.close();
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        back.addActionListener(actionEvent -> menu(theme)); // retour a la fenetre precedente
    }

    /**
     * Supprimer question.
     *
     * @param theme the theme
     */
    public void supprimerQuestion(String theme) {
        this.dispose(); // dispose au cas où y a eu un appel back()
        // initialisation des composants
        JPanel panel6 = new JPanel(new BorderLayout());
        JLabel questionLabel = new JLabel("Num\u00e9ro de la question :");
        JTextField questionField = new JTextField();
        JButton save = new JButton("Valider");
        JButton back = new JButton("Pr\u00e9c\u00e9dent");
        // config la taille et position
        save.setBounds(100, 200, 100, 30);
        back.setBounds(230, 200, 150, 30);
        questionLabel.setBounds(10, 40, 170, 30);
        questionField.setBounds(150, 40, 200, 30);
        // ajout au panel
        panel6.add(questionLabel);
        panel6.add(questionField);
        panel6.add(save);
        panel6.add(back);
        // config fenetre
        this.setContentPane(panel6);
        this.getContentPane().setLayout(null);
        this.setSize(500, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Supprimer une question");
        this.getRootPane().setDefaultButton(save);
        // clic sur le boutton "valider"
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // erreur de saisie
                if (questionField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(panel4, "Input invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        String themeChoisi = theme.substring(0, 1).toUpperCase() + theme.substring(1).toLowerCase(); // en minuscules
                        // deserialize
                        FileInputStream fi = new FileInputStream(new File("src/projet/themesQ/" + themeChoisi + ".txt"));
                        if (fi.available() <= 0) {
                            JOptionPane.showMessageDialog(panel4, "Il n'y pas de question pour ce th\u00e8 !", "Erreur", JOptionPane.ERROR_MESSAGE);
                        } else {
                            ObjectInputStream oi = new ObjectInputStream(fi);
                            LinkedList<Question<? extends QType>> qList = (LinkedList<Question<? extends QType>>) oi.readObject(); // lecture
                            File fichier = new File("src/projet/themesQ/" + themeChoisi + ".txt"); // serialize
                            ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(fichier));
                            int num = Integer.parseInt(questionField.getText());
                            if (num < 0 || num > qList.size()) {
                                JOptionPane.showMessageDialog(panel4, "Cette question n'existe pas !", "Erreur", JOptionPane.ERROR_MESSAGE);
                            } else if (qList.isEmpty()) {
                                JOptionPane.showMessageDialog(panel4, "Il n'y a pas de question !", "Erreur", JOptionPane.ERROR_MESSAGE);
                            } else {
                                qList.remove(num); // ajout de la question créée dans la linked list
                                a.writeObject(qList); // ecrit dans le fichier txt en question
                                JOptionPane.showMessageDialog(panel4, "Question supprim\u00e9e !");
                            }
                            fi.close();
                            oi.close();
                            a.close();
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        back.addActionListener(actionEvent -> menu(theme)); // retour a la fenetre precedente
    }

    /**
     * Add question.
     *
     * @param theme the theme
     */
    public void addQuestion(String theme) {
        this.dispose(); // dispose au cas où y a eu un appel back()
        // init des elements
        JButton qcm = new JButton("QCM");
        JButton rc = new JButton("RC");
        JButton vf = new JButton("VF");
        JButton back = new JButton("Pr\u00e9c\u00e9dent");
        // config taille et position
        qcm.setBounds(20, 20, 100, 30);
        rc.setBounds(130, 20, 100, 30);
        vf.setBounds(20, 80, 100, 30);
        back.setBounds(70, 150, 150, 30);
        // ajout au panel
        panel3.add(qcm);
        panel3.add(rc);
        panel3.add(vf);
        panel3.add(back);
        // config fenetre
        this.setContentPane(panel3);
        this.getContentPane().setLayout(null);
        this.setSize(300, 250);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Ajouter une question");
        // action listener pour chaque boutons
        qcm.addActionListener(actionEvent -> addQCM(theme));
        rc.addActionListener(actionEvent -> addRC(theme));
        vf.addActionListener(actionEvent -> addVF(theme));
        back.addActionListener(actionEvent -> menu(theme)); // retour a la fenetre precedente
    }

    /**
     * Add qcm.
     *
     * @param theme the theme
     */
    public void addQCM(String theme) {
        this.dispose(); // dispose au cas où y a eu un appel back()
        // initialisation des composants
        JLabel questionLabel = new JLabel("Question :");
        JLabel bonneRepLabel = new JLabel("Bonne r\u00e9ponse :");
        JLabel rep2Label = new JLabel("R\u00e9ponse :");
        JLabel rep3Label = new JLabel("R\u00e9ponse :");
        JLabel niveauLabel = new JLabel("Niveau :");
        JTextField questionField = new JTextField();
        JTextField bonneRepField = new JTextField();
        JTextField rep2Field = new JTextField();
        JTextField rep3Field = new JTextField();
        JTextField niveauField = new JTextField();
        JButton save = new JButton("Valider");
        JButton back = new JButton("Pr\u00e9c\u00e9dent");
        // config la taille et position
        save.setBounds(100, 200, 100, 30);
        back.setBounds(230, 200, 150, 30);
        questionLabel.setBounds(20, 40, 100, 30);
        bonneRepLabel.setBounds(20, 70, 100, 30);
        rep2Label.setBounds(20, 100, 100, 30);
        rep3Label.setBounds(20, 130, 100, 30);
        niveauLabel.setBounds(20, 160, 100, 30);
        questionField.setBounds(150, 40, 200, 30);
        bonneRepField.setBounds(150, 70, 200, 30);
        rep2Field.setBounds(150, 100, 200, 30);
        rep3Field.setBounds(150, 130, 200, 30);
        niveauField.setBounds(150, 160, 200, 30);
        // ajout au panel
        panel4.add(questionLabel);
        panel4.add(bonneRepLabel);
        panel4.add(rep2Label);
        panel4.add(rep3Label);
        panel4.add(niveauLabel);
        panel4.add(questionField);
        panel4.add(bonneRepField);
        panel4.add(rep2Field);
        panel4.add(rep3Field);
        panel4.add(niveauField);
        panel4.add(save);
        panel4.add(back);
        // config fenetre
        this.setContentPane(panel4);
        this.getContentPane().setLayout(null);
        this.setSize(500, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Ajouter un QCM");
        this.getRootPane().setDefaultButton(save);
        // clic sur le boutton "valider"
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // erreur de saisie
                if (questionField.getText().isEmpty() || bonneRepField.getText().isEmpty() || rep2Field.getText().isEmpty() || rep3Field.getText().isEmpty() || niveauField.getText().isEmpty() || (Integer.parseInt(niveauField.getText()) < 1 || Integer.parseInt(niveauField.getText()) > 3)) {
                    JOptionPane.showMessageDialog(panel4, "Inputs invalides !", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        String themeChoisi = theme.substring(0, 1).toUpperCase() + theme.substring(1).toLowerCase(); // en minuscules
                        // deserialize
                        FileWriter fichierr = new FileWriter("src/projet/themesQ/" + themeChoisi + ".txt"); // serialize
                        fichierr.close();
                        File fichier = new File("src/projet/themesQ/" + themeChoisi + ".txt"); // serialize
                        FileInputStream fi = new FileInputStream(fichier);
                        if (fi.available() <= 0) {  // fichier vient d'etre cree
                            LinkedList<Question<? extends QType>> qList = new LinkedList<>();
                            ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(fichier));
                            // init question
                            ArrayList<String> reps = new ArrayList<>(Arrays.asList(bonneRepField.getText(), rep2Field.getText(), rep3Field.getText()));
                            Question<QCM> question = new Question<>(new QCM(questionField.getText(), reps, bonneRepField.getText()), Integer.parseInt(niveauField.getText()), themeChoisi);
                            qList.add(question); // ajout de la question créée dans la linked list
                            a.writeObject(qList); // ecrit dans le fichier txt en question
                            JOptionPane.showMessageDialog(panel4, "Question ajout\u00e9e !");
                            fi.close();
                            a.close();
                        } else {
                            ObjectInputStream oi = new ObjectInputStream(fi);
                            LinkedList<Question<? extends QType>> qList = (LinkedList<Question<? extends QType>>) oi.readObject(); // lecture
                            ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(fichier));
                            // init question
                            ArrayList<String> reps = new ArrayList<>(Arrays.asList(bonneRepField.getText(), rep2Field.getText(), rep3Field.getText()));
                            Question<QCM> question = new Question<>(new QCM(questionField.getText(), reps, bonneRepField.getText()), Integer.parseInt(niveauField.getText()), themeChoisi);
                            qList.add(question); // ajout de la question créée dans la linked list
                            a.writeObject(qList); // ecrit dans le fichier txt en question
                            JOptionPane.showMessageDialog(panel4, "Question ajout\u00e9e !");
                            fi.close();
                            oi.close();
                            a.close();
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        back.addActionListener(actionEvent -> backadd(theme)); // retour a la fenetre precedente
    }


    /**
     * Backadd.
     *
     * @param theme the theme
     */
    public void backadd(String theme) {
        this.dispose(); // quitte la fenetre inutile
        addQuestion(theme);
    }

    /**
     * Add rc.
     *
     * @param theme the theme
     */
    public void addRC(String theme) {
        this.dispose(); // dispose au cas où y a eu un appel back()
        // initialisation des composants
        JPanel panel5 = new JPanel(new BorderLayout());
        JLabel texteLabel = new JLabel("Texte :");
        JLabel bonneRepLabel = new JLabel("Bonne r\u00e9ponse :");
        JLabel niveauLabel = new JLabel("Niveau :");
        JTextField texteField = new JTextField();
        JTextField bonneRepField = new JTextField();
        JTextField niveauField = new JTextField();
        JButton save = new JButton("Valider");
        JButton back = new JButton("Pr\u00e9c\u00e9dent");
        // config la taille et position
        save.setBounds(100, 200, 100, 30);
        back.setBounds(230, 200, 150, 30);
        texteLabel.setBounds(20, 40, 100, 30);
        bonneRepLabel.setBounds(20, 70, 100, 30);
        niveauLabel.setBounds(20, 100, 100, 30);
        texteField.setBounds(150, 40, 200, 30);
        bonneRepField.setBounds(150, 70, 200, 30);
        niveauField.setBounds(150, 100, 200, 30);
        // ajout au panel
        panel5.add(texteLabel);
        panel5.add(bonneRepLabel);
        panel5.add(niveauLabel);
        panel5.add(texteField);
        panel5.add(bonneRepField);
        panel5.add(niveauField);
        panel5.add(save);
        panel5.add(back);
        // config fenetre
        this.setContentPane(panel5);
        this.getContentPane().setLayout(null);
        this.setSize(500, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Ajouter un RC");
        this.getRootPane().setDefaultButton(save);
        // clic sur le boutton "valider"
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // erreur de saisie
                if (texteField.getText().isEmpty() || bonneRepField.getText().isEmpty() || niveauField.getText().isEmpty() || (Integer.parseInt(niveauField.getText()) < 1 || Integer.parseInt(niveauField.getText()) > 3)) {
                    JOptionPane.showMessageDialog(panel4, "Inputs invalides !", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        String themeChoisi = theme.substring(0, 1).toUpperCase() + theme.substring(1).toLowerCase(); // en minuscules
                        // deserialize
                        FileWriter fichierr = new FileWriter("src/projet/themesQ/" + themeChoisi + ".txt"); // serialize
                        fichierr.close();
                        File fichier = new File("src/projet/themesQ/" + themeChoisi + ".txt"); // serialize
                        FileInputStream fi = new FileInputStream(fichier);
                        if (fi.available() <= 0) { // fichier vient d'etre cree
                            LinkedList<Question<? extends QType>> qList = new LinkedList<>(); // lecture
                            ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(fichier));
                            // init question
                            Question<RC> question = new Question<>(new RC(texteField.getText(), bonneRepField.getText()), Integer.parseInt(niveauField.getText()), themeChoisi);
                            qList.add(question); // ajout de la question créée dans la linked list
                            a.writeObject(qList); // ecrit dans le fichier txt en question
                            JOptionPane.showMessageDialog(panel4, "Question ajout\u00e9e !");
                            fi.close();
                            a.close();
                        } else {
                            ObjectInputStream oi = new ObjectInputStream(fi);
                            LinkedList<Question<? extends QType>> qList = (LinkedList<Question<? extends QType>>) oi.readObject(); // lecture
                            ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(fichier));
                            // init question
                            Question<RC> question = new Question<>(new RC(texteField.getText(), bonneRepField.getText()), Integer.parseInt(niveauField.getText()), themeChoisi);
                            qList.add(question); // ajout de la question créée dans la linked list
                            a.writeObject(qList); // ecrit dans le fichier txt en question
                            JOptionPane.showMessageDialog(panel4, "Question ajout\u00e9e !");
                            fi.close();
                            oi.close();
                            a.close();
                        }

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        back.addActionListener(actionEvent -> backadd(theme)); // retour a la fenetre precedente
    }

    /**
     * Add vf.
     *
     * @param theme the theme
     */
    public void addVF(String theme) {
        this.dispose(); // dispose au cas où y a eu un appel back()
        // initialisation des composants
        JPanel panel5 = new JPanel(new BorderLayout());
        JLabel texteLabel = new JLabel("Texte :");
        JLabel bonneRepLabel = new JLabel("Bonne r\u00e9ponse :");
        JLabel niveauLabel = new JLabel("Niveau :");
        JTextField texteField = new JTextField();
        JComboBox<String> bonneRepBox = new JComboBox<>(new String[]{"true", "false"});
        JTextField niveauField = new JTextField();
        JButton save = new JButton("Valider");
        JButton back = new JButton("Pr\u00e9c\u00e9dent");
        // config la taille et position
        save.setBounds(100, 200, 100, 30);
        back.setBounds(230, 200, 150, 30);
        texteLabel.setBounds(20, 40, 100, 30);
        bonneRepLabel.setBounds(20, 70, 100, 30);
        niveauLabel.setBounds(20, 140, 100, 30);
        texteField.setBounds(150, 40, 200, 30);
        bonneRepBox.setBounds(150, 70, 200, 30);
        niveauField.setBounds(150, 140, 200, 30);
        // ajout au panel
        panel5.add(texteLabel);
        panel5.add(bonneRepLabel);
        panel5.add(niveauLabel);
        panel5.add(texteField);
        panel5.add(bonneRepBox);
        panel5.add(niveauField);
        panel5.add(save);
        panel5.add(back);
        // config fenetre
        this.setContentPane(panel5);
        this.getContentPane().setLayout(null);
        this.setSize(500, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Ajouter un VF");
        this.getRootPane().setDefaultButton(save);
        // clic sur le boutton "valider"
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // erreur de saisie
                if (texteField.getText().isEmpty() || niveauField.getText().isEmpty() || (Integer.parseInt(niveauField.getText()) < 1 || Integer.parseInt(niveauField.getText()) > 3)) {
                    JOptionPane.showMessageDialog(panel4, "Inputs invalides !", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        String themeChoisi = theme.substring(0, 1).toUpperCase() + theme.substring(1).toLowerCase(); // en minuscules
                        // deserialize
                        FileWriter fichierr = new FileWriter("src/projet/themesQ/" + themeChoisi + ".txt"); // serialize
                        fichierr.close();
                        File fichier = new File("src/projet/themesQ/" + themeChoisi + ".txt"); // serialize
                        FileInputStream fi = new FileInputStream(fichier);
                        if (fi.available() <= 0) {  // fichier vient d'etre cree
                            LinkedList<Question<? extends QType>> qList = new LinkedList<>(); // lecture
                            ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(fichier));
                            // init question
                            Question<VF> question = new Question<>(new VF(texteField.getText(), Boolean.parseBoolean(String.valueOf(bonneRepBox.getSelectedItem()))), Integer.parseInt(niveauField.getText()), themeChoisi);
                            qList.add(question); // ajout de la question créée dans la linked list
                            a.writeObject(qList); // ecrit dans le fichier txt en question
                            JOptionPane.showMessageDialog(panel4, "Question ajout\u00e9e !");
                            fi.close();
                            a.close();
                        } else {
                            ObjectInputStream oi = new ObjectInputStream(fi);
                            LinkedList<Question<? extends QType>> qList = (LinkedList<Question<? extends QType>>) oi.readObject(); // lecture
                            ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(fichier));
                            // init question
                            Question<VF> question = new Question<>(new VF(texteField.getText(), Boolean.parseBoolean(String.valueOf(bonneRepBox.getSelectedItem()))), Integer.parseInt(niveauField.getText()), themeChoisi);
                            qList.add(question); // ajout de la question créée dans la linked list
                            a.writeObject(qList); // ecrit dans le fichier txt en question
                            JOptionPane.showMessageDialog(panel4, "Question ajout\u00e9e !");
                            fi.close();
                            oi.close();
                            a.close();
                        }

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        back.addActionListener(actionEvent -> backadd(theme)); // retour a la fenetre precedente
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        new ThemeVue();
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
        panel = new JPanel();
        panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel.setEnabled(false);
        jList = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        jList.setModel(defaultListModel1);
        panel.add(jList, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 2, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        label = new JLabel();
        label.setEnabled(true);
        label.setText("Choisissez un thème :");
        panel.add(label, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField = new JTextField();
        panel.add(textField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        validerButton = new JButton();
        validerButton.setText("Valider");
        panel.add(validerButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label.setLabelFor(textField);
    }

    /**
     * $$$ get root component $$$ j component.
     *
     * @return the j component
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
