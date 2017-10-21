package org.tsd.entity;

/**
 * Created by zgl on 17-10-20.
 */
public class EndData {
    private int number;
    private String id;

    public EndData(){

    }
    public EndData(int number, String id) {
        this.number = number;
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EndData{" +
                "number=" + number +
                ", id='" + id + '\'' +
                '}';
    }
}
