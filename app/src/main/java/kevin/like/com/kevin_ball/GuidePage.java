package kevin.like.com.kevin_ball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

@ContentView(R.layout.activity_guide_page)
public class GuidePage extends Activity implements ViewPager.OnPageChangeListener{
    @ViewInject(R.id.vp_guide_content)
     ViewPager vp_guide_content;
    @ViewInject(R.id.llt_guide)
    LinearLayout llt_guide;
    @ViewInject(R.id.btn_guide)
    Button btn_guide;

    private ArrayList<View> views = new ArrayList<>();
    private ImageView[] imageViews;
    private int currentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initViews();
        initPoint();
//        startActivity(new Intent(GuidePage.this,MainActivity.class));
//        finish();
    }
    private void initViews(){
        views.add(getLayoutInflater().inflate(R.layout.guide_page1,null));
        views.add(getLayoutInflater().inflate(R.layout.guide_page2,null));
        views.add(getLayoutInflater().inflate(R.layout.guide_page3,null));
        views.add(getLayoutInflater().inflate(R.layout.guide_page4,null));
        //注册滑动监听
        vp_guide_content.setOnPageChangeListener(this);
        vp_guide_content.setAdapter(new MyPagerAdapter());
        btn_guide.setVisibility(View.GONE);
        btn_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuidePage.this,MainActivity.class));
                finish();
            }
        });
    }
    private void initPoint(){
        imageViews = new ImageView[views.size()];
        for (int i=0;i<imageViews.length;i++){
            imageViews[i] = (ImageView) llt_guide.getChildAt(i);

        }
        currentIndex = 0;
        imageViews[currentIndex].setImageResource(R.drawable.guide_undefult);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        System.out.println("这里是滑动的地方"+position);
        imageViews[currentIndex].setImageResource(R.drawable.guide_defult);
        imageViews[position].setImageResource(R.drawable.guide_undefult);
        currentIndex = position;
        if(position==3){
            btn_guide.setVisibility(View.VISIBLE);
        }else {
            btn_guide.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    //适配器
    class MyPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return views.size();
        }

        //实例化选项卡
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View v = views.get(position);
            container.addView(v);
            return v;
        }
        //删除选项卡
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));
        }
        //判断视图是否为返回的对象
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }
    }

}
