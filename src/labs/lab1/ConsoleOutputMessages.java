package labs.lab1;

public enum ConsoleOutputMessages {
    ENTER_PATH("Введіть шлях до папки, яку потрібно обробити:"),
    INCORRECT_PATH("Ви ввели шлях у неправильному форматі. Приклад коректного шляху до папки: " +
                    "/home/user/directory. Спробуйте знову:"),
    DIRECTORY_DOESNT_EXIST("Каталогу з такою назвою не знайдено. Спробуйте знову:");

    private String message;

    ConsoleOutputMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
