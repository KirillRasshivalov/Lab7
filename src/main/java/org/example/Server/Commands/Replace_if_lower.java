package org.example.Server.Commands;

import org.example.Common.Models.Vehicle;
import org.example.Common.Response;
import org.example.Common.Utilites.Console;
import org.example.Server.Managers.CollectionManager;

public class Replace_if_lower extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Replace_if_lower(Console console, CollectionManager collectionManager){
        super("replace_if_lower", "заменить значение по ключу, если новое значение id больше страого.");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute_add(String[] args, Response response, Vehicle vehicle, String LogPas){
        try{
            collectionManager.replaceIfLower(args[1], response, vehicle, LogPas);
        } catch(Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
