-- we don't know how to generate root <with-no-name> (class Root) :(

grant alter, alter routine, create, create routine, create temporary tables, create view, delete, drop, event, execute, index, insert, lock tables, references, select, show view, trigger, update on card_game.* to thelotosss;

grant select on performance_schema.* to 'mysql.session'@localhost;

grant trigger on sys.* to 'mysql.sys'@localhost;

grant alter, alter routine, application_password_admin, audit_abort_exempt, audit_admin, authentication_policy_admin, backup_admin, binlog_admin, binlog_encryption_admin, clone_admin, connection_admin, create, create role, create routine, create tablespace, create temporary tables, create user, create view, delete, drop, drop role, encryption_key_admin, event, execute, file, firewall_exempt, flush_optimizer_costs, flush_status, flush_tables, flush_user_resources, group_replication_admin, group_replication_stream, index, innodb_redo_log_archive, innodb_redo_log_enable, insert, lock tables, passwordless_user_admin, persist_ro_variables_admin, process, references, reload, replication client, replication slave, replication_applier, replication_slave_admin, resource_group_admin, resource_group_user, role_admin, select, sensitive_variables_observer, service_connection_admin, session_variables_admin, set_user_id, show databases, show view, show_routine, shutdown, super, system_user, system_variables_admin, table_encryption_admin, telemetry_log_admin, trigger, update, xa_recover_admin, grant option on *.* to irepeshko;

grant audit_abort_exempt, firewall_exempt, select, system_user on *.* to 'mysql.infoschema'@localhost;

grant audit_abort_exempt, authentication_policy_admin, backup_admin, clone_admin, connection_admin, firewall_exempt, persist_ro_variables_admin, session_variables_admin, shutdown, super, system_user, system_variables_admin on *.* to 'mysql.session'@localhost;

grant audit_abort_exempt, firewall_exempt, system_user on *.* to 'mysql.sys'@localhost;

grant alter, alter routine, application_password_admin, audit_abort_exempt, audit_admin, authentication_policy_admin, backup_admin, binlog_admin, binlog_encryption_admin, clone_admin, connection_admin, create, create role, create routine, create tablespace, create temporary tables, create user, create view, delete, drop, drop role, encryption_key_admin, event, execute, file, firewall_exempt, flush_optimizer_costs, flush_status, flush_tables, flush_user_resources, group_replication_admin, group_replication_stream, index, innodb_redo_log_archive, innodb_redo_log_enable, insert, lock tables, passwordless_user_admin, persist_ro_variables_admin, process, references, reload, replication client, replication slave, replication_applier, replication_slave_admin, resource_group_admin, resource_group_user, role_admin, select, sensitive_variables_observer, service_connection_admin, session_variables_admin, set_user_id, show databases, show view, show_routine, shutdown, super, system_user, system_variables_admin, table_encryption_admin, telemetry_log_admin, trigger, update, xa_recover_admin, grant option on *.* to root;

grant alter, alter routine, application_password_admin, audit_abort_exempt, audit_admin, authentication_policy_admin, backup_admin, binlog_admin, binlog_encryption_admin, clone_admin, connection_admin, create, create role, create routine, create tablespace, create temporary tables, create user, create view, delete, drop, drop role, encryption_key_admin, event, execute, file, firewall_exempt, flush_optimizer_costs, flush_status, flush_tables, flush_user_resources, group_replication_admin, group_replication_stream, index, innodb_redo_log_archive, innodb_redo_log_enable, insert, lock tables, passwordless_user_admin, persist_ro_variables_admin, process, references, reload, replication client, replication slave, replication_applier, replication_slave_admin, resource_group_admin, resource_group_user, role_admin, select, sensitive_variables_observer, service_connection_admin, session_variables_admin, set_user_id, show databases, show view, show_routine, shutdown, super, system_user, system_variables_admin, table_encryption_admin, telemetry_log_admin, trigger, update, xa_recover_admin, grant option on *.* to root@localhost;

create table entrants
(
    birthday     date             not null,
    gender       bit default b'1' not null,
    is_deleted   bit default b'0' not null,
    rating_score double           not null,
    id           bigint auto_increment
        primary key,
    case_number  varchar(20)      not null,
    name         varchar(50)      not null,
    patronymic   varchar(50)      not null,
    surname      varchar(50)      not null,
    constraint UKj36ja5h4xym91tmpdw81xno84
        unique (case_number),
    check (`birthday` between _utf8mb4\'1914-01-01\' and _utf8mb4\'2008-01-01\'),
	check ((`rating_score` > 120.00) and (`rating_score` <= 200.00)),
	check (regexp_like(`case_number`,_utf8mb4\'^[Ð-Ð©Ð®Ð¯ÒÐÐÐ]{1,10}[0-9]{2}-[0-9]{1,4}$\',_utf8mb4\'c\') = 1),
	check (regexp_like(`name`,_utf8mb4\'^(?=.{1,50}$)[Ð-Ð©Ð®Ð¯ÒÐÐÐ](?:[Ð-Ð¯ÒÐÐÐÐ°-ÑÒÑÑÑÊ¼]*[Ð-Ð¯ÒÐÐÐÐ°-ÑÒÑÑÑ])?(?:[ -][Ð-Ð©Ð®Ð¯ÒÐÐÐ](?:[Ð-Ð¯ÒÐÐÐÐ°-ÑÒÑÑÑÊ¼]*[Ð-Ð¯ÒÐÐÐÐ°-ÑÒÑÑÑ])?)*$\',_utf8mb4\'c\') = 1),
	check (regexp_like(`patronymic`,_utf8mb4\'^(?=.{1,50}$)[Ð-Ð©Ð®Ð¯ÒÐÐÐ](?:[Ð-Ð¯ÒÐÐÐÐ°-ÑÒÑÑÑÊ¼]*[Ð-Ð¯ÒÐÐÐÐ°-ÑÒÑÑÑ])?(?:[ -][Ð-Ð©Ð®Ð¯ÒÐÐÐ](?:[Ð-Ð¯ÒÐÐÐÐ°-ÑÒÑÑÑÊ¼]*[Ð-Ð¯ÒÐÐÐÐ°-ÑÒÑÑÑ])?)*$\',_utf8mb4\'c\') = 1),
	check (regexp_like(`surname`,_utf8mb4\'^(?=.{1,50}$)[Ð-Ð©Ð®Ð¯ÒÐÐÐ](?:[Ð-Ð¯ÒÐÐÐÐ°-ÑÒÑÑÑÊ¼]*[Ð-Ð¯ÒÐÐÐÐ°-ÑÒÑÑÑ])?(?:[ -][Ð-Ð©Ð®Ð¯ÒÐÐÐ](?:[Ð-Ð¯ÒÐÐÐÐ°-ÑÒÑÑÑÊ¼]*[Ð-Ð¯ÒÐÐÐÐ°-ÑÒÑÑÑ])?)*$\',_utf8mb4\'c\') = 1)
);

create table students
(
    deleted            bit                                    default b'0'   not null,
    entrant_id         bigint                                                null,
    id                 bigint auto_increment
        primary key,
    corporate_email    varchar(123)                                          not null,
    funding_type       enum ('BUDGET', 'CONTRACT')                           not null,
    scholarship_status enum ('INCREASED', 'NONE', 'ORDINARY') default 'NONE' not null,
    constraint UKebgu37lcg62sdwu3n7d7gre0v
        unique (entrant_id),
    constraint UKng7nfmdg9laddmqsllbe38mkx
        unique (corporate_email)
);

