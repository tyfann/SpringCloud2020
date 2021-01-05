package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @author tyfann
 * @date 2021/1/4 8:35 下午
 */
@Service
public class PaymentService {
    /**
     * 正常访问，肯定OK
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id){
        return "线程池：  " + Thread.currentThread().getName()+"   paymentInfo_OK, id:   "+id+"\t"+"哈哈哈";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id){
        try{ TimeUnit.MILLISECONDS.sleep(3000); } catch (InterruptedException e) {e.printStackTrace();}
        return "线程池：  " + Thread.currentThread().getName()+"   paymentInfo_TimeOut, id:   "+id+"\t"+"哈哈哈"+"   耗时（秒）： ";
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池：  " + Thread.currentThread().getName()+"   8001系统繁忙或者运行报错，请稍后重试, id:   "+id+"\t"+"呜呜呜";
    }

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),// 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少以后跳闸

    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id < 0) {
            throw new RuntimeException("*******id cannot be negative");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id cannot be negative, please try later. id:  "+id;
    }
}
