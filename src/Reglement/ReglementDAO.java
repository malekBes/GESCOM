/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reglement;

import static Commercial.CommercialDao.buildTableModel;
import Commercial.FormCommercial;
import Config.Commen_Proc;
import Conn.DataBase_connect;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mlek
 */
public class ReglementDAO {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String year;

    public boolean ajouterReglement(Reglement d) {
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
            String date_r = d.getDate_reglement() == "" ? null : "'" + d.getDate_reglement() + "'";
            String date_e = d.getDate_echeance() == "" ? null : "'" + d.getDate_echeance() + "'";

            sql = "INSERT  INTO `reglement`(`id_client`, `id_facture`, `date_regelemnt`, `date_echeance`, `type_reglement`, `comptabilise`, "
                    + "`regle`, `banque`, `num_cheque`, `num_avoir`,isClient_Four_Passager,facture_avoir) "
                    + "VALUES ( " + d.getId_client() + ", '" + d.getNum_facture() + "', " + date_r + ""
                    + ", " + date_e + ", '" + d.getType_reglement() + "', " + d.getComptabilise() + ", " + d.getRegle() + ","
                    + "'" + d.getBanque() + "', '" + d.getNum_cheque() + "', '" + d.getNum_avoir() + "','Client','Facture')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane
                    .showMessageDialog(null, e + sql);
            return false;
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ReglementDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void supprimerReglement(String id_reglement) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {

            String sql = "UPDATE reglement SET "
                    + "statut=0 "
                    + " WHERE id ='" + id_reglement + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(ReglementDAO.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void modifierFactureReglement(String id_reglement) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {

            String sql = "UPDATE facture SET "
                    + "reglement='Non' "
                    + " WHERE num_facture ='" + id_reglement + "'";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(ReglementDAO.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public boolean ajouterReglementAvoir(Reglement d) {
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
            String date_r = d.getDate_reglement() == "" ? null : "'" + d.getDate_reglement() + "'";
            String date_e = d.getDate_echeance() == "" ? null : "'" + d.getDate_echeance() + "'";

            sql = "INSERT  INTO `reglement`(`id_client`, `id_facture`, `date_regelemnt`, `date_echeance`, `type_reglement`, `comptabilise`, "
                    + "`regle`, `banque`, `num_cheque`, `num_avoir`,isClient_Four_Passager,facture_avoir) "
                    + "VALUES ( " + d.getId_client() + ", '" + d.getNum_facture() + "', " + date_r + ""
                    + ", " + date_e + ", '" + d.getType_reglement() + "', " + d.getComptabilise() + ", " + d.getRegle() + ","
                    + "'" + d.getBanque() + "', '" + d.getNum_cheque() + "', '" + d.getNum_avoir() + "','Client','Avoir')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane
                    .showMessageDialog(null, e + sql);
            return false;
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ReglementDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public boolean ajouterReglementAvoirPassager(Reglement d) {
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
            String date_r = d.getDate_reglement() == "" ? null : "'" + d.getDate_reglement() + "'";
            String date_e = d.getDate_echeance() == "" ? null : "'" + d.getDate_echeance() + "'";

            sql = "INSERT  INTO `reglement`(`id_client`, `id_facture`,passager, `date_regelemnt`, `date_echeance`, `type_reglement`, `comptabilise`, "
                    + "`regle`, `banque`, `num_cheque`, `num_avoir`,isClient_Four_Passager,facture_avoir) "
                    + "VALUES ( " + d.getId_client() + ", '" + d.getNum_facture() + "','" + d.getPassager() + "', " + date_r + ""
                    + ", " + date_e + ", '" + d.getType_reglement() + "', " + d.getComptabilise() + ", " + d.getRegle() + ","
                    + "'" + d.getBanque() + "', '" + d.getNum_cheque() + "', '" + d.getNum_avoir() + "','Passager','Avoir')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane
                    .showMessageDialog(null, e + sql);
            return false;
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ReglementDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public boolean ajouterReglementAvoirFournisseur(Reglement d) {
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
            String date_r = d.getDate_reglement() == "" ? null : "'" + d.getDate_reglement() + "'";
            String date_e = d.getDate_echeance() == "" ? null : "'" + d.getDate_echeance() + "'";

            sql = "INSERT  INTO `reglement`(`id_client`,id_fournisseur, `id_facture`, `date_regelemnt`, `date_echeance`, `type_reglement`, `comptabilise`, "
                    + "`regle`, `banque`, `num_cheque`, `num_avoir`,isClient_Four_Passager,facture_avoir) "
                    + "VALUES ( " + d.getId_client() + "," + d.getId_fournisseur() + ", '" + d.getNum_facture() + "', " + date_r + ""
                    + ", " + date_e + ", '" + d.getType_reglement() + "', " + d.getComptabilise() + ", " + d.getRegle() + ","
                    + "'" + d.getBanque() + "', '" + d.getNum_cheque() + "', '" + d.getNum_avoir() + "','Client','Avoir')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane
                    .showMessageDialog(null, e + sql);
            return false;
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ReglementDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    boolean ajouterReglementFournisseur(Reglement d) {
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
            String date_r = d.getDate_reglement() == "" ? null : "'" + d.getDate_reglement() + "'";
            String date_e = d.getDate_echeance() == "" ? null : "'" + d.getDate_echeance() + "'";

            sql = "INSERT  INTO `reglement`(`id_client`,id_fournisseur, `id_facture`, `date_regelemnt`, `date_echeance`, `type_reglement`, `comptabilise`, "
                    + "`regle`, `banque`, `num_cheque`, `num_avoir`,isClient_Four_Passager,facture_avoir) "
                    + "VALUES ( " + d.getId_client() + "," + d.getId_fournisseur() + ", '" + d.getNum_facture() + "', " + date_r + ""
                    + ", " + date_e + ", '" + d.getType_reglement() + "', " + d.getComptabilise() + ", " + d.getRegle() + ","
                    + "'" + d.getBanque() + "', '" + d.getNum_cheque() + "', '" + d.getNum_avoir() + "','Fournisseur','Facture')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane
                    .showMessageDialog(null, e + sql);
            return false;
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ReglementDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public boolean ajouterReglementPassager(Reglement d) {
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
            String date_r = d.getDate_reglement() == "" ? null : "'" + d.getDate_reglement() + "'";
            String date_e = d.getDate_echeance() == "" ? null : "'" + d.getDate_echeance() + "'";

            sql = "INSERT  INTO `reglement`(`id_client`,id_fournisseur,passager, `id_facture`, `date_regelemnt`, `date_echeance`, `type_reglement`, `comptabilise`, "
                    + "`regle`, `banque`, `num_cheque`, `num_avoir`,isClient_Four_Passager,facture_avoir) "
                    + "VALUES ( " + d.getId_client() + "," + d.getId_fournisseur() + ",'" + d.getPassager() + "', '" + d.getNum_facture() + "', " + date_r + ""
                    + ", " + date_e + ", '" + d.getType_reglement() + "', " + d.getComptabilise() + ", " + d.getRegle() + ","
                    + "'" + d.getBanque() + "', '" + d.getNum_cheque() + "', '" + d.getNum_avoir() + "','Passager','Facture')";
//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane
                    .showMessageDialog(null, e + sql);
            return false;
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ReglementDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public DefaultTableModel afficherTableReglement(JTable table, String num_facture) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  * FROM `reglement` where id_facture = '" + num_facture + "' and statut = 1 ORDER BY `id` DESC";

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
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherTableAvoirClient(JTable table, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT c.nom, v.`Num_avoir`, DATE_FORMAT(v.`date_avoir`,'%d/%m/%Y'), v.`Total_TTC` FROM `avoir` v ,client c WHERE type_avoir='Avoir' and c.numero_Client=v.`id_client` and v.id_client =" + id_client + "";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
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
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherTableFactureClient(JTable table, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT c.nom, v.`num_facture`, DATE_FORMAT(v.`date_facture`,'%d/%m/%Y'), v.`TTC` FROM `facture` v ,client c WHERE type_facture is NULL and c.numero_Client=v.`id_client` and reglement in ('Semi','Non') and v.id_client =" + id_client + "";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
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
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherTableFacturePassager(JTable table, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT v.passager, v.`num_facture`, DATE_FORMAT(v.`date_facture`,'%d/%m/%Y'), v.`TTC` FROM `facture` v  WHERE v.type_facture ='Passager'  and reglement in ('Semi','Non') and v.passager like '%" + id_client + "%' and v.statut = 1";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 3);
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
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherTableAvoirPassager(JTable table, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  passager, v.`Num_avoir`, DATE_FORMAT(v.`date_avoir`,'%d/%m/%Y'), v.`Total_TTC` FROM `avoir` v   WHERE type_avoir='Passager'   and v.passager ='" + id_client + "'";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
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
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherTableAvoirFournisseur(JTable table, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT f.nom, v.`Num_avoir_achat`, DATE_FORMAT(v.`date_avoir_achat`,'%d/%m/%Y'), v.`Total_TTC` FROM `avoir_achat` v ,fournisseur f WHERE  f.id=v.`id_fournisseur` and v.id_fournisseur =" + id_client + "";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
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
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherTableFactureFournisseur(JTable table, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT f.nom, v.`Num_facture_achat`, DATE_FORMAT(v.`date_facture_achat`,'%d/%m/%Y'), v.`TTC` FROM `facture_achat` v ,fournisseur f WHERE  f.id=v.`id_fournisseur` and v.id_fournisseur =" + id_client + "";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
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
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
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

}
