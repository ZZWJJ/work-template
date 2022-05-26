package com.zzw.learning.mapper;

import com.zzw.learning.entity.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzw
 * @since 2022-05-26
 */
@Mapper
@Repository
public interface OrderItemMapper extends BaseMapper<OrderItem> {

}
