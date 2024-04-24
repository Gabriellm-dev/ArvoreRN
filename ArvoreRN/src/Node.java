
/* A classe Node representa um nó em uma árvore rubro-negra. Cada nó contém
informações sobre seu valor, sua cor (vermelha ou preta), referências para seus
filhos esquerdo e direito, e uma referência para seu nó pai. Além disso, a classe
inclui métodos para acessar e modificar essas informações.
 */

public class Node {

    private int data; // Armazena o valor do nó
    private NodeColor color; // Armazena a cor do nó (vermelho ou preto)
    private Node leftChild; // Referência para o filho esquerdo
    private Node rightChild; // Referência para o filho direito
    private Node Parent; // Referência para o nó pai

    private int level; // Nível do nó na árvore

    // Construtor que inicializa um nó com um dado valor e cor vermelha.
    public Node(int data) {
        this.data = data;
        this.color = NodeColor.RED;
    }

    // Método toString para representação textual do nó
    @Override
    public String toString() {
        return " " + this.data;
    }
    // Métodos getter e setter para o valor do nó
    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    // Métodos getter e setter para a cor do nó
    public NodeColor getColor() {
        return color;
    }

    public void setColor(NodeColor color) {
        this.color = color;
    }

    // Métodos getter e setter para o filho esquerdo
    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    // Métodos getter e setter para o filho direito
    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    // Métodos getter e setter para o nó pai
    public Node getParent() {
        return Parent;
    }

    public void setParent(Node parent) {
        Parent = parent;
    }
}