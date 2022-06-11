/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Devis;

import java.util.Date;

/**
 *
 * @author Mlek
 */
public class Devis {

    int id;
    String Num_Devis;
    String Date_devis;
    int id_client;
    Double remise;
    Double Totale_HT;
    Double Totale_TTC;
    Double montant_TVA;
    String Code_TVA;
    String infos_Devis;
    int exh_TVA;
    int Afficher_total;
    int facture_proformat;
    int Afficher_validiter;
    int Afficher_prix;
    int Edit_ref;
    int timbre;
    String passager;
    String code_passager;
    String Adresse;

    public Devis(int id, String Num_Devis, String Date_devis, int id_client, Double remise, Double Totale_HT, Double Totale_TTC, String Code_TVA, String infos_Devis, int exh_TVA, int Afficher_total, int facture_proformat, int Afficher_validiter, int Afficher_prix, int Edit_ref) {
        this.id = id;
        this.Num_Devis = Num_Devis;
        this.Date_devis = Date_devis;
        this.id_client = id_client;
        this.remise = remise;
        this.Totale_HT = Totale_HT;
        this.Totale_TTC = Totale_TTC;
        this.Code_TVA = Code_TVA;
        this.infos_Devis = infos_Devis;
        this.exh_TVA = exh_TVA;
        this.Afficher_total = Afficher_total;
        this.facture_proformat = facture_proformat;
        this.Afficher_validiter = Afficher_validiter;
        this.Afficher_prix = Afficher_prix;
        this.Edit_ref = Edit_ref;
    }

    public Devis() {
    }

    public String getCode_passager() {
        return code_passager;
    }

    public void setCode_passager(String code_passager) {
        this.code_passager = code_passager;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String Adresse) {
        this.Adresse = Adresse;
    }

    public String getPassager() {
        return passager;
    }

    public void setPassager(String passager) {
        this.passager = passager;
    }

    public int getTimbre() {
        return timbre;
    }

    public void setTimbre(int timbre) {
        this.timbre = timbre;
    }

    public Double getMontant_TVA() {
        return montant_TVA;
    }

    public void setMontant_TVA(Double montant_TVA) {
        this.montant_TVA = montant_TVA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum_Devis() {
        return Num_Devis;
    }

    public void setNum_Devis(String Num_Devis) {
        this.Num_Devis = Num_Devis;
    }

    public String getDate_devis() {
        return Date_devis;
    }

    public void setDate_devis(String Date_devis) {
        this.Date_devis = Date_devis;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public Double getRemise() {
        return remise;
    }

    public void setRemise(Double remise) {
        this.remise = remise;
    }

    public Double getTotale_HT() {
        return Totale_HT;
    }

    public void setTotale_HT(Double Totale_HT) {
        this.Totale_HT = Totale_HT;
    }

    public Double getTotale_TTC() {
        return Totale_TTC;
    }

    public void setTotale_TTC(Double Totale_TTC) {
        this.Totale_TTC = Totale_TTC;
    }

    public String getCode_TVA() {
        return Code_TVA;
    }

    public void setCode_TVA(String Code_TVA) {
        this.Code_TVA = Code_TVA;
    }

    public String getInfos_Devis() {
        return infos_Devis;
    }

    public void setInfos_Devis(String infos_Devis) {
        this.infos_Devis = infos_Devis;
    }

    public int getExh_TVA() {
        return exh_TVA;
    }

    public void setExh_TVA(int exh_TVA) {
        this.exh_TVA = exh_TVA;
    }

    public int getAfficher_total() {
        return Afficher_total;
    }

    public void setAfficher_total(int Afficher_total) {
        this.Afficher_total = Afficher_total;
    }

    public int getFacture_proformat() {
        return facture_proformat;
    }

    public void setFacture_proformat(int facture_proformat) {
        this.facture_proformat = facture_proformat;
    }

    public int getAfficher_validiter() {
        return Afficher_validiter;
    }

    public void setAfficher_validiter(int Afficher_validiter) {
        this.Afficher_validiter = Afficher_validiter;
    }

    public int getAfficher_prix() {
        return Afficher_prix;
    }

    public void setAfficher_prix(int Afficher_prix) {
        this.Afficher_prix = Afficher_prix;
    }

    public int getEdit_ref() {
        return Edit_ref;
    }

    public void setEdit_ref(int Edit_ref) {
        this.Edit_ref = Edit_ref;
    }

}
