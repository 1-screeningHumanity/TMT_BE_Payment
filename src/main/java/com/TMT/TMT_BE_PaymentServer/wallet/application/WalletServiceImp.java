package com.TMT.TMT_BE_PaymentServer.wallet.application;

import com.TMT.TMT_BE_PaymentServer.global.common.exception.CustomException;
import com.TMT.TMT_BE_PaymentServer.global.common.response.BaseResponseCode;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.IncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.ReservationIncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.CashUpdateDto;
import com.TMT.TMT_BE_PaymentServer.wallet.domain.Wallet;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.CashDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonQueryDslDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonResponseDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.WonInfoRequestDto;
import com.TMT.TMT_BE_PaymentServer.wallet.infrastructure.WalletQueryDslImp;
import com.TMT.TMT_BE_PaymentServer.wallet.infrastructure.WalletRepository;
import com.TMT.TMT_BE_PaymentServer.wallet.vo.ChargeWonRequestVo;
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
    public void createWallet(String uuid) {
        Wallet wallet = Wallet.builder()
                .uuid(uuid)
                .cash(0)
                .won(1000000L)
                .build();
        walletRepository.save(wallet);
    }
    @Override
    public void increaseCash(CashUpdateDto cashUpdateDto){
        walletQueryDslImp.increaseCash(cashUpdateDto);
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
        Long won;

        //cash to won
        if (cash % 1000 == 0) {
            won = (long) (cash * 100);
        } else {
            // 백 단위로 들어왔을 경우
            won = (long) ((cash / 1000) * 100000 + (cash % 1000) * 100);
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
    @Override
    public void decreaseWon(DeductionWonDto deductionWonDto){
        walletQueryDslImp.decreaseWon(deductionWonDto);
    }

    @Override
    public void increaseWon(IncreaseWonDto increaseWonDto){
        walletQueryDslImp.increaseWon(increaseWonDto);
    }

    @Override
    public void reservationIncreaseWon(ReservationIncreaseWonDto reservationIncreaseWon){
        walletQueryDslImp.reservationIncreaseWon(reservationIncreaseWon);

    }
    @Override
    public WonInfoRequestDto getWonInfo(String uuid){
       Optional<Wallet> wallet = walletRepository.findByUuid(uuid);

       if (wallet != null){
           WonInfoRequestDto wonInfoRequestDto = new WonInfoRequestDto();
           wonInfoRequestDto.getwon(wallet.get().getWon());
           return wonInfoRequestDto;
       }
       throw new CustomException(BaseResponseCode.WRONG_TOKEN);


    }

}