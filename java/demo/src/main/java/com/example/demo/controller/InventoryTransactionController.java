package com.example.demo.controller;

import com.example.demo.payload.ResponData;
import com.example.demo.payload.request.AddInventoryTransactionRequest;
import com.example.demo.service.InventoryTransactionService;
import com.example.demo.service.imp.InventoryTransactionServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/InventoryTran")
public class InventoryTransactionController {
    @Autowired
    InventoryTransactionServiceimp inventoryTransactionServiceimp;

    @GetMapping("")
    public ResponseEntity<?> getInventory(){
        return new ResponseEntity<>(inventoryTransactionServiceimp.getAllInventoryTransaction(), HttpStatus.OK);
    }

    @PostMapping("/addTrans/{type}")
    public ResponseEntity<?> addInventoryTransaction(@PathVariable String type,@RequestBody AddInventoryTransactionRequest request) {
        ResponData responData = new ResponData();
        request.setType(type);
        boolean success = inventoryTransactionServiceimp.addInventoryTransaction(request);
        if(success) {
            return new ResponseEntity<>(responData, HttpStatus.OK);
        }
        responData.setSuccess(false);
        return new ResponseEntity<>(responData, HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInventoryTransaction(@PathVariable int id) {
        ResponData responData = new ResponData();
        responData.setSuccess(inventoryTransactionServiceimp.deleteInventoryTransaction(id));
        return new ResponseEntity<>(responData, HttpStatus.OK);
    }
}
