import java.util.ArrayList;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws Exception{
        //Problema 1 - Itens 1 e 2
        // Arrays de tarefas faceis(l) e dificeis(h)
        int[] l = {10, 50, 10, 20, 10};
        int[] h = {0, 70, 0, 100, 0};
        //algoritmo item 1
        versaoGreedy(l, h);
        //algoritmo item 2 
        // solveP1(l, h);


        //Problema 2 - Item 1

        int n = 1; //número de itens 
        int[] wi = {5,6,2,4,7};//Vetor de peso (wi)
        int[] vi = {18,22,6,5,28}; //Vetor de valores (vi)
        int W = 11; // capacidade da mochila
        solveP2(n,wi,vi,W);
    }

    public static void versaoGreedy(int[] l, int[] h) {
        int n = l.length;

        for (int i = 0; i < n; i++) {
            if (i + 1 < n && h[i + 1] > l[i] + l[i + 1]) {
                System.out.println("Escolha 'Fazer Nada' na semana " + (i + 1));
                System.out.println("Escolha uma tarefa high stress na semana " + (i + 2));
                i++; // Continue with iteration i+2
            } else {
                System.out.println("Escolha uma tarefa low stress na semana " + (i + 1));
            }
        }
    }

    // Algoritmo baseado em programação dinâmica
    public static void solveP1(int[] l, int[] h) {
        int n = l.length;
        int[] dp = new int[n]; // Array para armazenar o valor máximo acumulado até a semana i
        int[] choice = new int[n]; // Array para armazenar a escolha feita em cada semana (0 para low, 1 para high)

        // Casos base
        dp[0] = l[0]; // Se escolher low na primeira semana
        choice[0] = 0; // Escolha low na primeira semana

        if (n > 1) {
            dp[1] = Math.max(l[1], h[1]); // Escolher o máximo entre low e high na segunda semana
            choice[1] = l[1] > h[1] ? 0 : 1; // Escolha low se l[1] > h[1], senão high
        }

        // Preenchimento dos valores para as semanas restantes usando programação dinâmica
        for (int i = 2; i < n; i++) {
            // Escolher entre (1) low na semana i e o máximo até i-1, ou (2) high na semana i e o máximo até i-2
            if (dp[i - 1] + l[i] >= dp[i - 2] + h[i]) {
                dp[i] = dp[i - 1] + l[i];
                choice[i] = 0; // Escolhe low na semana i
            } else {
                dp[i] = dp[i - 2] + h[i];
                choice[i] = 1; // Escolhe high na semana i
            }
        }

        // Recuperação do plano ótimo
       ArrayList<String> plan = new ArrayList<>();
        int index = n - 1;
        while (index >= 0) {
            if (choice[index] == 0) {
                plan.add("Choose a low-stress job in week " + (index + 1));
                index--;
            } else {
                plan.add("Choose a high stress job in week " + (index + 1));
                while (index > 0 && choice[index - 1] == 1) {
                    index--; // Pula semanas adicionais com alta dificuldade
                }
                index--; // Avança para a semana anterior após escolher alta dificuldade
            }
        }
        Collections.reverse(plan); // Inverte a ordem para começar da semana 1 até n

        // Imprime o plano de trabalho
        for (String step : plan) {
            System.out.println(step);
        }

        // Imprime o valor máximo alcançado
        System.out.println("Maximum revenue: " + dp[n - 1]);
    }

    public static void solveP2(int n, int wi[], int vi[], int W){

    }

}
