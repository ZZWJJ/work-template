package com.zzw.learning.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.google.common.collect.Lists;
import com.zzw.learning.service.ITeacherService;
import com.zzw.learning.support.validate.WarnInfo;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author : shenjue
 * @Description: TODO
 * @date : 2019/12/2 17:05
 **/
public class FileUtil {


    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

    public static String getPath() {
        return FileUtil.class.getResource("/").getPath();
    }

    public static File createNewFile(String pathName) {
        File file = new File(getPath() + pathName);
        if (file.exists()) {
            file.delete();
        } else {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        return file;
    }

    /**
     * 是否是表格类型
     *
     * @param fileName 文件名称
     * @return 判断结果
     */
    public static boolean isExcel(String fileName) {
        String[] suffixes = {"xls", "xlsx", "XLS", "XLSX"};
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return ArrayUtil.contains(suffixes, suffix);
    }

    /**
     * 是否是压缩包类型
     *
     * @param fileName 文件名称
     * @return 判断结果
     */
    public static boolean isZip(String fileName) {
        String[] suffixes = {"zip", "rar"};
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return ArrayUtil.contains(suffixes, suffix);
    }

    /**
     * 判断文件名是否一样
     *
     * @param fileName     上传的文件名称
     * @param descFileName 系统中设置的文件名称
     * @return 判断结果
     */
    public static boolean isSameFileName(String fileName, String descFileName) {
        if (StrUtil.isNotEmpty(fileName) && StrUtil.isNotEmpty(descFileName)) {
            return fileName.substring(0, fileName.lastIndexOf(".")).trim().equalsIgnoreCase(descFileName);
        }
        return false;
    }

    /**
     * 判断文件名是否一样
     *
     * @param fileName 上传的文件名称
     * @return 判断结果
     */
    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 判断文件名是否一样
     *
     * @param fileName 上传的文件名称
     * @return 判断结果
     */
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static void main(String[] args) {
        String fileName = "ddsdsd.xls";
        System.out.println(fileName.substring(fileName.lastIndexOf(".") + 1));

    }

    /**
     * 解压缩ZIP文件
     *
     * @param file 压缩文件
     */
    public static List<ITeacherService.FileData> unZip(File file) {
        ZipFile zipFile = new ZipFile(file);
        zipFile.setCharset(Charset.forName("GBK"));
        String folder = System.getProperty("java.io.tmpdir");
        String descZip = folder + "/file/extract/" + System.currentTimeMillis() + "/";
        File dir = new File(descZip);
        if (!dir.exists()) {
            dir.mkdirs(); //创建目录
        }
        try {
            zipFile.extractAll(descZip);
            List<FileHeader> headerList = zipFile.getFileHeaders();
            List<ITeacherService.FileData> fileList = Lists.newArrayList();
            for (FileHeader fileHeader : headerList) {
                File unZipFile = new File(descZip + fileHeader.getFileName());
                if (!unZipFile.isDirectory()) {
                    fileList.add(new ITeacherService.FileData(new File(descZip + fileHeader.getFileName())));
                }
            }
            return fileList;
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解压缩RAR文件
     *
     * @param file 压缩文件
     */
    public static List<ITeacherService.FileData> unRar(File file) {
        String folder = System.getProperty("java.io.tmpdir");
        String outDir = folder + "/file/extract/" + System.currentTimeMillis() + "//";
        File outFileDir = new File(outDir);
        if (!outFileDir.exists()) {
            boolean isMakDir = outFileDir.mkdirs();
            if (isMakDir) {
                System.out.println("创建压缩目录成功");
            }
        }
        Archive archive = null;
        try {
            List<ITeacherService.FileData> fileList = Lists.newArrayList();
            archive = new Archive(new FileInputStream(file));
            com.github.junrar.rarfile.FileHeader fileHeader = archive.nextFileHeader();
            while (fileHeader != null) {
                // 防止文件名中文乱码问题的处理  
                String fileName = fileHeader.getFileNameW().isEmpty() ? fileHeader
                        .getFileNameString() : fileHeader.getFileNameW();
                if (fileHeader.isDirectory()) { // 文件夹  
                    File fol = new File(outDir
                            + File.separator + fileName);
                    fol.mkdirs();
                } else { // 文件  
                    File out = new File(outDir
                            + File.separator + fileName.trim());
                    try {
                        if (!out.exists()) {
                            if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.  
                                out.getParentFile().mkdirs();
                            }
                            out.createNewFile();
                        }
                        fileList.add(new ITeacherService.FileData(out));
                        FileOutputStream os = new FileOutputStream(out);
                        archive.extractFile(fileHeader, os);
                        os.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                fileHeader = archive.nextFileHeader();
            }
            archive.close();
            return fileList;
        } catch (RarException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 压缩指定文件到当前文件夹
     * @param saveSrc 保存文件的目录
     * @param fileSrc 要压缩的指定文件
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static void zip(String saveSrc,String fileSrc) throws IOException{
        ZipFile zip=new ZipFile(saveSrc);
        File file=zip.getFile();
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //为了不被原有文件干扰,保证每次重新生成
        if (file.exists()) {
            file.delete();
        }
        ZipParameters para=new ZipParameters();
        //设置压缩级别，压缩方法默认
        para.setCompressionLevel(CompressionLevel.NORMAL);
        zip.addFolder(new File(fileSrc), para);
        System.out.println("压缩完成！");
    }

    public static File readFile(String pathName) {
        return new File(getPath() + pathName);
    }

    public static FileItem createFileItem(String filePath, String fileName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "file";
        int num = filePath.lastIndexOf(".");
        String extFile = filePath.substring(num);
        FileItem item = factory.createItem(textFieldName, "text/plain", true, fileName);
        File newfile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(newfile);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

    /** */
    /**
     * 创建文本文件.
     *
     * @throws IOException
     */
    public static String creatTxtFile(List<WarnInfo> warnInfos) throws IOException {
        String enter = "\r\n";
        // 临时文件目录 ，到时候放在配置文件进行获取、
        String folder = System.getProperty("java.io.tmpdir");
        //String folder = "D:/";
        String filePath = folder + "/text/";
        String fileName = System.currentTimeMillis() + "_message.txt";
        File dir = new File(filePath);

        if (!dir.exists()) {
            dir.mkdirs(); //创建目录
        }

        File f = new File(filePath + fileName);
        // 写入文件
        FileWriter fw = new FileWriter(filePath + fileName);
        for (WarnInfo warn : warnInfos) {
            fw.write("行数：" + warn.getRowNo() + ",列名：" + warn.getColName() + ",警告信息：" + warn.getErrorMessage() + enter);
        }

        fw.close();

//        MultipartFile multipartFile;
//        multipartFile = new CommonsMultipartFile(FileUtil.createFileItem(f.getPath(), f.getName()));
        return filePath + fileName;
    }

    public static void downProcessFile(HttpServletResponse response, String path) {
        try {
            File file = new File(path);
            String filename = file.getName();// 获取日志文件名称
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            os.write(buffer);// 输出文件
            os.flush();
            os.close();
        } catch (Exception e) {
        }
    }
}
