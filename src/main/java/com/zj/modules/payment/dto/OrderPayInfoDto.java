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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@ApiModel("OrderPayInfoDto")
public class OrderPayInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("支付方式：WX_PAY, ALI_PAY,BALANCE_PAY 必填")
	@NotBlank(message = "支付方式不能为空")
    private String payType;

	@ApiModelProperty("商户订单号，商户网站订单系统中唯一订单号，必填")
	@NotBlank(message = "商户订单号不能为空")
    private String orderNO;
	
	@ApiModelProperty("订单名称，必填(默认是  服务订购）")
	@NotBlank(message = "订单名称 不能为空")
    private String orderName = "服务订购";
	
	@ApiModelProperty("请求类型， 1 付款， 2 退款， 3 订单查询 。。。。(默认为1)")
	private Integer requestType = 1;
//	
//	@ApiModelProperty("付款金额 必填")
//	@NotNull(message = "付款金额不能为空")
//    private BigDecimal totalAmount;
	
	@ApiModelProperty("退款金额 退款时必填必填")
    private BigDecimal shouldReturn;
	
	@ApiModelProperty("备注")
    private String remark;
	
	
	
    

}
