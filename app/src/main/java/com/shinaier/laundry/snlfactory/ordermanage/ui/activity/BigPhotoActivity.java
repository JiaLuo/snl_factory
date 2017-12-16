package com.shinaier.laundry.snlfactory.ordermanage.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.common.view.SidesLipGallery;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.ordermanage.adapter.BigPhotoAdapter;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;


/**
 * Created by 张家洛 on 2017/3/15.
 */

public class BigPhotoActivity extends ToolBarActivity {
    @ViewInject(R.id.big_photo_gallery)
    private SidesLipGallery bigPhotoGallery;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;
    private ArrayList<String> imagePath;
    private int imagePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_photo_act);
        ViewInjectUtils.inject(this);
        Intent intent = getIntent();
        imagePath = intent.getStringArrayListExtra("imagePath");
        imagePosition = intent.getIntExtra("imagePosition", 0);
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
        BigPhotoAdapter adapter = new BigPhotoAdapter(this,imagePath);
        bigPhotoGallery.setAdapter(adapter);
        bigPhotoGallery.setSelection(imagePosition);
    }
}
