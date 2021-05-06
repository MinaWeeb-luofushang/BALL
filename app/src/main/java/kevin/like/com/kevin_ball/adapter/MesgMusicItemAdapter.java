package kevin.like.com.kevin_ball.adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import kevin.like.com.kevin_ball.MusicPlay;
import kevin.like.com.kevin_ball.MyApplication;
import kevin.like.com.kevin_ball.R;
import kevin.like.com.kevin_ball.entity.MesgByMusicItem;

public class MesgMusicItemAdapter extends RecyclerView.Adapter<MesgMusicItemAdapter.ViewHolder> {
    private List<MesgByMusicItem> listData;
    private MesgByMusicItem getItem;
    private Context mContext;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mesg_music_listview_type1,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        getItem= listData.get(position);
        final String intor = getItem.musicIntro;
        holder.music_tv_item_number.setText(getItem.musicNumber);
        holder.music_tv_item.setText(getItem.musicIntro);
        if (intor.length()>12){
            String subIntro = holder.music_tv_item.getText().subSequence(0,13)+"...";
            holder.music_tv_item.setText(subIntro);
        }

        holder.imageLoader.displayImage(getItem.musicImgUlr,holder.music_iv_ig, MyApplication.setImageOptions());


        if (listener!=null){
            holder.music_iv_ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.myClick(view,position,listData.get(position));
            }
        });
        }
    }
    public MesgMusicItemAdapter (Context context, List<MesgByMusicItem> list) {
        mContext = context;
        listData = list;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView music_iv_ig;
        TextView music_tv_item_number;;
        TextView music_tv_item;
        ImageLoader imageLoader = ImageLoader.getInstance();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            music_iv_ig = itemView.findViewById(R.id.music_iv_ig);
            music_tv_item_number = itemView.findViewById(R.id.music_tv_item_number);
            music_tv_item = itemView.findViewById(R.id.music_tv_item);
        }
    }

    //设置给fragment 的点击监听
    private OnMyItemClickListener listener;

    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;
    }
    public interface OnMyItemClickListener{
        void myClick(View v,int pos,MesgByMusicItem mesg);
    }


}
