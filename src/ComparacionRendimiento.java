public class ComparacionRendimiento {
    public static void main(String[] args) {
        int[] tamanios = {1000, 5000, 10000, 20000};
        System.out.printf("%-8s | %-22s | %-22s%n", "n", "ArrayList (ms)", "HashMap (ms)");
        System.out.printf("%-8s | %-10s %-11s | %-10s %-11s%n", "", "registrar", "buscar", "registrar", "buscar");
        System.out.println("-".repeat(60));

        medir(new RepositorioArrayList(), 2000); medir(new RepositorioHashMap(), 2000);
        for (int n : tamanios) {
            double[] al = medir(new RepositorioArrayList(), n);
            double[] hm = medir(new RepositorioHashMap(), n);
            System.out.printf("%-8d | %-10.2f %-11.2f | %-10.2f %-11.2f%n", n, al[0], al[1], hm[0], hm[1]);
        }
    }

    private static double[] medir(RepositorioAvengers repo, int n) {
        long t0 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            repo.registrar(new Avenger("A" + i, "Avenger " + i, "Misión " + i, 1 + i % 5, 1000 + i));
        }
        long t1 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            repo.buscarPorId("A" + i);
        }
        long t2 = System.nanoTime();
        return new double[]{(t1 - t0) / 1e6, (t2 - t1) / 1e6};
    }
}
