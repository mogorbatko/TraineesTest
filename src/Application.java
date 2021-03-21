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
        int i = Integer.parseInt(string.replaceAll("\\D",""));
        for (int j = 0; j < i; j++) {
            result.append(string.replaceAll("\\d|\\W", ""));
        }
        return result.toString();

    }
    //Метод-валидатор строк
    public static String stringsValidator(String string) {
        if (string.matches("[a-zA-Z0-9]")) {
            return "";
        }

        Pattern pattern = Pattern.compile("\\d?\\[");
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            if (matcher.group().matches("\\d{0}\\[")) {
                return "";
            }
        }
        return string;
    }
}
