package id.littlequery.bakoeljamu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.button.MaterialButton;

import android.widget.ImageView;
import android.widget.Toast;

public class ProductDetailFragment extends Fragment {

    private static final String ARG_PRODUCT = "product";
    private Product product;
    private CartManager cartManager;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment newInstance(Product product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (Product) getArguments().getSerializable(ARG_PRODUCT);
        }
        cartManager = new CartManager(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        ImageView imageView = view.findViewById(R.id.imageViewProductDetail);
        MaterialTextView textViewName = view.findViewById(R.id.textViewProductNameDetail);
        MaterialTextView textViewPrice = view.findViewById(R.id.textViewProductPriceDetail);
        MaterialTextView textViewDescription = view.findViewById(R.id.textViewProductDescription);
        MaterialButton buttonAddToCart = view.findViewById(R.id.buttonAddToCart);
        MaterialButton buttonShareWhatsApp = view.findViewById(R.id.buttonShareWhatsApp);
        MaterialButton buttonShareSMS = view.findViewById(R.id.buttonShareSMS);
        MaterialButton buttonBack = view.findViewById(R.id.buttonBack);

        imageView.setImageResource(product.getImageResId());
        textViewName.setText(product.getName());
        textViewPrice.setText("Rp " + product.getPrice());
        textViewDescription.setText(product.getDescription());

        buttonAddToCart.setOnClickListener(v -> {
            cartManager.addProductToCart(product);

            Snackbar.make(view, "Barang berhasil dimasukkan ke keranjang", Snackbar.LENGTH_LONG).show();

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).updateCartBadge(cartManager.getCartItemCount());
            }
        });

        buttonShareWhatsApp.setOnClickListener(v -> {
            shareProduct("WhatsApp");
        });

        buttonShareSMS.setOnClickListener(v -> {
            shareProduct("SMS");
        });

        buttonBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void shareProduct(String type) {
        String message = "Check out this product: " + product.getName() + " - Rp " + product.getPrice();
        Intent intent = null;
        boolean isAppInstalled = false;

        if (type.equals("WhatsApp")) {
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, message);
            intent.setPackage("com.whatsapp");

            isAppInstalled = isAppInstalled("com.whatsapp");
            if (!isAppInstalled) {
                showMessage("WhatsApp is not installed.");
                return;
            }

        } else if (type.equals("SMS")) {
            intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:")); // only SMS apps should handle this
            intent.putExtra("sms_body", message);

            isAppInstalled = isAppInstalled("com.android.mms");
            if (!isAppInstalled) {
                showMessage("No SMS app is installed.");
                return;
            }
        }

        if (isAppInstalled) {
            startActivity(intent);
        }
    }

    private boolean isAppInstalled(String packageName) {
        try {
            requireContext().getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void showMessage(String message) {
        View view = getView();
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        } else {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
