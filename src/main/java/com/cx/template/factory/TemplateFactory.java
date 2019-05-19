package com.cx.template.factory;

import com.cx.template.AbstractPayCallbackTemplate;
import com.cx.template.utils.SpringUtils;


public class TemplateFactory {

    public static AbstractPayCallbackTemplate getPayCallbackTemplate(String templateId) {
        AbstractPayCallbackTemplate payCallbackTemplate = (AbstractPayCallbackTemplate) SpringUtils.getBean(templateId);
        return payCallbackTemplate;
    }
}
