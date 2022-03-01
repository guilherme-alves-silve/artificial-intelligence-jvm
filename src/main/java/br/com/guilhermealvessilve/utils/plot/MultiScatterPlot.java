package br.com.guilhermealvessilve.utils.plot;

import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.components.*;
import tech.tablesaw.plotly.traces.ScatterTrace;
import tech.tablesaw.plotly.traces.Trace;

import java.util.Arrays;
import java.util.Map;

public class MultiScatterPlot {

    private MultiScatterPlot() {
        throw new IllegalArgumentException("No MultiScatterPlot!");
    }

    @SafeVarargs
    public static void plot(final String title,
                            final Table table,
                            final String xTitle,
                            final String yTitle,
                            Map.Entry<String, String>... xy) {

        var traces = Arrays.stream(xy)
            .map(entry -> ScatterTrace.builder(
                        table.numberColumn(entry.getKey()),
                        table.numberColumn(entry.getValue())
                    )
                    .build())
            .toArray(Trace[]::new);

        var layout =
                Layout.builder(title, xTitle)
                        .yAxis(Axis.builder().title(yTitle).build())
                        .build();

        Plot.show(new Figure(layout, traces));
    }
}
