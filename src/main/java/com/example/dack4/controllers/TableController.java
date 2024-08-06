package com.example.dack4.controllers;

import com.example.dack4.config.MessageResponse;
import com.example.dack4.enums.TableStatus;
import com.example.dack4.models.Table;
import com.example.dack4.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/tables")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping
    public List<Table> getAllTables() {
        return tableService.getAllTables();
    }

    @PostMapping
    public ResponseEntity<Table> createTable(
            @RequestParam("numberOfTables") String numberOfTables,
            @RequestParam("chairs") int chairs,
            @RequestParam("status") TableStatus status,
            @RequestParam("qrCode") MultipartFile qr) {

        try {
            String qrImageName = null;
            if (qr != null && !qr.isEmpty()) {
                String originalFilename = qr.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFilename = new Date().getTime() + fileExtension;
                String qrImagePath = "public/img/qr/" + uniqueFilename;

                File uploadDirectory = new File("public/img/qr");
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }

                Files.copy(qr.getInputStream(), Paths.get(qrImagePath), StandardCopyOption.REPLACE_EXISTING);
                qrImageName = uniqueFilename;
            }

            // Tạo đối tượng Table mới
            Table newTable = new Table();
            newTable.setNumberOfTables(numberOfTables);
            newTable.setChairs(chairs);
            newTable.setStatus(status);
            newTable.setQrCode(qrImageName);

            Table createdTable = tableService.saveTable(newTable);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTable);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editTable(
            @PathVariable Long id,
            @RequestParam("numberOfTables") String numberOfTables,
            @RequestParam("chairs") int chairs,
            @RequestParam("status") TableStatus status,
            @RequestParam(value = "qrCode", required = false) MultipartFile qr) {

        Optional<Table> tableOptional = tableService.getTableById(id);
        if (!tableOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Table not found");
        }

        Table tableUpdate = tableOptional.get();
        tableUpdate.setNumberOfTables(numberOfTables);
        tableUpdate.setChairs(chairs);
        tableUpdate.setStatus(status);

        try {
            // Xử lý file hình ảnh QR nếu được tải lên
            if (qr != null && !qr.isEmpty()) {
                // Lưu file hình ảnh vào thư mục
                String originalFilename = qr.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFilename = new Date().getTime() + fileExtension;
                String qrImagePath = "public/img/qr/" + uniqueFilename;

                File uploadDirectory = new File("public/img/qr");
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }

                Files.copy(qr.getInputStream(), Paths.get(qrImagePath), StandardCopyOption.REPLACE_EXISTING);
                tableUpdate.setQrCode(uniqueFilename);
            }

            tableService.saveTable(tableUpdate);
            return ResponseEntity.ok("Table updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating table");
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTable(@PathVariable Long id) {
        Optional<Table> tableOptional = tableService.getTableById(id);
        if (!tableOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Table not found");
        }

        Table table = tableOptional.get();

        try {
            // Xóa tệp hình ảnh QR nếu có
            String qrImageName = table.getQrCode();
            if (qrImageName != null && !qrImageName.isEmpty()) {
                String qrImagePath = "public/img/qr/" + qrImageName;
                File qrImageFile = new File(qrImagePath);
                if (qrImageFile.exists()) {
                    qrImageFile.delete(); // Xóa tệp hình ảnh khỏi hệ thống tệp
                }
            }

            // Xóa đối tượng Table
            tableService.deleteTableById(id);
            return ResponseEntity.ok("Xoá thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting table");
        }
    }



}
