package org.example.Server.Commands;

import org.example.Common.Models.Vehicle;
import org.example.Common.Response;

/**
 * Абстрактная команда с именем и описанием
 */
public abstract class Command{
    private final String name;
    private final String description;
    public Command(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public String getDiscription(){
        return description;
    }

    public void execute(String[] args, Response response, String LogPas) throws Exception{

    }

    public void execute_add(String[] args, Response response, Vehicle vehicle, String LogPas) throws Exception{

    }

    @Override
    public String toString(){
        return "Command { name = " + name + ", description = " + description + "}";
    }

}
