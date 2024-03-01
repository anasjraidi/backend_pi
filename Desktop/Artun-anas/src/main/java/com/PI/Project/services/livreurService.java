package com.PI.Project.services;

import java.sql.*;
import java.util.List;

import com.PI.Project.models.livreur;
import com.PI.Project.utils.MyDabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class livreurService implements IService<livreur>{

    private Connection connection;

    public livreurService() {
        connection = MyDabase.getInstance().getConnection();
    }

    @Override
    public List<livreur> recuperer() throws SQLException {
        String sql = "select * from livreur";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        ObservableList<livreur> livreurs = FXCollections.observableArrayList();
        while (rs.next()) {
            livreur l = new livreur();
            l.setId(rs.getInt("id"));
            l.setNom(rs.getString("nom"));
            l.setPrenom(rs.getString("prenom"));
            l.setMail(rs.getString("mail"));
            l.setPhoto(rs.getString("photo"));
            l.setMdp(rs.getString("mdp"));




            livreurs.add(l);
        }
        return livreurs;
    }
    @Override
    public void ajouter(livreur livreur) throws SQLException {
        if (livreurExists(livreur.getMail())) {
            throw new SQLException("Un livreur avec le méme adresse mail existe déja.");
        }

        String sql = "INSERT INTO livreur (nom, prenom, photo, mail, mdp) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, livreur.getNom());
            statement.setString(2, livreur.getPrenom());
            statement.setString(3, livreur.getPhoto());
            statement.setString(4, livreur.getMail());
            statement.setString(5, livreur.getMdp());
            statement.executeUpdate();
        }
    }



    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from livreur where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }


    public void modifier(livreur livreur) throws SQLException {

        String sql = "update livreur set nom = ?, prenom = ?, photo = ? , mail = ? , mdp = ? where id=? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, livreur.getNom());
        statement.setString(2, livreur.getPrenom());
        statement.setString(3, livreur.getPhoto());
        statement.setString(4, livreur.getMail());
        statement.setString(5, livreur.getMdp());
        statement.setInt(6, livreur.getId());

        statement.executeUpdate();

    }
    public livreur findById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM livreur WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new livreur(resultSet.getInt("id"), resultSet.getString("nom"),
                    resultSet.getString("prenom"), resultSet.getString("photo"),
                    resultSet.getString("mail"), resultSet.getString("mdp"));

        } else {
            return null;
        }
    }
    public boolean livreurExists(String mail) throws SQLException {
        String query = "SELECT COUNT(*) FROM livreur WHERE mail = ? ";


             PreparedStatement statement = connection.prepareStatement(query); {

            statement.setString(1, mail);


            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }

        return false;
    }


}
