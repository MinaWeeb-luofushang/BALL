package kevin.like.com.kevin_ball.adapter;

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

import org.xutils.view.annotation.ContentView;

import java.util.List;

import kevin.like.com.kevin_ball.MyApplication;
import kevin.like.com.kevin_ball.R;
import kevin.like.com.kevin_ball.entity.MesgByMusicItem1;

@ContentView(R.layout.mesg_music_listview_type0)
public class MesgMusicItem1Adapter extends RecyclerView.Adapter<MesgMusicItem1Adapter.ViewHolder> {

    private List<MesgByMusicItem1> listData;
    private Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mesg_music_listview_type0,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final MesgByMusicItem1 entity = listData.get(position);
        holder.music_tv_title.setText(entity.Title);
        holder.music_tv_anchor.setText(entity.Anchor);
        holder.imageLoader.displayImage(entity.ImageUrl,holder.music_iv);
        holder.music_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener1.myClick(view,position,entity);
            }
        });
    }

    //设置callback 点击事件
    private OnMyItemClickListener listener1;

    public void setOnMyItemClickListener1(OnMyItemClickListener listener){
        this.listener1 = listener;
    }

    public interface OnMyItemClickListener{
        void myClick(View v,int pos,MesgByMusicItem1 mesg);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public MesgMusicItem1Adapter(Context context,List<MesgByMusicItem1> list){
        mContext = context;
        listData = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView music_iv;
        TextView music_tv_title;
        TextView music_tv_anchor;
        ImageLoader imageLoader = ImageLoader.getInstance();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            music_iv = itemView.findViewById(R.id.music_iv);
            music_tv_title = itemView.findViewById(R.id.music_tv_title);
            music_tv_anchor = itemView.findViewById(R.id.music_tv_anchor);
        }
    }

}
