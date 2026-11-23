import java.util.List;

/**
 * Contrato de almacenamiento de los Avengers.
 * La ventana trabaja solo con esta interfaz, por lo que la estructura
 * de datos se puede cambiar sin tocar la GUI ni los cálculos.
 */
public interface RepositorioAvengers {

    /** Registra un Avenger. Devuelve false si el ID ya existe. */
    boolean registrar(Avenger a);

    /** Devuelve el Avenger con ese ID, o null si no existe. */
    Avenger buscarPorId(String id);

    /** Modifica los datos de un Avenger existente (el ID no cambia). */
    boolean modificar(String id, Avenger datos);

    /** Devuelve los Avengers en orden de registro. */
    List<Avenger> listar();
}
