/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Achat.pre_commande;

import Devis.*;
import Article.Article;
import Article.ArticleDao;
import BL.BLDao;
import BL.LigneBL;
import Config.Commen_Proc;
import Conn.DataBase_connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mlek
 */
public class pre_commandeDao {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String year;

    boolean ajouterPre_commande(pre_commande d) {
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
            sql = "INSERT INTO pre_command_achat(num_bon_commande, date_pre_commande, id_fournisseur, id_client) "
                    + "VALUES ('" + d.getNum_bon_commande() + "','" + d.getDate_pre_commande() + "'," + d.getId_fournisseur() + ","
                    + d.getId_client() + ")";
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
                Logger.getLogger(pre_commandeDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    boolean modifierPre_commande(pre_commande d) {
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
            sql = "update pre_command_achat set "
                    + "date_pre_commande = '" + d.getDate_pre_commande() + "', "
                    + "id_fournisseur = " + d.getId_fournisseur() + ", "
                    + "id_client = " + d.getId_client() + " "
                    + "where num_bon_commande = " + d.getNum_bon_commande() + " ";
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
                Logger.getLogger(pre_commandeDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    void ajouterLignePre_commande(ArrayList<lignePre_commande> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (lignePre_commande d : lstd) {

                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_pre_commande(id_pre_commande, ref_article, designation_article, qte) "
                        + "VALUES ('" + d.getId_pre_commande() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
                        + d.getQte() + ")";
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
                Logger.getLogger(pre_commandeDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    void modifierLignePre_commande(ArrayList<lignePre_commande> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        String sqldel = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (lignePre_commande d : lstd) {
                lignePre_commande c = d;
                String currentTime = sdf.format(dt);

                sql = "UPDATE ligne_pre_commande SET "
                        + "id_pre_commande='-1'"
                        + " WHERE id_pre_commande = '" + c.getId_pre_commande() + "'";

//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }
            sqldel = "delete from ligne_pre_commande where id_pre_commande ='-1'";
            pst = (PreparedStatement) conn.prepareStatement(sqldel);
            pst.execute();

            for (lignePre_commande d : lstd) {

                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_pre_commande(id_pre_commande, ref_article, designation_article, qte) "
                        + "VALUES ('" + d.getId_pre_commande() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
                        + d.getQte() + ")";
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
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
                Logger.getLogger(pre_commandeDao.class.getName()).log(Level.SEVERE, null, ex);
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
            sql = "SELECT c.nom as `Client`,d.`Num_devis`, DATE_FORMAT(d.`date_devis`,'%d/%m/%Y')  ,d.`Total_TTC` FROM `devis` d , client c where d.id_client=c.numero_client";

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
                Logger.getLogger(pre_commandeDao.class.getName()).log(Level.SEVERE, null, ex);
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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(pre_commandeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
                Logger.getLogger(pre_commandeDao.class.getName()).log(Level.SEVERE, null, ex);
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
            return df;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {

            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(pre_commandeDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public String maxNumDevis() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String pre_cmd = "0";
        String cmd = "0";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial

            try {

                String sql = "SELECT if(max(num_bon_commande) is null ,'0',max(num_bon_commande)) as 'max(num_bon_commande)' FROM pre_command_achat";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();

                while (rs.next()) {
                    pre_cmd = rs.getString("max(num_bon_commande)");
                }
            } catch (Exception e) {
            }
            try {

                String sql = "SELECT if(max(num_commande) is null,'0',max(num_commande)) as 'max(num_commande)' FROM commande_achat ";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();

                while (rs.next()) {
                    cmd = rs.getString("max(num_commande)");
                }
            } catch (Exception e) {
            }
            if (cmd.isEmpty()) {
                cmd = "0";
            }
            if (Integer.valueOf(cmd) > Integer.valueOf(pre_cmd)) {
                return cmd;
            } else {
                return pre_cmd;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(pre_commandeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (Integer.valueOf(cmd) > Integer.valueOf(pre_cmd)) {
            return cmd;
        } else {
            return pre_cmd;
        }
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

    public void afficherPre_Commande(JTable table, String id_four) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT pre.`num_bon_commande`, DATE_FORMAT(pre.`date_pre_commande`,'%d/%m/%Y') ,c.nom   FROM `pre_command_achat` pre, client c WHERE pre.id_client=c.numero_Client and pre.id_fournisseur =" + id_four;

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
                Logger.getLogger(pre_commandeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
