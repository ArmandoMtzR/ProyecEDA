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
        ArbolEA expTree = new ArbolEA();  // Instancia del árbol de Expresión Aritmética
        int opcion;

        do {
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
                    System.out.print("Ingrese la expresión aritmética: ");
                    String expresion = scanner.nextLine();
                    expTree.crearArbolEA(expresion);
                    System.out.println("Árbol de expansión aritmética creado con éxito.");
                    break;
                case 2:
                    System.out.println("Árbol de expansión aritmética generado:");
                    expTree.mostrarArbol();
                    break;
                case 3:
                    System.out.println("Resultado de la expresión ingresada: " + expTree.evaluarExpresion());
                    expTree = null;
                    break;
                case 0:
                    System.out.println("Saliendo de árbol de expansión aritmética...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
        System.out.println();
    }
}

