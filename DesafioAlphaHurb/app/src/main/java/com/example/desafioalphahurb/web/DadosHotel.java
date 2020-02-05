package com.example.desafioalphahurb.web;

import com.example.desafioalphahurb.model.Hotel;

import org.json.JSONException;
import org.json.JSONObject;

public class DadosHotel {

    public Hotel create(JSONObject jsonPost) {
        Hotel hotel = null;
        try {
            //PERCORRE POR TODAS AS INFORMAÇÕES DA API, QUE SERÁ CARREGADO NO PACOTE 'task' CLASSE 'HotelAsyncTask'
            String name = jsonPost.getString("name");
            String city = jsonPost.getJSONObject("address").getString("city");
            String state = jsonPost.getJSONObject("address").getString("state");
            String price = jsonPost.getJSONObject("price").getString("current_price");
            String description = jsonPost.getString("description");
            Integer stars = jsonPost.getInt("stars");
            String image = jsonPost.getString("image");
            String gallery = jsonPost.getString("image");
            String amenities = jsonPost.getJSONObject("featuredItem").getString("amenities");

            hotel = new Hotel(name, city, state, price, description, stars, image, gallery, amenities);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hotel;
    }
}
