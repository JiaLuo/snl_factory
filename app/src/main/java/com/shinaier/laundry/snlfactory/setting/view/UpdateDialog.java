package com.shinaier.laundry.snlfactory.setting.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;


/**
 * Created by 张家洛 on 2017/5/30.
 */

public class UpdateDialog extends Dialog {
    private Context context;
    private Handler handler;

    public UpdateDialog(@NonNull Context context) {
        super(context);
    }

    public UpdateDialog(@NonNull Context context, @StyleRes int themeResId, Handler handler) {
        super(context, themeResId);
        this.context = context;
        this.handler = handler;
    }

    protected UpdateDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setView(String versionContent, String versionNum, String versionSize){
        View view = View.inflate(context, R.layout.update_version_view,null);
        setContentView(view);
        TextView updateVersionNum = (TextView) view.findViewById(R.id.update_version_num);
        TextView updateVersionSize = (TextView) view.findViewById(R.id.update_version_size);
        TextView updateVersionContent = (TextView) view.findViewById(R.id.update_version_content);
        RelativeLayout nowUpdate = (RelativeLayout) view.findViewById(R.id.now_update);
        updateVersionContent.setText("【最新内容】" +versionContent);
        updateVersionNum.setText("最新版本：" + versionNum);
        updateVersionSize.setText("版本大小：" + versionSize);
        nowUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }
}
