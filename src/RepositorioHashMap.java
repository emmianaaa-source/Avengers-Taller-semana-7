import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class RepositorioHashMap implements RepositorioAvengers {
    private final HashMap<String, Avenger> avengers = new HashMap<>();

    @Override
    public boolean registrar(Avenger a) {
        if (avengers.containsKey(a.getId())) return false;
        avengers.put(a.getId(), a);
        return true;
    }

    @Override
    public Avenger buscarPorId(String id) {
        return avengers.get(id);
    }

    @Override
    public boolean modificar(String id, Avenger datos) {
        Avenger a = avengers.get(id);
        if (a == null) return false;
        a.setNombre(datos.getNombre());
        a.setMision(datos.getMision());
        a.setPeligrosidad(datos.getPeligrosidad());
        a.setPagoMensual(datos.getPagoMensual());
        return true;
    }

    @Override
    public List<Avenger> listar() {
        List<Avenger> lista = new ArrayList<>(avengers.values());
        lista.sort(Comparator.comparing(Avenger::getId));
        return lista;
    }
}
