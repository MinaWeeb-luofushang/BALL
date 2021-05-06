package kevin.like.com.kevin_ball;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;



@ContentView(R.layout.activity_start_page)
public class StartPage extends Activity {

    @ViewInject(R.id.start_pager_close)
    TextView start_pager_close;

    private int StartNumber= 9520;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 9520:
                    firstRun();
                    break;
                default:
                    break;
            }
        }
    };

    private boolean isComIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start_page);
        x.view().inject(this);
        handler.sendEmptyMessageDelayed(StartNumber,30);
    }
    private void setActivityTimeOut(){
        CountDownTimer timer = new CountDownTimer(6*1000,1000) {
            @Override
            public void onTick(long l) {
                start_pager_close.setText(l/1000+"s");
            }

            @Override
            public void onFinish() {
                if (!isComIn){
                    startActivity(new Intent(StartPage.this,MainActivity.class));
                    finish();
                }
            }
        }.start();
    }

    @Event(value = R.id.start_pager_close,type = View.OnClickListener.class)
    private void viewOnClick(View view){
        switch (view.getId()){
            case R.id.start_pager_close:
                startActivity(new Intent(StartPage.this,MainActivity.class));
                isComIn = true;
//                finish();
                System.out.println("辣鸡尽量");
//                requestUrl("909090");
//                requestEasyMock("https://www.easy-mock.com/mock/5fa79dfe234c9b0d8babe595/mytest_request/post_data?name=周杰伦");
                break;
            default:
                break;
        }
    }

    //测试请求easy mock
    private void requestUrl(String url){
        System.out.println(url);
        RequestParams params = new RequestParams("http://192.168.233.1:3000/RcSystem/user/select");
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("onSuccess");
                System.out.println(result);
//                System.out.println(str1);
                JSONObject object = new JSONObject();
                JSONArray array = new JSONArray();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("onCancelled");
            }

            @Override
            public void onFinished() {
                System.out.println("onCancelled");
            }

            @Override
            public boolean onCache(String result) {
                System.out.println("onCache");
                return false;
            }
        });
    };


    private void requestEasyMock(String url){
        System.out.println("辣鸡尽量");
//        Map<String,String> entity = new HashMap<String,String>();
//        entity.put("name","周杰伦");
        RequestParams params = new RequestParams(url);
//            params.setHeader("name","周杰伦");

        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("test："+result);
                try{
                    // JSONObject result = new JSONObject(str); // String 转 JSONObject
                    JSONObject object = new JSONObject(result);
                    object = new JSONObject(object.get("data").toString());
                    System.out.println(object.get("name"));
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("onCancelled");
            }

            @Override
            public void onFinished() {
                System.out.println("onFinished");
            }

            @Override
            public boolean onCache(String result) {
//                System.out.println(result);
                return false;
            }
        });
    }
    private void firstRun() {
        SharedPreferences sharedPreferences = getSharedPreferences("FirstRun",0);
        Boolean first_run = sharedPreferences.getBoolean("First",true);
        if (first_run){
            sharedPreferences.edit().putBoolean("First",false).commit();
            startActivity(new Intent(StartPage.this,GuidePage.class));
            finish();
        }
        else {
//            startActivity(new Intent(StartPage.this,MainActivity.class));
//            finish();
            setActivityTimeOut();
        }
    }
}
