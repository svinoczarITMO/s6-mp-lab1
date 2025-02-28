import java.util.Arrays;
import java.util.concurrent.locks.*;

class DiningPhilosophers {

    public DiningPhilosophers() {
        forks = new ReentrantLock[FORKS_COUNT];
        Arrays.fill(forks, new ReentrantLock());
    }

    private ReentrantLock[] forks;
    private int PHILOSOPHERS_COUNT = 5;
    private int FORKS_COUNT = PHILOSOPHERS_COUNT;

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
            Runnable pickLeftFork,
            Runnable pickRightFork,
            Runnable eat,
            Runnable putLeftFork,
            Runnable putRightFork) throws InterruptedException {

        Runnable pickFirstFork, pickSecondFork, putFirstFork, putSecondFork;
        int rightFork = philosopher;
        int leftFork = (philosopher + 1) % PHILOSOPHERS_COUNT;
        int firstFork = Math.min(rightFork, leftFork);
        int secondFork = Math.max(firstFork, leftFork);

        if (firstFork == rightFork) {
            pickFirstFork = pickRightFork;
            pickSecondFork = pickLeftFork;
            putFirstFork = putRightFork;
            putSecondFork = putLeftFork;
        } else {
            pickFirstFork = pickLeftFork;
            pickSecondFork = pickRightFork;
            putFirstFork = putLeftFork;
            putSecondFork = putRightFork;
        }

        forks[firstFork].lock();
        pickFirstFork.run();
        forks[secondFork].lock();
        pickSecondFork.run();
        eat.run();
        putSecondFork.run();
        forks[secondFork].unlock();
        putFirstFork.run();
        forks[firstFork].unlock();
    }
}
