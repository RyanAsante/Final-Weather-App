package com.audible;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;



public class WeatherAppUserData {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        Set<String> cities = new HashSet();
        cities.add("Boston");
        cities.add("Newark");
        cities.add("Los Angeles"); 
        String userLocation;
        String USER_MEASUREMENT = "us";
        String API_KEY = "YMX5CP95D2LLVM4P4ABH5WNN4";
        String apiEndPoint = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

        String method = "GET";
        do { 
            System.out.println("Please enter a valid Audible office: ");
        userLocation = scanner.nextLine();
            
        } while (!cities.contains(userLocation));
        

        
         //System.out.println("Please enter the measurement you prefer (us, uk, metric, or base): ");
         //userMeasurement = scanner.nextLine();

         //System.out.println("Please enter the date: ");
         //userDate = scanner.nextLine();

        

        StringBuilder urlLink = new StringBuilder();
        urlLink.append(apiEndPoint);
        urlLink.append(userLocation);

        StringBuilder keyParameters = new StringBuilder();
        keyParameters.append("&").append("unitGroup=").append(USER_MEASUREMENT);
        keyParameters.append("&").append("key=").append(API_KEY);

        if ("GET".equals(method)) {
            urlLink.append("?").append(keyParameters);


        }


        try {
            URL url = new URL(urlLink.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            int responseNum = connection.getResponseCode();
            if (responseNum == 200) {
                StringBuilder weatherDataResponse = new StringBuilder();
                Scanner scanner2 = new Scanner(connection.getInputStream());
                while (scanner2.hasNext()) {
                    weatherDataResponse.append(scanner2.nextLine());
                }

                JSONObject myObject = new JSONObject(weatherDataResponse.toString());

                String weatherDescription = myObject.getString("description");

                
                JSONObject myJsonMap = myObject.getJSONObject("currentConditions");
                JSONArray myJsonMap2 = myObject.getJSONArray("days");


                BigDecimal temp = (BigDecimal)myJsonMap.get("temp");
                BigDecimal rainprob = (BigDecimal)myJsonMap.get("precipprob");
                BigDecimal rain = (BigDecimal)myJsonMap.get("precip");
                BigDecimal humid = (BigDecimal)myJsonMap.get("humidity");
                BigDecimal feelLike = (BigDecimal)myJsonMap.get("feelslike");

                System.out.println("The weather forecast today: \n");
                System.out.println("Weather Description in " + userLocation + ": " + weatherDescription);
                System.out.println("The temperature in " + userLocation + " is currently: " + temp + "°");
                System.out.println("The temperature feels like: " + feelLike + "°");
                System.out.println("The chance of rain is: " + rainprob + " % ");
                System.out.println("Current Rainfall: " + rain + "\"");
                System.out.println("Current Humidity: " + humid + " % ");

                System.out.println("\nThe weather forecast for the next 7 days: \n ");

                for (int i = 2; i < 9; i++){

                JSONObject dayInfo = myJsonMap2.getJSONObject(i);
                BigDecimal dayTempMin = (BigDecimal)dayInfo.get("tempmin");
                BigDecimal dayTempMax = (BigDecimal)dayInfo.get("tempmax");
                System.out.println(dayTempMin + "°" + " / " + dayTempMax + "°");

                }
                




            }
        }
        catch(Exception e){
            System.out.println("Error: Could not connect to API. Please try again. \n Exception:"+e);

        }
    }

}


