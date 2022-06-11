/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Achat.pre_commande;

import Devis.*;
import Article.ArticleDao;
import Article.ArticleForm;
import BL.BLDao;
import BL.FormBL;
import Client.Client;
import Client.ClientDao;
import Client.ClientForm;
import Config.Commen_Proc;
import Config.ConfigDao;
import Home.App;
import Home.TestTableSortFilter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Mlek
 */
public class pre_commandeForm extends javax.swing.JInternalFrame {

    int i = 0;
    ArrayList<String> listNomSte;
    ArrayList<String> listNomArticle;
    Vector<String> columnNames_Devis_Table;
    Vector<Vector<Object>> data_Devis_Table;
    JButton jbtValider;

    String nomclient;
    JButton jbtValiderarticle;
    String refarticle;
    JFrame frameListeClient;
    DefaultTableModel df;
    String id_client;
    pre_commandeDao pre_commandeDao = new pre_commandeDao();
    Double D_Total_HT = 0.0;
    Double D_Total_remise = 0.0;
    Double D_montant_TVA = 0.0;
    Double D_Total_Net = 0.0;
    private String year;

    /**
     * Creates new form form
     */
    String type_operation = "";
    String id_four = "";

    public pre_commandeForm(String Num_Devis, String type) {
        year = Commen_Proc.YearVal;
         initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTableHeader();
        data_Devis_Table = new Vector<Vector<Object>>();
        if (!Commen_Proc.isRemote) {
            autoCompleteFields();
        }
        //CheckBox_timbre.setSelected(true);
        //curent date
        Date_Devis.setDate(new Date());
        Date_Devis.setEnabled(false);
        txt_design_article.setEnabled(false);
        // txt_timbre.setText(Commen_Proc.TimbreVal);
        // txt_timbre.setEnabled(false);
        setNumDevis();
        fournisseurComboBox();
        if (!Num_Devis.isEmpty()) {
            type_operation = type;
            if (type.equals("Modif")) {
                setImportedPreCommande(Num_Devis);
                setImportedTableHeader();

                ArticleDao dart = new ArticleDao();
                String nom_and_codeTva = dart.getNameItemByIdJoinTablesPreCommande("pre_command_achat", "num_bon_commande", Num_Devis);
                String[] array = nom_and_codeTva.split(";");
                id_four = array[0];
                id_client = array[2];
                txt_search.setText(array[3]);

                //txt_search.setText(array[1]);
                //txt_Code_TVA.setText(array[1]);
                ComboBox_four.setSelectedItem(array[1]);
                txt_num_bon_commande.setText(Num_Devis);
                txt_num_bon_commande.setEnabled(false);
                Date_Devis.setEnabled(true);
                String date = dart.getDateByNumPreCommandeAchat(Num_Devis);
                try {
                    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    Date_Devis.setDate(date1);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }

            } else {

                setImportedDevis(Num_Devis);
                setImportedTableHeader();
                // formatTableValues();
                // Num_Devis = Num_Devis;
                ArticleDao dart = new ArticleDao();
                Num_Devis = (Num_Devis.split(",")[0]).replaceAll("'", "");
                String nom_and_codeTva = dart.getNameItemByIdJoinTables("devis", "num_devis", Num_Devis);
                String[] array = nom_and_codeTva.split(";");
                id_client = array[0];
                txt_search.setText(array[1]);
            }
        }
        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    }
    HashMap<String, String> fournisseurHashMap;

    public void fournisseurComboBox() {
        ArticleDao article = new ArticleDao();
        fournisseurHashMap = article.listFournisseur(ComboBox_four);
    }

    public void setTableHeader() {
        columnNames_Devis_Table = new Vector<String>();
        columnNames_Devis_Table.add("id");
        columnNames_Devis_Table.add("Référence");
        columnNames_Devis_Table.add("Designation");
        columnNames_Devis_Table.add("Quantité");
        df = new DefaultTableModel(data_Devis_Table, columnNames_Devis_Table);
        Devis_Table.setModel(df);

    }

    public void formatTableValues() {

        for (int j = 0; j < Devis_Table.getRowCount(); j++) {
            Object PU = df.getValueAt(j, 3);
            df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 3);

            Object qte = df.getValueAt(j, 4);
            Double d = Double.valueOf(qte.toString());
            df.setValueAt(String.valueOf(d.intValue()), j, 4);

            Object HT = df.getValueAt(j, 5);
            df.setValueAt(formatDouble(Double.parseDouble(HT.toString())), j, 5);

            Object TTC = df.getValueAt(j, 8);
            df.setValueAt(formatDouble(Double.parseDouble(TTC.toString())), j, 8);

            Object s = df.getValueAt(j, 0);
            // JOptionPane.showMessageDialog(null, " index : " + j + " value : " + s);
            df.setValueAt(j + 1, j, 0);
        }

    }

    public void setImportedDevis(String Num_Devis) {
        DevisDao devisDao = new DevisDao();
        df = new DefaultTableModel();
        df = devisDao.afficherDetailDevisForPre_commande(Devis_Table, Num_Devis);
        Devis_Table.setModel(df);
        //  reCalculerTT();

    }

    public void setImportedPreCommande(String Num_Devis) {
        BLDao blDao = new BLDao();
        df = new DefaultTableModel();
        // String s = blDao.afficherPreCommandeAchatPourModif(Devis_Table, Num_Devis);
        df = blDao.afficherPreCommandeAchatPourModif(Devis_Table, Num_Devis);
        Devis_Table.setModel(df);
        //  reCalculerTT();

    }

    public void setImportedTableHeader() {

        columnNames_Devis_Table = new Vector<String>();
        columnNames_Devis_Table.add("Num");
        columnNames_Devis_Table.add("Référence");
        columnNames_Devis_Table.add("Designation");
        columnNames_Devis_Table.add("Quantité");
        int i = 0;
        for (String string : columnNames_Devis_Table) {

            JTableHeader th = Devis_Table.getTableHeader();
            TableColumnModel tcm = th.getColumnModel();
            TableColumn tc = tcm.getColumn(i);
            tc.setHeaderValue(string);
            th.repaint();
            i++;
        }

    }

    void clearFields() {
        txt_searchArticle.setText("");
        txt_design_article.setText("");
        txt_qte.setText("");
    }

    public void addLigneDevis_Table() {

        Vector<ligneDevis> vector = new Vector<ligneDevis>();
        i++;

        Double qte;
        if (txt_qte.getText().isEmpty()) {
            qte = 1.0;
        } else {
            qte = Double.valueOf(txt_qte.getText());
        }

        Object LigneData[] = new Object[9];

        LigneData[0] = i;
        LigneData[1] = txt_searchArticle.getText();
        LigneData[2] = txt_design_article.getText();
        LigneData[3] = txt_qte.getText();
        if (Devis_Table.getSelectedRow() == -1) {
            df.addRow(LigneData);
        } else {
            df.insertRow(Devis_Table.getSelectedRow() + 1, LigneData);
        }
        Devis_Table.clearSelection();

//        if (CheckBox_timbre.isSelected()) {
        //      timbre = Double.valueOf(txt_timbre.getText());
        //   }
        clearFields();
    }

    public void autoCompleteFields() {
        //List suggestion client
        listNomSte = new ArrayList<>();
        ClientDao clientDao = new ClientDao();

        listNomSte = clientDao.afficherClient();

        //List suggestion client
        listNomArticle = new ArrayList<>();
        ArticleDao articleDao = new ArticleDao();
        listNomArticle = articleDao.afficherArticle();
    }

    /**
     * This method is called from within the constructor to initialize the
     * FormDevis. WARNING: Do NOT modify this code. The content of this method
     * is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_num_bon_commande = new javax.swing.JTextField();
        txt_search = new javax.swing.JTextField();
        Date_Devis = new com.toedter.calendar.JDateChooser();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        ComboBox_four = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        CheckBoxClientPassager = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Ajouter = new javax.swing.JButton();
        txt_design_article = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Supprimer = new javax.swing.JButton();
        txt_searchArticle = new javax.swing.JTextField();
        txt_qte = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Devis_Table = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Pré Commande");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Infos Pre-Commande"));

        jLabel1.setText("Nom Client");

        jLabel2.setText("Date Commande");

        jLabel5.setText("N° Bon Commande");

        txt_search.setBackground(new java.awt.Color(153, 204, 255));
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_searchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });

        jButton7.setText("Liste");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton9.setText("+");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        ComboBox_four.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboBox_four.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBox_fourActionPerformed(evt);
            }
        });

        jLabel15.setText("Fournisseur *");

        CheckBoxClientPassager.setText("Client Passager");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_num_bon_commande)
                            .addComponent(Date_Devis, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(615, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(ComboBox_four, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(51, 51, 51)
                                .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton7)
                                .addGap(33, 33, 33)
                                .addComponent(jButton9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CheckBoxClientPassager)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBox_four))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton7)
                    .addComponent(jButton9)
                    .addComponent(CheckBoxClientPassager))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(Date_Devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_num_bon_commande, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Article"));

        jLabel7.setText("Quantité");

        Ajouter.setText("+");
        Ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjouterActionPerformed(evt);
            }
        });

        jLabel3.setText("Référence Article");

        jLabel11.setText("Désignation Article");

        Supprimer.setText("-");
        Supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupprimerActionPerformed(evt);
            }
        });

        txt_searchArticle.setBackground(new java.awt.Color(153, 204, 255));
        txt_searchArticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchArticleActionPerformed(evt);
            }
        });
        txt_searchArticle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_searchArticleKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchArticleKeyReleased(evt);
            }
        });

        Devis_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Devis_Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Devis_TableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Devis_Table);

        jButton3.setText("Modifier Ligne");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton8.setText("Liste");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton10.setText("+");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel11))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txt_searchArticle)
                                    .addComponent(txt_design_article, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton10))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel7)
                                        .addGap(44, 44, 44)
                                        .addComponent(txt_qte, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(8, 8, 8))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(Ajouter)
                                .addGap(18, 18, 18)
                                .addComponent(Supprimer)
                                .addGap(163, 163, 163)))
                        .addComponent(jButton3)
                        .addGap(0, 207, Short.MAX_VALUE)))
                .addGap(349, 349, 349))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_searchArticle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButton8)
                    .addComponent(jButton10))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txt_design_article, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7)
                    .addComponent(txt_qte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Ajouter)
                        .addComponent(Supprimer))
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Validation Devis"));

        jButton2.setText("Valider");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Devis");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(92, 92, 92))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(247, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed

    }//GEN-LAST:event_txt_searchActionPerformed

    private void txt_searchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyPressed
        // TODO add your handling code here:

        if (evt.getKeyCode() == evt.VK_ENTER) {
            //setFieldsByName(txt_search.getText());
            //cDao.afficherCommercial(Table_Commercial, String.valueOf(txt_num_id.getText()));
            ArticleDao d = new ArticleDao();
            String s = d.getNameItemById("client", "numero_client", "nom", txt_search.getText());
            id_client = s;
        }
    }//GEN-LAST:event_txt_searchKeyPressed

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
        //cDao.searchClient(client_table, txt_search.getText());

        if (evt.getKeyCode() == evt.VK_BACK_SPACE || evt.getKeyCode() == evt.VK_DELETE) {

        } else {
            String to_check = txt_search.getText().toLowerCase();
            int to_check_len = to_check.length();
            for (String data : listNomSte) {
                data = data.toLowerCase();
                String check_from_data = "";
                for (int i = 0; i < to_check_len; i++) {
                    if (to_check_len <= data.length()) {
                        check_from_data = check_from_data + data.charAt(i);
                    }
                }
                //System.out.print(check_from_data);
                if (check_from_data.contains(to_check)) {
                    //System.out.print("Found");
                    txt_search.setText(data);
                    txt_search.setSelectionStart(to_check_len);
                    txt_search.setSelectionEnd(data.length());
                    break;
                }
            }
        }
    }//GEN-LAST:event_txt_searchKeyReleased
    private pre_commande setDevisFromEditText() {
        pre_commande r = new pre_commande();

        try {

            if (id_client != null) {
                r.setId_client(Integer.valueOf(id_client));
            } else {
                if (CheckBoxClientPassager.isSelected()) {
                    r.setId_client(99999999);

                } else {
                    r.setId_client(0);
                    return null;

                }
            }
            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(Date_Devis.getDate());
            r.setDate_pre_commande(currentTime);

            if (!txt_num_bon_commande.getText().isEmpty()) {
                r.setNum_bon_commande(txt_num_bon_commande.getText());
            } else {
                r.setNum_bon_commande("");
                return null;
            }
            String f = ComboBox_four.getSelectedItem().toString();
            if (!f.equals("-")) {
                String ss = daoArticle.getNameItemById("fournisseur", "id", "nom", f);
                r.setId_fournisseur(Integer.valueOf(ss));
            } else {
                r.setId_fournisseur(0);
                return null;

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println(e);

        }

        return r;
    }
    ArticleDao daoArticle = new ArticleDao();

    private String VerifStock(ArrayList<ligneDevis> lstd) {
        String res = "";
        for (ligneDevis d : lstd) {
            ArticleDao daoArticle = new ArticleDao();

            String stockNegative = daoArticle.getNameItemById("article", "stock_negative", "ref", d.getRef_article());

            int stockReel = 0;
            int qteDevis = 0;

            stockReel = Integer.parseInt(daoArticle.getNameItemById("article", "qte", "ref", d.getRef_article()));
            qteDevis = Integer.parseInt(d.getQte());
            if (stockNegative.equals("0")) {
                if ((stockReel < qteDevis)) {
                    res += "L'article " + d.getRef_article() + " à dépassé le stock \n";
                }
            }

        }

        return res;
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {

            pre_commande d = setDevisFromEditText();
            ArrayList<lignePre_commande> lstd;
            lstd = setLignePre_commandeFromDevisTable();
            //JOptionPane.showMessageDialog(null, s.toString());
            ConfigDao c = new ConfigDao();
            if (d != null) {
                if (c.DateCheckOnUpdateNoStatut("pre_command_achat", "date_pre_commande", "num_bon_commande", d.getNum_bon_commande(), d.getDate_pre_commande())) {
                    if (type_operation.equals("Modif")) {
                        if (pre_commandeDao.modifierPre_commande(d)) {
                            pre_commandeDao.modifierLignePre_commande(lstd);
                            JOptionPane.showMessageDialog(null, "Pré-Commande num " + txt_num_bon_commande.getText() + " à été bien Modifié !");
                            //pre_commandeDao.modifierStock(lstd);
                            // setNumDevis();
                            clearpreCmdFields();
                            df.setRowCount(0);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error in modif precommande ActionPerformed ");
                        }
                    } else {
                        if (pre_commandeDao.ajouterPre_commande(d)) {
                            pre_commandeDao.ajouterLignePre_commande(lstd);
                            JOptionPane.showMessageDialog(null, "Pré-Commande num " + txt_num_bon_commande.getText() + " à été bien enregistré !");
                            //pre_commandeDao.modifierStock(lstd);
                            setNumDevis();
                            clearpreCmdFields();
                            df.setRowCount(0);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error in create precommande ActionPerformed ");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Condition Date n'a pas été acceptée ");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Verifier les Champs ");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in DevisFrom/Ajouter_btn :  " + e);
        } finally {
            try {

            } catch (Exception ex) {
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed


    private void txt_searchArticleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchArticleKeyReleased
        //  daoArticle.searchClient(client_table, txt_search.getText());

        if (evt.getKeyCode() == evt.VK_BACK_SPACE || evt.getKeyCode() == evt.VK_DELETE) {

        } else {
            String to_check = txt_searchArticle.getText().toLowerCase();
            int to_check_len = to_check.length();
            for (String data : listNomArticle) {
                data = data.toLowerCase();
                String check_from_data = "";
                for (int i = 0; i < to_check_len; i++) {
                    if (to_check_len <= data.length()) {
                        check_from_data = check_from_data + data.charAt(i);
                    }
                }
                //System.out.print(check_from_data);
                if (check_from_data.contains(to_check)) {
                    //System.out.print("Found");
                    txt_searchArticle.setText(data);
                    txt_searchArticle.setSelectionStart(to_check_len);
                    txt_searchArticle.setSelectionEnd(data.length());
                    break;
                }
            }
        }
    }//GEN-LAST:event_txt_searchArticleKeyReleased

    private void txt_searchArticleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchArticleKeyPressed
        // TODO add your handling code here:

        if (evt.getKeyCode() == evt.VK_ENTER) {
            // setFieldsByName(txt_searchArticle.getText());
            ArticleDao d = new ArticleDao();
            String design = d.getNameItemById("article", "designation", "ref", txt_searchArticle.getText());
            String tva = d.getNameItemById("article", "tva", "ref", txt_searchArticle.getText());
            String prix_u = d.getNameItemById("article", "prix_u", "ref", txt_searchArticle.getText());
            txt_design_article.setText(design);

        }
    }//GEN-LAST:event_txt_searchArticleKeyPressed

    private void txt_searchArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchArticleActionPerformed

    }//GEN-LAST:event_txt_searchArticleActionPerformed

    private void SupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupprimerActionPerformed
        // TODO add your handling code here:

        int row = Devis_Table.getSelectedRow();
        if (row != -1) {
            int coll = 0;

            df.removeRow(Integer.valueOf(row));

            //df.setValueAt("5", row, coll);
            for (int j = 0; j < df.getRowCount(); j++) {
                Object s = df.getValueAt(j, coll);
                // JOptionPane.showMessageDialog(null, " index : " + j + " value : " + s);
                df.setValueAt(j + 1, j, coll);
            }
            // reCalculerTT();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(null, "No row selected");
        }

        //Devis_Table.clearSelection();

    }//GEN-LAST:event_SupprimerActionPerformed

    private void AjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjouterActionPerformed
        // TODO add your handling code here:
        addLigneDevis_Table();
        int row = Devis_Table.getSelectedRow();
        int coll = 0;
        // reCalculerTT();
        //df.setValueAt("5", row, coll);
        for (int j = 0; j < df.getRowCount(); j++) {
            Object s = df.getValueAt(j, coll);
            // JOptionPane.showMessageDialog(null, " index : " + j + " value : " + s);
            df.setValueAt(j + 1, j, coll);
        }
        clearArticleFields();

    }//GEN-LAST:event_AjouterActionPerformed

    void clearArticleFields() {
        txt_searchArticle.setText("");
        txt_design_article.setText("");
        txt_qte.setText("");
    }

    void clearpreCmdFields() {
        txt_search.setText("");
        ComboBox_four.setSelectedItem("-");
        Date_Devis.setDate(new Date());
        Date_Devis.setEnabled(false);
    }

    public String formatString(String s) {
        DecimalFormat df = new DecimalFormat("0.000");
        df.setRoundingMode(RoundingMode.CEILING);
        Double d1 = 0.0;
        try {
            d1 = Double.parseDouble(s);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error in formatString methode :  " + e.getMessage());
        }
        return d1.toString().replace(",", ".");
        //return s.substring(0, s.indexOf(".") + 4);
    }

    public String formatDouble(Double d) {
        DecimalFormat df = new DecimalFormat("0.000");
        df.setRoundingMode(RoundingMode.CEILING);// import java.text.DecimalFormat;
        String s = "0.0";
        try {
            s = df.format(d);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error in formatDouble methode :  " + e.getMessage());

        }

        return Config.Commen_Proc.formatDouble(d);
        /*  String s = d.toString().replace(".", ";");

        try {

            String[] x = s.split(";");
            if (x[1].length() == 1) {
                s = x[0] + "." + x[1] + "00";
            } else if (x[1].length() == 2) {
                s = x[0] + "." + x[1] + "0";
            } else {
                s = s.substring(0, s.indexOf(";") + 4);
                s = s.replace(";", ".");
            }

        } catch (Exception e) {
        }

        return s;*/
    }


    private void Devis_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Devis_TableMouseClicked
        // TODO add your handling code here:
        int row = Devis_Table.getSelectedRow();
        String ref = Devis_Table.getModel().getValueAt(row, 1).toString();
        String design = Devis_Table.getModel().getValueAt(row, 2).toString();
        String qte = Devis_Table.getModel().getValueAt(row, 3).toString();
        txt_searchArticle.setText(ref);
        txt_design_article.setText(design);
        txt_qte.setText(qte);

        // 6 remise 7 tvA

    }//GEN-LAST:event_Devis_TableMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int row = Devis_Table.getSelectedRow();
        String ref = txt_searchArticle.getText();
        String design = txt_design_article.getText();
        String qte = txt_qte.getText();

        Devis_Table.getModel().setValueAt(ref, row, 1);
        Devis_Table.getModel().setValueAt(design, row, 2);
        Devis_Table.getModel().setValueAt(qte, row, 3);

        //reCalculerTT();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        HistoriqueDevis c = new HistoriqueDevis();
        Home.App.d.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height - 100);
        c.setVisible(true);

        JDesktopPane ds = getDesktopPane();
        c.moveToFront();
        try {
            c.setSelected(true);

        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        ds.repaint();
    }//GEN-LAST:event_jButton4ActionPerformed


    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        /*  JFrame frame = new JFrame("Row Filter");
        frame.add(TestTableSortFilter());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);*/
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("id");
        columnNames.add("Nom");
        columnNames.add("Adresse");

        ClientDao clientDao = new ClientDao();
        Vector<Vector<Object>> data1 = clientDao.afficherListClient();
        DefaultTableModel model = new DefaultTableModel(data1, columnNames);
        JTable jTable = new JTable(model);

        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(jTable.getModel());

        JTextField jtfFilter = new JTextField();

        jbtValider = new JButton("Valider");

        //jTable.setRowSorter(rowSorter);
        JPanel Homepanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());

        panel.add(jbtValider, BorderLayout.SOUTH);

        Homepanel.add(panel, BorderLayout.SOUTH);

        Homepanel.add(
                new JScrollPane(jTable), BorderLayout.CENTER);

        jtfFilter.getDocument()
                .addDocumentListener(new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e
                    ) {
                        String text = jtfFilter.getText();

                        if (text.trim().length() == 0) {
                            rowSorter.setRowFilter(null);
                        } else {
                            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e
                    ) {
                        String text = jtfFilter.getText();

                        if (text.trim().length() == 0) {
                            rowSorter.setRowFilter(null);
                        } else {
                            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                        }
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e
                    ) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                }
                );

        frameListeClient = new JFrame("Liste Clients");
        frameListeClient.add(Homepanel);
        frameListeClient.pack();
        frameListeClient.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frameListeClient.setLocationRelativeTo(null);
        frameListeClient.setVisible(true);
        jbtValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomclient = jTable.getModel().getValueAt(jTable.getSelectedRow(), 1).toString();
                txt_search.setText(nomclient);
                ArticleDao d = new ArticleDao();
                String code_tva = d.getNameItemById("client", "id_Fiscale", "nom", txt_search.getText());
                String s = jTable.getModel().getValueAt(jTable.getSelectedRow(), 0).toString();
                id_client = s;

                frameListeClient.dispose();
            }
        });

    }//GEN-LAST:event_jButton7ActionPerformed
    JButton jbRecherchearticle;
    JTextField jtxtRecherchearticle;
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (ComboBox_four.getSelectedItem() != "-") {

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("id");
            columnNames.add("ref");
            columnNames.add("designation");
            columnNames.add("Prix Unitaire");
            String ss = daoArticle.getNameItemById("fournisseur", "id", "nom", ComboBox_four.getSelectedItem().toString());
            ArticleDao articleDao = new ArticleDao();
            Vector<Vector<Object>> data1 = articleDao.afficherListeArticle(ss);

            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            JTable jTable = new JTable(model);

            TableRowSorter<TableModel> rowSorter
                    = new TableRowSorter<>(jTable.getModel());

            JTextField jtfFilter = new JTextField();

            jbtValiderarticle = new JButton("Valider");
            jbRecherchearticle = new JButton("Recherche");
            jtxtRecherchearticle = new JTextField(30);
//jTable.setRowSorter(rowSorter);
            JPanel Homepanel = new JPanel(new BorderLayout());

            JPanel panel = new JPanel(new BorderLayout());
            JPanel panelbtn = new JPanel(new FlowLayout());

            panelbtn.add(jbtValiderarticle, BorderLayout.SOUTH);
            panelbtn.add(jbRecherchearticle, BorderLayout.NORTH);
            panelbtn.add(jtxtRecherchearticle, BorderLayout.NORTH);

            panel.add(panelbtn, BorderLayout.SOUTH);
            jbRecherchearticle.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {

                    ArticleDao articleDao = new ArticleDao();
                    Vector<Vector<Object>> data1 = articleDao.afficherListeArticle(jtxtRecherchearticle.getText(), "");

                    DefaultTableModel model = new DefaultTableModel(data1, columnNames);
                    jTable.setModel(model);
                }
            });

            Homepanel.add(panel, BorderLayout.SOUTH);

            Homepanel.add(
                    new JScrollPane(jTable), BorderLayout.CENTER);

            jtfFilter.getDocument()
                    .addDocumentListener(new DocumentListener() {

                        @Override
                        public void insertUpdate(DocumentEvent e
                        ) {
                            String text = jtfFilter.getText();

                            if (text.trim().length() == 0) {
                                rowSorter.setRowFilter(null);
                            } else {
                                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                            }
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e
                        ) {
                            String text = jtfFilter.getText();

                            if (text.trim().length() == 0) {
                                rowSorter.setRowFilter(null);
                            } else {
                                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                            }
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e
                        ) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                    }
                    );

            frameListeClient = new JFrame("Liste Article");
            frameListeClient.add(Homepanel);
            frameListeClient.pack();
            frameListeClient.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frameListeClient.setLocationRelativeTo(null);
            frameListeClient.setVisible(true);
            jbtValiderarticle.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    nomclient = jTable.getModel().getValueAt(jTable.getSelectedRow(), 1).toString();
                    txt_searchArticle.setText(nomclient);

                    String s = jTable.getModel().getValueAt(jTable.getSelectedRow(), 2).toString();
                    ArticleDao d = new ArticleDao();
                    String design = s;
                    String tva = d.getNameItemById("article", "tva", "ref", txt_searchArticle.getText());
                    String pu = jTable.getModel().getValueAt(jTable.getSelectedRow(), 3).toString();

                    txt_design_article.setText(design);
                    frameListeClient.dispose();
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "Merci de sélectionner Fournisseur ! ");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

        ClientForm c = new ClientForm();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        JDesktopPane ds = getDesktopPane();
        ds = Home.App.d;
        ds.add(c);
        c.moveToFront();
        try {
            c.setSelected(true);

        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        ds.repaint();

// TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        ArticleForm c = new ArticleForm();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        JDesktopPane ds = getDesktopPane();
        ds = Home.App.d;
        ds.add(c);
        c.moveToFront();
        try {
            c.setSelected(true);

        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        ds.repaint();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void ComboBox_fourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox_fourActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBox_fourActionPerformed


    /* private String[] columnNames
            = {"Country", "Capital", "Population in Millions", "Democracy"};

    private Object[][] data = {
        {"USA", "Washington DC", 280, true},
        {"Canada", "Ottawa", 32, true},
        {"United Kingdom", "London", 60, true},
        {"Germany", "Berlin", 83, true},
        {"France", "Paris", 60, true},
        {"Norway", "Oslo", 4.5, true},
        {"India", "New Delhi", 1046, true}
    };

    private DefaultTableModel model = new DefaultTableModel(data, columnNames);
    private JTable jTable = new JTable(model);

    private TableRowSorter<TableModel> rowSorter
            = new TableRowSorter<>(jTable.getModel());

    private JTextField jtfFilter = new JTextField();
    private JButton jbtFilter = new JButton("Filter");

    public JPanel TestTableSortFilter() {
        jTable.setRowSorter(rowSorter);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Specify a word to match:"),
                BorderLayout.WEST);
        panel.add(jtfFilter, BorderLayout.CENTER);

        
        panel.add(panel, BorderLayout.SOUTH);
        panel.add(new JScrollPane(jTable), BorderLayout.CENTER);

        jtfFilter.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
        return panel;
    }
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Ajouter;
    private javax.swing.JCheckBox CheckBoxClientPassager;
    private javax.swing.JComboBox<String> ComboBox_four;
    private com.toedter.calendar.JDateChooser Date_Devis;
    private javax.swing.JTable Devis_Table;
    private javax.swing.JButton Supprimer;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_design_article;
    private javax.swing.JTextField txt_num_bon_commande;
    private javax.swing.JTextField txt_qte;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_searchArticle;
    // End of variables declaration//GEN-END:variables

    private void setNumDevis() {

        String s = pre_commandeDao.maxNumDevis();
        if (!s.isEmpty()) {
            /*  String t[] = s.split("D");
            
            formatted = String.format("%05d", x);*/
            String num_devis;
            int x = Integer.valueOf(s) + 1;

            if (String.valueOf(x).length() == 1) {
                num_devis = "0000" + x;
            } else if (String.valueOf(x).length() == 2) {
                num_devis = "000" + x;
            } else if (String.valueOf(x).length() == 3) {
                num_devis = "00" + x;
            } else if (String.valueOf(x).length() >= 5) {
                num_devis = "" + x;
            } else {
                num_devis = "0" + x;
            }
            txt_num_bon_commande.setText(num_devis);
            txt_num_bon_commande.setEnabled(false);
        } else {
            /* formatted = String.format("%05d", 1);
            String num_devis = year + "D" + formatted;*/
            String num_devis = "00001";
            txt_num_bon_commande.setText(num_devis);
            txt_num_bon_commande.setEnabled(false);

        }
    }

    private ArrayList<lignePre_commande> setLignePre_commandeFromDevisTable() {
        ArrayList<lignePre_commande> lst = new ArrayList();

        try {

            for (int j = 0; j < Devis_Table.getRowCount(); j++) {
                lignePre_commande ld = new lignePre_commande();
                ld.setId_pre_commande(txt_num_bon_commande.getText());
                ld.setRef_article(Devis_Table.getValueAt(j, 1).toString());
                ld.setDesign(Devis_Table.getValueAt(j, 2).toString());
                ld.setQte(Devis_Table.getValueAt(j, 3).toString());

                lst.add(ld);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error in  setLigneDevisFromDevisTable : " + e);
        }
        return lst;
    }

}
