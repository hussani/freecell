# freecell
Freecell implementation of MAC5710

Esta solução tem a implementação de múltiplas pilhas controladas em um único vetor.
A estrutura base pode ser encontrada no pacote [ds](src/main/java/com/hussani/freecell/ds).

## Instruções para compilar e executar

### Local
#### Requisitos
- [Maven](http://maven.apache.org/)
- JDK 11+

Compile o projeto
```shell
mvn clean compile
```

Execute o projeto
```shell
java -classpath target/classes com.hussani.freecell.App
```

## Instruções para o jogo

### Compreendendo o tabuleiro

Abaixo segue um tabuleiro de exemplo.
```text
Free cells:
0               null           
1               null           
2               null           
3               null           

Foundation
DIAMONDS        null           
SPADES          null           
CLUBS           null           
HEARTS          null           

Tableau
1               [{6-CLUBS}, {7-HEARTS}, {6-SPADES}, {12-DIAMONDS}, {10-SPADES}, {4-SPADES}, {3-SPADES}]
2               [{9-CLUBS}, {1-HEARTS}, {10-CLUBS}, {5-CLUBS}, {7-SPADES}, {11-DIAMONDS}, {12-HEARTS}]
3               [{5-DIAMONDS}, {9-DIAMONDS}, {10-DIAMONDS}, {7-CLUBS}, {13-SPADES}, {3-DIAMONDS}, {1-SPADES}]
4               [{4-CLUBS}, {11-SPADES}, {9-SPADES}, {8-DIAMONDS}, {9-HEARTS}, {10-HEARTS}, {7-DIAMONDS}]
5               [{1-DIAMONDS}, {3-HEARTS}, {13-CLUBS}, {4-HEARTS}, {8-CLUBS}, {12-SPADES}]
6               [{12-CLUBS}, {8-HEARTS}, {13-HEARTS}, {2-HEARTS}, {2-DIAMONDS}, {3-CLUBS}]
7               [{6-HEARTS}, {2-CLUBS}, {11-CLUBS}, {4-DIAMONDS}, {5-HEARTS}, {1-CLUBS}]
8               [{5-SPADES}, {13-DIAMONDS}, {11-HEARTS}, {6-DIAMONDS}, {2-SPADES}, {8-SPADES}]
```

#### Cartas

As cartas são representadas no jogo pelo padrão `{<número>-<naipe>}`. 
Os naipes são apresentados em inglês.

| Naipe em português | Naipe em inglês |
|:------------------:|:---------------:|
|      Espadas       |     Spades      |
|       Copas        |     Hearts      |
|       Ouros        |    Diamonds     |
|        Paus        |      Clubs      |

Desta forma, no jogo quando vimos `{6-CLUBS}`, significa que a carta é 6 de paus.

Damas, valetes e reis são representados por seus números correspondentes.

| Figuras | Número |
|---------|--------|
| Valete  | 11     |
| Damas   | 12     |
| Reis    | 13     |
| Ás      | 1      |

Por esta lógica, a carta `{13-SPADES}` é o rei de espadas, 
já `{1-HEARTS}` é o ás de copas.

#### Free cells
Temos 4 células livres, que, quando vazias, qualquer carta do topo de uma pilha de jogo pode ser movida.
Estas células são representadas pelos índices 0, 1, 2 e 3.

#### Foundation
Aqui estão as pilhas de fundação, ou comummente chamadas de pilhas de saída.
Estas pilhas são representados pelos naipes das cartas em inglês.

#### Tabuleiro de jogo (Tableau)
Aqui estão as pilhas de jogo. Existem 8 pilhas de jogo, que são representados pelos números de 1 a 8.
As pilhas devem ser lidas com a primeira carta como a do final da pilha. 
Deste modo, a última carta deve ser lida como o topo da pilha.

Somente o final da pilha pode ser movido para uma célula livre ou para a fundação.

### Movimentos

#### Movimento de carta para célula livre

Para movimentar o topo de uma pilha para uma célula livre, devemos escrever:
```text
move game <número da pilha> freecell
```
Este comando irá mover o topo da pilha escolhida para a primeira célula livre vazia.
Não é possível escolher qual das celulas livres vazias uma carta será movida.

#### Movimento de carta para fundação

A fundação deve ser montada da carta de número mais baixo para o número mais alto.

Para movimentar o topo de uma pilha para uma fundação, devemos escrever:
```text
move game <número da pilha> foundation
```
Para mover uma carta da celula livre para a fundação, devemos escrever:
```text
move freecell <número da célula> foundation
```

Não é necessário especificar a fundação.
Cartas de fundação não podem ser movidas novamente.

#### Movimento de carta para tabuleiro

Para mover cartas entre as pilhas de jogo você deve escrever:
```text
move game <número da pilha> <número da pilha>
```

Para mover cartas de uma célula livre para uma pilha de jogo, devemos escrever:
```text
move freecell <número da célula> <número da pilha>
```

#### Comandos extras

Você pode reiniciar o jogo com o comando:
```text
reset
```

Para sair do jogo, devemos escrever:
```text
exit
```