package com.shinaier.laundry.snlfactory.offlinecash.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.viewinject.annotation.ViewInject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.RefluxEditEntity;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.RefluxClothesImgAdapter;
import com.shinaier.laundry.snlfactory.offlinecash.adapter.RefluxModuleAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.ui.activity.BigPhotoActivity;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.CommonDialog;
import com.shinaier.laundry.snlfactory.view.WrapHeightGridView;

import java.util.ArrayList;
import java.util.IdentityHashMap;

/**
 * Created by 张家洛 on 2018/1/18.
 */

public class OfflineRefluxEditActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_REFLUX_EDIT = 0x1;
    private static final int REQUEST_CODE_ADD_PHOTO = 0x2;
    public static final int FROM_REFLUX_EDIT = 0x3;
    private static final int REQUEST_CODE_DELETE_PHOTO = 0x4;
    private static final int REQUEST_CODE_REFLUX = 0x5;

    @ViewInject(R.id.reflux_edit_img)
    private SimpleDraweeView refluxEditImg;
    @ViewInject(R.id.reflux_edit_name)
    private TextView refluxEditName;
    @ViewInject(R.id.reflux_edit_number)
    private TextView refluxEditNumber;
    @ViewInject(R.id.reflux_clothes_img)
    private WrapHeightGridView refluxClothesImg;
    @ViewInject(R.id.et_reflux_edit_describe)
    private EditText etRefluxEditDescribe;
    @ViewInject(R.id.reflux_edit_describe_max_num)
    private TextView refluxEditDescribeMaxNum;
    @ViewInject(R.id.reflux_edit_normal)
    private TextView refluxEditNormal;
    @ViewInject(R.id.reflux_edit_not_normal)
    private TextView refluxEditNotNormal;
    @ViewInject(R.id.gv_reflux_step_list)
    private WrapHeightGridView gvRefluxStepList;
    @ViewInject(R.id.repulse_edit_btn)
    private TextView repulseEditBtn;
    @ViewInject(R.id.agree_edit_btn)
    private TextView agreeEditBtn;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private String itemId;
    private RefluxEditEntity.RefluxEditResult result;
    private StringBuffer stringBuffer = new StringBuffer(); //存权限id
    private RefluxModuleAdapter refluxModuleAdapter;
    private RefluxClothesImgAdapter refluxClothesImgAdapter;
    private String delImag;
    private CommonDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_reflux_edit_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        itemId = intent.getStringExtra("item_id");
        loadData(true,"");
        initView();
    }

    private void loadData(boolean isDefault,String refluxDescribe) {
        int code;
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        params.put("itemid",itemId);
        if (!isDefault){
            params.put("back_content",refluxDescribe);
            if (refluxEditNormal.isSelected()){
                params.put("is_back","1");
            }else if (refluxEditNotNormal.isSelected()){
                params.put("is_back","2");
            }else {
                params.put("is_back","1");
            }

            params.put("back_state",stringBuffer.toString());
            code = REQUEST_CODE_REFLUX;
        }else {
            code = REQUEST_CODE_REFLUX_EDIT;
        }
        requestHttpData(Constants.Urls.URL_POST_REFLUX_EDIT,code,
                FProtocol.HttpMethod.POST,params);
    }

    private void initView() {
        setCenterTitle("返流");
        initLoadingView(this);
        setLoadingStatus(LoadingStatus.LOADING);
        dialog = new CommonDialog(this);
        leftButton.setOnClickListener(this);
        refluxEditNormal.setOnClickListener(this);
        refluxEditNotNormal.setOnClickListener(this);
        agreeEditBtn.setOnClickListener(this);
        repulseEditBtn.setOnClickListener(this);

        etRefluxEditDescribe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refluxEditDescribeMaxNum.setText(s.length()+ "/20");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        gvRefluxStepList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stringBuffer.delete(0,stringBuffer.length());
                if (result.getRefluxEditModules().get(position).isSelect){
                    result.getRefluxEditModules().get(position).isSelect = false;
                    stringBuffer.append(result.getRefluxEditModules().get(position).getModule()); //如果没有选就remove掉已经选择的
                }else {

                    for (int i = 0; i < result.getRefluxEditModules().size(); i++) {
                        result.getRefluxEditModules().get(i).isSelect = false;
                    }
                    result.getRefluxEditModules().get(position).isSelect = true;
                    stringBuffer.append(result.getRefluxEditModules().get(position).getModule()); //选择的权限加入到集合里
                }
                refluxModuleAdapter.notifyDataSetChanged();
            }
        });

        refluxClothesImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (result.getData().getUrl().size() == 0){
                    //添加照片
                    Intent intent = new Intent(OfflineRefluxEditActivity.this,RefluxAddPhotoActivity.class);
                    intent.putExtra("item_id",itemId);
                    intent.putExtra("position",position);
                    startActivityForResult(intent,REQUEST_CODE_ADD_PHOTO);
                }else{
                    if (position < result.getData().getUrl().size()){
                        //查看大图
                        ArrayList<String> imgs = (ArrayList<String>) result.getData().getUrl();
                        Intent intent = new Intent(OfflineRefluxEditActivity.this,BigPhotoActivity.class);
                        intent.putExtra("imagePosition",position);
                        intent.putStringArrayListExtra("imagePath",imgs);
                        startActivity(intent);

                    }else if(position == result.getData().getUrl().size()){
                        //添加照片
                        Intent intent = new Intent(OfflineRefluxEditActivity.this,RefluxAddPhotoActivity.class);
                        intent.putExtra("extra_from",FROM_REFLUX_EDIT);
                        intent.putExtra("imgs",result.getData().getUrl().size());
                        intent.putExtra("item_id",itemId);
                        intent.putExtra("position",position);
                        startActivityForResult(intent,REQUEST_CODE_ADD_PHOTO);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
