package com.PI.Project.services;

import java.sql.SQLException;
import java.util.List;

public interface IServiceMission<T> {
    void ajoutermission(T t) throws SQLException;
    List<T> recuperermission() throws SQLException;

    void modifiermission(T t) throws SQLException;

}
