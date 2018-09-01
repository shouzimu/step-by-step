import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class TestByte {

    private int m;

    public int incr() {
        return m + 1;
    }

    @Test
    public void testCC() {
        List<cc> l1 = Arrays
                .asList(new cc(1), new cc(2), new cc(3), new cc(4), new cc(5), new cc(6), new cc(7), new cc(8),
                        new cc(9), new cc(10), new cc(11));
        List<cc> l2 = l1.stream().filter(c -> c.getIndex() > 5).collect(Collectors.toList());
        for (int i = 0; i < l1.size(); i++) {
            cc l = l1.get(i);
            if (l.getIndex() > 5) {
                cc c = new cc(l.getIndex() + 5);
                l1.set(i, c);
            }
        }

        System.out.println(l1);
        System.out.println(l2);

    }


    static class cc {

        private int index;
        private int value;

        public cc(int index) {
            this.index = index;
            this.value = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "cc{" +
                    "index=" + index +
                    ", value=" + value +
                    '}';
        }
    }
}
