package modelo;

public class Tablero {
	char[][] casillas = new char[6][7];
	private int ronda = 0;
	private boolean finalizado = false;
	private int ganador = -1;

	public Tablero() {
		for (int fila = 0; fila < casillas.length; fila++){
			for (int columna = 0; columna < casillas[0].length; columna++){
				casillas[fila][columna] = ' ';
			}
		}
	}

	public boolean juegoFinalizado() {
		return finalizado;
	}

	public int getJugador() {
		return ronda % 2;
	}

	public int getEspectador() {
		return (ronda + 1) % 2;
	}

	public char getSimboloJugador() {
		if(getJugador() % 2 == 0) {
			return 'O';
		} else {
			return 'X';
		}
	}

	public void ponerFicha(int columna) {
		char ficha = getSimboloJugador();

		for (int fila = casillas.length - 1; fila >= 0; fila--){
			if(casillas[fila][columna] == ' '){
				casillas[fila][columna] = ficha;
				break;
			}
		}

		if(comprobarGanador()) {
			finalizado = true;
			ganador = getJugador();
		} else if (ronda >= 42) {
			finalizado = true;
		} else {
			ronda++;
		}
	}

	private  boolean comprobarGanador() {
		char ficha = getSimboloJugador();

		// Horizontal
		for(int fila = 0; fila < casillas.length; fila++){
			for (int columna = 0; columna < casillas[0].length - 3; columna++){
				if (casillas[fila][columna] == ficha && 
						casillas[fila][columna+1] == ficha &&
						casillas[fila][columna+2] == ficha &&
						casillas[fila][columna+3] == ficha){
					return true;
				}
			}			
		}
		// Vertical
		for(int fila = 0; fila < casillas.length - 3; fila++){
			for(int columna = 0; columna < casillas[0].length; columna++){
				if (casillas[fila][columna] == ficha && 
						casillas[fila+1][columna] == ficha &&
						casillas[fila+2][columna] == ficha &&
						casillas[fila+3][columna] == ficha){
					return true;
				}
			}
		}
		// Diagonal hacia arriba
		for(int fila = 3; fila < casillas.length; fila++){
			for(int columna = 0; columna < casillas[0].length - 3; columna++){
				if (casillas[fila][columna] == ficha && 
						casillas[fila-1][columna+1] == ficha &&
						casillas[fila-2][columna+2] == ficha &&
						casillas[fila-3][columna+3] == ficha){
					return true;
				}
			}
		}
		// Diagonal hacia abajo
		for(int fila = 0; fila < casillas.length - 3; fila++){
			for(int columna = 0; columna < casillas[0].length - 3; columna++){
				if (casillas[fila][columna] == ficha && 
						casillas[fila+1][columna+1] == ficha &&
						casillas[fila+2][columna+2] == ficha &&
						casillas[fila+3][columna+3] == ficha){
					return true;
				}
			}
		}

		return false;
	}

	public int getGanador() {
		return ganador;
	}

	public int getPerdedor() {
		if (ganador != -1) {
			return (ganador + 1) %  2;
		} else {
			return -1;
		}

	}

	public boolean validarEntrada(int columna) {
		if (columna < 0 || columna > casillas[0].length - 1){
			return false;
		} else if (casillas[0][columna] != ' '){
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		StringBuilder salida = new StringBuilder();

		salida.append(" 0 1 2 3 4 5 6\r\n");
		salida.append("---------------\r\n");
		for (int fila = 0; fila < casillas.length; fila++) {
			salida.append("|");
			for (int columna = 0; columna < casillas[0].length; columna++) {
				salida.append(casillas[fila][columna]);
				salida.append("|");
			}
			salida.append("\r\n");
			salida.append("---------------\r\n");
		}
		salida.append(" 0 1 2 3 4 5 6\r\n");

		return salida.toString();
	}
}
