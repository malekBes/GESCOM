/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config.Bank;

import Commercial.*;

/**
 *
 * @author Mlek
 */
public class Depot {
    int Id;
    String Nom;
    String code;
    String adresse;
    String client;
 

    public Depot(int Id, String Nom, String Prenom, String Adresse) {
        this.Id = Id;
        this.Nom = Nom;
 
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Depot() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    
    
}
