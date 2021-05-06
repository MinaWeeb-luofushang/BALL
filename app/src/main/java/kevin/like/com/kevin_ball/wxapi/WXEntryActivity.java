package kevin.like.com.kevin_ball.wxapi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import kevin.like.com.kevin_ball.MyApplication;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private Context mContext ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext  = this;
        MyApplication.mWxApi.handleIntent(getIntent(),this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {

    }
    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp baseResp) {
            String code = null;
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:// 用户同意,只有这种情况的时候code是有效的
                    code = ((SendAuth.Resp) baseResp).code;
                    System.out.println("用户同意");
                    try {
                        System.out.println("这里是编码"+code);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:// 用户拒绝授权
                    System.out.println("用户拒绝授权");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:// 用户取消
                    System.out.println("Apptest用户取消");
                    break;
                default:// 发送返回
                    break;
            }

    }

}
