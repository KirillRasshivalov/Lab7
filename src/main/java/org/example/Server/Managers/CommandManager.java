package org.example.Server.Managers;

import org.example.Common.Models.Vehicle;
import org.example.Common.Response;
import org.example.Common.Utilites.Console;
import org.example.Common.Utilites.StandartConsole;
import org.example.Server.Commands.*;

import java.util.HashMap;

/**
 * Класс для обработки комманд.
 */
public class CommandManager {
    private static HashMap<String, Command> ListOfCommands;
    private CommandManager commandManager;
    private CollectionManager collectionManager;

    private DataBaseManager dataBaseManager;

    /**
     * Конструктор для квсех комманд.
     */
    public CommandManager(){
        Console console = new StandartConsole();
        ListOfCommands = new HashMap<>();
        ListOfCommands.put("help", new Help(commandManager));
        ListOfCommands.put("insert", new Insert(console, collectionManager));
        ListOfCommands.put("show", new Show(console, collectionManager));
        ListOfCommands.put("info", new Info(collectionManager));
        ListOfCommands.put("update", new Update(console, collectionManager));
        ListOfCommands.put("remove_key", new Remove_key(console, collectionManager));
        ListOfCommands.put("clear", new Clear(collectionManager));
        ListOfCommands.put("exit", new Exit(collectionManager));
        ListOfCommands.put("remove_greater", new Remove_greater(console, collectionManager));
        ListOfCommands.put("remove_lower", new Remove_lower(console, collectionManager));
        ListOfCommands.put("replace_if_lower", new Replace_if_lower(console, collectionManager));
        ListOfCommands.put("remove_any_by_fuel_consumption", new Remove_any_by_fuel_consumption(console, collectionManager));
        ListOfCommands.put("group_counting_by_fuel_consumption", new Group_counting_by_fuel_consumption(collectionManager));
        ListOfCommands.put("filter_greater_than_engine_power", new Filter_greater_than_engine_power(collectionManager));
        ListOfCommands.put("execute_script", new Execute_script(commandManager, collectionManager));
        ListOfCommands.put("logg_user", new Logg_user(console, dataBaseManager));
        ListOfCommands.put("create_new_user", new Create_new_user(console, dataBaseManager));
        ListOfCommands.put("check_user", new Check_user(console, dataBaseManager));
    }

    /**
     * Начало чтения команд из командной строки.
     * @param comand - команда посланная на вход
     * @throws Exception
     */

    public static void startExecuting(String comand, Response response, Vehicle vehicle, String LogPas) throws Exception {
        String commandName = comand.split(" ")[0];
        String[] fullCommand = comand.split(" ");
        if (!ListOfCommands.containsKey(commandName)){
            response.setAnswer("Введенной вами команды не существует.");
        }
        else {
            Command command = ListOfCommands.get(commandName.toLowerCase());
            if (vehicle != null) command.execute_add(fullCommand, response, vehicle, LogPas);
            else command.execute(fullCommand, response, LogPas);
        }
    }

    public HashMap<String, Command> getCommands(){
        return ListOfCommands;
    }
}
