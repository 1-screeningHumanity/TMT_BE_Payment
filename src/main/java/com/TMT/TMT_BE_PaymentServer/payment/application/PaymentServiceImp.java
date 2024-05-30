package com.TMT.TMT_BE_PaymentServer.payment.application;



import com.TMT.TMT_BE_PaymentServer.payment.dto.KaKaoPayReadyResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.infrastructure.PaymentRepository;
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
import org.springframework.http.MediaType;
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
            KaKaoPayReadyResponseDto response = restTemplate.postForObject(
                    "https://kapi.kakao.com/v1/payment/ready",
                    new HttpEntity<>(
                            this.kakaopayrequest(paymentStockInfoVo, partnerOrderId, userId), this.getHeaders()),
                    KaKaoPayReadyResponseDto.class
            ); //RestTemplate를 이용해, 결제 요청 호출

            return response;
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }


    //요청 파라미터 설정
    private String kakaopayrequest(PaymentReadyVo request, String partnerOrderId, String userId)
            throws JsonProcessingException {
        log.info("kakaopayrequest userId: " + userId);
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("partner_order_id", partnerOrderId);
        parameters.put("partner_user_id", userId);
        parameters.put("item_name", request.getItemName());
        parameters.put("quantity", String.valueOf(request.getQuantity()));
        parameters.put("total_amount", String.valueOf(request.getTotalAmount()));
        parameters.put("tax_free_amount", "0");
        parameters.put("approval_url", "http://localhost:8080/approval"); //결제성공시 redirect url
        parameters.put("cancel_url", "http://localhost:8080/cancel");//결제 취소시 redirect url
        parameters.put("fail_url", "http://localhost:8080/fail");//결제 실패시 redirect url



        return objectMapper.writeValueAsString(parameters);
    }

    // 카카오페이 요청 헤더값
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK " + secretKey);
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return httpHeaders;
    }
}