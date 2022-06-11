/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stat;

/**
 *
 * @author Mlek
 */
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * @author imssbora
 *
 */
public class LineChartExample extends JFrame {

    private static final long serialVersionUID = 1L;

    public LineChartExample() {

        // Create dataset
    }

    public ChartPanel createChart(JTable table) {
        DefaultCategoryDataset dataset = createDataset(table);
        // Create chart
        JFreeChart chart = ChartFactory.createLineChart(
                "Evolution CA Pa Client", // Chart title
                "Date", // X-Axis Label
                "CA", // Y-Axis Label
                dataset
        );

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
        return panel;
    }

    private DefaultCategoryDataset createDataset(JTable table) {
        DefaultCategoryDataset dataset = null;
        try {

            int maxVal = 0;
            int minVal = 0;
            String[] lstDate = null;
            String Dates = "";
            for (int j = 0; j < table.getRowCount() - 1; j++) {

                String date = table.getValueAt(j, 1).toString();
                Dates += date.split("/")[0] + ",";

            }
            Dates = Dates.substring(0, Dates.length() - 1);
            lstDate = Dates.split(",");
            List<String> wordList = Arrays.asList(lstDate);
            String series1 = "CA";
            minVal = Integer.valueOf(lstDate[0]);

            maxVal = Integer.valueOf(lstDate[lstDate.length - 1]);
            String s = "";
            String s1 = "";
            int monthTable = 0;
            dataset = new DefaultCategoryDataset();
            int[] LstAllValues = new int[maxVal + 1];
            int x = 0;
            for (int j = 1; j < maxVal + 1; j++) {
                //  LstAllValues[j] = j;
                String val = String.valueOf(j).length() == 1 ? "0" + String.valueOf(j) : String.valueOf(j);
                String date = "";
                String caClient = "";
                if (!wordList.contains(val)) {
                    date = val;
                    monthTable = 0;

                    monthTable = Integer.valueOf(date);

                    // double caClients = Double.valueOf(caClient);
                    if (!date.isEmpty()) {
                        String Mois = "";
                        if (monthTable == 1) {
                            Mois = "janv";
                        } else if (monthTable == 2) {
                            Mois = "févr";
                        } else if (monthTable == 3) {
                            Mois = "mars";
                        } else if (monthTable == 4) {
                            Mois = "avr";
                        } else if (monthTable == 5) {
                            Mois = "mai";
                        } else if (monthTable == 6) {
                            Mois = "juin";
                        } else if (monthTable == 7) {
                            Mois = "juill";
                        } else if (monthTable == 8) {
                            Mois = "août";
                        } else if (monthTable == 9) {
                            Mois = "sept";
                        } else if (monthTable == 10) {
                            Mois = "oct";
                        } else if (monthTable == 11) {
                            Mois = "nov";
                        } else if (monthTable == 12) {
                            Mois = "déc";
                        }

                        dataset.addValue(0.0, series1, Mois);

                    }
                } else {
                    s1 += j + " ";

                    date = table.getValueAt(x, 1).toString();
                    caClient = table.getValueAt(x, 2).toString();
                    monthTable = 0;
                    if (!date.isEmpty()) {

                        monthTable = Integer.valueOf(date.split("/")[0]);
                    }
                    double caClients = Double.valueOf(caClient);
                    if (!date.isEmpty()) {
                        String Mois = "";
                        if (monthTable == 1) {
                            Mois = "janv";
                        } else if (monthTable == 2) {
                            Mois = "févr";
                        } else if (monthTable == 3) {
                            Mois = "mars";
                        } else if (monthTable == 4) {
                            Mois = "avr";
                        } else if (monthTable == 5) {
                            Mois = "mai";
                        } else if (monthTable == 6) {
                            Mois = "juin";
                        } else if (monthTable == 7) {
                            Mois = "juill";
                        } else if (monthTable == 8) {
                            Mois = "août";
                        } else if (monthTable == 9) {
                            Mois = "sept";
                        } else if (monthTable == 10) {
                            Mois = "oct";
                        } else if (monthTable == 11) {
                            Mois = "nov";
                        } else if (monthTable == 12) {
                            Mois = "déc";
                        }

                        dataset.addValue(caClients, series1, Mois);
                        x++;
                    }
                }
            }
           
        } catch (Exception e) {
        }
        return dataset;
    }
}
