package org.example.Server.Commands;

import org.example.Client.Ask;
import org.example.Common.Models.Vehicle;
import org.example.Common.Response;
import org.example.Common.Utilites.Console;
import org.example.Common.Utilites.StandartConsole;
import org.example.Server.Exeptions.NumberValueExeption;
import org.example.Server.Managers.CollectionManager;

/**
 * Команда которая осуществляет добавление нового элемента в колекцию.
 */
public class Insert extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Insert(Console console, CollectionManager collectionManager){
        super("insert {element}",  "добавление нового элемента в коллекцию.");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute_add(String[] args, Response response, Vehicle vehicle, String LogPas) throws Ask.AskBreak, NumberValueExeption {
        Console console1 = new StandartConsole();
        if (args.length == 2 && !CollectionManager.getTable().containsKey(args[1])){
            try{
                console1.println("Начало заполнения коллекции.");
                CollectionManager.add(args[1], response, vehicle);
                response.setAnswer("Транспорт успеuшно добавлен");
                console1.println("Добавление нового элемента прошло успешно.");
            }catch (NumberFormatException e) {
                response.setAnswer("\u001B[31m" + "Вы ввели что-то неправильно, проверьте " +
                        "что вы ввели все согласно требованиям." + "\u001B[0m");
            }
        }
        else{
            response.setAnswer("Проверьте что все введенные вами данные соответсвтуют действительности, а ключи не совпадают.");
        }
    }
}
