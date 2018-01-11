package com.shinaier.laundry.snlfactory.ordermanage.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.DeviceUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CraftworkAddPriceEntities;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.TakeTimeEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.ui.activity.ScanActivity;
import com.shinaier.laundry.snlfactory.ordermanage.view.AwardAuthorPop;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CheckDistanceSpinnerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;



/**
 * 编辑价格
 * Created by 张家洛 on 2017/11/3.
 */

public class EditorPriceActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_REVISE_TAKE_TIME = 0x1;
    private static final int REQUEST_CODE_EDIT_PRICE = 0x2;
    private static final int GAIN_CLOTHES_NUMBER = 0x3;

    @ViewInject(R.id.et_input_clothes_number) //输入衣物编码
    private EditText etInputClothesNumber;
    @ViewInject(R.id.gain_clothes_number) //扫一扫 获取衣物编码
    private TextView gainClothesNumber;
    @ViewInject(R.id.et_input_technology_price)
    private EditText etInputTechnologyPrice;
    @ViewInject(R.id.et_input_technology_remarks)
    private EditText etInputTechnologyRemarks;
    @ViewInject(R.id.tv_remarks_num)
    private TextView tvRemarksNum;
    @ViewInject(R.id.et_input_hedge_value)
    private EditText etInputHedgeValue;
    @ViewInject(R.id.rl_revise_take_time)
    private RelativeLayout rlReviseTakeTime;
    @ViewInject(R.id.tv_take_clothes_time)
    private TextView tvTakeClothesTime;
    @ViewInject(R.id.tv_confirm_bt)
    private TextView tvConfirmBt;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.ll_revise_take_time_info)
    private LinearLayout llReviseTakeTimeInfo;
    @ViewInject(R.id.iv_edit_price_remark)
    private ImageView ivEditPriceRemark;

    private CheckDistanceSpinnerView checkDistanceSpinnerView;
    private TranslateAnimation animation;
    private View inflate;
    private WindowManager.LayoutParams attributes;
    private List<TakeTimeEntity> takeTimeEntity;
    private CraftworkAddPriceEntities.CraftworkAddPriceResult.CraftworkAddPriceItems craftworkAddPriceItems;
    private String takeTime;
    private String isOnline;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String text = (String) msg.obj;
                    etInputTechnologyRemarks.setText(etInputTechnologyRemarks.getText().toString() + text);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_price_two_act);
        inflate = View.inflate(this, R.layout.editor_price_two_act, null);
        ViewInjectUtils.inject(this);
        craftworkAddPriceItems = getIntent().getParcelableExtra("craftwork_add_price_items");
        //判断线上还是线下
        isOnline = getIntent().getStringExtra("is_online");
        takeTime = craftworkAddPriceItems.getTakeTime();

        loadData();
        initView(isOnline);
    }

    private void loadData() {
        requestHttpData(Constants.Urls.URL_POST_REVISE_TAKE_TIME,REQUEST_CODE_REVISE_TAKE_TIME);
    }

    private void initView(String isOnline) {
        setCenterTitle("编辑价格");
        rlReviseTakeTime.setOnClickListener(this);
        tvConfirmBt.setOnClickListener(this);
        gainClothesNumber.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        ivEditPriceRemark.setOnClickListener(this);
        tvTakeClothesTime.setText(takeTime);
        etInputClothesNumber.setText(craftworkAddPriceItems.getCleanSn());
        etInputTechnologyPrice.setText(craftworkAddPriceItems.getCraftPrice());
        etInputTechnologyRemarks.setText(craftworkAddPriceItems.getCraftDes());

        if (isOnline.equals("1")){ //线上取衣时间不显示 线下显示
            rlReviseTakeTime.setVisibility(View.GONE);
            llReviseTakeTimeInfo.setVisibility(View.GONE);
        }else {
            rlReviseTakeTime.setVisibility(View.VISIBLE);
            llReviseTakeTimeInfo.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(craftworkAddPriceItems.getCraftDes())){
            tvRemarksNum.setText(craftworkAddPriceItems.getCraftDes().length()+ "/20");
        }
        etInputHedgeValue.setText(formatMoney(craftworkAddPriceItems.getKeepPrice()));

        etInputTechnologyRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvRemarksNum.setText(s.length()+ "/20");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        checkDistanceSpinnerView = new CheckDistanceSpinnerView(this, new CheckDistanceSpinnerView.OnSpinnerItemClickListener() {
            @Override
            public void onItemClickListener1(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onItemClickListener2(String time) {
                takeTime = time;
                tvTakeClothesTime.setText(time);

            }
        });

        animation = new TranslateAnimation(0, 0, DeviceUtil.getHeight(this), 0);
        animation.setDuration(100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_revise_take_time:
                if (takeTimeEntity != null){
                    checkDistanceSpinnerView.showSpinnerPop(inflate, animation, takeTimeEntity,takeTime);
                }
                break;
            case R.id.tv_confirm_bt: //提交页面数据
                String inputClothesNumber = etInputClothesNumber.getText().toString();
                String inputTechnologyPrice = etInputTechnologyPrice.getText().toString();
                String inputTechnologyRemarks = etInputTechnologyRemarks.getText().toString();
                String inputHedgeValue = etInputHedgeValue.getText().toString();
                String takeClothesTime = tvTakeClothesTime.getText().toString();
                if (!TextUtils.isEmpty(inputClothesNumber)){
                    IdentityHashMap<String,String> params = new IdentityHashMap<>();
                    params.put("token", UserCenter.getToken(this));
                    params.put("item_id",craftworkAddPriceItems.getId());
                    params.put("clean_sn",inputClothesNumber);
                    if (!TextUtils.isEmpty(inputTechnologyPrice)){ //判断如果特殊工艺价格没有填就传0
                        params.put("craft_price",inputTechnologyPrice);
                    }else {
                        params.put("craft_price","0");
                    }
                    if (!TextUtils.isEmpty(inputTechnologyRemarks)){
                        params.put("craft_des",inputTechnologyRemarks);
                    }else {
                        params.put("craft_des","");
                    }
                    if (!TextUtils.isEmpty(inputHedgeValue)){
                        double hedgeValue = Double.parseDouble(inputHedgeValue);
                        params.put("keep_price",formatMoney(hedgeValue / 200));
                    }else {
                        params.put("keep_price","0");
                    }
                    if (isOnline.equals("0")){
                        //线上取衣时间不显示 线下显示 所以不传take_time参数
                        if (!TextUtils.isEmpty(takeClothesTime)){
                            params.put("take_time",takeClothesTime);
                        }else {
                            params.put("take_time",takeTime);
                        }
                    }

                    requestHttpData(Constants.Urls.URL_POST_NEW_EDIT_PRICE,REQUEST_CODE_EDIT_PRICE, FProtocol.HttpMethod.POST,params);
                }else {
                    ToastUtil.shortShow(this,"请输入衣物编码");
                }
                break;
            case R.id.gain_clothes_number: //获取衣物编码
                startActivityForResult(new Intent(this,ScanActivity.class), GAIN_CLOTHES_NUMBER);
                break;
            case R.id.left_button:
                finish();
                break;
            case R.id.iv_edit_price_remark:
                List<String> stringList = new ArrayList<>();
                stringList.add("有墨水难洗");
                stringList.add("需要缝补");
                stringList.add("有线头");
                stringList.add("有线头");
                stringList.add("有墨水难洗");
                stringList.add("需要缝补");
                stringList.add("有墨水难洗");
                stringList.add("有线头");
                AwardAuthorPop awardAuthorPop = new AwardAuthorPop(this,stringList,handler);
                awardAuthorPop.setView();
                //设置Popupwindow显示位置（从底部弹出）
                awardAuthorPop.showAtLocation(findViewById(R.id.ll_edit_price_content), Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
                attributes = getWindow().getAttributes();
                //当弹出Popupwindow时，背景变半透明
                attributes.alpha = 0.7f;
                getWindow().setAttributes(attributes);
                //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
                awardAuthorPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        attributes = getWindow().getAttributes();
                        attributes.alpha = 1f;
                        getWindow().setAttributes(attributes);
                    }
                });
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_REVISE_TAKE_TIME:
                if (data != null){
                    takeTimeEntity = Parsers.getTakeTimeEntity(data);
                }
                break;
            case REQUEST_CODE_EDIT_PRICE:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            Intent intent = new Intent(this,CraftworkAddPriceActivity.class);
                            setResult(RESULT_OK,intent);
                            finish();
                        }else if (entity.getRetcode() == 1000){
                            ToastUtil.shortShow(this,"页面没有任何修改");
                        } else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }

    public String formatMoney(double money){
        DecimalFormat df = new DecimalFormat("###0.00");
        return df.format(money);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case GAIN_CLOTHES_NUMBER:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        String clothesNumber = data.getStringExtra("pay_code"); //扫描二维码获取衣物编码
                        etInputClothesNumber.setText(clothesNumber);
                    }
                }
                break;
        }
    }
}
