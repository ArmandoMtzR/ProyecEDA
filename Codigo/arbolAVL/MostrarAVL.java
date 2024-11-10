package arbolAVL;

public class MostrarAVL {
        // Mostrar el árbol (inOrder traversal)
    public static void display(AVLTree arbol) {
        System.out.print("Árbol AVL (inOrder): ");
        inOrder(arbol.raiz);
        System.out.println();
    }
    //InOrden Obtenido de la Pracica 8-9 hecha por Hugo Armando Martíenz Rosales
    public static void inOrder(NodoAVL nodo) {
        if (nodo != null) {
            inOrder(nodo.izquierdo);
            System.out.print(nodo.valor + " ");
            inOrder(nodo.derecho);
        }
    }
}
