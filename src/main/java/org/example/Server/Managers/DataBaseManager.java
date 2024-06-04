package org.example.Server.Managers;

import org.example.Common.Models.Coordinates;
import org.example.Common.Models.Vehicle;
import org.example.Common.Response;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import static org.example.Common.Models.VehicleType.PLANE;

public class DataBaseManager{
    private static String URL = "jdbc:postgresql://pg:5432/studs";
    //private static String URL = "jdbc:postgresql://localhost:5432/postgres"; - подключение к локальной базе данных

    private static String USERNAME = "***";
    //private static String USERNAME = "postgres";

    private static String PASSWORD = "***";
    //private static String PASSWORD = "kirill";

    private static Connection connection;


    public static HashMap<String, String> map = new HashMap<>();

    public DataBaseManager(){
        map = new HashMap<>();
    }

    public static void add_users(String logPas){
        if (map == null){
            map = new HashMap<>();
        }
        String login = logPas.split(" ")[0];
        String password = logPas.split(" ")[1];
        map.put(login, password);
    }

    public static void fillTable(){
        String userTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id_user SERIAL PRIMARY KEY," +
                "LogPas VARCHAR(100)," +
                "key VARCHAR(100)," +
                "id BIGINT," +
                "name VARCHAR(100)," +
                "x BIGINT, " +
                "y DOUBLE PRECISION," +
                "ZoneDateTime VARCHAR(100)," +
                "enginePower DOUBLE PRECISION," +
                "numberOfWheels BIGINT," +
                "type VARCHAR(100)," +
                "fuelComp BIGINT);";
        try(PreparedStatement statement = connection.prepareStatement(userTable)){
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void fillUsers(){
        String loginTable = "CREATE TABLE IF NOT EXISTS logUsers(" +
                "id SERIAL PRIMARY KEY, " +
                "Login VARCHAR(150)," +
                "Password VARCHAR(150));";
        try(PreparedStatement statement = connection.prepareStatement(loginTable)){
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertionUser() throws SQLException{
        String ask = "DELETE FROM logUsers";
        Statement statement1 = connection.createStatement();
        statement1.executeUpdate(ask);
        String query = "INSERT INTO logUsers (Login, Password) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        try{
            connection.setAutoCommit(false);
            for(String keys : map.keySet()){
                preparedStatement.setObject(1, keys);
                preparedStatement.setObject(2, map.get(keys));
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            // Если произошла ошибка, откатываем изменения
            connection.rollback();
            e.printStackTrace();
        } finally {
            // Включаем автокоммит обратно
            connection.setAutoCommit(true);
            preparedStatement.close();
        }
    }

    public static void insertInformation() throws SQLException {
        String ask = "DELETE FROM users";
        Statement statement1 = connection.createStatement();
        statement1.executeUpdate(ask);
        String query = "INSERT INTO users (LogPas, key, id, name, x, y, ZoneDateTime, enginePower, numberOfWheels, " +
                "type, fuelComp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        try {
            // Выключаем автокоммит
            connection.setAutoCommit(false);

            // Итерируемся по элементам коллекции и вставляем их в базу данных
            for (String key : CollectionManager.getTable().keySet()) {
                Vehicle vehicle = CollectionManager.getTable().get(key);
                preparedStatement.setObject(1, vehicle.getLogPass());
                preparedStatement.setObject(2, vehicle.getKey());
                preparedStatement.setObject(3, vehicle.getId());
                preparedStatement.setObject(4, vehicle.getName());
                preparedStatement.setObject(5, vehicle.getCoordinates().getX());
                preparedStatement.setObject(6, vehicle.getCoordinates().getY());
                preparedStatement.setObject(7, vehicle.getCreationDate().toString());
                preparedStatement.setObject(8, vehicle.getEnginePower());
                preparedStatement.setObject(9, vehicle.getNumberOfWheels());
                preparedStatement.setObject(10, vehicle.getType().toString());
                preparedStatement.setObject(11, vehicle.getFuelConsumption());
                preparedStatement.executeUpdate();
            }

            // Фиксируем изменения
            connection.commit();
            System.out.println("Данные успешно добавлены в базу данных.");
        } catch (SQLException e) {
            // Если произошла ошибка, откатываем изменения
            connection.rollback();
            e.printStackTrace();
        } finally {
            // Включаем автокоммит обратно
            connection.setAutoCommit(true);
            preparedStatement.close();
        }
    }

    public static void loadInfromation() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM users;";
        ResultSet resultSet = statement.executeQuery(query);

        // Получите метаданные результирующего набора для получения информации о столбцах
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Выведите имена всех столбцов
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnName(i) + "\t");
        }
        System.out.println();

        Response response = new Response();

        // Выведите данные всех строк
        while (resultSet.next()) {
            Vehicle vehicle = new Vehicle();
            Coordinates coordinates = new Coordinates(resultSet.getLong(6), resultSet.getDouble(7));
            DataBaseManager.add_users(resultSet.getString(2));
            vehicle.setLogPass(resultSet.getString(2));
            vehicle.setKey(resultSet.getString(3));
            vehicle.setId(resultSet.getLong(4));
            vehicle.setName(resultSet.getString(5));
            vehicle.setCoordinates(coordinates);
            vehicle.setCreationDate(ZonedDateTime.now());
            vehicle.setEnginePower(resultSet.getDouble(9));
            vehicle.setNumberOfWheels(resultSet.getLong(10));
            vehicle.setType(PLANE);
            vehicle.setFuelConsumption(resultSet.getLong(12));
            CollectionManager.add(resultSet.getString(3), response, vehicle);
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + "\t");
            }
            System.out.println();
        }
    }

    public static void loadUsers() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM logUsers;";
        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()){
            DataBaseManager.add_users(resultSet.getString(2) + " " + resultSet.getString(3));
        }
    }

    public static void getConnect() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Соедиение с базой данных прошло успешно.");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static String CheckLogin(String login){
        if (map.isEmpty()) return "OK";
        for (String key : map.keySet()){
            if (key.equals(login)){
                return "Bad login";
            }
        }
        return "OK";
    }

    public static void LogNewUser(String login, String password) throws SQLException {
        map.put(login, password);
    }

    public static String CheckUserExist(String[] args){
        if (!map.containsKey(args[1]) || !map.get(args[1]).equals(args[2])){
            return "Incorrect";
        }
        return "OK";
    }

    public static HashMap<String, String> getTable(){
        return map;
    }

}
