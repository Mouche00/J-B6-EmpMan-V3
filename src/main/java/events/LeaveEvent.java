package events;

import models.User;

public class LeaveEvent {
    private long days;
    private User user;

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LeaveEvent(long days, User user) {
        this.days = days;
        this.user = user;
    }
}
