package arbolAVL;

public class NodoAVL {
    int valor;
    NodoAVL izquierdo, derecho;
    int altura;

    public NodoAVL(int valor) {
        this.valor = valor;
        this.altura = 1;
    }
}
