package by.bsu.practice.jdbc.service;

import by.bsu.practice.jdbc.dao.impl.DBInformationDAO;
import by.bsu.practice.jdbc.entity.Galaxy;
import by.bsu.practice.jdbc.entity.Planet;
import by.bsu.practice.jdbc.entity.Satellite;
import by.bsu.practice.jdbc.exception.InvalidDataException;
import by.bsu.practice.jdbc.factory.DAOFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Operation {
    public static final DBInformationDAO dbInformationDao = DAOFactory.getInstance().getDbFileDAO();
    public static final List<Galaxy> galaxies = dbInformationDao.getAllGalaxy();

    //информация о планетах и их спутников для определенной галактики
    public static String getCelestialBodiesLife(String galaxy) throws InvalidDataException {
        StringBuilder result = new StringBuilder();
        Galaxy correctGalaxy = new Galaxy();
        List<Planet> planets = new ArrayList<>();
        List<Satellite> satellites = new ArrayList<>();

        boolean isFind = false;
        //находим в списке галлактик нам нужную
        for (Galaxy galaxy1 : galaxies)
            if(galaxy1.getName().equals(galaxy)){
                correctGalaxy = galaxy1;
                isFind = true;
                break;
            }

        //если не находим бросаем исключение
        if(!isFind)
            throw new InvalidDataException("Catalog with this name does not exist!");

        try {
            //берем из бд нужную нам информацию
            ResultSet resultSet = dbInformationDao.getAllPlanetGalaxy(correctGalaxy.getId());

            //пока есть строки добавляем раздельно планеты и спутники в соответсвующие списки
            while (resultSet.next()) {

                Planet planet = new Planet();
                planet.setId(resultSet.getInt("idplanet"));
                planet.setName(resultSet.getString("planet.name"));
                planet.setRadius(resultSet.getInt("planet.radius"));
                planet.setTemperature(resultSet.getInt("coreTemperature"));
                planet.setAtmosphere(resultSet.getInt("isAtmosphere") != 0);
                planet.setLife(resultSet.getInt("isLife") != 0);
                planet.setGalaxyId(resultSet.getInt("galaxyId"));
                //если планеты еще нет в списке, добавляем ее
                if(!isPlanetInList(planets,planet.getId()))
                    planets.add(planet);

                //если у нее есть спутники, добавляем их в соответствующий список
                if(resultSet.getInt("satelliteId") != 0){
                    Satellite satellite = new Satellite();
                    satellite.setId(resultSet.getInt("satelliteId"));
                    satellite.setName(resultSet.getString("satellite.name"));
                    satellite.setDistance(resultSet.getInt("distance"));
                    satellite.setRadius(resultSet.getInt("satellite.radius"));
                    satellite.setPlanetId(resultSet.getInt("planetId"));
                    satellites.add(satellite);
                }
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //приводим всю информацию к строковому виду
        planetsAddToString(planets,result);
        satellitesAddToString(satellites,result);

        return result.toString();
    }

    //приводим к строковому виду список планет
    private static void planetsAddToString(List<Planet> list, StringBuilder result){
        for(Planet planet : list){
            result.append(planet.toString()).append("\n");
        }
    }

    //приводим к строковому виду список спутников
    private static void satellitesAddToString(List<Satellite> list, StringBuilder result){
        for(Satellite satellite: list){
            result.append(satellite.toString()).append("\n");
        }
    }


    //проверяем находится ли планета в списке
    private static boolean isPlanetInList(List<Planet> planets, int id){
        for(Planet planet:planets)
            if(planet.getId() == id)
                return true;

        return false;
    }

    //находим планеты и их спутники для планет с минимальным радиусом
    public static String getCelestialBodiesMinRadius(){

        //строка с итоговой информацией
        StringBuilder result = new StringBuilder();
        List<Planet> planets = new ArrayList<>();
        List<Satellite> satellites = new ArrayList<>();

        try {
            //берем с бд все планеты минимального радиуса
            ResultSet resultSet = dbInformationDao.getMinRadiusPlanet();

            //пока есть строки добавляем раздельно планеты и спутники в соответсвующие списки
            while (resultSet.next()) {
                Planet planet = new Planet();
                planet.setId(resultSet.getInt("idplanet"));
                planet.setName(resultSet.getString("planet.name"));
                planet.setRadius(resultSet.getInt("planet.radius"));
                planet.setTemperature(resultSet.getInt("coreTemperature"));
                planet.setAtmosphere(resultSet.getInt("isAtmosphere") != 0);
                planet.setLife(resultSet.getInt("isLife") != 0);
                planet.setGalaxyId(resultSet.getInt("galaxyId"));
                if(!isPlanetInList(planets,planet.getId()))
                    planets.add(planet);

                if(resultSet.getInt("satelliteId") != 0){
                    Satellite satellite = new Satellite();
                    satellite.setId(resultSet.getInt("satelliteId"));
                    satellite.setName(resultSet.getString("satellite.name"));
                    satellite.setDistance(resultSet.getInt("distance"));
                    satellite.setRadius(resultSet.getInt("satellite.radius"));
                    satellite.setPlanetId(resultSet.getInt("planetId"));
                    satellites.add(satellite);
                }
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //приводим к строковому виду всю информацию
        planetsAddToString(planets,result);
        satellitesAddToString(satellites,result);

        return result.toString();

    }

    //находим планеты и их спутники для планет с максимальным кол-вом спутников
    public static String getCelestialBodiesMaxSatellites(){
        //строка с итоговой информацией
        StringBuilder result = new StringBuilder();
        List<Planet> planets = new ArrayList<>();
        List<Satellite> satellites = new ArrayList<>();

        try {
            //берем с бд все планеты c максимальным кол-вом спутников
            ResultSet resultSet = dbInformationDao.getMaxSatellites();

            //пока есть строки добавляем раздельно планеты и спутники в соответсвующие списки
            while (resultSet.next()) {
                Planet planet = new Planet();
                planet.setId(resultSet.getInt("idplanet"));
                planet.setName(resultSet.getString("planetName"));
                planet.setRadius(resultSet.getInt("planetRadius"));
                planet.setTemperature(resultSet.getInt("coreTemperature"));
                planet.setAtmosphere(resultSet.getInt("isAtmosphere") != 0);
                planet.setLife(resultSet.getInt("isLife") != 0);
                planet.setGalaxyId(resultSet.getInt("galaxyId"));
                if(!isPlanetInList(planets,planet.getId()))
                    planets.add(planet);

                if(resultSet.getInt("satelliteId") != 0){
                    Satellite satellite = new Satellite();
                    satellite.setId(resultSet.getInt("satelliteId"));
                    satellite.setName(resultSet.getString("satelliteName"));
                    satellite.setDistance(resultSet.getInt("distance"));
                    satellite.setRadius(resultSet.getInt("satelliteRadius"));
                    satellite.setPlanetId(resultSet.getInt("planetId"));
                    satellites.add(satellite);
                }
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //приводим к строковому виду всю информацию

        planetsAddToString(planets,result);
        satellitesAddToString(satellites,result);
        return result.toString();
    }


    //находим планеты и их спутники для планет с максимальным кол-вом спутников и минимальной суммой объемов этих спутников
    public static String getCelestialBodiesMaxSatellitesMinRadius(){
        //строка с итоговой информацией
        StringBuilder result = new StringBuilder();
        List<Planet> planets = new ArrayList<>();
        List<Satellite> satellites = new ArrayList<>();

        try {
            //берем с бд все планеты c максимальным кол-вом спутников
            ResultSet resultSet = dbInformationDao.getMaxSatellites();

            //пока есть строки добавляем раздельно планеты и спутники в соответсвующие списки
            while (resultSet.next()) {
                Planet planet = new Planet();
                planet.setId(resultSet.getInt("idplanet"));
                planet.setName(resultSet.getString("planetName"));
                planet.setRadius(resultSet.getInt("planetRadius"));
                planet.setTemperature(resultSet.getInt("coreTemperature"));
                planet.setAtmosphere(resultSet.getInt("isAtmosphere") != 0);
                planet.setLife(resultSet.getInt("isLife") != 0);
                planet.setGalaxyId(resultSet.getInt("galaxyId"));
                if(!isPlanetInList(planets,planet.getId()))
                    planets.add(planet);

                if(resultSet.getInt("satelliteId") != 0){
                    Satellite satellite = new Satellite();
                    satellite.setId(resultSet.getInt("satelliteId"));
                    satellite.setName(resultSet.getString("satelliteName"));
                    satellite.setDistance(resultSet.getInt("distance"));
                    satellite.setRadius(resultSet.getInt("satelliteRadius"));
                    satellite.setPlanetId(resultSet.getInt("planetId"));
                    satellites.add(satellite);
                }
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //находим планету с минимальной суммой объемов спутников
        Planet resultPlanet = findMinVolumeSatellites(planets,satellites);
        //приводим к строковому виду ее спутники, ее саму и ее галактику
        result.append(findGalaxyById(resultPlanet.getGalaxyId()).toString()).append("\n");
        result.append(resultPlanet.toString()).append("\n");
        satellitesAddToString(findSatellitesForPlanet(resultPlanet,satellites),result);

        return result.toString();
    }

    //находим список всех спутников для определенной планеты
    private static List<Satellite> findSatellitesForPlanet(Planet planet, List<Satellite> list){
        List<Satellite> satellites = new ArrayList<>();
        for(Satellite satellite : list){
            if(planet.getId() == satellite.getPlanetId())
                satellites.add(satellite);
        }
        return satellites;
    }

    //находим минимальную сумму объемов спутников
    private static Planet findMinVolumeSatellites(List<Planet> planets, List<Satellite> satellites){
        Planet resultPlanet = planets.get(0);
        long minSumVolume = findSumVolumeSatellites(planets.get(0),satellites);
        //перебираем все планеты
        for(Planet planet : planets){
            long volume = findSumVolumeSatellites(planet,satellites);
            //если находим меньше, ставим минимальной текущую планету
            if(volume < minSumVolume){
                minSumVolume = volume;
                resultPlanet = planet;
            }
        }
        return resultPlanet;
    }

    //ищет сумму объемов спутников для определенной планеты
    private static long findSumVolumeSatellites(Planet planet,List<Satellite> satellites){
        long volume = 0;
        for(Satellite satellite : satellites){
            if(satellite.getPlanetId() == planet.getId()){
                volume += Math.pow(satellite.getRadius(),3);
            }
        }
        return volume;
    }

    //ищем галлактику по переданному id
    private static Galaxy findGalaxyById(int id){
        Galaxy result = null;
        for(Galaxy galaxy : galaxies){
            if(galaxy.getId() == id)
                result = galaxy;
        }
        return result;
    }

    //галактики с максимальной суммой температур ядер планет
    public static String  findGalaxiesMaxTemperature(){
        StringBuilder result = new StringBuilder();
        for(Galaxy galaxy : dbInformationDao.getGalaxyMaxTemperature()){
            result.append(galaxy.toString());
        }
        return result.toString();
    }
}
