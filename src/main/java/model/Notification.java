package model;

import java.util.List;

public abstract class Notification {

    private int status;

    public static final int UNEVALUATED = 0;
    public static final int DECLINED = -1;
    public static final int ACCEPTED = 1;

    public Notification() {
        this.status = UNEVALUATED;
    }

    public Notification(Notification n) {
        this.status = n.getStatus();
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int newStatus) {
        this.status = newStatus;
    }

    public boolean isPendent() {
        return this.status == 0;
    }

    public abstract Notification clone();

    public abstract List<String> toShow();

}
