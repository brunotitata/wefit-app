## Como iniciar o projeto (via Docker)

1. Certifique-se que tenha o docker e docker-compose instalados em sua maquina
2. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/wefit-api.git
cd wefit-api
```

3. Execute os comandos dentro da pasta wefit-api:
```bash
./gradlew clean build
docker-compose up -d
```
 - Acesse o swagger: http://localhost:8080/swagger-ui/index.html

 - Criar nova pessoa:
```declarative
curl -X POST http://localhost:8080/pessoa \
  -H "Content-Type: application/json" \
  -d '{
        "tipoPessoa": "PESSOA_FISICA",
        "cpfResponsavel": "123.456.789-10",
        "nome": "João Silva",
        "celular": "11987654321",
        "telefone": "1132345678",
        "email": "joao@example.com",
        "confirmEmail": "joao@example.com",
        "endereco": {
          "cep": "01001-000",
          "logradouro": "Rua A",
          "numero": "100",
          "complemento": "Apto 1",
          "cidade": "São Paulo",
          "bairro": "Centro",
          "estado": "SP"
        },
        "aceitarTermos": true
      }'
```

- Buscar pessoa:
```declarative
curl -X GET http://localhost:8080/pessoa/{pessoaId}
```
