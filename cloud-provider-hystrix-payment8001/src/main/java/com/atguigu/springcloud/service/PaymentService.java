package com.atguigu.springcloud.service;

import org.springframework.stereotype.Service;

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

    public String paymentInfo_TimeOut(Integer id){
        int timeNumber = 3;
        try{ TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) {e.printStackTrace();}
        return "线程池：  " + Thread.currentThread().getName()+"   paymentInfo_TimeOut, id:   "+id+"\t"+"哈哈哈"+"   耗时（秒）： "+timeNumber;
    }
}
