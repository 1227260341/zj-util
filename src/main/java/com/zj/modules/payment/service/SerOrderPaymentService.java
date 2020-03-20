package com.zj.modules.payment.service;


import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.google.zxing.WriterException;
import com.zj.modules.payment.dto.OrderPayInfoDto;
import com.zj.modules.payment.dto.OrderPayInfoForPayDto;
import com.zj.modules.payment.entity.ServOrder;
import com.zj.modules.payment.entity.ServPaySettings;

import ch.qos.logback.classic.Logger;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
@Slf4j
public class SerOrderPaymentService {

	@Resource
	private AlipayPaymentService alipayPaymentService;
	@Resource
	private WXPaymentService wxPaymentService;
	
	/**
	 * 支付宝网页支付-获取二维码地址
	 * @author zj
	 * @return
	 * 创建时间：2019年7月2日 上午11:40:55
	 */
	public String orderAlipayQRCodePay(OrderPayInfoDto dto) {
		
		//验证订单信息 //根据自身系统进行验证
		ServOrder servOrder = verifyServOrder(dto.getOrderNO());
//		ServOrder servOrder = null;//获取到对应的 订单详细，并进行验证
		
		//验证支付方式
		ServPaySettings servPaySettings = getServPaySettings(servOrder.getCompanyId(), new Integer[] {0, 1}, dto.getPayType());
		
		//存入支付需要相关信息数据
		OrderPayInfoForPayDto payDto = setOrderPayParams(dto, servOrder, servPaySettings.getPayConfig() );
		
		//获取支付二维码
		String qrcode = alipayPaymentService.alipayQRCodePay(payDto);
		
		return qrcode;
	}
	
	/**
	 * 支付宝网页支付
	 * @author zj
	 * @param dto
	 * @return
	 * 创建时间：2019年7月17日 下午4:25:28
	 */
	public String orderAlipayPagePay(OrderPayInfoDto dto) {
		
		//验证订单信息
		ServOrder servOrder = verifyServOrder(dto.getOrderNO());
		
		//验证支付方式
		ServPaySettings servPaySettings = getServPaySettings(servOrder.getCompanyId(), new Integer[] {0, 1}, dto.getPayType());
		
		//存入支付需要相关信息数据
		OrderPayInfoForPayDto payDto = setOrderPayParams(dto, servOrder, servPaySettings.getPayConfig() );
		
		//获取支付信息
		String result = alipayPaymentService.alipayForPagePay(payDto);
		
		return result;
	}
	
	
	/**
	 * 微信二维码支付
	 * @author zj
	 * @param dto
	 * @return
	 * 创建时间：2019年7月5日 下午2:54:31
	 */
	public String orderWXQRCodePay(OrderPayInfoDto dto, HttpServletRequest request) {
		
		//验证订单信息
		ServOrder servOrder = verifyServOrder(dto.getOrderNO());
		
		//验证支付方式
		ServPaySettings servPaySettings = getServPaySettings(servOrder.getCompanyId(), new Integer[] {0, 1}, dto.getPayType());
		
		//存入支付需要相关信息数据
		OrderPayInfoForPayDto payDto = setOrderPayParams(dto, servOrder, servPaySettings.getPayConfig());
		
		//获取支付二维码
		String qrcode = wxPaymentService.wxQRCodePay(payDto, request);
		
		return qrcode;
	}
	
	/**
	 * 商务经理余额支付
	 * @author zj
	 * @param dto
	 * @return
	 * 创建时间：2019年7月8日 下午12:40:17
	 */
	public BigDecimal orderBalanceByPay(OrderPayInfoDto dto) {
		
		// 平台对应的余额支付
		BigDecimal canUseMoney = null;
		return canUseMoney;
	}
	
	
	
	/**
	 * 支付宝交易查询
	 * @author zj
	 * @param dto
	 * @param request
	 * @return
	 * 创建时间：2019年7月12日 下午5:28:17
	 */
//	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, String> alipayTradeQuery(OrderPayInfoDto dto) {
		
		Map<String, String> result = new HashMap<>();
		try {
//			ServOrder servOrder = servOrderService.getServOrderByNoEtc(null, dto.getOrderNO());
			ServOrder servOrder = null;
			//验证支付方式
			ServPaySettings servPaySettings = getServPaySettings(servOrder.getCompanyId(), new Integer[] {0, 1}, dto.getPayType());
			
			//存入支付需要相关信息数据
			OrderPayInfoForPayDto payDto = setOrderPayParams(dto, servOrder, servPaySettings.getPayConfig());
			
			result = alipayPaymentService.alipayTradeQuery(payDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("code", "500");
			result.put("message", "焯去支付信息异常！");
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return result;
	}
	
	/**
	 * 支付宝退款
	 * @author zj
	 * @param dto
	 * @param request
	 * @return
	 * 创建时间：2019年7月12日 下午5:34:12
	 */
	public Map<String, String> alipayTradeRefund(OrderPayInfoDto dto) {
		
//		ServOrder servOrder = servOrderService.getServOrderByNoEtc(null, dto.getOrderNO());
		ServOrder servOrder = null;
		//验证支付方式
		ServPaySettings servPaySettings = getServPaySettings(servOrder.getCompanyId(), new Integer[] {0, 1}, dto.getPayType());
		
		//存入支付需要相关信息数据
		OrderPayInfoForPayDto payDto = setOrderPayParams(dto, servOrder, servPaySettings.getPayConfig());
		
		//获取支付二维码
		Map<String, String> result = alipayPaymentService.alipayTradeRefund(payDto);
		
		return result;
	}
	
	/**
	 * 位置查询订单
	 * @author zj
	 * @param dto
	 * @return
	 * 创建时间：2019年7月12日 下午5:38:33
	 */
	public Map<String, String> wxTradeQuery(OrderPayInfoDto dto) {
		
		//获取支付二维码
		Map<String, String> result = new HashMap<>();
		try {
//			ServOrder servOrder = servOrderService.getServOrderByNoEtc(null, dto.getOrderNO());
			ServOrder servOrder = null;
			//验证支付方式
			ServPaySettings servPaySettings = getServPaySettings(servOrder.getCompanyId(), new Integer[] {0, 1}, dto.getPayType());
			
			//存入支付需要相关信息数据
			OrderPayInfoForPayDto payDto = setOrderPayParams(dto, servOrder, servPaySettings.getPayConfig());
			
			result = wxPaymentService.wxTradeQuery(payDto);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "500");
			result.put("message", "焯去支付信息异常！");
		}
		
		return result;
	}
	
	/**
	 * 微信退款
	 * @author zj
	 * @param dto
	 * @param request
	 * @return
	 * 创建时间：2019年7月12日 下午5:34:12
	 */
	public Map<String, String> wxTradeRefund(HttpServletRequest request, OrderPayInfoDto dto) {
		
//		ServOrder servOrder = servOrderService.getServOrderByNoEtc(null, dto.getOrderNO());
		ServOrder servOrder = null;
		//验证支付方式
		ServPaySettings servPaySettings = getServPaySettings(servOrder.getCompanyId(), new Integer[] {0, 1}, dto.getPayType());
		
		//存入支付需要相关信息数据
		OrderPayInfoForPayDto payDto = setOrderPayParams(dto, servOrder, servPaySettings.getPayConfig());
		
		//获取支付二维码
		Map<String, String> result = wxPaymentService.wxTradeRefund(request, payDto);
		
		return result;
	}
	
	/**
	 * 订单状态查询
	 * @author zj
	 * @param dto
	 * @return
	 * 创建时间：2019年7月12日 下午6:53:31
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, String> findTradeStatus(OrderPayInfoDto dto) {
		
		dto.setPayType("WX_PAY");
		Map<String, String> result = wxTradeQuery(dto);
		result.put("payType", "WX_PAY");
		if (result == null || !"200".equals(result.get("code"))) {//说明未获取到
			dto.setPayType("ALI_PAY");
			result = alipayTradeQuery(dto);
			result.put("payType", "ALI_PAY");
		}
		
		return result;
	}
	
	/**
	 * 
	 * @author zj
	 * @param dto
	 * @return
	 * 创建时间：2019年7月24日 下午1:49:48
	 */
	public Map<String, String> findTradeStatusAndToCompelete(OrderPayInfoDto dto) {
		
		Map<String, String> orderSuccessMap = findTradeStatus(dto);
        if ("200".equals(orderSuccessMap.get("code"))) {
            //更新订单状态， 及完成订单
            String tradeNo = orderSuccessMap.get("tradeNo");
            String payType = orderSuccessMap.get("payType");
            BigDecimal totalAmount = new BigDecimal(orderSuccessMap.get("totalAmount"));
            log.debug("-----------------------------------------------------------");
            log.debug("tradeNo = " + tradeNo + ", payType = " + payType + ", totalAmount = " + totalAmount);
            log.debug("-----------------------------------------------------------");
            orderComplete(dto.getOrderNO(), tradeNo, 1, totalAmount, payType, "服务订购");
        }
		
		return orderSuccessMap;
	}
	
	
	
	
	
	
	
	
	/**
	 * 装入支付相关参数
	 * @author zj
	 * @param dto
	 * @param servOrder
	 * @return
	 * 创建时间：2019年7月5日 下午3:26:19
	 */
	public OrderPayInfoForPayDto setOrderPayParams(OrderPayInfoDto dto, ServOrder servOrder, String payConfig) {
		
//		//存入支付需要相关信息数据
//		String payConfig = servPaySettings.getPayConfig();
		
		JSONObject payConfigObj = JSONObject.fromObject(payConfig);
//				OrderPayInfoForPayDto payDto = (OrderPayInfoForPayDto) JSONObject.toBean(payConfigObj, OrderPayInfoForPayDto.class);
		OrderPayInfoForPayDto payDto = new OrderPayInfoForPayDto();
		payDto.setOrderId(servOrder.getId());
		if (!payConfigObj.containsKey("appId")) {
			throw new RuntimeException("未查找到对应的支付设置！");
		}
		payDto.setAppId(payConfigObj.getString("appId"));
		payDto.setOrderNO(servOrder.getOrderSn());
		payDto.setOrderName(dto.getOrderName());
		payDto.setRemark(dto.getRemark());
		payDto.setTotalAmount(servOrder.getTotalAmount());
		if (dto.getRequestType() == 2) {//说明是退款
			payDto.setShouldReturn(dto.getShouldReturn());
		}
		
//		支付类型：WX_PAY, ALI_PAY, BALANCE_PAY, OFF_LINE
		if ("ALI_PAY".equalsIgnoreCase(dto.getPayType())) {
			payDto.setMerchantPrivateKey(payConfigObj.getString("appPrivateKey"));
			payDto.setAlipayPublicKey(payConfigObj.getString("alipayPublicKey"));
		} else {
			payDto.setWxMchId(payConfigObj.getString("wxBusinessNo"));
			payDto.setWxKey(payConfigObj.getString("apiSecretKey"));
			if (payConfigObj.containsKey("certPath")) {
				payDto.setWxCertPath(payConfigObj.getString("certPath"));
			}
		}
		
		return payDto;
	}
	
	/**
	 * 验证订单信息
	 * @author zj
	 * @return
	 * 创建时间：2019年7月5日 下午2:56:58
	 */
	public ServOrder verifyServOrder(String orderNO) {
		//验证订单信息
		//验证订单状态
		//验证订单的库存是否充足
		//获取订单的明细根据订单id
		
		//验证订单金额
		ServOrder servOrder = null;
		return servOrder;
	}
	
	/**
	 * 验证订单信息-返回错误码
	 * @author zj
	 * @param orderNO
	 * @return
	 * 创建时间：2019年7月11日 下午5:50:55
	 */
	public Map<String, String> verifyServOrderReturnMessage(String orderNO, Integer payStatus) {
		Map<String, String> returnMap = new HashMap<String, String>();
		//具体写自己的业务逻辑
		
		returnMap.put("code", "200");
		returnMap.put("message", "获取信息成功！");
		return returnMap;
	}
	
	/**
	 * 获取支付信息
	 * @author zj
	 * termianlType 支付终端类型：0 所有平台 ，1 PC 2 APP 3 小程序  4 WAP支付
	 * @return
	 * 创建时间：2019年7月5日 下午2:37:56
	 */
	public ServPaySettings getServPaySettings(Long companyId, Integer[] termianlType, String payCode) {
		//获取支付信息  具体根据自身业务来进行获取
		ServPaySettings servPaySettings = null;
		return servPaySettings;
	}
	
	
	/**
	 * 支付成功后
	 * 
	 * @author zj
	 * @param outTradeNo 商家订单号
	 * @param tradeNo 第三方 交易号
	 * @param tradeStatus 交易状态 0 失败【解析失败等】， 1 成功
	 * @param totalAmount 支付的总金额
	 * @param remark 备注信息
	 * @param payType //支付类型：WX_PAY, ALI_PAY, BALANCE_PAY, OFF_LINE
	 * 创建时间：2019年7月4日 下午3:05:18
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void orderComplete(String outTradeNo, String tradeNo, Integer tradeStatus, BigDecimal totalAmount, String payType, String remark) {
		
		//支付成功后的具体操作

		
		
		//将订单加入到缓存以便前端扫码同步操作调用
//		redisTemplateService.stringSetValueAndExpireTime(CacheKeyConstant.OM_PAYMENT_ORDER_PAY_STATUS + outTradeNo, "true", 360L);
	}
	
	
	/**
	 * 获取支付状态  此处的主要是为了 扫码支付的 同步通知， 及 支付完成后，收到异步通知的时候，则往redis中写入对应的数据， 前端用定时器不断的 去获取这个值， 获取到了 说明支付成功， 那么则跳转对应的界面
	 * @author zj
	 * @return
	 * 创建时间：2019年7月22日 上午11:28:26
	 */
//	public Boolean getPayStatus(OrderNoDto dto) {
////		redisTemplateService.stringSetValueAndExpireTime(CacheKeyConstant.OM_PAYMENT_ORDER_PAY_STATUS + "123457", "true", 60L);
//		Object obj = redisTemplateService.stringGetStringByKey(CacheKeyConstant.OM_PAYMENT_ORDER_PAY_STATUS + dto.getOrderNO());
//		if (StringUtils.isEmpty(obj)) {
//			return false;
//		}
//		return true;
//	}
	
}
