package com.free.tools.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 * 二维码工具类
 *
 * @Author: dinghao
 * @Date: 2021-12-25 17:56:49
 */
public class QrcodeUtil {

    /**
     * 构造方法私有化
     */
    private QrcodeUtil() {
    }

    public static String createQrcode(String dir, String content) {
        return createQrcode(dir, null, content, 300, 300);
    }

    public static String createQrcode(String dir, String name, String content) {
        return createQrcode(dir, name, content, 300, 300);
    }

    /**
     * 创建二维码到指定位置
     * @param dir
     * @param name
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static String createQrcode(String dir, String name, String content, int width, int height) {
        try {
            String qrcodeFormat = "png";
            String qrcodeName;
            HashMap<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            if (name == null) {
                qrcodeName = IdUtil.get36UUId();
            } else {
                qrcodeName = name;
            }
            File file = new File(dir + "/" + qrcodeName + "." + qrcodeFormat);
            MatrixToImageWriter.writeToPath(bitMatrix, qrcodeFormat, file.toPath());
            return file.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("生成二维码失败", e);
        }
    }

    /**
     * 解析二维码内容
     * @param filePath
     * @return
     */
    public static String decodeQr(String filePath) {
        String retStr;
        if ("".equalsIgnoreCase(filePath)) {
            throw new RuntimeException("解析二维码失败，图片路径不能为空");
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap bitmap = new BinaryBitmap(binarizer);
            HashMap<DecodeHintType, Object> hintTypeObjectHashMap = new HashMap<>();
            hintTypeObjectHashMap.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(bitmap, hintTypeObjectHashMap);
            retStr = result.getText();
        } catch (Exception e) {
            throw new RuntimeException("解析二维码失败", e);
        }
        return retStr;
    }

    public static void main(String[] args) {
        //String qrUrl = createQrcode("", "测试");
        System.out.println(decodeQr(""));
    }
}
