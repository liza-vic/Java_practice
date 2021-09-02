package by.bsu.practice.jdbc.dao.impl;

import by.bsu.practice.jdbc.connection.DBConnection;
import by.bsu.practice.jdbc.dao.InformationDAO;
import by.bsu.practice.jdbc.entity.Galaxy;
import by.bsu.practice.jdbc.entity.Planet;
import by.bsu.practice.jdbc.entity.Satellite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBInformationDAO implements InformationDAO {

    private Connection dbConnection = null;

    @Override
    public ResultSet getAllPlanetGalaxy(int id) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            //извлекаем из бд все планеты и их спутники для опред. галактики
            String sqlQuery = "SELECT * FROM planet LEFT JOIN satellite ON planet.idplanet=satellite.planetId\n" +
                    "WHERE planet.galaxyId=? AND planet.isLife=1";
            dbConnection = DBConnection.getConnection();
            statement = dbConnection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public List<Galaxy> getAllGalaxy() {

        Statement statement = null;
        List<Galaxy> galaxies = new ArrayList<>();
        try {
            //извлекаем из бд все галактики
            String sqlQuery = "SELECT * FROM galaxy";
            dbConnection = DBConnection.getConnection();
            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {

                Galaxy galaxy = new Galaxy();
                galaxy.setId(resultSet.getInt("galaxyId"));
                galaxy.setName(resultSet.getString("name"));
                galaxies.add(galaxy);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return galaxies;
    }

    @Override
    public ResultSet getMinRadiusPlanet() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //извлекаем из бд планеты и их спутники с наим. радиусом
            String sqlQuery = "SELECT * FROM planet LEFT JOIN satellite ON planet.idplanet=satellite.planetId\n" +
                    "WHERE planet.radius = (SELECT MIN(planet.radius) FROM planet)";
            dbConnection = DBConnection.getConnection();
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public ResultSet getMaxSatellites() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //извлекаем из бд планеты и их спутники с наибольшим кол-вом спутников
            String sqlQuery = "WITH cte\n" +
                    "         AS  (\n" +
                    "    SELECT idplanet,\n" +
                    "           planet.name as planetName,\n" +
                    "           planet.radius as planetRadius,\n" +
                    "           coreTemperature,\n" +
                    "           isAtmosphere,\n" +
                    "           isLife,\n" +
                    "           galaxyId,\n" +
                    "           satelliteId,\n" +
                    "           satellite.name as satelliteName,\n" +
                    "           satellite.radius as satelliteRadius,\n" +
                    "           distance,\n" +
                    "           planetId\n" +
                    "    FROM planet LEFT JOIN satellite ON planet.idplanet = satellite.planetId\n" +
                    ")\n" +
                    "SELECT * FROM cte  INNER JOIN\n" +
                    "(SELECT planetId as pId, COUNT(*) AS amount from satellite\n" +
                    "GROUP BY planetId\n" +
                    "HAVING amount=\n" +
                    "(SELECT MAX(am) AS max FROM\n" +
                    "(\n" +
                    "    SELECT count(*) AS am from satellite\n" +
                    "GROUP BY planetId\n" +
                    "    ) as tbl)) as pIa\n" +
                    "ON cte.planetId = pIa.pId";
            dbConnection = DBConnection.getConnection();
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public List<Galaxy> getGalaxyMaxTemperature() {
        Statement statement = null;
        List<Galaxy> galaxies = new ArrayList<>();
        try {
            //извлекаем из бд информацию о галлактике с наибоьлшей суммой темератур ядер планет
            String sqlQuery = "WITH cte\n" +
                    "    AS ( SELECT MAX(galaxy.galaxyId) as id,SUM(coreTemperature) as sum FROM galaxy LEFT JOIN planet ON planet.galaxyId=galaxy.galaxyId\n" +
                    "         GROUP BY galaxy.galaxyId\n" +
                    "         HAVING sum = (\n" +
                    "             SELECT MAX(sum) as max FROM (\n" +
                    "                                             SELECT SUM(coreTemperature) as sum\n" +
                    "                                             FROM galaxy\n" +
                    "                                                      LEFT JOIN planet ON planet.galaxyId = galaxy.galaxyId\n" +
                    "                                             GROUP BY galaxy.galaxyId\n" +
                    "                                         ) as gps\n" +
                    "         ) )\n" +
                    "SELECT galaxyId, name from galaxy INNER JOIN cte ON galaxyId=cte.id\n" +
                    "\n";
            dbConnection = DBConnection.getConnection();
            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                //создаем объект класса Catalog с данными из бд для каждой строки
                Galaxy galaxy = new Galaxy();
                galaxy.setId(resultSet.getInt("galaxyId"));
                galaxy.setName(resultSet.getString("name"));
                galaxies.add(galaxy);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return galaxies;
    }
}
