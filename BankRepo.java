package Banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Banking.entity.BankingAccount;

public interface BankRepo extends JpaRepository<BankingAccount, Long>{

}
