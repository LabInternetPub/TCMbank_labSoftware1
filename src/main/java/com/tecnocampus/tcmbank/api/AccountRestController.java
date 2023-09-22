package com.tecnocampus.tcmbank.api;

import com.tecnocampus.tcmbank.application.AccountController;
import com.tecnocampus.tcmbank.application.dto.AccountDTO;
import com.tecnocampus.tcmbank.application.dto.UserDTO;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountRestController {
    private final AccountController accountController;

    public AccountRestController(AccountController accountController) {this.accountController = accountController;}

    @PostMapping("/users/{id}/accounts")
    public AccountDTO createAccount(@PathVariable String id, @RequestBody AccountDTO accountDTO) throws Exception {
        return accountController.createAccount(accountDTO, id);
    }

    @PostMapping("/accounts/{id}/movements")
    public AccountDTO accountBalanceMovement(@PathVariable String id, @RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        int quantity = jsonObject.getInt("quantity");

        return accountController.movement(id, quantity);
    }

    @PostMapping("/accounts/{originAccountId}/transfers/{destinationAccountId}")
    public List<AccountDTO> accountBalanceTransfer(@PathVariable String originAccountId,
                                                   @PathVariable String destinationAccountId,
                                                   @RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        int quantity = jsonObject.getInt("quantity");

        return accountController.transfer(originAccountId, destinationAccountId, quantity);
    }

    @GetMapping("/accounts/{accountId}")
    public AccountDTO getAccount(@PathVariable String accountId) {
        return accountController.getAccount(accountId);
    }

    @GetMapping("/users/{id}/accounts")
    public List<AccountDTO> getAllUserAccounts(@PathVariable String id) {
        return accountController.getAllUserAccounts(id);
    }

    @DeleteMapping("/accounts/{accountId}")
    public void deleteAccount(@PathVariable String accountId) {
        accountController.deleteAccount(accountId);
    }

    @DeleteMapping("/users/{id}/accounts")
    public UserDTO deleteAllUserAccounts(@PathVariable String id) {
        return accountController.deleteAllUserAccounts(id);
    }
}