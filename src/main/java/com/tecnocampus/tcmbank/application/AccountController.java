package com.tecnocampus.tcmbank.application;

import com.tecnocampus.tcmbank.application.dto.AccountDTO;
import com.tecnocampus.tcmbank.application.dto.UserDTO;
import com.tecnocampus.tcmbank.domain.*;
import com.tecnocampus.tcmbank.persistence.AccountRepository;
import com.tecnocampus.tcmbank.persistence.OperationRepository;
import com.tecnocampus.tcmbank.persistence.UserRepository;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    public AccountController(UserRepository userRepository, AccountRepository accountRepository,
                             OperationRepository operationRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    public AccountDTO createAccount(AccountDTO accountDTO, String userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();
        Account account = new Account(accountDTO, user);

        accountRepository.save(account);

        return new AccountDTO(account);
    }

    public List<AccountDTO> transfer(String sourceAccountId, String destinationAccountId, double transferAmount) {
        Account sourceAccount = accountRepository.findById(sourceAccountId).orElseThrow();
        Account destinationAccount = accountRepository.findById(destinationAccountId).orElseThrow();
        Transfer transfer = new Transfer(sourceAccount, destinationAccount, transferAmount);

        sourceAccount.operation(transfer);
        destinationAccount.operation(transfer);
        operationRepository.save(transfer);

        return List.of(new AccountDTO[]{new AccountDTO(sourceAccount), new AccountDTO(destinationAccount)});
    }

    public AccountDTO movement(String accountId, double movementAmount) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        Movement movement = new Movement(account, movementAmount);

        account.operation(movement);
        operationRepository.save(movement);

        return new AccountDTO(account);
    }

    public List<AccountDTO> getAllUserAccounts(String userId) {
        List<Account> accounts = accountRepository.findAllByUserIdEquals(userId);
        List<AccountDTO> accountDTOS = new ArrayList<>();

        for (Account account : accounts)
            accountDTOS.add(new AccountDTO(account));

        return accountDTOS;
    }

    public AccountDTO getAccount(String accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow();

        return new AccountDTO(account);
    }

    public UserDTO deleteAllUserAccounts(String userId) {
        User user = userRepository.findById(userId).orElseThrow();

        accountRepository.removeAllByUserId(userId);

        return new UserDTO(user);
    }

    public void deleteAccount(String accountId) {
        accountRepository.deleteById(accountId);
    }
}