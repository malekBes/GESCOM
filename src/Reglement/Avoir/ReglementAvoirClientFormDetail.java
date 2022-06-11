/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reglement.Avoir;

import Reglement.*;
import Article.ArticleDao;
import Config.Bank.BankDao;
import Client.ClientDao;
import Facture.Facture;
import Facture.FactureDAO;
import Facture.FactureForm;
import Facture.ligne_facture;
import java.awt.BorderLayout;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

/**
 *
 * @author Mlek
 */
public class ReglementAvoirClientFormDetail extends javax.swing.JInternalFrame {

    JButton jbtValider;
    JFrame frameListeClient;
    String nomclient;
    String id_client = "";
    ArrayList<String> listNomSte;
    ClientDao clientDao = new ClientDao();
    String numFacture = "";
    String TTC;
    HashMap<String, String> fournisseurHashMap;

    /**
     * Creates new form ReglementFormDetail
     */
    public ReglementAvoirClientFormDetail(String type_client, String type_reglement) {
         initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        listNomSte = clientDao.afficherClient();
        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        BankDao Bank = new BankDao();
        fournisseurHashMap = Bank.listBank(ComboBox_Bank);
        typeReglemenntComboBox();
    }

    public void typeReglemenntComboBox() {
        ArticleDao article = new ArticleDao();
        article.listTypeReglement(ComboBox_type_reglement);
    }

    private void update_table() {
        ReglementDAO reglementDAO = new ReglementDAO();

        reglementDAO.afficherTableReglement(Table_reglement, numFacture);

        Double total_regle_table = 0.0;
        for (int i = 0; i < Table_reglement.getModel().getRowCount(); i++) {
            String value = Table_reglement.getModel().getValueAt(i, 8).toString();
            total_regle_table += Double.valueOf(value);
        }
        txt_m_regle_total.setText(String.valueOf(total_regle_table));
        txt_m_restant.setText(Config.Commen_Proc.formatDouble(Double.valueOf(TTC) - total_regle_table));

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

    public void clearTable() {
        DefaultTableModel dm = (DefaultTableModel) Table_reglement.getModel();
        int rowCount = dm.getRowCount();
//Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
        }
    }

    public void lstFacture() {
        // TODO add your handling code here:
        /*  JFrame frame = new JFrame("Row Filter");
        frame.add(TestTableSortFilter());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);*/
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("id");
        columnNames.add("Num Avoir");
        columnNames.add("TTC");

        FactureDAO FactureDao = new FactureDAO();
        Vector<Vector<Object>> data1 = FactureDao.afficherListAvoir(id_client);
        DefaultTableModel model = new DefaultTableModel(data1, columnNames);

        for (int j = 0; j < model.getRowCount(); j++) {
            Object PU = model.getValueAt(j, 2);
            model.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 2);
        }

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

        frameListeClient = new JFrame("Liste Factures");
        frameListeClient.add(Homepanel);
        frameListeClient.pack();
        frameListeClient.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        frameListeClient.setLocationRelativeTo(null);
        frameListeClient.setVisible(true);
        jbtValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numFacture = jTable.getModel().getValueAt(jTable.getSelectedRow(), 1).toString();
                txt_facture.setText(numFacture);
                TTC = jTable.getModel().getValueAt(jTable.getSelectedRow(), 2).toString();
                txt_m_total_facture.setText(TTC);
                txt_m_regle_total.setText("0");
                txt_m_restant.setText("0");

                if (!numFacture.isEmpty()) {
                    update_table();
                } else {
                    clearTable();
                }
                /* ArticleDao d = new ArticleDao();
                String s = d.getNameItemById("client", "numero_client", "nom", txt_search.getText());
                id_client = s;*/

                frameListeClient.dispose();
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

        jPanel1 = new javax.swing.JPanel();
        txt_search = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        txt_facture = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_m_total_facture = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_m_regle_total = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_m_restant = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_avoir = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        ComboBox_type_reglement = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txt_m_regle = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_m_compta = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        Date_echeance = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        Date_reglement = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_num_cheque = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_num_avoir = new javax.swing.JTextField();
        ComboBox_Bank = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_reglement = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_avoir = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btn_valider = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setTitle("Reglement Avoir Client");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Info client"));

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

        jLabel2.setText("Avoir");

        txt_m_total_facture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_m_total_factureActionPerformed(evt);
            }
        });

        jLabel3.setText("Montant Total Facture");

        jLabel4.setText("Montant Réglé");

        jLabel5.setText("Montant Réstant");

        jLabel6.setText("Total factures encours");

        jButton2.setText("Liste");
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(72, 72, 72)
                        .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_m_total_facture)
                            .addComponent(txt_m_restant))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addGap(67, 67, 67)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(40, 40, 40)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_m_regle_total)
                            .addComponent(txt_avoir)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2)
                        .addGap(55, 55, 55)
                        .addComponent(txt_facture, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton7)
                    .addComponent(txt_facture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_m_total_facture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_m_restant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_m_regle_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_avoir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addGap(17, 17, 17))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Réglement"));

        ComboBox_type_reglement.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Espece", "Cheque", "Effet", "Virement", "Avoir", "Ristourne TVA", "Ristourne", "Retenue à la source" }));

        jLabel7.setText("Type réglement");

        jLabel8.setText("Montant Réglé");

        jLabel9.setText("Montant Comptabilisé");

        jLabel10.setText("Date échéance");

        jLabel11.setText("Date Régl.");

        jLabel12.setText("Banque");

        jLabel13.setText("Numéro Cheque");

        jLabel14.setText("Numéro Facture");

        ComboBox_Bank.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Espece", "Cheque", "Effet", "Virement", "Avoir", "Ristourne TVA", "Ristourne", " " }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ComboBox_type_reglement, 0, 129, Short.MAX_VALUE)
                            .addComponent(Date_echeance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8)))
                    .addComponent(txt_num_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_m_regle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Date_reglement, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_m_compta, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(40, 40, 40)
                                .addComponent(ComboBox_Bank, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(2, 2, 2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(26, 26, 26)
                        .addComponent(txt_num_avoir, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox_type_reglement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txt_m_regle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txt_m_compta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Date_echeance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(Date_reglement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(ComboBox_Bank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_num_avoir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_num_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        Table_reglement.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(Table_reglement);

        Table_avoir.setModel(new javax.swing.table.DefaultTableModel(
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
        Table_avoir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_avoirMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table_avoir);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions"));

        btn_valider.setText("Valider");
        btn_valider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_validerActionPerformed(evt);
            }
        });

        jButton1.setText("Quitter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_valider)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_valider)
                    .addComponent(jButton1))
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(337, 337, 337)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(726, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                ArticleDao d = new ArticleDao();
                String s = d.getNameItemById("client", "numero_client", "nom", txt_search.getText());
                id_client = s;

                ReglementDAO r = new ReglementDAO();
                df_avoir = r.afficherTableFactureClient(Table_avoir, id_client);
                Double tot = 0.0;
                for (int j = 0; j < Table_avoir.getRowCount(); j++) {
                    Object PU = df_avoir.getValueAt(j, 3);
                    tot += Double.parseDouble(PU.toString());
                }
                txt_avoir.setText(Config.Commen_Proc.formatDouble(tot));

                frameListeClient.dispose();
            }
        });
    }//GEN-LAST:event_jButton7ActionPerformed
    DefaultTableModel df_avoir;
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

    private void txt_m_total_factureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_m_total_factureActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_m_total_factureActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (id_client.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez choisir un client ! ");
        } else {
            lstFacture();

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_validerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_validerActionPerformed
        try {
            Reglement d = setReglementFromEditText();
            // JOptionPane.showMessageDialog(this, d.toString());

            FactureDAO daofacture = new FactureDAO();
            ReglementDAO fdao = new ReglementDAO();
            if (d != null) {
                String str = d.getDate_reglement();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date_reglement = format.parse(str);
                String dt = daofacture.getDateAvoir(txt_facture.getText());
                Date date_facture = new SimpleDateFormat("yyyy-MM-dd").parse(dt);
                //date_reglement is after date_facture
                if ((date_reglement.compareTo(date_facture) > 0) || (date_reglement.compareTo(date_facture) == 0)) {

                    Double total_regle_table = 0.0;
                    for (int i = 0; i < Table_reglement.getModel().getRowCount(); i++) {
                        String value = Table_reglement.getModel().getValueAt(i, 7).toString();
                        total_regle_table += Double.valueOf(value);
                    }
                    Double x = total_regle_table + Double.valueOf(txt_m_regle.getText());
                    if (x <= Double.valueOf(txt_m_total_facture.getText())) {

                        if (fdao.ajouterReglementAvoir(d)) {

                            JOptionPane.showMessageDialog(null, "Reglemnet à été bien enregistré !");
                            update_table();
                            if (Double.parseDouble(txt_m_restant.getText()) == 0.0) {
                                daofacture.setAvoirReglement(txt_facture.getText(), "Oui");
                                clearForm("all");
                            } else {
                                daofacture.setAvoirReglement(txt_facture.getText(), "Semi");
                                clearForm("semi");
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Error in Reglement btn_valider ");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Vous avez dépassé le montant total de la facture !  ");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Date Réglement est inferieur a la date facture ");
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

    }//GEN-LAST:event_btn_validerActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton1ActionPerformed

    private void Table_avoirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_avoirMouseClicked
        // TODO add your handling code here:*
        int row = Table_avoir.getSelectedRow();
        if (row != -1) {
            String value = Table_avoir.getModel().getValueAt(row, 1).toString();
            txt_num_avoir.setText(value);
        }
    }//GEN-LAST:event_Table_avoirMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBox_Bank;
    private javax.swing.JComboBox<String> ComboBox_type_reglement;
    private com.toedter.calendar.JDateChooser Date_echeance;
    private com.toedter.calendar.JDateChooser Date_reglement;
    private javax.swing.JTable Table_avoir;
    private javax.swing.JTable Table_reglement;
    private javax.swing.JButton btn_valider;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_avoir;
    private javax.swing.JTextField txt_facture;
    private javax.swing.JTextField txt_m_compta;
    private javax.swing.JTextField txt_m_regle;
    private javax.swing.JTextField txt_m_regle_total;
    private javax.swing.JTextField txt_m_restant;
    private javax.swing.JTextField txt_m_total_facture;
    private javax.swing.JTextField txt_num_avoir;
    private javax.swing.JTextField txt_num_cheque;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables

    public Reglement setReglementFromEditText() {

        Reglement r = new Reglement();

        // try {
        if (id_client != null) {
            r.setId_client(Integer.valueOf(id_client));
        } else {
            r.setId_client(0);
            return null;
        }

        if (numFacture != null) {
            r.setNum_facture(numFacture);
        } else {
            r.setNum_facture("0");
            return null;
        }

        if (ComboBox_type_reglement.getSelectedItem() != "-") {
            r.setType_reglement(ComboBox_type_reglement.getSelectedItem().toString());
        } else {
            r.setType_reglement("-");
            return null;
        }

        if (!ComboBox_Bank.getSelectedItem().toString().equals("-")) {
            r.setBanque(ComboBox_Bank.getSelectedItem().toString());
        } else {
            r.setBanque("0");
            if (r.getType_reglement().equals("Cheque")) {
                return null;
            }
        }

        if (!txt_m_regle.getText().isEmpty()) {
            r.setRegle(Double.parseDouble(txt_m_regle.getText()));
        } else {
            r.setRegle(0.0);
            return null;
        }

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd");

        try {

            String currentTime = sdf.format(Date_reglement.getDate());

            r.setDate_reglement(currentTime);

        } catch (Exception e) {
            r.setDate_reglement("");
        }

        try {

            String currentTime1 = sdf.format(Date_echeance.getDate());

            r.setDate_echeance(currentTime1);
        } catch (Exception e) {
            r.setDate_echeance("");

        }

        if (!txt_num_avoir.getText().isEmpty()) {
            r.setNum_avoir(txt_num_avoir.getText());
        } else {
            r.setNum_avoir("0");
        }

        if (!txt_num_cheque.getText().isEmpty()) {
            r.setNum_cheque(txt_num_cheque.getText());
        } else {
            r.setNum_cheque("0");
            if (r.getType_reglement().equals("Cheque")) {
                return null;
            }
        }
        if (!txt_m_compta.getText().isEmpty()) {
            r.setComptabilise(Double.valueOf(txt_m_compta.getText()));
        } else {
            r.setComptabilise(0.0);
        }

        /* } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println(e);

        }*/
        return r;
    }

    private void clearForm(String s) {
        ComboBox_Bank.setSelectedIndex(0);
        ComboBox_type_reglement.setSelectedIndex(0);
        txt_m_regle.setText("");
        Date_reglement.setCalendar(null);
        Date_echeance.setCalendar(null);
        txt_num_avoir.setText("");
        txt_num_cheque.setText("");
        txt_m_compta.setText("");
        if (s == "all") {
            txt_facture.setText("");
            txt_m_total_facture.setText("");
            txt_m_restant.setText("");
            txt_m_regle_total.setText("");
        }

    }

}
