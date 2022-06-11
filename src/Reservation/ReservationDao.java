/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reservation;

import BL.*;
import static BL.BLDao.buildTableModel;
import Config.Commen_Proc;
import Devis.*;
import Conn.DataBase_connect;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
public class ReservationDao {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String year;

    boolean ajouterResa(BL d) {
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
            sql = "INSERT INTO resa(`Num_Resa`, `date_Resa`, `id_client`, `remise`, `Total_HT`, "
                    + "`Total_TTC`, `montant_tva`, `year`)  "
                    + "VALUES ('" + d.getNum_bl() + "','" + d.getDate_bl() + "'," + d.getId_client() + ","
                    + "" + d.getRemise() + "," + d.getTotale_HT() + "," + d.getTotale_TTC()
                    + "," + d.getMontant_TVA() + ",'" + year + "')";
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
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    boolean ajouterBLFournisseur(BLFournisseur d) {
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
            sql = "INSERT INTO BL_achat(Num_BL_achat, date_bl_achat, id_fournisseur,id_commercial, remise, Total_HT, Total_TTC, montant_tva, timbre, exh_TVA,"
                    + " year) "
                    + "VALUES ('" + d.getNum_bl() + "','" + d.getDate_bl() + "'," + d.getId_fournisseur() + "," + d.getId_commercial() + ","
                    + "" + d.getRemise() + "," + d.getTotale_HT() + "," + d.getTotale_TTC()
                    + "," + d.getMontant_TVA() + "," + d.getTimbre() + "," + d.getExh_TVA() + ","
                    + "'" + year + "')";
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
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    void ajouterLigneResa(ArrayList<LigneBL> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (LigneBL d : lstd) {

                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_resa(id_resa, ref_article, designation_article, qte, prix_u, remise, "
                        + "tva, total_HT, total_TTC) "
                        + "VALUES ('" + d.getId_BL() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
                        + d.getQte() + "," + d.getPrix_U() + "," + d.getRemise() + "," + d.getTVA() + "," + d.getTotal_HT()
                        + "," + d.getTotale_TTC() + ")";
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
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public DefaultTableModel afficherResa(JTable table, String FromDate, String ToDate, String NumDevis, String NomClient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT c.nom as `Client`,d.`Num_resa`,DATE_FORMAT(d.`date_resa`,'%d/%m/%Y'),d.`Total_TTC` FROM `resa` d , client c  where d.id_client=c.numero_client  and statut =1 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_resa >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_resa <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_resa  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom  like '%" + NomClient + "%'";
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
            df = buildTableModel(rs);
            table.setModel(df);

            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    void ajouterLigneBLFournisseur(ArrayList<LigneBL> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (LigneBL d : lstd) {

                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_bl_achat(id_bl_achat, ref_article, designation_article, qte, prix_u, remise, "
                        + "tva, total_HT, total_TTC) "
                        + "VALUES ('" + d.getId_BL() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
                        + d.getQte() + "," + d.getPrix_U() + "," + d.getRemise() + "," + d.getTVA() + "," + d.getTotal_HT()
                        + "," + d.getTotale_TTC() + ")";
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
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
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
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
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
            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.total_TTC > " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC < " + ToMontant + "";
                sql = sql + MontantClauseTo;
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
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
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
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
            table.setModel(df);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherResa(JTable table) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT c.nom as `Client`,d.`Num_resa`,DATE_FORMAT(d.`date_resa`,'%d/%m/%Y'),d.`Total_TTC` FROM `resa` d , client c where d.id_client=c.numero_client and statut =1 ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
            table.setModel(df);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherDetailDevis(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * from ligne_devis where id_devis='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 6);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 9);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(k.toString())), j, 9);
            }
            table.setModel(df);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherDetailBL(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * from ligne_bl where id_bl='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 6);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 9);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(k.toString())), j, 9);
            }
            table.setModel(df);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherDetailResa(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * from ligne_resa where id_resa='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 6);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 9);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(k.toString())), j, 9);
            }
            table.setModel(df);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateStatutResa(String Num_resa) {
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
            String sql = "UPDATE resa SET "
                    + "statut =0"
                    + " WHERE num_Resa ='" + Num_resa + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

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
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);

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

    public void afficherDetailFacture(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT lf.num_facture, b.`Num_bl`, DATE_FORMAT(b.`date_bl`,'%d/%m/%Y'), b.`remise`, b.`Total_TTC` from ligne_facture lf,bl b where lf.num_bl=b.num_bl and lf.num_facture='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU1.toString())), j, 3);

                Object PU = df.getValueAt(j, 4);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 4);

            }
            table.setModel(df);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

    public String maxNumResa() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT * FROM `Resa` order by id desc LIMIT 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                s = rs.getString("num_Resa");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ReservationDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

}
