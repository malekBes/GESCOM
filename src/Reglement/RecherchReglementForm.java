/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reglement;

import Recherche.*;
import Client.ClientDao;
import BL.*;
import Devis.*;
import Article.ArticleDao;
import Client.ClientDao;
import Commercial.CommercialDao;
import Config.Commen_Proc;
import Conn.DataBase_connect;
import Home.App;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Mlek
 */
public class RecherchReglementForm extends javax.swing.JInternalFrame {

    JButton jbtValider;
    JFrame frameListeClient;
    String nomclient;
    DefaultTableModel df;
    String id_client;

    ArrayList<String> listNomSte;
    ArrayList<String> listNomArticle;
    RechercheDAO rechercheDao = new RechercheDAO();

    /**
     * Creates new form RecherchForm
     */
    public RecherchReglementForm() {
         initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        //Exporter.setVisible(false);
        jPanel6.setVisible(false);
        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        buttonGroupFiltre.add(RadioButton_all);
        buttonGroupFiltre.add(RadioButton_client);
        buttonGroupFiltre.add(RadioButton_passager);
        buttonGroupFiltre.add(RadioButton_four);

        buttonGroupFiltrePaid.add(RadioButton_all_payment);
        buttonGroupFiltrePaid.add(RadioButton_notPaid);
        buttonGroupFiltrePaid.add(RadioButton_paid);
        buttonGroupFiltrePaid.add(RadioButton_semiPaid);
        typeReglemenntComboBox();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        //update_table();
        //1  SearchDevis();
        RadioButton_all.setSelected(true);
    }

    public void typeReglemenntComboBox() {
        ArticleDao article = new ArticleDao();
        article.listTypeReglement(ComboBox_type_reglement1);
    }

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

            /* rechercheDao.afficherAvoir(DevisHist_Table, FromDate,
                    ToDate, txt_montant_from.getText(), txt_montant_to.getText(), txt_num_devis.getText(),
                    txt_search.getText(), type_filtre);*/
            try {
                if (CheckBoxArticle.isSelected()) {
                    id_commercial = "";
                }
                if (txt_searchArticle.getText().isEmpty()) {
                    id_commercial = "";
                }
                String mnt_from = "";
                mnt_from = txt_montant_from.getText();
                String mnt_to = "";
                mnt_to = txt_montant_to.getText();

                String id_client = "";
                if (CheckBoxClients.isSelected()) {
                    id_client = "";
                } else {
                    id_client = txt_search.getText();
                }
                String type_reglement = ComboBox_type_reglement1.getSelectedItem().toString();

                rechercheDao.afficherAvoir(DevisHist_Table, FromDate,
                        ToDate, mnt_from, mnt_to, id_commercial,
                        id_client, type_filtre, type_filtre_paid, type_reglement);
                //   setHeaders();
            } catch (NullPointerException e) {

            }

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

    void setHeaders() {
        DevisHist_Table.getColumnModel().getColumn(0).setHeaderValue("id");
        DevisHist_Table.getColumnModel().getColumn(1).setHeaderValue("Client");
        DevisHist_Table.getColumnModel().getColumn(2).setHeaderValue("Fournisseur");
        DevisHist_Table.getColumnModel().getColumn(3).setHeaderValue("Commercial");

        DevisHist_Table.getColumnModel().getColumn(4).setHeaderValue("Num Facture");

        DevisHist_Table.getColumnModel().getColumn(5).setHeaderValue("Date Reglement");
        DevisHist_Table.getColumnModel().getColumn(6).setHeaderValue("Date Echéance");
        DevisHist_Table.getColumnModel().getColumn(7).setHeaderValue("Type Réglement");
        DevisHist_Table.getColumnModel().getColumn(8).setHeaderValue("Montant Relgé");
        DevisHist_Table.getColumnModel().getColumn(9).setHeaderValue("Total TTC");
        DevisHist_Table.getColumnModel().getColumn(10).setHeaderValue("Montant Restant");
        DevisHist_Table.getColumnModel().getColumn(11).setHeaderValue("Banque");
        DevisHist_Table.getColumnModel().getColumn(12).setHeaderValue("Num Cheque");
        DevisHist_Table.getColumnModel().getColumn(13).setHeaderValue("Passager");

        DevisHist_Table.getTableHeader().resizeAndRepaint();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupFiltre = new javax.swing.ButtonGroup();
        buttonGroupFiltrePaid = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DevisHist_Table = new javax.swing.JTable();
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
        RadioButton_four = new javax.swing.JRadioButton();
        RadioButton_passager = new javax.swing.JRadioButton();
        RadioButton_client = new javax.swing.JRadioButton();
        RadioButton_all = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        RadioButton_semiPaid = new javax.swing.JRadioButton();
        RadioButton_paid = new javax.swing.JRadioButton();
        RadioButton_notPaid = new javax.swing.JRadioButton();
        RadioButton_all_payment = new javax.swing.JRadioButton();
        CheckBoxClients = new javax.swing.JCheckBox();
        ComboBox_type_reglement1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        CheckBoxArticle = new javax.swing.JCheckBox();
        jButton8 = new javax.swing.JButton();
        txt_searchArticle = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        Annuler = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consultation Réglement");

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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1704, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtre"));

        jLabel2.setText("Date Réglement");

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

        jLabel4.setText("Num Reglement");

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

        RadioButton_four.setText("Fournisseur");
        RadioButton_four.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioButton_fourActionPerformed(evt);
            }
        });

        RadioButton_passager.setText("Passager");
        RadioButton_passager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioButton_passagerActionPerformed(evt);
            }
        });

        RadioButton_client.setText("Client");
        RadioButton_client.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioButton_clientActionPerformed(evt);
            }
        });

        RadioButton_all.setText("Tous");
        RadioButton_all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioButton_allActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RadioButton_four)
                    .addComponent(RadioButton_passager)
                    .addComponent(RadioButton_client)
                    .addComponent(RadioButton_all))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RadioButton_all)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RadioButton_client)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RadioButton_passager)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RadioButton_four)
                .addContainerGap())
        );

        RadioButton_semiPaid.setText("Semi Payé");
        RadioButton_semiPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioButton_semiPaidActionPerformed(evt);
            }
        });

        RadioButton_paid.setText("Payé");
        RadioButton_paid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioButton_paidActionPerformed(evt);
            }
        });

        RadioButton_notPaid.setText("Non Payé");
        RadioButton_notPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioButton_notPaidActionPerformed(evt);
            }
        });

        RadioButton_all_payment.setText("Tous");
        RadioButton_all_payment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioButton_all_paymentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RadioButton_all_payment)
                    .addComponent(RadioButton_paid)
                    .addComponent(RadioButton_semiPaid)
                    .addComponent(RadioButton_notPaid))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(RadioButton_all_payment)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RadioButton_paid)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RadioButton_semiPaid))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(RadioButton_notPaid)))
                .addContainerGap())
        );

        CheckBoxClients.setText("Tous les clients");

        ComboBox_type_reglement1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Espece", "Cheque", "Effet", "Virement", "Avoir", "Ristourne TVA", "Ristourne", "Retenue à la source" }));

        jLabel8.setText("Type réglement");

        CheckBoxArticle.setText("Tous les Commercials");

        jButton8.setText("Liste");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
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

        jLabel5.setText("Commercial");

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(207, 207, 207)
                                .addComponent(txt_montant_to, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(txt_search))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_searchArticle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CheckBoxArticle))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ComboBox_type_reglement1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Date_Devis, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                                    .addComponent(txt_num_devis))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Date_Devis_To, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_montant_from, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CheckBoxClients)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_num_devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ComboBox_type_reglement1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_searchArticle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton8)
                                        .addComponent(CheckBoxArticle))
                                    .addComponent(jLabel5))))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(Date_Devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Date_Devis_To, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txt_montant_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_montant_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton7)
                    .addComponent(CheckBoxClients))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        Annuler.setText("Annuler");
        Annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnulerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Annuler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Annuler)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(182, Short.MAX_VALUE))
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
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DevisHist_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DevisHist_TableMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_DevisHist_TableMouseClicked
    private void setHeadersDetail() {
        /* DevisHistDetail_Table.getColumnModel().getColumn(0).setHeaderValue("id");
        DevisHistDetail_Table.getColumnModel().getColumn(1).setHeaderValue("Num Devis");
        DevisHistDetail_Table.getColumnModel().getColumn(2).setHeaderValue("Référence");
        DevisHistDetail_Table.getColumnModel().getColumn(3).setHeaderValue("Designation");
        DevisHistDetail_Table.getColumnModel().getColumn(4).setHeaderValue("Quantité");
        DevisHistDetail_Table.getColumnModel().getColumn(5).setHeaderValue("Prix Unit HT");
        DevisHistDetail_Table.getColumnModel().getColumn(6).setHeaderValue("Remise %");
        DevisHistDetail_Table.getColumnModel().getColumn(7).setHeaderValue("TVA %");
        DevisHistDetail_Table.getColumnModel().getColumn(8).setHeaderValue("Totale HT");
        DevisHistDetail_Table.getColumnModel().getColumn(9).setHeaderValue("Totale TTC");
        DevisHistDetail_Table.getTableHeader().resizeAndRepaint();*/
    }
    private void txt_montant_fromKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montant_fromKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchDevis();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_montant_fromKeyPressed

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

    private void txt_num_devisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_num_devisKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchDevis();
        }
    }//GEN-LAST:event_txt_num_devisKeyPressed

    private void txt_montant_toKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montant_toKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchDevis();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_montant_toKeyPressed

    private void txt_montant_toKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montant_toKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_montant_toKeyReleased
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        SearchDevis();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void DevisHist_TableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DevisHist_TableMousePressed
        // TODO add your handling code here:
        try {
            /* BLDao devisDao = new BLDao();
            FormBL fr = new FormBL("", "", "");
            int row = DevisHist_Table.getSelectedRow();
            String table_click = DevisHist_Table.getModel().getValueAt(row, 1).toString();
            devisDao.afficherDetailDevis(DevisHistDetail_Table, table_click);

            ClientDao clientDao = new ClientDao();
            txt_num_devis.setText(table_click);
            listNomSte = clientDao.afficherClient();
            setHeadersDetail();*/
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
    String type_filtre = "all";
    String type_filtre_paid = "all";

    private void RadioButton_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioButton_allActionPerformed
        // TODO add your handling code here:
        if (RadioButton_all.isSelected()) {
            type_filtre = "all";
            txt_num_devis.setText("");
            SearchDevis();
        }
    }//GEN-LAST:event_RadioButton_allActionPerformed

    private void RadioButton_clientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioButton_clientActionPerformed
        if (RadioButton_client.isSelected()) {
            type_filtre = "client";
            txt_num_devis.setText("");
            SearchDevis();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_RadioButton_clientActionPerformed

    private void RadioButton_passagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioButton_passagerActionPerformed
        // TODO add your handling code here:
        if (RadioButton_passager.isSelected()) {
            type_filtre = "passager";
            txt_num_devis.setText("");
            SearchDevis();
        }
    }//GEN-LAST:event_RadioButton_passagerActionPerformed

    private void RadioButton_fourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioButton_fourActionPerformed
        // TODO add your handling code here:
        if (RadioButton_four.isSelected()) {
            type_filtre = "four";
            txt_num_devis.setText("");
            SearchDevis();
        }
    }//GEN-LAST:event_RadioButton_fourActionPerformed

    private void RadioButton_notPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioButton_notPaidActionPerformed
        // TODO add your handling code here:

        if (RadioButton_notPaid.isSelected()) {
            type_filtre_paid = "Non";
            txt_num_devis.setText("");
            SearchDevis();
        }
    }//GEN-LAST:event_RadioButton_notPaidActionPerformed

    private void RadioButton_semiPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioButton_semiPaidActionPerformed
        // TODO add your handling code here:
        if (RadioButton_semiPaid.isSelected()) {
            type_filtre_paid = "Semi";
            txt_num_devis.setText("");
            SearchDevis();
        }
    }//GEN-LAST:event_RadioButton_semiPaidActionPerformed

    private void RadioButton_paidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioButton_paidActionPerformed
        // TODO add your handling code here:
        if (RadioButton_paid.isSelected()) {
            type_filtre_paid = "Oui";
            txt_num_devis.setText("");
            SearchDevis();
        }
    }//GEN-LAST:event_RadioButton_paidActionPerformed

    private void RadioButton_all_paymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioButton_all_paymentActionPerformed
        // TODO add your handling code here:
        if (RadioButton_all_payment.isSelected()) {
            type_filtre_paid = "all";
            txt_num_devis.setText("");
            SearchDevis();
        }
    }//GEN-LAST:event_RadioButton_all_paymentActionPerformed

    private void AnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerActionPerformed

        if (JOptionPane.showConfirmDialog(null, "Voulez vous vraimenet annuler ", "Confirmation !!!",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            ReglementDAO regd = new ReglementDAO();
            try {
                String id = "";
                id = DevisHist_Table.getModel().getValueAt(DevisHist_Table.getSelectedRow(), 0).toString();
                if (!id.isEmpty()) {
                    regd.supprimerReglement(id);

                    JOptionPane.showMessageDialog(null, "Annulation avec Succès !");
                    SearchDevis();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error in reglement/Annuler : \n" + e);

            }

        }
    }//GEN-LAST:event_AnnulerActionPerformed
    JButton jbtValiderarticle;
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        Vector<String> columnNames = new Vector<String>();
        columnNames.add("Id");
        columnNames.add("Nom");
        columnNames.add("Adresse");

        ClientDao clientDao = new ClientDao();
        Vector<Vector<Object>> data1 = null;//clientDao.afficherListClient();
        CommercialDao articleDao = new CommercialDao();
        data1 = articleDao.afficherListeCommercial();
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

                /*  ArticleDao articleDao = new ArticleDao();
                Vector<Vector<Object>> data1 = articleDao.afficherListeArticle(jtxtRecherchearticle.getText(), "");

                DefaultTableModel model = new DefaultTableModel(data1, columnNames);
                jTable.setModel(model);*/
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
                id_commercial = jTable.getModel().getValueAt(jTable.getSelectedRow(), 0).toString();

                txt_searchArticle.setText(nomclient);

                /*   String s = jTable.getModel().getValueAt(jTable.getSelectedRow(), 2).toString();
                ArticleDao d = new ArticleDao();
                String design = s;
                String tva = d.getNameItemById("article", "tva", "ref", txt_searchArticle.getText());
                String pu = jTable.getModel().getValueAt(jTable.getSelectedRow(), 3).toString();
                 */
                String ref = nomclient;
                // String remise = d.getRemiseById(id_client, ref);
                /*   txt_remise.setText(remise.isEmpty() ? "" : remise);

                txt_design_article.setText(design);
                txt_TVA.setText(tva);
                txt_Prix_U.setText(pu);*/

                frameListeClient.dispose();
            }
        });
    }//GEN-LAST:event_jButton8ActionPerformed
    String id_commercial = "";
    private void txt_searchArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchArticleActionPerformed

    }//GEN-LAST:event_txt_searchArticleActionPerformed

    private void txt_searchArticleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchArticleKeyPressed
        // TODO add your handling code here:

        if (evt.getKeyCode() == evt.VK_ENTER) {
            // setFieldsByName(txt_searchArticle.getText());
            ArticleDao d = new ArticleDao();
            String design = d.getNameItemById("article", "designation", "ref", txt_searchArticle.getText());
            String tva = d.getNameItemById("article", "tva", "ref", txt_searchArticle.getText());

            /*txt_design_article.setText(design);
            txt_TVA.setText(tva);*/
        }
    }//GEN-LAST:event_txt_searchArticleKeyPressed

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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            DataBase_connect obj = new DataBase_connect();

            File myObj = new File(Commen_Proc.PathExcel + "Liste Reglement" + ".xls");
            String error = Config.ExpotToExcel.exportExcelStat(DevisHist_Table, myObj, "Liste Reglement");

            HashMap<String, Object> h = new HashMap<>();
            DefaultTableModel model = (DefaultTableModel) DevisHist_Table.getModel();
            h.put("table_title", "Liste des Règlements");

            h.put("type_reglement", ComboBox_type_reglement1.getSelectedItem().toString());
            String FromDate = "";
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

            h.put("FromDate", FromDate);

            String ToDate = "";
            try {
                String df = Date_Devis_To.getDate().toString();
                if (df.isEmpty() & Date_Devis_To.getDate().toString().isEmpty()) {
                } else {

                    Date From = Date_Devis_To.getDate();
                    SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                    ToDate = tdate.format(From);
                }

            } catch (Exception e) {
                Date From = new Date();
                SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                ToDate = tdate.format(From);
                System.out.println("date Null");
            }
            h.put("ToDate", ToDate);

            JasperReport jr = JasperCompileManager.compileReport(
                    ClassLoader.getSystemResourceAsStream("Reglement/ConsultationReglemet.jrxml"));

            JasperPrint jp = JasperFillManager.fillReport(jr, h, new JRTableModelDataSource(model));

            JasperExportManager.exportReportToPdfFile(jp, Commen_Proc.PathPDF + "Liste Reglement.pdf");

            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed
    DefaultListModel listModel = new DefaultListModel();


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Annuler;
    private javax.swing.JCheckBox CheckBoxArticle;
    private javax.swing.JCheckBox CheckBoxClients;
    private javax.swing.JComboBox<String> ComboBox_type_reglement1;
    private com.toedter.calendar.JDateChooser Date_Devis;
    private com.toedter.calendar.JDateChooser Date_Devis_To;
    private javax.swing.JTable DevisHist_Table;
    private javax.swing.JRadioButton RadioButton_all;
    private javax.swing.JRadioButton RadioButton_all_payment;
    private javax.swing.JRadioButton RadioButton_client;
    private javax.swing.JRadioButton RadioButton_four;
    private javax.swing.JRadioButton RadioButton_notPaid;
    private javax.swing.JRadioButton RadioButton_paid;
    private javax.swing.JRadioButton RadioButton_passager;
    private javax.swing.JRadioButton RadioButton_semiPaid;
    private javax.swing.ButtonGroup buttonGroupFiltre;
    private javax.swing.ButtonGroup buttonGroupFiltrePaid;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_montant_from;
    private javax.swing.JTextField txt_montant_to;
    private javax.swing.JTextField txt_num_devis;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_searchArticle;
    // End of variables declaration//GEN-END:variables
}
