package com.zzw.learning.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zzw.learning.constant.CommonConstants;
import com.zzw.learning.entity.Teacher;
import com.zzw.learning.excel.TeacherDataListener;
import com.zzw.learning.mapper.TeacherMapper;
import com.zzw.learning.param.TeacherParam;
import com.zzw.learning.service.IRoleService;
import com.zzw.learning.service.ITeacherService;
import com.zzw.learning.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 教师信息管理 服务实现类
 * </p>
 *
 * @author zzw
 * @since 2020-03-31
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private ITeacherService teacherService;
    @Autowired
    private IRoleService roleService;


    @Override
    @Transactional
    public boolean importTeacher(MultipartFile file) {
        if (file.isEmpty()){
            return false;
        }
        // excel上传
        if (FileUtil.isExcel(Objects.requireNonNull(file.getOriginalFilename()))) {
            try {
                EasyExcel.read(file.getInputStream(), TeacherParam.class, new TeacherDataListener(null,null)).sheet().doRead();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }else if(FileUtil.isZip(Objects.requireNonNull(file.getOriginalFilename()))){
            // 压缩包上传
            List<ITeacherService.FileData> fileDataList = getFileList(file);
            // 头像urlMap
            Map<String,String> urlMap = new HashMap<>();
            List<String> avatarList = new ArrayList<>();

//            for (ITeacherService.FileData f:fileDataList) {
//                MultipartFile multipartFile = new CommonsMultipartFile(FileUtil.createFileItem(f.getFile().getPath(),f.getFileName()));
//                if (!ObjectUtils.isEmpty(multipartFile)){
//                    // 判断是否是excel
//                    if (!FileUtil.isExcel(f.getFileName())) {
//                        //EasyExcel.read(f.getInputStream(), TeacherParam.class, new TeacherDataListener(urlMap)).sheet().doRead();
//                        DCResponse dcResponse = baseServiceApi.upload(multipartFile, 0);
//                        urlMap.put(f.getFileName(), dcResponse.getData().toString());
//                        avatarList.add(f.getFileName());
//                    }
//                }
//            }
            for (ITeacherService.FileData f:fileDataList) {
                MultipartFile multipartFile = new CommonsMultipartFile(FileUtil.createFileItem(f.getFile().getPath(),f.getFileName()));
                if (!ObjectUtils.isEmpty(multipartFile)){
                    // 判断是否是excel
                    if (FileUtil.isExcel(f.getFileName())) {
                        EasyExcel.read(f.getInputStream(), TeacherParam.class, new TeacherDataListener(urlMap,avatarList)).sheet().doRead();
                    }
                }
            }
            return true;
        }else {
            return false;
        }
    }

    public List<ITeacherService.FileData> getFileList(MultipartFile file){
        // 修改源文件名称，防止重名
        String originName = file.getOriginalFilename();
        String fileName = FileUtil.getFileName(Objects.requireNonNull(file.getOriginalFilename())) + "_" + System.currentTimeMillis() + FileUtil.getSuffix(file.getOriginalFilename());
        // 临时文件目录 ，到时候放在配置文件进行获取、
        String folder = System.getProperty("java.io.tmpdir");
        String filePath = folder + "/file/";
        File dir = new File(filePath);
        List<ITeacherService.FileData> fileDataList = Lists.newArrayList();
        if (!dir.exists()) {
            dir.mkdirs(); //创建目录
        }
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            if (CommonConstants.ZIP_CONTEXT_TYPES.contains(file.getContentType())) {
                // zip 解压缩
                fileDataList = FileUtil.unZip(dest);
            } else if (CommonConstants.RAR_CONTEXT_TYPE.equals(file.getContentType())) {
                // RAR 解压缩 再上传（RAR解压缩只支持5.0以下的版本，因为5.0以上的解压缩算法不开源
                fileDataList = FileUtil.unRar(dest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileDataList;
    }
}
