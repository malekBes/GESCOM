/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recherche;

import BL.*;
import Devis.*;
import Article.ArticleDao;
import Client.ClientDao;
import Config.Commen_Proc;
import Conn.DataBase_connect;
import Home.App;
import PrintPapers.ReportGenarator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Mlek
 */
public class RecherchBLForm extends javax.swing.JInternalFrame {

    int i = 0;
    ArrayList<String> listNomSte;
    ArrayList<String> listNomArticle;
    Vector<String> columnNames_BL_Table;
    Vector<Vector<Object>> data_BL_Table;
    JButton jbtValider;
    JFrame frameListeClient;
    String nomclient;
    DefaultTableModel df;
    String id_client;
    RechercheDAO rechercheDao = new RechercheDAO();
    Double D_Total_HT = 0.0;
    Double D_Total_remise = 0.0;
    Double D_montant_TVA = 0.0;
    Double D_Total_Net = 0.0;
    private String year;

    /**
     * Creates new form form
     */
    public RecherchBLForm() {
        year = Commen_Proc.YearVal;
        initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTableHeader();
        data_BL_Table = new Vector<Vector<Object>>();
        if (!Commen_Proc.isRemote) {
            autoCompleteFields();
        }
        //curent date
        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        jButton4.setVisible(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        //update_table();
        SearchDevis();

        //TestTableSortFilter();
    }

    public void formatTableValues() {

        for (int j = 0; j < DevisHist_Table.getRowCount(); j++) {
            Object PU = df.getValueAt(j, 3);
            df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 3);
        }

    }

    public String formatDouble(Double d) {
        // String s = d.toString().replace(".", ";");
        DecimalFormat df = new DecimalFormat("0.000");
        df.setRoundingMode(RoundingMode.CEILING); // import java.text.DecimalFormat;
        String s = "0.0";
        try {
            s = df.format(d);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error in formatDouble methode :  " + e.getMessage());

        }

        return Config.Commen_Proc.formatDouble(d);
        /* try {

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

    private void update_table() {

        try {
            BLDao devisDao = new BLDao();
            devisDao.afficherDevis(DevisHist_Table);

            ClientDao clientDao = new ClientDao();

            listNomSte = clientDao.afficherClient();
            //client_table.setModel(buildTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in ClientFrom/load_Update_table :  " + e);
        } finally {
            try {

                System.out.println("disconnected");
            } catch (Exception ex) {
                Logger.getLogger(RecherchBLForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void setTableHeader() {
        columnNames_BL_Table = new Vector<String>();
        columnNames_BL_Table.add("id");
        columnNames_BL_Table.add("Référence");
        columnNames_BL_Table.add("Designation");
        columnNames_BL_Table.add("Prix Unit HT");
        columnNames_BL_Table.add("Quantité");
        columnNames_BL_Table.add("Totale HT");
        columnNames_BL_Table.add("Remise %");
        columnNames_BL_Table.add("TVA %");
        columnNames_BL_Table.add("Totale TTC");
        df = new DefaultTableModel(data_BL_Table, columnNames_BL_Table);
        DevisHist_Table.setModel(df);

    }

    /*
    public void reCalculerTT() {

        D_Total_HT = 0.0;
        D_Total_Net = 0.0;

        D_Total_remise = 0.0;
        D_montant_TVA = 0.0;

        for (int j = 0; j < Devis_Table.getRowCount(); j++) {
            Double Total_TTC = 0.0;
            Double Total_HT = 0.0;
            Double Prix_U = 0.0;
            Double montant_Tva = 0.0;
            Double montant_remise = 0.0;
            Double TVA = 0.0;
            String l_txt_TVA = Devis_Table.getValueAt(j, 7).toString();
            String l_txt_remise = Devis_Table.getValueAt(j, 6).toString();
            String l_txt_qte = Devis_Table.getValueAt(j, 4).toString();
            String l_txt_Prix_U = Devis_Table.getValueAt(j, 3).toString();

            if (l_txt_TVA.isEmpty()) {
                TVA = 0.0;
            } else {
                TVA = Double.valueOf(l_txt_TVA);
            }

            Double remise;
            if (l_txt_remise.isEmpty()) {
                remise = 0.0;
            } else {
                remise = Double.valueOf(l_txt_remise);
                if (remise >= 100) {
                    JOptionPane.showMessageDialog(null, "Remise ne depasse pas 100% ! ");
                    return;
                }
            }

            Double qte;
            if (l_txt_qte.isEmpty()) {
                qte = 1.0;
            } else {
                qte = Double.valueOf(l_txt_qte);
            }
            if (!l_txt_Prix_U.isEmpty()) {
                Prix_U = Double.valueOf(l_txt_Prix_U);

                Total_HT = Prix_U * qte;
                if (remise != 0.0) {
                    montant_remise = Total_HT * (remise / 100);
                }
                if (!CheckBox_exh_tva.isSelected()) {
                    montant_Tva = (Total_HT - montant_remise) * (TVA / 100);
                }

                Total_TTC += (Total_HT + montant_Tva) - montant_remise;
            }

            D_Total_HT += Total_HT;
            D_Total_Net += Total_TTC;
            if (!l_txt_remise.isEmpty()) {
                D_Total_remise += (Total_HT * (Double.valueOf(l_txt_remise) / 100));
            }
            D_montant_TVA += montant_Tva;
        }
        Double timbre = 0.0;
        if (CheckBox_timbre.isSelected()) {
            timbre = Double.valueOf(txt_timbre.getText());
        }

        txt_Total_HT.setText(String.valueOf(formatDouble(D_Total_HT)));
        txt_Total_Net.setText(String.valueOf(formatDouble(D_Total_Net + timbre)));
        txt_Total_remise.setText(String.valueOf(formatDouble(D_Total_remise)));
        txt_total_TVA.setText(String.valueOf(formatDouble(D_montant_TVA)));

    }
     */
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
     * FormBL. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Date_Devis = new com.toedter.calendar.JDateChooser();
        Date_Devis_To = new com.toedter.calendar.JDateChooser();
        txt_montant_from = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_num_devis = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_montant_to = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DevisHistDetail_Table = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jCheckBoxHideTotal = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DevisHist_Table = new javax.swing.JTable();

        setClosable(true);
        setTitle("Consultation BL");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtre"));

        jLabel2.setText("Date BL");

        txt_montant_from.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_montant_fromKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_montant_fromKeyReleased(evt);
            }
        });

        jLabel3.setText("Montant");

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

        jLabel1.setText("Nom Client");

        txt_num_devis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_num_devisKeyPressed(evt);
            }
        });

        jLabel4.setText("Num BL");

        txt_montant_to.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_montant_toKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_montant_toKeyReleased(evt);
            }
        });

        jButton7.setText("Liste");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
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
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_search)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_montant_from, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_montant_to, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Date_Devis, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(txt_num_devis))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Date_Devis_To, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7)
                .addGap(84, 84, 84))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_num_devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(Date_Devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Date_Devis_To, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_montant_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txt_montant_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Detail BL"));

        DevisHistDetail_Table.setModel(new javax.swing.table.DefaultTableModel(
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
        DevisHistDetail_Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DevisHistDetail_TableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(DevisHistDetail_Table);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1071, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Action"));

        jButton1.setText("Valider");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Editer");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Exporter");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setText("Modifier");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCheckBoxHideTotal.setText("Masquer Total");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton2)
                    .addComponent(jCheckBoxHideTotal))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBoxHideTotal))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("BL"));

        DevisHist_Table.setModel(new javax.swing.table.DefaultTableModel(
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
        DevisHist_Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DevisHist_TableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DevisHist_TableMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(DevisHist_Table);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private BL setDevisFromEditText() {
        BL r = new BL();

        try {

            if (id_client != null) {
                r.setId_client(Integer.valueOf(id_client));
            } else {
                r.setId_client(0);
            }
            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(Date_Devis.getDate());
            r.setDate_bl(currentTime);

            r.setTotale_HT(D_Total_HT);
            r.setTotale_TTC(D_Total_Net);
            r.setMontant_TVA(D_montant_TVA);
            r.setRemise(D_Total_remise);

            r.setTimbre(0);

            /* if (CheckBox_Afficher_prix.isSelected()) {
                r.setAfficher_prix(1);
            } else {
                r.setAfficher_prix(0);
            }

            if (CheckBox_afficher_totale.isSelected()) {
                r.setAfficher_total(1);
            } else {
                r.setAfficher_total(0);
            }

            if (CheckBox_afficher_validiter.isSelected()) {
                r.setAfficher_validiter(1);
            } else {
                r.setAfficher_validiter(0);
            }

            if (CheckBox_proformat.isSelected()) {
                r.setFacture_proformat(1);
            } else {
                r.setFacture_proformat(0);
            }*/
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println(e);

        }

        return r;
    }

    public String formatString(String s) {

        return s.substring(0, s.indexOf(".") + 4);
    }


    private void DevisHistDetail_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DevisHistDetail_TableMouseClicked
        // TODO add your handling code here:
        int row = DevisHistDetail_Table.getSelectedRow();
        String ref = DevisHist_Table.getModel().getValueAt(row, 1).toString();
        String design = DevisHist_Table.getModel().getValueAt(row, 2).toString();
        String prix_u = DevisHist_Table.getModel().getValueAt(row, 3).toString();
        String qte = DevisHist_Table.getModel().getValueAt(row, 4).toString();
        String remise = DevisHist_Table.getModel().getValueAt(row, 6).toString();
        String tva = DevisHist_Table.getModel().getValueAt(row, 7).toString();

        // 6 remise 7 tvA

    }//GEN-LAST:event_DevisHistDetail_TableMouseClicked

    private void txt_montant_fromKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montant_fromKeyReleased
        // TODO add your handling code here:
        /* if (evt.getKeyCode() == evt.VK_BACK_SPACE || evt.getKeyCode() == evt.VK_DELETE) {

        } else {
            String to_check = txt_Search_Devis.getText().toLowerCase();
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
                    txt_Search_Devis.setText(data);
                    txt_Search_Devis.setSelectionStart(to_check_len);
                    txt_Search_Devis.setSelectionEnd(data.length());
                    break;
                }
            }
        }*/
    }//GEN-LAST:event_txt_montant_fromKeyReleased
    public void SearchDevis() {
        try {
            String FromDate = "";
            String ToDate = "";

            try {
                String df = Date_Devis.getDate().toString();
                if (df.isEmpty() & Date_Devis.getDate().toString().isEmpty()) {
                } else {

                    Date From = Date_Devis.getDate();
                    SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                    FromDate = tdate.format(From);
                }

            } catch (Exception e) {
                System.out.println("date Null");
            }

            try {
                String dt = Date_Devis_To.getDate().toString();
                if (dt.isEmpty() & Date_Devis_To.getDate().toString().isEmpty()) {
                } else {

                    Date From = Date_Devis_To.getDate();
                    SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                    ToDate = tdate.format(From);
                }

            } catch (Exception e) {
                System.out.println("date Null");
            }

            rechercheDao.afficherBL(DevisHist_Table, FromDate,
                    ToDate, txt_montant_from.getText(), txt_montant_to.getText(), txt_num_devis.getText(),
                    txt_search.getText());
            setHeaders();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in HistoriqueBLFrom/SearchBL : \n" + e);
        } finally {
            try {

                System.out.println("disconnected");
            } catch (Exception ex) {
                Logger.getLogger(RecherchBLForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void setHeaders() {
        DevisHist_Table.getColumnModel().getColumn(0).setHeaderValue("Nom Client");
        DevisHist_Table.getColumnModel().getColumn(1).setHeaderValue("Num BL");

        DevisHist_Table.getColumnModel().getColumn(2).setHeaderValue("Date BL");
        DevisHist_Table.getColumnModel().getColumn(3).setHeaderValue("Total TTC");
        DevisHist_Table.getColumnModel().getColumn(4).setHeaderValue("F / N");

        DevisHist_Table.getTableHeader().resizeAndRepaint();

    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        SearchDevis();

    }//GEN-LAST:event_jButton1ActionPerformed
    public String tab_DevisHist;
    private void DevisHist_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DevisHist_TableMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_DevisHist_TableMouseClicked
    private void setHeadersDetail() {
        DevisHistDetail_Table.getColumnModel().getColumn(0).setHeaderValue("id");
        DevisHistDetail_Table.getColumnModel().getColumn(1).setHeaderValue("Num BL");
        DevisHistDetail_Table.getColumnModel().getColumn(2).setHeaderValue("Référence");
        DevisHistDetail_Table.getColumnModel().getColumn(3).setHeaderValue("Designation");
        DevisHistDetail_Table.getColumnModel().getColumn(4).setHeaderValue("Quantité");
        DevisHistDetail_Table.getColumnModel().getColumn(5).setHeaderValue("Prix Unit HT");
        DevisHistDetail_Table.getColumnModel().getColumn(6).setHeaderValue("Remise %");
        DevisHistDetail_Table.getColumnModel().getColumn(7).setHeaderValue("TVA %");
        DevisHistDetail_Table.getColumnModel().getColumn(8).setHeaderValue("Totale HT");
        DevisHistDetail_Table.getColumnModel().getColumn(9).setHeaderValue("Totale TTC");
        DevisHistDetail_Table.getTableHeader().resizeAndRepaint();
    }
    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed

    }//GEN-LAST:event_txt_searchActionPerformed

    private void txt_searchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchDevis();
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

    private void txt_montant_toKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montant_toKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_montant_toKeyReleased

    private void txt_num_devisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_num_devisKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchDevis();
        }

    }//GEN-LAST:event_txt_num_devisKeyPressed

    private void txt_montant_fromKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montant_fromKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchDevis();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_montant_fromKeyPressed

    private void txt_montant_toKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montant_toKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchDevis();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_montant_toKeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        FormBL c;
        if (txt_num_devis.getText().isEmpty()) {
            c = new FormBL("", "", "");
        } else {
            c = new FormBL(txt_num_devis.getText(), id_client, "");
        }
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
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        ds.repaint();
    }//GEN-LAST:event_jButton4ActionPerformed
    JButton jbtValiderarticle;
    JButton jbRecherchearticle;
    JTextField jtxtRecherchearticle;
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
        jbRecherchearticle = new JButton("Recherche");
        jtxtRecherchearticle = new JTextField(30);
//jTable.setRowSorter(rowSorter);
        JPanel Homepanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelbtn = new JPanel(new FlowLayout());

        panelbtn.add(jbtValider, BorderLayout.SOUTH);
        panelbtn.add(jbRecherchearticle, BorderLayout.NORTH);
        panelbtn.add(jtxtRecherchearticle, BorderLayout.NORTH);

        panel.add(panelbtn, BorderLayout.SOUTH);
        jbRecherchearticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                ClientDao articleDao = new ClientDao();
                Vector<Vector<Object>> data1 = articleDao.afficherListClientByName(jtxtRecherchearticle.getText());

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
                frameListeClient.dispose();
            }
        });
    }//GEN-LAST:event_jButton7ActionPerformed

    private void DevisHist_TableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DevisHist_TableMousePressed
        // TODO add your handling code here:
        try {
            BLDao devisDao = new BLDao();
            // FormBL fr = new FormBL("", "", "");
            int row = DevisHist_Table.getSelectedRow();
            String table_click = DevisHist_Table.getModel().getValueAt(row, 1).toString();
            devisDao.afficherDetailBL(DevisHistDetail_Table, table_click);

            ClientDao clientDao = new ClientDao();
            txt_num_devis.setText(table_click);
            listNomSte = clientDao.afficherClient();
            setHeadersDetail();
            //client_table.setModel(buildTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in ClientFrom/load_Update_table :  " + e);
        } finally {
            try {

                System.out.println("disconnected");
            } catch (Exception ex) {
                Logger.getLogger(RecherchBLForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_DevisHist_TableMousePressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        FormBL c;
        if (txt_num_devis.getText().isEmpty()) {
            c = new FormBL("", "", "");
        } else {
            c = new FormBL(txt_num_devis.getText(), id_client, "Modif");
        }
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
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        ds.repaint();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            int row = DevisHist_Table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Sélectioner une devis ! ");
            } else {

                String num_devis = DevisHist_Table.getModel().getValueAt(row, 1).toString();
                DataBase_connect obj = new DataBase_connect();

                Connection conn = obj.Open();
                Map<String, Object> map = new HashMap<>();
                map.put("PathImg", Commen_Proc.PathImg);

                if (jCheckBoxHideTotal.isSelected()) {
                    map.put("HideTotal", "Oui");
                } else {
                    map.put("HideTotal", "Non");
                }

                map.put("num_bl", num_devis);
                String date_devis = DevisHist_Table.getModel().getValueAt(row, 2).toString();
                map.put("date_bl", date_devis);
                String total_ttc = DevisHist_Table.getModel().getValueAt(row, 3).toString();

                String sql1;
                sql1 = "select montant_tva,Total_HT from bl where num_bl='" + num_devis + "'";
                PreparedStatement pst;
                ResultSet rs = null;
                pst = conn.prepareStatement(sql1);

                rs = pst.executeQuery();
                String total_ht = "0";
                String total_tva = "0";
                while (rs.next()) {
                    total_tva = rs.getString("montant_tva");
                    total_ht = rs.getString("Total_HT");
                }

                map.put("TOTAL_HT", Config.Commen_Proc.formatDouble(Double.parseDouble(total_ht)));
                map.put("TOTAL_TTC", Config.Commen_Proc.formatDouble(Double.parseDouble(total_ttc)));
                map.put("TOTAL_TVA", Config.Commen_Proc.formatDouble(Double.parseDouble(total_tva)));

                String sql;
                sql = "select sum(remise) from bl where num_bl='" + num_devis + "'";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();
                String isRemise = "";
                String total_remise = "0";

                while (rs.next()) {
                    isRemise = rs.getString("sum(remise)").equals("0") ? "Non" : "Oui";
                    total_remise = rs.getString("sum(remise)");
                }
                map.put("remise", isRemise);
                map.put("TOTAL_REMISE", Config.Commen_Proc.formatDouble(Double.parseDouble(total_remise)));

                String file_name = "BL_" + num_devis;
                if (JOptionPane.showConfirmDialog(null, "Avec en-tête ?", "Edition",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    new ReportGenarator().genarateReport(
                            ReportGenarator.REPORT_BL_Entete, map, conn, file_name, DevisHistDetail_Table, num_devis);
                } else {
                    // no option
                    new ReportGenarator().genarateReport(
                            ReportGenarator.REPORT_BL, map, conn, file_name, DevisHistDetail_Table, num_devis);
                }

                JOptionPane.showMessageDialog(null, "Fichier Pdf généré avec succès");
                try {
                    conn.close();
                    System.out.println("disconnected");
                } catch (SQLException ex) {
                    Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "BL  PDF:  " + e.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Date_Devis;
    private com.toedter.calendar.JDateChooser Date_Devis_To;
    private javax.swing.JTable DevisHistDetail_Table;
    private javax.swing.JTable DevisHist_Table;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBoxHideTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_montant_from;
    private javax.swing.JTextField txt_montant_to;
    private javax.swing.JTextField txt_num_devis;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables

    private ArrayList<LigneBL> setLigneDevisFromDevisTable() {

        ArrayList<LigneBL> lst = new ArrayList();

        for (int j = 0; j < DevisHistDetail_Table.getRowCount(); j++) {
            LigneBL ld = new LigneBL();
            ld.setRef_article(DevisHistDetail_Table.getValueAt(j, 1).toString());
            ld.setDesign(DevisHistDetail_Table.getValueAt(j, 2).toString());
            ld.setPrix_U(Double.valueOf(DevisHistDetail_Table.getValueAt(j, 3).toString().replace(",", ".")));
            ld.setQte(DevisHistDetail_Table.getValueAt(j, 4).toString());
            ld.setTotal_HT(Double.valueOf(DevisHistDetail_Table.getValueAt(j, 5).toString().replace(",", ".")));
            ld.setRemise(DevisHistDetail_Table.getValueAt(j, 6).toString());
            ld.setTVA(DevisHistDetail_Table.getValueAt(j, 7).toString());
            ld.setTotale_TTC(Double.valueOf(DevisHistDetail_Table.getValueAt(j, 8).toString().replace(",", ".")));

            lst.add(ld);

        }
        return lst;
    }

    public void TestTableSortFilter() {
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(DevisHist_Table.getModel());

        DevisHist_Table.setRowSorter(rowSorter);

        txt_montant_from.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = txt_montant_from.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txt_montant_from.getText();

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
    }
}
