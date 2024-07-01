import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Comparator;
public class App {
    public static void main(String[] args) throws Exception{

        /* 
         * Trabalho 2 - Projeto e Otimização de Algoritmos
         * Leonardo Heinen Oliveira e Leonardo Vargas Soares
         * Turma 31
         * Professor Rafael Scopel Silva
         * 
         * Análises e explicações sobre a implementação dos algoritmos no relatório,
         * Algoritmos/Técnicas utilizadas - Greedy, programação dinamica e branch and bound.
         * 
        */

        //Problema 1 - Itens 1 e 2
        // Arrays de tarefas faceis(l) e dificeis(h)
        // int[] l = {10, 5, 10, 10, 10};
        // int[] h = {0, 15, 40, 50, 15};
        //algoritmo item 1
        // versaoGreedy(l, h); - COMENTAR SOLVEP1 PARA TESTAR ESTE
        //algoritmo item 2

        //Conjuntos de dados para teste de tempo de execução:
        // int[][] lArrays = { 
        //     {5, 3, 8},
        //     {10, 5, 10, 10, 10},
        //     {8, 2, 4, 6, 7, 3, 5, 8, 10, 1},
        //     {7, 5, 8, 9, 6, 4, 7, 10, 5, 8, 3, 6, 9, 4, 5, 7, 6, 8, 9, 10},
        //     new int[1000000]
        // };
        
        // int[][] hArrays = {
        //     {0, 10, 15},
        //     {0, 15, 40, 50, 15},
        //     {0, 20, 30, 50, 60, 40, 55, 70, 80, 25},
        //     {0, 25, 35, 45, 50, 55, 60, 70, 75, 85, 90, 95, 100, 105, 110, 115, 120, 125, 130, 135},
        //     new int[1000000]
        // };

        // for (int i = 0; i < 1000000; i++) {
        //     lArrays[4][i] = (i % 10) + 1;
        //     hArrays[4][i] = (i % 15) + 20;
        // }

        // for (int i = 0; i < lArrays.length; i++) {
        //     int[] l = lArrays[i];
        //     int[] h = hArrays[i];

        //     long startTime = System.nanoTime();
        //     solveP1(l, h);
        //     long endTime = System.nanoTime();

        //     long duration = (endTime - startTime) / 1_000_000_000;
        //     System.out.println("Tempo de execução para conjunto de dados " + (i + 1) + ": " + duration + " segundos");
        // }

        //Problema 2 - Item 1

        int n = 5; //número de itens 
        int[] wi = {5,6,2,4,7};//Vetor de peso (wi)
        int[] vi = {18,22,6,5,28}; //Vetor de valores (vi)
        int W = 11; // capacidade da mochila
        solveP2(5, wi, vi, W);

        // int[] wi100000 = new int[100000];
        // int[] vi100000 = new int[100000];
        // int W100000 = 500000;

        // for (int i = 0; i < 100000; i++) {
        //     wi100000[i] = (int) (Math.random() * 100 + 1);
        //     vi100000[i] = (int) (Math.random() * 100 + 1);
        // }

        // long startTime = System.nanoTime();
        // solveP2(100000,wi100000,vi100000,W100000);
        // long endTime = System.nanoTime();
        // long duration = (endTime - startTime) / 1_000_000_000;
        // System.out.println("Tempo de execução para conjunto de dados 100000 " + ": " + duration + " segundos");
    }

    public static void versaoGreedy(int[] l, int[] h) {
        int n = l.length;

        for (int i = 0; i < n; i++) {
            if (i + 1 < n && h[i + 1] > l[i] + l[i + 1]) {
                System.out.println("Escolha 'Fazer Nada' na semana " + (i + 1));
                System.out.println("Escolha uma tarefa high stress na semana " + (i + 2));
                i++; 
            } else {
                System.out.println("Escolha uma tarefa low stress na semana " + (i + 1));
            }
        }
    }

    // Algoritmo baseado em programação dinâmica
    public static void solveP1(int[] l, int[] h) {
        int n = l.length;
        int[] vlrMax = new int[n + 1];
        int[] escolhas = new int[n + 1]; // 0 - fazer nada, 1 - baixa, 2 - alta

        vlrMax[0] = 0;
        if (n > 0) {
            vlrMax[1] = l[0];
            escolhas[1] = 1;
        }

        for (int i = 2; i <= n; i++) {
            int chooseLow = vlrMax[i - 1] + l[i - 1];
            int chooseHigh = vlrMax[i - 2] + h[i - 1];

            if (chooseLow >= chooseHigh) {
                vlrMax[i] = chooseLow;
                escolhas[i] = 1; // baixa dificuldade
            } else {
                vlrMax[i] = chooseHigh;
                escolhas[i] = 2; // alta dificuldade
            }
        }

        System.out.println("Valor do plano ideal: " + vlrMax[n]);

        // Reconstruindo as escolhas
        System.out.println("Tarefas executadas em cada semana:");
        int i = n;
        while (i > 0) {
            if (escolhas[i] == 1) {
                System.out.println("Semana " + i + ": baixa dificuldade");
                i--;
            } else if (escolhas[i] == 2) {
                System.out.println("Semana " + i + ": alta dificuldade");
                if (i - 1 > 0) {
                    System.out.println("Semana " + (i - 1) + ": fazer nada");
                }
                i -= 2;
            }
        }
    }
    static class Node{
        int nivel;
        int lucro;
        int peso;
        int bound;
        boolean[] itemsIncluded;
    
        public Node(int nivel, int lucro, int peso, int bound, boolean[] itemsIncluded) {
            this.nivel = nivel;
            this.lucro = lucro;
            this.peso = peso;
            this.bound = bound;
            this.itemsIncluded = itemsIncluded;
        }
    }

    public static void solveP2(int n, int[] wi, int[] vi, int W) {
        // Cria um array de indices e ordena, baseando em valor e peso
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        // Ordenação, utilizando Lambda e compare() - razão valor/peso
        Arrays.sort(indices, (a, b) -> Double.compare((double) vi[b] / wi[b], (double) vi[a] / wi[a]));

        PriorityQueue<Node> queue = new PriorityQueue<>(new NodeComparator());
        Node root = new Node(-1, 0, 0, bound(-1, 0, 0, n, W, wi, vi, indices), new boolean[n]);
        queue.add(root);

        int maxLucro = 0;
        boolean[] bestItems = new boolean[n];

        while (!queue.isEmpty()) {
            Node atual = queue.poll();

            if (atual.nivel == -1) {
                atual.nivel = 0;
            }

            if (atual.nivel == n - 1) {
                continue;
            }

            int proxNivel = atual.nivel + 1;
            int index = indices[proxNivel];

            // Considera o próximo item 
            if (atual.peso + wi[index] <= W) {
                int novoPeso = atual.peso + wi[index];
                int novoLucro = atual.lucro + vi[index];
                boolean[] newItemsIncluded = Arrays.copyOf(atual.itemsIncluded, n);
                newItemsIncluded[index] = true;

                if (novoLucro > maxLucro) {
                    maxLucro = novoLucro;
                    bestItems = newItemsIncluded;
                }

                int newBound = bound(proxNivel, novoLucro, novoPeso, n, W, wi, vi, indices);
                if (newBound > maxLucro) {
                    queue.add(new Node(proxNivel, novoLucro, novoPeso, newBound, newItemsIncluded));
                }
            }

            // não considera o próximo item 
            int newBound = bound(proxNivel, atual.lucro, atual.peso, n, W, wi, vi, indices);
            if (newBound > maxLucro) {
                queue.add(new Node(proxNivel, atual.lucro, atual.peso, newBound, atual.itemsIncluded));
            }
        }

        System.out.println("Valor ótimo para colocar na mochila: " + maxLucro);
        System.out.println("Itens colocados na mochila:");
        for (int i = 0; i < n; i++) {
            if (bestItems[i]) {
                System.out.println("Item " + i + " (Peso: " + wi[i] + ", Valor: " + vi[i] + ")");
            }
        }
    }

    private static int bound(int nivel, int lucro, int peso, int n, int W, int[] wi, int[] vi, Integer[] indices) {
        if (peso >= W) {
            return 0;
        }

        int lucroBound = lucro;
        int pesoTotal = peso;
        int j = nivel + 1;

        while ((j < n) && (pesoTotal + wi[indices[j]] <= W)) {
            pesoTotal += wi[indices[j]];
            lucroBound += vi[indices[j]];
            j++;
        }

        if (j < n) {
            lucroBound += (W - pesoTotal) * vi[indices[j]] / wi[indices[j]];
        }

        return lucroBound;
    }
    static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node a, Node b) {
            return Integer.compare(b.bound, a.bound);
        }
    }

}
