package fr.ensisa.rados.cafetariagestion.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.ensisa.rados.cafetariagestion.database.AppDatabase;
import fr.ensisa.rados.cafetariagestion.database.FeedDatabase;
import fr.ensisa.rados.cafetariagestion.databinding.CafetItemBinding;
import fr.ensisa.rados.cafetariagestion.databinding.ProviderItemBinding;
import fr.ensisa.rados.cafetariagestion.model.Cafet;
import fr.ensisa.rados.cafetariagestion.model.Provider;
import fr.ensisa.rados.cafetariagestion.ui.ItemSwipeCallback;
import fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel.CafetListViewModel;
import fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel.ProviderListViewModel;
import fr.ensisa.rados.cafetariagestion.R;

public class ProviderListFragment extends Fragment {

    private ProviderListViewModel mViewModel;
    private ProviderListAdapter adapter;

    public static CafetListFragment newInstance() {
        return new CafetListFragment();
    }


    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.provider_list_fragment, container, false);
        root.findViewById(R.id.add).setOnClickListener((view) -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_providerListFragment_to_providerFragment);
        });
        RecyclerView list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(root.getContext(), RecyclerView.VERTICAL, false));
        adapter = new ProviderListFragment.ProviderListAdapter();
        list.setAdapter(adapter);
        DividerItemDecoration divider = new DividerItemDecoration(list.getContext(), DividerItemDecoration.VERTICAL);
        list.addItemDecoration(divider);
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemSwipeCallback(getContext(), ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, new ItemSwipeCallback.SwipeListener() {
                    @Override
                    public void onSwiped(int direction, int position) {
                        Provider provider = adapter.providers.get(position);

                        switch (direction) {
                            case ItemTouchHelper.LEFT:
                                mViewModel.deleteProvider(provider);
                                break;
                            case ItemTouchHelper.RIGHT:
                                ProviderListFragmentDirections.ActionProviderListFragmentToProviderFragment action = ProviderListFragmentDirections.actionProviderListFragmentToProviderFragment();
                                action.setId(provider.getPid());
                                NavHostFragment.findNavController(ProviderListFragment.this).navigate(action);
                                break;
                        }
                    }
                })
        );
        touchHelper.attachToRecyclerView(list);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProviderListViewModel.class);
        mViewModel.setProviderDao(AppDatabase.getInstance().providerDao());
        mViewModel.getProviders().observe(getViewLifecycleOwner(), new Observer<List<Provider>>() {
            @Override
            public void onChanged(List<Provider> providers) { adapter.setProviders(providers); }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.provider_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                return doPopulate();
        }
        return onContextItemSelected(item);
    }

    private boolean doPopulate() {
        FeedDatabase feeder = new FeedDatabase();
        feeder.feedP2();
        return true;
    }


    private class ProviderListAdapter extends RecyclerView.Adapter<ProviderListFragment.ProviderListAdapter.ProviderHolder> {
        private List<Provider> providers;

        private class ProviderHolder extends RecyclerView.ViewHolder {
            ProviderItemBinding binding;

            public ProviderHolder(ProviderItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
                this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getLayoutPosition();
                        Provider provider = providers.get(position);
                        ProviderListFragmentDirections.ActionProviderListFragmentToProviderFragment ProvListToProv_action = ProviderListFragmentDirections.actionProviderListFragmentToProviderFragment();

                        ProvListToProv_action.setId(provider.getPid());
                        NavHostFragment.findNavController(ProviderListFragment.this).navigate(ProvListToProv_action);
                    }
                });

                this.binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        int position = getLayoutPosition();
                        Provider provider = providers.get(position);
                        mViewModel.deleteProvider(provider);
                        return true;
                    }
                });

            }

        }
        @NonNull
        @Override
        public ProviderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ProviderItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.provider_item, parent, false);
            return new ProviderHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ProviderListFragment.ProviderListAdapter.ProviderHolder holder, int position) {
            Provider provider = providers.get(position);
            holder.binding.setProvider(provider);
        }

        @Override
        public int getItemCount() { return providers != null ? providers.size() : 0; }

        public void setProviders(List<Provider> providers){
            this.providers=providers;
            notifyDataSetChanged();
        }
    }

}