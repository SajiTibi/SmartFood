package com.awesome.food_app.smartfood;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.tom.range.slider.RangeSliderView;

public class TimeDialog extends DialogFragment {
    private int startHour;
    private int endHour;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.availability_dialog, container, false);
        RangeSliderView rangeSliderView = view.findViewById(R.id.rangeSliderView);
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i <= 24; i++) {
            arr.add(i);
        }
        rangeSliderView.setRangeValues(arr);
        rangeSliderView.setMinAndMaxValue(0, 24);
        rangeSliderView.setOnValueChangedListener(new RangeSliderView.OnValueChangedListener() {
            @Override
            public void onValueChanged(int minValue, int maxValue) {
                startHour = minValue;
                endHour = maxValue;
            }

            @Override
            public String parseMinValueDisplayText(int minValue) {
                return super.parseMinValueDisplayText(minValue);
            }

            @Override
            public String parseMaxValueDisplayText(int maxValue) {
                return super.parseMinValueDisplayText(maxValue);
            }
        });
        return view;
    }
}
