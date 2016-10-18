package com.example.waitou.rxjava;

/**
 * Created by waitou on 16/10/8.
 */

public class UserBean {

    public String name;
    public int    age;

  //  @SerializedName(value = "emailAddress",alternate = {"email" , "email_address"})
    public String emailAddress;

    public UserBean(String name, int age , String emailAddress) {
        this.name = name;
        this.age = age;
        this.emailAddress = emailAddress;
    }


    public UserBean(){

    }

    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
