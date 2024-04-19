package org.example.Ida.BusinessObjects;

import org.example.Ida.DTOs.Car;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import org.example.Ida.DAOs.JsonConverter;

/**
 * Main Author: Logan Rushe
 */
public class ClientMenu {
    public static void main(String[] args) {
        ClientMenu client = new ClientMenu();
        client.start();
    }

    public void displayCar(Car car) {
        System.out.printf("%-5d %-12s %-20s %-10s %-19d %5d \n",
                car.getId(),
                car.getModel(),
                car.getBrand(),
                car.getColour(),
                car.getProductionYear(),
                car.getPrice()
        ); // Display the car data in table format
    }

    public void start() {

        try (   // create socket to connect to the server
                Socket socket = new Socket("localhost", 8888);
                // get the socket's input and output streams, and wrap them in writer and readers
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            System.out.println("Client: The Client is running and has connected to the server");
            //ask user to enter a command
            Scanner consoleInput = new Scanner(System.in);
            ClientServerCommands commands = new ClientServerCommands();
            System.out.printf("Valid commands are: \"%s <integer>\",\"display all cars\",\"delete car by id <integer>\", \"quit\"\n", commands.DisplayCarById);
            System.out.println("Please enter a command: ");
            String userRequest = consoleInput.nextLine();
            JsonConverter jsonConverter = new JsonConverter();

            while (true) {
                // send the command to the server on the socket
                out.println(userRequest);      // write the request to socket along with a newline terminator (which is required)
                // out.flush();                      // flushing buffer NOT necessary as auto flush is set to true

                // process the answer returned by the server
                if (userRequest.startsWith(commands.DisplayCarById)) // if user asked for "display car by id", we expect the server to return a car (in milliseconds)
                {
                    String jsonString = in.readLine();

                    if (jsonString.contains("}")) { // Server returned a JSON String
                        Car car = jsonConverter.fromJson(jsonString); // Convert JSON String to car Object
                        System.out.println("Car received!");
                        // Display the car in table format
                        System.out.printf("%-5s %-12s %-20s %-10s %s %9s \n", "Id", "Model", "Brand", "Colour", "Production Year", "Price");
                        displayCar(car);
                    } else { // Car was not found
                        System.out.println("Client message: Response from server after \"display car by id\" request: " + jsonString);
                    }

                    //Ida
                } else if (userRequest.startsWith(commands.DisplayAllCars)) {
                    String jsonString = in.readLine();  // wait for response from server
                    List<Car> list = jsonConverter.jsonToCarList(jsonString); // Convert JSON String to car Object

                    System.out.println("Displaying all cars!");

                    for (Car car : list) {
                        displayCar(car);
                        Car car2 = jsonConverter.fromJson(jsonString);
                        System.out.println("Car received!");
                    }
                } else if(userRequest.startsWith(commands.DeleteCarById)){
                    String boobies = in.readLine();
                    System.out.println(boobies);
                }

                else if (userRequest.startsWith("quit")) // if the user has entered the "quit" command
                {
                    String response = in.readLine();   // wait for response -
                    System.out.println("Client message: Response from server: \"" + response + "\"");
                    break;  // break out of while loop, client will exit.
                } else {
                    System.out.println("Command unknown. Try again.");
                }

                consoleInput = new Scanner(System.in);
                System.out.println("Valid commands are: \"time\" to get time, or \"echo <message>\" to get message echoed back, \"quit\"");
                System.out.println("Please enter a command: ");
                userRequest = consoleInput.nextLine();
            }
        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
        // sockets and streams are closed automatically due to try-with-resources
        // so no finally block required here.

        System.out.println("Exiting client, but server may still be running.");
    }
}

//  LocalTime time = LocalTime.parse(timeString); // Parse String -> convert to LocalTime object if required LocalTime.parse(timeString); // Parse timeString -> convert to LocalTime object if required