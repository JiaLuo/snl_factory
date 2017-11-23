package com.shinaier.laundry.snlfactory.ordermanage.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.google.gson.Gson;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.QuestionSettingSuccessEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.QuestionExpandableAdapter;
import com.shinaier.laundry.snlfactory.ordermanage.entities.QuestionsEntity;
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

public class QuestionInfoActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_QUESTION_SETTING = 0x1;

    @ViewInject(R.id.select_question)
    private FlowLayout selectQuestion;
    @ViewInject(R.id.question_list)
    private PinnedHeaderExpandableListView questionList;
    @ViewInject(R.id.question_confirm)
    private TextView questionConfirm;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private List<String> cquestionTitle = new ArrayList<>();
    private List<String> outList = new ArrayList<>();
    private QuestionsEntity questionsEntitys;
    private QuestionExpandableAdapter adapter;
    private TextView textView;
    private StringBuffer stringBuffer = new StringBuffer();
    private String itemId;
    private String extraQuestion;
    private List<String> questionsList = new ArrayList<>();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_info_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        extraQuestion = intent.getStringExtra("entity");
        position = intent.getIntExtra("position", 0);
        initView();
        initData();
    }

    private void initView() {
        setCenterTitle("描述设置");
        questionConfirm.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        cquestionTitle.add("污渍情况（食品类）");
        cquestionTitle.add("污渍情况（颜色类）");
        cquestionTitle.add("污渍情况（其他生活类）");
        cquestionTitle.add("破损情况（衣物类）");
        cquestionTitle.add("破损情况（配饰类）");
        cquestionTitle.add("破损情况（鞋或皮革类）");
        cquestionTitle.add("褪色情况");
        cquestionTitle.add("材质");
        cquestionTitle.add("洗后预估述");
        cquestionTitle.add("洗后注意事项");
        cquestionTitle.add("配件");
        cquestionTitle.add("其他");
        //读取本地数据
        try{
            InputStream is = getAssets().open("questions.txt");
            int size = is.available();
            byte [] bytes = new byte[size];
            is.read(bytes);
            is.close();
            String txt = new String(bytes);
            Gson gson = new Gson();
            questionsEntitys = gson.fromJson(txt, QuestionsEntity.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initData() {
        //设置悬浮头部VIEW
        questionList.setHeaderView(getLayoutInflater().inflate(R.layout.group_head,
                questionList, false));
        adapter = new QuestionExpandableAdapter(questionsEntitys.getQuestions(), cquestionTitle, getApplicationContext(),questionList);
        questionList.setAdapter(adapter);

        if (!TextUtils.isEmpty(extraQuestion)){
            String[] split = extraQuestion.split(",");
            for (int i = 0; i <split.length ; i++) {
                questionsList.add(split[i]);
            }
            for (int k = 0; k < questionsList.size(); k++) {
                String s = questionsList.get(k);
                for (int i = 0; i < 4 ; i++) {
                    for (int j = 0; j < questionsEntitys.getQuestions().get(i).size(); j++) {
                        if (s.equals(questionsEntitys.getQuestions().get(i).get(j).getQuestion())){
                            questionsEntitys.getQuestions().get(i).get(j).setIscheck(1);
                        }
                    }
                }
            }
            adapter.notifyDataSetChanged();
            outList.addAll(questionsList);
            initFlow();
        }

        adapter.setPositionListener(new QuestionExpandableAdapter.PositionListener() {
            @Override
            public void onClick(int groupPosition, int childPosition, ImageView iv) {
                if (questionsEntitys.getQuestions().get(groupPosition).get(childPosition).getIscheck() == 0){
                    outList.add(questionsEntitys.getQuestions().get(groupPosition).get(childPosition).getQuestion());
                    questionsEntitys.getQuestions().get(groupPosition).get(childPosition).setIscheck(1);
                }else{
                    questionsEntitys.getQuestions().get(groupPosition).get(childPosition).setIscheck(0);
                    for (int i = 0; i < outList.size(); i++) {
                        if( outList.get(i).equals(questionsEntitys.getQuestions().get(groupPosition).get(childPosition).getQuestion())){
                            outList.remove(i);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                initFlow();
            }

        });
        questionList.expandGroup(0);
        //设置单个分组展开
//        colorsList.setOnGroupClickListener(new GroupClickListener());
    }

    private void initFlow(){
        selectQuestion.removeAllViews();
        if (outList != null && outList.size() > 0){
            for (int i = 0; i < outList.size(); i++){
                textView = new TextView(this);
                textView.setBackgroundResource(R.drawable.login);
                textView.setText(outList.get(i));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                textView.setTextColor(Color.parseColor("#ffffff"));
                textView.setGravity(Gravity.CENTER);
                selectQuestion.addView(textView);
                final int j = i;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int k = 0; k < 4; k++) {
                            for (int l = 0; l <questionsEntitys.getQuestions().get(k).size() ; l++) {
                                if (questionsEntitys.getQuestions().get(k).get(l).getQuestion().equals(outList.get(j))) {
                                    questionsEntitys.getQuestions().get(k).get(l).setIscheck(0);
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
            selectQuestion.setPadding(padding,padding,padding,padding);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.question_confirm:
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

                String questionString = stringBuffer.toString();
                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                params.put("token", UserCenter.getToken(this));
                params.put("id",itemId);
                params.put("item_note",questionString);
                requestHttpData(Constants.Urls.URL_POST_QUESTION_SETTING,REQUEST_CODE_QUESTION_SETTING, FProtocol.HttpMethod.POST,params);
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
            case REQUEST_CODE_QUESTION_SETTING:
                if(data != null){
                    QuestionSettingSuccessEntities questionSettingSuccessEntities = Parsers.getQuestionSettingSuccessEntities(data);
                    if(questionSettingSuccessEntities != null){
                        if(questionSettingSuccessEntities.getRetcode() == 0){
                            Intent intent = new Intent(this,CheckClothesActivity.class);
                            intent.putExtra("question",stringBuffer.toString());
                            intent.putExtra("position",position);
                            setResult(RESULT_OK,intent);
                            finish();
                        }else {
                            ToastUtil.shortShow(this,questionSettingSuccessEntities.getStatus());
                        }
                    }
                }
                break;
        }
    }
}
