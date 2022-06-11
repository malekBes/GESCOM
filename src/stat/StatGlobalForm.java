/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stat;

import Recherche.*;
import BL.*;
import Devis.*;
import Article.ArticleDao;
import Client.ClientDao;
import Conn.DataBase_connect;
import Home.App;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;

import net.sf.jasperreports.engine.JasperReport;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

import net.sf.jasperreports.view.JasperViewer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author Mlek
 */
public class StatGlobalForm extends javax.swing.JInternalFrame {

    /**
     * Creates new form form
     */
    public StatGlobalForm() {
         initComponents();
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        //curent date
        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        //page();
        //test();
        jfreetest();
        //TestTableSortFilter();
    }

    public void test() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(620, 440);
        final JFXPanel fxpanel = new JFXPanel();
        frame.add(fxpanel);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                WebEngine engine;
                WebView wv = new WebView();
                engine = wv.getEngine();
                fxpanel.setScene(new Scene(wv));
                engine.load("http://localhost:8070/knowage/servlet/AdapterHTTP?PAGE=LoginPage&NEW_SESSION=TRUE&doc=formation&token=123");
            }
        });
        frame.setVisible(true);

    }

    public void page() {
        try {

            JEditorPane website = new JEditorPane();
            website.setSize(512, 342);
            website.setPage("http://localhost:8070/knowage/servlet/AdapterHTTP?PAGE=LoginPage&NEW_SESSION=TRUE&dev_mode=true&token=123");
            this.add(website);
            /* JFrame frame = new JFrame("Google");
            frame.add(website);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.pack();*/
        } catch (Exception e) {
        }
    }

    public void jasperReport() {
        try {
            DataBase_connect obj = new DataBase_connect();
            HashMap map = new HashMap<>();

            Connection conn = obj.Open();
            JasperDesign jd = JRXmlLoader.load("C:\\Users\\Mlek\\Documents\\NetBeansProjects\\SODIS\\src\\stat\\Dashboard.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JRViewer v = new JRViewer(jp);
            add(v);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Error in ClientFrom/Ajouter_btn :  " + e);

        }
    }

    public void testJesper() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/megrineemballage", "root", "");
            JasperDesign jd = JRXmlLoader.load("C:\\App_Megrine_Emballage\\report\\fiche_article.jrxml");
            String s
                    = "select r.*,c.nom as 'NomClient',c.adresse as 'ClientAdresse',"
                    + "c.num_tel as 'ClientNumTel',c.fax as 'Clientfax',c.site as 'ClientSite',c.email as 'ClientEmail'"
                    + " from article r,client c "
                    + "where "
                    + "r.id_client=c.id ";
            JRDesignQuery jrds = new JRDesignQuery();
            jrds.setText(s);
            jd.setQuery(jrds);

            HashMap<String, Object> h = new HashMap<>();
            String dim = "";

            dim = "50*50";

            h.put("dim", dim);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, h, conn);
            //  DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            // Date dateobj = new Date();
            //  JasperExportManager.exportReportToPdfFile(jp, "C:\\App_Megrine_Emballage\\PDF\\Fiche_" + 50 + "_" + df.format(dateobj) + ".pdf");

            //JasperViewer.viewReport(jp, false);
            JRViewer v = new JRViewer(jp);
            //jPanel1.add(v);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in ClientFrom/Ajouter_btn :  " + e);

        }
    }

    public void jfreetest() {

        XYDataset ds = createDataset();

        JFreeChart barChart1
                = ChartFactory.createXYLineChart("Histogram1", "x", "y", ds,
                        PlotOrientation.VERTICAL, true, true, false);
        ChartPanel c = new ChartPanel(barChart1);
        // c.setSize(imin isar, thbet louta);
        c.setSize(400, 300);
        c.setLocation(0, 0);
        getContentPane().add(c);
 

        pack();

    }

    private static XYDataset createDataset() {

        DefaultXYDataset ds = new DefaultXYDataset();
     
        double[][] data = {{}, {1, 2, 3}};

        ds.addSeries("CA", data);

        return ds;
    }

    /**
     * This method is called from within the constructor to initialize the
     * FormBL. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setClosable(true);
        setTitle("Statistiques");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1123, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 622, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
