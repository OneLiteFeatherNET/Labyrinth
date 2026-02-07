# Repository Guidelines

## LLM Action Protocol (Strict)
- MUST read relevant files before editing; do not guess structure or behavior.
- MUST keep changes minimal and scoped to the request.
- MUST record every action taken in the final response: files changed, commands run, tests run. If tests are not run, state "Not run" with a reason.
- MUST NOT invent outputs, test results, or file contents.
- MUST use the Gradle wrapper (`./gradlew`) for builds/tests.
- MUST ask if a requirement is ambiguous or missing.
- MUST keep `AGENTS.md` LLM-optimized after every change (clear, strict, and actionable).
- MUST express every requirement using direct, enforceable language (prefer `MUST` / `MUST NOT` / `SHOULD`).

## Task Execution Workflow (Strict)
- MUST follow a three-phase workflow: Planning → Refinement → Coding.
- MUST split user tasks into smaller tasks when necessary.
- MUST allow Planning and Refinement to repeat in cycles until scope and approach are agreed.

## Planning Phase (Strict)
- MUST provide a planning-poker estimate (T-shirt size or story points).
- MUST outline the approach and key decisions before any edits or commands.
- MUST ask whether to work on a new branch or the current branch.

## Refinement Phase (Strict)
- MUST ask clarification questions required to make the solution correct.
- MUST pause before Coding until Refinement is complete or assumptions are explicitly confirmed.

## Coding Phase (Strict)
- MUST begin only after Refinement is complete or assumptions are explicitly confirmed.
- MUST prioritize correctness and maintainability over speed.
- MUST default to the simplest maintainable solution; avoid cleverness.
- MUST follow established best practices for the stack.

## Consultation (Strict)
- MUST consult the project owner before architectural changes or adding dependencies.
- MUST consult the project owner before adopting patterns that impact long-term maintainability.

## Documentation Sync (Strict)
- MUST keep documentation in sync with workflow or maintainability changes.

## Ticketing (Strict)
- MUST have a ticket in the project’s ticketing system for every task.
- MUST use GitHub Issues or GitHub Projects for all tickets.
- MUST include a planning-poker estimate in each ticket.
- MUST split large work into sub-tickets.

## Commit Guidelines (Strict)
- MUST follow Conventional Commits (`feat:`, `fix:`, `chore(deps):`) as seen in history.
- MUST include a commit body (why/what/impact).
- MUST include a ticket reference in every commit.
- MUST keep commits small and scoped (e.g., `feat(commands): add zone rename`).
- MUST ensure every commit is buildable and testable so `git bisect` can be used reliably.
- MUST keep each file change in a separate commit (one file per commit).

## Pull Request Guidelines (Strict)
- MUST include a PR summary and tests run; link issues when relevant.

## Project Structure (Strict)
- MUST place production code under `src/main/java/net/onelitefeather/labyrinth/`.
- MUST place resources in `src/main/resources/` (e.g., `config.yml`).
- MUST place tests in `src/test/java/net/onelitefeather/labyrinth/` with matching package structure.

## Module Organization (Strict)
- MUST keep packages under `net.onelitefeather.labyrinth.*`.
- MUST follow existing module names (`commands`, `listener`, `service`, `utils`).

## Build Commands (Strict)
- MUST use `./gradlew build` for full compile + tests when asked to validate.

## Test Commands (Strict)
- MUST use `./gradlew test` for unit tests and report the exact command in the final response.

## Development Commands (Strict)
- MUST use `./gradlew shadowJar` to produce `build/libs/labyrinth.jar`.
- MUST use `./gradlew runServer` for manual Paper server checks (Minecraft `1.21.11`).

## Coding Style (Strict)
- MUST follow Java 4-space indentation and braces on the same line.
- SHOULD keep classes small and focused; validation logic in `service`, command wiring in `commands`, event logic in `listener`.
- MUST apply SOLID, KISS, and DRY principles.
- MUST follow Java best practices (clear naming, minimal visibility, immutability where possible, and avoiding unnecessary static state).

## Naming Conventions (Strict)
- MUST use `camelCase` for members and `PascalCase` for classes.

## Testing Guidelines (Strict)
- MUST use JUnit Jupiter (JUnit 5) and MockBukkit for tests.
- MUST name test classes `*Test` and keep them in matching packages.
- MUST add tests for any touched source files when required by project rules.
- MUST add success and failure tests for new commands/listeners.
- SHOULD prefer deterministic tests; avoid time- or network-dependent behavior.

## Configuration (Strict)
- MUST keep build configuration in `build.gradle.kts` and Gradle wrapper scripts.
- MUST update `README.md` if config keys change.

## Runtime Notes (Strict)
- MUST keep plugin entrypoint as `net.onelitefeather.labyrinth.Labyrinth` unless explicitly changing architecture.
- MUST update `README.md` if commands or permissions change.

## Security (Strict)
- MUST treat the Bukkit plugin as security-critical: validate all player input, permissions, and config access paths.
- MUST avoid unsafe reflection or dynamic class loading unless explicitly approved and documented.
- MUST ensure no command can bypass permission checks or zone validation.

## Architecture (Strict)
- MUST keep architecture stable and maintainable for 5+ years: favor clear boundaries, minimal coupling, and well-tested public behaviors.
- MUST document any architectural changes in `README.md` or a dedicated design note if added.

## Comments (Strict)
- MUST add succinct comments only for non-obvious or domain-specific logic.
