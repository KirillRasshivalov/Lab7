package org.example.Server.Commands;

import org.example.Common.Response;
import org.example.Server.Managers.CollectionManager;

/**
 * Команда которая осуществляет выход из программы. Прерывание.
 */
public class Exit extends Command{
    private final CollectionManager collectionManager;
    public Exit (CollectionManager collectionManager){
        super("exit", "осуществляет выход из программы.");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args, Response response, String LogPas){
        try{
            response.setAnswer("Программа завершена.");
        } catch (Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
