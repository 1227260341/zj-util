package com.zj.modules.payment.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 服务订单表
 * </p>
 *
 * @author system
 * @since 2019-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_serv_order")
public class ServOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    @TableId(value="id",type=IdType.ID_WORKER)
    private Long id;

    /**
     * 订单编号
     */
    @TableField("order_sn")
    private String orderSn;

    /**
     * 工程公司ID
     */
    @TableField("company_id")
    private Long companyId;

    /**
     * 订单总额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 订单支付金额（实际发生金额）
     */
    @TableField("money_paid")
    private BigDecimal moneyPaid;

    /**
     * 订单状态：0 待确认 1 已确认 2 取消 3 无效
     */
    @TableField("order_status")
    private Integer orderStatus;

    /**
     * 支付状态：0 待支付 1 已支付 2 已申请退款 3 已退款
     */
    @TableField("pay_status")
    private Integer payStatus;

    /**
     * 支付方式：WX_PAY, ALI_PAY,BALANCE
     */
    @TableField("pay_name")
    private String payName;

    /**
     * 是否可以付款：0 不可以 1 可以
     */
    @TableField("can_pay")
    private Integer canPay;

    /**
     * 保证金
     */
    @TableField("insure_fee")
    private BigDecimal insureFee;

    /**
     * 订单添加时间
     */
    @TableField("add_time")
    private LocalDateTime addTime;

    /**
     * 支付时间
     */
    @TableField("pay_time")
    private LocalDateTime payTime;

    /**
     * 订单确认时间
     */
    @TableField("confirm_time")
    private LocalDateTime confirmTime;

    /**
     * 订单登记状态： 0 待登记 1 已登记
     */
    @TableField("register_status")
    private Integer registerStatus;

    /**
     * 发货时间（服务登记）
     */
    @TableField("shipping_time")
    private LocalDateTime shippingTime;

    /**
     * 是否需要邮寄：0 不需要 1 需要
     */
    @TableField("is_need_shipping")
    private Integer isNeedShipping;

    /**
     * 邮寄费
     */
    @TableField("shipping_fee")
    private BigDecimal shippingFee;

    /**
     * 快递单号
     */
    @TableField("express_no")
    private String expressNo;

    /**
     * 快递公司编码（以后对接快递100使用）
     */
    @TableField("shipping_code")
    private String shippingCode;

    /**
     * 快递公司名称
     */
    @TableField("shipping_name")
    private String shippingName;

    /**
     * 收货人姓名
     */
    @TableField("consignee")
    private String consignee;

    /**
     * 收货人手机号
     */
    @TableField("consignee_mobile")
    private String consigneeMobile;

    /**
     * 省份ID
     */
    @TableField("province_id")
    private Integer provinceId;

    /**
     * 城市ID
     */
    @TableField("city_id")
    private Integer cityId;

    /**
     * 区县ID
     */
    @TableField("district_id")
    private Integer districtId;

    /**
     * 收货地址
     */
    @TableField("addr_detail")
    private String addrDetail;

    /**
     * 买方备注：给工程公司的信息
     */
    @TableField("to_seller")
    private String toSeller;

    /**
     * 附件文件URL
     */
    @TableField("attachment_url")
    private String attachmentUrl;

    /**
     * 商务经理所在公司ID
     */
    @TableField("manager_company_id")
    private Long managerCompanyId;

    /**
     * 商务经理ID
     */
    @TableField("manager_id")
    private Long managerId;

    /**
     * 商务经理名称（真实名称）
     */
    @TableField("manager_name")
    private String managerName;

    /**
     * 操作人ID
     */
    @TableField("buyer_id")
    private Long buyerId;

    /**
     * 操作人名称（真实名称）
     */
    @TableField("buyer_name")
    private String buyerName;

    /**
     * 服务处理登记信息
     */
    @TableField("send_info")
    private String sendInfo;

    /**
     * 0待审核 1 审核中 2 不通过 3 通过
     */
    @TableField("audit_status")
    private Integer auditStatus;


    
}
