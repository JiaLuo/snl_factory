package com.shinaier.laundry.snlfactory.util;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GRAY;

/**
 * Created by 张家洛 on 2017/8/19.
 */

public class BuildQr {
    /**
     * 生成二维码
     * @param code 二维码的信息
     * @param format 二维码格式
     * @param width 二维码宽
     * @param height 二维码高
     * @return bitmap
     */
    public static Bitmap setQvImageView(String code, BarcodeFormat format, int width, int height) {
        Map<EncodeHintType, Object> hints = null;
        if(code != null){
            String encoding = guessAppropriateEncoding(code);
            if (encoding != null) {
                hints = new EnumMap<>(EncodeHintType.class);
                hints.put(EncodeHintType.CHARACTER_SET, encoding);
            }
        }

        BitMatrix result = null;
        try {
            result = new MultiFormatWriter().encode(code, format, width, height, hints);
        } catch (IllegalArgumentException iae) {
            return null;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (result == null)
            return null;
        width = result.getWidth();
        height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : GRAY;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
//        imageView.setImageBitmap(bitmap);
    }

    /**
     * 获取合适的编码方式
     * @param contents
     * @return
     */
    private static String guessAppropriateEncoding(CharSequence contents) {
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF)
                return "UTF-8";
        }
        return null;
    }
}
