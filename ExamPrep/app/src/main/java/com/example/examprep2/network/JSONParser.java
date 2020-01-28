package com.example.examprep2.network;

import com.example.examprep2.AdaugaJucatorActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JSONParser {

    public static HttpResponse parseJson(String json) {
        if (json == null) {
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            List<Item> goalkeeper = getItemListFromJson(jsonObject
                    .getJSONArray("goalkeeper"));
            List<Item> center = getItemListFromJson(jsonObject
                    .getJSONArray("center"));
            List<Item> inter = getItemListFromJson(jsonObject
                    .getJSONArray("inter"));
            List<Item> winger = getItemListFromJson(jsonObject
                    .getJSONArray("winger"));
            return new HttpResponse(goalkeeper, center, inter, winger);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Item> getItemListFromJson(JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        }
        List<Item> results = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Item item = getItemFromJson(array
                    .getJSONObject(i));
            if (item != null) {
                results.add(item);
            }
        }
        return results;
    }

    private static Item getItemFromJson(JSONObject object) throws JSONException {
        if (object == null) {
            return null;
        }
        String team = object.getString("team");
        String extraInfo = object.getString("extraInfo");
        PlayerInfo player = getPlayerInfoFromJson(object
                .getJSONObject("playerInfo"));
        return new Item(team, extraInfo, player);
    }

    private static PlayerInfo getPlayerInfoFromJson(JSONObject object) throws JSONException {
        if (object == null) {
            return null;
        }

        String name = object.getString("name");
        Date birthday = null;
        try {
            birthday = new SimpleDateFormat(AdaugaJucatorActivity.DATE_FORMAT,
                    Locale.US).parse(object
                    .getString("birthday"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String favHand = object
                .getString("favoriteHand");
        return new PlayerInfo(name, birthday, favHand);
    }
}
