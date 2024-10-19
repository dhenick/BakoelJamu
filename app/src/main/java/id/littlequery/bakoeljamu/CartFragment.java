package id.littlequery.bakoeljamu;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Product> cartItems;
    private CartManager cartManager;
    private TextView textViewEmptyCart;
    private TextView textViewGrandTotal;
    private MaterialButton buttonCheckout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewCart);
        textViewEmptyCart = view.findViewById(R.id.textViewEmptyCart);
        textViewGrandTotal = view.findViewById(R.id.textViewTotalPrice);
        buttonCheckout = view.findViewById(R.id.buttonCheckout);

        // Initialize CartManager
        cartManager = new CartManager(requireContext());

        // Get items from the cart
        cartItems = cartManager.getCartItems();

        // Check if cart is empty
        if (cartItems.isEmpty()) {
            showEmptyCartMessage();
        } else {
            // Initialize CartAdapter
            cartAdapter = new CartAdapter(cartItems, this, (MainActivity) requireActivity());
            recyclerView.setAdapter(cartAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            updateGrandTotal();
        }

        buttonCheckout.setOnClickListener(v -> completeOrder());

        ((MainActivity) requireActivity()).updateCartBadge(cartItems.size());

        return view;
    }

    private void showEmptyCartMessage() {
        textViewEmptyCart.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        textViewGrandTotal.setVisibility(View.GONE);
        buttonCheckout.setVisibility(View.GONE);
    }

    public void updateGrandTotal() {
        double grandTotal = 0;
        for (Product product : cartItems) {
            grandTotal += product.getPrice() * product.getQuantity();
        }
        textViewGrandTotal.setText("Total Harga: Rp " + grandTotal);
        textViewGrandTotal.setVisibility(grandTotal > 0 ? View.VISIBLE : View.GONE);
        buttonCheckout.setVisibility(grandTotal > 0 ? View.VISIBLE : View.GONE);
    }

    public void removeProductFromCart(Product product) {
        cartItems.remove(product);
        cartManager.removeProductFromCart(product);
        cartAdapter.notifyDataSetChanged();

        if (cartItems.isEmpty()) {
            showEmptyCartMessage();
        }

        updateGrandTotal();
        ((MainActivity) requireActivity()).updateCartBadge(cartItems.size());
    }

    private void completeOrder() {
        Snackbar.make(requireView(), "Pesanan Sudah di Selesaikan", Snackbar.LENGTH_SHORT).show();

        cartManager.clearCart();
        cartItems.clear();
        cartAdapter.notifyDataSetChanged();

        showEmptyCartMessage();

        ((MainActivity) requireActivity()).updateCartBadge(cartItems.size());

        updateGrandTotal();
    }
}
