package com.shinaier.laundry.snlfactory.network.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shinaier.laundry.snlfactory.manage.entities.AddEmployeeJurisdictionEntity;
import com.shinaier.laundry.snlfactory.network.entity.AddCommodityEntities;
import com.shinaier.laundry.snlfactory.network.entity.AddExecEntity;
import com.shinaier.laundry.snlfactory.network.entity.AddItemShowEntities;
import com.shinaier.laundry.snlfactory.network.entity.AddProjectOfflineConfirmEntity;
import com.shinaier.laundry.snlfactory.network.entity.BuildOrderEntity;
import com.shinaier.laundry.snlfactory.network.entity.BuyPlateformCardEntity;
import com.shinaier.laundry.snlfactory.network.entity.CancelOrderEntities;
import com.shinaier.laundry.snlfactory.network.entity.CashBackEntity;
import com.shinaier.laundry.snlfactory.network.entity.CashConponEntity;
import com.shinaier.laundry.snlfactory.network.entity.CashCouponCenterEntity;
import com.shinaier.laundry.snlfactory.network.entity.CashCouponEntity;
import com.shinaier.laundry.snlfactory.network.entity.CheckClothesEntities;
import com.shinaier.laundry.snlfactory.network.entity.ColorSettingSuccessEntities;
import com.shinaier.laundry.snlfactory.network.entity.CooperativeStoreEntities;
import com.shinaier.laundry.snlfactory.network.entity.CooperativeStoreOperateEntities;
import com.shinaier.laundry.snlfactory.network.entity.CraftworkAddPriceEntities;
import com.shinaier.laundry.snlfactory.network.entity.EditCommodityEntity;
import com.shinaier.laundry.snlfactory.network.entity.EditEmployeeInfoEntity;
import com.shinaier.laundry.snlfactory.network.entity.EditItemShowEntities;
import com.shinaier.laundry.snlfactory.network.entity.EmployeeEntity;
import com.shinaier.laundry.snlfactory.network.entity.Entity;
import com.shinaier.laundry.snlfactory.network.entity.EvaluateEntities;
import com.shinaier.laundry.snlfactory.network.entity.IntoFactoryEntities;
import com.shinaier.laundry.snlfactory.network.entity.InviteFriendEntity;
import com.shinaier.laundry.snlfactory.network.entity.LeaveFactoryEntities;
import com.shinaier.laundry.snlfactory.network.entity.ManageCommodityEntities;
import com.shinaier.laundry.snlfactory.network.entity.ManageFinanceDetailEntities;
import com.shinaier.laundry.snlfactory.network.entity.ManageFinanceEntities;
import com.shinaier.laundry.snlfactory.network.entity.MerchantCardInfoEntity;
import com.shinaier.laundry.snlfactory.network.entity.MerchantCardListEntities;
import com.shinaier.laundry.snlfactory.network.entity.MessageDetailEntity;
import com.shinaier.laundry.snlfactory.network.entity.MessageEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineAddVisitorEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineAuthorityEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineChangeMemberInfoEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineCustomInfoEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineHangOnEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineHomeEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberBalanceEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberConsumeListEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberDetailEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberNumEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberRechargeEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineMemberRechargeListEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineOrderDetailEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineOrderPayEntity;
import com.shinaier.laundry.snlfactory.network.entity.OfflineRefluxEntity;
import com.shinaier.laundry.snlfactory.network.entity.OperateAnalysisEntities;
import com.shinaier.laundry.snlfactory.network.entity.OrderCleanEntities;
import com.shinaier.laundry.snlfactory.network.entity.OrderCleaningEntities;
import com.shinaier.laundry.snlfactory.network.entity.OrderDetailEntity;
import com.shinaier.laundry.snlfactory.network.entity.OrderDisposeEntities;
import com.shinaier.laundry.snlfactory.network.entity.OrderInquiryEntities;
import com.shinaier.laundry.snlfactory.network.entity.OrderPrintEntity;
import com.shinaier.laundry.snlfactory.network.entity.OrderSearchEntity;
import com.shinaier.laundry.snlfactory.network.entity.OrderSendEntities;
import com.shinaier.laundry.snlfactory.network.entity.OrderTakeOrderEntities;
import com.shinaier.laundry.snlfactory.network.entity.PhotoVerifyCodeEntity;
import com.shinaier.laundry.snlfactory.network.entity.PlatformPaySuccessEntity;
import com.shinaier.laundry.snlfactory.network.entity.PrintRechargeEntity;
import com.shinaier.laundry.snlfactory.network.entity.QuestionSettingSuccessEntities;
import com.shinaier.laundry.snlfactory.network.entity.RechargeSuccessEntity;
import com.shinaier.laundry.snlfactory.network.entity.RefluxEditEntity;
import com.shinaier.laundry.snlfactory.network.entity.SettingsEntity;
import com.shinaier.laundry.snlfactory.network.entity.StatisticsIncomeEntity;
import com.shinaier.laundry.snlfactory.network.entity.StatisticsNoPayEntity;
import com.shinaier.laundry.snlfactory.network.entity.StoreDetailEntity;
import com.shinaier.laundry.snlfactory.network.entity.StoreEntity;
import com.shinaier.laundry.snlfactory.network.entity.StoreInfoEntity;
import com.shinaier.laundry.snlfactory.network.entity.TakeClothesEntity;
import com.shinaier.laundry.snlfactory.network.entity.TakeTimeEntity;
import com.shinaier.laundry.snlfactory.network.entity.UpdataEntity;
import com.shinaier.laundry.snlfactory.network.entity.UploadAddPhotoEntity;
import com.shinaier.laundry.snlfactory.network.entity.UserEntity;
import com.shinaier.laundry.snlfactory.network.entity.VerifyCodeEntity;
import com.shinaier.laundry.snlfactory.network.entity.WashEntity;
import com.shinaier.laundry.snlfactory.network.entity.WashingEntity;
import com.shinaier.laundry.snlfactory.network.json.GsonObjectDeserializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/8.
 */

public class Parsers {
    public static Gson gson = GsonObjectDeserializer.produceGson();

    /**
     * 获取商户信息
     * @param data
     * @return
     */
    public static UserEntity getUserEntity(String data){
        return gson.fromJson(data,new TypeToken<UserEntity>(){}.getType());
    }

    /**
     * 获取商户管理界面信息
     * @param data
     * @return
     */
    public static StoreEntity getStoreEntity(String data){
//        StoreEntity storeEntity = null;
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            String data1 = jsonObject.optString("data");
//            storeEntity = gson.fromJson(data1,new TypeToken<StoreEntity>(){}.getType());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return storeEntity;
        return gson.fromJson(data,new TypeToken<StoreEntity>(){}.getType());
    }

    /**
     * 获取财务对账信息
     * @param data
     * @return
     */
        public static ManageFinanceEntities getManageFinanceEntities(String data){
//        ManageFinanceEntities manageFinanceEntities = null;
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            String data1 = jsonObject.optString("data");
//            manageFinanceEntities = gson.fromJson(data1,new TypeToken<ManageFinanceEntities>(){}.getType());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return manageFinanceEntities;
        return gson.fromJson(data,new TypeToken<ManageFinanceEntities>(){}.getType());
    }

    /**
     * 获取财务对账财务信息
     * @param data
     * @return
     */
    public static ManageFinanceDetailEntities getManageFinanceDetailEntities(String data){
        ManageFinanceDetailEntities manageFinanceDetailEntities = null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String data1 = jsonObject.optString("data");
            manageFinanceDetailEntities = gson.fromJson(data,new TypeToken<ManageFinanceDetailEntities>(){}.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return manageFinanceDetailEntities;
    }

    /**
     * 获取商品管理展示信息
     * @param data
     * @return
     */
    public static ManageCommodityEntities getManageCommodityEntities(String data){
//        ManageCommodityEntities manageCommodityEntities = null;
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            String data1 = jsonObject.optString("data");
//            manageCommodityEntities = gson.fromJson(data1,new TypeToken<ManageCommodityEntities>(){}.getType());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return manageCommodityEntities;
        return gson.fromJson(data,new TypeToken<ManageCommodityEntities>(){}.getType());
    }

    /**
     * 获取添加商品展示数据
     * @param data
     * @return
     */
    public static AddCommodityEntities getAddCommodityEntities(String data){
//        AddCommodityEntities addCommodityEntities = null;
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            String data1 = jsonObject.optString("data");
//            addCommodityEntities = gson.fromJson(data1,new TypeToken<AddCommodityEntities>(){}.getType());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return addCommodityEntities;
        return gson.fromJson(data,new TypeToken<AddCommodityEntities>(){}.getType());
    }

    /**
     * 邀请好友获取数据
     * @param data
     * @return
     */
    public static InviteFriendEntity getInviteFriendEntity(String data){
        InviteFriendEntity inviteFriendEntity = null;
        inviteFriendEntity = gson.fromJson(data,new TypeToken<InviteFriendEntity>(){}.getType());
        return  inviteFriendEntity;
    }

    public static Entity getEntity(String data){
        Entity entity = gson.fromJson(data,new TypeToken<Entity>(){}.getType());
        return entity;
    }

    /**
     * 获取评价列表数据展示
     * @param data
     * @return
     */
    public static EvaluateEntities getEvaluateEntities(String data){
        EvaluateEntities evaluateEntities = null;
        evaluateEntities = gson.fromJson(data,new TypeToken<EvaluateEntities>(){}.getType());

        return evaluateEntities;
    }

    /**
     * 获取消息列表
     * @param data
     * @return
     */
    public static MessageEntity getMessageEntity(String data){
        return gson.fromJson(data,new TypeToken<MessageEntity>(){}.getType());
    }

    /**
     * 获取员工管理数据
     * @param data
     * @return
     */
    public static EmployeeEntity getEmployeeEntity(String data){
        return gson.fromJson(data,new TypeToken<EmployeeEntity>(){}.getType());
    }

    /**
     * 获取门店信息
     * @param data
     * @return
     */
    public static StoreInfoEntity getStoreInfoEntity(String data){
//        StoreInfoEntity storeInfoEntity = null;
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            String data1 = jsonObject.optString("data");
//            storeInfoEntity = gson.fromJson(data1,new TypeToken<StoreInfoEntity>(){}.getType());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return storeInfoEntity;
        return gson.fromJson(data,new TypeToken<StoreInfoEntity>(){}.getType());
    }

    /**
     * 获取返现记录
     * @param data
     * @return
     */
    public static CashBackEntity getCashBackEntity(String data){
        return gson.fromJson(data,new TypeToken<CashBackEntity>(){}.getType());
    }

    /**
     * 设置页面数据
     * @param data
     * @return
     */
    public static SettingsEntity getSettingsEntity(String data){
//        SettingsEntity settingsEntity = null;
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            String data1 = jsonObject.optString("data");
//            settingsEntity = gson.fromJson(data1,new TypeToken<SettingsEntity>(){}.getType());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return settingsEntity;
        return gson.fromJson(data,new TypeToken<SettingsEntity>(){}.getType());
    }

    /**
     * 获取订单查询数据
     * @param data
     * @return
     */
    public static OrderInquiryEntities getOrderInquiryEntities(String data){
        return gson.fromJson(data,new TypeToken<OrderInquiryEntities>(){}.getType());
    }

    /**
     * 获取订单待处理状态数据
     * @param data
     * @return
     */
    public static OrderDisposeEntities getOrderDisposeEntities(String data){
        return gson.fromJson(data,new TypeToken<OrderDisposeEntities>(){}.getType());
    }

    /**
     * 获取订单待收件状态数据
     * @param data
     * @return
     */
    public static OrderTakeOrderEntities getOrderTakeOrderEntities(String data){
        return gson.fromJson(data,new TypeToken<OrderTakeOrderEntities>(){}.getType());
    }


    /**
     * 获取订单待清洗状态数据
     * @param data
     * @return
     */
    public static OrderCleanEntities getOrderCleanEntities(String data){
        return gson.fromJson(data,new TypeToken<OrderCleanEntities>(){}.getType());
    }

    /**
     * 获取订单清洗中状态数据
     * @param data
     * @return
     */
    public static OrderCleaningEntities getOrderCleaningEntities(String data){
        return gson.fromJson(data,new TypeToken<OrderCleaningEntities>(){}.getType());
    }

    /**
     * 获取订单待送达状态数据
     * @param data
     * @return
     */
    public static OrderSendEntities getOrderSendEntities(String data){
        return gson.fromJson(data,new TypeToken<OrderSendEntities>(){}.getType());
    }

    /**
     * 取消订单
     * @param data
     * @return
     */
    public static CancelOrderEntities getCancelOrderEntities(String data){
        return gson.fromJson(data,new TypeToken<CancelOrderEntities>(){}.getType());

    }

    /**
     * 添加商品展示
     * @param data
     * @return
     */
    public static List<AddItemShowEntities> getAddItemShowEntities(String data){
        List<AddItemShowEntities> addItemShowEntities = null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String data1 = jsonObject.optString("data");
            addItemShowEntities = gson.fromJson(data1,new TypeToken<List<AddItemShowEntities>>(){}.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return addItemShowEntities;
    }

    /**
     * 编辑商品展示
     * @param data
     * @return
     */
    public static List<EditItemShowEntities> getEditItemShowEntities(String data){
        List<EditItemShowEntities> editItemShowEntities = null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String data1 = jsonObject.optString("data");
            editItemShowEntities = gson.fromJson(data1,new TypeToken<List<EditItemShowEntities>>(){}.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return editItemShowEntities;
    }

    /**
     * 工艺加价数据展示
     * @param data
     * @return
     */
    public static CraftworkAddPriceEntities getCraftworkAddPriceEntities(String data){
//        CraftworkAddPriceEntities craftworkAddPriceEntities = null;
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            String data1 = jsonObject.optString("data");
//            craftworkAddPriceEntities = gson.fromJson(data1,new TypeToken<CraftworkAddPriceEntities>(){}.getType());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return craftworkAddPriceEntities;
        return  gson.fromJson(data,new TypeToken<CraftworkAddPriceEntities>(){}.getType());
    }

    /**
     * 获取衣物检查
     * @param data
     * @return
     */
    public static CheckClothesEntities getCheckClothesEntities(String data){
        //        List<CheckClothesEntities> clothesEntities = null;
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            String data1 = jsonObject.optString("data");
//            clothesEntities = gson.fromJson(data1,new TypeToken<List<CheckClothesEntities>>(){}.getType());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return clothesEntities;
        return gson.fromJson(data,new TypeToken<CheckClothesEntities>(){}.getType());
    }

    /**
     * 获取验证码
     * @param data
     * @return
     */
    public static VerifyCodeEntity getVerifyCodeEntity(String data){
        return gson.fromJson(data,new TypeToken<VerifyCodeEntity>(){}.getType());

    }

    /**
     * 订单详情数据
     * @param data
     * @return
     */
    public static OrderDetailEntity getOrderDetailEntity(String data){
//        OrderDetailEntity orderDetailEntity = null;
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            String data1 = jsonObject.optString("data");
//            orderDetailEntity = gson.fromJson(data1,new TypeToken<OrderDetailEntity>(){}.getType());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return orderDetailEntity;
        return gson.fromJson(data,new TypeToken<OrderDetailEntity>(){}.getType());
    }

    /**
     * 颜色设置成功
     * @param data
     * @return
     */
    public static ColorSettingSuccessEntities getColorSettingSuccessEntities(String data){
        return gson.fromJson(data,new TypeToken<ColorSettingSuccessEntities>(){}.getType());
    }

    public static QuestionSettingSuccessEntities getQuestionSettingSuccessEntities(String data){
        return gson.fromJson(data,new TypeToken<QuestionSettingSuccessEntities>(){}.getType());
    }

    /**
     * 版本升级
     * @param data
     * @return
     */
    public static UpdataEntity getUpdataEntity(String data){
        return gson.fromJson(data,new TypeToken<UpdataEntity>(){}.getType());
    }

    /**
     * 线下会员卡信息
     * @param data
     * @return
     */
    public static OfflineCustomInfoEntity getOfflineCustomInfoEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineCustomInfoEntity>(){}.getType());
    }

    /**
     * 生成订单
     * @param data
     * @return
     */
    public static BuildOrderEntity getBuildOrderEntity(String data){
        return gson.fromJson(data,new TypeToken<BuildOrderEntity>(){}.getType());
    }

    /**
     * 取衣接口
     * @param data
     * @return
     */
    public static TakeClothesEntity getTakeClothesEntity(String data){
        return gson.fromJson(data,new TypeToken<TakeClothesEntity>(){}.getType());
    }

    /**
     * 线下会员余额
     * @param data
     * @return
     */
    public static OfflineMemberBalanceEntity getOfflineMemberBalanceEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineMemberBalanceEntity>(){}.getType());
    }

    /**
     * 线下会员详情
     * @param data
     * @return
     */
    public static OfflineMemberDetailEntity getOfflineMemberDetailEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineMemberDetailEntity>(){}.getType());
    }

    /**
     * 业务 未付款统计
     * @param data
     * @return
     */
    public static StatisticsNoPayEntity getStatisticsNoPayEntity(String data){
        return gson.fromJson(data,new TypeToken<StatisticsNoPayEntity>(){}.getType());
    }

    /**
     * 业务 收银统计
     * @param data
     * @return
     */
    public static StatisticsIncomeEntity getStatisticsIncomeEntity(String data){
        return gson.fromJson(data,new TypeToken<StatisticsIncomeEntity>(){}.getType());
    }

    /**
     * 线下订单详情
     * @param data
     * @return
     */
    public static OfflineOrderDetailEntity getOfflineOrderDetailEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineOrderDetailEntity>(){}.getType());
    }

    /**
     * 线下首页
     * @param data
     * @return
     */
    public static OfflineHomeEntity getOfflineHomeEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineHomeEntity>(){}.getType());
    }

    /**
     * 获取会员卡号
     * @param data
     * @return
     */
    public static OfflineMemberNumEntity getOfflineMemberNumEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineMemberNumEntity>(){}.getType());
    }

    /**
     * 订单支付
     * @param data
     * @return
     */
    public static OfflineOrderPayEntity getOfflineOrderPayEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineOrderPayEntity>(){}.getType());
    }


    /**
     * 上挂列表
     * @param data
     * @return
     */
    public static OfflineHangOnEntity getOfflineHangOnEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineHangOnEntity>(){}.getType());
    }

    /**
     * 新增会员
     * @param data
     * @return
     */
    public static OfflineAddVisitorEntity getOfflineAddVisitorEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineAddVisitorEntity>(){}.getType());
    }

    /**
     * 会员消费报表
     * @param data
     * @return
     */
    public static OfflineMemberConsumeListEntity getOfflineMemberConsumeEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineMemberConsumeListEntity>(){}.getType());
    }

    /**
     * 会员充值报表
     * @param data
     * @return
     */
    public static OfflineMemberRechargeListEntity getOfflineMemberRechargeListEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineMemberRechargeListEntity>(){}.getType());
    }

    /**
     * 会员充值
     * @param data
     * @return
     */
    public static OfflineMemberRechargeEntity getOfflineMemberRechargeEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineMemberRechargeEntity>(){}.getType());
    }

    /**
     * 平台卡支付成功
     * @param data
     * @return
     */
    public static PlatformPaySuccessEntity getPlatformPaySuccessEntity(String data){
        return gson.fromJson(data,new TypeToken<PlatformPaySuccessEntity>(){}.getType());
    }

    /**
     * 添加照片成功
     * @param data
     * @return
     */
    public static UploadAddPhotoEntity getUploadAddPhotoEntity(String data){
        return gson.fromJson(data,new TypeToken<UploadAddPhotoEntity>(){}.getType());
    }

    /**
     * 订单打印
     * @param data
     * @return
     */
    public static OrderPrintEntity getOrderPrintEntity(String data){
        return gson.fromJson(data,new TypeToken<OrderPrintEntity>(){}.getType());
    }

    /**
     * 充值成功
     * @param data
     * @return
     */
    public static RechargeSuccessEntity getRechargeSuccessEntity(String data){
        return gson.fromJson(data,new TypeToken<RechargeSuccessEntity>(){}.getType());
    }

    /**
     * 会员充值打印
     * @param data
     * @return
     */
    public static PrintRechargeEntity getPrintRechargeEntity(String data){
        return gson.fromJson(data,new TypeToken<PrintRechargeEntity>(){}.getType());
    }

    /**
     * 购买平台卡
     * @param data
     * @return
     */
    public static BuyPlateformCardEntity getBuyPlateformCardEntity(String data){
        return gson.fromJson(data,new TypeToken<BuyPlateformCardEntity>(){}.getType());
    }

    /**
     * 经营分析
     * @param data
     * @return
     */
    public static OperateAnalysisEntities getOperateAnalysisEntities(String data){
        return gson.fromJson(data,new TypeToken<OperateAnalysisEntities>(){}.getType());
    }

    /**
     * 获取图片验证码
     * @param data
     * @return
     */
    public static PhotoVerifyCodeEntity getPhotoVerifyCodeEntity(String data){
        return gson.fromJson(data,new TypeToken<PhotoVerifyCodeEntity>(){}.getType());
    }

    /**
     * 商家编辑项目获取项目信息
     * @param data
     * @return
     */
    public static EditCommodityEntity getEditCommodityEntity(String data){
        return gson.fromJson(data,new TypeToken<EditCommodityEntity>(){}.getType());
    }

    /**
     * 卡券中心卡券查询
     * @param data
     * @return
     */
    public static CashCouponCenterEntity getCashCouponCenterEntity(String data){
        return gson.fromJson(data,new TypeToken<CashCouponCenterEntity>(){}.getType());
    }
    /**
     * 制作代金券
     * @param data
     * @return
     */
    public static CashCouponEntity getCashCouponEntity(String data){
        return gson.fromJson(data,new TypeToken<CashCouponEntity>(){}.getType());
    }

    /**
     * 商家修改会员卡获取会员卡信息
     * @param data
     * @return
     */
    public static MerchantCardInfoEntity getMerchantCardInfoEntity(String data){
        return gson.fromJson(data,new TypeToken<MerchantCardInfoEntity>(){}.getType());
    }

    /**
     * 订单查询
     * @param data
     * @return
     */
    public static OrderSearchEntity getOrderSearchEntity(String data){
        return gson.fromJson(data,new TypeToken<OrderSearchEntity>(){}.getType());
    }

    /**
     * 获取修改取衣时间
     * @param data
     * @return
     */
    public static List<TakeTimeEntity> getTakeTimeEntity(String data){
        return gson.fromJson(data,new TypeToken<List<TakeTimeEntity>>(){}.getType());
    }

    /**
     * 修改会员信息
     * @param data
     * @return
     */
    public static OfflineChangeMemberInfoEntity getOfflineChangeMemberInfoEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineChangeMemberInfoEntity>(){}.getType());
    }

    /**
     * 新增会员 的商户卡列表
     * @param data
     * @return
     */
    public static MerchantCardListEntities getMerchantCardListEntities(String data){
        return gson.fromJson(data,new TypeToken<MerchantCardListEntities>(){}.getType());
    }

    /**
     * 添加项目数据
     * @param data
     * @return
     */
    public static StoreDetailEntity getStoreDetailEntity(String data){
        return gson.fromJson(data,new TypeToken<StoreDetailEntity>(){}.getType());
    }

    /**
     * 线下添加项目确认
     * @param data
     * @return
     */
    public static AddProjectOfflineConfirmEntity getAddProjectOfflineConfirmEntity(String data){
        return gson.fromJson(data,new TypeToken<AddProjectOfflineConfirmEntity>(){}.getType());
    }

    /**
     * 获取代金券信息
     * @param data
     * @return
     */
    public static CashConponEntity getCashConponEntity(String data){
        return gson.fromJson(data,new TypeToken<CashConponEntity>(){}.getType());
    }

    /**
     * 消息详情
     * @param data
     * @return
     */
    public static MessageDetailEntity getMessageDetailEntity(String data){
        return gson.fromJson(data,new TypeToken<MessageDetailEntity>(){}.getType());
    }

    /**
     * 员工添加权限
     * @param data
     * @return
     */
    public static AddEmployeeJurisdictionEntity getAddEmployeeJurisdictionEntity(String data){
        return gson.fromJson(data,new TypeToken<AddEmployeeJurisdictionEntity>(){}.getType());
    }

    /**
     * 编辑员工信息
     * @param data
     * @return
     */
    public static EditEmployeeInfoEntity getEditEmployeeInfoEntity(String data){
        return gson.fromJson(data,new TypeToken<EditEmployeeInfoEntity>(){}.getType());
    }

    /**
     * 获取线下收银权限
     * @param data
     * @return
     */
    public static OfflineAuthorityEntity getOfflineAuthorityEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineAuthorityEntity>(){}.getType());
    }

    /**
     * 合作店铺列表
     * @param data
     * @return
     */
    public static CooperativeStoreEntities getCooperativeStoreEntities(String data){
        return gson.fromJson(data,new TypeToken<CooperativeStoreEntities>(){}.getType());
    }

    /**
     * 入厂列表
     * @param data
     * @return
     */
    public static IntoFactoryEntities getIntoFactoryEntities(String data){
        return gson.fromJson(data,new TypeToken<IntoFactoryEntities>(){}.getType());
    }

    /**
     * 获取烘干、熨烫、质检数据
     * @param data
     * @return
     */
    public static WashingEntity getWashingEntity(String data){
        return gson.fromJson(data,new TypeToken<WashingEntity>(){}.getType());
    }

    /**
     * 做清洗操作
     * @param data
     * @return
     */
    public static AddExecEntity getAddExecEntity(String data){
        return gson.fromJson(data,new TypeToken<AddExecEntity>(){}.getType());
    }

    /**
     * 返流列表
     * @param data
     * @return
     */
    public static OfflineRefluxEntity getOfflineRefluxEntity(String data){
        return gson.fromJson(data,new TypeToken<OfflineRefluxEntity>(){}.getType());
    }

    /**
     * 合作店铺 操作
     * @param data
     * @return
     */
    public static CooperativeStoreOperateEntities getCooperativeStoreOperateEntities(String data){
        return gson.fromJson(data,new TypeToken<CooperativeStoreOperateEntities>(){}.getType());
    }

    /**
     * 出厂列表
     * @param data
     * @return
     */
    public static LeaveFactoryEntities getLeaveFactoryEntities(String data){
        return gson.fromJson(data,new TypeToken<LeaveFactoryEntities>(){}.getType());
    }

    /**
     * 清洗列表
     * @param data
     * @return
     */
    public static WashEntity getWashEntity(String data){
        return gson.fromJson(data,new TypeToken<WashEntity>(){}.getType());
    }

    /**
     * 返流
     * @param data
     * @return
     */
    public static RefluxEditEntity getRefluxEditEntity(String data){
        return gson.fromJson(data,new TypeToken<RefluxEditEntity>(){}.getType());
    }
}
