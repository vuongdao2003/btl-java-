package com.example.demo.service;

import com.example.demo.dto.InventoryTransactionDTO;
import com.example.demo.entity.InventoryTransaction;
import com.example.demo.entity.Products;
import com.example.demo.payload.request.AddInventoryTransactionRequest;
import com.example.demo.repository.InventoryTransactionRepository;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.service.imp.InventoryServiceimp;
import com.example.demo.service.imp.InventoryTransactionServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class InventoryTransactionService implements InventoryTransactionServiceimp {
    @Autowired
    private InventoryTransactionRepository inventoryTransactionRepository;

    @Autowired
    private InventoryServiceimp inventoryService;
    @Autowired
    private ProductsRepository productsReponsitory;
    @Override
    public List<InventoryTransactionDTO> getAllInventoryTransaction() {
        List<InventoryTransactionDTO> inventoryTransactionDTOList = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page <InventoryTransaction> inventoryTransactions = inventoryTransactionRepository.findAll(pageRequest);
        for (InventoryTransaction inventoryTransaction : inventoryTransactions) {
            InventoryTransactionDTO inventoryTransactionDTO = new InventoryTransactionDTO();
            inventoryTransactionDTO.setInventoryId(inventoryTransaction.getTransactionID());
            inventoryTransactionDTO.setDate(inventoryTransaction.getTransactionDate());
            inventoryTransactionDTO.setProductId(inventoryTransaction.getProducts().getProductID());
            inventoryTransactionDTO.setQuantitychange(inventoryTransaction.getQuantityChange());
            inventoryTransactionDTO.setType(inventoryTransaction.getType());
            inventoryTransactionDTO.setNote(inventoryTransaction.getNote());
            inventoryTransactionDTOList.add(inventoryTransactionDTO);
        }

        return inventoryTransactionDTOList;
    }

    @Override
    public boolean addInventoryTransaction(AddInventoryTransactionRequest request) {
        InventoryTransaction inventoryTransaction = new InventoryTransaction();
        Products products = new Products();
        products.setProductID(request.getProductId());
        inventoryTransaction.setProducts(products);
        inventoryTransaction.setQuantityChange(request.getQuantitychange());
        inventoryTransaction.setType(request.getType());
        inventoryTransaction.setNote(request.getNote());
        inventoryTransaction.setTransactionDate(LocalDateTime.now());
        Products isProducts = productsReponsitory.findById(request.getProductId()).orElse(null);
        try{
            if (isProducts!=null){
                if(request.getType().equals("import")){
                    int quantity = isProducts.getQuantity()+request.getQuantitychange();
                    inventoryService.updateInventory(isProducts.getProductID(),quantity);
                }else {
                    int quantity = isProducts.getQuantity()-request.getQuantitychange();
                    inventoryService.updateInventory(isProducts.getProductID(),quantity);
                }

            }
            inventoryTransactionRepository.save(inventoryTransaction);
        }catch (Exception e){
            return false;
        }
        return true;
    }


    @Override
    public boolean deleteInventoryTransaction(int id) {
        if(inventoryTransactionRepository.existsById(id)){
            inventoryTransactionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
