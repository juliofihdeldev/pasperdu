package com.pasperdu.app.wapjwennli.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Utilisateur on 2017-05-04.
 */

public class Signaler implements Serializable {

    public String getNumeroAcontacter() {
        return numeroAcontacter;
    }

    public void setNumeroAcontacter(String numeroAcontacter) {
        this.numeroAcontacter = numeroAcontacter;
    }

    public String getNomObjet() {
        return nomObjet;
    }

    public void setNomObjet(String nomObjet) {
        this.nomObjet = nomObjet;
    }

    public String getNomProprietaire() {
        return nomProprietaire;
    }

    public void setNomProprietaire(String nomProprietaire) {
        this.nomProprietaire = nomProprietaire;
    }

    public String getLieuRetrouve() {
        return lieuRetrouve;
    }

    public void setLieuRetrouve(String lieuRetrouve) {
        this.lieuRetrouve = lieuRetrouve;
    }

    private String numeroAcontacter;
    private String nomObjet;
    private String nomProprietaire;
    private String lieuRetrouve;

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    private String etat ;

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    private Date dateAjout ;
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private String color;
}
