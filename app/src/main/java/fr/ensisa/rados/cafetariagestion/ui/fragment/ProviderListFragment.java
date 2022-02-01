package fr.ensisa.rados.cafetariagestion.ui.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel.ProviderListViewModel;
import fr.ensisa.rados.cafetariagestion.R;

public class ProviderListFragment extends Fragment {

    private ProviderListViewModel mViewModel;

    public static ProviderListFragment newInstance() {
        return new ProviderListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.provider_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProviderListViewModel.class);
        // TODO: Use the ViewModel
    }

}