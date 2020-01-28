package com.example.examprep2.network;

import java.util.Date;

public class PlayerInfo {
    String name;
    Date birthday;
    String favHand;

    public PlayerInfo(String name, Date birthday, String favHand) {
        this.name = name;
        this.birthday = birthday;
        this.favHand = favHand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getFavHand() {
        return favHand;
    }

    public void setFavHand(String favHand) {
        this.favHand = favHand;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", favHand='" + favHand + '\'' +
                '}';
    }
}
