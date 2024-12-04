package org.example.controller;

import org.example.exceptions.PersonValidationException;
import org.example.models.Person;
import org.example.views.PersonView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class PersonController implements Runnable  {
    /**
     * Сканер
     */
    private Scanner scanner = new Scanner(System.in);

    private PersonView view = new PersonView();

    private void saveToFile(Person person) throws IOException {
        Path file = Paths.get("persons/"+person.getLastName() + ".txt");
        if (!Files.exists(file)){
            Files.createDirectories(file.getParent());
            Files.createFile(file);
        }

        Files.write(file,(person + "\r\n").getBytes(), StandardOpenOption.APPEND);
    }

    /**
     * Runs this operation
     */
    @Override
    public void run() {
        System.out.println("Программа сохранения персонажей.Для остановки введите 'стоп'");
        System.out.println("Используйте формат <Фамилия> <Имя> <Отчество> <День рождения: день.месяц.год цифрами> <Телефон без +> <Пол:m/f>");
        String input;
        do {
            this.view.printInputCommand();
            input = this.scanner.nextLine();
            if(input.equalsIgnoreCase("Стоп"))
                break;
            try {
                Person current = new Person(input);
                current.parse();
                this.saveToFile(current);
            } catch (PersonValidationException e) {
                this.view.printError(e.getMessage());
            } catch (IOException io) {
                this.view.printError("Не удалось сохранить персонажа в файл. \n Сообщение:\n" + io.getMessage());
            } catch (Exception ex) {
                this.view.printError("Не удалось сохранить персонажа необработанное исключение.\nСообщение:\n" + ex.getMessage());
            }

        } while (true);
    }


}
