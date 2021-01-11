package com.zzw.learning.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.zzw.learning.param.TeacherParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * @author : zzw
 * @Description: 教职工信息excel读取监听类
 * @date : 2020/4/7 13:32
 **/
public class TeacherDataListener extends AnalysisEventListener<TeacherParam> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherDataListener.class);

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;
    private int COUNT = 0;

    private List<TeacherParam> list;
    private Map<String,String> urlMap;
    private List<String> avatarList;

    public TeacherDataListener(Map<String,String> urlMap, List<String> avatarList) {
        this.avatarList = avatarList;
        this.urlMap = urlMap;
    }


    /**
     * 这个每一条数据解析都会来调用
     *
     * @param teacherParam
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param analysisContext
     */
    @Override
    public void invoke(TeacherParam teacherParam, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(teacherParam));
        list.add(teacherParam);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
            // 计算总数量
            COUNT = COUNT + 1;
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        COUNT = 0;
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始数据检查！", list.size());
        LOGGER.info("{}条数据，数据检查结束！", list.size());

        LOGGER.info("{}条数据，开始存储数据库！", list.size());

        LOGGER.info("存储数据库成功！");
    }
}
