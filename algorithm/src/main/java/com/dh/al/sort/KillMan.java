package com.dh.al.sort;

import lombok.Data;
import org.junit.Test;

@Data
public class KillMan {

    @Data
    static class PP {

        PP previous;
        PP next;
        int n;

        public PP() {
        }

        public PP(int n) {
            this.n = n;
        }

        @Override
        public String toString() {
            return "PP{" +
                    "n=" + n +
                    '}';
        }
    }


    public static PP init(int n) {
        PP head = new PP(0);
        head.setN(0);

        PP pre = null;
        for (int i = 1; i < n; i++) {
            if (pre == null) {
                pre = head;
            }
            PP current = new PP(i);
            pre.setNext(current);
            current.setPrevious(pre);

            if (i == n - 1) {
                head.setPrevious(current);
                current.setNext(head);
            }
            pre = current;
        }
        return head;
    }

    public void kill(PP p, int m) {
        int number = 0;
        int num = 0;
        while (true) {
            num++;
            if (number == m) {
                p.getPrevious().setNext(p.getNext());
                p.getNext().setPrevious(p.getPrevious());
                number = 0;
            }
            number++;
            if (p.next == p || p.previous == p) {
                break;
            }
            p = p.next;
        }
        System.out.println("last kill " + p);
        System.out.println(num);
    }

    @Test
    public void testKill() {
        int n = 10;
        int m = 1;
        PP head = init(n);
        kill(head, m);

    }
}
