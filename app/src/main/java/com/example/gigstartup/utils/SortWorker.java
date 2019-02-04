package com.example.gigstartup.utils;

import com.example.gigstartup.dtos.DtoUserInfo;

import java.util.Comparator;

public class SortWorker implements Comparator<DtoUserInfo> {
    @Override
    public int compare(DtoUserInfo o1, DtoUserInfo o2) {
        if(o1.getRating()> o2.getRating()){
           return  -1;
        } else if (o1.getRating() == o2.getRating()) {
            if (o1.getRatePerHour() < o2.getRatePerHour()) {
                return -1;
            }
            else return 1;
        }
        else {
            return  1;
        }
    }
}
