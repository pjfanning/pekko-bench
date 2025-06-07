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

Results - Java 21.0.7 (Temurin)
```
[info] Benchmark                      Mode  Cnt           Score            Error  Units
[info] GetAndAddBench.atomic         thrpt    3   476927249.897 ± 1369494107.816  ops/s
[info] GetAndAddBench.atomicUpdater  thrpt    3   431651571.104 ±  166649743.216  ops/s
[info] GetAndAddBench.handle         thrpt    3    90016208.221 ±   46351709.190  ops/s
[info] GetAndAddBench.unsafe         thrpt    3   511140871.767 ±  468063450.750  ops/s
```

Results - Java 24.0.1 (Temurin)
```
[info] Benchmark                      Mode  Cnt           Score            Error  Units
[info] GetAndAddBench.atomic         thrpt    3   536265506.435 ±  276536732.635  ops/s
[info] GetAndAddBench.atomicUpdater  thrpt    3   421694955.573 ±  104778813.847  ops/s
[info] GetAndAddBench.handle         thrpt    3    60555471.877 ±  271513049.979  ops/s
[info] GetAndAddBench.unsafe         thrpt    3   535989370.115 ±  575970796.394  ops/s
```

I also tested AtomicLongFieldUpdater too and it has similar performance to AtomicLong and Unsafe.
