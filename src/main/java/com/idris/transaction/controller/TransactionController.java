package com.idris.transaction.controller;

import com.idris.transaction.common.CommonResponse;
import com.idris.transaction.payloads.request.TopUpBalanceRequest;
import com.idris.transaction.payloads.request.TransactionRequest;
import com.idris.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(transactionService.getBalance(token));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.ok(new CommonResponse<>(500, ex.getMessage(), null));
        }
    }

    @PostMapping("/topup")
    public ResponseEntity<?> topup(@RequestHeader("Authorization") String token, @RequestBody @Valid TopUpBalanceRequest request) {
        try {
            return ResponseEntity.ok(transactionService.topUpBalance(token, request));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.ok(new CommonResponse<>(500, ex.getMessage(), null));
        }
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> transaction(@RequestHeader("Authorization") String token, @RequestBody @Valid TransactionRequest request) {
        try {
            return ResponseEntity.ok(transactionService.transaction(token, request));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.ok(new CommonResponse<>(500, ex.getMessage(), null));
        }
    }

    @GetMapping("/transaction/history")
    public ResponseEntity<?> getBalance(@RequestHeader("Authorization") String token, @RequestParam(name = "limit", defaultValue = "0") int limit) {
        try {
            return ResponseEntity.ok(transactionService.historyTransaction(token, limit));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.ok(new CommonResponse<>(500, ex.getMessage(), null));
        }
    }
}
