package org.example.Server.Commands;

import org.example.Common.Response;
import org.example.Common.Utilites.Console;
import org.example.Common.Utilites.StandartConsole;
import org.example.Server.Managers.DataBaseManager;

public class Create_new_user extends Command{
    private final DataBaseManager dataBaseManager;
    private final Console console;
    public Create_new_user(Console console, DataBaseManager dataBaseManager) {
        super("", "");
        this.dataBaseManager = dataBaseManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args, Response response, String LogPas){
        Console console1 = new StandartConsole();
        try{
            DataBaseManager.add_users(LogPas);
            DataBaseManager.insertionUser();
            console1.println("Добавлен новый пользователь.");
        }catch (Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
