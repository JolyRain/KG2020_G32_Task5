/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Описывает координаты реальной точки.
 *
 * @author Alexey
 */
public class Vector2 {
    private double x, y;

    /**
     * Создаёт новый вектор/точку
     *
     * @param x x-составляющая (горизонтальная ось)
     * @param y y-составляющая (вертикальная ось)
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 rotate(Vector2 point, double angle){
        Vector2 rotatedPoint = new Vector2(0,0);
        rotatedPoint.x = point.x * cos(angle) - point.y * sin(angle);
        rotatedPoint.y = point.x * sin(angle) + point.y * cos(angle);
        return rotatedPoint;
    }

    public Vector2 rotate(Vector2 center, Vector2 point, double angle){
        Vector2 rotatedPoint = new Vector2(0,0);
        rotatedPoint.x = center.x + (point.x - center.x) * cos(angle) - (point.y - center.y) * sin(angle);
        rotatedPoint.y = center.y + (point.y - center.y) * cos(angle) + (point.x - center.x) * sin(angle);
        return rotatedPoint;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Вычисляет новый вектор, который является результатом сложения двух векторов
     *
     * @param other другой вектор, с которым складывается текущий
     * @return новый вектор
     */
    public Vector2 plus(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 minus(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    /**
     * Вычисляет новый вектор, котрый является результатом умножаения текущего вектора на число
     *
     * @param n число, на которое умножается вектор
     * @return новый вектор
     */
    public Vector2 mul(double n) {
        return new Vector2((x * n), y * n);
    }

    /**
     * Метод возвращает единичный (нормализованный) вектор
     * Если вектор нулевой, то возвращает новый нулевой вектор
     *
     * @return Получившийся вектор
     */
    public Vector2 normalize() {
        double len = length();
        if (len < 1e-12) /*Да, здесь не по модулю, т.к. корень не будет отрицательным.*/
            return new Vector2(0, 0);
        return new Vector2(x / len, y / len);
    }

    /**
     * Метод возвращает модуль вектора
     *
     * @return Вычесленное значение
     */
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }
}
