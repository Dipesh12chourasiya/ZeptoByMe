package com.example.zeptobyme;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void saveCartToPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(cartItems); // convert list to JSON
        editor.putString("cart_items", json);
        editor.apply();
    }

    public void loadCartFromPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("cart_items", null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Product>>() {}.getType();
            cartItems = gson.fromJson(json, type); // deserialize JSON to list
        }
    }

    public void addToCart(Product product) {
        cartItems.add(product);
    }

    public void removeFromCart(Product product) {
        cartItems.remove(product);
    }

    public List<Product> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public void clearCart() {
        cartItems.clear();
    }

}
