package org.example.Server.Commands;

import org.example.Common.Response;
import org.example.Common.Utilites.Console;
import org.example.Common.Utilites.StandartConsole;
import org.example.Server.Managers.DataBaseManager;

public class Check_user extends Command{
    private final DataBaseManager dataBaseManager;
    private final Console console;
    public Check_user(Console console, DataBaseManager dataBaseManager) {
        super("", "");
        this.dataBaseManager = dataBaseManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args, Response response, String LogPas){
        Console console1 = new StandartConsole();
        try{
            response.setAnswer(DataBaseManager.CheckUserExist(args));
            console1.println("Пользователь существует.");
        }catch (Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
