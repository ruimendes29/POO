package model;

import java.util.Comparator;

public class CompareByRents implements Comparator<User> {

    public int compare(User a,User b)
    {
        if (a.equals(b)) return 0;
        else if (a.performedRents() > b.performedRents()) return -1;
        else return 1;
    }

}
