# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A research pipeline measuring the trade-off between **performance overhead** and **decompilation resilience** introduced by Allatori obfuscation, comparing x86_64 and ARM (aarch64). The subject under test is a simulated financial transaction engine (`com.finance.engine.TransactionEngine`) — deliberately written with heavy branching (control-flow obfuscation target) and a "secret" regulatory-limits table (string-encryption target). Everything else (the JMH harness, order generator) is scaffolding that must stay untouched by obfuscation so timing comparisons are fair.

The whole project is a sequence of pipeline stages (T0, T2–T8); scripts are numbered to match. There is no application to "run" in the normal sense — the deliverable is `analysis/summary.csv` and the plots derived from JMH output across obfuscation profiles.

## Commands

Build the uber-jar (target/app-original.jar), obfuscate, and validate correctness:
```bash
export ALLATORI_JAR=/path/to/allatori.jar
bash run_pipeline.sh              # T2 build -> T3 obfuscate -> T4 correctness
bash run_pipeline.sh --bench      # also runs T6 benchmark on current host
```

Individual stages (always invoke with `bash <script>`, they don't rely on the exec bit):
```bash
bash scripts/00_check_env.sh              # T0 — check toolchain/versions
mvn -q clean package                      # T2 — build target/app-original.jar
bash scripts/02_obfuscate.sh              # T3 — needs ALLATORI_JAR; produces app-baseline/flow/string/flowstring.jar
bash scripts/03_verify_correctness.sh     # T4 — golden-file diff, original vs each obfuscated profile
sudo cpupower frequency-set -g performance  # required before T6 on both x86 and ARM
bash scripts/04_bench.sh                  # T6 — JMH run; detects arch via uname -m
bash scripts/05_decompile.sh              # T7 — needs CFR_JAR; decompiles com.finance.engine.* per profile
python3 scripts/07_analyze.py             # T8 — stats + plots from results/*.json -> analysis/
```

Run a single benchmark manually / list benchmarks:
```bash
java -jar target/app-original.jar -l                                   # list benchmarks
java -jar target/app-original.jar -f 5 -wi 10 -i 20 -foe true -rf json -rff out.json
java -cp target/app-original.jar com.finance.engine.CorrectnessRunner  # golden summary to stdout
```

There is no test suite in the JUnit sense — "correctness" means `CorrectnessRunner`'s deterministic `Summary.toString()` output is byte-identical between the original and each obfuscated jar (see T4). Any change to `TransactionEngine` that alters this output for a fixed input is treated as a regression.

## Architecture

- `src/main/java/com/finance/engine/` — the **only** code that gets obfuscated (`apply2class="class com.finance.engine.*"` in the Allatori configs). `TransactionEngine.pipeline()` runs orders through validate → assessRisk → computeFee → checkBalanceAndReserve → route, each stage a real target for control-flow obfuscation. `RegulatoryLimits` holds the notional caps that are the target for string encryption. `CorrectnessRunner.main()` is explicitly kept (name + entry point) via `<keep-names>` in every Allatori config so it stays invokable as the correctness oracle after obfuscation.
- `src/main/java/com/finance/perf/` — JMH harness (`TransactionProcessingBenchmark`) and deterministic order generator (`OrderFactory`, fixed xorshift seed). This package is excluded from obfuscation (`ignore-classes` covers `org.openjdk.jmh.*`, and `keep-names` covers "not class com.finance.engine.*") so JMH's reflection keeps working and timing differences are attributable only to the engine.
- `pom.xml` builds a single shaded uber-jar, `target/app-original.jar`, with `Main-Class: org.openjdk.jmh.Main`. All four obfuscated variants (`app-baseline.jar`, `app-flow.jar`, `app-string.jar`, `app-flowstring.jar`) are produced by feeding this same jar through Allatori with a different `allatori/config-*.xml` (in/out declared inside each config, not on the CLI).
- `allatori/config-*.xml` — one profile each: `baseline` (renaming only), `flow` (+ control-flow obfuscation, `extensive=maximum`), `string` (+ string encryption, `maximum`), `flowstring` (both). Renaming applies project-wide as a background factor in all profiles; control-flow and string-encryption are scoped to `com.finance.engine.*` only, via `apply2class`, so the measured overhead isolates those two techniques.
- Data flow across stages: `target/app-*.jar` (T2/T3) → `correctness/golden-*.txt` (T4) → `results/jmh-<arch>-<profile>.json` (+ `time-*.txt`, `thermal-*.log` on ARM) (T6) → `resilience/<profile>/*.java` decompiled sources (T7, manual LLM scoring against a rubric using `scripts/prompt-template.txt`) → `analysis/summary.csv` + `analysis/overhead-<arch>-size<N>.png` (T8).
- `scripts/07_analyze.py` treats **fork** (not iteration) as the unit of statistical independence when aggregating JMH `rawData`, since iterations within a fork are autocorrelated (same JVM/JIT warmup). It picks Welch's t-test or Mann-Whitney U per Shapiro-Wilk normality, applies Holm-Bonferroni correction across the (arch, size) family, and reports Cliff's delta as effect size.
- Cross-arch workflow: obfuscated jars are portable bytecode — do **not** re-obfuscate on ARM. Copy the same `target/app-*.jar` files to the Raspberry Pi, run `scripts/04_bench.sh` there, then copy `results/jmh-aarch64-*.json` back into the same `results/` directory before re-running T8. Compare relative overhead (%) across architectures, never absolute times (different CPUs).
- Any change to `TransactionEngine`'s business logic must keep `CorrectnessRunner` output stable for the fixed seed in `OrderFactory`, or update all `correctness/golden-*.txt` and re-validate — the whole resilience/performance comparison depends on original and obfuscated jars being behaviorally identical.
