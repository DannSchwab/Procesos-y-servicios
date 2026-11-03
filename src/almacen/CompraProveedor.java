package almacen;

public class CompraProveedor implements Runnable {

    private int unidades;
    private int total = 0;

    public CompraProveedor(int unidades) {
        this.unidades = unidades;
    }

    /**
     * runneable para el hilo. Se ejecuta cuando se hace el start.
     * Mismos compentarios en el inicio que en el run la clase ControlStock
     */
    @Override
    public void run() {
        Thread hilo = Thread.currentThread();
        String nombre = hilo.getName();
        System.out.println(nombre + " empieza con " + unidades + " unidades (prioridad " + hilo.getPriority() + ")");

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.println(nombre + " interrumpido");
            return;
        }

        if (unidades == 0) {
            System.out.println(nombre + " termina: no hace falta pedir nada");
            return;
        }

        // el precio del producto cambia en función de la cantidad pedida (Compra al por mayor para un menor coste de unidad)
        int precio;
        if (unidades >= 100) {
            precio = 3;
        } else if (unidades >= 50) {
            precio = 4;
        } else {
            precio = 5;
        }

        int coste = unidades * precio;
        // Dependiendo el coste el transporte sale gratis
        /* (Apuntes) se podría cambiar este controlador if/else por:
         * [int transporte = (coste < 200) ? 20 : 0;] con una sola linea, donde delante del ? se indica la condición, 
         * y detrás de este el valor de la variable si se cumple la condición después de : se indica el valor de este en cualquier otro caso (else) 
         */
        int transporte;
        if (coste < 200) {
            transporte = 20;
        } else {
            transporte = 0;
        }
        total = coste + transporte;

        System.out.println(nombre + " termina: " + unidades + " uds a " + precio + " €/ud = " + coste
                + " €, transporte " + transporte + " €, total " + total + " €");
    }

    public int getTotal() {
        return total;
    }
}