/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import Achat.commande.AnnulationCommandeAchatForm;
import Achat.commande.CommandeForm;
import Achat.commande.FormRecherchBLFournisseur;
import Achat.commande.FormRecherchCommande;
import Achat.commande.RecherchPreCommandeForm;
import Achat.pre_commande.AnnulationPreCommandeAchatForm;
import Achat.pre_commande.ModifPreCommandeAchatForm;
import Achat.pre_commande.pre_commandeForm;
import Article.ArticleForm;
import avtivite.ClassificationArticleParActiviteForm;
import Article.ListArticleForm;
import Avoir.AnnulationAvoirForm;
import Avoir.FormAvoir;
import Avoir.FormAvoirAchatFournisseur;
import Avoir.FormAvoirNonIdent;
import Avoir.FormAvoirPassager;
import BL.AnnulationBLAchatForm;
import BL.AnnulationBLForm;
import BL.FormBL;
import BL.FormBLDepot;
import BL.FormBLFournisseur;
import BL.ModifBLAchatForm;
import BL.ModifBLForm;
import Config.Bank.FormBank;
import Client.ClientForm;
import Client.ListClientForm;
import Commercial.FormCommercial;
import Commercial.FormTypeCommercial;
import Config.Bank.DepotConsultationForm;
import Config.Bank.FormConfigParams;
import Config.Bank.FormDepot;
import Config.Bank.FormEtatPayement;
import Config.Bank.FormFamilleProduit;
import Config.Bank.FormFournisseur;
import Config.Bank.FormMarqueProduit;
import Config.Bank.FormSousFamilleProduit;
import avtivite.FormTypeArticle;
import Config.Bank.FormTypeReglement;
import Config.Bank.FormZoneGeo;
import Config.Bank.Formtype_clientProduit;
import Config.Commen_Proc;
import Config.ConfigDao;
import Devis.AnnulationDevisForm;
import Devis.FormDevis;
import Devis.FormDevisPassager;
import Devis.FormReliquat;
import Devis.ModifDevisForm;
import Facture.FactureForm;
import Facture.FactureFormFournisseur;
import Facture.FormFacturePassager;
import Recherche.RecherchBLForm;
import Recherche.RecherchDevisForm;
import Recherche.RecherchFactureForm;
import Reglement.Avoir.ReglementAvoirForm;
import Reglement.ReglementForm;
import Reservation.FormReservation;
import Reservation.RecherchResaForm;
import Donnee.MAJMargeForm;
import Donnee.MAJMargeParFamilleForm;
import Donnee.MAJRmiseParArticleForm;
import Donnee.MAJRmiseParFamilleForm;
import Donnee.MAJStockForm;
import avtivite.PreferedArticleForm;
import Facture.AnnulationFactureAchatForm;
import Facture.AnnulationFactureForm;
import Facture.ModifFactureForm;
import Recherche.RecherchAvoirAchatForm;
import Recherche.RecherchAvoirVenteForm;
import Recherche.RecherchBLDepotForm;
import Recherche.RecherchFactureAchatForm;
import Recherche.RecherchReliquatDetailForm;
import Recherche.RecherchReliquatForm;
import Recherche.RecherchTransactionDuJourForm;
import Reglement.RechFactureNonPayeParRegion;
import stat.GrandLivreForm;
import Reglement.RecherchReglementForm;
import Reglement.RecherchReglement_ComptableForm;
import Reglement.SuiviFactureReglementForm;
import alerts.AffectationAlertArticle;
import alerts.AlertArticleForm;
import alerts.AlertReglementForm;
import alerts.ArticleNonVenduForm;
import alerts.RecherchFactureDechargeForm;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import prospection.ListeGroupeArticleForm;
import prospection.ProspectionGroupeArticleForm;
import prospection.RechercheProspectionFrom;
import prospection.RechercheSaisieProspectionFrom;
import prospection.SaisieProspectionCommercial;
import stat.EditionBLForm;
import stat.*;
import stat.StatVenteForm;
import stat.StatVenteParArticleForm;
import stat.StatGlobalForm;

/**
 *
 * @author Mlek
 */
public class App extends javax.swing.JFrame {

    public static JDesktopPane d;

    /**
     * Creates new FormDevis App
     */
    public App() {
        initComponents();

        setResizable(true);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        d = desktopPane;
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment quitter ?", "Quitter", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        try {

            ConfigDao d = new ConfigDao();
            d.getParametersTvaTimbreYear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        if (Commen_Proc.id_role.equals("1")) {

        } else if (Commen_Proc.id_role.equals("2")) {
            //.setVisible(false);
            BanqueMenu.setVisible(false);
            //VentesMenu.setVisible(false);
            VentesPassagerMenu.setVisible(false);
            ReglementMenu.setVisible(false);
            Achat.setVisible(false);
            DonneeMenu.setVisible(false);
            stat.setVisible(false);
            AlertMenu.setVisible(false);
            FactureMenu.setVisible(false);
            AvoirMenu.setVisible(false);
            ReliquatMenuItem.setVisible(false);
            annuldevisMenuItem.setVisible(false);
            modifdevisMenuItem1.setVisible(false);
            annulBLMenuItem.setVisible(false);
            ModifBLMenuItem2.setVisible(false);
            Facture.setVisible(false);
            consultAvoirVente.setVisible(false);
            RechercheMenu1.setVisible(false);
            DevisRecherche1.setVisible(false);
            GestionUserMenu.setVisible(false);
            RechercheMenu.setVisible(false);
        }
        statAchatFourParCommercial.setVisible(false);
        statglobal.setVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the
     * FormDevis. WARNING: Do NOT modify this code. The content of this method
     * is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        BanqueMenu = new javax.swing.JMenu();
        articleMenu = new javax.swing.JMenu();
        Article = new javax.swing.JMenuItem();
        Famille = new javax.swing.JMenuItem();
        SousFamille = new javax.swing.JMenuItem();
        marque = new javax.swing.JMenuItem();
        ListArticle = new javax.swing.JMenuItem();
        ClientMenu1 = new javax.swing.JMenu();
        clientMenuItem1 = new javax.swing.JMenuItem();
        type_client = new javax.swing.JMenuItem();
        Etat = new javax.swing.JMenuItem();
        zonegeo = new javax.swing.JMenuItem();
        clientListe = new javax.swing.JMenuItem();
        CommercialMenu = new javax.swing.JMenu();
        Commercial = new javax.swing.JMenuItem();
        typeCommercial = new javax.swing.JMenuItem();
        Fournisseur = new javax.swing.JMenuItem();
        Banque = new javax.swing.JMenuItem();
        type_reglement = new javax.swing.JMenuItem();
        ConfigParams = new javax.swing.JMenuItem();
        Activite = new javax.swing.JMenu();
        TypeArticle = new javax.swing.JMenuItem();
        ClassificationArticleParActivite = new javax.swing.JMenuItem();
        PreferedArticleMenu = new javax.swing.JMenuItem();
        VentesMenu = new javax.swing.JMenu();
        DevisMenu = new javax.swing.JMenu();
        devisMenuItem = new javax.swing.JMenuItem();
        annuldevisMenuItem = new javax.swing.JMenuItem();
        modifdevisMenuItem1 = new javax.swing.JMenuItem();
        BLMenu = new javax.swing.JMenu();
        BLMenuItem = new javax.swing.JMenuItem();
        annulBLMenuItem = new javax.swing.JMenuItem();
        ModifBLMenuItem2 = new javax.swing.JMenuItem();
        FactureMenu = new javax.swing.JMenu();
        FactureMenuItem = new javax.swing.JMenuItem();
        annulFactureMenuItem1 = new javax.swing.JMenuItem();
        ModifFactureMenuItem2 = new javax.swing.JMenuItem();
        AvoirMenu = new javax.swing.JMenu();
        AvoirMenuItem = new javax.swing.JMenuItem();
        avoir_non_idMenuItem = new javax.swing.JMenuItem();
        AvoirFinMenuItem1 = new javax.swing.JMenuItem();
        annulavoirMenuItem = new javax.swing.JMenuItem();
        ReliquatMenuItem = new javax.swing.JMenuItem();
        RechercheMenu = new javax.swing.JMenu();
        DevisRecherche = new javax.swing.JMenuItem();
        BL = new javax.swing.JMenuItem();
        Facture = new javax.swing.JMenuItem();
        consultAvoirVente = new javax.swing.JMenuItem();
        RechercheMenu1 = new javax.swing.JMenu();
        ReliquatRecherche = new javax.swing.JMenuItem();
        ReliquatRechercheDetail = new javax.swing.JMenuItem();
        DevisRecherche1 = new javax.swing.JMenuItem();
        VentesPassagerMenu = new javax.swing.JMenu();
        devisPassagerMenuItem = new javax.swing.JMenuItem();
        FacturePassagerMenuItem = new javax.swing.JMenuItem();
        AvoirPassagerMenuItem = new javax.swing.JMenuItem();
        ReglementMenu = new javax.swing.JMenu();
        Reglement = new javax.swing.JMenuItem();
        ConsultReglement = new javax.swing.JMenuItem();
        SuiviReglement = new javax.swing.JMenuItem();
        ConsultReglementComptable = new javax.swing.JMenuItem();
        SuiviReglement1 = new javax.swing.JMenuItem();
        Achat = new javax.swing.JMenu();
        PreCommandFournisseur = new javax.swing.JMenu();
        Pre_CommandeMenuItem = new javax.swing.JMenuItem();
        AnnulerBLMenuItem2 = new javax.swing.JMenuItem();
        ModifBLMenuItem4 = new javax.swing.JMenuItem();
        CommandFournisseur1 = new javax.swing.JMenu();
        CommandeMenuItem = new javax.swing.JMenuItem();
        AnnulerBLMenuItem1 = new javax.swing.JMenuItem();
        BLFournisseur = new javax.swing.JMenu();
        BLFournisseurMenuItem = new javax.swing.JMenuItem();
        AnnulerBLMenuItem = new javax.swing.JMenuItem();
        ModifBLachatMenuItem4 = new javax.swing.JMenuItem();
        FactureFournisseur1 = new javax.swing.JMenu();
        FactureFournisseurMenuItem = new javax.swing.JMenuItem();
        AnnulerBLMenuItem3 = new javax.swing.JMenuItem();
        AvoirFournisseurMenuItem = new javax.swing.JMenuItem();
        ConsultationMenuItem = new javax.swing.JMenu();
        ConsPre_CommandeMenuItem = new javax.swing.JMenuItem();
        ConsCommandeMenuItem1 = new javax.swing.JMenuItem();
        ConsBLMenuItem = new javax.swing.JMenuItem();
        ConsFactureMenuItem = new javax.swing.JMenuItem();
        ConsAvoirMenuItem = new javax.swing.JMenuItem();
        DonneeMenu = new javax.swing.JMenu();
        MAJ_Stock = new javax.swing.JMenuItem();
        DonneeMenu1 = new javax.swing.JMenu();
        MAJ_Marge_famille = new javax.swing.JMenuItem();
        MAJ_Marge = new javax.swing.JMenuItem();
        Resa = new javax.swing.JMenuItem();
        RemiseMenu = new javax.swing.JMenu();
        MAJ_remiseFamille = new javax.swing.JMenuItem();
        MAJ_remiseArticle = new javax.swing.JMenuItem();
        GestDepot = new javax.swing.JMenu();
        crudDepot = new javax.swing.JMenuItem();
        Consultation = new javax.swing.JMenuItem();
        blDepot = new javax.swing.JMenuItem();
        ConsultblDepot = new javax.swing.JMenuItem();
        ListArticle1 = new javax.swing.JMenuItem();
        stat = new javax.swing.JMenu();
        statglobal = new javax.swing.JMenuItem();
        StatVenteMenu = new javax.swing.JMenu();
        statAchatFour = new javax.swing.JMenuItem();
        statAchatFourParArticle = new javax.swing.JMenuItem();
        statAchatFourParArticleParClient = new javax.swing.JMenuItem();
        statAchatFourParArticleParPeriod = new javax.swing.JMenuItem();
        EtatBLEdition = new javax.swing.JMenuItem();
        CAMenu = new javax.swing.JMenu();
        CAbyClient = new javax.swing.JMenuItem();
        CAbyClientExonoreTVA = new javax.swing.JMenuItem();
        EvolutionCAbyClient = new javax.swing.JMenuItem();
        EvolutionQteByClient = new javax.swing.JMenuItem();
        statAchatFourParCommercial = new javax.swing.JMenuItem();
        CAbyCommercial = new javax.swing.JMenuItem();
        GrandLivre = new javax.swing.JMenuItem();
        AlertMenu = new javax.swing.JMenu();
        ArticleNonVenduMenuItem = new javax.swing.JMenuItem();
        AlertReglementMenuItem = new javax.swing.JMenuItem();
        AlertDechargeMenuItem = new javax.swing.JMenuItem();
        AlertMenu1 = new javax.swing.JMenu();
        AlertArticleMenuItem = new javax.swing.JMenuItem();
        Affectation_article_clent = new javax.swing.JMenuItem();
        GestionUserMenu = new javax.swing.JMenu();
        ArticleNonVenduMenuItem1 = new javax.swing.JMenuItem();
        Prospection = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        ArticleNonVenduMenuItem2 = new javax.swing.JMenuItem();
        consultationGroupeArticle = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        SaisieProspection = new javax.swing.JMenuItem();
        consultationSaisieProspection = new javax.swing.JMenuItem();
        RechercheProspction = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SODIS");
        setSize(new java.awt.Dimension(0, 0));

        BanqueMenu.setMnemonic('h');
        BanqueMenu.setText("Configuration");

        articleMenu.setMnemonic('e');
        articleMenu.setText("Article");

        Article.setMnemonic('t');
        Article.setText("Article");
        Article.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArticleActionPerformed(evt);
            }
        });
        articleMenu.add(Article);

        Famille.setMnemonic('c');
        Famille.setText("Famille");
        Famille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FamilleActionPerformed(evt);
            }
        });
        articleMenu.add(Famille);

        SousFamille.setMnemonic('c');
        SousFamille.setText("Sous Famille");
        SousFamille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SousFamilleActionPerformed(evt);
            }
        });
        articleMenu.add(SousFamille);

        marque.setMnemonic('c');
        marque.setText("Marque");
        marque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marqueActionPerformed(evt);
            }
        });
        articleMenu.add(marque);

        ListArticle.setMnemonic('t');
        ListArticle.setText("Consultation Article");
        ListArticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListArticleActionPerformed(evt);
            }
        });
        articleMenu.add(ListArticle);

        BanqueMenu.add(articleMenu);

        ClientMenu1.setMnemonic('f');
        ClientMenu1.setText("Client");
        ClientMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientMenu1ActionPerformed(evt);
            }
        });

        clientMenuItem1.setMnemonic('t');
        clientMenuItem1.setText("Client");
        clientMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientMenuItem1ActionPerformed(evt);
            }
        });
        ClientMenu1.add(clientMenuItem1);

        type_client.setMnemonic('c');
        type_client.setText("Type Client");
        type_client.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                type_clientActionPerformed(evt);
            }
        });
        ClientMenu1.add(type_client);

        Etat.setMnemonic('c');
        Etat.setText("Etat Paiement");
        Etat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EtatActionPerformed(evt);
            }
        });
        ClientMenu1.add(Etat);

        zonegeo.setMnemonic('c');
        zonegeo.setText("Zone Géographique");
        zonegeo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zonegeoActionPerformed(evt);
            }
        });
        ClientMenu1.add(zonegeo);

        clientListe.setMnemonic('t');
        clientListe.setText("Consultation Client");
        clientListe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientListeActionPerformed(evt);
            }
        });
        ClientMenu1.add(clientListe);

        BanqueMenu.add(ClientMenu1);

        CommercialMenu.setMnemonic('h');
        CommercialMenu.setText("Commercial");

        Commercial.setMnemonic('c');
        Commercial.setText("Commercial");
        Commercial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CommercialActionPerformed(evt);
            }
        });
        CommercialMenu.add(Commercial);

        typeCommercial.setText("Type Commercial");
        typeCommercial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeCommercialActionPerformed(evt);
            }
        });
        CommercialMenu.add(typeCommercial);

        BanqueMenu.add(CommercialMenu);

        Fournisseur.setMnemonic('c');
        Fournisseur.setText("Fournisseur");
        Fournisseur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FournisseurActionPerformed(evt);
            }
        });
        BanqueMenu.add(Fournisseur);

        Banque.setMnemonic('c');
        Banque.setText("Banque");
        Banque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BanqueActionPerformed(evt);
            }
        });
        BanqueMenu.add(Banque);

        type_reglement.setMnemonic('c');
        type_reglement.setText("Type Réglement");
        type_reglement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                type_reglementActionPerformed(evt);
            }
        });
        BanqueMenu.add(type_reglement);

        ConfigParams.setMnemonic('c');
        ConfigParams.setText("TVA / Timbre / Année");
        ConfigParams.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfigParamsActionPerformed(evt);
            }
        });
        BanqueMenu.add(ConfigParams);

        Activite.setMnemonic('h');
        Activite.setText("Gestion d'activité");

        TypeArticle.setMnemonic('c');
        TypeArticle.setText("Création Activité");
        TypeArticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TypeArticleActionPerformed(evt);
            }
        });
        Activite.add(TypeArticle);

        ClassificationArticleParActivite.setMnemonic('c');
        ClassificationArticleParActivite.setText("Affectation Artilces -> Activité");
        ClassificationArticleParActivite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClassificationArticleParActiviteActionPerformed(evt);
            }
        });
        Activite.add(ClassificationArticleParActivite);

        PreferedArticleMenu.setMnemonic('c');
        PreferedArticleMenu.setText("Affectation Activité / Clients");
        PreferedArticleMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreferedArticleMenuActionPerformed(evt);
            }
        });
        Activite.add(PreferedArticleMenu);

        BanqueMenu.add(Activite);

        menuBar.add(BanqueMenu);

        VentesMenu.setMnemonic('h');
        VentesMenu.setText("Ventes Client");

        DevisMenu.setMnemonic('h');
        DevisMenu.setText("Devis");

        devisMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        devisMenuItem.setMnemonic('c');
        devisMenuItem.setText("Création Devis");
        devisMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                devisMenuItemActionPerformed(evt);
            }
        });
        DevisMenu.add(devisMenuItem);

        annuldevisMenuItem.setMnemonic('c');
        annuldevisMenuItem.setText("Annulation Devis");
        annuldevisMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annuldevisMenuItemActionPerformed(evt);
            }
        });
        DevisMenu.add(annuldevisMenuItem);

        modifdevisMenuItem1.setMnemonic('c');
        modifdevisMenuItem1.setText("Modification Devis");
        modifdevisMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifdevisMenuItem1ActionPerformed(evt);
            }
        });
        DevisMenu.add(modifdevisMenuItem1);

        VentesMenu.add(DevisMenu);

        BLMenu.setMnemonic('h');
        BLMenu.setText("BL");

        BLMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        BLMenuItem.setMnemonic('a');
        BLMenuItem.setText("Création BL");
        BLMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BLMenuItemActionPerformed(evt);
            }
        });
        BLMenu.add(BLMenuItem);

        annulBLMenuItem.setMnemonic('c');
        annulBLMenuItem.setText("Annulation BL");
        annulBLMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annulBLMenuItemActionPerformed(evt);
            }
        });
        BLMenu.add(annulBLMenuItem);

        ModifBLMenuItem2.setMnemonic('c');
        ModifBLMenuItem2.setText("Modification BL");
        ModifBLMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifBLMenuItem2ActionPerformed(evt);
            }
        });
        BLMenu.add(ModifBLMenuItem2);

        VentesMenu.add(BLMenu);

        FactureMenu.setMnemonic('h');
        FactureMenu.setText("Facture");

        FactureMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        FactureMenuItem.setMnemonic('a');
        FactureMenuItem.setText("Création Facture");
        FactureMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FactureMenuItemActionPerformed(evt);
            }
        });
        FactureMenu.add(FactureMenuItem);

        annulFactureMenuItem1.setMnemonic('c');
        annulFactureMenuItem1.setText("Annulation Facture");
        annulFactureMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annulFactureMenuItem1ActionPerformed(evt);
            }
        });
        FactureMenu.add(annulFactureMenuItem1);

        ModifFactureMenuItem2.setMnemonic('c');
        ModifFactureMenuItem2.setText("Modification Facture");
        ModifFactureMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifFactureMenuItem2ActionPerformed(evt);
            }
        });
        FactureMenu.add(ModifFactureMenuItem2);

        VentesMenu.add(FactureMenu);

        AvoirMenu.setMnemonic('h');
        AvoirMenu.setText("Avoir");

        AvoirMenuItem.setMnemonic('c');
        AvoirMenuItem.setText("Identifié (Facture)");
        AvoirMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AvoirMenuItemActionPerformed(evt);
            }
        });
        AvoirMenu.add(AvoirMenuItem);

        avoir_non_idMenuItem.setMnemonic('a');
        avoir_non_idMenuItem.setText("Non Identifé");
        avoir_non_idMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avoir_non_idMenuItemActionPerformed(evt);
            }
        });
        AvoirMenu.add(avoir_non_idMenuItem);

        AvoirFinMenuItem1.setMnemonic('a');
        AvoirFinMenuItem1.setText("Avoir Financier");
        AvoirFinMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AvoirFinMenuItem1ActionPerformed(evt);
            }
        });
        AvoirMenu.add(AvoirFinMenuItem1);

        annulavoirMenuItem.setMnemonic('c');
        annulavoirMenuItem.setText("Annulation Avoir");
        annulavoirMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annulavoirMenuItemActionPerformed(evt);
            }
        });
        AvoirMenu.add(annulavoirMenuItem);

        VentesMenu.add(AvoirMenu);

        ReliquatMenuItem.setMnemonic('c');
        ReliquatMenuItem.setText("Reliquat");
        ReliquatMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReliquatMenuItemActionPerformed(evt);
            }
        });
        VentesMenu.add(ReliquatMenuItem);

        RechercheMenu.setMnemonic('h');
        RechercheMenu.setText("Consultation");

        DevisRecherche.setMnemonic('c');
        DevisRecherche.setText("Devis");
        DevisRecherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DevisRechercheActionPerformed(evt);
            }
        });
        RechercheMenu.add(DevisRecherche);

        BL.setMnemonic('c');
        BL.setText("BL");
        BL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BLActionPerformed(evt);
            }
        });
        RechercheMenu.add(BL);

        Facture.setMnemonic('c');
        Facture.setText("Facture");
        Facture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FactureActionPerformed(evt);
            }
        });
        RechercheMenu.add(Facture);

        consultAvoirVente.setMnemonic('c');
        consultAvoirVente.setText("Avoir");
        consultAvoirVente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultAvoirVenteActionPerformed(evt);
            }
        });
        RechercheMenu.add(consultAvoirVente);

        RechercheMenu1.setMnemonic('h');
        RechercheMenu1.setText("Reliquat");

        ReliquatRecherche.setMnemonic('c');
        ReliquatRecherche.setText("Reliquat");
        ReliquatRecherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReliquatRechercheActionPerformed(evt);
            }
        });
        RechercheMenu1.add(ReliquatRecherche);

        ReliquatRechercheDetail.setMnemonic('c');
        ReliquatRechercheDetail.setText("Détail Reliquat");
        ReliquatRechercheDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReliquatRechercheDetailActionPerformed(evt);
            }
        });
        RechercheMenu1.add(ReliquatRechercheDetail);

        RechercheMenu.add(RechercheMenu1);

        DevisRecherche1.setMnemonic('c');
        DevisRecherche1.setText("Etat Du Jour");
        DevisRecherche1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DevisRecherche1ActionPerformed(evt);
            }
        });
        RechercheMenu.add(DevisRecherche1);

        VentesMenu.add(RechercheMenu);

        menuBar.add(VentesMenu);

        VentesPassagerMenu.setMnemonic('h');
        VentesPassagerMenu.setText("Ventes Passager");

        devisPassagerMenuItem.setMnemonic('c');
        devisPassagerMenuItem.setText("Devis");
        devisPassagerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                devisPassagerMenuItemActionPerformed(evt);
            }
        });
        VentesPassagerMenu.add(devisPassagerMenuItem);

        FacturePassagerMenuItem.setMnemonic('a');
        FacturePassagerMenuItem.setText("Facture");
        FacturePassagerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FacturePassagerMenuItemActionPerformed(evt);
            }
        });
        VentesPassagerMenu.add(FacturePassagerMenuItem);

        AvoirPassagerMenuItem.setMnemonic('c');
        AvoirPassagerMenuItem.setText("Avoir");
        AvoirPassagerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AvoirPassagerMenuItemActionPerformed(evt);
            }
        });
        VentesPassagerMenu.add(AvoirPassagerMenuItem);

        menuBar.add(VentesPassagerMenu);

        ReglementMenu.setMnemonic('h');
        ReglementMenu.setText("Reglement");

        Reglement.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        Reglement.setMnemonic('c');
        Reglement.setText("Reglement");
        Reglement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReglementActionPerformed(evt);
            }
        });
        ReglementMenu.add(Reglement);

        ConsultReglement.setMnemonic('c');
        ConsultReglement.setText("Consultation Réglement");
        ConsultReglement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultReglementActionPerformed(evt);
            }
        });
        ReglementMenu.add(ConsultReglement);

        SuiviReglement.setMnemonic('c');
        SuiviReglement.setText("Factures Impayées");
        SuiviReglement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuiviReglementActionPerformed(evt);
            }
        });
        ReglementMenu.add(SuiviReglement);

        ConsultReglementComptable.setMnemonic('c');
        ConsultReglementComptable.setText("Consultation Réglement Comptable");
        ConsultReglementComptable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultReglementComptableActionPerformed(evt);
            }
        });
        ReglementMenu.add(ConsultReglementComptable);

        SuiviReglement1.setMnemonic('c');
        SuiviReglement1.setText("Factures Impayées Par Region");
        SuiviReglement1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuiviReglement1ActionPerformed(evt);
            }
        });
        ReglementMenu.add(SuiviReglement1);

        menuBar.add(ReglementMenu);

        Achat.setMnemonic('h');
        Achat.setText("Achat Fournisseur");

        PreCommandFournisseur.setMnemonic('h');
        PreCommandFournisseur.setText("Pré-Commande");

        Pre_CommandeMenuItem.setMnemonic('c');
        Pre_CommandeMenuItem.setText("Pré Commande");
        Pre_CommandeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Pre_CommandeMenuItemActionPerformed(evt);
            }
        });
        PreCommandFournisseur.add(Pre_CommandeMenuItem);

        AnnulerBLMenuItem2.setMnemonic('a');
        AnnulerBLMenuItem2.setText("Annulation Pré-Commande");
        AnnulerBLMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnulerBLMenuItem2ActionPerformed(evt);
            }
        });
        PreCommandFournisseur.add(AnnulerBLMenuItem2);

        ModifBLMenuItem4.setMnemonic('a');
        ModifBLMenuItem4.setText("Modification Pré-Commande");
        ModifBLMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifBLMenuItem4ActionPerformed(evt);
            }
        });
        PreCommandFournisseur.add(ModifBLMenuItem4);

        Achat.add(PreCommandFournisseur);

        CommandFournisseur1.setMnemonic('h');
        CommandFournisseur1.setText("Commande");

        CommandeMenuItem.setMnemonic('a');
        CommandeMenuItem.setText("Commande");
        CommandeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CommandeMenuItemActionPerformed(evt);
            }
        });
        CommandFournisseur1.add(CommandeMenuItem);

        AnnulerBLMenuItem1.setMnemonic('a');
        AnnulerBLMenuItem1.setText("Annulation Commande");
        AnnulerBLMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnulerBLMenuItem1ActionPerformed(evt);
            }
        });
        CommandFournisseur1.add(AnnulerBLMenuItem1);

        Achat.add(CommandFournisseur1);

        BLFournisseur.setMnemonic('h');
        BLFournisseur.setText("BL");

        BLFournisseurMenuItem.setMnemonic('a');
        BLFournisseurMenuItem.setText("Création BL");
        BLFournisseurMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BLFournisseurMenuItemActionPerformed(evt);
            }
        });
        BLFournisseur.add(BLFournisseurMenuItem);

        AnnulerBLMenuItem.setMnemonic('a');
        AnnulerBLMenuItem.setText("Annulation BL");
        AnnulerBLMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnulerBLMenuItemActionPerformed(evt);
            }
        });
        BLFournisseur.add(AnnulerBLMenuItem);

        ModifBLachatMenuItem4.setMnemonic('a');
        ModifBLachatMenuItem4.setText("Modifcation BL");
        ModifBLachatMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifBLachatMenuItem4ActionPerformed(evt);
            }
        });
        BLFournisseur.add(ModifBLachatMenuItem4);

        Achat.add(BLFournisseur);

        FactureFournisseur1.setMnemonic('h');
        FactureFournisseur1.setText("Facture");

        FactureFournisseurMenuItem.setMnemonic('a');
        FactureFournisseurMenuItem.setText("Facture");
        FactureFournisseurMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FactureFournisseurMenuItemActionPerformed(evt);
            }
        });
        FactureFournisseur1.add(FactureFournisseurMenuItem);

        AnnulerBLMenuItem3.setMnemonic('a');
        AnnulerBLMenuItem3.setText("Annulation Facture");
        AnnulerBLMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnulerBLMenuItem3ActionPerformed(evt);
            }
        });
        FactureFournisseur1.add(AnnulerBLMenuItem3);

        Achat.add(FactureFournisseur1);

        AvoirFournisseurMenuItem.setMnemonic('a');
        AvoirFournisseurMenuItem.setText("Avoir");
        AvoirFournisseurMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AvoirFournisseurMenuItemActionPerformed(evt);
            }
        });
        Achat.add(AvoirFournisseurMenuItem);

        ConsultationMenuItem.setMnemonic('h');
        ConsultationMenuItem.setText("Consultation Achat");

        ConsPre_CommandeMenuItem.setMnemonic('c');
        ConsPre_CommandeMenuItem.setText("Pré Commande");
        ConsPre_CommandeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsPre_CommandeMenuItemActionPerformed(evt);
            }
        });
        ConsultationMenuItem.add(ConsPre_CommandeMenuItem);

        ConsCommandeMenuItem1.setMnemonic('a');
        ConsCommandeMenuItem1.setText("Commande");
        ConsCommandeMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsCommandeMenuItem1ActionPerformed(evt);
            }
        });
        ConsultationMenuItem.add(ConsCommandeMenuItem1);

        ConsBLMenuItem.setMnemonic('a');
        ConsBLMenuItem.setText("BL");
        ConsBLMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsBLMenuItemActionPerformed(evt);
            }
        });
        ConsultationMenuItem.add(ConsBLMenuItem);

        ConsFactureMenuItem.setMnemonic('a');
        ConsFactureMenuItem.setText("Facture");
        ConsFactureMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsFactureMenuItemActionPerformed(evt);
            }
        });
        ConsultationMenuItem.add(ConsFactureMenuItem);

        ConsAvoirMenuItem.setMnemonic('a');
        ConsAvoirMenuItem.setText("Avoir");
        ConsAvoirMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsAvoirMenuItemActionPerformed(evt);
            }
        });
        ConsultationMenuItem.add(ConsAvoirMenuItem);

        Achat.add(ConsultationMenuItem);

        menuBar.add(Achat);

        DonneeMenu.setMnemonic('e');
        DonneeMenu.setText("Stock");

        MAJ_Stock.setMnemonic('t');
        MAJ_Stock.setText("MAJ Stock");
        MAJ_Stock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MAJ_StockActionPerformed(evt);
            }
        });
        DonneeMenu.add(MAJ_Stock);

        DonneeMenu1.setMnemonic('e');
        DonneeMenu1.setText("Marge");

        MAJ_Marge_famille.setMnemonic('t');
        MAJ_Marge_famille.setText("MAJ Marge Par Famille");
        MAJ_Marge_famille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MAJ_Marge_familleActionPerformed(evt);
            }
        });
        DonneeMenu1.add(MAJ_Marge_famille);

        MAJ_Marge.setMnemonic('t');
        MAJ_Marge.setText("MAJ Marge Exceptionnel");
        MAJ_Marge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MAJ_MargeActionPerformed(evt);
            }
        });
        DonneeMenu1.add(MAJ_Marge);

        DonneeMenu.add(DonneeMenu1);

        Resa.setMnemonic('t');
        Resa.setText("Réservation");
        Resa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResaActionPerformed(evt);
            }
        });
        DonneeMenu.add(Resa);

        RemiseMenu.setMnemonic('e');
        RemiseMenu.setText("Remise");

        MAJ_remiseFamille.setMnemonic('t');
        MAJ_remiseFamille.setText("Remise Par Famille");
        MAJ_remiseFamille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MAJ_remiseFamilleActionPerformed(evt);
            }
        });
        RemiseMenu.add(MAJ_remiseFamille);

        MAJ_remiseArticle.setMnemonic('t');
        MAJ_remiseArticle.setText("Remise Par Article");
        MAJ_remiseArticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MAJ_remiseArticleActionPerformed(evt);
            }
        });
        RemiseMenu.add(MAJ_remiseArticle);

        DonneeMenu.add(RemiseMenu);

        GestDepot.setMnemonic('e');
        GestDepot.setText("Dépot");

        crudDepot.setMnemonic('t');
        crudDepot.setText("Dépot Création / Modification / Suppression");
        crudDepot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crudDepotActionPerformed(evt);
            }
        });
        GestDepot.add(crudDepot);

        Consultation.setMnemonic('t');
        Consultation.setText("Consultation Dépot");
        Consultation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultationActionPerformed(evt);
            }
        });
        GestDepot.add(Consultation);

        blDepot.setMnemonic('t');
        blDepot.setText("BL Dépot ");
        blDepot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blDepotActionPerformed(evt);
            }
        });
        GestDepot.add(blDepot);

        ConsultblDepot.setMnemonic('t');
        ConsultblDepot.setText("Consultation BL Dépot ");
        ConsultblDepot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultblDepotActionPerformed(evt);
            }
        });
        GestDepot.add(ConsultblDepot);

        DonneeMenu.add(GestDepot);

        ListArticle1.setMnemonic('t');
        ListArticle1.setText("Consultation Article");
        ListArticle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListArticle1ActionPerformed(evt);
            }
        });
        DonneeMenu.add(ListArticle1);

        menuBar.add(DonneeMenu);

        stat.setMnemonic('h');
        stat.setText("Statistiques");

        statglobal.setMnemonic('c');
        statglobal.setText("Statistiques");
        statglobal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statglobalActionPerformed(evt);
            }
        });
        stat.add(statglobal);

        StatVenteMenu.setMnemonic('h');
        StatVenteMenu.setText("Statistique Vente");

        statAchatFour.setMnemonic('c');
        statAchatFour.setText("Statistique de vente");
        statAchatFour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statAchatFourActionPerformed(evt);
            }
        });
        StatVenteMenu.add(statAchatFour);

        statAchatFourParArticle.setMnemonic('c');
        statAchatFourParArticle.setText("Statistique de vente Par Article");
        statAchatFourParArticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statAchatFourParArticleActionPerformed(evt);
            }
        });
        StatVenteMenu.add(statAchatFourParArticle);

        statAchatFourParArticleParClient.setMnemonic('c');
        statAchatFourParArticleParClient.setText("Statistique de vente Par Article Par Client");
        statAchatFourParArticleParClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statAchatFourParArticleParClientActionPerformed(evt);
            }
        });
        StatVenteMenu.add(statAchatFourParArticleParClient);

        statAchatFourParArticleParPeriod.setMnemonic('c');
        statAchatFourParArticleParPeriod.setText("Statistique de vente Par Article Par Période");
        statAchatFourParArticleParPeriod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statAchatFourParArticleParPeriodActionPerformed(evt);
            }
        });
        StatVenteMenu.add(statAchatFourParArticleParPeriod);

        stat.add(StatVenteMenu);

        EtatBLEdition.setMnemonic('c');
        EtatBLEdition.setText("Etat Devis / BL / Facture / Avoir");
        EtatBLEdition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EtatBLEditionActionPerformed(evt);
            }
        });
        stat.add(EtatBLEdition);

        CAMenu.setMnemonic('h');
        CAMenu.setText("Chiffre d'affaire Client");

        CAbyClient.setText("Chiffre d'affaire clients");
        CAbyClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CAbyClientActionPerformed(evt);
            }
        });
        CAMenu.add(CAbyClient);

        CAbyClientExonoreTVA.setText("Chiffre d'affaire clients Exonéré TVA");
        CAbyClientExonoreTVA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CAbyClientExonoreTVAActionPerformed(evt);
            }
        });
        CAMenu.add(CAbyClientExonoreTVA);

        EvolutionCAbyClient.setText("Evolution Chiffre d'affaire clients");
        EvolutionCAbyClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EvolutionCAbyClientActionPerformed(evt);
            }
        });
        CAMenu.add(EvolutionCAbyClient);

        stat.add(CAMenu);

        EvolutionQteByClient.setText("Evolution Quantité Clients ");
        EvolutionQteByClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EvolutionQteByClientActionPerformed(evt);
            }
        });
        stat.add(EvolutionQteByClient);

        statAchatFourParCommercial.setMnemonic('c');
        statAchatFourParCommercial.setText("Statistique de vente Par Commercial");
        statAchatFourParCommercial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statAchatFourParCommercialActionPerformed(evt);
            }
        });
        stat.add(statAchatFourParCommercial);

        CAbyCommercial.setText("Chiffre d'affaire Commercial");
        CAbyCommercial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CAbyCommercialActionPerformed(evt);
            }
        });
        stat.add(CAbyCommercial);

        GrandLivre.setText("Grand Liver");
        GrandLivre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrandLivreActionPerformed(evt);
            }
        });
        stat.add(GrandLivre);

        menuBar.add(stat);

        AlertMenu.setMnemonic('h');
        AlertMenu.setText("Alerts");

        ArticleNonVenduMenuItem.setText("Articles Non Vendus");
        ArticleNonVenduMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArticleNonVenduMenuItemActionPerformed(evt);
            }
        });
        AlertMenu.add(ArticleNonVenduMenuItem);

        AlertReglementMenuItem.setText("Alert Reglement");
        AlertReglementMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlertReglementMenuItemActionPerformed(evt);
            }
        });
        AlertMenu.add(AlertReglementMenuItem);

        AlertDechargeMenuItem.setText("Décharge Facture");
        AlertDechargeMenuItem.setToolTipText("");
        AlertDechargeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlertDechargeMenuItemActionPerformed(evt);
            }
        });
        AlertMenu.add(AlertDechargeMenuItem);

        AlertMenu1.setMnemonic('h');
        AlertMenu1.setText("Alert Article");

        AlertArticleMenuItem.setText("Alert Articles");
        AlertArticleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlertArticleMenuItemActionPerformed(evt);
            }
        });
        AlertMenu1.add(AlertArticleMenuItem);

        Affectation_article_clent.setText("Affectation Article/Client/Période");
        Affectation_article_clent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Affectation_article_clentActionPerformed(evt);
            }
        });
        AlertMenu1.add(Affectation_article_clent);

        AlertMenu.add(AlertMenu1);

        menuBar.add(AlertMenu);

        GestionUserMenu.setMnemonic('h');
        GestionUserMenu.setText("Utilisateurs");

        ArticleNonVenduMenuItem1.setText("Création Utilisateur");
        ArticleNonVenduMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArticleNonVenduMenuItem1ActionPerformed(evt);
            }
        });
        GestionUserMenu.add(ArticleNonVenduMenuItem1);

        menuBar.add(GestionUserMenu);

        Prospection.setMnemonic('h');
        Prospection.setText("Prospection");

        jMenu1.setText("Groupe Prospection");

        ArticleNonVenduMenuItem2.setText("Création groupe Prospection");
        ArticleNonVenduMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArticleNonVenduMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(ArticleNonVenduMenuItem2);

        consultationGroupeArticle.setText("Consultation Groupe Prospection");
        consultationGroupeArticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultationGroupeArticleActionPerformed(evt);
            }
        });
        jMenu1.add(consultationGroupeArticle);

        Prospection.add(jMenu1);

        jMenu2.setText("Saisie Prospection");

        SaisieProspection.setText("Saisie Prospection");
        SaisieProspection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaisieProspectionActionPerformed(evt);
            }
        });
        jMenu2.add(SaisieProspection);

        consultationSaisieProspection.setText("Consultation Saisie Prospection");
        consultationSaisieProspection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultationSaisieProspectionActionPerformed(evt);
            }
        });
        jMenu2.add(consultationSaisieProspection);

        Prospection.add(jMenu2);

        RechercheProspction.setText("Consultation ");
        RechercheProspction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RechercheProspctionActionPerformed(evt);
            }
        });
        Prospection.add(RechercheProspction);

        menuBar.add(Prospection);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("frame1");
        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ClientMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientMenu1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_ClientMenu1ActionPerformed
    void doLogin() {
        JButton jbtValider = new JButton("Valider");

        JPanel Homepanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());

        panel.add(jbtValider, BorderLayout.SOUTH);

        Homepanel.add(panel, BorderLayout.SOUTH);

        // Homepanel.add(, BorderLayout.CENTER);
        JFrame frameListeClient = new JFrame("Login");
        frameListeClient.add(Homepanel);
        frameListeClient.pack();
        frameListeClient.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frameListeClient.setLocationRelativeTo(null);
        frameListeClient.setVisible(true);
        jbtValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

            }
        });
    }
    private void clientMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientMenuItem1ActionPerformed
        // TODO add your handling code here:
        /* desktopPane.removeAll();
        desktopPane.updateUI();*/
        ClientForm c = new ClientForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();


    }//GEN-LAST:event_clientMenuItem1ActionPerformed

    private void ArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArticleActionPerformed
        // TODO add your handling code here:
        /*  desktopPane.removeAll();
        desktopPane.updateUI();*/
        ArticleForm c = new ArticleForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ArticleActionPerformed

    private void devisMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_devisMenuItemActionPerformed
        // TODO add your handling code here:
        /*  desktopPane.removeAll();
        desktopPane.updateUI();*/
        Devis.FormDevis c = new FormDevis("", "");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);

        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_devisMenuItemActionPerformed

    private void BLMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BLMenuItemActionPerformed
        // TODO add your handling code here:
        /* desktopPane.removeAll();
        desktopPane.updateUI();*/
        BL.FormBL c = new FormBL("", "", "");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_BLMenuItemActionPerformed

    private void FactureMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FactureMenuItemActionPerformed
        // TODO add your handling code here:
        /*  desktopPane.removeAll();
        desktopPane.updateUI();*/
        Vector<Vector<Object>> s = null;
        FactureForm c = new FactureForm(s, "", "");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_FactureMenuItemActionPerformed

    private void CommercialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CommercialActionPerformed
        // TODO add your handling code here:
        /* desktopPane.removeAll();
        desktopPane.updateUI();*/
        FormCommercial c = new FormCommercial();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_CommercialActionPerformed

    private void ReglementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReglementActionPerformed
        // TODO add your handling code here:

        ReglementForm c = new ReglementForm();
        desktopPane.add(c);

        /* Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);*/
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ReglementActionPerformed

    private void BanqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BanqueActionPerformed
        // TODO add your handling code here:
        FormBank c = new FormBank();
        desktopPane.add(c);

        /* Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);*/
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_BanqueActionPerformed

    private void DevisRechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DevisRechercheActionPerformed
        // TODO add your handling code here:
        /*   desktopPane.removeAll();
        desktopPane.updateUI();*/
        RecherchDevisForm c = new RecherchDevisForm("");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_DevisRechercheActionPerformed

    private void BLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BLActionPerformed
        // TODO add your handling code here:
        /* desktopPane.removeAll();
        desktopPane.updateUI();*/
        RecherchBLForm c = new RecherchBLForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_BLActionPerformed

    private void FactureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FactureActionPerformed
        // TODO add your handling code here:
        /*    desktopPane.removeAll();
        desktopPane.updateUI();*/
        RecherchFactureForm c = new RecherchFactureForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_FactureActionPerformed

    private void Pre_CommandeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Pre_CommandeMenuItemActionPerformed
        // TODO add your handling code here:
        pre_commandeForm c = new pre_commandeForm("", "");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_Pre_CommandeMenuItemActionPerformed

    private void CommandeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CommandeMenuItemActionPerformed
        // TODO add your handling code here:
        CommandeForm c = new CommandeForm("");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_CommandeMenuItemActionPerformed

    private void ConsPre_CommandeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsPre_CommandeMenuItemActionPerformed
        // TODO add your handling code here:
        RecherchPreCommandeForm c = new RecherchPreCommandeForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ConsPre_CommandeMenuItemActionPerformed

    private void ConsCommandeMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsCommandeMenuItem1ActionPerformed
        // TODO add your handling code here:

        FormRecherchCommande c = new FormRecherchCommande();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ConsCommandeMenuItem1ActionPerformed

    private void AvoirMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AvoirMenuItemActionPerformed
        // TODO add your handling code here:
        FormAvoir c = new FormAvoir("");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_AvoirMenuItemActionPerformed

    private void avoir_non_idMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avoir_non_idMenuItemActionPerformed
        // TODO add your handling code here:
        FormAvoirNonIdent c = new FormAvoirNonIdent("");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();

    }//GEN-LAST:event_avoir_non_idMenuItemActionPerformed

    private void AvoirFinMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AvoirFinMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AvoirFinMenuItem1ActionPerformed

    private void devisPassagerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_devisPassagerMenuItemActionPerformed
        // TODO add your handling code here:
        FormDevisPassager c = new FormDevisPassager("", "");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_devisPassagerMenuItemActionPerformed

    private void FacturePassagerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FacturePassagerMenuItemActionPerformed
        // TODO add your handling code here:
        FormFacturePassager c = new FormFacturePassager("", "", "");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_FacturePassagerMenuItemActionPerformed

    private void AvoirPassagerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AvoirPassagerMenuItemActionPerformed
        // TODO add your handling code here:
        FormAvoirPassager c = new FormAvoirPassager("");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_AvoirPassagerMenuItemActionPerformed

    private void FamilleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FamilleActionPerformed
        // TODO add your handling code here:
        FormFamilleProduit c = new FormFamilleProduit();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_FamilleActionPerformed

    private void marqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_marqueActionPerformed
        // TODO add your handling code here:
        FormMarqueProduit c = new FormMarqueProduit();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_marqueActionPerformed

    private void SousFamilleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SousFamilleActionPerformed
        // TODO add your handling code here:
        FormSousFamilleProduit c = new FormSousFamilleProduit();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_SousFamilleActionPerformed

    private void BLFournisseurMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BLFournisseurMenuItemActionPerformed
        // TODO add your handling code here:
        FormBLFournisseur c = new FormBLFournisseur("", "", "");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_BLFournisseurMenuItemActionPerformed

    private void FactureFournisseurMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FactureFournisseurMenuItemActionPerformed
        // TODO add your handling code here:
        FactureFormFournisseur c = new FactureFormFournisseur(null, "");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_FactureFournisseurMenuItemActionPerformed

    private void AvoirFournisseurMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AvoirFournisseurMenuItemActionPerformed
        // TODO add your handling code here:
        FormAvoirAchatFournisseur c = new FormAvoirAchatFournisseur("");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_AvoirFournisseurMenuItemActionPerformed

    private void MAJ_StockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MAJ_StockActionPerformed
        // TODO add your handling code here:
        MAJStockForm c = new MAJStockForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_MAJ_StockActionPerformed

    private void MAJ_Marge_familleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MAJ_Marge_familleActionPerformed
        // TODO add your handling code here:
        MAJMargeParFamilleForm c = new MAJMargeParFamilleForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_MAJ_Marge_familleActionPerformed

    private void MAJ_MargeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MAJ_MargeActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:3
        MAJMargeForm c = new MAJMargeForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_MAJ_MargeActionPerformed

    private void ResaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResaActionPerformed
        // TODO add your handling code here:
        RecherchResaForm c = new RecherchResaForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ResaActionPerformed

    private void MAJ_remiseFamilleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MAJ_remiseFamilleActionPerformed
        // TODO add your handling code here:
        MAJRmiseParFamilleForm c = new MAJRmiseParFamilleForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_MAJ_remiseFamilleActionPerformed

    private void MAJ_remiseArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MAJ_remiseArticleActionPerformed
        // TODO add your handling code here:
        MAJRmiseParArticleForm c = new MAJRmiseParArticleForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_MAJ_remiseArticleActionPerformed

    private void crudDepotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crudDepotActionPerformed
        FormDepot c = new FormDepot();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_crudDepotActionPerformed

    private void ConsultationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultationActionPerformed
        DepotConsultationForm c = new DepotConsultationForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ConsultationActionPerformed

    private void blDepotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blDepotActionPerformed
        // TODO add your handling code here:
        FormBLDepot c = new FormBLDepot("", "");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_blDepotActionPerformed

    private void ConsultblDepotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultblDepotActionPerformed
        // TODO add your handling code here:
        RecherchBLDepotForm c = new RecherchBLDepotForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ConsultblDepotActionPerformed

    private void statglobalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statglobalActionPerformed
        // TODO add your handling code here:
        StatGlobalForm c = new StatGlobalForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_statglobalActionPerformed

    private void consultAvoirVenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultAvoirVenteActionPerformed
        // TODO add your handling code here:
        RecherchAvoirVenteForm c = new RecherchAvoirVenteForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_consultAvoirVenteActionPerformed

    private void ConsBLMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsBLMenuItemActionPerformed
        // TODO add your handling code here:
        FormRecherchBLFournisseur c = new FormRecherchBLFournisseur();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ConsBLMenuItemActionPerformed

    private void ConsFactureMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsFactureMenuItemActionPerformed
        // TODO add your handling code here:
        RecherchFactureAchatForm c = new RecherchFactureAchatForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ConsFactureMenuItemActionPerformed

    private void ConsAvoirMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsAvoirMenuItemActionPerformed
        // TODO add your handling code here:
        RecherchAvoirAchatForm c = new RecherchAvoirAchatForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ConsAvoirMenuItemActionPerformed

    private void annuldevisMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annuldevisMenuItemActionPerformed
        // TODO add your handling code here:
        AnnulationDevisForm c = new AnnulationDevisForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_annuldevisMenuItemActionPerformed

    private void annulBLMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annulBLMenuItemActionPerformed
        // TODO add your handling code here:
        AnnulationBLForm c = new AnnulationBLForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_annulBLMenuItemActionPerformed

    private void annulavoirMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annulavoirMenuItemActionPerformed
        AnnulationAvoirForm c = new AnnulationAvoirForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_annulavoirMenuItemActionPerformed

    private void annulFactureMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annulFactureMenuItem1ActionPerformed
        // TODO add your handling code here:
        AnnulationFactureForm c = new AnnulationFactureForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_annulFactureMenuItem1ActionPerformed

    private void type_clientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_type_clientActionPerformed
        // TODO add your handling code here:
        Formtype_clientProduit c = new Formtype_clientProduit();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_type_clientActionPerformed

    private void EtatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EtatActionPerformed
        // TODO add your handling code here:
        FormEtatPayement c = new FormEtatPayement();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_EtatActionPerformed

    private void zonegeoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zonegeoActionPerformed
        // TODO add your handling code here:
        FormZoneGeo c = new FormZoneGeo();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_zonegeoActionPerformed

    private void FournisseurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FournisseurActionPerformed
        // TODO add your handling code here:
        FormFournisseur c = new FormFournisseur();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_FournisseurActionPerformed

    private void ConsultReglementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultReglementActionPerformed
        // TODO add your handling code here:
        RecherchReglementForm c = new RecherchReglementForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ConsultReglementActionPerformed

    private void AnnulerBLMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerBLMenuItemActionPerformed
        // TODO add your handling code here:
        AnnulationBLAchatForm c = new AnnulationBLAchatForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_AnnulerBLMenuItemActionPerformed

    private void AnnulerBLMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerBLMenuItem1ActionPerformed
        AnnulationCommandeAchatForm c = new AnnulationCommandeAchatForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
        // TODO add your handling code here:
    }//GEN-LAST:event_AnnulerBLMenuItem1ActionPerformed

    private void AnnulerBLMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerBLMenuItem2ActionPerformed
        // TODO add your handling code here:
        AnnulationPreCommandeAchatForm c = new AnnulationPreCommandeAchatForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_AnnulerBLMenuItem2ActionPerformed

    private void AnnulerBLMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerBLMenuItem3ActionPerformed
        // TODO add your handling code here:
        AnnulationFactureAchatForm c = new AnnulationFactureAchatForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_AnnulerBLMenuItem3ActionPerformed

    private void modifdevisMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifdevisMenuItem1ActionPerformed
        // TODO add your handling code here:
        ModifDevisForm c = new ModifDevisForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_modifdevisMenuItem1ActionPerformed

    private void ModifBLMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifBLMenuItem2ActionPerformed
        // TODO add your handling code here:
        ModifBLForm c = new ModifBLForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ModifBLMenuItem2ActionPerformed

    private void ModifFactureMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifFactureMenuItem2ActionPerformed
        // TODO add your handling code here:
        ModifFactureForm c = new ModifFactureForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ModifFactureMenuItem2ActionPerformed

    private void ModifBLMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifBLMenuItem4ActionPerformed
        // TODO add your handling code here:
        ModifPreCommandeAchatForm c = new ModifPreCommandeAchatForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ModifBLMenuItem4ActionPerformed

    private void ModifBLachatMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifBLachatMenuItem4ActionPerformed
        // TODO add your handling code here:
        ModifBLAchatForm c = new ModifBLAchatForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ModifBLachatMenuItem4ActionPerformed

    private void type_reglementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_type_reglementActionPerformed
        // TODO add your handling code here:
        FormTypeReglement c = new FormTypeReglement();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_type_reglementActionPerformed

    private void statAchatFourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statAchatFourActionPerformed
        // TODO add your handling code here:
        StatVenteForm c = new StatVenteForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_statAchatFourActionPerformed

    private void SuiviReglementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuiviReglementActionPerformed
        // TODO add your handling code here:
        SuiviFactureReglementForm c = new SuiviFactureReglementForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_SuiviReglementActionPerformed

    private void statAchatFourParArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statAchatFourParArticleActionPerformed
        // TODO add your handling code here:
        StatVenteParArticleForm c = new StatVenteParArticleForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_statAchatFourParArticleActionPerformed

    private void ListArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListArticleActionPerformed
        // TODO add your handling code here:
        ListArticleForm c = new ListArticleForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ListArticleActionPerformed

    private void clientListeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientListeActionPerformed
        // TODO add your handling code here:
        ListClientForm c = new ListClientForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_clientListeActionPerformed

    private void EtatBLEditionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EtatBLEditionActionPerformed
        // TODO add your handling code here:
        EditionBLForm c = new EditionBLForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_EtatBLEditionActionPerformed

    private void CAbyClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CAbyClientActionPerformed
        // TODO add your handling code here:
        CAByClient c = new CAByClient();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_CAbyClientActionPerformed

    private void EvolutionCAbyClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EvolutionCAbyClientActionPerformed
        // TODO add your handling code here:
        EvolutionCAByClient c = new EvolutionCAByClient();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_EvolutionCAbyClientActionPerformed

    private void EvolutionQteByClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EvolutionQteByClientActionPerformed
        // TODO add your handling code here:
        EvolutionQteByClient c = new EvolutionQteByClient();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_EvolutionQteByClientActionPerformed

    private void ListArticle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListArticle1ActionPerformed
        // TODO add your handling code here:
        ListArticleForm c = new ListArticleForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ListArticle1ActionPerformed

    private void statAchatFourParArticleParPeriodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statAchatFourParArticleParPeriodActionPerformed
        // TODO add your handling code here:
        StatVenteParArticleParPeridoForm c = new StatVenteParArticleParPeridoForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_statAchatFourParArticleParPeriodActionPerformed

    private void statAchatFourParCommercialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statAchatFourParCommercialActionPerformed
        // TODO add your handling code here:
        StatVenteParCommercialForm c = new StatVenteParCommercialForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_statAchatFourParCommercialActionPerformed

    private void CAbyCommercialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CAbyCommercialActionPerformed
        // TODO add your handling code here:
        CAByCommercial c = new CAByCommercial();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_CAbyCommercialActionPerformed

    private void TypeArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TypeArticleActionPerformed
        // TODO add your handling code here:
        FormTypeArticle c = new FormTypeArticle();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_TypeArticleActionPerformed

    private void ArticleNonVenduMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArticleNonVenduMenuItemActionPerformed
        // TODO add your handling code here:ArticleNonVenduForm
        ArticleNonVenduForm c = new ArticleNonVenduForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ArticleNonVenduMenuItemActionPerformed

    private void PreferedArticleMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreferedArticleMenuActionPerformed
        // TODO add your handling code here:
        PreferedArticleForm c = new PreferedArticleForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_PreferedArticleMenuActionPerformed

    private void ConfigParamsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfigParamsActionPerformed
        // TODO add your handling code here:
        FormConfigParams c = new FormConfigParams();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ConfigParamsActionPerformed

    private void statAchatFourParArticleParClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statAchatFourParArticleParClientActionPerformed
        // TODO add your handling code here:
        StatVenteParArticleParClientForm c = new StatVenteParArticleParClientForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_statAchatFourParArticleParClientActionPerformed

    private void ClassificationArticleParActiviteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClassificationArticleParActiviteActionPerformed
        // TODO add your handling code here:
        ClassificationArticleParActiviteForm c = new ClassificationArticleParActiviteForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ClassificationArticleParActiviteActionPerformed

    private void AlertReglementMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlertReglementMenuItemActionPerformed
        // TODO add your handling code here:
        AlertReglementForm c = new AlertReglementForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_AlertReglementMenuItemActionPerformed

    private void GrandLivreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrandLivreActionPerformed
        // TODO add your handling code here:
        GrandLivreForm c = new GrandLivreForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_GrandLivreActionPerformed

    private void CAbyClientExonoreTVAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CAbyClientExonoreTVAActionPerformed
        CAByClientNonTVA c = new CAByClientNonTVA();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_CAbyClientExonoreTVAActionPerformed

    private void typeCommercialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeCommercialActionPerformed
        // TODO add your handling code here:
        FormTypeCommercial c = new FormTypeCommercial();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_typeCommercialActionPerformed

    private void ConsultReglementComptableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultReglementComptableActionPerformed
        // TODO add your handling code here:
        RecherchReglement_ComptableForm c = new RecherchReglement_ComptableForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ConsultReglementComptableActionPerformed

    private void ReliquatMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReliquatMenuItemActionPerformed
        // TODO add your handling code here:
        FormReliquat c = new FormReliquat("", "");
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ReliquatMenuItemActionPerformed

    private void ReliquatRechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReliquatRechercheActionPerformed
        // TODO add your handling code here:
        RecherchReliquatForm c = new RecherchReliquatForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ReliquatRechercheActionPerformed

    private void AlertDechargeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlertDechargeMenuItemActionPerformed
        // TODO add your handling code here:
        RecherchFactureDechargeForm c = new RecherchFactureDechargeForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_AlertDechargeMenuItemActionPerformed

    private void AlertArticleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlertArticleMenuItemActionPerformed
        // TODO add your handling code here:
        AlertArticleForm c = new AlertArticleForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_AlertArticleMenuItemActionPerformed

    private void ReliquatRechercheDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReliquatRechercheDetailActionPerformed
        // TODO add your handling code here:
        RecherchReliquatDetailForm c = new RecherchReliquatDetailForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ReliquatRechercheDetailActionPerformed

    private void SuiviReglement1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuiviReglement1ActionPerformed
        // TODO add your handling code here:
        RechFactureNonPayeParRegion c = new RechFactureNonPayeParRegion();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_SuiviReglement1ActionPerformed

    private void DevisRecherche1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DevisRecherche1ActionPerformed
        // TODO add your handling code here:
        RecherchTransactionDuJourForm c = new RecherchTransactionDuJourForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_DevisRecherche1ActionPerformed

    private void ArticleNonVenduMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArticleNonVenduMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ArticleNonVenduMenuItem1ActionPerformed

    private void Affectation_article_clentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Affectation_article_clentActionPerformed
//alertDAO    
        AffectationAlertArticle c = new AffectationAlertArticle();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_Affectation_article_clentActionPerformed

    private void ArticleNonVenduMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArticleNonVenduMenuItem2ActionPerformed
        // TODO add your handling code here:
        ProspectionGroupeArticleForm c = new ProspectionGroupeArticleForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_ArticleNonVenduMenuItem2ActionPerformed

    private void SaisieProspectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaisieProspectionActionPerformed
        // TODO add your handling code here:
        SaisieProspectionCommercial c = new SaisieProspectionCommercial();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_SaisieProspectionActionPerformed

    private void RechercheProspctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RechercheProspctionActionPerformed
        // TODO add your handling code here:
        RechercheProspectionFrom c = new RechercheProspectionFrom();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_RechercheProspctionActionPerformed

    private void consultationGroupeArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultationGroupeArticleActionPerformed
        // TODO add your handling code here:ListeGroupeArticleForm
        ListeGroupeArticleForm c = new ListeGroupeArticleForm();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_consultationGroupeArticleActionPerformed

    private void consultationSaisieProspectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultationSaisieProspectionActionPerformed
        // TODO add your handling code here:
        RechercheSaisieProspectionFrom c = new RechercheSaisieProspectionFrom();
        desktopPane.add(c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize(screenSize.width, screenSize.height);
        c.setVisible(true);
        c.moveToFront();
        try {
            c.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.show();
        desktopPane.repaint();
    }//GEN-LAST:event_consultationSaisieProspectionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(App.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the FormDevis */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new App().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Achat;
    private javax.swing.JMenu Activite;
    private javax.swing.JMenuItem Affectation_article_clent;
    private javax.swing.JMenuItem AlertArticleMenuItem;
    private javax.swing.JMenuItem AlertDechargeMenuItem;
    private javax.swing.JMenu AlertMenu;
    private javax.swing.JMenu AlertMenu1;
    private javax.swing.JMenuItem AlertReglementMenuItem;
    private javax.swing.JMenuItem AnnulerBLMenuItem;
    private javax.swing.JMenuItem AnnulerBLMenuItem1;
    private javax.swing.JMenuItem AnnulerBLMenuItem2;
    private javax.swing.JMenuItem AnnulerBLMenuItem3;
    private javax.swing.JMenuItem Article;
    private javax.swing.JMenuItem ArticleNonVenduMenuItem;
    private javax.swing.JMenuItem ArticleNonVenduMenuItem1;
    private javax.swing.JMenuItem ArticleNonVenduMenuItem2;
    private javax.swing.JMenuItem AvoirFinMenuItem1;
    private javax.swing.JMenuItem AvoirFournisseurMenuItem;
    private javax.swing.JMenu AvoirMenu;
    private javax.swing.JMenuItem AvoirMenuItem;
    private javax.swing.JMenuItem AvoirPassagerMenuItem;
    private javax.swing.JMenuItem BL;
    private javax.swing.JMenu BLFournisseur;
    private javax.swing.JMenuItem BLFournisseurMenuItem;
    private javax.swing.JMenu BLMenu;
    private javax.swing.JMenuItem BLMenuItem;
    private javax.swing.JMenuItem Banque;
    private javax.swing.JMenu BanqueMenu;
    private javax.swing.JMenu CAMenu;
    private javax.swing.JMenuItem CAbyClient;
    private javax.swing.JMenuItem CAbyClientExonoreTVA;
    private javax.swing.JMenuItem CAbyCommercial;
    private javax.swing.JMenuItem ClassificationArticleParActivite;
    private javax.swing.JMenu ClientMenu1;
    private javax.swing.JMenu CommandFournisseur1;
    private javax.swing.JMenuItem CommandeMenuItem;
    private javax.swing.JMenuItem Commercial;
    private javax.swing.JMenu CommercialMenu;
    private javax.swing.JMenuItem ConfigParams;
    private javax.swing.JMenuItem ConsAvoirMenuItem;
    private javax.swing.JMenuItem ConsBLMenuItem;
    private javax.swing.JMenuItem ConsCommandeMenuItem1;
    private javax.swing.JMenuItem ConsFactureMenuItem;
    private javax.swing.JMenuItem ConsPre_CommandeMenuItem;
    private javax.swing.JMenuItem ConsultReglement;
    private javax.swing.JMenuItem ConsultReglementComptable;
    private javax.swing.JMenuItem Consultation;
    private javax.swing.JMenu ConsultationMenuItem;
    private javax.swing.JMenuItem ConsultblDepot;
    private javax.swing.JMenu DevisMenu;
    private javax.swing.JMenuItem DevisRecherche;
    private javax.swing.JMenuItem DevisRecherche1;
    private javax.swing.JMenu DonneeMenu;
    private javax.swing.JMenu DonneeMenu1;
    private javax.swing.JMenuItem Etat;
    private javax.swing.JMenuItem EtatBLEdition;
    private javax.swing.JMenuItem EvolutionCAbyClient;
    private javax.swing.JMenuItem EvolutionQteByClient;
    private javax.swing.JMenuItem Facture;
    private javax.swing.JMenu FactureFournisseur1;
    private javax.swing.JMenuItem FactureFournisseurMenuItem;
    private javax.swing.JMenu FactureMenu;
    private javax.swing.JMenuItem FactureMenuItem;
    private javax.swing.JMenuItem FacturePassagerMenuItem;
    private javax.swing.JMenuItem Famille;
    private javax.swing.JMenuItem Fournisseur;
    private javax.swing.JMenu GestDepot;
    private javax.swing.JMenu GestionUserMenu;
    private javax.swing.JMenuItem GrandLivre;
    private javax.swing.JMenuItem ListArticle;
    private javax.swing.JMenuItem ListArticle1;
    private javax.swing.JMenuItem MAJ_Marge;
    private javax.swing.JMenuItem MAJ_Marge_famille;
    private javax.swing.JMenuItem MAJ_Stock;
    private javax.swing.JMenuItem MAJ_remiseArticle;
    private javax.swing.JMenuItem MAJ_remiseFamille;
    private javax.swing.JMenuItem ModifBLMenuItem2;
    private javax.swing.JMenuItem ModifBLMenuItem4;
    private javax.swing.JMenuItem ModifBLachatMenuItem4;
    private javax.swing.JMenuItem ModifFactureMenuItem2;
    private javax.swing.JMenu PreCommandFournisseur;
    private javax.swing.JMenuItem Pre_CommandeMenuItem;
    private javax.swing.JMenuItem PreferedArticleMenu;
    private javax.swing.JMenu Prospection;
    private javax.swing.JMenu RechercheMenu;
    private javax.swing.JMenu RechercheMenu1;
    private javax.swing.JMenuItem RechercheProspction;
    private javax.swing.JMenuItem Reglement;
    private javax.swing.JMenu ReglementMenu;
    private javax.swing.JMenuItem ReliquatMenuItem;
    private javax.swing.JMenuItem ReliquatRecherche;
    private javax.swing.JMenuItem ReliquatRechercheDetail;
    private javax.swing.JMenu RemiseMenu;
    private javax.swing.JMenuItem Resa;
    private javax.swing.JMenuItem SaisieProspection;
    private javax.swing.JMenuItem SousFamille;
    private javax.swing.JMenu StatVenteMenu;
    private javax.swing.JMenuItem SuiviReglement;
    private javax.swing.JMenuItem SuiviReglement1;
    private javax.swing.JMenuItem TypeArticle;
    private javax.swing.JMenu VentesMenu;
    private javax.swing.JMenu VentesPassagerMenu;
    private javax.swing.JMenuItem annulBLMenuItem;
    private javax.swing.JMenuItem annulFactureMenuItem1;
    private javax.swing.JMenuItem annulavoirMenuItem;
    private javax.swing.JMenuItem annuldevisMenuItem;
    private javax.swing.JMenu articleMenu;
    private javax.swing.JMenuItem avoir_non_idMenuItem;
    private javax.swing.JMenuItem blDepot;
    private javax.swing.JMenuItem clientListe;
    private javax.swing.JMenuItem clientMenuItem1;
    private javax.swing.JMenuItem consultAvoirVente;
    private javax.swing.JMenuItem consultationGroupeArticle;
    private javax.swing.JMenuItem consultationSaisieProspection;
    private javax.swing.JMenuItem crudDepot;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuItem devisMenuItem;
    private javax.swing.JMenuItem devisPassagerMenuItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem marque;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem modifdevisMenuItem1;
    private javax.swing.JMenu stat;
    private javax.swing.JMenuItem statAchatFour;
    private javax.swing.JMenuItem statAchatFourParArticle;
    private javax.swing.JMenuItem statAchatFourParArticleParClient;
    private javax.swing.JMenuItem statAchatFourParArticleParPeriod;
    private javax.swing.JMenuItem statAchatFourParCommercial;
    private javax.swing.JMenuItem statglobal;
    private javax.swing.JMenuItem typeCommercial;
    private javax.swing.JMenuItem type_client;
    private javax.swing.JMenuItem type_reglement;
    private javax.swing.JMenuItem zonegeo;
    // End of variables declaration//GEN-END:variables

}
