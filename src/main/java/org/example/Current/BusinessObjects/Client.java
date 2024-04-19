package org.example.Current.BusinessObjects;

import org.example.Current.DTOs.Car;

import java.util.List;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Main Author: Logan Rushe
 */
public class Client {
    public static void main(String[] args) {
        Client client = new Client();
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
            System.out.printf("Valid commands are: \"%s <integer>\", \"%s\", \"%s <integer>\", \"%s\", \"%s [fileName]\", \"Exit\"\n",
                    commands.DisplayCarById, commands.DisplayAllCars, commands.DeleteCarById, commands.GetImagesList, commands.GetImage);
            System.out.println("Please enter a command: ");
            String userRequest = consoleInput.nextLine();
            JsonConverter jsonConverter = new JsonConverter();
            DataInputStream dataInputStream = null;

            while(true) {
                // send the command to the server on the socket
                out.println(userRequest);      // write the request to socket along with a newline terminator (required)

                // process the answer returned by the server
                /**
                 * Main Author: Logan Rushe
                 */
                if (userRequest.startsWith(commands.DisplayCarById)) // if user asked for "display car by id", we expect the server to return a car
                {
                    String response = in.readLine(); // Wait for response
                    if(response.contains("}")) { // Server returned a JSON String
                        Car car = jsonConverter.fromJson(response); // Convert JSON String to car Object
                        System.out.println("Car received!");
                        // Display the car in table format
                        System.out.printf("%-5s %-12s %-20s %-10s %s %9s \n", "Id", "Model", "Brand", "Colour", "Production Year", "Price");
                        displayCar(car);
                    } else { // Car was not found
                        System.out.println("Client: Response from server after \"display car by id\" request: " + response);
                    }
                }
                /**
                 * Main Author: Ida Tehlarova
                 * <p>
                 * Other contributors: Logan Rushe
                 */
                else if (userRequest.startsWith(commands.DisplayAllCars)) {
                    String jsonString = in.readLine();  // wait for response from server
                    List<Car> list = jsonConverter.JsonToCarList(jsonString); // Convert JSON String to car Object

                    System.out.println("Displaying all cars!");
                    System.out.printf("%-5s %-12s %-20s %-10s %s %9s \n", "Id", "Model", "Brand", "Colour", "Production Year", "Price");
                    for (Car car : list) {
                        displayCar(car);
                    }
                }
                /**
                 * Main Author: Ida Tehlarova
                 */
                else if(userRequest.startsWith(commands.DeleteCarById)){
                    String response = in.readLine();
                    System.out.println(response);
                }
                /**
                 * Main Author: Logan Rushe
                 */
                else if (userRequest.startsWith(commands.GetImagesList)) // if the user has entered the "get images list" command
                {
                    String response = in.readLine();   // Wait for response
                    System.out.println("Client: Response from server: " + response + "");
                    List<String> imagesList = jsonConverter.JsonToStringList(response);
                    System.out.println("To select an image to download, type \"get image [imageName].jpg\"\nAvailable Images: ");
                    for (String image : imagesList) { System.out.println(image); }
                }
                /**
                 * Main Author: Logan Rushe
                 */
                else if (userRequest.startsWith(commands.GetImage)) // if the user has entered the "get image" command
                {
                    String response = in.readLine();
                    if (response.equals("ERROR: File not found")) {
                        System.out.println("Client: Response from server: " + response);
                    } else {
                        System.out.println("Server: " + response);
                        dataInputStream = new DataInputStream(socket.getInputStream());

                        String[] userCommands = userRequest.split(" ");
                        String fileName = "Received images/" + userCommands[2];
                        int bytes = 0;
                        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

                        // read the size of the file in bytes (the file length)
                        long size = dataInputStream.readLong();
                        System.out.println("Server: file size in bytes = " + size);

                        // create a buffer to receive the incoming bytes from the socket
                        byte[] buffer = new byte[4 * 1024];         // 4 kilobyte buffer

                        System.out.println("Client: Bytes remaining to be read from socket: ");

                        // next, read the raw bytes in chunks (buffer size) that make up the image file
                        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {

                            // write the buffer data into the local file
                            fileOutputStream.write(buffer, 0, bytes);

                            // reduce the 'size' by the number of bytes read in.
                            size = size - bytes;
                            System.out.print(size + ", ");
                        }

                        System.out.println("Image Downloaded!");
                        System.out.printf("Look in the Received images folder to see the downloaded file: %s\n", fileName);

                        fileOutputStream.close();
                    }
                }
                else if (userRequest.startsWith("Exit")) // if the user has entered the "Exit" command
                {
                    String response = in.readLine();   // Wait for response
                    System.out.println("Client: Response from server: " + response);
                    break;  // break out of while loop, client will exit.
                }
                else {
                    System.out.println("Command unknown. Try again.");
                    System.out.println("Server: " + in.readLine());
                }

                consoleInput = new Scanner(System.in);
                System.out.printf("\nValid commands are: \"%s <integer>\", \"%s\", \"%s <integer>\", \"%s\", \"%s [fileName]\", \"Exit\"\n",
                        commands.DisplayCarById, commands.DisplayAllCars, commands.DeleteCarById, commands.GetImagesList, commands.GetImage);
                System.out.println("Please enter a command: ");
                userRequest = consoleInput.nextLine();
            }
            if (dataInputStream != null) {
                dataInputStream.close();
            }
        } catch (IOException e) {
            System.out.println("Client: IOException: " + e);
        }
        // sockets and streams are closed automatically due to try-with-resources
        // so no finally block required here.

        System.out.println("Exiting client, but server may still be running.");
    }
}

