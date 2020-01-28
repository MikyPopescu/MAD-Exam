package com.example.examprep2.network;

import java.util.List;

public class HttpResponse {
    List<Item> goalkeeper;
    List<Item> winger;
    List<Item> inter;
    List<Item> center;


    public HttpResponse(List<Item> goalkeeper, List<Item> winger, List<Item> inter, List<Item> center) {
        this.goalkeeper = goalkeeper;
        this.winger = winger;
        this.inter = inter;
        this.center = center;
    }

    public List<Item> getGoalkeeper() {
        return goalkeeper;
    }

    public void setGoalkeeper(List<Item> goalkeeper) {
        this.goalkeeper = goalkeeper;
    }

    public List<Item> getWinger() {
        return winger;
    }

    public void setWinger(List<Item> winger) {
        this.winger = winger;
    }

    public List<Item> getInter() {
        return inter;
    }

    public void setInter(List<Item> inter) {
        this.inter = inter;
    }

    public List<Item> getCenter() {
        return center;
    }

    public void setCenter(List<Item> center) {
        this.center = center;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "goalkeeper=" + goalkeeper +
                ", winger=" + winger +
                ", inter=" + inter +
                ", center=" + center +
                '}';
    }
}
