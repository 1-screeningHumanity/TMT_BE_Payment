package com.TMT.TMT_BE_PaymentServer.payment.application;



import com.TMT.TMT_BE_PaymentServer.global.common.enumclass.PaymentStatus;
import com.TMT.TMT_BE_PaymentServer.payment.domain.PaymentLog;
import com.TMT.TMT_BE_PaymentServer.payment.dto.KaKaoPayApproveResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.KaKaoPayReadyResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.infrastructure.PaymentRepository;
import com.TMT.TMT_BE_PaymentServer.payment.vo.PaymentApproveVo;
import com.TMT.TMT_BE_PaymentServer.payment.vo.PaymentReadyVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;



@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentServiceImp implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ObjectMapper objectMapper;
    static final String cid = "TC0ONETIME"; // 테스트용 CID

    @Value("${spring.KAKAO.SECRET}") // 시크릿 키
    private String secretKey;

    public String createOrderNum() { // 주문번호 생성
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String currentDate = dateFormat.format(new Date()); // 현재 날짜 시간
        String orderNum = currentDate + "-TMT-"; // TMT로 시작

        Random random = new Random();
        StringBuilder randomCodeBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++) { // 0~9까지 난수 생성
            randomCodeBuilder.append(random.nextInt(10));
        }

        orderNum += randomCodeBuilder.toString();
        return orderNum;
    }

    @Override
    public KaKaoPayReadyResponseDto KakaopayRequest(PaymentReadyVo paymentStockInfoVo,
            String uuid) {
        String partnerOrderId = createOrderNum(); // 주문번호 생성
        String userId = uuid; // userid = uuid

        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<HashMap<String,String>> httpEntity =
                    new HttpEntity<>(kakaopayrequestBody(paymentStockInfoVo, partnerOrderId, userId),
                            this.getHeaders());

            KaKaoPayReadyResponseDto response = restTemplate.postForObject(
                    "https://open-api.kakaopay.com/online/v1/payment/ready ",
                    httpEntity,
                    KaKaoPayReadyResponseDto.class
            ); //RestTemplate를 이용해, 결제 요청 호출

            log.info("reponse: {}",response);

            paymentSave(partnerOrderId, userId);

            return response;

        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }


    //요청 파라미터 설정
    private HashMap<String, String> kakaopayrequestBody(PaymentReadyVo request,
            String partnerOrderId, String userId)
            throws JsonProcessingException {

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("partner_order_id", partnerOrderId);
        parameters.put("partner_user_id", userId);
        parameters.put("item_name", request.getItemName());
        parameters.put("quantity", String.valueOf(request.getQuantity()));
        parameters.put("total_amount", String.valueOf(request.getTotalAmount()));
        parameters.put("tax_free_amount", "0");
        parameters.put("approval_url", "http://localhost:8080"); //결제성공시 redirect url
        parameters.put("cancel_url", "http://localhost:8080");//결제 취소시 redirect url
        parameters.put("fail_url", "http://localhost:8080");//결제 실패시 redirect url

        return parameters;
    }

    // 카카오페이 요청 헤더값
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization","SECRET_KEY " + secretKey);
        httpHeaders.set("Content-type", "application/json");
        return httpHeaders;
    }

    private void paymentSave(String parthnerOrderId, String uuid) {

        PaymentLog payment = PaymentLog.builder()
                .orderNum(parthnerOrderId)
                .uuid(uuid)
                .payMethod("KAKAO_PAY")
                .paymentStatus(PaymentStatus.READY)
                .build();

        paymentRepository.save(payment);
    } //결제 대기일때도 일단 DB에 저장

    public String findPartnerOrderID(String uuid) {
        PaymentLog payment = paymentRepository.findByUuid(uuid);
        return payment.getOrderNum();
    } //partnerOrderId를 담음.

    //approve header
    public KaKaoPayApproveResponseDto kakaoPayApprove(PaymentApproveVo paymentApproveVo, String uuid) {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<HashMap<String, String>> httpEntity = new HttpEntity<>(
                kakaoPayApproveBody(paymentApproveVo, uuid),getHeaders());

        KaKaoPayApproveResponseDto response = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/approve",
                httpEntity,
                KaKaoPayApproveResponseDto.class
        );
        return response;
    }

    //approve body 만듦
    public HashMap<String, String> kakaoPayApproveBody(PaymentApproveVo
            paymentApproveVo, String  uuid){

        String partnerOrderId = findPartnerOrderID(uuid);

        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("tid", paymentApproveVo.getTid());
        parameters.put("partner_order_id", partnerOrderId);
        parameters.put("partner_user_id", uuid);
        parameters.put("pg_token", paymentApproveVo.getApproval_url());

        return parameters;
    }

    public void paymentResult(String uuid){
        PaymentLog paymentLog;

        paymentRepository.findByUuid(uuid);


    }

}