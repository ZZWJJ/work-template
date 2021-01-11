package com.zzw.learning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzw.learning.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * 教师信息管理 Mapper 接口
 * </p>
 *
 * @author zzw
 * @since 2020-03-31
 */
@Mapper
@Repository
public interface TeacherMapper extends BaseMapper<Teacher> {

}
