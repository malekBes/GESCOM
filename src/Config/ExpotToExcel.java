/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import Conn.DataBase_connect;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ExpotToExcel extends JFrame {
    //Header de JTable 

    /*   String[] columns = new String[]{
        "Id",
        "Nom",
        "Adresse",
        "Taux horaire",};

    //données pour JTable dans un tableau 2D
    Object[][] data = new Object[][]{
        {1, "Thomas", "Paris", 20.0},
        {2, "Jean", "Marseille", 50.0},
        {3, "Yohan", "Lyon", 30.0},
        {4, "Emily", "Toulouse", 60.0},
        {5, "Alex", "Nice", 10.0},
        {6, "Nicolas", "Lille", 11.5},};

    //crée un JTable avec des données
    JTable table = new JTable(data, columns);
    JPanel panel = new JPanel(new BorderLayout());
    JButton btn = new JButton("Export");
     */
    /**
     * public ExpotToExcel() { setSize(450, 200);
     * setDefaultCloseOperation(EXIT_ON_CLOSE); setLocationRelativeTo(null);
     * setTitle("Exporter JTable en Excel"); panel.add(btn, BorderLayout.SOUTH);
     * panel.add(new JScrollPane(table), BorderLayout.NORTH); add(panel);
     * setVisible(true); btn.addActionListener(new MyListener()); }
     */
    // export(table, new File("c:" + "\\Sodis-PDF\\" + "test" + ".xls"));
    public static void exportExcel(JTable table, File file, String num, Map<String, Object> map) {
        try {
            String params = getDataForExcel(num);
            String[] tabParams_client_date_tva = params.split(";");

            TableModel m = table.getModel();
            FileWriter fw = new FileWriter(file);

            fw.write("\nTunis Le : " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            fw.write("\n");
            fw.write("\n");
            fw.write("\n");

            fw.write("\t");
            fw.write("\t");
            fw.write("\t");
            fw.write("\t");

            //nom client
            if (!num.contains("cm")) {
                fw.write(tabParams_client_date_tva[0]);
            }

            fw.write("\n");
            fw.write("\t");
            fw.write("\t");
            fw.write("\t");
            fw.write("\t");
            if (num.contains("pre") || num.contains("cm")) {
                fw.write("Fournisseur : " + tabParams_client_date_tva[2]);

            } else {
                fw.write("Code TVA : " + tabParams_client_date_tva[2]);
            }
            fw.write("\n");

            if (num.contains("D")) {
                fw.write("Devis");
            } else if (num.contains("F")) {
                fw.write("Facture");
            } else if (num.contains("B")) {
                fw.write("BL");
            } else if (num.contains("A")) {
                fw.write("Avoir");
            } else if (num.contains("cm")) {
                fw.write("Commande");
            } else if (num.contains("pre")) {
                fw.write("Pre Commande");
            }

            fw.write("\n");
            if (num.contains("pre") || num.contains("cm")) {
                fw.write(num.split(";")[1]);
            } else {
                fw.write(num);
            }
            fw.write("\t");
            fw.write("\t");
            fw.write("\t");
            fw.write("\t");
            fw.write("Date : " + tabParams_client_date_tva[1]);
            fw.write("\n");
            fw.write("\n");
            fw.write("\n");
            if (!num.contains("cm") && !num.contains("pre")) {

                for (int i = 2; i < m.getColumnCount() - 1; i++) {
                    fw.write(m.getColumnName(i) + "\t");
                    if (i == 3) {
                        fw.write("\t");
                        fw.write("\t");
                        fw.write("\t");
                    }
                }

                fw.write("\n");
                fw.write("\n");
                for (int i = 0; i < m.getRowCount(); i++) {
                    for (int j = 2; j < m.getColumnCount() - 1; j++) {
                        String s = "";
                        if (m.getValueAt(i, j) != null) {
                            s = m.getValueAt(i, j).toString();
                        }
                        fw.write(s + "\t");
                        if (j == 3) {
                            fw.write("\t");
                            fw.write("\t");
                            fw.write("\t");
                        }
                    }
                    fw.write("\n");
                }
                fw.write("\n");

                // ajouter les totales 
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (key.equals("TOTAL_HT")) {
                        fw.write("\t\t\t\t\t\t\t");
                        fw.write("Total HT" + "\t");

                        fw.write(value.toString());
                        fw.write("\n");
                    }

                }

                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (key.equals("TOTAL_TVA")) {
                        fw.write("\t\t\t\t\t\t\t");
                        fw.write("Total TVA" + "\t");

                        fw.write(value.toString());
                        fw.write("\n");
                    }

                }

                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (key.equals("TOTAL_TTC")) {
                        fw.write("\t\t\t\t\t\t\t");
                        if (num.contains("F")) {
                            fw.write("Total Facture" + "\t");
                        } else if (num.contains("B")) {
                            fw.write("Total Net" + "\t");
                        } else {
                            fw.write("Total TTC" + "\t");
                        }

                        fw.write(value.toString());
                        fw.write("\n");
                    }

                }

                // ajouter les totales                 
            } else {
                for (int i = 0; i < m.getColumnCount(); i++) {
                    fw.write(m.getColumnName(i) + "\t");
                }

                fw.write("\n");
                fw.write("\n");
                for (int i = 0; i < m.getRowCount(); i++) {
                    for (int j = 0; j < m.getColumnCount(); j++) {
                        if (m.getValueAt(i, j) == null) {
                            fw.write("" + "\t");
                        } else {
                            fw.write(m.getValueAt(i, j).toString() + "\t");
                        }
                    }
                    fw.write("\n");
                }
            }
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void exportExcelGrandLivre(JTable table, File file, String num, Map<String, Object> map) {
        try {
            //  String params = getDataForExcel(num);
            //   String[] tabParams_client_date_tva = params.split(";");

            //table_title, Client, Date_from, Date_to
            String table_title = "";
            String Client = "";
            String Date_from = "";
            String Date_to = "";

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key.equals("table_title")) {
                    table_title = value.toString();
                }
                if (key.equals("Client")) {
                    Client = value.toString();
                }
                if (key.equals("Date_from")) {
                    Date_from = value.toString();
                }
                if (key.equals("Date_to")) {
                    Date_to = value.toString();
                }

            }

            TableModel m = table.getModel();
            FileWriter fw = new FileWriter(file);

            fw.write("\n");
            fw.write("\n");
            fw.write("\n");
            fw.write("\n");

            fw.write("\t");
            fw.write("\t");
            fw.write("\t");
            fw.write("\t");

            //nom client
            //  fw.write(Client);
            fw.write("\n");
            fw.write("\t");
            fw.write("\t");
            fw.write("\t");
            fw.write("\t");
            //
            //  fw.write("Fournisseur : " + tabParams_client_date_tva[2]);

            fw.write("\n");
            fw.write("\t");
            fw.write("\t");
            fw.write("\t");

            fw.write("Grand Livre");

            fw.write("\n");
            fw.write("\n");

            fw.write(Client);

            fw.write("\t");
            fw.write("\t");
            fw.write("\t");
            fw.write("\t");
            fw.write("\n");
            fw.write("Date du : " + Date_from + " / " + Date_to);
            fw.write("\n");
            fw.write("\n");
            fw.write("\n");

            for (int i = 0; i < m.getColumnCount(); i++) {
                fw.write(m.getColumnName(i) + "\t");
            }

            fw.write("\n");
            fw.write("\n");
            for (int i = 0; i < m.getRowCount(); i++) {
                for (int j = 0; j < m.getColumnCount(); j++) {
                    if (m.getValueAt(i, j) == null) {
                        fw.write("" + "\t");
                    } else {
                        fw.write(m.getValueAt(i, j).toString() + "\t");
                    }
                }
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static String getDataForExcel(String num) {
        try {
            String sql1;
            DataBase_connect obj = new DataBase_connect();
            Connection conn = obj.Open();
            sql1 = "";

            if (num.contains("D")) {
                sql1 = "select  if(c.nom is null,b.passager,c.nom) as client, DATE_FORMAT(b.date_devis,'%d/%m/%Y') as date,if(c.id_Fiscale is null,b.Code_TVA,c.id_Fiscale) as code_tva from devis b left join client c on b.id_client=c.numero_client where b.num_devis='" + num + "'";
            } else if (num.contains("F")) {
                /*   if (num.contains("#Passager")) {
                    num.replace("#Passager", "");
                    sql1 = "select c.nom as client, DATE_FORMAT(b.date_facture,'%d/%m/%Y') as date,c.id_Fiscale as code_tva from facture b left join client c on b.id_client=c.numero_client where b.num_facture='" + num + "'";

                } else {
                    sql1 = "select c.nom as client, DATE_FORMAT(b.date_facture,'%d/%m/%Y') as date,c.id_Fiscale as code_tva from facture b left join client c on b.id_client=c.numero_client where b.num_facture='" + num + "'";
                }*/
                sql1 = "select if(type_facture = 'Passager',passager, c.nom) as client, DATE_FORMAT(b.date_facture,'%d/%m/%Y') as date, if(type_facture = 'Passager',code_tva_passager, c.id_Fiscale) as code_tva from facture b left join client c on b.id_client=c.numero_client where b.num_facture='" + num + "'";

            } else if (num.contains("B")) {
                sql1 = "select c.nom as client, DATE_FORMAT(b.date_bl,'%d/%m/%Y') as date,c.id_Fiscale as code_tva from bl b left join client c on b.id_client=c.numero_client where b.num_bl='" + num + "'";
            } else if (num.contains("A")) {
                sql1 = "select c.nom as client, DATE_FORMAT(b.date_avoir,'%d/%m/%Y') as date,c.id_Fiscale as code_tva from avoir b left join client c on b.id_client=c.numero_client where b.num_avoir='" + num + "'";
            } else if (num.contains("cm")) {
                sql1 = "select DATE_FORMAT(cmd.date_commande,'%d/%m/%Y') as date,0 as client, f.nom  as code_tva from commande_achat cmd left join fournisseur f on f.id=cmd.id_fournisseur where cmd.num_Commande='" + num.split(";")[1] + "'";
            } else if (num.contains("pre")) {
                sql1 = "select DATE_FORMAT(cmd.date_pre_commande,'%d/%m/%Y') as date,c.nom as client, f.nom as code_tva from pre_command_achat cmd left join fournisseur f on f.id=cmd.id_fournisseur left join client c on c.numero_client= cmd.id_client where cmd.num_bon_commande='" + num.split(";")[1] + "'";
            } else if (num.contains("R")) {
                sql1 = "select c.nom as client, DATE_FORMAT(b.date_reliquat,'%d/%m/%Y') as date,c.id_Fiscale as code_tva from reliquat b left join client c on b.id_client=c.numero_client where b.num_reliquat='" + num + "'";
            }

            PreparedStatement pst;
            ResultSet rs = null;
            pst = conn.prepareStatement(sql1);

            rs = pst.executeQuery();
            String code_tva = "0";
            String client = "0";

            String date = "0";

            while (rs.next()) {
                if (!rs.getString("client").isEmpty()) {
                    client = rs.getString("client");
                }
                if (!rs.getString("date").isEmpty()) {
                    date = rs.getString("date");
                }
                if (!rs.getString("code_tva").isEmpty()) {
                    code_tva = rs.getString("code_tva");
                }
            }
            return client + ";" + date + ";" + code_tva;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "exportExcel.getDataForExcel : " + e);
            return "";
        }

    }

    public static String exportExcelStat(JTable table, File file, String titre) {
        try {
            //  String params = getDataForExcel(num);
            //   String[] tabParams_client_date_tva = params.split(";");

            TableModel m = table.getModel();
            FileWriter fw = new FileWriter(file);

            fw.write("\nTunis le : " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            fw.write("\n");

            fw.write("\n");

            fw.write("\t");
            fw.write("\t");
            fw.write("\t");
            fw.write(titre);
            //  fw.write("Date : " + tabParams_client_date_tva[1]);
            fw.write("\n");
            fw.write("\n");
            fw.write("\n");

            for (int i = 0; i < m.getColumnCount(); i++) {
                fw.write(m.getColumnName(i) + "\t");
            }

            fw.write("\n");
            fw.write("\n");
            for (int i = 0; i < m.getRowCount(); i++) {
                for (int j = 0; j < m.getColumnCount(); j++) {
                    if (m.getValueAt(i, j) == null) {
                        fw.write("" + "\t");
                    } else {
                        fw.write(m.getValueAt(i, j).toString() + "\t");
                    }
                }
                fw.write("\n");
            }

            fw.close();
            return "";
        } catch (Exception e) {
            System.out.println(e);
            return e.getMessage();
        }
    }

    public static String exportExcelStatparPeriod(JTable table, File file, String titre, Object tab[]) {
        try {
            //  String params = getDataForExcel(num);
            //   String[] tabParams_client_date_tva = params.split(";");

            TableModel m = table.getModel();
            FileWriter fw = new FileWriter(file);

            fw.write("\nTunis le : " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            fw.write("\n");

            fw.write("\n");

            fw.write("\t");
            fw.write("\t");
            fw.write("\t");
            fw.write(titre);
            //  fw.write("Date : " + tabParams_client_date_tva[1]);
            fw.write("\n");
            fw.write("\n");
            fw.write("\n");
            int jj = 0;
            for (int i = 0; i < m.getColumnCount()-2; i++) {
                if (i > 0 && i < 5) {
                    fw.write(m.getColumnName(i) + " " + tab[jj] + "\t");
                    jj++;
                } else {
                    fw.write(m.getColumnName(i) + "\t");
                }
            }

            fw.write("\n");
            fw.write("\n");
            for (int i = 0; i < m.getRowCount()-2; i++) {
                for (int j = 0; j < m.getColumnCount(); j++) {
                    if (m.getValueAt(i, j) == null) {
                        fw.write("" + "\t");
                    } else {
                        fw.write(m.getValueAt(i, j).toString() + "\t");
                    }
                }
                fw.write("\n");
            }

            fw.close();
            return "";
        } catch (Exception e) {
            System.out.println(e);
            return e.getMessage();
        }
    }

    /*   public static void main(String[] args) throws IOException {
         XSSFWorkbook workbook = new XSSFWorkbook();
         XSSFSheet sheet = workbook.createSheet("Java Books");
         
        Object[][] bookData = {
                {"Head First Java", "Kathy Serria", 79},
                {"Effective Java", "Joshua Bloch", 36},
                {"Clean Code", "Robert martin", 42},
                {"Thinking in Java", "Bruce Eckel", 35},
        };
 
        int rowCount = 0;
         
        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(++rowCount);
             
            int columnCount = 0;
             
            for (Object field : aBook) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
             
        }
         
         
        try (FileOutputStream outputStream = new FileOutputStream("JavaBooks.xlsx")) {
            workbook.write(outputStream);
        }
    }*/
 /*   public static void main(String[] args) {
   
        new ExpotToExcel();
    }*/

 /* class MyListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btn) {
                //     JFileChooser fchoose = new JFileChooser();
                //      int option = fchoose.showSaveDialog(JTableToExcel.this);
                //   if (option == JFileChooser.APPROVE_OPTION) {
                //       String name = fchoose.getSelectedFile().getName();
                //        String path = fchoose.getSelectedFile().getParentFile().getPath();
                //           String file = path + "\\" + name + ".xls";

                export(table, new File("c:" + "\\Sodis-PDF\\" + "test" + ".xls"));
                //   }
            }
        }
    }*/
}
