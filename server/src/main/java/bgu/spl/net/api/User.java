package bgu.spl.net.api;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class User {
    private String username;
    private String password;
    private LocalDate birthday;
    private int numPosts;
    private ConcurrentLinkedQueue<User> followers;
    private LinkedList<User> following;
    private boolean loggedIn = false;
    public User(String username, String password, String Sbirthday){
        this.username = username;
        this.password = password;
        int day = Integer.parseInt(Sbirthday.substring(0,Sbirthday.indexOf('-')));
        Sbirthday = Sbirthday.substring(Sbirthday.indexOf('-')+1);
        int month = Integer.parseInt(Sbirthday.substring(0,Sbirthday.indexOf('-')));
        Sbirthday = Sbirthday.substring(Sbirthday.indexOf('-')+1);
        int year = Integer.parseInt(Sbirthday);
        this.birthday = LocalDate.of(year,month,day);
        numPosts = 0;
        following =  new LinkedList<User>();
        followers = new ConcurrentLinkedQueue<User>();
    }

    public LinkedList<User> getFollowing() {
        return following;
    }
    public int getNumPosts() {
        return numPosts;
    }

    public ConcurrentLinkedQueue<User> getFollowers() {
        return followers;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    public void setNumPosts() {
        numPosts++;
    }
    public int calculateAge() {
        LocalDate currentDate = LocalDate.now();
        if ((birthday != null) && (currentDate != null)) {
            return Period.between(birthday, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
