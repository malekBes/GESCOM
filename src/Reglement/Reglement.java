/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reglement;

/**
 *
 * @author Mlek
 */
public class Reglement {

    int id;
    int id_client;
    int id_fournisseur;
    String Passager;
    String num_facture;
    String date_reglement;
    String date_echeance;
    String type_reglement;
    Double comptabilise;
    Double regle;
    String banque;
    String num_cheque;
    String num_avoir;

    public Reglement() {
    }

    public int getId() {
        return id;
    }

    public int getId_fournisseur() {
        return id_fournisseur;
    }

    public void setId_fournisseur(int id_fournisseur) {
        this.id_fournisseur = id_fournisseur;
    }

    public String getPassager() {
        return Passager;
    }

    public void setPassager(String Passager) {
        this.Passager = Passager;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getNum_facture() {
        return num_facture;
    }

    public void setNum_facture(String num_facture) {
        this.num_facture = num_facture;
    }

    public String getDate_reglement() {
        return date_reglement;
    }

    public void setDate_reglement(String date_reglement) {
        this.date_reglement = date_reglement;
    }

    public String getDate_echeance() {
        return date_echeance;
    }

    public void setDate_echeance(String date_echeance) {
        this.date_echeance = date_echeance;
    }

    public String getType_reglement() {
        return type_reglement;
    }

    public void setType_reglement(String type_reglement) {
        this.type_reglement = type_reglement;
    }

    public Double getComptabilise() {
        return comptabilise;
    }

    public void setComptabilise(Double comptabilise) {
        this.comptabilise = comptabilise;
    }

    public Double getRegle() {
        return regle;
    }

    public void setRegle(Double regle) {
        this.regle = regle;
    }

    public String getBanque() {
        return banque;
    }

    public void setBanque(String banque) {
        this.banque = banque;
    }

    public String getNum_cheque() {
        return num_cheque;
    }

    public void setNum_cheque(String num_cheque) {
        this.num_cheque = num_cheque;
    }

    public String getNum_avoir() {
        return num_avoir;
    }

    public void setNum_avoir(String num_avoir) {
        this.num_avoir = num_avoir;
    }

    @Override
    public String toString() {
        return "Reglement{" + "id=" + id + ", id_client=" + id_client + ", num_facture=" + num_facture + ", date_reglement=" + date_reglement + ", date_echeance=" + date_echeance + ", type_reglement=" + type_reglement + ", comptabilise=" + comptabilise + ", regle=" + regle + ", banque=" + banque + ", num_cheque=" + num_cheque + ", num_avoir=" + num_avoir + '}';
    }

}
