/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Achat.commande;

import Achat.pre_commande.pre_commandeDao;
import Recherche.*;
import Client.ClientDao;
import BL.*;
import Devis.*;
import Article.ArticleDao;
import Client.ClientDao;
import Config.Commen_Proc;
import Config.ConvertChiffreToString;
import Conn.DataBase_connect;
import Facture.FactureDAO;
import Home.App;
import PrintPapers.ReportGenarator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.List;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
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
public class RecherchPreCommandeForm extends javax.swing.JInternalFrame {

    JButton jbtValider;
    JFrame frameListeClient;
    String nomclient;
    DefaultTableModel df;
    String id_client;
    boolean b = false;
    ArrayList<String> listNomSte;
    ArrayList<String> listNomArticle;
    CommandeDAO commandeDAO = new CommandeDAO();
    HashMap<String, String> fournisseurHashMap;

    /**
     * Creates new form RecherchForm
     */
    public RecherchPreCommandeForm() {
         initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        buttonGroup1.add(RadioTous);
        buttonGroup1.add(RadioEnCours);
        buttonGroup1.add(RadioCommande);
        Exporter.setVisible(false);
        fournisseurComboBox();
        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        //update_table();
        SearchDevis("Tous");
    }

    public void fournisseurComboBox() {
        ArticleDao article = new ArticleDao();
        fournisseurHashMap = article.listFournisseur(ComboBox_four);
    }

    public void SearchDevis(String Filtre) {
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
            String id_four = "";
            String f = ComboBox_four.getSelectedItem().toString();
            if (!f.equals("-")) {
                id_four = daoArticle.getNameItemById("fournisseur", "id", "nom", f);
            }
            commandeDAO.afficherPreCommandeRecherche(DevisHist_Table, FromDate,
                    ToDate, txt_num_devis.getText(),
                    txt_search.getText(), id_four, Filtre);

            setHeaders();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in HistoriqueDevisFrom/SearchDevis : \n" + e);
        } finally {
            try {

                System.out.println("disconnected");
            } catch (Exception ex) {
                Logger.getLogger(RecherchBLForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DevisHist_Table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Date_Devis = new com.toedter.calendar.JDateChooser();
        Date_Devis_To = new com.toedter.calendar.JDateChooser();
        txt_search = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_num_devis = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        ComboBox_four = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        RadioTous = new javax.swing.JRadioButton();
        RadioCommande = new javax.swing.JRadioButton();
        RadioEnCours = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DevisHistDetail_Table = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        Exporter = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consultation global des Pré-Commande");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Devis"));

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtre"));

        jLabel2.setText("Date Comm");

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

        jLabel4.setText("Num Pré-Commande");

        jButton7.setText("Liste");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        ComboBox_four.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboBox_four.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBox_fourActionPerformed(evt);
            }
        });

        jLabel15.setText("Fournisseur *");

        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        RadioTous.setText("Tous");
        RadioTous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioTousActionPerformed(evt);
            }
        });

        RadioCommande.setText("Commandé");
        RadioCommande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioCommandeActionPerformed(evt);
            }
        });

        RadioEnCours.setText("En Cours");
        RadioEnCours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioEnCoursActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RadioTous)
                    .addComponent(RadioEnCours)
                    .addComponent(RadioCommande))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(RadioTous, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RadioEnCours)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RadioCommande)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel15))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Date_Devis, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                                    .addComponent(txt_num_devis))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Date_Devis_To, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(ComboBox_four, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_num_devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(Date_Devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Date_Devis_To, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                            .addComponent(ComboBox_four))
                        .addGap(18, 32, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jButton7))))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Detail Devis"));

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

        Exporter.setText("Exporter");
        Exporter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExporterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Exporter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Exporter)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
void setHeaders() {
        DevisHist_Table.getColumnModel().getColumn(0).setHeaderValue("Nom Fournisseur");
        DevisHist_Table.getColumnModel().getColumn(1).setHeaderValue("Num Bon Commande");

        DevisHist_Table.getColumnModel().getColumn(2).setHeaderValue("Date Pré-Commande");
        DevisHist_Table.getColumnModel().getColumn(3).setHeaderValue("Nom Client");
        DevisHist_Table.getColumnModel().getColumn(4).setHeaderValue("Num Commande");

        DevisHist_Table.getTableHeader().resizeAndRepaint();

    }
    private void DevisHist_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DevisHist_TableMouseClicked

    }//GEN-LAST:event_DevisHist_TableMouseClicked

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed

    }//GEN-LAST:event_txt_searchActionPerformed

    private void txt_searchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchDevis("Tous");
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

    private void txt_num_devisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_num_devisKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchDevis("Tous");
        }
    }//GEN-LAST:event_txt_num_devisKeyPressed

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
                frameListeClient.dispose();
            }
        });
    }//GEN-LAST:event_jButton7ActionPerformed

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        SearchDevis("Tous");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ExporterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExporterActionPerformed
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
    }//GEN-LAST:event_ExporterActionPerformed

    ArticleDao daoArticle = new ArticleDao();
    private void ComboBox_fourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox_fourActionPerformed
        // TODO add your handling code here:
        if (b) {

            radioSelected();
            SearchDevis("Tous");
        }
        b = true;
    }//GEN-LAST:event_ComboBox_fourActionPerformed

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jPanel6MouseClicked
    String selectedRdio = "";

    public void radioSelected() {

        if (RadioCommande.isSelected()) {
            selectedRdio = "Commande";
        } else if (RadioTous.isSelected()) {
            selectedRdio = "Tous";
        } else if (RadioEnCours.isSelected()) {
            selectedRdio = "En Cours";
        } else {
            selectedRdio = "";
        }

    }
    private void RadioTousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioTousActionPerformed
        radioSelected();
        SearchDevis("Tous");
        // TODO add your handling code here:
    }//GEN-LAST:event_RadioTousActionPerformed

    private void RadioEnCoursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioEnCoursActionPerformed
        // TODO add your handling code here:
        radioSelected();
        SearchDevis("En Cours");

    }//GEN-LAST:event_RadioEnCoursActionPerformed

    private void RadioCommandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioCommandeActionPerformed
        // TODO add your handling code here:
        radioSelected();
        SearchDevis("Commande");

    }//GEN-LAST:event_RadioCommandeActionPerformed

    private void DevisHist_TableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DevisHist_TableMousePressed
        // TODO add your handling code here:
        try {

            int row = DevisHist_Table.getSelectedRow();
            String table_click = DevisHist_Table.getModel().getValueAt(row, 1).toString();
            commandeDAO.afficherDetailPre_Commande(DevisHistDetail_Table, table_click);

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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            int row = DevisHist_Table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Sélectioner une devis ! ");
            } else {
                DataBase_connect obj = new DataBase_connect();

                Connection conn = obj.Open();
                Map<String, Object> map = new HashMap<>();

                String num_pre_cmd = DevisHist_Table.getModel().getValueAt(row, 1).toString();

                map.put("num_pre_cmd", num_pre_cmd);

                String nom_founisseur = DevisHist_Table.getModel().getValueAt(row, 0).toString();
                map.put("nom_founisseur", nom_founisseur);

                String nom_client = DevisHist_Table.getModel().getValueAt(row, 3).toString();
                map.put("nom_client", nom_client);

                String date_pre_cmd = DevisHist_Table.getModel().getValueAt(row, 2).toString();
                map.put("date_pre_cmd", date_pre_cmd);

                map.put("nb_rows", String.valueOf(DevisHistDetail_Table.getModel().getRowCount()));

                String file_name = "Pré Commande " + num_pre_cmd;

                new ReportGenarator().genarateReport(
                        ReportGenarator.STAT_Pre_Commande, map, conn, file_name, DevisHistDetail_Table, "pre;" + num_pre_cmd);

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
    private void setHeadersDetail() {
        DevisHistDetail_Table.getColumnModel().getColumn(0).setHeaderValue("Num Pré-Commande");
        DevisHistDetail_Table.getColumnModel().getColumn(1).setHeaderValue("Référence");
        DevisHistDetail_Table.getColumnModel().getColumn(2).setHeaderValue("Designation");
        DevisHistDetail_Table.getColumnModel().getColumn(3).setHeaderValue("Quantité");
        DevisHistDetail_Table.getTableHeader().resizeAndRepaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBox_four;
    private com.toedter.calendar.JDateChooser Date_Devis;
    private com.toedter.calendar.JDateChooser Date_Devis_To;
    private javax.swing.JTable DevisHistDetail_Table;
    private javax.swing.JTable DevisHist_Table;
    private javax.swing.JButton Exporter;
    private javax.swing.JRadioButton RadioCommande;
    private javax.swing.JRadioButton RadioEnCours;
    private javax.swing.JRadioButton RadioTous;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_num_devis;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables
}
