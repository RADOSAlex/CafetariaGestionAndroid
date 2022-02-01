package fr.ensisa.rados.cafetariagestion.ui.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel.ProviderViewModel;
import fr.ensisa.rados.cafetariagestion.R;

public class ProviderFragment extends Fragment {

    private ProviderViewModel mViewModel;

    public static ProviderFragment newInstance() {
        return new ProviderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.provider_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProviderViewModel.class);
        // TODO: Use the ViewModel
    }

}