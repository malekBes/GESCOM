/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stat;

import Recherche.*;
import BL.*;
import Devis.*;
import Article.ArticleDao;
import Client.ClientDao;
import Config.Commen_Proc;
import Conn.DataBase_connect;
import Home.App;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.*;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import java.util.Map;

import java.util.Collections;
import java.util.HashMap;

import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.components.ComponentsExtensionsRegistryFactory;
import net.sf.jasperreports.components.table.DesignCell;
import net.sf.jasperreports.components.table.StandardColumn;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignDatasetRun;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Mlek
 */
public class StatVenteParArticleParPeridoForm extends javax.swing.JInternalFrame {

    int i = 0;
    ArrayList<String> listNomSte;
    ArrayList<String> listNomArticle;
    Vector<String> columnNames_BL_Table;
    Vector<Vector<Object>> data_BL_Table;
    JButton jbtValider;
    JFrame frameListeClient;
    String nomclient;
    String id_marque = "";
    DefaultTableModel df;
    String id_client = "";
    RechercheDAO rechercheDao = new RechercheDAO();
    Double D_Total_HT = 0.0;
    Double D_Total_remise = 0.0;
    Double D_montant_TVA = 0.0;
    Double D_Total_Net = 0.0;
    private String year;

    /**
     * Creates new form form
     */
    public StatVenteParArticleParPeridoForm() {
        year = Commen_Proc.YearVal;
        initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        //  setTableHeader();
        setHeaders();
        txt_search.setVisible(false);
        jButton7.setVisible(false);
        CheckBoxClient.setVisible(false);
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
        // jButton4.setVisible(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        //update_table();
        //SearchDevis();

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
                Logger.getLogger(StatVenteParArticleParPeridoForm.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel3 = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        txt_searchArticle = new javax.swing.JTextField();
        CheckBoxClient = new javax.swing.JCheckBox();
        CheckBoxArticle = new javax.swing.JCheckBox();
        Date_Devis_To6 = new com.toedter.calendar.JDateChooser();
        Date_DevisFrom6 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        Date_Devis_To_3 = new com.toedter.calendar.JDateChooser();
        Date_DevisFrom3 = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Date_Devis_To1 = new com.toedter.calendar.JDateChooser();
        Date_DevisFrom1 = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DevisHist_Table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txt_searchTable = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Statistiques Vente par article Par Période");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtre"));

        jLabel2.setText("Période 1 an");

        Date_Devis.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                Date_DevisPropertyChange(evt);
            }
        });

        jLabel3.setText("Article");

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

        CheckBoxClient.setText("Tous les Clients");

        CheckBoxArticle.setText("Tous les Articles");

        Date_DevisFrom6.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                Date_DevisFrom6PropertyChange(evt);
            }
        });

        jLabel4.setText("Période 6 mois");

        Date_DevisFrom3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                Date_DevisFrom3PropertyChange(evt);
            }
        });

        jLabel6.setText("Période 3 mois");

        jLabel7.setText("Période 1 mois");

        Date_DevisFrom1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                Date_DevisFrom1PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(txt_search, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CheckBoxClient))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(44, 44, 44)
                                .addComponent(txt_searchArticle, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Date_Devis, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                    .addComponent(Date_DevisFrom6, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                    .addComponent(Date_DevisFrom3, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                    .addComponent(Date_DevisFrom1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Date_Devis_To, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Date_Devis_To6, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Date_Devis_To_3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Date_Devis_To1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CheckBoxArticle)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(Date_Devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Date_Devis_To, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(Date_DevisFrom6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Date_Devis_To6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(Date_DevisFrom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Date_Devis_To_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(Date_DevisFrom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Date_Devis_To1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_searchArticle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton8)
                        .addComponent(CheckBoxArticle))
                    .addComponent(jLabel3))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7)
                    .addComponent(CheckBoxClient))
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("BL"));

        DevisHist_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
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

        jLabel1.setText("Recherche");

        txt_searchTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchTableActionPerformed(evt);
            }
        });
        txt_searchTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchTableKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 859, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txt_searchTable, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_searchTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
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
    String FromDate1Year = "";
    String ToDate1Year = "";
    String FromDate6Month = "";
    String ToDate6Month = "";
    String FromDate3Month = "";
    String ToDate3Month = "";
    String FromDate1Month = "";
    String ToDate1Month = "";

    public PeriodDatesClass setDates() {
        PeriodDatesClass c = new PeriodDatesClass();
        try {
            String df = Date_Devis.getDate().toString();
            if (df.isEmpty() & Date_Devis.getDate().toString().isEmpty()) {
                c.setFromDate1Year("");
            } else {

                Date From = Date_Devis.getDate();
                SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                FromDate1Year = tdate.format(From);
                c.setFromDate1Year(FromDate1Year);
            }

        } catch (Exception e) {
            System.out.println("date Null");
        }

        try {
            String dt = Date_Devis_To.getDate().toString();
            if (dt.isEmpty() & Date_Devis_To.getDate().toString().isEmpty()) {
                c.setToDate1Year("");
            } else {

                Date From = Date_Devis_To.getDate();
                SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                ToDate1Year = tdate.format(From);
                c.setToDate1Year(ToDate1Year);
            }

        } catch (Exception e) {
            System.out.println("date Null");
        }
        //----------------------------------------------------------------
        try {
            String df = Date_DevisFrom6.getDate().toString();
            if (df.isEmpty() & Date_DevisFrom6.getDate().toString().isEmpty()) {
                c.setFromDate6Month(FromDate6Month);
            } else {

                Date From = Date_DevisFrom6.getDate();
                SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                FromDate6Month = tdate.format(From);
                c.setFromDate6Month(FromDate6Month);
            }

        } catch (Exception e) {
            System.out.println("date Null");
        }

        try {
            String dt = Date_Devis_To6.getDate().toString();
            if (dt.isEmpty() & Date_Devis_To6.getDate().toString().isEmpty()) {
                c.setToDate6Month(ToDate6Month);
            } else {

                Date From = Date_Devis_To6.getDate();
                SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                ToDate6Month = tdate.format(From);
                c.setToDate6Month(ToDate6Month);
            }

        } catch (Exception e) {
            System.out.println("date Null");
        }
        //----------------------------------------------------------------
        try {
            String df = Date_DevisFrom3.getDate().toString();
            if (df.isEmpty() & Date_DevisFrom3.getDate().toString().isEmpty()) {
                c.setFromDate3Month(FromDate3Month);
            } else {

                Date From = Date_DevisFrom3.getDate();
                SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                FromDate3Month = tdate.format(From);
                c.setFromDate3Month(FromDate3Month);
            }

        } catch (Exception e) {
            System.out.println("date Null");
        }

        try {
            String dt = Date_Devis_To_3.getDate().toString();
            if (dt.isEmpty() & Date_Devis_To_3.getDate().toString().isEmpty()) {
                c.setToDate3Month(ToDate3Month);
            } else {

                Date From = Date_Devis_To_3.getDate();
                SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                ToDate3Month = tdate.format(From);
                c.setToDate3Month(ToDate3Month);
            }

        } catch (Exception e) {
            System.out.println("date Null");
        }
        //----------------------------------------------------------------
        try {
            String df = Date_DevisFrom1.getDate().toString();
            if (df.isEmpty() & Date_DevisFrom1.getDate().toString().isEmpty()) {
                c.setFromDate1Month(FromDate1Month);
            } else {

                Date From = Date_DevisFrom1.getDate();
                SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                FromDate1Month = tdate.format(From);
                c.setFromDate1Month(FromDate1Month);
            }

        } catch (Exception e) {
            System.out.println("date Null");
        }

        try {
            String dt = Date_Devis_To1.getDate().toString();
            if (dt.isEmpty() & Date_Devis_To1.getDate().toString().isEmpty()) {
                c.setToDate1Month(ToDate1Month);
            } else {

                Date From = Date_Devis_To1.getDate();
                SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                ToDate1Month = tdate.format(From);
                c.setToDate1Month(ToDate1Month);
            }

        } catch (Exception e) {
            System.out.println("date Null");
        }
        return c;
    }

    public String sortMaxDates(PeriodDatesClass Dates) {
        List<Date> d = new ArrayList<Date>();

        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmt.parse(Dates.getFromDate1Month());
            Date dateOne = fmt.parse(Dates.getFromDate3Month());
            Date dateTwo = fmt.parse(Dates.getFromDate6Month());
            Date datefour = fmt.parse(Dates.getFromDate1Year());

            d.add(date);
            d.add(dateOne);
            d.add(dateTwo);
            d.add(datefour);

            // Using java.util.Collections
            //   System.out.println("Maximum Element : " + fmt.format(Collections.max(d)));
            // // Using Sort Technique of java.util.Collections
            Collections.sort(d);
            //   System.out.println("Maximum Element After Sorting - " + fmt.format(d.get(d.size() - 1)));
            return fmt.format(Collections.min(d));
        } catch (Exception e) {
        }
        return "";
    }

    public String sortMinDates(PeriodDatesClass Dates) {
        List<Date> d = new ArrayList<Date>();

        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmt.parse(Dates.getToDate1Month());
            Date dateOne = fmt.parse(Dates.getToDate3Month());
            Date dateTwo = fmt.parse(Dates.getToDate6Month());
            Date datefour = fmt.parse(Dates.getToDate1Year());

            d.add(date);
            d.add(dateOne);
            d.add(dateTwo);
            d.add(datefour);

            // Using java.util.Collections
            //   System.out.println("Min Element : " + fmt.format(Collections.min(d)));
            // // Using Sort Technique of java.util.Collections
            Collections.sort(d);
            //  System.out.println("Min Element After Sorting - " + fmt.format(d.get(d.size() - 1)));
            return fmt.format(Collections.max(d));
        } catch (Exception e) {
        }
        return "";
    }

    public void SearchDevis() {
        try {
            PeriodDatesClass Dates = setDates();
            String MaxDate = sortMaxDates(Dates);
            String MinDate = sortMinDates(Dates);

            Dates.setMaxDate(MaxDate);
            Dates.setMinDate(MinDate);
            String marque = "";

            String ref = txt_searchArticle.getText();
            if (CheckBoxArticle.isSelected()) {
                ref = "";
            }

            String client = "";

            client = "";

            rechercheDao.afficherBLStatParArticleParPeriod(DevisHist_Table, Dates, marque, ref,
                    client);
            // setHeaders();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in HistoriqueBLFrom/SearchBL : \n" + e);
        } finally {
            try {

                System.out.println("disconnected");
            } catch (Exception ex) {
                Logger.getLogger(StatVenteParArticleParPeridoForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void setHeaders() {

        DevisHist_Table.getColumnModel().getColumn(0).setHeaderValue("Ref Article");
        DevisHist_Table.getColumnModel().getColumn(1).setHeaderValue("Designation");

        DevisHist_Table.getColumnModel().getColumn(2).setHeaderValue("Quantité");

        DevisHist_Table.getTableHeader().resizeAndRepaint();

    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        SearchDevis();
        // JOptionPane.showMessageDialog(this, "En cours de développement ");

    }//GEN-LAST:event_jButton1ActionPerformed
    public String tab_DevisHist;
    private void DevisHist_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DevisHist_TableMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_DevisHist_TableMouseClicked
    private void setHeadersDetail() {
        /*    DevisHistDetail_Table.getColumnModel().getColumn(0).setHeaderValue("id");
        DevisHistDetail_Table.getColumnModel().getColumn(1).setHeaderValue("Num BL");
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
                id_client = jTable.getModel().getValueAt(jTable.getSelectedRow(), 0).toString();
                frameListeClient.dispose();
            }
        });
    }//GEN-LAST:event_jButton7ActionPerformed

    private void DevisHist_TableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DevisHist_TableMousePressed
        // TODO add your handling code here:
        /*  try {
            BLDao devisDao = new BLDao();
            FormBL fr = new FormBL("", "", "");
            int row = DevisHist_Table.getSelectedRow();
            String table_click = DevisHist_Table.getModel().getValueAt(row, 1).toString();
            devisDao.afficherDetailBL(DevisHistDetail_Table, table_click);

            ClientDao clientDao = new ClientDao();
//            txt_num_devis.setText(table_click);
            listNomSte = clientDao.afficherClient();
            setHeadersDetail();
            //client_table.setModel(buildTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in ClientFrom/load_Update_table :  " + e);
        } finally {
            try {

                System.out.println("disconnected");
            } catch (Exception ex) {
                Logger.getLogger(StatAchatFournisseurForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }//GEN-LAST:event_DevisHist_TableMousePressed
    JButton jbtValiderarticle;
    JButton jbtAjouterarticle;
    JButton jbRecherchearticle;
    JTextField jtxtRecherchearticle;
    JTextField jtxtListeArticle;
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        Vector<String> columnNames = new Vector<String>();
        columnNames.add("id");
        columnNames.add("ref");
        columnNames.add("designation");
        columnNames.add("Prix Unitaire");
        columnNames.add("Qte");
        columnNames.add("Marge");
        columnNames.add("Réservé");

        ClientDao clientDao = new ClientDao();
        Vector<Vector<Object>> data1 = null;// clientDao.afficherListClient();
        ArticleDao articleDao = new ArticleDao();
        //   data1 = articleDao.afficherListeArticle();
        DefaultTableModel model = new DefaultTableModel(data1, columnNames);
        JTable jTable = new JTable(model);

        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(jTable.getModel());

        JTextField jtfFilter = new JTextField();

        jbtValiderarticle = new JButton("Valider la liste");
        jbtAjouterarticle = new JButton("Ajouter a la liste");
        jbRecherchearticle = new JButton("Recherche");
        jtxtRecherchearticle = new JTextField(20);
        jtxtListeArticle = new JTextField(40);
        jtxtListeArticle.setEditable(false);
//jTable.setRowSorter(rowSorter);
        JPanel Homepanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelbtn = new JPanel(new FlowLayout());

        panelbtn.add(jbtValiderarticle, BorderLayout.SOUTH);
        panelbtn.add(jbtAjouterarticle, BorderLayout.SOUTH);
        panelbtn.add(jbRecherchearticle, BorderLayout.NORTH);
        panelbtn.add(jtxtRecherchearticle, BorderLayout.NORTH);
        panelbtn.add(jtxtListeArticle, BorderLayout.NORTH);

        panel.add(panelbtn, BorderLayout.SOUTH);

        jbtAjouterarticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                String ref = jTable.getModel().getValueAt(jTable.getSelectedRow(), 1).toString();
                jtxtListeArticle.setText(jtxtListeArticle.getText() + "" + ref + ",");
            }
        });

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
                // nomclient = jTable.getModel().getValueAt(jTable.getSelectedRow(), 1).toString();
                String str = jtxtListeArticle.getText().substring(0, jtxtListeArticle.getText().length() - 1);
                txt_searchArticle.setText(str);

                /*   String s = jTable.getModel().getValueAt(jTable.getSelectedRow(), 2).toString();
                ArticleDao d = new ArticleDao();
                String design = s;
                String tva = d.getNameItemById("article", "tva", "ref", txt_searchArticle.getText());
                String pu = jTable.getModel().getValueAt(jTable.getSelectedRow(), 3).toString();
                 */
                // String remise = d.getRemiseById(id_client, ref);
                /*   txt_remise.setText(remise.isEmpty() ? "" : remise);

                txt_design_article.setText(design);
                txt_TVA.setText(tva);
                txt_Prix_U.setText(pu);*/
                frameListeClient.dispose();
            }
        });
    }//GEN-LAST:event_jButton8ActionPerformed

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

   /*     if (evt.getKeyCode() == evt.VK_BACK_SPACE || evt.getKeyCode() == evt.VK_DELETE) {

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
        }*/
    }//GEN-LAST:event_txt_searchArticleKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        try {
            /*    MessageFormat header = new MessageFormat("Chiffre d'affaire par client");
            MessageFormat footer = new MessageFormat("{0,number,integer}");
            DevisHist_Table.print(JTable.PrintMode.FIT_WIDTH, header, footer);*/

            DataBase_connect obj = new DataBase_connect();

            HashMap<String, Object> h = new HashMap<>();

            DefaultTableModel model = (DefaultTableModel) DevisHist_Table.getModel();
            h.put("table_title", "Stat Vente Par Article Par Période");
            h.put("val_1_m", FromDate1Month + " - " + ToDate1Month);
            h.put("val_3_m", FromDate3Month + " - " + ToDate3Month);
            h.put("val_6_m", FromDate6Month + " - " + ToDate6Month);
            h.put("val_1_a", FromDate1Year + " - " + ToDate1Year);
            Object LigneData[] = new Object[4];
            LigneData[0] = FromDate1Year + " - " + ToDate1Year;
            LigneData[1] = FromDate6Month + " - " + ToDate6Month;
            LigneData[2] = FromDate3Month + " - " + ToDate3Month;
            LigneData[3] = FromDate1Month + " - " + ToDate1Month;

            JasperReport jr = JasperCompileManager.compileReport(
                    ClassLoader.getSystemResourceAsStream("statPrint/StatVenteParPeriod.jrxml"));

            JasperPrint jp = JasperFillManager.fillReport(jr, h, new JRTableModelDataSource(model));

            JasperExportManager.exportReportToPdfFile(jp, Commen_Proc.PathPDF + "Stat Vente Par Article Par Période.pdf");

            File myObj = new File(Commen_Proc.PathExcel + "Stat Vente Par Article Par Période" + ".xls");
            Config.ExpotToExcel.exportExcelStatparPeriod(DevisHist_Table, myObj, "Stat Vente Par Article Par Période", LigneData);
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void Date_DevisPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_Date_DevisPropertyChange
        // TODO add your handling code here:
        try {
            String df = Date_Devis.getDate().toString();
            if (df.isEmpty() & Date_Devis.getDate().toString().isEmpty()) {
            } else {

                Date From = Date_Devis.getDate();
                SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                //  FromDate1Year = tdate.format(From);
                //  JOptionPane.showMessageDialog(this, FromDate1Year);
                Calendar cal = Calendar.getInstance();
                cal.setTime(From);
                cal.add(Calendar.YEAR, 1); // to get previous year add -1
                cal.add(Calendar.DAY_OF_MONTH, -1);
                Date nextYear = cal.getTime();
                Date_Devis_To.setDate(nextYear);

            }

        } catch (Exception e) {
            System.out.println("date Null");
        }
    }//GEN-LAST:event_Date_DevisPropertyChange

    private void Date_DevisFrom6PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_Date_DevisFrom6PropertyChange
        // TODO add your handling code here:
        try {
            String df = Date_DevisFrom6.getDate().toString();
            if (df.isEmpty() & Date_DevisFrom6.getDate().toString().isEmpty()) {
            } else {

                Date From = Date_DevisFrom6.getDate();
                SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                //  FromDate1Year = tdate.format(From);
                //  JOptionPane.showMessageDialog(this, FromDate1Year);
                Calendar cal = Calendar.getInstance();
                cal.setTime(From);
                cal.add(Calendar.MONTH, 6); // to get previous year add -1
                cal.add(Calendar.DAY_OF_MONTH, -1);
                Date nextYear = cal.getTime();
                Date_Devis_To6.setDate(nextYear);

            }

        } catch (Exception e) {
            System.out.println("date Null");
        }
    }//GEN-LAST:event_Date_DevisFrom6PropertyChange

    private void Date_DevisFrom3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_Date_DevisFrom3PropertyChange
        // TODO add your handling code here:
        try {
            String df = Date_DevisFrom3.getDate().toString();
            if (df.isEmpty() & Date_DevisFrom6.getDate().toString().isEmpty()) {
            } else {

                Date From = Date_DevisFrom3.getDate();
                SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                //  FromDate1Year = tdate.format(From);
                //  JOptionPane.showMessageDialog(this, FromDate1Year);
                Calendar cal = Calendar.getInstance();
                cal.setTime(From);
                cal.add(Calendar.MONTH, 3); // to get previous year add -1
                cal.add(Calendar.DAY_OF_MONTH, -1);
                Date nextYear = cal.getTime();
                Date_Devis_To_3.setDate(nextYear);

            }

        } catch (Exception e) {
            System.out.println("date Null");
        }
    }//GEN-LAST:event_Date_DevisFrom3PropertyChange

    private void Date_DevisFrom1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_Date_DevisFrom1PropertyChange
        // TODO add your handling code here:
        try {
            String df = Date_DevisFrom1.getDate().toString();
            if (df.isEmpty() & Date_DevisFrom1.getDate().toString().isEmpty()) {
            } else {

                Date From = Date_DevisFrom1.getDate();
                SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                //    FromDate1Year = tdate.format(From);
                //  JOptionPane.showMessageDialog(this, FromDate1Year);
                Calendar cal = Calendar.getInstance();
                cal.setTime(From);
                cal.add(Calendar.MONTH, 1); // to get previous year add -1
                cal.add(Calendar.DAY_OF_MONTH, -1);
                Date nextYear = cal.getTime();
                Date_Devis_To1.setDate(nextYear);

            }

        } catch (Exception e) {
            System.out.println("date Null");
        }
    }//GEN-LAST:event_Date_DevisFrom1PropertyChange

    private void txt_searchTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchTableActionPerformed

    private void txt_searchTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchTableKeyReleased

        TableRowSorter sorter;
        DefaultTableModel df = (DefaultTableModel) DevisHist_Table.getModel();
        String s = txt_searchTable.getText();

        sorter = new TableRowSorter<>(df);
        DevisHist_Table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(s));
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchTableKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckBoxArticle;
    private javax.swing.JCheckBox CheckBoxClient;
    private com.toedter.calendar.JDateChooser Date_Devis;
    private com.toedter.calendar.JDateChooser Date_DevisFrom1;
    private com.toedter.calendar.JDateChooser Date_DevisFrom3;
    private com.toedter.calendar.JDateChooser Date_DevisFrom6;
    private com.toedter.calendar.JDateChooser Date_Devis_To;
    private com.toedter.calendar.JDateChooser Date_Devis_To1;
    private com.toedter.calendar.JDateChooser Date_Devis_To6;
    private com.toedter.calendar.JDateChooser Date_Devis_To_3;
    private javax.swing.JTable DevisHist_Table;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_searchArticle;
    private javax.swing.JTextField txt_searchTable;
    // End of variables declaration//GEN-END:variables

    private ArrayList<LigneBL> setLigneDevisFromDevisTable() {

        ArrayList<LigneBL> lst = new ArrayList();
        /*
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

        }*/
        return lst;
    }

    /* public void TestTableSortFilter() {
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(DevisHist_Table.getModel());

        DevisHist_Table.setRowSorter(rowSorter);

        txt_article.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = txt_article.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txt_article.getText();

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
    }*/
}
