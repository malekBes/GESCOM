/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commercial;

import Article.ArticleDao;
import Client.ClientDao;
import static Client.ClientDao.customBuildTableModel;
import Conn.DataBase_connect;
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
public class CommercialDao {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void ajouterCommercial(Commercial c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into commercial(nom,adresse) "
                    + "values('" + c.getNom() + "','" + c.getAdresse() + "')";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Saved");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void ajouterTypeCommercial(Commercial c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into type_commercial(nom,valeur) "
                    + "values('" + c.getNom() + "','" + c.getAdresse() + "')";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Saved");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void ajouterTypeArticle(Commercial c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into type_article(type,valeur) "
                    + "values('" + c.getNom() + "','" + c.getAdresse() + "')";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Saved");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
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

    public void afficherCommercial(JTable table) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  nom , adresse FROM `commercial`";

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
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherTypeCommercial(JTable table) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  id,nom , valeur FROM `type_commercial`";

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
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherTypeArticle(JTable table) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  id,type FROM `type_article`";

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
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Vector<Vector<Object>> afficherListeCommercial() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select id,nom,adresse from commercial";
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

    public HashMap listCommercial(JComboBox comboBox) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        comboBox.removeAllItems();
        comboBox.addItem("-");
        try {
            String sql = "select id,nom,prenom from commercial";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                map.put(rs.getString("nom"), rs.getString("id"));

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

    public HashMap listTypeCommercial(JComboBox comboBox) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        comboBox.removeAllItems();
        comboBox.addItem("-");
        try {
            String sql = "select id,nom,valeur from type_commercial";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                map.put(rs.getString("nom"), rs.getString("id"));

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

    public void supprimerCommercial(String value) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "delete from commercial where nom ='" + value + "'";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprimé");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void updateTypeCommercial(Commercial c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "update type_commercial set nom ='" + c.getNom() + "', valeur ='" + c.getAdresse() + "' where id =" + c.getPrenom() + "";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Modifié");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void modifierTypeArticle(String id, String Type, String value) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "update type_article set type ='" + Type + "', valeur ='" + value + "' where id =" + id + "";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Modifié");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

}
