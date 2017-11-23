package com.shinaier.laundry.snlfactory.network;

/**
 * Created by 张家洛 on 2017/2/8.
 */

public class Constants {
    public static class Urls{
        //测试环境域名
        public static String URL_BASE_DOMAIN = "http://xiyi.wzj.dev.shuxier.com";
        //正式环境域名
//        public static String URL_BASE_DOMAIN = "http://clean.shuxier.com/";

        //登录
        public static final String URL_POST_LOGIN = URL_BASE_DOMAIN + "/app.php/Home/Login/login";
        //管理
        public static final String URL_POST_MANAGE = URL_BASE_DOMAIN + "/app.php/Home/Merchant/index";
        //财务对账基础信息
        public static final String URL_POST_MANAGE_FINANCE_BASE = URL_BASE_DOMAIN + "/app.php/Home/Merchant/finance";
        //财务对账详细记录
        public static final String URL_POST_MANAGE_FINANCE_DETAIL = URL_BASE_DOMAIN + "/app.php/Home/Merchant/financeRecord";
        //获取商品管理展示
        public static final String URL_POST_MANAGE_COMMODITY_SHOW = URL_BASE_DOMAIN + "/app.php/Home/Merchant/goods";
        //添加商品展示
        public static final String URL_POST_ADD_COMMODITY_SHOW = URL_BASE_DOMAIN + "/app.php/Home/Merchant/add_goods";
        //邀请好友
        public static final String URL_POST_INVITE_FRIEND = URL_BASE_DOMAIN + "/app.php/Home/Merchant/invite";
        //评价列表展示
        public static final String URL_POST_EVALUATE_LIST_SHOW = URL_BASE_DOMAIN + "/app.php/Home/Merchant/evaluate";
        //消息通知列表
        public static final String URL_POST_MSG_NOTICE_LIST = URL_BASE_DOMAIN + "/app.php/Home/Merchant/message1";
        //员工管理列表
        public static final String URL_POST_MANAGE_EMPLOYEE = URL_BASE_DOMAIN + "/app.php/Home/Merchant/manager";
        //门店信息
        public static final String URL_POST_STORE_INFO = URL_BASE_DOMAIN + "/app.php/Home/Merchant/merchant_info";
        //返现记录
        public static final String URL_POST_CASH_BACK = URL_BASE_DOMAIN + "/app.php/Home/Merchant/fanxian";
        //设置页面
        public static final String URL_POST_SETTINGS = URL_BASE_DOMAIN + "/app.php/Home/Merchant/set";
        //意见反馈
        public static final String URL_POST_FEEDBACK = URL_BASE_DOMAIN + "/app.php/Home/Merchant/feedback";
        //修改密码
        public static final String URL_POST_REVISE_PASSWORD = URL_BASE_DOMAIN + "/app.php/Home/Merchant/password";
        //订单查询列表
        public static final String URL_POST_ORDER_INQUIRY = URL_BASE_DOMAIN + "/app.php/Home/Merchant/ordersearch";
        //订单查询结果
        public static final String URL_POST_INQUIRY_RESULT = URL_BASE_DOMAIN + "/app.php/Home/Merchant/orderConditionSearch";
        //订单处理列表显示
        public static final String URL_POST_ORDER_MANAGE = URL_BASE_DOMAIN + "/app.php/Home/Merchant/orderHandle";
        //取消订单
        public static final String URL_POST_CANCEL_ORDER = URL_BASE_DOMAIN + "/app.php/Home/Merchant/cause";
        //添加项目展示
        public static final String URL_POST_ADD_ITEM_SHOW = URL_BASE_DOMAIN + "/app.php/Home/Merchant/item_add";
        //编辑项目展示
        public static final String URL_POST_EDIT_ITEM_SHOW = URL_BASE_DOMAIN + "/app.php/Home/Merchant/mod_item_add";
        //工艺加价展示
        public static final String URL_POST_CRAFTWORK_PLUS_PRICE = URL_BASE_DOMAIN + "/app.php/Home/Merchant/edit_price";
        //检查衣物
        public static final String URL_POST_CHECK_CLOTHES = URL_BASE_DOMAIN + "/app.php/Home/Merchant/jiancha";
        //获取验证码
        public static final String URL_POST_GET_VERIFY_CODE = URL_BASE_DOMAIN + "/app.php/Home/Merchant/add_manager";
        //编辑员工信息
        public static final String URL_POST_EDIT_EMPLOY_INFO = URL_BASE_DOMAIN + "/app.php/Home/Merchant/mod_manager";
        //删除员工信息
        public static final String URL_POST_DELETE_EMPLOY_INFO = URL_BASE_DOMAIN + "/app.php/Home/Merchant/del_manager";
        //评价回复
        public static final String URL_POST_EVALUATE_REPLY = URL_BASE_DOMAIN + "/app.php/Home/Merchant/evaluate";
        //编辑商品信息
        public static final String URL_POST_EDIT_COMMODITY_INFO = URL_BASE_DOMAIN + "/app.php/Home/Merchant/upd_goods";
        //删除商品信息
        public static final String URL_POST_DEL_COMMODITY_INFO = URL_BASE_DOMAIN + "/app.php/Home/Merchant/del_goods";
        //修改工艺加价
        public static final String URL_POST_REVISE_CRAFTWORK_PRICE = URL_BASE_DOMAIN + "/app.php/Home/Merchant/mod_gongyi";
        //确认收件
        public static final String URL_POST_CONFRIM_CONSIGNEE = URL_BASE_DOMAIN + "/app.php/Home/Merchant/shoujian";
        //订单详情
        public static final String URL_POST_ORDER_DETAIL = URL_BASE_DOMAIN + "/app.php/Home/Merchant/dingdan_detail";
        //颜色设置
        public static final String URL_POST_COLOR_SETTING = URL_BASE_DOMAIN + "/app.php/Home/Merchant/color";
        //问题设置
        public static final String URL_POST_QUESTION_SETTING = URL_BASE_DOMAIN + "/app.php/Home/Merchant/note";
        //送件完成
        public static final String URL_POST_SEND_ORDER = URL_BASE_DOMAIN + "/app.php/Home/Merchant/wancheng";
        //清洗完成
        public static final String URL_POST_CLEANED_ORDER = URL_BASE_DOMAIN + "/app.php/Home/Merchant/qingxiwancheng";
        //上传图片
        public static final String URL_POST_CLOTHES_PHOTO = URL_BASE_DOMAIN + "/app.php/Home/Merchant/imagesUpload";
        //检查完成
        public static final String URL_POST_CHECKED_CLOTHES = URL_BASE_DOMAIN + "/app.php/Home/Merchant/jiancha_end";
        //商家营业状态
        public static final String URL_POST_STORE_STATUS = URL_BASE_DOMAIN + "/app.php/Home/Merchant/state_change";
        //删除图片
        public static final String URL_POST_DELETE_CLOTHES_PHOTO = URL_BASE_DOMAIN + "/app.php/Home/Merchant/delImages";
        //忘记密码获取验证码
        public static final String URL_POST_GAIN_VERIFY_CODE = URL_BASE_DOMAIN + "/app.php/Home/Login/sendCode";
        //忘记密码确认
        public static final String URL_POST_FORGET_PASSWORD_CONFIRM = URL_BASE_DOMAIN + "/app.php/Home/Login/forgot";
        //修改保值金额
        public static final String URL_POST_REVISE_MAINTAIN_VALUE = URL_BASE_DOMAIN + "/app.php/Home/Merchant/hedging";
        //升级
        public static final String URL_POST_UPDATE_VERSION = URL_BASE_DOMAIN + "/app.php/Home/Merchant/app_android";
        //线下客户信息
        public static final String URL_POST_CUSTOM_INFO = URL_BASE_DOMAIN + "/app.php/Offline/Member/get_member";
        //生成订单
        public static final String URL_POST_BUILD_ORDER = URL_BASE_DOMAIN + "/app.php/Offline/Order/order";
        //取衣列表
        public static final String URL_POST_TAKE_CLOTHES_LIST = URL_BASE_DOMAIN + "/app.php/Offline/Order/order_search";
        //立即付款
        public static final String URL_POST_NOW_PAY = URL_BASE_DOMAIN + "/app.php/Offline/Order/take_pay";
        //单件取衣
        public static final String URL_POST_SINGLE_TAKE_CLOTHES = URL_BASE_DOMAIN + "/app.php/Offline/Order/take";
        //会员充值列表
        public static final String URL_POST_OFFLINE_MEMBER_LIST = URL_BASE_DOMAIN + "/app.php/Offline/Statistics/recharge";
        //线下会员余额
        public static final String URL_POST_OFFLINE_MEMBER_BALANCE = URL_BASE_DOMAIN + "/app.php/Offline/Statistics/balance";
        //线下会员详情
        public static final String URL_POST_OFFLINE_MEMBER_DETAIL = URL_BASE_DOMAIN + "/app.php/Offline/Member/member_detail";
        //业务 收银统计
        public static final String URL_POST_INCOME_STATISTICS = URL_BASE_DOMAIN + "/app.php/Offline/Statistics/finance";
        //业务 未付款统计
        public static final String URL_POST_NO_PAY_STATISTICS = URL_BASE_DOMAIN + "/app.php/Offline/Statistics/no_pay";
        //业务 未接单统计
        public static final String URL_POST_NO_DONE_STATISTICS = URL_BASE_DOMAIN + "/app.php/Offline/Statistics/no_done";
        //业务统计订单详情
        public static final String URL_POST_ORDER_DETAIL_STATISTICS = URL_BASE_DOMAIN + "/app.php/Offline/Order/order_detail";
        //线下首页
        public static final String URL_POST_OFFLINE_HOME = URL_BASE_DOMAIN + "/app.php/Offline/Merchant/index";
        //新增会员编号
        public static final String URL_POST_ADD_MEMBER_NUM = URL_BASE_DOMAIN + "/app.php/Offline/Merchant/ucode";
        //新增会员
        public static final String URL_POST_ADD_MEMBER = URL_BASE_DOMAIN + "/app.php/Offline/Member/add_member";
        //线下确认添加项目
        public static final String URL_POST_OFFLINE_EDIT_ITEM_SHOW = URL_BASE_DOMAIN + "/app.php/Offline/Merchant/item_add";
        //线下订单详情
        public static final String URL_POST_OFFLINE_ORDER_PAY_DETAIL = URL_BASE_DOMAIN + "/app.php/Offline/Order/order_pay";
        //送洗列表
        public static final String URL_POST_OFFLINE_SEND_LAUNDRY_LIST = URL_BASE_DOMAIN + "/app.php/Offline/Order/will_clean";
        //送洗完成
        public static final String URL_POST_OFFLINE_SEND_LAUNDRY = URL_BASE_DOMAIN + "/app.php/Offline/Order/cleaning";
        //上挂列表
        public static final String URL_POST_HANG_ON_LIST = URL_BASE_DOMAIN + "/app.php/Offline/Order/put_list";
        //上挂
        public static final String URL_POST_HANG_ON = URL_BASE_DOMAIN + "/app.php/Offline/Order/put_number";
        //专店会员卡充值
        public static final String URL_POST_OFFLINE_VIP_RECHARGE = URL_BASE_DOMAIN + "/app.php/Offline/Member/get_merchant_member";
        //购买商家会员卡
        public static final String URL_POST_BUY_MERCHANT_CARD = URL_BASE_DOMAIN + "/app.php/Offline/BuyMerchantCard/buy_card";
        //获取门店信息
        public static final String URL_POST_MERCHANT_INFO = URL_BASE_DOMAIN + "/app.php/Offline/Merchant/detail";
        //修改商家卡会员规则
        public static final String URL_POST_UPDATE_MERCHANT_RULE = URL_BASE_DOMAIN + "/app.php/Offline/Merchant/upd_card";
        //线下平台会员卡支付发送验证码
        public static final String URL_POST_SEND_CODE = URL_BASE_DOMAIN + "/app.php/Offline/Member/send_code";
        //线下订单支付
        public static final String URL_POST_OFFLINE_ORDER_PAY = URL_BASE_DOMAIN + "/app.php/Offline/Pay/pay";
        //订单打印
        public static final String URL_POST_ORDER_PRINT = URL_BASE_DOMAIN + "/app.php/Offline/PrintInfo/o_p";
        //会员充值打印
        public static final String URL_POST_RECHARGE_PRINT = URL_BASE_DOMAIN + "/app.php/Offline/PrintInfo/r_p";
        //购买平台会员卡时获取的数据
        public static final String URL_POST_GET_PLATFORM_CARD_ = URL_BASE_DOMAIN + "/app.php/Offline/Buycard/p_card";
        //购买平台会员卡
        public static final String URL_POST_BUY_PLATFORM_CARD = URL_BASE_DOMAIN + "/app.php/Offline/Buycard/buy";
        //经营分析
        public static final String URL_POST_OPERATE_ANALYSIS = URL_BASE_DOMAIN + "/app.php/Home/MerchantData/statistic";
    }
}
