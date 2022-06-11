/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Article.ArticleDao;
import static Article.ArticleDao.buildTableModel;
import Conn.DataBase_connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mlek
 */
public class ClientDao {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void afficherListeClient(JTable table) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "select numero_Client as 'ID', nom, adresse, Ville from client order by nom ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            /*String DateClauseFrom = "";
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

            }*/
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
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int setMaxId() {

        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        int num = 0;
        try {
            //max_id upadate

            String sql = "select numero_client from client where numero_client <> 99999999 ORDER by numero_Client desc LIMIT 1";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);

            if (rs.next()) {
                num = rs.getInt(1) + 1;
            }
            return num;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
        return num;

    }

    public void ajouterClient(Client c) {

        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {
            //max_id upadate

            /*   sql = "select max_id from variable_table";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            int num = 0;
            if (rs.next()) {
                num = rs.getInt(1) + 1;
                sql = "UPDATE variable_table SET max_id='" + num + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }
            num++;*/
            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);
            sql = "INSERT INTO client "
                    + "( numero_client ,nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax,"
                    + " adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, "
                    + "Fournisseur_Preced, actif,Id_Commercial, Create_Date, Last_Update_Date, CreatedBy, UpdatedBy, seuil_compte, echeance_payement) "
                    + "VALUES (" + c.getNum_Client() + ",'" + c.Nom + "', '" + c.Num_tel + "', '" + c.Adresse + "', '" + c.Ville + "', '" + c.pays + "', '" + c.code_Postale
                    + "', '" + c.zone_Geo + "', '" + c.id_Fiscale + "', '" + c.Email + "', '" + c.site + "', '" + c.fax + "', '" + c.adresse_livraison + "', '" + c.contact_Client + "', '" + c.type_Client + "', '" + c.Etat_Paiement
                    + "', '" + c.agence + "', '" + c.Compte_Bank + "', '" + c.Fournisseur_Preced + "', '" + c.actif + "','" + c.Id_Commercial + "', '" + currentTime + "', '" + currentTime + "', '" + c.CreatedBy + "', '" + c.UpdatedBy
                    + "','" + c.getSeuil_compte() + "','" + c.getEcheance_payement() + "')";

//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
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

    public void ajouter_Client_Commercial(String ID_client, String Id_Commercial, int Pourcentage) {

        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {
            //sum % check

            sql = "select sum(p.pourcentage) from client c,pourcentagebonus p, commercial comm "
                    + "where c.numero_client = p.id_client "
                    + "and p.id_commercial = comm.id  and p.id_client = " + ID_client;
            pst = (PreparedStatement) conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            int sum = 0;
            if (rs.next()) {
                sum = rs.getInt(1);
            }
            //  if (sum != 0) {

            sql = "INSERT INTO pourcentagebonus( Id_Commercial, id_Client, Pourcentage) "
                    + "VALUES (" + Id_Commercial + "," + ID_client + ",'" + Pourcentage + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Saved  ");
            //    } else {
            //      JOptionPane.showMessageDialog(null, "Vous avez dépassé 100% !");
            //}

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " " + sql);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void updateClient(Client c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);
        try {

            String sql = "update client set"
                    + " nom='" + c.getNom()
                    + "',num_Tel='" + c.getNum_tel()
                    + "',adresse='" + c.getAdresse()
                    + "',Ville='" + c.getVille()
                    + "',pays='" + c.getPays()
                    + "',code_Postale='" + c.getCode_Postale()
                    + "',zone_Geo='" + c.getZone_Geo()
                    + "',id_Fiscale='" + c.getId_Fiscale()
                    // + "',exonere_TVA='" + c.getExonere_TVA()
                    + "',Email='" + c.getEmail()
                    + "',site='" + c.getSite()
                    + "',fax='" + c.getFax()
                    + "',adresse_livraison='" + c.getAdresse_livraison()
                    + "',contact_Client='" + c.getContact_Client()
                    + "',type_Client='" + c.getType_Client()
                    + "',Etat_Paiement='" + c.getEtat_Paiement()
                    + "',agence='" + c.getAgence()
                    + "',Compte_Bank='" + c.getCompte_Bank()
                    + "',Fournisseur_Preced='" + c.getFournisseur_Preced()
                    + "',actif='" + c.getActif()
                    + "',Id_Commercial='" + c.getId_Commercial()
                    + "',seuil_compte='" + c.getSeuil_compte()
                    + "',echeance_payement='" + c.getEcheance_payement()
                    + "',Last_Update_Date='" + currentTime
                    + "' where numero_Client='" + c.getNum_Client() + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "le client " + c.getNom() + " a été mise a jour avec succses !");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void deleteClient(String ID) {
        int p = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer ?", "Supprimer", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            PreparedStatement pst;

            DataBase_connect obj = new DataBase_connect();

            Connection conn = obj.Open();
            String sql = "delete from client where numero_Client=?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, ID);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Client à été bien Supprimé ");

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
    }

    public void deleteClient_Commercial(String ID) {
        int p = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer ?", "Supprimer", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            PreparedStatement pst;

            DataBase_connect obj = new DataBase_connect();

            Connection conn = obj.Open();
            String sql = "delete from pourcentagebonus where id=?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, ID);
                pst.execute();
                JOptionPane.showMessageDialog(null, "% à été bien Supprimé ");

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
    }

    public Client getClientById(String ID) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Client c = new Client();
        try {

            String sql = "select * from client where numero_Client ='" + ID + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("numero_Client");
                c.setNum_Client(Integer.parseInt(add1));
                c.setNom(rs.getString("nom"));
                c.setNum_tel(rs.getString("num_Tel"));
                c.setAdresse(rs.getString("adresse"));
                c.setVille(rs.getString("Ville"));
                c.setPays(rs.getString("pays"));
                c.setCode_Postale(rs.getString("code_Postale"));
                c.setZone_Geo(rs.getString("zone_Geo"));
                c.setId_Fiscale(rs.getString("id_Fiscale"));
                //   c.setExonere_TVA(rs.getString("exonere_TVA"));
                c.setEmail(rs.getString("Email"));
                c.setSite(rs.getString("site"));
                c.setFax(rs.getString("fax"));

                c.setEcheance_payement(rs.getString("echeance_payement"));
                c.setSeuil_compte(rs.getString("seuil_compte"));

                c.setAdresse_livraison(rs.getString("adresse_livraison"));
                c.setType_Client(rs.getString("type_Client"));
                c.setEtat_Paiement(rs.getString("Etat_Paiement"));
                c.setAgence(rs.getString("agence"));
                c.setCompte_Bank(rs.getString("Compte_Bank"));
                c.setFournisseur_Preced(rs.getString("Fournisseur_Preced"));
                c.setContact_Client(rs.getString("contact_Client"));
                c.setActif(rs.getString("actif"));
                c.setId_Commercial(rs.getString("Id_Commercial"));
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
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return c;
    }

    public Client getClientByName(String name) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Client c = new Client();
        try {

            String sql = "select * from client where nom like '%" + name + "%'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("numero_Client");
                c.setNum_Client(Integer.parseInt(add1));
                c.setNom(rs.getString("nom"));
                c.setNum_tel(rs.getString("num_Tel"));
                c.setAdresse(rs.getString("adresse"));
                c.setVille(rs.getString("Ville"));
                c.setPays(rs.getString("pays"));
                c.setCode_Postale(rs.getString("code_Postale"));
                c.setZone_Geo(rs.getString("zone_Geo"));
                c.setId_Fiscale(rs.getString("id_Fiscale"));

                //   c.setExonere_TVA(rs.getString("exonere_TVA"));
                c.setEmail(rs.getString("Email"));
                c.setSite(rs.getString("site"));
                c.setFax(rs.getString("fax"));

                c.setEcheance_payement(rs.getString("echeance_payement"));
                c.setSeuil_compte(rs.getString("seuil_compte"));

                c.setAdresse_livraison(rs.getString("adresse_livraison"));
                c.setType_Client(rs.getString("type_Client"));
                c.setEtat_Paiement(rs.getString("Etat_Paiement"));
                c.setAgence(rs.getString("agence"));
                c.setCompte_Bank(rs.getString("Compte_Bank"));
                c.setFournisseur_Preced(rs.getString("Fournisseur_Preced"));
                c.setContact_Client(rs.getString("contact_Client"));
                c.setActif(rs.getString("actif"));
                c.setId_Commercial(rs.getString("Id_Commercial"));
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
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return c;
    }

    public Client getClientByID(String id) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Client c = new Client();
        try {

            String sql = "select * from client where numero_Client =" + id + "";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("numero_Client");
                c.setNum_Client(Integer.parseInt(add1));
                c.setNom(rs.getString("nom"));
                c.setNum_tel(rs.getString("num_Tel"));
                c.setAdresse(rs.getString("adresse"));
                c.setVille(rs.getString("Ville"));
                c.setPays(rs.getString("pays"));
                c.setCode_Postale(rs.getString("code_Postale"));
                c.setZone_Geo(rs.getString("zone_Geo"));
                c.setId_Fiscale(rs.getString("id_Fiscale"));

                //   c.setExonere_TVA(rs.getString("exonere_TVA"));
                c.setEmail(rs.getString("Email"));
                c.setSite(rs.getString("site"));
                c.setFax(rs.getString("fax"));
                c.setSeuil_compte(rs.getString("seuil_compte"));
                c.setEcheance_payement(rs.getString("echeance_payement"));

                c.setAdresse_livraison(rs.getString("adresse_livraison"));
                c.setType_Client(rs.getString("type_Client"));
                c.setEtat_Paiement(rs.getString("Etat_Paiement"));
                c.setAgence(rs.getString("agence"));
                c.setCompte_Bank(rs.getString("Compte_Bank"));
                c.setFournisseur_Preced(rs.getString("Fournisseur_Preced"));
                c.setContact_Client(rs.getString("contact_Client"));
                c.setActif(rs.getString("actif"));
                c.setId_Commercial(rs.getString("Id_Commercial"));
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
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public void afficherClient(JTable table) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select numero_Client as 'ID', nom, num_Tel, adresse, Ville from client order by numero_Client desc";
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
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Vector<Vector<Object>> afficherListClient() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select numero_Client as 'ID', nom, adresse, Ville from client order by nom ";
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

    public Vector<Vector<Object>> afficherListZoneGeo() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select  distinct ville from client order by ville";
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

    public Vector<Vector<Object>> afficherListType_Article() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select id,type from type_article ";
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

    public Vector<Vector<Object>> afficherListClientByName(String nom) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select numero_Client as 'ID', nom, adresse, Ville from client where nom like '%" + nom + "%' order by nom ";
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

    public Vector<Vector<Object>> afficherListMarqueByName(String nom) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select id,marque from marque_produit where marque like '%" + nom + "%' ";
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

    public Vector<Vector<Object>> afficherListMarque() {
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
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public Vector<Vector<Object>> afficherListClientPassager() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Vector<Vector<Object>> df = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select distinct passager from facture where statut = 1";
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

    public void afficherCommercial(JTable table, String id) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select p.id,c.nom as 'Nom Client',comm.nom as 'Nom Commercial',p.pourcentage from client c,pourcentagebonus p, commercial comm "
                    + "where c.numero_client = p.id_client "
                    + "and p.id_commercial = comm.id "
                    + "and p.id_client = " + id;
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= 4; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("ID");
            columnNames.add("Client");
            columnNames.add("Commercial");
            columnNames.add("Type");

            DefaultTableModel df = new DefaultTableModel(data, columnNames);

            table.setModel(df);

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

    public ArrayList afficherClient() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        ArrayList<String> s = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select numero_Client as 'ID', nom, num_Tel, adresse, Ville from client";
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
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public void searchClient(JTable table, String txt) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, exonere_TVA, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select numero_Client as 'ID', nom, num_Tel, adresse, Ville from client where nom like '%" + txt + "%' order by numero_Client desc";
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
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
