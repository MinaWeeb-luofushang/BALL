package kevin.like.com.kevin_ball.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import kevin.like.com.kevin_ball.R;
import kevin.like.com.kevin_ball.adapter.MesgWatchAdapter;
import kevin.like.com.kevin_ball.fragment.mesg_fragment.MusicFragment;
import kevin.like.com.kevin_ball.fragment.mesg_fragment.NewFragment;
import kevin.like.com.kevin_ball.fragment.mesg_fragment.WatchFragment;

@ContentView(R.layout.fragment_mesg)
public class MesgFragment extends Fragment {
    @ViewInject(R.id.ball_mesg_tv_music)
    TextView ball_mesg_tv_music;
    @ViewInject(R.id.ball_mesg_tv_new)
    TextView ball_mesg_tv_new;
    @ViewInject(R.id.ball_mesg_tv_watch)
    TextView ball_mesg_tv_watch;
    @ViewInject(R.id.mesg_vp_content)
    ViewPager mesg_vp_content;

     //保存当前状态
    private int setItemIndex = 0;
    //实例化你这里的fragment
    private MusicFragment musicFragment;
    private NewFragment newFragment;
    private WatchFragment watchFragment;
    private List<Fragment> fragmentList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState!=null){
            setItemIndex = savedInstanceState.getInt("setItemIndex");
        }
        return x.view().inject(this,inflater,container);
    }
    private void initParams(){
        musicFragment = new MusicFragment();
        newFragment = new NewFragment();
        watchFragment = new WatchFragment();
        if (fragmentList.size()==0){
            fragmentList.add(newFragment);
            fragmentList.add(watchFragment);
            fragmentList.add(musicFragment);
        }
         //适配器绑定
        FragmentPagerAdapter  adapterPager = new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }
            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        mesg_vp_content.setAdapter(adapterPager);
        //设置当前的视图
        getItem(0);
    }

    //监听ViewPager左右滑动
    public class listenViewPager implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    getItem(0);
                    break;
                case 1:
                    getItem(1);
                    break;
                case 2:
                    getItem(2);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    //取得当前得下标设置显示类型 0:new,1:watch,2:music
    private void getItem(int item){
        setItemIndex = item;
        switch (item){
            case 0:
                ball_mesg_tv_new.setTextColor(getResources().getColor(R.color.colorRbtnBg));
                ball_mesg_tv_new.setBackgroundColor(getResources().getColor(R.color.colorTheme));
                ball_mesg_tv_watch.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_mesg_tv_watch.setBackgroundColor(getResources().getColor(R.color.colorListViewbg));
                ball_mesg_tv_music.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_mesg_tv_music.setBackgroundColor(getResources().getColor(R.color.colorListViewbg));
                mesg_vp_content.setCurrentItem(0);
                break;
            case 1:
                ball_mesg_tv_new.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_mesg_tv_new.setBackgroundColor(getResources().getColor(R.color.colorListViewbg));
                ball_mesg_tv_watch.setTextColor(getResources().getColor(R.color.colorRbtnBg));
                ball_mesg_tv_watch.setBackgroundColor(getResources().getColor(R.color.colorTheme));
                ball_mesg_tv_music.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_mesg_tv_music.setBackgroundColor(getResources().getColor(R.color.colorListViewbg));
                mesg_vp_content.setCurrentItem(1);
                break;
            case 2:
                ball_mesg_tv_new.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_mesg_tv_new.setBackgroundColor(getResources().getColor(R.color.colorListViewbg));
                ball_mesg_tv_watch.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_mesg_tv_watch.setBackgroundColor(getResources().getColor(R.color.colorListViewbg));
                ball_mesg_tv_music.setTextColor(getResources().getColor(R.color.colorRbtnBg));
                ball_mesg_tv_music.setBackgroundColor(getResources().getColor(R.color.colorTheme));
                mesg_vp_content.setCurrentItem(2);
                break;
            default:
                break;
        }
    }
    @Event(value = {R.id.ball_mesg_tv_watch,R.id.ball_mesg_tv_new,R.id.ball_mesg_tv_music})
    private void titleOnClick(View view){
        switch (view.getId()){
            case R.id.ball_mesg_tv_new:
                getItem(0);
                break;
            case R.id.ball_mesg_tv_watch:
                getItem(1);
                break;
            case R.id.ball_mesg_tv_music:
                getItem(2);
                break;
            default:
                break;
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState!=null){
            setItemIndex = savedInstanceState.getInt("setItemIndex");
        }
        initParams();
        mesg_vp_content.setOnPageChangeListener(new listenViewPager());
    }
    //当发生回收时 需要头部导航栏的状态
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("setItemIndex",setItemIndex);
    }
    //取得取得发生回收相关数据  状态 onActivityCreated
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState!=null){
            setItemIndex = savedInstanceState.getInt("setItemIndex");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getItem(setItemIndex);
    }
}
