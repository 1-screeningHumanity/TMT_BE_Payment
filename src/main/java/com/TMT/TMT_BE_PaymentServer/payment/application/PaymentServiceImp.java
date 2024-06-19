package com.TMT.TMT_BE_PaymentServer.payment.application;

import static com.TMT.TMT_BE_PaymentServer.payment.domain.QPaymentLog.paymentLog;
import com.TMT.TMT_BE_PaymentServer.global.common.enumclass.PayName;
import com.TMT.TMT_BE_PaymentServer.global.common.enumclass.PaymentStatus;
import com.TMT.TMT_BE_PaymentServer.global.common.exception.CustomException;
import com.TMT.TMT_BE_PaymentServer.global.common.response.BaseResponseCode;
import com.TMT.TMT_BE_PaymentServer.payment.domain.PaymentLog;
import com.TMT.TMT_BE_PaymentServer.payment.dto.CashUpdateDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.KaKaoPayApproveResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.KaKaoPayReadyResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.PaymentLogResponseDto;
import com.TMT.TMT_BE_PaymentServer.payment.infrastructure.PaymentLogQueryDslRepository;
import com.TMT.TMT_BE_PaymentServer.payment.infrastructure.PaymentRepository;
import com.TMT.TMT_BE_PaymentServer.payment.vo.PaymentApproveVo;
import com.TMT.TMT_BE_PaymentServer.payment.vo.PaymentReadyVo;
import com.TMT.TMT_BE_PaymentServer.wallet.application.WalletServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.querydsl.core.Tuple;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private final WalletServiceImp walletServiceImp;
    private final PaymentLogQueryDslRepository paymentLogQueryDslRepository;
    static final String cid = "TC0ONETIME"; // 테스트용 CID

    @Value("${spring.KAKAO.SECRET}") // 시크릿 키
    private String secretKey;

    @Value("${spring.KAKAO.APPROVE}")
    private String approve;

    @Value("${spring.KAKAO.CANCEL}")
    private String cancel;

    @Value("${spring.KAKAO.FAIL}")
    private String fail;
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

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<HashMap<String, String>> httpEntity =
                    new HttpEntity<>(
                            kakaopayrequestBody(paymentStockInfoVo, partnerOrderId, userId),
                            this.getHeaders());

            KaKaoPayReadyResponseDto response = restTemplate.postForObject(
                    "https://open-api.kakaopay.com/online/v1/payment/ready ",
                    httpEntity,
                    KaKaoPayReadyResponseDto.class
            ); //RestTemplate를 이용해, 결제 요청 호출

            if (response != null) {
                response.getPartner_order_id(partnerOrderId);
            } //OrderNum 또한 담음


            return response;

        } catch (JsonProcessingException e) {
            throw new CustomException(BaseResponseCode.REST_TAMPLATE_WRONG_BODY);
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
        parameters.put("approval_url", approve); //결제성공시 redirect url
        parameters.put("cancel_url", cancel);//결제 취소시 redirect url
        parameters.put("fail_url", fail);//결제 실패시 redirect url

        return parameters;
    }

    // 카카오페이 요청 헤더값
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "SECRET_KEY " + secretKey);
        httpHeaders.set("Content-type", "application/json");
        return httpHeaders;
    }

    //approve header
    @Override
    public KaKaoPayApproveResponseDto kakaoPayApprove(PaymentApproveVo paymentApproveVo,
            String uuid) {

        String orderNum = paymentApproveVo.getPartner_order_id();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<HashMap<String, String>> httpEntity = new HttpEntity<>(
                kakaoPayApproveBody(paymentApproveVo, uuid), getHeaders());

        KaKaoPayApproveResponseDto response = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/approve",
                httpEntity,
                KaKaoPayApproveResponseDto.class
        );
        paymentLogSave(response, orderNum, uuid);
        return response;
    }

    //approve body 만듦
    public HashMap<String, String> kakaoPayApproveBody(PaymentApproveVo
            paymentApproveVo, String uuid) {

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("tid", paymentApproveVo.getTid());
        parameters.put("partner_order_id", paymentApproveVo.getPartner_order_id());
        parameters.put("partner_user_id", uuid);
        parameters.put("pg_token", paymentApproveVo.getPgToken());

        return parameters;
    }

    //paymentLog저장
    @Override
    @Transactional
    public void paymentLogSave(KaKaoPayApproveResponseDto result, String orderNum, String uuid) {

        PaymentLog payment = PaymentLog.builder()
                .orderNum(orderNum)
                .uuid(uuid)
                .payName(PayName.KakaoPay)
                .payMethod(result.getPayment_method_type())
                .totalAmount(result.getAmount().getTotal())
                .itemName(result.getItem_name())
                .quantity(result.getQuantity())
                .paymentStatus(PaymentStatus.SUCCESS)
                .build();

        paymentRepository.save(payment);
        processDto(uuid, result.getItem_name()); //지갑정보 update
    }

    public void processDto(String uuid, String itemName) {
        // "캐시"를 제거
        String processedItemName = itemName.replace("캐시", "");
        int cash = Integer.parseInt(processedItemName);

        CashUpdateDto cashUpdateDto = new CashUpdateDto();
        cashUpdateDto.getCashUpdateDto(uuid, cash);

        walletServiceImp.increaseCash(cashUpdateDto);
    }

    private PaymentLogResponseDto maptoDto(Tuple tuple) { //tuple to dto
        PayName payName = tuple.get(paymentLog.payName);
        int totalAmount = tuple.get(paymentLog.totalAmount);
        String itemName = tuple.get(paymentLog.itemName);
        LocalDateTime createdAt = tuple.get(paymentLog.createdAt);
        String orderNum = tuple.get(paymentLog.orderNum);
        return new PaymentLogResponseDto(payName, totalAmount, itemName, createdAt, orderNum);
    }

    @Override
    public List<PaymentLogResponseDto> paymentlog(String uuid) {

        List<Tuple> tuple = paymentLogQueryDslRepository.paymentloginfo(uuid);
        List<PaymentLogResponseDto> tupledto = tuple.stream()
                .map(this::maptoDto).toList();

        return tupledto;
    }


}