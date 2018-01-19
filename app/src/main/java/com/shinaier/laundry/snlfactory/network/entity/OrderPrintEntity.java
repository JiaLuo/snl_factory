package com.shinaier.laundry.snlfactory.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/19.
 */

public class OrderPrintEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private OrderPrintResult result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OrderPrintResult getResult() {
        return result;
    }

    public void setResult(OrderPrintResult result) {
        this.result = result;
    }

    public static class OrderPrintResult implements Parcelable{
        @SerializedName("ordersn")
        private String orderSn;
        @SerializedName("umobile")
        private String uMobile;
        @SerializedName("amount")
        private String amount;
        @SerializedName("pay_amount")
        private String payAmount;
        @SerializedName("reduce_price")
        private String reducePrice;
        @SerializedName("pay_state")
        private String payState;
        @SerializedName("pay_gateway")
        private String payGateway;
        @SerializedName("maddress")
        private String mAddress;
        @SerializedName("phone_number")
        private String phoneNumber;
        @SerializedName("mid")
        private String mId;
        @SerializedName("append")
        private String append;
        @SerializedName("total_amount")
        private String totalAmount;
        @SerializedName("cbalance")
        private String cBalance;
        @SerializedName("items")
        private List<OrderPrintItems> items;
        @SerializedName("count")
        private String count;
        @SerializedName("employee")
        private String employee;
        @SerializedName("qrcode")
        private String qrcode;
        @SerializedName("mname")
        private String mName;

        protected OrderPrintResult(Parcel in) {
            orderSn = in.readString();
            uMobile = in.readString();
            amount = in.readString();
            payAmount = in.readString();
            reducePrice = in.readString();
            payState = in.readString();
            payGateway = in.readString();
            mAddress = in.readString();
            phoneNumber = in.readString();
            mId = in.readString();
            append = in.readString();
            totalAmount = in.readString();
            cBalance = in.readString();
            items = in.createTypedArrayList(OrderPrintItems.CREATOR);
            count = in.readString();
            employee = in.readString();
            qrcode = in.readString();
            mName = in.readString();
        }

        public static final Creator<OrderPrintResult> CREATOR = new Creator<OrderPrintResult>() {
            @Override
            public OrderPrintResult createFromParcel(Parcel in) {
                return new OrderPrintResult(in);
            }

            @Override
            public OrderPrintResult[] newArray(int size) {
                return new OrderPrintResult[size];
            }
        };

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getuMobile() {
            return uMobile;
        }

        public void setuMobile(String uMobile) {
            this.uMobile = uMobile;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getReducePrice() {
            return reducePrice;
        }

        public void setReducePrice(String reducePrice) {
            this.reducePrice = reducePrice;
        }

        public String getPayState() {
            return payState;
        }

        public void setPayState(String payState) {
            this.payState = payState;
        }

        public String getPayGateway() {
            return payGateway;
        }

        public void setPayGateway(String payGateway) {
            this.payGateway = payGateway;
        }

        public String getmAddress() {
            return mAddress;
        }

        public void setmAddress(String mAddress) {
            this.mAddress = mAddress;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getmId() {
            return mId;
        }

        public void setmId(String mId) {
            this.mId = mId;
        }

        public String getAppend() {
            return append;
        }

        public void setAppend(String append) {
            this.append = append;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getcBalance() {
            return cBalance;
        }

        public void setcBalance(String cBalance) {
            this.cBalance = cBalance;
        }

        public List<OrderPrintItems> getItems() {
            return items;
        }

        public void setItems(List<OrderPrintItems> items) {
            this.items = items;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getEmployee() {
            return employee;
        }

        public void setEmployee(String employee) {
            this.employee = employee;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(orderSn);
            dest.writeString(uMobile);
            dest.writeString(amount);
            dest.writeString(payAmount);
            dest.writeString(reducePrice);
            dest.writeString(payState);
            dest.writeString(payGateway);
            dest.writeString(mAddress);
            dest.writeString(phoneNumber);
            dest.writeString(mId);
            dest.writeString(append);
            dest.writeString(totalAmount);
            dest.writeString(cBalance);
            dest.writeTypedList(items);
            dest.writeString(count);
            dest.writeString(employee);
            dest.writeString(qrcode);
            dest.writeString(mName);
        }


        public static class OrderPrintItems implements Parcelable{
            @SerializedName("item_name")
            private String itemName;
            @SerializedName("color")
            private String color;
            @SerializedName("problem")
            private String problem;
            @SerializedName("item_real_price")
            private String itemRealPrice;

            protected OrderPrintItems(Parcel in) {
                itemName = in.readString();
                color = in.readString();
                problem = in.readString();
                itemRealPrice = in.readString();
            }

            public static final Creator<OrderPrintItems> CREATOR = new Creator<OrderPrintItems>() {
                @Override
                public OrderPrintItems createFromParcel(Parcel in) {
                    return new OrderPrintItems(in);
                }

                @Override
                public OrderPrintItems[] newArray(int size) {
                    return new OrderPrintItems[size];
                }
            };

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getProblem() {
                return problem;
            }

            public void setProblem(String problem) {
                this.problem = problem;
            }

            public String getItemRealPrice() {
                return itemRealPrice;
            }

            public void setItemRealPrice(String itemRealPrice) {
                this.itemRealPrice = itemRealPrice;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(itemName);
                dest.writeString(color);
                dest.writeString(problem);
                dest.writeString(itemRealPrice);
            }
        }

    }
}
