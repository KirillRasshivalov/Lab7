package org.example.Common.Models;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Long x; //Поле не может быть null

    private Double y; //Поле не может быть null

    public Coordinates(Long x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "x = " + x + "; " + "y = " + y;
    }

    public Double getY() {
        return y;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
