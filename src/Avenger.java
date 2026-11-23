public class Avenger {
    private final String id;
    private String nombre;
    private String mision;
    private int peligrosidad;
    private double pagoMensual;

    public Avenger(String id, String nombre, String mision, int peligrosidad, double pagoMensual) {
        validarTexto(id, "El ID");
        validarTexto(nombre, "El nombre");
        validarTexto(mision, "La misión");
        validarPeligrosidad(peligrosidad);
        validarPago(pagoMensual);

        this.id = id.trim();
        this.nombre = nombre.trim();
        this.mision = mision.trim();
        this.peligrosidad = peligrosidad;
        this.pagoMensual = pagoMensual;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validarTexto(nombre, "El nombre");
        this.nombre = nombre.trim();
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        validarTexto(mision, "La misión");
        this.mision = mision.trim();
    }

    public int getPeligrosidad() {
        return peligrosidad;
    }

    public void setPeligrosidad(int peligrosidad) {
        validarPeligrosidad(peligrosidad);
        this.peligrosidad = peligrosidad;
    }

    public double getPagoMensual() {
        return pagoMensual;
    }

    public void setPagoMensual(double pagoMensual) {
        validarPago(pagoMensual);
        this.pagoMensual = pagoMensual;
    }

    private static void validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío.");
        }
    }

    private static void validarPeligrosidad(int peligrosidad) {
        if (peligrosidad < 1 || peligrosidad > 5) {
            throw new IllegalArgumentException("La peligrosidad debe estar entre 1 y 5.");
        }
    }

    private static void validarPago(double pagoMensual) {
        if (!Double.isFinite(pagoMensual) || pagoMensual <= 0) {
            throw new IllegalArgumentException("El pago mensual debe ser mayor que cero.");
        }
    }
}
