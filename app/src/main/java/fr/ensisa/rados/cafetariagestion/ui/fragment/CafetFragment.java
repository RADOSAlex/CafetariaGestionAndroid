package fr.ensisa.rados.cafetariagestion.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

import fr.ensisa.rados.cafetariagestion.BR;
import fr.ensisa.rados.cafetariagestion.R;
import fr.ensisa.rados.cafetariagestion.database.AppDatabase;
import fr.ensisa.rados.cafetariagestion.databinding.CafetFragmentBinding;
import fr.ensisa.rados.cafetariagestion.databinding.ProductFragmentBinding;
import fr.ensisa.rados.cafetariagestion.databinding.ProductItemBinding;
import fr.ensisa.rados.cafetariagestion.model.Product;
import fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel.CafetViewModel;
import fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel.ProductViewModel;

public class CafetFragment extends Fragment {
    static private final String TAG = "CafetFragment";


    private CafetViewModel mViewModel;
    private CafetFragmentBinding binding;
    private ProductAdapter adapter;
    private long id;


    public static CafetFragment newInstance() {
        return new CafetFragment();
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            id = CafetFragmentArgs.fromBundle(getArguments()).getId();
        } else {
            id = -1;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.cafet_fragment, container, false);
        binding.setLifecycleOwner(this);
        binding.listCafetProductAss.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false));
        RecyclerView list = binding.getRoot().findViewById(R.id.listCafetProductAss);

        DividerItemDecoration divider = new DividerItemDecoration(list.getContext(), DividerItemDecoration.VERTICAL);
        list.addItemDecoration(divider);
        adapter= new ProductAdapter();
        binding.listCafetProductAss.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override //A changer par viewcreated
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CafetViewModel.class);
        mViewModel.setCafetDao(AppDatabase.getInstance().cafetDao());
        mViewModel.setProductDao(AppDatabase.getInstance().productDao());
        mViewModel.getFullCafet().observe(getViewLifecycleOwner(), p ->{
            adapter.setCollection(p.products);
        });
        AppDatabase.getInstance();
        long id = CafetFragmentArgs.fromBundle(getArguments()).getId();
        if (id == -1) {
            mViewModel.createCafet();
        } else {
            mViewModel.setId(id);
        }
        binding.setVm(mViewModel);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cafet_menu_save, menu);
        inflater.inflate(R.menu.return_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                return save();
            case R.id.back:
                return back();
        }
        return onContextItemSelected(item);
    }

    private boolean back() {
        Navigation.findNavController(getView()).popBackStack();
        return true;
    }

    private boolean save() {
        mViewModel.saveCafet(mViewModel.getCafet().getValue());
        Navigation.findNavController(getView()).popBackStack();
        return true;
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

        private List<Product> collection;

        private class ViewHolder extends RecyclerView.ViewHolder {
            ViewDataBinding binding;

            public ViewHolder(@NonNull ViewDataBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ProductItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from((parent.getContext())), R.layout.product_item, parent, false);
            binding.setLifecycleOwner(getViewLifecycleOwner());
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
            Product p = collection.get(position);
            holder.binding.setVariable(BR.product, p);
            holder.binding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return collection == null ? 0 : collection.size();
        }

        public void setCollection(List<Product> collection) {
            this.collection = collection;
            notifyDataSetChanged();
        }
    }


}
