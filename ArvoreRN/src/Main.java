
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();

        JFrame frame = new JFrame("Visualização de Árvore Rubro Negra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        BinaryTreeVisualization visualization = new BinaryTreeVisualization();
        visualization.setRedBlackTree(redBlackTree);

        frame.add(visualization);

        frame.setVisible(true);
    }
}



