package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private TransferDao transferDao;
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private UserDao userDao;

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public Transfer makeTransfer(@RequestBody Transfer transfer){
        System.out.println(transfer);
        Transfer transfer1 = transferDao.createTransfer(transfer);
        if(transfer1 == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not complete the transfer");
        }
        return transfer1;
    }

// If(transferMoney>0)


     @RequestMapping(path = "/transfers", method = RequestMethod.GET )
    // that in Postman, GET request to the path of http://localhost:8080/transfers
    public List<Transfer> Transfers(Principal principal) {
         int senderUserId = userDao.findIdByUsername(principal.getName());
         int receiverUserId = userDao.findIdByUsername(principal.getName());
        return transferDao.getTransfers(senderUserId,receiverUserId);
    }

    @GetMapping("/transfer/{id}")
    public Transfer getTransferByTransferId(@PathVariable int id){
        Transfer transfer = transferDao.getTransferByTransferId(id);
        if (transfer == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found!!!");
        } else {
            return transfer;
        }
    }
}
