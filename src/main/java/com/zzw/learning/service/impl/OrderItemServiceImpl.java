package com.zzw.learning.service.impl;

import com.zzw.learning.entity.OrderItem;
import com.zzw.learning.mapper.OrderItemMapper;
import com.zzw.learning.service.IOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzw
 * @since 2022-05-26
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

}
