import arbolExpresionAritmetica.*;
import arbolAVL.AVLTree;
import arbolHeap.MaxHeapTree;
import java.util.Scanner;
import java.util.InputMismatchException;

public class ArbolMenu {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("-----Seleccione el tipo de Árbol-----");
            System.out.println("1. Árbol AVL");
            System.out.println("2. Árbol MaxHeap");
            System.out.println("3. Árbol de Expresión Aritmética");
            System.out.println("0. Salir");
            System.out.print("Que opción deseas: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch(opcion) {
                case 1:
                    menuArbolAVL();
                    break;
                case 2:
                    menuArbolHeap();
                    break;
                case 3:
                    menuArbolExpresion();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void menuArbolAVL() {
        AVLTree avlTree = new AVLTree();  // Instancia del árbol AVL
        int opcion;

        do {
            System.out.println("\n----- Árbol AVL ------");
            System.out.println("1. Agregar clave");
            System.out.println("2. Buscar valor");
            System.out.println("3. Eliminar clave");
            System.out.println("4. Mostrar árbol");
            System.out.println("0. Regresar al menú principal");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch(opcion) {
                case 1:
                    System.out.print("Ingrese la clave a agregar: ");
                    int clave = scanner.nextInt();
                    avlTree.insert(clave);
                    break;
                case 2:
                    System.out.print("Ingrese el valor a buscar: ");
                    int valor = scanner.nextInt();
                    avlTree.search(valor);
                    break;
                case 3:
                    System.out.print("Ingrese la clave a eliminar: ");
                    int eliminar = scanner.nextInt();
                    avlTree.delete(eliminar);
                    break;
                case 4:
                    avlTree.display();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
        System.out.println();
    }

    private static void menuArbolHeap() {
        MaxHeapTree maxHeap = new MaxHeapTree();  // Instancia del árbol Red-Black
        int opcion;

        do {
            System.out.println("\n------ Árbol Max Heap -------");
            System.out.println("1. Agregar clave");
            System.out.println("2. Eliminar clave");
            System.out.println("3. Mostrar árbol");
            System.out.println("0. Regresar al menú principal");
            System.out.print("Ingresa la opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch(opcion) {
                case 1:
                    System.out.print("Ingrese la clave a agregar: ");
                    try {
                        int clave = scanner.nextInt();
                        maxHeap.insert(clave);
                    } catch (InputMismatchException e) {
                        System.out.println("Ingrese un número");
                        scanner.next(); 
                    }
                    break;
                case 2:
                    System.out.print("Ingrese la clave a eliminar: ");
                    try {
                        int eliminar = scanner.nextInt();
                        maxHeap.delete(eliminar);
                    } catch (InputMismatchException e) {
                        System.out.println("Ingrese un número");
                        scanner.next(); 
                    }
                    break;
                case 3:
                    maxHeap.display();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
        System.out.println();
    }

    private static void menuArbolExpresion() {
        ArbolEA expTree = null; // Instancia del árbol de Expresión Aritmética
        int opcion;
        
        do {
            try {
                System.out.println("\n------ Árbol de Expresión Aritmética -----");
                System.out.println("1. Ingresar expresión");
                System.out.println("2. Mostrar árbol");
                System.out.println("3. Resolver");
                System.out.println("0. Regresar al menú principal");
                System.out.print("Ingresa la opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch(opcion) {
                    case 1:
                        System.out.println("Es posible realizar las siguientes operaciones: suma(+), resta(-), multiplicación(*), división(/) y potencia(^).");
                        System.out.println("Ingrese la expresión aritmética: ");
                        String expresion = scanner.nextLine();

                        boolean valido = true;
                        for(int i = 0; i < expresion.length(); i++) {
                            char c = expresion.charAt(i);
                            if(!Character.isDigit(c) && "+-*/^()".indexOf(c) == -1 && c != ' ') {
                                valido = false; // Verifica que la entrada sea la adecuada
                                break;
                            }
                        }
                        
                        if(valido) {
                            expTree = new ArbolEA(expresion);
                            System.out.println("EL árbol de expresión aritmética se ha creado con éxito.");
                            expTree.notacionPostfija();
                        } else {
                            System.out.println("La expresión contiene caracteres no válidos. Use solo números, operadores y paréntesis.");
                        }
                        break;

                    case 2:
                        if(expTree != null) {
                            System.out.println("Árbol de expresión aritmética:");
                            expTree.mostrarArbol();
                        } else {
                            System.out.println("No se ha creado ningún árbol de expresión. Ingrese primero una expresión aritmética.");
                        }
                        break;

                    case 3:
                        if(expTree != null) {
                            System.out.println("Resultado de la expresión: "+ expTree.evaluarExpresion());
                            System.out.println("Para crear un nuevo árbol, ingrese una nueva expresión.");
                        } else {
                            System.out.println("No se ha creado ningún árbol de expresión. Ingrese primero una expresión aritmética.");
                        }
                        break;

                    case 0:
                        System.out.println("Saliendo de árbol de expresión aritmética...");
                        expTree = null;
                        break;

                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida. Por favor, ingrese un número correspondiente a las opciones del menú.");
                scanner.nextLine();
                opcion = -1;
            }
        } while (opcion != 0);
    }
}
