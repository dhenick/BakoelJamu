package id.littlequery.bakoeljamu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<Product> cartItems;
    private final CartFragment cartFragment;
    private final MainActivity mainActivity;
    private final CartManager cartManager;

    public CartAdapter(List<Product> cartItems, CartFragment cartFragment, MainActivity mainActivity) {
        this.cartItems = cartItems;
        this.cartFragment = cartFragment;
        this.mainActivity = mainActivity;
        this.cartManager = new CartManager(cartFragment.requireContext());
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItems.get(position);
        holder.textViewName.setText(product.getName());
        holder.textViewPrice.setText("Rp " + product.getPrice());

        holder.textViewQuantity.setText(String.valueOf(product.getQuantity()));
        updateTotalPrice(holder, product);

        holder.buttonDecrease.setOnClickListener(v -> {
            if (product.getQuantity() > 1) {
                product.setQuantity(product.getQuantity() - 1);
                holder.textViewQuantity.setText(String.valueOf(product.getQuantity()));
                updateTotalPrice(holder, product);
                cartFragment.updateGrandTotal();
                cartManager.updateProductQuantity(product);
            } else {
                Toast.makeText(holder.itemView.getContext(), "Quantity can't be less than 1", Toast.LENGTH_SHORT).show();
            }
        });

        holder.buttonIncrease.setOnClickListener(v -> {
            product.setQuantity(product.getQuantity() + 1);
            holder.textViewQuantity.setText(String.valueOf(product.getQuantity()));
            updateTotalPrice(holder, product);
            cartFragment.updateGrandTotal();
            cartManager.updateProductQuantity(product);
        });

        holder.buttonDelete.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());

            cartManager.removeProductFromCart(product);

            cartFragment.updateGrandTotal();
            mainActivity.updateCartBadge(cartItems.size());
        });
    }

    private void updateTotalPrice(CartViewHolder holder, Product product) {
        double totalPrice = product.getPrice() * product.getQuantity();
        holder.textViewTotalPricePerItem.setText("Total: Rp " + totalPrice);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPrice, textViewQuantity, textViewTotalPricePerItem;
        MaterialButton buttonDecrease, buttonIncrease, buttonDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewCartItemName);
            textViewPrice = itemView.findViewById(R.id.textViewCartItemPrice);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewTotalPricePerItem = itemView.findViewById(R.id.textViewTotalPricePerItem);
            buttonDecrease = itemView.findViewById(R.id.buttonDecrease);
            buttonIncrease = itemView.findViewById(R.id.buttonIncrease);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
