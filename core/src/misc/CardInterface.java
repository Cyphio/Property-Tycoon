package misc;

public interface CardInterface {
    void setAction(String action);

    String getAction();

    void setValue(int value);

    int getValue();

    void runAction();

    String getCardMessage();
}
