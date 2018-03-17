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
    private String[] imageUrl;
    private Integer imageID;

    public ViewPagerAdapter(Context context,Integer imageID,String[] imageUrl){
        this.context=context;
        this.imageID=imageID;
        this.imageUrl=imageUrl;
    }
    @Override
    public int getCount(){
        return imageUrl.length;
    }

    @Override
    public boolean isViewFromObject(View view,Object object){
        return view==object;
    }
    public Object instantiateItem(ViewGroup container,int position){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.content_main,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView2);
        Glide.with(context).load(imageUrl[position]).into(imageView);

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
