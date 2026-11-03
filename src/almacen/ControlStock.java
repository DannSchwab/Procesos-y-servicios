package almacen;

public class ControlStock implements Runnable {

    private String[] productos;
    private int[] stock;
    private int[] minimo;
    private int productosAReponer = 0;
    private int unidadesAPedir = 0;

    public ControlStock(String[] productos, int[] stock, int[] minimo) {
        this.productos = productos;
        this.stock = stock;
        this.minimo = minimo;
    }

    /**
     * runneable para el hilo. Se ejecuta cuando se hace el start.
     */
    @Override
    public void run() {
        Thread hilo = Thread.currentThread();
        // se guarda el nombre dado al hilo cuando se instancia.
        String nombre = hilo.getName();
        System.out.println(nombre + " empieza (prioridad " + hilo.getPriority() + ")");

        // Controla uno por uno los productos de los array de stock[], minimo[] y productos[]
        for (int i = 0; i < productos.length; i++) {
            try {
            	// simula el tiempo de revisar un producto (0,2 segundos)
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println(nombre + " interrumpido");
                return;
            }
            
            /* se comprueba si el stock es inferior al mínimo que se 
            pide y, de ser así se busca llegar al doble del mínimo*/
            if (stock[i] < minimo[i]) {
                int unidades = minimo[i] * 2 - stock[i];
                // contador para saber 
                productosAReponer++;
                unidadesAPedir = unidadesAPedir + unidades; // o unidadesAPedir += unidades; 
                System.out.println(nombre + ": reponer " + productos[i] + " (" + unidades + " uds)");
            }
        }

        System.out.println(nombre + " termina: " + productosAReponer + " productos a reponer, "
                + unidadesAPedir + " unidades en total");
    }

    public int getProductosAReponer() {
        return productosAReponer;
    }

    public int getUnidadesAPedir() {
        return unidadesAPedir;
    }
}