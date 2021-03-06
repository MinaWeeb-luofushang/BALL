package kevin.like.com.kevin_ball;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


@ContentView(R.layout.activity_new_page)
public class NewPage extends AppCompatActivity implements TextToSpeech.OnInitListener {
    @ViewInject(R.id.new_speak_bg)
    ImageView new_speak_bg;

    private TextToSpeech textToSpeech;

    private String test = "国家发改委：到2025年前适时启动海南岛封关运作\n" +
            "中国新闻网 | 2020-06-08 10:33:09\n" +
            "　　中新网6月8日电 国家发展改革委副主任林念修8日介绍，《海南自由贸易港建设总体方案》的推进实施可以分为打基础和全面推进两个阶段。第一个阶段，从现在起到2025年以前主要是打基础、做准备，在这一阶段适时启动全岛封关运作。第二个阶段，2035年以前，主要是全面推进自由贸易港政策落地见效。\n" +
            "\n" +
            "\n" +
            "资料图为航拍海口秀英港集装箱码头。中新社记者 骆云飞 摄\n" +
            "　　6月1日，《海南自由贸易港建设总体方案》正式公布。国新办6月8日举行发布会，介绍《海南自由贸易港建设总体方案》有关情况，并回答记者提问。\n" +
            "\n" +
            "　　林念修在会上介绍，总的来看，《总体方案》始终把握三个关键点：第一，坚持党的集中统一领导，坚持中国特色社会主义制度；第二，对接国际高水平经贸规则，把制度集成创新摆在重要的位置上，促进生产要素自由便利流动；第三，成熟一项推动一项，行稳致远，久久为功。想用一个非常简单的概括，就是把准方向、集成创新、自由便利、步步为营。\n" +
            "\n" +
            "　　林念修指出，《总体方案》的主要内容可以概括为“6+1+4”。\n" +
            "\n" +
            "　　“6”就是贸易自由便利、投资自由便利、跨境资金流动自由便利、人员进出自由便利、运输来往自由便利、数据安全有序流动。围绕这6个方面，《总体方案》作出了一系列制度安排。\n" +
            "\n" +
            "　　贸易自由便利方面，对货物贸易，简单的说就是“零关税”，就是实行以“零关税”为基本特征的自由化便利化制度安排。对服务贸易，简单说就是“既准入又准营”，将实行以“既准入又准营”为基本特征的自由化便利化一系列政策举措。\n" +
            "\n" +
            "　　投资自由便利方面，一句话，“非禁即入”，就是大幅放宽海南自由贸易市场准入，实行“非禁即入”制，进一步激发各类市场主体的活力。\n" +
            "\n" +
            "　　跨境资金流动自由便利方面，强调金融服务实体经济，围绕贸易投资自由化便利化，分阶段开放资本项目，有序推进海南自由贸易港与境外资金自由便利流动。\n" +
            "\n" +
            "　　人员进出自由便利方面，针对高端产业人才，实行更加开放的人才和停居留政策，目的是打造人才聚集的高地。\n" +
            "\n" +
            "　　关于运输往来自由便利方面，将实行高度自由便利开放的运输政策，推动建设西部陆海新通道国际航运枢纽和航空枢纽。\n" +
            "\n" +
            "　　数据安全有序流动方面，要在确保数据流动安全可控的前提下，扩大数据领域开放，培育发展数字经济。\n" +
            "\n" +
            "　　“1”就是构建现代产业体系。特别强调要突出海南的优势和特色，大力发展旅游业、现代服务业和高新技术产业，进一步夯实实体经济的基础，增强经济创新力和竞争力。\n" +
            "\n" +
            "　　“4”就是要加强税收、社会治理、法治、风险防控等四个方面的制度建设。\n" +
            "\n" +
            "　　一是按照零关税、低税率、简税制、强法治、分阶段的原则，逐步建立与高水平自由贸易港相适应的税收制度。\n" +
            "\n" +
            "　　二是着力推进政府机构改革和政府职能转变，构建系统完备、科学规范、运行有效的自由贸易港治理体系。\n" +
            "\n" +
            "　　三是建立以海南自由贸易港法为基础，以地方性法规和商事纠纷经济解决机制为重要组成的自由贸易港法治体系，营造国际一流的自由贸易港法治环境。\n" +
            "\n" +
            "　　四是制定实施有效措施，有针对性防范化解贸易、投资、金融、公共卫生、生态等领域的重大风险，牢牢守住不发生系统性风险的底线。\n" +
            "\n" +
            "　　林念修介绍，《总体方案》的推进实施可以分为打基础和全面推进两个阶段。\n" +
            "\n" +
            "　　第一个阶段，从现在起到2025年以前主要是打基础、做准备。这一阶段的目标任务是，突出贸易投资自由化便利化，在有效监管的基础上，有序推进开放进程，推动各类要素便捷高效流动，形成早期收获，适时启动全岛封关运作。围绕这一目标任务，我们将抓紧开展工作，争取用3年左右的时间能够取得突破性的进展，为全面封关奠定一个良好的基础。\n" +
            "\n" +
            "　　第二个阶段，2035年以前，主要是全面推进自由贸易港政策落地见效。这一阶段的目标任务是，进一步优化完善开放政策和相关制度安排，实现贸易自由便利、投资自由便利、跨境资金流动自由便利、人员进出自由便利、运输来往自由便利和数据安全有序流动，基本形成完备的法律法规体系、现代产业体系和现代化社会治理体系，打造我国开放型经济新高地。\n" +
            "\n" +
            "　　林念修表示，下一步，国家发展改革委将会同海南省和有关部门，按照《总体方案》的要求，突出重点环节和关键领域，坚持“管得住”才能“放得开”的原则，把握好分步骤分阶段实施的推进节奏，只争朝夕，扎实稳妥，推动各项政策措施尽快落地见效，高质量高标准的建设海南自由贸易港。";

    private boolean isSpeak = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
//直接设置颜色
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        initPrams();
    }
    private void initPrams(){
        textToSpeech = new TextToSpeech(this.getApplicationContext(), this);
        textToSpeech.setPitch(1f);
    }

//    private String test2 = "i am  kevin";
    @Override
    public void onInit(int i) {
        if (textToSpeech!=null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                textToSpeech.speak(test,TextToSpeech.QUEUE_FLUSH,null);
            }

            isSpeak = true;
        }

    }
    //返回
    public void new_back(View view){
        onBackPressed();
    }
    //停止播放
    public void new_speak(View view){
        if (isSpeak){
            textToSpeech.stop();
            isSpeak = false;
            new_speak_bg.setImageDrawable(getResources().getDrawable(R.drawable.new_speak_false));
        }else {
            textToSpeech.speak(test, TextToSpeech.QUEUE_FLUSH, null);
            isSpeak = true;
            new_speak_bg.setImageDrawable(getResources().getDrawable(R.drawable.new_speak_true));
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        textToSpeech.speak(test, TextToSpeech.QUEUE_FLUSH, null);
        isSpeak = true;
        new_speak_bg.setImageDrawable(getResources().getDrawable(R.drawable.new_speak_true));
    }

    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.stop();
        textToSpeech.shutdown();
    }

    @Override
    protected void onStart() {
        super.onStart();
        textToSpeech = new TextToSpeech(this.getApplicationContext(), this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        textToSpeech.stop();
        textToSpeech.shutdown();
    }
}
