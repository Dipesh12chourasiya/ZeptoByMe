package com.example.zeptobyme;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.zeptobyme.models.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private static final String PREF_NAME = "order_pref";
    private static final String KEY_ORDERS = "orders";

    public static void saveOrders(Context context, List<Order> orders) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(orders);
        editor.putString(KEY_ORDERS, json);
        editor.apply();
    }

    public static List<Order> loadOrders(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_ORDERS, null);
        if (json == null) return new ArrayList<>();
        return new Gson().fromJson(json, new TypeToken<List<Order>>() {}.getType());
    }
}

