package com.nagarro.policyservice.messaging;

import com.google.gson.Gson;

public interface JsonContent {

    default String toJson(){
        return new Gson().toJson(this);
    }

}
