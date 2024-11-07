package arbolExpresionAritmetica;

/**
 * Represente un nodo de un árbol de expresión aritmética.
 * Cada nodo puede contener un operando o un operador.
 * Los nodos hoja contiene únicamente operandos, mientras que los operadores
 * pueden tener hasta dos hijos: izquierdo y derecho.
 * 
 * @author Carlos Roberto Vázquez Villegas
 */
public class NodoEA {
    private Integer operando;
    private Character operador;
    private NodoEA izquierdo, derecho;

    /**
     * Constructor vacío que crea un nodo sin valor de operando ni operador.
     */
    public NodoEA() {
        this.operando = null;
        this.operador = null;
        izquierdo = derecho = null;
    }

    /**
     * Constructor que crea un nodo con un operando entero.
     * 
     * @param operando El valor del operando.
     */
    public NodoEA(int operando) {
        this.operando = operando;
        this.operador = null;
        izquierdo = derecho = null;
    }

    /**
     * Constructor que crea un nodo con un operador.
     * 
     * @param operador El operador del nodo.
     */
    public NodoEA(char operador) {
        this.operando = null;
        this.operador = operador;
        izquierdo = derecho = null;
    }

    /**
     * Constructor que crea un nodo con un operador y los nodos hijo izquierdo y derecho.
     * 
     * @param operador
     * @param izquierdo
     * @param derecho
     */
    public NodoEA(char operador, NodoEA izquierdo, NodoEA derecho) {
        this.operando = null;
        this.operador = null;
        this.izquierdo = null;
        this.derecho = null;
    }

    
    /**
     * Obtiene el valor del operando del nodo.
     * 
     * @return El operando si el nodo es un operando, de lo contrario retorna 0.
     */
    public int getOperando() {
        if(this.operador == null && this.operando != null) {
            return operando;
        } else {
            return 0;
        }
    }
    
    /**
     * Establece el valor del operando para el nodo.
     * 
     * @param operando El valor del operando.
     */
    public void setOperando(int operando) {
        this.operando = operando;
    }
    
    /**
     * Obtiene el valor del operador del nodo.
     * 
     * @return El operador si el nodo es un operador, de lo contrario retorna un carácter nulo.
     */
    public char getOperador() {
        if(this.operador != null && this.operando == null) {
            return operador;
        } else {
            return '\u0000';
        }
    }
    
    /**
     * Establece el valor del operador para el nodo.
     * 
     * @param operador El valor del operador.
     */
    public void setOperador(char operador) {
        this.operador = operador;
    }

    /**
     * Obtiene el nodo hijo izquierdo.
     * 
     * @return El hijo izquierdo.
     */
    public NodoEA getIzquierdo() {
        return izquierdo;
    }

    /**
     * Establace el nodo hijo izquierdo.
     * 
     * @param izquierdo El nodo hijo izquierdo a establecer.
     */
    public void setIzquierdo(NodoEA izquierdo) {
        this.izquierdo = izquierdo;
    }

    /**
     * Obtiene el nodo hijo derecho.
     * 
     * @return El hijo derecho.
     */
    public NodoEA getDerecho() {
        return derecho;
    }

    /**
     * Establece el nodo hijo derecho.
     * 
     * @param derecho El nodo hijo derecho a establecer.
     */
    public void setDerecho(NodoEA derecho) {
        this.derecho = derecho;
    }
}