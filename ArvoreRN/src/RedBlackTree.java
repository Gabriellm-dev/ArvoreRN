public class RedBlackTree implements Tree{

    // representa a raiz da arvore
    private Node root;

    public Node getRoot() {
        return root;
    }


    //implementa a travessia pela arvore, verifica se a raiz não é nula, se for chama o método para realizar a travessia em ordem
    @Override
    public void traverse() {
        if (root != null)
            inOrderTraversal(root);
    }

    // Método para atravessar a árvore em ordem, chama a si recursivamente para visitar os nos da arvore em ordem.
    private void inOrderTraversal(Node node) {
        if (node.getLeftChild() != null)
            inOrderTraversal(node.getLeftChild());
        System.out.println(node + " - " + node.getColor());

        if (node.getRightChild() != null)
            inOrderTraversal(node.getRightChild());
    }

    // Método para realizar a rotação a direita em torno do nó especifico
    private void rightRotate(Node node){
        System.out.println("Rotating to the right on Node" + node);
        Node tempLeftNode = node.getLeftChild();
        node.setLeftChild(tempLeftNode.getRightChild());
        
        if (node.getLeftChild() != null)
            node.getLeftChild().setParent(node);
        
        tempLeftNode.setParent(node.getParent());
        
        if (tempLeftNode.getParent() == null)
            root = tempLeftNode;
        else if (node == node.getParent().getLeftChild())
            node.getParent().setLeftChild(tempLeftNode);
        else
            node.getParent().setRightChild(tempLeftNode);

        tempLeftNode.setRightChild(node);
        node.setParent(tempLeftNode);
    }

    // Método para realizar a rotação a esquerda em torno do nó especifico
    private void leftRotate(Node node){
        System.out.println("Rotating to the left on Node" + node);
        Node tempRightNode = node.getRightChild();
        node.setRightChild(tempRightNode.getLeftChild());

        if (node.getRightChild() != null)
            node.getRightChild().setParent(node);

        tempRightNode.setParent(node.getParent());

        if (tempRightNode.getParent() == null)
            root = tempRightNode;
        else if (node == node.getParent().getLeftChild())
            node.getParent().setLeftChild(tempRightNode);
        else
            node.getParent().setRightChild(tempRightNode);

        tempRightNode.setLeftChild(node);
        node.setParent(tempRightNode);
    }


    // Método para inserir um novo nó na árvore.
    @Override
    public void insert(int data) {
        Node node = new Node(data);
        root = insertIntoTree(root, node);

        fixViolations(node);
    }

    // implementação para corrigir violações das propriedades da arvore apos a insercao
    private void fixViolations(Node node) {
        Node parentNode = null;
        Node grandParentNode = null;

        while (node != root && node.getColor() != NodeColor.BLACK && node.getParent() != null && node.getParent().getColor() == NodeColor.RED) {
            parentNode = node.getParent();
            grandParentNode = node.getParent().getParent();

            if (parentNode == grandParentNode.getLeftChild()) {
                Node uncle = grandParentNode.getRightChild();
                if (uncle != null && uncle.getColor() == NodeColor.RED) {
                    grandParentNode.setColor(NodeColor.RED);
                    parentNode.setColor(NodeColor.BLACK);
                    uncle.setColor(NodeColor.BLACK);
                    node = grandParentNode;
                } else {
                    if (node == parentNode.getRightChild()) {
                        leftRotate(parentNode);
                        node = parentNode;
                        parentNode = node.getParent();
                    }
                    rightRotate(grandParentNode);
                    NodeColor tempColor = parentNode.getColor();
                    parentNode.setColor(grandParentNode.getColor());
                    grandParentNode.setColor(tempColor);
                    node = parentNode;
                }
            } else {
                Node uncle = grandParentNode.getLeftChild();
                if (uncle != null && uncle.getColor() == NodeColor.RED) {
                    grandParentNode.setColor(NodeColor.RED);
                    parentNode.setColor(NodeColor.BLACK);
                    uncle.setColor(NodeColor.BLACK);
                    node = grandParentNode;
                } else {
                    if (node == parentNode.getLeftChild()) {
                        rightRotate(parentNode);
                        node = parentNode;
                        parentNode = node.getParent();
                    }
                    leftRotate(grandParentNode);
                    NodeColor tempColor = parentNode.getColor();
                    parentNode.setColor(grandParentNode.getColor());
                    grandParentNode.setColor(tempColor);
                    node = parentNode;
                }
            }
        }

        if (root.getColor() == NodeColor.RED) {
            root.setColor(NodeColor.BLACK);
        }
    }

    // Método para inserir um novo nó na arvore de forma recursiva
    private Node insertIntoTree(Node root, Node node) {
        if (root == null) return node;

        if (node.getData() < root.getData()){
            root.setLeftChild(insertIntoTree(root.getLeftChild(), node));
            root.getLeftChild().setParent(root);
        } else if (node.getData() > root.getData()) {
            root.setRightChild(insertIntoTree(root.getRightChild(), node));
            root.getRightChild().setParent(root);
        }

        return root;
    }

    // Método público para obter a altura de um nó
    public int getHeight(Node node) {
        if (node == null) {
            return -1; // A altura de um nó nulo é -1
        } else {
            return getHeightRecursive(node);
        }
    }

    // Método privado recursivo para calcular a altura de um nó
    private int getHeightRecursive(Node node) {
        if (node == null) {
            return 0; // A altura de um nó nulo é 0
        } else {
            int leftHeight = getHeightRecursive(node.getLeftChild());
            int rightHeight = getHeightRecursive(node.getRightChild());

            // Retorna o maior valor entre a altura da subárvore esquerda e direita, mais 1
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    public void remove(int data) {
        root = removeNode(root, data);
        // Após a remoção, é necessário garantir que a raiz seja preta
        if (root != null) {
            root.setColor(NodeColor.BLACK);
        }
    }

    private Node removeNode(Node node, int data) {
        // Caso base: se o nó for nulo, retornar nulo
        if (node == null) {
            return null;
        }

        // Buscar o nó a ser removido na subárvore esquerda
        if (data < node.getData()) {
            node.setLeftChild(removeNode(node.getLeftChild(), data));
        }
        // Buscar o nó a ser removido na subárvore direita
        else if (data > node.getData()) {
            node.setRightChild(removeNode(node.getRightChild(), data));
        }
        // Nó encontrado, então remover
        else {
            // Caso 1: Nó a ser removido tem zero ou um filho
            if (node.getLeftChild() == null || node.getRightChild() == null) {
                // Nó temporário para armazenar o filho não nulo (ou null)
                Node temp = node.getLeftChild() != null ? node.getLeftChild() : node.getRightChild();

                // Caso especial: o nó a ser removido é vermelho
                if (node.getColor() == NodeColor.RED) {
                    return temp; // Simplesmente remover o nó
                } else {
                    // Caso especial: o nó a ser removido é preto
                    // e não tem filhos (ou apenas um filho preto)
                    if (temp != null) {
                        // Filho não nulo substitui o nó removido
                        temp.setParent(node.getParent());
                        temp.setColor(NodeColor.BLACK); // Filho preto substitui nó preto
                    }
                    // Retornar o filho (pode ser null)
                    return temp;
                }
            }
            // Caso 2: Nó a ser removido tem dois filhos
            else {
                // Encontrar o nó sucessor (o menor nó na subárvore direita)
                Node successor = findSuccessor(node.getRightChild());
                // Substituir o nó a ser removido pelo sucessor
                node.setData(successor.getData());
                // Remover o sucessor da subárvore direita
                node.setRightChild(removeNode(node.getRightChild(), successor.getData()));
            }
        }

        // Reequilibrar a árvore
        return fixAfterDeletion(node);
    }


    private Node fixAfterDeletion(Node node) {
        // Caso 1: Nó é a raiz (node == root)
        if (node.getParent() == null) {
            return node; // Nada a fazer, a árvore permanece balanceada
        }

        Node sibling = getSibling(node);
        Node parent = node.getParent();

        // Caso 2: Irmão é vermelho
        if (sibling != null && sibling.getColor() == NodeColor.RED) {
            // Realizar rotações e trocar cores para equilibrar a árvore
            if (isLeftChild(sibling)) {
                rightRotate(parent);
            } else {
                leftRotate(parent);
            }
            // Atualizar as referências após a rotação
            sibling.setColor(NodeColor.BLACK);
            parent.setColor(NodeColor.RED);
            sibling = getSibling(node);
        }

        // Caso 3: Pai, irmão e sobrinhos são pretos
        if (parent.getColor() == NodeColor.BLACK && (sibling == null || sibling.getColor() == NodeColor.BLACK)) {
            // Trocar cor do irmão para vermelho
            if (sibling != null) {
                sibling.setColor(NodeColor.RED);
            }
            // Rebalancear a árvore recursivamente
            return fixAfterDeletion(parent);
        }

        // Caso 4: Irmão e sobrinhos são pretos, mas o pai é vermelho
        if (parent.getColor() == NodeColor.RED && (sibling == null || sibling.getColor() == NodeColor.BLACK)) {
            // Trocar cores entre pai e irmão
            parent.setColor(NodeColor.BLACK);
            if (sibling != null) {
                sibling.setColor(NodeColor.RED);
            }
            // Árvore permanece balanceada
            return node;
        }

        // Caso 5: O irmão é preto, mas pelo menos um sobrinho é vermelho
        if (sibling != null && sibling.getColor() == NodeColor.BLACK) {
            // Verificar se o sobrinho mais próximo ao nó é vermelho
            if (isLeftChild(sibling) && (sibling.getRightChild() != null && sibling.getRightChild().getColor() == NodeColor.RED)) {
                // Rotação à direita no irmão e trocar cores
                leftRotate(sibling);
                sibling.setColor(NodeColor.RED);
                sibling.getParent().setColor(NodeColor.BLACK);
            } else if (!isLeftChild(sibling) && (sibling.getLeftChild() != null && sibling.getLeftChild().getColor() == NodeColor.RED)) {
                // Rotação à esquerda no irmão e trocar cores
                rightRotate(sibling);
                sibling.setColor(NodeColor.RED);
                sibling.getParent().setColor(NodeColor.BLACK);
            }
        }

        return node;
    }

    private Node findSuccessor(Node node) {
        // Encontrar o nó mais à esquerda na subárvore direita
        while (node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        return node;
    }

    private Node getSibling(Node node) {
        Node parent = node.getParent();
        // Se não houver pai, não há irmão
        if (parent == null) {
            return null;
        }
        // Se o nó for filho esquerdo, retorna o irmão direito e vice-versa
        if (node == parent.getLeftChild()) {
            return parent.getRightChild();
        } else {
            return parent.getLeftChild();
        }
    }

    private boolean isLeftChild(Node node) {
        return node == node.getParent().getLeftChild();
    }
}