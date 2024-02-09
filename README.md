### API Boardcamp com Spring

## 📝 Descrição
A API Boardcamp é um sistema de gestão para uma locadora de jogos de tabuleiro. Em um contexto onde os jogos eletrônicos dominam, a API busca trazer de volta a diversão dos jogos de tabuleiro de forma acessível e econômica. Inspirada no conceito das antigas locadoras de VHS, a Boardcamp permite que os usuários aluguem jogos de tabuleiro por um período determinado, sem a necessidade de realizar um grande investimento na compra dos mesmos.

## ✅ Funcionalidades
#### Estrutura do Projeto:


- Estrutura de arquivos e pastas seguindo padrões convencionais, com as camadas controllers, services, repositories, models e dtos.
- Implementação de GlobalExceptionHandler para lidar com erros de requisição.

#### Jogos:

- Listagem de jogos disponíveis.
- Inserção de novos jogos no catálogo.

#### Clientes:

- Busca de clientes por ID.
- Inserção de novos clientes na base de dados.

#### Aluguéis:

- Listagem de todos os aluguéis registrados.
- Inserção de novos aluguéis com validação automática de disponibilidade de jogos.
- Finalização de aluguéis com cálculo automático de taxas de atraso, se aplicável.
