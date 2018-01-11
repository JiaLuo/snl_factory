package com.shinaier.laundry.snlfactory.ordermanage.entities;


import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

import com.google.gson.annotations.SerializedName;
import com.shinaier.laundry.snlfactory.util.CommonTools;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsBean implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

    @SerializedName("id")
    private int id;
    @SerializedName("item_name")
    private String item_name;//名
    @SerializedName("item_price")
    private BigDecimal item_price;//价格
    @SerializedName("item_discount")
    private String item_discount;//打折
    @SerializedName("image")
    private String image;//图片
    @SerializedName("has_discount")
    private String has_discount;
    @SerializedName("item_real_price")
    private String itemRealPrice;
    @SerializedName("item_count") //用户选择的件数
    private long itemCount;
    @SerializedName("item_sum") // 用户选择的件数的总价
    private double itemSum;

    public String getItemRealPrice() {

        return itemRealPrice;
    }

    public void setItemRealPrice(String itemRealPrice) {
        this.itemRealPrice = itemRealPrice;
    }

    public long getItemCount() {
        return itemCount;
    }

    public void setItemCount(long itemCount) {
        this.itemCount = itemCount;
    }

    public double getItemSum() {
        return itemSum;
    }

    public void setItemSum(double itemSum) {
        this.itemSum = itemSum;
    }

    public String getHas_discount() {
        return has_discount;
    }

    public void setHas_discount(String has_discount) {
        this.has_discount = has_discount;
    }

    private String sale;//销量
    private String isCommand;//是否推荐


    private String typeName;//分类名称
    private String typeNameEn;//英文名称

    private long selectCount;
    private int type;//类型 0是标题 1商品




    public SpannableString getStrPrice(Context context) {
        String priceStr = String.valueOf(getItem_price());
        SpannableString spanString = new SpannableString("¥" + priceStr);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(CommonTools.sp2px(context, 11));
        spanString.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spanString;
    }

    public SpannableString getStrPrice(Context context, BigDecimal price) {
        String priceStr = String.valueOf(price);
        SpannableString spanString = new SpannableString("¥" + priceStr);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(CommonTools.sp2px(context, 11));
        spanString.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spanString;
    }


    public String getTypeNameEn() {
        return typeNameEn;
    }

    public void setTypeNameEn(String typeNameEn) {
        this.typeNameEn = typeNameEn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getIsCommand() {
        return isCommand;
    }

    public void setIsCommand(String isCommand) {
        this.isCommand = isCommand;
    }

    public BigDecimal getItem_price() {
        return item_price;
    }

    public void setItem_price(BigDecimal item_price) {
        this.item_price = item_price;
    }

    public String getItem_discount() {
        return item_discount;
    }

    public void setItem_discount(String item_discount) {
        this.item_discount = item_discount;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(long selectCount) {
        this.selectCount = selectCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
