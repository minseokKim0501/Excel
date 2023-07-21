package com.main.controller;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestVO {

    private int a;

    private String test;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
