public class PruebasLogica {
    private static int total = 0, pasan = 0;

    public static void main(String[] args) {
        CalculadoraPagos calc = new CalculadoraPagos();

        System.out.println("=== CP-01 a CP-06: cálculo de fondo, impuesto y neto ===");
        probarCalculo(calc, "CP-01 Tramo 0%  (mensual 3,000)",  3000,  240.00,    0.00,  2760.00);
        probarCalculo(calc, "CP-02 Tramo 10% (mensual 6,000)",  6000,  480.00,  183.33,  5336.67);
        probarCalculo(calc, "CP-03 Tramo 20% (mensual 10,000)", 10000, 800.00,  333.33,  8866.67);
        probarCalculo(calc, "CP-04 Tramo 30% (mensual 20,000)", 20000, 1600.00, 1000.00, 17400.00);
        verificar("CP-05 Límite: anual 100,000 -> impuesto anual 5,000",
                cerca(calc.calcularImpuestoAnual(100000), 5000.00));
        verificar("CP-06 Límite: anual 100,001 -> impuesto anual 0.20",
                cerca(calc.calcularImpuestoAnual(100001), 0.20));

        System.out.println();
        System.out.println("=== CP-07 a CP-10: validaciones de la entidad Avenger ===");
        verificar("CP-07 Peligrosidad 0 se rechaza", lanzaError(() -> new Avenger("A1", "Thor", "Asgard", 0, 5000)));
        verificar("CP-08 Peligrosidad 6 se rechaza", lanzaError(() -> new Avenger("A1", "Thor", "Asgard", 6, 5000)));
        verificar("CP-09 Pago mensual 0 se rechaza", lanzaError(() -> new Avenger("A1", "Thor", "Asgard", 3, 0)));
        verificar("CP-10 Nombre vacío se rechaza",   lanzaError(() -> new Avenger("A1", "  ", "Asgard", 3, 5000)));

        probarRepositorio("Alternativa 1 - ArrayList", new RepositorioArrayList());
        probarRepositorio("Alternativa 2 - HashMap",   new RepositorioHashMap());

        System.out.println();
        System.out.printf("RESULTADO: %d de %d pruebas pasaron.%n", pasan, total);
        if (pasan != total) System.exit(1);
    }

    private static void probarRepositorio(String nombre, RepositorioAvengers repo) {
        System.out.println();
        System.out.println("=== CP-11 a CP-16: repositorio (" + nombre + ") ===");
        verificar("CP-11 Registrar Avenger nuevo",
                repo.registrar(new Avenger("A01", "Iron Man", "Defensa de Nueva York", 4, 20000)));
        repo.registrar(new Avenger("A02", "Black Widow", "Infiltración", 3, 6000));
        verificar("CP-12 Registrar ID duplicado se rechaza",
                !repo.registrar(new Avenger("A01", "Otro", "Otra", 1, 1000)));
        verificar("CP-13 Buscar por ID existente",
                repo.buscarPorId("A02") != null && repo.buscarPorId("A02").getNombre().equals("Black Widow"));
        boolean modifico = repo.modificar("A02", new Avenger("A02", "Natasha Romanoff", "Rescate", 5, 10000));
        Avenger mod = repo.buscarPorId("A02");
        verificar("CP-14 Modificar ID existente actualiza los datos",
                modifico && mod.getNombre().equals("Natasha Romanoff") && mod.getPeligrosidad() == 5
                        && cerca(mod.getPagoMensual(), 10000));
        verificar("CP-15 Modificar ID inexistente se rechaza",
                !repo.modificar("Z99", new Avenger("Z99", "Nadie", "Nada", 1, 1000)));
        verificar("CP-16 Listar devuelve los 2 Avengers registrados", repo.listar().size() == 2);
    }

    private static void probarCalculo(CalculadoraPagos c, String caso, double pm,
                                      double fondo, double imp, double neto) {
        double f = c.calcularFondo(pm), i = c.calcularImpuestoMensual(pm), n = c.calcularNeto(pm);
        boolean ok = cerca(f, fondo) && cerca(i, imp) && cerca(n, neto);
        verificar(String.format("%s -> fondo %.2f, impuesto %.2f, neto %.2f", caso, f, i, n), ok);
    }

    private static boolean lanzaError(Runnable r) {
        try { r.run(); return false; } catch (IllegalArgumentException e) { return true; }
    }

    private static boolean cerca(double a, double b) { return Math.abs(a - b) < 0.005; }

    private static void verificar(String caso, boolean ok) {
        total++;
        if (ok) pasan++;
        System.out.println((ok ? "[PASA]  " : "[FALLA] ") + caso);
    }
}
