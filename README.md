# Bairro em Dia

Global Solution 2026.2 · Microservice and Web Engineering & IT Services · FIAP  
Lucas Ferreira de Oliveira — RM 557945  
Guilherme Ferreira Negro Garcia — RM 558102

No tema CitizenTech, o Bairro em Dia registra problemas urbanos como postes apagados e buracos. Esta entrega implementa a API de relatos e o acompanhamento pelas equipes de atendimento. Ao abrir ou atualizar um atendimento, o serviço envia a etapa ao relato e espera a confirmação antes de responder.

| Módulo | Responsabilidade | Porta |
|---|---|---|
| eureka-server | Registro e descoberta dos serviços | 8761 |
| api-gateway | Entrada HTTP, roteamento com LoadBalancer e Swagger | 8082 |
| relatos-service | CRUD de relatos e transições de status | Aleatória |
| atendimentos-service | CRUD de atendimentos e integração síncrona | Aleatória |

Os serviços usam Java 25 e Spring Boot 4.0.9. Cada serviço separa Controller, Service, Repository, Entity e DTO. O Flyway cria e popula uma base MySQL independente por serviço. Os dados iniciais são fictícios.

## Executar em um ambiente limpo

Pré-requisitos: JDK 25, Maven 3.9+, Docker com Compose v2. A partir da raiz do repositório:

```bash
java -version
mvn -version
docker compose up -d --wait
mvn clean package
```

Em quatro terminais, execute um comando por terminal, a partir da raiz. Inicie o Eureka primeiro e aguarde a inicialização antes dos demais:

```bash
java -jar eureka-server/target/eureka-server-1.0.0.jar
java -jar relatos-service/target/relatos-service-1.0.0.jar
java -jar atendimentos-service/target/atendimentos-service-1.0.0.jar
java -jar api-gateway/target/api-gateway-1.0.0.jar
```

Confira o [Eureka](http://localhost:8761) até os serviços aparecerem como UP. O Gateway pode levar alguns segundos para atualizar sua lista de instâncias. Abra o [Swagger UI](http://localhost:8082/swagger-ui/index.html) e selecione Relatos ou Atendimentos. No IntelliJ, importe o POM da raiz, selecione o JDK 25 e execute as quatro classes Application na mesma ordem.

Para iniciar duas instâncias de Relatos, execute o mesmo JAR em outro terminal. O Spring escolhe outra porta e o Eureka registra outro ID. Ambas utilizam a mesma base do serviço de Relatos. Atendimentos funciona da mesma forma.

As bases ficam disponíveis em localhost, portas 3307 e 3308. Os valores padrão de conexão são para uso local. Para substituir a conexão, configure DB_URL, DB_USER e DB_PASSWORD no processo do serviço correspondente. EUREKA_URL configura o servidor de descoberta. Finalize os processos Java com Ctrl+C e use `docker compose down` para desligar os bancos preservando os dados.

## Exercitar as APIs

As rotas públicas são `/api/v1/relatos` e `/api/v1/atendimentos`. Ambas oferecem GET da coleção, GET por ID, POST, PUT por ID e DELETE por ID. A criação devolve 201 e Location, a remoção devolve 204. A API retorna detalhes de problemas para erros de validação, recursos ausentes, conflitos e indisponibilidade.

Use o Swagger ou os exemplos abaixo em Bash. Anote os IDs retornados pelo POST. Os exemplos usam 2 para ambos após iniciar bancos vazios, pois cada migration insere um registro inicial.

```bash
base=http://localhost:8082
curl -i -X POST "$base/api/v1/relatos" -H 'Content-Type: application/json' \
  -d '{"titulo":"Poste apagado","descricao":"Sem luz na esquina","categoria":"Iluminação","logradouro":"Rua das Flores, 120","bairro":"Centro"}'
curl -i -X POST "$base/api/v1/atendimentos" -H 'Content-Type: application/json' \
  -d '{"relatoId":2,"equipe":"Iluminação","responsavel":"Equipe de plantão","descricao":"Vistoria agendada","prioridade":"NORMAL","status":"EM_TRIAGEM"}'
curl -i -X PUT "$base/api/v1/atendimentos/2" -H 'Content-Type: application/json' \
  -d '{"relatoId":2,"equipe":"Iluminação","responsavel":"Equipe de plantão","descricao":"Vistoria iniciada","prioridade":"NORMAL","status":"EM_ATENDIMENTO"}'
curl -i "$base/api/v1/relatos/2"
```

O relato começa RECEBIDA. Ao abrir o atendimento ele passa a EM_TRIAGEM. A atualização pode avançar para EM_ATENDIMENTO e depois RESOLVIDA. O serviço aceita repetir a mesma etapa, mas recusa saltos e regressões. Para conferir o fallback, desligue todas as instâncias de Relatos, tente avançar um atendimento e verifique a resposta 503. A alteração local é revertida e pode ser reenviada após restabelecer Relatos.

O Feign chama `relatos-service` por nome. O LoadBalancer resolve a instância pelo Eureka e o circuit breaker do Resilience4j aciona o fallback. A rota interna de status não está exposta no Gateway. O PUT de status é idempotente, o que permite repetir uma chamada após timeout. Cada banco confirma sua própria transação. Uma falha após a confirmação remota pode exigir repetir a operação para reconciliar os dados. Use as APIs em ambiente acadêmico local.

## Referências de aula

As aulas de JPA e CRUD orientaram as camadas e os contratos. Os exemplos de Eureka, Gateway, OpenFeign e fallback orientaram descoberta e comunicação. As aulas de Swagger, Docker Compose, MySQL e Flyway orientaram a documentação e a execução local. Consulte também os [exemplos da disciplina](https://github.com/cidarosa/microservice-and-web-engineering-2026).
