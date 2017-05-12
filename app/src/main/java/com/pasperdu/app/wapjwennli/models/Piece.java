package com.pasperdu.app.wapjwennli.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Utilisateur on 2017-04-28.
 */

public class Piece implements Serializable {

    public Piece(){

    }

    private String telephoneReference ;
    private String typePiece ;
    private String namePiece ;
    private String message ;
    private String lieuPiece ;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private String color ;

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    private String etat ;
    private Date dateAjout ;

    public String getTelephoneReference() {
        return telephoneReference;
    }

    public void setTelephoneReference(String telephoneReference) {
        this.telephoneReference = telephoneReference;
    }

    public String getTypePiece() {
        return typePiece;
    }

    public void setTypePiece(String typePiece) {
        this.typePiece = typePiece;
    }

    public String getNamePiece() {
        return namePiece;
    }

    public void setNamePiece(String namePiece) {
        this.namePiece = namePiece;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLieuPiece() {
        return lieuPiece;
    }

    public void setLieuPiece(String lieuPiece) {
        this.lieuPiece = lieuPiece;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }
}
