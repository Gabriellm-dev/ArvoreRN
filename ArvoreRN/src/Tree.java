
// interface chamada Tree, que define dois métodos que devem ser implementados por qualquer classe que represente uma árvore:

public interface Tree {

    // Este método é responsável por realizar a travessia da árvore.
        void traverse();

    // Este método é usado para inserir um novo nó na árvore.
        void insert(int data);

        void delete(int data);

        int getHeight(Node node);
    }
