package com.zj.modules.payment.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zj.modules.payment.config.PayUtil;
import com.zj.modules.payment.dto.OrderPayInfoDto;
import com.zj.modules.payment.service.AlipayPaymentService;
import com.zj.modules.payment.service.SerOrderPaymentService;
import com.zj.modules.payment.service.WXPaymentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;

@RestController
@Api(tags = "支付")
@RequestMapping("/payment")
@Log4j
public class PaymentController {
	
	private final String WX_PAY = "WX_PAY";//微信支付
	private final String ALI_PAY = "ALI_PAY";
	private final String BALANCE = "BALANCE_PAY";

//	@Resource
//	private PaymentService alipayPaymentService;
	
	@Resource
	private SerOrderPaymentService serOrderPaymentService;
	
	@Resource
	private AlipayPaymentService alipayPaymentService;
	@Resource
	private WXPaymentService wxPaymentService;
//	@Autowired
//    private RedisTemplateService redisTemplateService;
	
	/**
	 * 支付宝网页支付(第三方支付返回二维码，如果余额支付则返回余额)
	 * @author zj
	 * @param dto
	 * @return
	 * 创建时间：2019年7月8日 下午4:22:44
	 */
	@ApiOperation("支付宝网页支付(第三方支付返回二维码base64格式，如果余额支付则返回余额)")
	@RequestMapping(value="/forPay", method = RequestMethod.POST)
	public Object forPay(@Valid @RequestBody OrderPayInfoDto dto, HttpServletResponse response, HttpServletRequest request) {
		dto.setPayType(ALI_PAY);
		Object result = "";
		Boolean isQRcode = true;
		switch (dto.getPayType()) {
		case WX_PAY:
			result = serOrderPaymentService.orderWXQRCodePay(dto, request);
			break;
		case ALI_PAY:
			result = serOrderPaymentService.orderAlipayQRCodePay(dto);
//			result = serOrderPaymentService.orderAlipayPagePay(dto);
			isQRcode = true;
			break;
		case BALANCE://自身平台余额支付， 根据自身平台来进行操作
			result = serOrderPaymentService.orderBalanceByPay(dto);
			isQRcode = false;
			break;
		}
		
		if (isQRcode) {
		    try {
		    	result = PayUtil.getBase64QRCode(result.toString());
		    } catch (Exception e) {
				log.info("生成二维码失败！");
				e.printStackTrace();
				throw new RuntimeException("生成二维码失败！");
//				throw new BusinessException(PaymentBusinessErrorEnum.GET_QR_CODE_BASE64_ERROR);
			}
		}
		
		return result;
//		return ReqResUtil.getResponseBean(result);
	}
	
	@ApiOperation("支付宝网页支付")
	@RequestMapping(value="/forPagePay", method = RequestMethod.GET)
	public void forPagePay(OrderPayInfoDto dto, HttpServletResponse response, HttpServletRequest request) {
		String result = "";
			
	        try {
//	        	response.getWriter().write(result.toString());
////	        	response.sendRedirect(request.getContextPath()+"/jsp/result.jsp");
//				response.flushBuffer();
	        	dto.setPayType(ALI_PAY);
	        	result = serOrderPaymentService.orderAlipayPagePay(dto);
	        	response.setContentType("text/html;charset=" + "UTF-8");
	            response.getWriter().write(result);
	            response.getWriter().flush();
	            response.getWriter().close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.info(e.getMessage() + e);
			}
		
	}
	
	/**
	 * 查询订单（值适合第三方支付）
	 * @author zj
	 * @param dto
	 * @param response
	 * @param request
	 * @return
	 * 创建时间：2019年7月12日 下午5:43:27
	 */
	@ApiOperation("获取订单第三方支付详情详情")
	@RequestMapping(value="/getPaymentOrderInfo", method = RequestMethod.POST)
	public Object getPaymentOrderInfo(@Valid @RequestBody OrderPayInfoDto dto, HttpServletResponse response, HttpServletRequest request) {
		Object result = "";
		switch (dto.getPayType()) {
		case WX_PAY:
			result = serOrderPaymentService.wxTradeQuery(dto);
			break;
		case ALI_PAY:
			result = serOrderPaymentService.alipayTradeQuery(dto);
			break;
		}
		
		return result;
//		return ReqResUtil.getResponseBean(result);
	}
	
	/**
	 * 第三方退款功能
	 * @author zj
	 * @param dto
	 * @param response
	 * @param request
	 * @return
	 * 创建时间：2019年7月12日 下午5:45:23
	 */
	@ApiOperation("订单第三方支付退款")
	@RequestMapping(value="/orderRefund", method = RequestMethod.POST)
	public Object orderRefund(@Valid @RequestBody OrderPayInfoDto dto, HttpServletResponse response, HttpServletRequest request) {
		Object result = "";
		switch (dto.getPayType()) {
		case WX_PAY:
			result = serOrderPaymentService.wxTradeRefund(request, dto);
			break;
		case ALI_PAY:
			result = serOrderPaymentService.alipayTradeRefund(dto);
			break;
		}
		
		return result;
//		return ReqResUtil.getResponseBean(result);
	}
	

	
	
	
	/**
	 * 支付宝网页支付
	 * @author zj
	 * @param dto
	 * @return
	 * 创建时间：2019年7月2日 上午11:23:56
	 */
//	@ApiOperation("支付宝网页支付")
//	@RequestMapping(value="/alipay/forPagePay", method = RequestMethod.POST)
//	public ResponseBean<String> alipayForPagePay(@Valid @RequestBody OrderPayInfoDto dto) {
//		OrderPayInfoForPayDto  aa = new OrderPayInfoForPayDto();
//		String qrCodeUrl = paymentService.alipayForPagePay(aa);
//		return ReqResUtil.getResponseBean(qrCodeUrl);
//	}
//	
//	/**
//	 * 支付宝当面付-获取二维码地址
//	 * @author zj
//	 * @param dto
//	 * @return
//	 * 创建时间：2019年7月3日 上午9:40:24
//	 */
//	@ApiOperation("支付宝当面付-获取二维码地址")
//	@RequestMapping(value="/alipay/qrCodePay", method = RequestMethod.POST)
//	public ResponseBean<String> alipayQRCodePay(@Valid @RequestBody OrderPayInfoDto dto) {
//		String qrCodeUrl = alipayPaymentService.alipayQRCodePay(dto);
//		return ReqResUtil.getResponseBean(qrCodeUrl);
//	}
	
	
	/**
	 * 支付宝支付-异步通知(此接口只有在公网环境下才能被通知到)
	 * @author zj
	 * @param dto
	 * @return
	 * 创建时间：2019年7月2日 下午2:52:44
	 */
	@ApiOperation("支付宝支付-异步通知")
	@RequestMapping(value="/alipay/syncNotify", method = RequestMethod.POST)
	public void alipaySyncNotify(HttpServletRequest request, HttpServletResponse response) {
		
		alipayPaymentService.alipaySyncNotify(request, response);
	}
	
	/**
	 * 微信支付-异步通知(此接口只有在公网环境下才能被通知到)
	 * @author zj
	 * @param request
	 * @param response
	 * 创建时间：2019年7月5日 下午3:59:41
	 */
	@ApiOperation("微信支付-异步通知")
	@RequestMapping(value="/wx/syncNotify", method = RequestMethod.POST)
	public void wxSyncNotify(HttpServletRequest request, HttpServletResponse response) {
		
		wxPaymentService.wxSyncNotify(request, response);
	}
	
	/**
	 * 获取支付状态 这个就是获取redis 里面的支付状态 
	 * @author zj
	 * @param dto
	 * @return
	 * 创建时间：2019年7月22日 上午11:32:59
	 */
//	@ApiOperation("获取支付状态")
//	@RequestMapping(value="/get/payStatus", method = RequestMethod.POST)
//	public ResponseBean<Boolean> getPayStatus(@Valid @RequestBody OrderNoDto dto) {
//		Boolean result = serOrderPaymentService.getPayStatus(dto);
////		redisTemplateService.stringSetValueAndExpireTime(CacheKeyConstant.OM_PAYMENT_ORDER_PAY_STATUS + "123457", "true", 60L);
////		Object obj = redisTemplateService.stringGetStringByKey(CacheKeyConstant.OM_PAYMENT_ORDER_PAY_STATUS + "123457");
//		return ReqResUtil.getResponseBean(result);
//	}
	
	
	
	/******以下两接口为测试二维码聚合支付， 具体可忽略*********/
	
	@ApiOperation("生成一个写好的页面【存在判断客户端的页面】二维码")
	@RequestMapping(value="/forPayStr")
	public Object forPayStr(HttpServletResponse response, HttpServletRequest request) {
		Object result = "";
	    try {
	    	result = PayUtil.getBase64QRCode("http://192.168.1.66:7002/lz/om/index.html");
	    } catch (Exception e) {
			log.info("生成二维码失败！");
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * @author zj
	 * @param response
	 * @param request
	 * 创建时间：2020年3月16日 下午2:41:41
	 */
	@RequestMapping(value="/forPayPay")
	public void forPayPay(HttpServletResponse response, HttpServletRequest request) {
		String UrlStr = "QRCode/";
	    String userAgent = request.getHeader("user-agent");
	    String RequestURL = request.getRequestURL().toString();
	    String ReqInfo = RequestURL.substring(RequestURL.indexOf(UrlStr)+UrlStr.length());
	}
	

	
}
