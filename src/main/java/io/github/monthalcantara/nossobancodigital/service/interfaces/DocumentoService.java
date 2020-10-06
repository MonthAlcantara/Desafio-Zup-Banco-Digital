package io.github.monthalcantara.nossobancodigital.service.interfaces;

import io.github.monthalcantara.nossobancodigital.model.Cliente;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentoService {

    void salveArquivosDocumentoCliente(String diretorio, Long id, MultipartFile arquivoFrente, MultipartFile arquivoVerso);

    void salveDocumentosCliente(Cliente cliente, String docFrente, String docVerso);

    String busqueLocalizacaoDocumento(String diretorio, Long id, String nomeDoArquivo);
}
