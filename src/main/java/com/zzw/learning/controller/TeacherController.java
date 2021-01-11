package com.zzw.learning.controller;


import com.alibaba.excel.EasyExcel;
import com.zzw.learning.entity.Teacher;
import com.zzw.learning.excel.CustomSheetWriteHandler;
import com.zzw.learning.excel.TeacherDataListener;
import com.zzw.learning.param.TeacherExportParam;
import com.zzw.learning.response.DCResponse;
import com.zzw.learning.service.ITeacherService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * <p>
 * 教师信息管理 前端控制器
 * </p>
 *
 * @author zzw
 * @since 2020-03-31
 */
@RestController
@RequestMapping("/{version}/teacher")
@Api(value = "/{version}/teacher", tags = {"教师信息管理接口"})
public class TeacherController {

    @Autowired
    private ITeacherService campusTeacherService;

    /**
     * 文件上传
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link Teacher}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link TeacherDataListener}
     * <p>
     * 3. 直接读即可
     */
    @ApiOperation(value = "教职工档案批量上传", notes = "教职工档案批量上传")
    @PostMapping(value = "/import")
    public DCResponse<String> upload(@RequestParam MultipartFile file) {
        campusTeacherService.importTeacher(file);
        return DCResponse.success("导入成功！");
    }

    @ApiOperation("下载导出模板")
    @GetMapping("exportTemplate")
    public void exportTemplate(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=teacher.xlsx");

        EasyExcel.write(response.getOutputStream(), TeacherExportParam.class)
                .autoCloseStream(Boolean.FALSE)
                .registerWriteHandler(new CustomSheetWriteHandler(TeacherExportParam.class.getDeclaredFields(),1))
                .sheet("教职工档案")
                .doWrite(Collections.emptyList());
    }

}

