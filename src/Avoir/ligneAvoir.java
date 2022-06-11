/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Avoir;

import Devis.*;

/**
 *
 * @author Mlek
 */
public class ligneAvoir {

    int id;
    String id_client;
    String id_Devis;
    String ref_article;
    String design;
    Double prix_U;
    String qte;
    Double total_HT;
    String remise;
    String TVA;
    Double totale_TTC;

    public ligneAvoir(int id, String id_client, String id_Devis, String ref_article, String design, Double prix_U, String qte, Double total_HT, String remise, String TVA, Double totale_TTC) {
        this.id = id;
        this.id_client = id_client;
        this.id_Devis = id_Devis;
        this.ref_article = ref_article;
        this.design = design;
        this.prix_U = prix_U;
        this.qte = qte;
        this.total_HT = total_HT;
        this.remise = remise;
        this.TVA = TVA;
        this.totale_TTC = totale_TTC;
    }

    public ligneAvoir() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String getId_Devis() {
        return id_Devis;
    }

    public void setId_Devis(String id_Devis) {
        this.id_Devis = id_Devis;
    }

    public String getRef_article() {
        return ref_article;
    }

    public void setRef_article(String ref_article) {
        this.ref_article = ref_article;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public Double getPrix_U() {
        return prix_U;
    }

    public void setPrix_U(Double prix_U) {
        this.prix_U = prix_U;
    }

    public String getQte() {
        return qte;
    }

    public void setQte(String qte) {
        this.qte = qte;
    }

    public Double getTotal_HT() {
        return total_HT;
    }

    public void setTotal_HT(Double total_HT) {
        this.total_HT = total_HT;
    }

    public String getRemise() {
        return remise;
    }

    public void setRemise(String remise) {
        this.remise = remise;
    }
 
    public String getTVA() {
        return TVA;
    }

    public void setTVA(String TVA) {
        this.TVA = TVA;
    }

    public Double getTotale_TTC() {
        return totale_TTC;
    }

    public void setTotale_TTC(Double totale_TTC) {
        this.totale_TTC = totale_TTC;
    }

    
}
