/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facture;

import Article.ArticleDao;
import BL.HistoriqueDevis;
import Client.ClientDao;
import Client.ClientForm;
import Config.Commen_Proc;
import Home.App;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Mlek
 */
public class FactureFormFournisseur extends javax.swing.JInternalFrame {

    ArrayList<String> listNomSte;
    FactureDAO factureDAO;
    String year;
    Vector<String> columnNames;
    JButton jbtValider;
    JFrame frameListeClient;
    String nomclient;
    TableModel tableModel;

    /**
     * Creates new form FactureForm
     */
    public FactureFormFournisseur(Vector<Vector<Object>> obj, String s) {

         initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        year = Commen_Proc.YearVal;
        listNomSte = new ArrayList<>();
        ClientDao clientDao = new ClientDao();
        factureDAO = new FactureDAO();
        listNomSte = clientDao.afficherClient();
        fournisseurComboBox();
        Date_facture.setDate(new Date());
        Date_facture.setEnabled(false);
        CheckBox_timbre.setSelected(true);
        txt_timbre.setText(Commen_Proc.TimbreVal);
        if (s == "true") {
            columnNames = new Vector<String>();
            columnNames.add("Nom Fournisseur");
            columnNames.add("Date Facture");
            columnNames.add("Num Facture");
            columnNames.add("Total HT");
            columnNames.add("Remise");
            columnNames.add("TVA");
            columnNames.add("TTC");
            tableModel = new DefaultTableModel(obj, columnNames);
            Table_facture.setModel(tableModel);
            Object nom_four = Table_facture.getValueAt(0, 0);
            ComboBox_four.setSelectedItem(nom_four.toString());
            ArticleDao d = new ArticleDao();
            String adresse = d.getNameItemById("fournisseur", "adresse", "Nom", nom_four.toString());
            txt_adresse.setText(adresse);
            formatTableValues();
            reCalculerTT();

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

    public void fournisseurComboBox() {
        ArticleDao article = new ArticleDao();
        article.listFournisseur(ComboBox_four);
    }
    Double D_Total_HT = 0.0;
    Double D_Total_remise = 0.0;
    Double D_montant_TVA = 0.0;
    Double D_Total_Net = 0.0;

    public void formatTableValues() {

        for (int j = 0; j < Table_facture.getRowCount(); j++) {
            Object PU = tableModel.getValueAt(j, 3);
            tableModel.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 3);
            Object HT = tableModel.getValueAt(j, 4);
            tableModel.setValueAt(formatDouble(Double.parseDouble(HT.toString())), j, 4);
            Object TTC = tableModel.getValueAt(j, 5);
            tableModel.setValueAt(formatDouble(Double.parseDouble(TTC.toString())), j, 5);
            Object remise = tableModel.getValueAt(j, 6);
            tableModel.setValueAt(formatDouble(Double.parseDouble(remise.toString())), j, 6);

            // JOptionPane.showMessageDialog(null, " index : " + j + " value : " + s);
        }

    }

    public void reCalculerTT() {

        D_Total_HT = 0.0;
        D_Total_Net = 0.0;

        D_Total_remise = 0.0;
        D_montant_TVA = 0.0;

        for (int j = 0; j < Table_facture.getRowCount(); j++) {
            Double Total_TTC = 0.0;
            Double Total_HT = 0.0;
            Double Prix_U = 0.0;
            Double montant_Tva = 0.0;
            Double montant_remise = 0.0;
            Double TVA = 0.0;

            String l_txt_ht = Table_facture.getValueAt(j, 3).toString();
            String l_txt_remise = Table_facture.getValueAt(j, 4).toString();
            String l_txt_TVA = Table_facture.getValueAt(j, 5).toString();
            String l_txt_TTC = Table_facture.getValueAt(j, 6).toString();

            D_Total_HT += Double.parseDouble(l_txt_ht);
            D_Total_remise += Double.parseDouble(l_txt_remise);

            D_montant_TVA += Double.parseDouble(l_txt_TVA);

            D_Total_Net += Double.parseDouble(l_txt_TTC);

        }
        Double timbre = 0.0;
        if (CheckBox_timbre.isSelected()) {
            timbre = Double.valueOf(txt_timbre.getText());
        }
        D_Total_Net += timbre;
        txt_Total_HT.setText(String.valueOf(formatDouble(D_Total_HT)));
        txt_Total_Net.setText(String.valueOf(formatDouble(D_Total_Net)));
        txt_Total_remise.setText(String.valueOf(formatDouble(D_Total_remise)));
        txt_total_TVA.setText(String.valueOf(formatDouble(D_montant_TVA)));

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
        df.setRoundingMode(RoundingMode.CEILING); // import java.text.DecimalFormat;
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Date_facture = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txt_num_facture = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_infos_facture = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_adresse = new javax.swing.JTextField();
        ComboBox_four = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_Total_Net = new javax.swing.JTextField();
        txt_Total_HT = new javax.swing.JTextField();
        txt_total_TVA = new javax.swing.JTextField();
        txt_timbre = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_Total_remise = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_facture = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        CheckBox_timbre = new javax.swing.JCheckBox();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Facture Fournisseur");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Info Facture"));

        jLabel1.setText("Nom Fournisseur");

        jLabel2.setText("Date Facture");

        jLabel4.setText("Num Facture");

        jLabel5.setText("Info Facture");

        jLabel6.setText("Adresse");

        txt_adresse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_adresseActionPerformed(evt);
            }
        });

        ComboBox_four.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboBox_four.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBox_fourActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_adresse)
                            .addComponent(txt_infos_facture)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Date_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(ComboBox_four, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txt_num_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_num_facture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(ComboBox_four))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(Date_facture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_adresse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_infos_facture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel13.setText("Montant remise");

        jLabel15.setText("Timbre");

        jLabel16.setText("Total Net");

        jLabel12.setText("Total Hors Taxe");

        jLabel14.setText("Montant TVA");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_timbre, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                        .addComponent(txt_Total_remise, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_total_TVA, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_Total_Net, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_Total_HT, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Total_HT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Total_remise, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_total_TVA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_timbre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Total_Net, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        jButton1.setText("Exporter BL");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Table_facture.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(Table_facture);

        jButton3.setText("Valider");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        CheckBox_timbre.setText("Timbre");
        CheckBox_timbre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBox_timbreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(CheckBox_timbre)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(CheckBox_timbre))
                        .addGap(101, 101, 101)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(164, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_adresseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_adresseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_adresseActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        HistoriqueBLFournisseurForm c = new HistoriqueBLFournisseurForm();
        Home.App.d.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height - 100);
        c.setVisible(true);

        JDesktopPane ds = getDesktopPane();
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        ds.repaint();
        this.dispose();

    }//GEN-LAST:event_jButton1ActionPerformed
    FactureDAO fdao = new FactureDAO();
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            Facture d = setFactureFromEditText();
            //JOptionPane.showMessageDialog(null, s.toString());
            if (d != null) {

                if (fdao.ajouterFactureFournisseur(d)) {

                    ArrayList<ligne_facture> lstd;
                    lstd = setLigneDevisFromDevisTable();
                    fdao.ajouterLigneFactureFournisseur(lstd);
                    fdao.setBLFournisseurInvoiced(lstd);
                    JOptionPane.showMessageDialog(null, "Facture Fournisseur " + txt_num_facture.getText() + " à été bien enregistré !");
                    clearBLFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Error in CreateBL ActionPerformed ");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Verifier les champs obligatoire !");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in VLFrom/Ajouter_btn :  " + e);
        } finally {
            try {

            } catch (Exception ex) {
                Logger.getLogger(FactureFormFournisseur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed
    void clearBLFields() {
        txt_num_facture.setText("");
        ComboBox_four.setSelectedItem("-");
        Date_facture.setDate(new Date());
        Date_facture.setEnabled(false);
        txt_infos_facture.setText("");
        Vector<Vector<Object>> obj = null;
        tableModel = new DefaultTableModel(obj, columnNames);
        Table_facture.setModel(tableModel);
        txt_Total_HT.setText("");
        txt_adresse.setText("");
        txt_timbre.setText("");
        txt_Total_Net.setText("");
        txt_Total_remise.setText("");
        txt_total_TVA.setText("");

    }

    private ArrayList<ligne_facture> setLigneDevisFromDevisTable() {

        ArrayList<ligne_facture> lst = new ArrayList<ligne_facture>();

        for (int j = 0; j < Table_facture.getRowCount(); j++) {
            ligne_facture lf = new ligne_facture();
            lf.setNum_bl(Table_facture.getValueAt(j, 2).toString());
            lf.setNum_facture(txt_num_facture.getText());
            lst.add(lf);
        }
        return lst;
    }
    private void CheckBox_timbreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBox_timbreActionPerformed
        // TODO add your handling code here:
        if (CheckBox_timbre.isSelected()) {
            txt_timbre.setText(Commen_Proc.TimbreVal);
            txt_timbre.setEnabled(true);

        } else {
            txt_timbre.setEnabled(false);
            txt_timbre.setText("0.000");
        }
        reCalculerTT();

    }//GEN-LAST:event_CheckBox_timbreActionPerformed

    private void ComboBox_fourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox_fourActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBox_fourActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckBox_timbre;
    private javax.swing.JComboBox<String> ComboBox_four;
    private com.toedter.calendar.JDateChooser Date_facture;
    private javax.swing.JTable Table_facture;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_Total_HT;
    private javax.swing.JTextField txt_Total_Net;
    private javax.swing.JTextField txt_Total_remise;
    private javax.swing.JTextField txt_adresse;
    private javax.swing.JTextField txt_infos_facture;
    private javax.swing.JTextField txt_num_facture;
    private javax.swing.JTextField txt_timbre;
    private javax.swing.JTextField txt_total_TVA;
    // End of variables declaration//GEN-END:variables

    private Facture setFactureFromEditText() {
        Facture r = new Facture();

        // try {
        if (!txt_num_facture.getText().isEmpty()) {
            r.setNum_facture(txt_num_facture.getText());
        } else {
            r.setNum_facture("");
            return null;
        }
        if (!txt_adresse.getText().isEmpty()) {
            r.setAdresse(txt_adresse.getText());
        } else {
            r.setAdresse("");
        }

        ArticleDao daoArticle = new ArticleDao();
        String f = ComboBox_four.getSelectedItem().toString();
        if (!f.equals("-")) {
            String ss = daoArticle.getNameItemById("fournisseur", "id", "nom", f);
            r.setId_fournisseur(Integer.valueOf(ss));
        } else {
            r.setId_fournisseur(0);
            return null;

        }

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(Date_facture.getDate());

        r.setDate_facture(currentTime);

        if (!txt_infos_facture.getText().isEmpty()) {
            r.setInfo_facture(txt_infos_facture.getText());
        } else {
            r.setInfo_facture("");
        }

        if (!txt_Total_HT.getText().isEmpty()) {
            r.setHT(Double.parseDouble(formatDouble(Double.parseDouble(txt_Total_HT.getText()))));
        }
        if (!txt_Total_Net.getText().isEmpty()) {
            r.setTTC(Double.parseDouble(formatDouble(Double.parseDouble(txt_Total_Net.getText()))));
        }
        if (!txt_total_TVA.getText().isEmpty()) {
            r.setTVA(Double.parseDouble(formatDouble(Double.parseDouble(txt_total_TVA.getText()))));
        }
        if (!txt_Total_remise.getText().isEmpty()) {
            r.setRemise(Double.parseDouble(formatDouble(Double.parseDouble(txt_Total_remise.getText()))));
        }
        if (CheckBox_timbre.isSelected()) {
            r.setTimbre(Commen_Proc.TimbreVal);
        }

        /* } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println(e);

        }*/
        return r;
    }

}
