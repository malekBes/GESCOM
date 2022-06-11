/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facture;

import BL.BL;
import BL.BLDao;
import BL.LigneBL;
import Client.ClientDao;
import static Client.ClientDao.customBuildTableModel;
import Config.Commen_Proc;
import Conn.DataBase_connect;
import Devis.DevisDao;
import Devis.ligneDevis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
public class FactureDAO {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String year;

    public FactureDAO() {
    }
    static Vector<Vector<Object>> data;

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

    public HashMap listFacture(JComboBox comboBox) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        comboBox.removeAllItems();
        comboBox.addItem("-");
        try {
            String sql = "select num_facture from facture where statut = 0 order by date_facture";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                map.put(rs.getString("num_facture"), rs.getString("num_facture"));

                comboBox.addItem(rs.getString("num_facture"));
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

    public Vector<Vector<Object>> afficherListFactureAvoir(String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select  Num_facture from facture where id_client=" + id_client + " and statut = 1 order by id desc";
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

    public Vector<Vector<Object>> afficherListFactureAvoirFournisseur(String id_four) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "";
            if (id_four.isEmpty()) {
                sql = "select  Num_facture_achat,`date_facture_achat`, `TTC` from facture_achat where statut = 1  order by id desc";
            } else {
                sql = "select  Num_facture_achat,`date_facture_achat`, `TTC` from facture_achat where id_fournisseur=" + id_four + " and statut = 1  order by id desc";
            }
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

    public Vector<Vector<Object>> afficherListFactureAvoirPassager(String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select  Num_facture, passager, code_tva_passager, adresse from facture where type_facture = 'Passager' and statut =1 order by id desc"; //id_client=" + id_client + " and
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

    public Vector<Vector<Object>> afficherListFacture(String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT `id`, `num_facture`, `TTC` FROM `facture` where id_client = '" + id_client + "' and type_facture='Client' and `reglement` in ('Non','Semi') and statut = 1 order by id desc";
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
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public Vector<Vector<Object>> afficherListAvoir(String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT `id`, `Num_avoir`, `Total_TTC` FROM `avoir` where id_client = '" + id_client + "' and Type_avoir in ('Avoir','Non_Ident') and reglement in ('Semi','Non') and statut = 1  order by id desc";
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
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public Vector<Vector<Object>> afficherListAvoirPassager(String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT `id`, `Num_avoir`, `Total_TTC` FROM `avoir` where passager like '%" + id_client + "%' and Type_avoir ='Passager' and reglement in ('Semi','Non')  order by id desc";
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
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public Vector<Vector<Object>> afficherListAvoirFournisseur(String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT `id`, `Num_avoir_achat`, `Total_TTC` FROM `avoir_achat` where id_fournisseur = '" + id_client + "' and Type_avoir ='Avoir' and reglement in ('Semi','Non')  order by id desc";
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
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public Vector<Vector<Object>> afficherListFacturePassager(String nomclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "";
            if (nomclient.isEmpty()) {
                sql = "SELECT passager, `num_facture`, `TTC` FROM `facture` where  type_facture = 'Passager' and `reglement` in ('Non','Semi')  and statut= 1 order by id desc";

            } else {
                sql = "SELECT passager, `num_facture`, `TTC` FROM `facture` where  type_facture = 'Passager' and `reglement` in ('Non','Semi') and passager like '%" + nomclient + "%' and statut= 1 order by id desc";
            }
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
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public Vector<Vector<Object>> afficherListFactureForunisseur(String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT `id`, `num_facture_achat`, `TTC` FROM `facture_achat` where id_fournisseur = '" + id_client + "' and type_facture_achat is NULL and `reglement` in ('Non','Semi') and statut = 1 order by id desc";
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
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public String getDateFacture(String id_facture) {
        DataBase_connect obj = new DataBase_connect();
        Connection conn = obj.Open();
        String date = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT  `date_facture` FROM `facture` where num_facture = '" + id_facture + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                date = rs.getString("date_facture");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return date;
    }

    public String getDateAvoir(String id_facture) {
        DataBase_connect obj = new DataBase_connect();
        Connection conn = obj.Open();
        String date = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT  `date_avoir` FROM `avoir` where num_avoir = '" + id_facture + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                date = rs.getString("date_avoir");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return date;
    }

    public String getDateAvoirFournisseur(String id_facture) {
        DataBase_connect obj = new DataBase_connect();
        Connection conn = obj.Open();
        String date = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT  `date_avoir_achat` FROM `avoir_achat` where num_avoir_achat = '" + id_facture + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                date = rs.getString("date_avoir_achat");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return date;
    }

    public String getDateFactureFournisseur(String id_facture) {
        DataBase_connect obj = new DataBase_connect();
        Connection conn = obj.Open();
        String date = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT  `date_facture_achat` FROM `facture_achat` where num_facture_achat = '" + id_facture + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                date = rs.getString("date_facture_achat");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return date;
    }

    public Vector<Vector<Object>> afficherBL(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;

            sql = "SELECT c.nom as `Client`,DATE_FORMAT(d.`date_bl`,'%d/%m/%Y'),d.`Num_bl` ,d.total_ht , d.remise, d.montant_tva, d.`Total_TTC` FROM `bl` d left join client c on d.id_client=c.numero_client where d.invoiced = 0 and statut = 1 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClause = "";
            if (!(FromDate.isEmpty() & ToDate.isEmpty())) {
                DateClause = " and d.date_bl BETWEEN '" + FromDate + "' AND '" + ToDate + "'";
                sql = sql + DateClause;
            }
            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.total_TTC > " + FromMontant;
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";

            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC < " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }
            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_bl like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom like '%" + NomClient + "%'";
                sql = sql + ClientClause;

            }
            sql = sql + " group by d.`Num_bl` ORDER BY `d`.`id` DESC ";
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModel(rs);
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

    public Vector<Vector<Object>> afficherBLFournisseur(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;

            sql = "SELECT c.nom as `Fournisseur`,DATE_FORMAT(d.`date_bl_achat`,'%d/%m/%Y'),d.`Num_bl_achat` ,d.total_ht , d.remise, d.montant_tva, d.`Total_TTC` FROM `bl_achat` d , fournisseur c where d.id_fournisseur=c.id and d.invoiced = 0 and d.statut = 1 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClause = "";
            if (!(FromDate.isEmpty() & ToDate.isEmpty())) {
                DateClause = " and d.date_bl BETWEEN '" + FromDate + "' AND '" + ToDate + "'";
                sql = sql + DateClause;
            }
            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.total_TTC > " + FromMontant;
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";

            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC < " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }
            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_bl like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(NomClient.equals("-"))) {
                ClientClause = " and c.nom like '%" + NomClient + "%'";
                sql = sql + ClientClause;

            }
            sql = sql + " ORDER BY `d`.`id` DESC";
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModel(rs);
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

    public String maxNumFacture(String num) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT * FROM `facture` where num_facture  like '%" + num + "%' order by id desc LIMIT 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                s = rs.getString("num_facture");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public boolean ajouterFacture(Facture d) {
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
            sql = "INSERT INTO `facture` ( `num_facture`, `id_client`, `date_facture`, `adresse`,"
                    + " `info_facture`, `timbre`, `HT`, `remise`, `TVA`, `TTC`,type_facture,year) "
                    + "VALUES ( '" + d.getNum_facture() + "', " + d.getId_client() + ", '" + d.getDate_facture() + "'"
                    + ", '" + d.getAdresse() + "', '" + d.getInfo_facture() + "', " + d.isTimbre() + ", " + d.getHT() + ","
                    + " " + d.getRemise() + ", " + d.getTVA() + ", " + d.getTTC() + ",'Client','" + year + "')";
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
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    boolean modifierFacture(Facture d) {
        PreparedStatement pst;
        year = Commen_Proc.YearVal;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();
            Facture c = d;
            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
            sql = "UPDATE facture SET "
                    + "date_facture='" + c.getDate_facture() + "',"
                    + "HT=" + c.getHT() + ","
                    + "TTC=" + c.getTTC() + ","
                    + "timbre=" + c.isTimbre() + ","
                    + "remise=" + c.getRemise() + ","
                    + "TVA=" + c.getTVA() + ","
                    + "statut=1"
                    + " WHERE Num_facture = '" + c.getNum_facture() + "'";

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

    public boolean ajouterFactureFournisseur(Facture d) {
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
            sql = "INSERT INTO `facture_achat` ( `num_facture_achat`, `id_fournisseur`, `date_facture_achat`, `adresse`,"
                    + " `info_facture_achat`, `timbre`, `HT`, `remise`, `TVA`, `TTC`,year) "
                    + "VALUES ( '" + d.getNum_facture() + "', " + d.getId_fournisseur() + ", '" + d.getDate_facture() + "'"
                    + ", '" + d.getAdresse() + "', '" + d.getInfo_facture() + "', " + d.isTimbre() + ", " + d.getHT() + ","
                    + " " + d.getRemise() + ", " + d.getTVA() + ", " + d.getTTC() + ",'" + year + "')";
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
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public boolean ajouterFacturePassager(Devis.Devis d) {
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
            sql = "INSERT INTO `facture` ( `num_facture`, `id_client`, `date_facture`, `adresse`,"
                    + " `info_facture`, `timbre`, `HT`, `remise`, `TVA`, `TTC`,type_facture, passager, code_tva_passager ,year) "
                    + "VALUES ( '" + d.getNum_Devis() + "', " + d.getId_client() + ", '" + d.getDate_devis() + "'"
                    + ", '" + d.getAdresse() + "', '" + d.getInfos_Devis() + "', " + d.getTimbre() + ", " + d.getMontant_TVA() + ","
                    + " " + d.getRemise() + ", " + d.getMontant_TVA() + ", " + d.getTotale_TTC() + ",'Passager',"
                    + "'" + d.getPassager() + "','" + d.getCode_TVA() + "','" + year + "')";
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
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    boolean modifierFacturePassager(Devis.Devis d) {
        PreparedStatement pst;
        year = Commen_Proc.YearVal;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();
            Devis.Devis c = d;
            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
            sql = "UPDATE facture SET "
                    + "date_facture='" + c.getDate_devis() + "',"
                    + "passager='" + c.getPassager() + "',"
                    + "adresse='" + c.getAdresse() + "',"
                    + "remise=" + c.getRemise() + ","
                    + "HT=" + c.getTotale_HT() + ","
                    + "TTC=" + c.getTotale_TTC() + ","
                    + "tva=" + c.getMontant_TVA() + ","
                    + "timbre=" + c.getTimbre() + ","
                    + "Code_TVA_passager='" + c.getCode_TVA() + "',"
                    + "Info_facture='" + c.getInfos_Devis() + "',"
                    /*   + "Afficher_total=" + c.getAfficher_total() + ","
                    + "facture_proformat=" + c.getFacture_proformat() + ","
                    + "Afficher_validiter=" + c.getAfficher_validiter() + ","
                    + "Afficher_prix=" + c.getAfficher_prix() + ","
                    + "Edit_ref=" + c.getEdit_ref() + ","*/
                    + "year='" + year + "'"
                    + " WHERE Num_facture = '" + c.getNum_Devis() + "'";

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

    public void ajouterLigneFacturePassager(ArrayList<ligneDevis> lstd) {
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
            for (ligneDevis d : lstd) {

                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_facture_passager(id_facture_passager, ref_article, designation_article, qte, prix_u, remise, "
                        + "tva, total_HT, total_TTC,rank) "
                        + "VALUES ('" + d.getId_Devis() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void modifierLigneFacturePassager(ArrayList<ligneDevis> lstd) {
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

            for (ligneDevis d : lstd) {
                ligneDevis c = d;
                String currentTime = sdf.format(dt);

                sql = "UPDATE ligne_facture_passager SET "
                        + "id_facture_passager='-1'"
                        + " WHERE id_facture_passager = '" + c.getId_Devis() + "'";

//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }
            sqldel = "delete from ligne_facture_passager where id_facture_passager ='-1'";
            pst = (PreparedStatement) conn.prepareStatement(sqldel);
            pst.execute();
            int i = 0;
            for (ligneDevis d : lstd) {
                i++;
                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_facture_passager(id_facture_passager, ref_article, designation_article, qte, prix_u, remise, "
                        + "tva, total_HT, total_TTC,rank) "
                        + "VALUES ('" + d.getId_Devis() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void ajouterLigneFacture(ArrayList<ligne_facture> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            int i = 1;
            for (ligne_facture lf : lstd) {

                sql = "INSERT INTO `ligne_facture` (`num_facture`, `num_bl`,rank) "
                        + "VALUES ( '" + lf.getNum_facture() + "', '" + lf.getNum_bl() + "','" + i + "')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
                i++;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    void modifierLigneFacture(ArrayList<ligne_facture> lstd) {
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

            for (ligne_facture d : lstd) {
                ligne_facture c = d;
                String currentTime = sdf.format(dt);

                sql = "UPDATE ligne_facture SET "
                        + "num_bl='-1'"
                        + " WHERE num_bl = '" + c.getNum_bl() + "'";

//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }
            /*sqldel = "delete from ligne_facture where num_bl ='-1'";
            pst = (PreparedStatement) conn.prepareStatement(sqldel);
            pst.execute();*/
            int i = 1;
            for (ligne_facture d : lstd) {

                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_facture(num_facture,num_bl,rank) "
                        + "VALUES ('" + d.getNum_facture() + "','" + d.getNum_bl() + "','" + i + "')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
                i++;
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

    public void ajouterLigneFactureFournisseur(ArrayList<ligne_facture> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            int i = 1;
            for (ligne_facture lf : lstd) {

                sql = "INSERT INTO `ligne_facture_achat` (`num_facture_achat`, `num_bl_achat`,rank) "
                        + "VALUES ( '" + lf.getNum_facture() + "', '" + lf.getNum_bl() + "','" + i + "')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
                i++;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void setBLInvoiced(ArrayList<ligne_facture> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {

            for (ligne_facture lf : lstd) {

                sql = "update bl set invoiced = 1 where num_bl = '" + lf.getNum_bl() + "';";
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
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void setBLFournisseurInvoiced(ArrayList<ligne_facture> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {

            for (ligne_facture lf : lstd) {

                sql = "update bl_achat set invoiced = 1 where num_bl_achat = '" + lf.getNum_bl() + "';";
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
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void setFactureReglement(String id_facture, String etatReglement) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {

            sql = "update facture set reglement = '" + etatReglement + "' where num_facture = '" + id_facture + "';";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void setFactureDecharge(String id_facture, String etatDecharge) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {

            sql = "update facture set decharge = '" + etatDecharge + "' where num_facture = '" + id_facture + "';";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void setAvoirReglement(String id_facture, String etatReglement) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {

            sql = "update avoir set reglement = '" + etatReglement + "' where num_avoir = '" + id_facture + "';";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void setAvoirReglementFournisseur(String id_facture, String etatReglement) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {

            sql = "update avoir_achat set reglement = '" + etatReglement + "' where num_avoir_achat = '" + id_facture + "';";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void setFactureReglementFournisseur(String id_facture, String etatReglement) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {

            sql = "update facture_achat set reglement = '" + etatReglement + "' where num_facture_achat = '" + id_facture + "';";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

}
