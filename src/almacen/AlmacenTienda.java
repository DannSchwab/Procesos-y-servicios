package almacen;

public class AlmacenTienda {

    public static void main(String[] args) {
        String[] productos = {"Auriculares", "Cargador USB", "Funda móvil", "Ratón", "Teclado",
                "Cable HDMI", "Webcam", "Altavoz", "Powerbank", "Memoria USB"};
        int[] stock = {12, 40, 5, 18, 3, 25, 7, 30, 2, 50};
        // Caso 4 (stock alternativo para las pruebas) :
        // int[] stock = {25, 40, 5, 18, 12, 25, 12, 30, 20, 50};
        int[] minimo = {20, 30, 15, 10, 10, 20, 10, 15, 15, 40};
        int[] importes = {35, 80, 12, 64, 50, 49, 120, 22, 75, 18,
                90, 45, 33, 150, 60, 27, 52, 8, 99, 41,
                70, 15, 55, 38, 200, 47, 66, 29, 85, 10};

        // Se instancian ambas clases con los datos del enunciado
        ControlStock control = new ControlStock(productos, stock, minimo);
        ProcesadorPedidos procesador = new ProcesadorPedidos(importes);

        /* Se instancian los hilos con los datos del nombre de la clase 
        y el nombre que recibe para cuando se haga el start() */
        Thread hStock = new Thread(control, "Stock");
        Thread hPedidos = new Thread(procesador, "Pedidos");

        hStock.setPriority(Thread.MAX_PRIORITY);
        hPedidos.setPriority(Thread.MIN_PRIORITY);

        // Se ejecutan los hilos
        hStock.start();
        hPedidos.start();

        try {
        	// Se esera a que termine hStock para continuar el Main()
            hStock.join();
        } catch (InterruptedException e) {
            System.out.println("Main interrumpido esperando a Stock");
        }

        /* 
         * Una vez terminado el hilo Stock se muestra el estado de hPedidos tras mostrar las unidades necesarias a 
         * pedir de la clase control, que ha cambiado el valor de sus atributos al ejecutarse el hilo hStock con la 
         * instanciación de la clase ControlStock (Parece un lio pero yo me entiendo)
         */
        System.out.println("Orden: comprar " + control.getUnidadesAPedir() + " unidades al proveedor (Pedidos está "
                + hPedidos.getState() + ")");

        /* 
         * se crea un objeto compra con la clase CompraProveedor, para luego instanciarse un hilo con esta
         * para darle el resultado de las unidades a pedir obtenidas por el hilo
         */
        CompraProveedor compra = new CompraProveedor(control.getUnidadesAPedir());
        Thread hCompra = new Thread(compra, "Compra");

        hCompra.start();

        try {
        	// se espera a que ambos procesos terminen antes de continuar con el main
            hPedidos.join();
            hCompra.join();
        } catch (InterruptedException e) {
            System.out.println("Main interrumpido esperando a Pedidos y Compra");
        }

        System.out.println("=== Informe del almacén ===");
        System.out.println("Productos a reponer: " + control.getProductosAReponer() + " ("
                + control.getUnidadesAPedir() + " unidades)");
        System.out.println("Compra al proveedor: " + compra.getTotal() + " €");
        System.out.println("Pedidos: " + procesador.getFacturado() + " € facturados, "
                + procesador.getEnviosGratis() + " con envío gratis");
        System.out.println("Pedido máximo: " + procesador.getPedidoMaximo() + " €");
    }
}