package model;

public enum Month {
    JANUARY("январь"),
    FEBRUARY("февраль"),
    MARCH("март");

    private String ru;

    Month(String ru) {
        this.ru = ru;
    }

    public String translate() {
        return ru;
    }

    public static Month convertToMonth(int number) {
        switch (number) {
            case 1:
                return JANUARY;
            case 2:
                return FEBRUARY;
            case 3:
                return MARCH;
            default:
                throw new RuntimeException("Недопустимое значение месяца");
        }
    }
}
