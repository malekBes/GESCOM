/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Achat.commande;

import Achat.pre_commande.*;
import Devis.*;
import Article.ArticleDao;
import Article.ArticleForm;
import BL.FormBL;
import Client.Client;
import Client.ClientDao;
import Client.ClientForm;
import Config.Commen_Proc;
import Config.ConfigDao;
import Facture.FactureDAO;
import Home.App;
import Home.TestTableSortFilter;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
public class CommandeForm extends javax.swing.JInternalFrame {

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
    CommandeDAO commandeDao = new CommandeDAO();
    Double D_Total_HT = 0.0;
    Double D_Total_remise = 0.0;
    Double D_montant_TVA = 0.0;
    Double D_Total_Net = 0.0;
    private String year;
    boolean b = false;

    /**
     * Creates new form form
     */
    public CommandeForm(String Num_Devis) {
        year = Commen_Proc.YearVal;
        initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTableHeader();
        data_Devis_Table = new Vector<Vector<Object>>();
        lstBLexport = new Vector<Vector<Object>>();
        columnNames = new Vector<String>();
        columnNames.add("Num Pré-Commande");
        columnNames.add("Date Pré-Commande");
        columnNames.add("Client");

        if (!Commen_Proc.isRemote) {
            autoCompleteFields();
        }

        //CheckBox_timbre.setSelected(true);
        //curent date
        Date d = new Date();
        Date_Devis.setDate(d);
        // txt_timbre.setText(Commen_Proc.TimbreVal);
        // txt_timbre.setEnabled(false);
        setNumDevis();
        Date_Devis.setDate(new Date());
        Date_Devis.setEnabled(false);
        fournisseurComboBox();
        if (!Num_Devis.isEmpty()) {
            setImportedDevis(Num_Devis);
            setImportedTableHeader();
            // formatTableValues();
            // Num_Devis = Num_Devis;
            ArticleDao dart = new ArticleDao();
            String nom_and_codeTva = dart.getNameItemByIdJoinTables("devis", "num_devis", Num_Devis);
            String[] array = nom_and_codeTva.split(";");
            id_client = array[0];

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
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_num_bon_commande = new javax.swing.JTextField();
        Date_Devis = new com.toedter.calendar.JDateChooser();
        ComboBox_four = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Devis_Table = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Devis_TableExport = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Commande");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Infos Commande"));

        jLabel2.setText("Date Commande");

        jLabel5.setText("N° Commande");

        ComboBox_four.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboBox_four.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBox_fourActionPerformed(evt);
            }
        });

        jLabel15.setText("Fournisseur *");

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
                            .addComponent(Date_Devis, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(ComboBox_four, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(537, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBox_four))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(Date_Devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_num_bon_commande, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

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

        jButton3.setText(">");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("<");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setText(">>");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("<<");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        Devis_TableExport.setModel(new javax.swing.table.DefaultTableModel(
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
        Devis_TableExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Devis_TableExportMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Devis_TableExport);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(251, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Validation Devis"));

        jButton2.setText("Valider");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Détail Commande");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private Commande setDevisFromEditText() {
        Commande r = new Commande();

        try {

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(Date_Devis.getDate());
            r.setDate_commande(currentTime);

            if (!txt_num_bon_commande.getText().isEmpty()) {
                r.setNum_Commande(txt_num_bon_commande.getText());
            } else {
                r.setNum_Commande("");
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

    public void clearFields() {
        Date_Devis.setDate(new Date());
        Date_Devis.setEnabled(false);
        ComboBox_four.setSelectedItem("-");
        clearExportTable();
        clearTable();
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            Commande d = setDevisFromEditText();
            ArrayList<ligne_commande> lstd;
            lstd = setLigne_commandeFromDevisTable();
            //JOptionPane.showMessageDialog(null, s.toString());
            ConfigDao c = new ConfigDao();

            if (c.DateCheckOnUpdateNoStatut("commande_achat", "date_commande", "num_Commande", d.getNum_Commande(), d.getDate_commande())) {

                if (commandeDao.ajouterCommande(d)) {
                    commandeDao.ajouterLigne_commande(lstd);
                    //commandeDao.setPreCommande_NumCommande(lstd);
                    JOptionPane.showMessageDialog(null, "Commande num " + txt_num_bon_commande.getText() + " à été bien enregistré !");
                    //pre_commandeDao.modifierStock(lstd);

                    clearFields();
                    setNumDevis();
                } else {
                    JOptionPane.showMessageDialog(null, "Error in Commande ActionPerformed ");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Condition Date n'a pas été acceptée ");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in Commande/Ajouter_btn :  " + e);
        } finally {
            try {

            } catch (Exception ex) {
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed
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
        /*  int row = Devis_Table.getSelectedRow();
        String ref = Devis_Table.getModel().getValueAt(row, 0).toString();
        String design = Devis_Table.getModel().getValueAt(row, 1).toString();
        String qte = Devis_Table.getModel().getValueAt(row, 2).toString();
         */
        // 6 remise 7 tvA

    }//GEN-LAST:event_Devis_TableMouseClicked

    TableModel tableModel;

    private void update_table(String id_four) {

        try {
            pre_commandeDao pre_commandeDao = new pre_commandeDao();
            // pre_commandeDao.afficherPre_Commande(Devis_Table, id_four);
            FactureDAO factureDAO = new FactureDAO();
            lstBL = commandeDao.afficherCommande(Devis_Table, id_four);

            tableModel = new DefaultTableModel(lstBL, columnNames);
            Devis_Table.setModel(tableModel);

            ClientDao clientDao = new ClientDao();

            listNomSte = clientDao.afficherClient();
            //client_table.setModel(buildTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in ClientFrom/load_Update_table :  " + e);
        } finally {
            try {

                System.out.println("disconnected");
            } catch (Exception ex) {
                Logger.getLogger(CommandeForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }


    private void ComboBox_fourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox_fourActionPerformed
        // TODO add your handling code here:
        if (b) {

            String f = ComboBox_four.getSelectedItem().toString();
            if (!f.equals("-")) {
                String ss = daoArticle.getNameItemById("fournisseur", "id", "nom", f);
                update_table(ss);
            } else {
                update_table("-1");
                //clearTable();
            }
            clearExportTable();
        }
        b = true;

    }//GEN-LAST:event_ComboBox_fourActionPerformed
    Vector<Vector<Object>> lstBL;
    Vector<Vector<Object>> lstBLexport;
    Vector<String> columnNames;

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int row = Devis_Table.getSelectedRow();

        lstBLexport.add(lstBL.get(row));
        lstBL.removeElementAt(row);

        tableModel = new DefaultTableModel(lstBL, columnNames);
        Devis_Table.setModel(tableModel);

        tableModel1 = new DefaultTableModel(lstBLexport, columnNames);
        Devis_TableExport.setModel(tableModel1);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int row = Devis_TableExport.getSelectedRow();
        lstBL.add(lstBLexport.get(row));
        lstBLexport.removeElementAt(row);

        tableModel = new DefaultTableModel(lstBL, columnNames);
        Devis_Table.setModel(tableModel);

        tableModel1 = new DefaultTableModel(lstBLexport, columnNames);
        Devis_TableExport.setModel(tableModel1);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        int i = lstBL.size();
        for (int j = 0; j < i; j++) {

            lstBLexport.add(lstBL.get(0));
            lstBL.removeElementAt(0);
        }
        tableModel = new DefaultTableModel(lstBL, columnNames);
        Devis_Table.setModel(tableModel);

        tableModel1 = new DefaultTableModel(lstBLexport, columnNames);
        Devis_TableExport.setModel(tableModel1);


    }//GEN-LAST:event_jButton7ActionPerformed
    TableModel tableModel1;
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:

        int i = lstBLexport.size();
        for (int j = 0; j < i; j++) {

            lstBL.add(lstBLexport.get(0));
            lstBLexport.removeElementAt(0);
        }
        tableModel = new DefaultTableModel(lstBL, columnNames);
        Devis_Table.setModel(tableModel);

        tableModel1 = new DefaultTableModel(lstBLexport, columnNames);
        Devis_TableExport.setModel(tableModel1);
    }//GEN-LAST:event_jButton8ActionPerformed
    void clearExportTable() {
        int i = lstBLexport.size();
        for (int j = 0; j < i; j++) {

            lstBLexport.removeElementAt(0);
        }
        tableModel1 = new DefaultTableModel(lstBLexport, columnNames);
        Devis_TableExport.setModel(tableModel1);

    }

    void clearTable() {
        int i = lstBL.size();
        for (int j = 0; j < i; j++) {

            lstBL.removeElementAt(0);
        }
        tableModel = new DefaultTableModel(lstBL, columnNames);
        Devis_Table.setModel(tableModel);

    }
    private void Devis_TableExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Devis_TableExportMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Devis_TableExportMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        /*  JFrame frame = new JFrame("Row Filter");
        frame.add(TestTableSortFilter());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);*/

        int row = Devis_Table.getSelectedRow();
        if (row != -1) {

            Object o = tableModel.getValueAt(row, 0);

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Ref Produit");
            columnNames.add("Designation");
            columnNames.add("quantité");

            Vector<Vector<Object>> data1 = commandeDao.afficherListPreCommande(o.toString());
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

            frameListeClient = new JFrame("Consultation bon commande fournisseur");
            frameListeClient.add(Homepanel);
            frameListeClient.pack();
            frameListeClient.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frameListeClient.setLocationRelativeTo(null);
            frameListeClient.setVisible(true);
            jbtValider.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    /*  nomclient = jTable.getModel().getValueAt(jTable.getSelectedRow(), 1).toString();
                txt_search.setText(nomclient);
                ArticleDao d = new ArticleDao();
                String code_tva = d.getNameItemById("client", "id_Fiscale", "nom", txt_search.getText());
                String s = jTable.getModel().getValueAt(jTable.getSelectedRow(), 0).toString();
                String adresse = d.getNameItemById("client", "adresse", "nom", txt_search.getText());

                id_client = s;*/
                    frameListeClient.dispose();
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionner une pre-commande !");
        }
    }//GEN-LAST:event_jButton1ActionPerformed


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
    private javax.swing.JComboBox<String> ComboBox_four;
    private com.toedter.calendar.JDateChooser Date_Devis;
    private javax.swing.JTable Devis_Table;
    private javax.swing.JTable Devis_TableExport;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_num_bon_commande;
    // End of variables declaration//GEN-END:variables

    private void setNumDevis() {

        String s = commandeDao.maxNumDevis();
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

    private ArrayList<ligne_commande> setLigne_commandeFromDevisTable() {
        ArrayList<ligne_commande> lst = new ArrayList();

        try {

            for (int j = 0; j < Devis_TableExport.getRowCount(); j++) {
                ligne_commande ld = new ligne_commande();
                ld.setNum_commande(txt_num_bon_commande.getText());
                ld.setId_pre_commande(Devis_TableExport.getValueAt(j, 0).toString());
                SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

                String reformattedStr = myFormat.format(fromUser.parse(Devis_TableExport.getValueAt(j, 1).toString()));

                /* java.text.SimpleDateFormat sdf
                        = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format();*/
                ld.setDate_pre_command(reformattedStr);
                ArticleDao d = new ArticleDao();
                String id_client = "";
                if (Devis_TableExport.getValueAt(j, 2).toString().equals("Passager")) {
                    id_client = "99999999";
                } else {
                    id_client = d.getNameItemById("client", "numero_Client", "nom", Devis_TableExport.getValueAt(j, 2).toString());

                }
                ld.setId_client(Integer.valueOf(id_client));

                lst.add(ld);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error in  setLigneDevisFromDevisTable : " + e);
        }
        return lst;
    }

}
