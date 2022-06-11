/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alerts;

import Recherche.RechercheDAO;
import stat.*;
import Recherche.*;
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
import java.util.HashMap;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.Map;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
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
public class ArticleNonVenduForm extends javax.swing.JInternalFrame {

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
    public ArticleNonVenduForm() {
        year = Commen_Proc.YearVal;
         initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        //  setTableHeader();
        setHeaders();

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
                Logger.getLogger(ArticleNonVenduForm.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel1 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        txt_searchArticle = new javax.swing.JTextField();
        CheckBoxClient = new javax.swing.JCheckBox();
        CheckBoxArticle = new javax.swing.JCheckBox();
        CheckBoxMarque = new javax.swing.JCheckBox();
        jButton9 = new javax.swing.JButton();
        txt_search_Marque = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DevisHist_Table = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        DevisHist_Table1 = new javax.swing.JTable();

        setClosable(true);
        setResizable(true);
        setTitle("Articles Non Vendus / Dernière vente");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtre"));

        jLabel2.setText("Période");

        jLabel3.setText("Commercial");

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

        CheckBoxArticle.setText("Tous les Commercials");

        CheckBoxMarque.setText("Tous les Marques");

        jButton9.setText("Liste");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        txt_search_Marque.setBackground(new java.awt.Color(153, 204, 255));
        txt_search_Marque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_MarqueActionPerformed(evt);
            }
        });
        txt_search_Marque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_search_MarqueKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_MarqueKeyReleased(evt);
            }
        });

        jLabel4.setText("Marque");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(txt_search))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(txt_searchArticle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(txt_search_Marque))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(38, 38, 38)
                        .addComponent(Date_Devis, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Date_Devis_To, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CheckBoxClient))
                    .addComponent(CheckBoxArticle)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CheckBoxMarque)))
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
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_searchArticle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton8)
                        .addComponent(CheckBoxArticle))
                    .addComponent(jLabel3))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton7)
                    .addComponent(CheckBoxClient))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search_Marque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jButton9)
                    .addComponent(CheckBoxMarque))
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
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(0, 97, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Non Vendu"));

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Dernière vente"));

        DevisHist_Table1.setModel(new javax.swing.table.DefaultTableModel(
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
        DevisHist_Table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DevisHist_Table1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DevisHist_Table1MousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(DevisHist_Table1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(64, Short.MAX_VALUE))
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
    String id_commercial = "";

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

            String marque = id_marque;
            String commercial_id = "";
            commercial_id = id_commercial;
            String ref = "";
            ref = txt_searchArticle.getText();
            if (CheckBoxArticle.isSelected()) {
                // id_commercial = "";
                commercial_id = "";
            }
            if (ref.isEmpty()) {
                // id_commercial = "";
                commercial_id = "";

            }

            String client = "";
            if (CheckBoxClient.isSelected()) {
                client = "";
            } else {
                client = id_client;
            }
            // if (!id_client.isEmpty()) {

            rechercheDao.afficherBLStatNonVendu(DevisHist_Table, FromDate,
                    ToDate, marque, commercial_id,
                    client, "not in");

            sqlReturned = rechercheDao.afficherBLStatVendu(DevisHist_Table1, FromDate,
                    ToDate, marque, commercial_id,
                    client, "in");
            /*   } else {
                JOptionPane.showMessageDialog(null, "Champ Client est obligatoire !");

            }*/
            //setHeaders();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in HistoriqueBLFrom/SearchBL : \n" + e);
        } finally {
            try {

                System.out.println("disconnected");
            } catch (Exception ex) {
                Logger.getLogger(ArticleNonVenduForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    String sqlReturned = "";

    void setHeaders() {

        /*columnNames.add("Client");
            columnNames.add("Ref Article");
            columnNames.add("Derniére BL");
            columnNames.add("Activité");//nbjrs
            columnNames.add("Nb Jrs dés la Dernière Vente");// dés la Dernière Vente

            columnNames.add("Dépasse 45 jrs ? (O/N)");// ? (O/N)*/
        DevisHist_Table1.getColumnModel().getColumn(0).setHeaderValue("Client");
        DevisHist_Table1.getColumnModel().getColumn(1).setHeaderValue("Ref Article");
        DevisHist_Table1.getColumnModel().getColumn(2).setHeaderValue("Derniére BL");
        DevisHist_Table1.getColumnModel().getColumn(3).setHeaderValue("Activité");

        DevisHist_Table1.getColumnModel().getColumn(4).setHeaderValue("Nb Jrs dés la Dernière Vente");

        DevisHist_Table1.getColumnModel().getColumn(5).setHeaderValue("Dépasse 45 jrs ? (O/N)");
        DevisHist_Table1.getTableHeader().resizeAndRepaint();

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
    JButton jbRecherchearticle;
    JTextField jtxtRecherchearticle;
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
            printArticleDernierVente();
            printArticleNonVendu();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    public void printArticleDernierVente() {
        try {

            DataBase_connect obj = new DataBase_connect();

            HashMap<String, Object> h = new HashMap<>();
            DefaultTableModel model = (DefaultTableModel) DevisHist_Table1.getModel();
            h.put("table_title", "Article Dernier vente");

            h.put("query", sqlReturned);

            JasperReport jr = JasperCompileManager.compileReport(
                    ClassLoader.getSystemResourceAsStream("alerts/ArticleDernierVente.jrxml"));

            JasperPrint jp = JasperFillManager.fillReport(jr, h, new JRTableModelDataSource(model));

            JasperExportManager.exportReportToPdfFile(jp, Commen_Proc.PathPDF + "Article Dernier vente.pdf");

            File myObj = new File(Commen_Proc.PathExcel + "Article Dernier vente" + ".xls");
            Config.ExpotToExcel.exportExcelStat(DevisHist_Table1, myObj, "Article Dernier vente");

            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());

        }
    }

    public void printArticleNonVendu() {
        try {

            DataBase_connect obj = new DataBase_connect();

            HashMap<String, Object> h = new HashMap<>();
            DefaultTableModel model = (DefaultTableModel) DevisHist_Table.getModel();
            h.put("table_title", "Article Non Vendu");

            JasperReport jr = JasperCompileManager.compileReport(
                    ClassLoader.getSystemResourceAsStream("alerts/ArticleNonVendu.jrxml"));

            JasperPrint jp = JasperFillManager.fillReport(jr, h, new JRTableModelDataSource(model));

            JasperExportManager.exportReportToPdfFile(jp, Commen_Proc.PathPDF + "Article Non Vendu.pdf");

            File myObj = new File(Commen_Proc.PathExcel + "Article Non Vendu" + ".xls");
            Config.ExpotToExcel.exportExcelStat(DevisHist_Table, myObj, "Article Non Vendu");

            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());

        }
    }


    private void DevisHist_Table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DevisHist_Table1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_DevisHist_Table1MouseClicked

    private void DevisHist_Table1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DevisHist_Table1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DevisHist_Table1MousePressed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        /*  JFrame frame = new JFrame("Row Filter");
        frame.add(TestTableSortFilter());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);*/
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("id");
        columnNames.add("Marque");

        ClientDao clientDao = new ClientDao();
        Vector<Vector<Object>> data1 = clientDao.afficherListMarque();
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
                Vector<Vector<Object>> data1 = articleDao.afficherListMarqueByName(jtxtRecherchearticle.getText());

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

        frameListeClient = new JFrame("Liste Marques");
        frameListeClient.add(Homepanel);
        frameListeClient.pack();
        frameListeClient.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        frameListeClient.setLocationRelativeTo(null);
        frameListeClient.setVisible(true);
        jbtValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marque = jTable.getModel().getValueAt(jTable.getSelectedRow(), 1).toString();
                txt_search_Marque.setText(marque);
                id_marque = jTable.getModel().getValueAt(jTable.getSelectedRow(), 0).toString();
                frameListeClient.dispose();
            }
        });
    }//GEN-LAST:event_jButton9ActionPerformed
    String marque = "";


    private void txt_search_MarqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_MarqueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_MarqueActionPerformed

    private void txt_search_MarqueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_MarqueKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_MarqueKeyPressed

    private void txt_search_MarqueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_MarqueKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_MarqueKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckBoxArticle;
    private javax.swing.JCheckBox CheckBoxClient;
    private javax.swing.JCheckBox CheckBoxMarque;
    private com.toedter.calendar.JDateChooser Date_Devis;
    private com.toedter.calendar.JDateChooser Date_Devis_To;
    private javax.swing.JTable DevisHist_Table;
    private javax.swing.JTable DevisHist_Table1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_searchArticle;
    private javax.swing.JTextField txt_search_Marque;
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
