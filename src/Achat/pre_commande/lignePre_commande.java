/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Achat.pre_commande;

import Devis.*;

/**
 *
 * @author Mlek
 */
public class lignePre_commande {

    int id;
    String id_pre_commande;
    String ref_article;
    String design;
    String qte;
    Double Prix_u;
    String rank;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrix_u() {
        return Prix_u;
    }

    public void setPrix_u(Double Prix_u) {
        this.Prix_u = Prix_u;
    }

    public String getId_pre_commande() {
        return id_pre_commande;
    }

    public void setId_pre_commande(String id_pre_commande) {
        this.id_pre_commande = id_pre_commande;
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

    public String getQte() {
        return qte;
    }

    public void setQte(String qte) {
        this.qte = qte;
    }

}
