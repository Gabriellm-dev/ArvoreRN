public class RedBlackTree implements Tree {

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
    private void rightRotate(Node node) {
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
    private void leftRotate(Node node) {
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

        if (node.getData() < root.getData()) {
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

    public void delete(int data) {
        root = deleteNode(root, data);
    }

    private Node deleteNode(Node root, int data) {
        if (root == null)
            return root;

        if (data < root.getData())
            root.setLeftChild(deleteNode(root.getLeftChild(), data));
        else if (data > root.getData())
            root.setRightChild(deleteNode(root.getRightChild(), data));
        else {
            if (root.getLeftChild() == null || root.getRightChild() == null) {
                Node temp = root.getLeftChild() != null ? root.getLeftChild() : root.getRightChild();
                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = minValueNode(root.getRightChild());
                root.setData(temp.getData());
                root.setRightChild(deleteNode(root.getRightChild(), temp.getData()));
            }
        }

        if (root == null)
            return root;

        // Fixar violações após a exclusão
        fixViolationsAfterDeletion(root);

        return root;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.getLeftChild() != null)
            current = current.getLeftChild();
        return current;
    }

    private void fixViolationsAfterDeletion(Node node) {
        if (node == null)
            return;

        if (node == root)
            return;

        Node sibling = null;
        Node parent = node.getParent();
        boolean isLeftChild = (node == parent.getLeftChild());

        if (isLeftChild) {
            sibling = parent.getRightChild();
            if (sibling != null && sibling.getColor() == NodeColor.RED) {
                // Caso 1: O irmão é vermelho
                sibling.setColor(NodeColor.BLACK);
                parent.setColor(NodeColor.RED);
                leftRotate(parent);
                sibling = parent.getRightChild();
            }

            if ((sibling == null || sibling.getColor() == NodeColor.BLACK) &&
                    (sibling == null || sibling.getLeftChild() == null || sibling.getLeftChild().getColor() == NodeColor.BLACK) &&
                    (sibling == null || sibling.getRightChild() == null || sibling.getRightChild().getColor() == NodeColor.BLACK)) {
                // Caso 2: O irmão é preto e ambos os filhos são pretos
                sibling.setColor(NodeColor.RED);
                fixViolationsAfterDeletion(parent);
            } else {
                if (sibling != null && sibling.getColor() == NodeColor.BLACK) {
                    // Caso 3: O irmão é preto e pelo menos um filho é vermelho
                    if (sibling.getLeftChild() != null && sibling.getLeftChild().getColor() == NodeColor.RED) {
                        if (sibling.getRightChild() != null)
                            sibling.getRightChild().setColor(NodeColor.BLACK);
                        sibling.setColor(NodeColor.RED);
                        rightRotate(sibling);
                        sibling = parent.getRightChild();
                    }
                }

                // Caso 4: O irmão é preto, o filho direito do irmão é vermelho e o filho esquerdo do irmão é preto
                sibling.setColor(parent.getColor());
                parent.setColor(NodeColor.BLACK);
                if (sibling.getRightChild() != null)
                    sibling.getRightChild().setColor(NodeColor.BLACK);
                leftRotate(parent);
            }
        } else {
            sibling = parent.getLeftChild();
            if (sibling != null && sibling.getColor() == NodeColor.RED) {
                // Caso 1: O irmão é vermelho
                sibling.setColor(NodeColor.BLACK);
                parent.setColor(NodeColor.RED);
                rightRotate(parent);
                sibling = parent.getLeftChild();
            }

            if ((sibling == null || sibling.getColor() == NodeColor.BLACK) &&
                    (sibling == null || sibling.getLeftChild() == null || sibling.getLeftChild().getColor() == NodeColor.BLACK) &&
                    (sibling == null || sibling.getRightChild() == null || sibling.getRightChild().getColor() == NodeColor.BLACK)) {
                // Caso 2: O irmão é preto e ambos os filhos são pretos
                sibling.setColor(NodeColor.RED);
                fixViolationsAfterDeletion(parent);
            } else {
                if (sibling != null && sibling.getColor() == NodeColor.BLACK) {
                    // Caso 3: O irmão é preto e pelo menos um filho é vermelho
                    if (sibling.getRightChild() != null && sibling.getRightChild().getColor() == NodeColor.RED) {
                        if (sibling.getLeftChild() != null)
                            sibling.getLeftChild().setColor(NodeColor.BLACK);
                        sibling.setColor(NodeColor.RED);
                        leftRotate(sibling);
                        sibling = parent.getLeftChild();
                    }
                }

                // Caso 4: O irmão é preto, o filho esquerdo do irmão é vermelho e o filho direito do irmão é preto
                sibling.setColor(parent.getColor());
                parent.setColor(NodeColor.BLACK);
                if (sibling.getLeftChild() != null)
                    sibling.getLeftChild().setColor(NodeColor.BLACK);
                rightRotate(parent);
            }
        }
    }
}