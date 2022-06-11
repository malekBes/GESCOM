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
import Home.App;
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
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Mlek
 */
public class RecherchBLDepotForm extends javax.swing.JInternalFrame {

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
    public RecherchBLDepotForm() {
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
        //jButton4.setVisible(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
     /*   depotComboBox();
        depotdestComboBox();*/
        update_table();
        SearchDevis();

        //TestTableSortFilter();
    }

    HashMap<String, String> depotHashMap;
    HashMap<String, String> depotDestHashMap;

   /* public void depotComboBox() {
        ArticleDao article = new ArticleDao();
        depotHashMap = article.listDepot(ComboBox_Depot);
    }

    public void depotdestComboBox() {
        ArticleDao article = new ArticleDao();
        depotDestHashMap = article.listDepot(ComboBox_Depot_dest);
    }*/

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
                Logger.getLogger(RecherchBLDepotForm.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DevisHistDetail_Table = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DevisHist_Table = new javax.swing.JTable();

        setClosable(true);
        setTitle("Consultation BL Dépot");

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
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1095, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Action"));

        jButton3.setText("Editer");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jButton3))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(139, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(256, Short.MAX_VALUE))
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

//            String currentTime = sdf.format(Date_Devis.getDate());
            //r.setDate_bl(currentTime);

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
      /*  int row = DevisHistDetail_Table.getSelectedRow();
        String ref = DevisHist_Table.getModel().getValueAt(row, 1).toString();
        String design = DevisHist_Table.getModel().getValueAt(row, 2).toString();
        String prix_u = DevisHist_Table.getModel().getValueAt(row, 3).toString();
        String qte = DevisHist_Table.getModel().getValueAt(row, 4).toString();
        String remise = DevisHist_Table.getModel().getValueAt(row, 6).toString();
        String tva = DevisHist_Table.getModel().getValueAt(row, 7).toString();*/

        // 6 remise 7 tvA

    }//GEN-LAST:event_DevisHistDetail_TableMouseClicked
    public void SearchDevis() {
        try {
            String FromDate = "";
            String ToDate = "";

           /* try {
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
            ArticleDao daoArticle = new ArticleDao();

            String id_depot_src = "";

            String depot = ComboBox_Depot.getSelectedItem().toString();
            if (!depot.equals("-")) {
                id_depot_src = daoArticle.getNameItemById("depot", "id", "nom", depot);
            }

            String id_depot_dest = "";
            String depot_dest = ComboBox_Depot_dest.getSelectedItem().toString();
            if (!depot_dest.equals("-")) {
                id_depot_dest = daoArticle.getNameItemById("depot", "id", "nom", depot_dest);
            }
*/
            rechercheDao.afficherBLDepot(DevisHist_Table, "",
                    "", "", "", "");
            setHeaders();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in HistoriqueBLFrom/SearchBL : \n" + e);
        } finally {
            try {

                System.out.println("disconnected");
            } catch (Exception ex) {
                Logger.getLogger(RecherchBLDepotForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void setHeaders() {
        DevisHist_Table.getColumnModel().getColumn(0).setHeaderValue("Dépot Source");
        DevisHist_Table.getColumnModel().getColumn(1).setHeaderValue("Dépot Destination");

        DevisHist_Table.getColumnModel().getColumn(2).setHeaderValue("Date BL");
        DevisHist_Table.getColumnModel().getColumn(3).setHeaderValue("Total TTC");

        DevisHist_Table.getTableHeader().resizeAndRepaint();

    }
    public String tab_DevisHist;
    private void DevisHist_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DevisHist_TableMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_DevisHist_TableMouseClicked
    private void setHeadersDetail() {
        DevisHistDetail_Table.getColumnModel().getColumn(0).setHeaderValue("id");
        DevisHistDetail_Table.getColumnModel().getColumn(1).setHeaderValue("Num BL Dépot");
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
    private void DevisHist_TableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DevisHist_TableMousePressed
        // TODO add your handling code here:
        try {
            BLDao devisDao = new BLDao();
            FormBL fr = new FormBL("", "","");
            int row = DevisHist_Table.getSelectedRow();
            String table_click = DevisHist_Table.getModel().getValueAt(row, 2).toString();
            devisDao.afficherDetailBLDepot(DevisHistDetail_Table, table_click);

            ClientDao clientDao = new ClientDao();
           // txt_num_devis.setText(table_click);
            listNomSte = clientDao.afficherClient();
            setHeadersDetail();
            //client_table.setModel(buildTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in ClientFrom/load_Update_table :  " + e);
        } finally {
            try {

                System.out.println("disconnected");
            } catch (Exception ex) {
                Logger.getLogger(RecherchBLDepotForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_DevisHist_TableMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable DevisHistDetail_Table;
    private javax.swing.JTable DevisHist_Table;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
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

}
