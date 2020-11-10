package com.github.games647.minefana.common.collectors;

import com.github.games647.minefana.common.AnalyticsType;
import com.github.games647.minefana.common.InfluxConnector;

public class JVMCollector extends AbstractCollector {
    Runtime runtime;
    public JVMCollector(InfluxConnector connector) {
        super(connector);
        this.runtime = Runtime.getRuntime();
    }


    @Override
    public void run() {
        System.out.println("Sending Runtime Items");
        send(AnalyticsType.JVM.newPoint().addField("usedMemory", runtime.totalMemory() - runtime.freeMemory()));
        send(AnalyticsType.JVM.newPoint().addField("totalMemory", runtime.totalMemory()));
        send(AnalyticsType.JVM.newPoint().addField("freeMemory", runtime.freeMemory()));
    }
}
