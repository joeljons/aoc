package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent18b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent18b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int parsePos;

    private int calc(List<String> strs) {
        int best = 0;
        for (int i = 0; i < strs.size(); i++) {
            for (int j = 0; j < strs.size(); j++) {
                if (i == j) {
                    continue;
                }
                parsePos = 0;
                Num left = new Num(strs.get(i));
                parsePos = 0;
                Num right = new Num(strs.get(j));
                Num sum = left.add(right);
                while (sum.reduce() != Action.NONE) {
                    //   System.out.println("reduced to: " + sum);
                }
                parsePos = 0;
                int magnitude = sum.magnitude();
                best = Math.max(best, magnitude);
            }
        }
        return best;
    }

    private enum Action {
        EXPLODE, SPLIT, NONE
    }

    private class Num {
        Num parent;
        List<Num> sub;
        Integer value;

        public Num(String str) {
            char c = str.charAt(parsePos++);
            if (Character.isDigit(c)) {
                value = c - '0';
            } else if (c == '[') {
                sub = new ArrayList<>();
                while (str.charAt(parsePos) != ']') {
                    Num next = new Num(str);
                    next.parent = this;
                    sub.add(next);
                    if (str.charAt(parsePos) == ',') {
                        parsePos++;
                    }
                }
                parsePos++;
            } else {
                throw new RuntimeException("Unknown part: " + c);
            }
        }

        public Num(Num left, Num right) {
            sub = new ArrayList<>();
            sub.add(left);
            sub.add(right);
            left.parent = this;
            right.parent = this;
        }

        public Num(int value) {
            this.value = value;
        }

        public Num add(Num num) {
            return new Num(this, num);
        }

        public int magnitude() {
            if (value != null) {
                if (sub != null) {
                    throw new RuntimeException();
                }
                return value;
            } else {
                if (sub.size() != 2) {
                    throw new RuntimeException();
                }
                return sub.get(0).magnitude() * 3 + sub.get(1).magnitude() * 2;
            }
        }

        public Action reduce(int level, Action tryNow) {
            if (sub != null) {
                if (level == 5 && tryNow == Action.EXPLODE) {
                    Integer left = sub.get(0).value;
                    Integer right = sub.get(1).value;
                    sub = null;
                    this.value = 0;
                    Num root = this;
                    while (root.parent != null) {
                        root = root.parent;
                    }
                    List<Num> allValueNums = new ArrayList<>();
                    root.fillValueNums(allValueNums);
                    int i = allValueNums.indexOf(this);
                    if (i > 0) {
                        allValueNums.get(i - 1).value += left;
                    }
                    if (i < allValueNums.size() - 1) {
                        allValueNums.get(i + 1).value += right;
                    }
                    return Action.EXPLODE;
                }
                Action result = sub.get(0).reduce(level + 1, tryNow);
                if (result != Action.NONE) {
                    return result;
                }
                result = sub.get(1).reduce(level + 1, tryNow);
                return result;
            } else {
                if (value >= 10 && tryNow == Action.SPLIT) {
                    sub = new ArrayList<>();
                    Num left = new Num(value / 2);
                    left.parent = this;
                    sub.add(left);
                    Num right = new Num((value + 1) / 2);
                    right.parent = this;
                    sub.add(right);
                    value = null;
                    return Action.SPLIT;
                } else {
                    return Action.NONE;
                }
            }
        }

        private void fillValueNums(List<Num> allValueNums) {
            if (sub != null) {
                for (Num subNum : sub) {
                    subNum.fillValueNums(allValueNums);
                }
            } else {
                allValueNums.add(this);
            }
        }

        @Override
        public String toString() {
            if (sub != null) {
                return sub.toString().replace(" ", "");
            } else {
                return value.toString();
            }
        }

        public Action reduce() {
            Action result = reduce(1, Action.EXPLODE);
            if (result != Action.NONE) {
                return result;
            }
            result = reduce(1, Action.SPLIT);
            return result;
        }

    }

}
//4416 too low