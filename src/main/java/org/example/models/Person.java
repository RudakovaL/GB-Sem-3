package org.example.models;

import org.example.exceptions.PersonValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Person {

    /**
     * Данные от пользователя
     */
    private String stringData;

    /**
     * Конструктор
     * @param input Данные от пользователя
     */
    public Person(String input) {
        this.stringData = input;
    }

    /**
     * Имя
     */
    private String firstName;

    /**
     * Отчество
     */
    private String middleName;

    /**
     * Фамилия
     */
    private String lastName;

    /**
     * Дата рождения
     */
    private Date birthday;

    /**
     * Телефон
     */
    private  Long phone;

    /**
     * Пол
     */
    private Sex sex;

    /**
     * Получить фамилию
     * @return Фамилия
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Парсинг модели
     * @throws org.example.exceptions.PersonValidationException Возникает, если ввод от пользователя не прошел проверку
     */
    public void parse() throws PersonValidationException {
        String[] parts = this.stringData.split("");
        if(parts.length != 6)
            throw new PersonValidationException("Проверка ввода всех данных", parts.length, 6);

        this.lastName = parts[0].trim();
        this.firstName = parts[1].trim();
        this.middleName = parts[2].trim();
        this.validNames();

        this.birthday = this.validBirthday(parts[3]);
        this.phone = this.validPhone(parts[4]);
        this.sex = this.validSex(parts[5]);
    }

    /**
     * Валидация пола
     * @param input Ввод пользователя
     * @return Пол
     * @throws PersonValidationException Возникает, если не удается определить пол
     */
    private Sex validSex(String input) throws PersonValidationException {
        try {
            return Sex.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new PersonValidationException("Пол должен содержать символ f или m", input.trim().toUpperCase(), "m или f");
        }
    }

    /**
     * Проверка номера телефона
     * @param input Ввод пользователя
     * @return Телефон
     * @throws PersonValidationException Возникает, когда не удается считать ввод как число
     */
    private Long validPhone(String input) throws  PersonValidationException {
        try {
            return Long.parseLong(input.trim());
        } catch (NumberFormatException ex) {
            throw new PersonValidationException("Номер телефона должен быть числом без пробелов", input, 79000000000L);
        }
    }

    /**
     * Валидация дня рождения
     * @param input Ввод от пользователя
     * @return Дата рождения
     * @throws PersonValidationException Возникает, когда не удалось считать дату
     */
    private Date validBirthday(String input) throws PersonValidationException {
        try {
            return new SimpleDateFormat("dd.MM.yy").parse(input.trim());
        } catch (ParseException e) {
            throw new PersonValidationException("Дата должна быть 'день.месяц.год'", input.trim());
        }
    }

    /**
     * Проверка ФИО
     * @throws PersonValidationException Возникает, если присутствует числа
     */
    private void validNames() throws PersonValidationException {
        this.validNames(this.lastName, "Фамилия");
        this.validNames(this.firstName, "Имя");
        this.validNames(this.middleName, "Отчество");

    }

    /**
     * Проверка антропонима
     * @parms name Значение антропонима
     * @param type Аетропоним
     * @throws PersonValidationException Возникает, если присутвуют числа
     */
    private void validNames(String name, String type) throws PersonValidationException {
        Pattern pattern = Pattern.compile("/[0-9]/gm");
        if(pattern.matcher(name).matches()) {
            throw new PersonValidationException(type + "Не должно содержать цифры", name);
        }
    }

    /**
     * Строчное представление персонажа
     * @return Строчное представление
     */
    @Override
    public String toString() {
        return "%s %s %s %s %d %s".formatted(
                this.lastName,
                this.firstName,
                this.middleName,
                new SimpleDateFormat("dd.MM.yyyy").format(this.birthday),
                this.phone,
                this.sex.value());
    }
}
