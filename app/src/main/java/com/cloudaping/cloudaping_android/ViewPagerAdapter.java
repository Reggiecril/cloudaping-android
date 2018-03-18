package com.cloudaping.cloudaping_android;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by reggie on 19/02/18.
 */

public class ViewPagerAdapter extends PagerAdapter{
    private Context context;
    private LayoutInflater layoutInflater;
    private Integer id;
    private String [] images={"http://cloudaping.com/assets/images/1491587284_screen_shot_2017-04-07_at_10.47.03_am.jpg",
            "http://cloudaping.com/assets/images/d7e5ee71fa9452b10dbcda80c0b5f3c4.jpg",
            "http://cloudaping.com/assets/images/beats-ad-800x332.jpg",
            "http://cloudaping.com/assets/images/xbox one commercial $399.png",
            "http://cloudaping.com/assets/images/Screen-Shot-2014-01-02-at-4.27.12-AM.png"};

    public ViewPagerAdapter(Context context,Integer id,String[] images){
        this.context=context;
        this.id=id;
        this.images=images;
    }
    @Override
    public int getCount(){
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view,Object object){
        return view==object;
    }
    public Object instantiateItem(ViewGroup container,int position){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.content_main,null);
        ImageView imageView = (ImageView) view.findViewById(id);
        Glide.with(context).load(images[position]).into(imageView);

        ViewPager vp=(ViewPager) container;
        vp.addView(view,0);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        ViewPager vp =(ViewPager) container;
        View view=(View)object;
        vp.removeView(view);
    }
}
