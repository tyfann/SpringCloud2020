package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author tyfann
 * @date 2021/1/5 5:26 下午
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String PaymentInfo_OK(Integer id) {
        return "-----PaymentFallbackService fall back-PaymentInfo_OK,呜呜呜";
    }

    @Override
    public String PaymentInfo_TimeOut(Integer id) {
        return "-----PaymentFallbackService fall back-PaymentInfo_TimeOut,呜呜呜";
    }
}
