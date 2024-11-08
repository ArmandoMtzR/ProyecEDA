package arbolAVL;

class NodoAVL {
    int valor;
    NodoAVL izquierdo, derecho;
    int altura;

    public NodoAVL(int valor) {
        this.valor = valor;
        this.altura = 1;
    }
}

public class AVLTree {
    private NodoAVL raiz;

    // Obtener la altura de un nodo
    private int obtenerAltura(NodoAVL nodo) {
        if (nodo == null) return 0;
        return nodo.altura;
    }

    // Rotación simple a la derecha
    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL T2 = x.derecho;

        x.derecho = y;
        y.izquierdo = T2;

        y.altura = Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho)) + 1;
        x.altura = Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho)) + 1;

        return x;
    }

    // Rotación simple a la izquierda
    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        x.altura = Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho)) + 1;
        y.altura = Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho)) + 1;

        return y;
    }

    // Obtener el factor de balance de un nodo
    private int obtenerBalance(NodoAVL nodo) {
        if (nodo == null) return 0;
        return obtenerAltura(nodo.izquierdo) - obtenerAltura(nodo.derecho);
    }

    // Inserción de un nodo
    public void insert(int valor) {
        raiz = insertar(raiz, valor);
    }

    private NodoAVL insertar(NodoAVL nodo, int valor) {
        if (nodo == null) return new NodoAVL(valor);

        if (valor < nodo.valor)
            nodo.izquierdo = insertar(nodo.izquierdo, valor);
        else if (valor > nodo.valor)
            nodo.derecho = insertar(nodo.derecho, valor);
        else
            return nodo;

        nodo.altura = 1 + Math.max(obtenerAltura(nodo.izquierdo), obtenerAltura(nodo.derecho));
        int balance = obtenerBalance(nodo);

        if (balance > 1 && valor < nodo.izquierdo.valor)
            return rotarDerecha(nodo);

        if (balance < -1 && valor > nodo.derecho.valor)
            return rotarIzquierda(nodo);

        if (balance > 1 && valor > nodo.izquierdo.valor) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }

        if (balance < -1 && valor < nodo.derecho.valor) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    // Buscar un valor
    public void search(int valor) {
        NodoAVL resultado = buscar(raiz, valor);
        if (resultado != null) {
            System.out.println("Valor " + valor + " encontrado en el árbol.");
        } else {
            System.out.println("Valor " + valor + " no encontrado en el árbol.");
        }
    }

    private NodoAVL buscar(NodoAVL nodo, int valor) {
        if (nodo == null || nodo.valor == valor) return nodo;
        if (valor < nodo.valor) return buscar(nodo.izquierdo, valor);
        return buscar(nodo.derecho, valor);
    }

    // Eliminar un nodo
    public void delete(int valor) {
        raiz = eliminar(raiz, valor);
    }

    private NodoAVL eliminar(NodoAVL nodo, int valor) {
        if (nodo == null) return nodo;

        if (valor < nodo.valor)
            nodo.izquierdo = eliminar(nodo.izquierdo, valor);
        else if (valor > nodo.valor)
            nodo.derecho = eliminar(nodo.derecho, valor);
        else {
            if ((nodo.izquierdo == null) || (nodo.derecho == null)) {
                NodoAVL temp = nodo.izquierdo != null ? nodo.izquierdo : nodo.derecho;
                nodo = temp != null ? temp : null;
            } else {
                NodoAVL temp = obtenerMinValorNodo(nodo.derecho);
                nodo.valor = temp.valor;
                nodo.derecho = eliminar(nodo.derecho, temp.valor);
            }
        }

        if (nodo == null) return nodo;

        nodo.altura = Math.max(obtenerAltura(nodo.izquierdo), obtenerAltura(nodo.derecho)) + 1;
        int balance = obtenerBalance(nodo);

        if (balance > 1 && obtenerBalance(nodo.izquierdo) >= 0)
            return rotarDerecha(nodo);

        if (balance > 1 && obtenerBalance(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }

        if (balance < -1 && obtenerBalance(nodo.derecho) <= 0)
            return rotarIzquierda(nodo);

        if (balance < -1 && obtenerBalance(nodo.derecho) > 0) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private NodoAVL obtenerMinValorNodo(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.izquierdo != null)
            actual = actual.izquierdo;
        return actual;
    }

    // Mostrar el árbol (inOrder traversal)
    public void display() {
        System.out.print("Árbol AVL (inOrder): ");
        inOrder(raiz);
        System.out.println();
    }

    private void inOrder(NodoAVL nodo) {
        if (nodo != null) {
            inOrder(nodo.izquierdo);
            System.out.print(nodo.valor + " ");
            inOrder(nodo.derecho);
        }
    }
}

