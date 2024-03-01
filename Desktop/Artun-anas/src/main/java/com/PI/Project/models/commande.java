package com.PI.Project.models;
import java.sql.Date;
public class commande {
    private int id,id_client,id_produit,id_mission;
    private String reference;
    private Date date;
    private double prixtotal;

    public commande(int id, int id_client, int id_produit, int id_mission, String reference, Date date, double prixtotal) {
        this.id = id;
        this.id_client = id_client;
        this.id_produit = id_produit;
        this.id_mission = id_mission;
        this.reference = reference;
        this.date = date;
        this.prixtotal = prixtotal;
    }
    public commande( int id_client, int id_produit, int id_mission, String reference, Date date, double prixtotal) {
        this.id_client = id_client;
        this.id_produit = id_produit;
        this.id_mission = id_mission;
        this.reference = reference;
        this.date = date;
        this.prixtotal = prixtotal;
    }
    public commande() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getId_mission() {
        return id_mission;
    }

    public void setId_mission(int id_mission) {
        this.id_mission = id_mission;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrixtotal() {
        return prixtotal;
    }

    public void setPrixtotal(double prixtotal) {
        this.prixtotal = prixtotal;
    }

    @Override
    public String toString() {
        return "commande{" +
                "id=" + id +
                ", id_client=" + id_client +
                ", id_produit=" + id_produit +
                ", id_mission=" + id_mission +
                ", reference='" + reference + '\'' +
                ", date=" + date +
                ", prixtotal=" + prixtotal +
                '}';
    }
}
