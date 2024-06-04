package org.example.Server.Commands;

import org.example.Common.Response;
import org.example.Server.Managers.CollectionManager;

/**
 * Команда которая выводит элементы, значение поля enginePower которых больше заданного
 */
public class Filter_greater_than_engine_power extends Command{
    private final CollectionManager collectionManager;
    public Filter_greater_than_engine_power(CollectionManager collectionManager){
        super("filter_greater_than_engine_power", "выводит значения полей больше заданного.");
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute(String[] args, Response response, String LogPas){
        try{
            collectionManager.filterByEngine(args[1], response);
        }catch(Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
