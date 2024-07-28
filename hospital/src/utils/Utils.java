package utils;

import javax.swing.JTable;

/**
 *
 * @author andres
 */
public class Utils {

    // Método para verificar si una fila está vacía
    public static boolean isRowEmpty(int row, JTable tabla) {
        for (int col = 0; col < tabla.getColumnCount(); col++) {
            Object value = tabla.getValueAt(row, col);
            if (value != null && !value.toString().trim().isEmpty()) {
                return false; // La fila no está vacía
            }
        }
        return true; // La fila está vacía
    }

}
