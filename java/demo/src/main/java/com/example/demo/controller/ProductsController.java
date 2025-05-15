package com.example.demo.controller;

import com.example.demo.dto.ProductsDTO;
import com.example.demo.entity.Products;
import com.example.demo.payload.ResponData;
import com.example.demo.service.ProductsService;
import com.example.demo.service.imp.FileServiceimp;
import com.example.demo.service.imp.ProductsServiceimp;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController

@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private FileServiceimp fileService;
    @Autowired
    ProductsServiceimp productsServiceimp;
    @PostMapping()
    public ResponseEntity<?> createProducts(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productName") String productName,
            @RequestParam("category") Integer category,
            @RequestParam("price") Double price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("supplierID") Integer supplierID

    ) {
        ResponData responData = new ResponData();
        boolean isSucces = productsServiceimp.insertProduct(file, productName, category, price, quantity, supplierID);
        responData.setData(isSucces);
        return new ResponseEntity<>(responData, HttpStatus.CREATED);
    }
    @GetMapping("")
    public ResponseEntity<?> getProducts() {
        ResponData responData = new ResponData();
        responData.setData(productsServiceimp.getProducts());
        return new ResponseEntity<>(responData, HttpStatus.OK);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProduct(@PathVariable String category) {
        ResponData responData = new ResponData();
        responData.setData(productsServiceimp.getProductsByCategoryT(category));
        return new ResponseEntity<>(responData, HttpStatus.OK);
    }
    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        System.out.println("Download file: " + filename);
        Resource file = fileService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody ProductsDTO productsDTO) {
        productsDTO.setId(id);
        ResponData responData = new ResponData();
        responData.setData(productsServiceimp.updateProduct(productsDTO));
        return new ResponseEntity<>(responData, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        ResponData responData = new ResponData();
        responData.setData(productsServiceimp.deleteProduct(id));
        return new ResponseEntity<>(responData, HttpStatus.OK);
    }
}
