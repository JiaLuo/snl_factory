package com.shinaier.laundry.snlfactory.util;

import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.github.promeg.pinyinhelper.Pinyin;
import com.shinaier.laundry.snlfactory.offlinecash.entities.PrintEntity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 蓝牙打印工具类
 */
public class PrintUtil {

    private OutputStreamWriter mWriter = null;
    private OutputStream mOutputStream = null;

    public final static int WIDTH_PIXEL = 1080;
    public final static int IMAGE_SIZE = 320;

    /**
     * 初始化Pos实例
     *
     * @param encoding 编码
     * @throws IOException
     */
    public PrintUtil(OutputStream outputStream, String encoding) throws IOException {
        mWriter = new OutputStreamWriter(outputStream, encoding);
        mOutputStream = outputStream;
        initPrinter();
    }

    public void print(byte[] bs) throws IOException {
        mOutputStream.write(bs);
    }

    public void printRawBytes(byte[] bytes) throws IOException {
        mOutputStream.write(bytes);
        mOutputStream.flush();
    }

    /**
     * 初始化打印机
     *
     * @throws IOException
     */
    public void initPrinter() throws IOException {
        mWriter.write(0x1B);
        mWriter.write(0x40);
        mWriter.flush();
    }

    /**
     * 打印换行
     *
     * @return length 需要打印的空行数
     * @throws IOException
     */
    public void printLine(int lineNum) throws IOException {
        for (int i = 0; i < lineNum; i++) {
            mWriter.write("\n");
        }
        mWriter.flush();
    }

    /**
     * 打印换行(只换一行)
     *
     * @throws IOException
     */
    public void printLine() throws IOException {
        printLine(1);
    }

    /**
     * 打印空白(一个Tab的位置，约4个汉字)
     *
     * @param length 需要打印空白的长度,
     * @throws IOException
     */
    public void printTabSpace(int length) throws IOException {
        for (int i = 0; i < length; i++) {
            mWriter.write("\t");
        }
        mWriter.flush();
    }

    /**
     * 绝对打印位置
     *
     * @return
     * @throws IOException
     */
    public byte[] setLocation(int offset) throws IOException {
        byte[] bs = new byte[4];
        bs[0] = 0x1B;
        bs[1] = 0x24;
        bs[2] = (byte) (offset % 256);
        bs[3] = (byte) (offset / 256);
        return bs;
    }

    public byte[] getGbk(String stText) throws IOException {
        byte[] returnText = stText.getBytes("GBK"); // 必须放在try内才可以
        return returnText;
    }

    private int getStringPixLength(String str) {
        int pixLength = 0;
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Pinyin.isChinese(c)) {
                pixLength += 24;
            } else {
                pixLength += 12;
            }
        }
        return pixLength;
    }

    public int getOffset(String str) {
        return WIDTH_PIXEL - getStringPixLength(str);
    }

    /**
     * 打印文字
     *
     * @param text
     * @throws IOException
     */
    public void printText(String text) throws IOException {
        mWriter.write(text);
        mWriter.flush();
    }

    /**
     * 对齐0:左对齐，1：居中，2：右对齐
     */
    public void printAlignment(int alignment) throws IOException {
        mWriter.write(0x1b);
        mWriter.write(0x61);
        mWriter.write(alignment);
    }

    public void printLargeText(String text) throws IOException {

        mWriter.write(0x1b);
        mWriter.write(0x21);
        mWriter.write(48);

        mWriter.write(text);

        mWriter.write(0x1b);
        mWriter.write(0x21);
        mWriter.write(0);

        mWriter.flush();
    }

    public void printCenterText(String text, int textSize) throws IOException {

        mWriter.write(0x1b);
        mWriter.write(0x21);
        mWriter.write(textSize);

        mWriter.write(text);

        mWriter.write(0x1b);
        mWriter.write(0x21);
        mWriter.write(0);

        mWriter.flush();
    }

    public void printTwoColumn(String title, String content) throws IOException {
        int iNum = 0;
        byte[] byteBuffer = new byte[100];
        byte[] tmp;

        tmp = getGbk(title);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = setLocation(getOffset(content));
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = getGbk(content);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);

        print(byteBuffer);
    }



    public void printThreeColumn(String left, String middle, String right) throws IOException {
        int iNum = 0;
        byte[] byteBuffer = new byte[200];
        byte[] tmp = new byte[0];

        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = getGbk(left);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        int pixLength = getStringPixLength(left) % WIDTH_PIXEL;
        if (pixLength > WIDTH_PIXEL / 2 || pixLength == 0) {
            middle = "\n\t\t" + middle;
        }

        tmp = setLocation(192);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = getGbk(middle);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = setLocation(getOffset(right));
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = getGbk(right);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);

        print(byteBuffer);
    }

    public void printDashLine() throws IOException {
        printText("--------------------------------");
    }

    public void printBitmap(Bitmap bmp) throws IOException {
        bmp = compressPic(bmp);
        byte[] bmpByteArray = draw2PxPoint(bmp);
        printRawBytes(bmpByteArray);
    }

    /*************************************************************************
     * 假设一个360*360的图片，分辨率设为24, 共分15行打印 每一行,是一个 360 * 24 的点阵,y轴有24个点,存储在3个byte里面。
     * 即每个byte存储8个像素点信息。因为只有黑白两色，所以对应为1的位是黑色，对应为0的位是白色
     **************************************************************************/
    private byte[] draw2PxPoint(Bitmap bmp) {
        //先设置一个足够大的size，最后在用数组拷贝复制到一个精确大小的byte数组中
        int size = bmp.getWidth() * bmp.getHeight() / 8 + 1000;
        byte[] tmp = new byte[size];
        int k = 0;
        // 设置行距为0
        tmp[k++] = 0x1B;
        tmp[k++] = 0x33;
        tmp[k++] = 0x00;
        // 居中打印
        tmp[k++] = 0x1B;
        tmp[k++] = 0x61;
        tmp[k++] = 1;
        for (int j = 0; j < bmp.getHeight() / 24f; j++) {
            tmp[k++] = 0x1B;
            tmp[k++] = 0x2A;// 0x1B 2A 表示图片打印指令
            tmp[k++] = 33; // m=33时，选择24点密度打印
            tmp[k++] = (byte) (bmp.getWidth() % 256); // nL
            tmp[k++] = (byte) (bmp.getWidth() / 256); // nH
            for (int i = 0; i < bmp.getWidth(); i++) {
                for (int m = 0; m < 3; m++) {
                    for (int n = 0; n < 8; n++) {
                        byte b = px2Byte(i, j * 24 + m * 8 + n, bmp);
                        tmp[k] += tmp[k] + b;
                    }
                    k++;
                }
            }
//            tmp[k++] = 10;// 换行
        }
        // 恢复默认行距
        tmp[k++] = 0x1B;
        tmp[k++] = 0x32;

        byte[] result = new byte[k];
        System.arraycopy(tmp, 0, result, 0, k);
        return result;
    }

    /**
     * 图片二值化，黑色是1，白色是0
     *
     * @param x   横坐标
     * @param y   纵坐标
     * @param bit 位图
     * @return
     */
    private byte px2Byte(int x, int y, Bitmap bit) {
        if (x < bit.getWidth() && y < bit.getHeight()) {
            byte b;
            int pixel = bit.getPixel(x, y);
            int red = (pixel & 0x00ff0000) >> 16; // 取高两位
            int green = (pixel & 0x0000ff00) >> 8; // 取中两位
            int blue = pixel & 0x000000ff; // 取低两位
            int gray = RGB2Gray(red, green, blue);
            if (gray < 128) {
                b = 1;
            } else {
                b = 0;
            }
            return b;
        }
        return 0;
    }

    /**
     * 图片灰度的转化
     */
    private int RGB2Gray(int r, int g, int b) {
        int gray = (int) (0.29900 * r + 0.58700 * g + 0.11400 * b); // 灰度转化公式
        return gray;
    }

    /**
     * 对图片进行压缩（去除透明度）
     *
     * @param bitmapOrg
     */
    private Bitmap compressPic(Bitmap bitmapOrg) {
        // 获取这个图片的宽和高
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        // 定义预转换成的图片的宽度和高度
        int newWidth = IMAGE_SIZE;
        int newHeight = IMAGE_SIZE;
        Bitmap targetBmp = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas targetCanvas = new Canvas(targetBmp);
        targetCanvas.drawColor(0xffffffff);
        targetCanvas.drawBitmap(bitmapOrg, new Rect(0, 0, width, height), new Rect(0, 0, newWidth, newHeight), null);
        return targetBmp;
    }

    public static void printTest(PrintEntity printEntity,
                                 BluetoothSocket bluetoothSocket, Bitmap bitmap) {

        try {
            PrintUtil pUtil = new PrintUtil(bluetoothSocket.getOutputStream(), "GBK");
            pUtil.printAlignment(2);
            pUtil.printText("（全国连锁）");
            pUtil.printLine();
            pUtil.printLine();

            // 店铺名 居中 放大
            pUtil.printAlignment(1);
            pUtil.printLargeText("速洗达洗衣");
            pUtil.printLine();

            pUtil.printLine();
            pUtil.printLine();
            pUtil.printAlignment(1);
            pUtil.printText("美国GEP干洗");
            pUtil.printLine();


            pUtil.printAlignment(1);
            if (printEntity.getPayOrderPrintEntity() != null){
                pUtil.printText("洗 衣 凭 据");
            }else {
                pUtil.printText("会 员 充 值 凭 据");
            }
            pUtil.printLine();
            pUtil.printLine();

            pUtil.printAlignment(0);
            pUtil.printLine();
            if (printEntity.getPayOrderPrintEntity() != null){
                pUtil.printTwoColumn("交易单号:",printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getOrdersn());
            }else {
                pUtil.printTwoColumn("交易单号:",printEntity.getRechargePrintEntity().getOrderNumber());
            }
            pUtil.printLine();

            if (printEntity.getPayOrderPrintEntity() != null){
                pUtil.printTwoColumn("顾客电话:",printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getMobile());
            }else {
                pUtil.printTwoColumn("顾客电话:",printEntity.getRechargePrintEntity().getMobile());
            }
//            pUtil.printLine();

            if (printEntity.getPayOrderPrintEntity() != null){
                pUtil.printTwoColumn("   件数:",printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPieceNum());
            }
            pUtil.printLine();

            pUtil.printTwoColumn("顾客签字:", "_ _ _ _ _ _ _ _ _ _ _");
            pUtil.printLine();
            pUtil.printLine();

            if (printEntity.getPayOrderPrintEntity() != null){
                pUtil.printDashLine();
                pUtil.printLine();
                pUtil.printText("名称     价格     颜色     瑕疵");
                pUtil.printLine();
                pUtil.printDashLine();
                pUtil.printLine();

                for (int i = 0; i < printEntity.getPayOrderPrintEntity().getPayOrderPrintItems().size(); i++) {
                    pUtil.printText(printEntity.getPayOrderPrintEntity().getPayOrderPrintItems().get(i).getName() + "   ");
                    pUtil.printText(printEntity.getPayOrderPrintEntity().getPayOrderPrintItems().get(i).getPrice() + "   ");
                    pUtil.printText(printEntity.getPayOrderPrintEntity().getPayOrderPrintItems().get(i).getColor() + "   ");
                    pUtil.printText(printEntity.getPayOrderPrintEntity().getPayOrderPrintItems().get(i).getItemNote() + "   ");
                    pUtil.printLine();
                }
                pUtil.printLine();
                pUtil.printLine();
                pUtil.printTwoColumn("总额:", printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getAmount() + "元（附加费：0.00元）");
                pUtil.printLine();
            }else {
                pUtil.printTwoColumn("总额:", printEntity.getRechargePrintEntity().getRechargeAmount() + "元（附加费：" +
                        printEntity.getRechargePrintEntity().getGive()+"元）");
                pUtil.printLine();
            }

            // 分隔线
            pUtil.printDashLine();
            pUtil.printLine();


            if (printEntity.getPayOrderPrintEntity() != null){
                if (printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPayState().equals("1")){
                    pUtil.printAlignment(0);
                    pUtil.printLargeText("实收：" + printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPayAmount() + "元");
                }else {
                    pUtil.printAlignment(2);
                    pUtil.printLargeText("未付款");
                }
            }else {
                pUtil.printLargeText("实收：" + printEntity.getRechargePrintEntity().getRechargeAmount() + "元");
            }
            pUtil.printLine();
            pUtil.printLine();
            pUtil.printLine();


            pUtil.printAlignment(0);
            if (printEntity.getPayOrderPrintEntity() != null){
                if (printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPayState().equals("1")){
                    if (printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPayChannel().equals("1")){
                        pUtil.printTwoColumn("付款方式：","现金支付");
                    }else if (printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPayChannel().equals("2")){
                        pUtil.printTwoColumn("付款方式：","平台会员卡");
                    }else if (printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPayChannel().equals("3")){
                        pUtil.printTwoColumn("付款方式：","专店会员卡");
                    }else if (printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPayChannel().equals("4")){
                        pUtil.printTwoColumn("付款方式：","微信");
                    }else {
                        pUtil.printTwoColumn("付款方式：","支付宝");
                    }
                    pUtil.printLine();
                }
            }else {
                if (printEntity.getRechargePrintEntity().getPayType().equals("1")){
                    pUtil.printTwoColumn("付款方式：","现金支付");
                }else if (printEntity.getRechargePrintEntity().getPayType().equals("2")){
                    pUtil.printTwoColumn("付款方式：","平台会员卡");
                }else if (printEntity.getRechargePrintEntity().getPayType().equals("3")){
                    pUtil.printTwoColumn("付款方式：","专店会员卡");
                }else if (printEntity.getRechargePrintEntity().getPayType().equals("4")){
                    pUtil.printTwoColumn("付款方式：","微信");
                }else {
                    pUtil.printTwoColumn("付款方式：","支付宝");
                }
                pUtil.printLine();
            }

            pUtil.printAlignment(0);
            if (printEntity.getPayOrderPrintEntity() != null){
                if (printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPayState().equals("1")){
                    pUtil.printTwoColumn("优惠：",printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getReducePrice() + "元");
                }
            }
            pUtil.printLine();


            if (printEntity.getPayOrderPrintEntity() != null){
                if (printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPayState().equals("1")){
                    if (printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPayChannel().equals("2") &&
                            printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPayChannel().equals("3")){
                        pUtil.printAlignment(0);
                        pUtil.printTwoColumn("会员卡号：",printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getCardNumber());
                        pUtil.printLine();

                        pUtil.printAlignment(0);
                        pUtil.printTwoColumn("卡支付：",printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPayAmount() + "元");
                        pUtil.printLine();

                        pUtil.printAlignment(0);
                        pUtil.printTwoColumn("卡内余额：",printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getCardBalance() + "元");
                        pUtil.printLine();
                        pUtil.printLine();
                        pUtil.printLine();
                    }
                }
            }else {
                if (!printEntity.getRechargePrintEntity().getPayType().equals("1")){
                    pUtil.printAlignment(0);
                    pUtil.printTwoColumn("会员卡号：",printEntity.getRechargePrintEntity().getUcode());
                    pUtil.printLine();

                    pUtil.printAlignment(0);
                    pUtil.printTwoColumn("卡内余额：",printEntity.getRechargePrintEntity().getBalance() + "元");
                    pUtil.printLine();
                    pUtil.printLine();
                    pUtil.printLine();
                }
            }


            pUtil.printAlignment(0);
            if (printEntity.getPayOrderPrintEntity() != null){
                pUtil.printTwoColumn("本店地址：",printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getAddress());
            }else {
                pUtil.printTwoColumn("本店地址：",printEntity.getRechargePrintEntity().getAddress());
            }
            pUtil.printLine();

            pUtil.printAlignment(0);
            if (printEntity.getPayOrderPrintEntity() != null){
                pUtil.printTwoColumn("服务热线：",printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getPhone());
            }else {
                pUtil.printTwoColumn("服务热线：",printEntity.getRechargePrintEntity().getPhone());
            }
            pUtil.printLine();

            pUtil.printAlignment(0);
            if (printEntity.getPayOrderPrintEntity() != null){
                pUtil.printTwoColumn("店    员：",printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getClerkName());
            }else {
                pUtil.printTwoColumn("店    员：",printEntity.getRechargePrintEntity().getClerkName());
            }

            if (printEntity.getPayOrderPrintEntity() != null){
                pUtil.printTwoColumn("        店号：",printEntity.getPayOrderPrintEntity().getPayOrderPrintInfo().getMid());
            }else {
                pUtil.printTwoColumn("        店号：",printEntity.getRechargePrintEntity().getMid());
            }
            pUtil.printLine();

            pUtil.printAlignment(0);
            pUtil.printTwoColumn("打印日期：",getTodayTime(0));
            pUtil.printLine();

            pUtil.printDashLine();
            pUtil.printAlignment(1);

            pUtil.printAlignment(2);
            pUtil.printBitmap(bitmap);
            if (printEntity.getPayOrderPrintEntity() != null){

                pUtil.printAlignment(1);
                pUtil.printText("(订单二维码)");
                pUtil.printLine();

                pUtil.printDashLine();
                pUtil.printLine();
            }else {
                pUtil.printAlignment(1);
                pUtil.printText("速洗达洗衣");
                pUtil.printLine();
                pUtil.printText("(扫描关注公众号)");
                pUtil.printLine();

                pUtil.printDashLine();
                pUtil.printLine();
            }


            pUtil.printAlignment(1);
            pUtil.printText("欢迎下次再来");
            pUtil.printLine();
            pUtil.printText("请仔细阅读本单");
            pUtil.printLine();

            pUtil.printDashLine();

            pUtil.printLine();
            pUtil.printText("门店联");
//
            pUtil.printLine(8);

        } catch (IOException e) {

        }
    }

    private static String getTodayTime(int number){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, number);
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String qt = sdf.format(calendar.getTime());
        return qt;
    }
}