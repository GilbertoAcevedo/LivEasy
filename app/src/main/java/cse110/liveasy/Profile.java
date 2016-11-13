package cse110.liveasy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Map;

/**
 * Created by Duke Lin on 11/12/2016.
 */

public class Profile {

    String username;
    String email;
    String phoneNum;

    public Profile(Map<String, Object> user, String username) {

        this.username = username;
        this.email = (String)user.get("email");
        this.phoneNum = (String)user.get("phone_number");
    }

    public Profile(User userObject, String username) {

        this.username = username;
        this.email = userObject.getEmail();
        this.phoneNum = userObject.getPhone_number();
    }
}
