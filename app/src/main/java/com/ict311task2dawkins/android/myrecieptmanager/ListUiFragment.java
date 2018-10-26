package com.ict311task2dawkins.android.myrecieptmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ict311task2dawkins.android.myrecieptmanager.model.Reciept;

import java.util.List;

public class ListUiFragment extends Fragment {
    private RecyclerView mReciptRecyclerView;
    private ItemAdapter mAdapter;

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_ui, container, false);

        mReciptRecyclerView = view.findViewById(R.id.fragment_list_ui);
        mReciptRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        updateUi();

        return view;
    }

    private void updateUi(){
        ItemLab itemLab = ItemLab.get(getActivity());
        List<Reciept> reciepts = itemLab.getmRecipts();

        if(mAdapter == null){
            mAdapter = new ItemAdapter(reciepts);
            mReciptRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setRecipts(reciepts);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_list_ui, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_menu_item:
                //insert new code
                Reciept reciept = new Reciept();
                ItemLab.get(getActivity()).addRecipt(reciept);
                Intent intent = ItemUiActivity.newIntent(getActivity(), reciept.getUuid());
                startActivity(intent);

            case R.id.help_menu_item:
                startActivity(new Intent(getContext(),WebViewActivty.class));

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Reciept mrecipt;
        private TextView titleTextView;
        private TextView shopNameTextView;
        private TextView dateTextView;
        private ImageView deleteButton;

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));

            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view_item);
            shopNameTextView = (TextView) itemView.findViewById(R.id.shop_name_text_view_item);
            dateTextView = (TextView) itemView.findViewById(R.id.date_text_view_item);
            deleteButton = (ImageView) itemView.findViewById(R.id.item_delete);

            itemView.setOnClickListener(this);
        }

        public void bind(Reciept reciept){
            mrecipt = reciept;
            titleTextView.setText(mrecipt.getTitle());
            shopNameTextView.setText(mrecipt.getShopeName());
            dateTextView.setText(mrecipt.getDate().toString());
            deleteButton.setOnClickListener(view -> {
                ItemLab.get(getContext()).removeRecipt(mrecipt);
                updateUi();
            });
        }

        @Override
        public void onClick(View view) {
            Intent intent = ItemUiActivity.newIntent(getActivity(),mrecipt.getUuid());
            startActivity(intent);

        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder>{

        private List<Reciept> mRecipts;

        public ItemAdapter(List<Reciept> recipts){
            mRecipts = recipts;
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
            Reciept reciept = mRecipts.get(i);
            itemHolder.bind(reciept);

        }

        @Override
        public int getItemCount() {
            return mRecipts.size();
        }

        public void setRecipts(List<Reciept> recipts){
            mRecipts = recipts;
        }
    }


}
