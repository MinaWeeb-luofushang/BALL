package kevin.like.com.kevin_ball.fragment.mesg_fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import kevin.like.com.kevin_ball.MusicPlay;
import kevin.like.com.kevin_ball.R;
import kevin.like.com.kevin_ball.adapter.MesgMusicItem1Adapter;
import kevin.like.com.kevin_ball.adapter.MesgMusicItemAdapter;
import kevin.like.com.kevin_ball.entity.MesgByMusic;
import kevin.like.com.kevin_ball.entity.MesgByMusicItem;
import kevin.like.com.kevin_ball.entity.HttpCom;
import kevin.like.com.kevin_ball.entity.MesgByMusicItem1;

@ContentView(R.layout.mesg_music_fragment)
public class MusicFragment extends Fragment {
    @ViewInject(R.id.mesg_music_rv_type_one)
    RecyclerView mesg_music_rv_type_one;
    @ViewInject(R.id.mesg_music_rv_type_two)
    RecyclerView mesg_music_rv_type_two;
    @ViewInject(R.id.music_tv_type)
    TextView music_tv_type;
    @ViewInject(R.id.music_tv_more)
    TextView music_tv_more;

    @ViewInject(R.id.llt_isrequest)
    LinearLayout llt_isrequest;
    @ViewInject(R.id.music_ll_update)
    LinearLayout music_ll_update;
    @ViewInject(R.id.music_banner)
    Banner music_banner;


    //轮播图url数组
    private ArrayList<String> bannerList;
    private HttpCom com = new HttpCom();
    private String dataUrl = com.httpCom+"music_json.html";
    //适配器
    private MesgMusicItemAdapter ItemAdapter;
    //数据源
    private List<MesgByMusic> datas = new ArrayList<>();
    //需要当实体类
    private List<MesgByMusicItem> entity = new ArrayList<>();
    //实体类2
    private List<MesgByMusicItem1> entity1 = new ArrayList<>();
    //适配器1
    private MesgMusicItem1Adapter Item1Adapter;

    //存储内部的数组
    private JSONArray itemArray;
    //取到所有数据
    private JSONArray jsonArray;
    //取到该对象
    private JSONObject jsonObject;
    //取当前数据已经显示的长度
    private int getDataShowed;
    //记录当前显示到的下标
    private  int showIndex = 0;
    //请求失败的次数
    private int errorNumber = 0;
    private StaggeredGridLayoutManager linearLayoutManager;
    private Intent musicPlayIntent = new Intent();

    private List<MesgByMusic> arrResults;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }
    //设置点击事件
    @Event(value = R.id.music_ll_update)
    private void updateOnClick(View view){
        switch (view.getId()){
            case R.id.music_ll_update:
                getNextData(getDataShowed);
                break;
            default:
                break;
        }
    }

    //切换数据到下一个
    private void getNextData(int dataLengh){
        llt_isrequest.setVisibility(View.VISIBLE);
        datas.clear();
        entity.clear();
        ItemAdapter = null;
        showIndex = showIndex+1;
        try {
            if(showIndex<dataLengh){
                jsonObject = new JSONObject(jsonArray.get(showIndex).toString());
                MesgByMusic mesgByMusic = new MesgByMusic();
                MesgByMusicItem mesgByMusicItem = null;
                mesgByMusic.TitleType = jsonObject.getString("TitleType");
                mesgByMusic.MusicType = jsonObject.getString("MusicType");
                JSONArray itemArray = null;
                itemArray = jsonObject.getJSONArray("data");
                jsonObject = null;
                for (int i=0;i<itemArray.length();i++){
                    mesgByMusicItem = new MesgByMusicItem();
                    jsonObject =new JSONObject(String.valueOf(itemArray.get(i)));
                    mesgByMusicItem.musicImgUlr = jsonObject.getString("musicImgUlr");
                    mesgByMusicItem.musicIntro = jsonObject.getString("musicIntro");
                    mesgByMusicItem.musicMp3Url = jsonObject.getString("musicMp3Url");
                    mesgByMusicItem.musicNumber = jsonObject.getString("musicNumber");
                    mesgByMusicItem.musicName = jsonObject.getString("musicName");
                    mesgByMusicItem.musicSinger = jsonObject.getString("musicSinger");
                    entity.add(mesgByMusicItem);
                }
                music_tv_type.setText(mesgByMusic.TitleType);
                ItemAdapter = new MesgMusicItemAdapter(getActivity(),entity);
                setSeelp(2000);
                getMusicItem();
            }else {
                showIndex = -1;
                getNextData(getDataShowed);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //使用线程完成的一些东西比如延迟的效果
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 9090:
                    llt_isrequest.setVisibility(View.GONE);
                    mesg_music_rv_type_one.setAdapter(ItemAdapter);
                    break;
                default:
                    break;
            }
        }
    };
    //延迟数据请求
    private void setSeelp(final int time){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(time);
                    Message message = new Message();
                    message.what = 9090;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //禁止滚动
    private void stopListScroll(){
        mesg_music_rv_type_one.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        return true;
                    default:
                        break;
                }
                return true;
            }
        });

        mesg_music_rv_type_two.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        return true;
                    default:
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData(dataUrl);
//        stopListScroll();
        setBanner();
        getMusicItem();
    }

    //点击监听进入音乐播放界面
    private void getMusicItem(){

        if (ItemAdapter!=null){
            ItemAdapter.setOnMyItemClickListener(new MesgMusicItemAdapter.OnMyItemClickListener() {
                @Override
                public void myClick(View v, int pos, MesgByMusicItem mesg) {

                    MusicPlay musicPlay = new MusicPlay();
                    musicPlayIntent.setClass(getActivity(), musicPlay.getClass());
                    musicPlayIntent.putExtra("musicMp3Url",mesg.musicMp3Url);
                    musicPlayIntent.putExtra("musicImgUlr",mesg.musicImgUlr);
                    musicPlayIntent.putExtra("musicIntro",mesg.musicIntro);
                    musicPlayIntent.putExtra("musicNumber",mesg.musicNumber);
                    musicPlayIntent.putExtra("musicSinger",mesg.musicSinger);
                    musicPlayIntent.putExtra("musicName",mesg.musicName);
                    startActivity(musicPlayIntent);
                }
            });
            Item1Adapter.setOnMyItemClickListener1(new MesgMusicItem1Adapter.OnMyItemClickListener() {
                @Override
                public void myClick(View v, int pos, MesgByMusicItem1 mesg) {
                    System.out.println("test:   "+mesg.ItemConUrl);
                    MusicPlay musicPlay = new MusicPlay();
                    musicPlayIntent.setClass(getActivity(), musicPlay.getClass());
                    musicPlayIntent.putExtra("musicMp3Url",mesg.ItemConUrl);
                    musicPlayIntent.putExtra("musicImgUlr",mesg.ImageUrl);
//                    musicPlayIntent.putExtra("musicIntro",mesg.Title);
//                    musicPlayIntent.putExtra("musicNumber",mesg.Title);
                    musicPlayIntent.putExtra("musicSinger",mesg.Anchor);
                    musicPlayIntent.putExtra("musicName",mesg.Title);
                    startActivity(musicPlayIntent);
                }
            });

        }
    }

    //数据请求
    private void getData(final String url){
        music_ll_update.setVisibility(View.GONE);
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    jsonArray = result.getJSONArray("data");
                    setListType0(result.getJSONArray("anchor"));
                    if (jsonArray.length()>0){
                        getDataShowed = jsonArray.length();
                        llt_isrequest.setVisibility(View.GONE);
                        if (ItemAdapter!=null){
                            mesg_music_rv_type_one.setAdapter(ItemAdapter);
                            music_ll_update.setVisibility(View.VISIBLE);
                        }else {
                            MesgByMusic mesgByMusic = new MesgByMusic();
                            MesgByMusicItem mesgByMusicItem = null;
                            jsonObject = null;
                            jsonObject = new JSONObject(result.getJSONArray("data").get(0).toString());
                            mesgByMusic.TitleType = jsonObject.getString("TitleType");
                            mesgByMusic.MusicType = jsonObject.getString("MusicType");
                            itemArray = jsonObject.getJSONArray("data");
                            for (int i=0;i<itemArray.length();i++){
                                mesgByMusicItem = new MesgByMusicItem();
                                jsonObject = null;
                                jsonObject =new JSONObject(itemArray.get(i).toString());
                                mesgByMusicItem.musicImgUlr = jsonObject.getString("musicImgUlr");
                                mesgByMusicItem.musicIntro = jsonObject.getString("musicIntro");
                                mesgByMusicItem.musicMp3Url = jsonObject.getString("musicMp3Url");
                                mesgByMusicItem.musicNumber = jsonObject.getString("musicNumber");
                                mesgByMusicItem.musicName = jsonObject.getString("musicName");
                                mesgByMusicItem.musicSinger = jsonObject.getString("musicSinger");
                                entity.add(mesgByMusicItem);
                            }
                            music_tv_type.setText(mesgByMusic.TitleType);
                            ItemAdapter = new MesgMusicItemAdapter(getActivity(),entity);
                            mesg_music_rv_type_one.setAdapter(ItemAdapter);
                            music_ll_update.setVisibility(View.VISIBLE);
                            getMusicItem();
                        }
                    }
                    else {
//                        System.out.println("没有数据了");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //每一行多少个
                linearLayoutManager= new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                mesg_music_rv_type_one.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                errorNumber = errorNumber+1;
                if(errorNumber<2){
                    //请求第二次
                    getData(dataUrl);
                }else {
                    //不再请求
                }
            }


            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    //图片加载器
    private class MyBanderLoader extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }
    }
    //轮播图
    private void setBanner(){
        bannerList = new ArrayList<>();
        bannerList.add("http://192.168.245.1:9090/img/music_img/music_set_img2.png");
        bannerList.add("http://192.168.245.1:9090/img/music_img/music_set_img4.jpg");
        bannerList.add("http://192.168.245.1:9090/img/music_img/music_set_img6.png");
        bannerList.add("http://192.168.245.1:9090/img/music_img/music_set_img9.png");
        music_banner.setImageLoader(new MyBanderLoader());
        music_banner.setBannerAnimation(Transformer.Default);
        //设置轮播间隔时间
        music_banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是true
        music_banner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        music_banner.setIndicatorGravity(BannerConfig.CENTER);
        //判断是否为第一次 显示
        if(bannerList.size()!=0){
            music_banner.setImages(bannerList).start();
        }

    }

    //list类型2
    private  void setListType0(JSONArray jsonArray)  {
        MesgByMusicItem1 entityType1 = null;
        if (Item1Adapter==null){
            try {

                for (int i=0;i<=jsonArray.length();i++){
                    JSONObject object = null;
                    entityType1 = new MesgByMusicItem1();
                    object = jsonArray.getJSONObject(i);
                    entityType1.Anchor = object.getString("Anchor");
                    entityType1.ImageUrl = object.getString("ImageUrl");
                    entityType1.Title = object.getString("Title");
                    entityType1.ItemConUrl = object.getString("ItemConUrl");
                    entity1.add(entityType1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            Item1Adapter = new MesgMusicItem1Adapter(getActivity(),entity1);
            mesg_music_rv_type_two.setAdapter(Item1Adapter);
        }else {
            mesg_music_rv_type_two.setAdapter(Item1Adapter);
        }
        //每一行多少个
        linearLayoutManager= new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mesg_music_rv_type_two.setLayoutManager(linearLayoutManager);

    }
}
