import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Alternativa 2: almacenamiento en HashMap con acceso directo por ID (clave).
 *
 * Complejidad:
 *  - registrar:   O(1) promedio  (containsKey + put)
 *  - buscarPorId: O(1) promedio  (acceso directo por la clave)
 *  - modificar:   O(1) promedio  (get + actualización de atributos)
 *  - listar:      O(n log n)     (copia de values() y ordenamiento por ID)
 */
public class RepositorioHashMap implements RepositorioAvengers {

    private final Map<String, Avenger> avengers = new HashMap<>();

    /** Algoritmo 1: registrar. */
    @Override
    public boolean registrar(Avenger a) {
        if (avengers.containsKey(a.getId())) {
            return false; // ID repetido
        }
        avengers.put(a.getId(), a);
        return true;
    }

    /** Algoritmo 2a: buscar por ID. */
    @Override
    public Avenger buscarPorId(String id) {
        return avengers.get(id); // null si no existe
    }

    /** Algoritmo 2b: modificar por ID. */
    @Override
    public boolean modificar(String id, Avenger datos) {
        Avenger a = avengers.get(id);
        if (a == null) {
            return false; // el ID debe existir
        }
        a.setNombre(datos.getNombre());
        a.setMision(datos.getMision());
        a.setPeligrosidad(datos.getPeligrosidad());
        a.setPagoMensual(datos.getPagoMensual());
        return true; // el ID (clave) no se modifica
    }

    /** Algoritmo 3: listar. HashMap no guarda el orden de registro, por eso se ordena por ID. */
    @Override
    public List<Avenger> listar() {
        List<Avenger> lista = new ArrayList<>(avengers.values());
        lista.sort(Comparator.comparing(Avenger::getId));
        return lista;
    }
}