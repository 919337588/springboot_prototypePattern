package com.cx.template.impl;

import com.cx.template.AbstractPayCallbackTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class AliPayCallbackTemplate extends AbstractPayCallbackTemplate {
    @Override
    protected Map<String, String> verifySignature() {
        //>>>>假设一下为银联回调报文>>>>>>>>>>>>>>>>
        log.info(">>>>>第一步 解析支付宝据报文.....verifySignature()");
        Map<String, String> verifySignature = new HashMap<>();
        verifySignature.put("price", "1399");
        verifySignature.put("orderDes", "充值会员");
        // 支付状态为1表示为成功....
        verifySignature.put("aliPayMentStatus", "1");
        verifySignature.put("aliPayOrderNumber", "201910101011");
        // 解析报文是否成功 200 为成功..
        verifySignature.put("analysisCode", "200");
        return verifySignature;
    }

    @Override
    protected String asyncService(Map<String, String> verifySignatureMap) {
        log.info(">>>>>第三步asyncService()verifySignatureMap:{}", verifySignatureMap);
        String paymentStatus = verifySignatureMap.get("aliPayMentStatus");
        if (paymentStatus.equals("1")) {
            String aliPayOrderNumber = verifySignatureMap.get("aliPayOrderNumber");
            log.info(">>>>orderNumber:{aliPayOrderNumber},已经支付成功 修改订单状态为已经支付...");
        }
        return resultSuccess();
    }

    @Override
    protected String resultSuccess() {
        return "ok";
    }

    @Override
    protected String resultFail() {
        return "支付宝支付失败";
    }
}
