package com.zuimeng.hughfowl.latee.ec.shop.shopdata;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.shop.BottomItemShopDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by hughfowl on 2018/3/24.
 */

public class GeneralDataDelegate extends BottomItemShopDelegate {

    @BindView(R2.id.data_pie_chart)
    PieChartView pieChartView = null;
    private PieChartData pieChartData;



    @Override
    public Object setLayout() {
        return R.layout.delegate2_data_general;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        generateData();
        pieChartView.setOnValueTouchListener(new ValueTouchListener());
        pieChartView.setPieChartData(pieChartData);

    }

    private void generateData(){

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < 6; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
            values.add(sliceValue);
        }


        pieChartData = new PieChartData(values);

        pieChartData.setHasLabels(true);
        pieChartData.setHasLabelsOnlyForSelected(false);
        pieChartData.setHasLabelsOutside(false);
        pieChartData.setHasCenterCircle(true);
        pieChartData.setCenterCircleColor(R.color.app_main);
        pieChartData.setCenterText1("总览");
        pieChartData.setCenterText2("选择的日期");


    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
}
