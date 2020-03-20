package com.zj.modules.payment.entity;

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
 * 工程公司付款设置表
 * </p>
 *
 * @author system
 * @since 2019-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_serv_pay_settings")
public class ServPaySettings implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("company_id")
    private Long companyId;

    /**
     * 支付终端类型：0不区分终端类型 1 PC 2 APP 3 小程序  4 WAP支付
     */
    @TableField("termianl_type")
    private Integer termianlType;

    /**
     * 支付方式编码
     */
    @TableField("pay_code")
    private String payCode;

    /**
     * 支付方式名称
     */
    @TableField("pay_name")
    private String payName;

    /**
     * 付款方式描述
     */
    @TableField("pay_desc")
    private String payDesc;

    /**
     * 付款配置，json序列化存储，不同支付方式序列化的实体不同
     */
    @TableField("pay_config")
    private String payConfig;

    /**
     * 是否启用：0 不启用 1 启用
     */
    @TableField("is_enabled")
    private Integer isEnabled;

    /**
     * 排序：越小越靠前
     */
    @TableField("sort_order")
    private Integer sortOrder;

    /**
     * 支付回调地址
     */
    @TableField("callback_url")
    private String callbackUrl;

    /**
     * 支付方式logo地址
     */
    @TableField("logo_url")
    private String logoUrl;

    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    @TableField("updated_by")
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField("updated_time")
    private LocalDateTime updatedTime;


}
