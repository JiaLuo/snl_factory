package com.shinaier.laundry.snlfactory.ordermanage.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.google.gson.Gson;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.ColorSettingSuccessEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.ColorExpandableAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.entities.ColorsEntity;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.FlowLayout;
import com.shinaier.laundry.snlfactory.view.PinnedHeaderExpandableListView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;



/**
 * Created by 张家洛 on 2017/3/2.
 */

public class ColorSettingActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_COLOR_SETTING = 0x1;

    @ViewInject(R.id.select_color)
    private FlowLayout selectColor;
    @ViewInject(R.id.color_confirm)
    private TextView colorConfirm;
    @ViewInject(R.id.colors_list)
    private PinnedHeaderExpandableListView colorsList;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private List<String> colorTitle = new ArrayList<>();
    private List<String> outList = new ArrayList<>();
    private ColorsEntity colorsEntities;
    private ColorExpandableAdapter adapter;
    private int expandFlag = -1;//控制列表的展开
    private String itemId;
    private StringBuffer stringBuffer = new StringBuffer();
    private String extraColor;
    private List<String> colorList = new ArrayList<>();
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_setting_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        extraColor = intent.getStringExtra("entity");
        position = intent.getIntExtra("position", 0);
        initView();
        initData();
    }

    private void initData() {
        //设置悬浮头部VIEW
        colorsList.setHeaderView(getLayoutInflater().inflate(R.layout.group_head,
                colorsList, false));
        adapter = new ColorExpandableAdapter(colorsEntities.getColorsEntities(), colorTitle, getApplicationContext(),colorsList);
        colorsList.setAdapter(adapter);

        if (!TextUtils.isEmpty(extraColor)){
            String[] split = extraColor.split(",");
            for (int i = 0; i <split.length ; i++) {
                colorList.add(split[i]);
            }
            for (int k = 0; k < colorList.size(); k++) {
                String s = colorList.get(k);
                for (int i = 0; i < 4 ; i++) {
                    for (int j = 0; j < colorsEntities.getColorsEntities().get(i).size(); j++) {
                        if (s.equals(colorsEntities.getColorsEntities().get(i).get(j).getColor())){
                            colorsEntities.getColorsEntities().get(i).get(j).setIscheck(1);
                        }
                    }
                }
            }
            adapter.notifyDataSetChanged();
            outList.addAll(colorList);
            initFlow();
        }

        adapter.setPositionListener(new ColorExpandableAdapter.PositionListener() {
            @Override
            public void onClick(int groupPosition, int childPosition, ImageView iv) {

                if (colorsEntities.getColorsEntities().get(groupPosition).get(childPosition).getIscheck() == 0){
                    outList.add(colorsEntities.getColorsEntities().get(groupPosition).get(childPosition).getColor());
                    colorsEntities.getColorsEntities().get(groupPosition).get(childPosition).setIscheck(1);
                }else{
                    colorsEntities.getColorsEntities().get(groupPosition).get(childPosition).setIscheck(0);
                    for (int i = 0; i < outList.size(); i++) {
                        if( outList.get(i).equals(colorsEntities.getColorsEntities().get(groupPosition).get(childPosition).getColor())){
                            outList.remove(i);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                initFlow();
            }

        });
        colorsList.expandGroup(0);
        //设置单个分组展开
//        colorsList.setOnGroupClickListener(new GroupClickListener());
    }

    private void initView() {
        setCenterTitle("颜色设置");
        colorConfirm.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        colorTitle.add("花色");
        colorTitle.add("暖色调（纯色）");
        colorTitle.add("中色调（纯色）");
        colorTitle.add("冷色调（纯色）");

        //读取本地数据
        try{
            InputStream is = getAssets().open("colors.txt");
            int size = is.available();
            byte [] bytes = new byte[size];
            is.read(bytes);
            is.close();
            String txt = new String(bytes);
            Gson gson = new Gson();
            colorsEntities = gson.fromJson(txt, ColorsEntity.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initFlow(){
        selectColor.removeAllViews();
        if (outList != null && outList.size() > 0){
            for (int i = 0; i < outList.size(); i++){
                TextView textView = new TextView(this);
                textView.setBackgroundResource(R.drawable.login);
                textView.setText(outList.get(i));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                textView.setTextColor(Color.parseColor("#ffffff"));
                textView.setGravity(Gravity.CENTER);
                selectColor.addView(textView);
                final int j = i;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int k = 0; k < 4; k++) {
                            for (int l = 0; l <colorsEntities.getColorsEntities().get(k).size() ; l++) {
                                if (colorsEntities.getColorsEntities().get(k).get(l).getColor().equals(outList.get(j))) {
                                    colorsEntities.getColorsEntities().get(k).get(l).setIscheck(0);
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }
                        outList.remove(j);
                        initFlow();
                    }

                });
            }
            int padding = 24;
            selectColor.setPadding(padding,padding,padding,padding);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.color_confirm:
                stringBuffer.delete(0,stringBuffer.length());
                for (int i = 0; i < outList.size(); i++) {
                    if(i == 0){
                        if(outList.size() == 1){
                            stringBuffer.append(outList.get(i));
                        }else {
                            stringBuffer.append(outList.get(i)).append(",");
                        }
                    }else if(i > 0 && i < outList.size() -1){
                        stringBuffer.append(outList.get(i)).append(",");
                    }else {
                        stringBuffer.append(outList.get(i));
                    }
                }

                String colorString = stringBuffer.toString();
                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                params.put("token", UserCenter.getToken(this));
                params.put("id",itemId);
                params.put("color",colorString);
                requestHttpData(Constants.Urls.URL_POST_COLOR_SETTING,REQUEST_CODE_COLOR_SETTING, FProtocol.HttpMethod.POST,params);
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
            case REQUEST_CODE_COLOR_SETTING:
                if(data != null){
                    ColorSettingSuccessEntities colorSettingSuccessEntities = Parsers.getColorSettingSuccessEntities(data);
                    if(colorSettingSuccessEntities != null){
                        if(colorSettingSuccessEntities.getRetcode() == 0){
//                            ToastUtil.shortShow(this,"编辑成功");
//                            finish();
                            Intent intent = new Intent(this,CheckClothesActivity.class);
                            intent.putExtra("color",stringBuffer.toString());
                            intent.putExtra("position",position);
                            setResult(RESULT_OK,intent);
                            finish();

                        }else {
                            ToastUtil.shortShow(this,colorSettingSuccessEntities.getStatus());
                        }
                    }
                }
                break;
        }
    }

    class GroupClickListener implements ExpandableListView.OnGroupClickListener {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {
            if (expandFlag == -1) {
                // 展开被选的group
                colorsList.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                colorsList.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            } else if (expandFlag == groupPosition) {
                colorsList.collapseGroup(expandFlag);
                expandFlag = -1;
            } else {
                colorsList.collapseGroup(expandFlag);
                // 展开被选的group
                colorsList.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                colorsList.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            }
            return true;
        }
    }
}
