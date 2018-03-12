package com.zuimeng.hughfowl.latee.ec.main.explorer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.library.FocusResizeAdapter;
import com.zuimeng.hughfowl.latee.ec.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hughfowl on 2018/3/10.
 */

public class CustomAdapter extends FocusResizeAdapter<RecyclerView.ViewHolder> {

    public static final int OFFSET_TEXT_SIZE = 4;
    public static final float OFFSET_TEXT_ALPHA = 100f;
    public static final float ALPHA_SUBTITLE = 0.81f;
    public static final float ALPHA_SUBTITLE_HIDE = 0f;

    private List<CustomObject> items;
    private Context context;

    public CustomAdapter(ExplorerDelegate context, int height) {
        super(context.getContext(), height);
        this.context = context.getContext();
        items = new ArrayList<>();
    }

    public void addItems(List<CustomObject> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void onItemBigResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        if (((CustomViewHolder) viewHolder).titleTextView.getTextSize() + (dyAbs / OFFSET_TEXT_SIZE) >= context.getResources().getDimension(R.dimen.font_xxxlarge)) {
            ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_xxxlarge));
        } else {
            ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ((CustomViewHolder) viewHolder).titleTextView.getTextSize() + (dyAbs / OFFSET_TEXT_SIZE));
        }

        float alpha = dyAbs / OFFSET_TEXT_ALPHA;
        if (((CustomViewHolder) viewHolder).subtitleTextView.getAlpha() + alpha >= ALPHA_SUBTITLE) {
            ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE);
        } else {
            ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(((CustomViewHolder) viewHolder).subtitleTextView.getAlpha() + alpha);
        }

    }

    @Override
    public void onItemBigResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_xxxlarge));
        ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE);

    }

    @Override
    public void onItemSmallResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_medium));
        ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE_HIDE);

    }

    @Override
    public void onItemSmallResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        if (((CustomViewHolder) viewHolder).titleTextView.getTextSize() - (dyAbs / OFFSET_TEXT_SIZE) <= context.getResources().getDimension(R.dimen.font_medium)) {
            ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_medium));
        } else {
            ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ((CustomViewHolder) viewHolder).titleTextView.getTextSize() - (dyAbs / OFFSET_TEXT_SIZE));
        }

        float alpha = dyAbs / OFFSET_TEXT_ALPHA;
        if (((CustomViewHolder) viewHolder).subtitleTextView.getAlpha() - alpha < ALPHA_SUBTITLE_HIDE) {
            ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE_HIDE);
        } else {
            ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(((CustomViewHolder) viewHolder).subtitleTextView.getAlpha() - alpha);
        }

    }

    @Override
    public void onItemInit(RecyclerView.ViewHolder viewHolder) {
        ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                context.getResources().getDimensionPixelSize(R.dimen.font_xxxlarge));
        ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE);

    }

    @Override
    public int getFooterItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomObject customObject = items.get(position);
        fill((CustomViewHolder) holder, customObject);
    }

    @SuppressLint("StaticFieldLeak")
    private void fill(final CustomViewHolder holder, final CustomObject customObject) {
        holder.titleTextView.setText(customObject.getTitle());
        holder.subtitleTextView.setText(customObject.getSubTitle());
        Glide.with(holder.image)
                .load(customObject.getDrawable())
                .into(holder.image);
        //        holder.image.setImageBitmap(BitmapFactory.decodeStream(customObject.getDrawable().openStream()));
    }





    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView titleTextView;
        TextView subtitleTextView;

        public CustomViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image_custom_item);
            titleTextView = (TextView) v.findViewById(R.id.title_custom_item);
            subtitleTextView = (TextView) v.findViewById(R.id.subtitle_custom_item);
        }
    }

}
