package com.zzw.learning.controller;


import com.alibaba.excel.EasyExcel;
import com.google.code.kaptcha.Producer;
import com.zzw.learning.dto.VifyCodeDTO;
import com.zzw.learning.entity.Teacher;
import com.zzw.learning.excel.CustomSheetWriteHandler;
import com.zzw.learning.excel.TeacherDataListener;
import com.zzw.learning.param.TeacherExportParam;
import com.zzw.learning.response.DCResponse;
import com.zzw.learning.service.ITeacherService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

/**
 * <p>
 * 测试功能接口 前端控制器
 * </p>
 *
 * @author zzw
 * @since 2020-03-31
 */
@RestController
@RequestMapping("/{version}/template")
@Api(value = "/{version}/template", tags = {"测试功能接口"})
public class TeacherController {

    @Autowired
    private ITeacherService campusTeacherService;

    @Autowired
    private Producer producer;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 文件上传
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link Teacher}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link TeacherDataListener}
     * <p>
     * 3. 直接读即可
     */
    @ApiOperation(value = "批量上传", notes = "批量上传")
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
                .registerWriteHandler(new CustomSheetWriteHandler(TeacherExportParam.class.getDeclaredFields(), 1))
                .sheet("教职工档案")
                .doWrite(Collections.emptyList());
    }

    /**
     * 生成验证码
     */
    @ApiOperation(value = "生成验证码", notes = "生成验证码")
    @GetMapping("/getVifCode")
    public DCResponse<VifyCodeDTO> getVifCode(HttpServletRequest request, HttpServletResponse response) {
        VifyCodeDTO res = new VifyCodeDTO();
        HttpSession session = request.getSession();

        response.setDateHeader("Expires", 0);

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = producer.createText();
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(uuid, capText);
        System.out.println("UUID" + uuid + "验证码:" + capText);
        BufferedImage bi = producer.createImage(capText);
        ByteArrayOutputStream out = null;
        out = new ByteArrayOutputStream();
        // write the data out
        try {
            out = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", out);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(out.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
            res.setBase64Code(captchaBase64);
            res.setUuid(uuid);
            return DCResponse.success(res);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return DCResponse.error(500, "生成失败");
    }


}

