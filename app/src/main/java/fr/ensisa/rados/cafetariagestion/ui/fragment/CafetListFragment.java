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

import fr.ensisa.rados.cafetariagestion.R;
import fr.ensisa.rados.cafetariagestion.database.AppDatabase;
import fr.ensisa.rados.cafetariagestion.database.FeedDatabase;
import fr.ensisa.rados.cafetariagestion.databinding.CafetItemBinding;
import fr.ensisa.rados.cafetariagestion.model.Cafet;
import fr.ensisa.rados.cafetariagestion.model.Product;
import fr.ensisa.rados.cafetariagestion.ui.ItemSwipeCallback;
import fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel.CafetListViewModel;

public class CafetListFragment extends Fragment {

    private CafetListViewModel mViewModel;
    private CafetListAdapter adapter;

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
        View root = inflater.inflate(R.layout.cafet_list_fragment, container, false);
        root.findViewById(R.id.add).setOnClickListener((view) -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_cafetListFragment_to_cafetFragment);
        });
        RecyclerView list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(root.getContext(), RecyclerView.VERTICAL, false));
        adapter = new CafetListFragment.CafetListAdapter();
        list.setAdapter(adapter);
        DividerItemDecoration divider = new DividerItemDecoration(list.getContext(), DividerItemDecoration.VERTICAL);
        list.addItemDecoration(divider);
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemSwipeCallback(getContext(), ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, new ItemSwipeCallback.SwipeListener() {
                    @Override
                    public void onSwiped(int direction, int position) {
                        Cafet cafet = adapter.cafets.get(position);

                        switch (direction) {
                            case ItemTouchHelper.LEFT:
                                mViewModel.deleteCafet(cafet);
                                break;
                            case ItemTouchHelper.RIGHT:
                                CafetListFragmentDirections.ActionCafetListFragmentToCafetFragment action = CafetListFragmentDirections.actionCafetListFragmentToCafetFragment();
                                action.setId(cafet.getCid());
                                NavHostFragment.findNavController(CafetListFragment.this).navigate(action);
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
        mViewModel = new ViewModelProvider(this).get(CafetListViewModel.class);
        mViewModel.setCafetDao(AppDatabase.getInstance().cafetDao());
        mViewModel.getCafets().observe(getViewLifecycleOwner(), new Observer<List<Cafet>>() {
            @Override
            public void onChanged(List<Cafet> cafets) { adapter.setCafets(cafets); }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cafet_menu, menu);
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
        feeder.feed();
        //feeder.feedC();
        return true;
    }


    private class CafetListAdapter extends RecyclerView.Adapter<CafetListFragment.CafetListAdapter.CafetHolder> {
        private List<Cafet> cafets;

        private class CafetHolder extends RecyclerView.ViewHolder {
            CafetItemBinding binding;

            public CafetHolder(CafetItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
                this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getLayoutPosition();
                        Cafet cafet = cafets.get(position);
                        CafetListFragmentDirections.ActionCafetListFragmentToCafetFragment CListToC_action = CafetListFragmentDirections.actionCafetListFragmentToCafetFragment();

                        CListToC_action.setId(cafet.getCid());
                        NavHostFragment.findNavController(CafetListFragment.this).navigate(CListToC_action);
                    }
                });

                this.binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        int position = getLayoutPosition();
                        Cafet cafet = cafets.get(position);
                        mViewModel.deleteCafet(cafet);
                        return true;
                    }
                });

            }

        }
        @NonNull
        @Override
        public CafetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CafetItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cafet_item, parent, false);
            return new CafetHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull CafetHolder holder, int position) {
            Cafet cafet = cafets.get(position);
            holder.binding.setCafet(cafet);
        }

        @Override
        public int getItemCount() { return cafets != null ? cafets.size() : 0; }

        public void setCafets(List<Cafet> cafets){
            this.cafets=cafets;
            notifyDataSetChanged();
        }
    }
}