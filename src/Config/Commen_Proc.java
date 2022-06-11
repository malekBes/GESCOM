/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Malek
 */
public class Commen_Proc {

    public static boolean isRemote = true;
    public static String TimbreVal = "";
    public static String YearVal = "";
    public static String TVAVal = "";
    public static String PathPDF = "";
    public static String PathExcel = "";
    public static String PathImg = "";
 

    public static String Nom_entreprise = "SODISS";

    public static boolean getDirCount() {
        return isRemote;
    }

    public static String roundAvoid(double value, int places) {

        double scale = Math.pow(10, places);
        Double d = Math.round(value * scale) / scale;
        String s = d.toString().replace(".", ";");
        String[] x = s.split(";");
        if (x[1].length() == 1) {
            s = x[0] + "." + x[1] + "00";
        } else if (x[1].length() == 2) {
            s = x[0] + "." + x[1] + "0";
        } else {
            s = s.substring(0, s.indexOf(";") + 4);
            s = s.replace(";", ".");
        }
        return s;
    }

    public static String formatDouble(Double d) {
        DecimalFormat df = new DecimalFormat("0.000");
        df.setRoundingMode(RoundingMode.CEILING); // import java.text.DecimalFormat;
        String s = "0.0";

        try {
            s = df.format(d);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error in formatDouble methode :  " + e.getMessage());

        }
        //s.replace(",", ".")

        return roundAvoid(d, 3);
        /*  String s = d.toString().replace(".", ";");

        try {

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

    public String formatString(String s) {
        DecimalFormat df = new DecimalFormat("0.000");
        df.setRoundingMode(RoundingMode.CEILING);
        Double d1 = 0.0;
        try {
            d1 = Double.parseDouble(s);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error in formatString methode :  " + e.getMessage());
        }
        return d1.toString().replace(",", ".");
        //return s.substring(0, s.indexOf(".") + 4);
    }
}
