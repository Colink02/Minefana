package com.github.games647.minefana.common;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.io.Closeable;
import java.util.Collections;

public class InfluxConnector implements Closeable {

    private final String url;
    private final String username;
    private final String password;
    private final String database;

    private InfluxDB connection;

    public InfluxConnector(String url, String username, String password, String database) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    protected void init() {
        InfluxDB influxDB = InfluxDBFactory.connect(url, username, password);

        QueryResult checkDatabase = influxDB.query(new Query("SHOW DATABASES", ""));

        if (!checkDatabase.getResults().get(0).getSeries().get(0).getValues().get(0).contains(database)) {
            influxDB.query(new Query("CREATE DATABASE " + database, "", true));
        }

        // Flush every 2000 Points, at least every 1s
        // Only one of these 2 calls should be enabled
        //influxDB.enableBatch(2_000, 2, TimeUnit.MINUTES);

        influxDB.enableBatch(BatchOptions.DEFAULTS.jitterDuration(500));


        influxDB.enableGzip();

        connection = influxDB;
    }

    public void send(Point measurement) {
        send(Collections.singletonList(measurement));
    }

    public void send(Iterable<Point> measurements) {
        BatchPoints batchPoints = BatchPoints.database(database).retentionPolicy("autogen").build();
        measurements.forEach(batchPoints::point);
        connection.write(batchPoints);
    }

    @Override
    public void close() {
        if (connection != null) {
            connection.close();
        }
    }
}
