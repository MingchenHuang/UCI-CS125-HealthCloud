package com.example.healthcloud;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class Task {
    private String title, type, date;
    private int intensity, starthour, startminute, endhour, endminute, ordertime,isrecommend;

    public Task(){
        // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    }

    public Task(String title, String type, String date, int intensity, int starthour, int startminute, int endhour, int endminute, int ordertime, int isrecommend) {
        this.title = title;
        this.type = type;
        this.date = date;
        this.intensity = intensity;
        this.starthour = starthour;
        this.startminute = startminute;
        this.endhour = endhour;
        this.endminute = endminute;
        this.ordertime = ordertime;
        this.isrecommend = isrecommend;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public int getStarthour() {
        return starthour;
    }

    public void setStarthour(int starthour) {
        this.starthour = starthour;
    }

    public int getStartminute() {
        return startminute;
    }

    public void setStartminute(int startminute) {
        this.startminute = startminute;
    }

    public int getEndhour() {
        return endhour;
    }

    public void setEndhour(int endhour) {
        this.endhour = endhour;
    }

    public int getEndminute() {
        return endminute;
    }

    public void setEndminute(int endminute) {
        this.endminute = endminute;
    }

    public int getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(int ordertime) {
        this.ordertime = ordertime;
    }

    public int getIsrecommend() {
        return isrecommend;
    }

    public void setIsrecommend(int isrecommend) {
        this.isrecommend = isrecommend;
    }

    public String gettimeperiod(){
        String sminute = "";
        if (this.startminute<10){
            sminute = "0"+String.valueOf(this.startminute);
        }
        else{
            sminute = String.valueOf(this.startminute);
        }

        String eminute = "";
        if(this.endminute<10){
            eminute = "0"+String.valueOf(this.endminute);
        }
        else {
            eminute = String.valueOf(this.endminute);
        }


        String timeperiod = String.valueOf(this.starthour)+":"+sminute+" - "+String.valueOf(this.endhour)+":"+eminute;
        return timeperiod;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", intensity=" + intensity +
                ", starthour=" + starthour +
                ", startminute=" + startminute +
                ", endhour=" + endhour +
                ", endminute=" + endminute +
                ", ordertime=" + ordertime +
                ", isrecommend=" + isrecommend +
                '}';
    }
}
