/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commercial;

/**
 *
 * @author Mlek
 */
public class Commercial {
    int Id;
    String Nom;
    String Prenom;
    String Adresse;

    public Commercial(int Id, String Nom, String Prenom, String Adresse) {
        this.Id = Id;
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.Adresse = Adresse;
    }

    public Commercial() {
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

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String Prenom) {
        this.Prenom = Prenom;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String Adresse) {
        this.Adresse = Adresse;
    }
    
    
}
