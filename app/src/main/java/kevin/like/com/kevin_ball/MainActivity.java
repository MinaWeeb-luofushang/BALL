package kevin.like.com.kevin_ball;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.melnykov.fab.FloatingActionButton;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import kevin.like.com.kevin_ball.fragment.HappyFragment;
import kevin.like.com.kevin_ball.fragment.HomeFragment;
import kevin.like.com.kevin_ball.fragment.MesgFragment;
import kevin.like.com.kevin_ball.fragment.SelfFragment;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.ball_llt_mesg)
    LinearLayout ball_llt_mesg;
    @ViewInject(R.id.ball_llt_home)
    LinearLayout ball_llt_home;
    @ViewInject(R.id.ball_llt_hap)
    LinearLayout ball_llt_hap;
    @ViewInject(R.id.ball_llt_self)
    LinearLayout ball_llt_self;

    @ViewInject(R.id.ball_iv_home)
    ImageView ball_iv_home;
    @ViewInject(R.id.ball_iv_mesg)
    ImageView ball_iv_mesg;
    @ViewInject(R.id.ball_iv_self)
    ImageView ball_iv_self;
    @ViewInject(R.id.ball_iv_hap)
    ImageView ball_iv_hap;

    @ViewInject(R.id.ball_tv_home)
    TextView ball_tv_home;
    @ViewInject(R.id.ball_tv_mesg)
    TextView ball_tv_mesg;
    @ViewInject(R.id.ball_tv_self)
    TextView ball_tv_self;
    @ViewInject(R.id.ball_tv_hap)
    TextView ball_tv_hap;
    @ViewInject(R.id.ball_flt)
    FrameLayout ball_flt;

    @ViewInject(R.id.nav_view)
    NavigationView nav_view;
    @ViewInject(R.id.ft_btn)
    FloatingActionButton ft_btn;
    @ViewInject(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    private int setNvaTxIndex = 0;
    private FragmentManager fm = getSupportFragmentManager();
    private FragmentTransaction ft ;
    private MesgFragment mesgFragment = new MesgFragment();
    private HomeFragment homeFragment = new HomeFragment();
    private HappyFragment HappyFragment = new HappyFragment();
    private SelfFragment selfFragment = new SelfFragment();
    //是否发生了回收
    private boolean isRecycle;
    private AlertDialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //使得状态栏透明
//        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//        | localLayoutParams.flags);
        //直接设置颜色
//        getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        x.view().inject(this);
        if (savedInstanceState==null){
            viewOnClick(ball_iv_mesg);
            navImgTvChange(setNvaTxIndex);
            ft.replace(R.id.ball_flt,mesgFragment).commitNowAllowingStateLoss();
        }
        listenNavItem();
        drawer_layout.addDrawerListener(new DrawerViewListener());

    }
    //绑定点击事件
    @Event(value = {R.id.ball_llt_mesg,R.id.ball_llt_home,R.id.ball_llt_hap,R.id.ball_llt_self,R.id.ft_btn},type = View.OnClickListener.class)
    private void viewOnClick(View view){
        ft = fm.beginTransaction();
        switch (view.getId()){
            case R.id.ball_llt_mesg:
                System.out.println("MESG");
                setNvaTxIndex=0;
                ft.replace(R.id.ball_flt,mesgFragment).commitNowAllowingStateLoss();
                break;
            case R.id.ball_llt_home:
                System.out.println("HOME");
                setNvaTxIndex=1;
                ft.replace(R.id.ball_flt,homeFragment).commitNowAllowingStateLoss();
                break;
            case R.id.ball_llt_hap:
                System.out.println("ORDER");
                ft.replace(R.id.ball_flt,HappyFragment).commitNowAllowingStateLoss();
                setNvaTxIndex=2;
                break;
            case R.id.ball_llt_self:
                System.out.println("SELF");
                ft.replace(R.id.ball_flt,selfFragment).commitNowAllowingStateLoss();
                setNvaTxIndex=3;
                break;
            case R.id.ft_btn:
//                System.out.println("点击了浮动按钮");
                nav_view.setCheckedItem(R.id.nav_call);
                drawer_layout.openDrawer(nav_view);
                break;
            default:
                break;
        }
        navImgTvChange(setNvaTxIndex);
    }

    //监听左侧菜单是否打开 旋转悬浮按钮角度
    private class DrawerViewListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            //滑动时调用
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            //打开菜单时调用
            ft_btn.animate().rotation(90).setDuration(300);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            //关闭菜单时调用
            ft_btn.animate().rotation(0).setDuration(300);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            //菜单状态改变时调用
        }
    }

    //给导航栏改变颜色 和图片的切换
    private void navImgTvChange(int index){
        switch (index){
            case 0:
                ball_iv_mesg.setSelected(true);
                ball_tv_mesg.setTextColor(getResources().getColor(R.color.colorMainNavTxTrue));
                ball_iv_home.setSelected(false);
                ball_tv_home.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_iv_hap.setSelected(false);
                ball_tv_hap.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_iv_self.setSelected(false);
                ball_tv_self.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                break;
            case 1:
                ball_iv_mesg.setSelected(false);
                ball_tv_mesg.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_iv_home.setSelected(true);
                ball_tv_home.setTextColor(getResources().getColor(R.color.colorMainNavTxTrue));
                ball_iv_hap.setSelected(false);
                ball_tv_hap.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_iv_self.setSelected(false);
                ball_tv_self.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                break;
            case 2:
                ball_iv_mesg.setSelected(false);
                ball_tv_mesg.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_iv_home.setSelected(false);
                ball_tv_home.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_iv_hap.setSelected(true);
                ball_tv_hap.setTextColor(getResources().getColor(R.color.colorMainNavTxTrue));
                ball_iv_self.setSelected(false);
                ball_tv_self.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));;
                break;
            case 3:
                ball_iv_mesg.setSelected(false);
                ball_tv_mesg.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_iv_home.setSelected(false);
                ball_tv_home.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_iv_hap.setSelected(false);
                ball_tv_hap.setTextColor(getResources().getColor(R.color.colorMainNavTxFalse));
                ball_iv_self.setSelected(true);
                ball_tv_self.setTextColor(getResources().getColor(R.color.colorMainNavTxTrue));
                break;
            default:
                break;

        }
    }

    //监听退出app
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.Transparent);
        dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setContentView(R.layout.activity_exit_game_dialog);

        TextView textView = dialog.findViewById(R.id.exit_dialog_text);
        Button cancelBtn = dialog.findViewById(R.id.cancal_button);
        Button exitBtn = dialog.findViewById(R.id.exit_button);
        exitBtn.setText("确 认");
        cancelBtn.setText("取 消");


        textView.setText("不想再逛逛么 亲");
        Display d = getWindowManager().getDefaultDisplay();


        int h,w,tSp;
        tSp = (int)(d.getHeight() * 0.00936);

        if(d.getWidth() > d.getHeight()) {
            h = (int) (d.getHeight() * 0.67);
            //if (h >= 892) h = 892;
            w = (int) (h * 1.35);
            textView.setTextSize(15);
        }else{
            w = (int)(d.getWidth() * 0.8);
            h = (int)(w * 0.626);
            textView.setTextSize(15);

            cancelBtn.setTextSize((float) (tSp*0.6));
            exitBtn.setTextSize((float) (tSp*0.6));
        }

        dialog.getWindow().setLayout(w, h);

        return;
    }

    public void dismiss(View view){
        dialog.dismiss();
    }

    public void exit_true(View view){
        dialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    //设置左侧菜单的点击监听 setNavigationItemSelectedListener
    private void listenNavItem(){
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_call:
                        nav_view.setCheckedItem(R.id.nav_call);
                        break;
                    case R.id.nav_friends:
                        nav_view.setCheckedItem(R.id.nav_friends);
                        break;
                    case R.id.nav_location:
                        nav_view.setCheckedItem(R.id.nav_location);
                        break;
                    case R.id.nav_mail:
                        nav_view.setCheckedItem(R.id.nav_mail);
                        break;
                    case R.id.nav_task:
                        nav_view.setCheckedItem(R.id.nav_task);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
    //当发生回收时 需要记录导航栏的状态
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRecycle",true);
        outState.putInt("setNvaTxIndex",setNvaTxIndex);
    }

    //取得取得发生回收相关数据  状态
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isRecycle = savedInstanceState.getBoolean("isRecycle");
        setNvaTxIndex = savedInstanceState.getInt("setNvaTxIndex");
    }
//    恢复到之前状态 （旋转屏幕的bug 就是这样解决）
    @Override
    protected void onResume() {
        super.onResume();
        if (isRecycle){
            navImgTvChange(setNvaTxIndex);
        }
    }


}
