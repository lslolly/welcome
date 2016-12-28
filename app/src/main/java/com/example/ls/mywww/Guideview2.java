
package com.example.ls.mywww;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ${lishu} on 2016/12/22.
 */

public class Guideview2 extends RelativeLayout {

    private MyPagerAdapter adapter;

    public Guideview2(Context context) {
        this(context, null);
    }

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private TextView startTV;
    private List<ImageView> views;
    private List<Integer> iconIDs;

    public Guideview2(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(getContext(), R.layout.gl_guide_view, this);
//        this.setVisibility(Util.getGlGuideState(context) ? View.VISIBLE : View.GONE);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        views = new LinkedList<>();
        iconIDs = new LinkedList<>();
        adapter = new MyPagerAdapter();
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                RadioButton childAt = (RadioButton) mRadioGroup.getChildAt(position % mRadioGroup.getChildCount());
                childAt.setChecked(true);
                if (position == views.size() - 1) {
                    mRadioGroup.setVisibility(View.GONE);
                    startTV.setVisibility(View.VISIBLE);
                } else {
                    mRadioGroup.setVisibility(View.VISIBLE);
                    startTV.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        startTV = (TextView) findViewById(R.id.tv_start);
        startTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);

//                Util.setGLGuideState(context, false);
                Guideview2.this.setVisibility(GONE);
            }
        });
    }

    public void setData(int... data) {
        ImageView imageView;
        RadioButton radioButton;
        for (int i = 0; i < data.length; i++) {
            imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            views.add(imageView);
            iconIDs.add(data[i]);
            radioButton = (RadioButton) View.inflate(getContext(), R.layout.gl_guide_indicator_item, null);
            radioButton.setChecked(false);
            mRadioGroup.addView(radioButton);

            RadioGroup.LayoutParams layoutParams = (RadioGroup.LayoutParams) radioButton.getLayoutParams();
            if (i > 0) {
                layoutParams.leftMargin = Util.dip2px(getContext(), 10);
            } else {
                radioButton.setChecked(true);
            }
        }
        adapter.notifyDataSetChanged();
    }
    private OnClickListener onClickListener;
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            views.get(position).setImageResource(iconIDs.get(position));
            container.addView(views.get(position));
            return views.get(position);
        }
    }
}
