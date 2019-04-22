
package tspbygenetico;

import java.util.ArrayList;

public class TspByGenetico {
    
    int N = 17;
    int[][] tours = new int[4][N-1];

    int[][] distancia = new int[][]{
            {0,633, 257, 91, 412, 150, 80, 134, 259, 505, 353, 324, 70, 211, 268, 246, 121},
            {633, 0, 390, 661, 227, 488, 572, 530, 555, 289, 282, 638, 567, 466, 420, 745, 518},
            {257, 390, 0, 228, 169, 112, 196, 154, 372, 262, 110, 437, 191, 74, 53, 472, 142},
            {91, 661, 228, 0, 383, 120, 77, 105, 175, 476, 324, 240, 27, 182, 239, 237, 84},
            {412, 227, 169, 383, 0, 267, 351, 309, 338, 196, 61, 421, 346, 243, 199, 528, 297},
            {150, 488, 112, 120, 267, 0, 63, 34, 264, 360, 208, 329, 83, 105, 123, 364, 35},
            {80, 572, 196, 77, 351, 63, 0, 29, 232, 444, 292, 297, 47, 150, 207, 332, 29},
            {134, 530, 154, 105, 309, 34, 29, 0, 249, 402, 250, 314, 68, 108, 165 ,349, 36},
            {259, 555, 372, 175, 338, 264, 232, 249, 0, 495, 352, 95, 189, 326, 383, 202, 236},
            {505, 289, 262, 476, 196, 360, 444, 402, 495, 0, 154, 578, 439, 336, 240, 685, 390},
            {353, 282, 110, 324, 61, 208, 292, 250, 352, 154, 0, 435, 287, 184, 140, 542, 238},
            {324, 638, 437, 240, 421, 329, 297, 314, 95, 578, 435, 0, 254, 391, 448, 157, 301},
            {70, 567, 191, 27, 346, 83, 47, 68, 189, 439, 287, 254, 0, 145, 202, 289, 55},
            {211,466,74,182,243,105,150,108,326,336,184,391,145,0,57,426,96},
            {268,420,53,239,199,123,207,165,383,240,140,448,202,57,0,483,153},
            {246,745,472,237,528,364,332,349,202,685,542,157,289,426,483,0,336},
            {121,518,142,84,297,35,29,36,236,390,238,301,55,96,153,336,0}   
        }; 
    
    public TspByGenetico(){
        //long x = 10000000000;
        int k = 0;
        int x = 1000000;
        //Iniciamos4 tours aleatorios
        for(int i=0; i<4; i++)
            tourAleatorio(i);
        //for(int i=0;i<4;i++) mostrarArray(i);
        int [] mejorTour =new int[N-1];
        int dist_mejorTour = distanciaTour(0);
        
        int nueva_distancia; 
        System.out.println("");
        while(k<x){
            ordenarTours();
            nueva_distancia = distanciaTour(0);
            if(nueva_distancia < dist_mejorTour){
                
                dist_mejorTour = nueva_distancia;
                //mejorTour va a tomar la primera posicion de Tour ya que hasta ahora es la mejor
                for(int i=0; i<N-1; i++){
                    mejorTour[i] = tours[0][i];
                }
            }
            //Para mostrar las matrices en orden
            //for(int i=0;i<4;i++) mostrarArray(i);
            //System.out.println("");
                
            tours = nuevaDescendencia();
            //dentro de nuevaDecendencia() agregue la posibilidad de mutacion
            
            k++;
        }
        System.out.println("Mejor Tour Encontrado:");
        mostrarRuta(mejorTour);
        System.out.println("");
        System.out.println("distancia: "+dist_mejorTour);
        
        
    
    }
    public void mostrarRuta(int[] ruta){
        System.out.print("["+0+"]-->");
        for(int i=0; i<ruta.length; i++){
            System.out.print("["+ruta[i]+"]-->");
        }
        System.out.print("["+0+"]");
    }
    public int distanciaMejotTour(int[]mejorTour ){
        int dist = 0;
        int ciudad_origen,ciudad_destino;
        for(int i=0; i<N-2; i++){
            ciudad_origen = mejorTour[i];//indice
            ciudad_destino = mejorTour[i+1];
            dist = dist + distancia[ciudad_origen][ciudad_destino];
        }
        //Le sumo de ciudad 0 a la primera y de la ultima al cero
        dist = dist + distancia[0][mejorTour[0]];
        dist = dist + distancia[0][mejorTour[N-2]];
        return dist;
    }
    
    public void mostrarArray(int t){
        System.out.print("[ ");
        for(int i=0; i<N-1; i++){
            System.out.print(tours[t][i]+" ");
        }
        System.out.print("]"+ distanciaTour(t));
        System.out.println();
    }
    public int[][] nuevaDescendencia(){
        int indice_gen;
        int t_des = 0;
        int t1 = 0;
        int t2 = 1;
        int puntoCruce1,puntoCruce2;
        int[][] descendencia = new int[4][N-1];
        ArrayList<Integer> fragmento1 = new ArrayList<>();
        ArrayList<Integer> fragmento2 = new ArrayList<>();
        while(t_des< 4){
            //Cambiamos los cromosomas que se van a intercambiar
            if(t_des == 0){
                t1 = 0;
                t2 = 1;
            }
            if(t_des == 1){
                t1 = 2;
                t2 = 3;
            }
            if(t_des == 2){
                t1 = 0;
                t2 = 2;
            }
            if(t_des == 3){
                t1 = 1;
                t2 = 3;
            }
            //descendencia inicial mente = t2
            for(int i=0; i<N-1; i++){
                 descendencia[t_des][i] = tours[t2][i];
            }
            int aux;
            puntoCruce1 = (int) (Math.random() * N-1);
            puntoCruce2 = (int) (Math.random() * N-1);
            while(puntoCruce2 == puntoCruce1){//aseguramos que sean distintos
                puntoCruce2 = (int) (Math.random() * N-1);
            }
            if(puntoCruce2 < puntoCruce1){//para que el pc1 sea menor a pc2
                aux = puntoCruce1;
                puntoCruce1 = puntoCruce2;
                puntoCruce2 = aux;
                
            }
            //obtengo los fragmentos de cada tour
            for(int i=puntoCruce1; i<puntoCruce2; i++){
                fragmento1.add(tours[t1][i]);

            }
            //aumento o disminuyo puntos de cruce
            int r = (int) (Math.random() * 1);
            int aumento;
            if(r ==0){//izquierda
                aumento = (int) (Math.random() * puntoCruce1);
                puntoCruce1 = puntoCruce1 - aumento;
                puntoCruce2 = puntoCruce2 - aumento;
            }
            else{
                aumento = (int) (Math.random() * N-1-puntoCruce2);
                puntoCruce1 = puntoCruce1 + aumento;
                puntoCruce2 = puntoCruce2 + aumento;
            }
            boolean distintoTodos=true;
            for(int i=puntoCruce1; i<puntoCruce2; i++){
                for(int j=0; j<fragmento1.size(); j++){
                    if(tours[t2][i]== fragmento1.get(j))
                        distintoTodos = false;
                }
                if(distintoTodos)
                    fragmento2.add(tours[t2][i]);
                distintoTodos =true;
            }
            
            //le pego a t2 el fragmento 1
            int i_frag1 =0;
            for(int i=puntoCruce1; i< puntoCruce2; i++){
                //tours[t2][i] = fragmento1.get(i);
                descendencia[t_des][i] = fragmento1.get(i_frag1); 
                i_frag1++;
            }
            //recorro todo el tour
            for(int i=0; i<puntoCruce1; i++){ //por cada gen
                for(int j=0; j<fragmento1.size(); j++)//recorro los fragmentos
                    if(tours[t2][i] == fragmento1.get(j)){
                        indice_gen = (int) (Math.random() * fragmento2.size());

                        //tours[t2][i] = fragmento2.get(indice_gen);
                        descendencia[t_des][i] = fragmento2.get(indice_gen);
                        fragmento2.remove(indice_gen);
                        fragmento1.remove(j);
                    }
            }
            for(int i=puntoCruce2; i<N-1; i++){ //por cada gen
                for(int j=0; j<fragmento1.size(); j++)//recorro los fragmentos
                    if(tours[t2][i] == fragmento1.get(j)){
                        indice_gen = (int) (Math.random() * fragmento2.size());

                        //tours[t2][i] = fragmento2.get(indice_gen);
                        descendencia[t_des][i] = fragmento2.get(indice_gen);
                        fragmento2.remove(indice_gen);
                        fragmento1.remove(j);
                    }
            }
            //Probabilidad de mutacion
            int prob_mutacion = (int) (Math.random() * 500);
            int cromosoma,gen1,gen2;
            int aux2;
            if(prob_mutacion ==25){
                //System.out.println("Muto :O");
                //mutamos un cromosoma al azar
                cromosoma = (int) (Math.random() * 3);
                gen1 = (int) (Math.random() * 15);
                gen2 = (int) (Math.random() * 15);
                while(gen1 == gen2){//para que no sean iguales
                    gen2 = (int) (Math.random() * 15);
                }
                aux2 = descendencia[cromosoma][gen2];
                descendencia[cromosoma][gen2] =descendencia[cromosoma][gen1];
                descendencia[cromosoma][gen1] = aux2;
                
            }
            
            fragmento1.clear();
            fragmento2.clear();
            //for(int i=0; i<N-1; i++){
                //System.out.print(descendencia[t_des][i]+" ");
            //}
            t_des++;
            
        }
        return descendencia;
    }

    public void tourAleatorio(int t){
        // = (int) (Math.random() * 9) + 1;
        int r1;
        ArrayList<Integer> soluTour1 = new ArrayList<>();
        for(int i=1; i<N; i++){
            soluTour1.add(i);
        }
        for(int i=0;i<N-1; i++){
            r1 = (int) (Math.random() * soluTour1.size());
            tours[t][i] = soluTour1.get(r1);
            soluTour1.remove(r1);
        }  
    }
    private int distanciaTour(int t){
        int dist = 0;
        int ciudad_origen,ciudad_destino;
        for(int i=0; i<N-2; i++){
            ciudad_origen = tours[t][i];//indice
            ciudad_destino = tours[t][i+1];
            dist = dist + distancia[ciudad_origen][ciudad_destino];
        }
        //Le sumo de ciudad 0 a la primera y de la ultima al cero
        dist = dist + distancia[0][tours[t][0]];
        dist = dist + distancia[0][tours[t][N-2]];
        return dist;
    }
    public void ordenarTours(){//ordena del mas corto al mas largo
        int[] dist_tours = new int[4];
        int[][] menorSiguiente;
        for (int i=0; i<4; i++)
            dist_tours[i] = distanciaTour(i); //obtenemos las distancias de los tours
        
        for (int i=0; i<3; i++){
            menorSiguiente = menor(i+1,dist_tours);
        
            if(menorSiguiente[0][1] < dist_tours[i]){

                rotarTours(i,menorSiguiente[0][0]);
                dist_tours = rotar_dist(dist_tours,i,menorSiguiente[0][0]);
            }
        }
    }
    private int[][] menor(int i, int[] dist_tours){
        int menor= dist_tours[i];
        int indice_menor = i;
        for(int j=i; j<dist_tours.length;j++){
            if(dist_tours[j]< menor){
                menor = dist_tours[j];
                indice_menor = j;
            }
        }
        int[][] a = new int[][]{{indice_menor,menor}};
        return a;
    }
    private int[] rotar_dist(int[] dist_tours, int t1, int t2){
        int aux_dist = dist_tours[t1];
        dist_tours[t1] = dist_tours[t2];
        dist_tours[t2] = aux_dist;
        return dist_tours;
    }
    private void rotarTours(int t1, int t2){
        int aux_tour;
        for(int i=0; i<N-1; i++){
            
            aux_tour = tours[t1][i];
            tours[t1][i] = tours[t2][i];
            tours[t2][i] = aux_tour;
        }

    }
    
    public static void main(String[] args) {
        TspByGenetico app = new TspByGenetico();
    }
    
}
