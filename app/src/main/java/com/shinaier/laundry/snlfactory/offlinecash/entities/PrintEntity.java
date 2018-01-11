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

    public class PayOrderPrintEntity implements Serializable {
        private PayOrderPrintInfo payOrderPrintInfo;
        private List<PayOrderPrintItems> payOrderPrintItems;

        public PayOrderPrintInfo getPayOrderPrintInfo() {
            return payOrderPrintInfo;
        }

        public void setPayOrderPrintInfo(PayOrderPrintInfo payOrderPrintInfo) {
            this.payOrderPrintInfo = payOrderPrintInfo;
        }

        public List<PayOrderPrintItems> getPayOrderPrintItems() {
            return payOrderPrintItems;
        }

        public void setPayOrderPrintItems(List<PayOrderPrintItems> payOrderPrintItems) {
            this.payOrderPrintItems = payOrderPrintItems;
        }

        public class PayOrderPrintItems implements Serializable {
            private String itemNote;
            private String color;
            private String price;
            private String putNumber;
            private String name;
            private String noteText;
            private String colorText;
            private String assess;
            private String assessText;

            public String getNoteText() {
                return noteText;
            }

            public void setNoteText(String noteText) {
                this.noteText = noteText;
            }

            public String getColorText() {
                return colorText;
            }

            public void setColorText(String colorText) {
                this.colorText = colorText;
            }

            public String getAssess() {
                return assess;
            }

            public void setAssess(String assess) {
                this.assess = assess;
            }

            public String getAssessText() {
                return assessText;
            }

            public void setAssessText(String assessText) {
                this.assessText = assessText;
            }

            public String getItemNote() {
                return itemNote;
            }

            public void setItemNote(String itemNote) {
                this.itemNote = itemNote;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPutNumber() {
                return putNumber;
            }

            public void setPutNumber(String putNumber) {
                this.putNumber = putNumber;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public class PayOrderPrintInfo implements Serializable {
            private String ordersn;
            private String mobile;
            private String pieceNum;
            private String hedging;
            private String payAmount;
            private String reducePrice;
            private String payChannel;
            private String userId;
            private String address;
            private String phone;
            private String mid;
            private String clerkName;
            private String payState;
            private String amount;
            private String cardNumber;
            private String cardBalance;
            private String qrcode;
            private String mName;

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

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getPieceNum() {
                return pieceNum;
            }

            public void setPieceNum(String pieceNum) {
                this.pieceNum = pieceNum;
            }

            public String getHedging() {
                return hedging;
            }

            public void setHedging(String hedging) {
                this.hedging = hedging;
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

            public String getPayChannel() {
                return payChannel;
            }

            public void setPayChannel(String payChannel) {
                this.payChannel = payChannel;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getClerkName() {
                return clerkName;
            }

            public void setClerkName(String clerkName) {
                this.clerkName = clerkName;
            }

            public String getPayState() {
                return payState;
            }

            public void setPayState(String payState) {
                this.payState = payState;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCardNumber() {
                return cardNumber;
            }

            public void setCardNumber(String cardNumber) {
                this.cardNumber = cardNumber;
            }

            public String getCardBalance() {
                return cardBalance;
            }

            public void setCardBalance(String cardBalance) {
                this.cardBalance = cardBalance;
            }

            public String getQrcode() {
                return qrcode;
            }

            public void setQrcode(String qrcode) {
                this.qrcode = qrcode;
            }
        }

    }

    public class RechargePrintEntity implements Serializable {
        private String mobile;
        private String rechargeAmount;
        private String give;
        private String ucode;
        private String balance;
        private String address;
        private String phone;
        private String mid;
        private String payType;
        private String clerkName;
        private String qrcodeUrl;
        private String orderNumber;
        private String ordersn;
        private String qrcode;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRechargeAmount() {
            return rechargeAmount;
        }

        public void setRechargeAmount(String rechargeAmount) {
            this.rechargeAmount = rechargeAmount;
        }

        public String getGive() {
            return give;
        }

        public void setGive(String give) {
            this.give = give;
        }

        public String getUcode() {
            return ucode;
        }

        public void setUcode(String ucode) {
            this.ucode = ucode;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getClerkName() {
            return clerkName;
        }

        public void setClerkName(String clerkName) {
            this.clerkName = clerkName;
        }

        public String getQrcodeUrl() {
            return qrcodeUrl;
        }

        public void setQrcodeUrl(String qrcodeUrl) {
            this.qrcodeUrl = qrcodeUrl;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }
    }


}
