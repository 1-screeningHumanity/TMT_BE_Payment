package com.TMT.TMT_BE_PaymentServer.wallet.application;


import com.TMT.TMT_BE_PaymentServer.kafka.Dto.WalletDto;
import com.TMT.TMT_BE_PaymentServer.payment.dto.CashUpdateDto;
import com.TMT.TMT_BE_PaymentServer.wallet.domain.Wallet;
import com.TMT.TMT_BE_PaymentServer.wallet.infrastructure.WalletRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImp implements WalletService {

    private final WalletRepository walletRepository;

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

}
