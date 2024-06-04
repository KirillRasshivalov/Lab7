package org.example.Server.Commands;

import org.example.Common.Models.Vehicle;
import org.example.Common.Response;
import org.example.Common.Utilites.Console;
import org.example.Server.Exeptions.NumberValueExeption;
import org.example.Server.Managers.CollectionManager;

public class Update extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Update(Console console, CollectionManager collectionManager){
        super("update", "обновляет значение элемента коллекции id которого равен заданному.");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute_add(String[] args, Response response, Vehicle vehicle, String LogPas) {
        try {
            collectionManager.updateIdEquels(args[1], response, vehicle, LogPas);
        } catch (NumberValueExeption e) {
            throw new RuntimeException(e);
        }
    }
}
