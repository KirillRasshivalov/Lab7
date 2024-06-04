package org.example.Server.Commands;

import org.example.Common.Response;
import org.example.Server.Managers.CollectionManager;

/**
 * Команда которая очищает коллекцию
 */
public class Clear extends Command{
    private final CollectionManager collectionManager;
    public Clear(CollectionManager collectionManager){
        super("clear", "очищает коллекцию.");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args, Response response, String LogPas){
        try{
            collectionManager.clearCollection(response, LogPas);
        }catch (Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
