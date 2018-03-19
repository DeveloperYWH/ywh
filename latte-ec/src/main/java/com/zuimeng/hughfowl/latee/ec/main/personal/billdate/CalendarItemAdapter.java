package com.zuimeng.hughfowl.latee.ec.main.personal.billdate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latte.ui.billdate.BaseCalendarItemAdapter;
import com.zuimeng.hughfowl.latte.ui.billdate.BaseCalendarItemModel;

/**
 * Created by kelin on 16-7-19.
 */
public class CalendarItemAdapter extends BaseCalendarItemAdapter<CustomCalendarItemModel> {

    public CalendarItemAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(String date, CustomCalendarItemModel model, View convertView, ViewGroup parent) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.item_griditem_custom_calendar, null);

        TextView dayNum = (TextView) view.findViewById(R.id.tv_day_num);
        dayNum.setText(model.getDayNumber());

        view.setBackgroundResource(com.zuimeng.hughfowl.latte_ui.R.drawable.bg_shape_calendar_item_normal);

        if (model.isToday()) {
            dayNum.setTextColor(mContext.getResources().getColor(com.zuimeng.hughfowl.latte_ui.R.color.red_ff725f));
            dayNum.setText(mContext.getResources().getString(com.zuimeng.hughfowl.latte_ui.R.string.today));
        }

        if (model.isHoliday()) {
            dayNum.setTextColor(mContext.getResources().getColor(com.zuimeng.hughfowl.latte_ui.R.color.red_ff725f));
        }

        if (model.getStatus() == BaseCalendarItemModel.Status.DISABLE) {
            dayNum.setTextColor(mContext.getResources().getColor(android.R.color.darker_gray));
        }

        if (!model.isCurrentMonth()) {
            dayNum.setTextColor(mContext.getResources().getColor(com.zuimeng.hughfowl.latte_ui.R.color.gray_bbbbbb));
            view.setClickable(true);
        }

        TextView dayNewsCount = (TextView) view.findViewById(R.id.tv_day_new_count);
        if (model.getOrdersCount() > 0) {
            dayNewsCount.setText(String.format(mContext.getResources().getString(R.string.calendar_item_new_count), model.getOrdersCount()));
            dayNewsCount.setVisibility(View.VISIBLE);
        } else {
            dayNewsCount.setVisibility(View.GONE);
        }

        ImageView isFavImageView = (ImageView) view.findViewById(R.id.image_is_fav);
        if (model.isFav()) {
            isFavImageView.setVisibility(View.VISIBLE);
        } else {
            isFavImageView.setVisibility(View.GONE);
        }


        return view;
    }
}
