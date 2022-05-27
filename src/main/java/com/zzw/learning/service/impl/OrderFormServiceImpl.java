package com.zzw.learning.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.zzw.learning.entity.OrderForm;
import com.zzw.learning.entity.OrderItem;
import com.zzw.learning.entity.Product;
import com.zzw.learning.exception.ServiceException;
import com.zzw.learning.mapper.OrderFormMapper;
import com.zzw.learning.service.IOrderItemService;
import com.zzw.learning.service.IOrderFormService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.learning.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzw
 * @since 2022-05-26
 */
@Service
public class OrderFormServiceImpl extends ServiceImpl<OrderFormMapper, OrderForm> implements IOrderFormService {

    @Autowired
    private IProductService productService;
    @Autowired
    private IOrderItemService orderItemService;
    @Qualifier("transactionManager")
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;

    private int purchaseProductNum = 1;

    @Override
//    @Transactional
    public void order(String id) {
        Product product = null;
        synchronized (this) {
            product = productService.getById(id);
            if (ObjectUtil.isEmpty(product)) {
                throw new ServiceException(1111, "未找到该商品");
            }

            TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
            int currentAmount = product.getAmount();

            if (purchaseProductNum > currentAmount) {
                platformTransactionManager.rollback(transactionStatus);
                throw new ServiceException(1111, "库存不满足");
            }

            product.setAmount(currentAmount - purchaseProductNum);
            productService.updateById(product);
            platformTransactionManager.commit(transactionStatus);
        }

        TransactionStatus transactionStatus1 = platformTransactionManager.getTransaction(transactionDefinition);
        // 插入订单信息
        String orderId = IdWorker.getIdStr();
        save(OrderForm.builder()
                .id(orderId)
                .orderStatus(1)
                .orderAmount(new BigDecimal(purchaseProductNum))
                .receiverName("zzw").receiverPhone("1111")
                .build());

        orderItemService.save(OrderItem.builder()
                .orderId(orderId)
                .num(purchaseProductNum)
                .productId(id)
                .price(new BigDecimal("55"))
                .build());
        platformTransactionManager.commit(transactionStatus1);
        System.out.println("订单id为：" + orderId);
    }
}
