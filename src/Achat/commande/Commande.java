/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Achat.commande;

/**
 *
 * @author Mlek
 */
public class Commande {
    
    String num_Commande;
    int id_fournisseur;
    String date_commande;

    public String getNum_Commande() {
        return num_Commande;
    }

    public void setNum_Commande(String num_Commande) {
        this.num_Commande = num_Commande;
    }

    public int getId_fournisseur() {
        return id_fournisseur;
    }

    public void setId_fournisseur(int id_fournisseur) {
        this.id_fournisseur = id_fournisseur;
    }

    public String getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(String date_commande) {
        this.date_commande = date_commande;
    }
    
    
}
