import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DiningPhilosophers {
    private final Lock[] forks;
    private static final Lock globalLock = new ReentrantLock(); 
    
    public DiningPhilosophers() {
        forks = new ReentrantLock[5];
        for (int i = 0; i < 5; i++) {
            forks[i] = new ReentrantLock();
        }
    }

    public void wantsToEat(int philosopher,
                         Runnable pickLeftFork,
                         Runnable pickRightFork,
                         Runnable eat,
                         Runnable putLeftFork,
                         Runnable putRightFork) throws InterruptedException {
        
        globalLock.lock();
        try {
            int leftFork = philosopher;
            int rightFork = (philosopher + 1) % 5;

            forks[leftFork].lock();
            pickLeftFork.run();
            
            forks[rightFork].lock();
            pickRightFork.run();

            eat.run();

            putRightFork.run();
            forks[rightFork].unlock();
            
            putLeftFork.run();
            forks[leftFork].unlock();
        } finally {
            globalLock.unlock();
        }
    }
}