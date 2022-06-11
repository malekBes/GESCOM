/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Article;

import BL.BLDao;

import static Client.ClientDao.customBuildTableModel;
import Conn.DataBase_connect;
import Facture.FactureDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
public class ArticleDao {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    static Vector<Vector<Object>> data;

    public void afficherListeArticle(JTable table, String Depot, String Famille, String Sous_famille, String Designation, String ref, String Marque) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "select r.ref, '-' as designation,fam.famille,mp.marque, r.qte,r.prix_achat, r.prix_vente as prix_vente, r.marge from article r left join fournisseur f on (r.id_fournisseur=f.id) left JOIN famille_produit fam on r.id_famille=fam.id left join marque_produit mp on r.id_marque=mp.id where 1";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClauseFrom = "";
            if (!(Depot.isEmpty())) {
                DateClauseFrom = " and r.id_depot = '" + Depot + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(Famille.isEmpty())) {
                DateClauseTo = " and r.id_famille = '" + Famille + "'";
                sql = sql + DateClauseTo;
            }
            String MontantClauseFrom = "";
            if (!(Sous_famille.isEmpty())) {
                MontantClauseFrom = " and r.id_sous_famille = " + Sous_famille + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(Designation.isEmpty())) {
                MontantClauseTo = " and r.designation like '%" + Designation + "%'";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(ref.isEmpty())) {
                NumDevisClause = " and r.ref  like '%" + ref + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(Marque.isEmpty())) {
                ClientClause = " and r.id_marque  = " + Marque + "";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY r.`id` DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModelArticleListe(rs);

            table.setModel(df);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherListeArticleProspection(JTable table, String Depot, String Famille, String Sous_famille, String Designation, String ref, String Marque) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "select r.ref, r.designation as designation,fam.famille,mp.marque, r.qte,r.prix_achat, r.prix_vente as prix_vente, r.marge from article r left join fournisseur f on (r.id_fournisseur=f.id) left JOIN famille_produit fam on r.id_famille=fam.id left join marque_produit mp on r.id_marque=mp.id where 1";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClauseFrom = "";
            if (!(Depot.isEmpty())) {
                DateClauseFrom = " and r.groupe_prospection = '" + Depot + "'";
                sql = sql + DateClauseFrom;
            }

            sql += " ORDER BY r.`id` DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModelArticleListe(rs);

            table.setModel(df);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Vector<Vector<Object>> afficherArticle(JTable table, String refArticle, String NomClient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;

            sql = "SELECT ref,designation from article where 1=1 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            /*  String DateClause = "";
            if (!(FromDate.isEmpty() & ToDate.isEmpty())) {
                DateClause = " and d.date_bl BETWEEN '" + FromDate + "' AND '" + ToDate + "'";
                sql = sql + DateClause;
            }*/
            String refClauseFrom = "";
            if (!(refArticle.isEmpty())) {
                refClauseFrom = " and ref like '%" + refArticle + "%'";
                sql = sql + refClauseFrom;
            }
            /* String MontantClauseTo = "";

            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC < " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }
            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_bl like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }*/
 /*   String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and ref like '%" + NomClient + "%'";
                sql = sql + ClientClause;

            }*/
            sql = sql + "  ORDER BY ref DESC ";
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModelReturnData(rs);
            table.setModel(df);
            return data;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    public Vector<Vector<Object>> afficherClientActivite(JTable table, String type_article, String NomClient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;

            sql = "SELECT nom,classification from client where 1=1 and classification <> '-' ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            /*  String DateClause = "";
            if (!(FromDate.isEmpty() & ToDate.isEmpty())) {
                DateClause = " and d.date_bl BETWEEN '" + FromDate + "' AND '" + ToDate + "'";
                sql = sql + DateClause;
            }*/
 /*  String refClauseFrom = "";
            if (!(type_article.isEmpty())) {
                refClauseFrom = " and ref like '%" + type_article + "%'";
                sql = sql + refClauseFrom;
            }*/
 /* String MontantClauseTo = "";

            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC < " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }*/
            String type_articleClause = "";
            if (!(type_article.equals("-"))) {
                type_articleClause = " and classification = '" + type_article + "'";
                sql = sql + type_articleClause;

            }
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and nom like '%" + NomClient + "%'";
                sql = sql + ClientClause;

            }
            sql = sql + "  ORDER BY nom DESC ";
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModelReturnData(rs);
            table.setModel(df);
            return data;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    public Vector<Vector<Object>> afficherArticleAffecter(JTable table, String NomClient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;

            sql = "SELECT a.ref,a.designation from prefered_article ap left join article a on ap.ref_article=a.ref where 1=1 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            /*  String DateClause = "";
            if (!(FromDate.isEmpty() & ToDate.isEmpty())) {
                DateClause = " and d.date_bl BETWEEN '" + FromDate + "' AND '" + ToDate + "'";
                sql = sql + DateClause;
            }*/
            String ClientClauseFrom = "";
            if (!(NomClient.isEmpty())) {
                ClientClauseFrom = " and ap.id_client  = " + NomClient + "";
                sql = sql + ClientClauseFrom;
            }
            /* String MontantClauseTo = "";

            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC < " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }
            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_bl like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }*/
 /*   String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and ref like '%" + NomClient + "%'";
                sql = sql + ClientClause;

            }*/
            sql = sql + "  ORDER BY ref DESC ";
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModelReturnData(rs);
            table.setModel(df);
            return data;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    public Vector<Vector<Object>> afficherArticleAvtivite(JTable table, String type_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;

            sql = "SELECT a.ref,a.designation from  "
                    + " article a where 1=1 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            /*  String DateClause = "";
            if (!(FromDate.isEmpty() & ToDate.isEmpty())) {
                DateClause = " and d.date_bl BETWEEN '" + FromDate + "' AND '" + ToDate + "'";
                sql = sql + DateClause;
            }*/
            String ClientClauseFrom = "";
            if (!(type_article.isEmpty())) {
                ClientClauseFrom = " and a.classification = '" + type_article + "'";
                sql = sql + ClientClauseFrom;
            }
            /* String MontantClauseTo = "";

            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC < " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }
            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_bl like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }*/
 /*   String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and ref like '%" + NomClient + "%'";
                sql = sql + ClientClause;

            }*/
            sql = sql + "  ORDER BY ref DESC ";
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModelReturnData(rs);
            table.setModel(df);
            return data;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    public HashMap listfamille(JComboBox comboBox) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        try {
            comboBox.removeAllItems();
            comboBox.addItem("-");

        } catch (Exception e) {
        }
        try {
            String sql = "select id,famille from famille_produit";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                map.put(rs.getString("famille"), rs.getString("id"));

                //comboBox.addItem(comm);
            }

            try {
                for (String s : map.keySet()) {
                    comboBox.addItem(s);
                }
            } catch (Exception e) {
            }

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

    public HashMap listSousFamille(JComboBox comboBox, String id_famille) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        try {
            comboBox.removeAllItems();
            comboBox.addItem("-");
        } catch (Exception e) {
        }

        try {
            String sql = "select id,Sous_famille from sous_famille where id_famille = " + id_famille;
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                map.put(rs.getString("Sous_famille"), rs.getString("id"));

                //comboBox.addItem(comm);
            }
            try {
                for (String s : map.keySet()) {
                    comboBox.addItem(s);
                }
            } catch (Exception e) {
            }

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

    public Article getArticleByName(String name) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Article c = new Article();
        try {

            String sql = "select * from article where ref like '%" + name + "%'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {

                c.setId(Integer.valueOf(rs.getString("id")));
                c.setRef(rs.getString("ref"));
                c.setDesignation(rs.getString("designation"));
                c.setMarque(rs.getString("id_marque"));
                c.setId_famille(rs.getString("id_famille"));
                c.setId_sous_famille(rs.getString("id_sous_famille"));
                c.setId_fournisseur(rs.getString("id_fournisseur"));
                c.setIsImporte(rs.getString("isImporte"));
                c.setPrix_achat(Double.valueOf(rs.getString("prix_achat")));
                c.setPrix_vente(Double.valueOf(rs.getString("prix_vente")));
                c.setRemise_four(rs.getString("remise_four"));
                c.setQte(Integer.valueOf(rs.getString("qte")));
                c.setStock_negative(Integer.valueOf(rs.getString("stock_negative")));
                c.setMarge(Double.valueOf(rs.getString("marge")));
                c.setVIP(rs.getString("VIP"));
                c.setVIP_pourcentage(Double.valueOf(rs.getString("VIP_pourcentage")));
                c.setCode_barres(rs.getString("code_barres"));
                c.setPays_origine(rs.getString("pays_origine"));
                c.setDepot(rs.getString("depot"));
                c.setTVA(rs.getString("TVA"));
                c.setId_depot(rs.getString("id_depot"));
                c.setType_article(rs.getString("classification"));


                /* String ad = rs.getObject("datee").toString();
                java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(ad);
                c.setAdresse(add1);*/
                return c;
            }
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
        return c;
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

    public static DefaultTableModel buildTableModelArticleListe(ResultSet rs)
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
                vector.add(rs.getObject(columnIndex).toString());
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    public static DefaultTableModel buildTableModelReturnData(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    public ArrayList afficherArticle() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        ArrayList<String> s = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select * from article";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = new ArrayList<>();

            while (rs.next()) {
                s.add(rs.getString("ref"));
            }
            return s;

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
        return s;
    }

    public ArrayList afficherClient() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        ArrayList<String> s = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select * from client";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = new ArrayList<>();

            while (rs.next()) {
                s.add(rs.getString("nom"));
            }
            return s;

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
        return s;
    }

    public Vector<Vector<Object>> afficherListeArticle() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select r.id, r.ref, r.designation, r.prix_vente, r.qte, r.marge, sum(lr.qte) from article r left join ligne_resa lr on r.ref=lr.ref_article GROUP by r.id order by r.designation";
            pst = conn.prepareStatement(sql);
            //  pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            df = customBuildTableModel(rs);
            return df;

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
        return df;
    }

    public Vector<Vector<Object>> afficherListeArticleWithStock() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select r.id, r.ref, r.designation, r.prix_vente, r.qte, r.marge, sum(lr.qte),r.stock_negative from article r left join ligne_resa lr on r.ref=lr.ref_article GROUP by r.id order by r.designation";
            pst = conn.prepareStatement(sql);
            //  pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            df = customBuildTableModel(rs);
            return df;

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
        return df;
    }

    public Vector<Vector<Object>> afficherListeArticlewithoutprice() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select r.id, r.ref, r.designation, 0 as 'prix_vente', r.qte, ''  as 'marge', sum(lr.qte) from article r left join ligne_resa lr on r.ref=lr.ref_article GROUP by r.id order by r.designation";
            pst = conn.prepareStatement(sql);
            //  pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            df = customBuildTableModel(rs);
            return df;

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
        return df;
    }

    public Vector<Vector<Object>> afficherListeArticle(String ref, String s) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select r.id, r.ref, r.designation, r.prix_vente, r.qte, r.marge, sum(lr.qte) from article r left join ligne_resa lr on r.ref=lr.ref_article  WHERE r.ref like '%" + ref + "%' or r.designation like '%" + ref + "%' GROUP by r.id order by r.designation";
            pst = conn.prepareStatement(sql);
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            df = customBuildTableModel(rs);
            return df;

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
        return df;
    }

    public Vector<Vector<Object>> afficherListeArticlewithStock(String ref, String s) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select r.id, r.ref, r.designation, r.prix_vente, r.qte, r.marge, sum(lr.qte),r.stock_negative from article r left join ligne_resa lr on r.ref=lr.ref_article  WHERE r.ref like '%" + ref + "%' or r.designation like '%" + ref + "%' GROUP by r.id order by r.designation";
            pst = conn.prepareStatement(sql);
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            df = customBuildTableModel(rs);
            return df;

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
        return df;
    }

    public Vector<Vector<Object>> afficherListeMarque() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select id,marque from marque_produit order by id desc";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            df = customBuildTableModel(rs);
            return df;

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
        return df;
    }

    public Vector<Vector<Object>> afficherListeArticle(String id_four) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select id,ref,designation,prix_vente from article where id_fournisseur =" + id_four;
            pst = conn.prepareStatement(sql);
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            df = customBuildTableModel(rs);
            return df;

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
        return df;
    }

    void updateArticle(Article c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);
        try {

            String sql = "UPDATE article SET "
                    + "designation='" + c.getDesignation() + "',"
                    + "id_marque=" + c.getMarque() + ","
                    + "id_fournisseur='" + c.getId_fournisseur() + "',"
                    + "id_famille='" + c.getId_famille() + "',"
                    + "id_sous_famille=" + c.getId_sous_famille() + ","
                    + "TVA='" + c.getTVA() + "',"
                    + "isImporte=" + c.getIsImporte() + ","
                    + "prix_achat=" + c.getPrix_achat() + ","
                    + "prix_vente=" + c.getPrix_vente() + ","
                    + "remise_four='" + c.getRemise_four() + "',"
                    + "qte=" + c.getQte() + ","
                    + "stock_negative=" + c.getStock_negative() + ","
                    + "marge=" + c.getMarge() + ","
                    //   + "VIP='" + c.getVIP() + "',"
                    //  + "VIP_pourcentage=" + c.getVIP_pourcentage() + ","
                    + "code_barres='" + c.getCode_barres() + "',"
                    + "pays_origine='" + c.getPays_origine() + "',"
                    + "depot='" + c.getDepot() + "',"
                    + "classification='" + c.getType_article() + "'"
                    + " WHERE ref = '" + c.getRef() + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "l'article " + c.getRef() + " a été mise a jour avec succses !");

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

    public void updateArticlePrix(Article c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);
        try {

            String sql = "UPDATE article SET "
                    + "prix_achat =" + c.getPrix_achat() + ","
                    + "prix_vente =" + c.getPrix_vente() + ""
                    + " WHERE ref = '" + c.getRef() + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "l'article " + c.getRef() + " a été mise a jour avec succses !");

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

    public void deleteArticle(String ID) {
        int p = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer ?", "Supprimer", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            PreparedStatement pst;

            DataBase_connect obj = new DataBase_connect();

            Connection conn = obj.Open();
            String sql = "delete from article where ref=?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, ID);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Article " + ID + " à été bien Supprimé ");

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    conn.close();
                    System.out.println("disconnected");
                } catch (SQLException ex) {
                    Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void ajouterArticle(Article c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String remise = (c.getRemise_four() == null) ? "" : c.getRemise_four();

            String currentTime = sdf.format(dt);
            sql = "INSERT INTO article( ref, designation, id_marque, id_fournisseur, id_famille, "
                    + "id_sous_famille, TVA, isImporte, prix_achat, prix_vente, remise_four, detail_qte ,qte, "
                    + "stock_negative, marge, VIP, VIP_pourcentage, code_barres, pays_origine, depot,id_depot) "
                    + "VALUES ( '" + c.getRef() + "', '" + c.getDesignation() + "', " + c.getMarque() + ", '" + c.getId_fournisseur() + "', '" + c.getId_famille() + "', "
                    + "" + c.getId_sous_famille() + ", '" + c.getTVA() + "', '" + c.getIsImporte() + "', " + c.getPrix_achat() + ", " + c.getPrix_vente() + ", "
                    + "'" + remise + "'," + c.getDetail_qte() + ", " + c.getQte() + ","
                    + " " + c.getStock_negative() + "," + c.getMarge()
                    + ",'" + c.getVIP() + "', " + c.getVIP_pourcentage() + ", '"
                    + c.getCode_barres() + "','" + c.getPays_origine() + "', '"
                    + c.getDepot() + "'," + c.getId_depot() + ")";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "l'article avec ref : " + c.getRef() + " a été bien Ajoutée !");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void ajouterPreferedArticle(String id_client, Vector<Vector<Object>> lstArticle) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //  String remise = (c.getRemise_four() == null) ? "" : c.getRemise_four();
            //  String currentTime = sdf.format(dt);
            sql = "delete from prefered_article where id_client = " + id_client;
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

            for (Vector<Object> object : lstArticle) {

                sql = "insert into prefered_article (id_client,ref_article) values (" + id_client + ",'" + object.elementAt(0) + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            //  JOptionPane.showMessageDialog(null, "l'article avec ref : " + c.getRef() + " a été bien Ajoutée !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void updateClientClassification(String id_client, String type_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //  String remise = (c.getRemise_four() == null) ? "" : c.getRemise_four();
            //  String currentTime = sdf.format(dt);
            sql = "update client set classification ='" + type_article + "'  where numero_Client = " + id_client + "";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

            /* for (Vector<Object> object : lstArticle) {

                sql = "insert into prefered_article (id_client,ref_article) values (" + id_client + ",'" + object.elementAt(0) + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }
             */
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            //  JOptionPane.showMessageDialog(null, "l'article avec ref : " + c.getRef() + " a été bien Ajoutée !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void updateClientClassificationParNom(String id_client, String type_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //  String remise = (c.getRemise_four() == null) ? "" : c.getRemise_four();
            //  String currentTime = sdf.format(dt);
            sql = "update client set classification ='-'  where nom = '" + id_client + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

            /* for (Vector<Object> object : lstArticle) {

                sql = "insert into prefered_article (id_client,ref_article) values (" + id_client + ",'" + object.elementAt(0) + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }
             */
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            //  JOptionPane.showMessageDialog(null, "l'article avec ref : " + c.getRef() + " a été bien Ajoutée !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void updateArticleClassification(String type_article, Vector<Vector<Object>> lstArticleToUpdate) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //  String remise = (c.getRemise_four() == null) ? "" : c.getRemise_four();
            //  String currentTime = sdf.format(dt);
            /*   sql = "delete from article_activite where type_article = '" + type_article + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();*/
            for (Vector<Object> object : lstArticleToUpdate) {

                sql = "update article set classification ='" + type_article + "'  where ref='" + object.elementAt(0) + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            //  JOptionPane.showMessageDialog(null, "l'article avec ref : " + c.getRef() + " a été bien Ajoutée !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void updateArticleProspection(String groupe_prospection, Vector<Vector<Object>> lstArticleToUpdate) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            /*  java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             */
            //  String remise = (c.getRemise_four() == null) ? "" : c.getRemise_four();
            //  String currentTime = sdf.format(dt);
            /*   sql = "delete from article_activite where type_article = '" + type_article + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();*/
            for (Vector<Object> object : lstArticleToUpdate) {

                sql = "update article set groupe_prospection ='" + groupe_prospection + "'  where ref='" + object.elementAt(0) + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            //  JOptionPane.showMessageDialog(null, "l'article avec ref : " + c.getRef() + " a été bien Ajoutée !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void updateArticleClassificationParRef(String type_article, String ref) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //  String remise = (c.getRemise_four() == null) ? "" : c.getRemise_four();
            //  String currentTime = sdf.format(dt);
            /*   sql = "delete from article_activite where type_article = '" + type_article + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();*/
            sql = "update article set classification ='-'  where ref='" + ref + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            //  JOptionPane.showMessageDialog(null, "l'article avec ref : " + c.getRef() + " a été bien Ajoutée !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public HashMap<String, String> listFournisseur(JComboBox<String> ComboBox_four) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        ComboBox_four.removeAllItems();
        ComboBox_four.addItem("-");
        try {
            String sql = "select id, nom from fournisseur";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                //map.put(rs.getString("nom"), rs.getString("id"));
                ComboBox_four.addItem(rs.getString("nom"));
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

    public HashMap<String, String> listTypeReglement(JComboBox<String> ComboBox_four) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        ComboBox_four.removeAllItems();
        ComboBox_four.addItem("-");
        try {
            String sql = "select id, type from type_reglement";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                //map.put(rs.getString("nom"), rs.getString("id"));
                ComboBox_four.addItem(rs.getString("type"));
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

    public HashMap<String, String> listMarque(JComboBox<String> ComboBox_marque) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        try {
            ComboBox_marque.removeAllItems();
            ComboBox_marque.addItem("-");
        } catch (Exception e) {
        }

        try {
            String sql = "select id, marque from marque_produit";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                map.put(rs.getString("marque"), rs.getString("id"));

                //comboBox.addItem(comm);
            }
            try {
                for (String s : map.keySet()) {
                    ComboBox_marque.addItem(s);
                }
            } catch (Exception e) {
            }

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

    public HashMap<String, String> listDepot(JComboBox<String> ComboBox_marque) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        try {
            ComboBox_marque.removeAllItems();
            ComboBox_marque.addItem("-");
        } catch (Exception e) {
        }
        try {
            String sql = "select id, nom from depot";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                map.put(rs.getString("nom"), rs.getString("id"));

                //comboBox.addItem(comm);
            }
            try {

                for (String s : map.keySet()) {
                    ComboBox_marque.addItem(s);
                }
            } catch (Exception e) {
            }
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

    public HashMap<String, String> listProspection(JComboBox<String> ComboBox_marque) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        try {
            ComboBox_marque.removeAllItems();
            // ComboBox_marque.addItem("-");
        } catch (Exception e) {
        }
        try {
            String sql = "select ref, groupe_prospection from article where groupe_prospection <> '-' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                map.put(rs.getString("groupe_prospection"), rs.getString("ref"));

                //comboBox.addItem(comm);
            }
            try {

                for (String s : map.keySet()) {
                    ComboBox_marque.addItem(s);
                }
            } catch (Exception e) {
            }
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

    public HashMap<String, String> listTypeArticle(JComboBox<String> ComboBox) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        try {
            ComboBox.removeAllItems();
            ComboBox.addItem("-");
        } catch (Exception e) {
        }
        try {
            String sql = "select id, type from type_article";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                map.put(rs.getString("type"), rs.getString("id"));

                ComboBox.addItem(rs.getString("type"));
            }
            try {

                /* for (String s : map.keySet()) {
                    ComboBox_marque.addItem(s);
                }*/
            } catch (Exception e) {
            }
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

    public String getNameItemById(String table_name, String Column_Name, String Id_Column, String Id_value) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString(Column_Name);
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getNameItemByIdVerifStock(String table_name, String Column_Name, String Id_Column, String Id_value) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString(Column_Name);
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
            return s;
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public String getIdItemByName(String table_name, String Column_Name, String Id_Column, String Id_value) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = " + Id_value + "";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString(Column_Name);
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getIdAdresseFacture(String table_name, String Column_Name, String Id_Column, String Id_value) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            // sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";
//numero_client,adresse
            while (rs.next()) {
                String a = "adresse";
                if (!rs.getString("adresse").isEmpty()) {
                    a = rs.getString("adresse");
                }
                s = rs.getString("numero_client") + ";" + a;
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getIdFactureModif(String table_name, String Column_Name, String Id_Column, String Id_value) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            if (Column_Name.equals("id_client")) {
                sql = "select f." + Column_Name + " from " + table_name + " f left join client c on c.numero_client=f.id_client where f." + Id_Column + " = '" + Id_value + "' ";
            }
// sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";
//numero_client,adresse
            while (rs.next()) {
                String a = "adresse";
                if (!rs.getString("adresse").isEmpty()) {
                    a = rs.getString("adresse");
                }
                s = rs.getString("numero_client") + ";" + a;
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getRemiseById(String id_client, String ref) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String id_f = getIdFamilleByRef(ref);
            sql = "select remise from remise where (ref = '" + ref + "' or id_famille = " + id_f + ") and id_client =" + id_client;
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString("remise");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getIdFamilleByRef(String ref) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "select id_famille from article where ref = '" + ref + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString("id_famille");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getNameItemByIdJoinTables(String table_name, String Id_Column, String Id_value) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            if (table_name.equals("devis")) {
                sql = "SELECT   d.id_client,c.nom , c.id_Fiscale,d.timbre, d.passager FROM " + table_name + " d left join client c on (c.numero_Client=d.id_client) WHERE d." + Id_Column + "='" + Id_value + "'";
            } else {
                sql = "SELECT   d.id_client,c.nom , c.id_Fiscale,d.timbre FROM " + table_name + " d left join client c on (c.numero_Client=d.id_client) WHERE d." + Id_Column + "='" + Id_value + "'";
            }
            // sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                if (table_name.equals("devis")) {
                    s = rs.getString("d.id_client") + ";" + rs.getString("c.nom") + ";" + rs.getString("c.id_Fiscale") + ";" + rs.getString("d.timbre") + ";" + rs.getString("d.passager");
                } else {
                    s = rs.getString("d.id_client") + ";" + rs.getString("c.nom") + ";" + rs.getString("c.id_Fiscale") + ";" + rs.getString("d.timbre");

                }
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getNameItemByIdJoinTablesFacture(String table_name, String Id_Column, String Id_value, String typeFacture) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //nom_adresse_timbre_info
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            if (typeFacture.equals("Client")) {
                sql = "SELECT  d.id_client,c.nom , c.adresse,d.timbre,d.info_facture FROM " + table_name + " d left join client c on (c.numero_Client=d.id_client) WHERE d." + Id_Column + "='" + Id_value + "'";
            } // sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                String adresse = "";
                if (rs.getString("c.adresse").isEmpty()) {
                    adresse = "null";
                } else {
                    adresse = rs.getString("c.adresse");
                }
                String info = "";
                if (rs.getString("d.info_facture").isEmpty()) {
                    info = "null";
                } else {
                    info = rs.getString("d.info_facture");
                }
                s = rs.getString("d.id_client") + ";" + rs.getString("c.nom") + ";" + adresse + ";" + rs.getString("d.timbre") + ";" + info;
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getNameItemByIdJoinTablesFacturePassager(String table_name, String Id_Column, String Id_value, String typeFacture) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //codetva_adresse_timbre_info
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            if (typeFacture.equals("Client")) {
                sql = "SELECT  d.code_tva_passager,d.adresse, d.timbre,d.info_facture FROM " + table_name + " d WHERE d." + Id_Column + "='" + Id_value + "'";
            } // sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                String adresse = "";
                if (rs.getString("d.adresse").isEmpty()) {
                    adresse = "null";
                } else {
                    adresse = rs.getString("d.adresse");
                }
                String code_tva_passager = "";
                if (rs.getString("d.code_tva_passager").isEmpty()) {
                    code_tva_passager = "null";
                } else {
                    code_tva_passager = rs.getString("d.code_tva_passager");
                }
                String timbre = "";
                if (rs.getString("d.timbre").isEmpty()) {
                    timbre = "null";
                } else {
                    timbre = rs.getString("d.timbre");
                }
                String info = "";
                if (rs.getString("d.info_facture").isEmpty()) {
                    info = "null";
                } else {
                    info = rs.getString("d.info_facture");
                }
                s = code_tva_passager + ";" + adresse + ";" + timbre + ";" + info;
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getNameItemByIdJoinTablesFour(String table_name, String Id_Column, String Id_value) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT   d.id_fournisseur,c.nom ,d.timbre FROM " + table_name + " d left join fournisseur c on (c.id=d.id_fournisseur) WHERE d." + Id_Column + "='" + Id_value + "'";
            // sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString("d.id_fournisseur") + ";" + rs.getString("c.nom") + ";" + rs.getString("d.timbre");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getNameItemByIdJoinTablesPreCommande(String table_name, String Id_Column, String Id_value) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT   d.id_fournisseur,f.nom ,d.id_client,c.nom FROM " + table_name + " d left join fournisseur f on (f.id=d.id_fournisseur) left join client c on(c.numero_client=d.id_client) WHERE d." + Id_Column + "='" + Id_value + "'";
            // sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString("d.id_fournisseur") + ";" + rs.getString("f.nom") + ";" + rs.getString("d.id_client") + ";" + rs.getString("c.nom");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getNameItemByIdJoinTablesPassager(String table_name, String Id_Column, String Id_value) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT   d.id_client,d.passager , d.Code_TVA,d.adresse,d.timbre FROM " + table_name + " d WHERE d." + Id_Column + "='" + Id_value + "'";
            // sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString("d.id_client") + ";" + rs.getString("d.passager") + ";" + rs.getString("d.Code_TVA") + ";" + rs.getString("d.adresse") + ";" + rs.getString("d.timbre");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getDateByNumDevis(String num_devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  DATE_FORMAT(`date_devis`,'%d/%m/%Y') from devis where num_devis ='" + num_devis + "'";
            // sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString("DATE_FORMAT(`date_devis`,'%d/%m/%Y')");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getDateByNumBL(String num_devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  DATE_FORMAT(`date_bl`,'%d/%m/%Y') from bl where num_bl ='" + num_devis + "'";
            // sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString("DATE_FORMAT(`date_bl`,'%d/%m/%Y')");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getDateByNumFacture(String num_devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  DATE_FORMAT(`date_facture`,'%d/%m/%Y') from facture where num_facture ='" + num_devis + "'";
            // sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString("DATE_FORMAT(`date_facture`,'%d/%m/%Y')");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getDateByNumBLAchat(String num_devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  DATE_FORMAT(`date_bl_achat`,'%d/%m/%Y') from bl_achat where num_bl_achat ='" + num_devis + "'";
            // sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString("DATE_FORMAT(`date_bl_achat`,'%d/%m/%Y')");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getDateByNumPreCommandeAchat(String num_devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  DATE_FORMAT(`date_pre_commande`,'%d/%m/%Y') from pre_command_achat where num_bon_commande ='" + num_devis + "'";
            // sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString("DATE_FORMAT(`date_pre_commande`,'%d/%m/%Y')");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String getNameItemByIdNomCodeAdresseClient(String num_facture) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String sql = "";
        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT c.nom,c.id_Fiscale,c.adresse FROM `facture` f, client c WHERE c.numero_Client = f.id_client and f.`num_facture` ='" + num_facture + "'";
            // sql = "select " + Column_Name + " from " + table_name + " where " + Id_Column + " = '" + Id_value + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            s = "";

            while (rs.next()) {
                s = rs.getString("c.nom") + ";" + rs.getString("c.id_Fiscale") + ";" + rs.getString("c.adresse");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " sql : " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

}
