package com.shinaier.laundry.snlfactory.ordermanage.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
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
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.json.GsonObjectDeserializer;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.AssessExpandableAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.entities.AssessEntity;
import com.shinaier.laundry.snlfactory.ordermanage.entities.ParserEntity;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.FlowLayout;
import com.shinaier.laundry.snlfactory.view.PinnedHeaderExpandableListView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * 洗后预估述
 * Created by 张家洛 on 2017/11/4.
 */

public class CleanedAssessmentActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CLEANED_ASSESS = 0x1;

    @ViewInject(R.id.et_cleaned_assessment_describe)
    private EditText etCleanedAssessmentDescribe;
    @ViewInject(R.id.cleaned_assessment_max_num)
    private TextView cleanedAssessmentMaxNum;
    @ViewInject(R.id.cleaned_assessment)
    private FlowLayout cleanedAssessment;
    @ViewInject(R.id.cleaned_assessment_list)
    private PinnedHeaderExpandableListView cleanedAssessmentList;
    @ViewInject(R.id.cleaned_assessment_confirm)
    private TextView cleanedAssessmentConfirm;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    private int position;
    private String itemId;

    private List<String> assessTitle = new ArrayList<>();
    private AssessEntity assessEntity;
    private AssessExpandableAdapter adapter;
    private String extraAssess;
    private List<String> assessesList = new ArrayList<>();
    private List<String> outList = new ArrayList<>();
    private TextView textView;
    private StringBuffer stringBuffer = new StringBuffer();
    private String inputCleanedAssess;
    private String assessText;
    private String forecastData;

    private Gson gson = GsonObjectDeserializer.produceGson();
    private ParserEntity parserEntity;
    private List<String> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cleaned_assessment_act);
        ViewInjectUtils.inject(this);
        itemId = getIntent().getStringExtra("item_id");
        position = getIntent().getIntExtra("position", 0);
        forecastData = getIntent().getStringExtra("forecast_data");
        initView();
        initData();
    }

    private void initView() {
        setCenterTitle("洗后预估");
        parserEntity = gson.fromJson(forecastData, ParserEntity.class);
        options = parserEntity.getOptions();
        etCleanedAssessmentDescribe.setText(parserEntity.getContent());

        leftButton.setOnClickListener(this);
        cleanedAssessmentConfirm.setOnClickListener(this);
        etCleanedAssessmentDescribe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cleanedAssessmentMaxNum.setText(s.length()+ "/20");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        assessTitle.add("洗后预估述");
        //读取本地数据
        try{
            InputStream is = getAssets().open("assess.txt");
            int size = is.available();
            byte [] bytes = new byte[size];
            is.read(bytes);
            is.close();
            String txt = new String(bytes);
            Gson gson = new Gson();
            assessEntity = gson.fromJson(txt, AssessEntity.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void initData() {
        //设置悬浮头部VIEW
        cleanedAssessmentList.setHeaderView(getLayoutInflater().inflate(R.layout.group_head,
                cleanedAssessmentList, false));
        adapter = new AssessExpandableAdapter(assessEntity.getAssesses(), assessTitle, getApplicationContext(),cleanedAssessmentList);
        cleanedAssessmentList.setAdapter(adapter);

        for (int k = 0; k < options.size(); k++) {
            String s = options.get(k);
            for (int i = 0; i < 1 ; i++) {
                for (int j = 0; j < assessEntity.getAssesses().get(i).size(); j++) {
                    if (s.equals(assessEntity.getAssesses().get(i).get(j).getAssesses())){
                        assessEntity.getAssesses().get(i).get(j).setIscheck(1);
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
        outList.addAll(options);
        initFlow();

        adapter.setPositionListener(new AssessExpandableAdapter.PositionListener() {
            @Override
            public void onClick(int groupPosition, int childPosition, ImageView iv) {
                if (assessEntity.getAssesses().get(groupPosition).get(childPosition).getIscheck() == 0){
                    outList.add(assessEntity.getAssesses().get(groupPosition).get(childPosition).getAssesses());
                    assessEntity.getAssesses().get(groupPosition).get(childPosition).setIscheck(1);
                }else{
                    assessEntity.getAssesses().get(groupPosition).get(childPosition).setIscheck(0);
                    for (int i = 0; i < outList.size(); i++) {
                        if( outList.get(i).equals(assessEntity.getAssesses().get(groupPosition).get(childPosition).getAssesses())){
                            outList.remove(i);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                initFlow();
            }

        });
        cleanedAssessmentList.expandGroup(0);
        //设置单个分组展开
//        colorsList.setOnGroupClickListener(new GroupClickListener());
    }

    private void initFlow(){
        cleanedAssessment.removeAllViews();
        if (outList != null && outList.size() > 0){
            for (int i = 0; i < outList.size(); i++){
                textView = new TextView(this);
                textView.setBackgroundResource(R.drawable.login);
                textView.setText(outList.get(i));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                textView.setTextColor(Color.parseColor("#ffffff"));
                textView.setGravity(Gravity.CENTER);
                cleanedAssessment.addView(textView);
                final int j = i;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int k = 0; k < 1; k++) {
                            for (int l = 0; l <assessEntity.getAssesses().get(k).size() ; l++) {
                                if (assessEntity.getAssesses().get(k).get(l).getAssesses().equals(outList.get(j))) {
                                    assessEntity.getAssesses().get(k).get(l).setIscheck(0);
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
            cleanedAssessment.setPadding(padding,padding,padding,padding);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_button:
                finish();
                break;
            case R.id.cleaned_assessment_confirm:
                String inputAssessDescribe = etCleanedAssessmentDescribe.getText().toString();
                parserEntity.setOptions(outList);
                parserEntity.setContent(inputAssessDescribe);
                String s = gson.toJson(parserEntity);

                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                params.put("token", UserCenter.getToken(this));
                params.put("item_id",itemId);
                params.put("data",s);
                requestHttpData(Constants.Urls.URL_POST_CLEANED_ASSESS,REQUEST_CODE_CLEANED_ASSESS, FProtocol.HttpMethod.POST,params);
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_CLEANED_ASSESS:
                if (data != null){
                    Entity entity = Parsers.getEntity(data);
                    if (entity != null){
                        if (entity.getRetcode() == 0){
                            Intent intent = new Intent(this,CheckClothesActivity.class);
                            intent.putExtra("assess",parserEntity);
                            intent.putExtra("position",position);
                            setResult(RESULT_OK,intent);
                            finish();
                        }else {
                            ToastUtil.shortShow(this,entity.getStatus());
                        }
                    }
                }
                break;
        }
    }
}
