package cse110.liveasy;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by gilbe_000 on 10/23/2016.
 * This class is for group and has attributes for group name, members in it
 * Also has number of users, and users pending in group
 */

public class Group {

    public String name;
    public Map<String, Object> members;
    public int num_users;
    public ArrayList<String> pending;

    public Group(){
        this.pending = new ArrayList<String>();
        num_users = 1;
    }

    /*
     * Defines group and sets varialbes for group
     */
    public Group(String name, Map<String, Object> members, int num_users){
        this.name = name;
        this.members = members;
        this.num_users = num_users;
        this.pending = new ArrayList<String>();
    }

    /*
     * gets members for groups and saves it into a hashmap
     */

    public String[] getMembers() {

        String[] mems = new String[num_users];
        int i = 0;
        for (Map.Entry<String, Object> entry : members.entrySet()) {

            mems[i] = entry.getKey();
            i++;
        }

        return mems;
    }
}

