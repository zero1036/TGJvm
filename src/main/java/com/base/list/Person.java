package com.base.list;

/**
 * Created by linzc on 2017/1/17.
 */
public class Person {
    public Person(int id, String nameFirst, String nameSecond) {
        this.id = id;
        this.nameFirst = nameFirst;
        this.nameSecond = nameSecond;
    }

    private int id;

    private String nameFirst;

    private String nameSecond;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public String getNameSecond() {
        return nameSecond;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public void setNameSecond(String nameSecond) {
        this.nameSecond = nameSecond;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
//            Person person = (Person) obj;
            if (((Person) obj).getNameFirst().equals(this.getNameFirst()) && ((Person) obj).getNameSecond().equals(this.getNameSecond())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return this.nameFirst + this.nameSecond;
    }

    @Override
    public int hashCode() {
//        Person p = (Person)this;
        return (int) ExceptFn.hashString(this.toString(), 1);
    }
}
