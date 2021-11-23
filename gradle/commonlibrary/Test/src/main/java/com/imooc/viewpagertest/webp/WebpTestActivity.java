package com.imooc.viewpagertest.webp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.imooc.viewpagertest.R;
import com.imooc.viewpagertest.webp.WebpUtils;

import java.io.InputStream;

public class WebpTestActivity extends AppCompatActivity {

    private ViewPager mPager;
    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;

    /**
     * 图片资源id
     */
    private int[] imgIdArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        imgIdArray = new int[]{R.raw.bg_1_a_01,
                R.raw.bg_1_b_01,
                R.raw.bg_2_a_01,
                R.raw.bg_2_b_01};

        //将图片装载到数组中
        mImageViews = new ImageView[imgIdArray.length];
        for (int i = 0; i < mImageViews.length; i++) {
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
            imageView.setImageBitmap(showAlbum(imgIdArray[i]));
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter());
        mPager.setPageMargin(30);
        //设置监听，主要是设置点点的背景
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        mPager.setCurrentItem((mImageViews.length) * 100);
    }

    //将webp转化为bitmap
    private Bitmap showAlbum(int resourceID) {
        InputStream rawImageStream = getResources().openRawResource(resourceID);
        byte[] data = WebpUtils.streamToBytes(rawImageStream);
        Bitmap webpBitmap = WebpUtils.webpToBitmap(data);
        return webpBitmap;
    }

    /**
     * @author xiaanming
     */
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);

        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
            return mImageViews[position % mImageViews.length];
        }
    }
}
