package org.example.Server.Commands;

import org.example.Common.Response;
import org.example.Common.Utilites.Console;
import org.example.Common.Utilites.StandartConsole;
import org.example.Server.Managers.CollectionManager;

public class Remove_key extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Remove_key(Console console, CollectionManager collectionManager){
        super("remove_key", "удаляет значение элемента по заданному ключу.");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute(String[] args, Response response, String LogPas){
        Console console1 = new StandartConsole();
        try{
            collectionManager.deleteElementByKey(args[1], response, LogPas);
        } catch (Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
