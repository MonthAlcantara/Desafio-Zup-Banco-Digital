package io.github.monthalcantara.nossobancodigital.service.interfaces;

import io.github.monthalcantara.nossobancodigital.model.Cliente;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface DocumentoService {

    void salveArquivosDocumentoCliente(String diretorio, Long id, MultipartFile arquivoFrente, MultipartFile arquivoVerso);

    void salveDocumentosCliente(Cliente cliente, String docFrente, String docVerso);

    String busqueLocalizacaoDocumento(Path diretorioPath, String nomeDoArquivo);
}
