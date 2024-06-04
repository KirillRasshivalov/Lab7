package org.example.Common.Models;

import org.example.Client.Managers.OperationWithId;
import org.example.Common.Utilites.Validator;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Vehicle implements Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double enginePower; //Поле не может быть null, Значение поля должно быть больше 0
    private Long numberOfWheels; //Поле не может быть null, Значение поля должно быть больше 0
    private VehicleType type; //Поле не может быть null
    private long fuelConsumption; //Значение поля должно быть больше 0

    private String LogPass;

    private String key;

    public Vehicle(String LogPass, String key,  Long id, String name, Coordinates coordinates, ZonedDateTime creationDate, Double enginePower, Long numberOfWheels, VehicleType type, long fuelConsumption){
        this.LogPass = LogPass;
        this.key = key;
        this.id = OperationWithId.generateId();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.type = type;
        this.fuelConsumption = fuelConsumption;
    }

    public Vehicle(String LogPas, String key, Long id, String name, Coordinates coordinates, Double enginePower, Long numberOfWheels, VehicleType type, long fuelConsumption){
        this(LogPas, key, id, name, coordinates, ZonedDateTime.now(), enginePower, numberOfWheels, type, fuelConsumption);
    }

    public Vehicle() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Double getEnginePower() {
        return enginePower;
    }

    public Long getNumberOfWheels() {
        return numberOfWheels;
    }

    public VehicleType getType() {
        return type;
    }

    public long getFuelConsumption() {
        return fuelConsumption;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogPass(String logPass) {
        this.LogPass = logPass;
    }

    public String getLogPass() {
        return LogPass;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setFuelConsumption(long fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public void setNumberOfWheels(Long numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public void setEnginePower(Double enginePower) {
        this.enginePower = enginePower;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "UserName=" + LogPass.split(" ")[0] +
                ", key=" + key +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", enginePower=" + enginePower +
                ", numberOfWheels=" + numberOfWheels +
                ", type=" + type +
                ", fuelConsumption=" + fuelConsumption +
                '}';
    }

    /**
     * Приводит все типы к верным в формате xmlю
     * @return строку валидную для .xml файлов
     */
    public String toXML(){
        return "id=\"" + id + "\"" +
                " name=\"" + name + '\"' +
                " x=\"" + coordinates.getX() + "\"" +
                " y=\"" + coordinates.getY() + "\"" +
                " creationDate=\"" + creationDate + "\"" +
                " enginePower=\"" + enginePower + "\"" +
                " numberOfWheels=\"" + numberOfWheels + "\"" +
                " LogPas=\'" + LogPass + "\'" +
                " key=\'" + key + "\'" +
                " type=\"" + type + "\"" +
                " fuelConsumption=\"" + fuelConsumption + "\"";
    }

    public Vehicle(String[] data) throws Exception{
        Validator.checkId(data[1]);
        Validator.notEmpty(data[2]);
        Validator.checkCoordenateX(data[3]);
        Validator.checkCoordinatesY(data[4]);
        Validator.checkPower(data[6]);
        Validator.checkWheels(data[7]);
        Validator.checkType(data[8]);
        Validator.fuelComp(data[9]);


        this.id = Long.parseLong(data[1]);
        this.name = data[2];
        this.coordinates = new Coordinates(Long.parseLong(data[3]), Double.parseDouble(data[4]));
        this.creationDate = ZonedDateTime.parse(data[5]);
        this.enginePower = Double.parseDouble(data[6]);
        this.numberOfWheels = Long.parseLong(data[7]);
        this.type = VehicleType.valueOf(data[8]);
        this.fuelConsumption = Long.parseLong(data[9]);
    }

}
