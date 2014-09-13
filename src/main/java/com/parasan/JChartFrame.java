package com.parasan;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;

import javax.swing.*;
import java.awt.*;

/**
 * User: sanych
 * Date: 13.09.14
 * Time: 15:09
 */
public class JChartFrame extends JFrame {

    public static final String TITLE_CHART = "Проверка";
    private final DynamicTimeSeriesCollection dataset;

    public JChartFrame() {

        dataset = createDataSet();
        JFreeChart chart = build();

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));

        setContentPane(chartPanel);
    }

    private DynamicTimeSeriesCollection createDataSet() {
        DynamicTimeSeriesCollection result = new DynamicTimeSeriesCollection(1, 2 * 60, new Second());
        result.setTimeBase(new Second());
//        result.addSeries(data, 0, "Data");
        return result;
    }

    private JFreeChart build() {
        JFreeChart result = ChartFactory.createTimeSeriesChart(TITLE_CHART, "", "", dataset, false, false, false);

        XYPlot plot = result.getXYPlot();

        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);

        ValueAxis range = plot.getRangeAxis();
        range.setRange(-100, 100);

        return result;
    }

    public static void main(String[] args) {
        JChartFrame chart = new JChartFrame();
        chart.pack();
        chart.setVisible(true);
    }
}
