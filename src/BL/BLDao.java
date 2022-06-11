/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import Config.Commen_Proc;
import Devis.*;
import javax.swing.JComboBox;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mlek
 */
public class BLDao {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String year;

    boolean ajouterBL(BL d) {
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

            sql = "INSERT INTO BL(Num_BL, date_bl, id_client,id_commercial, remise, Total_HT, Total_TTC, montant_tva, timbre, exh_TVA,"
                    + " Code_TVA, Infos_bl, Afficher_total, facture_proformat, "
                    + "Afficher_validiter, Afficher_prix, Edit_ref,year) "
                    + "VALUES ('" + d.getNum_bl() + "','" + d.getDate_bl() + "'," + d.getId_client() + "," + d.getId_commercial() + ","
                    + "" + d.getRemise() + "," + d.getTotale_HT() + "," + d.getTotale_TTC()
                    + "," + d.getMontant_TVA() + "," + d.getTimbre() + "," + d.getExh_TVA() + ",'" + d.getCode_TVA() + "',"
                    + "'" + d.getInfos_bl() + "'," + d.getAfficher_total() + "," + d.getFacture_proformat()
                    + "," + d.getAfficher_validiter() + "," + d.getAfficher_prix() + "," + d.getEdit_ref() + ",'" + year + "')";
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public HashMap listBL(JComboBox comboBox) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        comboBox.removeAllItems();
        comboBox.addItem("-");
        try {
            String sql = "select num_bl from bl where statut = 0 order by date_bl";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                map.put(rs.getString("num_bl"), rs.getString("num_bl"));

                comboBox.addItem(rs.getString("num_bl"));
            }

            /* for (String s : map.keySet()) {
                comboBox.addItem(s);
            }*/
            return map;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");

            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return map;
    }

    public HashMap listDevis(JComboBox comboBox) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        comboBox.removeAllItems();
        comboBox.addItem("-");
        try {
            String sql = "select num_devis from devis where statut = 0 order by date_devis";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                map.put(rs.getString("num_devis"), rs.getString("num_devis"));

                comboBox.addItem(rs.getString("num_devis"));
            }

            /* for (String s : map.keySet()) {
                comboBox.addItem(s);
            }*/
            return map;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");

            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return map;
    }

    boolean modifierBL(BL d) {
        PreparedStatement pst;
        year = Commen_Proc.YearVal;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();
            BL c = d;
            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
            sql = "UPDATE bl SET "
                    + "date_bl='" + c.getDate_bl() + "',"
                    + "id_client=" + c.getId_client() + ","
                    + "id_commercial=" + c.getId_commercial() + ","
                    + "remise=" + c.getRemise() + ","
                    + "Total_HT=" + c.getTotale_HT() + ","
                    + "Total_TTC=" + c.getTotale_TTC() + ","
                    + "montant_tva=" + c.getMontant_TVA() + ","
                    + "timbre=" + c.getTimbre() + ","
                    + "exh_TVA=" + c.getExh_TVA() + ","
                    + "Code_TVA='" + c.getCode_TVA() + "',"
                    + "Infos_bl='" + c.getInfos_bl() + "',"
                    + "Afficher_total=" + c.getAfficher_total() + ","
                    + "facture_proformat=" + c.getFacture_proformat() + ","
                    + "Afficher_validiter=" + c.getAfficher_validiter() + ","
                    + "Afficher_prix=" + c.getAfficher_prix() + ","
                    + "Edit_ref=" + c.getEdit_ref() + ","
                    + "statut=1 ,"
                    + "year='" + year + "'"
                    + " WHERE Num_BL = '" + c.getNum_bl() + "'";

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    boolean modifierBLAchat(BLFournisseur d) {
        PreparedStatement pst;
        year = Commen_Proc.YearVal;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();
            BLFournisseur c = d;
            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
            sql = "UPDATE bl SET "
                    + "date_bl='" + c.getDate_bl() + "',"
                    + "id_client=" + c.getId_client() + ","
                    + "id_commercial=" + c.getId_commercial() + ","
                    + "remise=" + c.getRemise() + ","
                    + "Total_HT=" + c.getTotale_HT() + ","
                    + "Total_TTC=" + c.getTotale_TTC() + ","
                    + "montant_tva=" + c.getMontant_TVA() + ","
                    + "timbre=" + c.getTimbre() + ","
                    + "exh_TVA=" + c.getExh_TVA() + ","
                    + "Code_TVA='" + c.getCode_TVA() + "',"
                    + "Infos_bl='" + c.getInfos_bl() + "',"
                    + "Afficher_total=" + c.getAfficher_total() + ","
                    + "facture_proformat=" + c.getFacture_proformat() + ","
                    + "Afficher_validiter=" + c.getAfficher_validiter() + ","
                    + "Afficher_prix=" + c.getAfficher_prix() + ","
                    + "Edit_ref=" + c.getEdit_ref() + ","
                    + "year='" + year + "'"
                    + " WHERE Num_BL = '" + c.getNum_bl() + "'";

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public Vector<Vector<Object>> afficherListBL(String NumBL) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT c.nom as `Client`,d.`Num_bl`,DATE_FORMAT(d.`date_bl`,'%d/%m/%Y'),d.`Total_TTC`, IF(d.invoiced = 1,\"F\", \"N\" ) FROM `bl` d , client c  where d.id_client=c.numero_client and d.statut =1 ";
            String NumDevisClause = "";
            if (!(NumBL.isEmpty())) {
                NumDevisClause = " and d.Num_bl  like '%" + NumBL + "%'";
                sql = sql + NumDevisClause;

            }
            sql += " ORDER BY d.`Num_bl` DESC";

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public Vector<Vector<Object>> afficherListDevis(String NumBL) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT c.nom as `Client`,d.`Num_devis`,DATE_FORMAT(d.`date_devis`,'%d/%m/%Y'),d.`Total_TTC` FROM `devis` d , client c  where d.id_client=c.numero_client and d.statut =1 ";
            String NumDevisClause = "";
            if (!(NumBL.isEmpty())) {
                NumDevisClause = " and d.Num_devis  like '%" + NumBL + "%'";
                sql = sql + NumDevisClause;

            }
            sql += " ORDER BY d.`Num_devis` DESC";

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public Vector<Vector<Object>> afficherListFacture(String NumBL) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT c.nom as `Client`,d.`Num_facture`,DATE_FORMAT(d.`date_facture`,'%d/%m/%Y'),d.`TTC` FROM `facture` d , client c  where d.id_client=c.numero_client and d.statut =1 ";
            String NumDevisClause = "";
            if (!(NumBL.isEmpty())) {
                NumDevisClause = " and d.Num_facture  like '%" + NumBL + "%'";
                sql = sql + NumDevisClause;

            }
            sql += " ORDER BY d.`Num_facture` DESC";

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public static Vector<Vector<Object>> customBuildTableModel(ResultSet rs)
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

        return data;

    }

    boolean ajouterBLDepot(BL d) {
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
            sql = "INSERT INTO BL_depot(Num_BL_depot, date_bl_depot, id_depot_src,id_depot_dest, remise, Total_HT, Total_TTC, montant_tva, timbre, exh_TVA,"
                    + " Code_TVA, year) "
                    + "VALUES ('" + d.getNum_bl() + "','" + d.getDate_bl() + "'," + d.getId_depot_src() + "," + d.getId_depot_dest() + ","
                    + "" + d.getRemise() + "," + d.getTotale_HT() + "," + d.getTotale_TTC()
                    + "," + d.getMontant_TVA() + "," + d.getTimbre() + "," + d.getExh_TVA() + ",'"
                    + d.getCode_TVA() + "','" + year + "')";
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    void ajouterLigneBL(ArrayList<LigneBL> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int i = 0;
            for (LigneBL d : lstd) {
                i++;
                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_bl(id_bl, ref_article, designation_article, qte, prix_u, remise, "
                        + "tva, total_HT, total_TTC,rank) "
                        + "VALUES ('" + d.getId_BL() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
                        + d.getQte() + "," + d.getPrix_U() + "," + d.getRemise() + "," + d.getTVA() + "," + d.getTotal_HT()
                        + "," + d.getTotale_TTC() + ",'" + i + "')";
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

    void modifierLigneBL(ArrayList<LigneBL> lstd) {
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

            for (LigneBL d : lstd) {
                LigneBL c = d;
                String currentTime = sdf.format(dt);

                sql = "UPDATE ligne_bl SET "
                        + "id_bl='-1'"
                        + " WHERE id_bl = '" + c.getId_Devis() + "'";

//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }
           /* sqldel = "delete from ligne_bl where id_bl ='-1'";
            pst = (PreparedStatement) conn.prepareStatement(sqldel);
            pst.execute();*/
            int i = 0;
            for (LigneBL d : lstd) {
                i++;
                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_bl(id_bl, ref_article, designation_article, qte, prix_u, remise, "
                        + "tva, total_HT, total_TTC,rank) "
                        + "VALUES ('" + d.getId_BL() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
                        + d.getQte() + "," + d.getPrix_U() + "," + d.getRemise() + "," + d.getTVA() + "," + d.getTotal_HT()
                        + "," + d.getTotale_TTC() + ",'" + i + "')";
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

    void modifierLigneBLAchat(ArrayList<LigneBL> lstd) {
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

            for (LigneBL d : lstd) {
                LigneBL c = d;
                String currentTime = sdf.format(dt);

                sql = "UPDATE ligne_bl_achat SET "
                        + "id_bl_achat='-1'"
                        + " WHERE id_bl_achat = '" + c.getId_Devis() + "'";

//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }
            sqldel = "delete from ligne_bl_achat where id_bl_achat ='-1'";
            pst = (PreparedStatement) conn.prepareStatement(sqldel);
            pst.execute();

            for (LigneBL d : lstd) {

                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_bl_achat(id_bl_achat, ref_article, designation_article, qte, prix_u, remise, "
                        + "tva, total_HT, total_TTC,rank) "
                        + "VALUES ('" + d.getId_BL() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    void ajouterLigneBLDepot(ArrayList<LigneBL> lstd) {
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
                sql = "INSERT INTO ligne_bl_depot(id_bl_depot, ref_article, designation_article, qte, prix_u, remise, "
                        + "tva, total_HT, total_TTC,rank) "
                        + "VALUES ('" + d.getId_BL() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
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
                        + "tva, total_HT, total_TTC,rank) "
                        + "VALUES ('" + d.getId_BL() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherReliquat(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT c.nom as `Client`,d.`Num_reliquat`,DATE_FORMAT(d.`date_reliquat`,'%d/%m/%Y'),d.`Total_TTC` FROM `reliquat` d , client c where d.id_client=c.numero_client";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClause = "";
            if (!(FromDate.isEmpty() & ToDate.isEmpty())) {
                DateClause = " and d.date_reliquat BETWEEN '" + FromDate + "' AND '" + ToDate + "'";
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
                NumDevisClause = " and d.Num_reliquat  like '%" + NumDevis + "%'";
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
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
            sql = "SELECT c.nom as `Client`,d.`Num_devis`,DATE_FORMAT(d.`date_devis`,'%d/%m/%Y'),d.`Total_TTC` FROM `devis` d , client c where d.id_client=c.numero_client and d.statut = 1 order by d.`date_devis` desc ";

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherReliquat(JTable table) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT c.nom as `Client`,d.`Num_reliquat`,DATE_FORMAT(d.`date_reliquat`,'%d/%m/%Y'),d.`Total_TTC` FROM `reliquat` d , client c where d.id_client=c.numero_client and d.statut = 1 order by d.`date_reliquat` desc ";

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
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
            sql = "select ld.ref_article, ld.designation_article, ld.`prix_u`, ld.qte, ld.remise, ld.tva,DATE_FORMAT(d.date_devis,'%d/%m/%Y') "
                    + " from ligne_devis ld , devis d , client c where ld.id_devis = d.num_devis "
                    + " and d.id_client = c.numero_client and c.nom='" + NomClient + "' "
                    + " and ld.ref_article = '" + Ref_article + "' order by d.`date_devis` desc limit 1";
            System.out.println(sql);

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            setHeadersDetail(table);
            table.setModel(df);

        } catch (Exception e) {
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

    public void afficherHistPrixBL(JTable table, String NomClient, String Ref_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "select ld.ref_article, ld.designation_article, ld.`prix_u`, ld.qte, ld.remise, ld.tva,DATE_FORMAT(d.date_bl,'%d/%m/%Y') "
                    + " from ligne_bl ld , bl d , client c where ld.id_bl = d.num_bl "
                    + " and d.id_client = c.numero_client and c.nom='" + NomClient + "' "
                    + " and ld.ref_article = '" + Ref_article + "' order by d.`date_bl` desc ";
            System.out.println(sql);

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            setHeadersDetail(table);
            table.setModel(df);

        } catch (Exception e) {
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

    private void setHeadersDetail(JTable table) {
        table.getColumnModel().getColumn(0).setHeaderValue("Référence");
        table.getColumnModel().getColumn(1).setHeaderValue("Designation");
        table.getColumnModel().getColumn(2).setHeaderValue("Prix Unit HT");
        table.getColumnModel().getColumn(3).setHeaderValue("Quantité");
        table.getColumnModel().getColumn(4).setHeaderValue("Remise %");
        table.getColumnModel().getColumn(5).setHeaderValue("TVA %");
        table.getColumnModel().getColumn(6).setHeaderValue("Date Devis");
        table.getTableHeader().resizeAndRepaint();
    }

    public DefaultTableModel afficherDetailDevis(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        DefaultTableModel df = null;

        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT id,ref_article,designation_article,prix_u,qte,total_ht,remise,tva,total_ttc from ligne_devis where id_devis='" + Num_Devis + "' group by ref_article order by cast(rank as unsigned)";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 3);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 3);

                Object PU = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 5);

                Object v = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 6);

                Object k = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 8);
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailDevisPreCommande(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        DefaultTableModel df = null;

        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT id,ref_article,designation_article,prix_u,qte,total_ht,remise,tva,total_ttc,rank from ligne_devis where id_devis='" + Num_Devis + "' group by ref_article order by cast(rank as unsigned)";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 3);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 3);

                Object PU = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 5);

                Object v = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 6);

                Object k = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 8);
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailReliquat(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        DefaultTableModel df = null;

        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT id,ref_article,designation_article,prix_u,qte,total_ht,remise,tva,total_ttc from ligne_reliquat where id_reliquat='" + Num_Devis + "' group by ref_article";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 3);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 3);

                Object PU = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 5);

                Object v = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 6);

                Object k = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 8);
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailRechercheDevis(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        DefaultTableModel df = null;

        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * from ligne_devis where id_devis='" + Num_Devis + "'  group by ref_article order by cast(rank as unsigned)";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 7);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 7);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                /*  Object k = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 8);*/
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailRechercheReliquat(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        DefaultTableModel df = null;

        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * from ligne_reliquat where id_reliquat='" + Num_Devis + "' group by ref_article";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 7);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 7);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                /*  Object k = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 8);*/
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailRechercheMultipleReliquat(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        DefaultTableModel df = null;

        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  if(c.nom is null , '', c.nom ) as nom,re.date_reliquat,r.* from ligne_reliquat r left join reliquat re on re.num_reliquat=r.id_reliquat left join client c on c.numero_client=re.id_client where r.id_reliquat in (" + Num_Devis + ") ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            //  df = buildTableModelDetailRELIQUAT(rs);

            int qteTotalClient = 0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();

            columnNames.add("id");
            columnNames.add("Client");

            columnNames.add("Date Reliquat");
            columnNames.add("Num Reliquat");
            columnNames.add("Référence");
            columnNames.add("Designation");
            columnNames.add("Quantité");
            columnNames.add("Prix Unit HT");
            columnNames.add("Remise %");
            columnNames.add("TVA %");
            columnNames.add("Totale HT");
            columnNames.add("Totale TTC");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            String previous_val = "";
            while (rs.next()) {
                Object LigneData[] = new Object[12];
//, , , , , , , , , 
                LigneData[0] = rs.getString("id");

                if (!previous_val.equals(rs.getString("id_reliquat"))) {
                    LigneData[3] = rs.getString("id_reliquat");
                    LigneData[1] = rs.getString("nom");
                    LigneData[2] = rs.getString("date_reliquat");

                } else {
                    LigneData[3] = "";
                    LigneData[1] = "";
                    LigneData[2] = "";

                }

                previous_val = rs.getString("id_reliquat");

                LigneData[4] = rs.getString("ref_article");
                LigneData[5] = rs.getString("designation_article");
                LigneData[6] = String.valueOf(rs.getString("qte"));

                LigneData[7] = String.valueOf(formatDouble(Double.parseDouble(rs.getString("prix_u"))));
                LigneData[8] = String.valueOf(rs.getString("remise"));
                LigneData[9] = String.valueOf(rs.getString("tva"));
                LigneData[10] = String.valueOf(formatDouble(Double.parseDouble(rs.getString("total_HT"))));
                LigneData[11] = String.valueOf(formatDouble(Double.parseDouble(rs.getString("total_TTC"))));

                df.insertRow(startAdd, LigneData);

                // qteTotalClient += Integer.valueOf(rs.getString("quantity"));
                startAdd++;

            }

            // table.setModel(df);
            table.setModel(df);
            return df;
        } catch (Exception e) {
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

    public String afficherDevisPourAnnuler(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String s = "";

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  DATE_FORMAT(d.date_devis,'%d/%m/%Y'),d.total_ttc,c.nom,d.id_client,d.passager from devis d left join client c on c.numero_Client=d.id_client where  d.num_devis='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString("d.id_client").equals("0")) {
                    s = rs.getString("DATE_FORMAT(d.date_devis,'%d/%m/%Y')") + ";" + formatDouble(Double.valueOf(rs.getString("d.total_ttc"))) + ";" + rs.getString("d.passager");
                } else {
                    s = rs.getString("DATE_FORMAT(d.date_devis,'%d/%m/%Y')") + ";" + formatDouble(Double.valueOf(rs.getString("d.total_ttc"))) + ";" + rs.getString("c.nom");

                }
            }
            return s;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;

    }

    public String afficherFacturePourAnnuler(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String s = "";

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT distinct DATE_FORMAT(d.date_facture,'%d/%m/%Y') as 'date' ,d.ttc,c.nom,d.type_facture,d.passager from facture d left join client c  on c.numero_Client=d.id_client where  d.num_facture='" + Num_Devis + "' and d.statut =1 and d.reglement = 'Non' ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString("d.type_facture").equals("Passager")) {

                    s = rs.getString("date") + ";" + formatDouble(Double.valueOf(rs.getString("d.ttc"))) + ";" + rs.getString("d.passager") + ";" + rs.getString("d.type_facture");
                } else {
                    s = rs.getString("date") + ";" + formatDouble(Double.valueOf(rs.getString("d.ttc"))) + ";" + rs.getString("c.nom") + ";" + rs.getString("d.type_facture");

                }
            }
            return s;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;

    }

    public String afficherFacturePourModif(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String s = "";

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  DATE_FORMAT(d.date_facture,'%d/%m/%Y'),d.ttc,c.nom,d.type_facture,d.passager from facture d left join client c  on c.numero_Client=d.id_client where  d.num_facture='" + Num_Devis + "' and d.statut =1";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString("d.type_facture").equals("Passager")) {

                    s = rs.getString("DATE_FORMAT(d.date_facture,'%d/%m/%Y')") + ";" + formatDouble(Double.valueOf(rs.getString("d.ttc"))) + ";" + rs.getString("d.passager") + ";" + rs.getString("d.type_facture");
                } else {
                    s = rs.getString("DATE_FORMAT(d.date_facture,'%d/%m/%Y')") + ";" + formatDouble(Double.valueOf(rs.getString("d.ttc"))) + ";" + rs.getString("c.nom") + ";" + rs.getString("d.type_facture");

                }
            }
            return s;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;

    }

    public String afficherBLPourAnnuler(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String s = "";

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  DATE_FORMAT(d.date_bl,'%d/%m/%Y'),d.total_ttc,c.nom from bl d ,client c  where c.numero_Client=d.id_client and d.num_bl='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                s = rs.getString("DATE_FORMAT(d.date_bl,'%d/%m/%Y')") + ";" + formatDouble(Double.valueOf(rs.getString("d.total_ttc"))) + ";" + rs.getString("c.nom");
            }
            return s;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;

    }

    public String afficherBLPourModif(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String s = "";

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  DATE_FORMAT(d.date_bl,'%d/%m/%Y'),d.total_ttc,c.nom,d.id_client,d.invoiced from bl d ,client c  where c.numero_Client=d.id_client and d.num_bl='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                s = rs.getString("DATE_FORMAT(d.date_bl,'%d/%m/%Y')") + ";" + formatDouble(Double.valueOf(rs.getString("d.total_ttc"))) + ";" + rs.getString("c.nom") + ";" + rs.getString("d.id_client") + ";" + rs.getString("d.invoiced");
            }
            return s;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;

    }

    public String afficherBLAchatPourModif(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String s = "";

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  DATE_FORMAT(d.date_bl_achat,'%d/%m/%Y'),d.total_ttc,c.nom,d.invoiced from bl_achat d ,fournisseur c  where c.id=d.id_fournisseur and d.num_bl_achat='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                s = rs.getString("DATE_FORMAT(d.date_bl_achat,'%d/%m/%Y')") + ";" + formatDouble(Double.valueOf(rs.getString("d.total_ttc"))) + ";" + rs.getString("c.nom") + ";" + rs.getString("d.invoiced");
            }
            return s;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;

    }

    public String afficherBLAchatPourAnnuler(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String s = "";

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  DATE_FORMAT(d.date_bl_achat,'%d/%m/%Y'),d.total_ttc,f.nom,d.invoiced from bl_achat d left join fournisseur f  on (f.id=d.id_fournisseur) where d.num_bl_achat='" + Num_Devis + "' and statut = 1";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                s = rs.getString("DATE_FORMAT(d.date_bl_achat,'%d/%m/%Y')") + ";" + formatDouble(Double.valueOf(rs.getString("d.total_ttc"))) + ";" + rs.getString("f.nom") + ";" + rs.getString("d.invoiced");
            }
            return s;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;

    }

    public String afficherFactureAchatPourAnnuler(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String s = "";

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  DATE_FORMAT(d.date_facture_achat,'%d/%m/%Y'),d.ttc,f.nom from facture_achat d left join fournisseur f  on (f.id=d.id_fournisseur) where d.num_facture_achat='" + Num_Devis + "' and statut = 1";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                s = rs.getString("DATE_FORMAT(d.date_facture_achat,'%d/%m/%Y')") + ";" + formatDouble(Double.valueOf(rs.getString("d.ttc"))) + ";" + rs.getString("f.nom");
            }
            return s;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;

    }

    public String afficherCommandeAchatPourAnnuler(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String s = "";

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT   DATE_FORMAT(d.date_commande,'%d/%m/%Y'),f.nom from commande_achat d left join fournisseur f  on (f.id=d.id_fournisseur) where d.num_Commande='" + Num_Devis + "' and statut = 1";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                s = rs.getString("DATE_FORMAT(d.date_commande,'%d/%m/%Y')") + ";" + rs.getString("f.nom");
            }
            return s;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;

    }

    public String afficherPreCommandeAchatPourAnnuler(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String s = "";

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT DATE_FORMAT(d.date_pre_commande,'%d/%m/%Y'),f.nom,c.nom from pre_command_achat d left join fournisseur f  on (f.id=d.id_fournisseur) left join client c on (c.numero_client=d.id_client) where d.num_bon_commande='" + Num_Devis + "' and statut = 1 ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                s = rs.getString("DATE_FORMAT(d.date_pre_commande,'%d/%m/%Y')") + ";" + rs.getString("f.nom") + ";" + rs.getString("c.nom");
            }

            return s;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;

    }

    public DefaultTableModel afficherPreCommandeAchatPourModif(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String s = "";
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT `id`, `ref_article`, `designation_article`, `qte`, rank from ligne_pre_commande where id_pre_commande='" + Num_Devis + "' ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            /*  while (rs.next()) {
                s = rs.getString("DATE_FORMAT(d.date_pre_commande,'%d/%m/%Y')") + ";" + rs.getString("f.nom") + ";" + rs.getString("c.nom");
            }*/
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;

    }

    public String afficherAvoirPourAnnuler(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        String s = "";

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  DATE_FORMAT(d.date_avoir,'%d/%m/%Y'),d.total_ttc,c.nom,d.type_avoir,d.passager from avoir d left join client c on (c.numero_Client=d.id_client) where d.num_avoir='" + Num_Devis + "' and d.statut = 1 ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString("d.type_avoir").equals("Passager")) {
                    s = rs.getString("DATE_FORMAT(d.date_avoir,'%d/%m/%Y')") + ";" + formatDouble(Double.valueOf(rs.getString("d.total_ttc"))) + ";" + rs.getString("d.passager") + ";" + rs.getString("d.type_avoir");
                } else {
                    s = rs.getString("DATE_FORMAT(d.date_avoir,'%d/%m/%Y')") + ";" + formatDouble(Double.valueOf(rs.getString("d.total_ttc"))) + ";" + rs.getString("c.nom") + ";" + rs.getString("d.type_avoir");
                }
            }
            return s;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;

    }

    public DefaultTableModel afficherDetailAvoir(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT c.nom,la.* from ligne_avoir la left join avoir a on a.num_avoir=la.id_avoir left join client c on c.numero_client=a.id_client  where id_avoir='" + Num_Devis + "'  group by ref_article order by la.rank";

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;

    }

    public void afficherDetailAvoirAchat(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * from ligne_avoir_achat where id_avoir_achat='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
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
        } catch (Exception e) {
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

    public DefaultTableModel afficherDetailBL(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * from ligne_bl where id_bl='" + Num_Devis + "' order by cast(rank as unsigned)";

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }
//

    public DefaultTableModel afficherDetailBLV2(JTable table, String Num_Devis, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * from ligne_bl where id_bl='" + Num_Devis + "' order by cast(rank as unsigned)";

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }
//

    public DefaultTableModel afficherDetailBLOnExport(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT `id`,  `ref_article`, `designation_article`,`prix_u`, `qte`, `total_HT`, `remise`, `tva`,  `total_TTC` from ligne_bl where id_bl='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 3);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 3);

                Object PU = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 5);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                /*Object k = df.getValueAt(j, 9);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 9);*/
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailBLAchat(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * from ligne_bl_achat where id_bl_achat='" + Num_Devis + "'";

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailCommandeAchat(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial

            sql
                    = //"SELECT lc.`id`, lc.`num_commande`, lc.`num_pre_command`, lc.`date_pre_command`,c.nom from ligne_commande_achat lc left join client c on (c.numero_client=lc.id_client) where lc.num_commande='" + Num_Devis + "'";
                    "SELECT f.Nom,pc.id_commande,pc.`num_bon_commande`,DATE_FORMAT( pc.`date_pre_commande`,'%d/%m/%Y') as date_pre_commande, c.nom FROM `pre_command_achat` pc left join fournisseur f on pc.`id_fournisseur` = f.id left join client c on pc.`id_client`=c.numero_Client WHERE  pc.id_commande='" + Num_Devis + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);
            table.setModel(df);
            /*  for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 6);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 9);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 9);
            }*/
            table.setModel(df);
            return df;
        } catch (Exception e) {
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

    public DefaultTableModel afficherDetailFactureAchatAnnuler(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT lf.* FROM `ligne_facture_achat` lf where lf.num_facture_achat = '" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);
            table.setModel(df);
            /*  for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 6);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 9);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 9);
            }*/
            table.setModel(df);
            return df;
        } catch (Exception e) {
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

    public DefaultTableModel afficherDetailPreCommandeAchat(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT lpc.`id`,lpc.id_pre_commande, lpc.`ref_article`, lpc.`designation_article`, lpc.`qte`from ligne_pre_commande lpc where lpc.id_pre_commande='" + Num_Devis + "' ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);
            table.setModel(df);
            /*  for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 6);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 9);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 9);
            }*/
            table.setModel(df);
            return df;
        } catch (Exception e) {
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

    public void afficherDetailBLDepot(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * from ligne_bl_depot where id_bl_depot='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
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
        } catch (Exception e) {
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
            sql = "SELECT DISTINCT lf.num_facture, b.`Num_bl`, DATE_FORMAT(b.`date_bl`,'%d/%m/%Y'), b.`remise`, f.`TTC` from ligne_facture lf, bl b, facture f where lf.num_bl=b.num_bl and f.num_facture=lf.num_facture and lf.num_facture='" + Num_Devis + "' and b.statut =1";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 3);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 3);

                Object PU = df.getValueAt(j, 4);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 4);

            }
            table.setModel(df);
        } catch (Exception e) {
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

    public void afficherDetailLigneFacture(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        Vector<String> columnNames = new Vector<String>();

        //lb.id, lb.id_bl, lb.ref_article, lb.designation_article, lb.qte, lb.prix_u, lb.total_ht, lb.tva, DATE_FORMAT(`date_bl`,'%d/%m/%Y') as date_bl_fomrated 
        columnNames.add("id_bl");
        columnNames.add("ref_article");
        columnNames.add("designation_article");
        columnNames.add("qte");
        columnNames.add("prix_u");
        columnNames.add("total_HT");//total_TTC

        columnNames.add("tva");
        columnNames.add("date_bl_fomrated");
        columnNames.add("id");//lb.remise
        columnNames.add("remise");//
        columnNames.add("total_TTC");//total_TTC
        columnNames.add("num_facture");//total_TTC
        columnNames.add("adresse");//total_TTC
        columnNames.add("nom");//total_TTC
        columnNames.add("id_Fiscale");//total_TTC

        Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
        DefaultTableModel df = new DefaultTableModel(data1, columnNames);

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial

//            sql = "SELECT DISTINCT lf.num_facture, b.`Num_bl`, DATE_FORMAT(b.`date_bl`,'%d/%m/%Y'), b.`remise`, f.`TTC` from ligne_facture lf, bl b, facture f where lf.num_bl=b.num_bl and f.num_facture=lf.num_facture and lf.num_facture='" + Num_Devis + "' and b.statut =1";
            sql = "SELECT distinct lb.id, lb.id_bl, lb.ref_article, lb.designation_article, lb.qte, lb.prix_u, lb.total_ht,lb.total_ttc, lb.tva,lb.remise,f.num_facture, if(c.adresse is null , '', c.adresse) as 'c.adresse',if(c.nom is null,f.passager,c.nom) as 'c.nom', if(c.id_fiscale is null , f.code_tva_passager ,c.id_fiscale) as 'c.id_fiscale',  if(date_bl is null ,'', DATE_FORMAT(`date_bl`,'%d/%m/%Y') ) as date_bl_fomrated "
                    + " FROM `ligne_bl` lb left join ligne_facture lf on lb.id_bl=lf.num_bl left join facture f on f.num_facture=lf.num_facture  left join client c on c.numero_client=f.id_client left join bl bl on bl.num_bl=lb.id_bl   WHERE f.num_facture = '" + Num_Devis + "'   order by lb.id_bl";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            int i = 0;
            int startAdd = 0;
            String last_num_bl = "";
            String last_date_bl = "";

            while (rs.next()) {
                Object LigneData[] = new Object[15];

                if (i == 0) {
                    LigneData[0] = rs.getString("lb.id_bl").toString();
                    LigneData[7] = rs.getString("date_bl_fomrated");
                } else if (rs.getString("lb.id_bl").equals(last_num_bl)) {
                    LigneData[0] = "";
                } else {
                    LigneData[0] = rs.getString("lb.id_bl").toString();
                    LigneData[7] = rs.getString("date_bl_fomrated");
                }
                last_num_bl = rs.getString("lb.id_bl");
                last_date_bl = rs.getString("date_bl_fomrated");
                //----------
                //----------
                //  nomclient = rs.getString("Client");
                // LigneData[0] = nomclient;
                LigneData[1] = rs.getString("lb.ref_article");
                LigneData[2] = rs.getString("lb.designation_article");

                LigneData[3] = rs.getString("lb.qte").toString();
                LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("lb.prix_u"))).toString();
                LigneData[5] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("lb.total_ht"))).toString();
                LigneData[6] = rs.getString("lb.tva") + " %";

                /*  if (i == 0) {
                    LigneData[7] = rs.getString("date_bl_fomrated");
                } else if (rs.getString("date_bl_fomrated").equals(last_date_bl)) {
                    LigneData[7] = "";
                } else {
                    LigneData[7] = rs.getString("date_bl_fomrated");
                }
                last_date_bl = rs.getString("date_bl_fomrated");
                 */
                LigneData[8] = rs.getString("lb.id");//lb.remise
                LigneData[9] = rs.getString("lb.remise").toString();//lb.
                LigneData[10] = rs.getString("lb.total_ttc").toString();//lb.
                LigneData[11] = rs.getString("f.num_facture");
                LigneData[12] = rs.getString("c.adresse");
                LigneData[13] = rs.getString("c.nom");
                LigneData[14] = rs.getString("c.id_fiscale");

                // df.insertRow(startAdd, LigneData);
                // startAdd++;
                df.insertRow(startAdd, LigneData);

                //  qteTotalClient += Integer.valueOf(rs.getString("sum(lb.qte)"));
                startAdd++;
                i++;
            }
            // sql = "SELECT DISTINCT lb.id, lb.id_bl, lb.ref_article, lb.designation_article, lb.qte, lb.prix_u, lb.total_ht, lb.tva, DATE_FORMAT(`date_bl`,'%d/%m/%Y') as date_bl_fomrated FROM `ligne_bl` lb left join ligne_facture lf on lb.id_bl=lf.num_bl left join facture f on f.num_facture=lf.num_facture  left join client c on c.numero_client=f.id_client left join bl bl on bl.num_bl=lb.id_bl   WHERE f.num_facture = $P{num_facture}  GROUP by lb.ref_article";
            // DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            /*    for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 6);

            }*/
            // table.setModel(df);
        } catch (Exception e) {
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

    public Vector<Vector<Object>> afficherDetailFactureModif(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT distinct c.nom, b.`Num_bl`, DATE_FORMAT(b.`date_bl`,'%d/%m/%Y') as date,b.total_HT, b.`remise`,b.montant_tva, b.`Total_TTC` from ligne_facture lf, bl b, client c where c.numero_client=b.id_client and lf.num_bl=b.num_bl and lf.num_facture='" + Num_Devis + "' and statut =1";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 3);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 3);
                Object PU2 = df.getValueAt(j, 4);
                df.setValueAt(formatDouble(Double.parseDouble(PU2.toString())), j, 4);
                Object PU4 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU4.toString())), j, 5);
                Object PU6 = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(PU6.toString())), j, 6);

            }
            table.setModel(df);
            return data;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;

    }

    public void afficherDetailFacturePassager(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * from ligne_facture_passager lf where lf.id_facture_passager='" + Num_Devis + "' order by cast(rank as unsigned)";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 8);

                Object PU2 = df.getValueAt(j, 9);
                df.setValueAt(formatDouble(Double.parseDouble(PU2.toString())), j, 9);
            }
            table.setModel(df);

        } catch (Exception e) {
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

    public void afficherDetailFactureAchat(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT lf.num_facture_achat, b.`Num_bl_achat`, DATE_FORMAT(b.`date_bl_achat`,'%d/%m/%Y'), b.`remise`, b.`Total_TTC` from ligne_facture_achat lf,bl_achat b where lf.num_bl_achat=b.num_bl_achat and lf.num_facture_achat='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 3);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 3);

                Object PU = df.getValueAt(j, 4);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 4);

            }
            table.setModel(df);
        } catch (Exception e) {
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
    static Vector<Vector<Object>> data;

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column).toString());
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

    public static DefaultTableModel buildTableModelDetailRELIQUAT(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // names of columns
        Vector<String> columnNames = new Vector<String>();

        columnNames.add("id");
        columnNames.add("Num Reliquat");
        columnNames.add("Référence");
        columnNames.add("Designation");
        columnNames.add("Quantité");
        columnNames.add("Prix Unit HT");
        columnNames.add("Remise %");
        columnNames.add("TVA %");
        columnNames.add("Totale HT");
        columnNames.add("Totale TTC");

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

    public static DefaultTableModel buildTableModelColumnName(ResultSet rs, Vector<String> columnsNames)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // names of columns
        /*   Vector<String> columnNames = new Vector<String>();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }*/
        // data of the table
        data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnsNames);

    }

    public String maxNumBL(String num) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT * FROM `bl` where num_bl like '%" + num + "%' order by id desc LIMIT 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                s = rs.getString("num_bl");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String maxNumBLDepot() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT * FROM `bl_depot` order by id desc LIMIT 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                s = rs.getString("num_bl_depot");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

}
