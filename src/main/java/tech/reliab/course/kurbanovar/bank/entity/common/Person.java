package tech.reliab.course.kurbanovar.bank.entity.common;

import java.util.Date;

abstract public class Person {
    protected Integer id;
    protected String name;
    protected String surname;
    protected String middleName = null;
    protected Date birthday;

    public Person(Integer id, String name, String surname, Date birthday){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFullName(){
        String str = getName();
        if (getMiddleName() != null)
            str += " " + getMiddleName();
        str += " " + getSurname();
        return str;
    }
}
