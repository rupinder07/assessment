package com.nagarro.common.dto;

import com.google.gson.Gson;

public interface JsonContent {

    default String toJson(){
        return new Gson().toJson(this);
    }

}
