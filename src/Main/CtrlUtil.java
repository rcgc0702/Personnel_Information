package Main;

public class CtrlUtil {

    private static Controller theController = null;

    private CtrlUtil() {
    }

    public static void myController (Controller b_control) {
        theController = b_control;
    }

    public static Controller getController() {
        return theController;
    }
}
