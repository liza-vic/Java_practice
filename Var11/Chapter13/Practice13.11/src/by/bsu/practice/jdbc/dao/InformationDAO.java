package by.bsu.practice.jdbc.dao;

import by.bsu.practice.jdbc.entity.Galaxy;
import by.bsu.practice.jdbc.entity.Planet;
import by.bsu.practice.jdbc.entity.Satellite;

import java.sql.ResultSet;
import java.util.List;


public interface InformationDAO {

    public ResultSet getAllPlanetGalaxy(int id);
    public List<Galaxy> getAllGalaxy();
    public ResultSet getMinRadiusPlanet();
    public ResultSet getMaxSatellites();
    public List<Galaxy> getGalaxyMaxTemperature();
}
