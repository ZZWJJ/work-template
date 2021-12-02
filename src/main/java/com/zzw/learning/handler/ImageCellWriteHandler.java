package com.zzw.learning.handler;

/**
 * @description:
 * @projectName:ineco_workflow
 * @see:com.kunyue.leave.handler
 * @author:zzw
 * @createTime:2021/10/22 14:46
 * @version:1.0
 */

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.util.Units;

import java.util.List;
import java.util.Objects;

/**
 * EasyExcel图片填充拦截器
 *
 * @author shixf on 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageCellWriteHandler extends AbstractCellWriteHandler {

    /**
     * 图片行列跨度
     */
    private int colSpan = 1;
    private int rowSpan = 1;
    /**
     * 左侧和右侧边框粗细
     */
    private int borderPixelX1Y1;
    private int borderPixelX2Y2;
    /**
     * 可以随着单元格一起移动，不改变大小
     */
    private AnchorType anchorType = AnchorType.MOVE_DONT_RESIZE;


    public ImageCellWriteHandler() {
        this.borderPixelX1Y1 = 5;
        this.borderPixelX2Y2 = 5;
    }

    public ImageCellWriteHandler(int borderPixel) {
        this.borderPixelX1Y1 = borderPixel;
        this.borderPixelX2Y2 = borderPixel;
    }

    public ImageCellWriteHandler(int borderPixelX1Y1, int borderPixelX2Y2) {
        this.borderPixelX1Y1 = borderPixelX1Y1;
        this.borderPixelX2Y2 = borderPixelX2Y2;
    }

    /**
     * 单元格数据转换后调用
     */
    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder,
                                       WriteTableHolder writeTableHolder,
                                       CellData cellData, Cell cell,
                                       Head head, Integer relativeRowIndex, Boolean isHead) {
        // 此处不处理表头，不处理不包含图像的
        boolean noImageValue = Objects.isNull(cellData) || Objects.isNull(cellData.getImageValue());
        if (Objects.equals(Boolean.TRUE, isHead) || noImageValue) {
            return;
        }
        // 设置单元格类型为EMPTY 让EasyExcel不去处理该单元格 由我们自己填充
        cellData.setType(CellDataTypeEnum.EMPTY);
    }

    /**
     * 在单元格上的所有操作完成后调用
     */
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder,
                                 WriteTableHolder writeTableHolder,
                                 List<CellData> cellDataList, Cell cell,
                                 Head head, Integer relativeRowIndex, Boolean isHead) {
        if (CollectionUtils.isEmpty(cellDataList) || Objects.equals(Boolean.TRUE, isHead)) {
            return;
        }

        // cellDataList 是list的原因是 填充的情况下 可能会多个写到一个单元格 但是如果普通写入只有一个
        CellData cellData = cellDataList.get(0);

        //  在afterCellDataConverted方法里面已经将CellDataType设置为EMPTY 此处不能用cellData.getType()来判断是否图片类型
        if (Objects.isNull(cellData) || Objects.isNull(cellData.getImageValue())) {
            return;
        }

        setImageValue(cellData, cell);
    }

    private void setImageValue(CellData cellData, Cell cell) {
        Sheet sheet = cell.getSheet();
        int index = sheet.getWorkbook().addPicture(cellData.getImageValue(), HSSFWorkbook.PICTURE_TYPE_PNG);
        Drawing drawing = sheet.getDrawingPatriarch();
        if (drawing == null) {
            drawing = sheet.createDrawingPatriarch();
        }
        CreationHelper helper = sheet.getWorkbook().getCreationHelper();
        ClientAnchor anchor = helper.createClientAnchor();
        // 图片边距：让图片不会填满整个单元格，与四周有一定边距
        final int borderWidth1 = Units.pixelToEMU(getBorderPixelX1Y1());
        final int borderWidth2 = Units.pixelToEMU(getBorderPixelX2Y2());
        // 图片左上角偏移量
        anchor.setDx1(borderWidth1);
        anchor.setDy1(borderWidth1);
        // 图片右下角偏移量
        anchor.setDx2(Math.negateExact(borderWidth2));
        anchor.setDy2(Math.negateExact(borderWidth2));
        // 图片行列
        anchor.setCol1(cell.getColumnIndex());
        anchor.setCol2(cell.getColumnIndex() + getColSpan());
        anchor.setRow1(cell.getRowIndex());
        anchor.setRow2(cell.getRowIndex() + getRowSpan());
        anchor.setAnchorType(getAnchorType());
        drawing.createPicture(anchor, index);
        // 按照图片本身实际尺寸
        //Picture picture = drawing.createPicture(anchor, index);
        //picture.resize();
    }
}



