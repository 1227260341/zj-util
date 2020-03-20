package com.zj.modules.payment.config;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import lombok.extern.log4j.Log4j;

@Log4j
public class PayUtil {

    /**
     * 根据url生成二位图片对象
     *
     * @param codeUrl
     * @return
     * @throws WriterException
     */
    public static BufferedImage getQRCodeImge(String codeUrl) throws WriterException {
        Map<EncodeHintType, Object> hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF8");
        int width = 256;
        BitMatrix bitMatrix = (new MultiFormatWriter()).encode(codeUrl, BarcodeFormat.QR_CODE, width, width, hints);
        
        BufferedImage image = new BufferedImage(width, width, 1);
        
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < width; ++y) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? -16777216 : -1);
            }
        }
        
        return image;
        //获取到的图片控制层输出方式
        /**
//       * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
//       * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，
//       * 判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
//       * 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
//       */
//
//      Map<String, String> responseMap = new HashMap<>(2);
//      responseMap.put("return_code", "SUCCESS");
//      responseMap.put("return_msg", "OK");
//      String responseXml = WXPayUtil.mapToXml(responseMap);
//
//      response.setContentType("text/xml");
//      response.getWriter().write(responseXml);
//      response.flushBuffer();
        
    }
    
    /**
     * 获取二维码{去除白边}
     * @author zj
     * @param codeUrl
     * @return
     * @throws WriterException
     * 创建时间：2019年7月10日 下午7:25:15
     */
    public static BufferedImage getQRCodeImgeRemoveBlacek(String codeUrl) throws WriterException {
       
    	 Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
         hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
         hints.put(EncodeHintType.CHARACTER_SET, "UTF8");
         hints.put(EncodeHintType.MARGIN, 1);
         int QRCODE_SIZE = 256;
         log.info("codeUrl" + codeUrl);
         BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl,
                 BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
         //调用去除白边方法
         bitMatrix = deleteWhite(bitMatrix);
         int width = bitMatrix.getWidth();
         int height = bitMatrix.getHeight();
         BufferedImage image = new BufferedImage(width, height,
                 BufferedImage.TYPE_INT_RGB);
         for (int x = 0; x < width; x++) {
             for (int y = 0; y < height; y++) {
                 image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() //int 0
                         : Color.WHITE.getRGB()); //int 255);
             }
         }
         return image;
        
    }
    
    /**
     * 去除白边
     * @author zj
     * @param matrix
     * @return
     * 创建时间：2019年7月10日 下午6:54:27
     */
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;
 
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }
    
    /**
     * 生成二维码并使用Base64编码
     * @param content
     * @throws Exception
     * 网页中显示的时候要加上前缀：data:image/jpg;base64,（此处默认给加上）
     */

    public static String getBase64QRCode(String content) throws Exception {
      MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
      @SuppressWarnings("rawtypes")
      Map hints = new HashMap();
      
//      BufferedImage image = getQRCodeImge(content);
      BufferedImage image = getQRCodeImgeRemoveBlacek(content);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ImageIO.write(image, "png", outputStream);
      String base64String = "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray()); 
      return base64String;
    }
}
