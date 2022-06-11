/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import Conn.DataBase_connect;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.sql.Connection;

class JTableToExcel extends JFrame {
    //Header de JTable 

    String[] columns = new String[]{
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

    public JTableToExcel() {
        setSize(450, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Exporter JTable en Excel");
        panel.add(btn, BorderLayout.SOUTH);
        panel.add(new JScrollPane(table), BorderLayout.NORTH);
        add(panel);
        setVisible(true);
        btn.addActionListener(new MyListener());
    }

    public void export(JTable table, File file) {
        try {
            TableModel m = table.getModel();
            FileWriter fw = new FileWriter(file);

            for (int i = 0; i < m.getColumnCount(); i++) {
                fw.write(m.getColumnName(i) + "\t");
            }

            fw.write("\n");

            for (int i = 0; i < m.getRowCount(); i++) {
                for (int j = 0; j < m.getColumnCount(); j++) {
                    fw.write(m.getValueAt(i, j).toString() + "\t");
                }
                fw.write("\n");
            }

            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        /*   try {
            Process p = Runtime.getRuntime().exec("C:\\Users\\rss\\Desktop\\RunJobs_joussour_v2_0.1_test\\RunJobs_joussour_v2\\RunJobs_joussour_v2_run.bat");
            p.waitFor();
            System.out.println("exit code: " + p.exitValue());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
        try {

            DataBase_connect obj = new DataBase_connect();

            Connection conn = obj.Open();

            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /*   String[] columns = {"a", "f", "b", "x", "c"};

        for (String valeur : columns) {
            System.out.print(valeur + " ");
        }
        System.out.println();*/
        //     new JTableToExcel();
    }

    public static void trier(int[] tableau) {
        int i, j, cle;

        for (i = 1; i < tableau.length; i++) {
            cle = tableau[i];
            j = i;
            while ((j >= 1) && (tableau[j - 1] > cle)) {
                tableau[j] = tableau[j - 1];
                j = j - 1;
            }
            tableau[j] = cle;
        }
    }

    class MyListener implements ActionListener {

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
    }
}
