import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeVisualization extends JPanel {

    private static final int NODE_WIDTH = 40;
    private static final int NODE_HEIGHT = 40;
    private static final int LEVEL_HEIGHT = 60;

    private Node root;
    private RedBlackTree redBlackTree;

    public BinaryTreeVisualization() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();

        JButton insertButton = new JButton("Inserir elemento");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog("Digite o elemento a ser inserido:");
                if (inputValue != null) {
                    int element = Integer.parseInt(inputValue);
                    redBlackTree.insert(element);
                    setRoot(redBlackTree.getRoot());
                    repaint();
                }
            }
        });
        buttonPanel.add(insertButton);


        JButton removeButton = new JButton("Remover elemento");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog("Digite o elemento a ser removido:");
                if (inputValue != null) {
                    int element = Integer.parseInt(inputValue);
                    redBlackTree.delete(element);
                    setRoot(redBlackTree.getRoot());
                    repaint();
                }
            }
        });
        buttonPanel.add(removeButton);



        JButton heightButton = new JButton("Calcular altura de um nó");
        heightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog("Digite o elemento do nó para calcular a altura:");
                if (inputValue != null) {
                    int nodeValue = Integer.parseInt(inputValue);
                    Node node = findNode(root, nodeValue);
                    if (node != null) {
                        int height = redBlackTree.getHeight(node);
                        JOptionPane.showMessageDialog(null, "Altura do nó " + nodeValue + ": " + height);
                    } else {
                        JOptionPane.showMessageDialog(null, "Nó não encontrado na árvore.");
                    }
                }
            }
        });
        buttonPanel.add(heightButton);

        add(buttonPanel, BorderLayout.NORTH);

        JLabel spaceLabel = new JLabel();
        spaceLabel.setPreferredSize(new Dimension(1, 100)); // Ajuste a altura conforme necessário
        add(spaceLabel, BorderLayout.CENTER); // Adiciona o espaço entre o botão e a árvore
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (root != null) {
            int treeWidth = getTreeWidth(root) * LEVEL_HEIGHT;
            int treeHeight = getTreeHeight(root) * LEVEL_HEIGHT;
            int startX = (getWidth() - treeWidth) / 2;
            int startY = (getHeight() - LEVEL_HEIGHT) / 2; // Centraliza verticalmente
            drawTree(g, startX + treeWidth / 2, startY, root, 0, treeWidth);
        }
    }

    private void drawTree(Graphics g, int x, int y, Node node, int level, int treeWidth) {
        if (node != null) {
            g.setColor(node.getColor() == NodeColor.RED ? Color.RED : Color.BLACK);
            g.fillOval(x - NODE_WIDTH / 2, y - NODE_HEIGHT / 2, NODE_WIDTH, NODE_HEIGHT);
            g.setColor(Color.white);
            g.drawString(String.valueOf(node.getData()), x - 5, y + 5);

            int xOffset = treeWidth / (1 << (level + 1)); // Calcula o espaçamento horizontal entre os nós

            if (node.getLeftChild() != null) {
                drawLine(g, x, y, x - xOffset, y + LEVEL_HEIGHT);
                drawTree(g, x - xOffset, y + LEVEL_HEIGHT, node.getLeftChild(), level + 1, treeWidth);
            }
            if (node.getRightChild() != null) {
                drawLine(g, x, y, x + xOffset, y + LEVEL_HEIGHT);
                drawTree(g, x + xOffset, y + LEVEL_HEIGHT, node.getRightChild(), level + 1, treeWidth);
            }
        }
    }

    private void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
    }

    private Node findNode(Node root, int value) {
        if (root == null || root.getData() == value) {
            return root;
        }

        if (value < root.getData()) {
            return findNode(root.getLeftChild(), value);
        } else {
            return findNode(root.getRightChild(), value);
        }
    }

    public void setRedBlackTree(RedBlackTree redBlackTree) {
        this.redBlackTree = redBlackTree;
        setRoot(redBlackTree.getRoot());
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    private int getTreeWidth(Node node) {
        if (node == null) return 0;

        int width = 1;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            int levelWidth = queue.size();
            while (levelWidth-- > 0) {
                Node current = queue.poll();
                if (current.getLeftChild() != null) {
                    queue.offer(current.getLeftChild());
                    width++;
                }
                if (current.getRightChild() != null) {
                    queue.offer(current.getRightChild());
                    width++;
                }
            }
        }

        return width;
    }

    private int getTreeHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getTreeHeight(node.getLeftChild()), getTreeHeight(node.getRightChild()));
    }
}
