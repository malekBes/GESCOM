/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Article;

/**
 *
 * @author Mlek
 */
public class Article {

    int id;
    String ref;
    String designation;
    String marque;
    String id_famille;
    String id_sous_famille;
    String id_fournisseur;
    String TVA;
    String isImporte;
    double prix_achat;
    double prix_vente;
    String douane;
    String remise_four;
    int qte;
    int detail_qte;

    int stock_negative;
    double marge;
    String VIP;
    double VIP_pourcentage;
    String code_barres;
    String pays_origine;
    String id_depot;
    String depot;
    String id_client;
    Double remise;
    String type_article;
    String code_1;
    String code_2;
    String code_3;

    public Article() {
    }

    public String getType_article() {
        return type_article;
    }

    public void setType_article(String type_article) {
        this.type_article = type_article;
    }

    public Article(int id, String ref, String designation, String marque, String id_famille, String id_sous_famille, String id_fournisseur, String TVA, String isImporte, double prix_achat, double prix_vente, String douane, String remise_four, int qte, int stock_negative, double marge, String VIP, double VIP_pourcentage, String code_barres, String pays_origine, String depot) {
        this.id = id;
        this.ref = ref;
        this.designation = designation;
        this.marque = marque;
        this.id_famille = id_famille;
        this.id_sous_famille = id_sous_famille;
        this.id_fournisseur = id_fournisseur;
        this.TVA = TVA;
        this.isImporte = isImporte;
        this.prix_achat = prix_achat;
        this.prix_vente = prix_vente;
        this.douane = douane;
        this.remise_four = remise_four;
        this.qte = qte;
        this.stock_negative = stock_negative;
        this.marge = marge;
        this.VIP = VIP;
        this.VIP_pourcentage = VIP_pourcentage;
        this.code_barres = code_barres;
        this.pays_origine = pays_origine;
        this.depot = depot;
    }

    public String getCode_1() {
        return code_1;
    }

    public void setCode_1(String code_1) {
        this.code_1 = code_1;
    }

    public String getCode_2() {
        return code_2;
    }

    public void setCode_2(String code_2) {
        this.code_2 = code_2;
    }

    public String getCode_3() {
        return code_3;
    }

    public void setCode_3(String code_3) {
        this.code_3 = code_3;
    }

    public Double getRemise() {
        return remise;
    }

    public void setRemise(Double remise) {
        this.remise = remise;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public int getId() {
        return id;
    }

    public String getId_depot() {
        return id_depot;
    }

    public void setId_depot(String id_depot) {
        this.id_depot = id_depot;
    }

    public int getDetail_qte() {
        return detail_qte;
    }

    public void setDetail_qte(int detail_qte) {
        this.detail_qte = detail_qte;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getId_famille() {
        return id_famille;
    }

    public void setId_famille(String id_famille) {
        this.id_famille = id_famille;
    }

    public String getId_sous_famille() {
        return id_sous_famille;
    }

    public void setId_sous_famille(String id_sous_famille) {
        this.id_sous_famille = id_sous_famille;
    }

    public String getId_fournisseur() {
        return id_fournisseur;
    }

    public void setId_fournisseur(String id_fournisseur) {
        this.id_fournisseur = id_fournisseur;
    }

    public String getTVA() {
        return TVA;
    }

    public void setTVA(String TVA) {
        this.TVA = TVA;
    }

    public String getIsImporte() {
        return isImporte;
    }

    public void setIsImporte(String isImporte) {
        this.isImporte = isImporte;
    }

    public double getPrix_achat() {
        return prix_achat;
    }

    public void setPrix_achat(double prix_achat) {
        this.prix_achat = prix_achat;
    }

    public double getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(double prix_vente) {
        this.prix_vente = prix_vente;
    }

    public String getDouane() {
        return douane;
    }

    public void setDouane(String douane) {
        this.douane = douane;
    }

    public String getRemise_four() {
        return remise_four;
    }

    public void setRemise_four(String remise_four) {
        this.remise_four = remise_four;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getStock_negative() {
        return stock_negative;
    }

    public void setStock_negative(int stock_negative) {
        this.stock_negative = stock_negative;
    }

    public double getMarge() {
        return marge;
    }

    public void setMarge(double marge) {
        this.marge = marge;
    }

    public String getVIP() {
        return VIP;
    }

    public void setVIP(String VIP) {
        this.VIP = VIP;
    }

    public double getVIP_pourcentage() {
        return VIP_pourcentage;
    }

    public void setVIP_pourcentage(double VIP_pourcentage) {
        this.VIP_pourcentage = VIP_pourcentage;
    }

    public String getCode_barres() {
        return code_barres;
    }

    public void setCode_barres(String code_barres) {
        this.code_barres = code_barres;
    }

    public String getPays_origine() {
        return pays_origine;
    }

    public void setPays_origine(String pays_origine) {
        this.pays_origine = pays_origine;
    }

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

}
