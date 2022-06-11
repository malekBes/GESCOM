/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Devis;

import Article.ArticleDao;
import Article.ArticleForm;
import BL.BLDao;
import BL.FormBL;
import BL.LigneBL;
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
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class FormDevis_old extends javax.swing.JInternalFrame {

    int i = 0;
    ArrayList<String> listNomSte;
    ArrayList<String> listNomArticle;
    Vector<String> columnNames_Devis_Table;
    Vector<Vector<Object>> data_Devis_Table;
    JButton jbtValider;

    String nomclient;
    JButton jbtValiderarticle;
    JButton jbRecherchearticle;
    JTextField jtxtRecherchearticle;
    String refarticle;
    JFrame frameListeClient;
    DefaultTableModel df;
    String id_client;
    DevisDao devisDao = new DevisDao();
    Double D_Total_HT = 0.0;
    Double D_Total_remise = 0.0;
    Double D_montant_TVA = 0.0;
    Double D_Total_Net = 0.0;
    private String year;

    /**
     * Creates new form form
     */
    String type_operation = "";

    public FormDevis_old(String LstNum_Devis, String type) {
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
        combo = "0";
        //CheckBox_timbre.setSelected(true);
        //curent date
        Date_Devis.setDate(new Date());
        Date_Devis.setEnabled(false);
        txt_design_article.setEnabled(false);
        //txt_timbre.setText(Commen_Proc.TimbreVal);
        txt_timbre.setEnabled(false);
        setNumDevis();
        numDevisAnnuleeComboBox();

        if (!type.isEmpty()) {
            if (!LstNum_Devis.isEmpty()) {
                df = new DefaultTableModel();
                setImportedDevis(LstNum_Devis);
                setImportedTableHeader();
                formatTableValues();

                type_operation = type;

                String Num_Devis = (LstNum_Devis.split(",")[0]).replaceAll("'", "");
                ArticleDao dart = new ArticleDao();

                if (type.equals("Modif")) {
                    //  Date_Devis.setEnabled(true);
                    txt_num_devis.setText(Num_Devis);
                    String date = dart.getDateByNumDevis(Num_Devis);
                    try {
                        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);

                        Date_Devis.setDate(date1);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage());
                    }
                }
                String nom_and_codeTva = dart.getNameItemByIdJoinTables("devis", "num_devis", Num_Devis);

                String[] array = nom_and_codeTva.split(";");
                id_client = array[0];
                txt_search.setText(array[1]);
                txt_Code_TVA.setText(array[2]);
                if (array[3].equals("1")) {
                    CheckBox_timbre.setSelected(true);
                } else {
                    CheckBox_timbre.setSelected(false);
                }

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
        type_operation = type;
    }
    HashMap<String, String> NumDevisHashMap;

    public void numDevisAnnuleeComboBox() {
        BLDao b = new BLDao();
        NumDevisHashMap = b.listDevis(ComboBoxDevis);
    }

    public void setTableHeader() {
        columnNames_Devis_Table = new Vector<String>();
        columnNames_Devis_Table.add("id");
        columnNames_Devis_Table.add("Référence");
        columnNames_Devis_Table.add("Designation");
        columnNames_Devis_Table.add("Prix Unit HT");
        columnNames_Devis_Table.add("Quantité");
        columnNames_Devis_Table.add("Totale HT");
        columnNames_Devis_Table.add("Remise %");
        columnNames_Devis_Table.add("TVA %");
        columnNames_Devis_Table.add("Totale TTC");
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

        df = devisDao.afficherDetailMultipleDevis(Devis_Table, Num_Devis, false);
        Devis_Table.setModel(df);
        reCalculerTT();

    }

    public void setImportedTableHeader() {

        columnNames_Devis_Table = new Vector<String>();
        columnNames_Devis_Table.add("Num");
        columnNames_Devis_Table.add("Référence");
        columnNames_Devis_Table.add("Designation");
        columnNames_Devis_Table.add("Prix Unit HT");
        columnNames_Devis_Table.add("Quantité");
        columnNames_Devis_Table.add("Totale HT");
        columnNames_Devis_Table.add("Remise %");
        columnNames_Devis_Table.add("TVA %");
        columnNames_Devis_Table.add("Totale TTC");
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
        txt_Prix_U.setText("");
        txt_qte.setText("");
        txt_remise.setText("");
        txt_TVA.setText("");
    }

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
                Total_HT = Double.valueOf((formatDouble(Total_HT)));
                if (remise != 0.0) {
                    montant_remise = Total_HT * (remise / 100);
                }

                if (!CheckBox_exh_tva.isSelected()) {
                    montant_Tva = (Total_HT - montant_remise) * (TVA / 100);
                    montant_Tva = montant_Tva;

                }
//Double.valueOf((formatDouble(Total_TTC)))
                Total_TTC += (Total_HT + montant_Tva) - montant_remise;
                // 494.10300000000007
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
        D_Total_Net += timbre;
        txt_Total_HT.setText(String.valueOf(formatDouble(D_Total_HT)));
        txt_Total_Net.setText(String.valueOf(formatDouble(D_Total_Net)));
        txt_Total_remise.setText(String.valueOf(formatDouble(D_Total_remise)));
        txt_total_TVA.setText(String.valueOf(formatDouble(D_montant_TVA)));

    }

    public void addLigneDevis_Table() {

        Vector<ligneDevis> vector = new Vector<ligneDevis>();
        i++;

        //   decimformat.setMaximumFractionDigits(3);
        //  decimformat.setMaximumFractionDigits(3);
        Double Total_TTC = 0.0;
        Double Total_HT = 0.0;
        Double Prix_U = 0.0;
        Double montant_Tva = 0.0;
        Double montant_remise = 0.0;
        Double TVA;
        if (txt_TVA.getText().isEmpty()) {
            TVA = 0.0;
        } else {
            TVA = Double.valueOf(txt_TVA.getText());
        }

        Double remise;
        if (txt_remise.getText().isEmpty()) {
            remise = 0.0;
        } else {
            remise = Double.valueOf(txt_remise.getText());
            if (remise >= 100) {
                JOptionPane.showMessageDialog(null, "Remise ne depasse pas 100% ! ");
                return;
            }
        }

        Double qte;
        if (txt_qte.getText().isEmpty()) {
            qte = 1.0;
        } else {
            qte = Double.valueOf(txt_qte.getText());
        }
        if (!txt_Prix_U.getText().isEmpty()) {
            Prix_U = Double.valueOf(txt_Prix_U.getText());

            Total_HT = Prix_U * qte;
            if (!txt_remise.getText().isEmpty()) {
                montant_remise = Total_HT * (remise / 100);
            }
            if (!CheckBox_exh_tva.isSelected()) {

                montant_Tva = (Total_HT - montant_remise) * (TVA / 100);
            }

            // Total_TTC = (Total_HT + montant_Tva) - montant_remise;
            Total_TTC += (Total_HT + montant_Tva) - montant_remise;

        }

        Object LigneData[] = new Object[9];

        LigneData[0] = i;
        LigneData[1] = txt_searchArticle.getText();
        LigneData[2] = txt_design_article.getText();
        LigneData[3] = formatDouble(Prix_U);
        LigneData[4] = txt_qte.getText();
        LigneData[5] = formatDouble(Total_HT);
        LigneData[6] = remise.toString();
        LigneData[7] = TVA.toString();
        LigneData[8] = formatDouble(Total_TTC);
        if (Devis_Table.getSelectedRow() == -1) {
            df.addRow(LigneData);
        } else {
            df.insertRow(Devis_Table.getSelectedRow() + 1, LigneData);
        }
        Devis_Table.clearSelection();
        D_Total_HT += Total_HT;
        D_Total_Net += Total_TTC;
        if (!txt_remise.getText().isEmpty()) {
            D_Total_remise += (Total_HT * (Double.valueOf(txt_remise.getText()) / 100));
        }
        D_montant_TVA += montant_Tva;

        Double timbre = 0.0;
        if (CheckBox_timbre.isSelected()) {
            timbre = Double.valueOf(txt_timbre.getText());
        }

        txt_Total_HT.setText(String.valueOf(formatDouble(D_Total_HT)));
        txt_Total_Net.setText(String.valueOf(formatDouble(D_Total_Net + timbre)));
        txt_Total_remise.setText(String.valueOf(formatDouble(D_Total_remise)));
        txt_total_TVA.setText(String.valueOf(formatDouble(D_montant_TVA)));
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
        txt_Code_TVA = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_infos_devis = new javax.swing.JTextField();
        txt_num_devis = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        CheckBox_proformat = new javax.swing.JCheckBox();
        CheckBox_afficher_totale = new javax.swing.JCheckBox();
        CheckBox_exh_tva = new javax.swing.JCheckBox();
        CheckBox_Afficher_prix = new javax.swing.JCheckBox();
        CheckBox_editer_ref = new javax.swing.JCheckBox();
        CheckBox_afficher_validiter = new javax.swing.JCheckBox();
        CheckBox_afficher_entet = new javax.swing.JCheckBox();
        txt_search = new javax.swing.JTextField();
        Date_Devis = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        CheckBox_timbre = new javax.swing.JCheckBox();
        ComboBoxDevis = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Ajouter = new javax.swing.JButton();
        txt_TVA = new javax.swing.JTextField();
        txt_design_article = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Supprimer = new javax.swing.JButton();
        txt_searchArticle = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_qte = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_remise = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Devis_Table = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txt_Total_Net = new javax.swing.JTextField();
        txt_Total_HT = new javax.swing.JTextField();
        txt_total_TVA = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_Total_remise = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        txt_ajuster = new javax.swing.JTextField();
        txt_timbre = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_Prix_U = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Devis");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Infos Devis"));

        jLabel1.setText("Nom Client");

        jLabel2.setText("Date Devis");

        jLabel6.setText("Code TVA");

        jLabel5.setText("Info Devis");

        CheckBox_proformat.setText("Facture proformat");

        CheckBox_afficher_totale.setText("Afficher Total");

        CheckBox_exh_tva.setText("Exh TVA");
        CheckBox_exh_tva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBox_exh_tvaActionPerformed(evt);
            }
        });

        CheckBox_Afficher_prix.setText("Afficher Prix");

        CheckBox_editer_ref.setText("Editer Reférence");

        CheckBox_afficher_validiter.setText("Afficher Validiter");

        CheckBox_afficher_entet.setText("Afficher Entêt");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CheckBox_afficher_totale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CheckBox_editer_ref, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CheckBox_Afficher_prix, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CheckBox_afficher_validiter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CheckBox_proformat, javax.swing.GroupLayout.PREFERRED_SIZE, 127, Short.MAX_VALUE)
                    .addComponent(CheckBox_exh_tva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CheckBox_afficher_entet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CheckBox_afficher_totale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(CheckBox_exh_tva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CheckBox_proformat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CheckBox_afficher_validiter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CheckBox_Afficher_prix, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CheckBox_editer_ref, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CheckBox_afficher_entet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

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

        jLabel4.setText("Num Devis");

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

        CheckBox_timbre.setText("Timbre");
        CheckBox_timbre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBox_timbreActionPerformed(evt);
            }
        });

        ComboBoxDevis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        ComboBoxDevis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxDevisActionPerformed(evt);
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
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addGap(18, 18, 18)
                                .addComponent(jButton9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txt_num_devis, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CheckBox_timbre)
                                    .addComponent(txt_Code_TVA, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_infos_devis)
                            .addComponent(Date_Devis, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ComboBoxDevis, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txt_num_devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jButton7)
                    .addComponent(jButton9)
                    .addComponent(ComboBoxDevis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(Date_Devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txt_Code_TVA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txt_infos_devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(CheckBox_timbre))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

        jLabel8.setText("Remise %");

        jLabel9.setText("TVA %");

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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Devis_TableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(Devis_Table);

        jLabel13.setText("Montant remise");

        jLabel16.setText("Total Net");

        jLabel12.setText("Total Hors Taxe");

        jLabel14.setText("Montant TVA");

        jButton5.setText("Ajuster");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel15.setText("Timbre");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_Total_remise, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_total_TVA, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(txt_ajuster, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_Total_Net, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_timbre, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_Total_HT, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(jButton5)
                .addContainerGap(152, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Total_Net, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(txt_ajuster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setText("Prix Unitaire HT");

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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10))
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(Ajouter)
                                .addGap(18, 18, 18)
                                .addComponent(Supprimer)
                                .addGap(163, 163, 163)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_remise, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                                .addComponent(txt_Prix_U))
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_qte, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(txt_TVA))
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(txt_Prix_U, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_searchArticle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)
                                .addComponent(txt_qte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton8)
                                .addComponent(jButton10)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(txt_remise, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)
                                .addComponent(jLabel11)
                                .addComponent(txt_design_article, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_TVA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Ajouter)
                                .addComponent(Supprimer))
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Validation Devis"));

        jButton2.setText("Valider");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Historique");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("Devis");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setText("Exporter Vers BL");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(92, 92, 92))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jButton6))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton4)))
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
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addContainerGap(15, Short.MAX_VALUE))
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

    private void CheckBox_exh_tvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBox_exh_tvaActionPerformed
        // TODO add your handling code here:

        if (CheckBox_exh_tva.isSelected()) {
            txt_TVA.setEnabled(false);
            txt_TVA.setText("0");

        } else {
            txt_TVA.setEnabled(true);
        }
        setTvaColumn();
    }//GEN-LAST:event_CheckBox_exh_tvaActionPerformed

    public void setTvaColumn() {
        String tvaVal = "";
        if (CheckBox_exh_tva.isSelected()) {
            tvaVal = "0";
        } else {
            tvaVal = Commen_Proc.TVAVal;

        }
        for (int count = 0; count < Devis_Table.getModel().getRowCount(); count++) {
            Devis_Table.getModel().getValueAt(count, 0).toString();
            Devis_Table.getModel().setValueAt(tvaVal, count, 7);
        }
        reCalculerTT();

    }

    private Devis setDevisFromEditText() {
        Devis r = new Devis();

        try {
            if (!txt_num_devis.getText().isEmpty()) {
                r.setNum_Devis(txt_num_devis.getText());
            } else {
                r.setNum_Devis("");
            }
            if (id_client != null) {
                r.setId_client(Integer.valueOf(id_client));
            } else {
                r.setId_client(0);
            }
            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(Date_Devis.getDate());
            r.setDate_devis(currentTime);

            if (!txt_infos_devis.getText().isEmpty()) {
                r.setInfos_Devis(txt_infos_devis.getText());
            } else {
                r.setInfos_Devis("");
            }

            if (!txt_Code_TVA.getText().isEmpty()) {
                r.setCode_TVA(txt_Code_TVA.getText());
            } else {
                r.setCode_TVA("");
            }

            r.setTotale_HT(D_Total_HT);
            r.setTotale_TTC(D_Total_Net);
            r.setMontant_TVA(D_montant_TVA);
            r.setRemise(D_Total_remise);
            if (CheckBox_timbre.isSelected()) {
                r.setTimbre(1);
            } else {
                r.setTimbre(0);
            }

            if (CheckBox_Afficher_prix.isSelected()) {
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

            if (CheckBox_editer_ref.isSelected()) {
                r.setEdit_ref(1);
            } else {
                r.setEdit_ref(0);
            }

            if (CheckBox_exh_tva.isSelected()) {
                r.setExh_TVA(1);
            } else {
                r.setExh_TVA(0);
            }

            if (CheckBox_proformat.isSelected()) {
                r.setFacture_proformat(1);
            } else {
                r.setFacture_proformat(0);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println(e);

        }

        return r;
    }

    private String VerifStock(ArrayList<ligneDevis> lstd) {
        String res = "";
        for (ligneDevis d : lstd) {
            ArticleDao daoArticle = new ArticleDao();

            String stockNegative = daoArticle.getNameItemById("article", "stock_negative", "ref", d.getRef_article());

            double stockReel = 0;
            double qteDevis = 0;

            stockReel = Integer.parseInt(daoArticle.getNameItemById("article", "qte", "ref", d.getRef_article()));

            qteDevis = Double.valueOf(d.getQte());
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
            if (txt_num_devis.getText().equals(ComboBoxDevis.getSelectedItem().toString())) {
                type_operation = "Modif";
            }
            Devis d = setDevisFromEditText();
            ConfigDao c = new ConfigDao();

            if (type_operation.equals("Modif")) {
                if (c.DateCheckOnUpdate("devis", "date_devis", "num_devis", d.getNum_Devis(), d.getDate_devis())) {
                    ArrayList<ligneDevis> lstd;
                    lstd = setLigneDevisFromDevisTable();
                    //JOptionPane.showMessageDialog(null, s.toString());
                    String s = VerifStock(lstd);
                    if (s.isEmpty()) {
                        if (devisDao.modifierDevis(d)) {
                            devisDao.modifierLigneDevis(lstd);
                            JOptionPane.showMessageDialog(null, "Devis num " + txt_num_devis.getText() + " à été bien Modifié !");
                            //devisDao.modifierStock(lstd);

                            clearDevisLFields();
                            setNumDevis();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error in Modif ActionPerformed ");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, s);

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Condition Date n'a pas été acceptée ");
                }

            } else {

                if (c.DateCheckOnUpdate("devis", "date_devis", "num_devis", d.getNum_Devis(), d.getDate_devis())) {
                    ArrayList<ligneDevis> lstd;
                    lstd = setLigneDevisFromDevisTable();
                    //JOptionPane.showMessageDialog(null, s.toString());
                    if (lstd != null) {

                        String s = VerifStock(lstd);
                        if (s.isEmpty()) {
                            if (devisDao.ajouterDevis(d)) {
                                devisDao.ajouterLigneDevis(lstd);
                                JOptionPane.showMessageDialog(null, "Devis num " + txt_num_devis.getText() + " à été bien enregistré !");
                                //devisDao.modifierStock(lstd);

                                clearDevisLFields();
                                setNumDevis();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error in CreateDevis ActionPerformed ");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, s);

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Ref Article est vide !");

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Condition Date n'a pas été acceptée ");
                }
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

    void clearDevisLFields() {
        txt_searchArticle.setText("");
        txt_search.setText("");

        Date_Devis.setDate(new Date());
        Date_Devis.setEnabled(false);
        txt_infos_devis.setText("");
        txt_Code_TVA.setText("");
        df.setRowCount(0);
        txt_Total_HT.setText("");
        txt_Total_Net.setText("");
        txt_Total_remise.setText("");
        txt_total_TVA.setText("");
        txt_ajuster.setText("");

    }

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
            txt_TVA.setText(tva);
            txt_Prix_U.setText(prix_u);

        }
    }//GEN-LAST:event_txt_searchArticleKeyPressed

    private void txt_searchArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchArticleActionPerformed

    }//GEN-LAST:event_txt_searchArticleActionPerformed

    private void SupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupprimerActionPerformed
        // TODO add your handling code here:

        int row = Devis_Table.getSelectedRow();
        if (row != -1) {
            Double d_HT = 0.0;
            int coll = 0;
            Object HT = df.getValueAt(row, 5);
            String ss = HT.toString();
            d_HT = Double.parseDouble(ss.replace(",", "."));
            D_Total_HT -= d_HT;
            txt_Total_HT.setText(String.valueOf(formatDouble(D_Total_HT)));
            Double d_remise = 0.0;

            Object remise = df.getValueAt(row, 6);
            Double montant_remise = 0.0;
            if (!remise.toString().isEmpty()) {
                d_remise = Double.parseDouble(remise.toString().replace(",", "."));
                montant_remise = d_HT * (d_remise / 100);

                D_Total_remise -= montant_remise;
                txt_Total_remise.setText(String.valueOf(formatDouble(D_Total_remise)));
            }
            Double d = 0.0;
            Object tva = df.getValueAt(row, 7);
            if (!tva.toString().isEmpty()) {
                Double d_tva = Double.parseDouble(tva.toString().replace(",", "."));

                //montant_Tva = (Total_HT - montant_remise) * (TVA / 100);
                d = (d_HT - montant_remise) * (d_tva / 100);

                D_montant_TVA -= d;
                txt_total_TVA.setText(String.valueOf(formatDouble(D_montant_TVA)));

            }

            Object TTC = df.getValueAt(row, 8);
            Double d_TTC = Double.parseDouble(TTC.toString().replace(",", "."));
            Double Total_TTC = (d_HT + d) - montant_remise;

            D_Total_Net -= Total_TTC;
            txt_Total_Net.setText(String.valueOf(Config.Commen_Proc.formatDouble(D_Total_Net)));

            df.removeRow(Integer.valueOf(row));

            //df.setValueAt("5", row, coll);
            for (int j = 0; j < df.getRowCount(); j++) {
                Object s = df.getValueAt(j, coll);
                // JOptionPane.showMessageDialog(null, " index : " + j + " value : " + s);
                df.setValueAt(j + 1, j, coll);
            }
            reCalculerTT();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(null, "No row selected");
        }

        //Devis_Table.clearSelection();

    }//GEN-LAST:event_SupprimerActionPerformed
    public static String roundAvoid(double value, int places) {

        double scale = Math.pow(10, places);
        Double d = Math.round(value * scale) / scale;
        String s = d.toString().replace(".", ";");
        String[] x = s.split(";");
        if (x[1].length() == 1) {
            s = x[0] + "." + x[1] + "00";
        } else if (x[1].length() == 2) {
            s = x[0] + "." + x[1] + "0";
        } else {
            s = s.substring(0, s.indexOf(";") + 4);
            s = s.replace(";", ".");
        }
        return s;
    }

    public static String formatDouble(Double d) {
        DecimalFormat df = new DecimalFormat("0.000");
        df.setRoundingMode(RoundingMode.CEILING); // import java.text.DecimalFormat;
        String s = "0.0";

        try {
            s = df.format(d);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error in formatDouble methode :  " + e.getMessage());

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

    public String formatString(String s) {
        DecimalFormat df = new DecimalFormat("0.000");
        df.setRoundingMode(RoundingMode.CEILING);
        Double d1 = 0.0;
        try {
            d1 = Double.parseDouble(s);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error in formatString methode :  " + e.getMessage());
        }
        return d1.toString().replace(",", ".");
        //return s.substring(0, s.indexOf(".") + 4);
    }
    private void AjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjouterActionPerformed
        // TODO add your handling code here:
        addLigneDevis_Table();
        int row = Devis_Table.getSelectedRow();
        int coll = 0;
        reCalculerTT();
        //df.setValueAt("5", row, coll);
        for (int j = 0; j < df.getRowCount(); j++) {
            Object s = df.getValueAt(j, coll);
            // JOptionPane.showMessageDialog(null, " index : " + j + " value : " + s);
            df.setValueAt(j + 1, j, coll);
        }
    }//GEN-LAST:event_AjouterActionPerformed


    private void Devis_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Devis_TableMouseClicked
        // TODO add your handling code here:

        // 6 remise 7 tvA

    }//GEN-LAST:event_Devis_TableMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int row = Devis_Table.getSelectedRow();
        String ref = txt_searchArticle.getText();
        String design = txt_design_article.getText();
        String prix_u = txt_Prix_U.getText();
        String qte = txt_qte.getText();
        String remise = txt_remise.getText();
        String tva = txt_TVA.getText();

        Devis_Table.getModel().setValueAt(ref, row, 1);
        Devis_Table.getModel().setValueAt(design, row, 2);
        Devis_Table.getModel().setValueAt(prix_u, row, 3);
        Devis_Table.getModel().setValueAt(formatDouble(Double.valueOf(prix_u) * Double.valueOf(qte)), row, 5);
        Devis_Table.getModel().setValueAt(qte, row, 4);
        Devis_Table.getModel().setValueAt(remise, row, 6);
        Devis_Table.getModel().setValueAt(tva, row, 7);
        Double Prix_U;
        Double Total_HT;
        Double montant_remise = 0.0;
        Double montant_Tva = 0.0;
        Double Total_TTC = 0.0;

        Prix_U = Double.valueOf(prix_u);

        Total_HT = Prix_U * Integer.valueOf(qte);
        montant_remise = Total_HT * (Double.valueOf(remise) / 100);
        if (!CheckBox_exh_tva.isSelected()) {
            montant_Tva = (Total_HT - montant_remise) * (Double.valueOf(tva) / 100);
        }

        Total_TTC = (Total_HT + montant_Tva) - montant_remise;

        Devis_Table.getModel().setValueAt(String.valueOf(formatDouble(Total_TTC)), row, 8);

        reCalculerTT();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
      /*  HistoriqueDevis c = new HistoriqueDevis();
        Home.App.d.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        dispose();
        c.setVisible(true);*/
    }//GEN-LAST:event_jButton4ActionPerformed


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        JTable table = new JTable();
        if (txt_search.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Client est Vide ");
            return;
        }
        if (txt_searchArticle.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Référence article est Vide ");
            return;
        }
        devisDao.afficherHistPrix(table, txt_search.getText(), txt_searchArticle.getText());
        panel.add(new JScrollPane(table));
        frame.add(panel);
        frame.setSize(550, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Double ajuster = 0.0;
        Double TTC = 0.0;
        if (!txt_ajuster.getText().isEmpty()) {
            ajuster = Double.parseDouble(txt_ajuster.getText());
        }
        TTC = D_Total_Net;

        Boolean b = true;
        if ((Math.abs(ajuster - TTC) > 5.0)) {
            b = false;
        }
        if (b) {
            txt_Total_Net.setText(txt_ajuster.getText());
        } else {
            JOptionPane.showMessageDialog(this, "Vous avez depassé les 5 DT !!!");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
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
    }//GEN-LAST:event_jButton6ActionPerformed


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
                ArticleDao d = new ArticleDao();
                String code_tva = d.getNameItemById("client", "id_Fiscale", "nom", txt_search.getText());
                String s = jTable.getModel().getValueAt(jTable.getSelectedRow(), 0).toString();
                id_client = s;
                txt_Code_TVA.setText(code_tva);
                Date_Devis.setDate(new Date());
                Date_Devis.setEnabled(false);
                frameListeClient.dispose();
            }
        });

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        Vector<String> columnNames = new Vector<String>();
        columnNames.add("id");
        columnNames.add("ref");
        columnNames.add("designation");
        columnNames.add("Prix Unitaire");
        columnNames.add("Qte");
        columnNames.add("Marge");
        columnNames.add("Réservé");

        ArticleDao articleDao = new ArticleDao();
        Vector<Vector<Object>> data1;
        if (!Commen_Proc.isRemote) {
            data1 = articleDao.afficherListeArticle();
        } else {
            data1 = null;
        }

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
                String ref = nomclient;
                String s = jTable.getModel().getValueAt(jTable.getSelectedRow(), 2).toString();
                ArticleDao d = new ArticleDao();
                String design = s;
                String tva = d.getNameItemById("article", "tva", "ref", txt_searchArticle.getText());
                String pu = jTable.getModel().getValueAt(jTable.getSelectedRow(), 3).toString();

                String remise = d.getRemiseById(id_client, ref);
                txt_remise.setText(remise.isEmpty() ? "" : remise);

                txt_design_article.setText(design);
                txt_TVA.setText(tva);
                txt_Prix_U.setText(pu);
                frameListeClient.dispose();
            }
        });

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
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        ds.repaint();
    }//GEN-LAST:event_jButton10ActionPerformed

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
    String combo = "";
    ArrayList<LigneBL> lstdmodif;

    private void ComboBoxDevisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxDevisActionPerformed
        if (combo != "0") {

            try {

                ArticleDao dart = new ArticleDao();
                type_operation = "Modif";
                setBLToModify(ComboBoxDevis.getSelectedItem().toString());
                lstdmodif = setLigneDevisFromDevisTableExport();

                String nom_and_codeTva = dart.getNameItemByIdJoinTables("devis", "num_devis", ComboBoxDevis.getSelectedItem().toString());
                String[] array = nom_and_codeTva.split(";");
                id_client = array[0];
                txt_search.setText(array[1]);
                txt_Code_TVA.setText(array[2]);
                txt_num_devis.setText(ComboBoxDevis.getSelectedItem().toString());

                String date = dart.getDateByNumDevis(ComboBoxDevis.getSelectedItem().toString());
                try {
                    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    Date_Devis.setDate(date1);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }

                //detail devis where  ComboBoxDevis.getSelectedItem().toString()
                BLDao devisDao = new BLDao();
                //   FormBL fr = new FormBL("", "", "");
                df = devisDao.afficherDetailDevis(Devis_Table, ComboBoxDevis.getSelectedItem().toString());
            } catch (Exception e) {
            }
        }
        combo = "1";
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxDevisActionPerformed

    private void Devis_TableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Devis_TableMousePressed
        int row = Devis_Table.getSelectedRow();
        String ref = Devis_Table.getModel().getValueAt(row, 1).toString();
        String design = Devis_Table.getModel().getValueAt(row, 2).toString();
        String prix_u = Devis_Table.getModel().getValueAt(row, 3).toString();
        String qte = Devis_Table.getModel().getValueAt(row, 4).toString();
        String remise = Devis_Table.getModel().getValueAt(row, 6).toString();
        String tva = Devis_Table.getModel().getValueAt(row, 7).toString();
        txt_searchArticle.setText(ref);
        txt_design_article.setText(design);
        txt_Prix_U.setText(prix_u);
        txt_qte.setText(qte);
        txt_remise.setText(remise);
        txt_TVA.setText(tva);
        // TODO add your handling code here:
    }//GEN-LAST:event_Devis_TableMousePressed
    private ArrayList<LigneBL> setLigneDevisFromDevisTableExport() {
        ArrayList<LigneBL> lst = new ArrayList();
        try {
            for (int j = 0; j < Devis_Table.getRowCount(); j++) {
                LigneBL ld = new LigneBL();
                ld.setId_BL(txt_num_devis.getText());
                ld.setId_BL(ComboBoxDevis.getSelectedItem().toString());
                ld.setRef_article(Devis_Table.getValueAt(j, 1).toString());
                ld.setDesign(Devis_Table.getValueAt(j, 2).toString());
                ld.setPrix_U(Double.valueOf(Devis_Table.getValueAt(j, 3).toString().replace(",", ".")));
                ld.setQte(Devis_Table.getValueAt(j, 4).toString());
                ld.setTotal_HT(Double.valueOf(Devis_Table.getValueAt(j, 5).toString().replace(",", ".")));
                ld.setRemise(Devis_Table.getValueAt(j, 6).toString());
                ld.setTVA(Devis_Table.getValueAt(j, 7).toString());
                ld.setTotale_TTC(Double.valueOf(Devis_Table.getValueAt(j, 8).toString().replace(",", ".")));

                lst.add(ld);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        return lst;
    }

    public void setBLToModify(String Num_Devis) {
        DevisDao devisDao = new DevisDao();
        df = new DefaultTableModel();
        df = devisDao.afficherDetailBL(Devis_Table, Num_Devis, true);
        Devis_Table.setModel(df);
        reCalculerTT();

    }
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
    private javax.swing.JCheckBox CheckBox_Afficher_prix;
    private javax.swing.JCheckBox CheckBox_afficher_entet;
    private javax.swing.JCheckBox CheckBox_afficher_totale;
    private javax.swing.JCheckBox CheckBox_afficher_validiter;
    private javax.swing.JCheckBox CheckBox_editer_ref;
    private javax.swing.JCheckBox CheckBox_exh_tva;
    private javax.swing.JCheckBox CheckBox_proformat;
    private javax.swing.JCheckBox CheckBox_timbre;
    private javax.swing.JComboBox<String> ComboBoxDevis;
    private com.toedter.calendar.JDateChooser Date_Devis;
    private javax.swing.JTable Devis_Table;
    private javax.swing.JButton Supprimer;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_Code_TVA;
    private javax.swing.JTextField txt_Prix_U;
    private javax.swing.JTextField txt_TVA;
    private javax.swing.JTextField txt_Total_HT;
    private javax.swing.JTextField txt_Total_Net;
    private javax.swing.JTextField txt_Total_remise;
    private javax.swing.JTextField txt_ajuster;
    private javax.swing.JTextField txt_design_article;
    private javax.swing.JTextField txt_infos_devis;
    private javax.swing.JTextField txt_num_devis;
    private javax.swing.JTextField txt_qte;
    private javax.swing.JTextField txt_remise;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_searchArticle;
    private javax.swing.JTextField txt_timbre;
    private javax.swing.JTextField txt_total_TVA;
    // End of variables declaration//GEN-END:variables

    private void setNumDevis() {

        String s = devisDao.maxNumDevis(year + "D");
        String formatted;
        if (!s.isEmpty()) {
            String t[] = s.split("D");
            int x = Integer.valueOf(t[1]) + 1;
            formatted = String.format("%05d", x);
            String num_devis = year + "D" + formatted;
            txt_num_devis.setText(num_devis);
            txt_num_devis.setEnabled(false);
        } else {
            formatted = String.format("%05d", 1);
            String num_devis = year + "D" + formatted;
            txt_num_devis.setText(num_devis);
            txt_num_devis.setEnabled(false);

        }
    }

    private ArrayList<ligneDevis> setLigneDevisFromDevisTable() {
        ArrayList<ligneDevis> lst = new ArrayList();

        try {

            for (int j = 0; j < Devis_Table.getRowCount(); j++) {
                ligneDevis ld = new ligneDevis();
                ld.setId_Devis(txt_num_devis.getText());
                ld.setRef_article(Devis_Table.getValueAt(j, 1).toString());
                ld.setDesign(Devis_Table.getValueAt(j, 2).toString());
                ld.setPrix_U(Double.valueOf(Devis_Table.getValueAt(j, 3).toString().replace(",", ".")));
                ld.setQte(Devis_Table.getValueAt(j, 4).toString());
                ld.setTotal_HT(Double.valueOf(Devis_Table.getValueAt(j, 5).toString().replace(",", ".")));
                ld.setRemise(Devis_Table.getValueAt(j, 6).toString());
                ld.setTVA(Devis_Table.getValueAt(j, 7).toString());
                ld.setTotale_TTC(Double.valueOf(Devis_Table.getValueAt(j, 8).toString().replace(",", ".")));

                lst.add(ld);
                if (Devis_Table.getValueAt(j, 1).toString().isEmpty()) {
                    return null;
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error in  setLigneDevisFromDevisTable : " + e);
        }
        return lst;
    }

}
