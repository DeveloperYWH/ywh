package com.zuimeng.hughfowl.latee.ec.main.personal.billdate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Toast;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.billdate.CalendarHelper;
import com.zuimeng.hughfowl.latte.ui.billdate.CalendarListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import butterknife.BindView;

/**
 * Created by hughfowl on 2018/3/9.
 */

public class BillDateDelegate extends LatteDelegate {

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyyMMdd");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat("yyyy年MM月");

    private DayNewsListAdapter mdayNewsListAdapter;
    private CalendarItemAdapter mcalendarItemAdapter;
    //key:date "yyyy-mm-dd" format.
    private TreeMap<String, List<NewsService.News.StoriesBean>> listTreeMap = new TreeMap<>();
    private Handler handler = new Handler();

    @BindView(R2.id.calendar_listview_bd)
    CalendarListView mcalendarListView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_billdate;
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        ActionBar actionBar = getProxyActivity().getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mdayNewsListAdapter = new DayNewsListAdapter(getContext());
        mcalendarItemAdapter = new CalendarItemAdapter(getContext());
        mcalendarListView.setCalendarListViewAdapter(mcalendarItemAdapter, mdayNewsListAdapter);


        // set start time,just for test.
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -7);
        loadNewsList(DAY_FORMAT.format(calendar.getTime()));
        actionBar.setTitle(YEAR_MONTH_FORMAT.format(calendar.getTime()));

        // deal with refresh and load more event.
        mcalendarListView.setOnListPullListener(new CalendarListView.onListPullListener() {
            @Override
            public void onRefresh() {
                String date = listTreeMap.firstKey();
                Calendar calendar = CalendarHelper.getCalendarByYearMonthDay(date);
                calendar.add(Calendar.MONTH, -1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                loadNewsList(DAY_FORMAT.format(calendar.getTime()));
            }

            @Override
            public void onLoadMore() {
                String date = listTreeMap.lastKey();
                Calendar calendar = CalendarHelper.getCalendarByYearMonthDay(date);
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                loadNewsList(DAY_FORMAT.format(calendar.getTime()));
            }
        });

        //
        mcalendarListView.setOnMonthChangedListener(new CalendarListView.OnMonthChangedListener() {
            @Override
            public void onMonthChanged(String yearMonth) {
                Calendar calendar = CalendarHelper.getCalendarByYearMonth(yearMonth);
                getProxyActivity().getSupportActionBar().setTitle(YEAR_MONTH_FORMAT.format(calendar.getTime()));
                loadCalendarData(yearMonth);
                Toast.makeText(getContext(), YEAR_MONTH_FORMAT.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
            }
        });

        mcalendarListView.setOnCalendarViewItemClickListener(new CalendarListView.OnCalendarViewItemClickListener() {
            @Override
            public void onDateSelected(View View, String selectedDate, int listSection, SelectedDateRegion selectedDateRegion) {

            }
        });

    }


    private void loadNewsList(String date) {
        Calendar calendar = getCalendarByYearMonthDay(date);
        String key = CalendarHelper.YEAR_MONTH_FORMAT.format(calendar.getTime());
    }
    private void loadCalendarData(final String date) {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Random random = new Random();
                            if (date.equals(mcalendarListView.getCurrentSelectedDate().substring(0, 7))) {
                                for (String d : listTreeMap.keySet()) {
                                    if (date.equals(d.substring(0, 7))) {
                                        CustomCalendarItemModel customCalendarItemModel = mcalendarItemAdapter.getDayModelList().get(d);
                                        if (customCalendarItemModel != null) {
                                            customCalendarItemModel.setOrdersCount(listTreeMap.get(d).size());
                                            customCalendarItemModel.setFav(random.nextBoolean());
                                        }

                                    }
                                }
                                mcalendarItemAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }

    public static Calendar getCalendarByYearMonthDay(String yearMonthDay) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTimeInMillis(DAY_FORMAT.parse(yearMonthDay).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }


}
