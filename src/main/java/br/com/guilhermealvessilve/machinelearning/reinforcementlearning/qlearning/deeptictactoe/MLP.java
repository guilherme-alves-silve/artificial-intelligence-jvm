package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.deeptictactoe;

import org.deeplearning4j.datasets.iterator.utilty.SingletonDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.learning.config.Adam;

import static org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

public class MLP {

    private final MultiLayerNetwork model;

    public MLP() {
        this.model = createDeepNeuralNetwork();
    }

    public void fit(final INDArray features, final INDArray labels, int numEpochs) {
        var dataSet = new DataSet(features, labels);
        var dataSetIterator = new SingletonDataSetIterator(dataSet);
        model.fit(dataSetIterator, numEpochs);
    }

    public float predict(final INDArray input) {
        return model.output(input).getFloat(0);
    }

    private MultiLayerNetwork createDeepNeuralNetwork() {
        var conf = new NeuralNetConfiguration.Builder()
                .seed(12345)
                .updater(new Adam(0.01))
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .weightInit(WeightInit.XAVIER)
                .list()
                .layer(0, denseLayer(36, 32))
                .layer(1, denseLayer(32, 3))
                .layer(2, outLayer(3, 1))
                .backpropType(BackpropType.Standard)
                .build();

        // Create the MLP model
        var model = new MultiLayerNetwork(conf);
        model.init();
        return model;
    }

    private DenseLayer denseLayer(int in, int out) {
        return new DenseLayer.Builder()
                .nIn(in)
                .nOut(out)
                .activation(Activation.RELU)
                .build();
    }

    private OutputLayer outLayer(int in, int out) {
        return new OutputLayer.Builder(LossFunction.MSE)
                .nIn(in)
                .nOut(out)
                .activation(Activation.RELU)
                .build();
    }
}
