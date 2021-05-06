package kevin.like.com.kevin_ball.fragment.mesg_fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import kevin.like.com.kevin_ball.R;
import kevin.like.com.kevin_ball.adapter.MesgWatchAdapter;
import kevin.like.com.kevin_ball.entity.MesgByWatch;

@ContentView(R.layout.mesg_watch_fragment)
public class WatchFragment extends Fragment {

    @ViewInject(R.id.watch_vp2)
    ViewPager2 watch_vp2;

    private List<MesgByWatch> listWatch  = new ArrayList<>();
    private MesgWatchAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

//    public void getVideoState(boolean state){
//        if (adapter!=null){
//        adapter.setVideoState(state);
//        }
//    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser){
//            if (adapter!=null){
//                adapter.setVideoState(false);
//            }
//        }else {
//            if (adapter!=null){
//                adapter.setVideoState(true);
//            }
//        }
//    }

    private void initData(){
        for (int i=0;i<=9; i++){
            MesgByWatch mesgByWatch = new MesgByWatch();
            mesgByWatch.VideoId = "131452"+i;
            mesgByWatch.VideoIntro = "我们经常忽略那些疼爱我们的人，却疼爱着那些忽略我们的人。"+i;
            mesgByWatch.VideoUrl = "http://192.168.245.1:9090/kevin_video/%E9%A2%84%E8%A7%81%E6%9C%AA%E6%9D%A5.mp4";
            listWatch.add(mesgByWatch);
        }
        adapter = new MesgWatchAdapter(getActivity(),listWatch);
        watch_vp2.setAdapter(adapter);
        ListenVideoOver();

    }

    private MediaPlayer mediaPlayer;
    //监听视频播放结束的动作
    public void ListenVideoOver(){
        if (adapter!=null){
            adapter.setOnMyItemClickListener(new MesgWatchAdapter.OnMyItemVideoListener() {
                @Override
                public void videoOverListen(MediaPlayer v, int pos,Integer lenght) {
                    if (pos<lenght){
                        watch_vp2.setCurrentItem(pos+1);
                        v.start();
                    }else {
                        v.stop();
                    }
                }
            });
        }
    }


    //监听是否在当前页面
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (adapter!=null){
            System.out.println("这里是判断fragment 是否显示的地方"+menuVisible);
                adapter.VideoPlay(menuVisible);
        }
    }

}
