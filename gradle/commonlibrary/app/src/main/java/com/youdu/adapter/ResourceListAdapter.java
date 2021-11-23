package com.youdu.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

/**
 * 创建时间:  2017/06/12 16:21 <br>
 * 作者:  renzhiqiang <br>
 * 描述: 通过泛型来抽象一个父adapter出来，真正的业务逻辑adapter实现显示与交互即可。
 */
public class ResourceListAdapter<T> extends BaseAdapter {

  private Context mContext;
  private LayoutInflater mInflaterLayout;
  private int mResId;
  private List<T> mData;

  public ResourceListAdapter(Context context, @LayoutRes int resId) {
    this(context, resId, null);
  }

  public ResourceListAdapter(Context context, @LayoutRes int resId, List<T> data) {
    mContext = context;
    mInflaterLayout = LayoutInflater.from(mContext);
    mResId = resId;
    mData = data;
  }

  @Override public T getItem(int position) {
    return mData.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public int getCount() {
    return mData == null ? 0 : mData.size();
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = mInflaterLayout.inflate(mResId, parent, false);
    }
    bindView(convertView, position, getItem(position));
    return null;
  }

  public void updateDatas(List<T> datas) {
    this.mData.addAll(datas);
    notifyDataSetChanged();
  }

  public void updateData(T data) {
    this.mData.add(data);
    notifyDataSetChanged();
  }

  //子类完成不同的处理
  public void bindView(View convertView, int position, T item) {

  }
}
