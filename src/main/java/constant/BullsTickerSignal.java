package constant;

public enum BullsTickerSignal {

    SENETTE_KAL("SENETTE KAL"),
    NAKITTE_KAL("NAKİTTE KAL"),
    SAT("SAT"),
    AL("AL");

    private String value;

    BullsTickerSignal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
