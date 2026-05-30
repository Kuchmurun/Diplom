CREATE TABLE clients
(
    id                   BIGSERIAL PRIMARY KEY,
    full_name            VARCHAR(255),
    gender               VARCHAR(50),
    birth_date           DATE,
    monthly_income       NUMERIC(19, 2),
    has_criminal_record  BOOLEAN,
    credit_history_score INTEGER,
    current_credit_debt  NUMERIC(19, 2),
    is_deleted           BOOLEAN
);

CREATE TABLE employees
(
    id        BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255),
    username  VARCHAR(255) NOT NULL UNIQUE,
    password  VARCHAR(255),
    role      VARCHAR(50),
    enabled   BOOLEAN
);

CREATE TABLE credit_applications
(
    id               BIGSERIAL PRIMARY KEY,
    client_id        BIGINT NOT NULL,
    requested_amount NUMERIC(19, 2),
    requested_months INTEGER,
    status           VARCHAR(50),
    created_at       TIMESTAMP,
    CONSTRAINT fk_credit_application_client
        FOREIGN KEY (client_id)
            REFERENCES clients (id)
);

CREATE TABLE credit_score_results
(
    id                BIGSERIAL PRIMARY KEY,
    application_id    BIGINT NOT NULL UNIQUE,
    score             INTEGER,
    reliability_level VARCHAR(50),
    approved_amount   NUMERIC(19, 2),
    decision_reason   TEXT,
    calculated_at     TIMESTAMP,
    CONSTRAINT fk_score_result_application
        FOREIGN KEY (application_id)
            REFERENCES credit_applications (id)
);

CREATE TABLE audit_logs
(
    id                BIGSERIAL PRIMARY KEY,
    employee_username VARCHAR(255),
    action            VARCHAR(255),
    entity_type       VARCHAR(255),
    entity_id         BIGINT,
    created_at        TIMESTAMP,
    details           TEXT
);