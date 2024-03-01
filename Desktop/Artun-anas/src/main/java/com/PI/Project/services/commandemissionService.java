package com.PI.Project.services;


import java.sql.*;
import java.util.List;

import com.PI.Project.models.commande;
import com.PI.Project.models.mission;
import com.PI.Project.utils.MyDabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class commandemissionService implements IServiceCommande<commande>,IServiceMission<mission> {
    private Connection connection;

    public commandemissionService() {
        connection = MyDabase.getInstance().getConnection();
    }

    @Override
    public List<commande> recuperercommande() throws SQLException {
        String sql = "select * from commande";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        ObservableList<commande> commandes = FXCollections.observableArrayList();
        while (rs.next()) {
            commande l = new commande();
            l.setId(rs.getInt("id"));
            l.setReference(rs.getString("reference"));
            l.setDate(rs.getDate("date"));
            l.setPrixtotal(rs.getDouble("prixtotal"));
            l.setId_mission(rs.getInt("id_mission")); // Utiliser une autre propriété pour id_mission

            commandes.add(l);
        }
        return commandes;
    }

    @Override
    public void ajoutermission(mission mission) throws SQLException {
        String sql = "INSERT INTO mission (id_mission, etat) VALUES ('"
                + mission.getId() + "', '"
                + mission.getEtat() + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public List<mission> recuperermission() throws SQLException {


            String sql = "select * from mission";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            ObservableList<mission> missions = FXCollections.observableArrayList();
            while (rs.next()) {
                mission m = new mission();
                m.setId(rs.getInt("id_mission"));
                m.setEtat(rs.getString("etat"));


                missions.add(m);
            }
            return missions;
        }

    @Override
    public void mettreAJourCommande(commande commande) throws SQLException {
        String sql = "UPDATE commande SET id_mission = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, commande.getId_mission());
        statement.setInt(2, commande.getId()); // Ajoutez cette ligne
        statement.executeUpdate();
    }




    @Override
    public void supprimercommande(int id) throws SQLException {
        String sql = "delete from commande where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }


    @Override
    public void modifiermission(mission mission) throws SQLException {
        String sql = "UPDATE mission SET etat = 'livré' WHERE id_mission = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, mission.getId());
            preparedStatement.executeUpdate();
        }
    }


    public void supprimercommandeParMission(int idMission) throws SQLException {
        String sql = "DELETE FROM commande WHERE id_mission = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Définir l'ID de la mission pour laquelle vous souhaitez supprimer les commandes
            preparedStatement.setInt(1, idMission);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La suppression des commandes liées à la mission a échoué, aucune ligne affectée.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la suppression des commandes liées à la mission: " + e.getMessage());
        }
    }



}



