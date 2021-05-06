package kevin.like.com.kevin_ball.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import kevin.like.com.kevin_ball.MyApplication;
import kevin.like.com.kevin_ball.R;
import kevin.like.com.kevin_ball.entity.MesgItem;

public class MesgNewAdapter extends MyBaseAdapter <MesgItem>{


    public MesgNewAdapter(Context c, List<MesgItem> datas){
        super(c,datas);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        EntityMesg entityMesg = null;
        if (entityMesg == null){
            entityMesg = new EntityMesg();
            convertView = layoutInflater.inflate(R.layout.mesg_new_listview,null);
            x.view().inject(entityMesg,convertView);
            convertView.setTag(entityMesg);
        }else {
            convertView.clearAnimation();
            entityMesg  = (EntityMesg) convertView.getTag();
        }
        entityMesg.mesg_tv_title.setText(datas.get(position).ItemTitle);
        entityMesg.mesg_tv_intro.setText(datas.get(position).ItemIntro);
        entityMesg.mesg_tv_type.setText(datas.get(position).ItemType);
        entityMesg.mesg_tv_manyperson.setText(datas.get(position).ManyPersonOnClick);
        entityMesg.imageLoader.displayImage(datas.get(position).ItemImageUrl,entityMesg.mesg_list_iv,MyApplication.setImageOptions());
        return convertView;
    }
    private class EntityMesg{
        @ViewInject(R.id.mesg_tv_title)
        TextView mesg_tv_title;
        @ViewInject(R.id.mesg_tv_intro)
        TextView mesg_tv_intro;
        @ViewInject(R.id.mesg_tv_type)
        TextView mesg_tv_type;
        @ViewInject(R.id.mesg_tv_manyperson)
        TextView mesg_tv_manyperson;
        @ViewInject(R.id.mesg_list_iv)
        ImageView mesg_list_iv;
        ImageLoader imageLoader = ImageLoader.getInstance();
    }
}
