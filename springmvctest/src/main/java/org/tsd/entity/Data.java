package org.tsd.entity;

import java.util.List;

/**
 * Created by zgl on 17-10-19.
 */
public class Data {

    private String id;
    private List<Float> list;
    public Data(){

    }

    public Data(String id, List<Float> list) {
        this.id = id;
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Float> getList() {
        return list;
    }

    public void setList(List<Float> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", list=" + list +
                '}';
    }
}
