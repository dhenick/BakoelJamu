package id.littlequery.bakoeljamu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewProducts);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        productList = new ArrayList<>();
        productList.add(new Product(1,"Temu Lawak", R.drawable.temu, 10000, "Jamu", "Temulawak (Curcuma zanthorrhiza) adalah tanaman herbal asli Indonesia yang kerap dimanfaatkan untuk mengobati berbagai masalah kesehatan, mulai dari kurang nafsu makan, gangguan lambung, sembelit, diare, demam, radang sendi, hingga gangguan fungsi hati. Tanaman ini masih kerabat dekat dengan kunyit",1));
        productList.add(new Product(2,"Beras Kencur", R.drawable.beras, 15000, "Jamu", "Beras kencur adalah minuman penyegar khas Jawa. Minuman ini juga digolongkan sebagai jamu karena memiliki khasiat meningkatkan nafsu makan. Beras kencur sangat populer karena memiliki rasa yang manis dan segar. Bahan utama beras kencur adalah beras dan kencur",1));
        productList.add(new Product(3,"Kunir Asem", R.drawable.kunir, 7500, "Jamu", "Manfaat minum kunyit asam bisa membantu tubuh untuk melawan peradangan akut dan oksidasi. Oleh karenanya, kunyit asam dapat menurunkan risiko penyakit jantung. Bila digunakan bersamaan dengan obat untuk mengelola kadar kolesterol, maka kandungan curcumin dapat menurunkan kadar kolesterol tertentu",1));
        productList.add(new Product(4,"Jamu Paitan", R.drawable.paitan, 20500, "Jamu", "Pahitan atau jamu pahitan adalah ramuan jamu yang terbuat dari bahan–bahan jamu dengan rasa pahit. Jamu pahitan dimanfaatkan untuk berbagai masalah kesehatan",1));
        productList.add(new Product(5,"Jamu Cabe Puyang", R.drawable.cabe, 20500, "Jamu", "Untuk membuat jamu ini bahan yang dipakai meliputi puyang ditambah dengan bahan lain seperti temuireng, temulawak, jahe, kudu, adas, pulosari, kunyit, merica, kedawung, keningar, asam jawa, dan temukunci. Sebagai pemanis digunakan gula merah, gula putih dan garam",1));
        productList.add(new Product(6,"Donat Kentang", R.drawable.donat, 22500, "Makanan", "Donat kentang adalah donat yang terbuat dari campuran tepung, gula, telur, ragi, dan diberi tambahan kentang. Lama proses donat kentang mengembang membutuhkan waktu, sebab adonan harus melalui pengistirahatan atau proofing terlebih dahulu supaya ragi bekerja",1));
        productList.add(new Product(7,"Kerupuk Gendar", R.drawable.gendar, 17000, "Makanan", "Legendar adalah penganan yang dibuat dari nasi yang dibentuk lempeng dikeringkan lalu digoreng. Biasanya untuk menambah kekenyalan kadang kala ditambahkan sedikit bleng, tetapi jika tidak menggunakan bleng bisa ditambahkan tepung tapioka agar adonan mentahnya menjadi kenyal dan padat",1));

        productAdapter = new ProductAdapter(getContext(), productList, this);
        recyclerView.setAdapter(productAdapter);

        return view;
    }

    @Override
    public void onProductClick(Product product) {
        ProductDetailFragment detailFragment = ProductDetailFragment.newInstance(product);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, detailFragment) // Use your fragment container ID
                .addToBackStack(null)
                .commit();
    }
}
