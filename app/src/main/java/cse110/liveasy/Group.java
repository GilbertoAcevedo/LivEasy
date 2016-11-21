package cse110.liveasy;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by gilbe_000 on 10/23/2016.
 */

public class Group {

    public String name;
    public Map<String, Object> members;
    public int num_users;
    public ArrayList<String> pending;

    public Group() {
        this.pending = new ArrayList<String>();
        num_users = 1;
    }

    public Group(String name, Map<String, Object> members, int num_users) {
        this.name = name;
        this.members = members;
        this.num_users = num_users;
        this.pending = new ArrayList<String>();
    }

}

