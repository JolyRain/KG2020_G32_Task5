/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;


import math.Rectangle;

/**
 *
 * Класс, описывающий прямоугольное поле.
 * @author Alexey
 */
public class Space {
    private Rectangle rectangle;

    /**
     * Создаёт игровое поле
     * @param rectangle Прямоугольник поля
     */
    public Space(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    
    
}
