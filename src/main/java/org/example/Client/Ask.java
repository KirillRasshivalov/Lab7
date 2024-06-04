package org.example.Client;



import org.example.Client.Exception.NumberValueException;
import org.example.Common.Models.Coordinates;
import org.example.Common.Models.Vehicle;
import org.example.Common.Models.VehicleType;
import org.example.Common.Utilites.Console;


import java.io.Serializable;

/**
 * Класс для заполнения коллекции.
 */
public class Ask implements Serializable {
    public static class AskBreak extends Exception{}
    public static Vehicle askVehicle(Console console, Long id, String LogPas, String key) throws AskBreak, NumberValueException {
        String name;
        while (true){
            console.print("Введите имя: ");
            name = console.readline().trim();
            if (!name.isEmpty()) break;
        }
        var coordinates = askCoordinates(console);
        Double enginePower;
        while (true){
            console.print("Введите мощность двигателя: ");
            String power = console.readline().trim();
            if (!power.isEmpty()){
                try{
                    enginePower = Double.parseDouble(power);
                    if (enginePower > 0) break;
                    else throw new NumberValueException();
                }catch (NumberFormatException e){
                    console.println("\u001B[31m" + "Вы ввели что-то неправильно, проверьте " +
                            "что введенные данные это число." + "\u001B[0m");
                }catch (NumberValueException e){
                    console.println("\u001B[31m" + "Вы ввели что-то неправильно, мощность двигателя не може быть" +
                            " отрицательной или равной нулю. " + "Попробуйте еще раз." + "\u001B[0m");
                }
            }
        }
        Long numberOfWheels;
        while (true){
            console.print("Введите количество колес в автомобиле: ");
            String number = console.readline().trim();
            if (!number.isEmpty()){
                try{
                    numberOfWheels = Long.parseLong(number);
                    if (numberOfWheels > 0) break;
                    else throw new NumberValueException();
                }catch(NumberFormatException e){
                    console.println("\u001B[31m" + "Вы уверены что введенные вами данные " +
                            "соответствуют целочисленному типу? Попробуйте ввести еще раз." + "\u001B[0m");
                } catch(NumberValueException e){
                    console.println("\u001B[31m" + "Вы ввели некорректные данные, количество колес у транспортного средства " +
                            "не может быть равным нулю или быть отрицательным. " + "\u001B[0m");
                }
            }
        }
        var vehicleType = askVehicleType(console);
        long fuelConsumption;
        while(true){
            console.print("Введите расход топлива: ");
            String cost = console.readline().trim();
            if (!cost.isEmpty()){
                try {
                    fuelConsumption = Long.parseLong(cost);
                    if (fuelConsumption > 0) break;
                    else throw new NumberValueException();
                } catch(NumberFormatException e){
                    console.println("\u001B[31m" + "Введенный вами формат некорректен, проверьте что " +
                            "введенные данные соответствуют целочисленному типу." + "\u001B[0m");
                } catch(NumberValueException e){
                    console.println("\u001B[31m" + "Вы ввели некорректные данные, " +
                            "расход топлива не может быть равным нулю или отрицательным, попробуйте еще раз." + "\u001B[0m");
                }
            }
        }
        return new Vehicle(LogPas, key, id, name, coordinates, enginePower, numberOfWheels, vehicleType, fuelConsumption);
    }

    public static Coordinates askCoordinates(Console console) throws AskBreak{
        long x;
        while (true){
            console.print("Введите координату x: ");
            String coordinate = console.readline().trim();
            if(!coordinate.isEmpty()){
                try {
                    x = Long.parseLong(coordinate);
                    break;
                }catch(NumberFormatException e){
                    console.println("\u001B[31m" + "Проверьте что введенная вами координата x" +
                            " соответствует целочисленному типу." + "\u001B[0m");
                }
            }
        }
        double y;
        while (true){
            console.print("Введите координату y: ");
            String coordinate = console.readline().trim();
            if (!coordinate.isEmpty()){
                try{
                    y = Double.parseDouble(coordinate);
                    break;
                }catch(NumberFormatException e){
                    console.println("\u001B[31m" + "Проверьте что введенная вами координата y" +
                            " соответствует целочисленному или вещественному типу." + "\u001B[0m");
                }
            }
        }
        return new Coordinates(x, y);
    }

    public static VehicleType askVehicleType(Console console) throws AskBreak{
        VehicleType r;
        while(true){
            console.print("Введите вид транспорта [" + VehicleType.names() + "]: ");
            String type = console.readline().trim();
            if (!type.isEmpty()){
                try{
                    r = VehicleType.valueOf(type.toUpperCase());
                    break;
                } catch(NullPointerException | IllegalArgumentException e){
                    console.println("\u001B[31m" + "Вы ввели что-то неправильно. Проверьте что " +
                            "введенный вами тип транспорта находится в списке приведенном ниже." + "\u001B[0m");
                }
            }
        }
        return r;
    }
}
