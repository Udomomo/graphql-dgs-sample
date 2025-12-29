-- H2 用スキーマ

CREATE TABLE IF NOT EXISTS users(
    id VARCHAR PRIMARY KEY NOT NULL,
    name VARCHAR NOT NULL,
    project_v2 VARCHAR
);

CREATE TABLE IF NOT EXISTS repositories(
    id VARCHAR PRIMARY KEY NOT NULL,
    owner VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS issues(
    id VARCHAR PRIMARY KEY NOT NULL,
    url VARCHAR NOT NULL,
    title VARCHAR NOT NULL,
    closed INTEGER NOT NULL DEFAULT 0,
    number INTEGER NOT NULL,
    repository VARCHAR NOT NULL,
    CHECK (closed IN (0, 1)),
    FOREIGN KEY (repository) REFERENCES repositories(id)
);

CREATE TABLE IF NOT EXISTS projects(
    id VARCHAR PRIMARY KEY NOT NULL,
    title VARCHAR NOT NULL,
    url VARCHAR NOT NULL,
    owner VARCHAR NOT NULL,
    FOREIGN KEY (owner) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS pull_requests(
    id VARCHAR PRIMARY KEY NOT NULL,
    base_ref_name VARCHAR NOT NULL,
    closed INTEGER NOT NULL DEFAULT 0,
    head_ref_name VARCHAR NOT NULL,
    url VARCHAR NOT NULL,
    number INTEGER NOT NULL,
    repository VARCHAR NOT NULL,
    CHECK (closed IN (0, 1)),
    FOREIGN KEY (repository) REFERENCES repositories(id)
);

CREATE TABLE IF NOT EXISTS project_cards(
    id VARCHAR PRIMARY KEY NOT NULL,
    project VARCHAR NOT NULL,
    issue VARCHAR,
    pull_request VARCHAR,
    FOREIGN KEY (project) REFERENCES projects(id),
    FOREIGN KEY (issue) REFERENCES issues(id),
    FOREIGN KEY (pull_request) REFERENCES pull_requests(id),
    CHECK (issue IS NOT NULL OR pull_request IS NOT NULL)
);
