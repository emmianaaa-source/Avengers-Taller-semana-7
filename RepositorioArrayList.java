import java.util.ArrayList;
import java.util.List;

/**
 * Alternativa 1: almacenamiento en ArrayList con búsqueda lineal por ID.
 *
 * Complejidad:
 *  - registrar:   O(n)  (verifica que el ID no esté repetido)
 *  - buscarPorId: O(n)  (búsqueda lineal)
 *  - modificar:   O(n)  (busca primero; actualizar es O(1))
 *  - listar:      O(n)  (copia en orden de registro)
 */
public class RepositorioArrayList implements RepositorioAvengers {

    private final List<Avenger> avengers = new ArrayList<>();

    /** Algoritmo 1: búsqueda lineal. Devuelve la posición o -1 si no existe. */
    private int buscarIndice(String id) {
        for (int i = 0; i < avengers.size(); i++) {
            if (avengers.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    /** Algoritmo 2: registrar. */
    @Override
    public boolean registrar(Avenger a) {
        if (buscarIndice(a.getId()) != -1) {
            return false; // ID repetido
        }
        avengers.add(a);
        return true;
    }

    /** Algoritmo 3a: buscar por ID. */
    @Override
    public Avenger buscarPorId(String id) {
        int i = buscarIndice(id);
        return (i == -1) ? null : avengers.get(i);
    }

    /** Algoritmo 3b: modificar por ID. */
    @Override
    public boolean modificar(String id, Avenger datos) {
        Avenger a = buscarPorId(id);
        if (a == null) {
            return false; // el ID debe existir
        }
        a.setNombre(datos.getNombre());
        a.setMision(datos.getMision());
        a.setPeligrosidad(datos.getPeligrosidad());
        a.setPagoMensual(datos.getPagoMensual());
        return true; // el ID no se modifica
    }

    /** Algoritmo 4: listar. */
    @Override
    public List<Avenger> listar() {
        return new ArrayList<>(avengers); // copia, en orden de registro
    }
}
