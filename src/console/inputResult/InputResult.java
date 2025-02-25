package console.inputResult;

public class InputResult {
    public enum Types {
        NONE,
        NEW_MATRIX,
        INPUT_OP,
        MATRIX_OP
    }

    public boolean cont;
    public Types type;

    public InputResult(boolean cont) {
        this.cont = cont;
        this.type = Types.NONE;
    }

    public InputResult(boolean cont, Types type) {
        this.cont = cont;
        this.type = type;
    }
}
