package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.deeptictactoe;

import br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe.Player;
import br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe.Ticker;
import org.nd4j.common.primitives.Pair;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.*;

import static java.util.List.copyOf;
import static org.nd4j.common.primitives.Pair.pairOf;

public class DeepAIPlayer extends Player {

    private static final int NO_ACTION = -1;
    private static final float DEFAULT_Q = 0f;
    private final float alpha;
    private final float gamma;
    private final float epsilon;
    private final Ticker ticker;
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
    protected DeepAIPlayer(final float alpha,
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

    protected final INDArray oneHotEncodeInput(List<String> states, int action) {

        // X -> 1, 0, 0
        // O -> 0, 1, 0
        // BLANK -> 0, 0, 1
        var oneHotArray = new float[36];
        for (int i = 0, j = 0; i < states.size(); ++i, j += 3) {
            oneHotArray[j] = states.get(i).equals(Ticker.X.toString()) ? 1 : 0;
            oneHotArray[j+1] = states.get(i).equals(Ticker.O.toString()) ? 1 : 0;
            oneHotArray[j+2] = states.get(i).isBlank() ? 1 : 0;
        }

        // one of 9 actions -> 0, 0, 0, 0, 0, 0, 0, 0, 0
        oneHotArray[27+action] = 1;

        return Nd4j.create(oneHotArray, new int[]{1, 36});
    }

    /**
     * Below the formula that is used in Q-Learning that is TD (Time-Difference):
     * Q(s, a) = Q(s, a) + alpha[R(s, a) + gamma maxQ(s', a') - Q(s, a)]
     * @param alpha the learning rate
     * @param gamma the discount factor
     * @param epsilon the exploration factor
     * @param ticker the player ticker (X or O)
     */
    public DeepAIPlayer(final float alpha,
                        final float gamma,
                        final float epsilon,
                        final Ticker ticker) {
        this(alpha, gamma, epsilon, ticker, new HashMap<>());
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
        return qtable.getOrDefault(pairOf(state, action), DEFAULT_Q);
    }

    protected final float getMaxQ(List<String> state) {
        var availableMoves = availableMoves(state);
        if (availableMoves.isEmpty()) {
            return 0f;
        }

        var bestMovesAndRewards = bestMoves(state, availableMoves);
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
