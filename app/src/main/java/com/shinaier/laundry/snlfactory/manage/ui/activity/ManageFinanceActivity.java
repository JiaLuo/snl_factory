package com.shinaier.laundry.snlfactory.manage.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.viewinject.annotation.ViewInject;
import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.base.activity.ToolBarActivity;
import com.shinaier.laundry.snlfactory.main.UserCenter;
import com.shinaier.laundry.snlfactory.manage.adapter.ManageFinanceAdapter;
import com.shinaier.laundry.snlfactory.manage.ui.fragment.ManageFinanceFragment;
import com.shinaier.laundry.snlfactory.network.Constants;
import com.shinaier.laundry.snlfactory.network.entity.ManageFinanceEntities;
import com.shinaier.laundry.snlfactory.network.parser.Parsers;
import com.shinaier.laundry.snlfactory.util.CommonTools;
import com.shinaier.laundry.snlfactory.util.ViewInjectUtils;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;



/**
 * 财务对账
 * Created by 张家洛 on 2017/2/10.
 */

public class ManageFinanceActivity extends ToolBarActivity {
    private static final int REQUEST_CODE_BASE = 0x5;
    public static final int INCOME = 1;
    public static final int WITHDRAW = 3;

    @ViewInject(R.id.account_balance)
    private TextView accountBalance;
    @ViewInject(R.id.store_finance_detail)
    private TextView storeFinanceDetail;
    @ViewInject(R.id.finance_manage_category)
    private RadioGroup financeManageCategory;
    @ViewInject(R.id.finance_manage_all)
    private RadioButton financeManageAll;
    @ViewInject(R.id.finance_manage_income)
    private RadioButton financeManageIncome;
    @ViewInject(R.id.finance_manage_withdraw)
    private RadioButton financeManageWithdraw;
    @ViewInject(R.id.finance_manage_withdrawals)
    private ViewPager financeManageWithdrawals;
    @ViewInject(R.id.left_button)
    private ImageView leftButton;

    private List<ManageFinanceFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_finance_act);
        ViewInjectUtils.inject(this);
        loadData();
        initView();
    }

    private void loadData() {
        IdentityHashMap<String,String> params = new IdentityHashMap<>();
        params.put("token", UserCenter.getToken(this));
        requestHttpData(Constants.Urls.URL_POST_MANAGE_FINANCE_BASE,REQUEST_CODE_BASE, FProtocol.HttpMethod.POST,params);
    }


    private void initView() {
        setCenterTitle("财务对账");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ManageFinanceFragment manageFinanceFragment1 = new ManageFinanceFragment();
        manageFinanceFragment1.setArgs(this);
        ManageFinanceFragment manageFinanceFragment2 = new ManageFinanceFragment();
        manageFinanceFragment2.setArgs(this,INCOME);
        ManageFinanceFragment manageFinanceFragment3 = new ManageFinanceFragment();
        manageFinanceFragment3.setArgs(this,WITHDRAW);
        fragments.add(manageFinanceFragment1);
        fragments.add(manageFinanceFragment2);
        fragments.add(manageFinanceFragment3);
        ManageFinanceAdapter manageFinanceAdapter = new ManageFinanceAdapter(getSupportFragmentManager(),fragments);
        financeManageWithdrawals.setAdapter(manageFinanceAdapter);
        financeManageCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    int currentItem = financeManageWithdrawals.getCurrentItem();
                    switch (checkedId){
                        case R.id.finance_manage_all:
                            if(currentItem != 0){
                                financeManageWithdrawals.setCurrentItem(0);
                                changeTextColor(0);
                            }
                            break;
                        case R.id.finance_manage_income:
                            if(currentItem != 1){
                                financeManageWithdrawals.setCurrentItem(1);
                                changeTextColor(1);
                            }
                            break;
                        case R.id.finance_manage_withdraw:
                            if(currentItem != 2){
                                financeManageWithdrawals.setCurrentItem(2);
                                changeTextColor(2);
                            }
                            break;
                    }
                }
            });

            financeManageWithdrawals.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    switch (position){
                        case 0:
                            financeManageCategory.check(R.id.finance_manage_all);
                            break;
                        case 1:
                            financeManageCategory.check(R.id.finance_manage_income);
                            break;
                        case 2:
                            financeManageCategory.check(R.id.finance_manage_withdraw);
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode){
            case REQUEST_CODE_BASE:
                if(data != null){
                    ManageFinanceEntities manageFinanceEntities = Parsers.getManageFinanceEntities(data);
                    accountBalance.setText("￥" + manageFinanceEntities.getRemainder());
                    String fromBank = manageFinanceEntities.getFromBank();
                    String cardNumber = manageFinanceEntities.getCardNumber();
                    String newStrings = fromBank + cardNumber;
//                    CharSequence charSequence = Html.fromHtml(manageFinanceEntities.getNoticeInfo());
//                    storeFinanceDetail.setText(charSequence);
                    String info = "账户余额结算周期为T+7，平台将通过银行打款结算至"  + newStrings +"账户，每个账期内余额结款最低1000元起，不满1000元将累计至下一个账期结算。";
//                    CommonTools.StringInterceptionChangeRed(tvTwoMemberRechargePrompt,twoLine,"≥" + diamondPrice + "","" + diamondDiscount + "折");
                    CommonTools.StringInterceptionChangeGreen(this,storeFinanceDetail,info, newStrings + "","");
                }
                break;
        }
    }

    private void changeTextColor(int position) {
        financeManageAll.setTextColor(getResources().getColor(R.color.black_text));
        financeManageIncome.setTextColor(getResources().getColor(R.color.black_text));
        financeManageWithdraw.setTextColor(getResources().getColor(R.color.black_text));
        switch (position) {
            case 0:
                financeManageAll.setTextColor(getResources().getColor(R.color.base_color));
                break;
            case 1:
                financeManageIncome.setTextColor(getResources().getColor(R.color.base_color));
                break;
            case 2:
                financeManageWithdraw.setTextColor(getResources().getColor(R.color.base_color));
                break;
        }
    }
}
