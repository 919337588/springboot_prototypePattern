package com.cx.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Slf4j
@Component
public abstract class AbstractPayCallbackTemplate {

    /**
     * 异步回调业务
     *
     * @return
     */
    public String asyncCallBack() {
        // 1. 支付回调验证参数
        Map<String, String> verifySignatureMap = verifySignature();
        // 2. 参数验证成功，写入日志中..
        payLog(verifySignatureMap);
        String analysisCode = verifySignatureMap.get("analysisCode");
        if (!analysisCode.equals("200")) {
            return resultFail();
        }
        // 3. 执行回调异步相关逻辑
        return asyncService(verifySignatureMap);

    }


    /**
     * 支付回调验证参数
     *
     * @return
     */
    protected abstract Map<String, String> verifySignature();

    /**
     * 使用多线程异步写入日志
     *
     * @param verifySignatureMap
     */
    @Async
    private void payLog(Map<String, String> verifySignatureMap) {
        log.info(">>>>>>>>>>第二步 写入payLog........");
    }

    /**
     * 每个子类需要实现 实现业务解析操作
     *
     * @return
     */
    protected abstract String asyncService(Map<String, String> verifySignatureMap);

    /**
     * 异步返回结果..
     *
     * @return
     */
    protected abstract String resultSuccess();

    /**
     * 异步返回失败
     *
     * @return
     */
    protected abstract String resultFail();
}
