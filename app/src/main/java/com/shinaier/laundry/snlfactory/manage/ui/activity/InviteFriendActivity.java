package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.InviteFriendEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.IdentityHashMap;


/**
 * 邀请好友
 * Created by 张家洛 on 2017/2/15.
 */

public class InviteFriendActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_INVITE_FRIEND = 0x1;

    @ViewInject(R.id.invite_code)
    private TextView inviteCode;
    @ViewInject(R.id.invite_friend_now_up)
    private TextView inviteFriendNowUp;
    @ViewInject(R.id.invite_friend_now_down)
    private TextView inviteFriendNowDown;
    @ViewInject(R.id.invite_text_info)
    private TextView inviteTextInfo;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private String url;
    private String share;
    private String another = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_friend_act);
        ViewInjectUtils.inject(this);
        loadData();
        initView();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_INVITE_FRIEND,REQUEST_CODE_INVITE_FRIEND, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("邀请好友");
        inviteTextInfo.setText(this.getResources().getString(R.string.invite_friend_info));
        inviteFriendNowUp.setOnClickListener(this);
        inviteFriendNowDown.setOnClickListener(this);
        leftButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.invite_friend_now_down:
            case R.id.invite_friend_now_up:
                ToastUtil.shortShow(this,"此功能正在开发中敬请期待...");
//                new ShareAction(InviteFriendActivity.this)
//                        .withText("关注速洗达洗衣啦，您也可以邀请好友享受返现奖励") //设置内容
//                        .withMedia(new UMImage(InviteFriendActivity.this,R.drawable.share_img)) //设置图片
//                        .withTargetUrl(url + "?invite=" + share +"&for=ANDROID") //设置分享链接
//                        .withTitle("我邀请你洗衣啦！") // 设置标题
//                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_CIRCLE)
//                        .setCallback(umShareListener).open();

//                UMShareAPI umShareAPI = UMShareAPI.get(this);
//                if ( umShareAPI.isInstall(this,SHARE_MEDIA.WEIXIN)){
//                    UMWeb web = new UMWeb(url + "?invite=" + share +"&for=ANDROID");
//                    web.setTitle("我邀请你洗衣啦！");//标题
//                    web.setThumb(new UMImage(InviteFriendActivity.this,R.drawable.share_img));  //缩略图
//                    web.setDescription("关注速洗达洗衣啦，您也可以邀请好友享受返现奖励");//描述
//                    new ShareAction(InviteFriendActivity.this)
//                            .withMedia(web)
//                            .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.SINA)
//                            .setCallback(umShareListener).open();
//                }else {
//                    ToastUtil.shortShow(this,"请安装微信客户端");
//                }




                break;
            case R.id.left_button:
                finish();
                break;
        }
    }


    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_INVITE_FRIEND:
                if(data != null){
                    InviteFriendEntity inviteFriendEntity = Parsers.getInviteFriendEntity(data);
                    if (inviteFriendEntity.getCode() == 0){
                        if (inviteFriendEntity.getResult() != null){
                            url = inviteFriendEntity.getResult().getUrl();
                            share = inviteFriendEntity.getResult().getmCode();
                            inviteCode.setText("邀请码：" + share);
                        }
                    }else {
                        ToastUtil.shortShow(this,inviteFriendEntity.getMsg());
                    }

                }
                break;
        }
    }

    private void platformToName(SHARE_MEDIA platform) {
        String name = platform.name();
        switch (name) {
            case "WEIXIN":
                another = "微信";
                break;
            case "WEIXIN_CIRCLE":
                another = "微信朋友圈";
                break;
            case "QZONE":
                another = "QQ空间";
                break;
            case "SINA":
                another = "新浪微博";
                break;
            case "QQ":
                another = "QQ";
                break;
            default:
                another = "";
                break;
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            platformToName(platform);
            Toast.makeText(InviteFriendActivity.this,another + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            platformToName(platform);
            Toast.makeText(InviteFriendActivity.this,another + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            platformToName(platform);
            Toast.makeText(InviteFriendActivity.this,another + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
