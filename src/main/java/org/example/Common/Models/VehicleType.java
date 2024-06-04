package org.example.Common.Models;

import java.io.Serializable;

public enum VehicleType implements Serializable {
    PLANE, DRONE, CHOPPER;
    public static String names(){
        StringBuilder moods = new StringBuilder();
        for(var obj : values()){
            moods.append(obj.name()).append(", ");
        }
        return moods.substring(0, moods.length() - 2);
    }
}
