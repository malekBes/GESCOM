/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facture;

import Article.ArticleDao;
import BL.FormBL;
import BL.HistoriqueDevis;
import Client.ClientDao;
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
public class HistoriqueBLForm extends javax.swing.JInternalFrame {

    FactureDAO factureDAO;
    ArrayList<String> listNomSte;
    ClientDao clientDao;
    Vector<Vector<Object>> lstBL;
    Vector<Vector<Object>> lstBLexport;
    Vector<String> columnNames;
    JButton jbtValider;
    JFrame frameListeClient;
    String nomclient;

    /**
     * Creates new form HistoriqueBLForm
     */
    public HistoriqueBLForm() {

         initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        factureDAO = new FactureDAO();
        clientDao = new ClientDao();
        listNomSte = new ArrayList<>();
        listNomSte = clientDao.afficherClient();
        lstBLexport = new Vector<Vector<Object>>();
        columnNames = new Vector<String>();
        columnNames.add("Nom Client");
        columnNames.add("Date BL");
        columnNames.add("Num BL");
        columnNames.add("Total HT");
        columnNames.add("Remise");
        columnNames.add("TVA");
        columnNames.add("TTC");
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

    public void formatTableValues() {

        for (int j = 0; j < BLHist_Table.getRowCount(); j++) {
            Object PU = tableModel.getValueAt(j, 3);
            tableModel.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 3);
            Object HT = tableModel.getValueAt(j, 4);
            tableModel.setValueAt(formatDouble(Double.parseDouble(HT.toString())), j, 4);
            Object TTC = tableModel.getValueAt(j, 5);
            tableModel.setValueAt(formatDouble(Double.parseDouble(TTC.toString())), j, 5);
            Object remise = tableModel.getValueAt(j, 6);
            tableModel.setValueAt(formatDouble(Double.parseDouble(remise.toString())), j, 6);

            // JOptionPane.showMessageDialog(null, " index : " + j + " value : " + s);
            // tableModel.setValueAt(j + 1, j, 0);
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

        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Date_Devis = new com.toedter.calendar.JDateChooser();
        Date_Devis_To = new com.toedter.calendar.JDateChooser();
        txt_montant_from = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_search_client = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_num_devis = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_montant_to = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        BLHist_Table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        BLHistExport_Table = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setClosable(true);
        setResizable(true);
        setTitle("Historique BL");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtre"));

        jLabel3.setText("Date BL");

        txt_montant_from.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_montant_fromKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_montant_fromKeyReleased(evt);
            }
        });

        jLabel7.setText("Montant");

        txt_search_client.setBackground(new java.awt.Color(153, 204, 255));
        txt_search_client.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_clientActionPerformed(evt);
            }
        });
        txt_search_client.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_search_clientKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_clientKeyReleased(evt);
            }
        });

        jLabel8.setText("Nom Client");

        txt_num_devis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_num_devisKeyPressed(evt);
            }
        });

        jLabel9.setText("Num BL");

        txt_montant_to.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_montant_toKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_montant_toKeyReleased(evt);
            }
        });

        jButton1.setText("Rehercher");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton7.setText("Liste");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(162, 162, 162)
                                .addComponent(txt_montant_to, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(txt_search_client))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_montant_from, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Date_Devis, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                                    .addComponent(txt_num_devis))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Date_Devis_To, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(34, 34, 34))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_num_devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(Date_Devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Date_Devis_To, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_montant_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txt_montant_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search_client, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jButton1)
                    .addComponent(jButton7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
        jScrollPane1.setViewportView(BLHist_Table);

        BLHistExport_Table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(BLHistExport_Table);

        jButton2.setText("<");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText(">");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText(">>");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("<<");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Exporter Vers Facture");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addGap(442, 442, 442))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(263, 263, 263)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
TableModel tableModel;

    public void SearchBL() {
        try {
            String FromDate = "";
            String ToDate = "";

            try {
                String dt = Date_Devis_To.getDate().toString();
                if (dt.isEmpty() & Date_Devis.getDate().toString().isEmpty()) {
                } else {
                    Date To = Date_Devis_To.getDate();
                    Date From = Date_Devis.getDate();
                    SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd");
                    FromDate = tdate.format(From);
                    ToDate = tdate.format(To);
                }

            } catch (Exception e) {
                System.out.println("date Null");
            }
            lstBL = factureDAO.afficherBL(BLHist_Table, FromDate,
                    ToDate, txt_montant_from.getText(), txt_montant_to.getText(), txt_num_devis.getText(),
                    txt_search_client.getText());
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

    private void txt_montant_fromKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montant_fromKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchBL();
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

    private void txt_search_clientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_clientActionPerformed

    }//GEN-LAST:event_txt_search_clientActionPerformed

    private void txt_search_clientKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_clientKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchBL();
        }
    }//GEN-LAST:event_txt_search_clientKeyPressed

    private void txt_search_clientKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_clientKeyReleased
        //cDao.searchClient(client_table, txt_search.getText());

        if (evt.getKeyCode() == evt.VK_BACK_SPACE || evt.getKeyCode() == evt.VK_DELETE) {

        } else {
            String to_check = txt_search_client.getText().toLowerCase();
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
                    txt_search_client.setText(data);
                    txt_search_client.setSelectionStart(to_check_len);
                    txt_search_client.setSelectionEnd(data.length());
                    break;
                }
            }
        }
    }//GEN-LAST:event_txt_search_clientKeyReleased

    private void txt_num_devisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_num_devisKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchBL();
        }
    }//GEN-LAST:event_txt_num_devisKeyPressed

    private void txt_montant_toKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montant_toKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchBL();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_montant_toKeyPressed

    private void txt_montant_toKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montant_toKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_montant_toKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        SearchBL();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int row = BLHist_Table.getSelectedRow();

        lstBLexport.add(lstBL.get(row));
        lstBL.removeElementAt(row);

        TableModel tableModel = new DefaultTableModel(lstBL, columnNames);
        BLHist_Table.setModel(tableModel);

        TableModel tableModel1 = new DefaultTableModel(lstBLexport, columnNames);
        BLHistExport_Table.setModel(tableModel1);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int row = BLHistExport_Table.getSelectedRow();
        lstBL.add(lstBLexport.get(row));
        lstBLexport.removeElementAt(row);

        TableModel tableModel = new DefaultTableModel(lstBL, columnNames);
        BLHist_Table.setModel(tableModel);

        TableModel tableModel1 = new DefaultTableModel(lstBLexport, columnNames);
        BLHistExport_Table.setModel(tableModel1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int i = lstBL.size();
        for (int j = 0; j < i; j++) {

            lstBLexport.add(lstBL.get(0));
            lstBL.removeElementAt(0);
        }
        TableModel tableModel = new DefaultTableModel(lstBL, columnNames);
        BLHist_Table.setModel(tableModel);

        TableModel tableModel1 = new DefaultTableModel(lstBLexport, columnNames);
        BLHistExport_Table.setModel(tableModel1);


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:

        int i = lstBLexport.size();
        for (int j = 0; j < i; j++) {

            lstBL.add(lstBLexport.get(0));
            lstBLexport.removeElementAt(0);
        }
        TableModel tableModel = new DefaultTableModel(lstBL, columnNames);
        BLHist_Table.setModel(tableModel);

        TableModel tableModel1 = new DefaultTableModel(lstBLexport, columnNames);
        BLHistExport_Table.setModel(tableModel1);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        FactureForm c;
        if (lstBLexport.isEmpty()) {
            c = new FactureForm(lstBLexport, "false", "");
        } else {
            c = new FactureForm(lstBLexport, "true", "");
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setFocusable(true);
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
        this.dispose();

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
                txt_search_client.setText(nomclient);
                frameListeClient.dispose();
            }
        });
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BLHistExport_Table;
    private javax.swing.JTable BLHist_Table;
    private com.toedter.calendar.JDateChooser Date_Devis;
    private com.toedter.calendar.JDateChooser Date_Devis_To;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_montant_from;
    private javax.swing.JTextField txt_montant_to;
    private javax.swing.JTextField txt_num_devis;
    private javax.swing.JTextField txt_search_client;
    // End of variables declaration//GEN-END:variables
}
