package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zgl on 17-10-19.
 */
public class Data implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Float> list;
    private int number;

    public List<Float> getList() {
        return list;
    }

    public void setList(List<Float> list) {
        this.list = list;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
