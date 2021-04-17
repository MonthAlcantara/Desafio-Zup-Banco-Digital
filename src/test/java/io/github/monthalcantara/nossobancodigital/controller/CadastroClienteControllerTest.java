package io.github.monthalcantara.nossobancodigital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Conta;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import io.github.monthalcantara.nossobancodigital.service.interfaces.DocumentoService;
import io.github.monthalcantara.nossobancodigital.service.interfaces.EnderecoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CadastroClienteController.class})
@ExtendWith(SpringExtension.class)
public class CadastroClienteControllerTest {
    @Autowired
    private CadastroClienteController cadastroClienteController;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private DocumentoService documentoService;

    @MockBean
    private EnderecoService enderecoService;

    @Test
    @DisplayName("Deve retornar 200 para aceite da proposta")
    public void testAceiteContrato() throws Exception {
        Endereco endereco = enderecoFactory();
        when(this.enderecoService.retorneSeExistirEnderecoComId((Long) any())).thenReturn(endereco);

        Cliente cliente = clienteFactory(endereco);

        Conta conta = contaFactory(cliente);

        Endereco endereco1 = enderecoFactory();

        Cliente cliente1 = clienteSemNomeFactory(conta, endereco1);

        Conta conta1 = contaFactory(cliente1);

        Endereco endereco2 = enderecoFactory();

        Cliente cliente2 = clienteSemNomeFactory(conta1, endereco2);

        when(this.clienteService.retorneSeExistirEnderecoParaClienteComId((Long) any())).thenReturn(cliente2);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders
                .post("/v1/cadastro/cliente/{id}/endereco/arquivo/aceite", 1L);
        MockHttpServletRequestBuilder requestBuilder = postResult.param("aceite", String.valueOf(true));
        MockMvcBuilders.standaloneSetup(this.cadastroClienteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("null!!! Iremos criar a sua conta")));
    }


    @Test
    @DisplayName("Deve salvar um nove endereco")
    public void testSalveEndereco() throws Exception {
        Endereco endereco = enderecoFactory();
        when(this.enderecoService.salveNovoEndereco((Long) any(), (EnderecoDTO) any())).thenReturn(endereco);

        Cliente cliente = clienteFactory(endereco);

        Conta conta = contaFactory(cliente);

        Endereco endereco1 = enderecoFactory();

        Cliente cliente1 = clienteSemNomeFactory(conta, endereco1);

        Conta conta1 = contaFactory(cliente1);

        Endereco endereco2 = enderecoFactory();

        Cliente cliente2 = clienteSemNomeFactory(conta1, endereco2);
        when(this.clienteService.busqueClientePeloId((Long) any())).thenReturn(cliente2);

        EnderecoDTO enderecoDTO = enderecoDtoFactory();

        String content = (new ObjectMapper()).writeValueAsString(enderecoDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/cadastro/cliente/{id}/endereco", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.cadastroClienteController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString(
                                "{\"cep\":\"Cep\",\"rua\":\"Rua\",\"bairro\":\"Bairro\",\"complemento\":\"alice.liddell@example.org\",\"cidade\":\"Cidade"
                                        + "\",\"estado\":\"Estado\"}")))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/v1/cadastro/cliente/1/endereco/arquivo"));
    }

    private EnderecoDTO enderecoDtoFactory() {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setComplemento("alice.liddell@example.org");
        enderecoDTO.setBairro("Bairro");
        enderecoDTO.setRua("Rua");
        enderecoDTO.setCep("Cep");
        enderecoDTO.setCidade("Cidade");
        enderecoDTO.setEstado("Estado");
        return enderecoDTO;
    }

    private Cliente clienteFactory(Endereco endereco) {
        Cliente cliente = clienteSemNomeFactory(new Conta(), endereco);
        return cliente;
    }

    private Endereco enderecoFactory() {
        Endereco endereco = new Endereco();
        endereco.setComplemento("alice.liddell@example.org");
        endereco.setBairro("Bairro");
        endereco.setRua("Rua");
        endereco.setId(123L);
        endereco.setCep("Cep");
        endereco.setCidade("Cidade");
        endereco.setEstado("Estado");
        return endereco;
    }


    private Conta contaFactory(Cliente cliente) {
        Conta conta = new Conta();
        conta.setNumeroConta("Numero Conta");
        conta.setCodigoBanco("Codigo Banco");
        conta.setAgencia("Agencia");
        conta.setId(123L);
        conta.setCliente(cliente);
        return conta;
    }

    private Cliente clienteSemNomeFactory(Conta conta, Endereco endereco1) {
        Cliente cliente1 = new Cliente();
        cliente1.setConta(conta);
        cliente1.setId(123L);
        cliente1.setEndereco(endereco1);
        return cliente1;
    }

}

