package com.shinaier.laundry.snlfactory.ordermanage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.common.network.FProtocol;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.CheckClothesEntities;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.EditPhotoAdapter;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.shinaier.laundry.snlfactory.view.WrapHeightGridView;

import java.util.ArrayList;
import java.util.IdentityHashMap;


/**
 * Created by 张家洛 on 2017/3/13.
 */

public class EditPhotoActivity extends ToolBarActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_DELETE_PHOTO = 0x1;

    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    @ViewInject(R.id.edit_clothes_img)
    private WrapHeightGridView editClothesImg;
    private CheckClothesEntities clothesEntities;
    private EditPhotoAdapter adapter;
    private String imgPath;
    private int position;
    private ArrayList<String> imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_photo_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        clothesEntities = intent.getParcelableExtra("entity");
        position = intent.getIntExtra("position", 0);
        initView();
    }

    private void initView() {
        setCenterTitle("编辑图片");
        leftButton.setOnClickListener(this);
        adapter = new EditPhotoAdapter(this,clothesEntities.getImgs());
        editClothesImg.setAdapter(adapter);
        editClothesImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<String> imgs = (ArrayList<String>) clothesEntities.getImgs();
                Intent intent = new Intent(EditPhotoActivity.this,BigPhotoActivity.class);
                intent.putExtra("imagePosition",i);
                intent.putStringArrayListExtra("imagePath",imgs);
                startActivity(intent);
            }
        });
        adapter.setDeletePhotoListener(new EditPhotoAdapter.DeletePhotoListener() {
            @Override
            public void onDelete(int position) {
                imgPath = clothesEntities.getImgs().get(position);
                IdentityHashMap<String,String> params = new IdentityHashMap<>();
                params.put("token", UserCenter.getToken(EditPhotoActivity.this));
                params.put("image",imgPath);
                params.put("orderid",clothesEntities.getOrderId());
                params.put("id",clothesEntities.getId());
                requestHttpData(Constants.Urls.URL_POST_DELETE_CLOTHES_PHOTO,REQUEST_CODE_DELETE_PHOTO, FProtocol.HttpMethod.POST,params);

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_button:
                if (imgs != null){
                    backPreviousPage();
                }
                finish();
                break;
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_DELETE_PHOTO:
                if(data != null){
                    Entity entity = Parsers.getEntity(data);
                    if(entity.getRetcode() == 0){
                        imgs = (ArrayList<String>) clothesEntities.getImgs();
                        imgs.remove(imgPath);
                        adapter.notifyDataSetChanged();
                        if(clothesEntities.getImgs().size() == 0){
                            backPreviousPage();
                        }
                    }else {
                        ToastUtil.shortShow(this,entity.getStatus());
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (imgs != null){
            backPreviousPage();
        }
        super.onBackPressed();
    }

    private void backPreviousPage() {
            Intent intent = new Intent(this,CheckClothesActivity.class);
            intent.putExtra("position",position);
            intent.putStringArrayListExtra("photo_entity",imgs);
            setResult(RESULT_OK,intent);
            finish();
    }
}
