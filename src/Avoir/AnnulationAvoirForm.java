/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Avoir;

import BL.*;
import Devis.*;
import BL.BLDao;
import Reglement.*;
import Home.App;
import Reglement.Avoir.ReglementAvoirClientFormDetail;
import Reglement.Avoir.ReglementAvoirFournisseurFormDetail;
import Reglement.Avoir.ReglementAvoirPassagerFormDetail;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mlek
 */
public class AnnulationAvoirForm extends javax.swing.JInternalFrame {

    /**
     * Creates new form ReglementForm
     */
    public AnnulationAvoirForm() {
         initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        txt_Res = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_detail = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_client = new javax.swing.JTextField();
        Label_client_passager = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txt_date = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_num_devis = new javax.swing.JTextField();
        txt_total = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Annulation Avoir");

        jButton1.setText("Valider");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Table_detail.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(Table_detail);

        jLabel2.setText("Date Avoir");

        txt_client.setEnabled(false);

        Label_client_passager.setText("Client");

        jLabel1.setText("Num Avoir");

        txt_date.setEnabled(false);

        jLabel4.setText("Total");

        txt_num_devis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_num_devisKeyPressed(evt);
            }
        });

        txt_total.setEnabled(false);

        jButton2.setText("Annuler");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_num_devis)
                    .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Label_client_passager)
                    .addComponent(jLabel4))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_client)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_num_devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_client, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Label_client_passager))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addGap(17, 17, 17)))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_Res)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jButton1)))
                .addGap(65, 65, 65))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(txt_Res))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                .addGap(105, 105, 105)
                .addComponent(jButton1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
/*
        String type_client = "";
        String type_reglement = "";

        if (RadioButton_facture.isSelected()) {
            type_reglement = "Facture";
        }
        if (RadioButton_Avoir.isSelected()) {
            type_reglement = "Avoir";
        }

        if (RadioButton_Client.isSelected()) {
            type_client = "Client";
        }
        if (RadioButton_Passager.isSelected()) {
            type_client = "Passager";
        }
        if (RadioButton_Fournisseur.isSelected()) {
            type_client = "Fournisseur";
        }

        if (type_client.isEmpty() || type_reglement.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Merci de verifier la selection !");
        } else if ((type_client.equals("Client")) && (type_reglement.equals("Facture"))) {
            ReglementFormDetail c;
            c = new ReglementFormDetail(type_client, type_reglement);

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
        } else if ((type_client.equals("Fournisseur")) && (type_reglement.equals("Facture"))) {
            ReglementFournisseurFormDetail c;
            c = new ReglementFournisseurFormDetail(type_client, type_reglement);

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
        } else if ((type_client.equals("Passager")) && (type_reglement.equals("Facture"))) {
            ReglementPassagerFormDetail c;
            c = new ReglementPassagerFormDetail(type_client, type_reglement);

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
        } else if ((type_client.equals("Client")) && (type_reglement.equals("Avoir"))) {
            ReglementAvoirClientFormDetail c;
            c = new ReglementAvoirClientFormDetail(type_client, type_reglement);

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
        } else if ((type_client.equals("Fournisseur")) && (type_reglement.equals("Avoir"))) {
            ReglementAvoirFournisseurFormDetail c;
            c = new ReglementAvoirFournisseurFormDetail(type_client, type_reglement);

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
        } else if ((type_client.equals("Passager")) && (type_reglement.equals("Avoir"))) {
            ReglementAvoirPassagerFormDetail c;
            c = new ReglementAvoirPassagerFormDetail(type_client, type_reglement);

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
        }*/
    }//GEN-LAST:event_jButton1ActionPerformed
    DefaultTableModel df;

    private void txt_num_devisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_num_devisKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            BLDao blDao = new BLDao();
            String table_click = txt_num_devis.getText();

            String s = blDao.afficherAvoirPourAnnuler(Table_detail, table_click);
            if (!s.isEmpty()) {
                df = blDao.afficherDetailAvoir(Table_detail, table_click);
                String[] x = s.split(";");
                if (x[3].equals("Passager")) {
                    Label_client_passager.setText("Passager");
                } else {
                    Label_client_passager.setText("Client");
                }
                txt_client.setText(x[2]);
                txt_total.setText(x[1]);
                txt_date.setText(x[0]);

            }
            //JOptionPane.showMessageDialog(this, s);
        }

    }//GEN-LAST:event_txt_num_devisKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (!txt_num_devis.getText().isEmpty()) {
            if (JOptionPane.showConfirmDialog(null, "Voulez-vous Annuler la Avoir Num : " + txt_num_devis.getText(), "Confirmation !!!",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                DevisDao devisDao = new DevisDao();
                devisDao.supprimerAvoir(txt_num_devis.getText());
                JOptionPane.showMessageDialog(this, "Avoir Num " + txt_num_devis.getText() + " à été Annulée ! ");
               
                ArrayList<LigneBL> lst = new ArrayList<LigneBL>();
                for (int i = 0; i < Table_detail.getRowCount(); i++) {
                    LigneBL b = new LigneBL();
                    b.setRef_article(Table_detail.getValueAt(i, 2).toString());
                    b.setQte(Table_detail.getValueAt(i, 4).toString());
                    lst.add(b);
                }
                devisDao.modifierStockBLAchatOnDelete(lst);
                
                txt_num_devis.setText("");
                txt_client.setText("");
                txt_total.setText("");
                txt_date.setText("");
                df.setRowCount(0);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Num Avoir est vide !");
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Label_client_passager;
    private javax.swing.JTable Table_detail;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel txt_Res;
    private javax.swing.JTextField txt_client;
    private javax.swing.JTextField txt_date;
    private javax.swing.JTextField txt_num_devis;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
