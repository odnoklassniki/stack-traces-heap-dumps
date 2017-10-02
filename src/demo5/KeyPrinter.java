package demo5;

import sun.jvm.hotspot.oops.DefaultOopVisitor;
import sun.jvm.hotspot.oops.OopField;
import sun.jvm.hotspot.oops.TypeArray;

public class KeyPrinter extends DefaultOopVisitor {

    @Override
    public void doOop(OopField field, boolean isVMField) {
        if (field.getID().getName().equals("key")) {
            TypeArray array = (TypeArray) field.getValue(getObj());
            long length = array.getLength();

            for (long i = 0; i < length; i++) {
                System.out.printf("%02x", array.getByteAt(i));
            }
        }
    }
}
