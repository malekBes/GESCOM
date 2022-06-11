/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Article;

import Donnee.*;
import Article.*;
import Client.Client;
import Client.ClientDao;
import Commercial.CommercialDao;
import Config.Commen_Proc;
import Conn.DataBase_connect;
import java.awt.BorderLayout;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
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
import javax.swing.RowFilter;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
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
public class ListArticleComparativeForm extends javax.swing.JInternalFrame {

    HashMap<String, String> familleHashMap;
    HashMap<String, String> sousFamilleHashMap;
    HashMap<String, String> fournisseurHashMap;
    HashMap<String, String> marqueHashMap;

    ArticleDao daoArticle = new ArticleDao();
    ArrayList<String> listNomSte;

    /**
     * Creates new form ArticleForm
     */
    public ListArticleComparativeForm() {
        initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        jPanel4.setVisible(false);
        setComboBox();
        //SearchStockArticle();
        listNomSte = daoArticle.afficherArticle();
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

    public void typeArticleComboBox() {
        ArticleDao article = new ArticleDao();
        article.listTypeArticle(ComboBoxTypeArticle);
    }

    public void SearchStockArticle() {
        try {
            Article r = setArticleFromEditText();

            ArticleDao articleDao = new ArticleDao();

            String type_article = "";
            if (!ComboBoxTypeArticle.getSelectedItem().toString().equals("-")) {
                type_article = ComboBoxTypeArticle.getSelectedItem().toString();
            }
            articleDao.afficherListeArticleComparatif(Table_Article, r.getId_depot(), r.getId_famille(), r.getId_sous_famille(), r.getDesignation(),
                    r.getRef(), r.getMarque(), type_article);

            //setHeaders();
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error in HistoriqueDevisFrom/SearchDevis : \n" + e);
        } finally {
            try {

                System.out.println("disconnected");
            } catch (Exception ex) {
                Logger.getLogger(ListArticleComparativeForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void resetComboBox() {

        ComboBoxSous_famille.removeAllItems();
        ComboBoxSous_famille.addItem("-");
        ComboBox_famille.removeAllItems();
        ComboBox_famille.addItem("-");
        ComboBox_marque.removeAllItems();
        ComboBox_marque.addItem("-");

    }

    public void setComboBox() {
        resetComboBox();
        familleComboBox();
        depotComboBox();
        marqueComboBox();
        typeArticleComboBox();
    }

    public void familleComboBox() {
        ArticleDao article = new ArticleDao();
        familleHashMap = article.listfamille(ComboBox_famille);
    }

    public void sousFamilleComboBox(String id) {
        ArticleDao article = new ArticleDao();
        sousFamilleHashMap = article.listSousFamille(ComboBoxSous_famille, id);
    }

    public void marqueComboBox() {
        ArticleDao article = new ArticleDao();
        marqueHashMap = article.listMarque(ComboBox_marque);
    }

    public void depotComboBox() {
        ArticleDao article = new ArticleDao();
        marqueHashMap = article.listDepot(ComboBox_Depot);
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
        ComboBox_famille = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ComboBoxSous_famille = new javax.swing.JComboBox<>();
        txt_ref = new javax.swing.JTextField();
        ComboBox_marque = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_desgination = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        ComboBox_Depot = new javax.swing.JComboBox<>();
        ComboBoxTypeArticle = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txt_searchArticle = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Article = new javax.swing.JTable();

        setClosable(true);
        setResizable(true);
        setTitle("Tableau comparatif");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Article"));

        ComboBox_famille.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboBox_famille.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboBox_familleItemStateChanged(evt);
            }
        });
        ComboBox_famille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBox_familleActionPerformed(evt);
            }
        });

        jLabel1.setText("Famille *");

        jLabel2.setText("Sous Famille *");

        jLabel3.setText("Référence *");

        ComboBoxSous_famille.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboBoxSous_famille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxSous_familleActionPerformed(evt);
            }
        });

        txt_ref.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_refKeyPressed(evt);
            }
        });

        ComboBox_marque.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboBox_marque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBox_marqueActionPerformed(evt);
            }
        });

        jLabel4.setText("Marque *");

        jLabel6.setText("Dépot");

        txt_desgination.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_desginationKeyPressed(evt);
            }
        });

        jLabel9.setText("Désignation *");

        ComboBox_Depot.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboBox_Depot.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboBox_DepotItemStateChanged(evt);
            }
        });
        ComboBox_Depot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBox_DepotActionPerformed(evt);
            }
        });

        jLabel8.setText("Type Article (RFT/C...)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboBox_Depot, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboBox_famille, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ComboBoxSous_famille, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ComboBox_marque, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ref, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_desgination, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 27, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(ComboBoxTypeArticle, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboBox_Depot, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(ComboBox_famille, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_ref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_desgination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxSous_famille)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBox_marque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBoxTypeArticle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(5, 5, 5))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recherche", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 51, 153))); // NOI18N

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(165, 165, 165))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_searchArticle, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txt_searchArticle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Actions", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jButton4.setText("Editer");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Recherche");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Articles", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        Table_Article.setModel(new javax.swing.table.DefaultTableModel(
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
        Table_Article.setMaximumSize(new java.awt.Dimension(500, 64));
        Table_Article.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_ArticleMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Table_ArticleMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(Table_Article);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ComboBox_familleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboBox_familleItemStateChanged
        // TODO add your handling code here:


    }//GEN-LAST:event_ComboBox_familleItemStateChanged

    private void ComboBox_familleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox_familleActionPerformed

        // TODO add your handling code here:
        try {

            String s = familleHashMap.get(ComboBox_famille.getSelectedItem().toString());
            sousFamilleComboBox(s);
        } catch (Exception e) {
        }
        //  SearchStockArticle();
    }//GEN-LAST:event_ComboBox_familleActionPerformed

    private void txt_searchArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchArticleActionPerformed

    }//GEN-LAST:event_txt_searchArticleActionPerformed
    public void setFieldsByName(String table_click) {

        Article c = new Article();
        c = daoArticle.getArticleByName(table_click);
        if (c.getRef() == "0") {
            JOptionPane.showMessageDialog(null, " Verifier le Nom de la société ");
            // clearTxt();u
        } else {

            txt_ref.setText(c.getRef().toString());

            if (!c.getId_famille().isEmpty()) {
                String ss = daoArticle.getNameItemById("famille_produit", "famille", "id", c.getId_famille());

                ComboBox_famille.setSelectedItem(ss);
            }

            if (!c.getId_sous_famille().isEmpty()) {
                String ss = daoArticle.getNameItemById("sous_famille", "sous_famille", "id", c.getId_sous_famille());

                ComboBoxSous_famille.setSelectedItem(ss);
            }
            if (!c.getMarque().isEmpty()) {
                ComboBox_marque.setSelectedIndex(Integer.valueOf(c.getMarque()));
            }

        }
    }


    private void txt_searchArticleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchArticleKeyPressed
        // TODO add your handling code here:

        if (evt.getKeyCode() == evt.VK_ENTER) {
            setFieldsByName(txt_searchArticle.getText());

        }
    }//GEN-LAST:event_txt_searchArticleKeyPressed

    private void txt_searchArticleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchArticleKeyReleased
        //  daoArticle.searchClient(client_table, txt_search.getText());

        if (evt.getKeyCode() == evt.VK_BACK_SPACE || evt.getKeyCode() == evt.VK_DELETE) {

        } else {
            String to_check = txt_searchArticle.getText().toLowerCase();
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
                    txt_searchArticle.setText(data);
                    txt_searchArticle.setSelectionStart(to_check_len);
                    txt_searchArticle.setSelectionEnd(data.length());
                    break;
                }
            }
        }
    }//GEN-LAST:event_txt_searchArticleKeyReleased

    private void ComboBox_DepotItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboBox_DepotItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBox_DepotItemStateChanged

    private void ComboBox_DepotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox_DepotActionPerformed
        //  SearchStockArticle();
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBox_DepotActionPerformed

    private void Table_ArticleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_ArticleMouseClicked
        // TODO add your handling code here:

        // 6 remise 7 tvA
    }//GEN-LAST:event_Table_ArticleMouseClicked

    private void Table_ArticleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_ArticleMousePressed
        // TODO add your handling code here:


    }//GEN-LAST:event_Table_ArticleMousePressed
    StockDAO stockDAO = new StockDAO();
    JFrame frameListeClient;
    JButton jbtValider;

    JButton jbtValiderarticle;
    private void txt_refKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_refKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchStockArticle();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_refKeyPressed

    private void txt_desginationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_desginationKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            SearchStockArticle();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_desginationKeyPressed

    private void ComboBoxSous_familleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxSous_familleActionPerformed
        //  SearchStockArticle();
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxSous_familleActionPerformed

    private void ComboBox_marqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox_marqueActionPerformed
        //   SearchStockArticle();
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBox_marqueActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            /*   MessageFormat header = new MessageFormat("Statistiques Vente");
            MessageFormat footer = new MessageFormat("{0,number,integer}");
            DevisHist_Table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
             */
            DataBase_connect obj = new DataBase_connect();

            HashMap<String, Object> h = new HashMap<>();
            DefaultTableModel model = (DefaultTableModel) Table_Article.getModel();
            h.put("table_title", "Consultation Article");

            JasperReport jr = JasperCompileManager.compileReport(
                    ClassLoader.getSystemResourceAsStream("Article/ListeArticle.jrxml"));

            JasperPrint jp = JasperFillManager.fillReport(jr, h, new JRTableModelDataSource(model));

            JasperExportManager.exportReportToPdfFile(jp, Commen_Proc.PathPDF + "Consultation Article.pdf");

            JasperViewer.viewReport(jp, false);

            File myObj = new File(Commen_Proc.PathExcel + "Consultation Article" + ".xls");
            Config.ExpotToExcel.exportExcelStat(Table_Article, myObj, "StatVente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        SearchStockArticle();
    }//GEN-LAST:event_jButton5ActionPerformed

    private Article setArticleFromEditText() {
        Article r = new Article();

        try {
            if (!txt_ref.getText().isEmpty()) {
                r.setRef(txt_ref.getText());
            } else {
                r.setRef("");

            }

            if (!txt_desgination.getText().isEmpty()) {
                r.setDesignation(txt_desgination.getText());
            } else {
                r.setDesignation("");

            }
            String depot = ComboBox_Depot.getSelectedItem().toString();
            if (!depot.equals("-")) {
                String ss = daoArticle.getNameItemById("depot", "id", "nom", depot);
                r.setId_depot(ss);
            } else {

                r.setId_depot("");

            }
            String marque = ComboBox_marque.getSelectedItem().toString();
            if (!marque.equals("-")) {
                String ss = daoArticle.getNameItemById("marque_produit", "id", "marque", marque);
                r.setMarque(ss);
            } else {

                r.setMarque("");

            }

            String fm = ComboBox_famille.getSelectedItem().toString();

            if (!fm.equals("-")) {
                String ss = daoArticle.getNameItemById("famille_produit", "id", "famille", fm);
                r.setId_famille(ss);
            } else {
                r.setId_famille("");

            }

            String sfm = ComboBoxSous_famille.getSelectedItem().toString();
            if (!sfm.equals("-")) {
                String ss = daoArticle.getNameItemById("sous_famille", "id", "sous_famille", sfm);
                r.setId_sous_famille(ss);
            } else {
                r.setId_sous_famille("");

            }

        } catch (Exception e) {
            // JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println(e);

        }

        return r;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxSous_famille;
    private javax.swing.JComboBox<String> ComboBoxTypeArticle;
    private javax.swing.JComboBox<String> ComboBox_Depot;
    private javax.swing.JComboBox<String> ComboBox_famille;
    private javax.swing.JComboBox<String> ComboBox_marque;
    private javax.swing.JTable Table_Article;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_desgination;
    private javax.swing.JTextField txt_ref;
    private javax.swing.JTextField txt_searchArticle;
    // End of variables declaration//GEN-END:variables

    private void clearTxt() {
        txt_ref.setText("");

        txt_desgination.setText("");

        //String s = ComboBox_four.getSelectedItem().toString();
        ComboBox_famille.setSelectedItem("-");

        ComboBoxSous_famille.setSelectedItem("-");

        ComboBox_marque.setSelectedItem("-");

    }

}
