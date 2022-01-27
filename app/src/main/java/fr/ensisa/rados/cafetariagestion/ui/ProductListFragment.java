package fr.ensisa.rados.cafetariagestion.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.ensisa.rados.cafetariagestion.R;
import fr.ensisa.rados.cafetariagestion.database.AppDatabase;
import fr.ensisa.rados.cafetariagestion.database.FeedDatabase;
import fr.ensisa.rados.cafetariagestion.databinding.ProductItemBinding;
import fr.ensisa.rados.cafetariagestion.databinding.ProductListFragmentBinding;
import fr.ensisa.rados.cafetariagestion.model.Product;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.ensisa.rados.cafetariagestion.R;
import fr.ensisa.rados.cafetariagestion.database.AppDatabase;
import fr.ensisa.rados.cafetariagestion.database.FeedDatabase;
import fr.ensisa.rados.cafetariagestion.databinding.ProductItemBinding;
import fr.ensisa.rados.cafetariagestion.model.Product;

public class ProductListFragment extends Fragment {

    private ProductListViewModel mViewModel;
    private ProductListAdapter adapter;

    public static ProductListFragment newInstance() {
        return new ProductListFragment();
    }


    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.product_list_fragment, container, false);
        root.findViewById(R.id.add).setOnClickListener((view) -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_productListFragment_to_productFragment);
        });
        RecyclerView list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(root.getContext(), RecyclerView.VERTICAL, false));

        adapter = new ProductListAdapter();
        list.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        mViewModel.setProductDao(AppDatabase.getInstance().productDao());
        mViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.setProducts(products);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.products_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.populate:
                return doPopulate();
        }
        return onContextItemSelected(item);
    }

    private boolean doPopulate() {
        FeedDatabase feeder = new FeedDatabase();
        feeder.feed();
        return true;
    }

    private class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductHolder> {

        private List<Product> products;


        private class ProductHolder extends RecyclerView.ViewHolder {

            ProductItemBinding binding;

            public ProductHolder(ProductItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ProductItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_item, parent, false);
            return new ProductHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
            Product product = products.get(position);
            holder.binding.setProduct(product);
        }


        @Override
        public int getItemCount() {
            return products != null ? products.size() : 0;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
            notifyDataSetChanged();
        }

    }
}