package com.example.desafioalphahurb.web;

import com.example.desafioalphahurb.model.Hotel;
import com.example.desafioalphahurb.model.Pacote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DadosPacote {

    public Pacote create(JSONObject jsonPost) {
        Pacote pacote = null;
        try {
            //PERCORRE POR TODAS AS INFORMAÇÕES DA API, QUE SERÁ CARREGADO NO PACOTE 'task' CLASSE 'PacoteAsyncTask'
            String name = jsonPost.getString("name");
            String city = jsonPost.getJSONObject("address").getString("city");
            String state = jsonPost.getJSONObject("address").getString("state");
            String price = jsonPost.getJSONObject("price").getString("currentPrice");

            //String image = jsonPost.getJSONObject("gallery").getString("url");
            //JSONObject image = jsonPost.getJSONObject("gallery").getJSONArray("url").getJSONObject(0);
            //String amenities = jsonPost.getJSONObject("gallery").getString("url");
            pacote = new Pacote(name, city, state, price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pacote;
    }
}