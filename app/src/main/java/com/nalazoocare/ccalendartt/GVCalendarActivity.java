package com.nalazoocare.ccalendartt;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;


/**
 * �׸���並 �̿��� �޷� ����
 * 
 * @blog http://croute.me
 * @link http://croute.me/335
 * 
 * @author croute
 * @since 2011.03.08
 */
public class GVCalendarActivity extends Activity implements AdapterView.OnItemClickListener, OnClickListener
{
	public static int SUNDAY 		= 1;
	public static int MONDAY 		= 2;
	public static int TUESDAY 		= 3;
	public static int WEDNSESDAY 	= 4;
	public static int THURSDAY 		= 5;
	public static int FRIDAY 		= 6;
	public static int SATURDAY 		= 7;
	
	private TextView mTvCalendarTitle;
	private GridView mGvCalendar;
	
	private ArrayList<DayInfo> mDayList;
	private CalendarAdapter mCalendarAdapter;
	
	Calendar mLastMonthCalendar;
	Calendar mThisMonthCalendar;
	Calendar mNextMonthCalendar;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gv_calendar_activity);
		
		Button bLastMonth = (Button)findViewById(R.id.gv_calendar_activity_b_last);
		Button bNextMonth = (Button)findViewById(R.id.gv_calendar_activity_b_next);
		
		mTvCalendarTitle = (TextView)findViewById(R.id.gv_calendar_activity_tv_title);
		mGvCalendar = (GridView)findViewById(R.id.gv_calendar_activity_gv_calendar);
		
		
		bLastMonth.setOnClickListener(this);
		bNextMonth.setOnClickListener(this);

		mDayList = new ArrayList<DayInfo>();
	}



	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
		
		// �̹��� �� Ķ���� �ν��Ͻ��� �����Ѵ�.
		mThisMonthCalendar = Calendar.getInstance();
		mThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
		getCalendar(mThisMonthCalendar);
	}

	/**
	 * �޷��� �����Ѵ�.
	 * 
	 * @param calendar �޷¿� �������� �̹����� Calendar ��ü
	 */
	private void getCalendar(Calendar calendar)
	{
		int lastMonthStartDay;
		int dayOfMonth;
		int thisMonthLastDay;
		
		mDayList.clear();
		
		// �̹��� �������� ������ ���Ѵ�. �������� �Ͽ����� ��� �ε����� 1(�Ͽ���)���� 8(������ �Ͽ���)�� �ٲ۴�.)
		dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
		thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		calendar.add(Calendar.MONTH, -1);
		Log.e("������ ��������", calendar.get(Calendar.DAY_OF_MONTH)+"");

		// �������� ������ ���ڸ� ���Ѵ�.
		lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		calendar.add(Calendar.MONTH, 1);
		Log.e("�̹��� ������", calendar.get(Calendar.DAY_OF_MONTH)+"");
		
		if(dayOfMonth == SUNDAY)
		{
			dayOfMonth += 7;
		}
		
		lastMonthStartDay -= (dayOfMonth-1)-1;
		

		// Ķ���� Ÿ��Ʋ(��� ǥ��)�� �����Ѵ�. 
		mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "�� "
				+ (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "��");

		DayInfo day;
		
		Log.e("DayOfMOnth", dayOfMonth+"");
		
		for(int i=0; i<dayOfMonth-1; i++)
		{
			int date = lastMonthStartDay+i;
			day = new DayInfo();
			day.setDay(Integer.toString(date));
			day.setInMonth(false);
			
			mDayList.add(day);
		}
		for(int i=1; i <= thisMonthLastDay; i++)
		{
			day = new DayInfo();
			day.setDay(Integer.toString(i));
			day.setInMonth(true);
			
			mDayList.add(day);
		}
		for(int i=1; i<42-(thisMonthLastDay+dayOfMonth-1)+1; i++)
		{
			day = new DayInfo();
			day.setDay(Integer.toString(i));
			day.setInMonth(false);
			mDayList.add(day);
		}
		
		initCalendarAdapter();
	}

	/**
	 * �������� Calendar ��ü�� ��ȯ�մϴ�.
	 * 
	 * @param calendar
	 * @return LastMonthCalendar
	 */
	private Calendar getLastMonth(Calendar calendar)
	{
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		calendar.add(Calendar.MONTH, -1);
		mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "�� "
				+ (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "��");
		return calendar;
	}

	/**
	 * �������� Calendar ��ü�� ��ȯ�մϴ�.
	 * 
	 * @param calendar
	 * @return NextMonthCalendar
	 */
	private Calendar getNextMonth(Calendar calendar)
	{
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		calendar.add(Calendar.MONTH, +1);
		mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "�� "
				+ (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "��");
		return calendar;
	}
//

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.gv_calendar_activity_b_last:
			mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
			getCalendar(mThisMonthCalendar);
			break;
		case R.id.gv_calendar_activity_b_next:
			mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
			getCalendar(mThisMonthCalendar);
			break;
		}
	}

	private void initCalendarAdapter()
	{
		mCalendarAdapter = new CalendarAdapter(this, R.layout.day, mDayList);
		mGvCalendar.setAdapter(mCalendarAdapter);

	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}
}