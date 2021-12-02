package com.zzw.learning.handler;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * @description: 纵向合并
 * @projectName:ineco_workflow
 * @see:com.kunyue.leave.handler
 * @author:zzw
 * @createTime:2021/11/1 13:42
 * @version:1.0
 */
public class ExcelFillCellMergeStrategy implements CellWriteHandler {

    /**
     * 合并字段的下标
     */
    private int[] mergeColumnIndex;
    /**
     * 合并几行
     */
    private int mergeRowIndex;

    // 默认为 1行高度
    private Integer maxHeight = 1;

    /**
     * 默认高度
     */
    private static final Integer DEFAULT_HEIGHT = 300;

    public ExcelFillCellMergeStrategy() {
    }

    public ExcelFillCellMergeStrategy(int mergeRowIndex, int[] mergeColumnIndex) {
        this.mergeRowIndex = mergeRowIndex;
        this.mergeColumnIndex = mergeColumnIndex;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                                 Head head, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
                                Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                       CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 List<CellData> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {
        //当前行
        int curRowIndex = cell.getRowIndex();
        //当前列
        int curColIndex = cell.getColumnIndex();
        // 设置换行
        CellStyle cellStyle = cell.getCellStyle();
        cellStyle.setWrapText(true);
        // 根据换行设置行高
        setCellHeight(cell);
        if (ObjectUtils.isNotEmpty(mergeRowIndex) && null != mergeColumnIndex){
            if (curRowIndex > mergeRowIndex) {
                for (int i = 0; i < mergeColumnIndex.length; i++) {
                    if (curColIndex == mergeColumnIndex[i]) {
                        mergeWithPrevRow(writeSheetHolder, cell, curRowIndex, curColIndex);
                        break;
                    }
                }
            }
        }
    }

    private void setCellHeight(Cell cell) {
        if (ObjectUtils.isNotEmpty(cell)) {
            Row row = cell.getRow();
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    if (cell.getStringCellValue().contains("\n")) {
                        int length = cell.getStringCellValue().split("\n").length;
                        maxHeight = Math.max(maxHeight, length);
                    }
                    break;
                default:
                    break;
            }
            row.setHeight((short) (maxHeight * DEFAULT_HEIGHT));
        }
    }

    private void mergeWithPrevRow(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex) {
        //获取当前行的当前列的数据和上一行的当前列列数据，通过上一行数据是否相同进行合并
        Object curData = cell.getCellTypeEnum() == CellType.STRING ? cell.getStringCellValue() :
                cell.getNumericCellValue();
        Cell preCell = cell.getSheet().getRow(curRowIndex - 1).getCell(curColIndex);
        Object preData = preCell.getCellTypeEnum() == CellType.STRING ? preCell.getStringCellValue() :
                preCell.getNumericCellValue();
        // 比较当前行的第一列的单元格与上一行是否相同，相同合并当前单元格与上一行
        //
        if (curData.equals(preData)) {
            Sheet sheet = writeSheetHolder.getSheet();
            List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
            boolean isMerged = false;
            for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
                CellRangeAddress cellRangeAddr = mergeRegions.get(i);
                // 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
                if (cellRangeAddr.isInRange(curRowIndex - 1, curColIndex)) {
                    sheet.removeMergedRegion(i);
                    cellRangeAddr.setLastRow(curRowIndex);
                    sheet.addMergedRegion(cellRangeAddr);
                    isMerged = true;
                }
            }
            // 若上一个单元格未被合并，则新增合并单元
            if (!isMerged) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(curRowIndex - 1, curRowIndex, curColIndex,
                        curColIndex);
                sheet.addMergedRegion(cellRangeAddress);
            }
        }
    }
}
