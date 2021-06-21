package com.example.myparks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myparks.adapter.OnParkClickListener;
import com.example.myparks.adapter.ParkRecyclerViewAdapter;
import com.example.myparks.data.AsyncResponse;
import com.example.myparks.data.Repository;
import com.example.myparks.modle.Park;
import com.example.myparks.modle.ParkViewModel;

import java.util.ArrayList;
import java.util.List;


public class ParksFragment extends Fragment implements OnParkClickListener {
    private RecyclerView recyclerView;
    private ParkRecyclerViewAdapter parkRecyclerViewAdapter;
    private List<Park> parkList;
    private ParkViewModel parkViewModel;


    public ParksFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ParksFragment newInstance() {
        ParksFragment fragment = new ParksFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parkList = new ArrayList<>();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // view model
        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);
        if (parkViewModel.getParks().getValue() != null) {
            // enter the parkList into the view model class
            parkList = parkViewModel.getParks().getValue();
            parkRecyclerViewAdapter= new ParkRecyclerViewAdapter(parkList,this);
            recyclerView.setAdapter(parkRecyclerViewAdapter);
        }

//        Repository.getParks(parks -> {
//            parkRecyclerViewAdapter= new ParkRecyclerViewAdapter(parks,this);
//            recyclerView.setAdapter(parkRecyclerViewAdapter);
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_parks,container,false);
        recyclerView = view.findViewById(R.id.park_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onParkClick(Park park) {
        Log.d("PARK", "OnParkClick: " + park.getName());
        parkViewModel.setSelectedPark(park);
        // move to other fragment
        getFragmentManager().beginTransaction()
                .replace(R.id.park_fragment,DetailsFragment.newInstance())
                .commit();


    }
}