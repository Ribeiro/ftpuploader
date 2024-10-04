package br.tec.gtech.ftp_uploader.job;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.tec.gtech.ftp_uploader.entity.Dado;
import br.tec.gtech.ftp_uploader.repository.DadoRepository;
import br.tec.gtech.ftp_uploader.service.FtpService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FtpJob {

    @Autowired
    private DadoRepository dadoRepository;

    @Autowired
    private FtpService ftpService;

    private static final String FILE_PATH = "dados.txt";

    // Job agendado para rodar todos os dias às 2:00 AM
    @Scheduled(cron = "0 0 2 * * ?")
    public void exportDataToFtp() {
        log.info("Iniciando Job para exportação de dados e upload via FTP.");

        // Recuperar dados do banco de dados
        List<Dado> dados = dadoRepository.findAll();

        // Gerar arquivo .txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Dado dado : dados) {
                writer.write(dado.getId() + "," + dado.getNome() + "," + dado.getValor());
                writer.newLine();
            }
            log.info("Arquivo .txt gerado com sucesso.");
        } catch (IOException e) {
            log.error("Erro ao gerar arquivo .txt", e);
            return;
        }

        // Realizar upload via FTP
        boolean success = ftpService.uploadFile(FILE_PATH);
        if (success) {
            log.info("Arquivo enviado com sucesso via FTP.");
        } else {
            log.error("Falha ao enviar o arquivo via FTP.");
        }
    }
}