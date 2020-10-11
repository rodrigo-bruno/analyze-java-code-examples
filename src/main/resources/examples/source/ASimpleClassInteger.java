package examples.source;

public class ASimpleClassInteger extends ASuperSimpleClass<Integer> {

    Integer aField;

    public ASimpleClassInteger(Integer aSuperField, Integer aField) {
        this.aSuperField = aSuperField;
        this.aField = aField;
    }

    public Integer getAField() {
        return aField;
    }
}
