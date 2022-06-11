/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Achat.pre_commande;

/**
 *
 * @author Mlek
 */
public class pre_commande {
    int id;
    String num_bon_commande;
    int id_fournisseur;
    int id_client;
    String date_pre_commande;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum_bon_commande() {
        return num_bon_commande;
    }

    public void setNum_bon_commande(String num_bon_commande) {
        this.num_bon_commande = num_bon_commande;
    }

    public int getId_fournisseur() {
        return id_fournisseur;
    }

    public void setId_fournisseur(int id_fournisseur) {
        this.id_fournisseur = id_fournisseur;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getDate_pre_commande() {
        return date_pre_commande;
    }

    public void setDate_pre_commande(String date_pre_commande) {
        this.date_pre_commande = date_pre_commande;
    }
    
    
    
}
