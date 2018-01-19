package com.shinaier.laundry.snlfactory.offlinecash.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 张家洛 on 2017/8/23.
 */

public class PrintEntity implements Serializable {

    private RechargePrintEntity rechargePrintEntity;
    private PayOrderPrintEntity payOrderPrintEntity;

    public RechargePrintEntity getRechargePrintEntity() {
        return rechargePrintEntity;
    }

    public void setRechargePrintEntity(RechargePrintEntity rechargePrintEntity) {
        this.rechargePrintEntity = rechargePrintEntity;
    }

    public PayOrderPrintEntity getPayOrderPrintEntity() {
        return payOrderPrintEntity;
    }

    public void setPayOrderPrintEntity(PayOrderPrintEntity payOrderPrintEntity) {
        this.payOrderPrintEntity = payOrderPrintEntity;
    }

    public class PayOrderPrintEntity implements Serializable{
        private PayOrderPrintInfo payOrderPrintInfo;

        public PayOrderPrintInfo getPayOrderPrintInfo() {
            return payOrderPrintInfo;
        }

        public void setPayOrderPrintInfo(PayOrderPrintInfo payOrderPrintInfo) {
            this.payOrderPrintInfo = payOrderPrintInfo;
        }

        public class PayOrderPrintInfo implements Serializable{
            private String ordersn;
            private String umobile;
            private String amount;
            private String payAmount;
            private String reducePrice;
            private String payState;
            private String payGateway;
            private String mAddress;
            private String phoneNumber;
            private String mid;
            private String append;
            private String totalAmount;
            private String cBalance;
            private List<PayOrderPrintItems> itemses;
            private String count;
            private String employee;
            private String qrcode;
            private String mName;

            public class PayOrderPrintItems implements Serializable{
                private String itemName;
                private String color;
                private String problem;
                private String itemRealPrice;

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
            }

            public String getmName() {
                return mName;
            }

            public void setmName(String mName) {
                this.mName = mName;
            }

            public String getOrdersn() {
                return ordersn;
            }

            public void setOrdersn(String ordersn) {
                this.ordersn = ordersn;
            }

            public String getUmobile() {
                return umobile;
            }

            public void setUmobile(String umobile) {
                this.umobile = umobile;
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

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
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

            public List<PayOrderPrintItems> getItemses() {
                return itemses;
            }

            public void setItemses(List<PayOrderPrintItems> itemses) {
                this.itemses = itemses;
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
        }
    }

    public class RechargePrintEntity implements Serializable{
        private String tradeSn;
        private String umobile;
        private String amount;
        private String give;
        private String maddress;
        private String phone_number;
        private String mid;
        private String mname;
        private String employee;
        private String qrcode;
        private String gateway;
        private String cBalance;

        public String getcBalance() {
            return cBalance;
        }

        public void setcBalance(String cBalance) {
            this.cBalance = cBalance;
        }

        public String getTradeSn() {
            return tradeSn;
        }

        public void setTradeSn(String tradeSn) {
            this.tradeSn = tradeSn;
        }

        public String getUmobile() {
            return umobile;
        }

        public void setUmobile(String umobile) {
            this.umobile = umobile;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getGive() {
            return give;
        }

        public void setGive(String give) {
            this.give = give;
        }

        public String getMaddress() {
            return maddress;
        }

        public void setMaddress(String maddress) {
            this.maddress = maddress;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
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

        public String getGateway() {
            return gateway;
        }

        public void setGateway(String gateway) {
            this.gateway = gateway;
        }
    }

}
