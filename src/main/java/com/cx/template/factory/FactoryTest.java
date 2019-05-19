package com.cx.template.factory;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class FactoryTest {
    public static void main(String[] args) {
//        PaymentService aliPaySercice = new AliPaySercice();
//        aliPaySercice.pay();
//        PaymentService weChatService = new WeChatService();
//        weChatService.pay();
        PaymentService aliPay = PayMentFactory.getPaymentService("wechat_pay");
        aliPay.pay();

        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean.xml");
        classPathXmlApplicationContext.getBean("sss");

    }
}
