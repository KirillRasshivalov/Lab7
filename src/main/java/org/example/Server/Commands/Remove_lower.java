package org.example.Server.Commands;

import org.example.Common.Response;
import org.example.Common.Utilites.Console;
import org.example.Common.Utilites.StandartConsole;
import org.example.Server.Managers.CollectionManager;

public class Remove_lower extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Remove_lower (Console console, CollectionManager collectionManager){
        super("remove_lower", "удаляет все эелемента из коллекции, чьи id превышают заданный.");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args, Response response, String LogPas){
        Console console1 = new StandartConsole();
        try{
            collectionManager.removeLowerElem(args[1], response, LogPas);
        }catch (Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
