package com.example.myparks.data;

import com.example.myparks.modle.Park;

import java.util.List;

public interface AsyncResponse {
    // will be list that hold parks object
    void processPark(List<Park> parks);
}
