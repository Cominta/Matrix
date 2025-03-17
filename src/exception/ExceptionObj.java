package exception;

public class ExceptionObj {
    public enum Types {
        INPUT_ERROR,
        OUT_OF_RANGE,
        DIVIDE_BY_ZERO,
        NULL_MATRIX,
        INVALID_MODES,
        INVALID_SIZES,
        FILE_ERROR
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
