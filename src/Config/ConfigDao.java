/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import Client.ClientDao;
import Commercial.FormCommercial;
import static Config.Bank.BankDao.buildTableModel;
import Conn.DataBase_connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
public class ConfigDao {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public HashMap listType_Client(JComboBox comboBox) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        comboBox.removeAllItems();
        comboBox.addItem("-");
        try {
            String sql = "select type,id from Type_Client ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                if (!rs.getString("type").isEmpty()) {
                    comboBox.addItem(rs.getString("type"));
                }

                //comboBox.addItem(comm);
            }

            for (String s : map.keySet()) {
                comboBox.addItem(s);
            }
            return map;
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
        return map;
    }

    public void getParametersTvaTimbreYear() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();

        try {
            String sql = "select * from variable_table";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                if (rs.getString("cle").equals("tva")) {
                    Commen_Proc.TVAVal = rs.getString("valeur");
                }
                if (rs.getString("cle").equals("timbre")) {
                    Commen_Proc.TimbreVal = rs.getString("valeur");
                }
                if (rs.getString("cle").equals("year")) {

                    String year = rs.getString("valeur").length() > 2 ? rs.getString("valeur").substring(rs.getString("valeur").length() - 2) : rs.getString("valeur");
                    Commen_Proc.YearVal = year;
                }
                
                if (rs.getString("cle").equals("PathPDF")) {
                    Commen_Proc.PathPDF = rs.getString("valeur");
                }
                if (rs.getString("cle").equals("PathExcel")) {
                    Commen_Proc.PathExcel = rs.getString("valeur");
                }
                if (rs.getString("cle").equals("PathImg")) {
                    Commen_Proc.PathImg = rs.getString("valeur");
                }

            }

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

    }

    public String afficherParams() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String values = "";
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  * FROM `variable_table` ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                values += rs.getString("cle") + ":" + rs.getString("valeur") + ";";
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ConfigDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return values;
    }

    public HashMap listZone_geo(JComboBox comboBox) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        comboBox.removeAllItems();
        comboBox.addItem("-");
        try {
            String sql = "select zone from zone_geo";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                if (!rs.getString("zone").isEmpty()) {
                    comboBox.addItem(rs.getString("zone"));
                }

                //comboBox.addItem(comm);
            }

            for (String s : map.keySet()) {
                comboBox.addItem(s);
            }
            return map;
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
        return map;
    }

    public HashMap listetat_payement(JComboBox comboBox) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        comboBox.removeAllItems();
        comboBox.addItem("-");
        try {
            String sql = "select etat from etat_payement";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                if (!rs.getString("etat").isEmpty()) {
                    comboBox.addItem(rs.getString("etat"));
                }

                //comboBox.addItem(comm);
            }

            for (String s : map.keySet()) {
                comboBox.addItem(s);
            }
            return map;
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
        return map;
    }

    public void update() {

    }

    public String login(String username, String Password) {
        String v = "";
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        if(conn==null){
            return "error_cnx";
        }
        try {

            String ss = "select count(*),id_role from users where username='" + username + "' and password='" + Password + "'";
            pst = conn.prepareStatement(ss);
            rs = pst.executeQuery();
            while (rs.next()) {
                v = rs.getString("count(*)");
                Commen_Proc.id_role = rs.getString("id_role");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return v;
    }

    public Boolean DateCheckOnUpdate(String table_name, String Date_column, String where_num_column, String val_num, String DateToChek) {
        String v = "";
        String num_last_devis = "";
        String num_first_devis = "";
        PreparedStatement pst;
        Boolean b = false;
        Boolean b1 = false;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String s2 = "select " + where_num_column + " from " + table_name + " where " + where_num_column + " > '" + val_num + "' and statut = 1 order by id asc LIMIT 1";
            pst = conn.prepareStatement(s2);
            rs = pst.executeQuery();
            String Date_first = "";

            while (rs.next()) {
                num_first_devis = rs.getString(where_num_column);
            }
            if (!num_first_devis.isEmpty()) {

                String ss = "select " + Date_column + " from " + table_name + " where " + where_num_column + " = '" + num_first_devis + "' ";
                pst = conn.prepareStatement(ss);
                rs = pst.executeQuery();

                while (rs.next()) {
                    Date_first = rs.getString(Date_column);
                }
                /*  DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date new_date = format.parse(DateToChek);
                String old_dt = Date_first;
                Date date_old = new SimpleDateFormat("yyyy-MM-dd").parse(old_dt);
                //new_date is after date_old
                if ((new_date.compareTo(date_old) < 0) || (new_date.compareTo(date_old) == 0)) {
                    b1 = true;
                }*/
            }

            String s = "select " + where_num_column + " from " + table_name + " where " + where_num_column + " < '" + val_num + "' and statut = 1 order by id desc LIMIT 1";
            pst = conn.prepareStatement(s);
            rs = pst.executeQuery();
            String Date_last = "";

            while (rs.next()) {
                num_last_devis = rs.getString(where_num_column);
            }
            if (!num_last_devis.isEmpty()) {

                String ss = "select " + Date_column + " from " + table_name + " where " + where_num_column + " = '" + num_last_devis + "' ";
                pst = conn.prepareStatement(ss);
                rs = pst.executeQuery();

                while (rs.next()) {
                    Date_last = rs.getString(Date_column);
                }
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date new_date = format.parse(DateToChek);
                String old_dt = Date_last;
                Date date_old = new SimpleDateFormat("yyyy-MM-dd").parse(old_dt);
                //new_date is after date_old
                if (Date_first.isEmpty()) {
                    if ((new_date.compareTo(date_old) > 0) || (new_date.compareTo(date_old) == 0)) {
                        b = true;
                    }
                } else {
                    Date date_first = new SimpleDateFormat("yyyy-MM-dd").parse(Date_first);
                    if (((new_date.compareTo(date_old) > 0) && (new_date.compareTo(date_first) < 0)) || (new_date.compareTo(date_old) == 0)) {
                        b = true;
                    }
                }
            }

            if (num_first_devis.isEmpty() && num_last_devis.isEmpty()) {
                b = true;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return true;//b;//DateCheckOnUpdateTest(table_name, Date_column, where_num_column, val_num, DateToChek, "Yes");
    }

    public Boolean DateCheckOnUpdateTest(String table_name, String Date_column, String where_num_column, String val_num, String DateToChek, String withStatut) {
        String v = "";
        String num_last_devis = "";
        String num_first_devis = "";
        PreparedStatement pst;
        Boolean b = false;
        Boolean b1 = false;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date_to_chek = format.parse(DateToChek);
            String Date_Before = "";
            Date_Before = getDateBefore(table_name, Date_column, where_num_column, val_num, DateToChek, withStatut);

            Date date_Before = null;
            if (Date_Before != null && !Date_Before.isEmpty()) {
                date_Before = new SimpleDateFormat("yyyy-MM-dd").parse(Date_Before);
            }

            String Date_After = "";
            Date_After = getDateAfter(table_name, Date_column, where_num_column, val_num, DateToChek, "Yes");

            Date date_After = null;
            if (Date_After != null && !Date_After.isEmpty()) {
                date_After = new SimpleDateFormat("yyyy-MM-dd").parse(Date_After);
            }

            if (Date_After == null && Date_Before == null) {
                return true;
            }
            String s = "";
            if (Date_Before.isEmpty() && !Date_After.isEmpty()) {
                //  date_Before = date_to_chek;
                //  b = date_After.compareTo(date_to_chek) <= 0;
                s = "SELECT ('" + DateToChek + "' >= '" + Date_After + "' ) as diff";

                // b = date_Before.compareTo(date_to_chek) * date_to_chek.compareTo(date_After) >= 0;
            } else if (Date_After.isEmpty() && !Date_Before.isEmpty()) {
                date_After = date_to_chek;
                // b = date_Before.compareTo(date_to_chek) >= 0;

                s = "SELECT ('" + DateToChek + "' <= '" + Date_Before + "' ) as diff";

// b = date_Before.compareTo(date_to_chek) * date_to_chek.compareTo(date_After) >= 0;
            } else {
                s = "SELECT ('" + DateToChek + "' BETWEEN '" + Date_After + "' AND '" + Date_Before + "') as diff";

            }

            pst = conn.prepareStatement(s);
            rs = pst.executeQuery();

            while (rs.next()) {
                b = rs.getString("diff").equals("1") ? true : false;
            }

            /*
            if (date1.compareTo(date2) > 0) {
            System.out.println("Date1 is after Date2");
        } else if (date1.compareTo(date2) < 0) {
            System.out.println("Date1 is before Date2");
        } else if (date1.compareTo(date2) == 0) {
            System.out.println("Date1 is equal to Date2");
        } else {
            System.out.println("How to get here?");
        }
             */
 /*  if (date_to_chek.compareTo(date_Before) < 0 || date_to_chek.compareTo(date_Before) == 0) {
                b = true;
            }*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return b;
    }

    public String getDateBefore(String table_name, String Date_column, String where_num_column, String val_num, String DateToChek, String withStatut) {
        String v = "";
        String num_first_devis = "";
        PreparedStatement pst;
        Boolean b = false;
        Boolean b1 = false;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String Date_first = "";
        try {
            String s2;
            if (withStatut.equals("Yes")) {
                s2 = "select " + where_num_column + " from " + table_name + " where " + where_num_column + " > '" + val_num + "' and statut = 1 order by id asc LIMIT 1";
            } else {
                s2 = "select " + where_num_column + " from " + table_name + " where " + where_num_column + " > '" + val_num + "'  order by id asc LIMIT 1";
            }
            pst = conn.prepareStatement(s2);
            rs = pst.executeQuery();

            while (rs.next()) {
                num_first_devis = rs.getString(where_num_column);
            }
            if (!num_first_devis.isEmpty()) {

                String ss = "select " + Date_column + " from " + table_name + " where " + where_num_column + " = '" + num_first_devis + "' ";
                pst = conn.prepareStatement(ss);
                rs = pst.executeQuery();

                while (rs.next()) {
                    Date_first = rs.getString(Date_column);
                }
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date new_date = format.parse(DateToChek);
                String old_dt = Date_first;
                Date date_old = new SimpleDateFormat("yyyy-MM-dd").parse(old_dt);
                //new_date is after date_old
                /*   if ((new_date.compareTo(date_old) < 0) || (new_date.compareTo(date_old) == 0)) {
                    b1 = true;
                }*/
            }
            return Date_first;
        } catch (Exception e) {
        }
        return Date_first;
    }

    public String getDateAfter(String table_name, String Date_column, String where_num_column, String val_num, String DateToChek, String withStatut) {
        String v = "";
        String num_last_devis = "";
        PreparedStatement pst;
        Boolean b = false;
        Boolean b1 = false;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String Date_last = "";
        try {
            String s;
            if (withStatut.equals("Yes")) {
                s = "select " + where_num_column + " from " + table_name + " where " + where_num_column + " < '" + val_num + "' and statut = 1 order by id desc LIMIT 1";
            } else {
                s = "select " + where_num_column + " from " + table_name + " where " + where_num_column + " < '" + val_num + "' order by id desc LIMIT 1";
            }
            pst = conn.prepareStatement(s);
            rs = pst.executeQuery();

            while (rs.next()) {
                num_last_devis = rs.getString(where_num_column);
            }
            Date new_date = null;
            Date date_old = null;
            if (!num_last_devis.isEmpty()) {

                String ss = "select " + Date_column + " from " + table_name + " where " + where_num_column + " = '" + num_last_devis + "' ";
                pst = conn.prepareStatement(ss);
                rs = pst.executeQuery();

                while (rs.next()) {
                    Date_last = rs.getString(Date_column);
                }
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                new_date = format.parse(DateToChek);
                String old_dt = Date_last;
                date_old = new SimpleDateFormat("yyyy-MM-dd").parse(old_dt);
                //new_date is after date_old

            }
            return Date_last;
        } catch (Exception e) {
        }
        return Date_last;
    }

    public Boolean DateCheckOnUpdateNoStatut(String table_name, String Date_column, String where_num_column, String val_num, String DateToChek) {
        String v = "";
        String num_last_devis = "";

        PreparedStatement pst;
        Boolean b = false;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String s = "select " + where_num_column + " from " + table_name + " where " + where_num_column + " < '" + val_num + "'  order by id desc LIMIT 1";
            pst = conn.prepareStatement(s);
            rs = pst.executeQuery();

            while (rs.next()) {
                num_last_devis = rs.getString(where_num_column);
            }

            if (!num_last_devis.isEmpty()) {

                String ss = "select " + Date_column + " from " + table_name + " where " + where_num_column + " = '" + num_last_devis + "' ";
                pst = conn.prepareStatement(ss);
                rs = pst.executeQuery();

                while (rs.next()) {
                    v = rs.getString(Date_column);
                }
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date new_date = format.parse(DateToChek);
                String old_dt = v;
                Date date_old = new SimpleDateFormat("yyyy-MM-dd").parse(old_dt);
                //new_date is after date_old
                if ((new_date.compareTo(date_old) > 0) || (new_date.compareTo(date_old) == 0)) {
                    b = true;
                }
            }
            if (num_last_devis.isEmpty()) {
                b = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return true;//b;//DateCheckOnUpdateTest(table_name, Date_column, where_num_column, val_num, DateToChek, "No");
    }

}
