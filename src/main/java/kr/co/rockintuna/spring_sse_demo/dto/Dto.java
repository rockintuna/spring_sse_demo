package kr.co.rockintuna.spring_sse_demo.dto;

public class Dto {
    private String key;
    private Object value;

    public Dto() {
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Dto{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
