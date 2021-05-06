package kevin.like.com.kevin_ball.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kevin.like.com.kevin_ball.R;
import kevin.like.com.kevin_ball.entity.MesgByWatch;
import kevin.like.com.kevin_ball.fragment.mesg_fragment.WatchFragment;

public class MesgWatchAdapter extends RecyclerView.Adapter<MesgWatchAdapter.ViewHolder> {


    private List<MesgByWatch> listdata;
    private Context Mcontext;
    private MesgByWatch getItem;
    private VideoView videoView1;
    private Integer getVideoNext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mesg_watch_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MesgWatchAdapter.ViewHolder holder, final int position) {
        getVideoNext = position;
        videoView1 = null;

        getItem = listdata.get(getVideoNext);
        holder.watch_item_vv.setVideoURI(Uri.parse(getItem.VideoUrl));
        holder.watch_item_id.setText(getItem.VideoId);
        holder.watch_item_intro.setText(getItem.VideoIntro);
//        holder.watch_item_vv.setMediaController(new MediaController(Mcontext));
//        holder.watch_item_vv.start();
        videoView1 = holder.watch_item_vv;
        holder.watch_item_vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.watch_item_vv.isPlaying()){
                    holder.watch_item_vv.pause();
                }else {
                    holder.watch_item_vv.start();
                }
                videoView1 = holder.watch_item_vv;
            }
        });

        holder.watch_item_vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                listener.videoOverListen(mediaPlayer,position,listdata.size());
                videoView1 = holder.watch_item_vv;
            }
        });
    }


    public MesgWatchAdapter(Context context, List<MesgByWatch> list){
        listdata = list;
        Mcontext  = context;
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView watch_item_id;
        TextView watch_item_intro;
        VideoView watch_item_vv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            watch_item_id = itemView.findViewById(R.id.watch_item_id);
            watch_item_intro = itemView.findViewById(R.id.watch_item_intro);
            watch_item_vv = itemView.findViewById(R.id.watch_item_vv);
        }
    }


    //监听视频播放是否完成
    private OnMyItemVideoListener listener;
    public void setOnMyItemClickListener(OnMyItemVideoListener listener){
        this.listener = listener;
    }
    public interface OnMyItemVideoListener{
        void videoOverListen(MediaPlayer v,int pos,Integer listSize);
    }

    //判断是否在显示 在就播放 不在就停止
    public void VideoPlay(boolean IsPlay){
        if (IsPlay){
            videoView1.start();
        }else {
            videoView1.pause();
        }
    }

}
