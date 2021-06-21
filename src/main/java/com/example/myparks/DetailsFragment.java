package com.example.myparks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myparks.adapter.ViewPagerAdapter;
import com.example.myparks.modle.ParkViewModel;


public class DetailsFragment extends Fragment {

    private ParkViewModel parkViewModel;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // need to use view to access
        viewPager = view.findViewById(R.id.details_viewpager);

        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);

        TextView parkName = view.findViewById(R.id.details_park_name);
        TextView parkDes = view.findViewById(R.id.details_park_designation);

        TextView description = view.findViewById(R.id.details_description);

        TextView activites = view.findViewById(R.id.details_activites);
        TextView ophours = view.findViewById(R.id.details_Hours);
        TextView entranceFees = view.findViewById(R.id.details_fees);
        TextView detailTopics = view.findViewById(R.id.details_topciks);
        TextView direction = view.findViewById(R.id.ditaels_directions);
        TextView topick = view.findViewById(R.id.details_topciks);





        parkViewModel.getSelectedPark().observe(this, park -> {
            parkName.setText(park.getName());
            parkDes.setText(park.getDesignation());
            description.setText(park.getDescription());

            // activity set up text
            StringBuilder stringBuilder= new StringBuilder();
            for (int i = 0; i <park.getActivities().size() ; i++) {
                stringBuilder.append(park.getActivities().get(i).getName())
                        .append(" | ");
            }

            activites.setText(stringBuilder);
            // Topics set up text

            stringBuilder = new StringBuilder();
            for (int i = 0; i <park.getTopics().size() ; i++) {
                stringBuilder.append(park.getTopics().get(i).getName())
                        .append(" | ");
            }
            detailTopics.setText(stringBuilder);


            // FEES set up text
            // EntranceFee set up
            if (park.getEntranceFees().size() > 0) {
                Float seeFree = Float.parseFloat(park.getEntranceFees().get(0).getCost());
                Log.d("free", "onViewCreated: " + seeFree);
                if (seeFree == 0.00) {
                    entranceFees.setText("Free : ) ");
                } else {
                    entranceFees.setText(park.getEntranceFees().get(0).getCost() + " $");
                }
            }else {
                entranceFees.setText(R.string.not_price);


            }

            // set OpenHours
            StringBuilder opsString = new StringBuilder();
            opsString.append("Wednesday: ").append(park.getOperatingHours().get(0).getStandardHours().getWednesday()).append("\n")
                    .append("Monday: ").append(park.getOperatingHours().get(0).getStandardHours().getMonday()).append("\n")
                    .append("Friday: ").append(park.getOperatingHours().get(0).getStandardHours().getFriday()).append("\n")
                    .append("Thursday: ").append(park.getOperatingHours().get(0).getStandardHours().getThursday()).append("\n")
                    .append("Tuesday: ").append(park.getOperatingHours().get(0).getStandardHours().getTuesday()).append("\n")
                    .append("Saturday: ").append(park.getOperatingHours().get(0).getStandardHours().getSaturday()).append("\n")
                    .append("Sunday: ").append(park.getOperatingHours().get(0).getStandardHours().getSunday());
            ophours.setText(opsString);

            //set direction
            if (!TextUtils.isEmpty(park.getDirectionsInfo())){
                direction.setText(park.getDirectionsInfo());

            }else {
                direction.setText("Directions not available");
            }

            description.setText(park.getDescription());
            // description set up
            Log.d("dontmove", "onViewCreated: "+park.getDescription());
            //description.setText(park.getDescription());




            // for the image and the viewpages
            viewPagerAdapter = new ViewPagerAdapter(park.getImages());
            viewPager.setAdapter(viewPagerAdapter);




        });
    }

    public DetailsFragment() {

        // Required empty public constructor
    }


    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }
}