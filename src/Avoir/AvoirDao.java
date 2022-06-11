/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Avoir;

import Devis.*;
import Article.Article;
import Article.ArticleDao;
import Config.Commen_Proc;
import Conn.DataBase_connect;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mlek
 */
public class AvoirDao {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String year;

    boolean ajouterAvoir(Avoir d) {
        PreparedStatement pst;
        year = Commen_Proc.YearVal;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);
            sql = "INSERT INTO avoir(Num_avoir, date_avoir, id_client, remise, Total_HT, Total_TTC, montant_tva, timbre, exh_TVA,"
                    + " Code_TVA, adresse, Type_avoir,passager , "
                    + "year,num_facture) "
                    + "VALUES ('" + d.getNum_Devis() + "','" + d.getDate_devis() + "'," + d.getId_client() + ","
                    + "" + d.getRemise() + "," + d.getTotale_HT() + "," + d.getTotale_TTC()
                    + "," + d.getMontant_TVA() + "," + d.getTimbre() + "," + d.getExh_TVA() + ",'" + d.getCode_TVA() + "',"
                    + "'" + d.getInfos_Devis() + "' ,'" + d.getType_avoir() + "'"
                    + ",'" + d.getPassager() + "' ,'" + year + "','" + d.getNum_facture() + "')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + sql);
            return false;
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    boolean ajouterAvoirFournisseur(Avoir d) {
        PreparedStatement pst;
        year = Commen_Proc.YearVal;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);
            sql = "INSERT INTO avoir_achat(Num_avoir_achat, date_avoir_achat, id_fournisseur, remise, Total_HT, Total_TTC, montant_tva, timbre, exh_TVA,"
                    + " Code_TVA, adresse, Type_avoir,passager , "
                    + "year) "
                    + "VALUES ('" + d.getNum_Devis() + "','" + d.getDate_devis() + "'," + d.getId_fournisseur() + ","
                    + "" + d.getRemise() + "," + d.getTotale_HT() + "," + d.getTotale_TTC()
                    + "," + d.getMontant_TVA() + "," + d.getTimbre() + "," + d.getExh_TVA() + ",'" + d.getCode_TVA() + "',"
                    + "'" + d.getInfos_Devis() + "' ,'" + d.getType_avoir() + "'"
                    + ",'" + d.getPassager() + "' ,'" + year + "')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + sql);
            return false;
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public HashMap<String, String> listNumAvoir(JComboBox<String> ComboBox_four, String type_avoir, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        ComboBox_four.removeAllItems();
        ComboBox_four.addItem("-");
        try {
            String sql = "select id, num_avoir from avoir where type_avoir in ('Non_Ident','Avoir') and id_client =" + id_client + "";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                //map.put(rs.getString("nom"), rs.getString("id"));
                ComboBox_four.addItem(rs.getString("num_avoir"));
            }

            /* for (String s : map.keySet()) {
                ComboBox_four.addItem(s);
            }*/
            return map;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");

            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return map;
    }

    boolean ajouterAvoirPassager(Avoir d) {
        PreparedStatement pst;
        year = Commen_Proc.YearVal;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);
            sql = "INSERT INTO avoir(Num_avoir, date_avoir, id_client, remise, Total_HT, Total_TTC, montant_tva, timbre, exh_TVA,"
                    + " Code_TVA, adresse, Type_avoir, "
                    + "year) "
                    + "VALUES ('" + d.getNum_Devis() + "','" + currentTime + "'," + d.getId_client() + ","
                    + "" + d.getRemise() + "," + d.getTotale_HT() + "," + d.getTotale_TTC()
                    + "," + d.getMontant_TVA() + "," + d.getTimbre() + "," + d.getExh_TVA() + ",'" + d.getCode_TVA() + "',"
                    + "'" + d.getInfos_Devis() + "' ,'" + d.getType_avoir() + "'"
                    + ",'" + year + "')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + sql);
            return false;
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    void ajouterLigneAvoir(ArrayList<ligneAvoir> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (ligneAvoir d : lstd) {

                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_avoir(id_avoir, ref_article, designation_article, qte, prix_u, remise, "
                        + "tva, total_HT, total_TTC,rank) "
                        + "VALUES ('" + d.getId_Devis() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
                        + d.getQte() + "," + d.getPrix_U() + "," + d.getRemise() + "," + d.getTVA() + "," + d.getTotal_HT()
                        + "," + d.getTotale_TTC() + ",'" + d.getId() + "')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    void ajouterLigneAvoirFrounisseur(ArrayList<ligneAvoir> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (ligneAvoir d : lstd) {

                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_avoir_achat(id_avoir_achat, ref_article, designation_article, qte, prix_u, remise, "
                        + "tva, total_HT, total_TTC,rank) "
                        + "VALUES ('" + d.getId_Devis() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
                        + d.getQte() + "," + d.getPrix_U() + "," + d.getRemise() + "," + d.getTVA() + "," + d.getTotal_HT()
                        + "," + d.getTotale_TTC() + ",'" + d.getId() + "')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public ArrayList<String> afficherDevis() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        ArrayList<String> s = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select * from Devis";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = new ArrayList<>();

            while (rs.next()) {
                s.add(rs.getString("Num_devis"));
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public void afficherDevis(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT c.nom as `Client`,d.`Num_devis`,DATE_FORMAT(d.`date_devis`,'%d/%m/%Y'),d.`Total_TTC` FROM `devis` d , client c where d.id_client=c.numero_client";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClause = "";
            if (!(FromDate.isEmpty() & ToDate.isEmpty())) {
                DateClause = " and d.date_devis BETWEEN '" + FromDate + "' AND '" + ToDate + "'";
                sql = sql + DateClause;
            }
            String MontantClause = "";
            if (!(FromMontant.isEmpty() & ToMontant.isEmpty())) {
                MontantClause = " and d.total_TTC BETWEEN " + FromMontant + " AND " + ToMontant + "";
                sql = sql + MontantClause;
            }
            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_devis  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom  like '%" + NomClient + "%'";
                sql = sql + ClientClause;

            }

            pst = conn.prepareStatement(sql);
            System.out.println(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherDevis(JTable table) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT c.nom as `Client`,d.`Num_devis`,DATE_FORMAT(d.`date_devis`,'%d/%m/%Y'),d.`Total_TTC` FROM `devis` d , client c where d.id_client=c.numero_client";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
            table.setModel(df);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            JOptionPane.showMessageDialog(null, "error in formatDouble methode :  " + e.getMessage());

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

    public void afficherHistPrix(JTable table, String NomClient, String Ref_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "select ld.ref_article, ld.designation_article, ld.`prix_u`, ld.qte, ld.remise, ld.tva "
                    + " from ligne_devis ld , devis d , client c where ld.id_devis = d.num_devis "
                    + " and d.id_client = c.numero_client and c.nom='" + NomClient + "' "
                    + " and ld.ref_article = '" + Ref_article + "' order by d.`date_devis` desc limit 1";
            System.out.println(sql);

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public DefaultTableModel afficherDetailDevis(JTable table, String Num_Devis, Boolean withoutNumDevis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = new DefaultTableModel();
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            if (withoutNumDevis) {
                sql = "SELECT `id`,  `ref_article`, `designation_article`,`prix_u`, `qte`, `total_HT`, `remise`, `tva`,  `total_TTC` from ligne_devis where id_devis='" + Num_Devis + "'";

            } else {
                sql = "SELECT * from ligne_devis where id_devis='" + Num_Devis + "'";

            }

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 6);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 9);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 9);
            }
            table.setModel(df);

            return df;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {

            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailDevisForPre_commande(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = new DefaultTableModel();
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT `id`,  `ref_article`, `designation_article`, `qte` from ligne_devis where id_devis='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);
            table.setModel(df);

            return df;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {

            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    public String maxNumAvoir(String num) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT * FROM `avoir` where num_avoir like '%" + num + "%' order by id desc LIMIT 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                s = rs.getString("num_avoir");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String maxNumAvoirAchat() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT * FROM `avoir_achat` order by id desc LIMIT 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                s = rs.getString("num_avoir_achat");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    void modifierStock(ArrayList<ligneDevis> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (ligneDevis d : lstd) {

                String sql = "UPDATE article SET "
                        + "qte= qte - " + d.getQte()
                        + " WHERE ref = '" + d.getRef_article() + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    void modifierStockAvoir(ArrayList<ligneAvoir> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (ligneAvoir d : lstd) {

                String sql = "UPDATE article SET "
                        + "qte= qte - " + d.getQte()
                        + " WHERE ref = '" + d.getRef_article() + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public DefaultTableModel afficherListItemsFacture(JTable table, String num_facture) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT lb.id, lb.ref_article, lb.designation_article, lb.prix_u,lb.qte,lb.total_HT,  lb.remise,lb.tva, lb.total_TTC "
                    + "FROM `ligne_facture` lf , ligne_bl lb WHERE lf.num_bl = lb.id_bl and lf.num_facture ='" + num_facture + "'";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                Double d = Double.parseDouble(PU.toString());
                df.setValueAt(d.intValue(), j, 6);

                Object qte = df.getValueAt(j, 4);
                Double d2 = Double.parseDouble(qte.toString());
                df.setValueAt(d2.intValue(), j, 4);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 3);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 3);
            }
            table.setModel(df);
            return df;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherListItemsFactureAchat(JTable table, String num_facture) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT lb.id, lb.ref_article, lb.designation_article, lb.prix_u,lb.qte,lb.total_HT,  lb.remise,lb.tva, lb.total_TTC "
                    + "FROM `ligne_facture_achat` lf , ligne_bl_achat lb WHERE lf.num_bl_achat = lb.id_bl_achat and lf.num_facture_achat ='" + num_facture + "'";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                Double d = Double.parseDouble(PU.toString());
                df.setValueAt(d.intValue(), j, 6);

                Object qte = df.getValueAt(j, 4);
                Double d2 = Double.parseDouble(qte.toString());
                df.setValueAt(d2.intValue(), j, 4);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 3);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 3);
            }
            table.setModel(df);
            return df;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherListItemsFacturePassager(JTable table, String num_facture) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT lf.id, lf.ref_article, lf.designation_article, lf.prix_u,lf.qte,lf.total_HT, lf.remise,lf.tva, lf.total_TTC "
                    + "FROM `ligne_facture_passager` lf WHERE lf.id_facture_passager ='" + num_facture + "'";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                Double d = Double.parseDouble(PU.toString());
                df.setValueAt(d.intValue(), j, 6);

                Object qte = df.getValueAt(j, 4);
                Double d2 = Double.parseDouble(qte.toString());
                df.setValueAt(d2.intValue(), j, 4);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 3);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 3);
            }
            table.setModel(df);
            return df;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(AvoirDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }
}
