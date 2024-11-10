package arbolAVL;

public class OperacionesAVL {
    // Obtener la altura de un nodo
    public static int obtenerAltura(NodoAVL nodo) {
        if (nodo == null) return 0;
        return nodo.altura;
    }
        // Obtener el factor de balance de un nodo
    public static int obtenerBalance(NodoAVL nodo) {
        if (nodo == null) return 0;
        return obtenerAltura(nodo.izquierdo) - obtenerAltura(nodo.derecho);
    }

        // Rotación simple a la derecha obtenido el pseudocodigo en al documentacion anexada
    public static NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL T2 = x.derecho;

        x.derecho = y;
        y.izquierdo = T2;

        y.altura = Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho)) + 1;
        x.altura = Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho)) + 1;

        return x;
    }

    // Rotación simple a la izquierda obtenido el pseudocodigo en al documentacion anexada
    public static NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        x.altura = Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho)) + 1;
        y.altura = Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho)) + 1;

        return y;
    }
}
