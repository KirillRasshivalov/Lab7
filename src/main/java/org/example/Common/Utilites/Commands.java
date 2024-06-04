package org.example.Common.Utilites;

import java.util.ArrayList;

public class Commands {
    private static ArrayList<String> ListOfCommands;

    public void Commands(){
        ListOfCommands = new ArrayList<>();
        ListOfCommands.add("help");
        ListOfCommands.add("insert");
        ListOfCommands.add("show");
        ListOfCommands.add("info");
        ListOfCommands.add("update");
        ListOfCommands.add("remove_key");
        ListOfCommands.add("clear");
        ListOfCommands.add("exit");
        ListOfCommands.add("remove_greater");
        ListOfCommands.add("remove_lower");
        ListOfCommands.add("replace_if_lower");
        ListOfCommands.add("save");
        ListOfCommands.add("remove_any_by_fuel_consumption");
        ListOfCommands.add("group_counting_by_fuel_consumption");
        ListOfCommands.add("filter_greater_than_engine_power");
        ListOfCommands.add("execute_script");
    }

    public boolean find_command(String command){
        if (ListOfCommands.contains(command)){
            return true;
        }else{
            return false;
        }
    }
    public String get_command(String command){
        return command;
    }

}