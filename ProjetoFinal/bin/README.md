#  Projeto Final :shopping_cart:

## Grupo

- Daniel Santos
- Daniela Falk
- José Quaresma
- Lyllys Galhardo
- Roberta Bastos
- Rosimar Rabelo
- Sylvia Rodrigues

### Seu grupo deve criar um aplicativo de E-Commerce atendendo os seguintes requisitos:

- [ ] Utilizar um sistema de login.(JWT)
- [ ] Um Cliente poderá se cadastrar livremente.
- [x] Para o cadastro de cliente deverá informar os dados mostrados nas tabelas abaixo. 
- [ ] O endereço deverá ser validado através da API Via Cep.
- [ ] Após logado o Cliente poderá fazer as seguintes operações:(Com exceção das duas últimas (11 e 12), todas não poderão ser realizadas sem o envio do token)
- [ ] Atualizar seus próprios dados pessoais (como endereço, telefone, menos CPF).
- [ ] Deletar sua própria conta.
- [ ] Criar um novo Pedido 
- [ ] Editar um pedido que não esteja com status de finalizado.
- [ ] Finalizar um pedido, alterar seu status para finalizado. Ao finalizar o pedido enviar e-mail para o cliente informando data de entrega, produtos, quantidades e valor final do pedido.
- [x] Visualizar todas as categorias ou uma específica pelo nome.
- [x] Visualizar todos os produtos ou um específico pelo nome.


### Recursos que devem estar disponíveis sem o usuário estar logado no sistema:

- [x] Visualizar todas as categorias ou uma específica pelo nome.
- [x] Criar uma nova categoria.
- [x] Editar uma categoria.
- [x] Deletar uma categoria.
- [x] Visualizar todos os produtos.
- [x] Visualizar um produto específico pelo nome.
- [x] Criar um novo produto (Com imagem).
- [x] Editar um produto.
- [x] Deletar um produto.
- [x] Visualizar todos os pedidos.
- [x] Excluir algum pedido.

### Observações:

- [x] CPF deve ser válido.
- [x] Produto não poderá ter valores negativos.
- [x] Todas as exceptions devem ser tratadas.
- [x] A API deverá utilizar como documentação a ferramenta do Swagger.


### Desafio Extra (Opcional): 

- [ ] Criar uma opção de esqueci minha senha com envio de um código de verificação para o e-mail e posterior verificação se esse código pertence ao cliente.



#### Regras: 

- [x] Ser feito em grupo
- [x] Utilizar GIT

#### TODO:

- [ ] Confirmar se todas as exceptions estão sendo tratadas.
- [x] Criar DTO de apresentação dos Pedidos.
- [x] Validar o CEP.
- [ ] LOGIN!!!
- [ ] Criar Put no cliente para atualizar: endereço e telefone (menos CPF).
- [ ] Criar Delete para o cliente.
- [ ] Criar Put e Delete do pedido.