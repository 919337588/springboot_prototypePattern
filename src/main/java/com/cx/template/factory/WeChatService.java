package com.cx.template.factory;


public class WeChatService implements PaymentService {
    @Override
    public void pay() {
        System.out.println("微信支付对象...");
    }
}
