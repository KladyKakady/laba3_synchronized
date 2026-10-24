class ChickenOrEgg {

    private static final Object lock = new Object();
    private static String lastWord = "";

    private static synchronized void say(String word) {
        System.out.println(word);
        lastWord = word;
    }

    static class Speaker extends Thread {
        private final String word;
        private final int times;

        Speaker(String word, int times) {
            this.word = word;
            this.times = times;
        }

        @Override
        public void run() {
            for (int i = 0; i < times; i++) {
                say(word);
                try {
                    Thread.sleep((long) (Math.random() * 100));
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    static void main(String[] args) throws InterruptedException {
        Speaker chicken = new Speaker("Курица", 5);
        Speaker egg = new Speaker("Яйцо", 5);

        chicken.start();
        egg.start();

        while (chicken.isAlive() && egg.isAlive()) {
            Thread.sleep(10);
        }

        chicken.join();
        egg.join();

        synchronized (lock) {
            System.out.println("Спор окончен. Победила " + lastWord);
        }
    }
}