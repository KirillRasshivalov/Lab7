package org.example.Server.Commands;

import org.example.Common.Models.Vehicle;
import org.example.Common.Response;
import org.example.Common.Utilites.Console;
import org.example.Common.Utilites.StandartConsole;
import org.example.Server.Managers.CollectionManager;

import java.util.LinkedHashMap;

public class Show extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Show (Console console, CollectionManager collectionManager){
        super("show", "выводит введенные транспортные средства.");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute(String[] args, Response response, String LogPas){
        Console console1 = new StandartConsole();
        if (collectionManager.getTable().keySet().size() == 0){
            response.setAnswer("Коллекция пуста.");
        }
        else {
            LinkedHashMap<String, Vehicle> table = CollectionManager.getTable();
            String answer = "";
            for (String key : table.keySet()) {
                answer += table.get(key).toString() + "\n";
            }
            response.setAnswer(answer);
        }
    }
}
