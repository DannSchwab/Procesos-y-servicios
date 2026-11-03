package almacen;

public class ProcesadorPedidos implements Runnable {

    private int[] importes;
    private int facturado = 0;
    private int enviosGratis = 0;
    private int pedidoMaximo = 0;

    public ProcesadorPedidos(int[] importes) {
        this.importes = importes;
    }

    /**
     * runneable para el hilo. Se ejecuta cuando se hace el start.
     * Mismos compentarios en el inicio que en el run la clase ControlStock
     */
    @Override
    public void run() {
        Thread hilo = Thread.currentThread();
        String nombre = hilo.getName();
        System.out.println(nombre + " empieza (prioridad " + hilo.getPriority() + ")");

        // se comprueba importe por importe hasta llegar al final del Array
        for (int i = 0; i < importes.length; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(nombre + " interrumpido");
                return;
            }
            int importe = importes[i];
            // si el importe es mayor a 50 el envio es gratis
            if (importe >= 50) {
                facturado += importe; // se le suma a facturado el importe para llevar una cuenta total
                enviosGratis++;
            // Caso contrario, cuesta
            } else {
                facturado += importe + 5; // se le suma a facturado el importe para llevar una cuenta total junto con el coste de 5 del envio
            }
            // Aquí se guarda el pedido con el mayor importe
            if (importe > pedidoMaximo) {
                pedidoMaximo = importe;
            }
            // i + 1 para contar la cantidad real de pedidos, luego se divide entre 10 y se comprueba que el resto de 0 para contar pedidos de 10 a 10
            if ((i + 1) % 10 == 0) {
                System.out.println(nombre + ": " + (i + 1) + " pedidos procesados, " + facturado + " € facturados");
            }
        }

        System.out.println(nombre + " termina: " + facturado + " € facturados, " + enviosGratis
                + " envíos gratis, pedido máximo " + pedidoMaximo + " €");
    }

    public int getFacturado() {
        return facturado;
    }

    public int getEnviosGratis() {
        return enviosGratis;
    }

    public int getPedidoMaximo() {
        return pedidoMaximo;
    }
}