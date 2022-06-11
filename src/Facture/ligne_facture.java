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
public class ligne_facture {

    int id;
    String num_facture;
    String num_bl;

    public String getNum_facture() {
        return num_facture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNum_facture(String num_facture) {
        this.num_facture = num_facture;
    }

    public String getNum_bl() {
        return num_bl;
    }

    public void setNum_bl(String num_bl) {
        this.num_bl = num_bl;
    }

}
