package com.cx.template.controller;

import com.cx.template.AbstractPayCallbackTemplate;
import com.cx.template.factory.TemplateFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TemplateController {
    @RequestMapping("/asyncCallBack")
    public String asyncCallBack(String templateId) {
        AbstractPayCallbackTemplate payCallbackTemplate = TemplateFactory.getPayCallbackTemplate(templateId);
        return payCallbackTemplate.asyncCallBack();
    }
}
