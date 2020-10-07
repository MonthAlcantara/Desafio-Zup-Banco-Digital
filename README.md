# Desafio-Zup-Banco-Digital
API resolução de desafio Bootcamp Zup

## Contexto
Todo cliente precisa solicitar uma proposta de criação de nova conta de pessoa física antes de qualquer outra coisa e este é justamente processo que precisamos implementar. Só que tal processo precisa ser dividido em várias etapas, caso o contrário o cliente seria obrigado a passar um grande número de informações e poderíamos perder tudo por conta de uma falha de internet, falta de bateria no celular etc. A ideia aqui é minimizar isso.

## Passo 1
** Necessidade **

No primeiro passo precisamos de algumas informações básicas.
nome do cliente
sobrenome do cliente
email
cnh
data de nascimento

** Restrições **

nome do cliente é obrigatório
sobrenome do cliente é obrigatório
email é obrigatório e precisa ter formato de email
cnh é obrigatória e precisa respeitar o formato
data de nascimento obrigatório, no passado e tem que ter mais de 18 anos
email não pode ser duplicado
cpf não pode ser duplicado 

** Resultado esperado **

status 201 e header location preenchido para o próximo passo do cadastro
status 400 em caso de falha de qualquer validação e json de retorno com as informações.

## Passo 2

Agora vamos para o segundo passo do processo. A pessoa precisa colocar os dados do endereço

** Necessidade **

cep
rua
bairro
complemento
cidade(pode ser campo aberto)
estado(pode ser campo aberto)

** Restrições **

cep obrigatório e no formato adequado
rua obrigatório
bairro obrigatório
complemento obrigatório
cidade obrigatória
estado obrigatório
tudo que é obrigatório do passo 1 precisa estar correto

** Resultado esperado **

status 201 e header location preenchido para o próximo passo do cadastro
status 400 em caso de falha de qualquer validação e json de retorno com as informações.

## Passo 3

Agora vamos para o terceiro passo do processo. A pessoa precisa enviar a foto de frente e verso da cnh.

** Necessidade **

arquivo que representa a frente do cnh (não precisa ser o cnh de verdade)
arquivo que representa o fundo do cnh inútil quando deveria ser a carteira de motorista (não precisa ser o cnh de verdade)
tudo que é obrigatório anteriormente tem que estar completo

** Restrições **

arquivos são obrigatórios

** Resultado esperado **

status 201 e header location preenchido para o próximo passo do cadastro
status 400 em caso de falha de qualquer validação e json de retorno com as informações.
status 404 caso a proposta que supostamente deveria estar associada com esse passo não exista.
Se os passos anteriores não tiverem completos, retorne 422

## Passo 4

Agora precisamos mostrar tudo que foi enviado para o cliente do aceite.

** Necessidade **

Retornar um json com todas as informações da proposta para que a aplicação cliente exiba e a pessoa possa confirmar tudo!

** Restrição **

Todos os passos anteriores precisam ter sido completados

** Resultado esperado **

Em caso de aceite, informamos que vamos criar a conta dela e um email será enviado(próxima funcionalidade)
Em caso de não aceite o sistema registra aquela proposta e vai mandar um email implorando para ela aceitar(próxima funcionalidade)
Se os passos anteriores não tiverem completos, retorne 422

## Passo 5 (Em desenvolvimento)

Agora precisamos lidar com o aceite da proposta.

** Necessidade **

Caso a proposta seja aceita pelo cliente, uma nova conta deve ser criada em função daquela proposta. Essa nova conta tem as seguintes informações:

Agencia (4 dígitos e pode ser gerado aleatório)
Conta (8 dígitos e pode ser gerado aleatório)
Código do banco (3 dígitos e pode ser fixo)
A partir da conta, precisamos chegar na proposta que a originou.
Saldo igual a zero.
Com a conta criada, um email deve ser enviado para a dona da proposta informando a criação da nova conta e os respectivos dados.

** Detalhes adicionais **

O email não precisa ser real. Vai ser legal se o sistema ficar preparado para lidar com emails fake em dev e reais em produção.
O processo de criação de conta só acontece depois que um sistema externo aceita as informações do documento daquela pessoa. Como toda chamada remota, algo de errado pode acontecer. Precisamos tentar pelo menos 2x antes de desistir.
Caso o sistema externo de aceite de documentos não tenha liberado, precisamos deixar um status na proposta indicando que ela ainda não foi liberada.
Caso o sistema externo de aceite tenha liberado, além de criamos a conta deixamos um status na proposta informando que ela foi liberada.

** Restrições **

O processo de criação deve ser disparado de forma a não bloquear o retorno relativo ao aceite do usuário. A pessoa aceita e depois a conta é criada e o email informativo enviado. Isso é muito importante!

** Resultado esperado **

status 200(conta vai ser criada)
