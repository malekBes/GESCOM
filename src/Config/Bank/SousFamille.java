/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config.Bank;

/**
 *
 * @author Mlek
 */
public class SousFamille {

    int Id;
    String Nom;
    int id_famille;

    public SousFamille(int Id, String Nom, String Prenom, String Adresse) {
        this.Id = Id;
        this.Nom = Nom;

    }

    public int getId_famille() {
        return id_famille;
    }

    public void setId_famille(int id_famille) {
        this.id_famille = id_famille;
    }

    public SousFamille() {
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
