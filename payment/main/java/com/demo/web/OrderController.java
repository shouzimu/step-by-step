package com.demo.web;

import com.demo.alipay.config.AlipayConfig;
import com.demo.alipay.util.AlipaySubmit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/order")
@Controller
public class OrderController {

    @RequestMapping("new")
    public String order(HttpServletResponse response) {

        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", AlipayConfig.payment_type);
        sParaTemp.put("notify_url", AlipayConfig.notify_url);
        sParaTemp.put("return_url", AlipayConfig.return_url);
        sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
        sParaTemp.put("out_trade_no", "10210001");
        sParaTemp.put("subject", "q000201");
        sParaTemp.put("total_fee", "0.01");
        sParaTemp.put("body", "11212");

        String path = AlipaySubmit.buildRequestUrl(sParaTemp);
        try {
            response.sendRedirect(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
