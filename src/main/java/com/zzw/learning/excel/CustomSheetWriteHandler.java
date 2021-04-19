package com.zzw.learning.excel;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.google.common.collect.Lists;
import com.zzw.learning.annotate.ExplicitConstraint;
import com.zzw.learning.excel.DynamicExplicitInterface.ExplicitInterface;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author : zzw
 * @Description: 注解实现easyexcel的导出
 * @date : 2019/12/2 17:02
 **/
public class CustomSheetWriteHandler implements SheetWriteHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomSheetWriteHandler.class);

    //处理下拉列表
    //下拉列表集合
    private Map<Integer, String[]> explicitListConstraintMap = new HashMap<>();

    //循环获取对应列得下拉列表信息
    private Field[] declaredFields;
    // 首行数据行所在行 下标
    private Integer firstNum;

    public CustomSheetWriteHandler(Field[] declaredFields, Integer firstNum) {
        this.declaredFields = declaredFields;
        this.firstNum = firstNum ;
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        LOGGER.info("开始excel操作");
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            //解析注解信息
            ExplicitConstraint explicitConstraint = field.getAnnotation(ExplicitConstraint.class);
            String[] explicitArray = resolveExplicitConstraint(explicitConstraint);
            if (explicitArray != null && explicitArray.length > 0) {
                explicitListConstraintMap.put(i, explicitArray);
            }
        }

        //通过sheet处理下拉信息
        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();
        explicitListConstraintMap.forEach((k, v) -> {
            CellRangeAddressList rangeList = new CellRangeAddressList();
            CellRangeAddress addr = new CellRangeAddress(firstNum, 65535, k, k);
            rangeList.addCellRangeAddress(addr);
            DataValidationConstraint constraint = helper.createExplicitListConstraint(v);
            DataValidation validation = helper.createValidation(constraint, rangeList);
            sheet.addValidationData(validation);
        });

        LOGGER.info("Sheet写入成功!");
    }

    /**
     * 解析注解内容 获取下列表信息
     * @param explicitConstraint
     * @return
     */
    public static String[] resolveExplicitConstraint(ExplicitConstraint explicitConstraint){
        if (explicitConstraint == null) {
            return null;
        }
        //固定下拉信息
        String[] source = explicitConstraint.source();
        if (source.length > 0) {
            return source;
        }
        //动态下拉信息
        Class<? extends ExplicitInterface>[] classes = explicitConstraint.sourceClass();
        if (classes.length>0){
//            ExplicitInterface explicitInterface = null;
//            String[] source1 = explicitInterface.source();
            List<String> sourceList = new ArrayList<>();
            Stream.of(classes).forEach(aClass -> {
                ExplicitInterface anInterface = null;
                try {
                    anInterface = aClass.newInstance();
                    sourceList.addAll(Lists.newArrayList(anInterface.source()));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            if (sourceList.size()>0){
                return (String[]) sourceList.toArray();
            }
        }
        return null;
    }
}
