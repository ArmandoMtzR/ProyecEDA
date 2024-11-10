package arbolHeap;
import java.util.ArrayList;

/**
 * Representa un Max Heap.
 * Esto quiere decir que es un árbol binario completo, donde el nodo padre es mayor o igual a sus hijos
 * y el valor mayor esta en el root
 * @author Navarro Carbajal Fredy Emiliano
 */

public class MaxHeapTree {

    private ArrayList<Integer> heap;

    /**
     * Constructor que construye el MaxHeap utilizando como estructura de datos un ArrayList
     * 
     */
    public MaxHeapTree(){
        this.heap = new ArrayList<>();
    }

    /**
     * Inserta un nuevo valor al MaxHeap
     * @param value El nuevo valor a ingresar
     */
    public void insert(int value){
        heap.add(value);
        int indexActual = heap.size() - 1;

        while(indexActual > 0){
            int indexPadre = (indexActual - 1) / 2;
            if(heap.get(indexActual) > heap.get(indexPadre)){
                int aux = heap.get(indexActual);
                heap.set(indexActual, heap.get(indexPadre));
                heap.set(indexPadre, aux);
                indexActual = indexPadre;
            }else{
                break;
            }
        }
    }

    /**
     * Elimina una clave (dato) del MaxHeap
     * 
     * @param value El valor a eliminar del MaxHeap
     * @return Verdadero si se elimino y falso si no se elimino (si no existe)
     */
    public boolean delete(int value){
        int index = heap.indexOf(value);

        if(index == -1){
            System.out.println("El elemento " + value + " no existe");
            return false;
        }

        int lastElement = heap.remove(heap.size() - 1);
        if(index < heap.size()){
            heap.set(index, lastElement);
            heapify(index);
        }
        
        System.out.println("El elemento " + value + " fue eliminado correctamente");
        return true;
    }

    /**
     * Determina la prioridad de un operador.
     * @param i Indica el index donde se va iniciar la corrección
     */
    private void heapify(int i){
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int largest = i;

        if(l < heap.size() && heap.get(l) > heap.get(largest)){
            largest = l;
        }
        if(r < heap.size() && heap.get(r) > heap.get(largest)){
            largest = r;
        }

        if(largest != i){
            int aux = heap.get(i);
            heap.set(i, heap.get(largest));
            heap.set(largest, aux);
            heapify(largest);
        }
    }

    /**
     * Visualiza el MaxHeap
     */
    public void display(){
        if (heap.isEmpty()) {
            System.out.println("Heap vacio");
            return;
        }

        System.out.println("MaxHeap -> \n");
        for(int i = 0; i < heap.size(); i++){
            int indexPadre = (i - 1) / 2;
            if(i == 0){
                System.out.println("Root -> " + heap.get(i));
            }else if(i % 2 == 1){ 
                System.out.println("Padre -> " + heap.get(indexPadre) + " - Hijo Izquierdo -> " + heap.get(i));
            }else{
                System.out.println("Padre -> " + heap.get(indexPadre) + " - Hijo Derecho -> " + heap.get(i));
            }
        }
    }

}