package org.example.Server.Commands;

import org.example.Common.Response;
import org.example.Server.Managers.CommandManager;

import java.util.HashMap;

/**
 * Команда которая выводит информцию по всем командам
 */
public class Help extends Command{
    private static CommandManager commandManager;
    public Help(CommandManager commandManager){
        super("help", "выводит информаци про доступные команды.");
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args, Response response, String LogPas){
        CommandManager commandManager = new CommandManager();
        HashMap<String, Command> ListOfCommands = commandManager.getCommands();
        String answer = "";
        for(String name : ListOfCommands.keySet()){
            Command command = ListOfCommands.get(name);
            if (!name.equals("logg_user") && !name.equals("create_new_user") && !name.equals("check_user")) answer += command.getName() + ": " + command.getDiscription() + "\n";
        }
        response.setAnswer(answer);
    }
}
