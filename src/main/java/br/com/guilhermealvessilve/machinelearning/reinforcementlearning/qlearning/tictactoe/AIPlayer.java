package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe;

import org.nd4j.common.primitives.Pair;

import java.util.*;

import static java.util.List.copyOf;
import static org.nd4j.common.primitives.Pair.*;

public class AIPlayer extends Player {

    private static final int NO_ACTION = -1;
    private final float alpha;
    private final float gamma;
    private final float epsilon;
    private Ticker ticker;
    private final Random random;
    /**
     * The key is the (board, action) and the value is the reward
     */
    private final Map<Pair<List<String>, Integer>, Float> qtable;
    private int previousMove = -1;
    private List<String> previousBoard;

    /**
     * Used just for Unit-Test
     */
    protected AIPlayer(final float alpha,
                       final float gamma,
                       final float epsilon,
                       final Ticker ticker,
                       final Map<Pair<List<String>, Integer>, Float> qtable) {
        this.alpha = alpha;
        this.gamma = gamma;
        this.epsilon = epsilon;
        this.ticker = ticker;
        this.random = new Random();
        this.qtable = qtable;
    }

    /**
     * Q(s, a) = Q(s, a) + alpha[R(s, a) + gamma maxQ(s', a') - Q(s, a)]
     * @param alpha
     * @param gamma
     * @param epsilon
     * @param ticker
     */
    public AIPlayer(final float alpha,
                    final float gamma,
                    final float epsilon,
                    final Ticker ticker) {
        this(alpha, gamma, epsilon, ticker, new HashMap<>());
    }

    public AIPlayer setTicker(Ticker ticker) {
        this.ticker = ticker;
        return this;
    }

    @Override
    public int makeMove(List<String> board) {
        this.previousBoard = copyOf(board);
        var availableMoves = availableMoves(board);
        if (random.nextFloat() < epsilon) {
            previousMove = availableMoves.get(random.nextInt(availableMoves.size()));
            return previousMove;
        }

        var bestMovesAndRewards = bestMoves(board, availableMoves);
        int selectedMove = bestMovesAndRewards.size() > 1? random.nextInt(bestMovesAndRewards.size()) : 0;
        previousMove = bestMovesAndRewards.get(selectedMove).getFirst();
        return previousMove;
    }

    protected final float getQ(List<String> state, int action) {
        return qtable.getOrDefault(pairOf(state, action), 0f);
    }

    protected final float getMaxQ(List<String> state) {
        var bestMovesAndRewards = bestMoves(state, availableMoves(state));
        int selectedMove = bestMovesAndRewards.size() > 1? random.nextInt(bestMovesAndRewards.size()) : 0;
        return bestMovesAndRewards.get(selectedMove).getSecond();
    }

    protected final List<Pair<Integer, Float>> bestMoves(List<String> board, List<Integer> availableMoves) {
        var moveAndRewardList = new ArrayList<Pair<Integer, Float>>();
        for (var move : availableMoves) {
            float reward = getQ(board, move);
            moveAndRewardList.add(pairOf(move, reward));
        }

        var bestMoveAndReward = pairOf(0, 0f);
        var it = moveAndRewardList.iterator();
        while (it.hasNext()) {
            var moveAndReward = it.next();
            if (moveAndReward.getSecond() >= bestMoveAndReward.getSecond()) {
                bestMoveAndReward = moveAndReward;
            } else if (moveAndRewardList.size() > 1) { // Ensure that the list has at least one
                it.remove();
            }
        }

        return moveAndRewardList;
    }

    protected final List<Integer> availableMoves(List<String> board) {
        var moves = new ArrayList<Integer>();
        for (int i = 0; i < board.size(); ++i) {
            if (board.get(i).isBlank()) {
                moves.add(i);
            }
        }

        return moves;
    }

    /**
     * This method update the QTable, using the rewards, based on the formula Q-Learning.
     * Below, you can see the formula that is TD (Time-Difference):
     *  Q(s, a) = Q(s, a) + alpha[R(s, a) + gamma maxQ(s', a') - Q(s, a)]
     * @param reward The reward of the action taken by the player on the state board.
     * @param board The state (environment) of the tic-tac-toe game.
     */
    @Override
    public void reward(int reward, List<String> board) {
        if (NO_ACTION == previousMove) return;

        float oldQ = getQ(previousBoard, previousMove);
        float newMaxQ = getMaxQ(board);
        var updateQ = oldQ + alpha*(reward + gamma * newMaxQ - oldQ);
        qtable.put(pairOf(copyOf(previousBoard), previousMove), updateQ);

        this.previousBoard = null;
        this.previousMove = NO_ACTION;
    }

    @Override
    public Ticker ticker() {
        return ticker;
    }
}
