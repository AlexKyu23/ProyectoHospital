package Clases.Receta.logic;



public class Receta {

    private boolean confeccionada;
    // private ? fechaDeConfeccion;
    // private ? fechaDeRetiro;
    private boolean enProceso;
    private boolean lista;
    private boolean entregada;


    public Receta(boolean confeccionada, /* ? fechaDeConfeccion, ? fechaDeRetiro, */
                  boolean enProceso, boolean lista, boolean entregada) {
        this.confeccionada = confeccionada;
        // this.fechaDeConfeccion = fechaDeConfeccion;
        // this.fechaDeRetiro = fechaDeRetiro;
        this.enProceso = enProceso;
        this.lista = lista;
        this.entregada = entregada;
    }


    public boolean isConfeccionada() {
        return confeccionada;
    }

    public void setConfeccionada(boolean confeccionada) {
        this.confeccionada = confeccionada;
    }

    /*
    public ? getFechaDeConfeccion() {
        return fechaDeConfeccion;
    }

    public void setFechaDeConfeccion(? fechaDeConfeccion) {
        this.fechaDeConfeccion = fechaDeConfeccion;
    }

    public ? getFechaDeRetiro() {
        return fechaDeRetiro;
    }

    public void setFechaDeRetiro(? fechaDeRetiro) {
        this.fechaDeRetiro = fechaDeRetiro;
    }
    */

    public boolean isEnProceso() {
        return enProceso;
    }

    public void setEnProceso(boolean enProceso) {
        this.enProceso = enProceso;
    }

    public boolean isLista() {
        return lista;
    }

    public void setLista(boolean lista) {
        this.lista = lista;
    }

    public boolean isEntregada() {
        return entregada;
    }

    public void setEntregada(boolean entregada) {
        this.entregada = entregada;
    }

    // 🔹 toString
    @Override
    public String toString() {
        return "Receta{" +
                "confeccionada=" + confeccionada +
                // ", fechaDeConfeccion=" + fechaDeConfeccion +
                // ", fechaDeRetiro=" + fechaDeRetiro +
                ", enProceso=" + enProceso +
                ", lista=" + lista +
                ", entregada=" + entregada +
                '}';
    }
}
