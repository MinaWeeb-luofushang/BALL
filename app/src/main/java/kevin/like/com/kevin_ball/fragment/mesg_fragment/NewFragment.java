package kevin.like.com.kevin_ball.fragment.mesg_fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import kevin.like.com.kevin_ball.NewPage;
import kevin.like.com.kevin_ball.R;
import kevin.like.com.kevin_ball.adapter.MesgNewAdapter;
import kevin.like.com.kevin_ball.entity.MesgItem;
import kevin.like.com.kevin_ball.entity.HttpCom;

@ContentView(R.layout.mesg_new_fragment)
public class NewFragment extends Fragment {
    @ViewInject(R.id.ball_mesg_lv)
    ListView ball_mesg_lv;
    @ViewInject(R.id.llt_isrequest)
    LinearLayout llt_isrequest;
    @ViewInject(R.id.tv_load)
    TextView tv_load;
    //数据源
    private List<MesgItem> datas = new ArrayList<>();
    //适配器
    private MesgNewAdapter adapter;
    //是否为最后一行
    private  boolean isLastRow = false;
    //是否还有更多数据
    private boolean isMoreData = true;
    //是否正在加载数据
    private boolean isLoading = false;
    //加载布局
    private LinearLayout loading_llyt;
    //记录滚动到哪一个
    private int getfist;
    //取到所有数据
    private JSONArray jsonArray;
    //取到该对象
    private JSONObject jsonObject;
    //取当前数据已经显示的长度
    private int getDataShowed;
    private HttpCom com = new HttpCom();
    private String dataUrl = com.httpCom+"ball_json.html";
    //请求失败的次数
    private int errorNumber = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData(dataUrl);
        listenerListVew();
        //监听点击
        ball_mesg_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    System.out.println("test:    "+jsonArray.get(i));
                    Intent intent = new Intent(getActivity(), NewPage.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(),"点击了第几个："+i,Toast.LENGTH_LONG).show();
            }
        });
    }


    private void listenerListVew(){
        //底部布局
        loading_llyt =(LinearLayout) getLayoutInflater().inflate(R.layout.listview_loading_view,null);
        ball_mesg_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int  scrollState) {
                // 不滚动时保存当前滚动到的位置
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    getfist = ball_mesg_lv.getFirstVisiblePosition();
                }
                //既到底部也停止状态
                if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    //判断是否在加载中 而且判断是否还有数据
                    addArray(getDataShowed);
                    isLastRow = false;
                }
            }
            @Override
            public void onScroll(AbsListView absListView, int fistItem, int visibleItem, int totalItemCount) {
                if (fistItem+visibleItem==totalItemCount && totalItemCount !=0){
                    isLastRow = true;
                }
            }
        });
    }
    //不是第一次使用所有往数组里面添加数据就好了
    private void addArray(int index){
        llt_isrequest.setVisibility(View.GONE);
//        System.out.println("如果拉到的下标和数据总量一样的长度的时候就不再往数组里面添加了"+index);
        //如果拉到的下标和数据总量一样的长度的时候就不再往数组里面添加了
        if (index>=jsonArray.length()){
            isMoreData = false;
            ball_mesg_lv.removeFooterView(loading_llyt);
            loading_llyt =(LinearLayout) getLayoutInflater().inflate(R.layout.listview_nodata,null);
            ball_mesg_lv.addFooterView(loading_llyt);
            System.out.println("没有数据了");
        }else {
            try {
                jsonObject = null;
                MesgItem item = new MesgItem();
                jsonObject = new JSONObject(jsonArray.get(index).toString());
                item.ItemTitle= jsonObject.get("ItemTitle").toString();
                item.ItemIntro= jsonObject.get("ItemIntro").toString();
                item.ItemType =jsonObject.get("ItemType").toString();
                item.ItemImageUrl = jsonObject.get("ItemImageUrl").toString();
                item.ManyPersonOnClick = jsonObject.get("ManyPersonOnClick").toString();
                datas.add(item);
                adapter.notifyDataSetChanged();
                //重新设置数据的长度
                getDataShowed = datas.size();
                isLoading = false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    //数据请求
    private void getData(final String url){
        isLoading = true;
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    jsonArray = result.getJSONArray("data");
                    if(jsonArray.length()>0){
                        llt_isrequest.setVisibility(View.GONE);
                        //第一次加载
                        if (adapter!=null){
                            ball_mesg_lv.setAdapter(adapter);
                            ball_mesg_lv.setSelection(getfist);
                        }else {
                            for (int i=0;i<9;i++){
                                MesgItem item = new MesgItem();
                                jsonObject = null;
                                jsonObject = new JSONObject(result.getJSONArray("data").get(i).toString());
                                item.ItemTitle= jsonObject.get("ItemTitle").toString();
                                item.ItemIntro= jsonObject.get("ItemIntro").toString();
                                item.ItemType =jsonObject.get("ItemType").toString();
                                item.ItemImageUrl = jsonObject.get("ItemImageUrl").toString();
//                                System.out.println("test:   "+item.ItemImageUrl);
                                item.ManyPersonOnClick = jsonObject.get("ManyPersonOnClick").toString();
                                datas.add(item);
                            }
                            getDataShowed = datas.size();
                            adapter = new MesgNewAdapter(getActivity(),datas);
                            ball_mesg_lv.addFooterView(loading_llyt);
                            ball_mesg_lv.setAdapter(adapter);
                        }
                    }else {
                        isMoreData = false;
                        ball_mesg_lv.removeFooterView(loading_llyt);
                        loading_llyt =(LinearLayout) getLayoutInflater().inflate(R.layout.listview_nodata,null);
                        ball_mesg_lv.addFooterView(loading_llyt);
                        System.out.println("没有数据了");
                    }
                    isLoading = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                errorNumber = errorNumber+1;
                if(errorNumber<2){
                    //请求第二次
                    getData(dataUrl);
                }else {
                    //不再请求
                    tv_load.setText("请检查你的网络问题，转圈圈也会晕的啦，要么请重启！");
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("onCancelled"+cex);
            }

            @Override
            public void onFinished() {

            }
        });
    }

}
