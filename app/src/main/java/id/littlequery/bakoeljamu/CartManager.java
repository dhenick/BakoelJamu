package id.littlequery.bakoeljamu;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static final String PREF_NAME = "cart_pref";
    private static final String KEY_CART_ITEMS = "cart_items";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public CartManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void addProductToCart(Product product) {
        List<Product> cartItems = getCartItems();
        for (Product cartItem : cartItems) {
            if (cartItem.getId() == product.getId()) {
                cartItem.setQuantity(cartItem.getQuantity() + product.getQuantity());
                saveCartItems(cartItems);
                return;
            }
        }
        cartItems.add(product);
        saveCartItems(cartItems);
    }

    public void updateProductQuantity(Product product) {
        List<Product> cartItems = getCartItems();
        for (Product cartItem : cartItems) {
            if (cartItem.getId() == product.getId()) {
                cartItem.setQuantity(product.getQuantity());
                break;
            }
        }
        saveCartItems(cartItems);
    }

    public void removeProductFromCart(Product product) {
        List<Product> cartItems = getCartItems();
        cartItems.removeIf(cartItem -> cartItem.getId() == product.getId());
        saveCartItems(cartItems);
    }

    public List<Product> getCartItems() {
        String json = sharedPreferences.getString(KEY_CART_ITEMS, null);
        Type type = new TypeToken<List<Product>>() {}.getType();
        List<Product> cartItems = gson.fromJson(json, type);

        return cartItems != null ? cartItems : new ArrayList<>();
    }

    public int getCartItemCount() {
        return getCartItems().size();
    }

    public void clearCart() {
        sharedPreferences.edit().remove(KEY_CART_ITEMS).apply();
    }

    private void saveCartItems(List<Product> cartItems) {
        String json = gson.toJson(cartItems);
        sharedPreferences.edit().putString(KEY_CART_ITEMS, json).apply();
    }
}
