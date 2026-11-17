package panelDeVuelos;

public class Panel {
	
	
	final int PREVISTA = 600;
	// En minutos
	private int retraso;
	// En horas. Ej: 10:30, 10:45, 11:00 (cambiar luego)
	private String salida;
	
	
	public Panel(int retraso) {
		this.retraso = retraso;
		this.salida = hora(retraso);
	}


	public int getRetraso() {
		return retraso;
	}


	public void setRetraso(int retraso) {
		this.retraso = retraso;
	}

	public String hora(int minutos) {
		int hora = (minutos / 60);
		int min = (minutos % 60);
		
		String salida =(String.valueOf(hora) + ":" + String.valueOf(min));
		
		return salida;
	}
	
}
