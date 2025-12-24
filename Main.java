import functions.*;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=== ТЕСТИРОВАНИЕ МЕТОДОВ TabulatedFunction ===\n");
            
            // Создаем тестовые данные
            FunctionPoint[] points1 = {
                new FunctionPoint(0.0, 1.2),
                new FunctionPoint(1.0, 3.8),
                new FunctionPoint(2.0, 15.2),
                new FunctionPoint(3.0, 7.1),
                new FunctionPoint(4.0, 9.5)
            };
            
            FunctionPoint[] points2 = {
                new FunctionPoint(0.0, 1.2),
                new FunctionPoint(1.0, 3.8),
                new FunctionPoint(2.0, 15.2),
                new FunctionPoint(3.0, 7.1),
                new FunctionPoint(4.0, 9.5)
            };
            
            FunctionPoint[] points3 = {
                new FunctionPoint(0.0, 1.2),
                new FunctionPoint(1.0, 3.8),
                new FunctionPoint(2.0, 15.3), // Отличается на 0.1
                new FunctionPoint(3.0, 7.1),
                new FunctionPoint(4.0, 9.5)
            };
            
            FunctionPoint[] points4 = {
                new FunctionPoint(0.0, 1.2),
                new FunctionPoint(1.0, 3.8),
                new FunctionPoint(2.0, 15.2)
            };
            
            System.out.println("1. ТЕСТИРОВАНИЕ toString()");
            System.out.println("==================================");
            
            // Создаем объекты ArrayTabulatedFunction
            ArrayTabulatedFunction arrayFunc1 = new ArrayTabulatedFunction(points1);
            ArrayTabulatedFunction arrayFunc2 = new ArrayTabulatedFunction(points2);
            
            // Создаем объекты LinkedListTabulatedFunction
            LinkedListTabulatedFunction linkedFunc1 = new LinkedListTabulatedFunction(points1);
            LinkedListTabulatedFunction linkedFunc2 = new LinkedListTabulatedFunction(points3);
            
            System.out.println("ArrayTabulatedFunction 1: " + arrayFunc1);
            System.out.println("ArrayTabulatedFunction 2: " + arrayFunc2);
            System.out.println("LinkedListTabulatedFunction 1: " + linkedFunc1);
            System.out.println("LinkedListTabulatedFunction 2: " + linkedFunc2);
            
            // Проверка формата toString()
            System.out.println("\nПроверка формата toString():");
            String arrayStr = arrayFunc1.toString();
            System.out.println("Первый символ: '" + arrayStr.charAt(0) + "' (ожидается '{')");
            System.out.println("Последний символ: '" + arrayStr.charAt(arrayStr.length()-1) + "' (ожидается '}')");
            System.out.println("Содержит ли точки в формате (x; y): " + arrayStr.contains("(0.0; 1.2)"));
            
            System.out.println("\n2. ТЕСТИРОВАНИЕ equals()");
            System.out.println("==================================");
            
            System.out.println("arrayFunc1.equals(arrayFunc2) (одинаковые массивы): " + arrayFunc1.equals(arrayFunc2));
            System.out.println("arrayFunc1.equals(linkedFunc1) (разные классы, но одинаковые данные): " + arrayFunc1.equals(linkedFunc1));
            System.out.println("linkedFunc1.equals(linkedFunc2) (разные данные): " + linkedFunc1.equals(linkedFunc2));
            
            // Создаем ArrayTabulatedFunction с другими данными
            ArrayTabulatedFunction arrayFunc3 = new ArrayTabulatedFunction(points3);
            System.out.println("arrayFunc1.equals(arrayFunc3) (разные данные): " + arrayFunc1.equals(arrayFunc3));
            
            // Создаем ArrayTabulatedFunction с другим количеством точек
            ArrayTabulatedFunction arrayFunc4 = new ArrayTabulatedFunction(points4);
            System.out.println("arrayFunc1.equals(arrayFunc4) (разное количество точек): " + arrayFunc1.equals(arrayFunc4));
            
            // Тест с null и другим типом объекта
            System.out.println("arrayFunc1.equals(null): " + arrayFunc1.equals(null));
            System.out.println("arrayFunc1.equals(\"строка\"): " + arrayFunc1.equals("строка"));
            
            // Тест рефлексивности
            System.out.println("arrayFunc1.equals(arrayFunc1) (рефлексивность): " + arrayFunc1.equals(arrayFunc1));
            
            // Тест симметричности
            System.out.println("\nТест симметричности equals():");
            System.out.println("arrayFunc1.equals(linkedFunc1): " + arrayFunc1.equals(linkedFunc1));
            System.out.println("linkedFunc1.equals(arrayFunc1): " + linkedFunc1.equals(arrayFunc1));
            
            // Тест транзитивности
            System.out.println("\nТест транзитивности (для одинаковых объектов):");
            ArrayTabulatedFunction arrayFunc5 = new ArrayTabulatedFunction(points1);
            System.out.println("arrayFunc1.equals(arrayFunc2): " + arrayFunc1.equals(arrayFunc2));
            System.out.println("arrayFunc2.equals(arrayFunc5): " + arrayFunc2.equals(arrayFunc5));
            System.out.println("arrayFunc1.equals(arrayFunc5): " + arrayFunc1.equals(arrayFunc5));
            
            System.out.println("\n3. ТЕСТИРОВАНИЕ hashCode()");
            System.out.println("==================================");
            
            System.out.println("Хэш-код arrayFunc1: " + arrayFunc1.hashCode());
            System.out.println("Хэш-код arrayFunc2: " + arrayFunc2.hashCode());
            System.out.println("Хэш-код linkedFunc1: " + linkedFunc1.hashCode());
            System.out.println("Хэш-код linkedFunc2: " + linkedFunc2.hashCode());
            System.out.println("Хэш-код arrayFunc3: " + arrayFunc3.hashCode());
            System.out.println("Хэш-код arrayFunc4: " + arrayFunc4.hashCode());
            
            // Проверка согласованности equals() и hashCode()
            System.out.println("\nПроверка согласованности equals() и hashCode():");
            System.out.println("arrayFunc1.equals(arrayFunc2) = " + arrayFunc1.equals(arrayFunc2) + 
                             ", hashCodes equal: " + (arrayFunc1.hashCode() == arrayFunc2.hashCode()));
            
            System.out.println("arrayFunc1.equals(arrayFunc3) = " + arrayFunc1.equals(arrayFunc3) + 
                             ", hashCodes equal: " + (arrayFunc1.hashCode() == arrayFunc3.hashCode()));
            
            System.out.println("arrayFunc1.equals(arrayFunc4) = " + arrayFunc1.equals(arrayFunc4) + 
                             ", hashCodes equal: " + (arrayFunc1.hashCode() == arrayFunc4.hashCode()));
            
            // Изменяем объект и проверяем изменение хэш-кода
            System.out.println("\nИзменение объекта и проверка хэш-кода:");
            System.out.println("Хэш-код arrayFunc1 до изменения: " + arrayFunc1.hashCode());
            
            // Сохраняем исходное значение Y
            double originalY = arrayFunc1.getPointY(2);
            
            // Меняем одну координату на несколько тысячных
            arrayFunc1.setPointY(2, originalY + 0.005);
            System.out.println("Хэш-код arrayFunc1 после изменения Y[2] на +0.005: " + arrayFunc1.hashCode());
            
            // Возвращаем исходное значение
            arrayFunc1.setPointY(2, originalY);
            System.out.println("Хэш-код arrayFunc1 после возврата исходного значения: " + arrayFunc1.hashCode());
            
            // Проверка, что хэш-код вернулся к исходному
            System.out.println("Хэш-код вернулся к исходному: " + (arrayFunc1.hashCode() == arrayFunc2.hashCode()));
            
            System.out.println("\n4. ТЕСТИРОВАНИЕ clone()");
            System.out.println("==================================");
            
            // Тестирование клонирования ArrayTabulatedFunction
            System.out.println("Тестирование клонирования ArrayTabulatedFunction:");
            ArrayTabulatedFunction arrayOriginal = new ArrayTabulatedFunction(points1);
            ArrayTabulatedFunction arrayClone = (ArrayTabulatedFunction) arrayOriginal.clone();
            
            System.out.println("Оригинал: " + arrayOriginal);
            System.out.println("Клон: " + arrayClone);
            System.out.println("arrayOriginal.equals(arrayClone): " + arrayOriginal.equals(arrayClone));
            System.out.println("arrayOriginal == arrayClone: " + (arrayOriginal == arrayClone));
            
            // Изменяем оригинал
            System.out.println("\nИзменяем оригинал (увеличиваем Y[0] на 10):");
            double originalY0 = arrayOriginal.getPointY(0);
            arrayOriginal.setPointY(0, originalY0 + 10);
            System.out.println("Оригинал после изменения: " + arrayOriginal);
            System.out.println("Клон после изменения оригинала: " + arrayClone);
            System.out.println("Y[0] в оригинале: " + arrayOriginal.getPointY(0));
            System.out.println("Y[0] в клоне: " + arrayClone.getPointY(0));
            System.out.println("После изменения оригинала equals: " + arrayOriginal.equals(arrayClone));
            
            // Проверяем, что клон не изменился
            if (Math.abs(arrayClone.getPointY(0) - originalY0) < 0.0001) {
                System.out.println("✓ Клон не изменился (глубокое клонирование работает)");
            } else {
                System.out.println("✗ Клон изменился (проблема с глубоким клонированием)");
            }
            
            // Тестирование клонирования LinkedListTabulatedFunction
            System.out.println("\nТестирование клонирования LinkedListTabulatedFunction:");
            LinkedListTabulatedFunction linkedOriginal = new LinkedListTabulatedFunction(points1);
            LinkedListTabulatedFunction linkedClone = (LinkedListTabulatedFunction) linkedOriginal.clone();
            
            System.out.println("Оригинал: " + linkedOriginal);
            System.out.println("Клон: " + linkedClone);
            System.out.println("linkedOriginal.equals(linkedClone): " + linkedOriginal.equals(linkedClone));
            System.out.println("linkedOriginal == linkedClone: " + (linkedOriginal == linkedClone));
            
            // Изменяем оригинал
            System.out.println("\nИзменяем оригинал (увеличиваем Y[1] на 5):");
            double originalY1 = linkedOriginal.getPointY(1);
            linkedOriginal.setPointY(1, originalY1 + 5);
            System.out.println("Оригинал после изменения: " + linkedOriginal);
            System.out.println("Клон после изменения оригинала: " + linkedClone);
            System.out.println("Y[1] в оригинале: " + linkedOriginal.getPointY(1));
            System.out.println("Y[1] в клоне: " + linkedClone.getPointY(1));
            System.out.println("После изменения оригинала equals: " + linkedOriginal.equals(linkedClone));
            
            // Проверяем, что клон не изменился
            if (Math.abs(linkedClone.getPointY(1) - originalY1) < 0.0001) {
                System.out.println("✓ Клон не изменился (глубокое клонирование работает)");
            } else {
                System.out.println("✗ Клон изменился (проблема с глубоким клонированием)");
            }
            
            // Тест глубокого клонирования с изменением нескольких точек
            System.out.println("\nТест глубокого клонирования (множественные изменения):");
            LinkedListTabulatedFunction original = new LinkedListTabulatedFunction(points1);
            LinkedListTabulatedFunction clone = (LinkedListTabulatedFunction) original.clone();
            
            // Сохраняем исходные значения Y для проверки
            double[] originalYValues = new double[original.getPointsCount()];
            for (int i = 0; i < originalYValues.length; i++) {
                originalYValues[i] = original.getPointY(i);
            }
            
            // Изменяем несколько точек в оригинале
            original.setPointY(0, 100);
            original.setPointY(2, 200);
            original.setPointY(4, 300);
            
            System.out.println("Оригинал после изменений: " + original);
            System.out.println("Клон после изменений оригинала: " + clone);
            
            // Проверяем, что измененные точки в клоне не изменились
            boolean deepCloneCorrect = true;
            int[] changedIndices = {0, 2, 4};
            
            for (int i : changedIndices) {
                if (clone.getPointY(i) == original.getPointY(i)) {
                    deepCloneCorrect = false;
                    System.out.println("Ошибка: измененная координата Y[" + i + "] совпадает с клоном: " + original.getPointY(i));
                }
                // Проверяем, что клон сохранил исходные значения
                if (Math.abs(clone.getPointY(i) - originalYValues[i]) > 0.0001) {
                    deepCloneCorrect = false;
                    System.out.println("Ошибка: координата Y[" + i + "] в клоне изменилась: было " + originalYValues[i] + ", стало " + clone.getPointY(i));
                }
            }
            
            // Проверяем, что неизмененные точки остались теми же
            int[] unchangedIndices = {1, 3};
            for (int i : unchangedIndices) {
                if (Math.abs(clone.getPointY(i) - original.getPointY(i)) > 0.0001) {
                    deepCloneCorrect = false;
                    System.out.println("Ошибка: неизмененная координата Y[" + i + "] не совпадает: оригинал=" + original.getPointY(i) + ", клон=" + clone.getPointY(i));
                }
            }
            
            if (deepCloneCorrect) {
                System.out.println("✓ Глубокое клонирование LinkedListTabulatedFunction работает корректно");
            } else {
                System.out.println("✗ Глубокое клонирование LinkedListTabulatedFunction не работает корректно");
            }
            
            // Дополнительный тест для ArrayTabulatedFunction
            System.out.println("\nДополнительный тест глубокого клонирования для ArrayTabulatedFunction:");
            ArrayTabulatedFunction arrayOriginal2 = new ArrayTabulatedFunction(points1);
            ArrayTabulatedFunction arrayClone2 = (ArrayTabulatedFunction) arrayOriginal2.clone();
            
            // Сохраняем исходные значения
            double[] arrayOriginalYValues = new double[arrayOriginal2.getPointsCount()];
            for (int i = 0; i < arrayOriginalYValues.length; i++) {
                arrayOriginalYValues[i] = arrayOriginal2.getPointY(i);
            }
            
            // Изменяем несколько точек
            arrayOriginal2.setPointY(1, 50);
            arrayOriginal2.setPointY(3, 75);
            
            System.out.println("Оригинал после изменений: " + arrayOriginal2);
            System.out.println("Клон после изменений оригинала: " + arrayClone2);
            
            boolean arrayDeepCloneCorrect = true;
            if (arrayClone2.getPointY(1) == arrayOriginal2.getPointY(1)) {
                arrayDeepCloneCorrect = false;
                System.out.println("Ошибка: измененная координата Y[1] совпадает: " + arrayOriginal2.getPointY(1));
            }
            if (Math.abs(arrayClone2.getPointY(1) - arrayOriginalYValues[1]) > 0.0001) {
                arrayDeepCloneCorrect = false;
                System.out.println("Ошибка: координата Y[1] в клоне изменилась: было " + arrayOriginalYValues[1] + ", стало " + arrayClone2.getPointY(1));
            }
            
            if (arrayClone2.getPointY(3) == arrayOriginal2.getPointY(3)) {
                arrayDeepCloneCorrect = false;
                System.out.println("Ошибка: измененная координата Y[3] совпадает: " + arrayOriginal2.getPointY(3));
            }
            if (Math.abs(arrayClone2.getPointY(3) - arrayOriginalYValues[3]) > 0.0001) {
                arrayDeepCloneCorrect = false;
                System.out.println("Ошибка: координата Y[3] в клоне изменилась: было " + arrayOriginalYValues[3] + ", стало " + arrayClone2.getPointY(3));
            }
            
            if (arrayDeepCloneCorrect) {
                System.out.println("✓ Глубокое клонирование ArrayTabulatedFunction работает корректно");
            } else {
                System.out.println("✗ Глубокое клонирование ArrayTabulatedFunction не работает");
            }
            
            System.out.println("\n5. ДОПОЛНИТЕЛЬНЫЕ ТЕСТЫ");
            System.out.println("==================================");
            
            // Тест с конструкторами
            System.out.println("Тест с конструкторами ArrayTabulatedFunction:");
            ArrayTabulatedFunction arrayFromConstructor1 = new ArrayTabulatedFunction(0, 10, 5);
            System.out.println("ArrayTabulatedFunction(0, 10, 5): " + arrayFromConstructor1);
            
            double[] values = {1.0, 2.0, 3.0, 4.0, 5.0};
            ArrayTabulatedFunction arrayFromConstructor2 = new ArrayTabulatedFunction(0, 10, values);
            System.out.println("ArrayTabulatedFunction(0, 10, values): " + arrayFromConstructor2);
            
            System.out.println("\nТест с конструкторами LinkedListTabulatedFunction:");
            LinkedListTabulatedFunction linkedFromConstructor1 = new LinkedListTabulatedFunction(0, 10, 5);
            System.out.println("LinkedListTabulatedFunction(0, 10, 5): " + linkedFromConstructor1);
            
            LinkedListTabulatedFunction linkedFromConstructor2 = new LinkedListTabulatedFunction(0, 10, values);
            System.out.println("LinkedListTabulatedFunction(0, 10, values): " + linkedFromConstructor2);
            
            // Тест с изменением точки X (должно проверять упорядоченность)
            System.out.println("\nТест с изменением координаты X (проверка исключения):");
            try {
                // Пытаемся изменить X первой точки так, чтобы она стала больше второй
                arrayFunc1.setPointX(0, 2.0);
                System.out.println("✗ Не должно было получиться изменить X[0] на 2.0 (нарушит упорядоченность)");
            } catch (InappropriateFunctionPointException e) {
                System.out.println("✓ Корректно выброшено исключение: " + e.getMessage());
            }
            
            // Проверка, что после исключения объект не изменился
            System.out.println("Объект после попытки некорректного изменения: " + arrayFunc1);
            
            System.out.println("\n=== ТЕСТИРОВАНИЕ ЗАВЕРШЕНО ===");
            
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}