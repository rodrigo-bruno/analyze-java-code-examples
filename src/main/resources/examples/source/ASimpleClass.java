package examples.source;

public class ASimpleClass<T> extends ASuperSimpleClass<T> {

    T aField;

    public ASimpleClass(T aSuperField, T aField) {
    	this.aSuperField = aSuperField;
        this.aField = aField;
    }

    public T getAField() {
        return aField;
    }
}
