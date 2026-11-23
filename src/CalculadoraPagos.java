public class CalculadoraPagos {
    public static final double TASA_FONDO = 0.08;

    public double calcularFondo(double pagoMensual) {
        return redondear(pagoMensual * TASA_FONDO);
    }

    public double calcularImpuestoAnual(double pagoAnual) {
        if (pagoAnual <= 50000) return 0;
        if (pagoAnual <= 100000) return 0.10 * (pagoAnual - 50000);
        if (pagoAnual <= 200000) return 0.20 * (pagoAnual - 100000);
        return 0.30 * (pagoAnual - 200000);
    }

    public double calcularImpuestoMensual(double pagoMensual) {
        return redondear(calcularImpuestoAnual(pagoMensual * 12) / 12);
    }

    public double calcularNeto(double pagoMensual) {
        return redondear(pagoMensual - calcularFondo(pagoMensual) - calcularImpuestoMensual(pagoMensual));
    }

    private double redondear(double valor) {
        return Math.round(valor * 100) / 100.0;
    }
}
