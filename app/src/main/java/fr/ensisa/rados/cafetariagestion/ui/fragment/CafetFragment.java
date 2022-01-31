package fr.ensisa.rados.cafetariagestion.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import fr.ensisa.rados.cafetariagestion.R;
import fr.ensisa.rados.cafetariagestion.database.AppDatabase;
import fr.ensisa.rados.cafetariagestion.databinding.CafetFragmentBinding;
import fr.ensisa.rados.cafetariagestion.databinding.ProductFragmentBinding;
import fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel.CafetViewModel;
import fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel.ProductViewModel;

public class CafetFragment extends Fragment {
    static private final String TAG = "ProductFragment";
    private static final int EXPIRATIONDATE = 1;

    private CafetViewModel mViewModel;
    private CafetFragmentBinding binding;
    private long id;


    public static CafetFragment newInstance() {
        return new CafetFragment();
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
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CafetViewModel.class);
        mViewModel.setCafetDao(AppDatabase.getInstance().cafetDao());
        long id = ProductFragmentArgs.fromBundle(getArguments()).getId();
        if (id == 0) {
            mViewModel.createCafet();
        }
        else{
            mViewModel.setId(id);
        }
        binding.setVm(mViewModel);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.product_menu_save,menu);
        inflater.inflate(R.menu.return_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save: return save();
            case R.id.back: return back();
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


}
