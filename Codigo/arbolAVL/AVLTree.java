package arbolAVL;

public class AVLTree {
    public NodoAVL raiz;

    // Inserción de un nodo
    public void insert(int valor) {
        raiz = insertar(raiz, valor);
    }

    public NodoAVL insertar(NodoAVL nodo, int valor) {
        if (nodo == null) return new NodoAVL(valor);

        if (valor < nodo.valor)
            nodo.izquierdo = insertar(nodo.izquierdo, valor);
        else if (valor > nodo.valor)
            nodo.derecho = insertar(nodo.derecho, valor);
        else
            return nodo;

        nodo.altura = 1 + Math.max(OperacionesAVL.obtenerAltura(nodo.izquierdo), OperacionesAVL.obtenerAltura(nodo.derecho));
        int balance = OperacionesAVL.obtenerBalance(nodo);

        if (balance > 1 && valor < nodo.izquierdo.valor)
            return OperacionesAVL.rotarDerecha(nodo);

        if (balance < -1 && valor > nodo.derecho.valor)
            return OperacionesAVL.rotarIzquierda(nodo);

        if (balance > 1 && valor > nodo.izquierdo.valor) {
            nodo.izquierdo = OperacionesAVL.rotarIzquierda(nodo.izquierdo);
            return OperacionesAVL.rotarDerecha(nodo);
        }

        if (balance < -1 && valor < nodo.derecho.valor) {
            nodo.derecho =  OperacionesAVL.rotarDerecha(nodo.derecho);
            return  OperacionesAVL.rotarIzquierda(nodo);
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

    public NodoAVL buscar(NodoAVL nodo, int valor) {
        if (nodo == null || nodo.valor == valor) return nodo;
        if (valor < nodo.valor) return buscar(nodo.izquierdo, valor);
        return buscar(nodo.derecho, valor);
    }

    // Eliminar un nodo
    public void delete(int valor) {
        raiz = eliminar(raiz, valor);
    }

    public NodoAVL eliminar(NodoAVL nodo, int valor) {
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

        nodo.altura = Math.max(OperacionesAVL.obtenerAltura(nodo.izquierdo), OperacionesAVL.obtenerAltura(nodo.derecho)) + 1;
        int balance = OperacionesAVL.obtenerBalance(nodo);

        if (balance > 1 && OperacionesAVL.obtenerBalance(nodo.izquierdo) >= 0)
            return OperacionesAVL.rotarDerecha(nodo);

        if (balance > 1 && OperacionesAVL.obtenerBalance(nodo.izquierdo) < 0) {
            nodo.izquierdo = OperacionesAVL.rotarIzquierda(nodo.izquierdo);
            return OperacionesAVL.rotarDerecha(nodo);
        }

        if (balance < -1 && OperacionesAVL.obtenerBalance(nodo.derecho) <= 0)
            return OperacionesAVL.rotarIzquierda(nodo);

        if (balance < -1 && OperacionesAVL.obtenerBalance(nodo.derecho) > 0) {
            nodo.derecho = OperacionesAVL.rotarDerecha(nodo.derecho);
            return OperacionesAVL.rotarIzquierda(nodo);
        }

        return nodo;
    }

    public NodoAVL obtenerMinValorNodo(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.izquierdo != null)
            actual = actual.izquierdo;
        return actual;
    }
}

