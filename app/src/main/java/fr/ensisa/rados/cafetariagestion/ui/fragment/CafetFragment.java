package fr.ensisa.rados.cafetariagestion.ui.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.ensisa.rados.cafetariagestion.R;
import fr.ensisa.rados.cafetariagestion.database.AppDatabase;
import fr.ensisa.rados.cafetariagestion.databinding.CafetFragmentBinding;
import fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel.CafetViewModel;

public class CafetFragment extends Fragment {


    static private final String TAG = "CafetFragment";


    private CafetFragmentBinding binding;
    private long id;
    private CafetViewModel mViewModel;

    public static CafetFragment newInstance() {
        return new CafetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cafet_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CafetViewModel.class);
        mViewModel.setCafetDao(AppDatabase.getInstance().cafetDao());
    }

}