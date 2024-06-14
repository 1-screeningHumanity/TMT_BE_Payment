package com.TMT.TMT_BE_PaymentServer.wallet.application;

import static com.TMT.TMT_BE_PaymentServer.wallet.domain.QWallet.wallet;

import com.TMT.TMT_BE_PaymentServer.global.common.exception.CustomException;
import com.TMT.TMT_BE_PaymentServer.global.common.response.BaseResponseCode;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.WalletDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.CashUpdateDto;
import com.TMT.TMT_BE_PaymentServer.wallet.domain.Wallet;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.CashDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonQueryDslDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonResponseDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.SendWalletInfoDto;
import com.TMT.TMT_BE_PaymentServer.wallet.infrastructure.WalletQueryDslImp;
import com.TMT.TMT_BE_PaymentServer.wallet.infrastructure.WalletRepository;
import com.TMT.TMT_BE_PaymentServer.wallet.vo.ChargeWonRequestVo;
import com.querydsl.core.Tuple;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImp implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletQueryDslImp walletQueryDslImp;

    @Override
    @Transactional
    public void createWallet(WalletDto walletDto) {
        Wallet wallet = Wallet.builder()
                .uuid(walletDto.getUuid())
                .cash(0)
                .won(1000000)
                .build();
        walletRepository.save(wallet);
    }
    @Override
    @Transactional
    public void updateWallet(CashUpdateDto cashUpdateDto){
        String uuid = cashUpdateDto.getUuid();
        Optional<Wallet> wallet = walletRepository.findByUuid(uuid);
        if (wallet != null){
            Wallet changeCash = Wallet
                    .builder()
                    .wallet_id(wallet.get().getWallet_id())
                    .uuid(wallet.get().getUuid())
                    .won(wallet.get().getWon())
                    .cash(cashUpdateDto.getCash())
                    .build();
            walletRepository.save(changeCash);
        }
    }

    @Override //캐시조회
    public CashDto hascash(String uuid){
        Optional<Wallet> wallet = walletRepository.findByUuid(uuid);
        if (wallet != null){
            CashDto cashDto = new CashDto();
            cashDto.getcash(wallet.get().getCash());
            return cashDto;
        }

        throw new CustomException(BaseResponseCode.WRONG_TOKEN);
    }


    @Override
    public ChargeWonResponseDto chargewon(String uuid, ChargeWonRequestVo chargeWonRequestVo){

        int cash = chargeWonRequestVo.getCash();
        int won;

        //cash to won
        if (cash % 1000 == 0) {
            won = cash * 100;
        } else {
            // 백 단위로 들어왔을 경우
            won = (cash / 1000) * 100000 + (cash % 1000) * 100;
        }

        //querydsl 메소드에 담아서 보냄.
        ChargeWonQueryDslDto chargeWonQueryDslDto = ChargeWonQueryDslDto
                .builder()
                .uuid(uuid)
                .cash(cash)
                .won(won)
                .build();

        walletQueryDslImp.updateWon(chargeWonQueryDslDto);

        ChargeWonResponseDto chargeWonResponseDto = new ChargeWonResponseDto();
        chargeWonResponseDto.getwon(won);

        return chargeWonResponseDto;
    }


    private SendWalletInfoDto maptoDto(Tuple tuple){
        String uuid = tuple.get(wallet.uuid);
        int won = tuple.get(wallet.won);
        return new SendWalletInfoDto(uuid, won);
    }

    @Override //지갑 정보전송
    public List<SendWalletInfoDto> sendWalletInfo(){
        List<Tuple> sendRequestDto = walletQueryDslImp.sendwalletinfo();
        List<SendWalletInfoDto> send = sendRequestDto.
                stream().map(this::maptoDto).toList();
        return send;
    }

}