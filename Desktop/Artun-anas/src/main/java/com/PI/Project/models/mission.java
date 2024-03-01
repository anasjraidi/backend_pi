package com.PI.Project.models;

public class mission {

   int id_mission;
    String etat	;


    public mission(int id_mission,String etat) {
        this.id_mission = id_mission;
        this.etat = etat;

    }
    public mission(String etat) {
        this.etat = etat;
    }
    public mission() {

    }

    public int getId() {
        return id_mission;
    }

    public void setId(int id_mission) {
        this.id_mission = id_mission;
    }





    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "mission{" +
                "id=" + id_mission +
                ", etat='" + etat + '\'' +
                '}';
    }
}
