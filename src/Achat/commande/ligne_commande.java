/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Achat.commande;

import Achat.pre_commande.*;
import Devis.*;

/**
 *
 * @author Mlek
 */
public class ligne_commande {

    int id;
    String id_pre_commande;
    String date_pre_command;
    int id_client;
    String num_commande;

    public String getNum_commande() {
        return num_commande;
    }

    public void setNum_commande(String num_commande) {
        this.num_commande = num_commande;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_pre_commande() {
        return id_pre_commande;
    }

    public void setId_pre_commande(String id_pre_commande) {
        this.id_pre_commande = id_pre_commande;
    }

    public String getDate_pre_command() {
        return date_pre_command;
    }

    public void setDate_pre_command(String date_pre_command) {
        this.date_pre_command = date_pre_command;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

}
