package com.cloudaping.cloudaping_android;

/**
 * Created by reggie on 21/03/18.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String CART = "Shopping_Cart";
    Session session;
    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveShoppingCart(Context context, List<ShoppingCartBean> shoppingcart) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonShoppingCart = gson.toJson(shoppingcart);

        editor.putString(CART, jsonShoppingCart);

        editor.commit();
    }

    public void addShoppingCart(Context context, ShoppingCartBean product) {
        List<ShoppingCartBean> carts = getShoppingCart(context);
        if (carts == null)
            carts = new ArrayList<ShoppingCartBean>();
        carts.add(product);
        saveShoppingCart(context, carts);
    }

    public void removeShoppingCart(Context context, int index) {
        ArrayList<ShoppingCartBean> carts = getShoppingCart(context);
        if (carts != null) {
            carts.remove(index);
            saveShoppingCart(context, carts);
        }else{
            ToastUtil.showL(context,"sssss");
        }
    }

    public void editShoppingCart(Context context, int index,int count) {
        ArrayList<ShoppingCartBean> carts = getShoppingCart(context);
        if (carts != null) {
            carts.get(index).setCount(count);
            saveShoppingCart(context, carts);
        }else{
            ToastUtil.showL(context,"sssss");
        }
    }

    public ArrayList<ShoppingCartBean> getShoppingCart(Context context) {
        SharedPreferences settings;
        List<ShoppingCartBean> carts=new ArrayList<ShoppingCartBean>();
        session=new Session(context);

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        if (settings.contains(CART)) {
            String jsonFavorites = settings.getString(CART, null);
            Gson gson = new Gson();
            ShoppingCartBean[] cartItems = gson.fromJson(jsonFavorites,
                    ShoppingCartBean[].class);

            for (ShoppingCartBean cart:cartItems){
                if (cart.getCustomerID().equals(session.getCustomerID())){
                    carts.add(cart);
                }
            }
        } else
            return null;

        return (ArrayList<ShoppingCartBean>) carts;
    }
}
