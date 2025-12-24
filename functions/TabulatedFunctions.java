package functions;

import java.io.*;
import java.util.ArrayList;

/**
 * Вспомогательный класс со статическими методами для работы с табулированными функциями.
 * Не может быть инстанциирован.
 */
public class TabulatedFunctions {
    
    // Приватный конструктор для предотвращения создания экземпляров класса
    private TabulatedFunctions() {
        throw new AssertionError("Нельзя создавать экземпляры класса TabulatedFunctions");
    }
    
    // ========== Методы табулирования функций ==========
    
    /**
     * Табулирует функцию на заданном отрезке с заданным количеством точек
     */
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        if (pointsCount < 2) {
            throw new IllegalArgumentException("Количество точек должно быть не менее 2");
        }
        
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левая граница должна быть меньше правой границы");
        }
        
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()) {
            throw new IllegalArgumentException(
                "Границы табулирования [" + leftX + ", " + rightX + 
                "] выходят за область определения функции [" + 
                function.getLeftDomainBorder() + ", " + function.getRightDomainBorder() + "]"
            );
        }
        
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);
        
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            double y = function.getFunctionValue(x);
            points[i] = new FunctionPoint(x, y);
        }
        
        return new ArrayTabulatedFunction(points);
    }
    
    /**
     * Табулирует функцию на заданном отрезке с заданным шагом
     */
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, double step) {
        if (step <= 0) {
            throw new IllegalArgumentException("Шаг табулирования должен быть положительным");
        }
        
        int pointsCount = (int) Math.ceil((rightX - leftX) / step) + 1;
        if (Math.abs(leftX + (pointsCount - 1) * step - rightX) > 1e-10) {
            pointsCount++;
        }
        
        return tabulate(function, leftX, rightX, pointsCount);
    }
    
    // ========== Фабричные методы создания табулированных функций ==========
    
    /**
     * Создает табулированную функцию из массива точек
     */
    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
        return new ArrayTabulatedFunction(points);
    }
    
    /**
     * Создает табулированную функцию с равномерным распределением точек
     */
    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
        return new ArrayTabulatedFunction(leftX, rightX, values);
    }
    
    /**
     * Создает табулированную функцию с равномерным распределением точек и нулевыми значениями
     */
    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
        return new ArrayTabulatedFunction(leftX, rightX, pointsCount);
    }
    
    /**
     * Создает табулированную функцию с использованием LinkedListTabulatedFunction
     */
    public static TabulatedFunction createLinkedListTabulatedFunction(FunctionPoint[] points) {
        return new LinkedListTabulatedFunction(points);
    }
    
    /**
     * Создает табулированную функцию с использованием ArrayTabulatedFunction
     */
    public static TabulatedFunction createArrayTabulatedFunction(FunctionPoint[] points) {
        return new ArrayTabulatedFunction(points);
    }
    
    // ========== Методы для работы с байтовыми потоками ==========
    
    /**
     * Выводит табулированную функцию в байтовый поток
     */
    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        DataOutputStream dataOut = new DataOutputStream(out);
        
        int pointsCount = function.getPointsCount();
        dataOut.writeInt(pointsCount);
        
        for (int i = 0; i < pointsCount; i++) {
            dataOut.writeDouble(function.getPointX(i));
            dataOut.writeDouble(function.getPointY(i));
        }
    }
    
    /**
     * Вводит табулированную функцию из байтового потока
     */
    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        DataInputStream dataIn = new DataInputStream(in);
        
        int pointsCount = dataIn.readInt();
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        
        for (int i = 0; i < pointsCount; i++) {
            double x = dataIn.readDouble();
            double y = dataIn.readDouble();
            points[i] = new FunctionPoint(x, y);
        }
        
        return new ArrayTabulatedFunction(points);
    }
    
    // ========== Методы для работы с символьными потоками ==========
    
    /**
     * Записывает табулированную функцию в символьный поток
     */
    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        BufferedWriter writer = new BufferedWriter(out);
        
        int pointsCount = function.getPointsCount();
        writer.write(String.valueOf(pointsCount));
        writer.newLine();
        
        for (int i = 0; i < pointsCount; i++) {
            writer.write(function.getPointX(i) + " " + function.getPointY(i));
            writer.newLine();
        }
        
        writer.flush();
    }
    
    /**
     * Читает табулированную функцию из символьного потока
     */
    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        // Используем BufferedReader для построчного чтения
        BufferedReader reader = new BufferedReader(in);
        ArrayList<Double> numbers = new ArrayList<>();
        
        String line;
        int lineNumber = 0;
        
        try {
            // Читаем первую строку - количество точек
            line = reader.readLine();
            if (line == null) {
                throw new IOException("Неожиданный конец потока");
            }
            lineNumber++;
            
            // Парсим количество точек
            int pointsCount = Integer.parseInt(line.trim());
            numbers.add((double) pointsCount);
            
            // Читаем остальные строки с координатами
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    continue; // Пропускаем пустые строки
                }
                
                // Разделяем строку по пробелам
                String[] parts = line.split("\\s+");
                if (parts.length != 2) {
                    throw new IOException("Некорректный формат строки " + lineNumber + 
                                        ": ожидалось два числа через пробел, получено: " + line);
                }
                
                // Парсим координаты
                try {
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    numbers.add(x);
                    numbers.add(y);
                } catch (NumberFormatException e) {
                    throw new IOException("Некорректный формат числа в строке " + lineNumber + ": " + line);
                }
            }
            
        } catch (NumberFormatException e) {
            throw new IOException("Некорректный формат числа: " + e.getMessage());
        }
        
        // Проверяем соответствие количества точек
        int pointsCount = numbers.get(0).intValue();
        
        if (pointsCount * 2 + 1 != numbers.size()) {
            throw new IOException("Некорректное количество точек: ожидалось " + 
                                pointsCount + " точек (" + (pointsCount * 2) + " чисел), " +
                                "но получено " + (numbers.size() - 1) + " чисел");
        }
        
        // Создаем массив точек
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; i++) {
            double x = numbers.get(1 + i * 2);
            double y = numbers.get(1 + i * 2 + 1);
            points[i] = new FunctionPoint(x, y);
        }
        
        return new ArrayTabulatedFunction(points);
    }
    
    /**
     * Альтернативный метод чтения табулированной функции с использованием StreamTokenizer
     * (сохраняем для совместимости, если нужно именно StreamTokenizer)
     */
    public static TabulatedFunction readTabulatedFunctionWithStreamTokenizer(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        
        // Настраиваем токенизатор для чтения чисел
        tokenizer.resetSyntax();
        tokenizer.wordChars('0', '9');
        tokenizer.wordChars('.', '.');
        tokenizer.wordChars('-', '-');
        tokenizer.wordChars('e', 'e');
        tokenizer.wordChars('E', 'E');
        tokenizer.wordChars('+', '+'); // для экспоненциальной записи
        tokenizer.whitespaceChars(' ', ' ');
        tokenizer.whitespaceChars('\t', '\t');
        tokenizer.whitespaceChars('\n', '\n');
        tokenizer.whitespaceChars('\r', '\r');
        
        // Сначала читаем количество точек
        if (tokenizer.nextToken() != StreamTokenizer.TT_EOF && tokenizer.ttype == StreamTokenizer.TT_WORD) {
            try {
                int pointsCount = Integer.parseInt(tokenizer.sval);
                ArrayList<Double> numbers = new ArrayList<>();
                numbers.add((double) pointsCount);
                
                // Читаем пары координат
                for (int i = 0; i < pointsCount; i++) {
                    for (int j = 0; j < 2; j++) {
                        if (tokenizer.nextToken() != StreamTokenizer.TT_EOF && 
                            tokenizer.ttype == StreamTokenizer.TT_WORD) {
                            try {
                                double value = Double.parseDouble(tokenizer.sval);
                                numbers.add(value);
                            } catch (NumberFormatException e) {
                                throw new IOException("Некорректный формат числа: " + tokenizer.sval);
                            }
                        } else {
                            throw new IOException("Неожиданный конец потока или токен");
                        }
                    }
                }
                
                // Проверяем, что все точки прочитаны
                if (pointsCount * 2 + 1 != numbers.size()) {
                    throw new IOException("Некорректное количество точек");
                }
                
                // Создаем массив точек
                FunctionPoint[] points = new FunctionPoint[pointsCount];
                for (int i = 0; i < pointsCount; i++) {
                    double x = numbers.get(1 + i * 2);
                    double y = numbers.get(1 + i * 2 + 1);
                    points[i] = new FunctionPoint(x, y);
                }
                
                return new ArrayTabulatedFunction(points);
                
            } catch (NumberFormatException e) {
                throw new IOException("Некорректный формат количества точек: " + tokenizer.sval);
            }
        } else {
            throw new IOException("Не удалось прочитать количество точек");
        }
    }
    
    /**
     * Дополнительный метод: загружает табулированную функцию из текстового файла с использованием BufferedReader
     */
    public static TabulatedFunction loadFromTextFileWithBufferedReader(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return readTabulatedFunction(reader);
        }
    }
    
    // ========== Дополнительные утилитные методы ==========
    
    /**
     * Сохраняет табулированную функцию в файл (байтовый формат)
     */
    public static void saveToFile(TabulatedFunction function, String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            outputTabulatedFunction(function, fos);
        }
    }
    
    /**
     * Загружает табулированную функцию из файла (байтовый формат)
     */
    public static TabulatedFunction loadFromFile(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(filename)) {
            return inputTabulatedFunction(fis);
        }
    }
    
    /**
     * Сохраняет табулированную функцию в текстовый файл
     */
    public static void saveToTextFile(TabulatedFunction function, String filename) throws IOException {
        try (FileWriter fw = new FileWriter(filename)) {
            writeTabulatedFunction(function, fw);
        }
    }
    
    /**
     * Загружает табулированную функцию из текстового файла
     */
    public static TabulatedFunction loadFromTextFile(String filename) throws IOException {
        try (FileReader fr = new FileReader(filename)) {
            return readTabulatedFunction(fr);
        }
    }
    
    /**
     * Конвертирует табулированную функцию в строку для отладки
     */
    public static String toString(TabulatedFunction function) {
        StringBuilder sb = new StringBuilder();
        sb.append("TabulatedFunction[");
        sb.append("points=").append(function.getPointsCount());
        sb.append(", domain=[").append(function.getLeftDomainBorder());
        sb.append(", ").append(function.getRightDomainBorder()).append("]");
        sb.append(", points: ");
        
        for (int i = 0; i < Math.min(function.getPointsCount(), 5); i++) {
            if (i > 0) sb.append(", ");
            sb.append("(").append(String.format("%.4f", function.getPointX(i)));
            sb.append(", ").append(String.format("%.4f", function.getPointY(i))).append(")");
        }
        
        if (function.getPointsCount() > 5) {
            sb.append(", ...");
        }
        
        sb.append("]");
        return sb.toString();
    }
}