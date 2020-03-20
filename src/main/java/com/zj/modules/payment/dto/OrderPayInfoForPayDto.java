package com.zj.modules.payment.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@ApiModel("OrderPayInfoForPayDto")
public class OrderPayInfoForPayDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商户订单id")
    private Long orderId;
    
	@ApiModelProperty("商户订单号，商户网站订单系统中唯一订单号，必填")
	@NotBlank(message = "商户订单号不能为空")
    private String orderNO;
	
	@ApiModelProperty("支付宝交易号")
    private String tradeNo;
	
	@ApiModelProperty("订单名称，必填")
	@NotBlank(message = "订单名称 不能为空")
    private String orderName;
	
	@ApiModelProperty("付款金额 必填")
	@NotNull(message = "付款金额不能为空")
    private BigDecimal totalAmount;
	
	@ApiModelProperty("应退金额 ")
    private BigDecimal shouldReturn;
	
	@ApiModelProperty("标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传")
	private String outRequestNo ;
	
	@ApiModelProperty("备注")
    private String remark;
	
	/******************以下支付支付宝相关信息************************/
	
	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	@ApiModelProperty("商家appId(微信支付宝公用)")
	@NotNull(message = "商家appId不能为空")
    private String appId;
	
//	@ApiModelProperty("商户UID")
//	@NotNull(message = "商户UID不能为空")
//    private String sellerId;
	
	// 商户私钥，您的PKCS8格式RSA2私钥
	@ApiModelProperty("商户私钥")
//	@NotNull(message = "商户私钥不能为空")
    private String merchantPrivateKey;
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	@ApiModelProperty("支付宝公钥")
//	@NotNull(message = "支付宝公钥不能为空")
    private String alipayPublicKey;
	
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	@ApiModelProperty("服务器异步通知")
//	@NotNull(message = "商家appId不能为空")
//    private String notify_url;
	
	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	@ApiModelProperty("商家appId")
//	@NotNull(message = "商家appId不能为空")
//    private String return_url;
	
	/******************以下支付微信相关信息************************/

	@ApiModelProperty("商户id")
    private String wxMchId;
	
	@ApiModelProperty("商户api key")
    private String wxKey;
	
	@ApiModelProperty("商户证书位置")
    private String wxCertPath;
    

}
