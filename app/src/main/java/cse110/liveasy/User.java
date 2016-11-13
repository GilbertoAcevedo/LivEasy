package cse110.liveasy;

import java.util.HashMap;

/**
 * Created by gilbe_000 on 10/22/2016.
 */
/*
 * User class. Defines member variables which includes all the information
 * pertaining to the user that is going to be stored into the database.
 */
public class User {
    public String username;
    public String full_name;
    public String phone_number;
    public String email;
    public boolean group;
    public boolean isPending;
    public String groupID;
    public HashMap<String,Object> preferences;


    public User(String full_name, String phone_number, String email, boolean group) {
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.email = email;
        this.group = group;
        this.groupID = "";
        this.isPending = false;
    }

    public User() {
    }

    public String getUsername(){
        return username;
    }
    public String getFull_name(){
        return full_name;
    }
    public String getPhone_number(){
        return phone_number;
    }
    public String getEmail(){
        return email;
    }
    public boolean getGroup(){
        return group;
    }

    public void setPrefrences(HashMap<String,Object> map){
        this.preferences = map;
    }

}
