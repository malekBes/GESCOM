/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stat;

import BL.BLDao;
import static BL.BLDao.buildTableModel;
import Config.Commen_Proc;
import Conn.DataBase_connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Malek
 */
public class StatDAO {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String year;

    public DefaultTableModel EvolutionCaByClient(JTable table, String FromDate, String ToDate, String Source, String groupeby, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT  c.nom,extract(month from facture.date_facture) as 'month', extract(year from facture.date_facture) as annee,sum(facture.ht) as ca, (select sum(facture.ht) from  facture where extract(month from facture.date_facture) = month and id_client<>0 and statut=1 ) as ca_month,  ROUND((sum(facture.ht) / (select sum(facture.ht) from  facture where statut=1 and extract(month from facture.date_facture) = month ))*100,2) as evolution"
                    + " FROM facture facture left join client c on facture.id_client=c.numero_Client  where 1=1 and id_client <> 0 and facture.statut =1 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and facture.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and facture.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " GROUP BY /*id_client,*/ month ORDER BY /*c.nom,*/ month asc";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Client");
            columnNames.add("Date");
            columnNames.add("CA");
            columnNames.add("CA / CA Mensuel");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            table.setModel(df);

            Double TotalTTC = 0.0;
            Double TotalHT = 0.0;

            while (rs.next()) {

                Object LigneData[] = new Object[4];

                if (id_client.isEmpty()) {
                    LigneData[0] = "Tous";
                } else {
                    LigneData[0] = rs.getString("c.nom");
                }

                String dt = rs.getString("month");
                if (rs.getString("month").length() == 1) {
                    dt = "0" + rs.getString("month");
                }
                LigneData[1] = dt + "/" + rs.getString("annee");

                LigneData[2] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ca")));

                LigneData[3] = rs.getString("evolution") + "%";

                df.insertRow(startAdd, LigneData);

                TotalHT += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ca"))));
                startAdd++;

            }

            Object LigneData[] = new Object[4];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHT));
            LigneData[3] = "";

            df.insertRow(startAdd, LigneData);
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

    public DefaultTableModel EvolutionQteByClient(JTable table, String FromDate, String ToDate, String Source, String groupeby, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT  c.nom,extract(month from facture.date_facture) as 'month', extract(year from facture.date_facture) as annee, sum(lb.qte) as qte, "
                    + "(select sum(lb.qte) from  facture facture left join ligne_facture lf on lf.num_facture = facture.num_facture left join ligne_bl lb on lb.id_bl=lf.num_bl where extract(month from facture.date_facture) = month and facture.id_client<>0 ) as qte_month, "
                    + " ROUND((sum(lb.qte) / (select sum(lb.qte) from  facture facture left join ligne_facture lf on lf.num_facture = facture.num_facture left join ligne_bl lb on lb.id_bl=lf.num_bl where extract(month from facture.date_facture) = month ))*100,2) as evolution FROM facture facture left join ligne_facture lf on lf.num_facture = facture.num_facture left join ligne_bl lb on lb.id_bl=lf.num_bl left join client c on facture.id_client=c.numero_Client  where 1=1 and id_client <> 0 ";
            /*  
            sql = "SELECT  c.nom,extract(month from bl.date_bl) as 'month', extract(year from bl.date_bl) as annee, sum(lb.qte) as qte, "
                    + "(select sum(lb.qte) from ligne_bl lb left join bl bl on lb.id_bl = bl.num_bl where extract(month from bl.date_bl) = month and bl.id_client<>0 ) as qte_month, "
                    + " ROUND((sum(lb.qte) / (select sum(lb.qte) from ligne_bl lb left join bl bl on lb.id_bl = bl.num_bl where extract(month from bl.date_bl) = month ))*100,2) as evolution FROM ligne_bl lb left join bl bl on lb.id_bl = bl.num_bl  left join client c on bl.id_client=c.numero_Client  where 1=1 and id_client <> 0 ";
             */
            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and facture.date_facture >= '" + FromDate + "'";// chnaged from facture
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and facture.date_facture <= '" + ToDate + "'";// chnaged from facture
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and id_client  = " + id_client + "";
                sql = sql + ClientClause;
            }
            sql += " GROUP BY month,annee,c.nom ORDER BY month asc";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Client");
            columnNames.add("Date");
            columnNames.add("Qte");
            columnNames.add("Qte / Qte Mensuel");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            table.setModel(df);

            Double TotalTTC = 0.0;
            int TotalHT = 0;

            while (rs.next()) {

                if (rs.getString("qte") != null) {
                    if (!"0".equals(rs.getString("qte"))) {

                        Object LigneData[] = new Object[4];

                        LigneData[0] = rs.getString("c.nom");

                        String dt = rs.getString("month");
                        if (rs.getString("month").length() == 1) {
                            dt = "0" + rs.getString("month");
                        }
                        LigneData[1] = dt + "/" + rs.getString("annee");
                        String qte = "0";
                        if (rs.getString("qte") != null) {
                            qte = rs.getString("qte");
                        }
                        LigneData[2] = qte;//Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("qte")));
                        String eval = "0";
                        if (rs.getString("evolution") != null) {
                            eval = rs.getString("evolution");
                        }
                        LigneData[3] = eval + "%";

                        df.insertRow(startAdd, LigneData);

                        TotalHT += Integer.valueOf(qte);//Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("qte"))));
                        startAdd++;
                    }
                }

            }

            Object LigneData[] = new Object[4];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHT));
            LigneData[3] = "";

            df.insertRow(startAdd, LigneData);
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

    public DefaultTableModel CaByClient(JTable table, String FromDate, String ToDate, String Source, String groupeby, String id_client, boolean exonoreTVA) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT c.nom,CONVERT(count(*), CHAR(50)) as nbfact,CONVERT((select count(*) from avoir  where num_facture=f.num_facture), CHAR(50)) as nbavoir ,sum(av.total_ht),sum(f.HT),sum(f.TTC),sum(f.tva) as tva FROM `facture` f left join client c on (c.numero_Client=f.id_client) left join avoir av on (f.num_facture=av.num_facture) where 1=1 and f.statut=1  ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and f.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and f.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and f.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }

            if (exonoreTVA) {
                sql = sql + " and f.tva = 0";
            }
            sql += " group by c.nom  ORDER BY f.date_facture DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Nb Fact");
            columnNames.add("Total TTC");
            columnNames.add("Nb Avoir");
            columnNames.add("Total Avoir");
            columnNames.add("Total HT");
            columnNames.add("Total TVA");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            table.setModel(df);

            Double TotalTTC = 0.0;
            Double TotalHT = 0.0;
            Double TotalHTAvoir = 0.0;
            Double TotalTVA = 0.0;

            int nbFact = 0;
            int nbAvoir = 0;
            while (rs.next()) {

                Object LigneData[] = new Object[7];

                LigneData[0] = rs.getString("c.nom");

                LigneData[1] = String.valueOf(rs.getString("nbfact"));
                LigneData[2] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("sum(f.TTC)")));

                LigneData[3] = String.valueOf(rs.getString("nbavoir"));
                String HTAvoir = rs.getString("sum(av.total_ht)") == null ? "0" : rs.getString("sum(av.total_ht)");

                LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(HTAvoir));
                LigneData[5] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("sum(f.HT)")));
                LigneData[6] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("tva")));

                df.insertRow(startAdd, LigneData);
                nbFact += Integer.valueOf(rs.getString("nbfact"));
                nbAvoir += Integer.valueOf(rs.getString("nbavoir"));
                TotalTTC += Double.valueOf(Double.valueOf(rs.getString("sum(f.TTC)")));
                TotalHT += Double.valueOf(Double.valueOf(rs.getString("sum(f.HT)")));
                TotalHTAvoir += Double.valueOf(Double.valueOf(HTAvoir));
                TotalTVA += Double.valueOf(Double.valueOf(rs.getString("tva")));
                startAdd++;

            }

            Object LigneData[] = new Object[7];

            LigneData[0] = "Total";
            LigneData[1] = String.valueOf(nbFact);
            LigneData[2] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalTTC));
            LigneData[3] = String.valueOf(nbAvoir);

            LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHTAvoir));
            Double d = ((100 - Double.valueOf(Commen_Proc.TVAVal))) / 100;
            LigneData[5] = String.valueOf(Double.valueOf(Config.Commen_Proc.formatDouble(TotalTTC)) * d);//TotalHT
            LigneData[6] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalTVA));

            df.insertRow(startAdd, LigneData);
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

    public DefaultTableModel CaByCommercial(JTable table, String FromDate, String ToDate, String Source, String groupeby, String id_Commercial, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {

            String sql;
            //  sql = "SELECT c.nom,count(*),(select count(*) from avoir  where num_facture=f.num_facture) as nbavoir ,sum(av.total_ht),sum(f.HT),sum(f.TTC),sum(f.tva) as tva FROM `facture` f left join client c on (c.numero_Client=f.id_client) left join avoir av on (f.num_facture=av.num_facture) left join pourcentagebonus pb on pb.id_client=c.numero_Client where 1=1  ";
            // sql = "SELECT DISTINCT f.HT,f.num_facture,comm.nom FROM facture f left join ligne_facture lf on lf.num_facture=f.num_facture left join `bl` bl on lf.num_bl=bl.Num_bl left join commercial comm on comm.Id=bl.id_commercial WHERE 1=1 ";
            sql = "SELECT DISTINCT f.HT,f.TTC,f.num_facture,c.nom,comm.nom,pb.Pourcentage, DATE_FORMAT(f.date_facture,'%d/%m/%Y') as date_facture,"
                    + "if( ((type_comm.valeur/100)*f.HT) is null,"
                    + "0.0,"
                    + "((type_comm.valeur/100)*f.HT)) as ca_commercial "
                    + "FROM facture f left join ligne_facture lf on lf.num_facture=f.num_facture "
                    + "left join `bl` bl on lf.num_bl=bl.Num_bl "
                    + "left join client c on c.numero_Client=f.id_client "
                    + "left join pourcentagebonus pb on pb.id_Client=f.id_client "
                    + "left join type_commercial type_comm on type_comm.nom=pb.Pourcentage "
                    + "left join commercial comm on pb.Id_Commercial= comm.Id "
                    + "WHERE 1=1 and f.statut = 1";
            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and f.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and f.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String clientClause = "";
            if (!(id_client.isEmpty())) {
                clientClause = " and f.id_client  =" + id_client + "";
                sql = sql + clientClause;

            }

            String commercialClause = "";
            if (!(id_Commercial.isEmpty())) {
                commercialClause = " and comm.nom  = '" + id_Commercial + "'";
                sql = sql + commercialClause;

            }
            sql += " ORDER BY f.date_facture DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;
            int startAdd = 0;

//comm.nom  c.nom   f.num_facture  pb.Pourcentage  f.HT
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Commercial");
            columnNames.add("Client");

            columnNames.add("Num Fact");
            columnNames.add("Date Facture");
            columnNames.add("Type Commerial");
            // columnNames.add("Total Avoir");
            columnNames.add("Total HT");

            columnNames.add("CA Commercial");
//comm.nom,f.num_facture,bl.Num_bl,bl.id_commercial,sum(f.HT)
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            table.setModel(df);

            Double TotalCaCommercial = 0.0;
            Double TotalHT = 0.0;
            Double TotalHTAvoir = 0.0;
            Double TotalTVA = 0.0;
            Double TotalTTC = 0.0;

            int nbFact = 0;
            int nbAvoir = 0;
            while (rs.next()) {

                Object LigneData[] = new Object[7];
//comm.nom  c.nom   f.num_facture  pb.Pourcentage  f.HT

                LigneData[0] = rs.getString("comm.nom");
                LigneData[1] = rs.getString("c.nom");

                LigneData[2] = rs.getString("f.num_facture");
                LigneData[3] = rs.getString("date_facture");

                LigneData[4] = rs.getString("pb.Pourcentage");

                LigneData[5] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("f.HT"))).toString();

                if (!rs.getString("ca_commercial").isEmpty()) {
                    LigneData[6] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ca_commercial")));
                } else {
                    LigneData[6] = "0.0";
                }
                //  String HTAvoir = rs.getString("sum(av.total_ht)") == null ? "0" : rs.getString("sum(av.total_ht)");
                //   LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(HTAvoir));
                //    LigneData[5] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("sum(f.HT)")));
                //    LigneData[6] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("tva")));
                df.insertRow(startAdd, LigneData);
                //   nbFact += Integer.valueOf(rs.getString("count(*)"));
                //    nbAvoir += Integer.valueOf(rs.getString("nbavoir"));
                TotalCaCommercial += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("ca_commercial"))));
                TotalHT += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("f.HT"))));

                TotalTTC += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("f.TTC"))));

                //   TotalHTAvoir += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(HTAvoir)));
                //   TotalTVA += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("tva"))));
                startAdd++;

            }

            Object LigneData[] = new Object[7];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = "";
            LigneData[4] = "";

            Double d = ((100 - Double.valueOf(Commen_Proc.TVAVal))) / 100;
            LigneData[5] = String.valueOf(Double.valueOf(Config.Commen_Proc.formatDouble(TotalTTC)) * d);//TotalHT

            // LigneData[5] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHT));
            LigneData[6] = Config.Commen_Proc.formatDouble(TotalCaCommercial);//TotalHT
            //    LigneData[6] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalTVA));
            df.insertRow(startAdd, LigneData);
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

    static Vector<Vector<Object>> data;

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();

        columnNames.add("Nom Client");
        columnNames.add("Nb Fact");
        columnNames.add("Total HT");
        columnNames.add("Total TTC");
        int columnCount = metaData.getColumnCount();
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

    public DefaultTableModel afficherFactureEtat(JTable table, String FromDate, String ToDate, String Source, String groupeby, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            if (groupeby.equals("Client")) {

                // if (!id_client.isEmpty()) {
                addRowsStatAchatFacture(table, FromDate, ToDate, Source, groupeby, id_client, df);
                /* } else {
                    String clients = AllClientsList(table, FromDate, ToDate, Source, groupeby, id_client, df);
                    String[] lstClient = clients.split(",");
                    addRowsStatAchatAllClientsFacture(table, FromDate, ToDate, Source, groupeby, id_client, df, lstClient);

                }*/
            } else if (groupeby.equals("Date")) {
                if (!id_client.isEmpty()) {
                    addRowsStatAchatbyDateFacture(table, FromDate, ToDate, Source, groupeby, id_client, df);
                } else {
                    String clients = AllDatesList(table, "facture", "date_facture", FromDate, ToDate, Source, groupeby, id_client, df);
                    String[] lstClient = clients.split(",");
                    addRowsStatAchatAllDatesFacture(table, FromDate, ToDate, Source, groupeby, id_client, df, lstClient);

                }
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

    public DefaultTableModel afficherBLEtat(JTable table, String FromDate, String ToDate, String Source, String groupeby, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            if (groupeby.equals("Client")) {

                //  if (!id_client.isEmpty()) {
                addRowsStatAchatBL(table, FromDate, ToDate, Source, groupeby, id_client, df);
                /*   } else {
                    String clients = AllClientsList(table, FromDate, ToDate, Source, groupeby, id_client, df);
                    String[] lstClient = clients.split(",");
                    addRowsStatAchatAllClientsBL(table, FromDate, ToDate, Source, groupeby, id_client, df, lstClient);

                }*/
            } else if (groupeby.equals("Date")) {
                if (!id_client.isEmpty()) {
                    addRowsStatAchatbyDateBL(table, FromDate, ToDate, Source, groupeby, id_client, df);
                } else {
                    String clients = AllDatesList(table, "bl", "date_bl", FromDate, ToDate, Source, groupeby, id_client, df);
                    String[] lstClient = clients.split(",");
                    addRowsStatAchatAllDatesBL(table, FromDate, ToDate, Source, groupeby, id_client, df, lstClient);

                }
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

    public DefaultTableModel afficherDevisEtat(JTable table, String FromDate, String ToDate, String Source, String groupeby, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            if (groupeby.equals("Client")) {

                // if (!id_client.isEmpty()) {
                addRowsStatAchatDevis(table, FromDate, ToDate, Source, groupeby, id_client, df);
                /* } else {
                    String clients = AllClientsList(table, FromDate, ToDate, Source, groupeby, id_client, df);
                    String[] lstClient = clients.split(",");
                    addRowsStatAchatAllClientsDevis(table, FromDate, ToDate, Source, groupeby, id_client, df, lstClient);

                }*/
            } else if (groupeby.equals("Date")) {
                if (!id_client.isEmpty()) {
                    addRowsStatAchatbyDateDevis(table, FromDate, ToDate, Source, groupeby, id_client, df);
                } else {
                    String clients = AllDatesList(table, "devis", "date_devis", FromDate, ToDate, Source, groupeby, id_client, df);
                    String[] lstClient = clients.split(",");
                    addRowsStatAchatAllDatesDevis(table, FromDate, ToDate, Source, groupeby, id_client, df, lstClient);

                }
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

    public DefaultTableModel afficherReliquatEtat(JTable table, String FromDate, String ToDate, String Source, String groupeby, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            if (groupeby.equals("Client")) {

                // if (!id_client.isEmpty()) {
                addRowsStatAchatReliquat(table, FromDate, ToDate, Source, groupeby, id_client, df);
                /* } else {
                    String clients = AllClientsList(table, FromDate, ToDate, Source, groupeby, id_client, df);
                    String[] lstClient = clients.split(",");
                    addRowsStatAchatAllClientsDevis(table, FromDate, ToDate, Source, groupeby, id_client, df, lstClient);

                }*/
            } else if (groupeby.equals("Date")) {
                if (!id_client.isEmpty()) {
                    addRowsStatAchatbyDateReliquat(table, FromDate, ToDate, Source, groupeby, id_client, df);
                } else {
                    String clients = AllDatesList(table, "reliquat", "date_reliquat", FromDate, ToDate, Source, groupeby, id_client, df);
                    String[] lstClient = clients.split(",");
                    addRowsStatAchatAllDatesReliquat(table, FromDate, ToDate, Source, groupeby, id_client, df, lstClient);

                }
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

    public DefaultTableModel afficherGrandLivre(JTable table, String FromDate, String ToDate, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {

            /*    if (!id_client.isEmpty()) {
                String[] lstClient = id_client.split(",");
                // addRowsGrandLivre(table, FromDate, ToDate, id_client, df, lstClient);
                getDetailGrandLivre(table, FromDate, ToDate, id_client);
            } else {*/
            String clients = AllClientsListGrandLivre(table, FromDate, ToDate, id_client, df);
            String factures = AllFacturesListGrandLivre(table, FromDate, ToDate, id_client, df);

            String[] lstClient = clients.split(",");
            String[] lstFactures = factures.split(",");

            addRowsGrandLivre(table, FromDate, ToDate, id_client, df, lstFactures);
            //}

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

    public void addRowsGrandLivre(JTable table, String FromDate, String ToDate, String id_clients, DefaultTableModel df, String[] lstfacture) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            /*  Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Total Facture");
            columnNames.add("Total Reglement");
            columnNames.add("Solde");
             */
            Vector<String> columnNames = new Vector<String>();

            columnNames.add("Date");
            columnNames.add("Libelle");
            columnNames.add("Solde Initial");

            columnNames.add("Débit");
            columnNames.add("Crédit");
            columnNames.add("Solde Prog");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            Double totalGlobalCredit = 0.0;
            Double totalGlobalDebit = 0.0;
            double SoldeIntital = 0.0;
            double SoldeCumule = 0.0;
            for (String num_facture : lstfacture) {

                //--------------------------------- ajouter ligne facture begin
                String sqlFact = "select c.nom,r.date_facture,r.ttc,r.reglement from facture r left join client c on c.numero_client=r.id_client where r.num_facture='" + num_facture + "' ";

                /*  String DateClauseTo = "";
                if (!(ToDate.isEmpty())) {
                    DateClauseTo = " and bl.date_bl <= '" + ToDate + "'";
                    sql = sql + DateClauseTo;
                }

                String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and bl.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }*/
                sqlFact += " ORDER BY  r.date_facture DESC";

                pst = conn.prepareStatement(sqlFact);

                rs = pst.executeQuery();

                while (rs.next()) {

                    //   nomclient = rs.getString("c.nom");
                    Object LigneData[] = new Object[6];

                    /*  if (x == 0) {

                        LigneData[0] = rs.getString("date_devis");
                    } else {
                        LigneData[0] = "";
                    }
                    x++;*/
                    //r.date_regelemnt,,,,r.regle  num_facture
                    LigneData[0] = rs.getString("r.date_facture");

                    if (rs.getString("r.reglement").equals("Oui")) {

                        LigneData[1] = "Facture N : " + num_facture + " (Réglée)";
                    } else if (rs.getString("r.reglement").equals("Semi")) {
                        LigneData[1] = "Facture N : " + num_facture + " (Semi Réglée)";

                    } else {
                        LigneData[1] = "Facture N : " + num_facture + " (Non Réglée)";

                    }
                    LigneData[2] = Config.Commen_Proc.formatDouble(SoldeIntital);

                    LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("r.ttc")));

                    LigneData[4] = "0.000";

                    totalGlobalDebit += Double.valueOf(rs.getString("r.ttc"));
                    SoldeIntital += Double.valueOf(rs.getString("r.ttc"));
                    SoldeCumule = SoldeIntital;

                    //  SoldeCumule += Double.valueOf(Config.Commen_Proc.formatDouble(SoldeIntital + Double.valueOf(rs.getString("r.ttc"))));
                    LigneData[5] = Config.Commen_Proc.formatDouble(SoldeCumule);

                    //  LigneData[7] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("r.regle")));
                    df.insertRow(startAdd, LigneData);
                    startAdd++;

                    // qteTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                    // df.insertRow(startAdd + 1, LigneData);
                }

                // ---------------------------------ajouter ligne facture end 
                // ---------------------------------ajouter ligne reglement begin
                String sql;
                sql = "select c.nom,r.date_regelemnt,r.type_reglement,r.num_cheque,r.comptabilise,r.regle from reglement r left join client c on c.numero_client=r.id_client where r.id_facture='" + num_facture + "' ";

                /*  String DateClauseTo = "";
                if (!(ToDate.isEmpty())) {
                    DateClauseTo = " and bl.date_bl <= '" + ToDate + "'";
                    sql = sql + DateClauseTo;
                }

                String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and bl.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }*/
                sql += " ORDER BY  r.date_regelemnt DESC";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                while (rs.next()) {

                    //nomclient = rs.getString("c.nom");
                    Object LigneData[] = new Object[6];

                    /*  if (x == 0) {

                        LigneData[0] = rs.getString("date_devis");
                    } else {
                        LigneData[0] = "";
                    }
                    x++;*/
                    //r.date_regelemnt,,,,r.regle  num_facture
                    LigneData[0] = rs.getString("r.date_regelemnt");

                    if (rs.getString("r.type_reglement").equals("CHEQUE")) {

                        LigneData[1] = "Reglement par Cheque du " + rs.getString("r.date_regelemnt") + "" + " N : " + rs.getString("r.num_cheque");

                    } else if (rs.getString("r.type_reglement").equals("RISTOURN")) {//
                        LigneData[1] = "Retenue a la source Fact N : " + num_facture;

                    } else {
                        LigneData[1] = "Reglement Fact N : " + num_facture + "(" + rs.getString("r.type_reglement") + ")";
                    }
                    String date_fact = "";
                    LigneData[2] = Config.Commen_Proc.formatDouble(SoldeIntital);

                    LigneData[3] = "0.000";

                    LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("r.comptabilise")));

                    totalGlobalCredit += Double.valueOf(rs.getString("r.comptabilise"));

                    SoldeIntital -= Double.valueOf(rs.getString("r.comptabilise"));
                    SoldeCumule = SoldeIntital;

                    LigneData[5] = Config.Commen_Proc.formatDouble(SoldeCumule);

                    /*SoldeCumule -= Double.valueOf(Config.Commen_Proc.formatDouble(SoldeIntital - Double.valueOf(rs.getString("r.comptabilise"))));
                    LigneData[4] = Config.Commen_Proc.formatDouble(SoldeCumule);*/
                    //  LigneData[7] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("r.regle")));
                    df.insertRow(startAdd, LigneData);
                    startAdd++;

                    // qteTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                    // df.insertRow(startAdd + 1, LigneData);
                }
                // ajouter ligne reglement end

                /* startAdd = df.getRowCount();
                String totalF = "";
                String totalR = "";

                String resFN = getTotalFactureGrandLivre(FromDate, ToDate, num_facture);
                totalF = resFN.split(";")[0];
                totalR = getTotalReglementGrandLivre(FromDate, ToDate, num_facture);

                Double solde = Double.valueOf(totalF) - Double.valueOf(totalR);

                Object LigneData[] = new Object[4];

                LigneData[0] = resFN.split(";")[1];
                LigneData[1] = Config.Commen_Proc.formatDouble(Double.valueOf(totalF));
                LigneData[2] = Config.Commen_Proc.formatDouble(Double.valueOf(totalR));
                LigneData[3] = Config.Commen_Proc.formatDouble(solde);

                totalGlobalFacture += Double.valueOf(totalF);
                totalGlobalReglement += Double.valueOf(totalR);
                df.insertRow(startAdd, LigneData);
                Object LigneDatas[] = new Object[4];

                LigneDatas[0] = "";
                LigneDatas[1] = "";
                LigneDatas[2] = "";
                LigneDatas[3] = "";

                df.insertRow(startAdd + 1, LigneDatas);*/
            }
            Object LigneData[] = new Object[6];

            LigneData[0] = "";
            LigneData[1] = "";
            LigneData[2] = "Total";

            LigneData[3] = Config.Commen_Proc.formatDouble(totalGlobalDebit);
            LigneData[4] = Config.Commen_Proc.formatDouble(totalGlobalCredit);
            LigneData[5] = "";
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsGrandLivreTestDetail(JTable table, String FromDate, String ToDate, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Num Facture");
            columnNames.add("Date Facture");

            columnNames.add("Débit");
            columnNames.add("Crédit");
            columnNames.add("Date reglement");
            columnNames.add("Type reglement");
            columnNames.add("Num cheque");

            columnNames.add("Solde");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            Double totalGlobalFacture = 0.0;
            Double totalGlobalReglement = 0.0;

            for (String Date : lstclient) {
                startAdd = df.getRowCount();
                id_client = Date;
                String totalF = "";
                String totalR = "";

                String resFN = getTotalFactureGrandLivre(FromDate, ToDate, id_client);
                totalF = resFN.split(";")[0];
                totalR = getTotalReglementGrandLivre(FromDate, ToDate, id_client);

                Double solde = Double.valueOf(totalF) - Double.valueOf(totalR);

                Object LigneData[] = new Object[4];

                LigneData[0] = resFN.split(";")[1];
                LigneData[1] = Config.Commen_Proc.formatDouble(Double.valueOf(totalF));
                LigneData[2] = Config.Commen_Proc.formatDouble(Double.valueOf(totalR));
                LigneData[3] = Config.Commen_Proc.formatDouble(solde);

                totalGlobalFacture += Double.valueOf(totalF);
                totalGlobalReglement += Double.valueOf(totalR);
                df.insertRow(startAdd, LigneData);
                Object LigneDatas[] = new Object[4];

                LigneDatas[0] = "";
                LigneDatas[1] = "";
                LigneDatas[2] = "";
                LigneDatas[3] = "";

                df.insertRow(startAdd + 1, LigneDatas);
            }
            Object LigneData[] = new Object[4];

            LigneData[0] = "Total tous les Clients";
            LigneData[1] = Config.Commen_Proc.formatDouble(totalGlobalFacture);
            LigneData[2] = Config.Commen_Proc.formatDouble(totalGlobalReglement);
            LigneData[3] = "";
            df.insertRow(startAdd + 1, LigneData);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public String getDetailGrandLivre(JTable table, String FromDate, String ToDate, String id_client) {

        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String total = "0.0";
        try {
            String sql;
            sql = "select c.nom,f.num_facture,f.date_facture,f.TTC as Debit,r.comptabilise as credit,r.date_regelemnt,r.type_reglement,r.num_cheque "
                    + "from facture f "
                    + "left join reglement r on f.num_facture=r.id_facture "
                    + "left join client c on c.numero_Client=f.id_client  where 1=1 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and f.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and f.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and f.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }

            sql += " GROUP by f.num_facture,r.id ORDER BY f.date_facture ASC";
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            DefaultTableModel df = buildTableModelGrandLivre(rs);

            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return total;
    }

    public static DefaultTableModel buildTableModelGrandLivre(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();

        columnNames.add("Nom Client");
        columnNames.add("Num Fact");
        columnNames.add("Date Fact");
        columnNames.add("Débit");
        columnNames.add("Crédit");
        columnNames.add("Date Reglement");
        columnNames.add("Type Réglement");
        columnNames.add("Num cheque");

        int columnCount = metaData.getColumnCount();
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

    public static DefaultTableModel buildTableModelStat(ResultSet rs)
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

    public String getTotalFactureGrandLivre(String FromDate, String ToDate, String id_client) {

        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String total = "0.0";
        try {
            String sql;
            sql = "SELECT sum(bl.ttc),c.nom FROM `facture` bl left join client c on c.numero_client=bl.id_client  WHERE bl.statut=1  ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and bl.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and bl.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and bl.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            while (rs.next()) {
                String c = "0";
                if (rs.getString("c.nom") != null) {
                    c = rs.getString("c.nom");
                }
                String t = "0";
                if (rs.getString("sum(bl.ttc)") != null) {
                    t = rs.getString("sum(bl.ttc)");
                }
                total = t + ";" + c;

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return total;
    }

    public String getTotalReglementGrandLivre(String FromDate, String ToDate, String id_client) {

        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String total = "0.0";
        try {
            String sql;
            sql = "SELECT sum(bl.comptabilise) FROM `reglement` bl  WHERE bl.statut=1  ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and bl.date_regelemnt >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and bl.date_regelemnt <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and bl.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString("sum(bl.comptabilise)") != null) {
                    total = rs.getString("sum(bl.comptabilise)");
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return total;
    }

    public DefaultTableModel afficherAvoirEtat(JTable table, String FromDate, String ToDate, String Source, String groupeby, String id_client) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        DefaultTableModel df = null;
        Connection conn = obj.Open();
        try {
            if (groupeby.equals("Client")) {

                //     if (!id_client.isEmpty()) {
                addRowsStatAchatAvoir(table, FromDate, ToDate, Source, groupeby, id_client, df);
                /*  } else {
                    String clients = AllClientsList(table, FromDate, ToDate, Source, groupeby, id_client, df);
                    String[] lstClient = clients.split(",");
                    addRowsStatAchatAllClientsAvoir(table, FromDate, ToDate, Source, groupeby, id_client, df, lstClient);

                }*/
            } else if (groupeby.equals("Date")) {
                if (!id_client.isEmpty()) {
                    addRowsStatAchatbyDateAvoir(table, FromDate, ToDate, Source, groupeby, id_client, df);
                } else {
                    String clients = AllDatesList(table, "avoir", "date_avoir", FromDate, ToDate, Source, groupeby, id_client, df);
                    String[] lstClient = clients.split(",");
                    addRowsStatAchatAllDatesAvoir(table, FromDate, ToDate, Source, groupeby, id_client, df, lstClient);

                }
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

    public void addRowsStatAchatAllClientsBL(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date BL");

            columnNames.add("Num BL");
            columnNames.add("Total BL");
            columnNames.add("Sort");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            Double totalGlobal = 0.0;
            for (String client : lstclient) {
                startAdd = df.getRowCount();
                id_client = client;
                String sql;
                sql = "SELECT date_bl,c.nom, bl.Num_bl,bl.Total_HT,IF(bl.invoiced=1,'F','N') FROM `bl` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1  ";

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

                String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and bl.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }
                sql += " ORDER BY  bl.date_bl DESC";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                Double qteTotalClient = 0.0;
                String nomclient = "";
                int x = 0;
                while (rs.next()) {

                    nomclient = rs.getString("c.nom");
                    Object LigneData[] = new Object[5];
                    if (x == 0) {

                        LigneData[0] = rs.getString("c.nom");
                    } else {
                        LigneData[0] = "";
                    }
                    x++;
                    LigneData[1] = rs.getString("date_bl");

                    LigneData[2] = rs.getString("bl.Num_bl");
                    LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));
                    LigneData[4] = rs.getString("IF(bl.invoiced=1,'F','N')");
                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                    startAdd++;

                }
                if (qteTotalClient != 0.0) {
                    Object LigneData[] = new Object[5];

                    LigneData[0] = "Total";
                    LigneData[1] = "";
                    LigneData[2] = "";
                    LigneData[3] = Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(qteTotalClient)));
                    LigneData[4] = "";

                    totalGlobal += qteTotalClient;
                    df.insertRow(startAdd, LigneData);
                    Object LigneDatas[] = new Object[5];

                    LigneDatas[0] = "";
                    LigneDatas[1] = "";
                    LigneDatas[2] = "";
                    LigneDatas[4] = "";

                    df.insertRow(startAdd, LigneDatas);
                }
            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total tous les clients";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(totalGlobal)));
            LigneData[4] = "";
            df.insertRow(startAdd + 1, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatAllClientsDevis(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date devis");

            columnNames.add("Num devis");
            columnNames.add("Total devis");
            columnNames.add("Sort");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            Double totalGlobal = 0.0;
            for (String client : lstclient) {
                startAdd = df.getRowCount();
                id_client = client;
                String sql;
                sql = "SELECT date_devis,c.nom, bl.Num_devis,bl.Total_HT FROM `devis` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1  ";

                String DateClauseFrom = "";
                if (!(FromDate.isEmpty())) {
                    DateClauseFrom = " and bl.date_devis >= '" + FromDate + "'";
                    sql = sql + DateClauseFrom;
                }

                String DateClauseTo = "";
                if (!(ToDate.isEmpty())) {
                    DateClauseTo = " and bl.date_devis <= '" + ToDate + "'";
                    sql = sql + DateClauseTo;
                }

                String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and bl.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }
                sql += " ORDER BY  bl.date_devis DESC";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                Double qteTotalClient = 0.0;
                String nomclient = "";
                int x = 0;
                while (rs.next()) {

                    nomclient = rs.getString("c.nom");
                    Object LigneData[] = new Object[4];
                    if (x == 0) {

                        LigneData[0] = rs.getString("c.nom");
                    } else {
                        LigneData[0] = "";
                    }
                    x++;
                    LigneData[1] = rs.getString("date_devis");

                    LigneData[2] = rs.getString("bl.Num_devis");
                    LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));

                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                    startAdd++;

                }

                if (qteTotalClient != 0.0) {

                    Object LigneData[] = new Object[4];

                    LigneData[0] = "Total";
                    LigneData[1] = "";
                    LigneData[2] = "";
                    LigneData[3] = Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(qteTotalClient)));

                    totalGlobal += qteTotalClient;
                    df.insertRow(startAdd, LigneData);
                    Object LigneDatas[] = new Object[5];

                    LigneDatas[0] = "";
                    LigneDatas[1] = "";
                    LigneDatas[2] = "";
                    LigneDatas[4] = "";

                    df.insertRow(startAdd + 1, LigneDatas);
                }
            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total tous les clients";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = totalGlobal;
            LigneData[4] = "";
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatAllClientsAvoir(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date devis");

            columnNames.add("Num devis");
            columnNames.add("Total devis");
            columnNames.add("Sort");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            Double totalGlobal = 0.0;
            for (String client : lstclient) {
                startAdd = df.getRowCount();
                id_client = client;
                String sql;
                sql = "SELECT date_avoir,c.nom, bl.Num_avoir,bl.Total_HT FROM `avoir` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1  ";

                String DateClauseFrom = "";
                if (!(FromDate.isEmpty())) {
                    DateClauseFrom = " and bl.date_avoir >= '" + FromDate + "'";
                    sql = sql + DateClauseFrom;
                }

                String DateClauseTo = "";
                if (!(ToDate.isEmpty())) {
                    DateClauseTo = " and bl.date_avoir <= '" + ToDate + "'";
                    sql = sql + DateClauseTo;
                }

                String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and bl.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }
                sql += " ORDER BY  bl.date_avoir DESC";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                Double qteTotalClient = 0.0;
                String nomclient = "";
                int x = 0;
                while (rs.next()) {

                    nomclient = rs.getString("c.nom");
                    Object LigneData[] = new Object[4];
                    if (x == 0) {

                        LigneData[0] = rs.getString("c.nom");
                    } else {
                        LigneData[0] = "";
                    }
                    x++;
                    LigneData[1] = rs.getString("date_avoir");

                    LigneData[2] = rs.getString("bl.Num_avoir");
                    LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));

                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                    startAdd++;

                }

                if (qteTotalClient != 0.0) {

                    Object LigneData[] = new Object[4];

                    LigneData[0] = "Total";
                    LigneData[1] = "";
                    LigneData[2] = "";
                    LigneData[3] = Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(qteTotalClient)));

                    totalGlobal += qteTotalClient;
                    df.insertRow(startAdd, LigneData);
                    Object LigneDatas[] = new Object[5];

                    LigneDatas[0] = "";
                    LigneDatas[1] = "";
                    LigneDatas[2] = "";
                    LigneDatas[4] = "";

                    df.insertRow(startAdd + 1, LigneDatas);
                }
            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total tous les clients";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = totalGlobal;
            LigneData[4] = "";
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatAllClientsFacture(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date Facture");

            columnNames.add("Num Facture");
            columnNames.add("Total Facture");
            columnNames.add("Sort");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            Double totalGlobal = 0.0;
            for (String client : lstclient) {
                startAdd = df.getRowCount();
                id_client = client;
                String sql;
                sql = "SELECT date_facture,c.nom, facture.Num_facture,facture.HT FROM `facture` facture left join client c on (facture.id_client=c.numero_Client) WHERE facture.statut=1  ";

                String DateClauseFrom = "";
                if (!(FromDate.isEmpty())) {
                    DateClauseFrom = " and facture.date_facture >= '" + FromDate + "'";
                    sql = sql + DateClauseFrom;
                }

                String DateClauseTo = "";
                if (!(ToDate.isEmpty())) {
                    DateClauseTo = " and facture.date_facture <= '" + ToDate + "'";
                    sql = sql + DateClauseTo;
                }

                String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and facture.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }
                sql += " ORDER BY  facture.date_facture DESC";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                Double qteTotalClient = 0.0;
                String nomclient = "";
                int x = 0;
                while (rs.next()) {

                    nomclient = rs.getString("c.nom");
                    Object LigneData[] = new Object[4];
                    if (x == 0) {

                        LigneData[0] = rs.getString("c.nom");
                    } else {
                        LigneData[0] = "";
                    }
                    x++;
                    LigneData[1] = rs.getString("date_facture");

                    LigneData[2] = rs.getString("facture.Num_facture");
                    LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("facture.HT")));
                    //   LigneData[4] = rs.getString("IF(bl.invoiced=1,'F','N')");
                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("facture.HT"))));
                    startAdd++;

                }
                if (qteTotalClient != 0.0) {

                    Object LigneData[] = new Object[4];

                    LigneData[0] = "Total";
                    LigneData[1] = "";
                    LigneData[2] = "";
                    LigneData[3] = Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(qteTotalClient)));
                    // LigneData[4] = "";

                    totalGlobal += qteTotalClient;
                    df.insertRow(startAdd, LigneData);
                    Object LigneDatas[] = new Object[5];

                    LigneDatas[0] = "";
                    LigneDatas[1] = "";
                    LigneDatas[2] = "";
                    LigneDatas[4] = "";

                    df.insertRow(startAdd + 1, LigneDatas);
                }
            }
            Object LigneData[] = new Object[4];

            LigneData[0] = "Total tous les clients";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(totalGlobal)));
            //  LigneData[4] = "";
            df.insertRow(startAdd, LigneData);
            table.setModel(df);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatAllDatesBL(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Date");
            columnNames.add("Nom Client");
            columnNames.add("Num");
            columnNames.add("Total HT");
            columnNames.add("Total TTC");

            columnNames.add("Sort");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            Double totalGlobal = 0.0;
            Double totalGlobalTTC = 0.0;

            for (String Date : lstclient) {
                startAdd = df.getRowCount();
                id_client = Date;
                String sql;
                sql = "SELECT date_bl,c.nom, bl.Num_bl,bl.Total_HT,bl.Total_TTC,IF(bl.invoiced=1,'F','N') FROM `bl` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1  ";

                String DateClauseFrom = "";
                if (!(id_client.isEmpty())) {
                    DateClauseFrom = " and bl.date_bl = '" + id_client + "'";
                    sql = sql + DateClauseFrom;
                }

                /*  String DateClauseTo = "";
                if (!(ToDate.isEmpty())) {
                    DateClauseTo = " and bl.date_bl <= '" + ToDate + "'";
                    sql = sql + DateClauseTo;
                }

                String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and bl.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }*/
                sql += " ORDER BY  bl.date_bl DESC";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                Double qteTotalClient = 0.0;
                Double TTCTotalClient = 0.0;

                String nomclient = "";
                int x = 0;
                while (rs.next()) {

                    nomclient = rs.getString("c.nom");
                    Object LigneData[] = new Object[6];

                    if (x == 0) {

                        LigneData[0] = rs.getString("date_bl");
                    } else {
                        LigneData[0] = "";
                    }
                    x++;

                    LigneData[1] = rs.getString("c.nom");

                    LigneData[2] = rs.getString("bl.Num_bl");
                    LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));
                    LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC")));

                    LigneData[5] = rs.getString("IF(bl.invoiced=1,'F','N')");
                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));

                    TTCTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                    startAdd++;

                }

                Object LigneData[] = new Object[6];

                LigneData[0] = "Total";
                LigneData[1] = "";
                LigneData[2] = "";
                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(qteTotalClient));
                LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(TTCTotalClient));

                LigneData[5] = "";

                totalGlobal += qteTotalClient;
                totalGlobalTTC += TTCTotalClient;

                df.insertRow(startAdd, LigneData);
                Object LigneDatas[] = new Object[6];

                LigneDatas[0] = "";
                LigneDatas[1] = "";
                LigneDatas[2] = "";
                LigneDatas[3] = "";

                LigneDatas[4] = "";

                df.insertRow(startAdd + 1, LigneDatas);
            }
            Object LigneData[] = new Object[6];

            LigneData[0] = "Total tous les Dates";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(totalGlobal);
            LigneData[4] = Config.Commen_Proc.formatDouble(totalGlobalTTC);

            LigneData[5] = "";
            df.insertRow(startAdd + 2, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatAllDatesDevis(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Date");
            columnNames.add("Nom Client");
            columnNames.add("Num");
            columnNames.add("Total HT");
            columnNames.add("Total TTC");

            columnNames.add("Sort");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            Double totalGlobal = 0.0;
            Double totalGlobalTTC = 0.0;

            for (String Date : lstclient) {
                startAdd = df.getRowCount();
                id_client = Date;
                String sql;
                sql = "SELECT date_devis,c.nom, bl.Num_devis,bl.Total_HT,bl.Total_TTC FROM `devis` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1  ";

                String DateClauseFrom = "";
                if (!(id_client.isEmpty())) {
                    DateClauseFrom = " and bl.date_devis = '" + id_client + "'";
                    sql = sql + DateClauseFrom;
                }

                /*  String DateClauseTo = "";
                if (!(ToDate.isEmpty())) {
                    DateClauseTo = " and bl.date_bl <= '" + ToDate + "'";
                    sql = sql + DateClauseTo;
                }

                String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and bl.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }*/
                sql += " ORDER BY  bl.date_devis DESC";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                Double qteTotalClient = 0.0;
                Double TTCTotalClient = 0.0;

                String nomclient = "";
                int x = 0;
                while (rs.next()) {

                    nomclient = rs.getString("c.nom");
                    Object LigneData[] = new Object[6];

                    if (x == 0) {

                        LigneData[0] = rs.getString("date_devis");
                    } else {
                        LigneData[0] = "";
                    }
                    x++;

                    LigneData[1] = rs.getString("c.nom");

                    LigneData[2] = rs.getString("bl.Num_devis");
                    LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));
                    LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC")));

                    LigneData[5] = "";
                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                    TTCTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC"))));

                    startAdd++;

                }

                Object LigneData[] = new Object[6];

                LigneData[0] = "Total";
                LigneData[1] = "";
                LigneData[2] = "";
                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(qteTotalClient));
                LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(TTCTotalClient));

                LigneData[5] = "";

                totalGlobal += qteTotalClient;
                totalGlobalTTC += TTCTotalClient;

                df.insertRow(startAdd, LigneData);
                Object LigneDatas[] = new Object[6];

                LigneDatas[0] = "";
                LigneDatas[1] = "";
                LigneDatas[2] = "";
                LigneDatas[3] = "";

                LigneDatas[4] = "";
                LigneDatas[5] = "";

                df.insertRow(startAdd + 1, LigneDatas);
            }
            Object LigneData[] = new Object[6];

            LigneData[0] = "Total tous les Dates";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(totalGlobal);
            LigneData[4] = Config.Commen_Proc.formatDouble(totalGlobalTTC);

            LigneData[5] = "";
            df.insertRow(startAdd + 2, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatAllDatesReliquat(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Date");
            columnNames.add("Nom Client");
            columnNames.add("Num");
            columnNames.add("Total HT");
            columnNames.add("Total TTC");

            columnNames.add("Sort");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            Double totalGlobal = 0.0;
            Double totalGlobalTTC = 0.0;

            for (String Date : lstclient) {
                startAdd = df.getRowCount();
                id_client = Date;
                String sql;
                sql = "SELECT date_reliquat,c.nom, bl.Num_reliquat,bl.Total_HT,bl.Total_TTC FROM `reliquat` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1  ";

                String DateClauseFrom = "";
                if (!(id_client.isEmpty())) {
                    DateClauseFrom = " and bl.date_reliquat = '" + id_client + "'";
                    sql = sql + DateClauseFrom;
                }

                /*  String DateClauseTo = "";
                if (!(ToDate.isEmpty())) {
                    DateClauseTo = " and bl.date_bl <= '" + ToDate + "'";
                    sql = sql + DateClauseTo;
                }

                String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and bl.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }*/
                sql += " ORDER BY  bl.date_reliquat DESC";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                Double qteTotalClient = 0.0;
                Double TTCTotalClient = 0.0;

                String nomclient = "";
                int x = 0;
                while (rs.next()) {

                    nomclient = rs.getString("c.nom");
                    Object LigneData[] = new Object[6];

                    if (x == 0) {

                        LigneData[0] = rs.getString("date_reliquat");
                    } else {
                        LigneData[0] = "";
                    }
                    x++;

                    LigneData[1] = rs.getString("c.nom");

                    LigneData[2] = rs.getString("bl.Num_reliquat");
                    LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));
                    LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC")));

                    LigneData[5] = "";
                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                    TTCTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC"))));

                    startAdd++;

                }

                Object LigneData[] = new Object[6];

                LigneData[0] = "Total";
                LigneData[1] = "";
                LigneData[2] = "";
                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(qteTotalClient));
                LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(TTCTotalClient));

                LigneData[5] = "";

                totalGlobal += qteTotalClient;
                totalGlobalTTC += TTCTotalClient;

                df.insertRow(startAdd, LigneData);
                Object LigneDatas[] = new Object[6];

                LigneDatas[0] = "";
                LigneDatas[1] = "";
                LigneDatas[2] = "";
                LigneDatas[3] = "";

                LigneDatas[4] = "";
                LigneDatas[5] = "";

                df.insertRow(startAdd + 1, LigneDatas);
            }
            Object LigneData[] = new Object[6];

            LigneData[0] = "Total tous les Dates";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(totalGlobal);
            LigneData[4] = Config.Commen_Proc.formatDouble(totalGlobalTTC);

            LigneData[5] = "";
            df.insertRow(startAdd + 2, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatAllDatesAvoir(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Date");
            columnNames.add("Nom Client");
            columnNames.add("Num");
            columnNames.add("Total HT");
            columnNames.add("Total TTC");

            columnNames.add("Sort");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            Double totalGlobal = 0.0;
            Double totalGlobalTTC = 0.0;

            for (String Date : lstclient) {
                startAdd = df.getRowCount();
                id_client = Date;
                String sql;
                sql = "SELECT date_avoir,c.nom, bl.Num_avoir,bl.Total_HT,bl.Total_TTC FROM `avoir` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1  ";

                String DateClauseFrom = "";
                if (!(id_client.isEmpty())) {
                    DateClauseFrom = " and bl.date_avoir = '" + id_client + "'";
                    sql = sql + DateClauseFrom;
                }

                /*  String DateClauseTo = "";
                if (!(ToDate.isEmpty())) {
                    DateClauseTo = " and bl.date_bl <= '" + ToDate + "'";
                    sql = sql + DateClauseTo;
                }

                String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and bl.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }*/
                sql += " ORDER BY  bl.date_avoir DESC";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                Double qteTotalClient = 0.0;
                Double TTCTotalClient = 0.0;
                String nomclient = "";
                int x = 0;
                while (rs.next()) {

                    nomclient = rs.getString("c.nom");
                    Object LigneData[] = new Object[5];

                    if (x == 0) {

                        LigneData[0] = rs.getString("date_avoir");
                    } else {
                        LigneData[0] = "";
                    }
                    x++;

                    LigneData[1] = rs.getString("c.nom");

                    LigneData[2] = rs.getString("bl.Num_avoir");
                    LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));
                    LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC")));

                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                    TTCTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC"))));

                    startAdd++;

                }

                if (qteTotalClient != 0.0) {
                    Object LigneData[] = new Object[5];

                    LigneData[0] = "Total";
                    LigneData[1] = "";
                    LigneData[2] = "";
                    LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(qteTotalClient));
                    LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(TTCTotalClient));

                    totalGlobal += qteTotalClient;
                    totalGlobalTTC += TTCTotalClient;

                    df.insertRow(startAdd, LigneData);
                    Object LigneDatas[] = new Object[5];

                    LigneDatas[0] = "";
                    LigneDatas[1] = "";
                    LigneDatas[2] = "";
                    LigneDatas[3] = "";

                    LigneDatas[4] = "";

                    df.insertRow(startAdd + 1, LigneDatas);
                }
            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total tous les Dates";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(totalGlobal);
            LigneData[4] = Config.Commen_Proc.formatDouble(totalGlobalTTC);

            df.insertRow(startAdd + 2, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatAllDatesFacture(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df, String[] lstclient) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Date");
            columnNames.add("Nom Client");
            columnNames.add("Num");
            columnNames.add("Total HT");
            columnNames.add("Total TTC");

            columnNames.add("Sort");
            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;
            int startAdd = 0;
            Double totalGlobal = 0.0;
            Double totalGlobalTTC = 0.0;

            for (String Date : lstclient) {
                startAdd = df.getRowCount();
                id_client = Date;
                String sql;
                sql = "SELECT date_facture,c.nom, facture.Num_facture,facture.HT,facture.TTC FROM `facture` facture left join client c on (facture.id_client=c.numero_Client) WHERE facture.statut=1  ";

                String DateClauseFrom = "";
                if (!(id_client.isEmpty())) {
                    DateClauseFrom = " and facture.date_facture = '" + id_client + "'";
                    sql = sql + DateClauseFrom;
                }

                /*  String DateClauseTo = "";
                if (!(ToDate.isEmpty())) {
                    DateClauseTo = " and bl.date_bl <= '" + ToDate + "'";
                    sql = sql + DateClauseTo;
                }

                String ClientClause = "";
                if (!(id_client.isEmpty())) {
                    ClientClause = " and bl.id_client  = " + id_client + "";
                    sql = sql + ClientClause;

                }*/
                sql += " ORDER BY  facture.date_facture DESC";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                Double qteTotalClient = 0.0;
                Double TTCTotalClient = 0.0;

                String nomclient = "";
                int x = 0;
                while (rs.next()) {

                    nomclient = rs.getString("c.nom");
                    Object LigneData[] = new Object[5];

                    if (x == 0) {

                        LigneData[0] = rs.getString("date_facture");
                    } else {
                        LigneData[0] = "";
                    }
                    x++;

                    LigneData[1] = rs.getString("c.nom");

                    LigneData[2] = rs.getString("facture.Num_facture");
                    LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("facture.HT")));
                    LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("facture.TTC")));

                    //  LigneData[4] = rs.getString("IF(bl.invoiced=1,'F','N')");
                    df.insertRow(startAdd, LigneData);

                    qteTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("facture.HT"))));
                    TTCTotalClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("facture.TTC"))));

                    startAdd++;

                }
                if (qteTotalClient != 0.0) {

                    Object LigneData[] = new Object[5];

                    LigneData[0] = "Total";
                    LigneData[1] = "";
                    LigneData[2] = "";
                    LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(qteTotalClient));
                    LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(TTCTotalClient));

                    //  LigneData[4] = "";
                    totalGlobal += qteTotalClient;
                    totalGlobalTTC += TTCTotalClient;
                    df.insertRow(startAdd, LigneData);
                    Object LigneDatas[] = new Object[5];

                    LigneDatas[0] = "";
                    LigneDatas[1] = "";
                    LigneDatas[2] = "";
                    LigneDatas[3] = "";
                    LigneDatas[4] = "";

                    df.insertRow(startAdd, LigneDatas);
                }
            }

            Object LigneData[] = new Object[5];

            LigneData[0] = "Total tous les Dates";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(totalGlobal);
            LigneData[4] = Config.Commen_Proc.formatDouble(totalGlobalTTC);

            //LigneData[4] = "";
            df.insertRow(startAdd+2, LigneData);
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
            sql = "SELECT distinct bl.id_client FROM `bl` bl  WHERE bl.statut=1  ";

            /* String DateClauseFrom = "";
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
             */
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                ListClient += "" + rs.getString("bl.id_client") + ",";
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

    public String AllClientsListGrandLivre(JTable table, String FromDate, String ToDate, String id_client, DefaultTableModel df) {
        String ListClient = "";
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT distinct bl.id_client FROM `facture` bl left join client c on c.numero_client=bl.id_client WHERE bl.statut=1 and bl.id_client<> 99999999 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and bl.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and bl.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            sql += " ORDER BY c.nom ";
            /*
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
             */
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                ListClient += "" + rs.getString("bl.id_client") + ",";
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

    public String AllFacturesListGrandLivre(JTable table, String FromDate, String ToDate, String id_client, DefaultTableModel df) {
        String ListClient = "";
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT distinct bl.num_facture FROM `facture` bl left join client c on c.numero_client=bl.id_client WHERE bl.statut=1 and bl.id_client<> 99999999 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and bl.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and bl.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and bl.id_client = " + id_client + "";
                sql = sql + ClientClause;
            }

            sql += " ORDER BY  bl.num_facture ";
            /*
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
             */
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                ListClient += "" + rs.getString("bl.num_facture") + ",";
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

    public String AllDatesList(JTable table, String table_name, String date_column, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        String ListClient = "";
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT distinct " + date_column + " FROM " + table_name + " WHERE statut=1 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and " + date_column + " >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and " + date_column + " <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }
            /*
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
             */
            sql += "  order by  " + date_column + " desc ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                ListClient += "" + rs.getString(date_column) + ",";
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

    public void addRowsStatAchatBL(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT date_bl,c.nom, bl.Num_bl,bl.Total_HT,bl.Total_TTC,IF(bl.invoiced=1,'F','N') FROM `bl` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1 ";

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

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and bl.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY date_bl DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;
            Double TotalTTCClient = 0.0;

            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date");

            columnNames.add("Num");
            columnNames.add("Total HT");
            columnNames.add("Total TTC");

            columnNames.add("Sort");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                Object LigneData[] = new Object[6];
                LigneData[0] = rs.getString("c.nom");
                LigneData[1] = rs.getString("date_bl");
                LigneData[2] = rs.getString("bl.Num_bl");
                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));
                LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC")));

                LigneData[5] = rs.getString("IF(bl.invoiced=1,'F','N')");
                df.insertRow(startAdd, LigneData);

                TotalHTClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                TotalTTCClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC"))));

                startAdd++;

            }
            Object LigneData[] = new Object[6];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHTClient));
            LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalTTCClient));

            LigneData[5] = "";
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatDevis(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT date_devis,c.nom, bl.Num_devis,bl.Total_HT,bl.Total_TTC  FROM `devis` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and bl.date_devis >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and bl.date_devis <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and bl.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY date_devis DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;
            Double TotalTTCClient = 0.0;

            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date");

            columnNames.add("Num");
            columnNames.add("Total HT");
            columnNames.add("Total TTC");

            columnNames.add("Sort");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                Object LigneData[] = new Object[5];
                LigneData[0] = rs.getString("c.nom");
                LigneData[1] = rs.getString("date_devis");
                LigneData[2] = rs.getString("bl.Num_devis");
                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));
                LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC")));

                df.insertRow(startAdd, LigneData);

                TotalHTClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                TotalTTCClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC"))));

                startAdd++;

            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHTClient));
            LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalTTCClient));

            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatReliquat(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT date_reliquat,c.nom, bl.Num_reliquat,bl.Total_HT,bl.Total_TTC FROM `reliquat` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and bl.date_reliquat >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and bl.date_reliquat <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and bl.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY date_reliquat DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;
            Double TotalTTCClient = 0.0;

            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date");

            columnNames.add("Num");
            columnNames.add("Total HT");
            columnNames.add("Total TTC");

            columnNames.add("Sort");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                Object LigneData[] = new Object[5];
                LigneData[0] = rs.getString("c.nom");
                LigneData[1] = rs.getString("date_reliquat");
                LigneData[2] = rs.getString("bl.Num_reliquat");
                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));
                LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC")));

                df.insertRow(startAdd, LigneData);

                TotalHTClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                TotalTTCClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC"))));

                startAdd++;

            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHTClient));
            LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalTTCClient));

            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatAvoir(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT date_avoir,c.nom, bl.Num_avoir,bl.Total_HT,bl.Total_TTC FROM `avoir` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and bl.date_avoir >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and bl.date_avoir <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and bl.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY date_avoir DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;
            Double TotalTTCClient = 0.0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date");

            columnNames.add("Num");
            columnNames.add("Total HT");
            columnNames.add("Total TTC");

            columnNames.add("Sort");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                Object LigneData[] = new Object[5];
                LigneData[0] = rs.getString("c.nom");
                LigneData[1] = rs.getString("date_avoir");
                LigneData[2] = rs.getString("bl.Num_avoir");
                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));
                LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC")));

                df.insertRow(startAdd, LigneData);

                TotalHTClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                TotalTTCClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_TTC"))));

                startAdd++;

            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHTClient));
            LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalTTCClient));

            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatFacture(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT date_facture,c.nom, facture.Num_facture,facture.HT,facture.TTC FROM `facture` facture left join client c on (facture.id_client=c.numero_Client) WHERE facture.statut=1 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and facture.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and facture.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and facture.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY date_facture DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;

            Double TotalTTCClient = 0.0;

            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Nom Client");
            columnNames.add("Date");

            columnNames.add("Num");
            columnNames.add("Total HT");
            columnNames.add("Total TTC");

            columnNames.add("Sort");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                Object LigneData[] = new Object[5];
                LigneData[0] = rs.getString("c.nom");
                LigneData[1] = rs.getString("date_facture");
                LigneData[2] = rs.getString("facture.Num_facture");
                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("facture.HT")));
                LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("facture.TTC")));

                // LigneData[4] = rs.getString("IF(bl.invoiced=1,'F','N')");
                df.insertRow(startAdd, LigneData);

                TotalHTClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("facture.HT"))));
                TotalTTCClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("facture.HT"))));

                startAdd++;

            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHTClient));
            LigneData[4] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalTTCClient));

            //  LigneData[4] = "";
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatbyDateBL(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT date_bl,c.nom, bl.Num_bl,bl.Total_HT,IF(bl.invoiced=1,'F','N') FROM `bl` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1 ";

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

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and bl.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY date_bl DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Date");
            columnNames.add("Nom Client");

            columnNames.add("Num");
            columnNames.add("Total");
            columnNames.add("Sort");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                Object LigneData[] = new Object[5];
                LigneData[0] = rs.getString("date_bl");
                LigneData[1] = rs.getString("c.nom");
                LigneData[2] = rs.getString("bl.Num_bl");
                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));
                LigneData[4] = rs.getString("IF(bl.invoiced=1,'F','N')");
                df.insertRow(startAdd, LigneData);

                TotalHTClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                startAdd++;

            }
            Object LigneData[] = new Object[5];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHTClient));
            LigneData[4] = "";
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatbyDateDevis(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT date_devis,c.nom, bl.Num_devis,bl.Total_HT FROM `devis` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and bl.date_devisbl >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and bl.date_devis <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and bl.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY date_devis DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Date");
            columnNames.add("Nom Client");

            columnNames.add("Num");
            columnNames.add("Total");
            columnNames.add("Sort");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                Object LigneData[] = new Object[4];
                LigneData[0] = rs.getString("date_devis");
                LigneData[1] = rs.getString("c.nom");
                LigneData[2] = rs.getString("bl.Num_devis");
                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));

                df.insertRow(startAdd, LigneData);

                TotalHTClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                startAdd++;

            }
            Object LigneData[] = new Object[4];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHTClient));

            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatbyDateReliquat(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT date_reliquat,c.nom, bl.Num_reliquat,bl.Total_HT FROM `reliquat` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and bl.date_reliquat >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and bl.date_reliquat <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and bl.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY date_reliquat DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Date");
            columnNames.add("Nom Client");

            columnNames.add("Num");
            columnNames.add("Total");
            columnNames.add("Sort");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                Object LigneData[] = new Object[4];
                LigneData[0] = rs.getString("date_reliquat");
                LigneData[1] = rs.getString("c.nom");
                LigneData[2] = rs.getString("bl.Num_reliquat");
                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));

                df.insertRow(startAdd, LigneData);

                TotalHTClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                startAdd++;

            }
            Object LigneData[] = new Object[4];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHTClient));

            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatbyDateAvoir(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT date_avoir,c.nom, bl.Num_avoir,bl.Total_HT FROM `avoir` bl left join client c on (bl.id_client=c.numero_Client) WHERE bl.statut=1 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and bl.date_avoir >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and bl.date_avoir <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and bl.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY date_avoir DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Date");
            columnNames.add("Nom Client");

            columnNames.add("Num");
            columnNames.add("Total");
            columnNames.add("Sort");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                Object LigneData[] = new Object[4];
                LigneData[0] = rs.getString("date_avoir");
                LigneData[1] = rs.getString("c.nom");
                LigneData[2] = rs.getString("bl.Num_avoir");
                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT")));

                df.insertRow(startAdd, LigneData);

                TotalHTClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("bl.Total_HT"))));
                startAdd++;

            }
            Object LigneData[] = new Object[4];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHTClient));

            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void addRowsStatAchatbyDateFacture(JTable table, String FromDate, String ToDate, String id_marque, String refArticle, String id_client, DefaultTableModel df) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {

            String sql;
            sql = "SELECT date_facture,c.nom, facture.Num_facture,facture.HT  FROM `facture` facture left join client c on (facture.id_client=c.numero_Client) WHERE facture.statut=1 ";

            String DateClauseFrom = "";
            if (!(FromDate.isEmpty())) {
                DateClauseFrom = " and facture.date_facture >= '" + FromDate + "'";
                sql = sql + DateClauseFrom;
            }

            String DateClauseTo = "";
            if (!(ToDate.isEmpty())) {
                DateClauseTo = " and facture.date_facture <= '" + ToDate + "'";
                sql = sql + DateClauseTo;
            }

            String ClientClause = "";
            if (!(id_client.isEmpty())) {
                ClientClause = " and facture.id_client  = " + id_client + "";
                sql = sql + ClientClause;

            }
            sql += " ORDER BY date_facture DESC";

            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            Double TotalHTClient = 0.0;
            int startAdd = 0;

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Date");
            columnNames.add("Nom Client");

            columnNames.add("Num");
            columnNames.add("Total");
            columnNames.add("Sort");

            Vector<Vector<Object>> data1 = new Vector<Vector<Object>>();
            DefaultTableModel model = new DefaultTableModel(data1, columnNames);
            df = model;

            while (rs.next()) {
                Object LigneData[] = new Object[4];
                LigneData[0] = rs.getString("date_facture");
                LigneData[1] = rs.getString("c.nom");
                LigneData[2] = rs.getString("facture.Num_facture");
                LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("facture.HT")));
                //   LigneData[4] = rs.getString("IF(bl.invoiced=1,'F','N')");
                df.insertRow(startAdd, LigneData);

                TotalHTClient += Double.valueOf(Config.Commen_Proc.formatDouble(Double.valueOf(rs.getString("facture.HT"))));
                startAdd++;

            }
            Object LigneData[] = new Object[4];

            LigneData[0] = "Total";
            LigneData[1] = "";
            LigneData[2] = "";
            LigneData[3] = Config.Commen_Proc.formatDouble(Double.valueOf(TotalHTClient));
            // LigneData[4] = "";
            df.insertRow(startAdd, LigneData);
            table.setModel(df);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

}
