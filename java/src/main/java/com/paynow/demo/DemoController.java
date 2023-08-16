package com.paynow.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@RestController
public class DemoController {

    @Value("${paynow.apiUrl}")
    private String apiUrl;

    @Value("${paynow.iframe.privateKey}")
    private String privateIframeKey;

    @Value("${paynow.iframe.publicKey}")
    private String publicIframeKey;

    @Value("${paynow.functional.privateKey}")
    private String privateFunctionalKey;

    @Value("${paynow.functional.publicKey}")
    private String publicFunctionalKey;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 嵌入式 SDK
     * @param name
     * @param model
     * @return
     */
    @GetMapping("/iframe")
    public ModelAndView sdk(Model model) {

        var response = this.InitPaymentIntents(this.privateIframeKey);

        // 請妥善保管此 ID，此 ID 會在後續的付款程序中使用可用來取得付款狀態
        // 可與您的訂單編號做關聯
        var id = response.getResult().getID();

        var paymentIntentSecret = response.getResult().getSecret();

        var clientKey = this.publicIframeKey;

        model.addAttribute("clientKey", clientKey);
        model.addAttribute("paymentIntentSecret", paymentIntentSecret);

        ModelAndView mav = new ModelAndView("iframe");

        return mav;
    }

    /**
     * 嵌入式 SDK
     * @param name
     * @param model
     * @return
     */
    @GetMapping("/functional")
    public ModelAndView functional(Model model) {

        var response = this.InitPaymentIntents(this.privateFunctionalKey);

        // 請妥善保管此 ID，此 ID 會在後續的付款程序中使用可用來取得付款狀態
        // 可與您的訂單編號做關聯
        var id = response.getResult().getID();

        var paymentIntentSecret = response.getResult().getSecret();

        var clientKey = this.publicFunctionalKey;

        model.addAttribute("clientKey", clientKey);
        model.addAttribute("paymentIntentSecret", paymentIntentSecret);

        ModelAndView mav = new ModelAndView("functional");

        return mav;
    }

    private Response InitPaymentIntents(String privateKey) {
        RestTemplate restTemplate = new RestTemplate();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = "";

        Req request = new Req();
        request.setAmount("100");
        request.setCurrency("TWD");

        try {
            jsonPayload = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + privateKey); // 將替換為實際的私鑰

        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

        Response response = restTemplate.postForObject(this.apiUrl + "api/v1/payment-intents", entity, Response.class);

        return response;
    }
}