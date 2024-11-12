package arbolHeap;

public class Nodo {
    int valor;
    Nodo izquierda, derecha, padre;

    public Nodo(int valor){
        this.valor = valor;
        this.derecha = null;
        this.izquierda = null;
        this.padre = null;
    }
}