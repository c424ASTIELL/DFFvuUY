// 代码生成时间: 2025-10-03 03:45:24
package com.example.featureengineering;

import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * FeatureEngineeringTool is a class designed to perform feature engineering operations.
 * It provides a flexible way to add custom feature transformation functions and apply them to datasets.
 */
@Introspected
public class FeatureEngineeringTool {

    private List<Consumer<DataPoint>> transformationFunctions;

    /**
     * Constructor for FeatureEngineeringTool.
     */
    public FeatureEngineeringTool() {
        this.transformationFunctions = new ArrayList<>();
    }

    /**
     * Adds a transformation function to the tool.
     * @param transformationFunction The function to add.
     */
    public void addTransformationFunction(Consumer<DataPoint> transformationFunction) {
        this.transformationFunctions.add(transformationFunction);
    }

    /**
     * Applies all transformation functions to a given dataset.
     * @param dataset The dataset to transform.
     * @return A new dataset with all transformations applied.
     */
    public List<DataPoint> applyTransformations(@NotNull List<DataPoint> dataset) {
        List<DataPoint> transformedDataset = new ArrayList<>(dataset.size());
        for (DataPoint dataPoint : dataset) {
            DataPoint transformedDataPoint = new DataPoint(dataPoint);
            for (Consumer<DataPoint> function : transformationFunctions) {
                function.accept(transformedDataPoint);
            }
            transformedDataset.add(transformedDataPoint);
        }
        return transformedDataset;
    }
}

/**
 * DataPoint class represents a single data point in the dataset.
 * It is designed to be extendable for adding more features if necessary.
 */
@Introspected
class DataPoint {
    private double feature1;
    private double feature2;

    /**
     * Constructor for DataPoint.
     * @param feature1 The first feature of the data point.
     * @param feature2 The second feature of the data point.
     */
    public DataPoint(double feature1, double feature2) {
        this.feature1 = feature1;
        this.feature2 = feature2;
    }

    /**
     * Copy constructor for DataPoint.
     * @param other The data point to copy.
     */
    public DataPoint(DataPoint other) {
        this.feature1 = other.feature1;
        this.feature2 = other.feature2;
    }

    // Getters and setters for features
    public double getFeature1() {
        return feature1;
    }

    public void setFeature1(double feature1) {
        this.feature1 = feature1;
    }

    public double getFeature2() {
        return feature2;
    }

    public void setFeature2(double feature2) {
        this.feature2 = feature2;
    }
}
