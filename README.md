# Employee Management System – README

## Bean Scopes: Singleton vs Prototype

Most beans are **singletons** (Spring’s default):

- `EmployeeServiceImpl`, `NotificationManager`, `EmployeeValidator`
- All `Notifier` implementations (`EmailNotifier`, `SmsNotifier`, `PushNotifier`)
- Both repository implementations (`InMemoryEmployeeRepository`, `FileBackedEmployeeRepository`)
- `AppConfig` and `DataInitializer`

They are stateless or hold shared application-wide state (repository data, notifier list), so a single instance is efficient and correct.

`AuditLogger` is **prototype-scoped**. Each request yields a new instance, because an audit logger should record each event independently and its instance ID proves a fresh logger is used per operation. Prototype scope prevents shared state across logging calls.

## Injection Types Used

- **Constructor injection** for all mandatory dependencies:
    - `EmployeeServiceImpl` receives `EmployeeRepository`, `NotificationManager`, `EmployeeValidator`, and `ObjectProvider<AuditLogger>` through its constructor (Lombok `@RequiredArgsConstructor`).
    - `NotificationManager` receives `List<Notifier>` via constructor – **collection injection** lets Spring inject all `Notifier` beans at once in `@Order` sequence.
    - `DataInitializer` receives `EmployeeService` as a `@Bean` method parameter.
    - Repository and notifier classes have no explicit dependencies.

Constructor injection is preferred: dependencies are explicit, fields can be `final`, and circular dependencies are avoided. Collection injection is used specifically to gather all notifiers without wiring them individually.

## Solving the Scoped-Bean Problem

`EmployeeServiceImpl` is a singleton but needs a fresh `AuditLogger` (prototype) on every call. Direct injection would capture one instance at startup and reuse it forever.

Solution: inject `ObjectProvider<AuditLogger>` into `EmployeeServiceImpl`. Inside each method, call `auditLoggerProvider.getObject()` to obtain a new logger. This is explicit, testable, and avoids hidden proxy behaviour (`ScopedProxyMode`). Printed instance IDs confirm each call gets a distinct logger.

## Active Profiles: dev vs prod

The repository implementation is chosen by the active Spring profile:

- **dev** – `InMemoryEmployeeRepository` is active. Data lives in an `ArrayList` and is lost on shutdown.
- **prod** – `FileBackedEmployeeRepository` is active. Data is persisted to `employees.csv` in the working directory and reloaded on startup.

Both classes implement `EmployeeRepository` and are annotated with `@Profile`. Only one is instantiated, depending on the profile set in `Main` via `context.getEnvironment().setActiveProfiles("dev")` (or `"prod"`). This cleanly separates development from production persistence without changing service code.