package com.zzw.learning.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.zzw.learning.constant.DeletedState;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author : zzw
 * @Description: 自动填充
 * @date : 2020/9/23 9:18
 **/
@Component
public class BaseMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("create_time", LocalDateTime.now(), metaObject);
        this.setInsertFieldValByName("update_time", LocalDateTime.now(), metaObject);
        this.setInsertFieldValByName("del_flag", DeletedState.DELTET_N, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setInsertFieldValByName("create_time", LocalDateTime.now(), metaObject);
        this.setInsertFieldValByName("update_time", DeletedState.DELTET_N, metaObject);
    }
}
