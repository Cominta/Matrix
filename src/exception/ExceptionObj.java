package exception;

public class ExceptionObj {
    public enum Types {
        INPUT_ERROR,
        OUT_OF_RANGE
    }

    Types type;
    String message;

    public ExceptionObj(Types type, String message) {
        this.type = type;
        this.message = message;
    }

    public Types getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
