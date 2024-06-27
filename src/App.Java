public class App {
    public static void main(String[] args) throws Exception {
        //Problema 1 - Item 1
        int[] l = {0, 5, 1, 10};
        int[] h = {0, 6, 2, 20};
        versaoGreedy(l, h);
    }

    public static int versaoGreedy(int[] low, int[] high) {
        int n = 4;
        int[] valorMaxSemana = new int[n + 1];

        valorMaxSemana[0] = 0; // Valor acumulado até a semana 0

        for (int i = 1; i <= n; i++) {
            if (i == 1) {
                // Na primeira semana, escolhemos low stress job
                valorMaxSemana[i] = low[i - 1];
                System.out.println(valorMaxSemana[i]);
            } else {
                // Se escolher high stress job na semana i, precisamos ter escolhido no job na semana i-1
                valorMaxSemana[i] = Math.max(valorMaxSemana[i - 1] + high[i - 1], valorMaxSemana[i - 2] + low[i - 1]);
                System.out.println(valorMaxSemana[i]);
            }
        }

        return valorMaxSemana[n];
    }
    public void solveP1(int l[], int h[]){
        
    }

}
