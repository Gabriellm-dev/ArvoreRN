## Árvore Rubro Negra

Projeto realizado em Java para a disciplina de Construção em Estrutura de Dados.

Uma árvore rubro negra é um tipo de árvore binária de busca balanceada, uma estrutura de dados inventada em 1972 e que foi denominada de "Árvores Binárias B Simétricas". São muito usadas para organizar dados que possam ser comparáveis. Os nós folhas não são relevantes e não contém dados

- Cada nó é considerado vermelho ou preto
- A raiz da árvore é sempre um nó preto.
- Qualquer nó nulo é preto.
- Se um nó for vermelho, seus filhos serão sempre pretos.
- Um nó vermelho não pode ter qualquer filho vermelho.
- Qualquer caminho de um nó para um nó nulo deve conter o mesmo número de nós pretos.

## Métodos Desenvolvidos
### Inserção de um elemento
    - Verificação da raiz nula: Verifica se a raiz (root) é nula. Se for, o novo nó é inserido como raiz.
    - Decisão de subárvore: Se a raiz não for nula, o algoritmo decide se o novo nó deve ser inserido na subárvore esquerda ou direita, com base no valor do nó.
    - Inserção recursiva: O algoritmo insere o novo nó na subárvore correspondente, utilizando um processo recursivo.
    - Verificação de violações: Após a inserção, verifica-se se ocorreram violações das propriedades da árvore rubro-negra.
    - Correção das violações: Se houver violações, são aplicadas rotações para reequilibrar a árvore e manter suas propriedades.
    - Violação da Propriedade de Cor dos Nós:
        - O nó recém-inserido é vermelho, mas seu pai também é vermelho. Isso viola a regra que proíbe dois nós vermelhos consecutivos na árvore. Neste caso, precisamos reequilibrar a árvore fazendo rotações e ajustando as cores dos nós para restaurar as propriedades da árvore rubro-negra.
    - Violação da Propriedade da Raiz:
        - A raiz da árvore é vermelha. Nesse caso, simplesmente trocamos a cor da raiz para preto para manter a propriedade da raiz.
    -  Violação da Propriedade dos Nós Adjacentes:
        - Um nó vermelho tem um nó vermelho como filho direito. Esta violação é corrigida por rotações.
    - Violação da Propriedade dos Tios:
        - Um nó vermelho tem um tio vermelho. Aqui, precisamos recolorir e fazer rotações para restaurar as propriedades da árvore rubro-negra.
    - Violação da Propriedade dos Avós:
        - O nó recém-inserido, seu pai e tio estão todos vermelhos. Neste caso, ajustamos as cores e fazemos rotações para restaurar a propriedade.
    -Rotação à esquerda (left rotation):
        - Necessária quando um nó está desequilibrado à direita.
        - Realizada quando o filho direito de um nó é vermelho, enquanto o filho esquerdo é preto ou nulo.
        - O objetivo é mover o filho direito para a posição do pai e fazer o pai se tornar o filho esquerdo do filho direito.
        - Essa rotação é aplicada para manter a árvore balanceada e preservar as propriedades da árvore rubro-negra.
    - Rotação à direita (right rotation):
        - Necessária quando um nó está desequilibrado à esquerda.
        - Realizada quando o filho esquerdo de um nó é vermelho, enquanto o filho esquerdo do filho esquerdo também é vermelho.
        - O objetivo é mover o filho esquerdo para a posição do pai e fazer o pai se tornar o filho direito do filho esquerdo.
        - Essa rotação é aplicada para manter a árvore balanceada e preservar as propriedades da árvore rubro-negra.
### Remoção de um elemento

### Altura de um nó 
    - Verificação do nó nulo: Verifica se o nó passado como argumento é nulo. Se for, retorna -1.
    - Cálculo da altura: Utiliza um método privado recursivo para calcular a altura da subárvore a partir do nó dado.
    - Retorno da altura: Retorna a altura calculada, que é o máximo entre as alturas das subárvores esquerda e direita, mais 1.
## Instruções para compilação e execução.
