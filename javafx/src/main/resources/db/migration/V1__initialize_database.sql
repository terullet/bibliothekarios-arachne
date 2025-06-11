CREATE TABLE universes (
    universe_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    summary CLOB NOT NULL
);

CREATE TABLE works (
    work_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    summary CLOB NOT NULL,
    registered_at TIMESTAMP(3) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT uq_work_site UNIQUE (work_id, site_id),
    CONSTRAINT fk_work_site FOREIGN KEY site_id REFERENCES sites(site_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE works_universe_map (
    work_id BIGINT PRIMARY KEY,
    universe_id BIGINT NOT NULL,
    CONSTRAINT fk_works_universe_work FOREIGN KEY work_id REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_works_universe_universe FOREIGN KEY universe_id REFERENCES universes(universe_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE narou_works (
    work_id BIGINT PRIMARY KEY,
    ncode VARCHAR(12) UNIQUE NOT NULL,
    narou_id BIGINT UNIQUE NOT NULL,
    genre_id INT NOT NULL,
    CONSTRAINT fk_narou_work FOREIGN KEY work_id REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE
);
