package fr.ensisa.rados.cafetariagestion.ui.fragment;

import androidx.databinding.DataBindingUtil;
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

import fr.ensisa.rados.cafetariagestion.database.AppDatabase;
import fr.ensisa.rados.cafetariagestion.databinding.CafetFragmentBinding;
import fr.ensisa.rados.cafetariagestion.databinding.ProviderFragmentBinding;
import fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel.CafetViewModel;
import fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel.ProviderViewModel;
import fr.ensisa.rados.cafetariagestion.R;

public class ProviderFragment extends Fragment {

    static private final String TAG = "ProviderFragment";


    private ProviderViewModel mViewModel;
    private ProviderFragmentBinding binding;
    private long id;


    public static ProviderFragment newInstance() {
        return new ProviderFragment();
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() !=null)
        {
            id = CafetFragmentArgs.fromBundle(getArguments()).getId();
        } else { id=-1; }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.provider_fragment, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProviderViewModel.class);
        mViewModel.setProviderDao(AppDatabase.getInstance().providerDao());
        long id = ProviderFragmentArgs.fromBundle(getArguments()).getId();
        if (id == -1) {
            mViewModel.createProvider();
        }
        else{
            mViewModel.setId(id);
        }
        binding.setVm(mViewModel);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.provider_menu_save,menu);
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
        mViewModel.saveProvider(mViewModel.getProvider().getValue());
        Navigation.findNavController(getView()).popBackStack();
        return true;
    }
}