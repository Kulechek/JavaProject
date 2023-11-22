#SystemMonitor

**Описание**

Этот репозиторий содержит программную реализацию системы мониторинга, разработанной в рамках практической подготовки. Система предназначена для отслеживания и записи параметров сетевых протоколов и нагрузочных показателей системы.

**Введение**

Выбор данной темы обусловлен стремлением освоить создание ядра системы для отслеживания сетевых параметров, а также их сохранение в базе данных. Исследование и разработка такой функциональности предоставляют уникальную возможность практического применения знаний о сетевых протоколах, базах данных и веб-разработке.

**Программная реализация**

Импортируемые библиотеки:
spring-boot-starter-thymeleaf: Интеграция Thymeleaf в Spring Boot для разработки веб-приложений с динамическим HTML-контентом и шаблонами.
spring-boot-starter-web: Основные компоненты для разработки веб-приложений, такие как веб-сервер и обработка HTTP-запросов.
spring-boot-starter-data-jpa: Автоматическое создание репозиториев для работы с базой данных через JPA.
org.postgresql:postgresql: Драйвер PostgreSQL для взаимодействия с базой данных.
Глобальные переменные:
Глобальные переменные для настройки системы хранятся в конфигурационном файле config.cfg. Этот файл содержит параметры, такие как:

trackCpu: отслеживать загрузку CPU (1 - да, 0 - нет).
trackRam: отслеживать использование памяти RAM (1 - да, 0 - нет).
trackDisk: отслеживать использование диска (1 - да, 0 - нет).
trackNet: отслеживать пинг до DNS-сервера (1 - да, 0 - нет).
trackNetAddress: адрес DNS-сервера для отслеживания пинга.
trackSecondsInterval: интервал в секундах между замерами.

Эти переменные загружаются и используются в классе SystemMonitorService для настройки па- раметров мониторинга.

**Описание компонентов:**
• TemnikovJavaProjectApplication: Класс, представляющий точку входа в приложение. Здесь инициализируется чтение конфигурационного файла и запускается Spring контекст.
• DNS: Класс, реализующий функциональность отслеживания задержки запросов до DNS серверов. Включает методы для проверки корректности DNS сервера и измерения задержки.
• ConfigManager: Класс для управления конфигурационными значениями. Осуществляет чтение данных из файла конфигурации и предоставляет доступ к ним.
• TestPostService: Сервис для работы с данными о параметрах, реализующий сохранение и получение данных из базы.
• SystemMonitorService: Основной сервис системы, обеспечивающий отслеживание параметров системы с заданной периодичностью и их запись в базу.
• TestPostRepository: Интерфейс репозитория для работы с данными о параметрах, основанный на Spring Data.
• TestPost: Сущность, представляющая данные о параметрах системы.
• MainController: Контроллер для обработки запросов на главной странице, отображающий данные о параметрах на веб-интерфейсе.
Данные о параметрах сохраняются в базу данных с использованием TestPostService и TestPostRepository. Контроллер MainController отображает данные на веб-интерфейсе.
