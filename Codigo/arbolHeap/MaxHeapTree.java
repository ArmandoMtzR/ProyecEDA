package arbolHeap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Representa un Max Heap utilizando una estructura de árbol
 * @author Navarro Carbajal Fredy Emiliano
 */

public class MaxHeapTree {

    private Nodo raiz;  
    private int heapSize;   
    
    public MaxHeapTree(){
        this.raiz = null;
        this.heapSize = 0;
    }

    public void insert(int valor){
        Nodo nuevoNodo = new Nodo(valor);

        if(raiz == null){
            //Si todavia no hay nodos, el nuevo nodo es la raiz
            raiz = nuevoNodo;
        }else{
            //Buscamos en que nodo de va insertar
            Nodo padre = buscarNodoPadre();
            //Verificar que hijo usar
            if(padre.izquierda == null){
                padre.izquierda = nuevoNodo;
            }else{
                padre.derecha = nuevoNodo;
            }
            //Para no romper el arbol 
            nuevoNodo.padre = padre;   
            heapifyUp(nuevoNodo);
        }
        heapSize++;
    }
    
    private Nodo buscarNodoPadre(){
        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);

        //BFS para encontrar el padre con hijos libres
        while(!cola.isEmpty()){
            Nodo padre = cola.poll();
            //Si al menos un hijo esta vacio el padre esta diponinble para usar un hijo
            if(padre.derecha == null || padre.izquierda == null){
                return padre;
            }
            cola.add(padre.izquierda);
            cola.add(padre.derecha);
        }
        return null;
    }

    private void heapifyUp(Nodo nodo){
        //Condicion si es que no cumple que el nodo padre sea el mayor y antes de llegar a la raiz
        if(nodo.padre != null && nodo.padre.valor < nodo.valor){
            // Swap entre padre e hijo
            int aux = nodo.valor;
            nodo.valor = nodo.padre.valor;
            nodo.padre.valor = aux;
        
            heapifyUp(nodo.padre);
        }
    }
   
    private void heapifyDown(Nodo nodo){
    if(nodo.izquierda == null) return;

    //Buscar el hijo mayor
    Nodo mayor = nodo.izquierda;
    if(nodo.derecha != null && nodo.derecha.valor > mayor.valor){
        mayor = nodo.derecha;
    }

    //Verifica si la propiedad de Max-Heap cumple
    if (nodo.valor >= mayor.valor){
        return;
    }

    //Swap entre el hijo mayor y el nodo
    int aux = nodo.valor;
    nodo.valor = mayor.valor;
    mayor.valor = aux;

    heapifyDown(mayor);
    }

    private Nodo buscarNodo(int valor){
        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);
        //BFS para buscar un nodo y finalizar
        while(!cola.isEmpty()){
            Nodo encontrar = cola.poll();
            if(encontrar.valor == valor){
                return encontrar;
            }
            if(encontrar.izquierda != null) cola.add(encontrar.izquierda);
            if(encontrar.derecha != null) cola.add(encontrar.derecha);
        }
        return null;
    }
 
    public void delete(int valor){
        Nodo nodoEliminar = buscarNodo(valor);
        if(nodoEliminar != null){
            deleteNode(nodoEliminar);
        }else{
            System.out.println("No existe el nodo a eliminar");
        }
    }

    private void deleteNode(Nodo nodoEliminar){
        Nodo ultimoNodo = obtenerUltimoNodo();

        //Si no se cumple la condicion se elimina directamente
        if(ultimoNodo != nodoEliminar){
            nodoEliminar.valor = ultimoNodo.valor;
            if(ultimoNodo.padre.izquierda == ultimoNodo){
                ultimoNodo.padre.izquierda = null;
            }else{
                ultimoNodo.padre.derecha = null;
            }
            //Despues de elimar un hijo intermedio verificar la estrucutra del Max-Heap
            heapifyDown(nodoEliminar);
        }else{
            //Sino cumple quiere decir que es la raiz
            if(ultimoNodo.padre != null){
                if(ultimoNodo.padre.izquierda == ultimoNodo){
                    ultimoNodo.padre.izquierda = null;
                }else{
                    ultimoNodo.padre.derecha = null;
                }
            }else{
                raiz = null;
            }
        }
        heapSize--;
    }    
  
    private Nodo obtenerUltimoNodo(){
        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);

        //BFS para obtener el ultimo nodo
        Nodo ultimoNodo = null;
        while(!cola.isEmpty()){
            ultimoNodo = cola.poll();
            if(ultimoNodo.izquierda != null){
                cola.add(ultimoNodo.izquierda);
            } 
            if(ultimoNodo.derecha != null){
                cola.add(ultimoNodo.derecha);
            } 
        }
        return ultimoNodo;
    }

    public void display() {
        if(raiz == null){
            System.out.println("Max-Heap vacio");
            return;
        }
        //BFS para el recorrido del arbol en forma de niveles 
        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);

        int nivel = 0;
        while(!cola.isEmpty()){
            /*Tamaño por cada nivel del arbol (sepados por , son hermanos y por <-> son de firente padre)
                  [1]      tamaño 1 
                 [1,2]     tamaño 2
              [1,2<->3,4]  tamaño 4
            */
            int nivelTam = cola.size();
            System.out.print("Nivel " + nivel + " [");
            for(int i = 0; i < nivelTam; i++){
                Nodo actual = cola.poll();
                if(i < nivelTam-1){
                    if(i % 2 == 0) System.out.print(actual.valor + ",");
                    else System.out.print(actual.valor + " <-> ");
                } 
                else System.out.print(actual.valor + "");
                if (actual.izquierda != null) cola.add(actual.izquierda);
                if (actual.derecha != null) cola.add(actual.derecha);
            }
            System.out.println("]");
            System.out.println();
            nivel++;
        }
    }
}