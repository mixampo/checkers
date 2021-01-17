package utils;

import models.Box;

import java.util.List;

public class BoardPrinter {
    public static String toString(List<Box> boxes) {
        int counter = 0;
        StringBuilder sb = new StringBuilder();

        for (Box box : boxes) {
            if (box.getPiece() != null) {
                sb.append("| ").append(box.getPiece().getColor()).append(" ");
            } else {
                sb.append("|   ");
            }
            counter++;
            if (counter == 10) {
                sb.append("|\n");
                counter = 0;
            }
        }
        return sb.toString();
    }
}
