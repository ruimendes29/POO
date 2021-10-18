package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RatingNotification extends Notification {

    private LocalDateTime start;
    private String id;
    private int type;

    public static final int CLIENT = 1;
    public static final int CAR = 2;
    public static final int UNDEFINED = 0;

    public RatingNotification(String id, int type, double travelTime) {
        super();
        this.start = LocalDateTime.now().plusHours((long) travelTime);
        this.id = id;
        this.type = type;
    }

    public RatingNotification(RatingNotification n) {
        super(n);
        this.start = n.getStart();
        this.id = n.getId();
        this.type = n.getType();
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public String getId() {
        return this.id;
    }

    public int getType() {
        return this.type;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public RatingNotification clone() {
        return new RatingNotification(this);
    }

    public List<String> toShow() {
        List<String> ret = new ArrayList<>();
        String type = this.getType() == CAR ? "Transport : " : "Client : ";

        ret.add(type + this.getId());

        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingNotification that = (RatingNotification) o;

        return this.type != that.type && start.equals(that.start) && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = this.start.hashCode();
        result = 31 * result + this.id.hashCode();
        result = 31 * result + this.type;
        return result;
    }
}
