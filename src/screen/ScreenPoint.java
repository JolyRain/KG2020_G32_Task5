/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.util.Objects;

/**
 *
 * Описывает координаты экранной точки (пикселя)
 * @author Alexey
 */
public class ScreenPoint {
    /**
     * i - номер пикселя по горизонтальной оси
     * j - номер пикселя по вертикальной оси
     */
    private int i, j;

    /**
     * Создаёт экранную точку.
     * @param i Номер пикселя по горизонтальной оси (X)
     * @param j Номер пикселя по вертикальной оси (Y)
     */
    public ScreenPoint(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScreenPoint point = (ScreenPoint) o;
        return i == point.i &&
                j == point.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    @Override
    public String toString() {
        return "(" + i + ", " + j + ")";
    }
}
