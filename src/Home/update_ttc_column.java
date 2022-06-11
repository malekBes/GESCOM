/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import BL.BLDao;
import static BL.BLDao.buildTableModel;
import static Config.Commen_Proc.formatDouble;
import Conn.DataBase_connect;
import static Home.JTableToExcel.updateTTC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rss
 */
public class update_ttc_column {
    
        public static void main(String[] args) {

        try {
            JTable table1 = new JTable();
         

            updateTTC(table1, "ligne_devis");
            updateTTC(table1, "ligne_bl");
            updateTTC(table1, "ligne_avoir");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

   
    
       public static void updateTTC(JTable table, String table_name_to_update) {
        Double D_Total_HT = 0.0;
        Double D_Total_remise = 0.0;
        Double D_montant_TVA = 0.0;
        Double D_Total_Net = 0.0;

        ResultSet rs = null;
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            /*  if ((type_filtre.equals("passager"))) {
                sql = "SELECT 'Client Passager' as `Client` ,d.`Num_devis`,DATE_FORMAT(d.`date_devis`,'%d/%m/%Y'),d.`Total_TTC`,d.passager FROM `devis` d left join client c on (d.id_client=c.numero_client) where d.statut =1 ";

            } else {*/
            sql = "SELECT id,ref_article,qte,prix_u,remise,tva,total_HT,total_TTC FROM " + table_name_to_update + ";";
            // }

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
                //  Object PU = df.getValueAt(j, 3);
                // df.setValueAt(Config.Commen_Proc.formatDouble(Double.parseDouble(PU.toString())), j, 3);

                Double Total_TTC = 0.0;
                Double Total_HT = 0.0;
                Double Prix_U = 0.0;
                Double montant_Tva = 0.0;
                Double montant_remise = 0.0;
                Double TVA = 0.0;
                String l_txt_TVA = df.getValueAt(j, 5).toString();
                String l_txt_remise = df.getValueAt(j, 4).toString();
                String l_txt_qte = df.getValueAt(j, 2).toString();
                String l_txt_Prix_U = df.getValueAt(j, 3).toString();

                if (l_txt_TVA.isEmpty()) {
                    TVA = 0.0;
                } else {
                    TVA = Double.valueOf(l_txt_TVA);
                }

                Double remise;
                if (l_txt_remise.isEmpty()) {
                    remise = 0.0;
                } else {
                    remise = Double.valueOf(l_txt_remise);
                    if (remise >= 100) {
                        JOptionPane.showMessageDialog(null, "Remise ne depasse pas 100% ! ");
                        return;
                    }
                }

                Double qte;
                if (l_txt_qte.isEmpty()) {
                    qte = 1.0;
                } else {
                    qte = Double.valueOf(l_txt_qte);
                }
                if (!l_txt_Prix_U.isEmpty()) {
                    Prix_U = Double.valueOf(l_txt_Prix_U);

                    Total_HT = Prix_U * qte;
                    Total_HT = Double.valueOf((formatDouble(Total_HT)));
                    if (remise != 0.0) {
                        montant_remise = Total_HT * (remise / 100);
                    }

                    if (!l_txt_TVA.equals("0")) {
                        montant_Tva = (Total_HT - montant_remise) * (TVA / 100);
                        montant_Tva = montant_Tva;

                    }
//Double.valueOf((formatDouble(Total_TTC)))
                    Total_TTC += (Total_HT + montant_Tva) - montant_remise;
                    // 494.10300000000007
                }

                D_Total_HT += Total_HT;
                D_Total_Net += Total_TTC;
                if (!l_txt_remise.isEmpty()) {
                    D_Total_remise += (Total_HT * (Double.valueOf(l_txt_remise) / 100));
                }
                D_montant_TVA += montant_Tva;
             //   System.out.println(String.valueOf(formatDouble(Total_TTC)));
                rs = null;

                sql = "update " + table_name_to_update + " set total_ttc='" + String.valueOf(formatDouble(Total_TTC)) + "' where id =" + df.getValueAt(j, 0).toString() + ";";
                // }

                pst = conn.prepareStatement(sql);

                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.execute();

            }
            //  table.setModel(df);
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

}
