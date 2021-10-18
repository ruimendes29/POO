package model;

import java.util.Comparator;

public class CompareByKms implements Comparator<User> {

    public int compare(User a,User b)
    {
        if (a.equals(b)) return 0;
        else if (a.travelledKms() > b.travelledKms()) return -1;
        else return 1;
    }

}
