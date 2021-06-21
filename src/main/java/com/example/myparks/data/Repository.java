package com.example.myparks.data;


import android.util.Log;


import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myparks.controller.AppController;
import com.example.myparks.modle.Activities;
import com.example.myparks.modle.EntranceFees;
import com.example.myparks.modle.Images;
import com.example.myparks.modle.OperatingHours;
import com.example.myparks.modle.Park;
import com.example.myparks.modle.StandardHours;
import com.example.myparks.modle.Topics;
import com.example.myparks.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Repository {
    static List<Park> parkList = new ArrayList<>();

    public static void getParks(final AsyncResponse callback , String stateCode){
        String url = Util.getParksUrl(stateCode);
        Log.d("URL", "getParks: " + url);
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, url,null, response ->{

                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Park park= new Park();
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            // get the id of the object
                            park.setId(jsonObject.getString("id"));
                            park.setFullName(jsonObject.getString("fullName"));
                            park.setLatitude(jsonObject.getString("latitude"));
                            park.setLongitude(jsonObject.getString("longitude"));
                            park.setParkCode(jsonObject.getString("parkCode"));
                            park.setStates(jsonObject.getString("states"));


                            //park.setDescription(jsonObject.getString("description"));



                            // get the images array
                            JSONArray imageList = jsonObject.getJSONArray("images");
                            List<Images> list = new ArrayList<>();
                            for (int j = 0; j <imageList.length() ; j++) {
                                // get the object from the URl
                                Images images = new Images();
                                images.setCredit(imageList.getJSONObject(j).getString("credit"));
                                //images.setAltText(imageList.getJSONObject(j).getString("altText"));
                                //images.setCaption(imageList.getJSONObject(j).getString("caption"));
                                images.setTitle(imageList.getJSONObject(j).getString("title"));
                                images.setUrl(imageList.getJSONObject(j).getString("url"));

                                list.add(images);
                            }

                            park.setImages(list);

                            park.setWeatherInfo(jsonObject.getString("weatherInfo"));
                            park.setName(jsonObject.getString("name"));
                            park.setDesignation(jsonObject.getString("designation"));

                            park.setDescription(jsonObject.getString("description"));


                            // set up Activities / get
                            JSONArray acriviryArray = jsonObject.getJSONArray("activities");
                            List<Activities> activitiesList = new ArrayList<>();
                            for (int j = 0; j < acriviryArray.length(); j++) {
                                Activities activities = new Activities();
                                activities.setId(acriviryArray.getJSONObject(j).getString("id"));
                                activities.setName(acriviryArray.getJSONObject(j).getString("name"));
                                activitiesList.add(activities);

                            }
                            park.setActivities(activitiesList);

                            // set up Topics / get
                            JSONArray topicsArray = jsonObject.getJSONArray("topics");
                            List<Topics> topicsList = new ArrayList<>();
                            for (int j = 0; j < topicsArray.length(); j++) {
                                Topics topics = new Topics();
                                topics.setId(topicsArray.getJSONObject(j).getString("id"));
                                topics.setName(topicsArray.getJSONObject(j).getString("name"));
                                topicsList.add(topics);
                            }
                            park.setTopics(topicsList);


                            // set up topics // get
                            JSONArray feeArray = jsonObject.getJSONArray("entranceFees");
                            List<EntranceFees> feesArrayList = new ArrayList<>();
                            for (int k = 0; k < feeArray.length(); k++) {
                                EntranceFees entranceFees = new EntranceFees();
                                entranceFees.setCost(feeArray.getJSONObject(k).getString("cost"));
                                entranceFees.setDescription(feeArray.getJSONObject(k).getString("description"));
                                entranceFees.setTitle(feeArray.getJSONObject(k).getString("title"));
                                feesArrayList.add(entranceFees);
                            }
                            park.setEntranceFees(feesArrayList);

                            // set up operatingHours / get

                            JSONArray opHours = jsonObject.getJSONArray("operatingHours");
                            List<OperatingHours> operatingHoursList = new ArrayList<>();
                            for (int p = 0; p < opHours.length(); p++) {
                                OperatingHours op = new OperatingHours();

                                op.setDescription(opHours.getJSONObject(p).getString("description"));
                                op.setName(opHours.getJSONObject(p).getString("name"));

                                StandardHours standardHours = new StandardHours();
                                JSONObject hours = opHours.getJSONObject(p).getJSONObject("standardHours");

                                standardHours.setWednesday(hours.getString("wednesday"));                                standardHours.setWednesday(hours.getString("wednesday"));
                                standardHours.setMonday(hours.getString("monday"));
                                standardHours.setThursday(hours.getString("thursday"));
                                standardHours.setSunday(hours.getString("sunday"));
                                standardHours.setFriday(hours.getString("friday"));
                                standardHours.setSaturday(hours.getString("saturday"));
                                standardHours.setTuesday(hours.getString("tuesday"));

                                // set the standardHours in op
                                op.setStandardHours(standardHours);
                                operatingHoursList.add(op);
                            }
                            park.setOperatingHours(operatingHoursList);



                            // set up directionInfo // get
                            park.setDirectionsInfo(jsonObject.getString("directionsInfo"));

                            // set up WeatherInfo // get
                            park.setWeatherInfo(jsonObject.getString("weatherInfo"));





                            parkList.add(park);

                            Log.d("TAG", "getParks: " +jsonArray.getJSONObject(i));
                            Log.d("TAG", "getParks2: " +park.getFullName());


                        }
                        if (null != callback){ callback.processPark(parkList);}
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

}
