package com.parasan;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Millisecond;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: sanych
 * Date: 13.09.14
 * Time: 15:09
 */
public class JChartFrame extends JFrame {

    public static final String TITLE_CHART = "Проверка";
    public static final int HIGH_LIMIT = 100;
    public static final int LOW_LIMIT = -100;
    public static final int DATA_PERIOD = 500;

    private final DynamicTimeSeriesCollection dataset;
    private ChartPanel chartPanel;

    public JChartFrame() {

        dataset = createDataSet();
        JFreeChart chart = build();

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));

        setContentPane(chartPanel);
    }

    private DynamicTimeSeriesCollection createDataSet() {
        DynamicTimeSeriesCollection result = new DynamicTimeSeriesCollection(1, 2 * 60, new Millisecond());
        result.setTimeBase(new Millisecond());
        result.advanceTime();
        return result;
    }

    private JFreeChart build() {
        JFreeChart result = ChartFactory.createTimeSeriesChart(TITLE_CHART, "", "", dataset, false, false, false);

        XYPlot plot = result.getXYPlot();

        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);

        ValueAxis range = plot.getRangeAxis();
        range.setRange(LOW_LIMIT, HIGH_LIMIT);

        return result;
    }

    public void updateChartPanel() {
        chartPanel.restoreAutoBounds();
    }

    private void updateData(double value) {

        float[] data = {(float) value};

        // Если серий ещё нет - создаём
        if (dataset.getSeriesCount() == 0) {
            dataset.addSeries(data, 0, "");
        }
        dataset.advanceTime();
        dataset.appendData(data);
        updateChartPanel();
    }

    public static void main(String[] args) {

        //  Создаём и показывает начальный график
        final JChartFrame chart = new JChartFrame();
        chart.pack();
        chart.setVisible(true);


        // Запускаем задачу, которая будет генерировать новые данные
        // эти данные надо передавать графику, чтобы он обновлялся
        Timer timer = new Timer(DATA_PERIOD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double value = Math.random() * (HIGH_LIMIT - LOW_LIMIT) + LOW_LIMIT;
                chart.updateData(value);
            }
        });
        timer.start();
    }
}
