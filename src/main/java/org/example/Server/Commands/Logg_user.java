package org.example.Server.Commands;

import org.example.Common.Response;
import org.example.Common.Utilites.Console;
import org.example.Common.Utilites.StandartConsole;
import org.example.Server.Managers.DataBaseManager;

public class Logg_user extends Command{
    private final Console console;
    private final DataBaseManager dataBaseManager;
    public Logg_user(Console console, DataBaseManager dataBaseManager) {
        super("", "");
        this.console = console;
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public void execute(String[] args, Response response, String LogPas){
        Console console1 = new StandartConsole();
        try{
            String answer = DataBaseManager.CheckLogin(args[1]);
            response.setAnswer(answer);
        }catch (Exception e){
            response.setAnswer(e.getMessage());
        }
    }

}
