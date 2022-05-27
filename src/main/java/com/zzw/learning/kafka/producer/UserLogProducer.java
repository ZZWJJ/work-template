//package com.zzw.learning.kafka.producer;
//
//import com.alibaba.fastjson.JSON;
//import com.zzw.learning.kafka.bean.UserLog;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
///**
// * @author : zzw
// * @Description: 发送消息
// * @date : 2021/1/28 16:04
// **/
//@Component
//public class UserLogProducer {
//
//    @Autowired
//    private KafkaTemplate kafkaTemplate;
//
//    /**
//     * 发送数据
//     * @param userid
//     */
//    public void sendLog(String userid){
//        UserLog userLog = new UserLog();
//        userLog.setUserName("jhp");
//        userLog.setState("0");
//        userLog.setUserId(userid);
//
//        System.err.println("发送用户日志数据:"+userLog);
//        kafkaTemplate.send("user-log", JSON.toJSONString(userLog));
//    }
//
//
//
//}
