package br.tec.gtech.ftp_uploader.service;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class FtpService {

    @Value("${ftp.server}")
    private String server;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.user}")
    private String user;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.upload.directory}")
    private String uploadDirectory;

    public boolean uploadFile(String filePath) {
        FTPClient ftpClient = new FTPClient();
        boolean success = false;

        try {
            ftpClient.connect(server, port);
            log.info("Conectado ao servidor FTP: {}", server);

            ftpClient.login(user, password);
            log.info("Login realizado com sucesso.");

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.ASCII_FILE_TYPE);

            try (InputStream inputStream = new FileInputStream(filePath)) {
                success = ftpClient.storeFile(uploadDirectory, inputStream);

                if (success) {
                    log.info("Arquivo enviado com sucesso para o diretório: {}", uploadDirectory);
                } else {
                    log.error("Falha ao enviar o arquivo para o diretório: {}", uploadDirectory);
                }
            }
        } catch (IOException ex) {
            log.error("Erro durante o upload do arquivo: {}", filePath, ex);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                    log.info("Desconectado do servidor FTP.");
                }
            } catch (IOException ex) {
                log.error("Erro ao desconectar do servidor FTP", ex);
            }
        }

        return success;
    }
}
