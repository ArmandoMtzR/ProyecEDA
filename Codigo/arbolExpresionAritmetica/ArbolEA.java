package arbolExpresionAritmetica;

import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Representa un árbol de expresión aritmética.
 * El árbol se construye a partir de una expresión aritmética en notación infija,
 * y permite evaluarla la expresión en notación postfija (polaca inversa).
 * 
 * Además permite mostrar la expresión o la estructura del árbol en la terminal
 * según se requiera.
 * 
 * @author Equipo 12. Estructura de Datos y Algoritmos II, Grupo 07, FI-UNAM, 2025-1.
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
     * Constructor que construye el árbol de expresión a partir de una cadena de texto con la expresión aritmética.
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
     * Obtiene el valo del nodo, ya sea un operando o un operador.
     * 
     * @param nodo El nodo del cual obtener el valor.
     * @return El valor del nodo: operador u operando.
     */
    private String getValor(NodoEA nodo) {
        if(nodo == null) return null;

        if(nodo.getOperador() == '\u0000') {
            return String.valueOf(nodo.getOperando());
        } else {
            return String.valueOf(nodo.getOperador());
        }
    }

    /**
     * Muestra el árbol de expansión en notación infija por medio de un recorrido en inorden.
     * Utilizado para mostrar la expresión ingresada por el usuario en el menú.
     */
    public void mostrarExpresion() {
        recorridoInOrden(raiz);
        System.out.println();
    }

    /**
     * Realiza un recorrido en preorden del árbol de expresión y muestra el valor de cada nodo.
     * 
     * @param raiz El nodo raíz del subárbol.
     */
    private void recorridoInOrden(NodoEA raiz) {
        if(raiz != null) {
            recorridoInOrden(raiz.getIzquierdo());
            System.out.print(getValor(raiz)+ " ");
            recorridoInOrden(raiz.getDerecho());
        }
    }

    /**
     * Muesta la expresión en notación polaca inversa (postfija) en la terminal.
     * Utiliza el recorrido postorden para obtener los nodos en postfijo y los imprime.
     */
    public void notacionPostfija() {
        Queue<NodoEA> aux = notacionPolacaInversa();
        System.out.print("Notación Polaca Inversa: ");
        while(!aux.isEmpty()) {
            NodoEA tmp = aux.poll();
            System.out.print(getValor(tmp)+ " ");
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
     * Crea el árbol de expresión aritmética a partir de una cadena de texto que representa la 
     * expresión aritmética. Utiliza el algoritmo de Shunting Yard para convertir la notación
     * infija en un árbol binario.
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
            
            if (caracterActual == '-' && (i == 0 || esOperador(expresion.charAt(i - 1)))) { // En caso de ingresar números negativos
                sb.append(caracterActual);
            }   else if(Character.isDigit(caracterActual)) {
                sb.append(caracterActual);
                if(i == expresion.length()-1 || !Character.isDigit(expresion.charAt(i+1))) { // Si es un operando
                    token = new NodoEA(Integer.parseInt(sb.toString()));
                    pilaExpresiones.push(token);
                    sb.setLength(0);
                }
            } else if(esOperador(caracterActual)) { // Si es un operador
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
        
        while(!pilaOperadores.isEmpty()) { // En caso de que se hayan quedando elementos en la pila
            operando2 = pilaExpresiones.pop();
            operando1 = pilaExpresiones.pop();
            operador = pilaOperadores.pop();
            operador = crearSubArbol(operando1, operando2, operador);
            pilaExpresiones.push(operador);
        }
        return pilaExpresiones.pop();
    }

    /**
     * Convierte el árbol de expresión a notación polaca inversa utilizando un recorrido
     * postorden.
     * 
     * @return La cola donde se almacenan los nodos en postorden.
     */
    private Queue<NodoEA> notacionPolacaInversa() {
        Queue<NodoEA> salida = new LinkedList<>();
        recorridoPostOrden(raiz, salida);
        return salida;
    }

    /**
     * Realiza un recorrido postorden del árbol de expresión y agrega los nodos a la cola.
     * 
     * @param raiz El nodo raíz del subárbol.
     * @param cola La cola donde se almacenarán los nodos en postorden.
     */
    private void recorridoPostOrden(NodoEA raiz, Queue<NodoEA> cola) {
        if(raiz != null) {
            recorridoPostOrden(raiz.getIzquierdo(), cola);
            recorridoPostOrden(raiz.getDerecho(), cola);
            cola.add(raiz);
        }
    }

    /**
     * Realiza la operación aritmética correspondiente entre dos operandos.
     * 
     * @param operando1 El primer operando.
     * @param operando2 El segundo operando.
     * @param operador El operador a aplicar.
     * @return El resultado de la operación.
     */
    private double evalua(double operando1, double operando2, char operador) {
        return switch(operador) {
            case '+' -> operando1 + operando2;
            case '-' -> operando1 - operando2;
            case '*' -> operando1 * operando2;
            case '/' -> operando1 / operando2;
            case '^' -> Math.pow(operando1, operando2);
            default -> throw new UnsupportedOperationException("Operador no soportado: " + operador);
        };
    }

    /**
     * Evalúa la expresión aritmética representada por el árbol y devuelve el resultado.
     * 
     * @return El resultado de la evaluación de la expresión.
     */
    public double evaluarExpresion() {
        Stack<Double> pilaEvaluacion = new Stack<>(); 
        Queue<NodoEA> colaRPN = notacionPolacaInversa(); // Notación polaca inversa

        while(!colaRPN.isEmpty()) {
            NodoEA actual = colaRPN.poll();

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
    }

    /**
     * Calcula la altura del árbol de expresión, que es el número máximo de niveles.
     * 
     * @param nodo El nodo desde el cual calcular la altura.
     * @return La altura del árbol.
     */
    private int getAltura(NodoEA nodo) {
        if (nodo == null) return 0;
        return 1 + Math.max(getAltura(nodo.getIzquierdo()), getAltura(nodo.getDerecho()));
    }

    /**
     * Calcula la longitud máxima de los nodos en el árbol, basada en el valor más largo.
     * 
     * @return La longitud máxima de los valores de los nodos.
     */
    private int getMaximaLongitudNodos() {
        int max = 0;
        Queue<NodoEA> cola = new LinkedList<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            NodoEA actual = cola.poll();
            String valor = getValor(actual);
            max = Math.max(max, valor.length()); // Obtiene el máximo de los dos
            if (actual.getIzquierdo() != null) {
                cola.add(actual.getIzquierdo());
            }
            if (actual.getDerecho() != null) {
                cola.add(actual.getDerecho());
            }
        }
        return max;
    }

    /**
     * Convierte el árbol de expresión en una representación de texto por niveles.
     * Devuelve una cadena de texto con cada nodo representado por su valor.
     * 
     * @return La representación en texto del árbol por niveles.
     */
    private String getArbolToString() {
        if (this.raiz == null) return "";
    
        Queue<NodoEA> cola = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        cola.add(raiz);
    
        int altura = getAltura(raiz);
    
        for (int nivel = 0; nivel < altura; nivel++) {
            int nodosEnNivel = cola.size();
    
            for (int i = 0; i < nodosEnNivel; i++) {
                NodoEA actual = cola.poll();
                if (actual == null) {
                    sb.append("| ");
                    if (nivel < altura - 1) {
                        cola.add(null);
                        cola.add(null);
                    }
                } else {
                    sb.append("|").append(getValor(actual) + "");
                    cola.add(actual.getIzquierdo());
                    cola.add(actual.getDerecho());
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Muestra el árbol de expresión en la terminal, organizando los nodos por niveles.
     * Utiliza un formato de texto alineado para representar el árbol de manera visual.
     */
    public void mostrarArbol() {
        String[] niveles = getArbolToString().split("\n");
        StringBuilder sb = new StringBuilder();
        
        int nodosUltimoNivel = niveles[niveles.length - 1].length();
        int maxLongitud = getMaximaLongitudNodos();
        int espacioExtra = maxLongitud * 2;
    
        for (int i = 0; i < niveles.length; i++) {
            String[] nodosNivel = niveles[i].split("\\|");
    
            int espacioInicial = (nodosUltimoNivel - nodosNivel.length) * (maxLongitud + espacioExtra) / 2;
    
            for (int j = 0; j < espacioInicial; j++) {
                sb.append(" ");
            }
    
            for (int j = 0; j < nodosNivel.length; j++) {
                sb.append(String.format("%-" + maxLongitud + "s", nodosNivel[j]));
                if (j < nodosNivel.length - 1) {
                    for (int k = 0; k < maxLongitud + espacioExtra; k++) {
                        sb.append(" ");
                    }
                }
            }
            sb.append("\n");
        }
    
        System.out.println(sb.toString());
    }
}
