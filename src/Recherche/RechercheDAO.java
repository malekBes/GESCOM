/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recherche;

import BL.BLDao;
import static BL.BLDao.buildTableModel;
import Config.Commen_Proc;
import Conn.DataBase_connect;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import stat.PeriodDatesClass;

/**
 *
 * @author Mlek
 */
public class RechercheDAO {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String year;

    public void afficherDevis(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient, String type_filtre) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            /*  if ((type_filtre.equals("passager"))) {
                sql = "SELECT 'Client Passager' as `Client` ,d.`Num_devis`,DATE_FORMAT(d.`date_devis`,'%d/%m/%Y'),d.`Total_TTC`,d.passager FROM `devis` d left join client c on (d.id_client=c.numero_client) where d.statut =1 ";

            } else {*/
            sql = "SELECT if(d.passager is null,c.nom,'Client Passager') as `Client`,d.`Num_devis`,DATE_FORMAT(d.`date_devis`,'%d/%m/%Y'),d.`Total_TTC`,d.passager FROM `devis` d left join client c on (d.id_client=c.numero_client) where d.statut =1 ";
            // }
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and d.id_client <> 0 ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and d.id_client = 0 ";
                sql = sql + FiltreClauseFrom;
            }

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_devis >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_devis <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }
            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.total_TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC <= " + ToMontant + "";
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
            sql += " ORDER BY d.`Num_devis` DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
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

    public void afficherEtatDuJour(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient, String type_filtre) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql = "";
            String sql2 = "";
            /*  if ((type_filtre.equals("passager"))) {
                sql = "SELECT 'Client Passager' as `Client` ,d.`Num_devis`,DATE_FORMAT(d.`date_devis`,'%d/%m/%Y'),d.`Total_TTC`,d.passager FROM `devis` d left join client c on (d.id_client=c.numero_client) where d.statut =1 ";

            } else {*/
            data = new Vector<Vector<Object>>();
            // }
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and d.id_client <> 0 ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and d.id_client = 0 ";
                sql = sql + FiltreClauseFrom;
            }

            String DateClauseFromDevis = "";
            String DateClauseFromBL = "";
            String DateClauseFromFacture = "";

            if (!(FromDate.isEmpty())) {
                DateClauseFromDevis = " and d.date_devis >= '" + FromDate + "' ";
                DateClauseFromBL = " and d.date_bl >= '" + FromDate + "' ";
                DateClauseFromFacture = " and d.date_facture >= '" + FromDate + "' ";

            }

            String DateClauseToDevis = "";
            String DateClauseToBL = "";
            String DateClauseToFacture = "";

            if (!(ToDate.isEmpty())) {
                DateClauseToDevis = " and d.date_devis <= '" + ToDate + "' ";
                DateClauseToBL = " and d.date_bl <= '" + ToDate + "' ";
                DateClauseToFacture = " and d.date_facture <= '" + ToDate + "' ";

            }
            /*     String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.total_TTC >= " + FromMontant + " ";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC <= " + ToMontant + " ";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_devis  like '%" + NumDevis + "%' ";
                sql = sql + NumDevisClause;

            }*/
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom  like '%" + NomClient + "%' ";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY d.`Num_devis` DESC ";

            sql2 = "select c.nom as nom,d.Num_devis as num,d.date_devis as 'date',d.Total_TTC as TTC,if(d.passager is null,'',d.passager ) as passager from devis d left join client c on c.numero_client=d.id_client where 1=1 " + FiltreClauseFrom + ClientClause + DateClauseToDevis + DateClauseFromDevis + " union "
                    + "select c.nom as nom,d.Num_bl as num,d.date_bl as 'date',d.Total_TTC as TTC,'' as passager from bl d left join client c on c.numero_client=d.id_client where 1=1  " + FiltreClauseFrom + ClientClause + DateClauseFromBL + DateClauseToBL + "  union "
                    + "select c.nom as nom,d.Num_facture as num,d.date_facture as 'date',d.ttc as TTC,if(d.passager is null,'',d.passager ) as passager from facture d left join client c on c.numero_client=d.id_client where 1=1  " + FiltreClauseFrom + DateClauseToFacture + ClientClause + DateClauseFromFacture + "";

            pst = conn.prepareStatement(sql2);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            ResultSetMetaData metaData = rs.getMetaData();

            // names of columns
            Vector<String> columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column).toString());
            }

            // data of the table
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            DefaultTableModel df = new DefaultTableModel(data, columnNames);

            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
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

    public Vector<Vector<Object>> afficherAlertArticle(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, String InClause, String ref_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            //  if (!id_client.isEmpty()) {
            addRowsStatAchatVenduV3(table, FromDate, ToDate, id_marque, refArticle, id_client, df, InClause, ref_article);
            /*   } else {
                String clients = AllClientsList(table, FromDate, ToDate, id_marque, refArticle, id_client, df);
                String[] lstClient = clients.split(",");
                addRowsStatAchatAllClients(table, FromDate, ToDate, id_marque, refArticle, id_client, df, lstClient);

            }*/

            return data;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");

            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    public Vector<Vector<Object>> addRowsStatAchatVenduV3(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df, String InClause, String ref_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String RefArticles = lstArticleVendu(FromDate, ToDate, id_marque, "", id_client);

            // sql = "SELECT c.nom,lb.ref_article,lb.designation_article,b.Num_bl, DATE_FORMAT(b.date_bl,'%d/%m/%Y'),lb.prix_u,lb.qte FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";
            //  sql = "SELECT c.nom,lb.ref_article,lb.designation_article FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join commercial c on b.id_commercial=c.id left join client cl on b.id_client=cl.numero_client  WHERE b.statut=1  ";
            sqlReturn = "SELECT cl.nom, lb.ref_article,DATE_FORMAT(max(b.date_bl),'%d/%m/%Y') as dt, "
                    + "CONVERT((DATEDIFF(CURDATE(),max(b.date_bl))), CHAR(50)) as nbjrs, "
                    + " if((select nb_jours_alert from alert_client_article where ref_article =lb.ref_article AND b.id_client=id_client) IS NULL , 45 ,(select nb_jours_alert from alert_client_article where ref_article = lb.ref_article AND b.id_client=id_client)) as frequence_vente, "
                    + "if( CONVERT((DATEDIFF(CURDATE(),max(b.date_bl))), int) > if((select nb_jours_alert from alert_client_article where ref_article = lb.ref_article AND b.id_client=id_client) IS NULL , 45 ,(select nb_jours_alert from alert_client_article where ref_article = lb.ref_article AND b.id_client=id_client)) , \"Oui\",\"Non\" ) as depasse "
                    + "FROM `ligne_bl` lb "
                    + "left join bl b on (lb.id_bl=b.Num_bl) "
                    + "left join client cl on cl.numero_client=b.id_client "
                    + "left join article r on lb.ref_article=r.ref "
                    + "left join pourcentagebonus pb on pb.id_client=b.id_client "
                    // + "left join commercial c on b.id_commercial=c.id "
                    + "WHERE b.statut=1 ";
            String DateClauseFrom = "";

            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and b.date_bl >= '" + FromDate + "'";
                sqlReturn += DateClauseFrom;
            }
            String clientClauseTo = "";
            if (!(id_client.isEmpty())) {
                clientClauseTo = " and b.id_client = " + id_client + "";
                sqlReturn += clientClauseTo;
            }
            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and b.date_bl <= '" + ToDate + "'";
                sqlReturn += DateClauseTo;
            }

            /* String commercialClause = "";
            if (!(id_commercial.isEmpty())) {
                commercialClause = " and pb.id_commercial  = " + id_commercial + "";
                sqlReturn += commercialClause;

            }*/
            sqlReturn += " group by cl.nom,lb.ref_article order by  DATEDIFF(CURDATE(),max(b.date_bl)) ";
            pst = conn.prepareStatement(sqlReturn);

            rs = pst.executeQuery();

            int qteTotalClient = 0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();

            columnNames.add("Client");
            columnNames.add("Ref Article");
            columnNames.add("Derniére BL");

            columnNames.add("Nb Jrs dés la Dernière Vente");// dés la Dernière Vente
            columnNames.add("Frequence Vente (Jr)");//nbjrs
            columnNames.add("Relancer");// ? (O/N)

            columnNames.add("Coucher");

            /*    columnNames.add("Designation");
            columnNames.add("Num BL");
            columnNames.add("Prix Unitaire");

            columnNames.add("Quantité");*/
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = buildTableModelColsultAlertArticleClient(rs);
            df = model;

            while (rs.next()) {

                Object LigneData[] = new Object[6];
//cl.nom, lb.ref_article,b.date_bl
                LigneData[0] = rs.getString("cl.nom");
                LigneData[1] = rs.getString("lb.ref_article");
                LigneData[2] = rs.getString("dt").toString();
                //  LigneData[3] = rs.getString("r.classification");

                LigneData[3] = rs.getString("nbjrs");
                LigneData[4] = rs.getString("frequence_vente");

                LigneData[5] = rs.getString("depasse");
                //  LigneData[2] = rs.getString("lb.designation_article");
                //  LigneData[3] = rs.getString("b.Num_bl");
                // LigneData[5] = rs.getString("lb.prix_u");
                // LigneData[6] = rs.getString("lb.qte");
                df.insertRow(startAdd, LigneData);

                // qteTotalClient += Integer.valueOf(rs.getString("lb.qte"));
                startAdd++;

            }
            //    Object LigneData[] = new Object[4];

            /*   LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";

            /*  LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "";
            LigneData[6] = qteTotalClient;*/
            //  df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return data;
    }

    public static DefaultTableModel buildTableModelColsultAlertArticleClient(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        columnNamesreg = new Vector<String>();
        columnNamesreg.add("Client");
        columnNamesreg.add("Ref Article");
        columnNamesreg.add("Derniére BL");

        columnNamesreg.add("Nb Jrs dés la Dernière Vente");// dés la Dernière Vente
        columnNamesreg.add("Frequence Vente (Jr)");//nbjrs
        columnNamesreg.add("Relancer");
        columnNamesreg.add("Coucher");

        // names of columns
        int columnCount = metaData.getColumnCount();
        /*    for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }*/

        // data of the table
        data = new Vector<Vector<Object>>();

        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            vector.add(false);
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNamesreg);

    }

    public void addRowsAlertArticle(JTable table, String FromDate, String ToDate, String id_marque, String id_commercial, String id_client, DefaultTableModel df, String InClause, String ref_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String RefArticles = "";
            String RefClients = "";
            Vector<String> columnNames = new Vector<String>();

            columnNames.add("Client");
            columnNames.add("Ref Article");

            columnNames.add("Num BL");
            columnNames.add("Date BL");

            columnNames.add("Num Facture");
            columnNames.add("Date Facture");

            columnNames.add("Qte");
            columnNames.add("Fréquence vente (Jr)");
            columnNames.add("Relancer");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            RefClients = lstAlertClients(FromDate, ToDate, id_marque, id_commercial, id_client, ref_article);
            String[] lstclient = RefClients.split(",");
            int qteTotalClient = 0;
            int startAdd = 0;
            for (String client : lstclient) {

                // sql = "SELECT c.nom,lb.ref_article,lb.designation_article,b.Num_bl, DATE_FORMAT(b.date_bl,'%d/%m/%Y'),lb.prix_u,lb.qte FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";
                //  sql = "SELECT c.nom,lb.ref_article,lb.designation_article FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join commercial c on b.id_commercial=c.id left join client cl on b.id_client=cl.numero_client  WHERE b.statut=1  ";
                sqlReturn = "SELECT cl.nom, lb.ref_article,f.num_facture,b.num_bl,b.date_bl ,lb.qte,f.date_facture,DATE_FORMAT(b.date_bl,'%d/%m/%Y') as dt,r.classification, CONVERT((DATEDIFF(CURDATE(),b.date_bl)), CHAR(50)) as nbjrs, if( CONVERT((DATEDIFF(CURDATE(),b.date_bl)), CHAR(50))>45,\"Oui\",\"Non\" ) as depasse "
                        + ",(select nb_jours_alert from alert_client_article where ref_article = lb.ref_article and b.id_client=id_client) as frequence_vente"
                        + ", if(DATEDIFF(current_date(),b.date_bl)> (select if(nb_jours_alert is null,0,nb_jours_alert) from alert_client_article where ref_article = lb.ref_article and b.id_client=id_client),'Relancer','-' ) as Relancer  "
                        + "FROM `ligne_bl` lb "
                        + "left join bl b on (lb.id_bl=b.Num_bl) "
                        + "left join client cl on cl.numero_client=b.id_client "
                        + "left join article r on lb.ref_article=r.ref "
                        + "left join pourcentagebonus pb on pb.id_client=b.id_client "
                        + "left join ligne_facture lf on lf.num_bl=lb.id_bl "
                        + "left join facture f on f.num_facture=lf.num_facture "
                        // + "left join commercial c on b.id_commercial=c.id "
                        + "WHERE b.statut=1 ";
                String DateClauseFrom = "";

                if (!(FromDate.isEmpty())) {
                    DateClauseFrom = " and b.date_bl >= '" + FromDate + "'";
                    sqlReturn += DateClauseFrom;
                }
                String clientClauseTo = "";
                if (!(client.isEmpty())) {
                    clientClauseTo = " and b.id_client = " + client + "";
                    sqlReturn += clientClauseTo;
                }
                String DateClauseTo = "";
                if (!(ToDate.isEmpty())) {
                    DateClauseTo = " and b.date_bl <= '" + ToDate + "'";
                    sqlReturn += DateClauseTo;
                }
                String refClause = "";
                if (!(ref_article.isEmpty())) {
                    refClause = " and lb.ref_article in (" + ref_article + ")";
                    sqlReturn += refClause;

                }
                String commercialClause = "";
                if (!(id_commercial.isEmpty())) {
                    commercialClause = " and pb.id_commercial  = " + id_commercial + "";
                    sqlReturn += commercialClause;

                }
                sqlReturn += " order by b.date_bl desc ";
                pst = conn.prepareStatement(sqlReturn);

                rs = pst.executeQuery();

                // startAdd = 0;
                while (rs.next()) {

                    Object LigneData[] = new Object[9];

                    LigneData[0] = rs.getString("cl.nom");
                    LigneData[1] = rs.getString("lb.ref_article");
                    LigneData[2] = rs.getString("num_bl").toString();
                    LigneData[3] = rs.getString("date_bl");

                    LigneData[4] = rs.getString("num_facture");

                    LigneData[5] = rs.getString("date_facture");
                    LigneData[6] = rs.getString("qte");
                    LigneData[7] = rs.getString("frequence_vente");
                    LigneData[8] = rs.getString("Relancer");

                    //  LigneData[2] = rs.getString("lb.designation_article");
                    //  LigneData[3] = rs.getString("b.Num_bl");
                    // LigneData[5] = rs.getString("lb.prix_u");
                    // LigneData[6] = rs.getString("lb.qte");
                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Integer.valueOf(rs.getString("qte"));
                    startAdd++;

                }
            }
            Object LigneData[] = new Object[9];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";

            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "";
            LigneData[6] = String.valueOf(qteTotalClient);
            LigneData[7] = "";
            LigneData[8] = "";

            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsAlertArticleV2(JTable table, String FromDate, String ToDate, String id_marque, String id_commercial, String id_client, DefaultTableModel df, String InClause, String ref_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String RefArticles = "";
            String RefClients = "";
            Vector<String> columnNames = new Vector<String>();

            columnNames.add("Client");
            columnNames.add("Ref Article");

            columnNames.add("Num BL");
            columnNames.add("Date BL");

            columnNames.add("Num Facture");
            columnNames.add("Date Facture");

            columnNames.add("Qte");
            columnNames.add("Fréquence vente (Jr)");
            columnNames.add("Relancer");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            RefClients = lstAlertClients(FromDate, ToDate, id_marque, id_commercial, id_client, ref_article);
            String[] lstclient = RefClients.split(",");
            int qteTotalClient = 0;
            int startAdd = 0;
            for (String client : lstclient) {

                // sql = "SELECT c.nom,lb.ref_article,lb.designation_article,b.Num_bl, DATE_FORMAT(b.date_bl,'%d/%m/%Y'),lb.prix_u,lb.qte FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";
                //  sql = "SELECT c.nom,lb.ref_article,lb.designation_article FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join commercial c on b.id_commercial=c.id left join client cl on b.id_client=cl.numero_client  WHERE b.statut=1  ";
                sqlReturn = "SELECT cl.nom, lb.ref_article,f.num_facture,b.num_bl,b.date_bl ,lb.qte,f.date_facture,DATE_FORMAT(b.date_bl,'%d/%m/%Y') as dt,r.classification, CONVERT((DATEDIFF(CURDATE(),b.date_bl)), CHAR(50)) as nbjrs, if( CONVERT((DATEDIFF(CURDATE(),b.date_bl)), CHAR(50))>45,\"Oui\",\"Non\" ) as depasse "
                        + ",(select nb_jours_alert from alert_client_article where ref_article = lb.ref_article and b.id_client=id_client) as frequence_vente"
                        + ", if(DATEDIFF(current_date(),b.date_bl)> (select if(nb_jours_alert is null,0,nb_jours_alert) from alert_client_article where ref_article = lb.ref_article and b.id_client=id_client),'Relancer','-' ) as Relancer  "
                        + "FROM `ligne_bl` lb "
                        + "left join bl b on (lb.id_bl=b.Num_bl) "
                        + "left join client cl on cl.numero_client=b.id_client "
                        + "left join article r on lb.ref_article=r.ref "
                        + "left join pourcentagebonus pb on pb.id_client=b.id_client "
                        + "left join ligne_facture lf on lf.num_bl=lb.id_bl "
                        + "left join facture f on f.num_facture=lf.num_facture "
                        // + "left join commercial c on b.id_commercial=c.id "
                        + "WHERE b.statut=1 ";
                String DateClauseFrom = "";

                if (!(FromDate.isEmpty())) {
                    DateClauseFrom = " and b.date_bl >= '" + FromDate + "'";
                    sqlReturn += DateClauseFrom;
                }
                String clientClauseTo = "";
                if (!(client.isEmpty())) {
                    clientClauseTo = " and b.id_client = " + client + "";
                    sqlReturn += clientClauseTo;
                }
                String DateClauseTo = "";
                if (!(ToDate.isEmpty())) {
                    DateClauseTo = " and b.date_bl <= '" + ToDate + "'";
                    sqlReturn += DateClauseTo;
                }
                String refClause = "";
                if (!(ref_article.isEmpty())) {
                    refClause = " and lb.ref_article in (" + ref_article + ")";
                    sqlReturn += refClause;

                }
                String commercialClause = "";
                if (!(id_commercial.isEmpty())) {
                    commercialClause = " and pb.id_commercial  = " + id_commercial + "";
                    sqlReturn += commercialClause;

                }
                sqlReturn += " order by b.date_bl   desc ";
                pst = conn.prepareStatement(sqlReturn);

                rs = pst.executeQuery();

                // startAdd = 0;
                while (rs.next()) {

                    Object LigneData[] = new Object[9];

                    LigneData[0] = rs.getString("cl.nom");
                    LigneData[1] = rs.getString("lb.ref_article");
                    LigneData[2] = rs.getString("num_bl").toString();
                    LigneData[3] = rs.getString("date_bl");

                    LigneData[4] = rs.getString("num_facture");

                    LigneData[5] = rs.getString("date_facture");
                    LigneData[6] = rs.getString("qte");
                    LigneData[7] = rs.getString("frequence_vente");
                    LigneData[8] = rs.getString("Relancer");

                    //  LigneData[2] = rs.getString("lb.designation_article");
                    //  LigneData[3] = rs.getString("b.Num_bl");
                    // LigneData[5] = rs.getString("lb.prix_u");
                    // LigneData[6] = rs.getString("lb.qte");
                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Integer.valueOf(rs.getString("qte"));
                    startAdd++;

                }
            }
            Object LigneData[] = new Object[9];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";

            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "";
            LigneData[6] = String.valueOf(qteTotalClient);
            LigneData[7] = "";
            LigneData[8] = "";

            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public String lstAlertClients(String FromDate, String ToDate, String id_marque, String id_commercial, String id_client, String ref_article) {

        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String RefArticles = "";

        try {

            String sql2 = "SELECT distinct b.id_client, max(b.date_bl) "
                    + "FROM `ligne_bl` lb "
                    + "left join bl b on (lb.id_bl=b.Num_bl) ";
            if (!id_marque.isEmpty()) {
                sql2 += "left join article r on lb.ref_article=r.ref ";
            }
            if (!id_commercial.isEmpty()) {
                sql2 += "left join pourcentagebonus pb on pb.id_client=b.id_client ";
            }// + "left join commercial c on b.id_commercial=c.id "
            sql2 += "WHERE b.statut=1 ";
            String DateClauseFrom = "";

            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and b.date_bl >= '" + FromDate + "'";
                sql2 += DateClauseFrom;
            }
            String refClauseTo = "";
            if (!(ref_article.isEmpty())) {
                refClauseTo = " and lb.ref_article in (" + ref_article + ")";
                sql2 += refClauseTo;
            }

            String clientClauseTo = "";
            if (!(id_client.isEmpty())) {
                clientClauseTo = " and b.id_client = " + id_client + "";
                sql2 += clientClauseTo;
            }
            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and b.date_bl <= '" + ToDate + "'";
                sql2 += DateClauseTo;
            }

            String commercialClause = "";
            if (!(id_commercial.isEmpty())) {
                commercialClause = " and pb.id_commercial  = " + id_commercial + "";
                sql2 += commercialClause;

            }

            String marqueClause = "";
            if (!(id_marque.isEmpty())) {
                marqueClause = " and r.id_marque  = " + id_marque + "";
                sql2 = sql2 + marqueClause;

            }
            sql2 += "  group by b.id_client order by max(b.date_bl)";
            pst = conn.prepareStatement(sql2);

            rs = pst.executeQuery();

            while (rs.next()) {
                RefArticles += "" + rs.getString("b.id_client") + ",";
            }
            if (!RefArticles.isEmpty()) {

                RefArticles = RefArticles.substring(0, RefArticles.length() - 1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return RefArticles;
    }

    public void afficherReliquat(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient, String type_filtre) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            /*  if ((type_filtre.equals("passager"))) {
                sql = "SELECT 'Client Passager' as `Client` ,d.`Num_devis`,DATE_FORMAT(d.`date_devis`,'%d/%m/%Y'),d.`Total_TTC`,d.passager FROM `devis` d left join client c on (d.id_client=c.numero_client) where d.statut =1 ";

            } else {*/
            sql = "SELECT if(d.passager is null,c.nom,'Client Passager') as `Client`,d.`Num_reliquat`,DATE_FORMAT(d.`date_reliquat`,'%d/%m/%Y'),d.`Total_TTC`,d.passager FROM `reliquat` d left join client c on (d.id_client=c.numero_client) where d.statut =1 ";
            // }
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and d.id_client <> 0 ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and d.id_client = 0 ";
                sql = sql + FiltreClauseFrom;
            }

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_reliquat >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_reliquat <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }
            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.total_TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC <= " + ToMontant + "";
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
            sql += " ORDER BY d.`Num_reliquat` DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
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

    public Vector<Vector<Object>> afficherAvoir(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String id_commercial, String NomClient, String type_filtre, String type_filtre_paid, String type_reglement) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;//DATE_FORMAT(r.`date_regelemnt`,'%d/%m/%Y')
            sql = "SELECT r.id,c.nom, f.Nom ,comm.nom , r.`id_facture` ,  "
                    //
                    //  + " if(r.type_reglement in ('cheque','EFFET')  ,DATE_FORMAT(r.`date_echeance`,'%d/%m/%Y'),'') as  date_regelemnt, "
                    //  + "if(r.type_reglement in ('espece','virement')  ,DATE_FORMAT(r.`date_regelemnt`,'%d/%m/%Y'),'') as  date_echeance"
                    + "if(r.`date_regelemnt` is null && r.type_reglement in ('virement'), DATE_FORMAT(r.`date_echeance`,'%d/%m/%Y'),DATE_FORMAT(r.`date_regelemnt`,'%d/%m/%Y')) , if(r.type_reglement in ('espece','virement')  ,'-',DATE_FORMAT(r.`date_echeance`,'%d/%m/%Y')) as  date_echeance"
                    // "  DATE_FORMAT(r.`date_echeance`,'%d/%m/%Y')"
                    + ", r.`type_reglement`, "
                    + "r.`regle`,IF((r.isClient_Four_Passager = 'Client'), fct.TTC , fct_a.TTC) as TTC,"
                    + "IF((r.isClient_Four_Passager = 'Client'), (fct.TTC- r.regle) , (fct_a.TTC- r.regle)) as Restant "
                    + ", r.`banque`, r.`num_cheque`, r.`passager` FROM `reglement` r "
                    + "left join client c on (r.`id_client`=c.numero_Client) "
                    + "left join pourcentagebonus pb on  r.`id_client` = pb.id_client "
                    + "left join commercial comm on pb.id_commercial = comm.id "
                    + "left join fournisseur f on (r.`id_fournisseur`=f.id) "
                    + "left join facture fct on (r.`id_facture`=fct.num_facture) "
                    + "left join facture_achat fct_a on (r.`id_facture`=fct_a.num_facture_achat) WHERE 1 and r.statut =1 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and r.isClient_Four_Passager ='Client' ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and r.isClient_Four_Passager ='Passager' ";
                sql = sql + FiltreClauseFrom;
            }
            if ((type_filtre.equals("four"))) {
                FiltreClauseFrom = " and r.isClient_Four_Passager ='Fournisseur' ";
                sql = sql + FiltreClauseFrom;
            }
            String Type_reglementClause = "";
            if (!(type_reglement.equals("-"))) {
                Type_reglementClause = " and r.type_reglement like '" + type_reglement + "'";
                sql = sql + Type_reglementClause;
            }
            /*  if ((!type_filtre_paid.equals("all"))) {
                FiltreClauseFrom = " and r. ='" + type_filtre_paid + "' ";
                sql = sql + FiltreClauseFrom;
            }*/
            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and r.date_regelemnt >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and r.date_regelemnt <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }
            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and fct.TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and fct.TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            /*  String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and r.id  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }*/
            String CommercialClause = "";
            if (!(id_commercial.isEmpty())) {
                CommercialClause = " and pb.id_commercial  = " + id_commercial;
                sql = sql + CommercialClause;
            }

            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom  like '%" + NomClient + "%'";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY fct.num_facture desc ";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModelColsultReglement(rs);

            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                Object PU1 = df.getValueAt(j, 8);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU1.toString())), j, 8);

                Object PU2 = df.getValueAt(j, 9);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU2.toString())), j, 9);

                Object PU = df.getValueAt(j, 10);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 10);

            }
            table.setModel(df);
            return data;
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
        return data;
    }

    public Vector<Vector<Object>> afficherConsumtationReglementComptable(JTable table, String FromDate, String ToDate, String FromDate_fact, String ToDate_fact, String FromMontant, String ToMontant, String id_commercial, String NomClient, String type_filtre, String type_filtre_paid, String type_reglement, String num_facture) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;//DATE_FORMAT(r.`date_regelemnt`,'%d/%m/%Y')
            sql = "SELECT r.id,c.nom ,/* f.Nom ,comm.nom , */  fct.`num_facture` , fct.date_facture , DATE_FORMAT(r.`date_echeance`,'%d/%m/%Y'), /*DATE_FORMAT(r.`date_echeance`,'%d/%m/%Y'), */ r.`type_reglement`, "
                    + "count(r.`regle`),/*(select count(*) from reglement dr where fct.`num_facture`=dr.`id_facture`) as nb_reg,*/ "
                    + "/*IF((r.isClient_Four_Passager = 'Client'), fct.TTC , fct_a.TTC) as TTC,*/ "
                    + " fct.TTC as TTC, "
                    //  + "IF((r.isClient_Four_Passager = 'Client'), (fct.TTC- sum(r.regle)) , (fct_a.TTC- sum(r.regle))) as Restant "
                    //+ "IF((r.isClient_Four_Passager = 'Client'), (fct.TTC- (select sum(r1.regle) from reglement r1 where r1.id_facture=fct.num_facture and r1.statut=1)) , (fct_a.TTC- sum(r.regle))) as Restant "
                    + "IF((r.isClient_Four_Passager = 'Client'), if((select count(*) from reglement r2 where r2.id_facture=fct.num_facture and r2.statut=1 and upper(r2.type_reglement) = 'AVOIR') > 0,(fct.TTC - (select sum(r1.regle) from reglement r1 where r1.id_facture=fct.num_facture and r1.statut=1 and upper(r1.type_reglement)='AVOIR')), (fct.TTC - (select sum(r1.regle) from reglement r1 where r1.id_facture=fct.num_facture and r1.statut=1))) , (fct_a.TTC- sum(r.regle))) as Restant  "
                    + ", r.`banque`, r.`num_cheque`, fct.`passager` ,r.statut "
                    + "FROM facture fct "
                    + "left outer join `reglement` r on (r.`id_facture`=fct.num_facture) "
                    + "left join client c on (fct.`id_client`=c.numero_Client) "
                    + "left join pourcentagebonus pb on  fct.`id_client` = pb.id_client "
                    + "left join commercial comm on pb.id_commercial = comm.id "
                    + "left join fournisseur f on (r.`id_fournisseur`=f.id) "
                    + "left join facture_achat fct_a on (r.`id_facture`=fct_a.num_facture_achat) WHERE 1  and (r.statut = 1 or r.statut is null) ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and c.nom !='CLIENT PASSAGER' ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and c.nom ='CLIENT PASSAGER' ";
                sql = sql + FiltreClauseFrom;
            }
            if ((type_filtre.equals("four"))) {
                FiltreClauseFrom = " and r.isClient_Four_Passager ='Fournisseur' ";
                sql = sql + FiltreClauseFrom;
            }
            String Type_reglementClause = "";
            if (!(type_reglement.equals("-"))) {
                Type_reglementClause = " and r.type_reglement like '" + type_reglement + "'";
                sql = sql + Type_reglementClause;
            }
            /*  if ((!type_filtre_paid.equals("all"))) {
                FiltreClauseFrom = " and r. ='" + type_filtre_paid + "' ";
                sql = sql + FiltreClauseFrom;
            }*/
            String DateClauseFrom_fact = "";
            if (!(FromDate_fact.isEmpty())) {
                DateClauseFrom_fact = " and fct.date_facture >= '" + FromDate_fact + "'";
                sql = sql + DateClauseFrom_fact;
            }
            String DateClauseTo_fact = "";
            if (!(ToDate_fact.isEmpty())) {
                DateClauseTo_fact = " and fct.date_facture <= '" + ToDate_fact + "'";
                sql = sql + DateClauseTo_fact;
            }

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and r.date_echeance >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and r.date_echeance <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and fct.TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and fct.TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(num_facture.isEmpty())) {
                NumDevisClause = " and fct.`num_facture`  like '%" + num_facture + "%'";
                sql = sql + NumDevisClause;

            }
            String CommercialClause = "";
            if (!(id_commercial.isEmpty())) {
                CommercialClause = " and pb.id_commercial  = " + id_commercial;
                sql = sql + CommercialClause;
            }

            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and fct.id_client = " + NomClient + " ";
                sql = sql + ClientClause;

            }
            sql += ""
                    //  + "and  fct.`num_facture` in (\"18F00511\",\"18F00598\",\"18F00841\",\"18F00961\",\"18F00976\",\"18F00976\",\"18F00979\",\"18F00993\",\"18F01047\",\"18F01055\",\"18F01061\",\"18F01073\",\"18F01164\",\"19F00019\",\"19F00114\",\"19F00115\",\"19F00128\",\"19F00139\",\"19F00139\",\"19F00139\",\"19F00224\",\"19F00344\",\"19F00545\",\"19F00565\",\"19F00671\",\"19F00757\",\"19F00764\",\"19F00842\",\"19F00957\",\"19F00975\",\"19F00999\",\"19F01048\",\"19F01058\",\"19F01062\",\"20F00005\",\"20F00010\",\"20F00022\",\"20F00111\",\"20F00113\",\"20F00135\",\"20F00158\",\"20F00222\",\"20F00255\",\"20F00319\",\"20F00405\",\"20F00445\",\"20F00445\",\"20F00457\",\"20F00493\",\"20F00494\",\"20F00508\",\"20F00596\",\"20F00599\",\"20F00604\",\"20F00694\",\"20F00700\",\"20F00703\",\"20F00758\",\"20F00781\",\"20F00782\",\"20F00158\",\"20F00222\",\"20F00255\",\"20F00319\",\"20F00405\",\"20F00445\",\"20F00445\",\"20F00457\",\"20F00493\",\"20F00494\",\"20F00508\",\"20F00596\",\"20F00599\",\"20F00604\",\"20F00694\",\"20F00700\",\"20F00703\",\"20F00758\",\"20F00781\",\"20F00782\",\"20F00783\",\"20F00784\",\"20F00784\",\"20F00785\",\"20F00786\",\"20F00800\",\"20F00803\",\"20F00807\",\"20F00809\",\"20F00812\",\"20F00813\",\"20F00822\",\"20F00826\",\"20F00832\",\"20F00837\",\"20F00838\",\"20F00862\",\"20F00870\",\"20F00890\",\"20F00891\",\"20F00892\",\"20F00792\",\"20F00793\",\"20F00868\",\"20F00731\",\"19F00660\",\"19F00767\",\"19F00980\",\"18F01201\",\"19F00612\",\"19F00691\",\"19F00710\",\"19F00921\",\"19F00988\",\"19F00994\",\"20F00081\",\"20F00097\",\"20F00128\",\"20F00186\",\"20F00187\",\"20F00305\",\"20F00347\",\"20F00348\",\"20F00608\",\"20F00650\",\"20F00661\",\"20F00771\",\"20F00880\",\"19F00380\",\"19F00538\",\"19F00743\",\"20F00214\",\"20F00654\",\"20F00274\",\"20F00343\",\"20F00344\",\"20F00345\",\"20F00429\",\"20F00430\",\"20F00551\",\"20F00634\",\"20F00663\",\"20F00664\",\"20F00664\",\"20F00798\",\"20F00844\",\"20F00845\",\"20F00846\",\"20F00873\",\"20F00315\",\"20F00544\",\"20F00755\",\"20F00785\",\"20F00841\",\"20F00277\",\"20F00277\",\"20F00387\",\"20F00412\",\"20F00525\",\"20F00579\",\"20F00584\",\"20F00658\",\"20F00668\",\"20F00738\",\"20F00885\",\"20F00815\",\"20F00815\",\"20F00823\",\"20F00754\",\"20F00483\",\"20F00620\",\"20F00621\",\"20F00746\",\"20F00747\",\"20F00881\",\"20F00379\",\"20F00520\",\"20F00757\",\"20F00627\",\"20F00695\",\"20F00696\",\"20F00697\",\"20F00855\",\"20F00339\",\"20F00340\",\"20F00762\",\"20F00856\",\"20F00857\",\"20F00001\",\"20F00016\",\"20F00103\",\"20F00117\",\"20F00239\",\"20F00248\",\"20F00249\",\"20F00310\",\"20F00420\",\"20F00495\",\"20F00496\",\"20F00565\",\"20F00566\",\"20F00725\",\"20F00728\",\"20F00729\",\"20F00818\",\"20F00750\",\"20F00858\",\"20F00691\",\"20F00805\",\"20F00456\",\"20F00632\",\"20F00686\",\"20F00753\",\"20F00869\",\"20F00794\",\"20F00006\",\"20F00105\",\"20F00656\",\"19F01046\",\"20F00506\",\"20F00638\",\"20F00699\",\"20F00699\",\"20F00733\",\"20F00760\",\"20F00705\",\"20F00705\",\"20F00833\",\"20F00787\",\"20F00876\",\"20F00576\",\"20F00576\",\"20F00665\",\"20F00665\",\"20F00743\",\"20F00879\",\"20F00618\",\"20F00678\",\"20F00759\",\"20F00859\",\"20F00652\",\"20F00653\",\"20F00726\",\"20F00727\",\"20F00817\",\"20F00629\",\"20F00629\",\"20F00690\",\"20F00789\",\"20F00789\",\"20F00884\",\"20F00751\",\"20F00863\",\"19F01055\",\"20F00054\",\"20F00636\",\"20F00828\",\"20F00419\",\"20F00490\",\"20F00711\",\"20F00646\",\"20F00647\",\"20F00648\",\"20F00824\",\"20F00065\",\"20F00148\",\"20F00271\",\"20F00375\",\"20F00472\",\"20F00541\",\"20F00720\",\"20F00827\",\"20F00814\",\"20F00721\",\"19F00269\",\"19F00326\",\"19F00615\",\"20F00843\",\"20F00630\",\"20F00681\",\"20F00780\",\"20F00780\",\"20F00780\",\"20F00842\",\"17F01114\",\"17F01114\",\"20F00660\",\"20F00660\",\"20F00742\",\"20F00742\",\"20F00888\",\"20F00888\",\"20F00558\",\"20F00580\",\"20F00667\",\"20F00799\",\"20F00867\",\"20F00585\",\"20F00739\",\"17F00973\",\"20F00871\",\"20F00871\",\"17F00648\",\"18F01143\",\"20F00034\",\"20F00080\",\"20F00151\",\"20F00590\",\"20F00631\",\"20F00680\",\"20F00717\",\"20F00790\",\"20F00849\",\"20F00851\",\"20F00853\",\"20F00581\",\"20F00719\",\"20F00776\",\"20F00744\",\"20F00744\",\"20F00889\",\"20F00702\",\"20F00806\",\"20F00878\",\"20F00592\",\"20F00682\",\"20F00777\",\"20F00852\",\"20F00722\",\"20F00587\",\"20F00692\",\"20F00692\",\"20F00692\",\"20F00788\",\"20F00872\",\"17F00866\",\"17F00866\",\"20F00854\",\"20F00469\",\"20F00670\",\"20F00671\",\"20F00671\",\"20F00772\",\"20F00773\",\"20F00773\",\"20F00874\",\"20F00875\",\"19F00828\",\"19F00956\",\"19F00956\",\"19F01056\",\"18F00397\",\"19F00598\",\"19F00831\",\"20F00718\",\"20F00768\",\"20F00848\",\"18F00722\",\"18F00849\",\"18F00971\",\"19F00051\",\"16F00291\",\"16F00311\",\"16F00439\",\"16F00440\",\"20F00126\",\"20F00732\",\"20F00831\",\"20F00835\",\"17F00117\",\"17F00523\",\"17F01004\",\"20F00840\",\"20F00516\",\"20F00669\",\"20F00740\",\"20F00861\",\"20F00883\",\"20F00883\",\"20F00591\",\"20F00591\",\"20F00704\",\"20F00767\",\"20F00767\",\"20F00847\",\"20F00865\",\"17F01002\",\"18F00742\",\"18F01032\",\"19F00140\",\"19F00776\",\"19F00850\",\"19F00935\",\"19F01066\",\"20F00055\",\"20F00295\",\"20F00594\",\"20F00804\",\"20F00748\",\"20F00864\",\"20F00460\",\"20F00547\",\"20F00642\",\"20F00091\",\"20F00091\",\"20F00137\",\"20F00137\",\"20F00392\",\"20F00392\",\"19F00102\",\"18F00333\",\"18F00333\",\"18F00333\",\"18F01039\",\"18F01039\",\"19F00491\",\"20F00104\",\"20F00501\",\"20F00796\",\"20F00836\",\"20F00666\",\"20F00797\",\"20F00810\",\"20F00820\",\"20F00830\",\"20F00839\",\"20F00601\",\"20F00687\",\"20F00749\",\"20F00877\",\"20F00860\",\"18F00494\",\"18F00522\",\"18F00594\",\"18F00594\",\"18F00737\",\"18F00737\",\"18F00867\",\"18F00953\",\"18F00953\",\"18F01137\",\"20F00511\",\"20F00511\",\"20F00614\",\"20F00614\",\"20F00673\",\"20F00673\",\"20F00795\",\"20F00795\",\"20F00887\",\"18F00840\",\"18F00840\",\"18F01016\",\"18F01016\",\"18F01016\",\"18F01200\",\"20F00528\",\"20F00530\",\"20F00643\",\"20F00644\",\"20F00712\",\"20F00735\",\"20F00736\",\"20F00737\",\"20F00819\",\"20F00517\",\"20F00613\",\"20F00791\",\"20F00791\",\"20F00451\",\"20F00582\",\"20F00685\",\"20F00775\",\"20F00331\",\"20F00765\",\"20F00766\",\"19F00772\",\"19F00837\",\"19F00963\",\"19F01065\",\"20F00092\",\"20F00202\",\"20F00408\",\"20F00510\",\"20F00615\",\"20F00802\",\"20F00589\",\"20F00701\",\"20F00769\",\"20F00180\",\"20F00180\",\"20F00834\",\"20F00716\",\"20F00716\",\"20F00886\",\"20F00617\",\"20F00684\",\"20F00801\",\"20F00850\",\"20F00118\",\"20F00573\",\"20F00574\",\"20F00808\",\"20F00829\",\"20F00659\",\"20F00882\",\"20F00715\",\"20F00866\",\"20F00734\")"
                    + " group by fct.`num_facture` ORDER BY num_facture desc ";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModelColsultReglementComptable(rs);

            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {

                /*   Object PU1 = df.getValueAt(j, 4);
                if (PU1 == null) {
                    PU1 = 0.0;
                }
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU1.toString())), j, 4);
                 */
                Object PU2 = df.getValueAt(j, 7);
                if (PU2 == null) {
                    PU2 = 0.0;
                }
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU2.toString())), j, 7);

                Object PU = df.getValueAt(j, 8);
                if (PU == null) {
                    PU = 0.0;
                }
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 8);

                // begin condition 1% et décalage timbre 0.600 
                /*   if (df.getValueAt(j, 5).toString().equalsIgnoreCase("ESPECE")) {
                    if (String.valueOf(Double.parseDouble(df.getValueAt(j, 7).toString()) - Double.parseDouble(df.getValueAt(j, 8).toString())).contains("0.60")) {
                        df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble("0.600")), j, 7);
                        df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble("0.000")), j, 8);
                    }
                } else if (df.getValueAt(j, 5).toString().equalsIgnoreCase("CHEQUE") || df.getValueAt(j, 5).toString().equalsIgnoreCase("EFFET")) {
                    if (Double.parseDouble(df.getValueAt(j, 7).toString()) >= 1000) {
                        DecimalFormat df_format = new DecimalFormat("0.00");

                        String one_percent = Config.Commen_Proc.formatDouble(Double.parseDouble(df.getValueAt(j, 7).toString()) * 0.01);
                        one_percent = one_percent.substring(0, one_percent.length() - 1);
                        one_percent = one_percent.substring(0, one_percent.length() - 1);
                        String ss = "";
                        ss = df.getValueAt(j, 8).toString().substring(0, df.getValueAt(j, 8).toString().length() - 1);
                        ss = ss.substring(0, ss.length() - 1);
                        if (one_percent.equals(ss)) {
                            df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble("0.000")), j, 8);
                        }
                    }
                } else if (df.getValueAt(j, 5).toString().equalsIgnoreCase("VIREMENT")) {
                    if (df.getValueAt(j, 1).toString().equals("FIGEAC  AERO")) {
                        DecimalFormat df_format = new DecimalFormat("0.00");

                        String one_percent = Config.Commen_Proc.formatDouble(Double.parseDouble(df.getValueAt(j, 7).toString()) * 0.01);
                        one_percent = one_percent.substring(0, one_percent.length() - 1);
                        one_percent = one_percent.substring(0, one_percent.length() - 1);
                        String ss = "";
                        ss = df.getValueAt(j, 8).toString().substring(0, df.getValueAt(j, 8).toString().length() - 1);
                        ss = ss.substring(0, ss.length() - 1);
                        if (one_percent.equals(ss)) {
                            df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble("0.000")), j, 8);
                        }
                    } else {

                        if (Double.parseDouble(df.getValueAt(j, 7).toString()) >= 1000) {
                            DecimalFormat df_format = new DecimalFormat("0.00");

                            String one_percent = Config.Commen_Proc.formatDouble(Double.parseDouble(df.getValueAt(j, 7).toString()) * 0.01);
                            one_percent = one_percent.substring(0, one_percent.length() - 1);
                            one_percent = one_percent.substring(0, one_percent.length() - 1);
                            String ss = "";
                            ss = df.getValueAt(j, 8).toString().substring(0, df.getValueAt(j, 8).toString().length() - 1);
                            ss = ss.substring(0, ss.length() - 1);
                            if (one_percent.equals(ss)) {
                                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble("0.000")), j, 8);
                            }
                        }

                    }
                } // end condition 1% et décalage timbre 0.600 
                 */
            }
            table.setModel(df);
            return data;
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
        return data;
    }

    public Vector<Vector<Object>> afficherConsumtationFactureImpayeRegion(JTable table, String FromDate, String ToDate, String FromDate_fact, String ToDate_fact, String FromMontant, String ToMontant, String id_commercial, String NomClient, String type_filtre, String type_filtre_paid, String type_reglement, String num_facture, String id_zone_geo, String nom_ville) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql = "";

            sql = "SELECT  d.`Num_facture` as num_fact, if(c.nom is null , '',c.nom) as `Client`,DATE_FORMAT(d.`date_facture`,'%d/%m/%Y') as date,d.`TTC` as ttc,"
                    + "if(sum(if(r.facture_avoir='Facture',r.regle,0)) is NULL, 0, sum(if(r.facture_avoir='Facture',r.regle,0))) as \"regle\", "
                    + "(d.`TTC`-if(sum(if(r.statut=0,0,if(r.facture_avoir='Facture',r.regle,0))) is NULL, 0, sum(if(r.statut=0,0,if(r.facture_avoir='Facture',r.regle,0))))) as \"Impaye\", if(comm.nom is null , '',comm.nom) as \"Commercial\" "
                    + "FROM `Facture` d "
                    + "left join client c on (d.id_client=c.numero_client) "
                    + "left join reglement r on d.Num_facture = r.id_facture "
                    + "left join pourcentagebonus pb on pb.id_client=c.numero_client "
                    + "left join commercial comm on pb.id_commercial=comm.id "
                    + "where d.statut=1 and d.reglement in ('Semi','Non')";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and d.id_client <> 0 ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and d.id_client = 0 ";
                sql = sql + FiltreClauseFrom;
            }

            String DateClauseFrom = "";
            if (!(FromDate_fact.isEmpty())) {
                DateClauseFrom = " and d.date_facture >= '" + FromDate_fact + "'";
                sql = sql + DateClauseFrom;
            }

            String idCommercialClause = "";
            if (!(id_commercial.isEmpty())) {
                idCommercialClause = " and pb.id_commercial  = '" + id_commercial + "'";
                sql = sql + idCommercialClause;

            }

            String DateClauseTo = "";
            if (!(ToDate_fact.isEmpty())) {
                DateClauseTo = " and d.date_facture <= '" + ToDate_fact + "'";
                sql = sql + DateClauseTo;
            }

            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(num_facture.isEmpty())) {
                NumDevisClause = " and d.Num_facture  in (" + num_facture + ")";
                sql = sql + NumDevisClause;

            }

            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.numero_client  = '" + NomClient + "'";
                sql = sql + ClientClause;

            }
            String zoneClause = "";
            if (!(id_zone_geo.isEmpty())) {
                ClientClause = " and c.zone_geo  = '" + id_zone_geo + "'";
                sql = sql + ClientClause;
            }
            if (!(nom_ville.isEmpty())) {
                ClientClause = " and c.zone_geo  like '%" + nom_ville + "%'";
                sql = sql + ClientClause;
            }

            sql += "  GROUP by d.Num_facture ORDER BY d.date_facture,c.nom DESC";

            pst = conn.prepareStatement(sql);

            Double TotalTTC = 0.0;
            Double TotalImpaye = 0.0;
            Double Totalregle = 0.0;
            Double TotalTVA = 0.0;
            rs = pst.executeQuery();

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Client");
            columnNames.add("Num Facture");

            columnNames.add("Date Facture");
            columnNames.add("Total TTC");
            columnNames.add("Reglé");
            columnNames.add("Impayée");
            columnNames.add("Commercial");
            columnNames.add("CHECK");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel df = new DefaultTableModel(data1, columnNames);
            //   DefaultTableModel df = buildTableModelColsultFactureImpayeRegion(rs);
            data = new Vector<Vector<Object>>();
            int startAdd = 0;
            int totalGlobal = 0;
            int qteTotalClient = 0;
            String nomclient = "";
            String last_clietnt = "";
            int i = 0;
            while (rs.next()) {

                Vector<Object> vector = new Vector<Object>();

                //   Object LigneData[] = new Object[7];
                if (i == 0) {
                    vector.add(rs.getString("Client"));
                } else if (rs.getString("Client").equals(last_clietnt)) {
                    vector.add("");
                } else {
                    vector.add(rs.getString("Client"));
                }
                last_clietnt = rs.getString("Client");

                //  nomclient = rs.getString("Client");
                // LigneData[0] = nomclient;
                vector.add(rs.getString("num_fact"));
                vector.add(rs.getString("date"));

                vector.add(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ttc"))));
                vector.add(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("regle"))));
                vector.add(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("Impaye"))));
                vector.add(rs.getString("Commercial"));

                // df.insertRow(startAdd, LigneData);
                TotalTTC += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ttc"))));
                Totalregle += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("regle"))));
                TotalImpaye += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("Impaye"))));

                // startAdd++;
                //    df.insertRow(startAdd, LigneData);
                //  qteTotalClient += Integer.valueOf(rs.getString("sum(lb.qte)"));
                startAdd++;
                vector.add(false);
                data.add(vector);
                i++;
            }

            /*  Object LigneData[] = new Object[7];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = ("Total : " + Config.Commen_Proc.formatDouble(Double.valueOf(TotalImpaye)) + "").toString();

            LigneData[6] = "";
            totalGlobal += TotalImpaye;
            df.insertRow(startAdd, LigneData);
            Object LigneDatas[] = new Object[7];

            LigneDatas[0] = "";
            LigneDatas[1] = "";
            LigneDatas[2] = "";
            LigneDatas[3] = "";
            LigneDatas[4] = "";
            LigneDatas[5] = "";
            LigneDatas[6] = "";

            df.insertRow(startAdd + 1, LigneDatas);*/
 /*  }
            Object LigneData[] = new Object[7];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "Total tous les clients : " + Config.Commen_Proc.formatDouble(Double.valueOf(totalGlobal)) + "";
            LigneData[6] = "";

            df.insertRow(startAdd + 1, LigneData);*/
            return data;
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
        return data;
    }

    public Vector<Vector<Object>> afficherConsumtationReglementComptabledetail(JTable table, String FromDate, String ToDate, String FromDate_fact, String ToDate_fact, String FromMontant, String ToMontant, String id_commercial, String NomClient, String type_filtre, String type_filtre_paid, String type_reglement, String num_facture) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;//DATE_FORMAT(r.`date_regelemnt`,'%d/%m/%Y')
            // r.id,fct.`num_facture`, dt_echeance,r.`type_reglement`,r.`regle`,TTC,r.`banque`, r.`num_cheque`, r.`passager` ,r.statut
            sql = "SELECT r.id,c.nom,/* f.Nom ,comm.nom , */ fct.`num_facture`, fct.date_facture , DATE_FORMAT(r.`date_echeance`,'%d/%m/%Y') as dt_echeance, /*DATE_FORMAT(r.`date_echeance`,'%d/%m/%Y'), */ r.`type_reglement`, "
                    + "if( r.`regle` is null ,0,r.`regle`) as regle,"
                    + " /*IF((r.isClient_Four_Passager = 'Client'), if(fct.TTC is null,0, fct.TTC)  , if(fct_a.TTC is null ,0,fct_a.TTC)) as TTC */"
                    + " fct.TTC as TTC , "
                    //   + " IF((r.isClient_Four_Passager = 'Client'), (fct.TTC- r.regle) , (fct_a.TTC- r.regle)) as Restant "
                    + " IF((r.isClient_Four_Passager = 'Client'), if((select count(*) from reglement r2 where r2.id_facture=fct.num_facture and r2.statut=1 and upper(r2.type_reglement) = 'AVOIR') > 0,(fct.TTC - (select sum(r1.regle) from reglement r1 where r1.id_facture=fct.num_facture and r1.statut=1 and upper(r1.type_reglement)='AVOIR')), (fct.TTC - (select sum(r1.regle) from reglement r1 where r1.id_facture=fct.num_facture and r1.statut=1))) , (fct_a.TTC- sum(r.regle))) as rst  "
                    //  + " , if((IF((r.isClient_Four_Passager = 'Client'), fct.TTC , fct_a.TTC) - (select if(sum(regle) is null,0,sum(regle)) from reglement where id_facture = fct.num_facture and statut = 1 )) is null,0, (IF((r.isClient_Four_Passager = 'Client'), fct.TTC , fct_a.TTC) - (select if(sum(regle) is null,0,sum(regle)) from reglement where id_facture = fct.num_facture and statut = 1)))as rst"
                    + ", r.`banque`, r.`num_cheque`, r.`passager` ,r.statut "
                    + "FROM facture fct "
                    + "left outer join `reglement` r on (r.`id_facture`=fct.num_facture) "
                    + "left join client c on (fct.`id_client`=c.numero_Client) "
                    + "left join pourcentagebonus pb on  r.`id_client` = pb.id_client "
                    + "left join commercial comm on pb.id_commercial = comm.id "
                    + "left join fournisseur f on (r.`id_fournisseur`=f.id) "
                    + "left join facture_achat fct_a on (r.`id_facture`=fct_a.num_facture_achat) WHERE 1  and (r.statut = 1 or r.statut is null) ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and r.isClient_Four_Passager ='Client' ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and r.isClient_Four_Passager ='Passager' ";
                sql = sql + FiltreClauseFrom;
            }
            if ((type_filtre.equals("four"))) {
                FiltreClauseFrom = " and r.isClient_Four_Passager ='Fournisseur' ";
                sql = sql + FiltreClauseFrom;
            }
            String Type_reglementClause = "";
            if (!(type_reglement.equals("-"))) {
                Type_reglementClause = " and r.type_reglement like '" + type_reglement + "'";
                sql = sql + Type_reglementClause;
            }
            /*  if ((!type_filtre_paid.equals("all"))) {
                FiltreClauseFrom = " and r. ='" + type_filtre_paid + "' ";
                sql = sql + FiltreClauseFrom;
            }*/
            String DateClauseFrom_fact = "";
            if (!(FromDate_fact.isEmpty())) {
                DateClauseFrom_fact = " and fct.date_facture >= '" + FromDate_fact + "'";
                sql = sql + DateClauseFrom_fact;
            }
            String DateClauseTo_fact = "";
            if (!(ToDate_fact.isEmpty())) {
                DateClauseTo_fact = " and fct.date_facture <= '" + ToDate_fact + "'";
                sql = sql + DateClauseTo_fact;
            }

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and r.date_echeance >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and r.date_echeance <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }
            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and fct.TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and fct.TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(num_facture.isEmpty())) {
                NumDevisClause = " and fct.`num_facture`  in (" + num_facture + ")";
                sql = sql + NumDevisClause;
            }

            String CommercialClause = "";
            if (!(id_commercial.isEmpty())) {
                CommercialClause = " and pb.id_commercial  = " + id_commercial;
                sql = sql + CommercialClause;
            }

            /* String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and fct.id_client = '" + NomClient + "' ";
                sql = sql + ClientClause;

            }*/
            sql += ""
                    //  + "and  fct.`num_facture` in (\"18F00511\",\"18F00598\",\"18F00841\",\"18F00961\",\"18F00976\",\"18F00976\",\"18F00979\",\"18F00993\",\"18F01047\",\"18F01055\",\"18F01061\",\"18F01073\",\"18F01164\",\"19F00019\",\"19F00114\",\"19F00115\",\"19F00128\",\"19F00139\",\"19F00139\",\"19F00139\",\"19F00224\",\"19F00344\",\"19F00545\",\"19F00565\",\"19F00671\",\"19F00757\",\"19F00764\",\"19F00842\",\"19F00957\",\"19F00975\",\"19F00999\",\"19F01048\",\"19F01058\",\"19F01062\",\"20F00005\",\"20F00010\",\"20F00022\",\"20F00111\",\"20F00113\",\"20F00135\",\"20F00158\",\"20F00222\",\"20F00255\",\"20F00319\",\"20F00405\",\"20F00445\",\"20F00445\",\"20F00457\",\"20F00493\",\"20F00494\",\"20F00508\",\"20F00596\",\"20F00599\",\"20F00604\",\"20F00694\",\"20F00700\",\"20F00703\",\"20F00758\",\"20F00781\",\"20F00782\",\"20F00158\",\"20F00222\",\"20F00255\",\"20F00319\",\"20F00405\",\"20F00445\",\"20F00445\",\"20F00457\",\"20F00493\",\"20F00494\",\"20F00508\",\"20F00596\",\"20F00599\",\"20F00604\",\"20F00694\",\"20F00700\",\"20F00703\",\"20F00758\",\"20F00781\",\"20F00782\",\"20F00783\",\"20F00784\",\"20F00784\",\"20F00785\",\"20F00786\",\"20F00800\",\"20F00803\",\"20F00807\",\"20F00809\",\"20F00812\",\"20F00813\",\"20F00822\",\"20F00826\",\"20F00832\",\"20F00837\",\"20F00838\",\"20F00862\",\"20F00870\",\"20F00890\",\"20F00891\",\"20F00892\",\"20F00792\",\"20F00793\",\"20F00868\",\"20F00731\",\"19F00660\",\"19F00767\",\"19F00980\",\"18F01201\",\"19F00612\",\"19F00691\",\"19F00710\",\"19F00921\",\"19F00988\",\"19F00994\",\"20F00081\",\"20F00097\",\"20F00128\",\"20F00186\",\"20F00187\",\"20F00305\",\"20F00347\",\"20F00348\",\"20F00608\",\"20F00650\",\"20F00661\",\"20F00771\",\"20F00880\",\"19F00380\",\"19F00538\",\"19F00743\",\"20F00214\",\"20F00654\",\"20F00274\",\"20F00343\",\"20F00344\",\"20F00345\",\"20F00429\",\"20F00430\",\"20F00551\",\"20F00634\",\"20F00663\",\"20F00664\",\"20F00664\",\"20F00798\",\"20F00844\",\"20F00845\",\"20F00846\",\"20F00873\",\"20F00315\",\"20F00544\",\"20F00755\",\"20F00785\",\"20F00841\",\"20F00277\",\"20F00277\",\"20F00387\",\"20F00412\",\"20F00525\",\"20F00579\",\"20F00584\",\"20F00658\",\"20F00668\",\"20F00738\",\"20F00885\",\"20F00815\",\"20F00815\",\"20F00823\",\"20F00754\",\"20F00483\",\"20F00620\",\"20F00621\",\"20F00746\",\"20F00747\",\"20F00881\",\"20F00379\",\"20F00520\",\"20F00757\",\"20F00627\",\"20F00695\",\"20F00696\",\"20F00697\",\"20F00855\",\"20F00339\",\"20F00340\",\"20F00762\",\"20F00856\",\"20F00857\",\"20F00001\",\"20F00016\",\"20F00103\",\"20F00117\",\"20F00239\",\"20F00248\",\"20F00249\",\"20F00310\",\"20F00420\",\"20F00495\",\"20F00496\",\"20F00565\",\"20F00566\",\"20F00725\",\"20F00728\",\"20F00729\",\"20F00818\",\"20F00750\",\"20F00858\",\"20F00691\",\"20F00805\",\"20F00456\",\"20F00632\",\"20F00686\",\"20F00753\",\"20F00869\",\"20F00794\",\"20F00006\",\"20F00105\",\"20F00656\",\"19F01046\",\"20F00506\",\"20F00638\",\"20F00699\",\"20F00699\",\"20F00733\",\"20F00760\",\"20F00705\",\"20F00705\",\"20F00833\",\"20F00787\",\"20F00876\",\"20F00576\",\"20F00576\",\"20F00665\",\"20F00665\",\"20F00743\",\"20F00879\",\"20F00618\",\"20F00678\",\"20F00759\",\"20F00859\",\"20F00652\",\"20F00653\",\"20F00726\",\"20F00727\",\"20F00817\",\"20F00629\",\"20F00629\",\"20F00690\",\"20F00789\",\"20F00789\",\"20F00884\",\"20F00751\",\"20F00863\",\"19F01055\",\"20F00054\",\"20F00636\",\"20F00828\",\"20F00419\",\"20F00490\",\"20F00711\",\"20F00646\",\"20F00647\",\"20F00648\",\"20F00824\",\"20F00065\",\"20F00148\",\"20F00271\",\"20F00375\",\"20F00472\",\"20F00541\",\"20F00720\",\"20F00827\",\"20F00814\",\"20F00721\",\"19F00269\",\"19F00326\",\"19F00615\",\"20F00843\",\"20F00630\",\"20F00681\",\"20F00780\",\"20F00780\",\"20F00780\",\"20F00842\",\"17F01114\",\"17F01114\",\"20F00660\",\"20F00660\",\"20F00742\",\"20F00742\",\"20F00888\",\"20F00888\",\"20F00558\",\"20F00580\",\"20F00667\",\"20F00799\",\"20F00867\",\"20F00585\",\"20F00739\",\"17F00973\",\"20F00871\",\"20F00871\",\"17F00648\",\"18F01143\",\"20F00034\",\"20F00080\",\"20F00151\",\"20F00590\",\"20F00631\",\"20F00680\",\"20F00717\",\"20F00790\",\"20F00849\",\"20F00851\",\"20F00853\",\"20F00581\",\"20F00719\",\"20F00776\",\"20F00744\",\"20F00744\",\"20F00889\",\"20F00702\",\"20F00806\",\"20F00878\",\"20F00592\",\"20F00682\",\"20F00777\",\"20F00852\",\"20F00722\",\"20F00587\",\"20F00692\",\"20F00692\",\"20F00692\",\"20F00788\",\"20F00872\",\"17F00866\",\"17F00866\",\"20F00854\",\"20F00469\",\"20F00670\",\"20F00671\",\"20F00671\",\"20F00772\",\"20F00773\",\"20F00773\",\"20F00874\",\"20F00875\",\"19F00828\",\"19F00956\",\"19F00956\",\"19F01056\",\"18F00397\",\"19F00598\",\"19F00831\",\"20F00718\",\"20F00768\",\"20F00848\",\"18F00722\",\"18F00849\",\"18F00971\",\"19F00051\",\"16F00291\",\"16F00311\",\"16F00439\",\"16F00440\",\"20F00126\",\"20F00732\",\"20F00831\",\"20F00835\",\"17F00117\",\"17F00523\",\"17F01004\",\"20F00840\",\"20F00516\",\"20F00669\",\"20F00740\",\"20F00861\",\"20F00883\",\"20F00883\",\"20F00591\",\"20F00591\",\"20F00704\",\"20F00767\",\"20F00767\",\"20F00847\",\"20F00865\",\"17F01002\",\"18F00742\",\"18F01032\",\"19F00140\",\"19F00776\",\"19F00850\",\"19F00935\",\"19F01066\",\"20F00055\",\"20F00295\",\"20F00594\",\"20F00804\",\"20F00748\",\"20F00864\",\"20F00460\",\"20F00547\",\"20F00642\",\"20F00091\",\"20F00091\",\"20F00137\",\"20F00137\",\"20F00392\",\"20F00392\",\"19F00102\",\"18F00333\",\"18F00333\",\"18F00333\",\"18F01039\",\"18F01039\",\"19F00491\",\"20F00104\",\"20F00501\",\"20F00796\",\"20F00836\",\"20F00666\",\"20F00797\",\"20F00810\",\"20F00820\",\"20F00830\",\"20F00839\",\"20F00601\",\"20F00687\",\"20F00749\",\"20F00877\",\"20F00860\",\"18F00494\",\"18F00522\",\"18F00594\",\"18F00594\",\"18F00737\",\"18F00737\",\"18F00867\",\"18F00953\",\"18F00953\",\"18F01137\",\"20F00511\",\"20F00511\",\"20F00614\",\"20F00614\",\"20F00673\",\"20F00673\",\"20F00795\",\"20F00795\",\"20F00887\",\"18F00840\",\"18F00840\",\"18F01016\",\"18F01016\",\"18F01016\",\"18F01200\",\"20F00528\",\"20F00530\",\"20F00643\",\"20F00644\",\"20F00712\",\"20F00735\",\"20F00736\",\"20F00737\",\"20F00819\",\"20F00517\",\"20F00613\",\"20F00791\",\"20F00791\",\"20F00451\",\"20F00582\",\"20F00685\",\"20F00775\",\"20F00331\",\"20F00765\",\"20F00766\",\"19F00772\",\"19F00837\",\"19F00963\",\"19F01065\",\"20F00092\",\"20F00202\",\"20F00408\",\"20F00510\",\"20F00615\",\"20F00802\",\"20F00589\",\"20F00701\",\"20F00769\",\"20F00180\",\"20F00180\",\"20F00834\",\"20F00716\",\"20F00716\",\"20F00886\",\"20F00617\",\"20F00684\",\"20F00801\",\"20F00850\",\"20F00118\",\"20F00573\",\"20F00574\",\"20F00808\",\"20F00829\",\"20F00659\",\"20F00882\",\"20F00715\",\"20F00866\",\"20F00734\")"
                    + " GROUP BY r.id ORDER BY num_facture desc ";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Vector<String> columnNames;
            columnNames = new Vector<String>();
            columnNames.add("Client");

            columnNames.add("Num Facture");

            columnNames.add("Total TTC");
            columnNames.add("Montant Restant");

            //   columnNames.add("id");
            //   columnNames.add("Fournisseur");
            //   columnNames.add("Commercial");
            // columnNames.add("Date Facture");
            columnNames.add("Date Echéance");
            columnNames.add("Type Réglement");
            columnNames.add("Montant Relge");
            //  columnNames.add("Montant Restant");
            columnNames.add("Banque");
            columnNames.add("Num Cheque");
            columnNames.add("Passager");
            //  columnNames.add("Statut");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            DefaultTableModel df = model;
            table.setModel(df);
            int startAdd = 0;
            String num_fact = "";
            String num_fact_old = "";

            String rst = "";
            String rst_old = "";

            String ttc = "";
            String ttc_old = "";

            double total_ttc = 0.0;
            double total_ttc_regle = 0.0;

            while (rs.next()) {

                Object LigneData[] = new Object[10];
                boolean isDouble = false;

                LigneData[0] = rs.getString("c.nom");

                num_fact = rs.getString("num_facture");
                if (num_fact.equals(num_fact_old)) {
                    LigneData[1] = "";
                    isDouble = true;
                } else {
                    LigneData[1] = num_fact;

                }
                num_fact_old = rs.getString("num_facture");

                ttc = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("TTC")));
                if (isDouble) {
                    LigneData[2] = "";

                } else {
                    LigneData[2] = ttc;
                    total_ttc += Double.valueOf(ttc);

                }
                //   ttc_old = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("TTC")));

                rst = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("rst")));
                if (isDouble) {
                    LigneData[3] = "";

                } else {
                    LigneData[3] = rst;

                }
                //   rst_old = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("rst")));

                //   LigneData[1] = rs.getString("r.id");
                LigneData[4] = rs.getString("dt_echeance");

                LigneData[5] = rs.getString("r.type_reglement");
                //  String HTAvoir = rs.getString("sum(av.total_ht)") == null ? "0" : rs.getString("sum(av.total_ht)");
                total_ttc_regle += Double.valueOf(rs.getString("regle"));
                LigneData[6] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("regle")));
                LigneData[7] = rs.getString("r.banque");
                LigneData[8] = rs.getString("r.num_cheque");
                LigneData[9] = rs.getString("r.passager");

                df.insertRow(startAdd, LigneData);

                startAdd++;
            }
            Object LigneData[] = new Object[10];
            LigneData[0] = "";
            LigneData[1] = "Total : ";
            LigneData[2] = "" + Config.Commen_Proc.formatDouble(total_ttc);
            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "Total : ";
            LigneData[6] = "" + Config.Commen_Proc.formatDouble(total_ttc_regle);
            LigneData[7] = "";
            LigneData[8] = "";
            LigneData[9] = "";
            df.insertRow((startAdd++), LigneData);

//  DefaultTableModel df1 = buildTableModelColsultReglementComptableDetail(rs);
            table.setModel(df);

            return dataDetail;
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
        return data;
    }

    public Vector<Vector<Object>> afficherConsumtationFactureImpayeRegiondetail(JTable table, String FromDate, String ToDate, String FromDate_fact, String ToDate_fact, String FromMontant, String ToMontant, String id_commercial, String NomClient, String type_filtre, String type_filtre_paid, String type_reglement, String num_facture) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql = "";

            sql = "SELECT  d.`Num_facture` as num_fact, if(c.nom is null , '',c.nom) as `Client`,DATE_FORMAT(d.`date_facture`,'%d/%m/%Y') as date,d.`TTC` as ttc,"
                    + "if(sum(if(r.facture_avoir='Facture',r.regle,0)) is NULL, 0, sum(if(r.facture_avoir='Facture',r.regle,0))) as \"regle\", "
                    + "(d.`TTC`-if(sum(if(r.statut=0,0,if(r.facture_avoir='Facture',r.regle,0))) is NULL, 0, sum(if(r.statut=0,0,if(r.facture_avoir='Facture',r.regle,0))))) as \"Impaye\", if(comm.nom is null , '',comm.nom) as \"Commercial\" "
                    + "FROM `Facture` d "
                    + "left join client c on (d.id_client=c.numero_client) "
                    + "left join reglement r on d.Num_facture = r.id_facture "
                    + "left join pourcentagebonus pb on pb.id_client=c.numero_client "
                    + "left join commercial comm on pb.id_commercial=comm.id "
                    + "where d.statut=1  and d.reglement in ('Semi','Non')";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and d.id_client <> 0 ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and d.id_client = 0 ";
                sql = sql + FiltreClauseFrom;
            }

            String DateClauseFrom = "";
            if (!(FromDate_fact.isEmpty())) {
                DateClauseFrom = " and d.date_facture >= '" + FromDate_fact + "'";
                sql = sql + DateClauseFrom;
            }

            String idCommercialClause = "";
            if (!(id_commercial.isEmpty())) {
                idCommercialClause = " and pb.id_commercial  = '" + id_commercial + "'";
                sql = sql + idCommercialClause;

            }

            String DateClauseTo = "";
            if (!(ToDate_fact.isEmpty())) {
                DateClauseTo = " and d.date_facture <= '" + ToDate_fact + "'";
                sql = sql + DateClauseTo;
            }

            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(num_facture.isEmpty())) {
                NumDevisClause = " and d.`num_facture`  in (" + num_facture + ")";
                sql = sql + NumDevisClause;
            }
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.numero_client  = '" + NomClient + "'";
                sql = sql + ClientClause;

            }
            sql += "  GROUP by d.Num_facture ORDER BY d.date_facture,c.nom DESC";

            pst = conn.prepareStatement(sql);

            Double TotalTTC = 0.0;
            Double TotalImpaye = 0.0;
            Double Totalregle = 0.0;
            Double TotalTVA = 0.0;
            rs = pst.executeQuery();

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Client");
            columnNames.add("Num Facture");

            columnNames.add("Date Facture");
            columnNames.add("Total TTC");
            columnNames.add("Reglé");
            columnNames.add("Impayée");
            columnNames.add("Commercial");
            columnNames.add("CHECK");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel df = new DefaultTableModel(data1, columnNames);
            //   DefaultTableModel df = buildTableModelColsultFactureImpayeRegion(rs);
            data = new Vector<Vector<Object>>();
            int startAdd = 0;
            int totalGlobal = 0;
            int qteTotalClient = 0;
            String nomclient = "";
            String last_clietnt = "";
            int i = 0;
            while (rs.next()) {

                Vector<Object> vector = new Vector<Object>();

                //   Object LigneData[] = new Object[7];
                if (i == 0) {
                    vector.add(rs.getString("Client"));
                } else if (rs.getString("Client").equals(last_clietnt)) {
                    vector.add("");
                } else {
                    vector.add(rs.getString("Client"));
                }
                last_clietnt = rs.getString("Client");

                //  nomclient = rs.getString("Client");
                // LigneData[0] = nomclient;
                vector.add(rs.getString("num_fact"));
                vector.add(rs.getString("date"));

                vector.add(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ttc"))));
                vector.add(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("regle"))));
                vector.add(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("Impaye"))));
                vector.add(rs.getString("Commercial"));

                // df.insertRow(startAdd, LigneData);
                TotalTTC += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ttc"))));
                Totalregle += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("regle"))));
                TotalImpaye += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("Impaye"))));

                // startAdd++;
                //    df.insertRow(startAdd, LigneData);
                //  qteTotalClient += Integer.valueOf(rs.getString("sum(lb.qte)"));
                startAdd++;
                vector.add(false);
                data.add(vector);
                i++;
            }

            /*  Object LigneData[] = new Object[7];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = ("Total : " + Config.Commen_Proc.formatDouble(Double.valueOf(TotalImpaye)) + "").toString();

            LigneData[6] = "";
            totalGlobal += TotalImpaye;
            df.insertRow(startAdd, LigneData);
            Object LigneDatas[] = new Object[7];

            LigneDatas[0] = "";
            LigneDatas[1] = "";
            LigneDatas[2] = "";
            LigneDatas[3] = "";
            LigneDatas[4] = "";
            LigneDatas[5] = "";
            LigneDatas[6] = "";

            df.insertRow(startAdd + 1, LigneDatas);*/
 /*  }
            Object LigneData[] = new Object[7];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "Total tous les clients : " + Config.Commen_Proc.formatDouble(Double.valueOf(totalGlobal)) + "";
            LigneData[6] = "";

            df.insertRow(startAdd + 1, LigneData);*/
            //table.setModel(df);
            df = new DefaultTableModel(data, columnNames);
            table.setModel(df);
            return data;
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
        return data;
    }

    static Vector<Vector<Object>> data;
    static Vector<String> columnNamesreg;

    public static DefaultTableModel buildTableModelColsultReglement(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        columnNamesreg = new Vector<String>();
        columnNamesreg.add("id");
        columnNamesreg.add("Client");
        columnNamesreg.add("Fournisseur");
        columnNamesreg.add("Commercial");

        columnNamesreg.add("Num Facture");

        columnNamesreg.add("Date Reglement");
        columnNamesreg.add("Date Echéance");
        columnNamesreg.add("Type Réglement");
        columnNamesreg.add("Montant Relge");
        columnNamesreg.add("Total TTC");
        columnNamesreg.add("Montant Restant");
        columnNamesreg.add("Banque");
        columnNamesreg.add("Num Cheque");
        columnNamesreg.add("Passager");
        // names of columns

        int columnCount = metaData.getColumnCount();
        /*    for (int column = 1; column <= columnCount; column++) {
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

        return new DefaultTableModel(data, columnNamesreg);

    }

    public static DefaultTableModel buildTableModelColsultReglementComptable(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        columnNamesreg = new Vector<String>();
        columnNamesreg.add("id");
        columnNamesreg.add("Client");
        // columnNamesreg.add("Fournisseur");
        // columnNamesreg.add("Commercial");

        columnNamesreg.add("Num Facture");

        columnNamesreg.add("Date Facture");
        columnNamesreg.add("Date Echéance");
        columnNamesreg.add("Type Réglement");
        columnNamesreg.add("Montant Relge");
        columnNamesreg.add("Total TTC");
        columnNamesreg.add("Montant Restant");
        columnNamesreg.add("Banque");
        columnNamesreg.add("Num Cheque");
        columnNamesreg.add("Passager");
        columnNamesreg.add("Statut");
        columnNamesreg.add("CHECK");

        // names of columns
        int columnCount = metaData.getColumnCount();
        /*    for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }*/

        // data of the table
        data = new Vector<Vector<Object>>();

        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            vector.add(false);
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNamesreg);

    }
    static Vector<Vector<Object>> dataDetail;
    static Vector<String> columnNamesregDetail;

    public static DefaultTableModel buildTableModelColsultFactureImpayeRegion(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        columnNamesreg = new Vector<String>();
        columnNamesreg.add("Client");
        columnNamesreg.add("Num Facture");

        columnNamesreg.add("Date Facture");
        columnNamesreg.add("Total TTC");
        columnNamesreg.add("Reglé");
        columnNamesreg.add("Impayée");
        columnNamesreg.add("Commercial");

        // names of columns
        int columnCount = metaData.getColumnCount();
        /*    for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }*/

        // data of the table
        data = new Vector<Vector<Object>>();

        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            vector.add(false);
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNamesreg);

    }

    public static DefaultTableModel buildTableModelColsultReglementComptableDetail(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        columnNamesregDetail = new Vector<String>();
        columnNamesregDetail.add("id");
        //   columnNamesreg.add("Client");
        // columnNamesreg.add("Fournisseur");
        // columnNamesreg.add("Commercial");

        columnNamesregDetail.add("Num Facture");

        //columnNamesreg.add("Date Facture");
        columnNamesregDetail.add("Date Echéance");
        columnNamesregDetail.add("Type Réglement");
        columnNamesregDetail.add("Montant Relge");
        columnNamesregDetail.add("Total TTC");
        //   columnNamesregDetail.add("Montant Restant");
        columnNamesregDetail.add("Banque");
        columnNamesregDetail.add("Num Cheque");
        columnNamesregDetail.add("Passager");
        columnNamesregDetail.add("Statut");
        //   columnNamesregDetail.add("CHECK");

        // names of columns
        int columnCount = metaData.getColumnCount();
        /*    for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }*/

        // data of the table
        dataDetail = new Vector<Vector<Object>>();

        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            //    vector.add(false);
            dataDetail.add(vector);
        }

        return new DefaultTableModel(dataDetail, columnNamesregDetail);

    }

    public void afficherAvoir(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT c.nom as `Client`,d.`Num_avoir`,DATE_FORMAT(d.`date_avoir`,'%d/%m/%Y'),d.`Total_TTC`,passager FROM `avoir` d left join client c on (d.id_client=c.numero_client) where d.statut =1 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_avoir >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_avoir <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }
            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.total_TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_avoir  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom  like '%" + NomClient + "%'";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY d.date_avoir DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
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

    public void afficherAvoirAchat(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String id_four) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT f.nom as `Fournisseur`,d.`Num_avoir_achat`,DATE_FORMAT(d.`date_avoir_achat`,'%d/%m/%Y'),d.`Total_TTC` FROM `avoir_achat` d , fournisseur f where d.id_fournisseur=f.id ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_avoir_achat >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_avoir_achat <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }
            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.total_TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_avoir_achat  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(id_four.isEmpty())) {
                ClientClause = " and d.id_fournisseur  = " + id_four + "";
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
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
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

    public DefaultTableModel afficherBL(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT c.nom as `Client`,d.`Num_bl`,DATE_FORMAT(d.`date_bl`,'%d/%m/%Y'),d.`Total_TTC`, IF(d.invoiced = 1,\"F\", \"N\" ) FROM `bl` d , client c  where d.id_client=c.numero_client and d.statut =1";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_bl >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_bl <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.total_TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_bl  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom  like '%" + NomClient + "%'";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY d.`Num_bl` DESC";

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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherBLStatStatVente(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            //  if (!id_client.isEmpty()) {
            addRowsStatAchatStatVente(table, FromDate, ToDate, id_marque, refArticle, id_client, df);
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public DefaultTableModel afficherBLStatNonVendu(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, String InClause) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            //  if (!id_client.isEmpty()) {
            addRowsStatAchatNonVendu(table, FromDate, ToDate, id_marque, refArticle, id_client, df, InClause);
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
                Logger.getLogger(BLDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return df;
    }

    public String afficherBLStatVendu(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, String InClause) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            //  if (!id_client.isEmpty()) {
            addRowsStatAchatVendu(table, FromDate, ToDate, id_marque, refArticle, id_client, df, InClause);
            /*   } else {
                String clients = AllClientsList(table, FromDate, ToDate, id_marque, refArticle, id_client, df);
                String[] lstClient = clients.split(",");
                addRowsStatAchatAllClients(table, FromDate, ToDate, id_marque, refArticle, id_client, df, lstClient);

            }*/

            return sqlReturn;
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
        return sqlReturn;
    }

    public String afficherProspection(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, String InClause, String groupe_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            //  if (!id_client.isEmpty()) {
            addRowsProspection(table, FromDate, ToDate, id_marque, refArticle, id_client, df, InClause, groupe_article);
            /*   } else {
                String clients = AllClientsList(table, FromDate, ToDate, id_marque, refArticle, id_client, df);
                String[] lstClient = clients.split(",");
                addRowsStatAchatAllClients(table, FromDate, ToDate, id_marque, refArticle, id_client, df, lstClient);

            }*/

            return sqlReturn;
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
        return sqlReturn;
    }

    public String afficherSaisieProspection(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, String InClause, String groupe_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            //  if (!id_client.isEmpty()) {
            addRowsSaisieProspection(table, FromDate, ToDate, id_marque, refArticle, id_client, df, InClause, groupe_article);
            /*   } else {
                String clients = AllClientsList(table, FromDate, ToDate, id_marque, refArticle, id_client, df);
                String[] lstClient = clients.split(",");
                addRowsStatAchatAllClients(table, FromDate, ToDate, id_marque, refArticle, id_client, df, lstClient);

            }*/

            return sqlReturn;
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
        return sqlReturn;
    }

    public void addRowsStatAchat(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT c.nom,lb.ref_article,lb.designation_article,b.Num_bl, DATE_FORMAT(b.date_bl,'%d/%m/%Y'),lb.prix_u, CONVERT(lb.qte, CHAR(50)) as quantity  "
                    + "FROM `ligne_bl` lb "
                    + "left join bl b on (lb.id_bl=b.Num_bl) "
                    + "left join ligne_facture lf on lf.num_bl=b.num_bl "
                    + "left join facture f on f.num_facture=lf.num_facture "
                    + "left join article r on lb.ref_article=r.ref "
                    + "left join client c on b.id_client=c.numero_Client "
                    + "left join marque_produit mp on r.id_marque = mp.id "
                    + "WHERE b.statut=1 ";

            String DateClauseFrom = "";
            /*  if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and b.date_bl >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and b.date_bl <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }*/
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and (b.date_bl >= '" + FromDate + "' or f.date_facture >= '" + FromDate + "')";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and (b.date_bl <= '" + ToDate + "'  or f.date_facture <= '" + ToDate + "')";
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
            sql += " group by lb.ref_article,b.Num_bl ORDER BY b.date_bl DESC";

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
            columnNames.add("Quantite");

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
                LigneData[6] = String.valueOf(rs.getString("quantity"));
                df.insertRow(startAdd, LigneData);

                qteTotalClient += Integer.valueOf(rs.getString("quantity"));
                startAdd++;

            }
            Object LigneData[] = new Object[7];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "";
            LigneData[6] = String.valueOf(qteTotalClient);
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatStatVente(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT c.nom,lb.ref_article,lb.designation_article,b.Num_bl, DATE_FORMAT(b.date_bl,'%d/%m/%Y'),lb.prix_u, CONVERT(lb.qte, CHAR(50)) as quantity  "
                    + "FROM `ligne_bl` lb "
                    + "left join bl b on (lb.id_bl=b.Num_bl) "
                    + "left join ligne_facture lf on lf.num_bl=b.num_bl "
                    + "left join facture f on f.num_facture=lf.num_facture ";

            if (!id_marque.isEmpty()) {
                sql += "left join article r on lb.ref_article=r.ref ";  //
            }

            sql += "left join client c on b.id_client=c.numero_Client ";

            if (!id_marque.isEmpty()) {
                sql += "left join marque_produit mp on r.id_marque = mp.id "; //
            }

            sql += " WHERE b.statut=1 ";

            String DateClauseFrom = "";
            /*  if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and b.date_bl >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and b.date_bl <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }*/
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and (b.date_bl >= '" + FromDate + "' or f.date_facture >= '" + FromDate + "')";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and (b.date_bl <= '" + ToDate + "'  or f.date_facture <= '" + ToDate + "')";
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
            sql += " group by lb.ref_article,b.Num_bl ORDER BY b.date_bl DESC";

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
            columnNames.add("Quantite");

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
                LigneData[6] = String.valueOf(rs.getString("quantity"));
                df.insertRow(startAdd, LigneData);

                qteTotalClient += Integer.valueOf(rs.getString("quantity"));
                startAdd++;

            }
            Object LigneData[] = new Object[7];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "";
            LigneData[6] = String.valueOf(qteTotalClient);
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public String getTypeArticlebyIdClient(String id_client) {
        String ListClient = "";
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "select classification from client where numero_Client = " + id_client;

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                ListClient += "" + rs.getString("classification") + "";
            }
            /* if (!ListClient.isEmpty()) {
                ListClient = ListClient.substring(0, ListClient.length() - 1);
            }*/

            return ListClient;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return ListClient;

    }

    public void addRowsStatAchatNonVendu(JTable table, String FromDate, String ToDate, String id_marque, String id_commercial, String id_client, DefaultTableModel df, String InClause) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String RefArticles = lstArticleVendu(FromDate, ToDate, id_marque, id_commercial, id_client);

            String sql;

            // sql = "SELECT c.nom,lb.ref_article,lb.designation_article,b.Num_bl, DATE_FORMAT(b.date_bl,'%d/%m/%Y'),lb.prix_u,lb.qte FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";
            //  sql = "SELECT c.nom,lb.ref_article,lb.designation_article FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join commercial c on b.id_commercial=c.id left join client cl on b.id_client=cl.numero_client  WHERE b.statut=1  ";
            sql = "SELECT distinct a.ref,a.classification "
                    + "FROM `article` a "
                    + "where 1=1 "; //  + "and a.classification = (select c.classification from client c where c.numero_Client=65) "
            ///+ "and a.ref not in ('F26101218')";
            String s = "";

            String refClauseFrom = "";

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and a.classification = (select c.classification from client c where c.numero_Client=" + id_client + ") ";
                sql = sql + ClientClause;

            }

            if (!RefArticles.isEmpty()) {
                refClauseFrom = " and a.ref not in (" + RefArticles + ")";
                sql = sql + refClauseFrom;
            }

            /*  String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and bl.date_bl >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and bl.date_bl <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String refClause = "";
            if (!(id_commercial.isEmpty())) {
                refClause = " and pb.id_commercial  = '" + id_commercial + "'";
                sql = sql + refClause;

            }*/
            String marqueClause = "";
            if (!(id_marque.isEmpty())) {
                marqueClause = " and a.id_marque  = " + id_marque + "";
                sql = sql + marqueClause;

            }
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            int qteTotalClient = 0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();

            columnNames.add("Ref Article");
            columnNames.add("Activité");

            /*    columnNames.add("Designation");
            columnNames.add("Num BL");
            columnNames.add("Prix Unitaire");

            columnNames.add("Quantité");*/
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {

                Object LigneData[] = new Object[2];

                LigneData[0] = rs.getString("a.ref");
                LigneData[1] = rs.getString("a.classification");

                //  LigneData[2] = rs.getString("lb.designation_article");
                //  LigneData[3] = rs.getString("b.Num_bl");
                // LigneData[5] = rs.getString("lb.prix_u");
                // LigneData[6] = rs.getString("lb.qte");
                df.insertRow(startAdd, LigneData);

                // qteTotalClient += Integer.valueOf(rs.getString("lb.qte"));
                startAdd++;

            }
            /*     Object LigneData[] = new Object[2];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            /*  LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "";
            LigneData[6] = qteTotalClient;*/
            //  df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatNonVendu_Backup(JTable table, String FromDate, String ToDate, String id_marque, String id_commercial, String id_client, DefaultTableModel df, String InClause) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String RefArticles = lstArticleVendu(FromDate, ToDate, id_marque, id_commercial, id_client);

            String sql;

            // sql = "SELECT c.nom,lb.ref_article,lb.designation_article,b.Num_bl, DATE_FORMAT(b.date_bl,'%d/%m/%Y'),lb.prix_u,lb.qte FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";
            //  sql = "SELECT c.nom,lb.ref_article,lb.designation_article FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join commercial c on b.id_commercial=c.id left join client cl on b.id_client=cl.numero_client  WHERE b.statut=1  ";
            sql = "select c.nom,pa.id_client, pa.ref_article,DATE_FORMAT(bl.date_bl,'%d/%m/%Y') "
                    + "from prefered_article pa "
                    + "left join client c on c.numero_client=pa.id_client "
                    + "left join ligne_bl lb on pa.ref_article=lb.ref_article "
                    + "left join bl bl on lb.id_bl=bl.num_bl "
                    + "left join pourcentagebonus pb on pb.id_client=c.numero_client "
                    + "where 1=1";
            String s = "";
            if (InClause.equals("in")) {
                // String type = getTypeArticlebyIdClient(id_client);
                //  s = "select a.ref from article a where ref in (" + RefArticles + ") and a.classification = '" + "M" + "' and a.classification <> '-' ";
                s = "select a.ref from article a where  a.classification = '" + "M" + "' and a.classification <> '-' ";
                id_client = "";
                RefArticles = "";
                FromDate = "";
                ToDate = "";
                id_commercial = "";
            }
            String refClauseFrom = "";

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and pa.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }

            if (!RefArticles.isEmpty()) {
                refClauseFrom = " and pa.ref_article  " + InClause + " (" + RefArticles + ")";
                sql = sql + refClauseFrom;
            }

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and bl.date_bl >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and bl.date_bl <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String refClause = "";
            if (!(id_commercial.isEmpty())) {
                refClause = " and pb.id_commercial  = '" + id_commercial + "'";
                sql = sql + refClause;

            }
            /*  String marqueClause = "";
            if (!(id_marque.isEmpty())) {
                marqueClause = " and r.id_marque  = " + id_marque + "";
                sql = sql + marqueClause;

            }*/

            sql += " group by c.nom, pa.ref_article ORDER BY bl.date_bl DESC";

            if (InClause.equals("in")) {
                sql = s;
            }
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            int qteTotalClient = 0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            if (InClause.equals("in")) {
                columnNames.add("Ref Article");
            } else {
                columnNames.add("Nom Client");
                columnNames.add("Ref Article");
                columnNames.add("Date Dernière BL");
            }
            /*    columnNames.add("Designation");
            columnNames.add("Num BL");
            columnNames.add("Prix Unitaire");

            columnNames.add("Quantité");*/
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {

                Object LigneData[] = new Object[3];
                if (InClause.equals("in")) {
                    LigneData[0] = rs.getString("a.ref");
                } else {
                    LigneData[0] = rs.getString("c.nom");
                    LigneData[1] = rs.getString("pa.ref_article");
                    LigneData[2] = rs.getString("DATE_FORMAT(bl.date_bl,'%d/%m/%Y')");

                }

                //  LigneData[2] = rs.getString("lb.designation_article");
                //  LigneData[3] = rs.getString("b.Num_bl");
                // LigneData[5] = rs.getString("lb.prix_u");
                // LigneData[6] = rs.getString("lb.qte");
                df.insertRow(startAdd, LigneData);

                // qteTotalClient += Integer.valueOf(rs.getString("lb.qte"));
                startAdd++;

            }
            Object LigneData[] = new Object[3];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            /*  LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "";
            LigneData[6] = qteTotalClient;*/
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    String sqlReturn;

    public void addRowsStatAchatVendu(JTable table, String FromDate, String ToDate, String id_marque, String id_commercial, String id_client, DefaultTableModel df, String InClause) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String RefArticles = lstArticleVendu(FromDate, ToDate, id_marque, id_commercial, id_client);

            // sql = "SELECT c.nom,lb.ref_article,lb.designation_article,b.Num_bl, DATE_FORMAT(b.date_bl,'%d/%m/%Y'),lb.prix_u,lb.qte FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";
            //  sql = "SELECT c.nom,lb.ref_article,lb.designation_article FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join commercial c on b.id_commercial=c.id left join client cl on b.id_client=cl.numero_client  WHERE b.statut=1  ";
            sqlReturn = "SELECT cl.nom, lb.ref_article,DATE_FORMAT(b.date_bl,'%d/%m/%Y') as dt,r.classification, CONVERT((DATEDIFF(CURDATE(),b.date_bl)), CHAR(50)) as nbjrs, if( CONVERT((DATEDIFF(CURDATE(),b.date_bl)), CHAR(50))>45,\"Oui\",\"Non\" ) as depasse "
                    + "FROM `ligne_bl` lb "
                    + "left join bl b on (lb.id_bl=b.Num_bl) "
                    + "left join client cl on cl.numero_client=b.id_client "
                    + "left join article r on lb.ref_article=r.ref "
                    + "left join pourcentagebonus pb on pb.id_client=b.id_client "
                    // + "left join commercial c on b.id_commercial=c.id "
                    + "WHERE b.statut=1 ";
            String DateClauseFrom = "";

            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and b.date_bl >= '" + FromDate + "'";
                sqlReturn += DateClauseFrom;
            }
            String clientClauseTo = "";
            if (!(id_client.isEmpty())) {
                clientClauseTo = " and b.id_client = " + id_client + "";
                sqlReturn += clientClauseTo;
            }
            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and b.date_bl <= '" + ToDate + "'";
                sqlReturn += DateClauseTo;
            }

            String commercialClause = "";
            if (!(id_commercial.isEmpty())) {
                commercialClause = " and pb.id_commercial  = " + id_commercial + "";
                sqlReturn += commercialClause;

            }
            sqlReturn += " order by b.date_bl desc ";
            pst = conn.prepareStatement(sqlReturn);

            rs = pst.executeQuery();

            int qteTotalClient = 0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();

            columnNames.add("Client");
            columnNames.add("Ref Article");
            columnNames.add("Derniére BL");
            columnNames.add("Activité");//nbjrs
            columnNames.add("Nb Jrs dés la Dernière Vente");// dés la Dernière Vente

            columnNames.add("Dépasse 45 jrs ? (O/N)");// ? (O/N)


            /*    columnNames.add("Designation");
            columnNames.add("Num BL");
            columnNames.add("Prix Unitaire");

            columnNames.add("Quantité");*/
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {

                Object LigneData[] = new Object[6];
//cl.nom, lb.ref_article,b.date_bl
                LigneData[0] = rs.getString("cl.nom");
                LigneData[1] = rs.getString("lb.ref_article");
                LigneData[2] = rs.getString("dt").toString();
                LigneData[3] = rs.getString("r.classification");

                LigneData[4] = rs.getString("nbjrs");

                LigneData[5] = rs.getString("depasse");
                //  LigneData[2] = rs.getString("lb.designation_article");
                //  LigneData[3] = rs.getString("b.Num_bl");
                // LigneData[5] = rs.getString("lb.prix_u");
                // LigneData[6] = rs.getString("lb.qte");
                df.insertRow(startAdd, LigneData);

                // qteTotalClient += Integer.valueOf(rs.getString("lb.qte"));
                startAdd++;

            }
            //    Object LigneData[] = new Object[4];

            /*   LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";

            /*  LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "";
            LigneData[6] = qteTotalClient;*/
            //  df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsProspection(JTable table, String FromDate, String ToDate, String id_marque, String id_commercial, String id_client, DefaultTableModel df, String InClause, String groupe_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            // String RefArticlesByGroupe = lstArticleGroupe(FromDate, ToDate, groupe_article, id_commercial, id_client);

            // sql = "SELECT c.nom,lb.ref_article,lb.designation_article,b.Num_bl, DATE_FORMAT(b.date_bl,'%d/%m/%Y'),lb.prix_u,lb.qte FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";
            //  sql = "SELECT c.nom,lb.ref_article,lb.designation_article FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join commercial c on b.id_commercial=c.id left join client cl on b.id_client=cl.numero_client  WHERE b.statut=1  ";
            sqlReturn = "SELECT distinct a.ref,(SELECT concat(concat(b.date_bl ,' Qte : '),lb.qte) FROM ligne_bl lb LEFT JOIN bl b ON b.Num_bl=lb.id_bl WHERE lb.ref_article=a.ref AND b.date_bl >= '" + FromDate + "' AND b.date_bl <='" + ToDate + "' AND b.id_client=" + id_client + " ORDER BY lb.id_bl desc LIMIT 1 ) AS vente, "
                    + " (SELECT date_visite FROM prospection p WHERE ref_article=a.ref AND id_client=" + id_client + " AND date_visite >= '" + FromDate + "' AND date_visite <='" + ToDate + "' order by date_visite desc limit 1 ) AS prospection "
                    + " from article a where a.groupe_prospection='" + groupe_article + "'; ";

            String DateClauseFrom = "";

            /*  if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and b.date_bl >= '" + FromDate + "'";
                sqlReturn += DateClauseFrom;
            }
            String clientClauseTo = "";
            if (!(id_client.isEmpty())) {
                clientClauseTo = " and b.id_client = " + id_client + "";
                sqlReturn += clientClauseTo;
            }
            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and b.date_bl <= '" + ToDate + "'";
                sqlReturn += DateClauseTo;
            }

            String commercialClause = "";
            if (!(id_commercial.isEmpty())) {
                commercialClause = " and pb.id_commercial  = " + id_commercial + "";
                sqlReturn += commercialClause;

            }
            sqlReturn += " order by b.date_bl desc ";*/
            pst = conn.prepareStatement(sqlReturn);

            rs = pst.executeQuery();

            int qteTotalClient = 0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();

            columnNames.add("Article");
            columnNames.add("Vente");
            columnNames.add("Prospection");

            /*    columnNames.add("Designation");
            columnNames.add("Num BL");
            columnNames.add("Prix Unitaire");

            columnNames.add("Quantité");*/
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {

                Object LigneData[] = new Object[3];
//cl.nom, lb.ref_article,b.date_bl
                LigneData[0] = rs.getString("a.ref");
                LigneData[1] = rs.getString("vente");
                LigneData[2] = rs.getString("prospection");
                /*  LigneData[3] = rs.getString("r.classification");

                LigneData[4] = rs.getString("nbjrs");

                LigneData[5] = rs.getString("depasse");*/
                //  LigneData[2] = rs.getString("lb.designation_article");
                //  LigneData[3] = rs.getString("b.Num_bl");
                // LigneData[5] = rs.getString("lb.prix_u");
                // LigneData[6] = rs.getString("lb.qte");
                df.insertRow(startAdd, LigneData);

                // qteTotalClient += Integer.valueOf(rs.getString("lb.qte"));
                startAdd++;

            }
            //    Object LigneData[] = new Object[4];

            /*   LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";

            /*  LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "";
            LigneData[6] = qteTotalClient;*/
            //  df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsSaisieProspection(JTable table, String FromDate, String ToDate, String id_marque, String id_commercial, String id_client, DefaultTableModel df, String InClause, String groupe_article) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            // String RefArticlesByGroupe = lstArticleGroupe(FromDate, ToDate, groupe_article, id_commercial, id_client);

            // sql = "SELECT c.nom,lb.ref_article,lb.designation_article,b.Num_bl, DATE_FORMAT(b.date_bl,'%d/%m/%Y'),lb.prix_u,lb.qte FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";
            //  sql = "SELECT c.nom,lb.ref_article,lb.designation_article FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join commercial c on b.id_commercial=c.id left join client cl on b.id_client=cl.numero_client  WHERE b.statut=1  ";
            sqlReturn = " SELECT  p.ref_article,c.nom,p.date_visite,a.groupe_prospection FROM prospection p LEFT JOIN client c ON c.numero_Client=p.id_client LEFT JOIN article a ON a.ref=p.ref_article WHERE 1=1 ";

            String DateClauseFrom = "";

            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and p.date_visite >= '" + FromDate + "'";
                sqlReturn += DateClauseFrom;
            }
            String clientClauseTo = "";
            if (!(id_client.isEmpty())) {
                clientClauseTo = " and p.id_client = " + id_client + "";
                sqlReturn += clientClauseTo;
            }
            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and p.date_visite <= '" + ToDate + "'";
                sqlReturn += DateClauseTo;
            }

            String clientClause = "";
            if (!(id_client.isEmpty())) {
                clientClause = " and p.id_client  = " + id_client + "";
                sqlReturn += clientClause;

            }

            String commercialClause = "";
            if (!(groupe_article.isEmpty())) {
                commercialClause = " and a.groupe_prospection  = '" + groupe_article + "'";
                sqlReturn += commercialClause;

            }
            sqlReturn += " order by p.date_visite desc ";
            pst = conn.prepareStatement(sqlReturn);

            rs = pst.executeQuery();

            int qteTotalClient = 0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();

            columnNames.add("Article");
            columnNames.add("Client");
            columnNames.add("Date Visite");

            /*    columnNames.add("Designation");
            columnNames.add("Num BL");
            columnNames.add("Prix Unitaire");

            columnNames.add("Quantité");*/
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {

                Object LigneData[] = new Object[3];
//cl.nom, lb.ref_article,b.date_bl
                LigneData[0] = rs.getString("p.ref_article");
                LigneData[1] = rs.getString("c.nom");
                LigneData[2] = rs.getString("p.date_visite");
                /*  LigneData[3] = rs.getString("r.classification");

                LigneData[4] = rs.getString("nbjrs");

                LigneData[5] = rs.getString("depasse");*/
                //  LigneData[2] = rs.getString("lb.designation_article");
                //  LigneData[3] = rs.getString("b.Num_bl");
                // LigneData[5] = rs.getString("lb.prix_u");
                // LigneData[6] = rs.getString("lb.qte");
                df.insertRow(startAdd, LigneData);

                // qteTotalClient += Integer.valueOf(rs.getString("lb.qte"));
                startAdd++;

            }
            //    Object LigneData[] = new Object[4];

            /*   LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";

            /*  LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "";
            LigneData[6] = qteTotalClient;*/
            //  df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatAllClients(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
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
            int startAdd = 0;
            int totalGlobal = 0;
            for (String client : lstclient) {
                startAdd = df.getRowCount();
                id_client = client;
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
                String nomclient = "";
                while (rs.next()) {
                    Object LigneData[] = new Object[7];
                    nomclient = rs.getString("c.nom");
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
                LigneData[6] = "Total " + nomclient + " : " + qteTotalClient + "";
                totalGlobal += qteTotalClient;
                df.insertRow(startAdd, LigneData);
                Object LigneDatas[] = new Object[5];

                LigneDatas[0] = "";
                LigneDatas[1] = "";
                LigneDatas[2] = "";
                LigneDatas[4] = "";

                df.insertRow(startAdd + 1, LigneDatas);
            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "Total tous les clients : " + totalGlobal + "";
            df.insertRow(startAdd + 1, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public String AllClientsList(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        String ListClient = "";
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT distinct b.id_client FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";

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
            sql += " ORDER BY c.numero_Client DESC";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                ListClient += "" + rs.getString("b.id_client") + ",";
            }
            if (!ListClient.isEmpty()) {
                ListClient = ListClient.substring(0, ListClient.length() - 1);
            }

            return ListClient;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return ListClient;

    }

    public String AllArticlsList(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        String ListClient = "";
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT distinct lb.ref_article FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";

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
            sql += " ORDER BY c.numero_Client DESC";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                ListClient += "" + rs.getString("lb.ref_article") + ",";
            }
            if (!ListClient.isEmpty()) {
                ListClient = ListClient.substring(0, ListClient.length() - 1);
            }

            return ListClient;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return ListClient;

    }

    public String AllClientSuiviReglementList(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient, String type_filtre, String id_commercial) {
        String ListClient = "";
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql = "SELECT distinct c.numero_client as `Client` FROM `Facture` d left join client c on (d.id_client=c.numero_client) left join reglement r on d.Num_facture = r.id_facture left join pourcentagebonus pb on pb.id_client=c.numero_client left join commercial comm on pb.id_commercial=comm.id where d.statut=1 and d.reglement in ('Semi','Non') /*and c.numero_client != '99999999' */ ";
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and d.id_client <> 0 ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and d.id_client = 0 ";
                sql = sql + FiltreClauseFrom;
            }

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String idCommercialClause = "";
            if (!(id_commercial.isEmpty())) {
                idCommercialClause = " and pb.id_commercial  = '" + id_commercial + "'";
                sql = sql + idCommercialClause;

            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_facture  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom  = '" + NomClient + "'";
                sql = sql + ClientClause;

            }
            sql += " group by c.numero_client order by c.nom   ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                ListClient += "" + rs.getString("Client") + ",";
            }
            if (!ListClient.isEmpty()) {
                ListClient = ListClient.substring(0, ListClient.length() - 1);
            }

            return ListClient;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return ListClient;

    }

    public String AllArticlsListParPeriode(JTable table, PeriodDatesClass Dates, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        String ListClient = "";
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT distinct lb.ref_article FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";

            String DateClauseFrom = "";
            if (!(Dates.getFromDate1Year() == null)) {
                DateClauseFrom = " and b.date_bl >= '" + Dates.getFromDate1Year() + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(Dates.getToDate1Year() == null)) {
                DateClauseTo = " and b.date_bl <= '" + Dates.getToDate1Year() + "'";
                sql = sql + DateClauseTo;
            }

            String refClause = "";
            if (!(refArticle.isEmpty())) {
                refClause = " and lb.ref_article  in (" + refArticle + ")";
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
            sql += " ORDER BY c.numero_Client DESC";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                ListClient += "" + rs.getString("lb.ref_article") + ",";
            }
            if (!ListClient.isEmpty()) {
                ListClient = ListClient.substring(0, ListClient.length() - 1);
            }

            return ListClient;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return ListClient;

    }

    public DefaultTableModel afficherBLStatParArticle(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            //     if (!refArticle.isEmpty()) {
            addRowsStatAchatParArticle(table, FromDate, ToDate, id_marque, refArticle, id_client, df);
            /*   } else {
                String clients = AllArticlsList(table, FromDate, ToDate, id_marque, refArticle, id_client, df);
                String[] lstClient = clients.split(",");
                addRowsStatAchatAllClientsParArticle(table, FromDate, ToDate, id_marque, refArticle, id_client, df, lstClient);

            }*/

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

    public DefaultTableModel afficherBLStatParArticleParClient(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            //     if (!id_client.isEmpty()) {
            //  addRowsStatAchatParArticleParClient(table, FromDate, ToDate, id_marque, refArticle, id_client, df);
            addRowsStatAchatParArticle(table, FromDate, ToDate, id_marque, refArticle, id_client, df);
            /*    } else {
                String clients = AllArticlsList(table, FromDate, ToDate, id_marque, refArticle, id_client, df);
                String[] lstClient = clients.split(",");
                addRowsStatAchatAllClientsParArticleParClient(table, FromDate, ToDate, id_marque, refArticle, id_client, df, lstClient);

            }*/

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

    public DefaultTableModel afficherBLStatParCommercial(JTable table, String FromDate, String ToDate, String id_marque, String id_commercial, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            // if (!id_commercial.isEmpty()) {
            addRowsStatAchatParCommercial(table, FromDate, ToDate, id_marque, id_commercial, id_client, df);
            /*  } else {
                String clients = AllArticlsList(table, FromDate, ToDate, id_marque, id_commercial, id_client, df);
                String[] lstClient = clients.split(",");
                addRowsStatAchatAllClientsParArticle(table, FromDate, ToDate, id_marque, id_commercial, id_client, df, lstClient);

            }*/

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

    public DefaultTableModel afficherBLStatParArticleParPeriod(JTable table, PeriodDatesClass Dates, String id_marque, String refArticle, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            if (!refArticle.isEmpty()) {

                // addRowsStatAchatParArticleParPeriode(table, Dates, id_marque, refArticle, id_client, df);
                String[] lstClient = refArticle.split(",");

                addRowsStatAchatAllClientsParArticleParPeriodeTest(table, Dates, id_marque, refArticle, id_client, df, lstClient);

            } else {
                String clients = AllArticlsListParPeriode(table, Dates, id_marque, refArticle, id_client, df);
                String[] lstClient = clients.split(",");
                //String[] lstClient = null;
                addRowsStatAchatAllClientsParArticleParPeriodeTest(table, Dates, id_marque, refArticle, id_client, df, lstClient);
            }

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

    public int addRowsStatAchatParArticle(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT  c.nom,b.date_bl,lb.ref_article,lb.designation_article,lb.qte as Qte,f.num_facture,f.date_facture,b.num_bl "
                    + "FROM `ligne_bl` lb "
                    + "left join bl b on (lb.id_bl=b.Num_bl) "
                    + "left join ligne_facture lf on lf.num_bl=b.num_bl "
                    + "left join facture f on f.num_facture=lf.num_facture ";
            if (!id_marque.isEmpty()) {
                sql += "left join article r on lb.ref_article=r.ref ";
            }
            sql += "left join client c on b.id_client=c.numero_Client ";
            if (!id_marque.isEmpty()) {
                sql += "left join marque_produit mp on r.id_marque = mp.id ";
            }
            sql += "WHERE b.statut=1 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and (b.date_bl >= '" + FromDate + "' or f.date_facture >= '" + FromDate + "')";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and (b.date_bl <= '" + ToDate + "'  or f.date_facture <= '" + ToDate + "')";
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
            sql += " GROUP by c.nom,b.date_bl,lb.ref_article ORDER BY b.date_bl DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            int qteTotalClient = 0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Num BL");
            columnNames.add("Date BL");
            columnNames.add("Num Facture");
            columnNames.add("Date Facture");
            columnNames.add("Ref Article");
            columnNames.add("Designation");
            columnNames.add("Qte");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                Object LigneData[] = new Object[8];
                LigneData[0] = rs.getString("c.nom");
                LigneData[1] = rs.getString("b.num_bl");
                LigneData[2] = rs.getString("b.date_bl");
                LigneData[3] = rs.getString("f.num_facture");
                LigneData[4] = rs.getString("f.date_facture");
                LigneData[5] = rs.getString("lb.ref_article");
                LigneData[6] = rs.getString("lb.designation_article");
                LigneData[7] = String.valueOf(rs.getString("Qte"));
                df.insertRow(startAdd, LigneData);

                qteTotalClient += Integer.valueOf(rs.getString("Qte"));
                startAdd++;

            }
            Object LigneData[] = new Object[8];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "";
            LigneData[6] = "";
            LigneData[7] = String.valueOf(qteTotalClient);
            df.insertRow(startAdd, LigneData);
            table.setModel(df);
            return qteTotalClient;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return 0;

    }

    public void addRowsStatAchatParArticleParClient(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT  c.nom,b.date_bl,lb.ref_article,lb.designation_article,sum(lb.qte) FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";

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
            sql += " GROUP by c.nom,b.date_bl,lb.ref_article ORDER BY b.date_bl DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            int qteTotalClient = 0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date BL");
            columnNames.add("Ref Article");
            columnNames.add("Designation");
            columnNames.add("Quantité");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                Object LigneData[] = new Object[5];
                LigneData[0] = rs.getString("c.nom");
                LigneData[1] = rs.getString("b.date_bl");
                LigneData[2] = rs.getString("lb.ref_article");
                LigneData[3] = rs.getString("lb.designation_article");
                LigneData[4] = rs.getString("sum(lb.qte)");
                df.insertRow(startAdd, LigneData);

                qteTotalClient += Integer.valueOf(rs.getString("sum(lb.qte)"));
                startAdd++;

            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = qteTotalClient;
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public String lstArticleVendu(String FromDate, String ToDate, String id_marque, String id_commercial, String id_client) {

        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String RefArticles = "";

        try {

            String sql2 = "SELECT distinct lb.ref_article "
                    + "FROM `ligne_bl` lb "
                    + "left join bl b on (lb.id_bl=b.Num_bl) "
                    + "left join article r on lb.ref_article=r.ref "
                    + "left join pourcentagebonus pb on pb.id_client=b.id_client "
                    // + "left join commercial c on b.id_commercial=c.id "
                    + "WHERE b.statut=1 ";
            String DateClauseFrom = "";

            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and b.date_bl >= '" + FromDate + "'";
                sql2 += DateClauseFrom;
            }
            String clientClauseTo = "";
            if (!(id_client.isEmpty())) {
                clientClauseTo = " and b.id_client = " + id_client + "";
                sql2 += clientClauseTo;
            }
            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and b.date_bl <= '" + ToDate + "'";
                sql2 += DateClauseTo;
            }

            String commercialClause = "";
            if (!(id_commercial.isEmpty())) {
                commercialClause = " and pb.id_commercial  = " + id_commercial + "";
                sql2 += commercialClause;

            }

            String marqueClause = "";
            if (!(id_marque.isEmpty())) {
                marqueClause = " and r.id_marque  = " + id_marque + "";
                sql2 = sql2 + marqueClause;

            }
            pst = conn.prepareStatement(sql2);

            rs = pst.executeQuery();

            while (rs.next()) {
                RefArticles += "'" + rs.getString("lb.ref_article") + "',";
            }
            if (!RefArticles.isEmpty()) {

                RefArticles = RefArticles.substring(0, RefArticles.length() - 1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return RefArticles;
    }

    public String lstArticleGroupe(String FromDate, String ToDate, String groupe_article, String id_commercial, String id_client) {

        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String RefArticles = "";

        try {

            String sql2 = "SELECT distinct ref from article where groupe_article = '" + groupe_article + "'";
            String DateClauseFrom = "";

            /* if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and b.date_bl >= '" + FromDate + "'";
                sql2 += DateClauseFrom;
            }
            String clientClauseTo = "";
            if (!(id_client.isEmpty())) {
                clientClauseTo = " and b.id_client = " + id_client + "";
                sql2 += clientClauseTo;
            }
            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and b.date_bl <= '" + ToDate + "'";
                sql2 += DateClauseTo;
            }

            String commercialClause = "";
            if (!(id_commercial.isEmpty())) {
                commercialClause = " and pb.id_commercial  = " + id_commercial + "";
                sql2 += commercialClause;

            }

            String marqueClause = "";
            if (!(groupe_article.isEmpty())) {
                marqueClause = " and r.id_marque  = " + groupe_article + "";
                sql2 = sql2 + marqueClause;

            }*/
            pst = conn.prepareStatement(sql2);

            rs = pst.executeQuery();

            while (rs.next()) {
                RefArticles += "'" + rs.getString("ref") + "',";
            }
            if (!RefArticles.isEmpty()) {

                RefArticles = RefArticles.substring(0, RefArticles.length() - 1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return RefArticles;
    }

    public void addRowsStatAchatParCommercial(JTable table, String FromDate, String ToDate, String id_marque, String id_commercial, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String RefArticles = lstArticleVendu(FromDate, ToDate, id_marque, id_commercial, id_client);

            String sql;
            sql = "SELECT c.nom,b.date_bl,lb.ref_article,lb.designation_article,sum(lb.qte) FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join commercial c on b.id_commercial=c.id left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1  ";
            //  sql = "SELECT c.nom,lb.ref_article,lb.designation_article FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join commercial c on b.id_commercial=c.id  WHERE b.statut=1 and ref_article not in (" + RefArticles + ") ";
            String DateClauseFrom = "";

            DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and b.date_bl >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }
            String DateClauseTo = "";

            DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and b.date_bl <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String commercialClause = "";
            commercialClause = "";
            if (!(id_commercial.isEmpty())) {
                commercialClause = " and b.id_commercial  = " + id_commercial + "";
                sql = sql + commercialClause;

            }
            sql += " GROUP by c.nom,lb.ref_article ORDER BY b.date_bl DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            int qteTotalClient = 0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Commercial");
            // columnNames.add("Date BL");
            columnNames.add("Ref Article");
            columnNames.add("Designation");
            columnNames.add("Quantité");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            /*  df = buildTableModel(rs);
            table.setModel(df);*/
            while (rs.next()) {
                Object LigneData[] = new Object[5];
                LigneData[0] = rs.getString("c.nom");
                //    LigneData[1] = rs.getString("b.date_bl");
                LigneData[1] = rs.getString("lb.ref_article");
                LigneData[2] = rs.getString("lb.designation_article");
                LigneData[3] = rs.getString("sum(lb.qte)");
                df.insertRow(startAdd, LigneData);

                qteTotalClient += Integer.valueOf(rs.getString("sum(lb.qte)"));
                startAdd++;

            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = qteTotalClient;
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatParArticleParPeriode(JTable table, PeriodDatesClass Dates, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "select lb.ref_article,lb.designation_article,bl.date_bl,sum(qte),bl.invoiced from ligne_bl lb left join bl bl on lb.id_bl=bl.Num_bl   WHERE bl.statut=1 ";//left join ligne_facture lf on lf.num_bl=bl.Num_bl left join facture f on f.num_facture=lf.num_facture

            String DateClauseFrom = "";
            if (!(Dates.getFromDate1Year() == null)) {
                DateClauseFrom = " and bl.date_bl >= '" + Dates.getFromDate1Year() + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(Dates.getToDate1Year() == null)) {
                DateClauseTo = " and bl.date_bl <= '" + Dates.getToDate1Year() + "'";
                sql = sql + DateClauseTo;
            }

            String refClause = "";
            if (!(refArticle.isEmpty())) {
                refClause = " and lb.ref_article  in (" + refArticle + ")";
                sql = sql + refClause;

            }
            String marqueClause = "";
            if (!(id_marque.isEmpty())) {
                marqueClause = " and r.id_marque  = " + id_marque + "";
                sql = sql + marqueClause;

            }
            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and bl.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " GROUP by ref_article,bl.date_bl ORDER BY date_bl DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            int qteTotalClient = 0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Ref Article");
            columnNames.add("Designation");
            //   columnNames.add("Date");
            columnNames.add("1 An");
            columnNames.add("6 Mois");
            columnNames.add("3 Mois");
            columnNames.add("1 Mois");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                //, ,  ,f.date_facture, ,bl.invoiced
                Object LigneData[] = new Object[4];
                LigneData[0] = rs.getString("lb.ref_article");
                LigneData[1] = rs.getString("lb.designation_article");
                // LigneData[2] = rs.getString("bl.date_bl");
                LigneData[2] = rs.getString("sum(qte)");

                df.insertRow(startAdd, LigneData);

                qteTotalClient += Integer.valueOf(rs.getString("sum(qte)"));
                startAdd++;

            }
            Object LigneData[] = new Object[4];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = qteTotalClient;
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatAllClientsParArticle(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date BL");
            columnNames.add("Ref Article");
            columnNames.add("Designation");
            columnNames.add("Quantité");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            int totalGlobal = 0;
            for (String client : lstclient) {
                startAdd = df.getRowCount();
                id_client = client;
                String sql = "";
                sql = "SELECT c.nom,b.date_bl,lb.ref_article,lb.designation_article,sum(lb.qte) FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";

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
                    refClause = " and lb.ref_article  in (" + refArticle + ")";
                    sql = sql + refClause;

                }
                String marqueClause = "";
                if (!(id_marque.isEmpty())) {
                    marqueClause = " and r.id_marque  = " + id_marque + "";
                    sql = sql + marqueClause;

                }
                /* String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and b.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }*/
                sql += " GROUP by lb.ref_article,b.date_bl  ORDER BY b.date_bl DESC";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                int qteTotalClient = 0;
                String nomclient = "";
                while (rs.next()) {
                    Object LigneData[] = new Object[5];
                    LigneData[0] = rs.getString("c.nom");
                    LigneData[1] = rs.getString("b.date_bl");
                    LigneData[2] = rs.getString("lb.ref_article");
                    LigneData[3] = rs.getString("lb.designation_article");
                    LigneData[4] = rs.getString("sum(lb.qte)");
                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Integer.valueOf(rs.getString("sum(lb.qte)"));
                    startAdd++;

                }

                Object LigneData[] = new Object[5];

                LigneData[0] = "Total";
                LigneData[1] = "";
                LigneData[2] = "";
                LigneData[3] = "";
                LigneData[4] = "Total " + nomclient + " : " + qteTotalClient + "";
                totalGlobal += qteTotalClient;
                df.insertRow(startAdd, LigneData);
                Object LigneDatas[] = new Object[4];

                LigneDatas[0] = "";
                LigneDatas[1] = "";
                LigneDatas[2] = "";
                LigneDatas[3] = "";

                df.insertRow(startAdd + 1, LigneDatas);
            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "Total tous les clients : " + totalGlobal + "";
            df.insertRow(startAdd + 1, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsSuiviReglementByClient(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient, String type_filtre, String id_commercial, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Client");
            columnNames.add("Num Facture");

            columnNames.add("Date Facture");
            columnNames.add("Total TTC");
            columnNames.add("Reglé");
            columnNames.add("Impayée");
            columnNames.add("Commercial");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            int totalGlobal = 0;

            for (String client : lstclient) {
                startAdd = df.getRowCount();

                String sql = "";

                sql = "SELECT  d.`Num_facture` as num_fact,if(c.numero_client='99999999',concat(concat(c.nom,' : '),d.passager),c.nom) as `Client`,DATE_FORMAT(d.`date_facture`,'%d/%m/%Y') as date,d.`TTC` as ttc,"
                        + "if(sum(r.regle) is NULL, 0, sum(if(r.facture_avoir='Facture',r.regle,0))) as \"regle\", "
                        + "(d.`TTC`-if(sum(if(r.statut=0,0,if(r.facture_avoir='Facture',r.regle,0))) is NULL, 0, sum(if(r.statut=0,0,if(r.facture_avoir='Facture',r.regle,0))))) as \"Impaye\","
                        + "comm.nom as \"Commercial\" "
                        + "FROM `Facture` d "
                        + "left join client c on (d.id_client=c.numero_client) "
                        + "left join reglement r on d.Num_facture = r.id_facture "
                        + "left join pourcentagebonus pb on pb.id_client=c.numero_client "
                        + "left join commercial comm on pb.id_commercial=comm.id "
                        + "where d.statut=1 and d.reglement in ('Semi','Non')";

                //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
                String FiltreClauseFrom = "";
                if ((type_filtre.equals("client"))) {
                    FiltreClauseFrom = " and d.id_client <> 0 ";
                    sql = sql + FiltreClauseFrom;
                } else if ((type_filtre.equals("passager"))) {
                    FiltreClauseFrom = " and d.id_client = 0 ";
                    sql = sql + FiltreClauseFrom;
                }

                String DateClauseFrom = "";
                if (!(FromDate.isEmpty())) {
                    DateClauseFrom = " and d.date_facture >= '" + FromDate + "'";
                    sql = sql + DateClauseFrom;
                }

                String idCommercialClause = "";
                if (!(id_commercial.isEmpty())) {
                    idCommercialClause = " and pb.id_commercial  = '" + id_commercial + "'";
                    sql = sql + idCommercialClause;

                }

                String DateClauseTo = "";
                if (!(ToDate.isEmpty())) {
                    DateClauseTo = " and d.date_facture <= '" + ToDate + "'";
                    sql = sql + DateClauseTo;
                }

                String MontantClauseFrom = "";
                if (!(FromMontant.isEmpty())) {
                    MontantClauseFrom = " and d.TTC >= " + FromMontant + "";
                    sql = sql + MontantClauseFrom;
                }
                String MontantClauseTo = "";
                if (!(ToMontant.isEmpty())) {
                    MontantClauseTo = " and d.TTC <= " + ToMontant + "";
                    sql = sql + MontantClauseTo;
                }

                String NumDevisClause = "";
                if (!(NumDevis.isEmpty())) {
                    NumDevisClause = " and d.Num_facture  like '%" + NumDevis + "%'";
                    sql = sql + NumDevisClause;

                }
                String ClientClause = "";
                if (!(client.isEmpty())) {
                    ClientClause = " and c.numero_client  = '" + client + "'";
                    sql = sql + ClientClause;

                }
                sql += "  GROUP by d.Num_facture ORDER BY c.nom, d.date_facture DESC";

                pst = conn.prepareStatement(sql);

                Double TotalTTC = 0.0;
                Double TotalImpaye = 0.0;
                Double Totalregle = 0.0;
                Double TotalTVA = 0.0;
                rs = pst.executeQuery();

                int qteTotalClient = 0;
                String nomclient = "";
                String last_clietnt = "";
                int i = 0;
                while (rs.next()) {
                    Object LigneData[] = new Object[7];

                    if (i == 0) {
                        LigneData[0] = rs.getString("Client");
                    } else if (rs.getString("Client").equals(last_clietnt)) {
                        LigneData[0] = "";
                    } else {
                        LigneData[0] = rs.getString("Client");
                    }
                    last_clietnt = rs.getString("Client");

                    //  nomclient = rs.getString("Client");
                    // LigneData[0] = nomclient;
                    LigneData[1] = rs.getString("num_fact");
                    LigneData[2] = rs.getString("date");

                    LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ttc")));
                    LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("regle")));
                    LigneData[5] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("Impaye")));
                    LigneData[6] = rs.getString("Commercial");

                    // df.insertRow(startAdd, LigneData);
                    TotalTTC += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ttc"))));
                    Totalregle += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("regle"))));
                    TotalImpaye += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("Impaye"))));

                    // startAdd++;
                    df.insertRow(startAdd, LigneData);

                    //  qteTotalClient += Integer.valueOf(rs.getString("sum(lb.qte)"));
                    startAdd++;
                    i++;
                }

                Object LigneData[] = new Object[7];

                LigneData[0] = "Total";
                LigneData[1] = "";
                LigneData[2] = "";
                LigneData[3] = "";
                LigneData[4] = "";
                LigneData[5] = ("Total : " + Config.Commen_Proc.formatDouble(Double.valueOf(TotalImpaye)) + "").toString();

                LigneData[6] = "";
                totalGlobal += TotalImpaye;
                df.insertRow(startAdd, LigneData);
                Object LigneDatas[] = new Object[7];

                LigneDatas[0] = "";
                LigneDatas[1] = "";
                LigneDatas[2] = "";
                LigneDatas[3] = "";
                LigneDatas[4] = "";
                LigneDatas[5] = "";
                LigneDatas[6] = "";

                df.insertRow(startAdd + 1, LigneDatas);
            }
            Object LigneData[] = new Object[7];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = ("Total tous les clients : " + Config.Commen_Proc.formatDouble(Double.valueOf(totalGlobal)) + "").toString();
            LigneData[6] = "";

            df.insertRow(startAdd + 1, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsSuiviReglementByClient(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient, String type_filtre, String id_commercial, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Client");
            columnNames.add("Num Facture");

            columnNames.add("Date Facture");
            columnNames.add("Total TTC");
            columnNames.add("Reglé");
            columnNames.add("Impayée");
            columnNames.add("Commercial");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            int totalGlobal = 0;

            // for (String client : lstclient) {
            String client = NomClient;
            startAdd = df.getRowCount();

            String sql = "";

            sql = "SELECT  d.`Num_facture` as num_fact,if(c.numero_client='99999999',concat(concat(c.nom,' : '),d.passager),c.nom) as `Client`,DATE_FORMAT(d.`date_facture`,'%d/%m/%Y') as date,d.`TTC` as ttc, "
                    + "if(sum(r.regle) is NULL, 0, sum(if(r.facture_avoir='Facture',r.regle,0))) as \"regle\", "
                    + "(d.`TTC`-if(sum(if(r.statut=0,0,if(r.facture_avoir='Facture',r.regle,0))) is NULL, 0, sum(if(r.statut=0,0,if(r.facture_avoir='Facture',r.regle,0))))) as \"Impaye\""
                    + ",comm.nom as \"Commercial\" "
                    + "FROM `Facture` d "
                    + "left join client c on (d.id_client=c.numero_client) "
                    + "left join reglement r on d.Num_facture = r.id_facture "
                    + "left join pourcentagebonus pb on pb.id_client=c.numero_client "
                    + "left join commercial comm on pb.id_commercial=comm.id "
                    + "where d.statut=1 and  d.reglement in ('Semi','Non')";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and d.id_client <> 0 ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and d.id_client = 0 ";
                sql = sql + FiltreClauseFrom;
            }

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String idCommercialClause = "";
            if (!(id_commercial.isEmpty())) {
                idCommercialClause = " and pb.id_commercial  = '" + id_commercial + "'";
                sql = sql + idCommercialClause;

            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_facture  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(client.isEmpty())) {
                ClientClause = " and c.numero_client  = '" + client + "'";
                sql = sql + ClientClause;

            }
            sql += "  GROUP by d.Num_facture ORDER BY c.nom, d.date_facture DESC";

            pst = conn.prepareStatement(sql);

            Double TotalTTC = 0.0;
            Double TotalImpaye = 0.0;
            Double Totalregle = 0.0;
            Double TotalTVA = 0.0;
            rs = pst.executeQuery();

            int qteTotalClient = 0;
            String nomclient = "";
            String last_clietnt = "";
            int i = 0;
            while (rs.next()) {
                Object LigneData[] = new Object[7];

                if (i == 0) {
                    LigneData[0] = rs.getString("Client");
                } else if (rs.getString("Client").equals(last_clietnt)) {
                    LigneData[0] = "";
                } else {
                    LigneData[0] = rs.getString("Client");
                }
                last_clietnt = rs.getString("Client");

                //  nomclient = rs.getString("Client");
                // LigneData[0] = nomclient;
                LigneData[1] = rs.getString("num_fact");
                LigneData[2] = rs.getString("date");

                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ttc")));
                LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("regle")));
                LigneData[5] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("Impaye")));
                LigneData[6] = rs.getString("Commercial");

                // df.insertRow(startAdd, LigneData);
                TotalTTC += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ttc"))));
                Totalregle += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("regle"))));
                TotalImpaye += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("Impaye"))));

                // startAdd++;
                df.insertRow(startAdd, LigneData);

                //  qteTotalClient += Integer.valueOf(rs.getString("sum(lb.qte)"));
                startAdd++;
                i++;
            }

            Object LigneData[] = new Object[7];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = ("Total : " + Config.Commen_Proc.formatDouble(Double.valueOf(TotalImpaye)) + "").toString();

            LigneData[6] = "";
            totalGlobal += TotalImpaye;
            df.insertRow(startAdd, LigneData);
            Object LigneDatas[] = new Object[7];

            LigneDatas[0] = "";
            LigneDatas[1] = "";
            LigneDatas[2] = "";
            LigneDatas[3] = "";
            LigneDatas[4] = "";
            LigneDatas[5] = "";
            LigneDatas[6] = "";

            df.insertRow(startAdd + 1, LigneDatas);
            /*  }
            Object LigneData[] = new Object[7];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "";
            LigneData[5] = "Total tous les clients : " + Config.Commen_Proc.formatDouble(Double.valueOf(totalGlobal)) + "";
            LigneData[6] = "";

            df.insertRow(startAdd + 1, LigneData);*/
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatAllClientsParArticleParClient(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date BL");
            columnNames.add("Ref Article");
            columnNames.add("Designation");
            columnNames.add("Quantité");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            int totalGlobal = 0;
            for (String client : lstclient) {
                startAdd = df.getRowCount();
                id_client = client;
                String sql = "";
                sql = "SELECT c.nom,b.date_bl,lb.ref_article,lb.designation_article,sum(lb.qte) FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl) left join article r on lb.ref_article=r.ref left join client c on b.id_client=c.numero_Client left join marque_produit mp on r.id_marque = mp.id WHERE b.statut=1 ";

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
                    refClause = " and lb.ref_article  in (" + refArticle + ")";
                    sql = sql + refClause;

                }
                String marqueClause = "";
                if (!(id_marque.isEmpty())) {
                    marqueClause = " and r.id_marque  = " + id_marque + "";
                    sql = sql + marqueClause;

                }
                /* String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and b.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }*/
                sql += " GROUP by lb.ref_article,b.date_bl  ORDER BY b.date_bl DESC";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                int qteTotalClient = 0;
                String nomclient = "";
                while (rs.next()) {
                    Object LigneData[] = new Object[5];
                    LigneData[0] = rs.getString("c.nom");
                    LigneData[1] = rs.getString("b.date_bl");
                    LigneData[2] = rs.getString("lb.ref_article");
                    LigneData[3] = rs.getString("lb.designation_article");
                    LigneData[4] = rs.getString("sum(lb.qte)");
                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Integer.valueOf(rs.getString("sum(lb.qte)"));
                    startAdd++;

                }

                Object LigneData[] = new Object[5];

                LigneData[0] = "Total";
                LigneData[1] = "";
                LigneData[2] = "";
                LigneData[3] = "";
                LigneData[4] = "Total " + nomclient + " : " + qteTotalClient + "";
                totalGlobal += qteTotalClient;
                df.insertRow(startAdd, LigneData);
                Object LigneDatas[] = new Object[4];

                LigneDatas[0] = "";
                LigneDatas[1] = "";
                LigneDatas[2] = "";
                LigneDatas[3] = "";

                df.insertRow(startAdd + 1, LigneDatas);
            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "Total tous les clients : " + totalGlobal + "";
            df.insertRow(startAdd + 1, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatAllClientsParArticleParPeriode(JTable table, PeriodDatesClass Dates, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            // columnNames.add("Nom Client");
            //   columnNames.add("Date BL");
            columnNames.add("Ref Article");
            //   columnNames.add("Designation");
            // columnNames.add("Quantité");
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat sd = new SimpleDateFormat(pattern);
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            columnNames.add("1 An (" + sd.format(fmt.parse(Dates.getFromDate1Year())) + " - " + sd.format(fmt.parse(Dates.getToDate1Year())) + ")");
            columnNames.add("6 mois (" + sd.format(fmt.parse(Dates.getFromDate6Month())) + " - " + sd.format(fmt.parse(Dates.getToDate6Month())) + ")");
            columnNames.add("3 mois (" + sd.format(fmt.parse(Dates.getFromDate3Month())) + " - " + sd.format(fmt.parse(Dates.getToDate3Month())) + ")");
            columnNames.add("1 mois (" + sd.format(fmt.parse(Dates.getFromDate1Month())) + " - " + sd.format(fmt.parse(Dates.getToDate1Month())) + ")");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            int totalGlobal = 0;
            for (String client : lstclient) {
                startAdd = df.getRowCount();
                id_client = client;
                String sql = "";
                sql = "SELECT lb.ref_article,sum(lb.qte),bl.date_bl FROM `ligne_bl` lb left join bl b on (lb.id_bl=b.Num_bl)   WHERE b.statut=1 ";

                String DateClauseFrom = "";
                if (!(Dates.getMaxDate() == null)) {
                    DateClauseFrom = " and b.date_bl >= '" + Dates.getMaxDate() + "'";
                    sql = sql + DateClauseFrom;
                }

                String DateClauseTo = "";
                if (!(Dates.getMinDate() == null)) {
                    DateClauseTo = " and b.date_bl <= '" + Dates.getMinDate() + "'";
                    sql = sql + DateClauseTo;
                }

                String refClause = "";
                if (!(refArticle.isEmpty())) {
                    refClause = " and lb.ref_article  = '" + refArticle + "'";
                    sql = sql + refClause;

                }
                /*  String marqueClause = "";
                if (!(id_marque.isEmpty())) {
                    marqueClause = " and r.id_marque  = " + id_marque + "";
                    sql = sql + marqueClause;

                }*/
 /* String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and b.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }*/
                sql += " GROUP by lb.ref_article,bl.date_bl ";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                int qteTotalClient = 0;
                String nomclient = "";
                while (rs.next()) {

                    Object LigneData1[] = getIntervals(rs, Dates);

                    Object LigneData[] = new Object[5];
                    LigneData[0] = rs.getString("lb.ref_article");
                    LigneData[1] = rs.getString("sum(lb.qte)");
                    /* LigneData[2] = rs.getString("lb.ref_article");
                    LigneData[3] = rs.getString("lb.designation_article");
                    LigneData[4] = rs.getString("sum(lb.qte)");*/
                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Integer.valueOf(rs.getString("sum(lb.qte)"));
                    startAdd++;

                }

                Object LigneData[] = new Object[5];

                LigneData[0] = "Total";
                LigneData[1] = "";
                LigneData[2] = "";
                LigneData[3] = "";
                LigneData[4] = "Total " + nomclient + " : " + qteTotalClient + "";
                totalGlobal += qteTotalClient;
                df.insertRow(startAdd, LigneData);
                Object LigneDatas[] = new Object[4];

                LigneDatas[0] = "";
                LigneDatas[1] = "";
                LigneDatas[2] = "";
                LigneDatas[3] = "";

                df.insertRow(startAdd + 1, LigneDatas);
            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "Total tous les clients : " + totalGlobal + "";
            df.insertRow(startAdd + 1, LigneData);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public String queryInterval(String refArticle, String FromDate, String ToDate) {

        String sql = "SELECT lb.ref_article,sum(lb.qte),b.date_bl "
                + "FROM `ligne_bl` lb "
                + "left join bl b on (lb.id_bl=b.Num_bl)  "
                + "left join ligne_facture lf on lf.num_bl = b.num_bl "
                + "left join facture f on f.num_facture = lf.num_facture "
                + "WHERE b.statut=1 ";

        String DateClauseFrom = "";
        /* if (!(FromDate == null)) {
            DateClauseFrom = " and b.date_bl >= '" + FromDate + "'";
            sql = sql + DateClauseFrom;
        }

        String DateClauseTo = "";
        if (!(ToDate == null)) {
            DateClauseTo = " and b.date_bl <= '" + ToDate + "'";
            sql = sql + DateClauseTo;
        }
         */
        if (!(FromDate.isEmpty())) {
            DateClauseFrom = " and (b.date_bl >= '" + FromDate + "' or f.date_facture >= '" + FromDate + "')";
            sql = sql + DateClauseFrom;
        }

        String DateClauseTo = "";
        if (!(ToDate.isEmpty())) {
            DateClauseTo = " and (b.date_bl <= '" + ToDate + "'  or f.date_facture <= '" + ToDate + "')";
            sql = sql + DateClauseTo;
        }

        String refClause = "";
        if (!(refArticle.isEmpty())) {
            refClause = " and lb.ref_article  = '" + refArticle + "'";
            sql = sql + refClause;

        }
        /*  String marqueClause = "";
                if (!(id_marque.isEmpty())) {
                    marqueClause = " and r.id_marque  = " + id_marque + "";
                    sql = sql + marqueClause;

                }*/
 /* String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and b.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }*/
        sql += " GROUP by lb.ref_article ";
        return sql;
    }

    public void addRowsStatAchatAllClientsParArticleParPeriodeTest(JTable table, PeriodDatesClass Dates, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            // columnNames.add("Nom Client");
            //   columnNames.add("Date BL");
            columnNames.add("Ref Article");
            //   columnNames.add("Designation");
            // columnNames.add("Quantité");
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat sd = new SimpleDateFormat(pattern);
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            /* columnNames.add("1 An (" + sd.format(fmt.parse(Dates.getFromDate1Year())) + " - " + sd.format(fmt.parse(Dates.getToDate1Year())) + ")");
            columnNames.add("6 mois (" + sd.format(fmt.parse(Dates.getFromDate6Month())) + " - " + sd.format(fmt.parse(Dates.getToDate6Month())) + ")");
            columnNames.add("3 mois (" + sd.format(fmt.parse(Dates.getFromDate3Month())) + " - " + sd.format(fmt.parse(Dates.getToDate3Month())) + ")");
            columnNames.add("1 mois (" + sd.format(fmt.parse(Dates.getFromDate1Month())) + " - " + sd.format(fmt.parse(Dates.getToDate1Month())) + ")");
             */
            columnNames.add("1 an");
            columnNames.add("6 mois");
            columnNames.add("3 mois");
            columnNames.add("1 mois");

            columnNames.add("Total");
            columnNames.add("vide");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            int totalGlobal = 0;
            ResultSet rs1Y = null;
            ResultSet rs6 = null;
            ResultSet rs3 = null;
            ResultSet rs1m = null;
            for (String client : lstclient) {
                startAdd = df.getRowCount();
                id_client = client;
                /*   String sqlOneYear = queryInterval(client, Dates.getFromDate1Year(), Dates.getToDate1Year());
                String sql6Months = queryInterval(client, Dates.getFromDate6Month(), Dates.getToDate6Month());
                String sql3Months = queryInterval(client, Dates.getFromDate3Month(), Dates.getToDate3Month());
                String sqlOneMonth = queryInterval(client, Dates.getFromDate1Month(), Dates.getToDate1Month());*/
                String ref = client;
                int sqlOneYearInt = addRowsStatAchatParArticle(table, Dates.getFromDate1Year(), Dates.getToDate1Year(), "", ref, "", df);
                int sql6MonthsInt = addRowsStatAchatParArticle(table, Dates.getFromDate6Month(), Dates.getToDate6Month(), "", ref, "", df);
                int sql3MonthsInt = addRowsStatAchatParArticle(table, Dates.getFromDate3Month(), Dates.getToDate3Month(), "", ref, "", df);
                int sqlOneMonthInt = addRowsStatAchatParArticle(table, Dates.getFromDate1Month(), Dates.getToDate1Month(), "", ref, "", df);

                /*  rs1Y = conn.prepareStatement(sqlOneYear).executeQuery();
                rs6 = conn.prepareStatement(sql6Months).executeQuery();
                rs3 = conn.prepareStatement(sql3Months).executeQuery();
                rs1m = conn.prepareStatement(sqlOneMonth).executeQuery();*/
                int qteTotalClientrs1Y = 0;
                String nomclient = "";
                Object LigneData2[] = new Object[7];
                LigneData2[0] = client;
                String qte1Y = "0";
                /*    while (rs1Y.next()) {

                    // Object LigneData1[] = getIntervals(rs, Dates);
                    qte1Y = rs1Y.getString("sum(lb.qte)");

                    // qteTotalClientrs1Y += Integer.valueOf(rs1Y.getString("sum(lb.qte)"));
                }*/
                //  LigneData2[1] = qte1Y;
                LigneData2[1] = String.valueOf(sqlOneYearInt);

                String qters6 = "0";
                /*   while (rs6.next()) {

                    // Object LigneData1[] = getIntervals(rs, Dates);
                    qters6 = rs6.getString("sum(lb.qte)");

                    // qteTotalClientrs1Y += Integer.valueOf(rs1Y.getString("sum(lb.qte)"));
                }*/
                // LigneData2[2] = qters6;
                LigneData2[2] = String.valueOf(sql6MonthsInt);

                String qters3 = "0";
                /* while (rs3.next()) {

                    // Object LigneData1[] = getIntervals(rs, Dates);
                    qters3 = rs3.getString("sum(lb.qte)");

                    // qteTotalClientrs1Y += Integer.valueOf(rs1Y.getString("sum(lb.qte)"));
                }*/
                //LigneData2[3] = qters3;
                LigneData2[3] = String.valueOf(sql3MonthsInt);

                String qters1m = "0";

                /*  while (rs1m.next()) {

                    // Object LigneData1[] = getIntervals(rs, Dates);
                    qters1m = rs1m.getString("sum(lb.qte)");

                    // qteTotalClientrs1Y += Integer.valueOf(rs1Y.getString("sum(lb.qte)"));
                }*/
                //LigneData2[4] = qters1m;
                LigneData2[4] = String.valueOf(sqlOneMonthInt);

                LigneData2[5] = String.valueOf(Integer.valueOf(sqlOneMonthInt) + Integer.valueOf(sql3MonthsInt) + Integer.valueOf(sql6MonthsInt) + Integer.valueOf(sqlOneYearInt));
                //  LigneData2[5] = String.valueOf(Integer.valueOf(qters1m) + Integer.valueOf(qters3) + Integer.valueOf(qters6) + Integer.valueOf(qte1Y));
                LigneData2[6] = "";

                df.insertRow(startAdd, LigneData2);
                startAdd++;
                /*Object LigneData[] = new Object[5];

                LigneData[0] = "Total";
                LigneData[1] = "";
                LigneData[2] = "";
                LigneData[3] = "";
                LigneData[4] = "Total " + nomclient + " : " + qteTotalClientrs1Y + "";
                totalGlobal += qteTotalClientrs1Y;
                df.insertRow(startAdd, LigneData);
                Object LigneDatas[] = new Object[4];

                LigneDatas[0] = "";
                LigneDatas[1] = "";
                LigneDatas[2] = "";
                LigneDatas[3] = "";

                df.insertRow(startAdd + 1, LigneDatas);*/
            }
            /*  Object LigneData[] = new Object[5];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "Total tous les clients : " + totalGlobal + "";
            df.insertRow(startAdd + 1, LigneData);*/
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Object[] getIntervals(ResultSet rs, PeriodDatesClass Dates) {
        try {

            int qte1Year = 0;
            int qte6Months = 0;
            int qte3Months = 0;
            int qte1Months = 0;
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date_bl = fmt.parse(rs.getString("lb.date_bl"));
            if (date_bl.after(fmt.parse(Dates.getFromDate1Year())) && date_bl.before(fmt.parse(Dates.getToDate1Year()))) {
                //  qte1Year
            }
        } catch (Exception e) {
        }

        return null;
    }

    public DefaultTableModel afficherBLDepot(JTable table, String FromDate, String ToDate, String NumDevis, String Depotsrc, String DepotDest) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT p.nom, p2.nom,d.`Num_bl_depot`,DATE_FORMAT(d.`date_bl_depot`,'%d/%m/%Y'),d.`Total_TTC`  FROM `bl_depot` d left join depot p on d.id_depot_src=p.id left join depot p2 on d.id_depot_dest=p2.id ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_bl_depot >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_bl_depot <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            /* String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.total_TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }*/
 /*  String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.total_TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }*/
            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_bl_depot  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause1 = "";
            if (!(DepotDest.isEmpty())) {
                ClientClause1 = " and d.id_depot_dest =" + DepotDest + "";
                sql = sql + ClientClause1;

            }

            String ClientClause = "";
            if (!(Depotsrc.isEmpty())) {
                ClientClause = " and d.id_depot_src =" + Depotsrc + "";
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
            df = buildTableModel(rs);
            table.setModel(df);

            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 4);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 4);
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
                Logger.getLogger(BLDao.class
                        .getName()).log(Level.SEVERE, null, ex);
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

    public void afficherFacture(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient, String type_filtre) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;

            /*  if ((type_filtre.equals("passager"))) {
                sql = "SELECT 'Client Passager' as `Client`,d.`Num_facture`,DATE_FORMAT(d.`date_facture`,'%d/%m/%Y'),d.`TTC`,d.passager FROM `Facture` d left join client c  on (d.id_client=c.numero_client) where d.statut=1 ";
            } else {*/
            sql = "SELECT  if(d.passager is null,c.nom,'Client Passager') as `Client`,d.`Num_facture`,DATE_FORMAT(d.`date_facture`,'%d/%m/%Y'),d.`TTC`,d.passager,d.remise FROM `Facture` d left join client c  on (d.id_client=c.numero_client) where d.statut=1 ";
            //  }

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and d.id_client <> 0 ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and d.id_client = 0 ";
                sql = sql + FiltreClauseFrom;
            }

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_facture  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom  like '%" + NomClient + "%'";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY d.`Num_facture` DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU1 = df.getValueAt(j, 5);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU1.toString())), j, 5);

                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
            table.setModel(df);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");

            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherFactureDecharge(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient, String type_filtre, String withDecharge) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;

            /*  if ((type_filtre.equals("passager"))) {
                sql = "SELECT 'Client Passager' as `Client`,d.`Num_facture`,DATE_FORMAT(d.`date_facture`,'%d/%m/%Y'),d.`TTC`,d.passager FROM `Facture` d left join client c  on (d.id_client=c.numero_client) where d.statut=1 ";
            } else {*/
            sql = "SELECT  if(d.passager is null,c.nom,'Client Passager') as `Client`,d.`Num_facture`,DATE_FORMAT(d.`date_facture`,'%d/%m/%Y'),d.`TTC`,d.passager , if(d.decharge = '0','Non','Oui') as Decharge FROM `Facture` d left join client c  on (d.id_client=c.numero_client) where d.statut=1 ";
            //  }

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreDechargeClauseFrom = "";
            if ((!withDecharge.isEmpty())) {
                FiltreDechargeClauseFrom = " and d.decharge = '" + withDecharge + "' ";
                sql = sql + FiltreDechargeClauseFrom;
            }

            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and d.id_client <> 0 ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and d.id_client = 0 ";
                sql = sql + FiltreClauseFrom;
            }

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_facture  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom  like '%" + NomClient + "%'";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY d.`Num_facture` DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
            table.setModel(df);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");

            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherFactureSuivi(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient, String type_filtre, String id_commercial) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT c.nom as `Client`,d.`Num_facture`,DATE_FORMAT(d.`date_facture`,'%d/%m/%Y'),d.`TTC`,if(sum(r.regle) is NULL, 0, sum(r.regle)) as \"regle\", (d.`TTC`-if(sum(r.regle) is NULL, 0, sum(r.regle))) as \"Impaye\",comm.nom as \"Commercial\" "
                    + "FROM `Facture` d "
                    + "left join client c on (d.id_client=c.numero_client) "
                    + "left join reglement r on d.Num_facture = r.id_facture "
                    + "left join pourcentagebonus pb on pb.id_client=c.numero_client "
                    + "left join commercial comm on pb.id_commercial=comm.id "
                    + "where d.statut=1 and d.reglement in ('Semi','Non')";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and d.id_client <> 0 ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and d.id_client = 0 ";
                sql = sql + FiltreClauseFrom;
            }

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String idCommercialClause = "";
            if (!(id_commercial.isEmpty())) {
                idCommercialClause = " and pb.id_commercial  = '" + id_commercial + "'";
                sql = sql + idCommercialClause;

            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_facture  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom  = '" + NomClient + "'";
                sql = sql + ClientClause;

            }
            sql += "  GROUP by d.Num_facture ORDER BY c.nom, d.date_facture DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Client");
            columnNames.add("Num Facture");

            columnNames.add("Date Facture");
            columnNames.add("Total TTC");
            columnNames.add("Reglé");
            columnNames.add("Impayée");
            columnNames.add("Commercial");
            BLDao b = new BLDao();
            DefaultTableModel df = b.buildTableModelColumnName(rs, columnNames);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU2 = df.getValueAt(j, 5);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU2.toString())), j, 5);
                Object PU1 = df.getValueAt(j, 4);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU1.toString())), j, 4);
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
            table.setModel(df);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");

            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public DefaultTableModel SuiviReglement(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient, String type_filtre, String id_commercial) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            if (!NomClient.isEmpty()) {
                addRowsSuiviReglementByClient(table, FromDate, ToDate, FromMontant, ToMontant, NumDevis, NomClient, type_filtre, id_commercial, df);
            } else {
                //table,  FromDate,  ToDate,  FromMontant,  ToMontant,  NumDevis,  NomClient,  type_filtre,  id_commercial
                String clients = AllClientSuiviReglementList(table, FromDate, ToDate, FromMontant, ToMontant, NumDevis, NomClient, type_filtre, id_commercial);
                String[] lstClient = clients.split(",");
                addRowsSuiviReglementByClient(table, FromDate, ToDate, FromMontant, ToMontant, NumDevis, NomClient, type_filtre, id_commercial, df, lstClient);

            }
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

    public void afficherFactureSuiviGroupedByClient(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient, String type_filtre, String id_commercial) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT c.nom as `Client`,d.`Num_facture` as num_fact,DATE_FORMAT(d.`date_facture`,'%d/%m/%Y') as date,d.`TTC` as ttc,if(sum(r.regle) is NULL, 0, sum(r.regle)) as \"regle\", (d.`TTC`-if(sum(r.regle) is NULL, 0, sum(r.regle))) as \"Impaye\",comm.nom as \"Commercial\" "
                    + "FROM `Facture` d "
                    + "left join client c on (d.id_client=c.numero_client) "
                    + "left join reglement r on d.Num_facture = r.id_facture "
                    + "left join pourcentagebonus pb on pb.id_client=c.numero_client "
                    + "left join commercial comm on pb.id_commercial=comm.id "
                    + "where d.statut=1 and d.reglement in ('Semi','Non')";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and d.id_client <> 0 ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and d.id_client = 0 ";
                sql = sql + FiltreClauseFrom;
            }

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String idCommercialClause = "";
            if (!(id_commercial.isEmpty())) {
                idCommercialClause = " and pb.id_commercial  = '" + id_commercial + "'";
                sql = sql + idCommercialClause;

            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_facture  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom  = '" + NomClient + "'";
                sql = sql + ClientClause;

            }
            sql += "  GROUP by d.Num_facture ORDER BY c.nom, d.date_facture DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Client");
            columnNames.add("Num Facture");

            columnNames.add("Date Facture");
            columnNames.add("Total TTC");
            columnNames.add("Reglé");
            columnNames.add("Impayée");
            columnNames.add("Commercial");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            table.setModel(df);

            Double TotalTTC = 0.0;
            Double TotalImpaye = 0.0;
            Double Totalregle = 0.0;
            Double TotalTVA = 0.0;
            int startAdd = 0;
            int nbFact = 0;
            int nbAvoir = 0;
            int i = 0;
            String last_clietnt = "";
            while (rs.next()) {

                Object LigneData[] = new Object[7];

                if (i == 0) {
                    LigneData[0] = rs.getString("Client");
                } else if (rs.getString("Client").equals(last_clietnt)) {
                    LigneData[0] = "";
                } else {
                    LigneData[0] = rs.getString("Client");
                }
                last_clietnt = rs.getString("Client");
                LigneData[1] = rs.getString("num_fact");
                LigneData[2] = rs.getString("date");

                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ttc")));
                LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("regle")));
                LigneData[5] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("Impaye")));
                LigneData[6] = rs.getString("Commercial");

                df.insertRow(startAdd, LigneData);
                TotalTTC += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ttc"))));
                Totalregle += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("regle"))));
                TotalImpaye += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("Impaye"))));
                i++;
                startAdd++;

            }

            Object LigneData[] = new Object[7];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";

            LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalTTC));

            LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(Totalregle));//TotalHT
            LigneData[5] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalImpaye));
            LigneData[6] = "";

            df.insertRow(startAdd, LigneData);
            table.setModel(df);

            /* BLDao b = new BLDao();
            DefaultTableModel df = b.buildTableModelColumnName(rs, columnNames);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU2 = df.getValueAt(j, 5);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU2.toString())), j, 5);
                Object PU1 = df.getValueAt(j, 4);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU1.toString())), j, 4);
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
            table.setModel(df);*/
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");

            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherFactureAlert(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String NomClient, String type_filtre, String id_commercial) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT c.nom as `Client`,d.`Num_facture`,DATE_FORMAT(d.`date_facture`,'%d/%m/%Y'),d.`TTC`,if(sum(r.regle) is NULL, 0, sum(r.regle)) as \"regle\", (d.`TTC`-if(sum(r.regle) is NULL, 0, sum(r.regle))) as \"Impaye\",comm.nom as \"Commercial\" "
                    + " , if((DATEDIFF(CURDATE(),d.date_facture)) > c.echeance_payement,\"Oui\",\"Non\")  as depasse, CONVERT((DATEDIFF(CURDATE(),d.date_facture)), CHAR(50)) as Ecarts,c.echeance_payement "
                    + "FROM `Facture` d "
                    + "left join client c on (d.id_client=c.numero_client) "
                    + "left join reglement r on d.Num_facture = r.id_facture "
                    + "left join pourcentagebonus pb on pb.id_client=c.numero_client "
                    + "left join commercial comm on pb.id_commercial=comm.id "
                    + "where d.statut=1 and d.reglement in ('Semi','Non') and (DATEDIFF(CURDATE(),d.date_facture)) > c.echeance_payement ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String FiltreClauseFrom = "";
            if ((type_filtre.equals("client"))) {
                FiltreClauseFrom = " and d.id_client <> 0 ";
                sql = sql + FiltreClauseFrom;
            } else if ((type_filtre.equals("passager"))) {
                FiltreClauseFrom = " and d.id_client = 0 ";
                sql = sql + FiltreClauseFrom;
            }

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String idCommercialClause = "";
            if (!(id_commercial.isEmpty())) {
                idCommercialClause = " and pb.id_commercial  = '" + id_commercial + "'";
                sql = sql + idCommercialClause;

            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_facture  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(NomClient.isEmpty())) {
                ClientClause = " and c.nom  = '" + NomClient + "'";
                sql = sql + ClientClause;

            }
            sql += "  GROUP by d.Num_facture ORDER BY d.`id` DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            /*    DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }*/
            Vector<String> columnNames = new Vector<String>();

            columnNames.add("Nom Client");
            columnNames.add("Num Facture");

            columnNames.add("Date Facture");
            columnNames.add("Total TTC");
            columnNames.add("Reglé");
            columnNames.add("Impayée");
            columnNames.add("Commercial");
            columnNames.add("Dépassé O/N");
            columnNames.add("Ecarts");
            columnNames.add("Echéance");

            BLDao b = new BLDao();
            DefaultTableModel df = b.buildTableModelColumnName(rs, columnNames);
            table.setModel(df);
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU2 = df.getValueAt(j, 5);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU2.toString())), j, 5);
                Object PU1 = df.getValueAt(j, 4);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU1.toString())), j, 4);
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
            table.setModel(df);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");

            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherFactureAchat(JTable table, String FromDate, String ToDate, String FromMontant, String ToMontant, String NumDevis, String id_four) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            sql = "SELECT f.nom as `Client`,d.`Num_facture_achat`,DATE_FORMAT(d.`date_facture_achat`,'%d/%m/%Y'),d.`TTC` FROM `Facture_achat` d , fournisseur f  where d.id_fournisseur=f.id and d.statut = 1 ";

            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and d.date_facture_achat >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and d.date_facture_achat <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String MontantClauseFrom = "";
            if (!(FromMontant.isEmpty())) {
                MontantClauseFrom = " and d.TTC >= " + FromMontant + "";
                sql = sql + MontantClauseFrom;
            }
            String MontantClauseTo = "";
            if (!(ToMontant.isEmpty())) {
                MontantClauseTo = " and d.TTC <= " + ToMontant + "";
                sql = sql + MontantClauseTo;
            }

            String NumDevisClause = "";
            if (!(NumDevis.isEmpty())) {
                NumDevisClause = " and d.Num_facture_achat  like '%" + NumDevis + "%'";
                sql = sql + NumDevisClause;

            }
            String ClientClause = "";
            if (!(id_four.isEmpty())) {
                ClientClause = " and f.id_fournisseur  = " + id_four + "";
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
            for (int j = 0; j < table.getRowCount(); j++) {
                Object PU = df.getValueAt(j, 3);
                df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);
            }
            table.setModel(df);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");

            } catch (SQLException ex) {
                Logger.getLogger(BLDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
