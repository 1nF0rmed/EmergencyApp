package com.example.a1nf0rmed.myapplication;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.places.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class PlaceData {
    public String name;
    public String phone;
    public String map_url;
    public String rating;
    public String place_id;

    PlaceData() {
        name = phone = map_url = rating = place_id = "";
    }

    PlaceData(String _name, String _phone, String _map_url, String _rating, String _place_id){
        name = _name;
        phone = _phone;
        map_url = _map_url;
        rating = _rating;
        place_id = _place_id;
    }
}

public class locationAPI extends Application {
    private String apiKey;
    private JsonObjectRequest jsonObjectRequest;
    JSONObject resp;


    locationAPI(String key) {
        apiKey = key;
    }

    public String getCurrentLocatio() {
        // TODO Get current location of the device and return long and lat concatination
        return "Hello there you scum!";
    }
/*
    public PlaceData extractDataPhase1(JSONObject _resp) {

    }*/

    public PlaceData getPlaceSearchResult(String query) {
        PlaceData pobj = new PlaceData(); // Place data object to store place information



        // 1. Request Place Search API
        String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input="+query+"&inputtype=textquery&fields=place_id,formatted_address,name,opening_hours,rating&locationbias=circle:4000@12.941018981141301,77.56576473155496&key="+apiKey;

        jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Get place basic information and place_id
                        resp = response;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error when no data is found

                    }
                });

        try {
            // 1.a Parse Place Search API response
            JSONArray candidates = resp.getJSONArray("candidates");

            for(int i=0;i<candidates.length();i++) {
                pobj.name = candidates.getJSONObject(i).getString("name");
                pobj.rating = candidates.getJSONObject(i).getString("rating");
                pobj.place_id = candidates.getJSONObject(i).getString("place_id");
            }

        } catch (JSONException e) {
            Log.e("locationAPI", "Unable to parse the JSON file.");
        } catch(Exception e) {
            Log.e("lcoationAPI", "Error Stack: "+e);
        }

        // Sleep for 2 seconds as the Google API will get overused
        // TODO Sleep method for the search request


        // 2. Send Request for detailed information
        try {

            String url2 = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+pobj.place_id+"&fields=url,name,rating,formatted_phone_number&key="+apiKey;
            if(!pobj.place_id.equals("")) {
                jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Get place basic information and place_id
                                resp = response;
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error when no data is found

                            }
                        });

                try {
                    // 1.a Parse Place Detail Search API response

                    pobj.map_url = resp.getJSONObject("result").getString("url");
                    pobj.phone = resp.getJSONObject("result").getString("formatted_phone_number");

                } catch (JSONException e) {
                    Log.e("locationAPI", "Unable to parse the JSON file.");
                } catch(Exception e) {
                    Log.e("lcoationAPI", "Error Stack: "+e);
                }
            }
        } catch(Exception e) {
            Log.e("locationAPI", "Phase 2 Places detail search error. Log: "+e);
        }

        return pobj; // Return new place information
    }
}
