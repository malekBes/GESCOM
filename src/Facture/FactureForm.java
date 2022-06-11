/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facture;

import Article.ArticleDao;
import BL.BLDao;
import BL.HistoriqueDevis;
import BL.LigneBL;
import Client.ClientDao;
import Client.ClientForm;
import Config.Commen_Proc;
import Config.ConfigDao;
import Devis.DevisDao;
import Home.App;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
public class FactureForm extends javax.swing.JInternalFrame {

    String id_client = null;
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
    String type_operation = "";
    String combo = "";

    public FactureForm(Vector<Vector<Object>> obj, String s, String NumFacture) {

        initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        combo = "0";
        year = Commen_Proc.YearVal;
        listNomSte = new ArrayList<>();
        lstBLexport = new Vector<Vector<Object>>();
        ClientDao clientDao = new ClientDao();
        factureDAO = new FactureDAO();
        listNomSte = clientDao.afficherClient();
        Date_facture.setDate(new Date());
        Date_facture.setEnabled(false);
        numFactureAnnuleeComboBox();
        CheckBox_timbre.setSelected(true);
        txt_timbre.setText(Commen_Proc.TimbreVal);
        if (s != "Client") {
            setNumFacture();
        }
        ArticleDao dart = new ArticleDao();
        if (s == "Client") {
            type_operation = s;
            columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Num BL");
            columnNames.add("Date BL");
            columnNames.add("Total HT");
            columnNames.add("Remise");
            columnNames.add("TVA");
            columnNames.add("Total TTC");

            tableModel = new DefaultTableModel(obj, columnNames);
            Table_facture.setModel(tableModel);
            Object nom_client = tableModel.getValueAt(0, 0);
            ArticleDao d = new ArticleDao();
            //String ids = d.getNameItemById("client", "numero_client", "nom", nom_client.toString());
            // String adresse = d.getIdFactureModif("facture", "id_client", "num_facture", nom_client.toString());
            String idClient_nom_adresse_timbre_info = dart.getNameItemByIdJoinTablesFacture("facture", "num_facture", NumFacture, "Client");
            String[] x = idClient_nom_adresse_timbre_info.split(";");
            id_client = x[0];
            txt_adresse.setText(x[2]);
            txt_adresse.setEnabled(false);
            txt_search.setText(x[1]);
            txt_search.setEnabled(false);
            txt_infos_facture.setText(x[4]);
            txt_infos_facture.setEnabled(false);
            jButtonExporterBL.setEnabled(false);
            // Date_facture.setEnabled(true);
            if (x[3].equals("1")) {
                CheckBox_timbre.setSelected(true);
            } else {
                CheckBox_timbre.setSelected(false);
            }

            txt_num_facture.setText(NumFacture);
            txt_num_facture.setEnabled(false);

            String date = dart.getDateByNumFacture(NumFacture);
            try {
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                Date_facture.setDate(date1);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }

            // formatTableValues();
            reCalculerTT();
        } else if (s == "true") {
            columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date BL");
            columnNames.add("Num BL");
            columnNames.add("Total HT");
            columnNames.add("Remise");
            columnNames.add("TVA");
            columnNames.add("TTC");
            tableModel = new DefaultTableModel(obj, columnNames);
            Table_facture.setModel(tableModel);
            Object nom_client = tableModel.getValueAt(0, 0);
            ArticleDao d = new ArticleDao();
            //String ids = d.getNameItemById("client", "numero_client", "nom", nom_client.toString());
            String adresse = d.getIdAdresseFacture("client", "numero_client,adresse", "nom", nom_client.toString());
            String[] x = adresse.split(";");
            id_client = x[0];
            txt_adresse.setText(x[1]);
            txt_search.setText(nom_client.toString());
            formatTableValues();
            reCalculerTT();

        }
        SearchBL();
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

    public void SearchBL() {
        try {
            columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date BL");
            columnNames.add("Num BL");
            columnNames.add("Total HT");
            columnNames.add("Remise");
            columnNames.add("TVA");
            columnNames.add("TTC");
            String FromDate = "";
            String ToDate = "";

            try {
                /*   String dt = Date_Devis_To.getDate().toString();
                if (dt.isEmpty() & Date_Devis.getDate().toString().isEmpty()) {
                } else {
                    Date To = Date_Devis_To.getDate();
                    Date From = Date_Devis.getDate();
                    SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                    FromDate = tdate.format(From);
                    ToDate = tdate.format(To);
                }*/

            } catch (Exception e) {
                System.out.println("date Null");
            }
            lstBL = factureDAO.afficherBL(BLHist_Table, FromDate,
                    ToDate, "", "", "",
                    txt_search.getText());
            tableModel = new DefaultTableModel(lstBL, columnNames);
            BLHist_Table.setModel(tableModel);
            formatTableValues();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in HistoriqueDevisFrom/SearchBL : \n" + e);
        } finally {
            try {

                System.out.println("disconnected");
            } catch (Exception ex) {
                Logger.getLogger(HistoriqueDevis.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void numFactureAnnuleeComboBox() {
        factureDAO.listFacture(ComboBoxFacture);
    }

    private void setNumFacture() {

        String s = factureDAO.maxNumFacture(year + "F");
        String formatted;
        if (!s.isEmpty()) {
            String t[] = s.split("F");
            int x = Integer.valueOf(t[1]) + 1;
            formatted = String.format("%05d", x);
            String num_devis = year + "F" + formatted;
            txt_num_facture.setText(num_devis);
            txt_num_facture.setEnabled(false);
        } else {
            formatted = String.format("%05d", 1);
            String num_devis = year + "F" + formatted;
            txt_num_facture.setText(num_devis);
            txt_num_facture.setEnabled(false);

        }
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

    public void reCalculerTTOnUpdates() {

        try {

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

                String l_txt_ht = Table_facture.getValueAt(j, 4).toString();
                String l_txt_remise = Table_facture.getValueAt(j, 5).toString();
                String l_txt_TVA = Table_facture.getValueAt(j, 6).toString();
                String l_txt_TTC = Table_facture.getValueAt(j, 7).toString();

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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "reCalculerTT : " + e.getMessage());
        }

    }

    public void reCalculerTT() {
        try {
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "reCalculerTTBL : " + e.getMessage());
        }
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
        txt_search = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Date_facture = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txt_num_facture = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_infos_facture = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_adresse = new javax.swing.JTextField();
        jButtonListeClient = new javax.swing.JButton();
        jButtonPlusCLient = new javax.swing.JButton();
        ComboBoxFacture = new javax.swing.JComboBox<>();
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
        jButton5 = new javax.swing.JButton();
        txt_ajuster = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_facture = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        BLHist_Table = new javax.swing.JTable();
        jButtonPlus = new javax.swing.JButton();
        jButtonMoins = new javax.swing.JButton();
        jButtonPlusAll = new javax.swing.JButton();
        jButtonMoinsAll = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButtonRecherche = new javax.swing.JButton();
        jButtonValider = new javax.swing.JButton();
        CheckBox_timbre = new javax.swing.JCheckBox();
        jButtonExporterBL = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Facture");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Info Facture"));

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

        jLabel2.setText("Date Facture");

        jLabel4.setText("Num Facture");

        jLabel5.setText("Info Facture");

        jLabel6.setText("Adresse");

        txt_adresse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_adresseActionPerformed(evt);
            }
        });

        jButtonListeClient.setText("Liste");
        jButtonListeClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListeClientActionPerformed(evt);
            }
        });

        jButtonPlusCLient.setText("+");
        jButtonPlusCLient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlusCLientActionPerformed(evt);
            }
        });

        ComboBoxFacture.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        ComboBoxFacture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxFactureActionPerformed(evt);
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(Date_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonListeClient)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPlusCLient)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txt_num_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ComboBoxFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txt_num_facture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jButtonListeClient)
                    .addComponent(jButtonPlusCLient)
                    .addComponent(ComboBoxFacture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jButton5.setText("Ajuster");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
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
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jButton5))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(txt_ajuster, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_ajuster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addContainerGap())
        );

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

        BLHist_Table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(BLHist_Table);

        jButtonPlus.setText(">");
        jButtonPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlusActionPerformed(evt);
            }
        });

        jButtonMoins.setText("<");
        jButtonMoins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMoinsActionPerformed(evt);
            }
        });

        jButtonPlusAll.setText(">>");
        jButtonPlusAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlusAllActionPerformed(evt);
            }
        });

        jButtonMoinsAll.setText("<<");
        jButtonMoinsAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMoinsAllActionPerformed(evt);
            }
        });

        jButtonRecherche.setText("Recherche");
        jButtonRecherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRechercheActionPerformed(evt);
            }
        });

        jButtonValider.setText("Valider");
        jButtonValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonValiderActionPerformed(evt);
            }
        });

        CheckBox_timbre.setText("Timbre");
        CheckBox_timbre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBox_timbreActionPerformed(evt);
            }
        });

        jButtonExporterBL.setText("Exporter BL");
        jButtonExporterBL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExporterBLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonExporterBL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonValider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonRecherche, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(CheckBox_timbre)
                        .addGap(22, 22, 22))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonValider)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRecherche)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonExporterBL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CheckBox_timbre)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonPlusAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonMoinsAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonMoins, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(325, 325, 325))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonPlus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonMoins)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPlusAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonMoinsAll))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed

    }//GEN-LAST:event_txt_searchActionPerformed

    private void txt_adresseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_adresseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_adresseActionPerformed

    private void jButtonExporterBLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExporterBLActionPerformed
        // TODO add your handling code here:
        HistoriqueBLForm c = new HistoriqueBLForm();
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
    }//GEN-LAST:event_jButtonExporterBLActionPerformed
    FactureDAO fdao = new FactureDAO();
    private void jButtonValiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonValiderActionPerformed
        // TODO add your handling code here:
        if (txt_num_facture.getText().toString().equals(ComboBoxFacture.getSelectedItem().toString())) {
            type_operation = "Modif";
        }
        try {
            Facture d = setFactureFromEditText();
            //JOptionPane.showMessageDialog(null, s.toString());
            ConfigDao c = new ConfigDao();
            if (d != null) {
                if (c.DateCheckOnUpdate("facture", "date_facture", "num_facture", d.getNum_facture(), d.getDate_facture())) {
                    if (type_operation.equals("Client")) {
                        if (fdao.modifierFacture(d)) {
                            JOptionPane.showMessageDialog(null, "Facture " + txt_num_facture.getText() + " à été bien modifiée !");
                            ArrayList<ligne_facture> lstd;
                            lstd = setLigneDevisFromDevisTable();
                            fdao.modifierLigneFacture(lstd);
                            // fdao.setBLInvoiced(lstd);

                            setNumFacture();
                            clearDevisLFields();
                            lstBL = new Vector<Vector<Object>>();
                            lstBLexport = new Vector<Vector<Object>>();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error in CreateBL ActionPerformed ");
                        }
                    } else if (type_operation.equals("Modif")) {
                        if (fdao.modifierFacture(d)) {
                            JOptionPane.showMessageDialog(null, "Facture " + txt_num_facture.getText() + " à été bien modifiée !");
                            ArrayList<ligne_facture> lstd;
                            lstd = setLigneDevisFromDevisTable();
                            fdao.modifierLigneFacture(lstd);
                            fdao.setBLInvoiced(lstd);

                            setNumFacture();
                            clearDevisLFields();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error in CreateBL ActionPerformed ");
                        }
                    } else {

                        if (fdao.ajouterFacture(d)) {

                            ArrayList<ligne_facture> lstd;
                            lstd = setLigneDevisFromDevisTable();
                            fdao.ajouterLigneFacture(lstd);
                            fdao.setBLInvoiced(lstd);
                            JOptionPane.showMessageDialog(null, "Facture " + txt_num_facture.getText() + " à été bien enregistré !");

                            setNumFacture();
                            clearDevisLFields();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error in CreateBL ActionPerformed ");
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Condition Date n'a pas été acceptée ");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Verifier les champs obligatoire !");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in VLFrom/Ajouter_btn :  " + e);
        } finally {
            try {

            } catch (Exception ex) {
                Logger.getLogger(FactureForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButtonValiderActionPerformed
    void clearDevisLFields() {
        txt_search.setText("");
        Date_facture.setDate(new Date());
        Date_facture.setEnabled(false);
        txt_infos_facture.setText("");
        Vector<Vector<Object>> obj = null;
        tableModel = new DefaultTableModel(obj, columnNames);
        Table_facture.setModel(tableModel);
        txt_timbre.setText("0.000");
        CheckBox_timbre.setSelected(false);
        txt_Total_HT.setText("");
        txt_Total_Net.setText("");
        txt_Total_remise.setText("");
        txt_total_TVA.setText("");

        txt_adresse.setText("");

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
    JButton jbRecherchearticle;
    JTextField jtxtRecherchearticle;
    private void jButtonListeClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListeClientActionPerformed
        // TODO add your handling code here:
        /*  JFrame frame = new JFrame("Row Filter");
        frame.add(TestTableSortFilter());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);*/
        Vector<String> columnName = new Vector<String>();
        columnName.add("id");
        columnName.add("Nom");
        columnName.add("Adresse");

        ClientDao clientDao = new ClientDao();
        Vector<Vector<Object>> data1 = clientDao.afficherListClient();
        DefaultTableModel model = new DefaultTableModel(data1, columnName);
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

                DefaultTableModel model = new DefaultTableModel(data1, columnName);
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
                String s = d.getNameItemById("client", "numero_client", "nom", txt_search.getText());

                id_client = s;
                frameListeClient.dispose();
                TableModel tableModel = new DefaultTableModel(null, columnNames);
                BLHist_Table.setModel(tableModel);

                TableModel tableModel1 = new DefaultTableModel(null, columnNames);
                Table_facture.setModel(tableModel1);
                lstBLexport = new Vector<Vector<Object>>();
                lstBL = new Vector<Vector<Object>>();
                Date_facture.setDate(new Date());
                Date_facture.setEnabled(false);
                SearchBL();
            }
        });
    }//GEN-LAST:event_jButtonListeClientActionPerformed

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

    private void jButtonPlusCLientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPlusCLientActionPerformed

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
    }//GEN-LAST:event_jButtonPlusCLientActionPerformed

    private void ComboBoxFactureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxFactureActionPerformed
        if (combo != "0") {

            try {

                ArticleDao dart = new ArticleDao();

                setBLToModify(ComboBoxFacture.getSelectedItem().toString());
                //setLigneDevisFromDevisTableExport();

                String nom_and_codeTva = dart.getNameItemByIdJoinTables("facture", "num_facture", ComboBoxFacture.getSelectedItem().toString());
                String[] array = nom_and_codeTva.split(";");
                id_client = array[0];
                txt_search.setText(array[1]);
                //  txt_Code_TVA.setText(array[2]);
                txt_num_facture.setText(ComboBoxFacture.getSelectedItem().toString());

                String date = dart.getDateByNumFacture(ComboBoxFacture.getSelectedItem().toString());
                try {
                    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    Date_facture.setDate(date1);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            } catch (Exception e) {
            }
        }
        combo = "1";
// TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxFactureActionPerformed

    private void jButtonPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPlusActionPerformed
        // TODO add your handling code here:
        int row = BLHist_Table.getSelectedRow();

        lstBLexport.add(lstBL.get(row));
        lstBL.removeElementAt(row);

        TableModel tableModel = new DefaultTableModel(lstBL, columnNames);
        BLHist_Table.setModel(tableModel);

        TableModel tableModel1 = new DefaultTableModel(lstBLexport, columnNames);
        Table_facture.setModel(tableModel1);
        reCalculerTT();
    }//GEN-LAST:event_jButtonPlusActionPerformed
    Vector<Vector<Object>> lstBL;
    Vector<Vector<Object>> lstBLexport;
    private void jButtonMoinsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMoinsActionPerformed
        // TODO add your handling code here:
        int row = Table_facture.getSelectedRow();
        lstBL.add(lstBLexport.get(row));
        lstBLexport.removeElementAt(row);

        TableModel tableModel = new DefaultTableModel(lstBL, columnNames);
        BLHist_Table.setModel(tableModel);

        TableModel tableModel1 = new DefaultTableModel(lstBLexport, columnNames);
        Table_facture.setModel(tableModel1);
        reCalculerTT();
    }//GEN-LAST:event_jButtonMoinsActionPerformed

    private void jButtonPlusAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPlusAllActionPerformed
        // TODO add your handling code here:
        int i = lstBL.size();
        for (int j = 0; j < i; j++) {

            lstBLexport.add(lstBL.get(0));
            lstBL.removeElementAt(0);
        }
        TableModel tableModel = new DefaultTableModel(lstBL, columnNames);
        BLHist_Table.setModel(tableModel);

        TableModel tableModel1 = new DefaultTableModel(lstBLexport, columnNames);
        Table_facture.setModel(tableModel1);
        reCalculerTT();
    }//GEN-LAST:event_jButtonPlusAllActionPerformed

    private void jButtonMoinsAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMoinsAllActionPerformed
        // TODO add your handling code here:

        int i = lstBLexport.size();
        for (int j = 0; j < i; j++) {

            lstBL.add(lstBLexport.get(0));
            lstBLexport.removeElementAt(0);
        }
        TableModel tableModel = new DefaultTableModel(lstBL, columnNames);
        BLHist_Table.setModel(tableModel);

        TableModel tableModel1 = new DefaultTableModel(lstBLexport, columnNames);
        Table_facture.setModel(tableModel1);
        reCalculerTT();
    }//GEN-LAST:event_jButtonMoinsAllActionPerformed

    private void jButtonRechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRechercheActionPerformed
        TableModel tableModel = new DefaultTableModel(null, columnNames);
        BLHist_Table.setModel(tableModel);

        TableModel tableModel1 = new DefaultTableModel(null, columnNames);
        Table_facture.setModel(tableModel1);
        lstBLexport = new Vector<Vector<Object>>();
        lstBL = new Vector<Vector<Object>>();
        SearchBL();

        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRechercheActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

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

    private ArrayList<LigneBL> setLigneDevisFromDevisTableExport() {
        ArrayList<LigneBL> lst = new ArrayList();
        try {
            for (int j = 0; j < Table_facture.getRowCount(); j++) {
                LigneBL ld = new LigneBL();
                ld.setId_BL(txt_num_facture.getText());

                ld.setId_BL(ComboBoxFacture.getSelectedItem().toString());

                ld.setRef_article(Table_facture.getValueAt(j, 1).toString());
                ld.setDesign(Table_facture.getValueAt(j, 2).toString());
                ld.setPrix_U(Double.valueOf(Table_facture.getValueAt(j, 3).toString().replace(",", ".")));
                ld.setQte(Table_facture.getValueAt(j, 4).toString());
                ld.setTotal_HT(Double.valueOf(Table_facture.getValueAt(j, 5).toString().replace(",", ".")));
                ld.setRemise(Table_facture.getValueAt(j, 6).toString());
                ld.setTVA(Table_facture.getValueAt(j, 7).toString());
                ld.setTotale_TTC(Double.valueOf(Table_facture.getValueAt(j, 8).toString().replace(",", ".")));

                lst.add(ld);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        return lst;
    }

    public void setBLToModify(String Num_Devis) {
        DevisDao devisDao = new DevisDao();
        tableModel = new DefaultTableModel();
        tableModel = devisDao.afficherDetailFacture(Table_facture, Num_Devis, true);
        Table_facture.setModel(tableModel);
        reCalculerTT();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BLHist_Table;
    private javax.swing.JCheckBox CheckBox_timbre;
    private javax.swing.JComboBox<String> ComboBoxFacture;
    private com.toedter.calendar.JDateChooser Date_facture;
    private javax.swing.JTable Table_facture;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonExporterBL;
    private javax.swing.JButton jButtonListeClient;
    private javax.swing.JButton jButtonMoins;
    private javax.swing.JButton jButtonMoinsAll;
    private javax.swing.JButton jButtonPlus;
    private javax.swing.JButton jButtonPlusAll;
    private javax.swing.JButton jButtonPlusCLient;
    private javax.swing.JButton jButtonRecherche;
    private javax.swing.JButton jButtonValider;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_Total_HT;
    private javax.swing.JTextField txt_Total_Net;
    private javax.swing.JTextField txt_Total_remise;
    private javax.swing.JTextField txt_adresse;
    private javax.swing.JTextField txt_ajuster;
    private javax.swing.JTextField txt_infos_facture;
    private javax.swing.JTextField txt_num_facture;
    private javax.swing.JTextField txt_search;
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
        if (id_client != null && !id_client.equals("")) {
            r.setId_client(Integer.valueOf(id_client));
        } else {
            r.setId_client(0);
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
