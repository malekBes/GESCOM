/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospection;

import avtivite.*;
import Facture.*;
import Article.ArticleDao;
import Article.ArticleDao;
import BL.FormBL;
import BL.HistoriqueDevis;
import Client.ClientDao;
import Config.Commen_Proc;
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
public class ProspectionGroupeArticleForm extends javax.swing.JInternalFrame {

    FactureDAO factureDAO;
    ArrayList<String> listNomSte;
    ClientDao clientDao;
    Vector<Vector<Object>> lstBL;
    Vector<Vector<Object>> lstBLexport;
    Vector<String> columnNames;
    JButton jbtValider;
    JFrame frameListeClient;
    String nomclient;
    String id_client = "";

    /**
     * Creates new form HistoriqueBLForm
     */
    public ProspectionGroupeArticleForm() {

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
        columnNames.add("Ref Article");
        columnNames.add("Designation Article");
        /*  columnNames.add("Num BL");
        columnNames.add("Total HT");
        columnNames.add("Remise");
        columnNames.add("TVA");
        columnNames.add("TTC");*/
        //   SearchBL();

        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        jButton5.setVisible(false);
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
        jLabel8 = new javax.swing.JLabel();
        txt_num_devis = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        txt_nom_groupe = new javax.swing.JTextField();
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
        setTitle("Affectation Artilces -> Activités");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtre"));

        jLabel8.setText("Nom Groupe Article");

        txt_num_devis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_num_devisKeyPressed(evt);
            }
        });

        jLabel9.setText("Ref Article");

        jButton8.setText("Liste");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Valider");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        txt_nom_groupe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nom_groupeKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8))
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_num_devis, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                    .addComponent(txt_nom_groupe))
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addGap(341, 341, 341))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(369, 369, 369))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_num_devis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jButton8))
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_nom_groupe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jButton9))
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
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addGap(442, 442, 442))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(381, Short.MAX_VALUE))
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
                        .addGap(82, 82, 82)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
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
            ArticleDao a = new ArticleDao();

            lstBL = a.afficherArticle(BLHist_Table, txt_num_devis.getText(),
                    "");

            tableModel = new DefaultTableModel(lstBL, columnNames);
            BLHist_Table.setModel(tableModel);
            // formatTableValues();
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
        String value = BLHistExport_Table.getModel().getValueAt(row, 0).toString();
        ArticleDao a = new ArticleDao();
        a.updateArticleClassificationParRef(Type_article, value);

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
    public void getArticleByClient(String id_client) {

        ArticleDao a = new ArticleDao();
        lstBLexport = a.afficherArticleAffecter(BLHistExport_Table,
                id_client);

        tableModel = new DefaultTableModel(lstBLexport, columnNames);
        BLHistExport_Table.setModel(tableModel);
    }

    public void getArticleByTypeArticle(String type) {

        ArticleDao a = new ArticleDao();
        lstBLexport = a.afficherArticleAvtivite(BLHistExport_Table,
                type);

        tableModel = new DefaultTableModel(lstBLexport, columnNames);
        BLHistExport_Table.setModel(tableModel);
    }
    String Type_article = "";
    private void txt_num_devisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_num_devisKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchBL();
        }
    }//GEN-LAST:event_txt_num_devisKeyPressed
    JButton jbtValiderarticle;
    JButton jbRecherchearticle;
    JTextField jtxtRecherchearticle;
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
                txt_num_devis.setText(nomclient);
                String ref = nomclient;
                // String s = jTable.getModel().getValueAt(jTable.getSelectedRow(), 2).toString();
                //   ArticleDao d = new ArticleDao();
                //    String design = s;
                //  String tva = d.getNameItemById("article", "tva", "ref", txt_searchArticle.getText());
                //  String pu = jTable.getModel().getValueAt(jTable.getSelectedRow(), 3).toString();

                //   String remise = d.getRemiseById(id_client, ref);
                //     txt_remise.setText(remise.isEmpty() ? "" : remise);
                //      txt_design_article.setText(design);
                //      txt_TVA.setText(tva);
                //      txt_Prix_U.setText(pu);
                SearchBL();
                frameListeClient.dispose();
            }
        });
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        try {
            if (!txt_nom_groupe.getText().isEmpty()) {
                ArticleDao a = new ArticleDao();
                a.updateArticleProspection(txt_nom_groupe.getText(), lstBLexport);

                JOptionPane.showMessageDialog(this, "Article Affecté");

            } else {
                JOptionPane.showMessageDialog(this, "Nom groupe est obligatoire");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

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

    private void txt_nom_groupeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nom_groupeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nom_groupeKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BLHistExport_Table;
    private javax.swing.JTable BLHist_Table;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_nom_groupe;
    private javax.swing.JTextField txt_num_devis;
    // End of variables declaration//GEN-END:variables
}
