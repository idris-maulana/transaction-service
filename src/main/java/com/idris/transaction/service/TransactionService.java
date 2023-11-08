package com.idris.transaction.service;

import com.idris.transaction.common.CommonResponse;
import com.idris.transaction.common.StringHelper;
import com.idris.transaction.constant.SequenceConstant;
import com.idris.transaction.model.History;
import com.idris.transaction.model.Sequence;
import com.idris.transaction.model.User;
import com.idris.transaction.payloads.request.TopUpBalanceRequest;
import com.idris.transaction.payloads.request.TransactionRequest;
import com.idris.transaction.payloads.response.HistoryTransactionResponse;
import com.idris.transaction.repository.HistoryRepository;
import com.idris.transaction.repository.SequenceRepository;
import com.idris.transaction.repository.ServiceRepository;
import com.idris.transaction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.*;

@Service
public class TransactionService {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Transactional(readOnly = true)
    public CommonResponse<?> getBalance(String token) {
        User user = jwtService.verifyToken(token);
        return new CommonResponse(0, "Get Balance Berhasil", Map.of("balance", user.getBalance()));
    }

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Transactional
    public CommonResponse<?> topUpBalance(String token, TopUpBalanceRequest request) {
        User user = jwtService.verifyToken(token);
        user.setBalance(user.getBalance() + request.getTopUpAmmount());
        userRepository.save(user);

        History history = new History();
        history.setUserId(user.getId());
        history.setCreatedOn(new Date());
        history.setTotalAmount(request.getTopUpAmmount());
        history.setDescription("Top Up balance");
        history.setTransactionType("TOPUP");
        history.setInvoiceNumber("INV" + new Date().getTime() + "-" + StringHelper.generateSandString(10));
        historyRepository.save(history);

        return new CommonResponse(0, "Top Up Balance berhasil", Map.of("balance", user.getBalance()));
    }

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Transactional
    public CommonResponse<?> transaction(String token, TransactionRequest request) {
        User user = jwtService.verifyToken(token);

        Optional<com.idris.transaction.model.Service> optService = serviceRepository.getServiceByCode(request.getServiceCode());
        if (optService.isPresent()) {
            if (user.getBalance() < optService.get().getServiceTarif()) {
                return new CommonResponse<>(200, "Balance Tidak Mencukupi", Map.of("balance", user.getBalance()));
            }

            user.setBalance(user.getBalance() - optService.get().getServiceTarif());
            userRepository.save(user);

            History history = new History();
            history.setUserId(user.getId());
            history.setCreatedOn(new Date());
            history.setTotalAmount(optService.get().getServiceTarif());
            history.setDescription(optService.get().getServiceName());
            history.setTransactionType("PAYMENT");
            history.setInvoiceNumber("INV" + new Date().getTime() + "-" + StringHelper.generateSandString(10));
            historyRepository.save(history);

            return new CommonResponse<>(0, "Transaksi berhasil", history);
        }
        return new CommonResponse<>(102, "Service ataus Layanan tidak ditemukan", null);
    }

    public CommonResponse<?> historyTransaction(String token, Integer offset, Integer limit) {
        User user = jwtService.verifyToken(token);

        Page<History> historiesPage = historyRepository.getHistoryTransactionByUserId(user.getId(), limit == 0 ? PageRequest.of(offset, Integer.MAX_VALUE) : PageRequest.of(offset, limit));
        List<HistoryTransactionResponse> transactionResponses = new ArrayList<>();
        historiesPage.forEach(hisPage -> {
            transactionResponses.add(new HistoryTransactionResponse(hisPage.getInvoiceNumber(), hisPage.getTransactionType(), hisPage.getDescription(), hisPage.getTotalAmount(), hisPage.getCreatedOn()));
        });
        return new CommonResponse<>(0, "Get History Berhasil", Map.of("offset", historiesPage.getTotalPages(), "limit", limit, "records", transactionResponses));
    }

}