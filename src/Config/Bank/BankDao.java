/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config.Bank;

import Commercial.*;
import Client.ClientDao;
import Conn.DataBase_connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mlek
 */
public class BankDao {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void ajouterBank(Bank c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into bank(nom,adresse) "
                    + "values('" + c.getNom() + "','" + c.getAdresse() + "')";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Enregistré");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void supprimerBank(String nom) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "delete from bank where nom ='" + nom + "'";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprimé");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void supprimerTypeReglements(String nom) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "delete from type_reglement where type ='" + nom + "'";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprimé");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void supprimerZoneGeo(String nom) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "delete from zone_geo where zone ='" + nom + "'";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprimé");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void supprimerTypeClient(String nom) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "delete from type_client where type ='" + nom + "'";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprimé");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void supprimerDepot(String nom) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "delete from depot where id =" + nom + "";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprimé");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void supprimerEtatPayement(String nom) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "delete from etat_payement where etat ='" + nom + "'";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprimé");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void supprimerFamille(String nom) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "delete from famille_produit where famille ='" + nom + "'";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprimé");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void supprimerSousFamille(String nom) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "delete from sous_famille where Sous_famille ='" + nom + "'";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprimé");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void supprimerFournisseur(String nom) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "delete from fournisseur where id =" + nom + "";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprimé");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void supprimerMarque(String nom) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "delete from marque_produit where marque ='" + nom + "'";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprimé");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void ajouterFamille(Famille c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into famille_produit(famille) "
                    + "values('" + c.getNom() + "')";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Enregistré");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void ajouterTypeClient(Famille c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into type_client(type) "
                    + "values('" + c.getNom() + "')";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Enregistré");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void ajouterTypeReglement(Famille c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into type_reglement(type) "
                    + "values('" + c.getNom() + "')";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Enregistré");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void ajouterEtatPaiement(Famille c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into etat_payement(etat) "
                    + "values('" + c.getNom() + "')";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Enregistré");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void ajouterZoneGeo(Famille c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into zone_geo(zone) "
                    + "values('" + c.getNom() + "')";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Enregistré");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void ajouterSousFamille(SousFamille c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into sous_famille(sous_famille,id_famille) "
                    + "values('" + c.getNom() + "'," + c.getId_famille() + ")";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Enregistré");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void ajouterMarque(Famille c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into marque_produit(marque) "
                    + "values('" + c.getNom() + "')";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Enregistré");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void ajouterDepot(Depot c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into depot(nom,code,client,adresse) "
                    + "values('" + c.getNom() + "','" + c.getCode() + "','" + c.getClient() + "','" + c.getAdresse() + "')";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Saved");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void ajouterFour(Fournisseur c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "INSERT into fournisseur(nom,email,num_tel,adresse) "
                    + "values('" + c.getNom() + "','" + c.getEmail() + "','" + c.getNum_tel() + "','" + c.getAdresse() + "')";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Saved");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void updateDepot(Depot c) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        String sql = "";
        try {

            sql = "UPDATE `depot` SET `nom`= '" + c.getNom() + "',`code`='" + c.getCode() + "',`client`='" + c.getClient() + "',`adresse`='" + c.getAdresse() + "'  WHERE id = " + c.getId() + "";

            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Modification réussite");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, "Error in :  " + ex);
            }
        }

    }

    public void afficherBank(JTable table) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  nom,adresse FROM `bank` ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherFamille(JTable table) {
        PreparedStatement pst;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  famille FROM `famille_produit` ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherTypeClient(JTable table) {
        PreparedStatement pst;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  type FROM `type_client` ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherTypeReglement(JTable table) {
        PreparedStatement pst;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  type FROM `type_reglement` ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherEtatPayement(JTable table) {
        PreparedStatement pst;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  etat FROM `etat_payement` ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherZoneGeo(JTable table) {
        PreparedStatement pst;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  zone FROM `zone_geo` ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherSousFamille(JTable table) {
        PreparedStatement pst;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  f.famille, sf.sous_famille FROM sous_famille sf, `famille_produit` f where f.id = sf.id_famille ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherMarque(JTable table) {
        PreparedStatement pst;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  marque FROM `marque_produit` ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherDepot(JTable table) {
        PreparedStatement pst;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  id ,`code`,`nom` , `client`, `adresse` FROM `depot` ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherFour(JTable table) {
        PreparedStatement pst;
        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  * FROM `Fournisseur` ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FormCommercial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    public void afficherCommercial(JTable table) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  nom , adresse FROM `bank`";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FormBank.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void afficherCommercial2(JTable table) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();

        Connection conn = obj.Open();
        try {
            String sql;
            //numero_Client, nom, num_Tel, adresse, Ville, pays, code_Postale, zone_Geo, id_Fiscale, Email, site, fax, adresse_livraison, contact_Client, type_Client, Etat_Paiement, agence, Compte_Bank, Fournisseur_Preced, actif, Id_Commercial
            sql = "SELECT  * FROM `commercial`";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel df = buildTableModel(rs);
            table.setModel(df);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();
                System.out.println("disconnected");
            } catch (SQLException ex) {
                Logger.getLogger(FormBank.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public HashMap listBank(JComboBox comboBox) {
        PreparedStatement pst;

        DataBase_connect obj = new DataBase_connect();
        HashMap<String, String> map = new HashMap<>();

        Connection conn = obj.Open();
        comboBox.removeAllItems();
        comboBox.addItem("-");
        try {
            String sql = "select id,nom from bank";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                map.put(rs.getString("nom"), rs.getString("id"));

                //comboBox.addItem(comm);
            }

            for (String s : map.keySet()) {
                comboBox.addItem(s);
            }
            return map;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                conn.close();

                System.out.println("disconnected");

            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return map;
    }

}
