/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import Config.ConfigDao;
import Conn.DataBase_connect;
import java.awt.BorderLayout;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class TestTableSortFilter extends JPanel {

    JPanel TestTableSortFilter1() {
        String[] columnNames
                = {"Country", "Capital", "Population in Millions", "Democracy"};

        Object[][] data = {
            {"USA", "Washington DC", 280, true},
            {"Canada", "Ottawa", 32, true},
            {"United Kingdom", "London", 60, true},
            {"Germany", "Berlin", 83, true},
            {"France", "Paris", 60, true},
            {"Norway", "Oslo", 4.5, true},
            {"India", "New Delhi", 1046, true}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable jTable = new JTable(model);

        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(jTable.getModel());

        JTextField jtfFilter = new JTextField();

        JButton jbtValider = new JButton("Valider");

        jTable.setRowSorter(rowSorter);
        JPanel Homepanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());

        panel.add(
                new JLabel("Specify a word to match:"),
                BorderLayout.WEST);
        panel.add(jtfFilter, BorderLayout.CENTER);

        panel.add(jbtValider, BorderLayout.SOUTH);

        Homepanel.add(panel, BorderLayout.SOUTH);

        Homepanel.add(
                new JScrollPane(jTable), BorderLayout.CENTER);

        jtfFilter.getDocument()
                .addDocumentListener(new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e
                    ) {
                        String text = jtfFilter.getText();

                        if (text.trim().length() == 0) {
                            rowSorter.setRowFilter(null);
                        } else {
                            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e
                    ) {
                        String text = jtfFilter.getText();

                        if (text.trim().length() == 0) {
                            rowSorter.setRowFilter(null);
                        } else {
                            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                        }
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e
                    ) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                }
                );
        return Homepanel;
    }
    String s = "";

    public String go() {

        /* SwingUtilities.invokeLater(new Runnable() {
            public void run() {*/
        JFrame frame = new JFrame("Row Filter");
        frame.add(new TestTableSortFilter());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        /*   }

        });*/

        return s;//jTable.getModel().getValueAt(1, 1).toString();
    }

    public static void main(String[] args) {

       /* ConfigDao c = new ConfigDao();

        System.out.println(c.DateCheckOnUpdateTest("devis", "date_devis", "num_devis", "20D00053", "2020-06-12", "Yes"));
       */
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        
        /*  String ss = "111111";
        String num_devis;
        int x = Integer.valueOf(ss) + 1;
        if (ss.length() == 1) {
            num_devis = "0000" + x;
        } else if (ss.length() == 2) {
            num_devis = "000" + x;
        } else if (ss.length() == 3) {
            num_devis = "00" + x;
        } else if (ss.length() >= 5) {
            num_devis = "" + x;
        } else {
            num_devis = "0" + x;
        }
        System.out.println(num_devis);*/
 /*    
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Row Filter");
                frame.add(new TestTableSortFilter());
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

        });*/
    }

}
