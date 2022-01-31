package fr.ensisa.rados.cafetariagestion.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import fr.ensisa.rados.cafetariagestion.R;
import fr.ensisa.rados.cafetariagestion.database.AppDatabase;
import fr.ensisa.rados.cafetariagestion.databinding.ProductFragmentBinding;
import fr.ensisa.rados.cafetariagestion.model.Product;
import fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel.ProductViewModel;

public class ProductFragment extends Fragment implements DatePickerFragment.OnChangedDate{

    static private final String TAG = "ProductFragment";
    private static final int EXPIRATIONDATE = 1;

    private ProductViewModel mViewModel;
    private ProductFragmentBinding binding;
    private long id;


    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() !=null)
        {
            id = ProductFragmentArgs.fromBundle(getArguments()).getId();
        } else { id=-1; }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.product_fragment, container, false);
        binding.setLifecycleOwner(this);
        binding.setChangeDate(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(binding.getProduct().getExpirationDate());
                fragment.setTargetFragment(ProductFragment.this, EXPIRATIONDATE);
                FragmentManager manager = getParentFragmentManager();
                fragment.show(manager, "Expiration Date");
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        mViewModel.setProductDao(AppDatabase.getInstance().productDao());
        mViewModel.getProduct().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if(product != null){
                    binding.setProduct(product);
                }
            }
        });
        if(id != -1){
        mViewModel.setId(id);
        } else {
            binding.setProduct(new Product());
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.product_menu_save,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save: return save();
        }
        return onContextItemSelected(item);
    }

    private boolean save() {
        Product toSave = binding.getProduct();
        if (toSave != null){
            Log.d(TAG,toSave.toString());
            mViewModel.saveProduct(toSave);
        }
        NavHostFragment.findNavController(this).popBackStack();
        return true;
    }

    @Override
    public void onDateChanged(Date date) {
        binding.getProduct().setExpirationDate(date);
        binding.invalidateAll();
    }
}