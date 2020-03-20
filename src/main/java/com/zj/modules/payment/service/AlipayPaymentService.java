package com.zj.modules.payment.service;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.zj.modules.payment.config.AlipayConfig;
import com.zj.modules.payment.dto.OrderPayInfoForPayDto;
import com.zj.modules.payment.entity.ServPaySettings;
import com.zj.modules.util.BaseUtil;

import lombok.extern.log4j.Log4j;
import net.sf.json.JSONObject;


@Service
@Log4j
//@PropertySource("classpath:application-payment.yml")
@ConfigurationProperties
public class AlipayPaymentService {

	@Value("${payment.alipay.gatewayUrl}")
    private String gatewayUrl;
	
	@Value("${payment.alipay.charset}")
    private String charset;
	
	@Value("${payment.alipay.signType}")
    private String signType;
	
	@Value("${payment.alipay.notifyUrl}")
    private String notifyUrl;
	
//	private final String NOTIFY_PATH = "/lz/om/payment/alipay/syncNotify";
	
	@Value("${payment.alipay.returnUrl}")
    private String returnUrl;
	
	@Resource  
    public Environment env;
	
	@Resource
	private SerOrderPaymentService serOrderPaymentService;
	
	/**
	 * 支付宝网页支付-获取二维码地址
	 * @author zj
	 * @return
	 * 创建时间：2019年7月2日 上午11:40:55
	 */
	public String alipayForPagePay(OrderPayInfoForPayDto dto) {
		
		//TODO 此处需要获取商家自己的支付配置项数据
		
		
		//获得初始化的AlipayClient
//		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key,
//				"json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
//		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", "2016101100661839", 
//				"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC48fsXkSvI7G8uRHzzRHyW6O59yqCFt7Cb8Y82xCnchdFNhFIG6AdpirmSiI04CkVfewyZ6Ln43KCD3tou4/XDaJ0bUgOtmj7vDROSoHD1I87p2G9eISUSNQq1dFvQH2/H1xnOUfaFaW9HhbJohqU5YMyqbE0WVH/2433GSQG2RZAw8atESLcwKtYDxhCQGDMeS8k8g2SKvK4NE8VHjX+CuDAUmqP+grUSEQ15X0N8koWWBMYo+jds06vbkBLtYcgABkKAQd1J0sRMNpt7eg+0tr8/v9PQ/BAIcGkOL0ZqbYVLyMNEcmjS7w7eQF5VH/cm1u21oFB2dgoC1b5tuglVAgMBAAECggEASYTHz8KBqUlzmOzYzst2y3ak95RI41718cfAqoCoM2VuomXrRZuO9sGSq1Fk8dYDZcVAaR4+b1Mk68b27JTp6Vf4vBeKJsSeJ+EG2s60mH43jnXfL4f1eJtlOmSa6szUXph/RakHWlljRwc6uJptq7VzJnwO2MWWaMpeASicaZvnxAxGBM7IeFVRKqyGYd17og+wyTiq55g0BI2O3dmnJIviO8drdyCkAoEyvq79ju1O7p5CScihFN71Y2VofR+1fMJRGvfJ9dNFUuxb7KFcGnd3vwG9aFcX5wdAiz1EBlTcGXlv1j4ex10evshWLkTDiuah0PZBc2LNJ7yK7OfBAQKBgQDvOgD433dwxMnNBZ4egp5bcLB2PpAHTAMbm4rG8GmKIZosJUiW9q6EMj5J0KPwfGJWkijTDIBPWtB/Tq64tkEXuNOuiB7EQts/sajXAFzUgtdwYrWTes1Gk9AUb5w4nnkQ6FMv3/0rRQ6MxFpeyWKlqQ1LP8//GwbZHvIvVwoFEQKBgQDF6aeHL5bakoywecvNRy2xKanCxPFRr70I54QqIhJ/NDM2moVuZ+RGilIkjLCDP+oVMRa0BlF4x8NRoc2LFk4j1UprHiMgwS/F8rNa+VAqI0Eyk/6w95uLVMv247Im9N0fTrDMCRdyP7zWQZtEnsYk9gDaPUp/F91mUkhkWUjwBQKBgQCjlgd6J92WOItCbpf8hxdgsgOJKCj3RGxPoaJZfaa99VJm46vqx475CR58/XZNidD5IANYppDLMu+mTpi96KtEXHgsPhrD3G/u8z7gnvfbvgkyad1+lxfuLj+46cVrFjr1a9kOwN1vjE5xxgeCD7YsUBiuH1nNOZ8KM9Yqtxs78QKBgQCqmeBEgvxJy1wlX8gTWtEDT4O7liLjJFcSDuf5ncdOCYRABHESm9HGEMQAJ5qceQLiY59LbcrbD2/JtW1GAOM4tkphDeh/+qegvbZnrFOzDxLLc5FvoPFe6KitWNegByF5NE7ogsnIPTMdig4615a8E6bmeUD3T24VHsayYdiitQKBgCNCADDq5Z5I1/kI25BOhU9ttXWkWxiZMAoYlromcRdUCqlPMOIpopchK5AdN8NCQmRexuztp1rqD3Iq275q+/hB+StxgzerAMNP+qxPkFARLcYhG0+KrziAIg0B0Jw7tEcacb1IZcoJh07R91Ap7Es9N8UmiY/XyQQn/Jbtt9OV",
//				"json", AlipayConfig.charset, 
//				"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzcSlzU+86EmQLxfnka5/ts5J5K6loum1D95W46+dDUtHaFUCT09+6kEbzK37+CKHG0ij76BgVvdks0WDy0zHrll9u3dvVxhwzYS9v1cvmJutD5v2IS0pMl63msDUaOLbWzn3DtMq3WfIQrWFdDrJjBVjFggdpaluucTPFUkfF/GeXNLIz0kghEx5LBG9B36Ql9s5Q7k2U23tIg2AdIUbmHnAyhQCn7SGsJWuIb4uRlbVpz/vkW3idMQ7LbOr+kVRWlQbwbsMAz/UMOwza56JqB4HezYJf8iKS5aFayCvt+xMBhlefYKGkDNmnk0vWKPoDkboSaZP2Jl/Q3Q5DPLECQIDAQAB", 
//				AlipayConfig.sign_type);
		
//		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "2019071065799209", 
//				"MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCpIBfLoNLXEyiZH/O42gk/3FohBMBb2vp5+yKDX2Y8268aO11+hc7IgXOfsTkkBiedIjK6GX8u4HFNBvxiDwREeyFwJrnOnDKPHh8uX4FXumYjSy3sD7woqS8H1D3qMXq267KGkg7l7yegp/nBX2ll9smCRr3qziyy0dycUGHVbhJQ4/P+4Tx6ChuUSAX6hUx1U6QCYKKMw5sY1OsvdHZB/6fOIMWv8ddigx/yE73apsD3L0XMQziRqVm9rJC/t20+uHNvXfpEH1cQ1rw3h0gr7A7Hb3G66C7sWOFe876Qru6bwMWvW67+I/+kLH1W9FargWmSEQZwBlspUmbWJZixAgMBAAECggEBAKFtaokIVnbHaKbPorg8FTwJPVXqzRhf6Vho5sZyVomcPoRRSaosWfVz6kLwy9OgtEr9i2GqWIUL4dld46RNJQugY4hCznNxD6kfXS/MJQ7xcfAaIBkD49sDsUpNwVM6KyFR07My0lmSbkagTnnOcjHK3wgkMb5fULbWLTZeWyN1QVOzCX1TC3RiVwZnEBx1ut+EDMgdRm89Ej9uw53bR8VHGBRWJo/QOnfQbyz0DlP5sWAiPiOJ/gni2Tkd12VDQ6OyUeBLmzsX+ptXCDgy04citr2efJy5WSM95gB1lWJY3JgKA+N6SsO2RErSkOf6JhJyl8et8yIHMxPsNuOT5IECgYEA1eSQC5yNbIGLXsSSJ18/c+BC4MSb4hty0xtCZuKk+soYmbT2HreDUtQrUkyro07WXfOHPH1AxZC6qz4iF0rGQalkofEXVN4enT8l+gwNs4WD0UqQGMBWTOpREMkBj5dh8LtSRzoUprmCFxlhpHY/psQuvPsYASnUBCrQhgojIqkCgYEAymto2R4i9ifcsdtdizyfe14ChyFoVV2EquYzQZ1pd4Vc/HQ05WWj1VEIv8y67g+ZddtQf1go51xvq+uLfL4c1UkiU2j9NGT7otSW40Gm3F3Ul7/ze0bgy1dpVUc/6IB7ZXpSeBWJoc8kS1RssFoZsF4h9OC+nikbJbuQ0gpTkskCgYEApWXHFZOLsNpD4ZZzxkpbEMncLV8bg67iDejIYjLkjKH2f/V1VK9nt4clsNBk8TWngqMsclQQ4DVYg87aIdTKlj/c93UtK41Pcx0/yqnDdozKL+9OmjUxBu0ynp4wvM69B6BysW1yRrKwrBgOb1SZR+XwlmY8W7nynv5UnTH9lXECgYBNrQwODTeDWGajRbfO35OfpsK3kkVURM/NOQtASaSYPfAh3IIT6X4GNNNzrp+pDQlppaBtTapcpicgRQJA+z9bRXZLpcVMntfFc2j3ilD6zXLwTLOlgdxvi3qbQ/0tPmib12XgOPuYwUF/OsV+owSqnmTPcJMVZFbLkv+O6nGJqQKBgQCmIn+CubzgRDxyJvAFciBnNRoDUgl5BRceGJma9ao/dN8duT0yaYTnl19n99w+u5S2nlFitn2GWadXo8qJqCXbmw3rS9Wa3/7y4lohRNzve/ZoIKlwXE1darQ4/BLmBjSYy3WNSZzKhI0LxIXZkdOpBiUSwhPIrKINb9ewe8O9Ow==",
//				"json", AlipayConfig.charset, 
//				"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnBSZEl/oaSpbIfXNQD2lW1HtLFsPW1lvnBJF4e28WSY2XRekA036/s8ppETodNzHqC6g5ZJiPQ53XlIpUWskXiPbN/uEKTRx7DGRRk+NTvAKVgvJnMiYBBl5+yvEMKlcGQdjiy6FPrR6BD9yP8RbxSxnSsl240bhV6+zPUV6Mu4+X6nvn8tcZ4nIiDqzHj7Tf3oVxldhPOmTw/DN/ARHim+q7BTDOT4r14vsTH7EWrXy+o6r1hXZjwsK0eiPYLwKqRrSR9OCewkawDJJMuWSkP0+Ta6S+yT9q9gDBjXxc9o/SScO07Qi/xy00STu3pBn3FqvLW8w70P3J0o4cvI2LQIDAQAB", 
//				AlipayConfig.sign_type);
		
		log.info("appid" + dto.getAppId());
		log.info("商户私钥" + dto.getMerchantPrivateKey());
		log.info("支付宝公钥" + dto.getAlipayPublicKey());
		AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, dto.getAppId().trim(), 
				BaseUtil.replaceBlank(dto.getMerchantPrivateKey()), 
				"json", charset, 
				BaseUtil.replaceBlank(dto.getAlipayPublicKey()), 
				signType); //获得初始化的AlipayClient
		
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(returnUrl);
		alipayRequest.setNotifyUrl(notifyUrl);
		
//		//商户订单号，商户网站订单系统中唯一订单号，必填
//		String out_trade_no = dto.getOrderNO();
//		//付款金额，必填
//		String total_amount = dto.getTotalAmount().toString();
//		//订单名称，必填
//		String subject = dto.getOrderName();
//		//商品描述，可空
//		String body = dto.getRemark();
		
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = dto.getOrderNO();
		//付款金额，必填
		String total_amount = dto.getTotalAmount().setScale(2, BigDecimal.ROUND_UP).toString();
		//订单名称，必填
		String subject = dto.getOrderName();
		//商品描述，可空
		String body = "订购服务";
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total_amount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		//若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		//alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
		//		+ "\"total_amount\":\""+ total_amount +"\"," 
		//		+ "\"subject\":\""+ subject +"\"," 
		//		+ "\"body\":\""+ body +"\"," 
		//		+ "\"timeout_express\":\"10m\"," 
		//		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		//请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
		
		//请求
		String result = "";
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取网页支付失败!");
		}
		
		//输出
		System.out.println(result);
		
		return result;
	}
	
	/**
	 * 支付宝当面付-获取二维码地址
	 * @author zj
	 * @param dto
	 * @return
	 * 创建时间：2019年7月3日 上午9:40:04
	 */
	public String alipayQRCodePay(OrderPayInfoForPayDto dto) {
		
		AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, dto.getAppId(), 
				dto.getMerchantPrivateKey(), 
				"json", charset, 
				dto.getAlipayPublicKey(), 
//				"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqSAXy6DS1xMomR/zuNoJP9xaIQTAW9r6efsig19mPNuvGjtdfoXOyIFzn7E5JAYnnSIyuhl/LuBxTQb8Yg8ERHshcCa5zpwyjx4fLl+BV7pmI0st7A+8KKkvB9Q96jF6tuuyhpIO5e8noKf5wV9pZfbJgka96s4sstHcnFBh1W4SUOPz/uE8egoblEgF+oVMdVOkAmCijMObGNTrL3R2Qf+nziDFr/HXYoMf8hO92qbA9y9FzEM4kalZvayQv7dtPrhzb136RB9XENa8N4dIK+wOx29xuugu7FjhXvO+kK7um8DFr1uu/iP/pCx9VvRWq4FpkhEGcAZbKVJm1iWYsQIDAQAB",
				signType); //获得初始化的AlipayClient
		
		//创建API对应的request类
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		//设置请求参数
//		request.setReturnUrl(AlipayConfig.return_url);
		request.setNotifyUrl(notifyUrl);
		request.setBizContent("{" +
		"    \"out_trade_no\":\"" + dto.getOrderNO() + "\"," +
		"    \"total_amount\":\""+ dto.getTotalAmount().setScale(2, BigDecimal.ROUND_UP).toString() +"\"," +
		"    \"subject\":\"" + dto.getOrderName() + "\"" +
		"    }");
//		request.setBizContent("{" +
//		"    \"out_trade_no\":\"" + dto.getOrderNO() + "\"," +
//		"    \"seller_id\":\"2088102179101631\"," +
//		"    \"total_amount\":\""+ dto.getTotalAmount().setScale(2, BigDecimal.ROUND_UP).toString() +"\"," +
//		"    \"discountable_amount\":\"8.88\"," +
//		"    \"undiscountable_amount\":\"80\"," +
//		"    \"buyer_logon_id\":\"15901825620\"," +
//		"    \"subject\":\"" + dto.getOrderName() + "\"" +
//		"    \"store_id\":\"NJ_001\"" +
//		"    }");
		//通过alipayClient调用API，获得对应的response类
		AlipayTradePrecreateResponse response = new AlipayTradePrecreateResponse();
		try {
			response = alipayClient.execute(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("获取二维码失败!");
			throw new RuntimeException("获取二维码失败!\"");
		}
		
		String responseBody = response.getBody();
		System.out.print(responseBody);
		//根据response中的结果继续业务逻辑处理
		JSONObject responseObj = JSONObject.fromObject(responseBody);
//		alipay_trade_precreate_response
		JSONObject contentObj = responseObj.getJSONObject("alipay_trade_precreate_response");
		
		if (!"10000".equals(contentObj.getString("code"))) {
			String messsage = contentObj.getString("sub_msg");
			log.info("获取二维码失败!" + messsage);
			throw new RuntimeException("获取二维码失败!\"");
		}
		
		//获取到的生成的二维码串
		String qrcodeUrl = contentObj.getString("qr_code");
		log.info("qrcodeUrl = " + qrcodeUrl);
		
		return qrcodeUrl;
	}
	
	
	/**
	 * 异步通知
	 * @author zj
	 * @param request
	 * 创建时间：2019年7月3日 上午9:37:35
	 */
	public void alipaySyncNotify(HttpServletRequest request, HttpServletResponse response) {
		//获取支付宝POST过来反馈信息
		
		Map<String,String> params = new HashMap<String,String>();
		
		Integer tradeStatus = 1;
		String remark = "";
		try {
			request.setCharacterEncoding("UTF-8");
			Map<String,String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用
//				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			
			String outTradeNo = params.get("out_trade_no");
			log.info("outTradeNo=" + outTradeNo);
			//获取订单信息
			//验证订单信息
			//获取支付信息
			//存入支付需要相关信息数据
			String payConfig = "";//根据自身系统获取到对应的 支付配置
			JSONObject payConfigObj = JSONObject.fromObject(payConfig);
			
			log.info("alipay_public_key = " + payConfigObj.getString("alipayPublicKey"));
			boolean signVerified = AlipaySignature.rsaCheckV1(params, payConfigObj.getString("alipayPublicKey"), AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
			log.info("验证的状态signVerified = " + signVerified);
			
			//——请在这里编写您的程序（以下代码仅作参考）——
			
			/* 实际验证过程建议商户务必添加以下校验：
			1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
			2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
			3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
			4、验证app_id是否为该商户本身。
			*/
			if(signVerified) {//验证成功
				//商户订单号
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
				//支付宝交易号
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
				//交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
				
				if(trade_status.equals("TRADE_FINISHED")){
					//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
						
					//注意：
					//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				}else if (trade_status.equals("TRADE_SUCCESS")){
					//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					
					//注意：
					//付款完成后，支付宝系统发送该交易状态通知
					
					response.getWriter().write("success");
			        response.flushBuffer();
					log.info("通知成功  out_trade_no= " + out_trade_no + ",trade_no = " + trade_no + ", trade_status = " + trade_status);
					
					String totalAmountStr = "0";
//					{gmt_create=2019-07-22 19:59:24, charset=utf-8, gmt_payment=2019-07-22 20:00:06, notify_time=2019-07-22 20:00:07, subject=服务订购, 
//					buyer_id=2088102179189105, body=订购服务, invoice_amount=0.02, version=1.0, notify_id=2019072200222200007089101000443850, 
//							fund_bill_list=[{"amount":"0.02","fundChannel":"ALIPAYACCOUNT"}], notify_type=trade_status_sync, out_trade_no=FWSQJ20190722195913158157, 
//							total_amount=0.02, trade_status=TRADE_SUCCESS, trade_no=2019072222001489101000035593, auth_app_id=2016101100661839, 
//							receipt_amount=0.02, point_amount=0.00, app_id=2016101100661839, buyer_pay_amount=0.02, seller_id=2088102179101631}
					if (params.containsKey("total_amount")) {
						totalAmountStr = params.get("total_amount");
					}
//					if (!StringUtils.isEmpty(totalAmountStr) && params.containsKey("refund_fee")) {//总退款金额
//						totalAmountStr = params.get("refund_fee");
//					}
					BigDecimal totalAmount = new BigDecimal(totalAmountStr);
			        serOrderPaymentService.orderComplete(params.get("out_trade_no"), params.get("trade_no"), tradeStatus, totalAmount,  "ALI_PAY", remark);
				}
//				response.getWriter().write("success");
//		        response.flushBuffer();
//				log.info("通知成功  out_trade_no= " + out_trade_no + ",trade_no = " + trade_no + ", trade_status = " + trade_status);
//		        
//		        serOrderPaymentService.orderComplete(params.get("out_trade_no"), params.get("trade_no"), tradeStatus, remark);
			}else {//验证失败
				tradeStatus = 0;
				remark = "验证失败";
				log.info("支付宝验证签名 验证失败");
				//调试用，写文本函数记录程序运行情况是否正常
				//String sWord = AlipaySignature.getSignCheckContentV1(params);
				//AlipayConfig.logResult(sWord);
				response.getWriter().write("fail");
		        response.flushBuffer();
			}
		} catch (Exception e) {
			e.printStackTrace();
			tradeStatus = 0;
			remark = "异步通知异常";
			log.info("异步通知异常");
		}
	}
	
	/**
	 * 支付宝-订单查询
	 * @author zj
	 * @param dto
	 * @return 订单号
	 * 创建时间：2019年7月11日 下午6:19:30
	 */
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> alipayTradeQuery(OrderPayInfoForPayDto dto) {
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
			//获得初始化的AlipayClient
			AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, dto.getAppId(), dto.getMerchantPrivateKey(),
					"json", AlipayConfig.charset, dto.getAlipayPublicKey(), AlipayConfig.sign_type);
			//设置请求参数
			AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
			
			//请二选一设置
//			alipayRequest.setBizContent("{\"out_trade_no\":\""+ dto.getOrderNO() +"\","+"\"trade_no\":\""+ dto.getTradeNo() +"\"}");
			alipayRequest.setBizContent("{\"out_trade_no\":\""+ dto.getOrderNO() + "\"}");
			
			//请求
			String result = alipayClient.execute(alipayRequest).getBody();
			System.out.print(result);
			//根据response中的结果继续业务逻辑处理
			JSONObject responseObj = JSONObject.fromObject(result); 
//			alipay_trade_precreate_response
			JSONObject contentObj = responseObj.getJSONObject("alipay_trade_query_response");
			if ("10000".equals(contentObj.getString("code"))) {//说明出错了
				String tradeNo = contentObj.getString("trade_no");
				String totalAmount = contentObj.getString("total_amount");
				//交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
				String tradeStatus = contentObj.getString("trade_status");
				returnMap.put("code", "200");
				returnMap.put("message", "查询成功");
				returnMap.put("tradeNo", tradeNo);
				returnMap.put("totalAmount", totalAmount);
				return returnMap;
			} else {
				returnMap.put("code", "500");
				returnMap.put("message", contentObj.getString("sub_msg"));
				return returnMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("查询支付宝订单信息失败");
		}
		
		returnMap.put("code", "500");
		returnMap.put("message", "查询支付宝订单信息数据失败！");
		return returnMap;
	}
	
	/**
	 * 支付宝退狂
	 * @author zj
	 * @param dto
	 * @return
	 * 创建时间：2019年7月11日 下午6:49:55
	 */
	public Map<String, String> alipayTradeRefund(OrderPayInfoForPayDto dto) {
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
			//获得初始化的AlipayClient
			AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, dto.getAppId(), dto.getMerchantPrivateKey(),
					"json", AlipayConfig.charset, dto.getAlipayPublicKey(), AlipayConfig.sign_type);
			
			//设置请求参数
			AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
			
//			alipayRequest.setBizContent("{" //\"out_trade_no\":\""+ dto.getOrderNO() +"\", //商户订单号，商户网站订单系统中唯一订单号
//					+ "\"trade_no\":\""+ dto.getTradeNo() +"\"," //需要退款的金额，该金额不能大于订单金额，必填
//					+ "\"refund_amount\":\""+ dto.getTotalAmount() +"\"," 
//					+ "\"refund_reason\":\""+ dto.getRemark() +"\"," //退款的原因说明
//					+ "\"out_request_no\":\""+ dto.getOutRequestNo() +"\"}");////标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
			
			alipayRequest.setBizContent("{\"out_trade_no\":\""+ dto.getOrderNO() +"\"," //商户订单号，商户网站订单系统中唯一订单号
//					+ "\"trade_no\":\""+ dto.getTradeNo() +"\"," //需要退款的金额，该金额不能大于订单金额，必填
					+ "\"refund_amount\":\""+ dto.getShouldReturn() +"\"," 
					+ "\"refund_reason\":\""+ dto.getRemark() +"\"," //退款的原因说明
					+ "\"out_request_no\":\""+ dto.getOutRequestNo() +"\"}");////标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
			
			//请求
			String result = alipayClient.execute(alipayRequest).getBody();
			System.out.print(result);
			log.debug("alipayTradeRefu========================================result = ");
			log.debug("alipayTradeRefu========================================result = " + result);
			log.debug("alipayTradeRefu========================================result = ");
			//根据response中的结果继续业务逻辑处理
			JSONObject responseObj = JSONObject.fromObject(result);
//			alipay_trade_precreate_response
//			{"alipay_trade_refund_response":{"code":"10000","msg":"Success","buyer_logon_id":"bhu***@sandbox.com",
//			"buyer_user_id":"2088102179189105","fund_change":"Y","gmt_refund_pay":"2019-07-22 19:36:47","out_trade_no":"FWSQJ20190722192624154652",
//			"refund_fee":"0.01","send_back_fee":"0.00","trade_no":"2019072222001489101000034435"},
//		"sign":"BCPGgX9hNz3214p2aMGgCAXy0+On7EeHitRqavuhjDyLVcZA6KSipAvhuJ5uqoLEqJqtCtMUwX0AVJxH+Sl+pmNL8AIvKD805HSJCCs4NeGcFq/6HA/7b75IvTVJ6iVfUO/iFkQPCPSR4odlaHOITRrTmJ/BXrqBn+c1PVSulf/1UAuLtgg4UNbJBLeb+HgpubCqYSCwENqjdO1g2uygXu+m9KRJtYCxxYrU97rR60sbEh00zIX+Iur+usJujNJFNEqI0YuOUmbmfGqbYRCDn+EjcMSNV1OspQP4sl9gVDA6RTUrEb1e9thSpoBslyGrT9iDN6PPNVJbHzdaPxTAjA=="}Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3bd0d670]

			JSONObject contentObj = responseObj.getJSONObject("alipay_trade_refund_response");
			if ("10000".equals(contentObj.getString("code"))) {//说明出错了
				String tradeNo = contentObj.getString("trade_no");
				String totalAmount = "0";
				
//				if (contentObj.containsKey("total_amount")) {
//					totalAmount = contentObj.get("total_amount") + "";
//				}
				if (contentObj.containsKey("refund_fee")) {
					totalAmount = contentObj.get("refund_fee") + "";
				}
				
				returnMap.put("code", "200");
				returnMap.put("message", "查询成功");
				returnMap.put("tradeNo", tradeNo);
				returnMap.put("totalAmount", totalAmount);
				return returnMap;
			} 
		} catch (AlipayApiException e) {
			e.printStackTrace();
			log.info("查询支付宝订单信息失败");
		}
		
		returnMap.put("code", "500");
		returnMap.put("message", "查询支付宝订单信息数据失败！");
		return returnMap;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
