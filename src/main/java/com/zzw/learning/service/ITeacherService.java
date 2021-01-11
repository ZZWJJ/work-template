package com.zzw.learning.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.learning.entity.Teacher;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 教师信息管理 服务类
 * </p>
 *
 * @author zzw
 * @since 2020-03-31
 */
public interface ITeacherService extends IService<Teacher> {
    /**
       * @Author zzw
       * @Description 批量导入教职工信息
       * @Date 2020/4/9 14:45
       * @param file
       * @return boolean
       **/
    boolean importTeacher(MultipartFile file);


    /**
     * 文件数据
     * @Author zzw
     * @Date 2019/12/12 13:16
     **/
    @Data
    public static class FileData{
        private String fileName;
        private InputStream inputStream;
        private File file;
        /**
         * 字节大小
         **/
        private long size;

        public FileData(String fileName, InputStream inputStream, long size) {
            this.fileName = fileName;
            this.inputStream = inputStream;
            this.size = size;
        }

        public FileData(File file) {
            this.fileName = file.getName();
            this.size = file.length();
            this.file = file ;
            try {
                this.inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
