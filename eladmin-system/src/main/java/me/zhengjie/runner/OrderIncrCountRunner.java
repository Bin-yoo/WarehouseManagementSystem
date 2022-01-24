package me.zhengjie.runner;

import me.zhengjie.constants.CommonConstant;
import me.zhengjie.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author LiangBin
 * @Description 初始化redis中单据自增序号
 * @date 2022/1/10 17:27
 */
@Component
public class OrderIncrCountRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(OrderIncrCountRunner.class);

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("初始化单据自增序号 start...");
        if (!redisUtils.hasKey(CommonConstant.CG)) {
            redisUtils.set(CommonConstant.CG, 0);
        }
        if (!redisUtils.hasKey(CommonConstant.SC)) {
            redisUtils.set(CommonConstant.SC, 0);
        }
        if (!redisUtils.hasKey(CommonConstant.XT)) {
            redisUtils.set(CommonConstant.XT, 0);
        }
        if (!redisUtils.hasKey(CommonConstant.GT)) {
            redisUtils.set(CommonConstant.GT, 0);
        }
        if (!redisUtils.hasKey(CommonConstant.XS)) {
            redisUtils.set(CommonConstant.XS, 0);
        }
        if (!redisUtils.hasKey(CommonConstant.TB)) {
            redisUtils.set(CommonConstant.TB, 0);
        }
        logger.info("初始化单据自增序号 end...");
    }
}
