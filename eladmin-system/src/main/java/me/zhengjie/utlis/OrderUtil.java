package me.zhengjie.utlis;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.constants.CommonConstant;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author LiangBin
 * @Description
 * @date 2022/1/4 17:49
 */
@Component
@Slf4j
public class OrderUtil {

    /**
     * 生成分包合同队列 key
     */
    private static final String RESOURCE_QUEUE_KEY = "nimble:contract:queue";
    //阻塞弹出永不超时
    private static final Integer TIME_OUT_SECOND = 0;

    private RedisTemplate<Object, Object> redisTemplate;

    public OrderUtil(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 获取新增自增单号
    public String getIncrOrderNo(String key){
        Date now = new Date();
        String dateTime = DateFormatUtils.format(now, "yyyy-MM-dd");
        return key.concat("-" + dateTime).concat(String.format("-%04d",redisTemplate.opsForValue().increment(key)));
    }

    // 获取展示自增单号
    public String getOrderNoForShow(String key){
        Date now = new Date();
        String dateTime = DateFormatUtils.format(now, "yyyy-MM-dd");
        return key.concat("-" + dateTime).concat(String.format("-%04d",(Integer)redisTemplate.opsForValue().get(key) + 1));
    }

    // 刷新自增单号序号
    public void refleshIncrOrderConut(){
        log.info("刷新计数器 start...");
        redisTemplate.opsForValue().set(CommonConstant.CG, 0);
        redisTemplate.opsForValue().set(CommonConstant.SC, 0);
        redisTemplate.opsForValue().set(CommonConstant.XT, 0);
        redisTemplate.opsForValue().set(CommonConstant.CT, 0);
        redisTemplate.opsForValue().set(CommonConstant.XS, 0);
        redisTemplate.opsForValue().set(CommonConstant.DC, 0);
        log.info("刷新计数器 end...");
    }

    public <T> void pushBranchContract(T agreement){
        if(agreement == null){
            return;
        }
        redisTemplate.opsForList().leftPush(RESOURCE_QUEUE_KEY, agreement);
    }

    public <T> T popBranchContract(){
        try{
            T value = (T) redisTemplate.opsForList().rightPop(RESOURCE_QUEUE_KEY, TIME_OUT_SECOND, TimeUnit.SECONDS);
            return value;
        }catch(Exception ex){
            log.error("[自由职业者服务协议合同redis队列]读取列表异常", ex);
        }
        return null;
    }
}
