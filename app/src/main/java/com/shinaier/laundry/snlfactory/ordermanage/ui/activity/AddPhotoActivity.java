package com.shinaier.laundry.snlfactory.ordermanage.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.BitmapUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.common.widget.ProgressImageView;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.UploadAddPhotoEntity;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.AddphotoAdapter;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;
import com.werb.pickphotoview.PickPhotoView;
import com.werb.pickphotoview.adapter.SpaceItemDecoration;
import com.werb.pickphotoview.util.PickConfig;
import com.werb.pickphotoview.util.PickUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;


/**
 * Created by 张家洛 on 2017/3/10.
 */

public class AddPhotoActivity extends ToolBarActivity {
    private static final int REQUEST_CODE_CLOTHES_PHOTO = 0x1;

    @ViewInject(R.id.photo_list)
    private RecyclerView photoList;
    @ViewInject(R.id.photo_num)
    private TextView photoNum;
    @ViewInject(R.id.photo_commit)
    private TextView photoCommit;
    @ViewInject(R.id.loading_img_anim)
    private ProgressImageView loadingImgAnim;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private List<String> temp = new ArrayList<>();
    private AddphotoAdapter adapter;
    private String orderId;
    private String itemId;
    private int upLoadImageNum = 0;
    private int upLoadImageNumRecord = 0; //记录图片上传个数
    private int from;
    private List<String> imgs;
    private int position;
    private ArrayList<String> allPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_photo_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        itemId = intent.getStringExtra("itemId");
        imgs = intent.getStringArrayListExtra("imgs");

        from = intent.getIntExtra("extra", 0);
        position = intent.getIntExtra("position", 0);
        if(from == CheckClothesActivity.FROM_CODE){
            photoNum.setText("添加图片不超过" + (12 - imgs.size()) + "张");
        }
        initView();
    }

    private void initView() {
        setCenterTitle("添加图片");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        photoCommit.setOnClickListener(new View.OnClickListener() {
            private File file;
            @Override
            public void onClick(View view) {
                if (upLoadImageNum == 0){
                    ToastUtil.shortShow(AddPhotoActivity.this,"请选择图片上传");
                    return;
                }
                upLoadImageNumRecord = 0;
                loadingImgAnim.setVisibility(View.VISIBLE);
                if(temp.size() >= 12){
                    for (int i = 0; i < 12; i++) {
                        file = new File(BitmapUtil.revitionLocalImage(AddPhotoActivity.this, temp.get(i)));
                        IdentityHashMap<String,String> params = new IdentityHashMap<>();
                        params.put("token", UserCenter.getToken(AddPhotoActivity.this));
                        params.put("orderid",orderId);
                        params.put("id",itemId);
                        requestHttpData(Constants.Urls.URL_POST_CLOTHES_PHOTO,REQUEST_CODE_CLOTHES_PHOTO, FProtocol.HttpMethod.POST
                                ,params,"file", file);
                    }
                }else {
                    for (int i = 0; i < temp.size(); i++) {
                        file = new File(BitmapUtil.revitionLocalImage(AddPhotoActivity.this, temp.get(i)));
                        IdentityHashMap<String,String> params = new IdentityHashMap<>();
                        params.put("token", UserCenter.getToken(AddPhotoActivity.this));
                        params.put("orderid",orderId);
                        params.put("id",itemId);
                        requestHttpData(Constants.Urls.URL_POST_CLOTHES_PHOTO,REQUEST_CODE_CLOTHES_PHOTO, FProtocol.HttpMethod.POST
                                ,params,"file", file);
                    }
                }
            }
        });
        adapter = new AddphotoAdapter(this,temp);
        GridLayoutManager manage = new GridLayoutManager(this,4);
        photoList.setLayoutManager(manage);
        photoList.addItemDecoration(new SpaceItemDecoration(PickUtils.getInstance(AddPhotoActivity.this).dp2px(PickConfig.ITEM_SPACE), 4));
        photoList.setAdapter(adapter);
        adapter.setAddPhotoListener(new AddphotoAdapter.AddPhotoListener() {
            @Override
            public void onAddPhoto() {
                if(from == CheckClothesActivity.FROM_CODE){
                    if(imgs.size() + temp.size() < 12){
                        startPickPhoto();
                    }else {
                        ToastUtil.shortShow(AddPhotoActivity.this,"图片不能超过12张");
                    }
                }else {

                    if(temp.size() < 12){
                        startPickPhoto();
                    }else {
                        ToastUtil.shortShow(AddPhotoActivity.this,"图片不能超过12张");
                    }
                }
            }
        });
    }

    private void startPickPhoto(){
        new PickPhotoView.Builder(AddPhotoActivity.this)
                .setPickPhotoSize(12)
                .setShowCamera(true)
                .setSpanCount(5)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 0){
            return;
        }
        if(data == null){
            return;
        }
        if (requestCode == PickConfig.PICK_PHOTO_DATA) {
            List<String> selectPaths = (List<String>) data.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT);

                if(temp.size() >= 12){
                    upLoadImageNum = 12;
                }else {
                    upLoadImageNum = selectPaths.size();
                }
                if(selectPaths != null){
                    temp.addAll(selectPaths);
                    adapter.notifyDataSetChanged();
                }

            if(from == CheckClothesActivity.FROM_CODE){
                if(12 - imgs.size() - temp.size() > 0){
                    photoNum.setText("添加图片不超过" + (12 - temp.size() - imgs.size()) + "张");
                }else {
                    photoNum.setText("添加图片不超过0张");
                }
            }else {
                if(12 - temp.size() > 0){
                    photoNum.setText("添加图片不超过" + (12 - temp.size()) + "张");
                }else {
                    photoNum.setText("添加图片不超过0张");
                }
            }
        }
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_CLOTHES_PHOTO:
                if (data != null){
                    UploadAddPhotoEntity uploadAddPhotoEntity = Parsers.getUploadAddPhotoEntity(data);
                    if (uploadAddPhotoEntity != null){
                        if (uploadAddPhotoEntity.getRetcode() == 0){
                            List<String> datas = uploadAddPhotoEntity.getDatas();
                            String photo = datas.get(0);
                            allPhotos.add(photo);

                            upLoadImageNumRecord++;
                            if (upLoadImageNumRecord == upLoadImageNum){
                                loadingImgAnim.setVisibility(View.GONE);
                                Intent intent = new Intent(this,CheckClothesActivity.class);
                                intent.putStringArrayListExtra("upload_photo",allPhotos);
                                intent.putExtra("position",position);
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                        }else {
                            ToastUtil.shortShow(this,uploadAddPhotoEntity.getStatus());
                        }
                    }
                }
                break;
        }
    }
}
