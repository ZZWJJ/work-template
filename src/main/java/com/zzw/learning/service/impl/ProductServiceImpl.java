package com.zzw.learning.service.impl;

import com.zzw.learning.entity.Product;
import com.zzw.learning.mapper.ProductMapper;
import com.zzw.learning.service.IProductService;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
