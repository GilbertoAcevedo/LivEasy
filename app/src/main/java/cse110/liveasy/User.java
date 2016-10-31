package cse110.liveasy;

/**
 * Created by gilbe_000 on 10/22/2016.
 */

public class User {
    public String username;
    public String full_name;
    public String phone_number;
    public String email;
    public boolean group;
    public boolean isPending;
    public String groupID;


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


}
