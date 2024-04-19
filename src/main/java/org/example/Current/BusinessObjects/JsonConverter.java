package org.example.Current.BusinessObjects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.Current.DTOs.Car;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

    public List<Car> JsonToCarList(String carListJson){
        Gson gsonParser = new Gson();
        Type carListType = new TypeToken<List<Car>>(){}.getType();
        List<Car> carList = gsonParser.fromJson(carListJson, carListType);
        return carList;
    }

    /**
     * Main Author: Logan Rushe
     */
    public List<String> JsonToStringList(String stringListJson){
        Gson gsonParser = new Gson();
        List<String> stringList = new ArrayList<>();
        // Type stringListType = new TypeToken<List<String>>(){}.getType();
        stringList = gsonParser.fromJson(stringListJson, stringList.getClass());
        return stringList;
    }

    /**
     * Main Author: Logan Rushe
     */
    public  String stringListToJson(List<String> stringList){
//        Using gsonBuilder instead of just new Gson because this allows to print nicely object after object instead of everything in one line
//        https://stackoverflow.com/questions/4105795/pretty-print-json-in-java
        Gson gsonParser = new GsonBuilder().create();
        String jsonString = gsonParser.toJson(stringList);
        return jsonString;
    }
}
