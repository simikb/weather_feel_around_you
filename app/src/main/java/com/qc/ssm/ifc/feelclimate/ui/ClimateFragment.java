package com.qc.ssm.ifc.feelclimate.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.qc.ssm.ifc.feelclimate.databinding.ClimateHomeLayoutBinding;
import com.qc.ssm.ifc.feelclimate.models.ClimateModel;
import com.qc.ssm.ifc.feelclimate.utils.Utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClimateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class ClimateFragment extends Fragment {
    ClimateHomeLayoutBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private ClimateModel mParam1;

    public ClimateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ClimateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClimateFragment newInstance(ClimateModel data) {

        ClimateFragment fragment = new ClimateFragment();
        if (data != null) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_PARAM1, (Serializable) data);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_PARAM1)) {
            {
                mParam1 = (ClimateModel) getArguments().getSerializable(ARG_PARAM1);
            }
        }
    }

    private String getTime(long time) {
        Date now = new Date(time);
        String datePattern = "yyyy-mm-dd HH-MM-SS";
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        return formatter.format(now);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ClimateHomeLayoutBinding.inflate(inflater, container, false);
        binding.degreeDiscText.setText(mParam1.getWeather().get(0).getMain());
        binding.airText.setText(mParam1.getWeather().get(0).getDescription());
        binding.nameText.setText(mParam1.getName());
        binding.degreeText.setText(getTemp(mParam1.getMain().getTemp()));
        String image = mParam1.getWeather().get(0).getIcon().trim();
        Glide.with(getActivity().getApplicationContext()).load(new Utils().getImageUrl(image)).into(binding.weatherIcon);
        getTime(mParam1.getSys().getSunrise());
        return binding.getRoot();
    }

    /*
     * Kelvin temp conversion
     */
    private String getTemp(Double temp) {
        int value = (int) (temp - 273.15);
        return Integer.toString(value);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* binding.layoutInclude.humidityText.setText(mParam1.getMain().getHumidity());
        binding.layoutInclude.pressureText.setText(mParam1.getMain().getPressure());*/
    }
}