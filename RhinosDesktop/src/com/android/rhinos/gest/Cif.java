package com.android.rhinos.gest;

public class Cif extends Id {

	private static final long serialVersionUID = 1L;
	 
	private static final String CONTROL_SOLO_NUMEROS = "ABEH"; // S�lo admiten n�meros como caracter de control
	private static final String CONTROL_SOLO_LETRAS = "KPQS"; // S�lo admiten letras como caracter de control
	private static final String CONTROL_NUMERO_A_LETRA = "JABCDEFGHI"; // Conversi�n de d�gito a letra de control.
	 
	public Cif(String id) {
		super(id);
	}
	
	@Override
	public int getType() {
		return Id.CIF;
	}
	
	@Override
	protected boolean test1() {
		return id.matches("[[A-H][J-N][P-S]UVW][0-9]{7}[0-9A-J]");
	}
	
	@Override
	protected boolean test2() {
        int parA = 0;
        for (int i = 2; i < 8; i += 2) {
            final int digito = Character.digit(id.charAt(i), 10);
            if (digito < 0) {
                return false;
            }
            parA += digito;
        }
 
        int nonB = 0;
        for (int i = 1; i < 9; i += 2) {
            final int digito = Character.digit(id.charAt(i), 10);
            if (digito < 0) {
                return false;
            }
            int nn = 2 * digito;
            if (nn > 9) {
                nn = 1 + (nn - 10);
            }
            nonB += nn;
        }
 
        final int parcialC = parA + nonB;
        final int digitoE = parcialC % 10;
        final int digitoD = (digitoE > 0) ? (10 - digitoE) : 0;
        final char letraIni = id.charAt(0);
        final char caracterFin = id.charAt(8);
 
        final boolean esControlValido =
            // �el caracter de control es v�lido como letra?
            (CONTROL_SOLO_NUMEROS.indexOf(letraIni) < 0
                    && CONTROL_NUMERO_A_LETRA.charAt(digitoD) == caracterFin)
            ||
            // �el caracter de control es v�lido como d�gito?
            (CONTROL_SOLO_LETRAS.indexOf(letraIni) < 0
                    && digitoD == Character.digit(caracterFin, 10));
        
        return esControlValido;
	}
}
