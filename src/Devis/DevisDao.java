/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Devis;

import Article.Article;
import Article.ArticleDao;
import Avoir.ligneAvoir;
import BL.LigneBL;
import Config.Commen_Proc;
import Conn.DataBase_connect;
import Facture.ligne_facture;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class DevisDao {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String year;

    boolean ajouterDevis(Devis d) {
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
            sql = "INSERT INTO devis(Num_devis, date_devis, id_client, remise, Total_HT, Total_TTC, montant_tva, timbre, exh_TVA,"
                    + " Code_TVA, Infos_devis, Afficher_total, facture_proformat, "
                    + "Afficher_validiter, Afficher_prix, Edit_ref,year) "
                    + "VALUES ('" + d.getNum_Devis() + "','" + d.getDate_devis() + "'," + d.getId_client() + ","
                    + "" + d.getRemise() + "," + d.getTotale_HT() + "," + d.getTotale_TTC()
                    + "," + d.getMontant_TVA() + "," + d.getTimbre() + "," + d.getExh_TVA() + ",'" + d.getCode_TVA() + "',"
                    + "'" + d.getInfos_Devis() + "'," + d.getAfficher_total() + "," + d.getFacture_proformat()
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    boolean ajouterModifImportDevis(Devis d) {
        PreparedStatement pst;
        PreparedStatement pst1;
        year = Commen_Proc.YearVal;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        String sql1 = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);
            sql1 = " select count(*) as nb from devis WHERE num_devis = '" + d.getNum_Devis() + "'";
            pst = conn.prepareStatement(sql1);
            rs = pst.executeQuery();
            int s = 0;

            while (rs.next()) {
                s = rs.getInt("nb");
            }

            if (s > 0) {
                sql = "UPDATE devis SET "
                        + "date_devis='" + d.getDate_devis() + "',"
                        + "id_client=" + d.getId_client() + ","
                        + "remise=" + d.getRemise() + ","
                        + "Total_HT=" + d.getTotale_HT() + ","
                        + "Total_TTC=" + d.getTotale_TTC() + ","
                        + "montant_tva=" + d.getMontant_TVA() + ","
                        + "timbre=" + d.getTimbre() + ","
                        + "exh_TVA=" + d.getExh_TVA() + ","
                        + "Code_TVA='" + d.getCode_TVA() + "',"
                        + "Infos_devis='" + d.getInfos_Devis() + "',"
                        + "Afficher_total=" + d.getAfficher_total() + ","
                        + "facture_proformat=" + d.getFacture_proformat() + ","
                        + "Afficher_validiter=" + d.getAfficher_validiter() + ","
                        + "Afficher_prix=" + d.getAfficher_prix() + ","
                        + "Edit_ref=" + d.getEdit_ref() + ","
                        + "year='" + year + "',";

                if (d.getPassager().isEmpty()) {
                    sql += "passager=null ,";

                } else {

                    sql += "passager='" + d.getPassager() + "',";
                }
                sql += "statut=1 "
                        + " WHERE num_devis = '" + d.getNum_Devis() + "'";

//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            } else {
                String Fieldsqlpas = "";
                String Valuesqlpas = "";

                if (!d.getPassager().isEmpty()) {
                    Fieldsqlpas = " , passager ";
                    Valuesqlpas = ", '" + d.getPassager() + "' ";
                }

                sql = "INSERT INTO devis(Num_devis, date_devis, id_client, remise, Total_HT, Total_TTC, montant_tva, timbre, exh_TVA,"
                        + " Code_TVA, Infos_devis, Afficher_total, facture_proformat, "
                        + "Afficher_validiter, Afficher_prix, Edit_ref,year" + Fieldsqlpas + " ) "
                        + "VALUES ('" + d.getNum_Devis() + "','" + d.getDate_devis() + "'," + d.getId_client() + ","
                        + "" + d.getRemise() + "," + d.getTotale_HT() + "," + d.getTotale_TTC()
                        + "," + d.getMontant_TVA() + "," + d.getTimbre() + "," + d.getExh_TVA() + ",'" + d.getCode_TVA() + "',"
                        + "'" + d.getInfos_Devis() + "'," + d.getAfficher_total() + "," + d.getFacture_proformat()
                        + "," + d.getAfficher_validiter() + "," + d.getAfficher_prix() + "," + d.getEdit_ref() + ",'" + year + "' " + Valuesqlpas + ")";
            }

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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public boolean ajouterProspection(String id_client, String ref_article, String date_visite) {
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
            sql = "INSERT INTO prospection(id_client,ref_article,date_visite) values(" + id_client + ",'" + ref_article + "','" + date_visite + "')";
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    boolean ajouterReliquat(Devis d) {
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
            String passager = "";
            if (d.getPassager().isEmpty()) {
                passager = "null";
            } else {
                passager = "'" + d.getPassager() + "'";

            }
            sql = "INSERT INTO reliquat(Num_reliquat, date_reliquat, id_client, remise, Total_HT, Total_TTC, montant_tva, timbre, exh_TVA,"
                    + " Code_TVA, Infos_reliquat, Afficher_total, facture_proformat, "
                    + "Afficher_validiter, Afficher_prix, Edit_ref,year, passager) "
                    + "VALUES ('" + d.getNum_Devis() + "','" + d.getDate_devis() + "'," + d.getId_client() + ","
                    + "" + d.getRemise() + "," + d.getTotale_HT() + "," + d.getTotale_TTC()
                    + "," + d.getMontant_TVA() + "," + d.getTimbre() + "," + d.getExh_TVA() + ",'" + d.getCode_TVA() + "',"
                    + "'" + d.getInfos_Devis() + "'," + d.getAfficher_total() + "," + d.getFacture_proformat()
                    + "," + d.getAfficher_validiter() + "," + d.getAfficher_prix() + "," + d.getEdit_ref() + ",'" + year + "'," + passager + ")";
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public Boolean checkSeuilCompte(String id_client, Double montant) {

        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        Boolean res = true;
        try {

            String s2 = "SELECT c.seuil_compte as seuil, sum(Total_TTC) as total "
                    + "FROM `bl` b "
                    + "left join client c on b.id_client=c.numero_Client "
                    + "where b.invoiced=0 and b.statut=1 ";

            String idClientFrom = "";
            if (!(id_client.isEmpty())) {
                idClientFrom = " and b.id_client = '" + id_client + "' ";
                s2 = s2 + idClientFrom;
            }

            s2 = s2 + " ORDER BY b.`date_bl` DESC";

            pst = conn.prepareStatement(s2);
            rs = pst.executeQuery();
            Double total = 0.0;
            Double seuil = 0.0;

            while (rs.next()) {
                String s = rs.getString("seuil");
                if (!s.isEmpty()) {
                    seuil = Double.valueOf(s);
                }

                String t = rs.getString("total");
                if (!t.isEmpty()) {
                    total = Double.valueOf(t);
                }
            }
            Double montantTotal = (total + montant);
            if (montantTotal <= seuil) {
                res = false;
            } else {
                res = true;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return res;//b;//DateCheckOnUpdateTest(table_name, Date_column, where_num_column, val_num, DateToChek, "Yes");
    }

    boolean modifierDevis(Devis d) {
        PreparedStatement pst;
        year = Commen_Proc.YearVal;
        DataBase_connect obj = new DataBase_connect();
        Devis c = d;
        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);

            sql = "UPDATE devis SET "
                    + "date_devis='" + c.getDate_devis() + "',"
                    + "id_client=" + c.getId_client() + ","
                    + "remise=" + c.getRemise() + ","
                    + "Total_HT=" + c.getTotale_HT() + ","
                    + "Total_TTC=" + c.getTotale_TTC() + ","
                    + "montant_tva=" + c.getMontant_TVA() + ","
                    + "timbre=" + c.getTimbre() + ","
                    + "exh_TVA=" + c.getExh_TVA() + ","
                    + "Code_TVA='" + c.getCode_TVA() + "',"
                    + "Infos_devis='" + c.getInfos_Devis() + "',"
                    + "Afficher_total=" + c.getAfficher_total() + ","
                    + "facture_proformat=" + c.getFacture_proformat() + ","
                    + "Afficher_validiter=" + c.getAfficher_validiter() + ","
                    + "Afficher_prix=" + c.getAfficher_prix() + ","
                    + "Edit_ref=" + c.getEdit_ref() + ","
                    + "year='" + year + "',";

            if (c.getPassager().isEmpty()) {
                sql += "passager=null ,";

            } else {

                sql += "passager='" + c.getPassager() + "',";
            }
            sql += "statut=1 "
                    + " WHERE num_devis = '" + c.getNum_Devis() + "'";

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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    boolean ajouterDevisPassager(Devis d) {
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
            sql = "INSERT INTO devis(Num_devis, date_devis, id_client, remise, Total_HT, Total_TTC, montant_tva, timbre, exh_TVA,"
                    + " Code_TVA, Infos_devis,adresse , Afficher_total, facture_proformat, "
                    + "Afficher_validiter, Afficher_prix, Edit_ref,passager ,year) "
                    + "VALUES ('" + d.getNum_Devis() + "','" + d.getDate_devis() + "'," + d.getId_client() + ","
                    + "" + d.getRemise() + "," + d.getTotale_HT() + "," + d.getTotale_TTC()
                    + "," + d.getMontant_TVA() + "," + d.getTimbre() + "," + d.getExh_TVA() + ",'" + d.getCode_TVA() + "',"
                    + "'" + d.getInfos_Devis() + "','" + d.getAdresse() + "'," + d.getAfficher_total() + "," + d.getFacture_proformat()
                    + "," + d.getAfficher_validiter() + "," + d.getAfficher_prix() + "," + d.getEdit_ref() + ",'"
                    + d.getPassager() + "','" + year + "')";
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void ajouterLigneDevis(ArrayList<ligneDevis> lstd) {
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
            int x = lstd.size();
            String values = "";
            for (ligneDevis d : lstd) {

                i++;
                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_devis(id_devis, ref_article, designation_article, qte, prix_u, remise, "
                        + "tva, total_HT, total_TTC,rank) "
                        + "VALUES ";

                if (i == x) {
                    values += " ('" + d.getId_Devis() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
                            + d.getQte() + "," + d.getPrix_U() + "," + d.getRemise() + "," + d.getTVA() + "," + d.getTotal_HT()
                            + "," + d.getTotale_TTC() + ",'" + i + "') ";
                } else {
                    values += " ('" + d.getId_Devis() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
                            + d.getQte() + "," + d.getPrix_U() + "," + d.getRemise() + "," + d.getTVA() + "," + d.getTotal_HT()
                            + "," + d.getTotale_TTC() + ",'" + i + "'), ";
                }

//sql = "insert into client_info(id,name,address,contact,datee) values (NULL,'" + c.Adresse + "','" + c.Compte_Bank + "','" + c.Compte_Bank + "','" + c.Adresse + "')";
            }
            pst = (PreparedStatement) conn.prepareStatement(sql + values + " ;");
            pst.execute();

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

    public void ajouterLigneReliquat(ArrayList<ligneDevis> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        String sql = "";
        try {
            //max_id upadate

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (ligneDevis d : lstd) {

                String currentTime = sdf.format(dt);
                sql = "INSERT INTO ligne_reliquat(id_reliquat, ref_article, designation_article, qte, prix_u, remise, "
                        + "tva, total_HT, total_TTC,rank) "
                        + "VALUES ('" + d.getId_Devis() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }
    }

    public void modifierLigneDevis(ArrayList<ligneDevis> lstd) {
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
            String refs = "";
            for (ligneDevis d : lstd) {
                ligneDevis c = d;
                String currentTime = sdf.format(dt);

                refs += "'" + c.getId_Devis() + "',";


                /* sql = "UPDATE ligne_devis SET "
                        + "ref_article='" + c.getRef_article() + "',"
                        + "designation_article='" + c.getDesign() + "',"
                        + "qte='" + c.getQte() + "',"
                        + "prix_u=" + c.getPrix_U() + ","
                        + "remise=" + c.getRemise() + ","
                        + "tva=" + c.getTVA() + ","
                        + "total_HT=" + c.getTotal_HT() + ","
                        + "total_TTC=" + c.getTotale_TTC() + ""
                        + " WHERE id_devis = '" + c.getId_Devis() + "'";*/ /* old
                sql = "UPDATE ligne_devis SET "
                        + "id_devis='-1'"
                        + " WHERE id_devis = '" + c.getId_Devis() + "'";

                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();*/
            }
            refs = refs.substring(0, refs.length() - 1);

            sql = "UPDATE ligne_devis SET "
                    + "id_devis='-1'"
                    + " WHERE id_devis in (" + refs + ")";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

            /* sqldel = "delete from ligne_devis where id_devis ='-1'";
            pst = (PreparedStatement) conn.prepareStatement(sqldel);
            pst.execute();*/
            int i = 0;
            int x = lstd.size();
            String values = "";
            for (ligneDevis d : lstd) {
                i++;
                sql = "INSERT INTO ligne_devis(id_devis, ref_article, designation_article, qte, prix_u, remise, "
                        + "tva, total_HT, total_TTC,rank) "
                        + "VALUES ";

                if (i == x) {
                    values += " ('" + d.getId_Devis() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
                            + d.getQte() + "," + d.getPrix_U() + "," + d.getRemise() + "," + d.getTVA() + "," + d.getTotal_HT()
                            + "," + d.getTotale_TTC() + ",'" + i + "') ";
                } else {
                    values += " ('" + d.getId_Devis() + "','" + d.getRef_article() + "','" + d.getDesign() + "',"
                            + d.getQte() + "," + d.getPrix_U() + "," + d.getRemise() + "," + d.getTVA() + "," + d.getTotal_HT()
                            + "," + d.getTotale_TTC() + ",'" + i + "'), ";
                }

            }

            pst = (PreparedStatement) conn.prepareStatement(sql + values);
            pst.execute();

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

    public ArrayList<String> afficherDevis() {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        ArrayList<String> s = null;
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "select * from Devis where statut = 1";
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
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
            sql = "SELECT c.nom as `Client`,d.`Num_devis`,DATE_FORMAT(d.`date_devis`,'%d/%m/%Y'),d.`Total_TTC` FROM `devis` d , client c where d.id_client=c.numero_client and d.statut=1 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClause = "";
            if (!(FromDate.isEmpty() & ToDate.isEmpty())) {
                DateClause = " and d.date_devis BETWEEN '" + FromDate + "' AND '" + ToDate + "'";
                sql = sql + DateClause;
            }
            String MontantClause = "";
            if (!(FromMontant.isEmpty() & ToMontant.isEmpty())) {
                MontantClause = " and d.total_TTC BETWEEN " + FromMontant + " AND " + ToMontant + "";
                sql = sql + MontantClause;
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
            System.out.println(sql);

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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public DefaultTableModel afficherDevis(JTable table) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT c.nom as `Client`,d.`Num_devis`,DATE_FORMAT(d.`date_devis`,'%d/%m/%Y'),d.`Total_TTC` FROM `devis` d left join client c on (d.id_client=c.numero_client)  where d.statut = 1 order by d.`date_devis` desc";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);

            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                //df.setValueAt("false", j, 4);
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public void afficherHistPrix(JTable table, String NomClient, String Ref_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "select ld.ref_article, ld.designation_article, ld.`prix_u`, ld.qte, ld.remise, ld.tva,DATE_FORMAT(d.date_bl,'%d/%m/%Y')  "
                    + "from ligne_bl ld left join  bl d on ld.id_bl = d.num_bl "
                    + "left join client c on d.id_client = c.numero_client  "
                    + " where c.nom='" + NomClient + "' "
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
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
        table.getColumnModel().getColumn(6).setHeaderValue("Date BL");
        table.getTableHeader().resizeAndRepaint();
    }

    public DefaultTableModel afficherDetailDevis(JTable table, String Num_Devis, Boolean withoutNumDevis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = new DefaultTableModel();
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            if (withoutNumDevis) {
                sql = "SELECT `id`,  `ref_article`, `designation_article`,`prix_u`, `qte`, `total_HT`, `remise`, `tva`,  `total_TTC` from ligne_devis where id_devis='" + Num_Devis + "'";

            } else {
                sql = "SELECT * from ligne_devis where id_devis='" + Num_Devis + "'";

            }

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

                /* Object k = df.getValueAt(j, 9);
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailFacturePassager(JTable table, String Num_Devis, Boolean withoutNumDevis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = new DefaultTableModel();
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            if (withoutNumDevis) {
                sql = "SELECT `id`,  `ref_article`, `designation_article`,`prix_u`, `qte`, `total_HT`, `remise`, `tva`,  `total_TTC` from  ligne_facture_passager where id_facture_passager='" + Num_Devis + "'";

            } else {
                sql = "SELECT * from ligne_facture_passager where id_facture_passager='" + Num_Devis + "'";

            }

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

                /* Object k = df.getValueAt(j, 9);
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailMultipleDevis(JTable table, String Num_Devis, Boolean withoutNumDevis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = new DefaultTableModel();
        Connection conn = obj.Open();
        try {
            String sql;
            String sql1;

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            //sql = "SELECT `id`,  `ref_article`, `designation_article`,`prix_u`, `qte`, `total_HT`, `remise`, `tva`,  `total_TTC` from ligne_devis where 1 ";
            sql = "SELECT `id`, `ref_article`, `designation_article`,`prix_u`, sum(`qte`), `total_HT`, `remise`, `tva`, sum(`total_TTC`) from ligne_devis where 1 ";
            sql1 = "SELECT * from ligne_devis where 1";
            if (!Num_Devis.isEmpty()) {
                if (Num_Devis.contains("'")) {
                    sql += " and id_devis in (" + Num_Devis + ")";
                } else {
                    sql += " and id_devis in ('" + Num_Devis + "')";
                }
            }
            sql += "GROUP by id order by cast(rank as unsigned)";
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

                /* Object k = df.getValueAt(j, 9);
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailMultipleReliquat(JTable table, String Num_Devis, Boolean withoutNumDevis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = new DefaultTableModel();
        Connection conn = obj.Open();
        try {
            String sql;
            String sql1;

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            //sql = "SELECT `id`,  `ref_article`, `designation_article`,`prix_u`, `qte`, `total_HT`, `remise`, `tva`,  `total_TTC` from ligne_devis where 1 ";
            sql = "SELECT `id`, `ref_article`, `designation_article`,`prix_u`, sum(`qte`), `total_HT`, `remise`, `tva`, sum(`total_TTC`) from ligne_reliquat where 1 ";
            sql1 = "SELECT * from ligne_reliquat where 1";
            if (!Num_Devis.isEmpty()) {
                if (Num_Devis.contains("'")) {
                    sql += " and id_reliquat in (" + Num_Devis + ")";
                } else {
                    sql += " and id_reliquat in ('" + Num_Devis + "')";
                }

            }
            sql += "GROUP by ref_article";
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

                /* Object k = df.getValueAt(j, 9);
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailBL(JTable table, String Num_Devis, Boolean withoutNumDevis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = new DefaultTableModel();
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            if (withoutNumDevis) {
                sql = "SELECT `id`,  `ref_article`, `designation_article`,`prix_u`, `qte`, `total_HT`, `remise`, `tva`,  `total_TTC` from ligne_bl where id_bl='" + Num_Devis + "' order by cast(rank as unsigned)";

            } else {
                sql = "SELECT * from ligne_bl where id_bl='" + Num_Devis + "' order by cast(rank as unsigned)";

            }

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

                /* Object k = df.getValueAt(j, 9);
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailFacture(JTable table, String Num_Devis, Boolean withoutNumDevis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = new DefaultTableModel();
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            if (withoutNumDevis) {
                sql = "SELECT `id`,  `ref_article`, `designation_article`,`prix_u`, `qte`, `total_HT`, `remise`, `tva`,  `total_TTC` from ligne_bl where id_bl='" + Num_Devis + "'";

            } else {
                sql = "SELECT c.nom,bl.date_bl,bl.Num_bl,bl.Total_HT,bl.remise,bl.montant_tva,bl.Total_TTC from ligne_facture lf left join bl bl on lf.num_bl=bl.Num_bl left join client c on bl.id_client=c.numero_Client WHERE lf.num_facture='" + Num_Devis + "' ";
            }
            sql = "SELECT c.nom,bl.date_bl,bl.Num_bl,bl.Total_HT,bl.remise,bl.montant_tva,bl.Total_TTC from ligne_facture lf left join bl bl on lf.num_bl=bl.Num_bl left join client c on bl.id_client=c.numero_Client WHERE lf.num_facture='" + Num_Devis + "' ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 3);
                df.setValueAt(formatDouble(Double.parseDouble(PU1.toString())), j, 3);

                Object PU = df.getValueAt(j, 4);
                df.setValueAt(formatDouble(Double.parseDouble(PU.toString())), j, 4);

                Object v = df.getValueAt(j, 5);
                df.setValueAt(formatDouble(Double.parseDouble(v.toString())), j, 5);

                Object k = df.getValueAt(j, 6);
                df.setValueAt(formatDouble(Double.parseDouble(k.toString())), j, 6);
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailBLAchat(JTable table, String Num_Devis, Boolean withoutNumDevis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = new DefaultTableModel();
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            if (withoutNumDevis) {
                sql = "SELECT `id`, `ref_article`,`designation_article`,`prix_u`,`qte`,`total_HT`,`remise`,`tva`,`total_TTC` from ligne_bl_achat where id_bl_achat='" + Num_Devis + "'";

            } else {
                sql = "SELECT `id`, `ref_article`,`designation_article`,`prix_u`,`qte`,`total_HT`,`remise`,`tva`,`total_TTC` from ligne_bl_achat where id_bl_achat='" + Num_Devis + "'";

            }

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

                /* Object k = df.getValueAt(j, 9);
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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherDetailDevisForPre_commande(JTable table, String Num_Devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = new DefaultTableModel();
        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT `id`,  `ref_article`, `designation_article`, `qte`,rank from ligne_devis where id_devis in (" + Num_Devis + ")";

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
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
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

    public String maxNumDevis(String num) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT * FROM `devis` where num_devis like '%" + num + "%' and statut=1 order by num_devis desc LIMIT 1 ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                s = rs.getString("num_devis");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public String maxNumReliquat(String num) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String s = "";
        try {
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String sql = "SELECT * FROM `reliquat` where num_reliquat like '%" + num + "%' order by id desc LIMIT 1 ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                s = rs.getString("num_reliquat");
            }
            return s;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(DevisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }

    public void modifierStock(ArrayList<ligneDevis> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (ligneDevis d : lstd) {

                String sql = "UPDATE article SET "
                        + "qte= qte - " + d.getQte()
                        + " WHERE ref = '" + d.getRef_article() + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

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

    public void modifierStockBL(ArrayList<LigneBL> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (LigneBL d : lstd) {

                String sql = "UPDATE article SET "
                        + "qte= qte - " + d.getQte()
                        + " WHERE ref = '" + d.getRef_article() + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

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

    public void modifierStockBLOnUpdate(ArrayList<LigneBL> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (LigneBL d : lstd) {

                String sql = "UPDATE article SET "
                        + "qte= qte - " + d.getQte()
                        + " WHERE ref = '" + d.getRef_article() + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

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

    public void modifierStockFacturePassagerOnUpdate(ArrayList<ligneDevis> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (ligneDevis d : lstd) {

                String sql = "UPDATE article SET "
                        + "qte= qte - " + d.getQte()
                        + " WHERE ref = '" + d.getRef_article() + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

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

    public void modifierStockBLOnDelete(ArrayList<LigneBL> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (LigneBL d : lstd) {

                String sql = "UPDATE article SET "
                        + "qte= qte + " + d.getQte()
                        + " WHERE ref = '" + d.getRef_article() + "'";

                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

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

    public void DeleteLigneFacture(ArrayList<ligne_facture> lstd) {
        PreparedStatement pst;
        PreparedStatement pst2;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (ligne_facture d : lstd) {

                String sql = "delete from ligne_facture where "
                        + "num_facture ='" + d.getNum_facture() + "' and num_bl = '" + d.getNum_bl() + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();

                String sql2 = "UPDATE bl SET "
                        + "invoiced = 0 "
                        + " WHERE num_bl ='" + d.getNum_bl() + "'";

                pst2 = (PreparedStatement) conn.prepareStatement(sql2);
                pst2.execute();
            }

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

    public void modifierStockBLAchatOnDelete(ArrayList<LigneBL> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (LigneBL d : lstd) {

                String sql = "UPDATE article SET "
                        + "qte= qte - " + d.getQte()
                        + " WHERE ref = '" + d.getRef_article() + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

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

    public void modifierStockAvoirOnDelete(ArrayList<ligneAvoir> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (ligneAvoir d : lstd) {

                String sql = "UPDATE article SET "
                        + "qte= qte + " + d.getQte()
                        + " WHERE ref = '" + d.getRef_article() + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

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

    public void modifierStockAdd(ArrayList<LigneBL> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (LigneBL d : lstd) {
                String ss = "select qte from ligne_bl where id_bl='" + d.getId_BL() + "' and ref_article='" + d.getRef_article() + "'";
                String v = "";
                pst = conn.prepareStatement(ss);
                rs = pst.executeQuery();
                while (rs.next()) {
                    v = rs.getString("qte");
                }
                String sql = "UPDATE article SET "
                        + "qte= qte + " + v
                        + " WHERE ref = '" + d.getRef_article() + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

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

    public void modifierStockAddFacturePassager(ArrayList<ligneDevis> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (ligneDevis d : lstd) {
                String ss = "select qte from ligne_facture_passager where id_facture_passager='" + d.getId_Devis() + "' and ref_article='" + d.getRef_article() + "'";
                String v = "";
                pst = conn.prepareStatement(ss);
                rs = pst.executeQuery();
                while (rs.next()) {
                    v = rs.getString("qte");
                }
                String sql = "UPDATE article SET "
                        + "qte= qte + " + v
                        + " WHERE ref = '" + d.getRef_article() + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

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

    public void modifierStockAddAchat(ArrayList<LigneBL> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (LigneBL d : lstd) {
                String ss = "select qte from ligne_bl_achat where id_bl_achat='" + d.getId_BL() + "' and ref_article='" + d.getRef_article() + "'";
                String v = "";
                pst = conn.prepareStatement(ss);
                rs = pst.executeQuery();
                while (rs.next()) {
                    v = rs.getString("qte");
                }
                String sql = "UPDATE article SET "
                        + "qte= qte + " + v
                        + " WHERE ref = '" + d.getRef_article() + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

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

    public void modifierStockModifAchat(ArrayList<LigneBL> lstd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            for (LigneBL d : lstd) {
                String ss = "select qte from ligne_bl_achat where id_bl_achat='" + d.getId_Devis() + "' and ref_article='" + d.getRef_article() + "'";
                String v = "";
                pst = conn.prepareStatement(ss);
                rs = pst.executeQuery();
                while (rs.next()) {
                    v = rs.getString("qte");
                }
                String sql = "UPDATE article SET "
                        + "qte= qte - " + v
                        + " WHERE ref = '" + d.getRef_article() + "'";
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();
            }

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

    public void supprimerDevis(String num_devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {

            String sql = "UPDATE devis SET "
                    + "statut=0 "
                    + " WHERE num_devis ='" + num_devis + "'";
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
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void supprimerFacture(String num_devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {

            String sql = "UPDATE facture SET "
                    + "statut=0 "
                    + " WHERE num_facture ='" + num_devis + "'";
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
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void supprimerBL(String num_devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {

            String sql = "UPDATE bl SET "
                    + "statut=0 "
                    + " WHERE num_bl ='" + num_devis + "'";
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
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void supprimerBLAchat(String num_devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {

            String sql = "UPDATE bl_achat SET "
                    + "statut=0 "
                    + " WHERE num_bl_achat ='" + num_devis + "'";
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
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void supprimerFactureAchat(String num_devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {

            String sql = "UPDATE facture_achat SET "
                    + "statut=0 "
                    + " WHERE num_facture_achat ='" + num_devis + "'";
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
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void supprimerCommandeAchat(String num_devis, String num_pre_cmd) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {

            if (!num_pre_cmd.isEmpty()) {
                num_pre_cmd = num_pre_cmd.substring(0, num_pre_cmd.length() - 1);
            }

            String sql = "UPDATE pre_command_achat SET "
                    + "id_commande =null "
                    + " WHERE num_bon_commande  in (" + num_pre_cmd + ")";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();

            sql = "UPDATE commande_achat SET "
                    + "statut=0 "
                    + " WHERE num_Commande ='" + num_devis + "'";
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
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void supprimerPreCommandeAchat(String num_devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {
            String sql = "UPDATE pre_command_achat SET "
                    + "statut=0 "
                    + " WHERE num_bon_commande ='" + num_devis + "'";
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
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void supprimerAvoir(String num_devis) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();

        try {

            String sql = "UPDATE avoir SET "
                    + "statut=0 "
                    + " WHERE num_avoir ='" + num_devis + "'";
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
                Logger.getLogger(ArticleDao.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

}
