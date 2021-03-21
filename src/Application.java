import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Условие:
Написать на Java программу распаковывания строки. На вход поступает строка вида число[строка],
на выход - строка, содержащая повторяющиеся подстроки.

Пример:
Вход: 3[xyz]4[xy]z
Выход: xyzxyzxyzxyxyxyxyz

Ограничения:
- одно повторение может содержать другое. Например: 2[3[x]y]  = xxxyxxxy
- допустимые символы на вход: латинские буквы, числа и скобки []
- числа означают только число повторений
- скобки только для обозначения повторяющихся подстрок
- входная строка всегда валидна.

Дополнительное задание:
Проверить входную строку на валидность.
 */

public class Application {
    public static void main(String[] args) throws IOException {
        //Чтение строки с консоли
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        //Вывод консоль распакованной строки
        System.out.println(stringsUnpacker(input));
    }

    //Метод-распаковщик
    public static String stringsUnpacker(String string) {
        //Проверка строки
        string = stringsValidator(string);
        //Многократный вызов метода stringReplacer() по условию наличия в строке цифр.
        while (!string.matches("^\\D*$")) {
            string = stringsReplacer(string);
        }
        return string;
    }

    //Метод-заменщик подстрок
    public static String stringsReplacer(String string) {
        //Обявление паттерна подстроки
        Pattern pattern = Pattern.compile("\\d*\\[[a-zA-Z]+]");
        Matcher matcher = pattern.matcher(string);
        //Замена подстроки совпадающей с паттерном на размноженную строку
        while (matcher.find()) {
            string = string.replace(matcher.group(), stringsMultiplier(matcher.group()));
        }
        return string;
    }

    //Метод-множитель строк
    public static String stringsMultiplier(String string) {
        StringBuilder result = new StringBuilder();
        //Кратность
        int i = Integer.parseInt(string.replaceAll("\\D", ""));
        //Подстрока
        String substring = string.replaceAll("\\d|\\W", "");
        for (int j = 0; j < i; j++) {
            result.append(substring);
        }
        return result.toString();
    }

    //Метод-валидатор строк
    public static String stringsValidator(String string) {
        //Проверка строки на допустимые символы
        if (!(string.matches("^[a-zA-Z0-9\\[\\]]*$")) || string.matches("^\\[.+")) {
            throw new IllegalArgumentException();
        }
        //Проверка соответствия открытых скобок закрытым
        int braceCounter = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '[') {
                braceCounter++;
            }
            if (string.charAt(i) == ']') {
                braceCounter--;
                if (braceCounter < 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        if (!(braceCounter == 0)) {
            throw new IllegalArgumentException();
        }
        //Проверка наличия цифр перед скобкой
        Pattern pattern = Pattern.compile("\\D+\\[");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            throw new IllegalArgumentException();
        }
        return string;
    }
}
