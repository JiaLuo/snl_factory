package com.shinaier.laundry.snlfactory.ordermanage.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.google.gson.Gson;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CheckClothesEntities;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.OrderPrintEntity;
import com.shinaier.laundry.snlfactory.network.json.GsonObjectDeserializer;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.offlinecash.entities.PrintEntity;
import com.shinaier.laundry.snlfactory.offlinecash.ui.activity.MemberInfoActivity;
import com.shinaier.laundry.snlfactory.offlinecash.ui.activity.OrderPayActivity;
import com.shinaier.laundry.snlfactory.offlinecash.ui.activity.PrintActivity;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.CheckClothesAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.entities.ParserEntity;
import com.shinaier.laundry.snlfactory.ordermanage.view.ActionSheetDialog;
import com.shinaier.laundry.snlfactory.util.ExitManager;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import static com.shinaier.laundry.snlfactory.offlinecash.ui.activity.OrderPayActivity.FROM_ORDER_PAY_ACT;


/**
 * Created by 张家洛 on 2017/3/2.
 */

public class CheckClothesActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CHECK_CLOTHES = 0x1;
    public static final int FROM_CODE = 0x3;
    public static final int REQUEST_CODE_QUESTIONS = 0x2;
    public static final int REQUEST_CODE_ADD_PHOTO = 0x5;
    private static final int REQUEST_CODE_NOW_PAY = 0x4;
    public static final int REQUEST_CODE_EDIT_PHOTO = 0x6;
    public static final int REQUEST_CODE_EDIT_ADD_PHOTO = 0x7;
    public static final int REQUEST_CODE_SETTING_COLOR = 0x8;
    private static final int REQUEST_CODE_ORDER_PRINT = 0x10;
    public static final int REQUEST_CODE_ASSESSMENT = 0x11;

    @ViewInject(R.id.check_clothes_list)
    private ListView checkClothesList;
    @ViewInject(R.id.checked_clothes)
    private TextView checkedClothes; //线上确定按钮
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.button_above_line)
    private View buttonAboveLine;
    @ViewInject(R.id.ll_offline_take_clothes)
    private LinearLayout llOfflineTakeClothes;//线下立即付款，收衣付款按钮
    @ViewInject(R.id.take_clothes_pay)
    private TextView takeClothesPay;
    @ViewInject(R.id.now_pay)
    private TextView nowPay;

    private String id;
    private int extraFrom;
    private CheckClothesEntities checkClothesEntities;
    private boolean isCollectPay = false;
    private CheckClothesAdapter checkClothesAdapter;
    private PrintEntity printEntity = new PrintEntity();
    private CommonDialog dialog;
    private StringBuffer stringBuffer = new StringBuffer();
    private Gson gson = GsonObjectDeserializer.produceGson();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_clothes_act);
        ViewInjectUtils.inject(this);
        ExitManager.instance.addOfflineCollectActivity(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        extraFrom = intent.getIntExtra("extraFrom", 0);
        loadData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("oid",id);
        requestHttpData(Constants.Urls.URL_POST_CHECK_CLOTHES,REQUEST_CODE_CHECK_CLOTHES, FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("检查衣物");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        dialog = new CommonDialog(this);

        leftButton.setOnClickListener(this);
        if(extraFrom == MemberInfoActivity.FROM_MEMBER_INFO_ACT){
            checkedClothes.setVisibility(View.GONE);
            llOfflineTakeClothes.setVisibility(View.VISIBLE);
            buttonAboveLine.setVisibility(View.VISIBLE);
            takeClothesPay.setOnClickListener(this);
            nowPay.setOnClickListener(this);
        }else {
            checkedClothes.setVisibility(View.VISIBLE); //线上确定按钮
            llOfflineTakeClothes.setVisibility(View.GONE); //线下立即付款，收衣付款按钮
            buttonAboveLine.setVisibility(View.GONE);
            checkedClothes.setOnClickListener(this);
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_CHECK_CLOTHES:
                if(data != null){
                    checkClothesEntities = Parsers.getCheckClothesEntities(data);
                    setLoadingStatus(LoadingStatus.GONE);
                    if (checkClothesEntities != null){
                        if (checkClothesEntities.getCode() == 0){
                            checkClothesAdapter = new CheckClothesAdapter(this,checkClothesEntities.getResults());
                            checkClothesList.setAdapter(checkClothesAdapter);

                            checkClothesAdapter.setEditPhotoListener(new CheckClothesAdapter.EditPhotoListener() {
                                private ArrayList<String> imgs;

                                @Override
                                public void onEditPhotoClick(final int position) {
                                    imgs = (ArrayList<String>)checkClothesEntities.getResults().get(position).getItemImages();
                                    ActionSheetDialog dialog = new ActionSheetDialog(CheckClothesActivity.this);
                                    dialog.builder().addSheetItem("编辑图片", ActionSheetDialog.SheetItemColor.Green, new ActionSheetDialog.OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {
                                            Intent intent = new Intent(CheckClothesActivity.this,EditPhotoActivity.class);
                                            intent.putStringArrayListExtra("item_images",imgs);
                                            intent.putExtra("position",position);
                                            intent.putExtra("item_id",checkClothesEntities.getResults().get(position).getId());
                                            startActivityForResult(intent,REQUEST_CODE_EDIT_PHOTO);

                                        }
                                    }).addSheetItem("添加照片", ActionSheetDialog.SheetItemColor.Green, new ActionSheetDialog.OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {
                                            if (CheckClothesActivity.this.checkClothesEntities.getResults().get(position).getItemImages().size() <  12){
                                                Intent intent = new Intent(CheckClothesActivity.this,AddPhotoActivity.class);
                                                intent.putExtra("item_id", checkClothesEntities.getResults().get(position).getId());
                                                intent.putStringArrayListExtra("imgs", imgs);
                                                intent.putExtra("extra",FROM_CODE);
                                                intent.putExtra("position",position);
                                                startActivityForResult(intent,REQUEST_CODE_EDIT_ADD_PHOTO);
                                            }else {
                                                ToastUtil.shortShow(CheckClothesActivity.this,"已经添加超过12张图片了。");
                                            }
                                        }
                                    }).show();

                                }
                            });

                            checkClothesAdapter.setAddPhotoListener(new CheckClothesAdapter.AddPhotoListener() {
                                @Override
                                public void onAddPhotoClick(int position) {
                                    Intent intent = new Intent(CheckClothesActivity.this,AddPhotoActivity.class);
                                    intent.putExtra("item_id",checkClothesEntities.getResults().get(position).getId());
                                    intent.putExtra("position",position);
                                    startActivityForResult(intent,REQUEST_CODE_ADD_PHOTO);
                                }
                            });

                            checkClothesAdapter.setEditPositionListener(new CheckClothesAdapter.EditPositionListener() {
                                @Override
                                public void onEditQuestion(int position) {
                                    Intent intent = new Intent(CheckClothesActivity.this,QuestionInfoActivity.class);
                                    intent.putExtra("item_id", checkClothesEntities.getResults().get(position).getId());
                                    intent.putExtra("problem_data",checkClothesEntities.getResults().get(position).getProblemData());
                                    intent.putExtra("position",position);
                                    startActivityForResult(intent,REQUEST_CODE_QUESTIONS);
                                }

                                @Override
                                public void onEditColor(int position) {
                                    Intent intent = new Intent(CheckClothesActivity.this,ColorSettingActivity.class);
                                    intent.putExtra("item_id", checkClothesEntities.getResults().get(position).getId());
                                    intent.putExtra("color_data", checkClothesEntities.getResults().get(position).getColorData());
                                    intent.putExtra("position",position);
                                    startActivityForResult(intent,REQUEST_CODE_SETTING_COLOR);
                                }

                                @Override
                                public void onEditAssess(int position) {
                                    Intent intent = new Intent(CheckClothesActivity.this,CleanedAssessmentActivity.class);
                                    intent.putExtra("item_id", checkClothesEntities.getResults().get(position).getId());
                                    intent.putExtra("forecast_data", checkClothesEntities.getResults().get(position).getForecastData());
                                    intent.putExtra("position",position);
                                    startActivityForResult(intent,REQUEST_CODE_ASSESSMENT);
                                }
                            });
                            checkClothesAdapter.setShowBigPhotoListener(new CheckClothesAdapter.ShowBigPhotoListener() {
                                @Override
                                public void onShowBigPhoto(int position, int innerPosition) {
                                    ArrayList<String> imgs = (ArrayList<String>) checkClothesEntities.getResults().get(position).getItemImages();
                                    Intent intent = new Intent(CheckClothesActivity.this,BigPhotoActivity.class);
                                    intent.putExtra("imagePosition",innerPosition);
                                    intent.putStringArrayListExtra("imagePath",imgs);
                                    startActivity(intent);
                                }

                            });

                        }else {
                            ToastUtil.shortShow(this,checkClothesEntities.getMsg());
                        }
                    }

                }
                break;
            case REQUEST_CODE_NOW_PAY:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity != null){
                        if(entity.getRetcode() == 0){
                            if (isCollectPay){
                                orderPrint(id);
                            }else {
                                Intent intent = new Intent(this,OrderPayActivity.class);
                                intent.putExtra("order_id",id);
                                startActivity(intent);
                            }
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
            case REQUEST_CODE_ORDER_PRINT:
                if (data != null){
                    OrderPrintEntity orderPrintEntity = Parsers.getOrderPrintEntity(data);
                    if (orderPrintEntity != null){
                        if (orderPrintEntity.getRetcode() == 0){
                            if (orderPrintEntity.getDatas() != null){
                                if (orderPrintEntity.getDatas().getInfo() != null &&
                                        orderPrintEntity.getDatas().getItems() != null &&
                                        orderPrintEntity.getDatas().getItems().size() > 0){

                                    setOrderPrint(orderPrintEntity);
                                    //链接打印机 打印条子
                                    Intent intent = new Intent(this,PrintActivity.class);
                                    intent.putExtra("print_entity",printEntity);
                                    intent.putExtra("extra_from",FROM_ORDER_PAY_ACT);
                                    startActivity(intent);
                                    if (dialog.isShowing()){
                                        dialog.dismiss();
                                    }
                                    ExitManager.instance.exitOfflineCollectActivity();
                                }
                            }
                        }else {
                            ToastUtil.shortShow(this,orderPrintEntity.getStatus());
                        }
                    }
                }
                break;
        }
    }

    private void setOrderPrint(OrderPrintEntity orderPrintEntity) {
        PrintEntity.PayOrderPrintEntity payOrderPrintEntity = printEntity.new PayOrderPrintEntity();
        PrintEntity.PayOrderPrintEntity.PayOrderPrintInfo payOrderPrintInfo = payOrderPrintEntity.new PayOrderPrintInfo();
        List<PrintEntity.PayOrderPrintEntity.PayOrderPrintItems> payOrderPrintItemses = new ArrayList<>();
        PrintEntity.PayOrderPrintEntity.PayOrderPrintItems payOrderPrintItems = null;


        payOrderPrintInfo.setOrdersn(orderPrintEntity.getDatas().getInfo().getOrdersn());
        payOrderPrintInfo.setMobile(orderPrintEntity.getDatas().getInfo().getMobile());
        payOrderPrintInfo.setPieceNum(orderPrintEntity.getDatas().getInfo().getPieceNum());
        payOrderPrintInfo.setHedging(orderPrintEntity.getDatas().getInfo().getHedging());
        payOrderPrintInfo.setPayAmount(orderPrintEntity.getDatas().getInfo().getPayAmount());
        payOrderPrintInfo.setReducePrice(orderPrintEntity.getDatas().getInfo().getReducePrice());
        payOrderPrintInfo.setPayChannel(orderPrintEntity.getDatas().getInfo().getPayChannel());
        payOrderPrintInfo.setUserId(orderPrintEntity.getDatas().getInfo().getUserid());
        payOrderPrintInfo.setAddress(orderPrintEntity.getDatas().getInfo().getAddress());
        payOrderPrintInfo.setPhone(orderPrintEntity.getDatas().getInfo().getPhone());
        payOrderPrintInfo.setMid(orderPrintEntity.getDatas().getInfo().getMid());
        payOrderPrintInfo.setClerkName(orderPrintEntity.getDatas().getInfo().getClerkName());
        payOrderPrintInfo.setPayState(orderPrintEntity.getDatas().getInfo().getPayState());
        payOrderPrintInfo.setAmount(orderPrintEntity.getDatas().getInfo().getAmount());
        payOrderPrintInfo.setCardNumber(orderPrintEntity.getDatas().getInfo().getCardNumber());
        payOrderPrintInfo.setCardBalance(orderPrintEntity.getDatas().getInfo().getCardBalance());

        List<OrderPrintEntity.OrderPrintDatas.OrderPrintItems> items = orderPrintEntity.getDatas().getItems();
        for (int i = 0; i < items.size(); i++) {
            payOrderPrintItems = payOrderPrintEntity.new PayOrderPrintItems();
            payOrderPrintItems.setItemNote(items.get(i).getItemNote());
            payOrderPrintItems.setName(items.get(i).getName());
            payOrderPrintItems.setPrice(items.get(i).getPrice());
            payOrderPrintItems.setColor(items.get(i).getColor());
            payOrderPrintItemses.add(payOrderPrintItems);
        }

        printEntity.setPayOrderPrintEntity(payOrderPrintEntity);
        printEntity.getPayOrderPrintEntity().setPayOrderPrintInfo(payOrderPrintInfo);
        printEntity.getPayOrderPrintEntity().setPayOrderPrintItems(payOrderPrintItemses);

    }

    private void orderPrint(String orderId) {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("order_id",orderId);
        params.put("clerk_id", UserCenter.getUid(this));
        params.put("token",UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_ORDER_PRINT,REQUEST_CODE_ORDER_PRINT, FProtocol.HttpMethod.POST,params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.checked_clothes:
            case R.id.left_button:
                finish();
                break;
            case R.id.loading_img_refresh:
                setLoadingStatus(LoadingStatus.LOADING);
                loadData();
                break;
            case R.id.take_clothes_pay:
                isCollectPay = true;
                dialog.setContent("加载中");
                dialog.show();
                IdentityHashMap<String,String> CollectPayParams = new IdentityHashMap<>();
                CollectPayParams.put("token",UserCenter.getToken(this));
                CollectPayParams.put("order_id",id);
                requestHttpData(Constants.Urls.URL_POST_NOW_PAY,REQUEST_CODE_NOW_PAY, FProtocol.HttpMethod.POST,CollectPayParams);
                break;
            case R.id.now_pay:
                isCollectPay = false;
                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                params.put("token",UserCenter.getToken(this));
                params.put("order_id",id);
                requestHttpData(Constants.Urls.URL_POST_NOW_PAY,REQUEST_CODE_NOW_PAY, FProtocol.HttpMethod.POST,params);
                break;
        }
    }

    @Override
    public void mistake(int requestCode, FProtocol.NetDataProtocol.ResponseStatus status, String errorMessage) {
        super.mistake(requestCode, status, errorMessage);
        switch (requestCode){
            case REQUEST_CODE_CHECK_CLOTHES:
                setLoadingStatus(LoadingStatus.RETRY);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ADD_PHOTO:
                if (resultCode == RESULT_OK){
                    ArrayList<String> uploadAddPhoto = data.getStringArrayListExtra("upload_photo");
                    int position = data.getIntExtra("position", 0);
                    List<String> imgs = checkClothesEntities.getResults().get(position).getItemImages();
                    for (int i = 0; i < uploadAddPhoto.size(); i++) {
                        String s = uploadAddPhoto.get(i);
                        imgs.add(s);
                    }

                    if (checkClothesEntities != null){
                        checkClothesEntities.getResults().get(position).setItemImages(imgs);
                        checkClothesAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case REQUEST_CODE_EDIT_PHOTO:
                if (resultCode == RESULT_OK){
                    int position = data.getIntExtra("position", 0);
                    ArrayList<String> photoEntity = data.getStringArrayListExtra("photo_entity");
                    if (checkClothesEntities != null){
                        checkClothesEntities.getResults().get(position).setItemImages(photoEntity);
                        checkClothesAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case REQUEST_CODE_EDIT_ADD_PHOTO:
                if (resultCode == RESULT_OK){
                    ArrayList<String> uploadPhoto = data.getStringArrayListExtra("upload_photo");
                    int position = data.getIntExtra("position", 0);
                    List<String> imgs = checkClothesEntities.getResults().get(position).getItemImages();
                    for (int i = 0; i < uploadPhoto.size(); i++) {
                        String uploadPhotoDatas = uploadPhoto.get(i);
                        imgs.add(uploadPhotoDatas);
                    }



                    if (checkClothesEntities != null){
                        checkClothesEntities.getResults().get(position).setItemImages(imgs);
                        checkClothesAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case REQUEST_CODE_SETTING_COLOR:
                if (resultCode == RESULT_OK){
                    ParserEntity parserEntity = (ParserEntity) data.getSerializableExtra("color");
                    int position = data.getIntExtra("position", 0);
                    String toJsonColorData = gson.toJson(parserEntity);
                    String content = parserEntity.getContent();
                    List<String> options = parserEntity.getOptions();

                    editColorForResult(options);


                    if (checkClothesEntities != null){
                        if (!TextUtils.isEmpty(content) && options.size() > 0){
                            checkClothesEntities.getResults().get(position).setColor(stringBuffer.toString() + ";" + content);
                            checkClothesEntities.getResults().get(position).setColorData(toJsonColorData);
                        }else if (options.size() > 0){
                            checkClothesEntities.getResults().get(position).setColor(stringBuffer.toString());
                            checkClothesEntities.getResults().get(position).setColorData(toJsonColorData);
                        }else {
                            checkClothesEntities.getResults().get(position).setColor(content);
                            checkClothesEntities.getResults().get(position).setColorData(toJsonColorData);
                        }
                        checkClothesAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case REQUEST_CODE_QUESTIONS:
                if (resultCode == RESULT_OK){
                    ParserEntity parserEntity = (ParserEntity) data.getSerializableExtra("question");
                    int position = data.getIntExtra("position", 0);
                    String toJsonColorData = gson.toJson(parserEntity);
                    String content = parserEntity.getContent();
                    List<String> options = parserEntity.getOptions();

                    editColorForResult(options);

                    if (checkClothesEntities != null){
                        if (!TextUtils.isEmpty(content) && options.size() > 0){
                            checkClothesEntities.getResults().get(position).setProblem(stringBuffer.toString() + ";"+ content);
                            checkClothesEntities.getResults().get(position).setProblemData(toJsonColorData);
                        }else if (options.size() > 0){
                            checkClothesEntities.getResults().get(position).setProblem(stringBuffer.toString());
                            checkClothesEntities.getResults().get(position).setProblemData(toJsonColorData);
                        }else {
                            checkClothesEntities.getResults().get(position).setProblem(content);
                            checkClothesEntities.getResults().get(position).setProblemData(toJsonColorData);
                        }
                        checkClothesAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case REQUEST_CODE_ASSESSMENT:
                if (resultCode == RESULT_OK){
                    ParserEntity parserEntity = (ParserEntity) data.getSerializableExtra("assess");
                    int position = data.getIntExtra("position", 0);
                    String toJsonColorData = gson.toJson(parserEntity);
                    String content = parserEntity.getContent();
                    List<String> options = parserEntity.getOptions();

                    editColorForResult(options);

                    if (checkClothesEntities != null){
                        if (!TextUtils.isEmpty(content) && options.size() > 0){
                            checkClothesEntities.getResults().get(position).setForecast(stringBuffer.toString() + ";"+ content);
                            checkClothesEntities.getResults().get(position).setForecastData(toJsonColorData);
                        }else if (options.size() > 0){
                            checkClothesEntities.getResults().get(position).setForecast(stringBuffer.toString());
                            checkClothesEntities.getResults().get(position).setForecastData(toJsonColorData);
                        }else {
                            checkClothesEntities.getResults().get(position).setForecast(content);
                            checkClothesEntities.getResults().get(position).setForecastData(toJsonColorData);
                        }
                        checkClothesAdapter.notifyDataSetChanged();
                    }
                }
                break;

        }
    }

    private void editColorForResult(List<String> options) {
        // stringbuffer添加新数据之前要清空一下
        stringBuffer.delete(0,stringBuffer.length());
        //遍历用户选择的颜色数据，添加分隔符
        for (int i = 0; i < options.size(); i++) {
            if(i == 0){
                if(options.size() == 1){
                    stringBuffer.append(options.get(i));
                }else {
                    stringBuffer.append(options.get(i)).append(";");
                }
            }else if(i > 0 && i < options.size() -1){
                stringBuffer.append(options.get(i)).append(";");
            }else {
                stringBuffer.append(options.get(i));
            }
        }
    }
}
