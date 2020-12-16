package math;

public class Vector3 {
    private double[] values; /*Значения хранятся в виде массива из трёх элементов*/

    /**
     * Создаёт экземпляр вектора на основе значений четырёх составляющих
     * @param x первая составляющая, описывающая X-координату
     * @param y вторая составляющая, описывающая Y-координату
     * @param w третья составляющая, описывающая нормализующее значение
     */
    public Vector3(double x, double y, double w) {
        values = new double[]{x, y, w};
    }

    /**
     * Создаёт экземпляр вектора на основе значений трёх составляющих.
     * Четвёртая берётся равной нулю.
     * @param x первая составляющая, описывающая X-координату
     * @param y вторая составляющая, описывающая Y-координату
     * @param z третья составляющая, описывающая Z-координату
     */
    public Vector3(double x, double y) {
        this(x, y, 0);
    }

    /**
     * Создаёт экземпляр четырёхмерного вектора на основе трёхмерного вектора
     * и четвёртой составляющей W.
     * @param v экземпляр трёхмерного вектора
     * @param w четвёртая составляющая.
     */
    public Vector3(Vector2 v, double w) {
        this(v.getX(), v.getY(), w);
    }

    /**
     * Создаёт экземпляр четырёхмерного вектора на основе трёхмерного вектора
     * Четвёртая составляющая W принимается равной нулю.
     * @param v исходный трёхмерный вектор.
     */
    public Vector3(Vector2 v) {
        this(v, 0);
    }

    /**
     * Скрытый конструктор, который создаёт вектор на основе массива.
     * Данный конструктор будет удобен для использования внутри класса.
     * В связи с этим, данный конструктор просто сохраняет принятый массив
     * без дополнительных обработок и проверок.
     * @param array массив с данными
     */
    private Vector3(double[] array) {
        this.values = array;
    }

    /**
     * Создаёт новый четырёхмерный вектор, все компоненты которого равны нулю.
     * @return нулевой четырёхмерный вектор.
     */
    public static Vector3 zero() {
        return new Vector3(new double[3]);
    }

    /**
     * Умножае текущие вектор на число.
     * @param number число, на которое умножается текущий вектор
     * @return новый вектор, который является результатом умножения.
     */
    public Vector3 mul(double number) {
        double[] array = new double[3];
        for (int i = 0; i < array.length; i++)
            array[i] = number * this.at(i);
        return new Vector3(array);
    }

    /**
     * Складывает текущий вектор с другим.
     * @param other вектор, с которым происходит сложение
     * @return рещультирующий вектор.
     */
    public Vector3 plus(Vector3 other) {
        double[] array = new double[3];
        for (int i = 0; i < array.length; i++)
            array[i] = this.at(i) + other.at(i);
        return new Vector3(array);
    }

    /**
     * Очень маленькое число для сравнений
     */
    private static final float EPSILON = 1e-10f;

    /**
     * Возвращает нормализованный по W четырёхмерный вектор.
     * Если W равен 0, то возвращается копия исходного вектора.
     * Если W не равен 1, то возвращается новй вектор, составляющие которого разделены на W.
     * @return новый нормализованный вектор.
     */
    public Vector3 normalize() {
        if (Math.abs(getW()) < EPSILON)
            return new Vector3(this.getX(), this.getY(), 0);
        return new Vector3(this.getX() / this.getW(), this.getY() / this.getW(), 1);
    }

    /**
     * Создаёт трёхмерный вектор, X, Y и Z составляющие которого равны
     * соответствующим значениям нормализованного четырёхмерного вектора.
     * @return новый трёхмерный вектор.
     */
    public Vector2 asVector2() {
        Vector3 n = this.normalize();
        return new Vector2(n.getX(), n.getY());
    }

    /**
     * X-составляющая вектора
     * @return X-составляющая вектора
     */
    public double getX() {
        return values[0];
    }

    /**
     * Y-составляющая вектора
     * @return Y-составляющая вектора
     */
    public double getY() {
        return values[1];
    }

    /**
     * W-составляющая вектора
     * @return W-составляющая вектора
     */
    public double getW() {
        return values[2];
    }

    /**
     * Метод, возвращающий составляющую вектора по порядковому номеру
     * @param idx порядковый номер
     * @return значение составляющей вектора
     */
    public double at(int idx) {
        return values[idx];
    }
}
