# Checkpoint 5 — Bug Hunt PetFiap

> Copie este arquivo para a raiz do seu repositório com o nome **README.md**
> e preencha todas as seções.

## Identificação

**Grupo:** ___

| Integrante | RM | Turma |
|---|---|---|
| Massayoshi Bando Fogaca e Silva             | 561779 | 2CCPH |
| Leonardo Augusto Bacelar da Cunha           | 565564 | 2CCPH |
| Pedro Gabriel Mendes Soares Leite           | 562242 | 2CCPH |
| Lucca Rosseto Rezende                       | 564180 | 2CCPH |
| Guilherme Verrilho Peres                    | 563981 | 2CCPH |
| Alexandre Campao Fernandes Shneider Bertini | 563346 | 2CCPH |

| Campo |                     |
|---|---------------------|
| **Total de bugs corrigidos** | 12 / 12             |
| **Total de ajustes de Clean Code** | 6 / 6               |
| **Total de testes novos escritos** | 6 / 6               |
| **Suíte final (Run As → JUnit Test)** | 26 testes, 0 falhas |

---

## Parte 1 — Bugs encontrados

> Uma linha por bug, na ordem em que você os encontrou. Use a numeração dos seus
> commits (`fix: bug01 ...`). Preencha TODAS as colunas — metade da nota está aqui.

| # | Sintoma observado (o que fiz/vi) | Causa raiz (arquivo e linha aproximada) | Correção aplicada | Conceito da disciplina |
|---|---|---|---|---|
| bug01 | Ao montar um atendimento com um pet, o nome informado não era armazenado e o objeto ficava com `petNome` nulo. | `AtendimentoBuilder.java`, método `comPet()` (~linha 23): o parâmetro era atribuído a ele mesmo em vez do atributo da classe. | Corrigida a atribuição para `this.petNome = petNome`. | Encapsulamento / `this` |
| bug02 | O Builder permitia construir atendimento sem nome ou porte do pet. | `AtendimentoBuilder.java`, método `construir()` (~linha 40): não havia validação dos campos obrigatórios. | Adicionadas validações de `petNome` e `petPorte` antes da criação do objeto. | Builder / validação |
| bug03 | Ao informar o tipo `TOSA`, a Factory retornava um objeto `Banho`. | `AtendimentoFactory.java`, método `criar()` (~linha 17): o `case "TOSA"` instanciava `Banho` em vez de `Tosa`. | Alterada a criação para `new Tosa(...)`. | Factory / Polimorfismo |
| bug04 | Os dados do pet e do atendimento não eram preenchidos corretamente em uma `ConsultaVeterinaria`. | `ConsultaVeterinaria.java`, construtor parametrizado (~linha 16): chamava `super()` sem os parâmetros do atendimento. | Corrigida a chamada para `super(protocolo, petNome, petPorte, tutorNome, dataHora)`. | Herança / construtor da superclasse |
| bug05 | `getInstancia()` retornava instâncias diferentes do `GeradorProtocolo`. | `GeradorProtocolo.java`, método `getInstancia()` (~linha 16): a instância criada não era armazenada em `instancia`. | A nova instância passou a ser atribuída ao atributo estático antes do retorno. | Singleton |
| bug06 | Os preços de banho para porte pequeno e grande estavam invertidos. | `Banho.java`, método `calcularPreco()` (~linha 26): `PEQUENO` retornava 100 e o caso final retornava 60. | Corrigidos os valores para `PEQUENO = 60`, `MEDIO = 80` e `GRANDE = 100`. | Regra de negócio |
| bug07 | A `Tosa` retornava a duração da classe pai em vez de 60 minutos. | `Tosa.java`, método de duração (~linha 41): o método tinha assinatura diferente de `getDuracaoMinutos()` da superclasse. | Corrigida a assinatura para `getDuracaoMinutos()` e adicionado `@Override`. | Override x Overload / Polimorfismo |
| bug08 | O sistema não reconhecia corretamente conflito de horário quando os valores eram iguais, mas estavam em objetos diferentes. | `AgendaService.java`, método `agendar()` (~linhas 29–31): comparação de `String` e `LocalDateTime` usando `==`. | Substituída a comparação por `Objects.equals()`. | `==` x `.equals()` |
| bug09 | Buscar um atendimento inexistente retornava `null` em vez de lançar a exceção prevista. | `AgendaService.java`, método `buscarPorId()` (~linha 41): um `catch (Exception)` capturava a exceção lançada pelo `orElseThrow()` e retornava `null`. | Removido o tratamento que engolia a exceção e mantido o `orElseThrow()`. | Tratamento de exceções |
| bug10 | Agendamentos com data passada eram processados e o repository podia ser consultado antes da rejeição. | `AgendaService.java`, método `agendar()` (~linha 22): a validação da data ocorria depois da consulta ao repository. | A validação da data foi colocada antes de qualquer chamada ao repository. | Ordem das operações / regra de negócio |
| bug11 | Um atendimento `CONCLUIDO` podia ser cancelado e virar `CANCELADO`. | `Atendimento.java`, método `cancelar()` (~linha 63): não havia validação do status atual antes da alteração. | O cancelamento passou a ser permitido somente quando o status é `AGENDADO`. | Máquina de estados / exceções |
| bug12 | O Singleton e o contador de protocolos não eram seguros para acesso concorrente. | `GeradorProtocolo.java`, métodos `getInstancia()` e `proximo()` (~linhas 16–25): havia condição de corrida na criação da instância e no incremento do contador. | Métodos críticos passaram a ser sincronizados, mantendo uma única instância e geração sequencial segura. | Singleton / concorrência |

## Parte 2 — Ajustes de Clean Code

| # | Onde estava | Qual princípio/boas práticas era violado | O que eu mudei |
|---|---|---|---|
| clean01 | `AtendimentoFactory`, parâmetros do método `criar()` | Nomes de parâmetros pouco descritivos dificultavam a leitura e a compreensão da intenção. | Renomeei os parâmetros para `protocolo`, `tipo`, `petNome`, `petPorte`, `tutorNome` e `dataHora`. |
| clean02 | `GeradorProtocolo` | A classe de domínio gerava saída diretamente no console durante sua criação. | Removi o `System.out.println()` do construtor. |
| clean03 | `AgendaService` | O Service misturava sua responsabilidade de negócio com impressão de informações no console. | Removi a impressão do recibo do fluxo de agendamento. |
| clean04 | `AtendimentoController`, método não utilizado `calcularDescontoFidelidade()` | Código morto aumentava a complexidade sem participar de nenhum fluxo da aplicação. | Removi o método que não era utilizado pelo sistema. |
| clean05 | `BanhoTest`, método `deveCalcularPrecoDeAcordoComOPorte()` | O teste possuía mais de uma verificação independente, contrariando o princípio de uma verificação por teste. | Reestruturei o teste para realizar uma única verificação dos resultados esperados. |
| clean06 | Tratamento de exceções do `AtendimentoController` | Os blocos de tratamento de exceção ficavam repetidos junto aos endpoints, misturando fluxo principal e tratamento de erros. | Centralizei o tratamento das exceções em um handler, mantendo os códigos HTTP apropriados. |

## Parte 3 — Testes novos (regras que estavam sem cobertura)

> Uma linha por teste novo (`test: ...`). "Regra coberta" é o comportamento do
> contrato (seção 3 do enunciado) que o teste protege. Em "Resultado", diga se o
> teste ficou vermelho ao ser escrito (revelou bug — qual?) ou verde de cara
> (regra já estava correta).

| # | Teste escrito (classe.método) | Regra coberta | Resultado ao escrever (vermelho/verde) |
|---|---|---|---|
| teste01 | `ConsultaVeterinariaTest.deveCustar150ReaisIndependentementeDoPorte()` | A consulta deve custar R$ 150,00 independentemente do porte do pet. | **Verde de cara** — a regra já estava correta. |
| teste02 | `TosaTest.deveDurar60Minutos()` | A Tosa deve ter duração de 60 minutos. | **Vermelho** — revelou o `bug07`; após a correção, ficou verde. |
| teste03 | `BanhoTest.deveCalcularPrecoDeAcordoComOPorte()` | O preço do banho deve ser R$ 60,00 para pequeno, R$ 80,00 para médio e R$ 100,00 para grande. | **Vermelho** — revelou o `bug06`; após a correção, ficou verde. |
| teste04 | `AgendaServiceTest.deveRecusarAgendamentoComDataNoPassadoSemConsultarRepository()` | Data/hora no passado deve gerar `IllegalArgumentException` sem consultar ou salvar no repository. | **Vermelho** — revelou o `bug10`; após a correção, ficou verde. |
| teste05 | `AgendaServiceTest.deveCancelarAtendimentoAgendado()` | Um atendimento `AGENDADO` pode ser cancelado e deve passar para `CANCELADO`. | **Verde de cara** — a regra já estava correta. |
| teste06 | `AgendaServiceTest.deveRecusarCancelamentoDeAtendimentoConcluido()` | Um atendimento `CONCLUIDO` não pode ser cancelado e deve gerar `StatusInvalidoException`. | **Vermelho** — revelou o `bug11`; após a correção, ficou verde. |

---

## Parte 4 — Perguntas de reflexão

> Responda com suas palavras, 5 a 10 linhas cada, **usando o código real do
> projeto como exemplo**. Respostas genéricas de tutorial não pontuam.

### 1. A suíte como contrato (Aula 15)
O projeto chegou com 20 testes, 9 vermelhos. Descreva como você usou as
mensagens de falha (ex.: `expected: <Rex> but was: <null>`) para caçar os bugs.
O que a suíte de testes tem de melhor do que testar tudo na mão com curl?
Resposta:Usei os testes como referência para descobrir o comportamento esperado do sistema. Quando aparecia uma falha como expected: <Rex> but was: <null>, eu analisava o teste e depois o código para encontrar a causa. Isso ajudou a descobrir bugs no Builder, Factory, Singleton e Service. A suíte também permitiu validar cada correção rapidamente. Diferente de testar com curl, os testes são automáticos, repetíveis e verificam também exceções e interações com o repository.

### 2. Mock e injeção de dependência (Aulas 13 a 15)
No `AgendaServiceTest`, o `@Mock` cria um `AtendimentoRepository` falso e o
`@InjectMocks` o injeta no service. Explique a relação disso com o `@Autowired`
que o Spring faz em produção — quem "injeta" em cada mundo, e por que o teste
consegue rodar sem banco e sem subir o Spring?
Resposta:No teste, o @Mock cria um AtendimentoRepository falso e o @InjectMocks coloca esse mock no AgendaService. Em produção, quem faz a injeção das dependências é o Spring. Nos testes, esse trabalho é feito pelo Mockito. Por isso conseguimos testar o service sem banco, sem Oracle e sem iniciar o Spring. Assim, o teste fica mais rápido e focado apenas na regra que queremos validar.

### 3. `==` vs `.equals()` (Aula 7)
Um dos bugs fazia o agendamento duplicado passar pela verificação de conflito.
Explique por que `==` entre Strings e `LocalDateTime` falhou aqui, por que ele
"funciona por sorte" com literais como `"Rex"`, e o que a sua correção mudou.
Resposta:O conflito de horário estava usando == para comparar objetos. Esse operador compara referências, enquanto equals() compara o conteúdo. Por isso dois objetos diferentes poderiam representar o mesmo pet e horário e ainda assim não serem considerados iguais. Com "Rex", pode parecer que funciona em alguns casos por causa do pool de Strings, mas isso não é seguro. A correção passou a comparar corretamente os valores usando Objects.equals().

### 4. Sobrescrita vs sobrecarga (Aula 7)
Um dos bugs compilava sem nenhum erro: um método parecia sobrescrever
`getDuracaoMinutos`, mas na verdade criava uma assinatura nova. Explique a
diferença entre override e overload nesse caso e por que a anotação `@Override`
teria impedido o bug.
Resposta:Na Tosa, o método de duração tinha uma assinatura diferente da classe Atendimento. Isso criou uma sobrecarga em vez de sobrescrever o método original. Por causa disso, o método da classe pai continuava sendo usado e a duração estava errada. Corrigimos a assinatura e adicionamos @Override. Essa anotação ajudaria porque o compilador avisaria caso o método não estivesse realmente sobrescrevendo outro.
### 5. Singleton manual vs bean do Spring (Aula 14)
O `GeradorProtocolo` é um Singleton escrito à mão e causou um dos bugs.
Explique o que ele garante, qual foi o bug, e por que o `AgendaService`
(`@Service`) não corre o mesmo risco no container do Spring.
Resposta:O GeradorProtocolo usa um Singleton manual para garantir uma única instância e manter a sequência global dos protocolos. O bug acontecia porque a instância criada não era armazenada e novas instâncias eram geradas. Corrigimos isso e também protegemos o contador contra acesso concorrente. Já o AgendaService é um @Service, então sua instância é administrada pelo container do Spring.

### 6. Cobertura de testes: onde parar? (Aula 15)
Dos 6 testes novos que você escreveu, alguns ficaram vermelhos (revelaram
bugs) e outros verdes de cara (regras já corretas). Vale a pena manter os que
ficaram verdes? Em um projeto real com prazo, o que você priorizaria testar:
caminho feliz, caminhos de erro, ou 100% de cobertura? Justifique.
Resposta:Os testes que passaram de primeira continuam importantes porque protegem regras que já estavam corretas. Os que falharam ajudaram a encontrar bugs escondidos. Em um projeto real, eu priorizaria primeiro as regras de negócio mais importantes e os cenários de erro. Depois ampliaria a cobertura para os demais caminhos. Ter 100% de cobertura é útil, mas não garante sozinho que todas as regras importantes estejam protegidas.

---

## Parte 5 — Espaço livre (opcional)

Alguma dificuldade, dúvida ou comentário sobre o checkpoint?

```

