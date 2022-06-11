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
public class Famille {
    int Id;
    String Nom;
    
 

    public Famille(int Id, String Nom, String Prenom, String Adresse) {
        this.Id = Id;
        this.Nom = Nom;
 
    }

    public Famille() {
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
