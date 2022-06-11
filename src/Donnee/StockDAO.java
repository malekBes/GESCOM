/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Donnee;

import BL.BLDao;
import static BL.BLDao.buildTableModel;
import Client.ClientDao;
import static Client.ClientDao.customBuildTableModel;
import Conn.DataBase_connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Malek
 */
public class StockDAO {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void afficherStockArticle(JTable table, String Depot, String Famille, String Sous_famille, String Designation, String ref, String Marque) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "select r.ref, r.designation, r.qte,if(sum(lr.qte) is null,0,sum(lr.qte)) from article r left join ligne_resa lr on r.ref=lr.ref_article where 1 ";

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
            sql += " group by r.ref, r.designation ORDER BY r.`id` DESC";

            pst = conn.prepareStatement(sql);

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherMarge(JTable table, String Depot, String Famille, String Sous_famille, String Designation, String ref, String Marque) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "select r.ref, r.designation, r.marge from article r where 1 ";

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
            sql += " ORDER BY `id` DESC";

            pst = conn.prepareStatement(sql);

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherDepot(JTable table, String Depot, String Famille, String Sous_famille, String Designation, String ref, String Marque) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "select r.ref, r.designation, r.qte from article r where 1 ";

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
            sql += " ORDER BY `id` DESC";

            pst = conn.prepareStatement(sql);

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherRemiseParFamille(JTable table, String Famille, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT r.id, f.famille as 'Famille' , c.nom , r.`remise` FROM `remise` r left join client c on (r.`id_client`=c.numero_Client) left join famille_produit f on (f.id = r.`id_famille`) WHERE 1 and r.id_famille <> '-1' ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            /* String DateClauseFrom = "";
            if (!(Depot.isEmpty())) {
                DateClauseFrom = " and r.id_famille = '" + Depot + "'";
                sql = sql + DateClauseFrom;
            }*/
            String DateClauseTo = "";
            if (!(Famille.isEmpty())) {
                DateClauseTo = " and r.id_famille = '" + Famille + "'";
                sql = sql + DateClauseTo;
            }

            /*String MontantClauseFrom = "";
            if (!(Sous_famille.isEmpty())) {
                MontantClauseFrom = " and r.id_sous_famille = " + Sous_famille + "";
                sql = sql + MontantClauseFrom;
            }*/
 /*  String MontantClauseTo = "";
            if (!(Designation.isEmpty())) {
                MontantClauseTo = " and r.designation like '%" + Designation + "%'";
                sql = sql + MontantClauseTo;
            }*/
 /*  String NumDevisClause = "";
            if (!(ref.isEmpty())) {
                NumDevisClause = " and r.ref  like '%" + ref + "%'";
                sql = sql + NumDevisClause;

            }*/
            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and r.id_client  = " + id_client + "";
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
            DefaultTableModel df = buildTableModel(rs);

            table.setModel(df);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherRemiseParArticle(JTable table, String ref, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT r.id, r.ref , c.nom , r.`remise` FROM `remise` r left join client c on (r.`id_client`=c.numero_Client)  WHERE 1 and r.ref <> '-1' ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            /* String DateClauseFrom = "";
            if (!(Depot.isEmpty())) {
                DateClauseFrom = " and r.id_famille = '" + Depot + "'";
                sql = sql + DateClauseFrom;
            }*/
 /*  String DateClauseTo = "";
            if (!(Famille.isEmpty())) {
                DateClauseTo = " and r.id_famille = '" + Famille + "'";
                sql = sql + DateClauseTo;
            }*/

 /*String MontantClauseFrom = "";
            if (!(Sous_famille.isEmpty())) {
                MontantClauseFrom = " and r.id_sous_famille = " + Sous_famille + "";
                sql = sql + MontantClauseFrom;
            }*/
 /*  String MontantClauseTo = "";
            if (!(Designation.isEmpty())) {
                MontantClauseTo = " and r.designation like '%" + Designation + "%'";
                sql = sql + MontantClauseTo;
            }*/
            String NumDevisClause = "";
            if (!(ref.isEmpty())) {
                NumDevisClause = " and r.ref  like '%" + ref + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and r.id_client  = " + id_client + "";
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
            DefaultTableModel df = buildTableModel(rs);

            table.setModel(df);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherRemiseParClient(JTable table, String Depot, String Famille, String id_client, String Designation, String ref, String Marque) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT c.nom as 'Nom Client',f.famille 'Famille' , r.`ref`, r.`id_famille`, r.`remise` FROM `remise` r left join client c on (r.`id_client`=c.numero_Client) left join famille_produit f on (f.id=r.`id_famille`) WHERE 1 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            /* String DateClauseFrom = "";
            if (!(Depot.isEmpty())) {
                DateClauseFrom = " and r.id_famille = '" + Depot + "'";
                sql = sql + DateClauseFrom;
            }*/
            String DateClauseTo = "";
            if (!(Famille.isEmpty())) {
                DateClauseTo = " and r.id_famille = '" + Famille + "'";
                sql = sql + DateClauseTo;
            }

            /*String MontantClauseFrom = "";
            if (!(Sous_famille.isEmpty())) {
                MontantClauseFrom = " and r.id_sous_famille = " + Sous_famille + "";
                sql = sql + MontantClauseFrom;
            }*/
 /*  String MontantClauseTo = "";
            if (!(Designation.isEmpty())) {
                MontantClauseTo = " and r.designation like '%" + Designation + "%'";
                sql = sql + MontantClauseTo;
            }*/
            String NumDevisClause = "";
            if (!(ref.isEmpty())) {
                NumDevisClause = " and r.ref  like '%" + ref + "%'";
                sql = sql + NumDevisClause;

            }

            String ClientClause = "";
            if (!(Marque.isEmpty())) {
                ClientClause = " and r.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY `id` DESC";

            pst = conn.prepareStatement(sql);

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherMargeArticle(JTable table, String Depot, String Famille, String Sous_famille, String Designation, String ref, String Marque) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "select r.ref, r.designation, r.marge from article r where 1 and marge_exceptionnel = 0 ";

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
            sql += " ORDER BY `id` DESC";

            pst = conn.prepareStatement(sql);

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherMargeArticleExcept(JTable table, String Depot, String Famille, String Sous_famille, String Designation, String ref, String Marque) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "select r.ref, r.designation, r.marge from article r where 1 and marge_exceptionnel = 1 ";

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
            sql += " ORDER BY `id` DESC";

            pst = conn.prepareStatement(sql);

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateArticleStock(String ref, String stock, String design, String oldStock) {
        PreparedStatement pst;
        PreparedStatement pst1;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String currentTime = sdf.format(dt);
        try {

            String sql = "UPDATE article SET "
                    + "qte =" + stock + ""
                    + " WHERE ref = '" + ref + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

            String sql1 = "INSERT INTO `historique_stock`(`date_modif`, `ref`, `designation_article`, `Ancien`, `neveau`) "
                    + "VALUES ('" + currentTime + "','" + ref + "','" + design + "'," + oldStock + "," + stock + ")";
            pst1 = (PreparedStatement) conn.prepareStatement(sql1);
            pst1.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void updateArticleMarge(String ref, String stock, String design, String oldStock, Boolean isExcep) {
        PreparedStatement pst;
        PreparedStatement pst1;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String currentTime = sdf.format(dt);
        try {

            String sql = "UPDATE article SET "
                    + "marge =" + stock + ", marge_exceptionnel =1 "
                    + " WHERE ref ='" + ref + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

            String sql1 = "INSERT INTO `historique_marge`(`date_modif`, `ref`, `designation_article`, `Ancien`, `neveau`) "
                    + "VALUES ('" + currentTime + "','" + ref + "','" + design + "'," + oldStock + "," + stock + ")";
            pst1 = (PreparedStatement) conn.prepareStatement(sql1);
            pst1.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void updateArticleMargeExcept(String ref, String stock, String design, String oldStock, Boolean isExcep) {
        PreparedStatement pst;
        PreparedStatement pst1;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String currentTime = sdf.format(dt);
        try {
            /*   String ss = "";
            if (isExcep) {

                String sqlchek = "select marge_exceptionnel from article WHERE ref ='" + ref + "'";
                pst = (PreparedStatement) conn.prepareStatement(sqlchek);
                rs = pst.executeQuery();

                while (rs.next()) {
                    ss = rs.getString("marge_exceptionnel");
                }
            }
            if ((ss.equals("1")) && (isExcep)) {*/
            String sql = "UPDATE article SET "
                    + "marge =" + stock + ", marge_exceptionnel =0 "
                    + " WHERE ref ='" + ref + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

            String sql1 = "INSERT INTO `historique_marge`(`date_modif`, `ref`, `designation_article`, `Ancien`, `neveau`) "
                    + "VALUES ('" + currentTime + "','" + ref + "','" + design + "'," + oldStock + "," + stock + ")";
            pst1 = (PreparedStatement) conn.prepareStatement(sql1);
            pst1.execute();
            //}

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public String listArticleMargeExcep(JTable table, String ref) {
        PreparedStatement pst;
        PreparedStatement pst1;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String ss = "";

        String currentTime = sdf.format(dt);
        try {

            String sqlchek = "select ref,marge,marge_exceptionnel from article WHERE ref in ('" + ref + "')";
            pst = (PreparedStatement) conn.prepareStatement(sqlchek);
            rs = pst.executeQuery();

            while (rs.next()) {
                ss = rs.getString("ref") + ";" + rs.getString("marge");
            }

            DefaultTableModel df = buildTableModel(rs);

            table.setModel(df);
            return ss;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        return ss;
    }

    public Vector<Vector<Object>> afficherHistoriqueStock(String ref) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT DATE_FORMAT(`date_modif`,'%d/%m/%Y'), `ref`, `designation_article`, `Ancien`, `neveau` FROM `historique_stock` where 1";

            if (!ref.equals("-1")) {
                sql += " and `ref` = '" + ref + "' ";
            }

            sql += " ORDER BY `id` DESC";

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
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public Vector<Vector<Object>> afficherHistoriqueMarge(String ref) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT DATE_FORMAT(`date_modif`,'%d/%m/%Y'), `ref`, `designation_article`, `Ancien`, `neveau` FROM `historique_marge` where 1";

            if (!ref.equals("-1")) {
                sql += " and `ref` = '" + ref + "'  ";
            }

            sql += " ORDER BY `id` DESC";

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
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public void insertRemiseParFamille(Article.Article r) {
        PreparedStatement pst;
        PreparedStatement pst1;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String currentTime = sdf.format(dt);
        try {

            String sql1 = "INSERT INTO `remise`( `id_client`, `ref`, `id_famille`, `remise`)  "
                    + "VALUES (" + r.getId_client() + ",'-1'," + r.getId_famille() + "," + r.getRemise() + ")";
            pst1 = (PreparedStatement) conn.prepareStatement(sql1);
            pst1.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void insertRemiseParArticle(Article.Article r) {
        PreparedStatement pst;
        PreparedStatement pst1;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String currentTime = sdf.format(dt);
        try {

            String sql1 = "INSERT INTO `remise`( `id_client`, `ref`, `id_famille`, `remise`)  "
                    + "VALUES (" + r.getId_client() + ",'" + r.getRef() + "',-1," + r.getRemise() + ")";
            pst1 = (PreparedStatement) conn.prepareStatement(sql1);
            pst1.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void supprimerRemise(String id_remise) {
        PreparedStatement pst;
        PreparedStatement pst1;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String currentTime = sdf.format(dt);
        try {

            String sql1 = "delete from remise where id=" + id_remise + "";
            pst1 = (PreparedStatement) conn.prepareStatement(sql1);
            pst1.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

}
