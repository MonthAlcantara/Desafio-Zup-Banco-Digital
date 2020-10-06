package io.github.monthalcantara.nossobancodigital.service.implementacoes;

import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.DocumentoCliente;
import io.github.monthalcantara.nossobancodigital.repository.ClienteRepository;
import io.github.monthalcantara.nossobancodigital.repository.DocumentoRepository;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import io.github.monthalcantara.nossobancodigital.service.interfaces.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    DocumentoRepository documentoRepository;

    @Override
    public void salveArquivosDocumentoCliente(String diretorio, Long id, MultipartFile arquivoFrente, MultipartFile arquivoVerso) {

        Cliente cliente = clienteService.retorneSeExistirEnderecoParaClienteComId(id);

        Path diretorioPath = Paths.get(diretorio, "cliente_" + id);

        gereDiretorioDestinoDocumento(diretorioPath, arquivoFrente);
        String caminhoArquivoFrente = busqueLocalizacaoDocumento(diretorioPath, arquivoFrente.getOriginalFilename());

        gereDiretorioDestinoDocumento(diretorioPath, arquivoVerso);
        String caminhoArquivoVerso = busqueLocalizacaoDocumento(diretorioPath, arquivoVerso.getOriginalFilename());

        salveDocumentosCliente(cliente, caminhoArquivoFrente, caminhoArquivoVerso);
    }

    @Override
    public void salveDocumentosCliente(Cliente cliente, String docFrente, String docVerso) {
        DocumentoCliente doc = gereNovoDocumento(docFrente, docVerso);
        cliente.setDocumentoCliente(documentoRepository.save(doc));
        clienteRepository.save(cliente);
    }


    public String busqueLocalizacaoDocumento(Path diretorioPath, String nomeDoArquivo) {
        return diretorioPath.toString() + "\\" + nomeDoArquivo;
    }

    private Path gereDiretorioDestinoDocumento(Path diretorioPath, MultipartFile arquivo) {

        String nomeArquivoFrente = retorneNomeDiretorioDestinoDocumento(diretorioPath.toString(), arquivo);
        Path arquivoPathFrente = diretorioPath.resolve(nomeArquivoFrente);
        try {
            Files.createDirectories(diretorioPath);
            arquivo.transferTo(arquivoPathFrente.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Problemas na tentativa de salvar arquivo: ", e);
        }
        return diretorioPath;
    }

    private String retorneNomeDiretorioDestinoDocumento(String diretorioPath, MultipartFile arquivo) {
        return diretorioPath + "\\" + arquivo.getOriginalFilename();
    }

    private DocumentoCliente gereNovoDocumento(String docFrente, String docVerso) {
        DocumentoCliente doc = new DocumentoCliente();
        doc.setDocumentoFrente(docFrente);
        doc.setDocumentoVerso(docVerso);
        return doc;
    }
}
