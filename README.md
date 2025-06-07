# pekko-bench

```
 sbt "Jmh/run -i 3 -wi 2 -f1 -t5 .*"
```

Results - Java 11.0.27 (Temurin)
```
[info] Benchmark               Mode  Cnt          Score           Error  Units
[info] GetAndAddBench.atomic  thrpt    3  387644165.536 ± 134210873.040  ops/s
[info] GetAndAddBench.handle  thrpt    3   50344569.970 ± 179630854.398  ops/s
[info] GetAndAddBench.unsafe  thrpt    3  409783873.401 ± 164705716.374  ops/s
```

Results - Java 17.0.15 (Temurin)
```
[info] Benchmark               Mode  Cnt          Score           Error  Units
[info] GetAndAddBench.atomic  thrpt    3  595250730.703 ± 451357085.999  ops/s
[info] GetAndAddBench.handle  thrpt    3   89078570.805 ± 347586371.004  ops/s
[info] GetAndAddBench.unsafe  thrpt    3  612197987.515 ± 339996223.052  ops/s
```

