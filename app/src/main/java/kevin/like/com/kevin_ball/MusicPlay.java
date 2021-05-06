package kevin.like.com.kevin_ball;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

@ContentView(R.layout.music_play)
public class MusicPlay extends Activity {
    @ViewInject(R.id.music_tv_time)
    TextView music_tv_time;
    @ViewInject(R.id.music_item_cpb)
    CircularProgressBar music_item_cpb;
    @ViewInject(R.id.music_item_civ)
    CircleImageView music_item_civ;

    @ViewInject(R.id.music_song_name)
    TextView music_song_name;
    @ViewInject(R.id.music_song_author)
    TextView music_song_author;

    @ViewInject(R.id.music_iv_random)
    ImageView music_iv_random;
    @ViewInject(R.id.music_iv_loop)
    ImageView music_iv_loop;
    @ViewInject(R.id.music_iv_single)
    ImageView music_iv_single;

    @ViewInject(R.id.music_iv_last)
    ImageView music_iv_last;
    @ViewInject(R.id.music_iv_play)
    ImageView music_iv_play;
    @ViewInject(R.id.music_iv_next)
    ImageView music_iv_next;



    public   MediaPlayer mediaPlayer;
    private float setPro = 0;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colormusicBg));
        x.view().inject(this);
        Intent getIntent = getIntent();
        String musicMp3Url = getIntent.getStringExtra("musicMp3Url");
        String musicName = getIntent.getStringExtra("musicName");
        String musicSinger = getIntent.getStringExtra("musicSinger");
        String musicImg  = getIntent.getStringExtra("musicImgUlr");
//        System.out.println("是否在播放 状态是:"+mediaPlayer.isPlaying());
        imageLoader.displayImage(musicImg,music_item_civ);

        if (musicMp3Url!=null){
            music_tv_time.setText("点击播放");
            MusicPlayer(musicMp3Url);
            music_song_name.setText(musicName);
            music_song_author.setText(musicSinger);
        }


    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    music_iv_play.setImageDrawable(getResources().getDrawable(R.drawable.music_btn_play));
                    break;
                case 101:
                    music_iv_play.setImageDrawable(getResources().getDrawable(R.drawable.music_btn_stop));
                    break;
                case 102:
                    music_item_cpb.setProgress(setPro);
                    break;
                default:
                    break;
            }
        }
    };
    //播放暂停
    private void setMusicPlay(){
        Message message = new Message();
        if(mediaPlayer.isPlaying()){
            message.what=100;
            mediaPlayer.pause();
        }else {
            message.what=101;
            mediaPlayer.start();
        }
        handler.sendMessage(message);
        SetMusicTime(mediaPlayer.getDuration()/1000);
    }

    //监听点击事件
    @Event(value = {R.id.music_iv_play,R.id.music_iv_next,R.id.music_iv_random,R.id.music_iv_loop,R.id.music_iv_single})
    private void listenOnClick(View view){
        switch (view.getId()){
            case R.id.music_iv_play:
                setMusicProgress();
                setMusicPlay();
                break;
            case R.id.music_iv_last:
                break;
            case R.id.music_iv_next:
//                if (mediaPlayer.isPlaying()){
//                    mediaPlayer.pause();
//                    mediaPlayer.stop();
//                    mediaPlayer.release();
//                }
//                String aa = "http://192.168.254.109:9090/kevin_music/%E5%91%A8%E6%9D%B0%E5%80%AB%20%20-%20%E4%B8%80%E8%B7%AF%E5%90%91%E5%8C%97.mp3";
//                MusicPlayer(aa);
                break;

            case R.id.music_iv_random:
                music_iv_random.setImageDrawable(getResources().getDrawable(R.drawable.random_true));
                music_iv_loop.setImageDrawable(getResources().getDrawable(R.drawable.loop_false));
                music_iv_single.setImageDrawable(getResources().getDrawable(R.drawable.single_false));
                break;
            case R.id.music_iv_loop:
                music_iv_random.setImageDrawable(getResources().getDrawable(R.drawable.random_false));
                music_iv_loop.setImageDrawable(getResources().getDrawable(R.drawable.loop_true));
                music_iv_single.setImageDrawable(getResources().getDrawable(R.drawable.single_false));
                break;
            case R.id.music_iv_single:
                music_iv_random.setImageDrawable(getResources().getDrawable(R.drawable.random_false));
                music_iv_loop.setImageDrawable(getResources().getDrawable(R.drawable.loop_false));
                music_iv_single.setImageDrawable(getResources().getDrawable(R.drawable.single_true));
                break;
            default:
                break;
        }
    }

    //监听播放进度
    private void setMusicProgress(){
        if (mediaPlayer!=null){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        while (mediaPlayer.isPlaying()){
                            Message message = new Message();
                            Thread.sleep(1200);
                            message.what = 102;
                            setPro = mediaPlayer.getCurrentPosition()*100/mediaPlayer.getDuration();
                            handler.sendMessage(message);
//                            System.out.println("这这这这"+setPro);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();
        }

    }
    //进行播放
    private void MusicPlayer(String musicUrl){
        mediaPlayer = new MediaPlayer();
        try {
           mediaPlayer.setDataSource(musicUrl);
            mediaPlayer.prepareAsync();
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setMusicProgress();
    }
    //处理时间 限制10 分钟之内的歌曲
    private void SetMusicTime(double time){
        String getTime = ""+time / 60;
        String min = getTime.substring(0,1);

        String second = "0"+getTime.substring(1);//加0 是因为后面是字符已经有点了
        double setSecond = Double.valueOf(second)*60;
        String getSecond = ""+setSecond;
        if (getSecond.substring(0,2).indexOf(".") ==1){
            music_tv_time.setText(min+":0"+getSecond.substring(0,1));
        }else {
            music_tv_time.setText(min+":"+getSecond.substring(0,2));
        }
    }

    public void come_back(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.pause();
        mediaPlayer.stop();
        mediaPlayer.release();
//        finish();
    }




}
