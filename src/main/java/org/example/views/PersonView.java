package org.example.views;

public class PersonView {
    /**
     * Сообщение о вводе данных
     */
    public void printInputCommand(){
        System.out.println("""
                Введите данные персонажачерез пробел:
                """);
    }

    /**
     * Вывод сообщения об удачном сохранении
     * @param filename Имя файла
     */
    public void printSuccess(String filename) {
        System.out.println("Данные сохранены в файле:" + filename);
    }

    /**
     * Вывод сообщения об ошибке сохранения
     */
    public void printError(String message) {
        System.out.println("Не удалось сохранить данные. \n Сообщение:\n" + message);
    }
}
