package kevin.like.com.kevin_ball;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.xutils.x;

import java.io.File;

public class MyApplication extends Application {
    public static Context ttContext;
    public static IWXAPI mWxApi;
    private String WX_APP_ID = "wxf34104427c077ebd";

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        initImageLoader(getApplicationContext());
        ttContext = getApplicationContext();
        wxLogin();
    }


    private void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context,"Kevin/");
        /**ImageLoader的配置**/
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY-2)//设置同时运行的线程
                .denyCacheImageMultipleSizesInMemory()//缓存显示不同大小的同一张图片
                .diskCacheSize(50*1024*1024)  //50MB SD卡本地缓存的最大值
                .diskCache(new UnlimitedDiskCache(cacheDir)) //SD卡缓存
                .memoryCache(new WeakMemoryCache()) //内存缓存
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        //全局初始话配置
        ImageLoader.getInstance().init(config);
    }
    public static DisplayImageOptions setImageOptions(){
        //定义显示配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.onloading)
                .showImageForEmptyUri(R.drawable.forempty)//图片为空
                .showImageOnFail(R.drawable.onfail)//图片加载乱码失败
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }
    private void wxLogin(){
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this,WX_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(WX_APP_ID);
    }

}
