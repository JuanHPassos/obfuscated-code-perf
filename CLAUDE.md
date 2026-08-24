# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A research pipeline measuring the trade-off between **performance overhead** and **decompilation resilience** introduced by ZKM (Zelix KlassMaster) obfuscation, comparing x86_64 and ARM (aarch64). The subject under test is a simulated financial transaction engine (`com.finance.engine.TransactionEngine`) — deliberately written with heavy branching (control-flow obfuscation target) and a "secret" regulatory-limits table (string-encryption target). Everything else (the JMH harness, order generator) keeps its own identity (class/field/method names, JMH annotations) untouched by obfuscation so timing comparisons are fair, even though it's reprocessed by ZKM alongside the engine (see Architecture below for why).

This project originally targeted the Allatori obfuscator, but its Educational edition silently no-ops both paid features the study needs (control-flow obfuscation and string encryption) — confirmed empirically via identical MD5 hashes, per engine class, regardless of whether those features were "enabled" in Allatori's config. Allatori's only remaining real contribution was renaming, which ZKM already does natively — so Allatori was dropped entirely and ZKM became the sole obfuscator under study.

The whole project is a sequence of pipeline stages (T0, T2–T8); scripts are numbered to match. There is no application to "run" in the normal sense — the deliverable is `analysis/summary.csv` and the plots derived from JMH output across obfuscation profiles.

## Commands

Build the uber-jar (target/app-original.jar), obfuscate, and validate correctness:
```bash
export ZKM_JAR=/path/to/ZKM.jar
bash run_pipeline.sh              # T2 build -> T3 obfuscate -> T4 correctness
bash run_pipeline.sh --bench      # also runs T6 benchmark on current host
```

Individual stages (always invoke with `bash <script>`, they don't rely on the exec bit):
```bash
bash scripts/00_check_env.sh              # T0 — check toolchain/versions
mvn -q clean package                      # T2 — build target/app-original.jar
bash scripts/02_obfuscate.sh              # T3 — needs ZKM_JAR; produces app-baseline/flow/string/flowstring.jar
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

- `src/main/java/com/finance/engine/` — the **only** code that's a target for control-flow/string-encryption obfuscation. `TransactionEngine.pipeline()` runs orders through validate → assessRisk → computeFee → checkBalanceAndReserve → route, each stage a real target for control-flow obfuscation. `RegulatoryLimits` holds the notional caps that are the target for string encryption. `CorrectnessRunner` (class name + `main()`) and `TransactionEngine$Order` (class name + all fields) are explicitly excluded from renaming in every `zkm/config-*.zkm` — `CorrectnessRunner` because it's the correctness oracle's entry point (`java -cp jar com.finance.engine.CorrectnessRunner`), `Order` because it's the public data contract `OrderFactory` (outside the engine package) constructs field-by-field.
- `src/main/java/com/finance/perf/` — JMH harness (`TransactionProcessingBenchmark`) and deterministic order generator (`OrderFactory`, fixed xorshift seed), plus the JMH-annotation-processor-generated invoker classes under `com/finance/perf/jmh_generated/` (created at `mvn package` time, before obfuscation). Every `zkm/config-*.zkm` opens this whole package tree **alongside** the engine — not because it's a target, but because ZKM needs to see (and correctly rewrite) its call sites into the engine when engine classes/methods get renamed; renaming, unlike control-flow/string-encryption, requires cross-class consistency, so it can't be done on the engine in isolation. `com.finance.perf.*`'s own names, control-flow and strings are explicitly excluded in every config, so its behavior/identity never changes — only its outgoing references to the (renamed) engine get updated. `org.openjdk.jmh.*` itself and all third-party libraries are never opened by ZKM (`classpath` only), so their bytecode stays 100% untouched and JMH's reflection keeps working.
- `pom.xml` builds a single shaded uber-jar, `target/app-original.jar`, with `Main-Class: org.openjdk.jmh.Main`. All four obfuscated variants (`app-baseline.jar`, `app-flow.jar`, `app-string.jar`, `app-flowstring.jar`) are produced by `scripts/02_obfuscate.sh`: extract `com/finance/engine/*` + `com/finance/perf/**` from `app-original.jar` once, run ZKM once per profile (`zkm/config-<profile>.zkm`, differing only in `obfuscateFlow`/`encryptStringLiterals`), then delete the old (pre-rename) entries from a copy of `app-original.jar` before merging the ZKM output back in — `jar uf` only adds/updates entries, so skipping the delete step leaves dead, unrenamed, fully-readable copies of the engine classes sitting unused in the jar (a real bug caught during migration: it doesn't break `CorrectnessRunner`/JMH functionally since nothing calls the dead classes, but it would have silently defeated the whole resilience experiment by handing a decompiler the original source of `RegulatoryLimits` etc. for free).
- **Why ZKM instead of Allatori**: Allatori Educational silently no-ops both paid features the study needs, `control-flow-obfuscation`/`extensive-flow-obfuscation` and `string-encryption` — confirmed empirically (MD5-identical `.class` files, per engine class, regardless of whether those properties were "enabled" in Allatori's config). Its only real remaining contribution, renaming, is also part of ZKM's `obfuscate` statement — so Allatori added a tool and a dependency without adding any actual obfuscation. It was removed from the pipeline entirely.
- Data flow across stages: `target/app-*.jar` (T2/T3) → `correctness/golden-*.txt` (T4) → `results/jmh-<arch>-<profile>.json` (+ `time-*.txt`, `thermal-*.log` on ARM) (T6) → `resilience/<profile>/*.java` decompiled sources (T7, manual LLM scoring against a rubric using `scripts/prompt-template.md`) → `analysis/summary.csv` + `analysis/overhead-<arch>-size<N>.png` (T8, `scripts/07_analyze.py`).
- `scripts/07_analyze.py` treats **fork** (not iteration) as the unit of statistical independence when aggregating JMH `rawData`, since iterations within a fork are autocorrelated (same JVM/JIT warmup). It picks Welch's t-test or Mann-Whitney U per Shapiro-Wilk normality, applies Holm-Bonferroni correction across the (arch, size) family, and reports Cliff's delta as effect size. `analysis-data-preparation.ipynb` and `analysis-exploratoria-dados.ipynb` (repo root) are a further, notebook-based cleaning + EDA pass on top of `analysis/summary.csv`/`arch_interaction.csv`, producing `analysis/summary_prepared.csv` / `arch_interaction_prepared.csv`.
- Any change that regenerates `target/app-*.jar` (e.g. re-running T3 after touching a `zkm/config-*.zkm`) invalidates every downstream artifact for the affected profiles — `results/`, `resilience/`, and everything derived from them (`analysis/*.csv`, both notebooks) — and T6/T7/T8 need to be re-run before those numbers can be trusted again.
- Cross-arch workflow: obfuscated jars are portable bytecode — do **not** re-obfuscate on ARM. Copy the same `target/app-*.jar` files to the Raspberry Pi, run `scripts/04_bench.sh` there, then copy `results/jmh-aarch64-*.json` back into the same `results/` directory before re-running T8. Compare relative overhead (%) across architectures, never absolute times (different CPUs).
- Any change to `TransactionEngine`'s business logic must keep `CorrectnessRunner` output stable for the fixed seed in `OrderFactory`, or update all `correctness/golden-*.txt` and re-validate — the whole resilience/performance comparison depends on original and obfuscated jars being behaviorally identical.
