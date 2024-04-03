package Entities;

import java.util.Calendar;

abstract class HumanResourse {

    protected int id;
    public String name;
    public String lastName;
    public Calendar birthDate;

    public HumanResourse(String name, String lastName, Calendar birhtDate){
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birhtDate;
    }

}
