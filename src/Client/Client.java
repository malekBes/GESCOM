/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 *
 * @author Mlek
 */
public class Client {

    int Num_Client;
    String Nom;
    String Num_tel;
    String Adresse;
    String Ville;
    String pays;
    String code_Postale;
    String zone_Geo;
    String id_Fiscale;
    String exonere_TVA;
    String Email;
    String site;
    String fax;
    String adresse_livraison;
    String contact_Client;
    String type_Client;
    String Etat_Paiement;
    String agence;
    String Compte_Bank;
    String Fournisseur_Preced;
    String actif;
    String Id_Commercial;
    String Create_Date;
    String Last_Update_Date;
    String CreatedBy;
    String UpdatedBy;
    String seuil_compte;
    String echeance_payement;

    public Client() {
    }

    public String getSeuil_compte() {
        return seuil_compte;
    }

    public void setSeuil_compte(String seuil_compte) {
        this.seuil_compte = seuil_compte;
    }

    public String getEcheance_payement() {
        return echeance_payement;
    }

    public void setEcheance_payement(String echeance_payement) {
        this.echeance_payement = echeance_payement;
    }

    public Client(int Num_Client, String Nom, String Num_tel, String Adresse, String Ville, String pays, String code_Postale, String zone_Geo, String id_Fiscale, String exonere_TVA, String Email, String site, String fax, String adresse_livraison, String contact_Client, String type_Client, String Etat_Paiement, String agence, String Compte_Bank, String Fournisseur_Preced, String actif, String Create_Date, String Last_Update_Date, String CreatedBy, String UpdatedBy, String Id_Commercial) {
        this.Num_Client = Num_Client;
        this.Nom = Nom;
        this.Num_tel = Num_tel;
        this.Adresse = Adresse;
        this.Ville = Ville;
        this.pays = pays;
        this.code_Postale = code_Postale;
        this.zone_Geo = zone_Geo;
        this.id_Fiscale = id_Fiscale;
        this.exonere_TVA = exonere_TVA;
        this.Email = Email;
        this.site = site;
        this.fax = fax;
        this.adresse_livraison = adresse_livraison;
        this.contact_Client = contact_Client;
        this.type_Client = type_Client;
        this.Etat_Paiement = Etat_Paiement;
        this.agence = agence;
        this.Compte_Bank = Compte_Bank;
        this.Fournisseur_Preced = Fournisseur_Preced;
        this.actif = actif;
        this.Id_Commercial = Id_Commercial;
        this.Create_Date = Create_Date;
        this.Last_Update_Date = Last_Update_Date;
        this.CreatedBy = CreatedBy;
        this.UpdatedBy = UpdatedBy;
    }

    public int getNum_Client() {
        return Num_Client;
    }

    public void setNum_Client(int Num_Client) {
        this.Num_Client = Num_Client;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getNum_tel() {
        return Num_tel;
    }

    public void setNum_tel(String Num_tel) {
        this.Num_tel = Num_tel;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String Adresse) {
        this.Adresse = Adresse;
    }

    public String getVille() {
        return Ville;
    }

    public void setVille(String Ville) {
        this.Ville = Ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getCode_Postale() {
        return code_Postale;
    }

    public void setCode_Postale(String code_Postale) {
        this.code_Postale = code_Postale;
    }

    public String getZone_Geo() {
        return zone_Geo;
    }

    public void setZone_Geo(String zone_Geo) {
        this.zone_Geo = zone_Geo;
    }

    public String getId_Fiscale() {
        return id_Fiscale;
    }

    public void setId_Fiscale(String id_Fiscale) {
        this.id_Fiscale = id_Fiscale;
    }

    public String getExonere_TVA() {
        return exonere_TVA;
    }

    public void setExonere_TVA(String exonere_TVA) {
        this.exonere_TVA = exonere_TVA;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAdresse_livraison() {
        return adresse_livraison;
    }

    public void setAdresse_livraison(String adresse_livraison) {
        this.adresse_livraison = adresse_livraison;
    }

    public String getContact_Client() {
        return contact_Client;
    }

    public void setContact_Client(String contact_Client) {
        this.contact_Client = contact_Client;
    }

    public String getType_Client() {
        return type_Client;
    }

    public void setType_Client(String type_Client) {
        this.type_Client = type_Client;
    }

    public String getEtat_Paiement() {
        return Etat_Paiement;
    }

    public void setEtat_Paiement(String Etat_Paiement) {
        this.Etat_Paiement = Etat_Paiement;
    }

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public String getCompte_Bank() {
        return Compte_Bank;
    }

    public void setCompte_Bank(String Compte_Bank) {
        this.Compte_Bank = Compte_Bank;
    }

    public String getFournisseur_Preced() {
        return Fournisseur_Preced;
    }

    public void setFournisseur_Preced(String Fournisseur_Preced) {
        this.Fournisseur_Preced = Fournisseur_Preced;
    }

    public String getActif() {
        return actif;
    }

    public void setActif(String actif) {
        this.actif = actif;
    }

    public String getId_Commercial() {
        return Id_Commercial;
    }

    public void setId_Commercial(String Id_Commercial) {
        this.Id_Commercial = Id_Commercial;
    }

    public String getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(String Create_Date) {
        this.Create_Date = Create_Date;
    }

    public String getLast_Update_Date() {
        return Last_Update_Date;
    }

    public void setLast_Update_Date(String Last_Update_Date) {
        this.Last_Update_Date = Last_Update_Date;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String UpdatedBy) {
        this.UpdatedBy = UpdatedBy;
    }

}
