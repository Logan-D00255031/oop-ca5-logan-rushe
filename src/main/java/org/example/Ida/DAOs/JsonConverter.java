package org.example.Ida.DAOs;
import org.example.Ida.DTOs.Car;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.util.List;

/**
 * Main Author: Dominik Domalip
 */
// *** Dominik's Code for feature 7 and 8
public class JsonConverter {
    public String carObjectToJson(Car car){
        Gson gsonParses = new Gson();
        String jsonString = gsonParses.toJson(car);
        return jsonString;
    }

    public  String carListToJson(List<Car> carList){
//        Using gsonBuilder instead of just new Gson because this allows to print nicely object after object instead of everything in one line
//        https://stackoverflow.com/questions/4105795/pretty-print-json-in-java
        Gson gsonParser = new GsonBuilder().create();
        String jsonString = gsonParser.toJson(carList);
        return jsonString;
    }

    public Car fromJson(String carJson){
        Gson gsonParser = new Gson();
        Car car = gsonParser.fromJson(carJson, Car.class);
        return car;
    }

    public List<Car> jsonToCarList(String carListJson){
        Gson gsonParser = new Gson();
        Type carListType = new TypeToken<List<Car>>(){}.getType();
        List<Car> carList = gsonParser.fromJson(carListJson, carListType);
        return carList;
    }
}
