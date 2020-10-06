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
    ClienteRepository clienteRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    DocumentoRepository documentoRepository;

    @Override
    public void salveArquivosDocumentoCliente(String diretorio, Long id, MultipartFile arquivoFrente, MultipartFile arquivoVerso) {

        Cliente cliente = clienteService.busqueClientePeloId(id);

        clienteService.retorneSeExistirEnderecoParaClienteComId(id);

        Path diretorioPath = Paths.get(diretorio, "cliente_" + id);

        String nomeArquivoFrente = diretorioPath.toString() + "\\" + arquivoFrente.getOriginalFilename();
        String nomeArquivoVerso = diretorioPath.toString() + "\\" + arquivoVerso.getOriginalFilename();

        System.out.println("diretorioPath.toString()" + diretorioPath.toString());
        System.out.println("arquivoFrente.getOriginalFilename()" + arquivoFrente.getOriginalFilename());

        Path arquivoPathFrente = diretorioPath.resolve(nomeArquivoFrente);
        Path arquivoPathVerso = diretorioPath.resolve(nomeArquivoVerso);
        System.out.println("arquivoPathFrente" + arquivoPathFrente);
        try {

            Files.createDirectories(diretorioPath);
            arquivoFrente.transferTo(arquivoPathFrente.toFile());
            arquivoVerso.transferTo(arquivoPathVerso.toFile());
            salveDocumentosCliente(cliente, nomeArquivoFrente, nomeArquivoVerso);

        } catch (IOException e) {
            throw new RuntimeException("Problemas na tentativa de salvar arquivo: ", e);
        }
    }

    @Override
    public void salveDocumentosCliente(Cliente cliente, String docFrente, String docVerso) {
        DocumentoCliente doc = new DocumentoCliente();
        doc.setDocumentoFrente(docFrente);
        doc.setDocumentoVerso(docVerso);

        cliente.setDocumentoCliente(documentoRepository.save(doc));

        clienteRepository.save(cliente);
    }

    @Override
    public String busqueLocalizacaoDocumento(String diretorio, Long id, String nomeDoArquivo) {
        Path diretorioPath = Paths.get(diretorio, "cliente_" + id);
        return diretorioPath.toString() + "\\" + nomeDoArquivo;
    }
}
