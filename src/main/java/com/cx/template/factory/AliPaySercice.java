package com.cx.template.factory;


public class AliPaySercice implements PaymentService {
    @Override
    public void pay() {
        System.out.println("阿里支付对象...");
    }
}
