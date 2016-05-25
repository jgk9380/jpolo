package testjava8;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScriptTest {
    public JavaScriptTest() {
        super();
    }
    public static void main(String[] args) throws ScriptException {
       ScriptEngineManager manager = new ScriptEngineManager();
       ScriptEngine engine = manager.getEngineByName( "JavaScript" );
                
       System.out.println( engine.getClass().getName() );
       System.out.println( "Result:" + engine.eval("function f() { return 1; }; f() + 1;"));
   }
}
