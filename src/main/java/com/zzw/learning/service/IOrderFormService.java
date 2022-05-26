package com.zzw.learning.service;

import com.zzw.learning.entity.OrderForm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzw
 * @since 2022-05-26
 */
public interface IOrderFormService extends IService<OrderForm> {


    void order(String id);


}
