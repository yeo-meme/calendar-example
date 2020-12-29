package com.nalazoocare.ccalendartt;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 *
 * @author croute
 * @since 2011.03.08
 */
public class CalendarAdapter extends BaseAdapter {
    private ArrayList<DayInfo> mDayList;
    private Context mContext;
    private int mResource;
    private LayoutInflater mLiInflater;
    private OnItemClickListener<DayInfo> onItemClickListener;

    public CalendarAdapter(Context context, int textResource, ArrayList<DayInfo> dayList) {
        this.mContext = context;
        this.mDayList = dayList;
        this.mResource = textResource;
        this.mLiInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void setOnItemClickListener(OnItemClickListener<DayInfo> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mDayList.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mDayList.get(position);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DayInfo day = mDayList.get(position);

        DayViewHolder dayViewHolder;

        if (convertView == null) {
            convertView = mLiInflater.inflate(mResource, null);

            if (position % 7 == 6) {
                convertView.setLayoutParams(new GridView.LayoutParams(getCellWidthDP() + getRestCellWidthDP(), getCellHeightDP()));
            } else {
                convertView.setLayoutParams(new GridView.LayoutParams(getCellWidthDP(), getCellHeightDP()));
            }


            dayViewHolder = new DayViewHolder();

            dayViewHolder.llBackground = (LinearLayout) convertView.findViewById(R.id.day_cell_ll_background);
            dayViewHolder.tvDay = (TextView) convertView.findViewById(R.id.day_cell_tv_day);
            convertView.setTag(dayViewHolder);

        } else {
            dayViewHolder = (DayViewHolder) convertView.getTag();
        }

        if (day != null) {
            dayViewHolder.tvDay.setText(day.getDay());

            if (day.isInMonth()) {
                if (position % 7 == 0) {
                    dayViewHolder.tvDay.setTextColor(Color.RED);
                } else if (position % 7 == 6) {
                    dayViewHolder.tvDay.setTextColor(Color.BLUE);
                } else {
                    dayViewHolder.tvDay.setTextColor(Color.BLACK);
                }
            } else {
                dayViewHolder.tvDay.setTextColor(Color.GRAY);
            }


        }



        return convertView;
    }

    public class DayViewHolder {
        public LinearLayout llBackground;
        public TextView tvDay;




    }

    private int getCellWidthDP() {
//		int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int cellWidth = 480 / 7;

        return cellWidth;
    }

    private int getRestCellWidthDP() {
//		int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int cellWidth = 480 % 7;

        return cellWidth;
    }

    private int getCellHeightDP() {
//		int height = mContext.getResources().getDisplayMetrics().widthPixels;
        int cellHeight = 480 / 6;

        return cellHeight;
    }

}
