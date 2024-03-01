package com.PI.Project.services;

import java.sql.SQLException;
import java.util.List;

public  interface IServiceCommande<T> {
      List<T> recuperercommande() throws SQLException;
      void supprimercommande(int id) throws SQLException;
      void mettreAJourCommande(T t) throws SQLException;

}
