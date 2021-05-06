package kevin.like.com.kevin_ball.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


import kevin.like.com.kevin_ball.MyApplication;
import kevin.like.com.kevin_ball.R;


@ContentView(R.layout.fragment_self)
public class SelfFragment extends Fragment {
    @ViewInject(R.id.ball_iv_weixin_login)
    ImageView ball_iv_weixin_login;
    @ViewInject(R.id.ball_iv_qq_login)
    ImageView ball_iv_qq_login;
    @ViewInject(R.id.ball_iv_phone_login)
    ImageView ball_iv_phone_login;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }


    @Event(value = {R.id.ball_iv_weixin_login,R.id.ball_iv_qq_login,R.id.ball_iv_phone_login},type = View.OnClickListener.class)
    private void OnClick(View view){
        switch (view.getId()){
            case R.id.ball_iv_weixin_login:
                System.out.println("微信登录哟");
                //app注册到微信里面
                wxLogin();
                System.out.println("微信登录天涯");
                break;
            case R.id.ball_iv_qq_login:
                System.out.println("QQ登录哟");
                break;
            case R.id.ball_iv_phone_login:
                System.out.println("手机号登录哟");
                break;
            default:
                 break;
        }
    }
    //微信登录
    public void wxLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_login";
        //像微信发送请求
        MyApplication.mWxApi.sendReq(req);
    }
}
