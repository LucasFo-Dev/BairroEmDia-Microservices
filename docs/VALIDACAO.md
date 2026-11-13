# Validação da entrega

O Maven empacotou os quatro módulos com JDK 25 e Spring Boot 4.0.9. Eureka e Gateway iniciaram no ambiente de verificação. A integração completa com MySQL e o Compose ainda precisam ser executados em uma máquina com Docker. O ambiente de verificação não dispõe de Docker e bloqueou a abertura do socket do servidor MySQL local.

## Conferência em máquina com Docker

1. Execute os comandos do README em uma máquina limpa e confira os serviços UP no Eureka.
2. Abra os dois contratos no Swagger e execute o fluxo de criação e atualização do README.
3. Verifique 201 com Location nos POSTs, 200 nas leituras e atualizações e 204 nas exclusões.
4. Envie um campo obrigatório vazio e consulte um ID inexistente para verificar 400 e 404.
5. Inicie uma segunda instância de Relatos e confira as duas portas no Eureka.
6. Pare todas as instâncias de Relatos e tente avançar o atendimento. Confira o 503 e confirme que a etapa do atendimento foi preservada.
