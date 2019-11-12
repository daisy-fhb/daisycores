package com.daisy.bangsen.util;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.dpi.cesg.collection.core.dao.SellerDao;
import com.dpi.cesg.collection.core.util.Protocol808.server.NettyTcpServer;
import com.dpi.cesg.collection.core.worker.WorkUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Component
public class InitUtil implements ApplicationContextAware, ServletContextAware,
        InitializingBean, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    NettyTcpServer nettyTcpServer;
    @Autowired
    WorkUtil workUtil;
    @Autowired
    SellerDao sellerDao;


    @Override
    public void afterPropertiesSet() {
        //每10分钟采集垃圾数据
        CronUtil.schedule("0 0/10 * * * ?", new Task() {
            @Override
            public void execute() {
                workUtil.getWeightInfo();
            }
        });

        //每3分钟检测餐厨车与商家的距离 以及 刷新车辆在地图上的重量显示数据
//        CronUtil.schedule("0 0/3 * * * ?", new Task() {
        CronUtil.schedule("0/5 * * * * ?", new Task() {
            @Override
            public void execute() {
//               workUtil.DetectDistanceBetweenCarAndSeller();
                workUtil.refreshCarWeightReal();
            }
        });

        //每天2点清空消息发送缓存
        CronUtil.schedule("0 0 2 * * ?",(Task) ()-> {
              workUtil.isSentMsgOfToday.clear();
        });

        //每天23点50产生作业和报表记录
        CronUtil.schedule("0 50 23 * * ?", (Task) () -> {
         workUtil.GenerateJobReocrd();
         workUtil.GenerateReportReocrd();
        });

        // 支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }

    @Override
    public void setServletContext(ServletContext servletContext) { }
}
