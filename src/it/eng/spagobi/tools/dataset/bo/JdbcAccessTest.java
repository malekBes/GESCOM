
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import it.eng.spagobi.tools.dataset.bo.IJavaClassDataSet;
import java.util.Map;
import java.util.List;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
 
/**
 *
 * @author rss
 */

/* 
Learn-Spagobi.Com 
If you want the compiled class, request at info@learn-spagobi.com 
 */
public class JdbcAccessTest implements IJavaClassDataSet {

    /*   public String getValues(Map userProfileAttributes, Map parameters) {

        String cities = "<ROWS><ROW Kenyan_Cities=\"Nairobi\"/><ROW Kenyan_Cities=\"Mombasass\"/></ROWS>";
        //Show the xml string on the console  
        String path = parameters.get("mdb_file_path").toString();
        String query = parameters.get("mdb_query").toString();

        System.out.println(path + "   " + query);

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String databaseURL = "jdbc:ucanaccess://" + path;

            try (Connection connection = DriverManager.getConnection(databaseURL)) {

                String sql = query;

                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);

                while (result.next()) {
                    int id = result.getInt("code_client");
                    String fullname = result.getString("adresse");
                    String email = result.getString("fax");
                    String phone = result.getString("tel");

                    System.out.println(id + ", " + fullname + ", " + email + ", " + phone);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {

        }
        return cities;
    }*/
    @Override
    public String getValues(Map userProfileAttributes, Map parameters) {

        StringWriter sw = new StringWriter();
        String xlmResult = "";
        try {
            String path = parameters.get("mdb_file_path").toString();
            String query = parameters.get("mdb_query").toString();
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String databaseURL = "jdbc:ucanaccess://" + path;

            try (Connection connection = DriverManager.getConnection(databaseURL.replace("'", ""))) {
                ResultSet rs = connection.createStatement().executeQuery(query.replace("'", ""));

                ResultSetMetaData rsmd = rs.getMetaData();
                int colCount = rsmd.getColumnCount();
                xlmResult = "<ROWS>";

                while (rs.next()) {
                    xlmResult += "<ROW";
                    for (int i = 1; i <= colCount; i++) {

                        String columnName = rsmd.getColumnName(i);

                        Object value = rs.getObject(i);

                        if (value == null || value.equals("")) {
                            value = "-";
                        }
                        xlmResult += " " + columnName + "=\"" + value.toString().replace("&", "+") + "\"";
                        // System.out.print(columnName + " : " + value.toString() + " ");

                    }
                    xlmResult += "/>";

                }

                xlmResult += "</ROWS>";
                System.out.println(xlmResult);

                connection.close();
                rs.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return xlmResult;
    }

    @Override
    public List getNamesOfProfileAttributeRequired() {
        return null;
    }

    /* public static void main(String[] args) {
        StringWriter sw = new StringWriter();
        String xlmResult = "";
        try {
            String path = "C://Users//rss//Desktop//data JBJ//SODISS.mdb";
            String query = "select * from clients";
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String databaseURL = "jdbc:ucanaccess://" + path;
            try (Connection connection = DriverManager.getConnection(databaseURL)) {
                ResultSet rs = connection.createStatement().executeQuery(query);

                ResultSetMetaData rsmd = rs.getMetaData();
                int colCount = rsmd.getColumnCount();
                xlmResult = "<ROWS>";

                while (rs.next()) {
                    xlmResult += "<ROW ";
                    for (int i = 1; i <= colCount; i++) {

                        String columnName = rsmd.getColumnName(i);

                        Object value = rs.getObject(i);

                        if (value == null) {
                            value = "";
                        }
                        xlmResult += "" + columnName + "=\"" + value.toString() + "\" ";
                        // System.out.print(columnName + " : " + value.toString() + " ");

                    }
                    xlmResult += " />";

                }

                xlmResult += "</ROWS>";
                System.out.println(xlmResult);

                connection.close();
                rs.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/
  /*  public static void main(String args[]) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element results = doc.createElement("ROWS");
        doc.appendChild(results);

        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        String databaseURL = "jdbc:ucanaccess://C://Users//rss//Desktop//data JBJ//SODISS.mdb";
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            ResultSet rs = connection.createStatement().executeQuery("select * from clients limit 20");

            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();
            String xlmResult = "<ROWS>";
            rs = connection.createStatement().executeQuery("select * from clients  limit 20");
            while (rs.next()) {
                xlmResult += "<ROW ";
                for (int i = 1; i <= colCount; i++) {

                    String columnName = rsmd.getColumnName(i);

                    Object value = rs.getObject(i);

                    if (value == null) {
                        value = "";
                    }
                    xlmResult += "" + columnName + "=\"" + value.toString() + "\" ";
                    // System.out.print(columnName + " : " + value.toString() + " ");
 System.out.println(xlmResult);
                }
                xlmResult += " />";
               
            }

            xlmResult += "</ROWS>";
           // System.out.println(xlmResult);
            //System.out.println(sw.toString());
            connection.close();
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }*/
}
