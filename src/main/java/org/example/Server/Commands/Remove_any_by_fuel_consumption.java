package org.example.Server.Commands;

import org.example.Common.Response;
import org.example.Common.Utilites.Console;
import org.example.Common.Utilites.StandartConsole;
import org.example.Server.Managers.CollectionManager;

public class Remove_any_by_fuel_consumption extends Command {
    final private Console console;
    final private CollectionManager collectionManager;
    public Remove_any_by_fuel_consumption(Console console, CollectionManager collectionManager){
        super("remove_any_by_fuel_consumption", "удаляет из коллекции один элемент значения поля которого равно заданному.");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args, Response response, String LogPas){
        Console console1 = new StandartConsole();
        try{
            collectionManager.removeByFuel(args[1], response, LogPas);
        }
        catch (Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
