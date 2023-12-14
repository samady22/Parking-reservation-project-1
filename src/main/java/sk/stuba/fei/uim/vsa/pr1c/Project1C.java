package sk.stuba.fei.uim.vsa.pr1c;

import sk.stuba.fei.uim.vsa.pr1c.ui.KeyboardInput;
import sk.stuba.fei.uim.vsa.pr1c.ui.Repl;

public class Project1C {

    public static void main(String[] args) {

        KeyboardInput.PROMPT_DELIMETER = "=> ";
        Repl repl = new Repl();
        repl.start();

    }

}
