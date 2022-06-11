/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facture;

/**
 *
 * @author Mlek
 */
public class Facture {

    int id_client;
    String date_facture;
    String num_facture;
    String adresse;
    String info_facture;
    String timbre;
    Double HT;
    Double remise;
    Double TVA;
    Double TTC;
    boolean editer_entet;
    int id_fournisseur;
    
   

    public Facture() {
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

    public String getDate_facture() {
        return date_facture;
    }

    public void setDate_facture(String date_facture) {
        this.date_facture = date_facture;
    }

    public String getNum_facture() {
        return num_facture;
    }

    public void setNum_facture(String num_facture) {
        this.num_facture = num_facture;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getInfo_facture() {
        return info_facture;
    }

    public void setInfo_facture(String info_facture) {
        this.info_facture = info_facture;
    }

    public String isTimbre() {
        return timbre;
    }

    public void setTimbre(String timbre) {
        this.timbre = timbre;
    }

    public boolean isEditer_entet() {
        return editer_entet;
    }

    public void setEditer_entet(boolean editer_entet) {
        this.editer_entet = editer_entet;
    }

    public Double getHT() {
        return HT;
    }

    public void setHT(Double HT) {
        this.HT = HT;
    }

    public Double getRemise() {
        return remise;
    }

    public void setRemise(Double remise) {
        this.remise = remise;
    }

    public Double getTVA() {
        return TVA;
    }

    public void setTVA(Double TVA) {
        this.TVA = TVA;
    }

    public Double getTTC() {
        return TTC;
    }

    public void setTTC(Double TTC) {
        this.TTC = TTC;
    }

}
