-- H2 用スキーマ

CREATE TABLE IF NOT EXISTS users(
    id VARCHAR(16) PRIMARY KEY NOT NULL,
    name VARCHAR(64) NOT NULL,
    project_v2 VARCHAR(16)
);

CREATE TABLE IF NOT EXISTS repositories(
    id VARCHAR(16) PRIMARY KEY NOT NULL,
    owner VARCHAR(16) NOT NULL,
    name VARCHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS issues(
    id VARCHAR(16) PRIMARY KEY NOT NULL,
    url VARCHAR(255) NOT NULL,
    title VARCHAR(64) NOT NULL,
    closed INTEGER NOT NULL DEFAULT 0,
    number INTEGER NOT NULL,
    repository VARCHAR(16) NOT NULL,
    CHECK (closed IN (0, 1)),
    FOREIGN KEY (repository) REFERENCES repositories(id)
);

CREATE TABLE IF NOT EXISTS projects(
    id VARCHAR(16) PRIMARY KEY NOT NULL,
    title VARCHAR(64) NOT NULL,
    url VARCHAR(255) NOT NULL,
    owner VARCHAR(16) NOT NULL,
    FOREIGN KEY (owner) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS pull_requests(
    id VARCHAR(16) PRIMARY KEY NOT NULL,
    base_ref_name VARCHAR(64) NOT NULL,
    closed INTEGER NOT NULL DEFAULT 0,
    head_ref_name VARCHAR(64) NOT NULL,
    url VARCHAR(255) NOT NULL,
    number INTEGER NOT NULL,
    repository VARCHAR(16) NOT NULL,
    CHECK (closed IN (0, 1)),
    FOREIGN KEY (repository) REFERENCES repositories(id)
);

CREATE TABLE IF NOT EXISTS project_cards(
    id VARCHAR(16) PRIMARY KEY NOT NULL,
    project VARCHAR(16) NOT NULL,
    issue VARCHAR(16),
    pull_request VARCHAR(16),
    FOREIGN KEY (project) REFERENCES projects(id),
    FOREIGN KEY (issue) REFERENCES issues(id),
    FOREIGN KEY (pull_request) REFERENCES pull_requests(id),
    CHECK (issue IS NOT NULL OR pull_request IS NOT NULL)
);
