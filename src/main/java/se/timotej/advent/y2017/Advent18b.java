package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Advent18b {
    private final int p;

    public Advent18b(int p) {
        this.p = p;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Advent18b prog0 = new Advent18b(0);
        Advent18b prog1 = new Advent18b(1);
        prog1.sendQ = prog1.recQ;
        //prog1.sendQ = prog0.recQ;
        /*Thread thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    prog0.calc(Online.get(18));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    prog1.calc(Online.get(18));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //thread0.start();
        thread1.start();
        while(true){
            Thread.sleep(10000);
            System.out.println("prog1.sends = " + prog1.sends);
        }
        /*thread0.join();
        thread1.join();

        Online.submit(18, 2, prog1.sends);*/
    }

    int sends = 0;
    BlockingQueue<Long> recQ = new LinkedBlockingQueue<>();
    BlockingQueue<Long> sendQ;

    private void calc(List<String> strs) throws InterruptedException {
        int i = 0;
        Map<String, Long> g = new HashMap<>();
        while (i < strs.size()) {
            Instr instr = getInstr(strs, i);
            if (instr.wat.equals("set")) {
                long setval;
                if (instr.ystr != null) {
                    setval = g.getOrDefault(instr.ystr, 0L);
                } else {
                    setval = instr.y;
                }
                g.put(instr.x, setval);
            } else if (instr.wat.equals("add")) {
                long addval;
                if (instr.ystr != null) {
                    addval = g.getOrDefault(instr.ystr, 0L);
                } else {
                    addval = instr.y;
                }
                g.put(instr.x, g.getOrDefault(instr.x, 0L) + addval);
            } else if (instr.wat.equals("mul")) {
                long mulval;
                if (instr.ystr != null) {
                    mulval = g.getOrDefault(instr.ystr, 0L);
                } else {
                    mulval = instr.y;
                }
                g.put(instr.x, g.getOrDefault(instr.x, 0L) * mulval);
            } else if (instr.wat.equals("mod")) {
                long modval;
                if (instr.ystr != null) {
                    modval = g.getOrDefault(instr.ystr, 0L);
                } else {
                    modval = instr.y;
                }
                g.put(instr.x, g.getOrDefault(instr.x, 0L) % modval);
            } else if (instr.wat.equals("jgz")) {
                if (instr.x.equals("1") || g.getOrDefault(instr.x, 0L) > 0L) {
                    i += instr.y - 1;
                }
            } else if (instr.wat.equals("rcv")) {
                Long rcvVal = null;
                try {
                    rcvVal = recQ.poll(10, TimeUnit.SECONDS);
                    System.out.println(p + " receives " + rcvVal);

                } catch (InterruptedException e) {
                    System.out.println("DeadLock");
                    System.exit(0);
                    if(p==1){
                        //Online.submit(18, 2, sends);
                    }
                }
                g.put(instr.x, rcvVal);
            } else if (instr.wat.equals("snd")) {
                sends++;
                System.out.println(p+" sends = " + sends);
                System.out.println(p + " sends " + g.getOrDefault(instr.x, 0L));
                sendQ.put(g.getOrDefault(instr.x, 0L));
            } else {
                throw new RuntimeException();
            }
            i++;
        }
    }
// 11938 too high
    private Map<Integer, Instr> instrMap = new HashMap<>();

    private Instr getInstr(List<String> strs, int i) {
        Instr instr = instrMap.get(i);
        if (instr == null) {
            String str = strs.get(i);
            String[] split = str.split(" ");
            instr = new Instr(split[0], split[1], split.length == 3 ? split[2] : null);
            instrMap.put(i, instr);
        }
        return instr;
    }

    private class Instr {
        String wat;
        String x;
        long y;
        String ystr;

        public Instr(String wat, String x, String yy) {
            this.wat = wat;
            this.x = x;
            if (yy != null) {
                if (Character.isAlphabetic(yy.charAt(0))) {
                    ystr = yy;
                } else {
                    this.y = Long.parseLong(yy);
                }
            }
        }
    }
}