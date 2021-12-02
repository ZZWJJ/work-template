package com.zzw.learning.handler;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @projectName:ineco_workflow
 * @see:com.kunyue.leave.handler
 * @author:zzw
 * @createTime:2021/11/3 10:37
 * @version:1.0
 */
public class CustomCellWriteWeightHandler extends AbstractColumnWidthStyleStrategy {
    private Map<Integer, Map<Integer, Integer>> CACHE = new HashMap<>();

    /**
     * 换行行数
     */
    private int rowIndex;
    private Integer minColumnWidth = 30;

    public CustomCellWriteWeightHandler(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> cellDataList, Cell cell, Head head, Integer integer, Boolean isHead) {
        boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
        //当前行
        int curRowIndex = cell.getRowIndex();
        if (curRowIndex > rowIndex){
            if (needSetWidth) {
                Map<Integer, Integer> maxColumnWidthMap = CACHE.get(writeSheetHolder.getSheetNo());
                if (maxColumnWidthMap == null) {
                    maxColumnWidthMap = new HashMap<>();
                    CACHE.put(writeSheetHolder.getSheetNo(), maxColumnWidthMap);
                }

                Integer columnWidth = this.dataLength(cellDataList, cell, isHead);
                if (columnWidth <= minColumnWidth){
                    columnWidth = minColumnWidth;
                }
                if (columnWidth >= 0) {
                    if (columnWidth > 254) {
                        columnWidth = 254;
                    }

                    Integer maxColumnWidth = maxColumnWidthMap.get(cell.getColumnIndex());
                    if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                        maxColumnWidthMap.put(cell.getColumnIndex(), columnWidth);
                        Sheet sheet = writeSheetHolder.getSheet();
                        sheet.setColumnWidth(cell.getColumnIndex(), columnWidth * 256);
                    }

                }
            }
        }
    }

    /**
     * 计算长度
     * @param cellDataList
     * @param cell
     * @param isHead
     * @return
     */
    private Integer dataLength(List<CellData> cellDataList, Cell cell, Boolean isHead) {
        if (isHead) {
            return cell.getStringCellValue().getBytes().length;
        } else {
            CellData cellData = cellDataList.get(0);
            CellDataTypeEnum type = cellData.getType();
            if (type == null) {
                return -1;
            } else {
                switch (type) {
                    case STRING:
                        // 换行符（数据需要提前解析好）
                        int index = cellData.getStringValue().indexOf("\n");
                        return index != -1 ?
                                cellData.getStringValue().substring(0, index).getBytes().length + 1 : cellData.getStringValue().getBytes().length + 1;
                    case BOOLEAN:
                        return cellData.getBooleanValue().toString().getBytes().length;
                    case NUMBER:
                        return cellData.getNumberValue().toString().getBytes().length;
                    default:
                        return -1;
                }
            }
        }
    }
}
