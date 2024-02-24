package ru.modiconme.core.multithreading.java5.synchronizers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangerDemo {
    private static Exchanger<Action> exchanger = new Exchanger<>();
    public static void main(String[] args) {
        List<Action> actions = Arrays.asList(Action.ROCK, Action.SCISSOR, Action.SCISSOR);
        List<Action> friendActions = Arrays.asList(Action.SCISSOR, Action.ROCK, Action.PAPER);
        new Thread(new BestFriend("Jim", actions, exchanger)).start();
        new Thread(new BestFriend("John", friendActions, exchanger)).start();
    }
}

enum Action {
    ROCK, PAPER, SCISSOR
}

class BestFriend implements Runnable {
    private String name;
    private List<Action> actions;
    private Exchanger<Action> exchanger;

    public BestFriend(String name, List<Action> actions, Exchanger<Action> exchanger) {
        this.name = name;
        this.actions = actions;
        this.exchanger = exchanger;
    }

    private void whoWins(Action myAction, Action friendAction) {
        if ((myAction == Action.ROCK && friendAction == Action.SCISSOR) ||
                (myAction == Action.PAPER && friendAction == Action.ROCK) ||
                (myAction == Action.SCISSOR && friendAction == Action.PAPER)) {
            System.out.println(name + " wins");
        }

    }

    @Override
    public void run() {
        Action reply;
        for (Action action : actions) {
            try {
                reply = exchanger.exchange(action);
                whoWins(action, reply);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
