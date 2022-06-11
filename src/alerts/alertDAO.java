/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alerts;

import Article.ArticleDao;
import Client.ClientDao;
import Config.Bank.Fournisseur;
import Conn.DataBase_connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class alertDAO {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String year;

    public DefaultTableModel afficherBLStat(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            //  if (!id_client.isEmpty()) {
            addRowsStatAchat(table, FromDate, ToDate, id_marque, refArticle, id_client, df);
            /*   } else {
                String clients = AllClientsList(table, FromDate, ToDate, id_marque, refArticle, id_client, df);
                String[] lstClient = clients.split(",");
                addRowsStatAchatAllClients(table, FromDate, ToDate, id_marque, refArticle, id_client, df, lstClient);

            }*/

            return df;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(alertDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public void addRowsStatAchat(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT c.nom,lb.ref_article,lb.designation_article,b.Num_bl, DATE_FORMAT(b.date_bl,'%d/%m/%Y'),lb.prix_u,lb.qte FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and b.date_bl >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and b.date_bl <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String refClause = "";
            if (!(refArticle.isEmpty())) {
                refClause = " and lb.ref_article  = '" + refArticle + "'";
                sql = sql + refClause;

            }
            String marqueClause = "";
            if (!(id_marque.isEmpty())) {
                marqueClause = " and r.id_marque  = " + id_marque + "";
                sql = sql + marqueClause;

            }
            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and b.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY b.date_bl DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            int qteTotalClient = 0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Ref Article");
            columnNames.add("Designation");
            columnNames.add("Num BL");
            columnNames.add("Date BL");
            columnNames.add("Prix Unitaire");

            columnNames.add("Quantité");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                Object LigneData[] = new Object[7];

                LigneData[0] = rs.getString("c.nom");
                LigneData[1] = rs.getString("lb.ref_article");
                LigneData[2] = rs.getString("lb.designation_article");
                LigneData[3] = rs.getString("b.Num_bl");
                LigneData[4] = rs.getString("DATE_FORMAT(b.date_bl,'%d/%m/%Y')");

                LigneData[5] = rs.getString("lb.prix_u");
                LigneData[6] = rs.getString("lb.qte");
                df.insertRow(startAdd, LigneData);

                qteTotalClient += Integer.valueOf(rs.getString("lb.qte"));
                startAdd++;

            }
            Object LigneData[] = new Object[7];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "";
            LigneData[6] = qteTotalClient;
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ajouterPeriodeArticleClient(String id_client, String ref_client, String nb_jour) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into alert_client_article (id_client,ref_article,nb_jours_alert) "
                    + "values('" + id_client + "','" + ref_client + "','" + nb_jour + "')";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Ligne Ajoutée");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(alertDAO.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void afficherAffectationArticleClient(JTable table) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  a.id,c.nom as 'Nom Client',a.ref_article ,a.nb_jours_alert as 'Nombre Jours',id_client FROM `alert_client_article` a left join client c on a.id_client=c.numero_Client";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = ArticleDao.buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(alertDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateAffectationAlertArticleClient(String id_client, String ref_article, String nb_jours, String id) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "UPDATE `alert_client_article` SET `nb_jours_alert`= '" + nb_jours + "',`id_client`='" + id_client + "',`ref_article`='" + ref_article + "' WHERE id = " + id + "";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Modification réussite");

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

    public void supprimerAffectationArticleClient(String id) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "delete from alert_client_article where id =" + id + "";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Ligné Supprimée");

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
