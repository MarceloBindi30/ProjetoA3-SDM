package com.a3sdm.Util;

import java.io.Serializable;

public class MsgResp implements Serializable {
    private double value;
    
    public MsgResp(double value) {
        this.value = value;
    }


    public double getValue() {
        return value;
    }

}
