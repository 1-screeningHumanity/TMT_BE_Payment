package com.TMT.TMT_BE_PaymentServer.wallet.infrastructure;

import com.TMT.TMT_BE_PaymentServer.wallet.domain.Wallet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUuid(String uuid);
}
