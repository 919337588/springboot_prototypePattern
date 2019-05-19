package com.cx.template.factory;


class PayMentFactory {

    public static PaymentService getPaymentService(String payType) {
        PaymentService paymentService = null;
        switch (payType) {
            case "ali_pay":
                paymentService = new AliPaySercice();
                break;
            case "wechat_pay":
                paymentService = new WeChatService();
                break;
        }
        return paymentService;
    }
}
