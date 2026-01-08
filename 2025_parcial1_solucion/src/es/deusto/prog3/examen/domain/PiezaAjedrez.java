package es.deusto.prog3.examen.domain;

public enum PiezaAjedrez {
    REY_BLANCO("♔", "resources/images/pieces/Chess_klt60.png"),
    REINA_BLANCA("♕", "resources/images/pieces/Chess_qlt60.png"),
    TORRE_BLANCA("♖", "resources/images/pieces/Chess_rlt60.png"),
    ALFIL_BLANCO("♗", "resources/images/pieces/Chess_blt60.png"),
    CABALLO_BLANCO("♘", "resources/images/pieces/Chess_nlt60.png"),
    PEON_BLANCO("♙", "resources/images/pieces/Chess_plt60.png"),

    REY_NEGRO("♚", "resources/images/pieces/Chess_kdt60.png"),
    REINA_NEGRA("♛", "resources/images/pieces/Chess_qdt60.png"),
    TORRE_NEGRA("♜", "resources/images/pieces/Chess_rdt60.png"),
    ALFIL_NEGRO("♝", "resources/images/pieces/Chess_bdt60.png"),
    CABALLO_NEGRO("♞", "resources/images/pieces/Chess_ndt60.png"),
    PEON_NEGRO("♟", "resources/images/pieces/Chess_pdt60.png"),

    VACIO("", "");
	
	private String simbolo;
	private String imagen;
	
	private PiezaAjedrez(String simbolo, String imagen) {
		this.simbolo = simbolo;
		this.imagen = imagen;
	}
	
	public String getImagen() {
		return imagen;
	}
	
	public String getSimbolo() {
		return simbolo;
	}
	
	@Override
	public String toString() {
		return getSimbolo();
	}
}
