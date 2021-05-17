package com.example.snapappdemo;

public interface TaskListener {

    //står for at vores bytes ryger det rigtige sted hen
    public void receive(byte[] bytes);

}
