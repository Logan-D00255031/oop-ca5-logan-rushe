package org.example.Dominik.DAOs;
import org.example.Dominik.DTOs.CarClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Main Author: Dominik Domalip
 */
// *** Dominik's Code for feature 7 and 8
public class JsonConverter {
    public String carObjectToJson(CarClass car){
        Gson gsonParses = new Gson();
        String jsonString = gsonParses.toJson(car);
        return jsonString;
    }

    public  String carListToJson(List<CarClass> carList){
//        Using gsonBuilder instead of just new Gson because this allows to print nicely object after object instead of everything in one line
//        https://stackoverflow.com/questions/4105795/pretty-print-json-in-java
        Gson gsonParser = new GsonBuilder().create();
        String jsonString = gsonParser.toJson(carList);
        return jsonString;
    }

    public CarClass fromJson(String carJson){
        Gson gsonParser = new Gson();
        CarClass car = gsonParser.fromJson(carJson, CarClass.class);
        return car;
    }

    public List<CarClass> JsonToCarList(String carListJson){
        Gson gsonParser = new Gson();
        Type carListType = new TypeToken<List<CarClass>>(){}.getType();
        List<CarClass> carList = gsonParser.fromJson(carListJson, carListType);
        return carList;
    }
}
