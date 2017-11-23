package com.shinaier.laundry.snlfactory.ordermanage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.common.view.SidesLipGallery;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.ToolBarActivity;
import com.shinaier.laundry.snlfactory.network.entity.OrderDetailEntity;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.BigImageAdapter;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;


/**
 * Created by 张家洛 on 2017/3/8.
 */

public class BigImageActivity extends ToolBarActivity {
    @ViewInject(R.id.big_image_gallery)
    private SidesLipGallery bigImageGallery;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private ArrayList<OrderDetailEntity.OrderDetailItem.Img> imagePath;
    private int imagePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_image_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        imagePosition = intent.getIntExtra("imagePosition", 0);
        imagePath = intent.getParcelableArrayListExtra("imagePath");
        initView();
    }
    private void initView() {
        setCenterTitle("查看大图");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        BigImageAdapter adapter = new BigImageAdapter(this,imagePath);
        bigImageGallery.setAdapter(adapter);
        bigImageGallery.setSelection(imagePosition);
    }
}
