package arbolExpresionAritmetica;

import java.util.Stack;

/**
 * Representa un árbol de expresión aritmética.
 * El árbol se construye a partir de una expresión aritmética en notación infija,
 * y permite evaluarla o mostrarla en notación postfija (polaca inversa).
 * 
 * @author Carlos Roberto Vázquez Villegas
 */

public class ArbolEA {
    private NodoEA raiz;

    /**
     * Contructor vacío que inicializa el árbol con una raíz nula.
     */
    public ArbolEA() {
        this.raiz = null;
    }

    /**
     * Constructor que construye el árbol de expresión a partir 
     * de una cadena de texto con la expresión aritmética.
     * 
     * @param expresion La expresión aritmética en notación infija.
     */
    public ArbolEA(String expresion) {
        this.raiz = crearArbolEA(expresion);
    }

    
    /**
     * Obtiene la raíz del árbol de expresión.
     *  
     * @return La raíz del árbol de expansión.
     */
    public NodoEA getRaiz() {
        return raiz;
    }

    /**
     * Establece la raíz del árbol de expresión.
     * 
     * @param raiz El nodo raíz a establecer.
     */
    public void setRaiz(NodoEA raiz) {
        this.raiz = raiz;
    }

    /**
     * Muestra el árbol de expansión en notación postfija (polaca inversa).
     * Utiliza un recorrido en postorden para imprimir la expresión.
     */
    public void mostrarArbol() {
        Stack<NodoEA> pilaPostOrden = new Stack<>();
        notacionPolacaInversa(pilaPostOrden);

        while(!pilaPostOrden.isEmpty()) {
            NodoEA actual = pilaPostOrden.pop();

            if(actual.getOperador() == '\u0000') {
                System.out.print(actual.getOperando()+ " ");
            } else {
                System.out.print(actual.getOperador()+ " ");
            }
        }
        System.out.println();
    }

    /**
     * Crea un subárbol con los dos operandos y el operador en el nodo raíz.
     * 
     * @param operando1 El primer operando.
     * @param operando2 El segundo operando.
     * @param operador El operador que se coloca como nodo raíz.
     * @return El nodo raíz del subárbol.
     */
    private NodoEA crearSubArbol(NodoEA operando1, NodoEA operando2, NodoEA operador) {
        operador.setIzquierdo(operando1);
        operador.setDerecho(operando2);
        return operador;
    }

    /**
     * Determina la prioridad de un operador.
     * 
     * @param c El operador a evaluar.
     * @return La prioridad del operador (1, 2, 3, o 0 si no es un operador válido).
     */
    private int prioridadOperador(char c) {
        return switch (c) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> 0;
        };
    }

    /**
     * Determina si un carácter es un operador válido.
     * 
     * @param c El carácter a evaluar.
     * @return true si el carácter es un operador, false de lo contrario.
     */
    private boolean esOperador(char c) {
        return switch (c) {
            case '+', '-', '*', '/', '^', '(', ')' -> true;
            default -> false;
        };
    }

    /**
     * Crea el árbol de expansión a partir de una cadena de texto que representa la expresión aritmética.
     * Utiliza el algoritmo de Shunting Yard para convertir la notación infija en un árbol binario.
     * 
     * @param expresion La expresión aritmética en notación infija.
     * @return El nodo raíz del árbol de expresión.
     */
    public NodoEA crearArbolEA(String expresion) {
        Stack<NodoEA> pilaOperadores = new Stack<>();
        Stack<NodoEA> pilaExpresiones = new Stack<>();
        NodoEA token, operando1, operando2, operador;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < expresion.length(); i++) {
            char caracterActual = expresion.charAt(i);

            if(Character.isDigit(caracterActual)) {
                sb.append(caracterActual);
                if(i == expresion.length()-1 || !Character.isDigit(expresion.charAt(i+1))) {
                    token = new NodoEA(Integer.parseInt(sb.toString()));
                    pilaExpresiones.push(token);
                    sb.setLength(0);
                }
            } else if(esOperador(caracterActual)) {
                token = new NodoEA(caracterActual);
                switch(caracterActual) {
                    case '(' -> pilaOperadores.push(token);
                    case ')' -> {
                        while(!pilaOperadores.isEmpty() && pilaOperadores.peek().getOperador() != '(') {
                            operando2 = pilaExpresiones.pop();
                            operando1 = pilaExpresiones.pop();
                            operador = pilaOperadores.pop();
                            operador = crearSubArbol(operando1, operando2, operador);
                            pilaExpresiones.push(operador);
                        }
                        pilaOperadores.pop(); // Se elimina '(' de la pila de operadores
                    }
                    default -> {
                        while(!pilaOperadores.isEmpty() && prioridadOperador(caracterActual) <= prioridadOperador(pilaOperadores.peek().getOperador())) {
                            operando2 = pilaExpresiones.pop();
                            operando1 = pilaExpresiones.pop();
                            operador = pilaOperadores.pop();
                            operador = crearSubArbol(operando1, operando2, operador);
                            pilaExpresiones.push(operador);
                        }
                        pilaOperadores.push(token);
                    }
                }
            } 
        } // Aquí termina la iteración
        
        while(!pilaOperadores.isEmpty()) {
            operando2 = pilaExpresiones.pop();
            operando1 = pilaExpresiones.pop();
            operador = pilaOperadores.pop();
            operador = crearSubArbol(operando1, operando2, operador);
            pilaExpresiones.push(operador);
        }
        return pilaExpresiones.pop();
    }

    /**
     * Convierte el árbol de expresión a notación polaca inversa utilizando un recorrido postorden.
     * 
     * @param pila La pola donde se almacenarán los nodos en postorden.
     */
    private void notacionPolacaInversa(Stack<NodoEA> pila) {
        Stack<NodoEA> pilaAux = new Stack<>();
        recorridoPostOrden(raiz, pilaAux);

        while(!pilaAux.isEmpty()) {
            pila.push(pilaAux.pop());
        }
    }

    /**
     * Realiza un recorrido postorden del árbol de expresión y agrega los nodos a la pila.
     * 
     * @param raiz El nodo raíz del subárbol.
     * @param pila La pila donde se almacenarán los nodos en postorden.
     */
    private void recorridoPostOrden(NodoEA raiz, Stack<NodoEA> pila) {
        if(raiz != null) {
            recorridoPostOrden(raiz.getIzquierdo(), pila);
            recorridoPostOrden(raiz.getDerecho(), pila);
            pila.push(raiz);
        }
    }

    /**
     * Evalúa la expresión aritmética representada por el árbol y devuelve el resultado.
     * 
     * @return El resultado de la evaluación de la expresión.
     */
    public double evaluarExpresion() {
        Stack<Double> pilaEvaluacion = new Stack<>(); 
        Stack<NodoEA> pilaPostOrden = new Stack<>();
        notacionPolacaInversa(pilaPostOrden);

        while(!pilaPostOrden.isEmpty()) {
            NodoEA actual = pilaPostOrden.pop();

            if(actual.getOperador() == '\u0000') {
                pilaEvaluacion.push((double)actual.getOperando());
            } else {
                double operando2 = pilaEvaluacion.pop();
                double operando1 = pilaEvaluacion.pop();
                double resultado = evalua(operando1, operando2, actual.getOperador());
                pilaEvaluacion.push(resultado);
            }
        }
        return pilaEvaluacion.pop();
        // return evalua(raiz);
    }

    /**
     * Evalúa una operación entre dos operandos.
     * 
     * @param operando1 El primer operando.
     * @param operando2 El segundo operando.
     * @param operador El operdaor operador que se va a aplicar.
     * @return El resultado de la operación.
     */
    private double evalua(double operando1, double operando2, char operador) {
        return switch(operador) {
            case '+' -> operando1 + operando2;
            case '-' -> operando1 - operando2;
            case '*' -> operando1 * operando2;
            case '/' -> operando1 / operando2;
            case '^' -> Math.pow(operando1, operando2);
            default -> throw new IllegalArgumentException("Operador no válido");
        };
    }

    /* private double evalua(NodoEA raiz) {
        if(!esOperador(raiz.getOperador())) {
            return raiz.getOperando();
        } else {
            double izq = evalua(raiz.getIzquierdo());
            double der = evalua(raiz.getDerecho());
            return switch(raiz.getOperador()) {
                case '^' -> Math.pow(izq, der);
                case '*' -> izq * der;
                case '/' -> izq / der;
                case '+' -> izq + der;
                case '-' -> izq - der;
                default -> throw new IllegalArgumentException("Operador no válido.");
            };
        }
    } */
}