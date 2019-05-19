# springboot_prototypePattern
模版模式

什么是模版方法
1.定义了一个操作中的算法的骨架，而将部分步骤的实现在子类中完成。
模板方法模式使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。
2.模板方法模式是所有模式中最为常见的几个模式之一，是基于继承的代码复用的基本技术，没有关联关系。 因此，在模板方法模式的类结构图中，只有继承关系。

核心设计要点：
AbstractClass : 抽象类，定义并实现一个模板方法。这个模板方法定义了算法的骨架，而逻辑的组成步骤在相应的抽象操作中，推迟到子类去实现
ConcreteClass : 实现实现父类所定义的一个或多个抽象方法。

模版方法应用场景
1.	比如聚合支付平台中系统回调代码重构
2.	Servlet 请求 
模版方法抽象类
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



具体实现模版

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
}


@Component
@Slf4j
public class UnionPayCallbackTemplate extends AbstractPayCallbackTemplate {
    @Override
    protected Map<String, String> verifySignature() {
        //>>>>假设一下为银联回调报文>>>>>>>>>>>>>>>>
        log.info(">>>>>第一步 解析银联数据报文.....verifySignature()");
        Map<String, String> verifySignature = new HashMap<>();
        verifySignature.put("price", "1399");
        verifySignature.put("orderDes", "充值会员");
        // 支付状态为1表示为成功....
        verifySignature.put("paymentStatus", "1");
        verifySignature.put("orderNumber", "201910101011");
        // 解析报文是否成功 200 为成功..
        verifySignature.put("analysisCode", "200");
        return verifySignature;
    }

    @Override
    protected String asyncService(Map<String, String> verifySignatureMap) {
        log.info(">>>>>第三步asyncService()verifySignatureMap:{}", verifySignatureMap);
        String paymentStatus = verifySignatureMap.get("paymentStatus");
        if (paymentStatus.equals("1")) {
            String orderNumber = verifySignatureMap.get("orderNumber");
            log.info(">>>>orderNumber:{orderNumber},已经支付成功 修改订单状态为已经支付...");
        }
        return resultSuccess();
    }

    @Override
    protected String resultSuccess() {
        return "success";
    }
}


工厂模式获取模版

public class TemplateFactory {

    public static AbstractPayCallbackTemplate getPayCallbackTemplate(String templateId) {
        AbstractPayCallbackTemplate payCallbackTemplate = (AbstractPayCallbackTemplate) SpringUtils.getBean(templateId);
        return payCallbackTemplate;
    }
}


模式模式优缺点


1.）优点
模板方法模式通过把不变的行为搬移到超类，去除了子类中的重复代码。子类实现算法的某些细节，有助于算法的扩展。通过一个父类调用子类实现的操作，通过子类扩展增加新的行为，符合“开放-封闭原则”。
2.）缺点
每个不同的实现都需要定义一个子类，这会导致类的个数的增加，设计更加抽象。
3.）适用场景
在某些类的算法中，用了相同的方法，造成代码的重复。控制子类扩展，子类必须遵守算法规则。

csdn地址：https://blog.csdn.net/qq_28056571/article/details/90344098
