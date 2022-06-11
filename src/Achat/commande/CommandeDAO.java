/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Achat.commande;

import Achat.pre_commande.lignePre_commande;
import Achat.pre_commande.pre_commandeDao;
import BL.BLDao;
import static BL.BLDao.buildTableModel;
import Client.ClientDao;
import static Client.ClientDao.customBuildTableModel;
import Config.Commen_Proc;
import Conn.DataBase_connect;
import Facture.FactureDAO;
import java.awt.List;
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
public class CommandeDAO {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String year;

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

                //  String sql = "SELECT max(num_bon_commande) FROM pre_command_achat ";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();

                while (rs.next()) {
                    pre_cmd = rs.getString("max(num_bon_commande)");
                }
            } catch (Exception e) {
            }
            try {
                String sql = "SELECT if(max(num_commande) is null,'0',max(num_commande)) as 'max(num_commande)' FROM commande_achat ";

                //  String sql = "SELECT max(num_commande) FROM commande_achat ";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();

                while (rs.next()) {
                    cmd = rs.getString("max(num_commande)");
                }
            } catch (Exception e) {
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

    public static DefaultTableModel buildTableModelCustomExport(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        int columnCount = metaData.getColumnCount();

        Vector<String> columnNames_Devis_Table = new Vector<String>();
        columnNames_Devis_Table.add("Num");
        columnNames_Devis_Table.add("Référence");
        columnNames_Devis_Table.add("Designation");
        columnNames_Devis_Table.add("Prix Unit HT");
        columnNames_Devis_Table.add("Quantité");
        columnNames_Devis_Table.add("Totale HT");
        columnNames_Devis_Table.add("Remise %");
        columnNames_Devis_Table.add("TVA %");
        columnNames_Devis_Table.add("Totale TTC");

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames_Devis_Table);

    }

    public void afficherPreCommandeRecherche(JTable table, String FromDate, String ToDate, String NumDevis, String NomClient, String id_four, String Filtre) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT f.`Nom`,d.`num_bon_commande`,DATE_FORMAT(d.`date_pre_commande`,'%d/%m/%Y') , c.nom as `Client`, id_commande FROM `pre_command_achat` d left join client c on d.id_client=c.numero_client  left join fournisseur f on d.id_fournisseur=f.id  where d.statut = 1 and d.id_client <>0 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_pre_commande >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_pre_commande <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.num_bon_commande  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }

            String FiltreClause = "";
            if (Filtre.equals("En Cours")) {
                FiltreClause = "and id_commande is NULL";
                sql += FiltreClause;
            } else if (Filtre.equals("Commande")) {
                FiltreClause = "and id_commande is NOT NULL";
                sql += FiltreClause;
            }

            String fourClause = "";
            if (!(id_four.isEmpty())) {
                fourClause = " and d.id_fournisseur  = " + id_four + "";
                sql = sql + fourClause;

            }

            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom  like '%" + NomClient + "%'";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY d.`id` DESC";

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

    public void afficherCommandeRecherche(JTable table, String FromDate, String ToDate, String NumDevis, String NomClient, String id_four, String Filtre) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT f.`Nom`,d.`num_commande`,DATE_FORMAT(d.`date_commande`,'%d/%m/%Y') FROM `commande_achat` d , fournisseur f where d.id_fournisseur=f.id and d.statut = 1 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_commande >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_commande <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.num_commande  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }

            String fourClause = "";
            if (!(id_four.isEmpty())) {
                fourClause = " and d.id_fournisseur  = " + id_four + "";
                sql = sql + fourClause;

            }

            sql += " ORDER BY d.`date_commande` DESC";

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

    public void afficherBLAchat(JTable table, String FromDate, String ToDate, String NumDevis, String NomClient, String id_four, String Filtre) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT f.`Nom`,d.`num_bl_achat`,DATE_FORMAT(d.`date_bl_achat`,'%d/%m/%Y'),Total_TTC FROM `bl_achat` d , fournisseur f where d.id_fournisseur=f.id and d.statut =1 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_bl_achat >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_bl_achat <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.num_bl_achat  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }

            String fourClause = "";
            if (!(id_four.isEmpty())) {
                fourClause = " and d.id_fournisseur  = " + id_four + "";
                sql = sql + fourClause;

            }

            sql += " ORDER BY d.`id` DESC";

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

    public Vector<Vector<Object>> afficherListPreCommande(String num_commande) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT lpc.ref_article, lpc.designation_article, lpc.qte FROM `ligne_pre_commande` lpc WHERE lpc.id_pre_commande = '" + num_commande + "'";
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
                Logger.getLogger(CommandeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public void afficherDetailPre_Commande(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT id_pre_commande,ref_article,designation_article,qte from ligne_pre_commande where id_pre_commande='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            /*for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 6);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 9);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 9);
            }
            table.setModel(df);*/
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

    public void afficherDetailPre_CommandeForExcel(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * FROM sodis_prod.ligne_pre_commande where id_pre_commande in(SELECT num_bon_commande FROM sodis_prod.pre_command_achat where id_commande in (" + Num_Devis + "))";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            /*for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 6);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 9);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 9);
            }
            table.setModel(df);*/
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

    public void afficherDetailCommande(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT f.Nom,pc.`num_bon_commande`, c.nom,DATE_FORMAT( pc.`date_pre_commande`,'%d/%m/%Y') FROM `pre_command_achat` pc left join fournisseur f on pc.`id_fournisseur` = f.id left join client c on pc.`id_client`=c.numero_Client WHERE  pc.id_commande='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            /*for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 6);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 9);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 9);
            }
            table.setModel(df);*/
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

    public void afficherDetailBLAchat(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * from ligne_bl_achat where id_bl_achat='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            /*for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 6);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 9);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 9);
            }
            table.setModel(df);*/
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

    public ArrayList<lignePre_commande> afficherDetailBLAchatModif(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        ArrayList<lignePre_commande> lst = new ArrayList();
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT * from ligne_bl_achat where id_bl_achat='" + Num_Devis + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                lignePre_commande lpc = new lignePre_commande();
                lpc.setRef_article(rs.getString("ref_article"));
                lpc.setDesign(rs.getString("designation_article"));
                lpc.setPrix_u(Double.valueOf(rs.getString("prix_u")));
                lpc.setQte(rs.getString("qte"));
                lst.add(lpc);

            }

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            /*for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 6);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 9);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 9);
            }
            table.setModel(df);*/
            return lst;
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
        return lst;
    }

    public String formatDouble(Double d) {
        // String s = d.toString().replace(".", ";");
        DecimalFormat df = new DecimalFormat("0.000");
        df.setRoundingMode(RoundingMode.CEILING);// import java.text.DecimalFormat;
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

    boolean ajouterCommande(Commande d) {
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
            sql = "INSERT INTO commande_achat(num_commande, date_commande, id_fournisseur) "
                    + "VALUES ('" + d.getNum_Commande() + "','" + d.getDate_commande() + "'," + d.getId_fournisseur() + ")";
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
                Logger.getLogger(CommandeDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    void ajouterLigne_commande(ArrayList<ligne_commande> lstd) {
        PreparedStatement pst;
        PreparedStatement pst1;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd");
            int i = 1;
            for (ligne_commande d : lstd) {

                sql = "INSERT INTO ligne_commande_achat(num_commande, num_pre_command, date_pre_command, id_client,rank) "
                        + "VALUES ('" + d.getNum_commande() + "' ,'" + d.getId_pre_commande() + "','" + d.getDate_pre_command() + "'," + d.getId_client() + ",'" + i + "')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
                String s = "update pre_command_achat set id_commande ='" + d.getNum_commande() + "' where num_bon_commande='" + d.getId_pre_commande() + "'";
                pst1 = (PreparedStatement) conn.prepareStatement(s);
                pst1.execute();
                i++;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(CommandeDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void setPreCommande_NumCommande(ArrayList<lignePre_commande> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {

            for (lignePre_commande lf : lstd) {

                sql = "update bl set invoiced = 1 where num_bl = '" + lf.getId_pre_commande() + "';";
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

    public Vector<Vector<Object>> afficherCommande(JTable table, String id_four) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;

            sql = "SELECT pre.`num_bon_commande`, DATE_FORMAT(pre.`date_pre_commande`,'%d/%m/%Y') ,if(pre.id_client = '99999999','Passager', c.nom) as nom  FROM `pre_command_achat` pre left join  client c on pre.id_client=c.numero_Client WHERE  pre.id_commande is NULL and pre.statut = 1 and num_bon_commande <> 0 ";
            if (!id_four.isEmpty()) {
                sql += "and pre.id_fournisseur =" + id_four;
            }
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = custombuildTableModel(rs);
            table.setModel(df);
            return data;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(CommandeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    static Vector<Vector<Object>> data;

    public static DefaultTableModel custombuildTableModel(ResultSet rs)
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

    public ArrayList<lignePre_commande> afficherDetailCommandeExporter(JTable table, String Num_Commande) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        ArrayList<lignePre_commande> lst = new ArrayList();

        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT   r.ref, r.designation ,r.prix_achat, lpc.qte FROM `ligne_commande_achat` lc, ligne_pre_commande lpc, article r WHERE lc.`num_commande` = '" + Num_Commande + "' and lc.`num_pre_command`= lpc.id_pre_commande and  r.ref = lpc.ref_article";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                lignePre_commande lpc = new lignePre_commande();
                lpc.setRef_article(rs.getString("r.ref"));
                lpc.setDesign(rs.getString("r.designation"));
                lpc.setPrix_u(Double.valueOf(rs.getString("r.prix_achat")));
                lpc.setQte(rs.getString("lpc.qte"));
                lst.add(lpc);

            }

            df = buildTableModel(rs);
            //table.setModel(df);
            return lst;
            /*for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 6);

                Object v = df.getValueAt(j, 8);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 8);

                Object k = df.getValueAt(j, 9);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 9);
            }
            table.setModel(df);*/
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
        return lst;
    }

}
