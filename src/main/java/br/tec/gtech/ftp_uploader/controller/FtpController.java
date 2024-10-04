package br.tec.gtech.ftp_uploader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.tec.gtech.ftp_uploader.service.FtpService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FtpController {

    @Autowired
    private FtpService ftpService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("filePath") String filePath) {
        log.info("Requisição recebida para upload do arquivo: {}", filePath);
        boolean result = ftpService.uploadFile(filePath);
        
        if (result) {
            log.info("Upload do arquivo {} realizado com sucesso!", filePath);
            return "Upload realizado com sucesso!";
        } else {
            log.error("Falha no upload do arquivo: {}", filePath);
            return "Falha no upload!";
        }
    }
}