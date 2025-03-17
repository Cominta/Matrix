package console.inputResult;

public class InputResult {
    public enum Types {
        NONE,
        NEW_MATRIX,
        INPUT_OP,
        MATRIX_OP,
        MWM_OP,
        SET_OP,
        PRINT_OP,
        // new
        READ_FROM_FILE,
        NEW_FROM_FILE,
    }

    protected boolean cont;
    protected Types type;

    public InputResult(boolean cont) {
        this.cont = cont;
        this.type = Types.NONE;
    }

    public InputResult(boolean cont, Types type) {
        this.cont = cont;
        this.type = type;
    }

    public boolean isCont() { return cont; }

    public Types getType() { return type; }
}
