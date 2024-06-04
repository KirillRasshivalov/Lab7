package org.example.Server.Commands;

import org.example.Common.Response;
import org.example.Server.Managers.CollectionManager;

import java.time.ZonedDateTime;

/**
 * Команда которая выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов)
 */
public class Info extends Command {
    private final CollectionManager collectionManager;
    public Info(CollectionManager collectionManager){
        super("info", "выводит информацию о коллекции (тип, дата инициализации, кол-во элементов.");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args, Response response, String LogPas){
        String answer = "";
        answer += "Тип коллекции: " + collectionManager.getTable().getClass().getName() + "\n";
        answer += "Дата инициализации: " + ZonedDateTime.now() + "\n";
        answer += "Кол-во элементов: " + collectionManager.getTable().keySet().size() + "\n";
        response.setAnswer(answer);
    }

}
