package com.TMT.TMT_BE_PaymentServer.wallet.application;

import static com.TMT.TMT_BE_PaymentServer.wallet.domain.QWallet.wallet;
import com.TMT.TMT_BE_PaymentServer.global.common.exception.CustomException;
import com.TMT.TMT_BE_PaymentServer.global.common.response.BaseResponseCode;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.CreateWalletDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionCashDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.DeductionWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.IncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.NicknameChangeDto;
import com.TMT.TMT_BE_PaymentServer.kafka.Dto.ReservationIncreaseWonDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.CashUpdateDto;
import com.TMT.TMT_BE_PaymentServer.wallet.domain.Wallet;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.CashDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonQueryDslDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.ChargeWonResponseDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.SendWalletInfoDto;
import com.TMT.TMT_BE_PaymentServer.wallet.dto.WonInfoRequestDto;
import com.TMT.TMT_BE_PaymentServer.wallet.infrastructure.WalletQueryDslImp;
import com.TMT.TMT_BE_PaymentServer.wallet.infrastructure.WalletRepository;
import com.TMT.TMT_BE_PaymentServer.wallet.vo.ChargeWonRequestVo;
import com.querydsl.core.Tuple;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImp implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletQueryDslImp walletQueryDslImp;
    @Override
    public void createWallet(CreateWalletDto createWalletDto) {

        Wallet wallet = Wallet.builder()
                .uuid(createWalletDto.getUuid())
                .nickname(createWalletDto.getNickname())
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


    private SendWalletInfoDto maptoDto(Tuple tuple){
        String uuid = tuple.get(wallet.uuid);
        Long won = tuple.get(wallet.won);
        String nickname = tuple.get(wallet.nickname);
        return new SendWalletInfoDto(uuid,nickname, won);
    }

    @Override //지갑 정보전송
    public List<SendWalletInfoDto> sendWalletInfo(){
        List<Tuple> sendRequestDto = walletQueryDslImp.sendwalletinfo();
        List<SendWalletInfoDto> send = sendRequestDto.
                stream().map(this::maptoDto).toList();
        return send;
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

    @Override
    public void changeNickname(NicknameChangeDto nicknameChangeDto){
        walletQueryDslImp.changeNickname(nicknameChangeDto);
    }

    @Override
    public void deductionCash(DeductionCashDto deductionCashDto){
        walletQueryDslImp.decreaseCash(deductionCashDto);
    }

}

