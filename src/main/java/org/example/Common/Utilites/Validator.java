package org.example.Common.Utilites;


import org.example.Client.Exception.NumberValueException;
import org.example.Common.Models.VehicleType;

public class Validator {
    public static void checkId(String t) throws Exception{
        Console console = new StandartConsole();
        Long id;
        try{
            id = Long.parseLong(t);
        } catch (Exception e){
            console.println(e.getMessage());
        }
    }

    public static void checkPower(String s) throws Exception{
        Console console = new StandartConsole();
        Double power = null;
        try {
            power = Double.parseDouble(s);
        }
        catch (Exception e){
            console.println(e.getMessage());
        }
        if (power <= 0){
            console.println("Значение поля enginePower не может быть меньше 0.");
            throw new NumberValueException();
        }

    }

    public static void checkCoordenateX(String x) throws Exception{
        Console console = new StandartConsole();
        Long xx = null;
        try{
            xx = Long.parseLong(x);
        }catch (Exception e){
            console.println(e.getMessage());
        }
        if (xx == 0) {
            console.println("Значение поля checkCoordenateX не может быть null.");
            throw new NumberValueException();
        }
    }
    public static void checkCoordinatesY(String y) throws Exception{
        Console console = new StandartConsole();
        Double yy = null;
        try{
            yy = Double.parseDouble(y);
        } catch (Exception e){
            console.println(e.getMessage());
        }
        if (yy <= 0){
            console.println("Значение поля checkCoordinatesY должно быть > 0.");
            throw new NumberValueException();
        }
    }

    public static void notEmpty(String arg) throws NumberValueException {
        if (arg.isEmpty() || arg.trim().isEmpty()) {
            throw new NumberValueException();
        }
    }

    public static void checkWheels(String s) throws Exception{
        Console console = new StandartConsole();
        Long wheels = null;
        try{
            wheels = Long.parseLong(s);
        }catch(Exception e){
            console.println(e);
        }
        if (wheels <= 0){
            console.println("Значение поля checkWheels должно быть > 0.");
            throw new NumberValueException();
        }
    }

    public static void fuelComp(String s) throws Exception{
        Console console = new StandartConsole();
        Long comp = null;
        try{
            comp = Long.parseLong(s);
        }catch(Exception e){
            console.println(e);
        }
        if (comp <= 0){
            console.println("Значение поля fuelComp должно быть > 0.");
            throw new NumberValueException();
        }
    }

    public static void checkType(String type) throws Exception{
        Console console = new StandartConsole();
        try{
            VehicleType.valueOf(type);
        }catch(Exception e){
            console.println(e.getMessage());
        }
    }
}
