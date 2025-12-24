package functions;
import java.io.Serializable;

public class FunctionPoint implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    
    private double x;
    private double y;
    
    public FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public FunctionPoint(FunctionPoint point) {
        this.x = point.x;
        this.y = point.y;
    }
    
    public FunctionPoint() {
        this.x = 0;
        this.y = 0;
    }
    
    // Геттеры и сеттеры для чтения и изменения данных
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        // Форматирование с точкой с запятой как в примере задания
        return "(" + x + "; " + y + ")";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        FunctionPoint that = (FunctionPoint) o;
        
        // Используем Double.compare для корректного сравнения чисел с плавающей точкой
        // и специальных значений (NaN, Infinity)
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }
    
    @Override
    public int hashCode() {
        // Преобразуем double в long bits
        long xBits = Double.doubleToLongBits(x);
        long yBits = Double.doubleToLongBits(y);
        
        // Разбиваем каждый long на два int (старшие и младшие 4 байта)
        int xHigh = (int)(xBits >>> 32);
        int xLow = (int)xBits;
        int yHigh = (int)(yBits >>> 32);
        int yLow = (int)yBits;
        
        // Комбинируем все значения с помощью XOR
        return xHigh ^ xLow ^ yHigh ^ yLow;
    }
    
    @Override
	public Object clone() {
		return new FunctionPoint(this.x, this.y);
	}
}