package com.zj.modules.payment.service;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.zj.modules.payment.config.MyWXPayConfig;
import com.zj.modules.payment.dto.OrderPayInfoForPayDto;
import com.zj.modules.util.BaseUtil;

import lombok.extern.log4j.Log4j;
import net.sf.json.JSONObject;


@Service
@Log4j
@ConfigurationProperties
public class WXPaymentService {

	/**
	 * 异步通知地址
	 */
	@Value("${payment.wx.notifyUrl}")
    private String notifyUrl;
	
	/**
	 * 是否是沙箱环境 false 否。
	 */
	@Value("${payment.wx.useSandbox}")
    private Boolean useSandbox;
	
	//证书存放的位置
	@Value("${payment.wx.certPath}")
	private String certPath;
	
//	@Autowired
//    private WXPay wxPay;
//
//    @Autowired
//    private WXPayClient wxPayClient;
//	@Resource
//	private ServOrderService servOrderService;
	@Resource
	private SerOrderPaymentService serOrderPaymentService;
//	
	/**
	 * 微信native-获取二维码地址
	 * @author zj
	 * @param dto
	 * @return
	 * 创建时间：2019年7月3日 上午9:40:04
	 */
	public String wxQRCodePay(OrderPayInfoForPayDto dto, HttpServletRequest request) {
		
		MyWXPayConfig config = new MyWXPayConfig();
		config.setAppID(dto.getAppId());//公众账号ID
		config.setMchID(dto.getWxMchId());//商户号
		config.setUseSandbox(useSandbox);//非沙箱环境
		config.setKey(dto.getWxKey());
        WXPay wxpay = new WXPay(config);
        
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", dto.getOrderName());
        data.put("out_trade_no", dto.getOrderNO());
//        data.put("device_info", "");//微信支付分配的终端设备号
//        data.put("fee_type", "CNY");//货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
        data.put("attach", "lz_wx");//附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
        data.put("total_fee", dto.getTotalAmount().setScale(2, BigDecimal.ROUND_UP).multiply(new BigDecimal(100)).intValue() + "");//订单总金额，单位为分
        data.put("spbill_create_ip", BaseUtil.getIpAddr(request));//支持IPV4和IPV6两种格式的IP地址。用户的客户端IP
        data.put("notify_url", notifyUrl);
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
//        data.put("nonce_str", BaseUtil.getGivenRandomNum("wx", 4));  // 随机字符串 32位以内
        //由于我们此处的 商品id 是存在多个的， 所以此处暂时 给订单id
        data.put("product_id", dto.getOrderId() + "");//trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
        try {
        	//{nonce_str=nCBgXDCTDfTEbpu2, code_url=weixin://wxpay/bizpayurl?pr=mCZ36EP, appid=wxbacfe13df6bf0a3a, sign=90566CB359036F22CB45C0D361E06696, trade_type=NATIVE, return_msg=OK, result_code=SUCCESS, mch_id=1544426061, return_code=SUCCESS, prepay_id=wx1215221811424337c89c82511504638300}
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
            
//            {nonce_str=UAU9qxQbxQPqOcGw, appid=wxbacfe13df6bf0a3a, sign=D5A4107FF810311F96C9BD0D6238C3EF, err_code=ORDERPAID, return_msg=OK, result_code=FAIL, err_code_des=该订单已支付, mch_id=1544426061, return_code=SUCCESS}
            if ("SUCCESS".equals(resp.get("return_code"))) {//说明请求成功
            	//获取到的生成的二维码串
        		String qrcodeUrl = resp.get("code_url");
        		log.debug("qrcodeUrl = " + qrcodeUrl);
        		return qrcodeUrl;
            } else {//说明请求失败了
            	String returnMsg = resp.get("return_msg");
            	log.info("获取二维码失败！msg= " + returnMsg);
            	throw new RuntimeException("获取二维码失败！msg= " + returnMsg);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取二维码失败！");
        }
	}
	
	
	/**
	 * 异步通知
	 * @author zj
	 * @param request
	 * 创建时间：2019年7月3日 上午9:37:35
	 */
	public void wxSyncNotify(HttpServletRequest request, HttpServletResponse response) {
//		String notifyData = "...."; // 支付结果通知的xml格式数据
		Integer tradeStatus = 1;
		try {
			
//			Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData);  // 转换成map
			Map<String, String> notifyMap = getNotifyParameter(request);
//			{transaction_id=4200000319201907129961731900, nonce_str=db21f5222dcf495f94ba0ffdfe3e789a, bank_type=CFT, openid=o7sco1iLGWUsKW3BrTKoIXjPEMFI, sign=546F3789D9C7D447098CB9B6844D4207, fee_type=CNY, mch_id=1544426061, cash_fee=1, out_trade_no=78962, appid=wxbacfe13df6bf0a3a, total_fee=1, trade_type=NATIVE, result_code=SUCCESS, attach=lz_wx, time_end=20190712165924, is_subscribe=N, return_code=SUCCESS}
			System.out.println(notifyMap);
			
			if (!"SUCCESS".equals(notifyMap.get("return_code"))) {//说明异步通知失败
				String returnMsg = notifyMap.get("return_msg");
            	log.info("获取二维码失败！msg= " + returnMsg);
            	throw new RuntimeException("获取二维码失败！msg= " + returnMsg);
            }
			
			String outTradeNo = notifyMap.get("out_trade_no");
			//获取订单信息
			//验证订单信息
			//获取支付信息
			//存入支付需要相关信息数据
			String payConfig = "";//数据库 或者配置化 对应的 支付相关参数、根据具体的自身的 业务老进行获取
			JSONObject payConfigObj = JSONObject.fromObject(payConfig);
			
			MyWXPayConfig config = new MyWXPayConfig();
			config.setAppID(payConfigObj.getString("appId"));//公众账号ID
			config.setMchID(payConfigObj.getString("wxBusinessNo"));//商户号
			config.setKey(payConfigObj.getString("apiSecretKey"));//api秘钥
			config.setUseSandbox(useSandbox);//非沙箱环境
			WXPay wxpay = new WXPay(config);
			if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
			    // 签名正确
			    // 进行处理。
			    // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
				
				 /**
//	             * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
//	             * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，
//	             * 判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
//	             * 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
//	             */
				returnSucces(response);
	            
	            String tradeNo = notifyMap.get("transaction_id");//微信支付订单号
	            String timeEnd = notifyMap.get("time_end");//支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
	            String totalAmountStr = notifyMap.get("total_fee");
				BigDecimal totalAmount = new BigDecimal(totalAmountStr);
	            serOrderPaymentService.orderComplete(outTradeNo, tradeNo, tradeStatus, totalAmount.divide(new BigDecimal(100)), "WX_PAY","");
			}
			else {
			    // 签名错误，如果数据里没有sign字段，也认为是签名错误
				log.info("wxSyncNotify 微信异步通知异常，签名错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("wxSyncNotify 微信异步通知异常，请检查代码！");
		}
	}
	
	/**
	 * 返回成功， 通知微信端
	 * @author zj
	 * 创建时间：2019年7月5日 下午4:15:16
	 */
	public void returnSucces(HttpServletResponse response) {
		try {
			Map<String, String> responseMap = new HashMap<>(2);
			responseMap.put("return_code", "SUCCESS");
			responseMap.put("return_msg", "OK");
			String responseXml = WXPayUtil.mapToXml(responseMap);
			response.setContentType("text/xml");
			response.getWriter().write(responseXml);
			response.flushBuffer();
		} catch (Exception e) {
			log.info("通知微信端支付成功失败！");
			e.printStackTrace();
		}
	}
	
	  /**
	  * 从request的inputStream中获取参数
	  * @param request
	  * @return
	  * @throws Exception
	  */
	 public Map<String, String> getNotifyParameter(HttpServletRequest request) throws Exception {
	     InputStream inputStream = request.getInputStream();
	     ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
	     byte[] buffer = new byte[1024];
	     int length = 0;
	     while ((length = inputStream.read(buffer)) != -1) {
	         outSteam.write(buffer, 0, length);
	     }
	     outSteam.close();
	     inputStream.close();
	
	     // 获取微信调用我们notify_url的返回信息
	     String resultXml = new String(outSteam.toByteArray(), "utf-8");
	     log.info("resultXml = " + resultXml);
	     Map<String, String> notifyMap = WXPayUtil.xmlToMap(resultXml);
	
	     return notifyMap;
	 }
	
	 /**
	  * 微信查询订单
	  * @author zj
	  * @param request
	  * @param response
	  * 创建时间：2019年7月12日 下午3:02:55
	  */
	 public Map<String, String> wxTradeQuery(OrderPayInfoForPayDto dto)  {
		 Map<String, String> returnMap = new HashMap<String, String>();
		 MyWXPayConfig config = new MyWXPayConfig();
		 config.setAppID(dto.getAppId());//公众账号ID
		 config.setMchID(dto.getWxMchId());//商户号
		 config.setUseSandbox(useSandbox);//非沙箱环境
		 config.setKey(dto.getWxKey());
	     WXPay wxpay = new WXPay(config);
	     Map<String, String> data = new HashMap<String, String>();
	     data.put("out_trade_no", dto.getOrderNO());
	     String returnMsg = "";
	     try {
	        Map<String, String> resp = wxpay.orderQuery(data);
//	        {transaction_id=4200000326201907126158926083, nonce_str=hxgD5o9uABU1gSCM, trade_state=SUCCESS, bank_type=CFT, openid=o7sco1iLGWUsKW3BrTKoIXjPEMFI, sign=3166C56B1DC6BE956567E9B3853A86F7, return_msg=OK, fee_type=CNY, mch_id=1544426061, cash_fee=1, out_trade_no=78963, appid=wxbacfe13df6bf0a3a, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=lz_wx, time_end=20190712171544, is_subscribe=N, return_code=SUCCESS}
	        System.out.println(resp);
	        
	        if ("SUCCESS".equals(resp.get("return_code")) && "SUCCESS".equals(resp.get("trade_state"))) {//说明请求成功
	        	
	        	returnMap.put("code", "200");
				returnMap.put("message", "查询成功");
				returnMap.put("tradeNo", resp.get("transaction_id"));
				returnMap.put("totalAmount", (resp.containsKey("cash_fee")? (new BigDecimal(resp.get("cash_fee")).divide(new BigDecimal(100)).toString()): "0"));//此处单位为分 所以除100
        		return returnMap;
            } else {//说明请求失败了
            	returnMsg = resp.get("return_msg");
            }
	     } catch (Exception e) {
	        e.printStackTrace();
	        
	     }
	     returnMap.put("code", "500");
		 returnMap.put("message", "获取订单信息失败！" + returnMsg);
		 return returnMap;
	 }
	 
	 /**
	  * 微信支付退款
	  * @author zj
	  * @param dto
	  * @return
	  * 创建时间：2019年7月12日 下午3:08:17
	  */
	 public Map<String, String> wxTradeRefund(HttpServletRequest request, OrderPayInfoForPayDto dto)  {
		 Map<String, String> returnMap = new HashMap<String, String>();
		 MyWXPayConfig config = new MyWXPayConfig();
		 config.setAppID(dto.getAppId());//公众账号ID
		 config.setMchID(dto.getWxMchId());//商户号
		 config.setUseSandbox(useSandbox);//非沙箱环境
		 config.setKey(dto.getWxKey());
		 String certIndex = certPath;
		 if (StringUtils.isEmpty(certPath)) {
			 certIndex = BaseUtil.getDemoPath(request, "otherFile");
		 }
		 config.setCertPath(certIndex + "/" + dto.getWxCertPath());//证书地址
	     WXPay wxpay = new WXPay(config);
	     Map<String, String> data = new HashMap<String, String>();
	     data.put("out_trade_no", dto.getOrderNO());
	     data.put("out_refund_no", dto.getOrderNO());//商户退款单号
	     data.put("total_fee", dto.getTotalAmount().setScale(2, BigDecimal.ROUND_UP).multiply(new BigDecimal(100)).intValue() + "");//订单金额
	     data.put("refund_fee", dto.getShouldReturn().setScale(2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).intValue() + "");//退款金额
	     String returnMsg = "";
	     try {
	        Map<String, String> resp = wxpay.refund(data);
	        System.out.println(resp);
	        if ("SUCCESS".equals(resp.get("return_code"))) {//说明请求成功
	        	returnMap.put("code", "200");
				returnMap.put("message", "退款成功");
				returnMap.put("tradeNo", resp.get("transaction_id"));
				returnMap.put("totalAmount", new BigDecimal(resp.get("cash_fee")).divide(new BigDecimal(100)).toString());//此处单位为分 所以除100
        		return returnMap;
            } else {//说明请求失败了
            	returnMsg = resp.get("return_msg");
            }
	        
	     } catch (Exception e) {
	        e.printStackTrace();
	     }
	     returnMap.put("code", "500");
		 returnMap.put("message", "退款失败！" + returnMsg);
		 return returnMap;
	 }
	 
	 
	 public String wxJsApiPay(OrderPayInfoForPayDto dto, HttpServletRequest request) {
			
			MyWXPayConfig config = new MyWXPayConfig();
			config.setAppID(dto.getAppId());//公众账号ID
			config.setMchID(dto.getWxMchId());//商户号
			config.setUseSandbox(useSandbox);//非沙箱环境
			config.setKey(dto.getWxKey());
	        WXPay wxpay = new WXPay(config);
	        
	        Map<String, String> data = new HashMap<String, String>();
	        data.put("body", dto.getOrderName());
	        data.put("out_trade_no", dto.getOrderNO());
//	        data.put("device_info", "");//微信支付分配的终端设备号
//	        data.put("fee_type", "CNY");//货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	        data.put("attach", "lz_wx");//附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
	        data.put("total_fee", dto.getTotalAmount().setScale(2, BigDecimal.ROUND_UP).multiply(new BigDecimal(100)).intValue() + "");//订单总金额，单位为分
	        data.put("spbill_create_ip", BaseUtil.getIpAddr(request));//支持IPV4和IPV6两种格式的IP地址。用户的客户端IP
	        data.put("notify_url", notifyUrl);
	        data.put("trade_type", "JSAPI");  // 此处指定为扫码支付
//	        data.put("nonce_str", BaseUtil.getGivenRandomNum("wx", 4));  // 随机字符串 32位以内
	        //由于我们此处的 商品id 是存在多个的， 所以此处暂时 给订单id
	        //data.put("product_id", dto.getOrderId() + "");//trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
	        try {
	        	//{nonce_str=nCBgXDCTDfTEbpu2, code_url=weixin://wxpay/bizpayurl?pr=mCZ36EP, appid=wxbacfe13df6bf0a3a, sign=90566CB359036F22CB45C0D361E06696, trade_type=NATIVE, return_msg=OK, result_code=SUCCESS, mch_id=1544426061, return_code=SUCCESS, prepay_id=wx1215221811424337c89c82511504638300}
	            Map<String, String> resp = wxpay.unifiedOrder(data);
	            System.out.println(resp);
	            
//	            {nonce_str=UAU9qxQbxQPqOcGw, appid=wxbacfe13df6bf0a3a, sign=D5A4107FF810311F96C9BD0D6238C3EF, err_code=ORDERPAID, return_msg=OK, result_code=FAIL, err_code_des=该订单已支付, mch_id=1544426061, return_code=SUCCESS}
	            if ("SUCCESS".equals(resp.get("return_code"))) {//说明请求成功
	            	//获取到的生成的二维码串
	        		String qrcodeUrl = resp.get("code_url");
	        		log.debug("qrcodeUrl = " + qrcodeUrl);
	        		return qrcodeUrl;
	            } else {//说明请求失败了
	            	String returnMsg = resp.get("return_msg");
	            	log.info("获取二维码失败！msg= " + returnMsg);
	            	throw new RuntimeException("获取二维码失败！msg= " + returnMsg);
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("获取二维码失败" );
	        }
		}
	 
	
//	 /**
//     * 扫码支付 - 统一下单
//     * 相当于支付不的电脑网站支付
//     *
//     * <a href="https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1">扫码支付API</a>
//     */
//    @PostMapping("")
//    public void precreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Map<String, String> reqData = new HashMap<>();
//        reqData.put("out_trade_no", String.valueOf(System.nanoTime()));
//        reqData.put("trade_type", "NATIVE");
//        reqData.put("product_id", "1");
//        reqData.put("body", "商户下单");
//        // 订单总金额，单位为分
//        reqData.put("total_fee", "2");
//        // APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
//        reqData.put("spbill_create_ip", "14.23.150.211");
//        // 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
//        reqData.put("notify_url", "http://3sbqi7.natappfree.cc/wxpay/precreate/notify");
//        // 自定义参数, 可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
//        reqData.put("device_info", "");
//        // 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
//        reqData.put("attach", "");
//
//        /**
//         * {
//         * code_url=weixin://wxpay/bizpayurl?pr=vvz4xwC,
//         * trade_type=NATIVE,
//         * return_msg=OK,
//         * result_code=SUCCESS,
//         * return_code=SUCCESS,
//         * prepay_id=wx18111952823301d9fa53ab8e1414642725
//         * }
//         */
//        Map<String, String> responseMap = wxPay.unifiedOrder(reqData);
//        String returnCode = responseMap.get("return_code");
//        String resultCode = responseMap.get("result_code");
//        if (WXPayConstants.SUCCESS.equals(returnCode) && WXPayConstants.SUCCESS.equals(resultCode)) {
//            String prepayId = responseMap.get("prepay_id");
//            String codeUrl = responseMap.get("code_url");
//
//            BufferedImage image = PayUtil.getQRCodeImge(codeUrl);
//
//            response.setContentType("image/jpeg");
//            response.setHeader("Pragma","no-cache");
//            response.setHeader("Cache-Control","no-cache");
//            response.setIntHeader("Expires",-1);
//            ImageIO.write(image, "JPEG", response.getOutputStream());
//        }
//
//    }
//
//    /**
//     *
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    public void precreateNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Map<String, String> reqData = wxPayClient.getNotifyParameter(request);
//
//        /**
//         * {
//         * transaction_id=4200000138201806180751222945,
//         * nonce_str=aaaf3fe4d3aa44d8b245bc6c97bda7a8,
//         * bank_type=CFT,
//         * openid=xxx,
//         * sign=821A5F42F5E180ED9EF3743499FBCF13,
//         * fee_type=CNY,
//         * mch_id=xxx,
//         * cash_fee=1,
//         * out_trade_no=186873223426017,
//         * appid=xxx,
//         * total_fee=1,
//         * trade_type=NATIVE,
//         * result_code=SUCCESS,
//         * time_end=20180618131247,
//         * is_subscribe=N,
//         * return_code=SUCCESS
//         * }
//         */
//
//        // 特别提醒：商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失。
//        boolean signatureValid = wxPay.isPayResultNotifySignatureValid(reqData);
//        if (signatureValid) {
//            /**
//             * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
//             * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，
//             * 判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
//             * 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
//             */
//
//            Map<String, String> responseMap = new HashMap<>(2);
//            responseMap.put("return_code", "SUCCESS");
//            responseMap.put("return_msg", "OK");
//            String responseXml = WXPayUtil.mapToXml(responseMap);
//
//            response.setContentType("text/xml");
//            response.getWriter().write(responseXml);
//            response.flushBuffer();
//        }
//    }
//	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
