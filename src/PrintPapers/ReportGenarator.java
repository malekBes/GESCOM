/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrintPapers;

import Config.Commen_Proc;
import java.io.File;
import java.sql.Connection;
import java.util.Map;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Mlek
 */
public class ReportGenarator {

    public static String OUT_PUT = "C:/App_Megrine_Emballage/PDF/devis.pdf";

    public static String REPORT_Devis = "PrintPapers/PrintDevis.jrxml";
    public static String REPORT_Devis_test = "PrintPapers/PrintDevis_1.jrxml";

    public static String REPORT_Reliquat = "PrintPapers/PrintReliquat.jrxml";
    public static String REPORT_Reliquat_Entete = "PrintPapers/PrintReliquat_Entete.jrxml";

    public static String REPORT_BL = "PrintPapers/PrintBL.jrxml";
    public static String REPORT_Facture = "PrintPapers/PrintFacture.jrxml";
    public static String REPORT_Avoir = "PrintPapers/PrintAvoir.jrxml";

    public static String REPORT_Devis_Entete = "PrintPapers/PrintDevis_Entete.jrxml";
    public static String REPORT_BL_Entete = "PrintPapers/PrintBL_Entete.jrxml";
    public static String REPORT_Facture_Entete = "PrintPapers/PrintFacture_Entete.jrxml";
    public static String REPORT_Avoir_Entete = "PrintPapers/PrintAvoir_Entete.jrxml";
    public static String STAT_Pre_Commande = "statPrint/Pre_Commande.jrxml";
    public static String STAT_Commande = "statPrint/Commande.jrxml";
    

    public void genarateReport(String reportPath,
            Map<String, Object> map, Connection con, String filename, JTable table, String num) {
        try {
            // export to pdf
            JasperReport jr = JasperCompileManager.compileReport(
                    ClassLoader.getSystemResourceAsStream(reportPath));

            JasperPrint jp = JasperFillManager.fillReport(jr, map, con);

            JasperExportManager.exportReportToPdfFile(jp, Commen_Proc.PathPDF + filename + ".pdf");
            JasperViewer.viewReport(jp, false);

            // export to excel
            File myObj = new File(Commen_Proc.PathExcel + filename + ".xls");
            Config.ExpotToExcel.exportExcel(table, myObj, num, map);

            //

            /*    JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
                    reportPath);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                    "C:\\Sodis-EXCEL\\sample_report.xls");

         JRXlsExporter exporter1 = new JRXlsExporter();

            JasperPrint jasperPrint = (JasperPrint) JasperFillManager.fillReport(jr, map, con);

            exporter1.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter1.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\Sodis-EXCEL\\sample_report.xls"));
            exporter1.exportReport();
            ///*/
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
}
