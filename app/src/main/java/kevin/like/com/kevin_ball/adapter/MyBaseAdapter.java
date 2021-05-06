package kevin.like.com.kevin_ball.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import kevin.like.com.kevin_ball.entity.MesgItem;

public class MyBaseAdapter<T> extends BaseAdapter {
    protected Context c = null;
    protected LayoutInflater layoutInflater = null;
    protected  List<T> datas = null;
    public MyBaseAdapter(Context c, List<T> datas) {
        this.c = c;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

}
