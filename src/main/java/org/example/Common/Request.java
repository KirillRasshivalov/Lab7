package org.example.Common;

import java.io.Serializable;

public class Request implements Serializable {
    private String command;
    private Object Vehicle;
    private String key;

    private String LogPass;

    public Request(Object Vehicle, String command, String key, String LogPass){
        this.Vehicle = Vehicle;
        this.command = command;
        this.LogPass = LogPass;
        this.key = key;
    }

    public Object getVehicle(){
        return Vehicle;
    }

    public String getCommand(){
        return command;
    }
    public String getKey(){
        return key;
    }

    public String getLogPass() {
        return LogPass;
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", Vehicle=" + Vehicle +
                ", key='" + key + '\'' +
                '}';
    }
}
