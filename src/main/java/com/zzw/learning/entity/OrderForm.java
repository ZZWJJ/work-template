package com.zzw.learning.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zzw
 * @since 2022-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderForm extends Model<OrderForm> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 订单状态（1：未支付，2：已支付）
     */
    @TableField("order_status")
    private Integer orderStatus;

    /**
     * 购买者姓名
     */
    @TableField("receiver_name")
    private String receiverName;

    /**
     * 购买者电话
     */
    @TableField("receiver_phone")
    private String receiverPhone;

    /**
     * 订单数量
     */
    @TableField("order_amount")
    private BigDecimal orderAmount;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
